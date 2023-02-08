package com.autowise.module.base.network;

import static com.autowise.module.base.network.Util.FORM_URLENCODED_TYPE;
import static com.autowise.module.base.network.Util.JSON_CONTENT_TYPE;
import static com.autowise.module.base.network.Util.SIGNATURE_ORIGINAL_PATH;
import static com.autowise.module.base.network.Util.UTF8;
import static com.autowise.module.base.network.Util.X_REQUESTED_TYPE;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 *
 */
public class SignatureInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {

    Request userRequest = chain.request();
    Request.Builder requestBuilder = userRequest.newBuilder();

    HttpUrl userHttpUrl = userRequest.url();
    HttpUrl.Builder httpUrlBuilder = userHttpUrl.newBuilder();

    // By default, the HTTP request method is already an uppercase letter
    final String method = userRequest.method();

    String path = "";

    if (SIGNATURE_ORIGINAL_PATH.equals(userRequest.header(X_REQUESTED_TYPE))) {
      requestBuilder.removeHeader(X_REQUESTED_TYPE);
      path = userHttpUrl.uri().getRawPath();
    } else {
      path = userHttpUrl.uri().getPath();
    }

    String signature = sign(method, path, userRequest, getParams(userRequest));

    httpUrlBuilder.addQueryParameter("sign", signature);
    requestBuilder.url(httpUrlBuilder.build());

    return chain.proceed(requestBuilder.build());
  }

  private String sign(String method, String path, Request userRequest, ArrayList<String> params) {

    return null;
  }

  private ArrayList<String> getParams(Request request) throws IOException {
    final HttpUrl httpUrl = request.url();
    final int querySize = httpUrl.querySize();
    final ArrayList<String> params = new ArrayList<>(querySize);

    // the query parameter key value pairs
    for (int index = 0; index < querySize; index++) {
      String name = httpUrl.queryParameterName(index);
      String value = httpUrl.queryParameterValue(index);

      if (Util.isEmpty(name) || Util.isEmpty(value)) {
        continue;
      }

      params.add(name + '=' + value);
    }

    // the body parameter key value pairs
    final String method = request.method();
    if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
      RequestBody requestBody = request.body();
      MediaType mediaType = requestBody.contentType();
      if (mediaType != null) {
        if (JSON_CONTENT_TYPE.type().equals(mediaType.type())
                && JSON_CONTENT_TYPE.subtype().equals(mediaType.subtype())) {
          Buffer buffer = new Buffer();
          requestBody.writeTo(buffer);

          Charset charset = UTF8;
          MediaType contentType = requestBody.contentType();
          if (contentType != null) {
            charset = contentType.charset(UTF8);
          }

          String jsonBody = buffer.readString(charset);
          params.add("jsonBody=" + jsonBody);
        } else if (FORM_URLENCODED_TYPE.equals(mediaType)) {
          FormBody formBody = (FormBody) requestBody;
          for (int index = 0; index < formBody.size(); index++) {
            String name = formBody.name(index);
            String value = formBody.value(index);
            params.add(name + '=' + value);
          }
        }
      }
    }

    return params;
  }
}

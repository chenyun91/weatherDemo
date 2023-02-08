package com.autowise.module.base.network;

import android.os.Build;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import androidx.annotation.Nullable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okio.ByteString;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 */
public class Util {
  static final Charset UTF8 = Charset.forName("UTF-8");
  static final MediaType FORM_URLENCODED_TYPE =
      MediaType.parse("application/x-www-form-urlencoded");
  static final MediaType JSON_CONTENT_TYPE =
      MediaType.parse("application/json");
  /** Common non-standard request fields for http header */
  static final String X_REQUESTED_TYPE = "X-Requested-Type";
  static final String ACCOUNT = "Account";
  static final String REMOVE_DEVICE_ID = "RemoveDeviceId";
  static final String CUSTOM_DEVICE_ID = "CustomDeviceId";
  static final String NOT_INTERCEPT_RESULT_CODE = "NotInterceptResultCode";
  static final String SIGNATURE_ORIGINAL_PATH = "SignatureOriginalPath";

  static final String TIMESTAMP = "timestamp";
  static final String DEVICE_ID = "device_id";
  static final String TERMINAL = "terminal";
  static final String NONCE = "nonce";
  static final String COUNTRY_CODE = "country_code";



  /**
   * Returns true if the string is null or 0-length.
   *
   * @param str the string to be examined
   * @return true if str is null or zero length
   */
  public static boolean isEmpty(@Nullable CharSequence str) {
    if (str == null || str.length() == 0) {
      return true;
    } else {
      return false;
    }
  }
  ////TODO
  //@Deprecated
  //public static String getUserAgent(Context context) {
  //  String userAgent = "";
  //  if (SDK_INT >= JELLY_BEAN_MR1) {
  //    try {
  //      userAgent = WebSettings.getDefaultUserAgent(context);
  //    } catch (Exception e) {
  //      userAgent = System.getProperty("http.agent");
  //    }
  //  } else {
  //    userAgent = System.getProperty("http.agent");
  //  }
  //
  //  StringBuffer sb = new StringBuffer();
  //  for (int i = 0, length = userAgent.length(); i < length; i++) {
  //    char c = userAgent.charAt(i);
  //    if (c <= '\u001f' || c >= '\u007f') {
  //      sb.append(String.format("\\u%04x", (int) c));
  //    } else {
  //      sb.append(c);
  //    }
  //  }
  //  sb.append("/");
  //  sb.append(DeviceUtil.getVersionName(context));
  //  sb.append("-Lifestyle-Android");
  //  return sb.toString();
  //}

  public static String getModel() {
    String model = Build.MODEL;
    if (model != null) {
      model = model.trim().replaceAll("\\s*", "");
    } else {
      model = "";
    }
    return model;
  }

  public static String rand32() {
    int randInt = 0;
    int bound = 32;
    if (SDK_INT >= LOLLIPOP) {
      randInt = ThreadLocalRandom.current().nextInt(bound);
    } else {
      randInt = ThreadLocalRandom.current().nextInt(bound);
    }

    return ByteString.encodeUtf8(String.valueOf(randInt)).md5().hex();
  }

  public static FormBody.Builder newFormBuilder(FormBody formBody) {
    FormBody.Builder formBuilder = new FormBody.Builder();
    int size = formBody.size();
    for (int index = 0; index < size; index++) {
      formBuilder.add(formBody.name(index), formBody.value(index));
    }
    return formBuilder;
  }

  /** Returns an immutable copy of {@code list}. */
  public static <T> List<T> immutableList(List<T> list) {
    return Collections.unmodifiableList(new ArrayList<>(list));
  }

  private Util() {
    throw new AssertionError("No instance.");
  }
}

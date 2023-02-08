package com.autowise.module.base.common.configs

import com.autowise.module.base.AppContext


/**
 * User: chenyun
 * Date: 2022/2/21
 * Description:
 * FIXME
 */
enum class EnvConfig(
    val env: String,
    val baseUrl: String,
    val wsBaseUrl: String,
    val elkUrl: String,
    val imAppId: Int
) {
    TestConfig(ENV.TEST, URL.testBaseUrl, URL.testWsBaseUrl, URL.testElkUrl, IM.DEV_SDKAPPID),
    ProdConfigCN(
        ENV.PROD_CN,
        URL.prodBaseUrl_CN,
        URL.prodWsBaseUrl_CN,
        URL.prodElkUrl_CN,
        IM.PROD_SDKAPPID
    ),
    ProdConfigEU(
        ENV.PROD_EU,
        URL.prodBaseUrl_EU,
        URL.prodWsBaseUrl_EU,
        URL.prodElkUrl_EU,
        IM.PROD_SDKAPPID
    );
}

object EnvUtils {

    fun getConfig(env: String): EnvConfig {
        EnvConfig.values().forEach {
            if (it.env == env) {
                return it
            }
        }
        return EnvConfig.ProdConfigCN
    }

    fun getUpgradeCountry(): String {
        return when (AppContext.env) {
            ENV.PROD_EU -> "eu"
            else -> "cn"
        }
    }
}

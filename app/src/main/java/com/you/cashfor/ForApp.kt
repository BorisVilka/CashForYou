package com.you.cashfor

import android.app.Application
import android.util.Log
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.onesignal.OneSignal
import java.io.IOException

var sub1:String? = null
var sub2:String? = null
var sub3:String? = null
var af_status:String? = null
var appsflyer_id:String? = null
var idfa:String? = null
var appsflyer_key= "YApyirF2ACU25GC3apvFbN"



class ForApp:Application() {
    override fun onCreate() {
        super.onCreate()
        init()

        MobileAds.initialize(this) {}
        Thread{
            try {
                val info = AdvertisingIdClient.getAdvertisingIdInfo(this)
                idfa = info.id
                Log.e("idfa", "$idfa")
            } catch (exception: IOException) {
            } catch (exception: GooglePlayServicesRepairableException) {
            } catch (exception: GooglePlayServicesNotAvailableException) {
            }
        }.start()

        // получаем уникальный айди
        appsflyer_id = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        Log.e("appsflyer_id", "$appsflyer_id")

        val conversionListener  = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                p0?.let { cvData ->
                    cvData.map {
                        Log.e("APS", "conversion_attribute:  ${it.key} = ${it.value}")
                    }

                    af_status = cvData.getValue("af_status").toString()

                    if (af_status == "Non-organic"){

                        val campaign = cvData.getValue("campaign").toString().split("_")
                        Log.e("campaign", "$campaign")
                        try {sub1 = campaign[0]} catch (e: Error){sub1 = ""}
                        try {sub2 = campaign[1]} catch (e: Error){sub2 = ""}
                        try {sub3 = campaign[2]} catch (e: Error){sub3 = ""}

                    }
                }

                val eventValues = HashMap<String, Any>()
                AppsFlyerLib.getInstance().logEvent(getApplicationContext(),

                    AFInAppEventType.COMPLETE_REGISTRATION, eventValues)
            }

            override fun onConversionDataFail(p0: String?) {}
            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
            override fun onAttributionFailure(p0: String?) {}

        }

        AppsFlyerLib.getInstance().run {
            init(appsflyer_key, conversionListener, this@ForApp)
            start(this@ForApp)
        }
    }
    private fun init(){
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("2ffa65a9-8030-4668-a73e-2ea5b98c3501")
    }
}
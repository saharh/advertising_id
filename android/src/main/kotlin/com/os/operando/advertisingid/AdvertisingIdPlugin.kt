package com.os.operando.advertisingid

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlin.concurrent.thread

class AdvertisingIdPlugin() : FlutterPlugin, ActivityAware, MethodCallHandler {
    private lateinit var applicationContext: Context
    private val channel: MethodChannel? = null

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        applicationContext = binding.applicationContext
        val channel = MethodChannel(binding.binaryMessenger, "advertising_id")
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    }

    override fun onDetachedFromActivity() {
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getAdvertisingId" -> thread {
                try {
                    val id = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id
                    Handler(Looper.getMainLooper()).post {
                        result.success(id)
                    }
                } catch (e: Exception) {
                    Handler(Looper.getMainLooper()).post {
                        result.error(e.javaClass.canonicalName, e.localizedMessage, null)
                    }
                }
            }
            "isLimitAdTrackingEnabled" -> thread {
                try {
                    val isLimitAdTrackingEnabled = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).isLimitAdTrackingEnabled
                    Handler(Looper.getMainLooper()).post {
                        result.success(isLimitAdTrackingEnabled)
                    }
                } catch (e: Exception) {
                    Handler(Looper.getMainLooper()).post {
                        result.error(e.javaClass.canonicalName, e.localizedMessage, null)
                    }
                }
            }
            else -> result.notImplemented()
        }
    }
}

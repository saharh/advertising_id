import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class AdvertisingId {
  static const MethodChannel _channel = const MethodChannel('advertising_id');

  static Future<String?> id([bool requestTrackingAuthorization = false]) async {
    final String? id = await _channel.invokeMethod(
        'getAdvertisingId', requestTrackingAuthorization);
    return id;
  }

  static Future<String?> idfv([bool requestTrackingAuthorization = false]) async {
    if (!Platform.isIOS) {
      return null;
    }
    final String? idfv = await _channel.invokeMethod(
        'getAdvertisingIdfv', requestTrackingAuthorization);
    return idfv;
  }

  static Future<bool?> get isLimitAdTrackingEnabled async {
    return await _channel.invokeMethod('isLimitAdTrackingEnabled');
  }
}

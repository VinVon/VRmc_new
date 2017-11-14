package com.cs.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by cs on 2016/10/11.
 */

public class SystemUtils {
	/**
	 * 获取手机系统android版本
	 *
	 * @return
	 */
	public static String getDeviceSystemVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机系统信息（例如：miui8.0)
	 *
	 * @return
	 */
	public static String getDeviceSystem() {
		return Build.DISPLAY;
	}

	/**
	 * 获取手机类型
	 *
	 * @return
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
	}

	/**
	 * 获取手机唯一标识
	 *
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
		if (tm != null) {
			return tm.getDeviceId();
		}
		return "";
	}

	/**
	 * 获取app版本号
	 *
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			return packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}

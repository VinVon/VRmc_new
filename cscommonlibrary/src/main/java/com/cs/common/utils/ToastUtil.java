package com.cs.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cs on 16/9/28.
 */

public class ToastUtil {
	public static void showMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	public static void showLongMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}

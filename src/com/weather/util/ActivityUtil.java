package com.weather.util;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.widget.Toast;

public class ActivityUtil {
	/**
	 * ���ڸ�ʽ����
	 */
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * ��ʾToast������Ϣ��
	 * @param context
	 * @param text
	 */
	public static void showMessageByToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}

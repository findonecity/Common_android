package com.example.zj.common_android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreferencesManager {
	public static final String SP_FILE_USER = "user";
	private static SharedPreferencesManager manager = null;
	private static SharedPreferences hmSpref;
	private static Editor editor;
	private final String Phone = "phone";
	private final String FLAG = "flag";
	private SharedPreferencesManager(){}

	public synchronized static SharedPreferencesManager getInstance(){
		if(null==manager){
			synchronized(SharedPreferencesManager.class){
				if(null==manager){
					manager = new SharedPreferencesManager();
				}
			}
		}
		return manager;
	}

	public void putFlag(Context context, boolean flag) {
		hmSpref = context.getSharedPreferences("hmSpref", Context.MODE_PRIVATE);
		editor = hmSpref.edit();
		editor.putBoolean(FLAG, flag);
		editor.commit();
	}
	public void putPhone(Context context, boolean flag) {
		hmSpref = context.getSharedPreferences("hmSpref", Context.MODE_PRIVATE);
		editor = hmSpref.edit();
		editor.putBoolean(Phone, flag);
		editor.commit();
	}
	//第一次请求权限的时候，返回的flag是false
	public boolean getPhone(Context context) {
		hmSpref = context.getSharedPreferences("hmSpref", Context.MODE_PRIVATE);
		return hmSpref.getBoolean(Phone, false);
	}
	//第一次请求权限的时候，返回的flag是false
	public boolean getFlag(Context context) {
		hmSpref = context.getSharedPreferences("hmSpref", Context.MODE_PRIVATE);
		return hmSpref.getBoolean(FLAG, false);
	}

	public String getData(Context context, String fileName, String key){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		return sf.getString(key, "");
	}


	public void putData(Context context, String fileName, String key, String value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public void saveCookie(Context context, String fileName, String value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString("cookie", value);
		editor.commit();
	}

	public long getCookie(Context context, String fileName){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		return sf.getLong("cookie", 0);
	}

	public void out(Context context, String fileName){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.clear();
		editor.commit();
	}

	public int getIntData(Context context, String fileName, String key){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		return sf.getInt(key, 0);
	}

	public void putIntData(Context context, String fileName, String key, int value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public long getLongData(Context context, String fileName, String key){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		return sf.getLong(key, 0);
	}

	public void putLongData(Context context, String fileName, String key, long value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putLong(key, value);
		editor.commit();
	}
}	

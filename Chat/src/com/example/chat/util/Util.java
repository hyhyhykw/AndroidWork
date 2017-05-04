package com.example.chat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONStringer;

/**
 * 工具类
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-5-9
 * @version 1.1.0
 */
public class Util {
	/**
	 * 设置时间格式
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间格式
		Date date = new Date(System.currentTimeMillis());
		String strDate = format.format(date);
		return strDate;

	}

	/**
	 * 将字符串组织成json格式的字符串
	 * 
	 * @param type
	 * @param chatContent
	 * @param name
	 * @return
	 */
	public static String getJSonStr(String type, String chatContent, String name) {
		String jsonstr = "";
		try {
			jsonstr = new JSONStringer().object().key("type").value(type)
					.key("chatContent").value(chatContent).key("name")
					.value(name).endObject().toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonstr;

	}

	/**
	 * 私聊Json串
	 * 
	 * @param type
	 *            3标识私聊
	 * @param chatContent
	 * @param personalName私聊者名称
	 * @param name
	 */
	public static String personalChatJson(String type, String chatContent,
			String personalName, String name) {
		String jsonstr = "";
		try {
			jsonstr = new JSONStringer().object().key("type").value(type)
					.key("chatContent").value(chatContent).key("personalName")
					.value(personalName).key("name").value(name).endObject()
					.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonstr;
	}
}

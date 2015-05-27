package com.weather.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Constant {
	/**
	 * 命名空间
	 */
	public static final String NAMESPACE = "http://WebXml.com.cn/";
	/**
	 * 获取天气信息URL
	 */
	public static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	/**
	 * 获得省份名方法名
	 */
	public static String GET_PROVINCES_METHOD_NAME = "getSupportProvince";
	/**
	 * 获得省份名soapAction
	 */
	public static String GET_PROVINCES_SOAP_ACTION = "http://WebXml.com.cn/getSupportProvince";
	/**
	 * 获取省份名返回数据key
	 */
	public static String GET_PROVINCES_RESULT_NAME = "getSupportProvinceResult";
	/**
	 * 获得城市名方法名
	 */
	public static String GET_CITYS_METHOD_NAME = "getSupportCity";
	/**
	 * 获得城市名soapAction
	 */
	public static String GET_CITYS_SOAP_ACTION = "http://WebXml.com.cn/getSupportCity";
	/**
	 * 获取城市名返回数据key
	 */
	public static String GET_CITYS_RESULT_NAME = "getSupportCityResult";
	/**
	 * 获得天气信息方法名
	 */
	public static String GET_CITY_WEATHER_METHOD_NAME = "getWeatherbyCityName";
	/**
	 * 获得天气信息soapAction
	 */
	public static String GET_CITY_WEATHER_SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";
	/**
	 * 获得天气信息返回数据key
	 */
	public static String GET_CITY_WEATHER_RESULT_NAME = "getWeatherbyCityNameResult";
	/**
	 * 获取字符串中的中文
	 * @param oriText
	 * @return
	 */
	public static List<String> GetChineseWord(String oriText)
	{
		List<String> chineseWordsList = new ArrayList<String>();
		String regex="([\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(oriText);
		while(matcher.find()){
			chineseWordsList.add(matcher.group());
		}
		return chineseWordsList;
	}
}

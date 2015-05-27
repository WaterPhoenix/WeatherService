package com.weather.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Constant {
	/**
	 * �����ռ�
	 */
	public static final String NAMESPACE = "http://WebXml.com.cn/";
	/**
	 * ��ȡ������ϢURL
	 */
	public static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	/**
	 * ���ʡ����������
	 */
	public static String GET_PROVINCES_METHOD_NAME = "getSupportProvince";
	/**
	 * ���ʡ����soapAction
	 */
	public static String GET_PROVINCES_SOAP_ACTION = "http://WebXml.com.cn/getSupportProvince";
	/**
	 * ��ȡʡ������������key
	 */
	public static String GET_PROVINCES_RESULT_NAME = "getSupportProvinceResult";
	/**
	 * ��ó�����������
	 */
	public static String GET_CITYS_METHOD_NAME = "getSupportCity";
	/**
	 * ��ó�����soapAction
	 */
	public static String GET_CITYS_SOAP_ACTION = "http://WebXml.com.cn/getSupportCity";
	/**
	 * ��ȡ��������������key
	 */
	public static String GET_CITYS_RESULT_NAME = "getSupportCityResult";
	/**
	 * ���������Ϣ������
	 */
	public static String GET_CITY_WEATHER_METHOD_NAME = "getWeatherbyCityName";
	/**
	 * ���������ϢsoapAction
	 */
	public static String GET_CITY_WEATHER_SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";
	/**
	 * ���������Ϣ��������key
	 */
	public static String GET_CITY_WEATHER_RESULT_NAME = "getWeatherbyCityNameResult";
	/**
	 * ��ȡ�ַ����е�����
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

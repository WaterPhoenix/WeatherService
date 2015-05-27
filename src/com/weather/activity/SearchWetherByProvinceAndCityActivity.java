package com.weather.activity;

import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.weather.util.ActivityUtil;
import com.weather.util.Constant;

public class SearchWetherByProvinceAndCityActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = SearchWetherByProvinceAndCityActivity.this;
	/**
	 * 省份下拉列表框控件
	 */
	private Spinner provinceListSpinner;
	/**
	 * 城市下拉列表框控件
	 */
	private Spinner cityListSpinner;
	/**
	 * 更新时间文本框
	 */
	private TextView textViewUpdateTime;
	/**
	 * 天气文本框
	 */
	private TextView textViewWeather;
	/**
	 * 温度文本框
	 */
	private TextView textViewTemperature;
	/**
	 * 风力风向文本框
	 */
	private TextView textViewWindPowerAndDirection;
	/**
	 * 湿度文本框
	 */
	private TextView textViewOtherInfo;
	/**
	 * 更新时间文本
	 */
	private String strUpdateTime;
	/**
	 * 天气文本
	 */
	private String strWeather;
	/**
	 * 温度文本
	 */
	private String strTemperature;
	/**
	 * 风力风向文本
	 */
	private String strWindPowerAndDirection;
	/**
	 * 其他信息
	 */
	private String strOtherInfo;
	/**
	 * 省份名列表
	 */
	private List<String> provinceList;
	/**
	 * 城市名列表
	 */ 
	private List<String> cityList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchprovinceandcity);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("provinceList");
		provinceList = bundle.getStringArrayList("provinceList");
		//provinceList.add(0, "请选择省份");
		/**
		 * 初始化控件
		 */
		initView();
		/**
		 * 向Spinner中设置数据
		 */
		ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(currentContext,android.R.layout.simple_spinner_dropdown_item, provinceList);
        provinceListSpinner.setAdapter(provinceAdapter);
	}
	/**
	 * 下拉列表点击事件处理
	 */
	private OnItemSelectedListener onItemSelectedListener = new Spinner.OnItemSelectedListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void onItemSelected(AdapterView<?> arg0, View view, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			String[] province = new String[]{provinceList.get(pos)};
			SearchCityByProvinceTask searchCityByProvinceTask = new SearchCityByProvinceTask(Constant.GET_CITYS_METHOD_NAME,Constant.GET_CITYS_SOAP_ACTION,province,Constant.GET_CITYS_RESULT_NAME);
			searchCityByProvinceTask.execute();
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
	
	private OnItemSelectedListener onItemSelectedListenerSearchWeatherByCity = new Spinner.OnItemSelectedListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void onItemSelected(AdapterView<?> arg0, View view, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			String[] city = new String[]{cityList.get(pos)};
			SearchWeatherByCity searchWeatherByCity = new SearchWeatherByCity(Constant.GET_CITY_WEATHER_METHOD_NAME,Constant.GET_CITY_WEATHER_SOAP_ACTION,city,Constant.GET_CITY_WEATHER_RESULT_NAME);
			searchWeatherByCity.execute();
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
	/**
	 * 初始化控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		provinceListSpinner = (Spinner) this.findViewById(R.id.searchProProvinceSpinner);
		cityListSpinner = (Spinner) this.findViewById(R.id.searchProCitySpinner);
		provinceListSpinner.setOnItemSelectedListener(onItemSelectedListener);
		cityListSpinner.setOnItemSelectedListener(onItemSelectedListenerSearchWeatherByCity);
		
		textViewUpdateTime = (TextView) this.findViewById(R.id.searchProTextUpdateTime);
		textViewWeather = (TextView) this.findViewById(R.id.searchProTextWeather);
		textViewTemperature = (TextView) this.findViewById(R.id.searchProTextTemperature);
		textViewWindPowerAndDirection = (TextView) this.findViewById(R.id.searchProTextWindPowerAndDirection);
		textViewOtherInfo = (TextView) this.findViewById(R.id.searchProTextOtherInfo);
	}
	
	/**
	 * 根据省份名获取城市名
	 * @author peng.wang
	 */
	@SuppressWarnings("unchecked")
   private class SearchCityByProvinceTask extends AsyncTask{
		private ProgressDialog pdialog;
		
		private String methodName;
		private String soapAction;
		private String[] param;
		private String getPropertyName;
		public SearchCityByProvinceTask(String methodName,String soapAction,String[] param,String getPropertyName){
			this.methodName = methodName;
			this.soapAction = soapAction;
			this.param = param;
			this.getPropertyName = getPropertyName;
			
			pdialog = new ProgressDialog(currentContext);
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setTitle("获取城市列表中...");
			pdialog.setIcon(R.drawable.icon);
			pdialog.show();
		}
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			SoapObject detail = null;
			try{
				SoapObject rpc = new SoapObject(Constant.NAMESPACE, methodName);
				if(param != null && param.length>0){
					for(String par : param){
						rpc.addProperty("byProvinceName", par);
					}
				}
				HttpTransportSE ht = new HttpTransportSE(Constant.URL);
				ht.debug = true;
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.bodyOut = rpc;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(rpc);
				ht.call(soapAction, envelope);
				SoapObject result = (SoapObject) envelope.bodyIn;
				detail = (SoapObject) result.getProperty(getPropertyName);
			}catch(Exception e){
				ActivityUtil.showMessageByToast(currentContext, "获取数据出错:"+e.getMessage());
			}
			return detail;
		}
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String resultStr = result.toString();
			/**
			 * 获取省份中文列表
			 */
			cityList = Constant.GetChineseWord(resultStr);
			//cityList.add(0, "请选择城市");
			pdialog.cancel();
			/**
			 * 设置城市下拉列表框中数据
			 */
			ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(currentContext,android.R.layout.simple_spinner_dropdown_item, cityList);
	        cityListSpinner.setAdapter(cityAdapter);
		}
	}
	/**
	 * 根据城市名查询天气
	 * @author peng.wang
	 */
	@SuppressWarnings("unchecked")
	private class SearchWeatherByCity extends AsyncTask{
		private ProgressDialog pdialog;
		private String methodName;
		private String soapAction;
		private String[] param;
		private String getPropertyName;
		public SearchWeatherByCity(String methodName,String soapAction,String[] param,String getPropertyName){
			this.methodName = methodName;
			this.soapAction = soapAction;
			this.param = param;
			this.getPropertyName = getPropertyName;
			
			pdialog = new ProgressDialog(currentContext);
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setTitle("更新天气中...");
			pdialog.setIcon(R.drawable.icon);
			pdialog.show();
		}
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			SoapObject detail = null;
			try{
				SoapObject rpc = new SoapObject(Constant.NAMESPACE, methodName);
				if(param != null && param.length>0){
					for(String par : param){
						rpc.addProperty("theCityName", par);
					}
				}
				HttpTransportSE ht = new HttpTransportSE(Constant.URL);
				ht.debug = true;
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.bodyOut = rpc;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(rpc);
				ht.call(soapAction, envelope);
				SoapObject result = (SoapObject) envelope.bodyIn;
				detail = (SoapObject) result.getProperty(getPropertyName);
			}catch(Exception e){
				ActivityUtil.showMessageByToast(currentContext, "更新过程出错:"+e.getMessage());
			}
			return detail;
		}
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			SoapObject detail = (SoapObject) result;
			/**
			 * 解析数据
			 */
			String date = detail.getProperty(6).toString();
			strUpdateTime = detail.getProperty(4).toString();
			strWeather = date.split(" ")[1];
			strTemperature = detail.getProperty(5).toString();
			strWindPowerAndDirection = detail.getProperty(7).toString();
			strOtherInfo = detail.getProperty(10).toString();
			/**
			 * 设置控件数据
			 */
			textViewUpdateTime.setText(strUpdateTime);
			textViewWeather.setText(strWeather);
			textViewTemperature.setText(strTemperature);
			textViewWindPowerAndDirection.setText(strWindPowerAndDirection);
			textViewOtherInfo.setText(strOtherInfo);
			
			pdialog.cancel();
		}
	}
}

package com.weather.activity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weather.util.ActivityUtil;
import com.weather.util.Constant;

public class SearchWeatherByCityActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = SearchWeatherByCityActivity.this;
	/**
	 * 城市名输入框
	 */
	private EditText editTextCityName;
	/**
	 * 刷新按钮
	 */
	private Button searchButton;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/**
		 * 去除标题栏
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchcity);
		/**
		 * 初始化控件
		 */
		 initView();
	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		editTextCityName = (EditText) this.findViewById(R.id.searchCityTextCityName);
		searchButton = (Button) this.findViewById(R.id.searchCityButtonSearch);
		textViewUpdateTime = (TextView) this.findViewById(R.id.searchCityTextUpdateTime);
		textViewWeather = (TextView) this.findViewById(R.id.searchCityTextWeather);
		textViewTemperature = (TextView) this.findViewById(R.id.searchCityTextTemperature);
		textViewWindPowerAndDirection = (TextView) this.findViewById(R.id.searchCityTextWindPowerAndDirection);
		textViewOtherInfo = (TextView) this.findViewById(R.id.searchCityTextOtherInfo);
		searchButton.setOnClickListener(onClickListener);
	}
	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			String cityName = editTextCityName.getText().toString().trim();
			if(cityName == null || "".equals(cityName)){
				ActivityUtil.showMessageByToast(currentContext, "请输入城市名后再查询!");
			}else{
				SearchWeatherTask searchWeatherTask = new SearchWeatherTask(Constant.GET_CITY_WEATHER_METHOD_NAME,Constant.GET_CITY_WEATHER_SOAP_ACTION,new String[]{cityName},Constant.GET_CITY_WEATHER_RESULT_NAME);
				searchWeatherTask.execute();
			}
		}
	};
	/**
	 * 获取天气信息异步类
	 *
	 */
	@SuppressWarnings("unchecked")
	public class SearchWeatherTask extends AsyncTask{
		private ProgressDialog pdialog;
		
		private String methodName;
		private String soapAction;
		private String[] param;
		private String getPropertyName;
		public SearchWeatherTask(String methodName,String soapAction,String[] param,String getPropertyName){
			this.methodName = methodName;
			this.soapAction = soapAction;
			this.param = param;
			this.getPropertyName = getPropertyName;
			
			pdialog = new ProgressDialog(currentContext);
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setTitle("更新中...");
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
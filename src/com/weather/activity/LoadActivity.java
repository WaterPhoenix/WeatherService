package com.weather.activity;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

import com.weather.util.ActivityUtil;
import com.weather.util.Constant;

public class LoadActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = LoadActivity.this;
	/**
	 * 省份名称List
	 */
	private List<String> provinceList;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/**
		 * 去除标题栏
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		if(!isNetworkConnected(currentContext)){
			ActivityUtil.showMessageByToast(currentContext, "无网络连接!");
			return;
		}
		SearchWeatherTask searchWeatherTask = new SearchWeatherTask(Constant.GET_PROVINCES_METHOD_NAME,Constant.GET_PROVINCES_SOAP_ACTION,null,Constant.GET_PROVINCES_RESULT_NAME);
		searchWeatherTask.execute();
	}
	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	         if (mNetworkInfo != null) {  
	             return mNetworkInfo.isAvailable();  
	         }  
	     }  
	     return false;  
	 }
	/**
	 * 判断wifi网络是否可用
	 * @param context
	 * @return
	 */
	public boolean isWifiConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                 .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	         if (mWiFiNetworkInfo != null) {  
	             return mWiFiNetworkInfo.isAvailable();  
	         }  
	     }  
	     return false;  
	 }
	/**
	 * 判断mobile网络是否可用
	 * @param context
	 * @return
	 */
	public boolean isMobileConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                 .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	         if (mMobileNetworkInfo != null) {  
	             return mMobileNetworkInfo.isAvailable();  
	         }  
	     }  
	     return false;  
	 }
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
			pdialog.setTitle("获取省份列表中...");
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
			provinceList = Constant.GetChineseWord(resultStr);
			pdialog.cancel();
			/**
			 * 跳转到查询界面
			 */
			//Intent intent = new Intent(currentContext,SearchWetherByProvinceAndCityActivity.class);
			Intent intent = new Intent(currentContext,TabView.class);
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("provinceList", (ArrayList<String>) provinceList);
			intent.putExtra("provinceList", bundle);
			startActivity(intent);
			LoadActivity.this.finish();
		}
	}
	
}

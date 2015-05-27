package com.weather.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class TabView extends TabActivity {
    /** Called when the activity is first created. */
	/**
	 * tab�ؼ�
	 */
	private TabHost tabhost;
	/**
	 * ��ת����������ĳ�������ѯ����intent
	 */
	private Intent gotoSearchWeatherByCityIntent;
	/**
	 * ��ת������ʡ�ݳ��в�ѯ����intent
	 */
	private Intent gotoWetherByProvinceAndCityIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tabpage);
        Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("provinceList");
		
        tabhost = this.getTabHost();
        /* ȥ����ǩ�·��İ��� */
        tabhost.setPadding(tabhost.getPaddingLeft(),
        		tabhost.getPaddingTop(), tabhost.getPaddingRight(),
        		tabhost.getPaddingBottom() - 5);
        
        gotoSearchWeatherByCityIntent = new Intent(this,SearchWeatherByCityActivity.class);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("�����в�ѯ",null).setContent(gotoSearchWeatherByCityIntent));
       
        gotoWetherByProvinceAndCityIntent = new Intent(this,SearchWetherByProvinceAndCityActivity.class);
		gotoWetherByProvinceAndCityIntent.putExtra("provinceList", bundle);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("��ʡ�в�ѯ", null).setContent(gotoWetherByProvinceAndCityIntent));
    }
}
package com.weather.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class TabView extends TabActivity {
    /** Called when the activity is first created. */
	/**
	 * tab控件
	 */
	private TabHost tabhost;
	/**
	 * 跳转到根据输入的城市名查询天气intent
	 */
	private Intent gotoSearchWeatherByCityIntent;
	/**
	 * 跳转到根据省份城市查询天气intent
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
        /* 去除标签下方的白线 */
        tabhost.setPadding(tabhost.getPaddingLeft(),
        		tabhost.getPaddingTop(), tabhost.getPaddingRight(),
        		tabhost.getPaddingBottom() - 5);
        
        gotoSearchWeatherByCityIntent = new Intent(this,SearchWeatherByCityActivity.class);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("按城市查询",null).setContent(gotoSearchWeatherByCityIntent));
       
        gotoWetherByProvinceAndCityIntent = new Intent(this,SearchWetherByProvinceAndCityActivity.class);
		gotoWetherByProvinceAndCityIntent.putExtra("provinceList", bundle);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("按省市查询", null).setContent(gotoWetherByProvinceAndCityIntent));
    }
}
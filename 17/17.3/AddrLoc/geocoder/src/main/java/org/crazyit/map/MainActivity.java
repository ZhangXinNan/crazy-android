package org.crazyit.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity
{
	private GeocodeSearch search;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取界面中的可视化组件
		Button parseBn = findViewById(R.id.parse);
		Button reverseBn = findViewById(R.id.reverse);
		final EditText etLng = findViewById(R.id.lng);
		final EditText etLat = findViewById(R.id.lat);
		final EditText etAddress = findViewById(R.id.address);
		final TextView etResult = findViewById(R.id.result);
		View.OnClickListener listener = source -> {
			switch (source.getId())
			{
				// 单击了“解析”按钮
				case R.id.parse:
					String address = etAddress.getText().toString().trim();
					if (address.equals(""))
					{
						Toast.makeText(this, "请输入有效的地址",
								Toast.LENGTH_LONG).show();
					}
					else
					{
						GeocodeQuery query = new GeocodeQuery(address, "广州");
						// 根据地理名称执行异步解析
						search.getFromLocationNameAsyn(query);  // ②
					}
					break;
				// 单击了“反向解析”按钮
				case R.id.reverse:
					String lng = etLng.getText().toString().trim();
					String lat = etLat.getText().toString().trim();
					if (lng.equals("") || lat.equals(""))
					{
						Toast.makeText(this, "请输入有效的经度、纬度!",
								Toast.LENGTH_LONG).show();
					}
					else
					{
						// 根据经纬度执行异步查询
						search.getFromLocationAsyn(new RegeocodeQuery(
								new LatLonPoint(Double.parseDouble(lat), Double.parseDouble(lng)), 20f // 区域半径
								, GeocodeSearch.GPS)); // ③
					}
					break;
			}
		};
		parseBn.setOnClickListener(listener);
		reverseBn.setOnClickListener(listener);
		// 创建GeocodeSearch对象
		search = new GeocodeSearch(this);
		// 设置解析监听器
		search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener()  // ①
		{
			@Override
			public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i)
			{
				RegeocodeAddress addr = regeocodeResult.getRegeocodeAddress();
				etResult.setText("经度:" + etLng.getText() + "、纬度:" +
						etLat.getText() + "的地址为：\n" + addr.getFormatAddress());
			}

			@Override
			public void onGeocodeSearched(GeocodeResult geocodeResult, int i)
			{
				GeocodeAddress addr = geocodeResult.getGeocodeAddressList().get(0);
				LatLonPoint latlng = addr.getLatLonPoint();
				etResult.setText(etAddress.getText().toString() + "的经度是:" +
						latlng.getLongitude() + "、纬度是:" + latlng.getLatitude());
			}
		});
	}
}

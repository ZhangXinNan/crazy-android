package org.crazyit.map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
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
		implements GeocodeSearch.OnGeocodeSearchListener
{
	private MapView mapView;
	private AMap aMap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = findViewById(R.id.map);
		// 必须回调MapView的onCreate()方法
		mapView.onCreate(savedInstanceState);
		init();
		Button bn = findViewById(R.id.loc);
		TextView addrTv = findViewById(R.id.address);
		bn.setOnClickListener(view ->
		{
			String addr = addrTv.getText().toString();
			if (addr.equals(""))
			{
				Toast.makeText(MainActivity.this, "请输入有效的地址",
						Toast.LENGTH_LONG).show();
			}
			else
			{
				GeocodeSearch search = new GeocodeSearch(MainActivity.this);
				search.setOnGeocodeSearchListener(MainActivity.this);
				GeocodeQuery query = new GeocodeQuery(addr, "广州");
				// 根据地址执行异步地址解析
				search.getFromLocationNameAsyn(query);  // ①
			}
		});
	}

	// 初始化AMap对象
	private void init()
	{
		if (aMap == null)
		{
			aMap = mapView.getMap();
			// 创建一个设置放大级别的CameraUpdate
			CameraUpdate cu = CameraUpdateFactory.zoomTo(16f);
			// 设置地图的默认放大级别
			aMap.moveCamera(cu);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// 必须回调MapView的onResume()方法
		mapView.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// 必须回调MapView的onPause()方法
		mapView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		// 必须回调MapView的onSaveInstanceState()方法
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 必须回调MapView的onDestroy()方法
		mapView.onDestroy();
	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i)
	{
	}

	@Override
	public void onGeocodeSearched(GeocodeResult geocodeResult, int i)
	{
		// 获取解析得到的第一个地址
		GeocodeAddress geo = geocodeResult.getGeocodeAddressList().get(0);
		// 获取解析得到的经纬度
		LatLonPoint pos = geo.getLatLonPoint();
		LatLng targetPos = new LatLng(pos.getLatitude(), pos.getLongitude());
		// 创建一个设置经纬度的CameraUpdate
		CameraUpdate cu = CameraUpdateFactory.changeLatLng(targetPos);
		// 更新地图的显示区域
		aMap.moveCamera(cu);
		// 创建一个GroundOverlayOptions（用于向地图上添加图片)
		GroundOverlayOptions options = new GroundOverlayOptions()
				// 设置GroundOverlayOptions包装的图片
				.image(BitmapDescriptorFactory.fromResource(
						R.drawable.fklogo)).position(targetPos, 64f);
		// 添加图片
		aMap.addGroundOverlay(options);
		// 创建一个CircleOptions（用于向地图上添加圆形）
		CircleOptions cOptions = new CircleOptions()
				.center(targetPos)  // 设置圆心
				.fillColor(-0x7f000100)  // 设置圆形的填充颜色
				.radius(80.0)  // 设置圆形的半径
				.strokeWidth(1f)  // 设置圆形的线条宽度
				.strokeColor(-0x1000000);  // 设置圆形的线条颜色
		aMap.addCircle(cOptions);
	}
}

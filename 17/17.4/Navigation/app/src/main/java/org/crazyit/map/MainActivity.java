package org.crazyit.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

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
		implements GeocodeSearch.OnGeocodeSearchListener, RouteSearch.OnRouteSearchListener
{
	private MapView mapView;
	private AMap aMap;
	private LocationManager locMgr;
	private GeocodeSearch search;
	private RouteSearch routeSearch;
	private EditText targetAddressEt;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		targetAddressEt = findViewById(R.id.address);
		mapView = findViewById(R.id.map);
		// 必须回调MapView的onCreate()方法
		mapView.onCreate(savedInstanceState);
		Button navBn = findViewById(R.id.nav);
		navBn.setOnClickListener(view ->
		{
			String address = targetAddressEt.getText().toString().trim();
			if (address.equals(""))
			{
				Toast.makeText(this, "请输入有效的地址",
						Toast.LENGTH_LONG).show();
			}
			else
			{
				GeocodeQuery query = new GeocodeQuery(address, "广州");
				// 根据地址执行异步查询
				search.getFromLocationNameAsyn(query);
			}
		});
		init();
		// 创建GeocodeSearch对象，并为之绑定监听器
		search = new GeocodeSearch(this);
		search.setOnGeocodeSearchListener(this);
		// 创建RouteSearch，并为之绑定监听器
		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0x123);
		locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	@SuppressLint("MissingPermission")
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults)

	{
		if (requestCode == 0x123 && grantResults.length == 1 &&
				grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			// 通过监听器监听GPS提供的定位信息的改变
			locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					300, 8f, new LocationListener()
					{
						@Override
						public void onLocationChanged(Location location)
						{
							// 使用GPS提供的定位信息来更新位置
							updatePosition(location);
						}

						@Override
						public void onStatusChanged(String provider, int status, Bundle extras)
						{

						}

						@Override
						public void onProviderEnabled(String provider)
						{
							// 使用GPS提供的定位信息来更新位置
							updatePosition(locMgr.getLastKnownLocation(provider));
						}

						@Override
						public void onProviderDisabled(String provider)
						{

						}
					});
		}
	}

	// 初始化AMap对象
	private void init()
	{
		if (aMap == null
				)
		{
			aMap = mapView.getMap();
			// 创建一个设置放大级别的CameraUpdate
			CameraUpdate cu = CameraUpdateFactory.zoomTo(16f);
			// 设置地图的默认放大级别
			aMap.moveCamera(cu);
		}
	}

	private void updatePosition(Location location)  // ①
	{
		LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
		// 创建一个设置经纬度的CameraUpdate
		CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
		// 更新地图的显示区域
		aMap.moveCamera(cu);
		// 清除所有Marker等覆盖物
		aMap.clear();
		MarkerOptions markerOptions = new MarkerOptions().position(pos)
				// 设置使用自定义图标
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
				.draggable(true);
		// 添加Marker
		aMap.addMarker(markerOptions);
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

	@SuppressLint("MissingPermission")
	@Override
	public void onGeocodeSearched(GeocodeResult geocodeResult, int i)  // ②
	{
		GeocodeAddress addr = geocodeResult.getGeocodeAddressList().get(0);
		// 获取目前的经纬度
		LatLonPoint latlng = addr.getLatLonPoint();
		// 获取用户当前的位置
		Location loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// 创建路线规划的起始点
		RouteSearch.FromAndTo ft = new RouteSearch.FromAndTo(new LatLonPoint(
				loc.getLatitude(), loc.getLongitude()), latlng);
		// 创建自驾车的查询条件
		RouteSearch.DriveRouteQuery driveRouteQuery =
				new RouteSearch.DriveRouteQuery(ft, // 定义道路规划的起始点
						RouteSearch.DRIVING_SINGLE_DEFAULT,
						null, // 该参数指定必须经过的多个点
						null, // 该参数指定必须避开的多个区域
						null); // 该参数指定必须避开的道路
		routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
	}


	@Override
	public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i)  // ③
	{
		// 获取系统规划第一条路线（实际应用中可提供多条路线供用户选择）
		DrivePath drivePath = driveRouteResult.getPaths().get(0);
		// 获取该规划路线所包含的多条路段
		List<DriveStep> steps = drivePath.getSteps();
		for (DriveStep step : steps)
		{
			// 获取组成该路段的多个点
			List<LatLonPoint> points = step.getPolyline();
			List<LatLng> latLngs = new ArrayList<>();
			for (LatLonPoint point : points)
			{
				latLngs.add(new LatLng(point.getLatitude()
						, point.getLongitude()));
			}
			// 创建一个PolylineOptions（用于向地图添加多线段）
			PolylineOptions ployOptions = new PolylineOptions()
					// 添加多个点
					.addAll(latLngs)
					.color(-0x10000)
					.width(8f);
			aMap.addPolyline(ployOptions);
		}
	}

	@Override
	public void onBusRouteSearched(BusRouteResult busRouteResult, int i)
	{

	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i)
	{

	}

	@Override
	public void onRideRouteSearched(RideRouteResult rideRouteResult, int i)
	{

	}
}

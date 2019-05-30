package org.crazyit.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

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
	public static final int CAPTURE_CODE = 0x123;
	private MediaProjectionManager projectionManager;
	private int screenDensity;
	private int displayWidth = 720;
	private int displayHeight = 1080;
	private boolean screenSharing;
	private MediaProjection mediaProjection;
	private VirtualDisplay virtualDisplay;
	private Surface surface;
	private SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取屏幕分辨率
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenDensity = metrics.densityDpi;
		// 获取应用界面上的SurfaceView组件
		surfaceView = findViewById(R.id.surface);
		surface = surfaceView.getHolder().getSurface();
		// 控制界面上的SurfaceView组件的宽度和高度
		ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
		lp.height = displayHeight;
		lp.width = displayWidth;
		surfaceView.setLayoutParams(lp);
		// 获取MediaProjectionManager管理器
		projectionManager = (MediaProjectionManager) getSystemService(
				Context.MEDIA_PROJECTION_SERVICE); // ①
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (mediaProjection != null) {
			mediaProjection.stop();
			mediaProjection = null;
		}
	}

	// 当用户单击开关按钮时激发该方法
	public void onToggleScreenShare(View view)
	{
		if (((ToggleButton)view).isChecked())
		{
			shareScreen();
		}
		else
		{
			stopScreenSharing();
		}
	}

	private void shareScreen()
	{
		screenSharing = true;
		if (surface == null) {
			return;
		}
		if (mediaProjection == null) {
			Intent intent = projectionManager.createScreenCaptureIntent();  // ②
			startActivityForResult(intent, CAPTURE_CODE);  // ③
		}
	}

	private void stopScreenSharing()
	{
		screenSharing = false;
		if (virtualDisplay == null) {
			return;
		}
		virtualDisplay.release();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == CAPTURE_CODE) {
			// 如果resultCode不等于RESULT_OK，表明用户拒绝了屏幕捕捉
			if (resultCode != Activity.RESULT_OK) {
				Toast.makeText(this, "用户取消了屏幕捕捉",
						Toast.LENGTH_SHORT).show();
				return;
			}
			mediaProjection = projectionManager.getMediaProjection(resultCode, data);  // ④
			virtualDisplay = mediaProjection.createVirtualDisplay("屏幕捕捉",
					displayWidth, displayHeight, screenDensity,
					DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
					surface, null /* Callback */, null /*Handler*/);
		}
	}
}

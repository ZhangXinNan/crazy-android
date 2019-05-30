package org.crazyit.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LifecycleFragment extends Fragment
{
	public final static String TAG = "--CrazyIt--";
	@Override public void onAttach(Context context)
	{
		super.onAttach(context);
		// 输出日志
		Log.d(TAG, "-------onAttach------");
	}
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 输出日志
		Log.d(TAG, "-------onCreate------");
	}
	@Override public View onCreateView(LayoutInflater inflater,
							 ViewGroup container, Bundle data)
	{
		// 输出日志
		Log.d(TAG, "-------onCreateView------");
		TextView tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setText("测试Fragment");
		tv.setTextSize(40f);
		return tv;
	}
	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		// 输出日志
		Log.d(TAG, "-------onActivityCreated------");
	}
	@Override public void onStart()
	{
		super.onStart();
		// 输出日志
		Log.d(TAG, "-------onStart------");
	}
	@Override public void onResume()
	{
		super.onResume();
		// 输出日志
		Log.d(TAG, "-------onResume------");
	}
	@Override public void onPause()
	{
		super.onPause();
		// 输出日志
		Log.d(TAG, "-------onPause------");
	}
	@Override public void onStop()
	{
		super.onStop();
		// 输出日志
		Log.d(TAG, "-------onStop------");
	}
	@Override public void onDestroyView()
	{
		super.onDestroyView();
		// 输出日志
		Log.d(TAG, "-------onDestroyView------");
	}
	@Override public void onDestroy()
	{
		super.onDestroy();
		// 输出日志
		Log.d(TAG, "-------onDestroy------");
	}
	@Override public void onDetach()
	{
		super.onDetach();
		// 输出日志
		Log.d(TAG, "-------onDetach------");
	}
}

package org.crazyit.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater
			, ViewGroup container, Bundle data)
	{
		TextView tv = new TextView(getActivity());
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setText("第二个Fragment");
		tv.setTextSize(40);
		return tv;
	}
}

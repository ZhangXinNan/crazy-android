package org.crazyit.io;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class TouchZoomView extends TextView
{
	// 保存TextView当前的字体大小
	private float textSize;
	// 保存两个手指前一次的距离
	private float prevDist;
	public TouchZoomView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	public TouchZoomView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	public TouchZoomView(Context context)
	{
		super(context);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// 只处理触碰点大于等于2（必须是多点触碰）的情形
		if (event.getPointerCount() >= 2) {
			// 获取该TextView默认的字体大小
			if (textSize == 0) {
				textSize = this.getTextSize();
			}
			// 对于多点触碰事件，需要使用getActionMasked来获取触摸事件类型
			switch (event.getActionMasked()) {
				// 处理手指按下的事件
				case MotionEvent.ACTION_POINTER_DOWN:
					// 计算两个手指之间的距离
					prevDist = calSpace(event);
					break;
				// 处理手指移动的事件
				case MotionEvent.ACTION_MOVE:
					// 实时计算两个手指之间的距离
					float curVDist = calSpace(event);
					// 根据两个手指之间的距离计算缩放比
					zoom(curVDist / prevDist);
					// 为下一次移动的缩放做准备
					prevDist = curVDist;
					break;
			}
		}
		return true;
	}
	// 缩放字体
	private void zoom(float f)
	{
		textSize *= f;
		this.setTextSize(px2sp(getContext(), textSize));
	}
	// 将px值转换为sp值，保证文字大小不变
	public static int px2sp(Context context, float pxValue)
	{
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
	// 计算两个手指之间的距离
	private float calSpace(MotionEvent event)
	{
		// 获取两个点之间X坐标的差值
		float x = event.getX(0) - event.getX(1);
		// 获取两个点之间Y坐标的差值
		float y = event.getY(0) - event.getY(1);
		// 计算两点距离
		return (float)Math.sqrt(x * x + y * y);
	}
}
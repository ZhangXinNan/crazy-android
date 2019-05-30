package org.crazyit.res;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class AlphaImageView extends ImageView
{
	// 每隔多少毫秒透明度改变一次
	private final static int SPEED = 300;
	// 图片透明度每次改变的大小
	private int alphaDelta;
	// 记录图片当前的透明度
	private int curAlpha;
	private Timer timer;
	static class MyHandler extends Handler
	{
		private WeakReference<AlphaImageView> imageView;
		private MyHandler(WeakReference<AlphaImageView> imageView)
		{
			this.imageView = imageView;
		}
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				// 每次增加curAlpha的值
				imageView.get().curAlpha += imageView.get().alphaDelta;
				if (imageView.get().curAlpha > 255)
					imageView.get().curAlpha = 255;
				// 修改该ImageView的透明度
				imageView.get().setImageAlpha(imageView.get().curAlpha);
			}
		}
	}
	private Handler handler = new MyHandler(new WeakReference<>(this));
	public AlphaImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaImageView);
		// 获取duration参数
		int duration = typedArray.getInt(R.styleable.AlphaImageView_duration, 0);
		typedArray.recycle();
		// 计算图片透明度每次改变的大小
		alphaDelta = 255 * SPEED / duration;
		timer = new Timer();
		// 按固定间隔发送消息，通知系统改变图片的透明度
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				Message msg = new Message();
				msg.what = 0x123;
				if (curAlpha >= 255)
				{
					timer.cancel();
				} else
				{
					handler.sendMessage(msg);
				}
			}
		}, 0L, SPEED);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		this.setImageAlpha(curAlpha);
		super.onDraw(canvas);
	}
}

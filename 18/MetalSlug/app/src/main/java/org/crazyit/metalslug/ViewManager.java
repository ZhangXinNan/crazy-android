package org.crazyit.metalslug;

import java.io.InputStream;
import org.crazyit.game.Graphics;
import org.crazyit.metalslug.comp.MonsterManager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.SparseIntArray;

/**
 * Description: 管理图片加载和图片绘制的工具类<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class ViewManager
{
	// 定义一个SoundPool
	public static SoundPool soundPool;
	public static SparseIntArray soundMap = new SparseIntArray();
	// 地图图片
	public static Bitmap map = null;
	// 保存角色站立时脚部动画帧的图片数组
	public static Bitmap[] legStandImage = null;
	// 保存角色站立时头部动画帧的图片数组
	public static Bitmap[] headStandImage = null;
	// 保存角色跑动时腿部动画帧的图片数组
	public static Bitmap[] legRunImage = null;
	// 保存角色跑动时头部动画帧的图片数组
	public static Bitmap[] headRunImage = null;
	// 保存角色跳动时腿部动画帧的图片数组
	public static Bitmap[] legJumpImage = null;
	// 保存角色跳动时头部动画帧的图片数组
	public static Bitmap[] headJumpImage = null;
	// 保存角色射击时头部动画帧的图片数组
	public static Bitmap[] headShootImage = null;
	// 加载所有子弹的图片
	public static Bitmap[] bulletImage = null;
	// 绘制角色的图片
	public static Bitmap head = null;
	// 保存第一种怪物（炸弹）未爆炸时动画帧的图片
	public static Bitmap[] bombImage = null;
	// 保存第一种怪物（炸弹）爆炸时动画帧的图片
	public static Bitmap[] bomb2Image = null;
	// 保存第二种怪物（飞机）的动画帧的图片
	public static Bitmap[] flyImage = null;
	// 保存第二种怪物（飞机）爆炸的动画帧的图片
	public static Bitmap[] flyDieImage = null;
	// 保存第三种怪物（人）的动画帧的图片
	public static Bitmap[] manImgae = null;
	// 保存第三种怪物（人）的死亡时动画帧的图片
	public static Bitmap[] manDieImage = null;
	// 定义游戏对图片的缩放比例
	public static float scale = 1f;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;

	// 获取屏幕初始宽度、高度的方法
	public static void initScreen(int width, int height)
	{
		SCREEN_WIDTH = (short) width;
		SCREEN_HEIGHT = (short) height;
	}

	// 清除屏幕的方法
	public static void clearScreen(Canvas c, int color)
	{
		color = 0xff000000 | color;
		c.drawColor(color);
	}

	// 清除屏幕的方法
	public static void clearScreen(Canvas c)
	{
		c.drawColor(Color.BLACK);
	}
	// 加载所有游戏图片、声音的方法
	public static void loadResource()
	{
		AudioAttributes attr = new AudioAttributes.Builder()
				// 设置音效使用场景
				.setUsage(AudioAttributes.USAGE_GAME)
				// 设置音效的类型
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.build();
		soundPool = new SoundPool.Builder()
				// 设置音效池的属性
				.setAudioAttributes(attr)
				.setMaxStreams(10) // 设置最多可容纳10个音频流
				.build();
		// load方法加载指定音频文件，并返回所加载的音频ID
		// 此处使用HashMap来管理这些音频流
		soundMap.put(1, soundPool.load(MainActivity.mainActivity, R.raw.shot, 1));
		soundMap.put(2, soundPool.load(MainActivity.mainActivity, R.raw.bomb, 1));
		soundMap.put(3, soundPool.load(MainActivity.mainActivity, R.raw.oh, 1));

		Bitmap temp = createBitmapByID(MainActivity.res, R.drawable.map);
		if (temp != null && !temp.isRecycled())
		{
			int height = temp.getHeight();
			if (height != SCREEN_HEIGHT && SCREEN_HEIGHT != 0)
			{
				scale = (float) SCREEN_HEIGHT / (float) height;
				map = Graphics.scale(temp, temp.getWidth() * scale, height * scale);
				temp.recycle();
			}
			else
			{
				map = temp;
			}
		}
		// 加载角色站立时腿部动画帧的图片
		legStandImage = new Bitmap[1];
		legStandImage[0] = createBitmapByID(MainActivity.res, R.drawable.leg_stand, scale);
		// 加载角色站立时头部动画帧的图片
		headStandImage = new Bitmap[3];
		headStandImage[0] = createBitmapByID(MainActivity.res, R.drawable.head_stand_1, scale);
		headStandImage[1] = createBitmapByID(MainActivity.res, R.drawable.head_stand_2, scale);
		headStandImage[2] = createBitmapByID(MainActivity.res, R.drawable.head_stand_3, scale);
		// 加载角色跑动时腿部动画帧的图片
		legRunImage = new Bitmap[3];
		legRunImage[0] = createBitmapByID(MainActivity.res, R.drawable.leg_run_1, scale);
		legRunImage[1] = createBitmapByID(MainActivity.res, R.drawable.leg_run_2, scale);
		legRunImage[2] = createBitmapByID(MainActivity.res, R.drawable.leg_run_3, scale);
		// 加载角色跑动时头部动画帧的图片
		headRunImage = new Bitmap[3];
		headRunImage[0] = createBitmapByID(MainActivity.res, R.drawable.head_run_1, scale);
		headRunImage[1] = createBitmapByID(MainActivity.res, R.drawable.head_run_2, scale);
		headRunImage[2] = createBitmapByID(MainActivity.res, R.drawable.head_run_3, scale);
		// 加载角色跳跃时腿部动画帧的图片
		legJumpImage = new Bitmap[5];
		legJumpImage[0] = createBitmapByID(MainActivity.res, R.drawable.leg_jum_1, scale);
		legJumpImage[1] = createBitmapByID(MainActivity.res, R.drawable.leg_jum_2, scale);
		legJumpImage[2] = createBitmapByID(MainActivity.res, R.drawable.leg_jum_3, scale);
		legJumpImage[3] = createBitmapByID(MainActivity.res, R.drawable.leg_jum_4, scale);
		legJumpImage[4] = createBitmapByID(MainActivity.res, R.drawable.leg_jum_5, scale);
		// 加载角色跳跃时头部动画帧的图片
		headJumpImage = new Bitmap[5];
		headJumpImage[0] = createBitmapByID(MainActivity.res, R.drawable.head_jump_1, scale);
		headJumpImage[1] = createBitmapByID(MainActivity.res, R.drawable.head_jump_2, scale);
		headJumpImage[2] = createBitmapByID(MainActivity.res, R.drawable.head_jump_3, scale);
		headJumpImage[3] = createBitmapByID(MainActivity.res, R.drawable.head_jump_4, scale);
		headJumpImage[4] = createBitmapByID(MainActivity.res, R.drawable.head_jump_5, scale);
		// 加载角色射击时头部动画帧的图片
		headShootImage = new Bitmap[6];
		headShootImage[0] = createBitmapByID(MainActivity.res, R.drawable.head_shoot_1, scale);
		headShootImage[1] = createBitmapByID(MainActivity.res, R.drawable.head_shoot_2, scale);
		headShootImage[2] = createBitmapByID(MainActivity.res, R.drawable.head_shoot_3, scale);
		headShootImage[3] = createBitmapByID(MainActivity.res, R.drawable.head_shoot_4, scale);
		headShootImage[4] = createBitmapByID(MainActivity.res, R.drawable.head_shoot_5, scale);
		headShootImage[5] = createBitmapByID(MainActivity.res, R.drawable.head_shoot_6, scale);
		// 加载子弹的图片
		bulletImage = new Bitmap[4];
		bulletImage[0] = createBitmapByID(MainActivity.res, R.drawable.bullet_1, scale);
		bulletImage[1] = createBitmapByID(MainActivity.res, R.drawable.bullet_2, scale);
		bulletImage[2] = createBitmapByID(MainActivity.res, R.drawable.bullet_3, scale);
		bulletImage[3] = createBitmapByID(MainActivity.res, R.drawable.bullet_4, scale);
		head = createBitmapByID(MainActivity.res, R.drawable.head, scale);
		// 加载第一种怪物（炸弹）未爆炸时动画帧的图片
		bombImage = new Bitmap[2];
		bombImage[0] = createBitmapByID(MainActivity.res, R.drawable.bomb_1, scale);
		bombImage[1] = createBitmapByID(MainActivity.res, R.drawable.bomb_2, scale);
		// 加载第一种怪物（炸弹）爆炸时的图片
		bomb2Image = new Bitmap[13];
		bomb2Image[0] = createBitmapByID(MainActivity.res, R.drawable.bomb2_1, scale);
		bomb2Image[1] = createBitmapByID(MainActivity.res, R.drawable.bomb2_2, scale);
		bomb2Image[2] = createBitmapByID(MainActivity.res, R.drawable.bomb2_3, scale);
		bomb2Image[3] = createBitmapByID(MainActivity.res, R.drawable.bomb2_4, scale);
		bomb2Image[4] = createBitmapByID(MainActivity.res, R.drawable.bomb2_5, scale);
		bomb2Image[5] = createBitmapByID(MainActivity.res, R.drawable.bomb2_6, scale);
		bomb2Image[6] = createBitmapByID(MainActivity.res, R.drawable.bomb2_7, scale);
		bomb2Image[7] = createBitmapByID(MainActivity.res, R.drawable.bomb2_8, scale);
		bomb2Image[8] = createBitmapByID(MainActivity.res, R.drawable.bomb2_9, scale);
		bomb2Image[9] = createBitmapByID(MainActivity.res, R.drawable.bomb2_10, scale);
		bomb2Image[10] = createBitmapByID(MainActivity.res, R.drawable.bomb2_11, scale);
		bomb2Image[11] = createBitmapByID(MainActivity.res, R.drawable.bomb2_12, scale);
		bomb2Image[12] = createBitmapByID(MainActivity.res, R.drawable.bomb2_13, scale);
		// 加载第二种怪物（飞机）的动画帧的图片
		flyImage = new Bitmap[6];
		flyImage[0] = createBitmapByID(MainActivity.res, R.drawable.fly_1, scale);
		flyImage[1] = createBitmapByID(MainActivity.res, R.drawable.fly_2, scale);
		flyImage[2] = createBitmapByID(MainActivity.res, R.drawable.fly_3, scale);
		flyImage[3] = createBitmapByID(MainActivity.res, R.drawable.fly_4, scale);
		flyImage[4] = createBitmapByID(MainActivity.res, R.drawable.fly_5, scale);
		flyImage[5] = createBitmapByID(MainActivity.res, R.drawable.fly_6, scale);
		// 加载第二种怪物（飞机）爆炸时的动画帧的图片
		flyDieImage = new Bitmap[13];
		flyDieImage[0] = createBitmapByID(MainActivity.res, R.drawable.fly_die_1, scale);
		flyDieImage[1] = createBitmapByID(MainActivity.res, R.drawable.fly_die_2, scale);
		flyDieImage[2] = createBitmapByID(MainActivity.res, R.drawable.fly_die_3, scale);
		flyDieImage[3] = createBitmapByID(MainActivity.res, R.drawable.fly_die_4, scale);
		flyDieImage[4] = createBitmapByID(MainActivity.res, R.drawable.fly_die_5, scale);
		flyDieImage[5] = createBitmapByID(MainActivity.res, R.drawable.fly_die_6, scale);
		flyDieImage[6] = createBitmapByID(MainActivity.res, R.drawable.fly_die_7, scale);
		flyDieImage[7] = createBitmapByID(MainActivity.res, R.drawable.fly_die_8, scale);
		flyDieImage[8] = createBitmapByID(MainActivity.res, R.drawable.fly_die_9, scale);
		flyDieImage[9] = createBitmapByID(MainActivity.res, R.drawable.fly_die_10, scale);
		// 加载第三种怪物（人）活着时的动画帧的图片
		manImgae = new Bitmap[3];
		manImgae[0] = createBitmapByID(MainActivity.res, R.drawable.man_1, scale);
		manImgae[1] = createBitmapByID(MainActivity.res, R.drawable.man_2, scale);
		manImgae[2] = createBitmapByID(MainActivity.res, R.drawable.man_3, scale);
		// 加载第三种怪物（人）死亡时的动画帧的图片
		manDieImage = new Bitmap[13];
		manDieImage[0] = createBitmapByID(MainActivity.res, R.drawable.man_die_1, scale);
		manDieImage[1] = createBitmapByID(MainActivity.res, R.drawable.man_die_2, scale);
		manDieImage[2] = createBitmapByID(MainActivity.res, R.drawable.man_die_3, scale);
		manDieImage[3] = createBitmapByID(MainActivity.res, R.drawable.man_die_4, scale);
		manDieImage[4] = createBitmapByID(MainActivity.res, R.drawable.man_die_5, scale);
	}
// 绘制游戏界面的方法，该方法先绘制游戏背景地图，再绘制游戏角色，最后绘制所有怪物
public static void drawGame(Canvas canvas)
{
	if (canvas == null)
	{
		return;
	}
	// 画地图
	if (map != null && !map.isRecycled())
	{
		int width = map.getWidth() + GameView.player.getShift();
		// 绘制map图片，也就是绘制地图
		Graphics.drawImage(canvas, map, 0, 0, -GameView.player.getShift()
			, 0 ,width, map.getHeight());
		int totalWidth = width;
		// 采用循环，保证地图前后可以拼接起来
		while (totalWidth < ViewManager.SCREEN_WIDTH)
		{
			int mapWidth = map.getWidth();
			int drawWidth = ViewManager.SCREEN_WIDTH - totalWidth;
			if (mapWidth < drawWidth)
			{
				drawWidth = mapWidth;
			}
			Graphics.drawImage(canvas, map, totalWidth, 0, 0, 0,
					drawWidth, map.getHeight());
			totalWidth += drawWidth;
		}
	}
	// 画角色
	GameView.player.draw(canvas);
	// 画怪物
	MonsterManager.drawMonster(canvas);
}

	// 工具方法：根据图片id来获取实际的位图
	private static Bitmap createBitmapByID(Resources res, int resID)
	{
		try
		{
			InputStream is = res.openRawResource(resID);
			return BitmapFactory.decodeStream(is, null, null);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	// 工具方法：根据图片id来获取实际的位图，并按scale进行缩放
	private static Bitmap createBitmapByID(Resources res, int resID, float scale)
	{
		try
		{
			InputStream is = res.openRawResource(resID);
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, null);
			if (bitmap == null || bitmap.isRecycled())
			{
				return null;
			}
			if (scale <= 0 || scale == 1f)
			{
				return bitmap;
			}
			int wdith = (int) (bitmap.getWidth() * scale);
			int height = (int) (bitmap.getHeight() * scale);
			Bitmap newBitmap = Graphics.scale(bitmap, wdith, height);
			if (!bitmap.isRecycled() && !bitmap.equals(newBitmap))
			{
				bitmap.recycle();
			}
			return newBitmap;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	// 工具方法：根据图片的文件名来获取实际的位图，
	private static Bitmap createBitmapByFile(String pathName)
	{
		try
		{
			InputStream fin = MainActivity.mainActivity.getAssets().open(pathName);
			return BitmapFactory.decodeStream(fin, null, null);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}

package org.crazyit.metalslug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.crazyit.metalslug.comp.MonsterManager;
import org.crazyit.metalslug.comp.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

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
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public static final Player player = new Player("孙悟空", Player.MAX_HP);
	// 保存当前Android应用的主Context
	private Context mainContext = null;
	// 画图所需要的Paint和Canvas对象
	private Paint paint = null;
	private Canvas canvas = null;
	// SurfaceHolder负责维护SurfaceView上绘制的内容
	private SurfaceHolder surfaceHolder;
	// 代表场景不改变的常量
	public static final int STAGE_NO_CHANGE = 0;
	// 代表初始化场景的常量
	public static final int STAGE_INIT = 1;
	// 代表登录场景的常量
	public static final int STAGE_LOGIN = 2;
	// 代表游戏场景的常量
	public static final int STAGE_GAME = 3;
	// 代表失败场景的常量
	public static final int STAGE_LOSE = 4;
	// 代表退出场景的常量
	public static final int STAGE_QUIT = 99;
	// 代表错误场景的常量
	public static final int STAGE_ERROR = 255;
	// 定义该游戏当前处于何种场景的变量
	private int gStage = 0;
	// 定义一个集合来保存该游戏已经加载到所有场景
	public static final List<Integer> stageList =
			Collections.synchronizedList(new ArrayList<Integer>());

	// 定义GameView的构造器
	public GameView(Context context, int firstStage)
	{
		super(context);
		mainContext = context;
		paint = new Paint();
		// 设置抗锯齿
		paint.setAntiAlias(true);
		// 设置该组件会保持屏幕常量，避免游戏过程中出现黑屏。
		setKeepScreenOn(true);
		// 设置焦点，相应事件处理
		setFocusable(true);
		// 获取SurfaceHolder
		surfaceHolder = getHolder();
		// 设置this为SurfaceHolder的回调，这要求该类实现SurfaceHolder.Callback接口
		surfaceHolder.addCallback(this);
		//初始化屏幕大小
		ViewManager.initScreen(MainActivity.windowWidth
				, MainActivity.windowHeight);
		gStage = firstStage;
	}

	public Context getMainContext()
	{
		return mainContext;
	}

	public void setMainContext(Context mainContext)
	{
		this.mainContext = mainContext;
	}

	public Paint getPaint()
	{
		return paint;
	}

	public void setPaint(Paint paint)
	{
		this.paint = paint;
	}

	public Canvas getCanvas()
	{
		return canvas;
	}

	public void setCanvas(Canvas canvas)
	{
		this.canvas = canvas;
	}

	public int getgStage()
	{
		return gStage;
	}

	public void setgStage(int gStage)
	{
		this.gStage = gStage;
	}

	public SurfaceHolder getSurfaceHolder()
	{
		return surfaceHolder;
	}

	public void setSurfaceHolder(SurfaceHolder surfaceHolder)
	{
		this.surfaceHolder = surfaceHolder;
	}

	// 步骤：初始化
	private static final int INIT = 1;
	//步骤：逻辑
	private static final int LOGIC = 2;
	// 步骤：清除
	private static final int CLEAN = 3;
	// 步骤：画
	private static final int PAINT = 4;

	// 处理游戏场景
	public int doStage(int stage, int step)
	{
		int nextStage;
		switch (stage)
		{
			case STAGE_INIT:
				nextStage = doInit(step);
				break;
			case STAGE_LOGIN:
				nextStage = doLogin(step);
				break;
			case STAGE_GAME:
				nextStage = doGame(step);
				break;
			case STAGE_LOSE:
				nextStage = doLose(step);
				break;
			default:
				nextStage = STAGE_ERROR;
				break;
		}
		return nextStage;
	}

	public void stageLogic()
	{
		int newStage = doStage(gStage, LOGIC);
		if (newStage != STAGE_NO_CHANGE && newStage != gStage)
		{
			doStage(gStage, CLEAN); // 清除旧的场景
			gStage = newStage & 0xFF;
			doStage(gStage, INIT);
		}
		else if (stageList.size() > 0)
		{
			newStage = STAGE_NO_CHANGE;
			synchronized (stageList)
			{
				newStage = stageList.get(0);
				stageList.remove(0);
			}
			if (newStage == STAGE_NO_CHANGE)
			{
				return;
			}
			doStage(gStage, CLEAN); // 清楚旧的场景
			gStage = newStage & 0xFF;
			doStage(gStage, INIT);
		}
	}

	// 执行初始化的方法
	public int doInit(int step)
	{
		// 初始化游戏图片
		ViewManager.loadResource();
		// 跳转到登录界面
		return STAGE_LOGIN;
	}

	static class SetViewHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			RelativeLayout layout = (RelativeLayout) msg.obj;
			if (layout != null)
			{
				RelativeLayout.LayoutParams params = new RelativeLayout
						.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				MainActivity.mainLayout.addView(layout, params);
			}
		}
	}
	public Handler setViewHandler = new SetViewHandler();
	static class DelViewHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			RelativeLayout layout = (RelativeLayout) msg.obj;
			if (layout != null)
			{
				MainActivity.mainLayout.removeView(layout);
			}
		}
	}
	public Handler delViewHandler = new DelViewHandler();
	// 定义游戏界面
	RelativeLayout gameLayout = null;
	private static final int ID_LEFT = 9000000;
	private static final int ID_FIRE = ID_LEFT + 1;

	public int doGame(int step)
	{
		switch (step)
		{
			case INIT:
				// 初始化游戏界面
				if (gameLayout == null)
				{
					gameLayout = new RelativeLayout(mainContext);
					// 添加向左移动的按钮
					Button button = new Button(mainContext);
					button.setId(ID_LEFT);
					// 设置按钮的背景图片
					button.setBackground(getResources().getDrawable(R.drawable.left));
					RelativeLayout.LayoutParams params = new RelativeLayout
						.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					params.setMargins((int) (ViewManager.scale * 20),
						0, 0, (int) (ViewManager.scale * 10));
					// 向游戏界面上添加向左的按钮
					gameLayout.addView(button, params);
					// 为按钮添加事件监听器
					button.setOnTouchListener((view, event) -> {
						switch (event.getAction())
						{
							case MotionEvent.ACTION_DOWN:
								player.setMove(Player.MOVE_LEFT);
								break;
							case MotionEvent.ACTION_UP:
								player.setMove(Player.MOVE_STAND);
								break;
							case MotionEvent.ACTION_MOVE:
								break;
						}
						return false;
					});
					// 添加向右移动的按钮
					button = new Button(mainContext);
					// 设置按钮的背景图片
					button.setBackground(getResources().getDrawable(R.drawable.right));
					params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.RIGHT_OF, ID_LEFT);
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					params.setMargins((int) (ViewManager.scale * 20),
						0, 0, (int) (ViewManager.scale * 10));
					// 向游戏界面上添加向右的按钮
					gameLayout.addView(button, params);
					// 为按钮添加事件监听器
					button.setOnTouchListener((view, event) -> {
						switch (event.getAction())
						{
							case MotionEvent.ACTION_DOWN:
								player.setMove(Player.MOVE_RIGHT);
								break;
							case MotionEvent.ACTION_UP:
								player.setMove(Player.MOVE_STAND);
								break;
							case MotionEvent.ACTION_MOVE:
								break;
						}
						return false;
					});
					// 添加射击按钮
					button = new Button(mainContext);
					button.setId(ID_FIRE);
					// 设置按钮的背景图片
					button.setBackgroundResource(R.drawable.fire);
					params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					params.setMargins(0, 0, (int) (ViewManager.scale * 20),
						(int) (ViewManager.scale * 10));
					// 向游戏界面上添加射击的按钮
					gameLayout.addView(button, params);
					// 为按钮添加事件监听器
					button.setOnClickListener(view -> {
						// 当角色的leftShootTime为0时（上一枪发射结束），角色才能发射下一枪。
						if(player.getLeftShootTime() <= 0)
						{
							player.addBullet();
						}
					});
					// 添加跳的按钮
					button = new Button(mainContext);
					// 设置按钮的背景图片
					button.setBackgroundResource(R.drawable.jump);
					params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.LEFT_OF, ID_FIRE);
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					params.setMargins(0, 0, (int) (ViewManager.scale * 20),
						(int) (ViewManager.scale * 10));
					// 向游戏界面上添加跳的按钮
					gameLayout.addView(button, params);
					// 为按钮添加事件监听器
					button.setOnClickListener(view -> player.setJump(true));
					setViewHandler.sendMessage(setViewHandler
							.obtainMessage(0, gameLayout));  // ③
				}
				break;
			case LOGIC:
				// 随机生成怪物
				MonsterManager.generateMonster();
				// 检查碰撞
				MonsterManager.checkMonster();
				// 角色跳与移动
				player.logic();
				// 角色死亡
				if (player.isDie())
				{
					stageList.add(STAGE_LOSE);
				}
				break;
			case CLEAN:
				// 清除游戏界面
				if (gameLayout != null)
				{
					delViewHandler.sendMessage(delViewHandler
							.obtainMessage(0, gameLayout));  // ④
					gameLayout = null;
				}
				break;
			case PAINT:
				// 画游戏元素
				ViewManager.clearScreen(canvas);
				ViewManager.drawGame(canvas);
				break;
		}
		return STAGE_NO_CHANGE;
	}

	// 定义登录界面
	private RelativeLayout loginView;
	public int doLogin(int step)
	{
		switch (step)
		{
			case INIT:
				// 初始化角色血量
				player.setHp(Player.MAX_HP);
				// 初始化登录界面
				if (loginView == null)
				{
					loginView = new RelativeLayout(mainContext);
					loginView.setBackgroundResource(R.drawable.game_back);
					// 创建按钮
					Button button = new Button(mainContext);
					// 设置按钮的背景图片
					button.setBackgroundResource(R.drawable.button_selector);
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.CENTER_IN_PARENT);
					// 添加按钮
					loginView.addView(button, params);
					button.setOnClickListener(view ->
							stageList.add(STAGE_GAME) /* 将游戏场景的常量添加到stageList集合中 */);
					// 通过Handler通知主界面加载loginView组件
					setViewHandler.sendMessage(setViewHandler
							.obtainMessage(0, loginView));  // ①
				}
				break;
			case LOGIC:
				break;
			case CLEAN:
				// 清除登录界面
				if (loginView != null)
				{
					// 通过Handler通知主界面删除loginView组件
					delViewHandler.sendMessage(delViewHandler
							.obtainMessage(0, loginView));  // ②
					loginView = null;
				}
				break;
			case PAINT:
				break;
		}
		return STAGE_NO_CHANGE;
	}

	// 定义游戏失败界面
	private RelativeLayout loseView;
	public int doLose(int step)
	{
		switch (step)
		{
			case INIT:
				// 初始化失败界面
				if (loseView == null)
				{
					// 创建失败界面
					loseView = new RelativeLayout(mainContext);
					loseView.setBackgroundResource(R.drawable.game_back);
					Button button = new Button(mainContext);
					button.setBackgroundResource(R.drawable.again);
					RelativeLayout.LayoutParams params = new RelativeLayout
						.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.CENTER_IN_PARENT);
					loseView.addView(button, params);
					button.setOnClickListener(view -> {
						// 跳转到继续游戏的界面
						stageList.add(STAGE_GAME);
						// 让角色的生命值回到最大值
						player.setHp(Player.MAX_HP);
					});
					setViewHandler.sendMessage(setViewHandler
						.obtainMessage(0, loseView));
				}
				break;
			case LOGIC:
				break;
			case CLEAN:
				// 清除界面
				if (loseView != null)
				{
					delViewHandler.sendMessage(delViewHandler
						.obtainMessage(0, loseView));
					loseView = null;
				}
				break;
			case PAINT:
				break;
		}
		return STAGE_NO_CHANGE;
	}

	// 两次调度之间默认的暂停时间
	public static final int SLEEP_TIME = 40;
	// 最小的暂停时间
	public static final int MIN_SLEEP = 5;
	class GameThread extends Thread
	{
		public SurfaceHolder surfaceHolder = null;
		public boolean needStop = false;
		public GameThread(SurfaceHolder holder)
		{
			this.surfaceHolder = holder;
		}

		public void run()
		{
			long t1, t2;
			Looper.prepare();
			synchronized (surfaceHolder)
			{
				// 游戏未退出
				while (gStage != STAGE_QUIT && !needStop)
				{
					try
					{
						// 处理游戏的场景逻辑
						stageLogic();
						t1 = System.currentTimeMillis();
						canvas = surfaceHolder.lockCanvas();
						if (canvas != null)
						{
							// 处理游戏场景
							doStage(gStage, PAINT);
						}
						t2 = System.currentTimeMillis();
						int paintTime = (int) (t2 - t1);
						long millis = SLEEP_TIME - paintTime;
						if (millis < MIN_SLEEP)
						{
							millis = MIN_SLEEP;
						}
						// 该线程暂停millis毫秒后再次调用doStage()方法
						sleep(millis);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						try
						{
							if (canvas != null)
							{
								surfaceHolder.unlockCanvasAndPost(canvas);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			Looper.loop();
			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	// 游戏线程
	private GameThread thread = null;

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// 启动主线程执行部分
		paint.setTextSize(15);
		if (thread != null)
		{
			thread.needStop = true;
		}
		thread = new GameThread(surfaceHolder);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height)
	{
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
	}
}

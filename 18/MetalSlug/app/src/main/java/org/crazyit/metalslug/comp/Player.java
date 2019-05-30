package org.crazyit.metalslug.comp;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.game.Graphics;
import org.crazyit.metalslug.ViewManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

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
public class Player
{
	// 定义角色的最高生命值
	public static final int MAX_HP = 500;
	// 定义控制角色动作的常量
	// 此处只控制该角色包含站立、跑、跳等动作
	public static final int ACTION_STAND_RIGHT = 1;
	public static final int ACTION_STAND_LEFT = 2;
	public static final int ACTION_RUN_RIGHT = 3;
	public static final int ACTION_RUN_LEFT = 4;
	public static final int ACTION_JUMP_RIGHT = 5;
	public static final int ACTION_JUMP_LEFT = 6;
	// 定义角色向右移动的常量
	public static final int DIR_RIGHT = 1;
	// 定义角色向左移动的常量
	public static final int DIR_LEFT = 2;
	// 控制角色的默认坐标
	public static int X_DEFAULT = 0;
	public static int Y_DEFALUT = 0;
	public static int Y_JUMP_MAX = 0;
	// 保存角色名字的成员变量
	private String name;
	// 保存角色生命值的成员变量
	private int hp;
	// 保存角色所使用枪的类型（以后可考虑让角色能更换不同的枪）
	private int gun;
	// 保存角色当前动作的成员变量（默认向右站立）
	private int action = ACTION_STAND_RIGHT;
	// 代表角色X坐标的成员变量
	private int x = -1;
	 // 代表角色Y坐标的成员变量
	private int y = -1;
	// 保存角色射出的所有子弹
	private final List<Bullet> bulletList = new ArrayList<>();
	// 定义控制角色移动的常量
	// 此处控制该角色只包含站立、向右移动、向左移动三种移动方式
	public static final int MOVE_STAND = 0;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_LEFT = 2;
	// 保存角色移动方式的成员变量
	public int move = MOVE_STAND;
	public static final int MAX_LEFT_SHOOT_TIME = 6;
	// 控制射击状态的保留计数器
	// 每当用户发射一枪时，leftShootTime会被设为MAX_LEFT_SHOOT_TIME。
	// 只有当leftShootTime变为0时，用户才能发射下一枪
	private int leftShootTime = 0;
	// 保存角色是否跳动的成员变量
	public boolean isJump = false;
	// 保存角色是否跳到最高处的成员变量
	public boolean isJumpMax = false;
	// 控制跳到最高处的停留时间
	public int jumpStopCount = 0;
	// 当前正在绘制角色脚部动画的第几帧
	private int indexLeg = 0;
	// 当前正在绘制角色头部动画的第几帧
	private int indexHead = 0;
	// 当前绘制头部图片的X坐标
	private int currentHeadDrawX = 0;
	// 当前绘制头部图片的Y坐标
	private int currentHeadDrawY = 0;
	// 当前正在画的脚部动画帧的图片
	private Bitmap currentLegBitmap = null;
	 // 当前正在画的头部动画帧的图片
	private Bitmap currentHeadBitmap = null;
	// 该变量控制用于控制动画刷新的速度
	private int drawCount = 0;
	// 定义Player的构造器
	public Player(String name, int hp)
	{
		this.name = name;
		this.hp = hp;
	}
	// 初始化角色的初始化位置，角色能跳的最大高度
	public void initPosition()
	{
		x = ViewManager.SCREEN_WIDTH * 15 / 100;
		y = ViewManager.SCREEN_HEIGHT * 75 / 100;
		X_DEFAULT = x;
		Y_DEFALUT = y;
		Y_JUMP_MAX = ViewManager.SCREEN_HEIGHT * 50 / 100;
	}

	// 获取该角色当前方向：action成员变量为奇数代表向右
	public int getDir()
	{
		if (action % 2 == 1)
		{
			return DIR_RIGHT;
		}
		return DIR_LEFT;
	}

	// 返回该角色在游戏界面上的位移
	public int getShift()
	{
		if (x <= 0 || y <= 0)
		{
			initPosition();
		}
		return X_DEFAULT - x;
	}

	// 判断角色是否已经死亡
	public boolean isDie()
	{
		return hp <= 0;
	}
	// 获取该角色发射的所有子弹
	public List<Bullet> getBulletList()
	{
		return bulletList;
	}

	// 画角色的方法
	public void draw(Canvas canvas)
	{
		if (canvas == null)
		{
			return;
		}

		switch (action)
		{
			case ACTION_STAND_RIGHT:
				drawAni(canvas, ViewManager.legStandImage, ViewManager.headStandImage, DIR_RIGHT);
				break;
			case ACTION_STAND_LEFT:
				drawAni(canvas, ViewManager.legStandImage, ViewManager.headStandImage, DIR_LEFT);
				break;
			case ACTION_RUN_RIGHT:
				drawAni(canvas, ViewManager.legRunImage, ViewManager.headRunImage, DIR_RIGHT);
				break;
			case ACTION_RUN_LEFT:
				drawAni(canvas, ViewManager.legRunImage, ViewManager.headRunImage, DIR_LEFT);
				break;
			case ACTION_JUMP_RIGHT:
				drawAni(canvas, ViewManager.legJumpImage, ViewManager.headJumpImage, DIR_RIGHT);
				break;
			case ACTION_JUMP_LEFT:
				drawAni(canvas, ViewManager.legJumpImage, ViewManager.headJumpImage, DIR_LEFT);
				break;
			default:
				break;
		}
	}
	// 绘制角色的动画帧
	public void drawAni(Canvas canvas, Bitmap[] legArr, Bitmap[] headArrFrom, int dir)
	{
		if (canvas == null)
		{
			return;
		}
		if (legArr == null)
		{
			return;
		}

		Bitmap[] headArr = headArrFrom;
		// 射击状态停留次数每次减1
		if (leftShootTime > 0)
		{
			headArr = ViewManager.headShootImage;
			leftShootTime--;
		}

		if (headArr == null)
		{
			return;
		}

		indexLeg = indexLeg % legArr.length;
		indexHead = indexHead % headArr.length;

		// 是否需要翻转图片
		int trans = dir == DIR_RIGHT ? Graphics.TRANS_MIRROR : Graphics.TRANS_NONE;

		Bitmap bitmap = legArr[indexLeg];
		if (bitmap == null || bitmap.isRecycled())
		{
			return;
		}

		// 先画脚
		int drawX = X_DEFAULT;
		int drawY = y - bitmap.getHeight();
		Graphics.drawMatrixImage(canvas, bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), trans, drawX, drawY, 0, Graphics.TIMES_SCALE);
		currentLegBitmap = bitmap;


		// 再画头
		Bitmap bitmap2 = headArr[indexHead];
		if (bitmap2 == null || bitmap2.isRecycled())
		{
			return;
		}
		drawX = drawX - ((bitmap2.getWidth() - bitmap.getWidth()) >> 1);
		if (action == ACTION_STAND_LEFT)
		{
			drawX += (int) (6 * ViewManager.scale);
		}
		drawY = drawY - bitmap2.getHeight() + (int) (10 * ViewManager.scale);
		Graphics.drawMatrixImage(canvas, bitmap2, 0, 0, bitmap2.getWidth(),
				bitmap2.getHeight(), trans, drawX, drawY, 0, Graphics.TIMES_SCALE);
		currentHeadDrawX = drawX;
		currentHeadDrawY = drawY;
		currentHeadBitmap = bitmap2;

		// drawCount控制该方法每调用4次才会切换到下一帧位图
		drawCount++;
		if (drawCount >= 4)
		{
			drawCount = 0;
			indexLeg++;
			indexHead++;
		}
		// 画子弹
		drawBullet(canvas);
		// 画左上角的角色、名字、血量
		drawHead(canvas);
	}

	// 绘制左上角的角色、名字、生命值的方法
	public void drawHead(Canvas canvas)
	{
		if (ViewManager.head == null)
		{
			return;
		}
		// 画头像
		Graphics.drawMatrixImage(canvas, ViewManager.head, 0, 0,
			ViewManager.head.getWidth(),ViewManager.head.getHeight(),
			Graphics.TRANS_MIRROR, 0, 0, 0, Graphics.TIMES_SCALE);
		Paint p = new Paint();
		p.setTextSize(30);
		// 画名字
		Graphics.drawBorderString(canvas, 0xa33e11, 0xffde00, name,
				ViewManager.head.getWidth(), (int) (ViewManager.scale * 20), 3, p);
		// 画生命值
		Graphics.drawBorderString(canvas, 0x066a14, 0x91ff1d, "HP: " + hp,
				ViewManager.head.getWidth(), (int) (ViewManager.scale * 40), 3, p);
	}

	// 判断该角色是否被子弹打中的方法
	public boolean isHurt(int startX, int endX, int startY, int endY)
	{
		if (currentHeadBitmap == null || currentLegBitmap == null)
		{
			return false;
		}
		// 计算角色的图片所覆盖的矩形区域
		int playerStartX = currentHeadDrawX;
		int playerEndX = playerStartX + currentHeadBitmap.getWidth();
		int playerStartY = currentHeadDrawY;
		int playerEndY = playerStartY + currentHeadBitmap.getHeight()
			+ currentLegBitmap.getHeight();
		// 如果子弹出现的位置与角色图片覆盖的矩形区域有重叠，即可判断角色被子弹打中
		return ((startX >= playerStartX && startX <= playerEndX) ||
			(endX >= playerStartX && endX <= playerEndX))
			&& ((startY >= playerStartY && startY <= playerEndY) ||
			(endY >= playerStartY && endY <= playerEndY));
	}

	// 画子弹
	public void drawBullet(Canvas canvas)
	{
		List<Bullet> deleteList = new ArrayList<>();
		Bullet bullet;
		// 遍历角色发射的所有子弹
		for (int i = 0; i < bulletList.size(); i++)
		{
			bullet = bulletList.get(i);
			if (bullet == null)
			{
				continue;
			}
			// 将所有越界的子弹收集到deleteList集合中
			if (bullet.getX() < 0 || bullet.getX() > ViewManager.SCREEN_WIDTH)
			{
				deleteList.add(bullet);
			}
		}
		Bitmap bitmap;
		// 清除所有越界的子弹
		bulletList.removeAll(deleteList);
		// 遍历用户发射的所有子弹
		for (int i = 0; i < bulletList.size(); i++)
		{
			bullet = bulletList.get(i);
			if (bullet == null)
			{
				continue;
			}
			// 获取子弹对应的位图
			bitmap = bullet.getBitmap();
			if (bitmap == null)
			{
				continue;
			}
			// 子弹移动
			bullet.move();
			// 画子弹
			Graphics.drawMatrixImage(canvas, bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), bullet.getDir() == Player.DIR_LEFT ?
				Graphics.TRANS_MIRROR : Graphics.TRANS_NONE,
				bullet.getX(), bullet.getY(), 0, Graphics.TIMES_SCALE);
		}
	}

	public int getLeftShootTime()
	{
		return leftShootTime;
	}

	// 发射子弹的方法
	public void addBullet()
	{
		// 计算子弹的初始X坐标
		int bulletX = getDir() == DIR_RIGHT ? X_DEFAULT +(int)
			(ViewManager.scale * 50) : X_DEFAULT - (int)(ViewManager.scale * 50);
		// 创建子弹对象
		Bullet bullet = new Bullet(Bullet.BULLET_TYPE_1, bulletX,
			y - (int) (ViewManager.scale * 60), getDir());
		// 将子弹添加到用户发射的子弹集合中
		bulletList.add(bullet);
		// 发射子弹时，将leftShootTime设置为射击状态最大值
		leftShootTime = MAX_LEFT_SHOOT_TIME;
		// 播放射击音效
		ViewManager.soundPool.play(ViewManager.soundMap.get(1), 1, 1, 0, 0, 1);
	}

	// 处理角色移动的方法
	private void move()
	{
		if (move == MOVE_RIGHT)
		{
			// 更新怪物的位置
			MonsterManager.updatePosistion((int) (6 * ViewManager.scale));
			// 更新角色位置
			setX(getX() + (int) (6 * ViewManager.scale));
			if (!isJump())
			{
				// 不跳的时候，需要设置动作
				setAction(Player.ACTION_RUN_RIGHT);
			}
		}
		else if (move == MOVE_LEFT)
		{
			if (getX() - (int) (6 * ViewManager.scale) < Player.X_DEFAULT)
			{
				// 更新怪物的位置
				MonsterManager.updatePosistion(-(getX() - Player.X_DEFAULT));
			}
			else
			{
				// 更新怪物的位置
				MonsterManager.updatePosistion(-(int) (6 * ViewManager.scale));
			}
			// 更新角色位置
			setX(getX() - (int) (6 * ViewManager.scale));
			if (!isJump())
			{
				// 不跳的时候，需要设置动作
				setAction(Player.ACTION_RUN_LEFT);
			}
		}
		else if (getAction() != Player.ACTION_JUMP_RIGHT
			&& getAction() != Player.ACTION_JUMP_LEFT)
		{
			if (!isJump())
			{
				// 不跳的时候，需要设置动作
				setAction(Player.ACTION_STAND_RIGHT);
			}
		}
	}

	// 处理角色移动与跳的逻辑关系
	public void logic()
	{
		if (!isJump())
		{
			move();
			return;
		}

		// 如果还没有跳到最高点
		if (!isJumpMax)
		{
			setAction(getDir() == Player.DIR_RIGHT ?
				Player.ACTION_JUMP_RIGHT : Player.ACTION_JUMP_LEFT);
			// 更新Y坐标
			setY(getY() - (int) (8 * ViewManager.scale));
			// 设置子弹在Y方向上具有向上的加速度
			setBulletYAccelate(-(int) (2 * ViewManager.scale));
			// 已经达到最高点
			if (getY() <= Player.Y_JUMP_MAX)
			{
				isJumpMax = true;
			}
		}
		else
		{
			jumpStopCount--;
			// 如果在最高点停留次数已经使用完
			if (jumpStopCount <= 0)
			{
				// 更新Y坐标
				setY(getY() + (int) (8 * ViewManager.scale));
				// 设置子弹在Y方向上具有向下的加速度
				setBulletYAccelate((int) (2 * ViewManager.scale));
				// 已经掉落到最低点
				if (getY() >= Player.Y_DEFALUT)
				{
					// 恢复Y坐标
					setY(Player.Y_DEFALUT);
					isJump = false;
					isJumpMax = false;
					setAction(Player.ACTION_STAND_RIGHT);
				}
				else
				{
					// 未掉落到最低点，继续使用跳的动作
					setAction(getDir() == Player.DIR_RIGHT ?
						Player.ACTION_JUMP_RIGHT : Player.ACTION_JUMP_LEFT);
				}
			}
		}
		// 控制角色移动
		move();
	}

	// 更新子弹的位置（子弹位置同样会受到角色的位移的影响）
	public void updateBulletShift(int shift)
	{
		for (Bullet bullet : bulletList)
		{
			if (bullet == null)
			{
				continue;
			}
			bullet.setX(bullet.getX() - shift);
		}
	}

	// 给子弹设置垂直方向上的加速度
	// 游戏的设计是：当角色跳动时，子弹会具有垂直方向上的加速度
	public void setBulletYAccelate(int accelate)
	{
		for (Bullet bullet : bulletList)
		{
			if (bullet == null || bullet.getyAccelate() != 0)
			{
				continue;
			}
			bullet.setyAccelate(accelate);
		}
	}

	public int getX()
	{
		if (x <= 0 || y <= 0)
		{
			initPosition();
		}

		return x;
	}

	public void setX(int x)
	{
		this.x = x % (ViewManager.map.getWidth() + X_DEFAULT);
		// 如果角色移动到屏幕最左边
		if (this.x < X_DEFAULT)
		{
			this.x = X_DEFAULT;
		}
	}

	public int getY()
	{
		if (x <= 0 || y <= 0)
		{
			initPosition();
		}
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public int getAction()
	{
		return action;
	}

	public void setAction(int action)
	{
		this.action = action;
	}

	public int getMove()
	{
		return move;
	}

	public void setMove(int move)
	{
		this.move = move;
	}

	public boolean isJump()
	{
		return isJump;
	}

	public void setJump(boolean isJump)
	{
		this.isJump = isJump;
		jumpStopCount = 6;
	}
}

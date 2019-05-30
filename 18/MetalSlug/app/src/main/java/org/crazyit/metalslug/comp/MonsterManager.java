package org.crazyit.metalslug.comp;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.metalslug.GameView;
import org.crazyit.metalslug.ViewManager;

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
public class MonsterManager
{
	// 保存所有死掉的怪物，保存它们是为了绘制死亡的动画，绘制完后清除这些怪物
	public static final List<Monster> dieMonsterList = new ArrayList<>();
	// 保存所有活着的怪物
	public static final List<Monster> monsterList = new ArrayList<>();

	// 随机生成、并添加怪物的方法
	public static void generateMonster()
	{
		if (monsterList.size() < 3 + Util.rand(3))
		{
			// 创建新怪物
			Monster monster = new Monster(1 + Util.rand(3));
			monsterList.add(monster);
		}
	}

	// 更新怪物与子弹的坐标的方法
	public static void updatePosistion(int shift)
	{
		Monster monster = null;
		// 定义一个集合，保存所有将要被删除的怪物
		List<Monster> delList = new ArrayList<>();
		// 遍历怪物集合
		for (int i = 0; i < monsterList.size(); i++)
		{
			monster = monsterList.get(i);
			if (monster == null)
			{
				continue;
			}
			// 更新怪物、怪物所有子弹的位置
			monster.updateShift(shift);  // ①
			// 如果怪物的X坐标越界，将怪物添加delList集合中
			if (monster.getX() < 0)
			{
				delList.add(monster);
			}
		}
		// 删除所有delList集合中所有怪物
		monsterList.removeAll(delList);
		delList.clear();
		// 遍历所有已死的怪物的集合
		for (int i = 0; i < dieMonsterList.size(); i++)
		{
			monster = dieMonsterList.get(i);
			if (monster == null)
			{
				continue;
			}
			// 更新怪物、怪物所有子弹的位置
			monster.updateShift(shift);  // ②
			// 如果怪物的X坐标越界，将怪物添加delList集合中
			if (monster.getX() < 0)
			{
				delList.add(monster);
			}
		}
		// 删除所有delList集合中所有怪物
		dieMonsterList.removeAll(delList);
		// 更新玩家控制的角色的子弹坐标
		GameView.player.updateBulletShift(shift);
	}

	// 检查怪物是否将要死亡的方法
	public static void checkMonster()
	{
		// 获取玩家发射的所有子弹
		List<Bullet> bulletList = GameView.player.getBulletList();
		if (bulletList == null)
		{
			bulletList = new ArrayList<>();
		}
		Monster monster;
		// 定义一个delList集合，用于保存将要死亡的怪物
		List<Monster> delList = new ArrayList<>();
		// 定义一个delBulletList集合，用于保存所有将要被删除的子弹
		List<Bullet> delBulletList = new ArrayList<>();
		// 遍历所有怪物
		for (int i = 0; i < monsterList.size(); i++)
		{
			monster = monsterList.get(i);
			if (monster == null)
			{
				continue;
			}
			// 如果怪物是炸弹
			if (monster.getType() == Monster.TYPE_BOMB)
			{
				// 角色被炸弹炸到
				if (GameView.player.isHurt(monster.getStartX()
						, monster.getEndX(), monster.getStartY(), monster.getEndY()))
				{
					// 将怪物设置为死亡状态
					monster.setDie(true);
					// 播放爆炸音效
					ViewManager.soundPool.play(
						ViewManager.soundMap.get(2), 1, 1, 0, 0, 1);
					// 将怪物（爆炸的炸弹）添加到delList集合中
					delList.add(monster);
					// 玩家控制的角色的生命值减10
					GameView.player.setHp(GameView.player.getHp() - 10);
				}
				continue;
			}
			// 对于其他类型的怪物，则需要遍历角色发射的所有子弹
			// 只要任何一个子弹打中怪物，即可判断怪物即将死亡
			for (Bullet bullet : bulletList)
			{
				if (bullet == null || !bullet.isEffect())
				{
					continue;
				}
				// 如果怪物被角色的子弹打到
				if (monster.isHurt(bullet.getX(), bullet.getY()))
				{
					// 将子弹设为无效
					bullet.setEffect(false);
					// 将怪物设为死亡状态
					monster.setDie(true);
					// 如果怪物是飞机
					if(monster.getType() == Monster.TYPE_FLY)
					{
						// 播放爆炸音效
						ViewManager.soundPool.play(
							ViewManager.soundMap.get(2), 1, 1, 0, 0, 1);
					}
					// 如果怪物是人
					if(monster.getType() == Monster.TYPE_MAN)
					{
						// 播放惨叫音效
						ViewManager.soundPool.play(
							ViewManager.soundMap.get(3), 1, 1, 0, 0, 1);
					}
					// 将怪物（被子弹打中的怪物）添加到delList集合中
					delList.add(monster);
					// 将打中怪物的子弹添加到delBulletList集合中
					delBulletList.add(bullet);
				}
			}
			// 将delBulletList包含的所有子弹从bulletList集合中删除
			bulletList.removeAll(delBulletList);
			// 检查怪物子弹是否打到角色
			monster.checkBullet();
		}
		// 将已死亡的怪物（保存在delList集合中）添加到dieMonsterList集合中
		dieMonsterList.addAll(delList);
		// 将已死亡的怪物（保存在delList集合中）从monsterList集合中删除
		monsterList.removeAll(delList);
	}

// 绘制所有怪物的方法
public static void drawMonster(Canvas canvas)
{
	Monster monster;
	// 遍历所有活着的怪物，绘制活着的怪物
	for (int i = 0; i < monsterList.size(); i++)
	{
		monster = monsterList.get(i);
		if (monster == null)
		{
			continue;
		}
		// 画怪物
		monster.draw(canvas);
	}

	List<Monster> delList = new ArrayList<>();
	// 遍历所有已死亡的怪物，绘制已死亡的怪物
	for (int i = 0; i < dieMonsterList.size(); i++)
	{
		monster = dieMonsterList.get(i);
		if (monster == null)
		{
			continue;
		}
		// 画怪物
		monster.draw(canvas);
		// 当怪物的getDieMaxDrawCount()返回0时，表明该怪物已经死亡，
		// 且该怪物的死亡动画所有帧都播放完成，将它们彻底删除。
		if (monster.getDieMaxDrawCount() <= 0)  // ③
		{
			delList.add(monster);
		}
	}
	dieMonsterList.removeAll(delList);
}
}

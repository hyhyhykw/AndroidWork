package inotia.rpg.game;

import java.util.Random;
import java.util.Scanner;

public class Play {
	public static void main(String[] args) {
		Role hero = new Role();
		Scanner input = new Scanner(System.in);
		System.out.print("请选择一个职业:");
		System.out.println("\n1.狂战士\n2.法师\n3.弓箭手");
		int id = input.nextInt() - 1;
		while (id > 2 || id < 0) {
			System.out.print("此职业不存在，请重新选择一个职业:");
			System.out.println("\n1.狂战士\n2.法师\n3.弓箭手");
			id = input.nextInt() - 1;
		}
		String job = hero.characterJob[id];// 英雄的职业
		int heroHp = hero.characterHp[id];// 英雄初始血量
		int heroMp = hero.characterMp[id];// 英雄初始魔法值
		int heroAtt = hero.characterAtt[id];// 英雄初始攻击力
		int heroDef = hero.characterDef[id];// 英雄初始防御

		// 不同英雄分配不同技能
		int skillId = 4;
		String[] skill = hero.characterSkill[id];
		int mpCost = 0;
		for (int i = 0; i < 4; i++) {
			skill[i] = hero.characterSkill[id][i];
		} // 不同英雄分配不同技能结束

		int level = 1;// 初始等级
		int levelExp = hero.levelExp[level - 1];// 每级的升级经验
		int exp = 0;// 初始经验
		System.out.println("你选择了" + job + "，血量为" + heroHp + "，攻击力为" + heroAtt + "，防御力为" + heroDef);

		// 创建怪物
		System.out.println("开始冒险.....");
		Monster monster = new Monster();
		Random random = new Random();
		int monsterId = random.nextInt(20);

		int monsterHp = monster.monsterHP[monsterId];
		int monsterAtt = monster.monsterAtt[monsterId];
		int monsterDef = monster.monsterDef[monsterId];
		String name = monster.monsterName[monsterId];
		int monsterLevel = monster.monsterLevel[monsterId];
		// 创建怪物结束
		int heroHurt = 0;
		int monsterHurt = 0;
		int expGain = monster.monsterEXP[monsterId];
		while (name != null) {
			System.out.println("你当前等级为" + level);
			if (!name.equals("")) {
				expGain = monster.monsterEXP[monsterId];
				monsterAtt = monster.monsterAtt[monsterId];
				monsterDef = monster.monsterDef[monsterId];
				monsterHp = monster.monsterHP[monsterId];
				name = monster.monsterName[monsterId];
				monsterLevel = monster.monsterLevel[monsterId];
				while (heroHp > 0 && monsterHp > 0) {
					if (monsterHp == monster.monsterHP[monsterId]) {
						System.out.println("你遇到了" + name + ",怪物等级为" + monsterLevel + "\n");
					} else {
						System.out.println("继续攻击" + name + "\n");
					}
					System.out.println("请选择您的攻击方式:\n1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3."
							+ skill[2] + "(mp-50)" + "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑");
					skillId = input.nextInt() - 1;// 选择攻击方式
					while (skillId < 0 || skillId > 4) {
						System.out.print("技能不存在，请重新选择攻击方式");
						System.out.println("1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3."
								+ skill[2] + "(mp-50)" + "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑");
						skillId = input.nextInt() - 1;
					}
					if (skillId != 4) {
						mpCost = hero.skillMp[skillId];
					}
					while (heroMp < mpCost && skillId != 0) {
						System.out.println("你的魔法值不足，不能使用此技能,请重新选择一个技能");
						System.out.println("1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3."
								+ skill[2] + "(mp-50)" + "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑");
						skillId = input.nextInt() - 1;
						mpCost = hero.skillMp[skillId];
					}
					if (hero.characterMp[id] - heroMp >= 10 && skillId == 0) {
						mpCost = heroMp - hero.characterMp[id];
					}

					// 计算英雄伤害
					heroHurt = getHeroHurt(id, skillId, heroAtt, monsterDef, monsterAtt);
					if (id == 0 && skillId == 2 && hero.characterHp[id] - heroHp >= 10) {
						heroHp += 10;
					} // 计算英雄伤害结束
						// 计算怪物伤害
					monsterHurt = getMonsterHurt(heroDef, monsterAtt, id, skillId);
					// 计算怪物伤害结束
				}

				if (skillId != 4) {
					monsterHp -= heroHurt;
					heroHp -= monsterHurt;
					heroMp -= mpCost;
					System.out.println(
							"你使用了" + skill[skillId] + "你的剩余魔法值:" + heroMp + "\n怪物剩余血量:" + monsterHp + "\n怪物攻击了你");
				} else {
					System.out.println("你逃跑了");
					break;
				}

				System.out.println("你的剩余血量:" + heroHp);
			}

			if (heroHp > 0 && skillId != 4) {
				exp += expGain;
				System.out.println("\n你赢了，你的剩余血量为" + heroHp + ",获得了经验" + expGain);
			} else if (heroHp <= 0) {
				System.out.println("\n你已经死亡，游戏结束");
				break;
			}

			else {
				System.out.println("\n周围没有动静，继续前进");
			}

			// 升级了
			if (exp > levelExp) {
				level++;
				exp = 0;
				levelExp = hero.levelExp[level - 1];
				heroAtt += 4;
				heroDef += 5;
				heroHp = hero.characterHp[id] + 50;
				hero.characterHp[id] = heroHp;
				heroMp = hero.characterMp[id] + 50;
				hero.characterMp[id] = heroMp;

				System.out
						.println("\n恭喜你升级了,当前攻击力为" + heroAtt + ",防御力为" + heroDef + ",血量为" + heroHp + ",魔法值为" + heroMp);
			} // 升级了

			// 每升10级，遇到一个boss
			// 创建boss
			Boss boss = new Boss();
			// 创建boss结束
			int j = level / 10 - 1;
			if (level % 10 == 0) {
				String bossName = boss.bossName[j];
				int bossAtt = boss.bossAtt[j];
				int bossDef = boss.bossDef[j];
				int bossHp = boss.bossHp[j];
				int bossExp = boss.bossExp[j];
				System.out.println("恭喜你升到了" + level + "级,你遇到了" + bossName + ",这是一个强大的怪物,努力去战胜它");
				int bossSkillId = random.nextInt(5);
				String bossSkill = boss.bossSkill[bossSkillId];
				while (bossHp > 0 && heroHp > 0) {
					System.out.println("请选择您的攻击方式:\n1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3."
							+ skill[2] + "(mp-50)" + "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑");
					skillId = input.nextInt() - 1;// 选择攻击方式
					while (skillId < 0 || skillId > 4) {
						System.out.print("技能不存在，请重新选择攻击方式");
						System.out.println("1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3."
								+ skill[2] + "(mp-50)" + "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑");
						skillId = input.nextInt() - 1;
					}

					while (heroMp < mpCost && skillId != 0) {
						System.out.println("你的魔法值不足，不能使用此技能,请重新选择一个技能");
						System.out.println("1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3."
								+ skill[2] + "(mp-50)" + "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑");
						skillId = input.nextInt() - 1;
						mpCost = hero.skillMp[skillId];
					}

					if (hero.characterMp[id] - heroMp >= 10 && skillId == 0) {
						mpCost = heroMp - hero.characterMp[id];
					}
					heroHurt = getHeroHurt(id, skillId, heroAtt, bossDef, bossAtt);
					if (id == 0 && skillId == 2 && hero.characterHp[id] - heroHp >= 10) {
						heroHp += 10;
					} // 计算英雄伤害结束
					int bossHurt = getBossHurt(id, skillId, bossAtt, bossSkillId, bossHp, heroDef, j);

					System.out.println("你使用了" + skill[skillId]);
					bossHp -= heroHurt;
					heroMp -= mpCost;

					System.out.println("boss使用了" + bossSkill);
					heroHp -= bossHurt;
					System.out.println("你的剩余魔法值:" + heroMp + "你的剩余血量:" + heroHp + "\nboss剩余血量" + bossHp);

					bossSkillId = random.nextInt(5);
					bossSkill = boss.bossSkill[bossSkillId];
				}

				if (heroHp > 0) {
					exp += bossExp;
					System.out.println("\n恭喜你战胜了" + bossName + "，你的剩余血量为" + heroHp + ",获得了经验" + bossExp);
				} else {
					System.out.println("\n你被击败了，等你变强之后在战胜它吧！等级减1");
					level--;
					exp = 0;
					levelExp = hero.levelExp[level - 1];
					heroAtt -= 4;
					heroDef -= 5;
					heroHp = hero.characterHp[id] - 50;
					hero.characterHp[id] = heroHp;
					heroMp = hero.characterMp[id] - 50;
					hero.characterMp[id] = heroMp;
				}

				if (exp > levelExp) {
					level++;
					exp = 0;
					levelExp = hero.levelExp[level - 1];
					heroAtt += 4;
					heroDef += 5;
					heroHp = hero.characterHp[id] + 50;
					hero.characterHp[id] = heroHp;
					heroMp = hero.characterMp[id] + 50;
					hero.characterMp[id] = heroMp;

					System.out.println(
							"\n恭喜你升级了,当前攻击力为" + heroAtt + ",防御力为" + heroDef + ",血量为" + heroHp + ",魔法值为" + heroMp);
				}
			}

			// 每升10级，遇到一个boss 结束

			monsterId = random.nextInt(20);
			System.out.println("\n是否继续前进:1.是  2.否");
			int num = input.nextInt();
			switch (num) {
			case 1:
				name = monster.monsterName[monsterId];
				break;
			case 2:
				name = null;
				break;
			}

			if (level >= 50) {
				System.out.println("恭喜你完成了所有冒险");
				break;
			}

		}
		input.close();
		System.out.println("Game Over");

	}

	private static int getBossHurt(int id, int skillId, int bossAtt, int bossSkillId, int bossHp, int heroDef, int j) {
		int bossHurt = 0;
		// 计算boss伤害
		if (bossSkillId == 0) {
			if (id == 0 && skillId == 3) {
				bossHurt = (int) (bossAtt - heroDef * 1.4);
			} else if (id == 2 && skillId != 0) {
				bossHurt = bossAtt - heroDef + 5;
			} else {
				bossHurt = bossAtt - heroDef;
			}
		} else if (bossSkillId == 1) {
			if (id == 0 && skillId == 3) {
				bossHurt = (int) (bossAtt * 0.8 + 10 - heroDef * 1.4);
			} else if (id == 2 && skillId != 0) {
				bossHurt = (int) (bossAtt * 0.8 + 10 - heroDef + 5);
			} else {
				bossHurt = (int) (bossAtt * 0.8 + 10 - heroDef);
			}
		} else if (bossSkillId == 2) {
			if (id == 0 && skillId == 3) {
				bossHurt = (int) (bossAtt * 1.1 + 5 - heroDef * 1.4);
			} else if (id == 2 && skillId != 0) {
				bossHurt = (int) (bossAtt * 1.1 + 5 - heroDef + 5);
			} else {
				bossHurt = (int) (bossAtt * 1.1 + 5 - heroDef);
			}
		} else if (bossSkillId == 3) {
			if (id == 0 && skillId == 3) {
				bossHurt = (int) (bossAtt * 1.2 + 10 - heroDef * 1.4);
			} else if (id == 2 && skillId != 0) {
				bossHurt = (int) (bossAtt * 1.2 + 10 - heroDef + 5);
			} else {
				bossHurt = (int) (bossAtt * 1.2 + 10 - heroDef);
			}
		} else if (bossSkillId == 4) {
			if (id == 0 && skillId == 3) {
				bossHurt = (int) (bossAtt - heroDef * 1.4);
			} else if (id == 2 && skillId != 0) {
				bossHurt = bossAtt - heroDef + 5;
			} else {
				bossHurt = bossAtt - heroDef;
			}
			bossHp += j * 30 * 1.2;
		}
		return bossHurt;

		// 计算boss伤害结束
	}

	private static int getMonsterHurt(int heroDef, int monsterAtt, int id, int skillId) {
		int monsterHurt = 0;
		if (monsterAtt > heroDef) {
			if (id == 0 && skillId == 3) {
				monsterHurt = (int) (monsterAtt - heroDef * 1.4);
			} else if (id == 2 && skillId != 0) {
				monsterHurt = monsterAtt - heroDef + 5;
			} else {
				monsterHurt = monsterAtt - heroDef;
			}
		} else {
			monsterHurt = 5;
		}
		return monsterHurt;

	}

	private static int getHeroHurt(int id, int skillId, int heroAtt, int monsterDef, int monsterAtt) {

		int heroHurt = 0;
		if (skillId == 0) {
			if (heroAtt <= monsterDef) {
				heroHurt = 5;
			} else {
				heroHurt = heroAtt - monsterDef;
			}
		}
		if (skillId != 0 && skillId != 4) {
			if (heroAtt > monsterDef) {
				if (id == 0) {
					if (skillId == 1) {
						heroHurt = heroAtt - monsterDef + 10;
					} else if (skillId == 2) {
						heroHurt = (int) (heroAtt * 0.8 - monsterDef);
					} else if (skillId == 3) {
						heroHurt = (int) (heroAtt + monsterAtt * 0.3 - monsterDef);
					}
				} else if (id == 1) {
					if (skillId == 1) {
						heroHurt = (int) (heroAtt * 1.5 - monsterDef);
					} else if (skillId == 2) {
						heroHurt = (int) (10 * (0.08 * heroAtt + 3) - monsterDef);
					} else if (skillId == 3) {
						heroHurt = (int) (heroAtt * 0.8 + 20 - monsterDef);
					}
				} else if (id == 2) {
					if (skillId == 1) {
						heroHurt = heroAtt * 2 - monsterDef;
					} else if (skillId == 2) {
						heroHurt = (int) (heroAtt * 1.8 + 20 - monsterDef);
					} else if (skillId == 3) {
						heroHurt = heroAtt * 3 - monsterDef;
					}
				}
			} // 计算英雄伤害结束

		}
		return heroHurt;
	}
}

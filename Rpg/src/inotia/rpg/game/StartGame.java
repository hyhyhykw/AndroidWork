package inotia.rpg.game;

import java.util.Random;

import javax.swing.JOptionPane;

public class StartGame {
	private static int getNum(String str, String str1) {
		boolean flag = false;
		int id = 0;
		try {
			id = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			flag = true;
		}
		while (flag) {
			String str2 = JOptionPane.showInputDialog("格式错误，请重新输入" + "\n" + str1);
			try {
				id = Integer.parseInt(str2);
				flag = false;
			} catch (NumberFormatException e) {
				flag = true;
			}
		}
		return id;
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

	public static void main(String[] args) {
		Role hero = new Role();
		String str1 = "\n1.狂战士\n2.法师\n3.弓箭手";
		String str = JOptionPane.showInputDialog("请选择一个职业:" + str1);
		int id = getNum(str, str1);
		int heroId = id - 1;
		while (heroId > 2 || heroId < 0) {
			str = JOptionPane.showInputDialog("此职业不存在，请重新选择一个职业:" + str1);
			id = getNum(str, str1);
			heroId = id - 1;
		}
		String job = hero.characterJob[heroId];// 英雄的职业
		int heroHp = hero.characterHp[heroId];// 英雄初始血量
		int heroMp = hero.characterMp[heroId];// 英雄初始魔法值
		int heroAtt = hero.characterAtt[heroId];// 英雄初始攻击力
		int heroDef = hero.characterDef[heroId];// 英雄初始防御

		// 不同英雄分配不同技能
		int skillId = 4;
		String[] skill = hero.characterSkill[heroId];
		int mpCost = 0;
		for (int i = 0; i < 4; i++) {
			skill[i] = hero.characterSkill[heroId][i];
		} // 不同英雄分配不同技能结束

		int level = 1;// 初始等级
		int levelExp = hero.levelExp[level - 1];// 每级的升级经验
		int exp = 0;// 初始经验
		JOptionPane.showMessageDialog(null, "你选择了" + job + "，血量为" + heroHp + "，攻击力为" + heroAtt + "，防御力为" + heroDef);
		// 创建怪物
		JOptionPane.showMessageDialog(null, "开始冒险.....");
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
		while (name != null) {
			JOptionPane.showMessageDialog(null, "你当前等级为" + level);
			if (!name.equals("")) {
				int expGain = monster.monsterEXP[monsterId];
				monsterAtt = monster.monsterAtt[monsterId];
				monsterDef = monster.monsterDef[monsterId];
				monsterHp = monster.monsterHP[monsterId];
				name = monster.monsterName[monsterId];
				monsterLevel = monster.monsterLevel[monsterId];
				while (heroHp > 0 && monsterHp > 0) {
					if (monsterHp == monster.monsterHP[monsterId]) {
						JOptionPane.showMessageDialog(null, "你遇到了" + name + ",怪物等级为" + monsterLevel + "\n");
					} else {
						JOptionPane.showMessageDialog(null, "继续攻击" + name + "\n");
					}
					str1 = "\n1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3." + skill[2] + "(mp-50)"
							+ "\n4." + skill[3] + "(mp-100)" + "\n5.逃跑";
					str = JOptionPane.showInputDialog("请选择您的攻击方式:" + str1);
					skillId = getNum(str, str1) - 1;// 选择攻击方式
					while (skillId < 0 || skillId > 4) {
						str = JOptionPane.showInputDialog("技能不存在，请重新选择攻击方式" + str1);
						skillId = getNum(str, str1) - 1;
					}
					if (skillId != 4) {
						mpCost = hero.skillMp[skillId];
					}
					while (heroMp < mpCost && skillId != 0) {
						str = JOptionPane.showInputDialog("你的魔法值不足，不能使用此技能,请重新选择一个技能" + str1);
						skillId = getNum(str, str1) - 1;
						mpCost = hero.skillMp[skillId];
					}
					if (hero.characterMp[heroId] - heroMp <= 10 && skillId == 0) {
						mpCost = heroMp - hero.characterMp[heroId];
					}

					// 计算英雄伤害
					heroHurt = getHeroHurt(id, skillId, heroAtt, monsterDef, monsterAtt);
					if (id == 0 && skillId == 2 && hero.characterHp[id] - heroHp >= 10) {
						heroHp += 10;
					} // 计算英雄伤害结束
						// 计算怪物伤害
					monsterHurt = getMonsterHurt(heroDef, monsterAtt, id, skillId);
					if (skillId != 4) {
						monsterHp -= heroHurt;
						heroHp -= monsterHurt;
						heroMp -= mpCost;
						JOptionPane.showMessageDialog(null,
								"你使用了" + skill[skillId] + "你的剩余魔法值:" + heroMp + "\n怪物剩余血量:" + monsterHp + "\n怪物攻击了你");
					} else {
						JOptionPane.showMessageDialog(null, "\n你逃跑了");
						break;
					}

					JOptionPane.showMessageDialog(null, "你的剩余血量:" + heroHp);

				}
				if (heroHp > 0 && skillId != 4) {
					exp += expGain;
					JOptionPane.showMessageDialog(null, "\n你赢了，你的剩余血量为" + heroHp + ",获得了经验" + expGain);
				} else if (heroHp <= 0) {
					JOptionPane.showMessageDialog(null, "\n你已经死亡，游戏结束");
					break;
				}

			} else {
				JOptionPane.showMessageDialog(null, "\n周围没有动静，继续前进");
			}

			// 升级了
			if (exp > levelExp) {
				level++;
				exp = 0;
				levelExp = hero.levelExp[level - 1];
				heroAtt += 4;
				heroDef += 5;
				heroHp = hero.characterHp[heroId] + 50;
				hero.characterHp[heroId] = heroHp;
				heroMp = hero.characterMp[heroId] + 50;
				hero.characterMp[heroId] = heroMp;

				JOptionPane.showMessageDialog(null,
						"\n恭喜你升级了,当前攻击力为" + heroAtt + ",\n防御力为" + heroDef + ",\n血量为" + heroHp + ",\n魔法值为" + heroMp);
			} // 升级了

			// 每升10级，遇到一个boss
			// 创建boss
			Boss boss = new Boss();
			// 创建boss结束
			int bossId = level / 10 - 1;
			if (level % 10 == 0) {
				String bossName = boss.bossName[bossId];
				int bossAtt = boss.bossAtt[bossId];
				int bossDef = boss.bossDef[bossId];
				int bossHp = boss.bossHp[bossId];
				int bossExp = boss.bossExp[bossId];
				JOptionPane.showMessageDialog(null, "恭喜你升到了" + level + "级,你遇到了" + bossName + ",这是一个强大的怪物,努力去战胜它");
				int bossSkillId = random.nextInt(5);
				String bossSkill = boss.bossSkill[bossSkillId];
				while (bossHp > 0 && heroHp > 0) {
					str1 = "\n1." + skill[0] + "(mp+10)" + "\n2." + skill[1] + "(mp-20)" + "\n3." + skill[2] + "(mp-50)"
							+ "\n4." + skill[3] + "(mp-100)";
					str = JOptionPane.showInputDialog("请选择您的攻击方式:" + str1);
					skillId = getNum(str, str1) - 1;// 选择攻击方式
					while (skillId < 0 || skillId > 4) {
						str = JOptionPane.showInputDialog("技能不存在，请重新选择攻击方式" + str1);
						skillId = getNum(str, str1) - 1;
					}

					while (heroMp < mpCost && skillId != 0) {
						str = JOptionPane.showInputDialog("你的魔法值不足，不能使用此技能,请重新选择一个技能" + str1);
						skillId = getNum(str, str1) - 1;
						mpCost = hero.skillMp[skillId];
					}

					if (hero.characterMp[heroId] - heroMp <= 10 && skillId == 0) {
						mpCost = heroMp - hero.characterMp[heroId];
					}

					heroHurt = getHeroHurt(id, skillId, heroAtt, bossDef, bossAtt);
					if (id == 0 && skillId == 2 && hero.characterHp[id] - heroHp >= 10) {
						heroHp += 10;
					} // 计算英雄伤害结束
					int bossHurt = getBossHurt(id, skillId, bossAtt, bossSkillId, bossHp, heroDef, bossId);
					// 计算boss伤害结束
					JOptionPane.showMessageDialog(null, "你使用了" + skill[skillId] + "\nboss使用了" + bossSkill);
					bossHp -= heroHurt;
					heroMp -= mpCost;
					heroHp -= bossHurt;
					JOptionPane.showMessageDialog(null,
							"你的剩余魔法值:" + heroMp + "你的剩余血量:" + heroHp + "\nboss剩余血量" + bossHp);

					bossSkillId = random.nextInt(5);
					bossSkill = boss.bossSkill[bossSkillId];
				}

				if (heroHp > 0) {
					exp += bossExp;
					JOptionPane.showMessageDialog(null,
							"\n恭喜你战胜了" + bossName + "，你的剩余血量为" + heroHp + ",获得了经验" + bossExp);
				} else {
					JOptionPane.showMessageDialog(null, "\n你被击败了，等你变强之后在战胜它吧！等级减1");
					level--;
					exp = 0;
					levelExp = hero.levelExp[level - 1];
					heroAtt -= 4;
					heroDef -= 5;
					heroHp = hero.characterHp[heroId] - 50;
					hero.characterHp[heroId] = heroHp;
					heroMp = hero.characterMp[heroId] - 50;
					hero.characterMp[heroId] = heroMp;
				}

				if (exp > levelExp) {
					level++;
					exp = 0;
					levelExp = hero.levelExp[level - 1];
					heroAtt += 4;
					heroDef += 5;
					heroHp = hero.characterHp[heroId] + 50;
					hero.characterHp[heroId] = heroHp;
					heroMp = hero.characterMp[heroId] + 50;
					hero.characterMp[heroId] = heroMp;

					JOptionPane.showMessageDialog(null,
							"\n恭喜你升级了,当前攻击力为" + heroAtt + ",防御力为" + heroDef + ",血量为" + heroHp + ",魔法值为" + heroMp);
				}
			}
			// 每升10级，遇到一个boss 结束
			monsterId = random.nextInt(20);
			str1 = "\n是否继续前进:1.是  2.否";
			str = JOptionPane.showInputDialog(str1);
			int num = getNum(str, str1);
			while (num > 2 || num < 1) {
				str = JOptionPane.showInputDialog("输入有误，请重新输入:");
				num = getNum(str, str1);
			}
			switch (num) {
			case 1:
				name = monster.monsterName[monsterId];
				break;
			case 2:
				name = null;
				break;
			}
			if (level >= 50) {
				JOptionPane.showMessageDialog(null, "恭喜你完成了所有冒险");
				break;
			}
		}
		JOptionPane.showMessageDialog(null, "Game Over");

	}

}

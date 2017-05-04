package inotia.rpg.game;

public class Role {
	public final String[] characterJob = { "狂战士", "法师", "弓箭手" };// 职业
	public final String[][] characterSkill = { { "普通攻击", "狂乱突击", "血之渴望", "盾牌格挡" }, { "普通攻击", "爆裂火球", "魔法风暴", "九天玄雷" },
			{ "普通攻击", "死亡狙击", "火焰之箭", "万箭齐发" } };// 技能
	public final int[] skillMp = { -10, 20, 50, 100 };// 技能mp消耗
	int[] characterHp = { 200, 150, 150 };// 初始hp
	int[] characterMp = { 100, 200, 150 };// 初始mp
	int[] characterDef = { 20, 10, 15 };// 初始防御
	int[] characterAtt = { 10, 20, 15 };// 初始攻击力

	public final int[] levelExp = new int[50];
	{// 升级所需经验值
		levelExp[0] = 15;
		levelExp[1] = 23;
		for (int i = 2; i < 50; i++) {
			if (i < 19) {
				levelExp[i] = levelExp[i - 1] + levelExp[i - 2];
			} else {
				levelExp[i] = (int) (levelExp[i - 1] * 1.1);
			}
		} // 升级所需经验值
	}

}

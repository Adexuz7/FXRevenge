package dad.fxrevenge.parameters;

import dad.fxrevenge.dialog.CharacterList;
import dad.fxrevenge.models.Avatar;
import dad.fxrevenge.models.ClassType;
import dad.fxrevenge.models.Skill;

public class Player {

	private static Avatar player = new Avatar(
			CharacterList.getPlayer().getPortrait(), 
			ClassType.Warlord,
			Skill.generateClassSkills(ClassType.Warlord), 
			CharacterList.getPlayer().getName()
			);

	public static Avatar getPlayer() {
		return player;
	}

}
package dad.fxrevenge.dialog;

import dad.fxrevenge.scene.DialogScene;
import dad.fxrevenge.scene.SceneManager;
import dad.fxrevenge.world.CMap;
import javafx.scene.image.Image;

public class CDialog extends DialogScene {

	// Imagen de fondo
	private Image background = new Image("/image/background/c.png");

	// Personajes
	private Character player = CharacterList.getPlayer();
	private Character c = CharacterList.getC();

	@Override
	public void start() {
		setGraphics(background, player, c);
		super.start();
	}

	@Override
	public void update() {
		super.update();

		// Diálogos
		switch (dialogNumber) {

		case 0:
			CharacterTalking(player, "(Estás pensado qué vas a almorzar cuando llegues a casa cuando de repente...)");
			break;

		case 1:
			CharacterTalking(c, "¡Buenos días chicos! Tienen 10 minutos para aprender a usar pyspell.");
			break;

		case 2:
			CharacterTalking(c,
					"Pasado ese tiempo tendrán que hacerme una demostración de lo que han conseguido hacer con él.");
			break;

		case 3:
			CharacterTalking(player, "(Le rezas a la diosa Java que te dé su bendición para poder lograrlo a tiempo)");
			break;

		case 4:
			CharacterTalking(c, "El objetivo es que seáis capaces de aprender nuevos hechizos rápidamente. ¡Suerte!");
			break;

		case 5:
			CharacterTalking(player, "En fin, manos a la obra.");
			break;

		default:
			SceneManager.changeScene(new CMap());
			break;

		}
	}
}

package dad.fxrevenge.scene;

import java.util.HashSet;

import dad.fxrevenge.dialog.Character;
import dad.fxrevenge.models.Avatar;
import dad.fxrevenge.parameters.Parameters;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Clase (padre) que controla la pantalla de diálogos entre personajes
 **/

public class DialogScene implements GameScene {

	protected Avatar avatar;

	// Nodo padre del canvas
	private Group root;

	// Escena que se creará a partir del nodo root
	protected Scene scene;

	// Clase encargada de renderizar la escena
	private AnimationTimer animationTimer;

	// Lienzo (canvas) sobre el que dibujar la escena
	protected Canvas canvas;
	protected GraphicsContext graphicsContext;

	// Hashset en el que se registrarán las teclas que se vayan presionando
	protected HashSet<String> currentlyActiveKeys;

	// Gráficos de la escena: fondo y retrato de los personajes izquierdo y derecho
	private Image player, enemy, background;

	// Clase encargada de dibujar el texto de los diálogos
	protected Dialog dialog;

	// Variable encargada de que se muestre el diálogo correspondiente
	protected int dialogNumber = 0;

	// Función para asignar los gráficos: imagen de fondo y los personajes que
	// aparecerán en la escena
	public void setGraphics(Character player, Character enemy, Image background) {
		this.player = player.getPortrait();
		this.enemy = enemy.getPortrait();
		this.background = background;
	}

	// Función que inicia la escena
	@Override
	public void start() {

		canvas = new Canvas();
		graphicsContext = canvas.getGraphicsContext2D();

		root = new Group();
		scene = new Scene(root, Parameters.getResolutionWidth(), Parameters.getResolutionHeight());
		root.getChildren().add(canvas);

		dialog = new Dialog(scene, graphicsContext);

		prepareActionHandlers();

		// Bucle principal que controla la escena
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};

		animationTimer.start();

	}

	// Función que detiene la escena
	@Override
	public void stop() {
		animationTimer.stop();
	}

	// Función que controla la entrada por teclado
	private void prepareActionHandlers() {

		// HashSet que registrará las teclas que se vayan presionando
		currentlyActiveKeys = new HashSet<String>();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// Añade la tecla presionada al HashSet
				currentlyActiveKeys.add(event.getCode().toString());
			}
		});

	}

	// Función que se ejecuta dentro del bucle principal
	protected void update() {

		// Redimensionar canvas
		canvas.setWidth(scene.getWidth());
		canvas.setHeight(scene.getHeight());

		// Limpiar canvas
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Dibujar fondo
		graphicsContext.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

		// Fuente del texto
		Font font = Font.font("Arial", FontWeight.NORMAL, 19);
		graphicsContext.setFont(font);

		// Vuelve al diálogo anterior
		if (currentlyActiveKeys.contains("Q")) {
			currentlyActiveKeys.clear();
			if (dialogNumber > 0)
				dialogNumber--;
		}

		// Avanza al diálogo siguiente
		if (currentlyActiveKeys.contains("E")) {
			currentlyActiveKeys.clear();
			dialogNumber++;
		}

	}

	// Función que controla los diálogos de los personajes
	protected void CharacterTalking(Character character, String dialogText) {

		// Dibujar el retrato del personaje
		graphicsContext.drawImage(character.getPortrait(), 0, 0);

		// Dibujar diálogo del personaje
		dialog.showDialog(character.getName(), dialogText);
	}

	// GETTERS Y SETTERS

	public Scene getScene() {
		return scene;
	}

	// Imagen de Fondo
	public Image getBackground() {
		return background;
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	// Imagen del personaje izquierdo
	public Image getLeftCharacter() {
		return player;
	}

	public void setLeftCharacter(Image leftCharacter) {
		this.player = leftCharacter;
	}

	// Imagen del personaje derecho
	public Image getRightCharacter() {
		return enemy;
	}

	public void setRightCharacter(Image rightCharacter) {
		this.enemy = rightCharacter;
	}

}

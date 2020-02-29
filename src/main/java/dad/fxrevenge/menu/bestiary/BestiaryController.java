package dad.fxrevenge.menu.bestiary;

import java.io.IOException;

import dad.fxrevenge.models.Enemy;
import dad.fxrevenge.models.Race;
import dad.fxrevenge.scene.GameScene;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Componente que permite visualizar los enemigos que no son jefes
 * 
 */
public class BestiaryController extends BorderPane implements GameScene {

	// model
	private SimpleListProperty<Enemy> listaBichos = new SimpleListProperty<Enemy>(this, "listaBichos",
			FXCollections.observableArrayList());

	@SuppressWarnings("unused")
	private Scene scene;

	private Enemy enemy;

	@FXML
	private BorderPane view;

	@FXML
	private Spinner<Integer> level;

	@FXML
	private ImageView enemyImage;

	@FXML
	private VBox datosBox;

	@FXML
	private GridPane datosGrid;

	@FXML
	private TextField expTextField;

	@FXML
	private TextField oroTextField;

	@FXML
	private TextArea descripiconArea;

	@FXML
	private HBox tituloHBox;

	@FXML
	private Label tituloLabel;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;
	
	@FXML
    private Button backButton;

    @FXML
    void onBackAction(ActionEvent event) {

    }

	@FXML
	void onNextAction(ActionEvent event) {
		Enemy nuevo = null;
		for (int i = 0; i < listaBichos.size(); i++) {
			if (enemy.getRace().equals(listaBichos.get(i).getRace())) {
				if (i == listaBichos.size() - 1) {
					nuevo = listaBichos.get(0);
				} else {
					nuevo = listaBichos.get(i + 1);
				}
			}
		}
		enemy = nuevo;
		bindeos();
	}

	@FXML
	void onPreviousAction(ActionEvent event) {
		Enemy nuevo = null;
		for (int i = 0; i < listaBichos.size(); i++) {
			if (enemy.getRace().equals(listaBichos.get(i).getRace())) {
				if (i == 0) {
					nuevo = listaBichos.get(listaBichos.size() - 1);
				} else {
					nuevo = listaBichos.get(i - 1);
				}
			}
		}
		enemy = nuevo;
		bindeos();
	}

	/**
	 * Funcion para bindear de nuevo los datos tras el cambio de enemigo
	 */
	private void bindeos() {
		tituloLabel.setText(enemy.getRace().toString());
		enemy.levelProperty().bind(level.valueProperty());
		expTextField.textProperty().bind(enemy.expProperty().multiply(enemy.levelProperty()).asString());
		oroTextField.textProperty().bind(enemy.moneyDropProperty().asString());
		enemyImage.imageProperty().bind(enemy.combatSpriteProperty());

//REVISAR LISTA DE DROPS dropList.setItems(enemy);
		descripiconArea.setText(enemy.getDescription());
	}

	@Override
	public void start() {

		scene = new Scene(view);

		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		level.setValueFactory(valueFactory);

		// seteos de la informacion

		tituloLabel.setText(enemy.getRace().toString());
		enemy.levelProperty().bind(level.valueProperty());
		expTextField.textProperty().bind(enemy.expProperty().multiply(enemy.levelProperty()).asString());
		oroTextField.textProperty().bind(enemy.moneyDropProperty().asString());
		enemyImage.imageProperty().bind(enemy.combatSpriteProperty());

//REVISAR LISTA DE DROPS		dropList.setItems(enemy);
		descripiconArea.setText(enemy.getDescription());
	}

	/**
	 * Constructor que genera la lista de enemigos para visualizarlos
	 * 
	 * @throws IOException Si ocurre algún error durante la carga del archivo
	 */
	public BestiaryController() throws IOException {
		super();

		for (Race r : Race.values()) {
			if (!r.equals(Race.M) && !r.equals(Race.V) && !r.equals(Race.C) && !r.equals(Race.FX))
				listaBichos.add(new Enemy(r, 1));
		}
		this.enemy = listaBichos.get(0);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BestiarioView.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
	}

	public BorderPane getView() {
		return view;
	}

	public final SimpleListProperty<Enemy> listaBichosProperty() {
		return this.listaBichos;
	}

	public final ObservableList<Enemy> getListaBichos() {
		return this.listaBichosProperty().get();
	}

	public final void setListaBichos(final ObservableList<Enemy> listaBichos) {
		this.listaBichosProperty().set(listaBichos);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}

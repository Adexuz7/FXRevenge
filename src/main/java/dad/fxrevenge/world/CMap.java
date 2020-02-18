package dad.fxrevenge.world;

import javafx.scene.image.Image;

public class CMap extends WorldMapController {

	private static Image background = new Image("/image/background/c.png");

	private static String[][] map = {
//			{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"},
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." },
			{ "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x" },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." },
			{ ".", "T1", ".", ".", ".", ".", "T2", ".", ".", ".", ".", ".", ".", ".", "T1" },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "T1" },
			{ ".", ".", ".", ".", "P", ".", ".", ".", ".", "M", ".", ".", ".", ".", "." },
			{ ".", ".", ".", "T3", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "T2", ".", ".", "." },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." },
			{ ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "." } };

	public CMap() {
		super(map, background);
	}

}

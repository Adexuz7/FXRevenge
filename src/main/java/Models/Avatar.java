package Models;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

public class Avatar extends Attributes {

	private IntegerProperty currentExp = new SimpleIntegerProperty();
	private IntegerProperty totalLevelExp = new SimpleIntegerProperty();
	private IntegerProperty money = new SimpleIntegerProperty();
	private ObjectProperty<Image> appearance = new SimpleObjectProperty<Image>();
	private ClassType work;
	private ListProperty<Item> inventory = new SimpleListProperty<Item>();
	private ListProperty<Gear> equipped = new SimpleListProperty<Gear>(this, "equipped",
			FXCollections.observableArrayList());
	private ListProperty<Skill> skills = new SimpleListProperty<Skill>(this, "skills",
			FXCollections.observableArrayList());
	private ListProperty<Skill> learnedSkills = new SimpleListProperty<Skill>(this, "learnedSkills",
			FXCollections.observableArrayList());
	private IntegerProperty currentMana = new SimpleIntegerProperty();

	// constructor
	public Avatar(Image appearance, ClassType work, List<Skill> skills, String name) {

		this.setWork(work);
		this.setCurrentExp(0);
		this.setMoney(0);
		this.setName(name);
		this.setLevel(1);
		this.setLuck(0);
		this.setTotalLevelExp(150);
		this.setAppearance(appearance);
		this.setCritChance(0);

		Gear helm = new Gear("Casco de practicas");
		helm.setPos(GearPosition.Helmet);
		Gear chest = new Gear("Peto de practicas");
		chest.setPos(GearPosition.Chest);
		Gear gloves = new Gear("Guanteletes de practicas");
		gloves.setPos(GearPosition.Gloves);
		Gear leggings = new Gear("Pantalones de practicas");
		leggings.setPos(GearPosition.Leggings);
		Gear boots = new Gear("Botas de practicas");
		boots.setPos(GearPosition.Boots);
		Gear rightHand = new Gear("Arma de Practica");
		rightHand.setPos(GearPosition.RightHand);
		Gear leftHand = new Gear("Complemento de practica");
		leftHand.setPos(GearPosition.LeftHand);

		this.equipped.addAll(helm, chest, gloves, leggings, boots, rightHand, leftHand);

		if (work.equals(ClassType.Archmage)) {

			this.setSkills(FXCollections.observableArrayList(skills));
			this.setCurrentMana(30);

			this.setHealth(20);
			this.setPhysDamage(2);
			this.setPhysDef(5);
			this.setMana(20);
			this.setMagicDamage(9);
			this.setMagicDef(7);
			this.setCurrentLife(this.getCurrentLife());

		} else if (work.equals(ClassType.Hunter)) {

			this.setSkills(FXCollections.observableArrayList(skills));
			this.setCurrentMana(23);

			this.setHealth(25);
			this.setPhysDamage(8);
			this.setPhysDef(5);
			this.setMana(15);
			this.setMagicDamage(0);
			this.setMagicDef(3);

		} else if (work.equals(ClassType.Warlord)) {

			this.setSkills(FXCollections.observableArrayList(skills));
			this.setCurrentMana(15);

			this.setHealth(30);
			this.setPhysDamage(5);
			this.setPhysDef(10);
			this.setMana(10);
			this.setMagicDamage(0);
			this.setMagicDef(10);

		}

		this.setCurrentLife(this.getHealth());

	}

	// funciones
	public void addItemToInventory(Item item) {
		Boolean added = false;
		for (int i = 0; i < this.getInventory().size(); i++) {
			if (this.getInventory().get(i).getName().equals(item.getName())) {
				this.getInventory().get(i).setQuantity(this.getInventory().get(i).getQuantity()+1);
				added = true;
			}
		}
		if (!added) {
			this.getInventory().add(item);
		}
	}
	
	public void useItem (Item item) {
		if (item.getEffect().equals(Effect.HealRestore)) {
			this.setCurrentLife(this.getHealth());
		} else {
			this.setCurrentMana(this.getMana());
		}
	}

	public int atacar() {
		int danyo = this.getPhysDamage();

		int proc = ((int) Math.random() * 100);

		if (proc < this.getCritChance()) {
			danyo *= 2;
		}

		return danyo;
	}

	public void recibeDaño(int danyo, boolean fisico) {
		if (fisico)
			this.setCurrentLife(this.getCurrentLife() - (danyo - this.getPhysDef()));
		else
			this.setCurrentLife(this.getCurrentLife() - (danyo - this.getMagicDef()));
	}

	public int atacar(Skill hability) {
		int danyo = hability.getDamage();
		this.setCurrentMana(this.getCurrentMana() - hability.getCost());

		if (danyo > 0) {
			if (hability.getDamageType()) {
				danyo += (hability.getDamageMultiplier() * this.getPhysDamage());
			} else {
				danyo += (hability.getDamageMultiplier() * this.getMagicDamage());
			}
		} else {
			if ((this.getCurrentLife() - danyo) > this.getHealth())
				this.setCurrentLife(this.getHealth());
			else
				this.setCurrentLife(this.getCurrentLife() - danyo);
		}
		// calculo critico
		int proc = ((int) Math.random() * 100);
		if (proc <= this.getCritChance()+hability.getAddCritChance()) {
			danyo *= 2;
		}
		return danyo;

	}

	public void levelUp() {

		this.setLevel(this.getLevel() + 1);
		this.setTotalLevelExp((int) (getTotalLevelExp() * 1.25));
		this.setCurrentExp(0);

		if (work.equals(ClassType.Archmage)) {
			this.setPhysDamage(this.getPhysDamage() + 1);
			this.setPhysDef(this.getPhysDef() + (int) (Math.random() * 2 + 1));
			this.setMana(this.getMana() + 20);
			this.setMagicDamage(this.getMagicDamage() + (int) (Math.random() * 6 + 4));
			this.setMagicDef(this.getMagicDef() + (int) (int) (Math.random() * 4 + 3));
			this.setHealth(this.getHealth() + (int) (Math.random() * 30 + 20));
		}
		if (work.equals(ClassType.Hunter)) {
			this.setPhysDamage(this.getPhysDamage() + (int) (Math.random() * 6 + 4));
			this.setPhysDef(this.getPhysDef() + (int) (Math.random() * 3 + 2));
			this.setMana(this.getMana() + 15);
			this.setMagicDef(this.getMagicDef() + (int) (int) (Math.random() * 3 + 2));
			this.setHealth(this.getHealth() + (int) (Math.random() * 40 + 30));
		}
		if (work.equals(ClassType.Warlord)) {
			this.setPhysDamage(this.getPhysDamage() + (int) (Math.random() * 4 + 2));
			this.setPhysDef(this.getPhysDef() + (int) (Math.random() * 6 + 4));
			this.setMana(this.getMana() + 10);
			this.setMagicDef(this.getMagicDef() + (int) (Math.random() * 6 + 4));
			this.setHealth(this.getHealth() + (int) (Math.random() * 60 + 40));

		}
	}

	public boolean sumarexp(int exp) {
		boolean lvlup = this.getCurrentExp() + exp > this.getTotalLevelExp();
		if (lvlup) {
			this.levelUp();
		}
		return lvlup;
	}

	// equipar items
	public void equipar(Gear equipment, Gear current) {
		try {
			Popup popup = new Popup();
			EventHandler<MouseEvent> evento;
			evento = new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Equipamiento");
					alert.setHeaderText("¿Quieres sustituir tu " + current.getName() + " por el recien obtenido "
							+ equipment.getName());
					alert.setContentText(
							"Equipado: " + current.toString() + "\n o \n" + equipment.toString() + "\n ¿Qué decides?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get().equals(ButtonType.OK)) {
						cambiaequipo(equipment, false);
					} else {
						cambiaequipo(equipment, true);
					}
				}
			};
			popup.setAutoHide(true);
			// popup.show(owner);

		} catch (NullPointerException e) {

		}
	}

	private void cambiaequipo(Gear newequip, boolean vender) {
		if (!vender) {
			for (int i = 0; i < this.getEquipped().size(); i++) {
				if (this.getEquipped().get(i).getPos().equals(newequip.getPos())) {
					Gear viejo = this.getEquipped().get(i);
					this.getEquipped().add(newequip);
					// actualizarstarts
					actualizarStats(viejo, newequip);
					this.getEquipped().remove(i);
				}
			}
		}
	}

	private void actualizarStats(Gear oldGear, Gear newGear) {
		this.setHealth(this.getHealth() - oldGear.getHealth() + newGear.getHealth());
		this.setMana(this.getMana() - oldGear.getMana() + newGear.getMana());
		this.setLuck(this.getLuck() - oldGear.getLuck() + newGear.getLuck());
		this.setCritChance(this.getCritChance() - oldGear.getCritChance() + newGear.getCritChance());
		this.setPhysDamage(this.getPhysDamage() - oldGear.getPhysDamage() + newGear.getPhysDamage());
		this.setPhysDef(this.getPhysDef() - oldGear.getPhysDef() + newGear.getPhysDef());
		this.setMagicDamage(this.getMagicDamage() - oldGear.getMagicDamage() + newGear.getMagicDamage());
		this.setMagicDef(this.getMagicDef() - oldGear.getMagicDef() + newGear.getMagicDef());
	}
	// getters-setters

	public final IntegerProperty currentExpProperty() {
		return this.currentExp;
	}

	public final int getCurrentExp() {
		return this.currentExpProperty().get();
	}

	public final void setCurrentExp(final int currentExp) {
		this.currentExpProperty().set(currentExp);
	}

	public final IntegerProperty totalLevelExpProperty() {
		return this.totalLevelExp;
	}

	public final int getTotalLevelExp() {
		return this.totalLevelExpProperty().get();
	}

	public final void setTotalLevelExp(final int totalLevelExp) {
		this.totalLevelExpProperty().set(totalLevelExp);
	}

	public final IntegerProperty moneyProperty() {
		return this.money;
	}

	public final int getMoney() {
		return this.moneyProperty().get();
	}

	public final void setMoney(final int money) {
		this.moneyProperty().set(money);
	}

	public final ListProperty<Item> inventoryProperty() {
		return this.inventory;
	}

	public final ObservableList<Item> getInventory() {
		return this.inventoryProperty().get();
	}

	public final void setInventory(final ObservableList<Item> inventory) {
		this.inventoryProperty().set(inventory);
	}

	public final ListProperty<Gear> equippedProperty() {
		return this.equipped;
	}

	public final ObservableList<Gear> getEquipped() {
		return this.equippedProperty().get();
	}

	public final void setEquipped(final ObservableList<Gear> equipped) {
		this.equippedProperty().set(equipped);
	}

	public final ListProperty<Skill> skillsProperty() {
		return this.skills;
	}

	public final ObservableList<Skill> getSkills() {
		return this.skillsProperty().get();
	}

	public final void setSkills(final ObservableList<Skill> skills) {
		this.skillsProperty().set(skills);
	}

	public final IntegerProperty currentManaProperty() {
		return this.currentMana;
	}

	public final int getCurrentMana() {
		return this.currentManaProperty().get();
	}

	public final void setCurrentMana(final int currentMana) {
		this.currentManaProperty().set(currentMana);
	}

	public final ListProperty<Skill> learnedSkillsProperty() {
		return this.learnedSkills;
	}

	public final ObservableList<Skill> getLearnedSkills() {
		return this.learnedSkillsProperty().get();
	}

	public final void setLearnedSkills(final ObservableList<Skill> learnedSkills) {
		this.learnedSkillsProperty().set(learnedSkills);
	}

	public Avatar getAvatar() {
		return this;
	}

	public final ObjectProperty<Image> appearanceProperty() {
		return this.appearance;
	}

	public final Image getAppearance() {
		return this.appearanceProperty().get();
	}

	public final void setAppearance(final Image appearance) {
		this.appearanceProperty().set(appearance);
	}

	public ClassType getWork() {
		return work;
	}

	public void setWork(ClassType work) {
		this.work = work;
	}

}

package com.example.shooter;

import javafx.application.Platform;
import javafx.scene.*;
import com.example.shooter.timer.MyTimer;
import com.example.shooter.timer.Updatable;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


import static com.example.shooter.Constants.*;

public class JavaFXApplication extends Application {
	private boolean gameOver;
	private MyTimer timer;
	private MyTimer pickuptimer;
	private MyTimer eventTimer;
	AtomicLong pickupTime = new AtomicLong(0L);
	private Group root;

	MyTimer bulletTimer = new MyTimer(new Updatable[0]);



	public void finishGame() {
		this.gameOver = true;
		this.timer.stop();
		this.eventTimer.stop();
		Text text = new Text("Game Over");
		text.setFont(new Font(80.0D));
		text.setFill(Color.BLACK);
		text.getTransforms().addAll(new Translate(375.0D, 375.0D), new Translate(-187.5D, 0.0D));
		this.root.getChildren().add(text);
		bulletTimer.stop();


	}
	public void WonGame() {
		this.gameOver = true;
		this.timer.stop();
		this.eventTimer.stop();
		Text text = new Text("Game Won");
		text.setFont(new Font(80.0D));
		text.setFill(Color.BLACK);
		text.getTransforms().addAll(new Translate(375.0D, 375.0D), new Translate(-187.5D, 0.0D));
		this.root.getChildren().add(text);
		bulletTimer.stop();

	}

	@Override
	public void start ( Stage stage ) throws IOException {

		Label labelTip = new Label("Izaberite vrstu topa");
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Mali", "Srednji", "Veliki");
		comboBox.setValue("Mali");


		Label labelTipTeren = new Label("Tip terena");
		ComboBox<String> comboBoxTeren = new ComboBox<>();
		comboBoxTeren.getItems().addAll("Teren1", "Teren2");
		comboBoxTeren.setValue("Teren1");


		Label labelTipEnemyHP = new Label("Tezina");
		ComboBox<String> comboBoxEnemyHP = new ComboBox<>();
		comboBoxEnemyHP.getItems().addAll("Lako", "Srednje", "Tesko");
		comboBoxEnemyHP.setValue("Srednje");

		// Create a Button
		Button button = new Button("Potvrdi");


		// Add event handler to the button
		button.setOnAction(event -> {
			String selectedItem = comboBox.getSelectionModel().getSelectedItem();
			String tipTerena = comboBoxTeren.getSelectionModel().getSelectedItem();
			String tezina = comboBoxEnemyHP.getSelectionModel().getSelectedItem();

			populateGame(stage,selectedItem, tipTerena,tezina);
		});

		// Create a VBox to hold the ComboBox and Button
		VBox vbox = new VBox(10, labelTip, comboBox, labelTipTeren, comboBoxTeren, labelTipEnemyHP,comboBoxEnemyHP, button);
		this.root = new Group(vbox);
		Scene scene = new Scene ( root, WINDOW_WIDTH, WINDOW_HEIGHT );




		scene.setCursor(Cursor.NONE);
		stage.setTitle("Shooter");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();





	}


	public void populateGame(Stage stage,String tipIgraca, String tipTerena, String tezina) {

		int hp = 0;
		Translate playerPosition = new Translate(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
		Player playerr = null;
		switch (tipIgraca) {
			case "Srednji": {
				playerr = new Player(PLAYER_RADIUS, playerPosition, 0.3 * PLAYER_RADIUS, 2 * PLAYER_RADIUS,
						2.1, 5, 1.);
				break;
			}
			case "Mali": {
				playerr = new Player(PLAYER_RADIUS, playerPosition, 0.2 * PLAYER_RADIUS, 1.5 * PLAYER_RADIUS,
						3.5, 3, 1.5);
				break;
			}
			case "Veliki": {
				playerr = new Player(PLAYER_RADIUS, playerPosition, 0.6 * PLAYER_RADIUS, 1.6 * PLAYER_RADIUS,
						1.1, 8, 0.5);
				break;
			}
			default: {
				playerr = new Player(PLAYER_RADIUS, playerPosition, 0.3 * PLAYER_RADIUS, 2 * PLAYER_RADIUS,
						2.1, 5, 1.);
				break;
			}
		}
		FieldInterface fieldd = null;
		final Player player = playerr;
		switch (tipTerena) {
			case ("Teren1"): {
				Translate fieldPosition = new Translate(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
				fieldd = new Field(FIELD_RADIUS, fieldPosition);
				break;
			}
			case ("Teren2"): {
				Translate fieldPosition = new Translate(WINDOW_WIDTH / 2 - FIELD_RADIUS, WINDOW_HEIGHT / 2 - FIELD_RADIUS);

				fieldd = new FieldRect(FIELD_RADIUS * 2, FIELD_RADIUS * 2, fieldPosition);
				break;
			}
			default: {
				Translate fieldPosition = new Translate(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
				fieldd = new Field(FIELD_RADIUS, fieldPosition);
				break;
			}
		}
		switch (tezina) {
			case ("Lako"): {
				hp = 2;
				break;
			}
			case ("Srednje"): {
				hp = 4;
				break;
			}
			case ("Tesko"): {
				hp = 8;
				break;
			}
			default: {
				hp = 4;
				break;
			}
		}

		bulletTimer.start();
		HealthBar HP = new HealthBar(750.0D, 15.0D, Color.GREEN);
		HP.getTransforms().add(new Translate(0.0D, 735.0D));
		if (fieldd instanceof Field) {
			Field krug = (Field) fieldd;
			krug.setFill(new ImagePattern(new Image("grass.jpg")));
			this.root = new Group(new Node[]{krug, player, HP});

		} else if (fieldd instanceof FieldRect) {
			FieldRect kvadrat = (FieldRect) fieldd;
			kvadrat.setFill(new ImagePattern(new Image("concrete.jpg")));
			this.root = new Group(new Node[]{kvadrat, player, HP});
		}

		final FieldInterface field = fieldd;


		ElapsedTime elapsedTime = new ElapsedTime();
		elapsedTime.setFill(Color.BLACK);
		elapsedTime.setFont(new Font(30.0D));
		elapsedTime.getTransforms().add(new Translate(30.0D, 30.0D));
		this.root.getChildren().add(elapsedTime);
		this.timer = new MyTimer(new Updatable[]{elapsedTime});
		this.pickuptimer = new MyTimer(new Updatable[0]);
		pickuptimer.start();
		List<Enemy> enemies = new ArrayList();
		ArrayList<Wall> walls = new ArrayList();
		List<Coin> coins = new ArrayList();

		Wall wall5=(new Wall(0.0, 260.0, 0.0));
		Wall wall6=(new Wall(0.0, -260.0, 0.0));
		Wall wall7=(new Wall(260.0, 0.0, 90.0));
		Wall wall8=(new Wall(-260.0, 0.0, 90.0));
		walls.addAll(Arrays.asList(wall5, wall6, wall7, wall8));
		AtomicReference<Long> lastWallTime = new AtomicReference<Long>(0L);
		Updatable loopWall = dns -> {
			if((double)((Long)lastWallTime.get().longValue()) >= 5.0E9){
				lastWallTime.set(0L);
				for(Wall wall : walls){
					wall.toggle();
				}
			}
			else {
				lastWallTime.updateAndGet(v->v+dns);

			}
			return false;
		};
		timer.add(loopWall);

		for (int i = 0; i < NUMBER_OF_ENEMIES; ++i) {
			Enemy enemy = new Enemy(ENEMY_RADIUS, hp, i);




			// Apply initial placement transformation
			enemy.getTransforms().add(new Translate(375.0D, 375.0D));
			enemy.getTransforms().add(new Rotate(90.0D * (double) i));
			enemy.getTransforms().add(new Translate(Constants.ENEMY_PLACEMENT_RADIUS, 0.0D));


			// Update gun rotation
			enemy.updateGun(player);


			this.root.getChildren().add(enemy);

			enemies.add(enemy);
		}
		root.getChildren().addAll(wall5,wall6,wall7,wall8);

		AtomicReference<Ammunition> ammunitionRef = new AtomicReference<>(new Ammunition(93.75D, 75.0D, 4));
		Ammunition ammunition = new Ammunition(93.75D, 75.0D, 4);
		this.root.getChildren().add(ammunition);
		ammunition.getTransforms().add(new Translate(656.25D, 0.0D));
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
			if (!this.gameOver) {
				double stepX = 0.0D;
				double stepY = 0.0D;


				switch (keyEvent.getCode()) {
					case UP:
						stepY = -4.0D;
						break;
					case DOWN:
						stepY = 4.0D;
						break;
					case LEFT:
						stepX = -4.0D;
						break;
					case RIGHT:
						stepX = 4.0D;
				}
				if (stepX != 0.0D || stepY != 0.0D) {
					player.move(stepX, stepY, field);
					if (enemies.isEmpty()) {
						WonGame();
					}


					Iterator var9 = enemies.iterator();

					while (var9.hasNext()) {
						Enemy enemy = (Enemy) var9.next();
						enemy.updateGun(player);
					}
				}
			}
		});

		for (Enemy enemy : enemies) {
			Updatable loopEnemyAim = dns -> {
				enemy.updateGun(player);
				return enemy.isDead();
			};
			timer.add(loopEnemyAim);
		}
		scene.addEventHandler(MouseEvent.ANY, (mouseEvent) -> {
			if (!this.gameOver) {
				if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED) && mouseEvent.isSecondaryButtonDown()) {
					if (!ammunition.hasOne()) {
						return;
					}
					//System.out.println("usao");
					ammunition.useOne();
					ammunitionRef.get().useOne();


				}

				Bullet bullet = player.handleMouseEvent(mouseEvent);
				if (bullet != null) {
					this.root.getChildren().add(bullet);
					Updatable updatable = (dns) -> {
						boolean outOfBounds = bullet.update(dns, 0.0D, 750.0D, 0.0D, 750.0D);
						boolean collided = false;


						if (outOfBounds) {
							this.root.getChildren().remove(bullet);
						} else {

							for (int i = 0; i < walls.size(); ++i) {
								boolean bl = collided = ((Wall)walls.get(i)).handleCollision(bullet) && ((Wall)walls.get(i)).isVisible();
								if (!collided) continue;
								root.getChildren().remove(bullet);
								return true;
							}
							for (int i = 0; i < enemies.size(); ++i) {

								collided = ((Enemy) enemies.get(i)).handleCollision(bullet);
								if (collided) {
									this.root.getChildren().remove(bullet);
									if (((Enemy) enemies.get(i)).isDead()) {
										this.root.getChildren().remove(enemies.get(i));
										enemies.remove(i);
									}
									break;
								}
							}
						}

						return outOfBounds || collided;
					};
					this.timer.add(updatable);
				}

			}
		});
		SecondCounter.Callback callback = () -> {
			if (enemies.size() != 0) {
				int index = (int) ((double) enemies.size() * Math.random());
				Bullet bullet = ((Enemy) enemies.get(index)).fire(player);
				this.root.getChildren().add(bullet);
				Updatable updatable = (dns) -> {
					boolean outOfBounds = bullet.update(dns, 0.0D, 750.0D, 0.0D, 750.0D);
					boolean collided = false;

					if (outOfBounds) {
						this.root.getChildren().remove(bullet);
					} else {

						collided = player.handleCollision(bullet);
						if (collided) {
							if(player.isShielded()){
								bullet.bounceOff(player);
								this.root.getChildren().remove(bullet);
							}
							else {
								bullet.setFill(Color.TRANSPARENT);
								player.updateLives(-1);
								HP.reduceBy(100. / (player.getMaxLives()));
								System.out.println(player.getLives());
								if (player.getLives() == 0) {

										finishGame();

								}
							}

						}
					}


					return outOfBounds || collided;
				};
				this.timer.add(updatable);
			}
		};
		SecondCounter secondCounter = new SecondCounter(0.5D, new SecondCounter.Callback[]{callback});
		this.eventTimer = new MyTimer(new Updatable[]{secondCounter});
		this.eventTimer.start();
		this.timer.start();
		scene.setFill(new ImagePattern(new Image("water.jpg")));
		scene.setCursor(Cursor.NONE);
		stage.setTitle("Shooter");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();


		Updatable loopPickup = dns -> {
			Translate position = null;
			double fieldX = field.getPosition().getX();
			double fieldY = field.getPosition().getY();
			boolean sudar = false;
			boolean overlap = false;

			while (!sudar) {
				position = new Translate(field.getLength() * 2 * Math.random() + field.getPosition().getX() - field.getLength(), field.getLength() * 2 * Math.random() + field.getPosition().getY() - field.getLength());
				if (field instanceof Field) {
					double dx = position.getX() - fieldX;
					double dy = position.getY() - fieldY;
					double dr = field.getLength() - 10 * 0.5;

					double distanceSquared = dx * dx + dy * dy;
					sudar = distanceSquared < dr * dr;
				} else if (field instanceof FieldRect) {
					if (!(position.getX() < fieldX || position.getX() > fieldX + ((FieldRect) field).getWidth() ||
							position.getY() < fieldY || position.getY() > fieldY + ((FieldRect) field).getHeight()))
						sudar = true;
				}
			}

			final Translate positionn = position;


			if ((double) pickupTime.get() >= 2.0E9) {
				pickupTime.set(0L);
				double rand = Math.random();

				if (rand <= 0.5) {
					Coin coin = new Coin(10, positionn, Color.GOLD);
					for (Node node : this.root.getChildren()) {
						if (node instanceof Coin) {
							Coin existingCoin = (Coin) node;
							if (existingCoin.getBoundsInParent().intersects(coin.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					for (Node node : this.root.getChildren()) {
						if (node instanceof Shield) {
							Shield existingShield = (Shield) node;
							if (existingShield.getBoundsInParent().intersects(coin.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					for (Node node : this.root.getChildren()) {
						if (node instanceof Heart) {
							Heart existingHeart = (Heart) node;
							if (existingHeart.getBoundsInParent().intersects(coin.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					if (!overlap) {
						this.root.getChildren().add(coin);
					}
					Updatable coinPickup = dns1 -> {
						if (coin.isPickedUp(player)) {
							//coin.PickUpEffect(player);
							root.getChildren().remove(coin);
							this.root.getChildren().remove(ammunitionRef.get());
							ammunitionRef.get().updateCount(1);
							ammunition.updateCount(1);
							int newCount = ammunitionRef.get().getCount();
							Ammunition newAmmunition = new Ammunition(93.75D, 75.0D, newCount);

							newAmmunition.getTransforms().add(new Translate(656.25D, 0.0D));

							ammunitionRef.set(newAmmunition);

							this.root.getChildren().add(newAmmunition);
							return true;
						}
						return false;
					};
					pickuptimer.add(coinPickup);
				} else if (rand > 0.5 && rand < 0.8) {
					Heart heart = new Heart(5, positionn);
					for (Node node : this.root.getChildren()) {
						if (node instanceof Heart) {
							Heart existingHeart = (Heart) node;
							if (existingHeart.getBoundsInParent().intersects(heart.getBoundsInParent())) {
								overlap = true;
							}
						}
						if (node instanceof Shield) {
							Shield existingShield = (Shield) node;
							if (existingShield.getBoundsInParent().intersects(heart.getBoundsInParent())) {
								overlap = true;
							}
						}
						if (node instanceof Coin) {
							Coin existingCoin = (Coin) node;
							if (existingCoin.getBoundsInParent().intersects(heart.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					if (!overlap) {
						this.root.getChildren().add(heart);
					}
					Updatable heartPickup = dns1 -> {
						if (heart.isPickedUp(player)) {
							heart.updateHP(player);
							HP.increaseBy(100./player.getMaxLives());
							root.getChildren().remove(heart);
							return true;

						}
						return false;
					};
					pickuptimer.add(heartPickup);
				} else {
					Shield shield = new Shield(5, positionn);
					for (Node node : this.root.getChildren()) {
						if (node instanceof Shield) {
							Shield existingShield = (Shield) node;
							if (existingShield.getBoundsInParent().intersects(shield.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					for (Node node : this.root.getChildren()) {
						if (node instanceof Heart) {
							Heart existingHeart = (Heart) node;
							if (existingHeart.getBoundsInParent().intersects(shield.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					for (Node node : this.root.getChildren()) {
						if (node instanceof Coin) {
							Coin existingCoin = (Coin) node;
							if (existingCoin.getBoundsInParent().intersects(shield.getBoundsInParent())) {
								overlap = true;
							}
						}
					}
					if (!overlap) {
						this.root.getChildren().add(shield);
					}
					Updatable loopShield = dns1 -> {
						if (shield.isPickedUp(player)) {
							shield.PickUpEffect(player);
							//player.addShield();
							root.getChildren().remove(shield);
							return true;
						}
						return false;
					};
					pickuptimer.add(loopShield);
				}
			} else {
				pickupTime.addAndGet(dns);
			}
			return false;
		};
		timer.add(loopPickup);

		AtomicReference<Long> lastTime = new AtomicReference<Long>(0L);
		AtomicReference<Long> limit = new AtomicReference<Long>(new Random().nextLong(0L, 2000000000L));
		Updatable loopEnemyShoot = dns -> {
			if (!enemies.isEmpty()) {
				return true;
			}
			Enemy chosen = (Enemy) enemies.get(new Random().nextInt(enemies.size()));
			if ((long) lastTime.get() <= (long) limit.get()) {
				lastTime.updateAndGet(v -> v + dns);
				return chosen.isDead();
			}
			lastTime.set(0L);
			limit.set(new Random().nextLong(0L, 2000000000L));
			Bullet bullet = chosen.fire(player);
			root.getChildren().add(bullet);
			Updatable loopBullet = dns1 -> {
				boolean outOfBounds = bullet.update(dns, 0.0D, 750.0D, 0.0D, 750.0D);
				boolean collided = player.handleCollision(bullet);

				if (collided) {


					if (player.isShielded()) {

						bullet.bounceOff(player);
						return false;
					}
					else {
						player.updateLives(-1);
						HP.reduceBy(100. / player.getMaxLives());
						if (player.getLives() == 0) {
							finishGame();
						}
						bullet.relocate(-1000.0, -1000.0);
						root.getChildren().remove(bullet);
						root.requestLayout();
					}

				}
				return outOfBounds || collided;
			};
			bulletTimer.add(loopBullet);
			return chosen.isDead();

		};
		timer.add(loopEnemyShoot);
	}

	
	public static void main ( String[] args ) {
		launch ( );
	}
}
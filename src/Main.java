import java.awt.Point;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	ImageView agent;
	Rectangle[] point;
	Game game;
	boolean gameOver, firstTime;
	Button againButton, b4, b5, b6, b7, b8, solver;
	int length, newLength, rectX, rectY, xPlus, yPlus, rectWidth;
	Pane root;
	boolean gameSolver, bSolver;
	Thread th;

	@Override
	public void start(Stage arg0) throws Exception {

		newLength = 4;
		firstTime = true;
		gameSolver = false;

		againButton = new Button();
		againButton.getStyleClass().add("againButton");
		againButton.relocate(40, 45);
		againButton.setFocusTraversable(false);

		b4 = new Button();
		b4.getStyleClass().add("b4");
		b4.relocate(55, 10);
		b4.setVisible(false);
		b4.setFocusTraversable(false);

		b5 = new Button();
		b5.getStyleClass().add("b5");
		b5.relocate(55, 40);
		b5.setVisible(false);
		b5.setFocusTraversable(false);

		b6 = new Button();
		b6.getStyleClass().add("b6");
		b6.relocate(55, 70);
		b6.setVisible(false);
		b6.setFocusTraversable(false);

		b7 = new Button();
		b7.getStyleClass().add("b7");
		b7.relocate(55, 100);
		b7.setVisible(false);
		b7.setFocusTraversable(false);

		b8 = new Button();
		b8.getStyleClass().add("b8");
		b8.relocate(55, 130);
		b8.setVisible(false);
		b8.setFocusTraversable(false);

		againButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				againButton.setVisible(false);
				b4.setVisible(true);
				b5.setVisible(true);
				b6.setVisible(true);
				b7.setVisible(true);
				b8.setVisible(true);
			}
		});

		b4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newLength = 4;
				reset();
			}
		});

		b5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newLength = 5;
				reset();
			}
		});

		b6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newLength = 6;
				reset();
			}
		});

		b7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newLength = 7;
				reset();
			}
		});

		b8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newLength = 8;
				reset();
			}
		});

		solver = new Button();
		solver.getStyleClass().add("play");
		solver.setFocusTraversable(false);
		bSolver = false;

		solver.setOnAction(new EventHandler<ActionEvent>() {

			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent arg0) {

				if (bSolver) {
					solver.getStyleClass().remove("stop");
					solver.getStyleClass().add("play");
					bSolver = false;
					th.stop();
				} else {
					solver.getStyleClass().remove("play");
					solver.getStyleClass().add("stop");
					bSolver = true;
					solver.requestFocus();

					th.start();

				}
			}
		});

		root = new Pane(againButton, b4, b5, b6, b7, b8, solver);

		reset();
		firstTime = false;

		Scene scene = new Scene(root, 864, 748);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		scene.setOnKeyPressed(this::keyHandle);
		scene.setOnMouseClicked(this::mouseClicked);
		arg0.setScene(scene);
		arg0.setTitle("Wampus World");
		arg0.setResizable(false);
		arg0.show();
	}

	private void mouseClicked(MouseEvent event) {
		b4.setVisible(false);
		b5.setVisible(false);
		b6.setVisible(false);
		b7.setVisible(false);
		b8.setVisible(false);
		againButton.setVisible(true);
	}

	private void keyHandle(KeyEvent event) {
		if (gameOver)
			return;

		if (event.getCode() == KeyCode.UP) {
			if (agent.getLayoutY() > 50) {
				agent.setLayoutY(agent.getLayoutY() - yPlus);
				game.moveAgentToUp();
				getGameInfo();
			}
		}
		if (event.getCode() == KeyCode.DOWN) {
			if (agent.getLayoutY() + rectWidth < 630) {
				agent.setLayoutY(agent.getLayoutY() + yPlus);
				game.moveAgentToDown();
				getGameInfo();
			}

		}
		if (event.getCode() == KeyCode.LEFT) {
			if (agent.getLayoutX() > 280) {
				agent.setLayoutX(agent.getLayoutX() - xPlus);
				game.moveAgentToLeft();
				getGameInfo();
			}
		}
		if (event.getCode() == KeyCode.RIGHT) {
			if (agent.getLayoutX() + rectWidth < 810) {
				agent.setLayoutX(agent.getLayoutX() + xPlus);
				game.moveAgentToRight();
				getGameInfo();
			}
		}
	}

	private void getGameInfo(Game.State state, Point agentPoint) {
		int x = agentPoint.x;
		int y = agentPoint.y;
		if (x + y == 0)
			return;
		gameOver = game.isGameOver();
		int p = x + y * length;
		switch (state) {
		case PIT:
			point[p].setStyle("-fx-fill: url(\"images/pit.jpg\");");
			break;
		case WAMPUS:
			point[p].setStyle("-fx-fill: url(\"images/wampus.jpg\");");
			break;
		case BREEZE:
			point[p].setStyle("-fx-fill: url(\"images/breeze.png\");");
			break;
		case SMELL:
			point[p].setStyle("-fx-fill: url(\"images/smell.png\");");
			break;
		case SB:
			point[p].setStyle("-fx-fill: url(\"images/sb.png\");");
			break;
		case GOLD:
			point[p].setStyle("-fx-fill: url(\"images/gold.png\");");
			break;
		case BLANK:
			point[p].setStyle("-fx-fill: url(\"images/ok.png\");");
			break;
		}
	}

	private void getGameInfo() {
		getGameInfo(game.getState(), new Point(game.getAgentX(), game.getAgentY()));
	}

	@SuppressWarnings("deprecation")
	private void reset() {
		mouseClicked(null);
		root.requestFocus();
		if (bSolver) {
			solver.getStyleClass().remove("stop");
			solver.getStyleClass().add("play");
			bSolver = false;
		}
		if (!firstTime) {
			for (int i = 0; i < length * length; i++)
				root.getChildren().remove(point[i]);
			root.getChildren().remove(agent);
		}
		length = newLength;
		gameOver = false;
		game = new Game(length);
		point = new Rectangle[length * length];
		switch (length) {
		case 4:
			rectX = 198;
			rectY = 547;
			xPlus = 156;
			yPlus = 157;
			rectWidth = 142;
			agent = new ImageView(new Image("images/4/caveman.png"));
			agent.relocate(223, 490);
			root.setStyle("-fx-background-image: url(\"images/4/background.jpg\");");
			break;
		case 5:
			rectX = 198;
			rectY = 577;
			xPlus = 125;
			yPlus = 125;
			rectWidth = 110;
			agent = new ImageView(new Image("images/5/caveman.png"));
			agent.relocate(219, 525);
			root.setStyle("-fx-background-image: url(\"images/5/background.jpg\");");
			break;
		case 6:
			rectX = 200;
			rectY = 596;
			xPlus = 103;
			yPlus = 104;
			rectWidth = 92;
			agent = new ImageView(new Image("images/6/caveman.png"));
			agent.relocate(215, 552);
			root.setStyle("-fx-background-image: url(\"images/6/background.jpg\");");
			break;
		case 7:
			rectX = 200;
			rectY = 605;
			xPlus = 88;
			yPlus = 88;
			rectWidth = 80;
			agent = new ImageView(new Image("images/7/caveman.png"));
			agent.relocate(215, 572);
			root.setStyle("-fx-background-image: url(\"images/7/background.jpg\");");
			break;
		case 8:
			rectX = 198;
			rectY = 621;
			xPlus = 77;
			yPlus = 78;
			rectWidth = 69;
			agent = new ImageView(new Image("images/8/caveman.png"));
			agent.relocate(210, 584);
			root.setStyle("-fx-background-image: url(\"images/8/background.jpg\");");
			break;
		}
		int count = 0;
		for (int x = rectX; x < 810; x += xPlus)
			for (int y = rectY; y > 0; y -= yPlus) {
				point[count] = new Rectangle(rectWidth, rectWidth);
				point[count].relocate(x, y);
				point[count].setStyle("-fx-fill: transparent;");
				count++;
			}
		point[0].setStyle("-fx-fill: url(\"images/door.jpg\");");
		for (int i = 0; i < length * length; i++)
			root.getChildren().add(point[i]);
		root.getChildren().add(agent);

		if (th != null)
			th.stop();
		th = new Thread(new Runnable() {

			@Override
			public void run() {

				ArrayList<Game.State> stateList = new ArrayList<>();
				ArrayList<Point> pointList = new ArrayList<>();
				ArrayList<GameSolver.Move> list = GameSolver.solve(game, stateList, pointList);

				for (GameSolver.Move i : list) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					switch (i) {
					case UP:
						agent.setLayoutY(agent.getLayoutY() - yPlus);
						getGameInfo(stateList.remove(0), pointList.remove(0));
						break;
					case DOWN:
						agent.setLayoutY(agent.getLayoutY() + yPlus);
						getGameInfo(stateList.remove(0), pointList.remove(0));
						break;
					case LEFT:
						agent.setLayoutX(agent.getLayoutX() - xPlus);
						getGameInfo(stateList.remove(0), pointList.remove(0));
						break;
					case RIGHT:
						agent.setLayoutX(agent.getLayoutX() + xPlus);
						getGameInfo(stateList.remove(0), pointList.remove(0));
						break;
					}

				}
				solver.getStyleClass().remove("stop");
				solver.getStyleClass().add("play");
				bSolver = false;
			}
		});
	}

}

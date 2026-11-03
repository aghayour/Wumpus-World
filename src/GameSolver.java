import java.awt.Point;
import java.util.ArrayList;

public class GameSolver {

	public enum Move {
		UP, DOWN, LEFT, RIGHT
	}

	private static boolean[][] wampus;
	private static boolean[][] pit;
	private static ArrayList<Move> list;

	public static ArrayList<Move> solve(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList) {
		list = new ArrayList<>();
		int length = game.getLength();
		//int tempi;
		//boolean upmost = false;

		wampus = new boolean[length][length];
		pit = new boolean[length][length];

		for (int m = 0; m < length; m++)
			for (int n = 0; n < length; n++)
				wampus[m][n] = pit[m][n] = true;

		wampus[0][1] = wampus[1][0] = pit[0][1] = pit[1][0] = false;

		int finish = 0;
		do {
			for (int i = game.getAgentX(); i < length; i++) {
				int j;
				if (i % 2 == 0)
					for (j = game.getAgentY(); j < length - 1; j++) {
						if (game.isGameOver())
							return list;
						if (!goRight(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}
				else
					for (j = game.getAgentY(); j > 0; j--) {
						if (game.isGameOver())
							return list;
						if (!goLeft(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}

				if (game.isGameOver())
					return list;

				try {
					if (i != length) {
						int gup = 1;
						while (!goUp(game, stateList, pointList)) {
							backskip(game, stateList, pointList, gup);
							gup += 2;
						}
					}
				} catch (Exception e) {
				}
			}

			for (int i = game.getAgentX(); i >= 0; i--) {
				int j;
				if (i % 2 == 0)
					for (j = game.getAgentY(); j > 0; j--) {
						if (game.isGameOver())
							return list;
						if (!goLeft(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}
				else
					for (j = game.getAgentY(); j < length - 1; j++) {
						if (game.isGameOver())
							return list;
						if (!goRight(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}

				if (game.isGameOver())
					return list;

				try {
					if (i != length) {
						int gup = 1;
						while (!goDown(game, stateList, pointList)) {
							backskip(game, stateList, pointList, gup);
							gup += 2;
						}
					}
				} catch (Exception e) {
				}
			}

			for (int i = game.getAgentY(); i < length; i++) {
				int j;
				if (i % 2 == 0)
					for (j = game.getAgentX(); j < length - 1; j++) {
						if (game.isGameOver())
							return list;
						if (!goUp(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}
				else
					for (j = game.getAgentX(); j > 0; j--) {
						if (game.isGameOver())
							return list;
						if (!goDown(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}

				if (game.isGameOver())
					return list;

				try {
					if (i != length) {
						int gup = 1;
						while (!goRight(game, stateList, pointList)) {
							backskip(game, stateList, pointList, gup);
							gup += 2;
						}
					}
				} catch (Exception e) {
				}
			}

			for (int i = game.getAgentY(); i > 0; i--) {
				int j;
				if (i % 2 == 0)
					for (j = game.getAgentX(); j > 0; j--) {
						if (game.isGameOver())
							return list;
						if (!goDown(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}
				else
					for (j = game.getAgentX(); j < length - 1; j++) {
						if (game.isGameOver())
							return list;
						if (!goUp(game, stateList, pointList)) {
							backskip(game, stateList, pointList, 1);
							// i = length;
							break;
						}
					}

				if (game.isGameOver())
					return list;

				try {
					if (i != length) {
						int gup = 1;
						while (!goLeft(game, stateList, pointList)) {
							backskip(game, stateList, pointList, gup);
							gup += 2;
						}
					}
				} catch (Exception e) {
				}
			}

			finish++;
		} while (finish < 2);
		/*
		 * if (game.isGameOver()) return list;
		 */

		// backskip(game, stateList, pointList, 1);

		return list;
	}

	private static boolean goUp(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList) {
		int i = game.getAgentX();
		int j = game.getAgentY();
		if (!wampus[i + 1][j] && !pit[i + 1][j]) {
			game.moveAgentToUp();
			list.add(Move.UP);
			addInfo(game, stateList, pointList);
			return true;
		} else {
			return false;
		}
	}

	private static boolean goDown(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList) {
		int i = game.getAgentX();
		int j = game.getAgentY();
		if (!wampus[i - 1][j] && !pit[i - 1][j]) {
			game.moveAgentToDown();
			list.add(Move.DOWN);
			addInfo(game, stateList, pointList);
			return true;
		} else {
			return false;
		}
	}

	private static boolean goLeft(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList) {
		int i = game.getAgentX();
		int j = game.getAgentY();
		if (!wampus[i][j - 1] && !pit[i][j - 1]) {
			game.moveAgentToLeft();
			list.add(Move.LEFT);
			addInfo(game, stateList, pointList);
			return true;
		} else {
			return false;
		}
	}

	private static boolean goRight(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList) {
		int i = game.getAgentX();
		int j = game.getAgentY();
		try {
			if (!wampus[i][j + 1] && !pit[i][j + 1]) {
				game.moveAgentToRight();
				list.add(Move.RIGHT);
				addInfo(game, stateList, pointList);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*private static void back(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList, int n) {
		n *= 2;
		for (int i = 1; i <= n; i += 2) {
			switch (list.get(list.size() - i)) {
			case UP:
				game.moveAgentToDown();
				list.add(Move.DOWN);
				break;
			case DOWN:
				game.moveAgentToUp();
				list.add(Move.UP);
				break;
			case LEFT:
				game.moveAgentToRight();
				list.add(Move.RIGHT);
				break;
			case RIGHT:
				game.moveAgentToLeft();
				list.add(Move.LEFT);
				break;
			}
			stateList.add(game.getState());
			pointList.add(new Point(game.getAgentX(), game.getAgentY()));
		}
	}*/

	private static void backskip(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList, int n) {
		switch (list.get(list.size() - n)) {
		case UP:
			game.moveAgentToDown();
			list.add(Move.DOWN);
			break;
		case DOWN:
			game.moveAgentToUp();
			list.add(Move.UP);
			break;
		case LEFT:
			game.moveAgentToRight();
			list.add(Move.RIGHT);
			break;
		case RIGHT:
			game.moveAgentToLeft();
			list.add(Move.LEFT);
			break;
		}
		stateList.add(game.getState());
		pointList.add(new Point(game.getAgentX(), game.getAgentY()));
	}

	private static void addInfo(Game game, ArrayList<Game.State> stateList, ArrayList<Point> pointList) {
		stateList.add(game.getState());
		int i = game.getAgentX();
		int j = game.getAgentY();
		pointList.add(new Point(i, j));
		if (game.getState() == Game.State.BLANK) {
			try {
				pit[i][j + 1] = wampus[i][j + 1] = false;
			} catch (Exception e) {
			}
			try {
				pit[i][j - 1] = wampus[i][j - 1] = false;
			} catch (Exception e) {
			}
			try {
				pit[i + 1][j] = wampus[i + 1][j] = false;
			} catch (Exception e) {
			}
			try {
				pit[i - 1][j] = wampus[i - 1][j] = false;
			} catch (Exception e) {
			}
		} else if (game.getState() == Game.State.BREEZE) {
			try {
				wampus[i][j + 1] = false;
			} catch (Exception e) {
			}
			try {
				wampus[i][j - 1] = false;
			} catch (Exception e) {
			}
			try {
				wampus[i + 1][j] = false;
			} catch (Exception e) {
			}
			try {
				wampus[i - 1][j] = false;
			} catch (Exception e) {
			}
		} else if (game.getState() == Game.State.SMELL) {
			try {
				pit[i][j + 1] = false;
			} catch (Exception e) {
			}
			try {
				pit[i][j - 1] = false;
			} catch (Exception e) {
			}
			try {
				pit[i + 1][j] = false;
			} catch (Exception e) {
			}
			try {
				pit[i - 1][j] = false;
			} catch (Exception e) {
			}

			int length = game.getLength();
			for (int x = 0; x < length; x++)
				for (int y = 0; y < length; y++) {
					if ((x == i && (y == j + 1 || y == j - 1)) || (y == j && (x == i + 1 || x == i - 1)))
						continue;
					else
						wampus[x][y] = false;
				}

		}
	}

}

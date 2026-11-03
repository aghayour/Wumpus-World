import java.util.Random;

public class Game {

	public enum State {
		BLANK, GOLD, WAMPUS, PIT, SMELL, BREEZE, SB
	}

	private int length;
	private State[][] board;
	private boolean gameOver, win;
	private int agentX, agentY;

	// **************************************************************** Constructor
	public Game(int length) {
		this.length = length;
		board = new State[length][length];
		gameOver = false;
		win = false;
		agentX = 0;
		agentY = 0;
		create();
	}

	// **************************************************************** create
	private void create() {
		/////////////////////////////////////////////// Add BLANK
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				board[i][j] = State.BLANK;

		/////////////////////////////////////////////// Add PIT
		Random random = new Random();
		int p = random.nextInt(length);
		for (int i = 0; i < p; i++) {
			int x = random.nextInt(length);
			int y;
			if (x == 0)
				y = random.nextInt(length - 2) + 2;
			else if (x == 1)
				y = random.nextInt(length - 1) + 1;
			else
				y = random.nextInt(length);
			board[x][y] = State.PIT;
			try {
				if (board[x + 1][y] == State.BLANK)
					board[x + 1][y] = State.BREEZE;
			} catch (Exception e) {
			}
			try {
				if (board[x - 1][y] == State.BLANK)
					board[x - 1][y] = State.BREEZE;
			} catch (Exception e) {
			}
			try {
				if (board[x][y + 1] == State.BLANK)
					board[x][y + 1] = State.BREEZE;
			} catch (Exception e) {
			}
			try {
				if (board[x][y - 1] == State.BLANK)
					board[x][y - 1] = State.BREEZE;
			} catch (Exception e) {
			}
		}

		/////////////////////////////////////////////// Add WAMPUS
		int wx, wy;
		do {
			wx = random.nextInt(length);
			if (wx == 0)
				wy = random.nextInt(length - 2) + 2;
			else if (wx == 1)
				wy = random.nextInt(length - 1) + 1;
			else
				wy = random.nextInt(length);
		} while (board[wx][wy] == State.PIT);
		board[wx][wy] = State.WAMPUS;
		try {
			if (board[wx + 1][wy] == State.BLANK)
				board[wx + 1][wy] = State.SMELL;
			else if (board[wx + 1][wy] == State.BREEZE)
				board[wx + 1][wy] = State.SB;
		} catch (Exception e) {
		}
		try {
			if (board[wx - 1][wy] == State.BLANK)
				board[wx - 1][wy] = State.SMELL;
			else if (board[wx - 1][wy] == State.BREEZE)
				board[wx - 1][wy] = State.SB;
		} catch (Exception e) {
		}
		try {
			if (board[wx][wy + 1] == State.BLANK)
				board[wx][wy + 1] = State.SMELL;
			else if (board[wx][wy + 1] == State.BREEZE)
				board[wx][wy + 1] = State.SB;
		} catch (Exception e) {
		}
		try {
			if (board[wx][wy - 1] == State.BLANK)
				board[wx][wy - 1] = State.SMELL;
			else if (board[wx][wy - 1] == State.BREEZE)
				board[wx][wy - 1] = State.SB;
		} catch (Exception e) {
		}

		/////////////////////////////////////////////// Add GOLD
		int gx, gy;
		do {
			gx = random.nextInt(length);
			if (gx == 0)
				gy = random.nextInt(length - 2) + 2;
			else if (gx == 1)
				gy = random.nextInt(length - 1) + 1;
			else
				gy = random.nextInt(length);
		} while (board[gx][gy] == State.PIT || board[gx][gy] == State.WAMPUS);
		board[gx][gy] = State.GOLD;

		//////////////////////////////////////////////////////
		/*for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++)
				System.out.print(board[i][j] + "\t");
			System.out.println();
		}*/
		/*for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				board[i][j] = State.BLANK;*/
		
		//board[1][0]=State.SMELL;
		//board[1][1]=State.BREEZE;
		//board[1][2]=State.BREEZE;
		//board[1][3]=State.BREEZE;

	}

	// **************************************************************** move
	public void moveAgentToUp() {
		if (gameOver)
			throw new IllegalStateException("Game is over");
		if (agentX < length - 1)
			agentX++;
		gameOver();
	}

	public void moveAgentToDown() {
		if (gameOver)
			throw new IllegalStateException("Game is over");
		if (agentX > 0)
			agentX--;
		gameOver();
	}

	public void moveAgentToLeft() {
		if (gameOver)
			throw new IllegalStateException("Game is over");
		if (agentY > 0)
			agentY--;
		gameOver();
	}

	public void moveAgentToRight() {
		if (gameOver)
			throw new IllegalStateException("Game is over");
		if (agentY < length - 1)
			agentY++;
		gameOver();
	}

	// ************************************************************** check GameOver
	private void gameOver() {
		if (board[agentX][agentY] == State.PIT || board[agentX][agentY] == State.WAMPUS)
			gameOver = true;
		if (board[agentX][agentY] == State.GOLD) {
			gameOver = true;
			win = true;
		}
	}

	// **************************************************************** Getter
	public State getState() {
		return board[agentX][agentY];
	}

	public int getLength() {
		return length;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean isWin() {
		return win;
	}

	public int getAgentX() {
		return agentX;
	}

	public int getAgentY() {
		return agentY;
	}
}

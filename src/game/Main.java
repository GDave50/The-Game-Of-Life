package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import display.Display;

/**
 * Conway's Game of Life is a mathematical cellular automaton. As an
 * automaton, it is a 0-player game. The rules of the game are simple.
 * If a cell is alive and has exactly 2 or 3 neighbors, it continues
 * to live. Any more or less and it dies. If a cell is dead and has
 * exactly 3 neighbors it is brought to life.
 * 
 * @author Gage Davidson
 */
class Main {
	
	static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	static final int SCREEN_WIDTH = SCREEN_DIMENSION.width;
	static final int SCREEN_HEIGHT = SCREEN_DIMENSION.height;
	static final int FPS = 10;
	static final String TITLE = "The Game of Life";
	static final Color FOREGROUND_COLOR = Color.WHITE;
	
	static Game game;
	
	/**
	 * @param unusedArgs command line arguments (unused)
	 */
	public static void main(String[] unusedArgs) {
		game = new Game();
		
		Display display = new Display(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE, true, FPS) {
			
			private final Graphics g = super.getDrawGraphics();
			
			@Override
			public void draw() {
				game.draw(g);
			}
			
			@Override
			public void tick() {
				game.update();
			}
		};
		
		display.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_N: // pressing n makes a new game
					game = new Game();
					break;
				case KeyEvent.VK_ESCAPE: // pressing escape quits the application
					System.exit(0);
					break;
				}
			}
		});
		
		display.vanishCursor();
		display.run();
	}
}

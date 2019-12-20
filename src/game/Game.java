package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Handles game logic and drawing.
 * 
 * @author Gage Davidson
 */
class Game {
	
	// size of each cell in pixels
	private static final int CELL_SIZE = 20;
	
	// width of the game grid
	private static final int GRID_WIDTH = Main.SCREEN_WIDTH / CELL_SIZE;
	
	// height of the game grid
	private static final int GRID_HEIGHT = Main.SCREEN_HEIGHT / CELL_SIZE;
	
	// the grid of cells
	private boolean[][] grid;
	
	/**
	 * Initializes and randomizes the grid of cells.
	 */
	Game() {
		grid = new boolean[GRID_WIDTH][GRID_HEIGHT];
	
		// randomize the grid
		for (int i = 0; i < GRID_WIDTH; ++i)
			for (int j = 0; j < GRID_HEIGHT; ++j)
				grid[i][j] = Math.random() < .5;
	}
	
	/**
	 * Updates the game state.
	 */
	void update() {
		// construct the new grid of cells separate from the current one
		boolean[][] newGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];
		
		// iterate through all the cells
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				// get the number of alive neighbors
				int neighbors = getNumberOfNeighbors(i, j);
				
				if (grid[i][j]) {
					// if the cell is alive with exactly 2 or 3 neighbors, it
					// lives (else it dies)
					if (neighbors == 2 || neighbors == 3)
						newGrid[i][j] = true;
				} else {
					// if the cell is dead with exactly 3 neighbors, it is
					// reborn
					if (neighbors == 3)
						newGrid[i][j] = true;
				}
			}
		}
		
		grid = newGrid;
	}
	
	/**
	 * Draws the game.
	 * @param g Graphics to use
	 */
	void draw(Graphics g) {
		// iterate through each cell, drawing alive ones
		for (int i = 0; i < GRID_WIDTH; ++i)
			for (int j = 0; j < GRID_HEIGHT; ++j)
				if (grid[i][j])
					drawCell(g, i, j);
	}
	
	/**
	 * Draws a cell.
	 * @param g Graphics to use
	 * @param x x-coordinate of the cell
	 * @param y y-coordinate of the cell
	 */
	private void drawCell(Graphics g, int x, int y) {
		// draw the cell background
		g.setColor(Main.FOREGROUND_COLOR);
		g.fillOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		
		// draw the cell border
		g.setColor(Color.BLACK);
		g.drawOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}
	
	/**
	 * Determines if the coordinate is in bounds.
	 * @param x x-value
	 * @param y y-value
	 * @return true if (x,y) is in bounds
	 */
	private static boolean inBounds(int x, int y) {
		return x >= 0 && x < GRID_WIDTH &&
				y >= 0 && y < GRID_HEIGHT;
	}
	
	/**
	 * Determines the number of live neighbors for a given cell.
	 * @param x x-value of cell
	 * @param y y-value of cell
	 * @return number of neighbors cell (x,y) has
	 */
	private int getNumberOfNeighbors(int x, int y) {
		int neighbors = 0;
		
		// iterate through all 8 neighbors
		for (int i = x - 1; i <= x + 1; ++i) {
			for (int j = y - 1; j <= y + 1; ++j) {
				// ignore cell positions that are out of bounds
				if (! inBounds(i, j))
					continue;
				
				// ignore this cell (it's not its own neighbor)
				if (i == x && j == y)
					continue;
				
				if (grid[i][j])
					++neighbors;
			}
		}
		
		return neighbors;
	}
}

/*******************************************************************************
 * 	ESACEL, a tool for editing and simulating cellular automatons.
 *     Copyright (C) 2017 Madson Paulo Alexandre da Silva
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *     
 *     Contact the project creator, Madson Paulo Alexandre da Silva by the e-mail 
 *     madson-paulo@hotmail.com.
 *******************************************************************************/

package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Uses Graphics2D to draw the grid
 * 
 * @author Madson
 *
 */
public class DrawSquare extends JPanel {
	private static final long serialVersionUID = 1L;
	// rectangles that represent the cells
	private List<Rectangle> squares = new ArrayList<Rectangle>();
	// color of the rectangles that represents the cell's states
	private List<Color> colors = new ArrayList<Color>();
	// lenght of the draw
	private int prefSize;

	/**
	 * Default constructor
	 * 
	 * @param size
	 *            size of the vector * size of each rectangle
	 */
	public DrawSquare(int size) {
		prefSize = size;
		setPrefSize(size);
	}

	/**
	 * adds a square to be drawn
	 * 
	 * Author: Madson
	 * 
	 * @param x
	 * @param y
	 * @param size
	 *            lenght of the square
	 * @param color
	 *            color of the square
	 */
	public void addSquare(int x, int y, int size, Color color) {
		Rectangle rect = new Rectangle(x, y, size, size);
		squares.add(rect);
		colors.add(color);
	}

	/**
	 * Changes the lenght of the draw, used when the scale is changed
	 * 
	 * Author: Madson
	 * 
	 * @param value
	 */
	public void setPrefSize(int value) {
		this.prefSize = value;
		this.setSize(new Dimension(prefSize, prefSize));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(prefSize, prefSize);
	}

	/**
	 * updates an specified square
	 * 
	 * Author: Madson
	 * 
	 * @param pos
	 *            position of the square in the squares ArrayList
	 * @param color
	 *            new color of the rectangle
	 */
	public void updatePosition(int pos, Color color) {
		colors.set(pos, color);
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.setColor(color);
		g2.fill(squares.get(pos));
		g2.setColor(Color.BLACK);
		g2.draw(squares.get(pos));
		g2.dispose();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// paint all the rectangles
		for (int i = 0; i < squares.size(); i++) {
			g2.setColor(colors.get(i));
			g2.fill(squares.get(i));
			g2.setColor(Color.BLACK);
			g2.draw(squares.get(i));
		}
		g2.dispose();
	}

	/**
	 * Erases the Lists of rectangles and colors
	 * 
	 * Author: Madson
	 */
	public void cleanSquare() {
		squares.clear();
		colors.clear();
	}

}
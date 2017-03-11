package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DrawSquare extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int PREF_W = 400;
	private static final int PREF_H = PREF_W;
	private List<Rectangle> squares = new ArrayList<Rectangle>();
	private List<Color> colors = new ArrayList<Color>();

	public void addSquare(int x, int y, int width, int height, Color color) {
		Rectangle rect = new Rectangle(x, y, width, height);
		squares.add(rect);
		colors.add(color);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (int i = 0; i < squares.size(); i++) {
			g2.setColor(colors.get(i));
			g2.fill(squares.get(i));
			g2.setColor(Color.BLACK);
			g2.draw(squares.get(i));
		}

		g2.dispose();
		squares.clear();
		colors.clear();
	}
}

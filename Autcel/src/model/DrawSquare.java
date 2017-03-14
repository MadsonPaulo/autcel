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
	private List<Rectangle> squares = new ArrayList<Rectangle>();
	private List<Color> colors = new ArrayList<Color>();
	private int prefSize;;
	
	public DrawSquare(int size){
		prefSize = size;
		setPrefSize(size);
	}

	public void addSquare(int x, int y, int size, Color color) {
		Rectangle rect = new Rectangle(x, y, size, size);
		squares.add(rect);
		colors.add(color);
	}

	public void setPrefSize(int value) {
		this.prefSize = value;
		this.setSize(new Dimension(prefSize, prefSize));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(prefSize, prefSize);
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
	}

	public void cleanSquare() {
		squares.clear();
		colors.clear();
	}

}
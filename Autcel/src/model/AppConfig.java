package model;

import java.awt.Color;
import java.awt.Font;

public class AppConfig {

	public Color color1;
	public Color color2;
	public Color color3;
	public Color color4;
	public Color color5;
	public Color color6;
	public Color color7;
	public Color color8;
	public Font buttonFont;
	public Font labelFont;
	public int tamVector;
	public int maxSavedVectors;
	public int vector[][];
	public int vectorSaver[][][];
	public int regras[][];
	public int regrasJogoDaVida[][];
	public int regrasCristaisDeUlam[][];
	public int activeStates;
	public int cicloAtual;
	public String nameState1;
	public String nameState2;
	public String nameState3;
	public String nameState4;
	public String nameState5;
	public String nameState6;
	public String nameState7;
	public String nameState8;
	public int lastSelected;

	public AppConfig() {
		super();
		this.color1 = Color.WHITE;
		this.color2 = Color.GRAY;
		this.color3 = Color.BLUE;
		this.color4 = Color.RED;
		this.color5 = Color.YELLOW;
		this.color6 = Color.CYAN;
		this.color7 = Color.MAGENTA;
		this.color8 = Color.LIGHT_GRAY;
		this.buttonFont = new Font("Tahoma", Font.BOLD, 11);
		this.labelFont = new Font("Tahoma", Font.PLAIN, 11);
		this.tamVector = 20;
		this.maxSavedVectors = 500;
		this.vector = new int[tamVector][tamVector];
		this.vectorSaver = new int[tamVector][tamVector][maxSavedVectors];
		this.activeStates = 2;
		this.cicloAtual = 0;
		this.nameState1 = "Estado 01";
		this.nameState2 = "Estado 02";
		this.nameState3 = "Estado 03";
		this.nameState4 = "Estado 04";
		this.nameState5 = "Estado 05";
		this.nameState6 = "Estado 06";
		this.nameState7 = "Estado 07";
		this.nameState8 = "Estado 08";
		this.regras = new int[18][6];
		this.lastSelected = 0;

		this.regrasJogoDaVida = new int[][] { { 1, 0, 2, 2, 1, 1 }, { 1, 1, 3, 1, 1, 0 }, { 1, 1, 2, 1, 1, 1 },
				{ 1, 1, 2, 2, 1, 1 }, { 1, 1, 1, 2, 1, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };

		this.regrasCristaisDeUlam = new int[][] { { 1, 0, 2, 0, 1, 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };
	}

}

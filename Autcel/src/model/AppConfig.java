/*******************************************************************************
 * 	Autcel, a tool for editing and simulating cellular automatons.
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
import java.awt.Font;
import java.util.ArrayList;

/**
 * Guarda configura��es b�sicas
 * 
 * @author Madson
 *
 */
public class AppConfig {

	public Color color1;
	public Color color2;
	public Color color3;
	public Color color4;
	public Color color5;
	public Color color6;
	public Color color7;
	public Color color8;
	public Font boldFont;
	public Font normalFont;
	public ArrayList<int[][]> vectorSaver;
	public ArrayList<int[]> regras;
	public ArrayList<int[]> regrasJogoDaVida;
	public ArrayList<int[]> regrasCristaisDeUlam;
	public ArrayList<int[]> regra614;
	public int vector[][];
	public int tamVector;
	public int activeStates;
	public int cicloAtual;
	public int maxStateNameSize;
	public int sqrSize;
	public int language;
	public int neighborwoodType;
	public int outerTotalistic;
	public String nameState1;
	public String nameState2;
	public String nameState3;
	public String nameState4;
	public String nameState5;
	public String nameState6;
	public String nameState7;
	public String nameState8;

	public AppConfig() {
		super();
		// cor dos estados
		this.color1 = Color.WHITE;
		this.color2 = Color.GRAY;
		this.color3 = Color.BLUE;
		this.color4 = Color.RED;
		this.color5 = Color.YELLOW;
		this.color6 = Color.CYAN;
		this.color7 = Color.MAGENTA;
		this.color8 = Color.LIGHT_GRAY;
		// fontes padr�o
		this.boldFont = new Font("Calibri", Font.BOLD, 14);
		this.normalFont = new Font("Calibri", Font.PLAIN, 14);
		// tamanho do vetor, grid
		this.tamVector = 160;
		this.vector = new int[tamVector][tamVector];
		// salva o grid a cada ciclo, permitindo voltar para ciclo anterior
		this.vectorSaver = new ArrayList<>();
		// por padr�o, inicia com 2 estados poss�veis
		this.activeStates = 2;
		this.cicloAtual = 0;
		// nome padr�o dos estados
		this.nameState1 = "State 01";
		this.nameState2 = "State 02";
		this.nameState3 = "State 03";
		this.nameState4 = "State 04";
		this.nameState5 = "State 05";
		this.nameState6 = "State 06";
		this.nameState7 = "State 07";
		this.nameState8 = "State 08";
		// guardar� as regras do aut�mato
		this.regras = new ArrayList<>();
		// tamanho m�ximo do nome de um estado
		this.maxStateNameSize = 12;
		// tipo de vizinhan�a
		this.neighborwoodType = 0;
		// � tipo de regra totalista exterior
		this.outerTotalistic = 0;
		// escala inicial padr�o
		this.sqrSize = 4;
		// idioma inicial padr�o
		this.language = 1;// 0: Portugu�s, 1: Ingl�s
		// regras do jogo da vida
		this.regrasJogoDaVida = new ArrayList<>();
		this.regrasJogoDaVida.add(new int[] { 0, 2, 1, 1, 3 });
		this.regrasJogoDaVida.add(new int[] { 1, 4, 0, 1, 2 });
		this.regrasJogoDaVida.add(new int[] { 1, 1, 0, 1, 3 });
		// regras de cristais de ulam
		this.regrasCristaisDeUlam = new ArrayList<>();
		this.regrasCristaisDeUlam.add(new int[] { 0, 2, 1, 1, 1 });
		/// regra 614
		this.regra614 = new ArrayList<>();
		this.regra614.add(new int[] { 0, 2, 1, 1, 1 });
		this.regra614.add(new int[] { 0, 2, 1, 1, 3 });
		this.regra614.add(new int[] { 0, 2, 1, 1, 5 });
		this.regra614.add(new int[] { 1, 2, 0, 1, 0 });
		this.regra614.add(new int[] { 1, 2, 0, 1, 2 });
		this.regra614.add(new int[] { 1, 2, 0, 1, 4 });
	}
}
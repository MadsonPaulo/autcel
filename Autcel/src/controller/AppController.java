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
package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.AppConfig;
import view.AppConfigAutomaton;

/**
 * @author Madson
 *
 */
public class AppController {

	private AppConfig config = new AppConfig();

	/**
	 * Inicializa a GUI
	 */
	public void startApplication() {
		AppConfigAutomaton view = new AppConfigAutomaton(this);
		view.setVisible(true);
	}

	// TODO estava implementando a passagem de ciclos
	/**
	 * Author: Madson
	 * 
	 * @param number
	 * @return cor do estado passado como argumento
	 */
	public Color getDefaultCollor(int number) {
		switch (number) {
		case 1:
			return config.color1;
		case 2:
			return config.color2;
		case 3:
			return config.color3;
		case 4:
			return config.color4;
		case 5:
			return config.color5;
		case 6:
			return config.color6;
		case 7:
			return config.color7;
		case 8:
			return config.color8;
		default:
			return config.color1;
		}
	}

	/**
	 * Author: Madson
	 * 
	 * @param frame
	 * @param mouseEvent
	 * @param estadosPossiveis
	 * 
	 *            Altera a cor de um componente, após um click, para a cor do
	 *            próximo estado possível
	 */
	public void setNextColor(JFrame frame, MouseEvent mouseEvent, int estadosPossiveis) {
		try {
			// Detecta o componente que está nas coordenadas do click
			Component comp = mouseEvent.getComponent().getComponentAt(mouseEvent.getX(), mouseEvent.getY());

			// Identifica a cor atual do componente
			Color corAtual = comp.getBackground();

			// Altera a cor do componente para a próxima possível
			comp.setBackground(getNextColor(corAtual, estadosPossiveis));
		} catch (Exception e) {
		}

	}

	/**
	 * Author: Madson
	 * 
	 * @param current
	 *            estado atual de um componente(célula)
	 * @param states
	 *            quantidade de estados possíveis
	 * @return cor do próximo estado possível, de acordo com quantos estados
	 *         forem possíveis
	 */
	public Color getNextColor(Color current, int states) {
		if (current == config.color1) {
			return config.color2;
		} else if (current == config.color2) {
			if (states <= 2) {
				return config.color1;
			} else {
				return config.color3;
			}
		} else if (current == config.color3) {
			if (states <= 3) {
				return config.color1;
			} else {
				return config.color4;
			}
		} else if (current == config.color4) {
			if (states <= 4) {
				return config.color1;
			} else {
				return config.color5;
			}
		} else if (current == config.color5) {
			if (states <= 5) {
				return config.color1;
			} else {
				return config.color6;
			}
		} else if (current == config.color6) {
			if (states <= 6) {
				return config.color1;
			} else {
				return config.color7;
			}
		} else if (current == config.color7) {
			if (states <= 7) {
				return config.color1;
			} else {
				return config.color8;
			}
		} else {
			return config.color1;
		}
	}

	/**
	 * Desenha um quadrado no JPanel representando uma célula.
	 * 
	 * @param state
	 *            estado da célula
	 * @param x
	 *            posição x da célula no JPanel
	 * @param y
	 *            posição y da célula no JPanel
	 */
	private void drawCube(int state, int x, int y, JPanel panel) {
		int[][] vector = config.vector;
		JLabel label = new JLabel("");
		label.setOpaque(true);
		label.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		label.setBounds(x, y, (panel.getBounds().width / vector.length), (panel.getBounds().width / vector.length));
		panel.setVisible(true);
		if (state == 0) {// Estado 1
			label.setBackground(config.color1);
		} else if ((state == 1)) {// Estado 2
			label.setBackground(config.color2);
		} else if ((state == 2)) {// Estado 3
			label.setBackground(config.color3);
		} else if ((state == 3)) {// Estado 4
			label.setBackground(config.color4);
		} else if ((state == 4)) {// Estado 5
			label.setBackground(config.color5);
		} else if ((state == 5)) {// Estado 6
			label.setBackground(config.color6);
		} else if ((state == 6)) {// Estado 7
			label.setBackground(config.color7);
		} else {// Estado 8
			label.setBackground(config.color8);
		}
		panel.add(label);
	}

	/**
	 * Remove todos os componentes do JPanel. Desenha a matriz no JPanel
	 */
	public void draw(JPanel panel) {
		panel.removeAll();
		int posX = 0;
		int posY = 0;
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				drawCube(config.vector[i][y], posX, posY, panel);
				posX += panel.getBounds().width / config.vector.length;
			}
			posX = 0;
			posY += panel.getBounds().width / config.vector.length;
		}
		panel.repaint();
	}

	/**
	 * Author: Madson
	 * 
	 * @param panel
	 * 
	 *            Remove todos os componentes do JPanel. Desenha a matriz no
	 *            JPanel
	 */
	public void fillPanel(JPanel panel) {
		panel.removeAll();
		int[][] vector = config.vector;
		int posX = 0;
		int posY = 0;
		for (int i = 0; i < vector.length; i++) {
			for (int y = 0; y < vector[i].length; y++) {
				drawCube(vector[i][y], posX, posY, panel);
				posX += panel.getBounds().width / vector.length;
			}
			posX = 0;
			posY += panel.getBounds().width / vector.length;
		}
		panel.repaint();
	}

	/**
	 * Percorre os componentes(JLabel) do JPanel e através da cor de cada
	 * componente identifica seu estado e grava o estado em posição equivalente
	 * na matriz
	 * 
	 * @return matriz
	 */
	public int[][] getVectorFromPanel(JPanel panel) {
		int vector[][] = config.vector;
		int count = 0;
		for (int i = 0; i < vector.length; i++) {
			for (int y = 0; y < vector[i].length; y++) {
				if (panel.getComponents()[count].getBackground() == config.color1) {
					vector[i][y] = 0;
				} else if (panel.getComponents()[count].getBackground() == config.color2) {
					vector[i][y] = 1;
				} else if (panel.getComponents()[count].getBackground() == config.color3) {
					vector[i][y] = 2;
				} else if (panel.getComponents()[count].getBackground() == config.color4) {
					vector[i][y] = 3;
				} else if (panel.getComponents()[count].getBackground() == config.color5) {
					vector[i][y] = 4;
				} else if (panel.getComponents()[count].getBackground() == config.color6) {
					vector[i][y] = 5;
				} else if (panel.getComponents()[count].getBackground() == config.color7) {
					vector[i][y] = 6;
				} else if (panel.getComponents()[count].getBackground() == config.color8) {
					vector[i][y] = 7;
				}
				count++;
			}
		}
		return vector;
	}

	/**
	 * @param vetor
	 *            matriz do autômato
	 * @return 'false' caso exista somente um estado ou existam estados além dos
	 *         possíveis, 'true' caso não hajam irregularidades
	 */
	public boolean areStatesValid(int[][] vetor) {
		boolean value = true;
		int state = 0;
		for (int i = 0; i < vetor.length; i++) {
			for (int y = 0; y < vetor[i].length; y++) {
				if ((vetor[i][y] + 1) > config.activeStates) {
					value = false;
				}
				if (vetor[i][y] > 0) {
					state = vetor[i][y];
				}
			}
		}
		if (state == 0) {
			value = false;
		}
		return value;
	}

	/**
	 * Avança um ciclo de acordo com as regras definidas
	 */
	public void nextCycle() {
		// copia a matriz original para fazer alterações
		int[][] tempVector = new int[config.vector.length][config.vector.length];
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				tempVector[i][y] = config.vector[i][y];
			}
		}

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// contadores de vizinhos com estado x
				int state1 = 0, state2 = 0, state3 = 0, state4 = 0, state5 = 0, state6 = 0, state7 = 0, state8 = 0;
				// recebe uma lista com o estado dos 8 vizinhos da célula
				int[] neighbors = getNeighborhood(i, y);
				// conta quantos vizinhos com estado x a célula têm
				for (int z = 0; z < neighbors.length; z++) {
					if (neighbors[z] == 0) {
						state1++;
					} else if (neighbors[z] == 1) {
						state2++;
					} else if (neighbors[z] == 2) {
						state3++;
					} else if (neighbors[z] == 3) {
						state4++;
					} else if (neighbors[z] == 4) {
						state5++;
					} else if (neighbors[z] == 5) {
						state6++;
					} else if (neighbors[z] == 6) {
						state7++;
					} else if (neighbors[z] == 7) {
						state8++;
					}
				}

				int[] vizinhos = new int[] { state1, state2, state3, state4, state5, state6, state7, state8 };

				/**
				 * Checa as regras ativas e as lançam sobre a célula atual até
				 * que alguma valide ou não hajam mais regras. As regras
				 * respeitam a prescedência de cima para baixo, ou seja, uma vez
				 * que uma regra valide, as outras abaixo não serão consideradas
				 * para uma célula x num ciclo y
				 */
				boolean validou = false;
				// config.regras[j][0] == ativo(1) ou inativo(0)
				// config.regras[j][1] == estado ao qual a regra se refere
				// config.regras[j][2] == operador, ">="(0), ">"(1), "=="(2),
				// "<"(3), "<="(4)
				// config.regras[j][3] == quantidade de vizinhos
				// config.regras[j][4] == estado do vizinho
				// config.regras[j][5] == novo estado

				for (int j = 0; j < config.regras.length; j++) {
					// prossegue validando as regras caso nenhuma tenha sido
					// validada para esta célula nesse ciclo
					if (validou == false && config.regras[j][0] == 1) {
						validou = passRule(config.vector[i][y], vizinhos, config.regras[j][1], config.regras[j][2],
								config.regras[j][3], config.regras[j][4]);
						if (validou) {
							tempVector[i][y] = config.regras[j][5];
						}
					}
				}
			}
		}
		// Grava a matriz atual em vectorSaver antes que ela seja
		// definitivamente alterada
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				config.vectorSaver[i][y][config.cicloAtual] = config.vector[i][y];
			}
		}
		// grava as alterações na matriz
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				config.vector[i][y] = tempVector[i][y];
			}
		}
		// avança 1 ciclo
		config.cicloAtual++;
	}

	/**
	 * carrega a matriz anteriormente salva do ciclo passado
	 */
	public void previousCycle() {
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				config.vector[i][y] = config.vectorSaver[i][y][config.cicloAtual - 1];
			}
		}
		// retorna 1 ciclo
		config.cicloAtual--;
	}

	/**
	 * Lê uma célula e através de seu estado e de um array com 8 posições
	 * contendo a quantidade de vizinhos de cada estado possível, verifica a
	 * regra através dos dados também passados em argumento
	 * 
	 * @param estadoAtual
	 *            estado da célula atual
	 * @param vizinhos
	 *            array contendo 8 inteiros representando a quantidade de
	 *            vizinhos de estado x, sendo a primeira posição a contagem de
	 *            vizinhos em estado 1, a segunda posição a contagem de vizinhos
	 *            em estado 2 e assim sucessivamente
	 * @param regraReferese
	 *            estado à qual a regra se refere
	 * @param regraVizinho
	 *            estado o qual a validação de quantidade de vizinhos irá
	 *            considerar
	 * @param regraOperador
	 *            operador lógico, recebendo valores e correspondentes em
	 *            parênteses como adiante: 0(>=), 1(>), 2(==), 3(<) e 4(<=)
	 * @param regraQuant
	 *            quantidade de vizinhos da célula atual de estado
	 *            correspondente ao determinado em tipo
	 * @return caso os argumentos validem a regra, retorna verdadeiro
	 */
	public boolean passRule(int estadoAtual, int[] vizinhos, int regraReferese, int regraOperador, int regraQuant,
			int regraVizinho) {
		// incrementa a quantidade da regra para adaptação ao valor real
		regraQuant++;
		// checa se a regra refere-se ao estado da célula atual
		if (estadoAtual == regraReferese) {
			switch (regraOperador) {
			case 0:
				if (vizinhos[regraVizinho - 1] >= regraQuant) {
					return true;
				}
				break;
			case 1:
				if (vizinhos[regraVizinho - 1] > regraQuant) {
					return true;
				}
				break;
			case 2:
				if (vizinhos[regraVizinho] == regraQuant) {
					return true;
				}
				break;
			case 3:
				if (vizinhos[regraVizinho - 1] < regraQuant) {
					return true;
				}
				break;
			case 4:
				if (vizinhos[regraVizinho - 1] <= regraQuant) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	/**
	 * Retorna um array contendo o estado dos 8 vizinhos de uma célula
	 * 
	 * @param x
	 *            correspondente à posição no array de vizinhos da posição X
	 * @param y
	 *            correspondente à posição no array de vizinhos da posição Y
	 * @return array com os estados dos 8 vizinhos
	 */
	public int[] getNeighborhood(int x, int y) {
		int neighborhood[] = new int[8];
		int[] mapX = { x - 1, x - 1, x - 1, x + 0, x + 0, x + 1, x + 1, x + 1 };
		int[] mapY = { y - 1, y + 0, y + 1, y - 1, y + 1, y - 1, y + 0, y + 1 };

		for (int i = 0; i < 8; i++) {
			try {
				neighborhood[i] = config.vector[mapX[i]][mapY[i]];
			} catch (Exception e) {
				// Acontece com células localizadas nas extremidades, quando não
				// há vizinho nesta posição
				neighborhood[i] = -1;
			}
		}
		return neighborhood;
	}

	public void copiarVetor(int[][] vetor1, int[][] vetor2) {
		for (int i = 0; i < vetor1.length; i++) {
			for (int y = 0; y < vetor1[i].length; y++) {
				vetor1[i][y] = vetor2[i][y];
			}
		}
	}

	public void resetVectorSaver() {
		config.vectorSaver = new int[config.tamVector][config.tamVector][config.maxSavedVectors];
	}

	public void setCiclo(int ciclo) {
		config.cicloAtual = ciclo;
	}

	public int getCiclo() {
		return config.cicloAtual;
	}

	public void setVector(int[][] newVector) {
		config.vector = newVector;
	}

	public int[][] getVector() {
		return config.vector;
	}

	public void printVector(int[][] vector) {
		for (int i = 0; i < vector.length; i++) {
			for (int y = 0; y < vector[i].length; y++) {
				if (y == 0) {
					System.out.print("{" + vector[i][y] + ", ");
				} else if (y == vector[i].length - 1) {
					System.out.print(vector[i][y] + "}");
				} else {
					System.out.print(vector[i][y] + ", ");
				}
			}
			System.out.println();
		}
	}

	public void setRegras(int[][] newVector) {
		config.regras = newVector;
	}

	public int[][] getRegras() {
		return config.regras;
	}

	public int[][] getRegrasJogoDaVida() {
		return config.regrasJogoDaVida;
	}

	public int[][] getRegrasCristaisDeUlam() {
		return config.regrasCristaisDeUlam;
	}

	public Font getLabelFont() {
		return config.labelFont;
	}

	public Font getButtonFont() {
		return config.buttonFont;
	}

	public int getActiveStates() {
		return config.activeStates;
	}

	public void setActiveStates(int ativos) {
		config.activeStates = ativos;
	}

	public void setTamVector(int tamanho) {
		config.tamVector = tamanho;
		config.vector = new int[tamanho][tamanho];
		config.vectorSaver = new int[tamanho][tamanho][config.maxSavedVectors];
	}

	public int getLastSelected() {
		return config.lastSelected;
	}

	public void setLastSelected(int selected) {
		config.lastSelected = selected;
	}

	public int getTamVector() {
		return config.tamVector;
	}

	public String getNameState1() {
		return config.nameState1;
	}

	public String getNameState2() {
		return config.nameState2;
	}

	public String getNameState3() {
		return config.nameState3;
	}

	public String getNameState4() {
		return config.nameState4;
	}

	public String getNameState5() {
		return config.nameState5;
	}

	public String getNameState6() {
		return config.nameState6;
	}

	public String getNameState7() {
		return config.nameState7;
	}

	public String getNameState8() {
		return config.nameState8;
	}

	public void setNameState1(String newName) {
		config.nameState1 = newName;
	}

	public void setNameState2(String newName) {
		config.nameState2 = newName;
	}

	public void setNameState3(String newName) {
		config.nameState3 = newName;
	}

	public void setNameState4(String newName) {
		config.nameState4 = newName;
	}

	public void setNameState5(String newName) {
		config.nameState5 = newName;
	}

	public void setNameState6(String newName) {
		config.nameState6 = newName;
	}

	public void setNameState7(String newName) {
		config.nameState7 = newName;
	}

	public void setNameState8(String newName) {
		config.nameState8 = newName;
	}

}

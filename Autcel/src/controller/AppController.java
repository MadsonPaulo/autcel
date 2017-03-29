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
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.AppConfig;
import model.DrawSquare;
import view.AppConfigAutomaton;
import view.Manual;

/**
 * Controller das 3 views, sendo sua única instância repassada entre as views
 * 
 * @author Madson
 *
 */
public class AppController {

	private AppConfig config = new AppConfig();

	/**
	 * Inicia a aplicação
	 * 
	 * Author: Madson
	 */
	public void startApplication(int size) {
		changeTamVector(size);
		AppConfigAutomaton view = new AppConfigAutomaton(this);
		view.setVisible(true);
	}

	/**
	 * Retorna a cor referente a um estado
	 * 
	 * Author: Madson
	 * 
	 * @param number
	 *            número referente ao estado de uma célula
	 * @return cor do estado passado como argumento
	 */
	public Color getColor(int number) {
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
	 * Retorna o valor do próximo estado possível, de acordo com a quantidade de
	 * estados possíveis
	 * 
	 * Author: Madson
	 * 
	 * @param current
	 *            estado atual
	 * @param states
	 *            quantidade de estados possíveis
	 * @return valor do próximo estado possível
	 */
	public int getNextStateValue(int current, int states) {
		if (current == 0) {
			return 1;
		} else if (current == 1) {
			if (states <= 2) {
				return 0;
			} else {
				return 2;
			}
		} else if (current == 2) {
			if (states <= 3) {
				return 0;
			} else {
				return 3;
			}
		} else if (current == 3) {
			if (states <= 4) {
				return 0;
			} else {
				return 4;
			}
		} else if (current == 4) {
			if (states <= 5) {
				return 0;
			} else {
				return 5;
			}
		} else if (current == 5) {
			if (states <= 6) {
				return 0;
			} else {
				return 6;
			}
		} else if (current == 6) {
			if (states <= 7) {
				return 0;
			} else {
				return 7;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Checa se todos os estados da matriz são estados possíveis
	 * 
	 * Author: Madson
	 * 
	 * @param vetor
	 *            matriz do autômato
	 * @return 'false' caso existam estados além dos possíveis, 'true' caso não
	 *         hajam irregularidades
	 */
	public boolean areStatesValid(int[][] vetor) {
		boolean value = true;
		for (int i = 0; i < vetor.length; i++) {
			for (int y = 0; y < vetor[i].length; y++) {
				// se uma célula está em estado que não é possível
				if ((vetor[i][y] + 1) > config.activeStates) {
					value = false;
				}
			}
		}
		return value;
	}

	/**
	 * Avança um ciclo de acordo com as regras definidas
	 * 
	 * Author: Madson
	 */
	public int[] nextCycle(DrawSquare square) {
		// quantidade de células em cada estado
		int[] states = { 0, 0, 0, 0, 0, 0, 0, 0 };
		// copia a matriz original para fazer alterações
		int[][] tempVector = new int[config.vector.length][config.vector.length];
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				tempVector[i][y] = config.vector[i][y];
			}
		}

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// array contendo a quantidade de vizinhos em cada estado
				int[] vizinhos = getNeighborhood(i, y);

				/**
				 * Checa as regras ativas e as lançam sobre a célula atual até
				 * que alguma valide ou não hajam mais regras. As regras
				 * respeitam a prescedência de cima para baixo, ou seja, uma vez
				 * que uma regra valide, as outras abaixo não serão consideradas
				 * para uma célula x num ciclo y
				 */
				boolean validou = false;
				/*
				 * [0] = estado ao qual a regra se refere
				 * 
				 * [1] = operador: ">="(0), ">"(1), "=="(2), "!="(3),"<"(4),
				 * "<="(5)
				 * 
				 * [2] = novo estado
				 * 
				 * [3] = estado do vizinho
				 * 
				 * [4] = quantidade de vizinhos
				 */
				for (int j = 0; j < config.regras.size(); j++) {
					// prossegue validando as regras caso nenhuma tenha sido
					// validada para esta célula nesse ciclo
					if (validou == false) {
						validou = passRule(config.vector[i][y], vizinhos, config.regras.get(j)[0],
								config.regras.get(j)[1], config.regras.get(j)[3], config.regras.get(j)[4]);
						// se validar, muda o estado da célula
						if (validou) {
							tempVector[i][y] = config.regras.get(j)[2];
						}
					}
				}
			}
		}

		// vetor temporário para guardar o ciclo atual
		int[][] newSave = new int[config.tamVector][config.tamVector];

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// grava o ciclo atual em newSave
				newSave[i][y] = config.vector[i][y];
				// grava as alterações na matriz final
				config.vector[i][y] = tempVector[i][y];
				// incrementa states
				if (tempVector[i][y] == 0) {
					states[0]++;
				} else if (tempVector[i][y] == 1) {
					states[1]++;
				} else if (tempVector[i][y] == 2) {
					states[2]++;
				} else if (tempVector[i][y] == 3) {
					states[3]++;
				} else if (tempVector[i][y] == 4) {
					states[4]++;
				} else if (tempVector[i][y] == 5) {
					states[5]++;
				} else if (tempVector[i][y] == 6) {
					states[6]++;
				} else if (tempVector[i][y] == 7) {
					states[7]++;
				}
			}
		}
		if ((config.cicloAtual + 1) > config.vectorSaver.size()) {
			// ciclo atual salvo em vectorSaver
			config.vectorSaver.add(newSave);
		}
		// avança 1 ciclo
		config.cicloAtual++;
		return states;
	}

	/**
	 * Retorna um ciclo
	 * 
	 * Author: Madson
	 */
	public int[] previousCycle() {
		// quantidade de células em cada estado
		int[] states = { 0, 0, 0, 0, 0, 0, 0, 0 };
		// carrega o valor salvo do ciclo anterior em vectorSaver na matriz
		// atual
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				config.vector[i][y] = config.vectorSaver.get(config.cicloAtual - 1)[i][y];
				// incrementa states
				if (config.vector[i][y] == 0) {
					states[0]++;
				} else if (config.vector[i][y] == 1) {
					states[1]++;
				} else if (config.vector[i][y] == 2) {
					states[2]++;
				} else if (config.vector[i][y] == 3) {
					states[3]++;
				} else if (config.vector[i][y] == 4) {
					states[4]++;
				} else if (config.vector[i][y] == 5) {
					states[5]++;
				} else if (config.vector[i][y] == 6) {
					states[6]++;
				} else if (config.vector[i][y] == 7) {
					states[7]++;
				}
			}
		}
		// retorna 1 ciclo
		config.cicloAtual--;
		return states;
	}

	public int[] countStates() {
		// quantidade de células em cada estado
		int[] states = { 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// incrementa states
				if (config.vector[i][y] == 0) {
					states[0]++;
				} else if (config.vector[i][y] == 1) {
					states[1]++;
				} else if (config.vector[i][y] == 2) {
					states[2]++;
				} else if (config.vector[i][y] == 3) {
					states[3]++;
				} else if (config.vector[i][y] == 4) {
					states[4]++;
				} else if (config.vector[i][y] == 5) {
					states[5]++;
				} else if (config.vector[i][y] == 6) {
					states[6]++;
				} else if (config.vector[i][y] == 7) {
					states[7]++;
				}
			}
		}
		return states;
	}

	/**
	 * Desenha o grid
	 * 
	 * Author: Madson
	 * 
	 * @param square
	 */
	public void drawMatriz(DrawSquare square) {
		// array com a cor dos 8 estados em ordem crescente
		Color[] configColors = getArrayOfCollors();
		// coordenadas dos quadrados a serem desenhados
		int posX = 0;
		int posY = 0;
		// tamanho do quadrado
		int size = getSqrSize();
		// limpa o array em DrawSquare
		square.cleanSquare();
		for (int i = 0; i < getTamVector(); i++) {
			for (int j = 0; j < getTamVector(); j++) {
				// adiciona um quadrado ao array de quadrados de DrawSquare
				square.addSquare(posX, posY, size, configColors[getVector()[i][j]]);
				posX += size;
			}
			posX = 0;
			posY += size;
		}
	}

	/**
	 * Troca a posição dos itens de um ArrayList
	 * 
	 * Author: Madson
	 * 
	 * @param index
	 *            posição a ser trocada
	 * @param newIndex
	 *            nova posição
	 * @param array
	 *            O ArrayList em questão
	 */
	public void swapAt(int index, int newIndex, ArrayList<int[]> array) {
		int[] temp = array.get(newIndex).clone();
		array.set(newIndex, array.get(index).clone());
		array.set(index, temp.clone());
	}

	/**
	 * Lê uma célula e através de seu estado e de um array com 8 posições
	 * contendo a quantidade de vizinhos de cada estado possível e verifica a
	 * regra
	 * 
	 * @param estadoAtual
	 *            estado da célula atual
	 * @param vizinhos
	 *            array contendo 8 inteiros representando a quantidade de
	 *            vizinhos de estado x, sendo a primeira posição a contagem de
	 *            vizinhos em estado 1, a segunda posição a contagem de vizinhos
	 *            em estado 2 e assim sucessivamente
	 * @param regraReferese
	 *            estado ao qual a regra se refere
	 * @param regraVizinho
	 *            estado ao qual a quantidade de vizinhos irá considerar
	 * @param regraOperador
	 *            operador relacional, de acordo com o seguinte dicionário:
	 *            0(>=), 1(>), 2(==), 3(!=), 4(<) e 5(<=)
	 * @param regraQuant
	 *            quantidade de vizinhos da célula atual de estado igual ao
	 *            determinado em regraVizinho
	 * @return caso os argumentos validem a regra, retorna verdadeiro
	 */
	public boolean passRule(int estadoAtual, int[] vizinhos, int regraReferese, int regraOperador, int regraVizinho,
			int regraQuant) {
		// checa se a regra refere-se ao estado da célula atual
		if (estadoAtual == regraReferese) {
			switch (regraOperador) {
			case 0:
				if (vizinhos[regraVizinho] >= regraQuant) {
					return true;
				}
				break;
			case 1:
				if (vizinhos[regraVizinho] > regraQuant) {
					return true;
				}
				break;
			case 2:
				if (vizinhos[regraVizinho] == regraQuant) {
					return true;
				}
				break;
			case 3:
				if (vizinhos[regraVizinho] != regraQuant) {
					return true;
				}
				break;
			case 4:
				if (vizinhos[regraVizinho] < regraQuant) {
					return true;
				}
				break;
			case 5:
				if (vizinhos[regraVizinho] <= regraQuant) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	/**
	 * Retorna um array contendo a quantidade de vizinhos em cada estado, da
	 * célula
	 * 
	 * Author: Madson
	 * 
	 * @param x
	 *            correspondente à posição no array de vizinhos da posição X
	 * @param y
	 *            correspondente à posição no array de vizinhos da posição Y
	 * @return array com a quantidade de vizinhos em cada estado
	 */
	public int[] getNeighborhood(int x, int y) {
		// array contendo a quantidade de vizinhos em cada estado
		int neighborhood[] = new int[8];
		// tamanho do array;
		ArrayList<Integer> mapX = new ArrayList<Integer>();
		ArrayList<Integer> mapY = new ArrayList<Integer>();
		// arrays das posições x e y em relação à célula
		if (config.outerTotalistic == 1) {
			if (config.neighborwoodType == 0) {
				mapX.addAll(Arrays.asList(x, x - 1, x - 1, x - 1, x + 0, x + 0, x + 1, x + 1, x + 1));
				mapY.addAll(Arrays.asList(y, y - 1, y + 0, y + 1, y - 1, y + 1, y - 1, y + 0, y + 1));
			} else if (config.neighborwoodType == 1) {
				mapX.addAll(Arrays.asList(x, x - 2, x - 2, x - 2, x - 2, x - 2, x - 1, x - 1, x - 1, x - 1, x - 1, x, x,
						x, x, x + 1, x + 1, x + 1, x + 1, x + 1, x + 2, x + 2, x + 2, x + 2, x + 2));
				mapY.addAll(Arrays.asList(y, y - 2, y - 1, y, y + 1, y + 2, y - 2, y - 1, y, y + 1, y + 2, y - 2, y - 1,
						y + 1, y + 2, y - 2, y - 1, y, y + 1, y + 2, y - 2, y - 1, y, y + 1, y + 2));
			} else if (config.neighborwoodType == 2) {
				mapX.addAll(Arrays.asList(x, x - 3, x - 3, x - 3, x - 3, x - 3, x - 3, x - 3, x - 2, x - 2, x - 2,
						x - 2, x - 2, x - 2, x - 2, x - 1, x - 1, x - 1, x - 1, x - 1, x - 1, x - 1, x, x, x, x, x, x,
						x + 1, x + 1, x + 1, x + 1, x + 1, x + 1, x + 1, x + 2, x + 2, x + 2, x + 2, x + 2, x + 2,
						x + 2, x + 3, x + 3, x + 3, x + 3, x + 3, x + 3, x + 3));
				mapY.addAll(Arrays.asList(y, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3, y - 3, y - 2, y - 1, y, y + 1,
						y + 2, y + 3, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3, y - 3, y - 2, y - 1, y + 1, y + 2,
						y + 3, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3,
						y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3));
			} else if (config.neighborwoodType == 3) {
				mapX.addAll(Arrays.asList(x, x - 1, x, x + 1, x));
				mapY.addAll(Arrays.asList(y, y, y + 1, y, y - 1));
			} else if (config.neighborwoodType == 4) {
				mapX.addAll(Arrays.asList(x, x - 1, x, x + 1, x, x - 1, x - 1, x + 1, x + 1, x - 2, x + 2, x, x));
				mapY.addAll(Arrays.asList(y, y, y + 1, y, y - 1, y - 1, y + 1, y - 1, y + 1, y, y, y - 2, y + 2));
			} else if (config.neighborwoodType == 5) {
				mapX.addAll(Arrays.asList(x, x - 1, x, x + 1, x, x - 1, x - 1, x + 1, x + 1, x - 2, x + 2, x, x, x - 3,
						x + 3, x, x, x - 1, x - 2, x - 2, x - 1, x + 1, x + 2, x + 2, x + 1));
				mapY.addAll(Arrays.asList(y, y, y + 1, y, y - 1, y - 1, y + 1, y - 1, y + 1, y, y, y - 2, y + 2, y, y,
						y - 3, y + 3, y - 2, y - 1, y + 1, y + 2, y - 2, y - 1, y + 1, y + 2));
			}
		} else {
			if (config.neighborwoodType == 0) {
				mapX.addAll(Arrays.asList(x - 1, x - 1, x - 1, x + 0, x + 0, x + 1, x + 1, x + 1));
				mapY.addAll(Arrays.asList(y - 1, y + 0, y + 1, y - 1, y + 1, y - 1, y + 0, y + 1));
			} else if (config.neighborwoodType == 1) {
				mapX.addAll(Arrays.asList(x - 2, x - 2, x - 2, x - 2, x - 2, x - 1, x - 1, x - 1, x - 1, x - 1, x, x, x,
						x, x + 1, x + 1, x + 1, x + 1, x + 1, x + 2, x + 2, x + 2, x + 2, x + 2));
				mapY.addAll(Arrays.asList(y - 2, y - 1, y, y + 1, y + 2, y - 2, y - 1, y, y + 1, y + 2, y - 2, y - 1,
						y + 1, y + 2, y - 2, y - 1, y, y + 1, y + 2, y - 2, y - 1, y, y + 1, y + 2));
			} else if (config.neighborwoodType == 2) {
				mapX.addAll(Arrays.asList(x - 3, x - 3, x - 3, x - 3, x - 3, x - 3, x - 3, x - 2, x - 2, x - 2, x - 2,
						x - 2, x - 2, x - 2, x - 1, x - 1, x - 1, x - 1, x - 1, x - 1, x - 1, x, x, x, x, x, x, x + 1,
						x + 1, x + 1, x + 1, x + 1, x + 1, x + 1, x + 2, x + 2, x + 2, x + 2, x + 2, x + 2, x + 2,
						x + 3, x + 3, x + 3, x + 3, x + 3, x + 3, x + 3));
				mapY.addAll(Arrays.asList(y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3, y - 3, y - 2, y - 1, y, y + 1,
						y + 2, y + 3, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3, y - 3, y - 2, y - 1, y + 1, y + 2,
						y + 3, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3, y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3,
						y - 3, y - 2, y - 1, y, y + 1, y + 2, y + 3));
			} else if (config.neighborwoodType == 3) {
				mapX.addAll(Arrays.asList(x - 1, x, x + 1, x));
				mapY.addAll(Arrays.asList(y, y + 1, y, y - 1));
			} else if (config.neighborwoodType == 4) {
				mapX.addAll(Arrays.asList(x - 1, x, x + 1, x, x - 1, x - 1, x + 1, x + 1, x - 2, x + 2, x, x));
				mapY.addAll(Arrays.asList(y, y + 1, y, y - 1, y - 1, y + 1, y - 1, y + 1, y, y, y - 2, y + 2));
			} else if (config.neighborwoodType == 5) {
				mapX.addAll(Arrays.asList(x - 1, x, x + 1, x, x - 1, x - 1, x + 1, x + 1, x - 2, x + 2, x, x, x - 3,
						x + 3, x, x, x - 1, x - 2, x - 2, x - 1, x + 1, x + 2, x + 2, x + 1));
				mapY.addAll(Arrays.asList(y, y + 1, y, y - 1, y - 1, y + 1, y - 1, y + 1, y, y, y - 2, y + 2, y, y,
						y - 3, y + 3, y - 2, y - 1, y + 1, y + 2, y - 2, y - 1, y + 1, y + 2));
			}
		}
		// percorre a vizinhança
		for (int i = 0; i < mapX.size(); i++) {
			try {
				// as células da borda serão tratadas utilizando borda
				// periódica, código a seguir
				int pX = mapX.get(i);
				int pY = mapY.get(i);
				if (pX < 0) {
					pX = config.tamVector + pX;
				} else if (pX >= config.tamVector) {
					pX = pX - config.tamVector;
				}
				if (pY < 0) {
					pY = config.tamVector + pY;
				} else if (pY >= config.tamVector) {
					pY = pY - config.tamVector;
				}
				// identifica o estado da célula da vizinhança
				int neighborState = config.vector[pX][pY];
				// incrementa neighborhood de acordo com o estado do vizinho
				if (neighborState == 0) {
					neighborhood[0]++;
				} else if (neighborState == 1) {
					neighborhood[1]++;
				} else if (neighborState == 2) {
					neighborhood[2]++;
				} else if (neighborState == 3) {
					neighborhood[3]++;
				} else if (neighborState == 4) {
					neighborhood[4]++;
				} else if (neighborState == 5) {
					neighborhood[5]++;
				} else if (neighborState == 6) {
					neighborhood[6]++;
				} else if (neighborState == 7) {
					neighborhood[7]++;
				}
			} catch (Exception e) {
			}
		}
		return neighborhood;
	}

	/**
	 * Copia um vetor
	 * 
	 * Author: Madson
	 * 
	 * @param vetor1
	 *            vetor a receber a cópia
	 * @param vetor2
	 *            vetor a ser copiado
	 */
	public void copyVector(int[][] vetor1, int[][] vetor2) {
		for (int i = 0; i < vetor1.length; i++) {
			for (int y = 0; y < vetor1[i].length; y++) {
				vetor1[i][y] = vetor2[i][y];
			}
		}
	}

	public int[][] getInitialCycle() {
		return config.vectorSaver.get(0);
	}

	public void resetVectorSaver() {
		config.vectorSaver = new ArrayList<>();
	}

	public void resetVector() {
		config.vector = new int[config.tamVector][config.tamVector];
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

	public void setRegras(ArrayList<int[]> newRules) {
		config.regras = newRules;
	}

	public ArrayList<int[]> getRegras() {
		return config.regras;
	}

	public ArrayList<int[]> getRegrasJogoDaVida() {
		return config.regrasJogoDaVida;
	}

	public ArrayList<int[]> getRegrasCristaisDeUlam() {
		return config.regrasCristaisDeUlam;
	}

	public ArrayList<int[]> getRegra614() {
		return config.regra614;
	}

	public Font getNormalFont() {
		return config.normalFont;
	}

	public Font getBoldFont() {
		return config.boldFont;
	}

	public int getActiveStates() {
		return config.activeStates;
	}

	public void setActiveStates(int ativos) {
		config.activeStates = ativos;
	}

	/**
	 * JOptionPane a ser exibido ao clicar em "Sobre"
	 * 
	 * Author: Madson
	 */
	public void showAboutPopUp() {
		String about = "";
		String title = "";
		String version = "2.0";
		String date = "22/03/2017";
		ImageIcon icon = new ImageIcon(AppController.class.getResource("/img/main64x64.png"));
		if (config.language == 0) {// Português
			title = "Sobre o Autcel";
			about = "AUTCEL: Editor e Simulador de Autômatos Celulares" + "\nVersão: " + version + ". Publicado em: "
					+ date + "." + "\n\nAutor: Madson Paulo Alexandre da Silva.\nE-mail: madson-paulo@hotmail.com";
		} else if (config.language == 1) {// Inglês
			title = "About Autcel";
			about = "AUTCEL: Editor and Simulator of Cellular Automatons" + "\nVersion: " + version + ". Published in: "
					+ date + "." + "\n\nAuthor: Madson Paulo Alexandre da Silva.\nE-mail: madson-paulo@hotmail.com";
		}

		JOptionPane.showMessageDialog(null, about, title, JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Exibe o JFrame do manual de uso
	 * 
	 * Author: Madson
	 */
	public void showManualPopUp() {
		Manual manual = new Manual(config.language);
		manual.setVisible(true);
	}

	/**
	 * Carrega o modelo pronto de autômato celular
	 * 
	 * Author: Madson
	 * 
	 * @param value
	 * @return
	 */
	public boolean loadPrefab(int value) {
		String fileName = "/prefabs/Conway's Game of Life";
		if (value == 1) {
			fileName = "/prefabs/Ulam's Crystals";
		} else if (value == 2) {
			fileName = "/prefabs/Rule 614";
		} else if (value == 3) {
			fileName = "/prefabs/Tsunami";
		}

		try {
			// necessários para ler o arquivo
			InputStream in = this.getClass().getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			// arrayList que receberá todas as linhas do arquivo
			ArrayList<String> data = new ArrayList<String>();
			// linha a ser lida
			String line = reader.readLine();
			while (line != null) {
				// adiciona a linha ao arrayList
				data.add(line);
				// prepara a próxima linha
				line = reader.readLine();
			}
			// fecha-os
			reader.close();
			in.close();

			// se o arquivo não estiver corrompido, devido a modificações
			// manuais pelo usuário, importa seus dados
			if (checkData(data)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + "\n\n\n" + e.getMessage(), "Pah",
					JOptionPane.INFORMATION_MESSAGE, null);
			return false;
		}
	}

	/**
	 * Importa configurações de um arquivo externo
	 * 
	 * Author: Madson
	 * 
	 * @param textArea
	 *            terá seu texto alterado em caso de erro
	 * @return
	 */
	public boolean importData() {
		ImageIcon icon = new ImageIcon(AppController.class.getResource("/img/warning.png"));
		String[] messages = { "Aviso",
				"Você precisa aprovar o acesso aos arquivos do\nseu computador para importar um arquivo.",
				"Não foi possível importar as configurações.\nO arquivo está corrompido.",
				"Não foi possível localizar o arquivo." };
		if (config.language == 1) {
			messages[0] = "Warning";
			messages[1] = "You need to approve the access to the\nfiles of your computer to import a file.";
			messages[2] = "The settings could not be imported.\nThe file is corrupted.";
			messages[3] = "The file could not be located.";
		}
		// explorador de arquivos para definir o destino/arquivo
		JFileChooser abrindoArquivo = new JFileChooser();
		int resultado = abrindoArquivo.showOpenDialog(null);
		// se o usuário não aprovar o acesso aos arquivos, retorna falso
		if (resultado != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, messages[1], messages[0], JOptionPane.INFORMATION_MESSAGE, icon);
			return false;
		}
		// recebe a localização/arquivo selecionada
		File abrirArquivoEscolhido = abrindoArquivo.getSelectedFile();
		try {
			// necessários para ler o arquivo
			FileInputStream fis = new FileInputStream(abrirArquivoEscolhido);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			// arrayList que receberá todas as linhas do arquivo
			ArrayList<String> data = new ArrayList<String>();
			// linha a ser lida
			String line = reader.readLine();
			while (line != null) {
				// adiciona a linha ao arrayList
				data.add(line);
				// prepara a próxima linha
				line = reader.readLine();
			}
			// fecha-os
			reader.close();
			fis.close();

			// se o arquivo não estiver corrompido, devido a modificações
			// manuais pelo usuário, importa seus dados
			if (checkData(data)) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, messages[2], messages[0], JOptionPane.INFORMATION_MESSAGE, icon);
				return false;
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, messages[3], messages[0], JOptionPane.INFORMATION_MESSAGE, icon);
			ex.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checa se há alguma irregularidade no arquivo importado. Se não houver,
	 * importa os dados
	 * 
	 * Author: Madson
	 * 
	 * @param data
	 *            ArrayList com as linhas do arquivo importado
	 * @return
	 */
	private boolean checkData(ArrayList<String> data) {
		// valores temporários dos dados a serem importados
		// se todos os dados foram válidos, então importa-os
		int activeStates;
		int neighborwoodType;
		int outerTotalistic;
		ArrayList<int[]> regras = new ArrayList<int[]>();
		int tamVector;
		Color color1;
		Color color2;
		Color color3;
		Color color4;
		Color color5;
		Color color6;
		Color color7;
		Color color8;
		int language;
		String nameState1;
		String nameState2;
		String nameState3;
		String nameState4;
		String nameState5;
		String nameState6;
		String nameState7;
		String nameState8;
		int vector[][];

		try {
			// alguns dados
			activeStates = Integer.valueOf(data.get(3));
			if (activeStates < 2 || activeStates > 8) {
				return false;
			}
			neighborwoodType = Integer.valueOf(data.get(4));
			if (neighborwoodType < 0 || neighborwoodType > 5) {
				return false;
			}
			outerTotalistic = Integer.valueOf(data.get(5));
			if (outerTotalistic < 0 || outerTotalistic > 1) {
				return false;
			}

			// quantidade máxima de vizinhos nas regras
			int maxVizinhos = 0;
			if (neighborwoodType == 0) {
				maxVizinhos = 8;
			} else if (neighborwoodType == 1) {
				maxVizinhos = 24;
			} else if (neighborwoodType == 2) {
				maxVizinhos = 48;
			} else if (neighborwoodType == 3) {
				maxVizinhos = 4;
			} else if (neighborwoodType == 4) {
				maxVizinhos = 12;
			} else if (neighborwoodType == 5) {
				maxVizinhos = 24;
			}
			if (outerTotalistic == 1) {
				maxVizinhos++;
			}

			// REGRAS
			// posição da primeira regra
			int pos = 6;
			// enquanto o tamanho for igual a 5, são regras
			int size = data.get(pos).length();
			while (size == 5 || size == 6) {
				// garante que são inteiros e válidos
				int refere = Character.getNumericValue(data.get(pos).charAt(0));
				int operador = Character.getNumericValue(data.get(pos).charAt(1));
				int novo = Character.getNumericValue(data.get(pos).charAt(2));
				int vizinho = Character.getNumericValue(data.get(pos).charAt(3));
				int qnt = Character.getNumericValue(data.get(pos).charAt(4));
				if (size == 6) {
					qnt = Integer.valueOf(data.get(pos).substring(4, 6));
				}
				// verificações
				if (refere < 0 || refere > 7) {
					return false;
				}
				if (operador < 0 || operador > 5) {
					return false;
				}
				if (novo < 0 || novo > 7) {
					return false;
				}
				if (vizinho < 0 || vizinho > 7) {
					return false;
				}
				if (qnt < 0 || qnt > maxVizinhos) {
					return false;
				}

				// regra válida, adiciona ao array temporário
				regras.add(new int[] { refere, operador, novo, vizinho, qnt });
				pos++;
				size = data.get(pos).length();
			}

			// tamanho do vetor
			tamVector = Integer.valueOf(data.get(pos));
			if (tamVector == 20 || tamVector == 40 || tamVector == 80 || tamVector == 160 || tamVector == 320) {
				// se não for algum desses valores, é inválido
			} else {
				return false;
			}
			vector = new int[tamVector][tamVector];
			pos++;

			// CORES
			// é possível utilizar cores diferentes das 8 padrão, basta inserir
			// o código rgb correto no arquivo a ser importado
			color1 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color2 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color3 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color4 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color5 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color6 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color7 = new Color(Integer.valueOf(data.get(pos)));
			pos++;
			color8 = new Color(Integer.valueOf(data.get(pos)));
			pos++;

			// IDIOMA
			language = Integer.valueOf(data.get(pos));
			pos++;

			// NOME DOS ESTADOS
			// checa se algum é vazio ou de tamanho acima do permitido
			for (int i = 0; i < 8; i++) {
				if (data.get(pos + i).length() > config.maxStateNameSize || data.get(pos + i).isEmpty()
						|| data.get(pos + i).trim().length() == 0) {
					return false;
				}
			}
			// salva os nomes dos estados
			nameState1 = data.get(pos);
			pos++;
			nameState2 = data.get(pos);
			pos++;
			nameState3 = data.get(pos);
			pos++;
			nameState4 = data.get(pos);
			pos++;
			nameState5 = data.get(pos);
			pos++;
			nameState6 = data.get(pos);
			pos++;
			nameState7 = data.get(pos);
			pos++;
			nameState8 = data.get(pos);
			pos++;

			// VETOR
			// percorre as linhas do array
			for (int i = 0; i < tamVector; i++) {
				// percorre cada caractere do array
				for (int j = 0; j < tamVector; j++) {
					int num = Character.getNumericValue(data.get(pos).charAt(j));
					// checa se o estado é válido
					if (num < 0 || num > (activeStates - 1)) {
						return false;
					} else {
						vector[i][j] = num;
					}
				}
				pos++;
			}

			// IMPORTA OS DADOS
			config.activeStates = activeStates;
			config.neighborwoodType = neighborwoodType;
			config.outerTotalistic = outerTotalistic;
			config.regras.clear();
			config.regras.addAll(regras);
			config.tamVector = tamVector;
			config.vector = new int[tamVector][tamVector];
			config.color1 = color1;
			config.color2 = color2;
			config.color3 = color3;
			config.color4 = color4;
			config.color5 = color5;
			config.color6 = color6;
			config.color7 = color7;
			config.color8 = color8;
			config.language = language;
			config.nameState1 = nameState1;
			config.nameState2 = nameState2;
			config.nameState3 = nameState3;
			config.nameState4 = nameState4;
			config.nameState5 = nameState5;
			config.nameState6 = nameState6;
			config.nameState7 = nameState7;
			config.nameState8 = nameState8;
			for (int i = 0; i < tamVector; i++) {
				for (int j = 0; j < tamVector; j++) {
					config.vector[i][j] = vector[i][j];
				}
			}
			// reseta os dados não importados
			resetVectorSaver();
			config.cicloAtual = 0;
			config.sqrSize = new AppConfig().sqrSize;

			return true;
		} catch (Exception e) {
			// qualquer exceção que ocorrer no processo, invalida a importação
			return false;
		}
	}

	/**
	 * Exporta as configurações do autômato.
	 * 
	 * Author: Madson
	 */
	public boolean exportData() {
		// aviso para quem abrir o arquivo como de texto e editá-lo
		String warning = "Arquivo de configurações do Autcel.\nAVISO: alterações neste arquivo podem invalidá-lo.\n\n";
		if (config.language == 1) {
			warning = "Autcel settings file.\nNOTE: Changes in this file can invalidate it.\n\n";
		}
		// explorador de arquivos para definir o destino/arquivo
		JFileChooser salvandoArquivo = new JFileChooser();
		int resultado = salvandoArquivo.showSaveDialog(null);
		// se o usuário não aprovar o acesso aos arquivos, retorna falso
		if (resultado != JFileChooser.APPROVE_OPTION) {
			return false;
		}
		// recebe a localização/arquivo selecionada
		File salvarArquivoEscolhido = salvandoArquivo.getSelectedFile();
		try {
			// necessários para escrever no arquivo
			FileWriter arq = new FileWriter(salvarArquivoEscolhido, false);
			PrintWriter gravarArq = new PrintWriter(arq);
			// aviso e configurações
			String data = warning;
			data += config.activeStates + "\n";
			data += config.neighborwoodType + "\n";
			data += config.outerTotalistic + "\n";
			data += arrayListToString(config.regras) + "\n";
			data += config.tamVector + "\n";
			data += config.color1.getRGB() + "\n";
			data += config.color2.getRGB() + "\n";
			data += config.color3.getRGB() + "\n";
			data += config.color4.getRGB() + "\n";
			data += config.color5.getRGB() + "\n";
			data += config.color6.getRGB() + "\n";
			data += config.color7.getRGB() + "\n";
			data += config.color8.getRGB() + "\n";
			data += config.language + "\n";
			data += config.nameState1 + "\n";
			data += config.nameState2 + "\n";
			data += config.nameState3 + "\n";
			data += config.nameState4 + "\n";
			data += config.nameState5 + "\n";
			data += config.nameState6 + "\n";
			data += config.nameState7 + "\n";
			data += config.nameState8 + "\n";
			if (config.cicloAtual == 0) {
				data += arrayToString(config.vector);
			} else {
				data += arrayToString(config.vectorSaver.get(0));
			}

			// aviso e configurações são gravadas no arquivo
			gravarArq.printf(data);
			// fecha o arquivo
			arq.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Converte um ArrayList de array de inteiros em string
	 * 
	 * Author: Madson
	 * 
	 * @param ArrayList
	 *            a ser convertido
	 * @return string como todos os números em sequência e sem separadores
	 */
	private String arrayListToString(ArrayList<int[]> array) {
		String text = "";

		for (int i = 0; i < array.size(); i++) {
			for (int j = 0; j < array.get(0).length; j++) {
				text += array.get(i)[j];
			}
			if (i < array.size() - 1) {
				text += "\n";
			}
		}
		return text;
	}

	/**
	 * Converte um array de inteiros em string, com a finalidade de exportá-la
	 * 
	 * Author: Madson
	 * 
	 * @param array
	 *            a ser convertido
	 * @return string como todos os números em sequência e sem separadores
	 */
	private String arrayToString(int[][] array) {
		String text = "";

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				text += array[i][j];
			}
			if (i < array.length - 1) {
				text += "\n";
			}
		}
		return text;
	}

	/**
	 * Retorna um array com as 8 cores atuais
	 * 
	 * Author: Madson
	 * 
	 * @return
	 */
	public Color[] getArrayOfCollors() {
		return new Color[] { config.color1, config.color2, config.color3, config.color4, config.color5, config.color6,
				config.color7, config.color8 };
	}

	/**
	 * Altera uma cor de estado específica
	 * 
	 * Author: Madson
	 * 
	 * @param color
	 *            nova cor
	 * @param position
	 *            posição da nova cor
	 */
	public void setColor(Color color, int position) {
		if (position == 0) {
			config.color1 = color;
		} else if (position == 1) {
			config.color2 = color;
		} else if (position == 2) {
			config.color3 = color;
		} else if (position == 3) {
			config.color4 = color;
		} else if (position == 4) {
			config.color5 = color;
		} else if (position == 5) {
			config.color6 = color;
		} else if (position == 6) {
			config.color7 = color;
		} else if (position == 7) {
			config.color8 = color;
		}
	}

	/**
	 * Altera o tamanho do vetor
	 * 
	 * Author: Madson
	 * 
	 * @param size
	 */
	public void changeTamVector(int size) {
		int tamAtual = config.tamVector;
		// se o novo tamanho for igual ao atual, não faz nada
		if (size == tamAtual) {
			return;
		}
		// vetor temporário para copiar o que for possível do atual
		int[][] tempVector = new int[size][size];
		config.tamVector = size;
		// copia o quer for aproveitável do vetor atual
		if (size < tamAtual) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					tempVector[i][j] = config.vector[i][j];
				}
			}
		} else if (size > tamAtual) {
			for (int i = 0; i < tamAtual; i++) {
				for (int j = 0; j < tamAtual; j++) {
					tempVector[i][j] = config.vector[i][j];
				}
			}
		}
		// transfere do vetor temporário para o atual
		config.vector = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				config.vector[i][j] = tempVector[i][j];
			}
		}
		resetVectorSaver();
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

	public int getMaxStateNameSize() {
		return config.maxStateNameSize;
	}

	public int getSqrSize() {
		return config.sqrSize;
	}

	public void setSqrSize(int value) {
		config.sqrSize = value;
	}

	public int getLanguage() {
		return config.language;
	}

	public void setLanguage(int value) {
		config.language = value;
	}

	public void setOuterTotalistic(int value) {
		config.outerTotalistic = value;
	}

	public int getOuterTotalistic() {
		return config.outerTotalistic;
	}

	public void setNeighborwoodType(int value) {
		config.neighborwoodType = value;
	}

	public int getNeighborwoodType() {
		return config.neighborwoodType;
	}
}
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
 * Controller of the 3 views, beint it's only instance being passed between
 * views
 * 
 * @author Madson
 *
 */
public class AppController {

	private AppConfig config = new AppConfig();

	/**
	 * Starts the application
	 * 
	 * Author: Madson
	 * 
	 * @param size
	 *            defines the size of the vector
	 */
	public void startApplication(int size) {
		changeVectorSize(size);
		AppConfigAutomaton view = new AppConfigAutomaton(this);
		view.setVisible(true);
	}

	/**
	 * Returns the color of a state
	 * 
	 * Author: Madson
	 * 
	 * @param number
	 *            number of the state of a cell
	 * @return Color of the state
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
	 * Returns the value of the next possible state based of the number of
	 * possible states
	 * 
	 * Author: Madson
	 * 
	 * @param current
	 *            current state
	 * @param states
	 *            number of possible states
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
	 * Checks if all states in the matrix are possible states
	 * 
	 * Author: Madson
	 * 
	 * @param vetor
	 *            matrix of the automaton
	 * @return flase if there are invalid states, otherwise returns true
	 */
	public boolean areStatesValid(int[][] vetor) {
		boolean value = true;
		for (int i = 0; i < vetor.length; i++) {
			for (int y = 0; y < vetor[i].length; y++) {
				// if a cell is in a state that is not possible anymore
				if ((vetor[i][y] + 1) > config.activeStates) {
					value = false;
				}
			}
		}
		return value;
	}

	/**
	 * Advances a cycle
	 * 
	 * Author: Madson
	 * 
	 * @param square
	 *            DrawSquare used for the drawings
	 * @return array with the amount of cells in each state
	 */
	public int[] nextCycle(DrawSquare square) {
		// array with the amount of cells in each state
		int[] states = { 0, 0, 0, 0, 0, 0, 0, 0 };
		// copy of the original matrix to make changes
		int[][] tempVector = new int[config.vector.length][config.vector.length];
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				tempVector[i][y] = config.vector[i][y];
			}
		}
		// pass the rules for each cell
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// array with the number of neighboors in each state for the
				// current cell
				int[] neightboors = getNeighborhood(i, y);

				/**
				 * Checks the rules for the automaton in the current cell until
				 * any rule validates or all the rules are passed. The rules
				 * respect the precedence from top to botton, that means that
				 * when a rule is validated, all other are ignored for the
				 * current cell in the current cycle
				 */
				boolean validated = false;
				/*
				 * [0] = state which the rule refers to
				 * 
				 * [1] = operator: ">="(0), ">"(1), "=="(2), "!="(3), "<"(4),
				 * "<="(5)
				 * 
				 * [2] = new state
				 * 
				 * [3] = state of the neighboor
				 * 
				 * [4] = number of neighboors in the state passed on [3]
				 */
				for (int j = 0; j < config.rules.size(); j++) {
					// proceeds passing rules while none was validated
					if (validated == false) {
						validated = passRule(config.vector[i][y], neightboors, config.rules.get(j)[0],
								config.rules.get(j)[1], config.rules.get(j)[3], config.rules.get(j)[4]);
						// if validates, changes the state of the cell
						if (validated) {
							tempVector[i][y] = config.rules.get(j)[2];
						}
					}
				}
			}
		}

		// temp vector to save the current cycle
		int[][] newSave = new int[config.vectorSize][config.vectorSize];

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// saves the current cycle in newSave
				newSave[i][y] = config.vector[i][y];
				// saves the changes in the real matrix
				config.vector[i][y] = tempVector[i][y];
				// increments the number of cells in each state
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
		// this grants that only states that were not saved previously be saved,
		// in case of the user returns cycles and advance again
		if ((config.currentCycle + 1) > config.vectorSaver.size()) {
			// current cycle is saved in vectorSaver
			config.vectorSaver.add(newSave);
		}
		// increments the cycle counter
		config.currentCycle++;
		return states;
	}

	/**
	 * Returns to the previous cycle
	 * 
	 * Author: Madson
	 * 
	 * @return array with the amount of cells in each state
	 */
	public int[] previousCycle() {
		// array with the amount of cells in each state
		int[] states = { 0, 0, 0, 0, 0, 0, 0, 0 };
		// loads the previous cycle saved in vectorSaver
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				config.vector[i][y] = config.vectorSaver.get(config.currentCycle - 1)[i][y];
				// increments the number of cells in each state
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
		// decreases the cycle counter
		config.currentCycle--;
		return states;
	}

	/**
	 * Counts the number of cells in each state
	 * 
	 * Author: Madson
	 * 
	 * @return array with the amount of cells in each state
	 */
	public int[] countStates() {
		// array with the amount of cells in each state
		int[] states = { 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// increments the number of cells in each state
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
	 * Draws the grid
	 * 
	 * Author: Madson
	 * 
	 * @param square
	 *            DrawSquare that is handling the grid
	 */
	public void drawMatriz(DrawSquare square) {
		// array with the color of the 8 states in crescent order
		Color[] configColors = getArrayOfCollors();
		// coordinates of the squares to be drawn
		int posX = 0;
		int posY = 0;
		// size of the squares
		int size = getSqrSize();
		// clean the arrays of DrawSquare
		square.cleanSquare();
		for (int i = 0; i < getVectorSize(); i++) {
			for (int j = 0; j < getVectorSize(); j++) {
				// adds the square to the arrays of DrawSquare
				square.addSquare(posX, posY, size, configColors[getVector()[i][j]]);
				posX += size;
			}
			posX = 0;
			posY += size;
		}
	}

	/**
	 * Swap the index of 2 itens in an ArrayList
	 * 
	 * Author: Madson
	 * 
	 * @param index
	 *            position to be changed
	 * @param newIndex
	 *            new position
	 * @param array
	 *            the ArrayList
	 */
	public void swapAt(int index, int newIndex, ArrayList<int[]> array) {
		int[] temp = array.get(newIndex).clone();
		array.set(newIndex, array.get(index).clone());
		array.set(index, temp.clone());
	}

	/**
	 * 
	 * 
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
	/**
	 * Verifies if one cell validates a rule
	 * 
	 * Author: Madson
	 * 
	 * @param currentState
	 * @param neighbors
	 * @param refersTo
	 * @param operator
	 * @param ruleNeighbor
	 * @param amount
	 * @return
	 */
	public boolean passRule(int currentState, int[] neighbors, int refersTo, int operator, int ruleNeighbor,
			int amount) {
		// checks if the rule refers to the current state of the cell
		if (currentState == refersTo) {
			switch (operator) {
			case 0:
				if (neighbors[ruleNeighbor] >= amount) {
					return true;
				}
				break;
			case 1:
				if (neighbors[ruleNeighbor] > amount) {
					return true;
				}
				break;
			case 2:
				if (neighbors[ruleNeighbor] == amount) {
					return true;
				}
				break;
			case 3:
				if (neighbors[ruleNeighbor] != amount) {
					return true;
				}
				break;
			case 4:
				if (neighbors[ruleNeighbor] < amount) {
					return true;
				}
				break;
			case 5:
				if (neighbors[ruleNeighbor] <= amount) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	/**
	 * Returns an array with the amounts of neighbors in each state of the cell
	 * 
	 * Author: Madson
	 * 
	 * @param x
	 *            the x position of the cell
	 * @param y
	 *            the y position of the cell
	 * @return
	 */
	public int[] getNeighborhood(int x, int y) {
		// array with the amount of cells in each state
		int neighborhood[] = new int[8];
		// ArrayLists of the positions of the neighbors of the cell
		ArrayList<Integer> mapX = new ArrayList<Integer>();
		ArrayList<Integer> mapY = new ArrayList<Integer>();
		// populates the positions based on the neighborwood type and if it is
		// outer totalistic or not
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
		// walks the neightborwood
		for (int i = 0; i < mapX.size(); i++) {
			try {
				// the cells on the border are treated using periodic border
				int pX = mapX.get(i);
				int pY = mapY.get(i);
				if (pX < 0) {
					pX = config.vectorSize + pX;
				} else if (pX >= config.vectorSize) {
					pX = pX - config.vectorSize;
				}
				if (pY < 0) {
					pY = config.vectorSize + pY;
				} else if (pY >= config.vectorSize) {
					pY = pY - config.vectorSize;
				}
				// identifies the state of the cell in the neighborwood
				int neighborState = config.vector[pX][pY];
				// increments the number of cells in each state
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
	 * Copies a matrix
	 * 
	 * Author: Madson
	 * 
	 * @param vector1
	 *            vector to receive the copy
	 * @param vector2
	 *            vector to be copied
	 */
	public void copyVector(int[][] vector1, int[][] vector2) {
		for (int i = 0; i < vector1.length; i++) {
			for (int y = 0; y < vector1[i].length; y++) {
				vector1[i][y] = vector2[i][y];
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
		config.vector = new int[config.vectorSize][config.vectorSize];
	}

	public void setCycle(int cycle) {
		config.currentCycle = cycle;
	}

	public int getCycle() {
		return config.currentCycle;
	}

	public void setVector(int[][] newVector) {
		config.vector = newVector;
	}

	public int[][] getVector() {
		return config.vector;
	}

	public void setRules(ArrayList<int[]> newRules) {
		config.rules = newRules;
	}

	public ArrayList<int[]> getRules() {
		return config.rules;
	}

	public ArrayList<int[]> getGameOfLifeRules() {
		return config.rulesGameOfLife;
	}

	public ArrayList<int[]> getUlamCrystalsRules() {
		return config.rulesUlamCrystals;
	}

	public ArrayList<int[]> getRule614() {
		return config.rules614;
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
	 * JOptionPane to be shown when "About" is clicked
	 * 
	 * Author: Madson
	 */
	public void showAboutPopUp() {
		String about = "";
		String title = "";
		String version = "2.1";
		String date = "29/03/2017";
		ImageIcon icon = new ImageIcon(AppController.class.getResource("/img/main64x64.png"));
		if (config.language == 0) {// Portuguese
			title = "Sobre o ESACEL";
			about = "ESACEL: Editor e Simulador de Autômatos Celulares" + "\nVersão: " + version + ". Publicado em: "
					+ date + "." + "\n\nAutor: Madson Paulo Alexandre da Silva.\nE-mail: madson-paulo@hotmail.com";
		} else if (config.language == 1) {// English
			title = "About ESACEL";
			about = "ESACEL: Editor and Simulator of Cellular Automatons" + "\nVersion: " + version + ". Published in: "
					+ date + "." + "\n\nAuthor: Madson Paulo Alexandre da Silva.\nE-mail: madson-paulo@hotmail.com";
		}

		JOptionPane.showMessageDialog(null, about, title, JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * JFrame to be shown when "User Manual" is clicked
	 * 
	 * Author: Madson
	 */
	public void showManualPopUp() {
		Manual manual = new Manual(config.language);
		manual.setVisible(true);
	}

	/**
	 * Loads a ready-to-use model of cellular automaton
	 * 
	 * Author: Madson
	 * 
	 * @param value
	 *            identifier of the model
	 * @return true if successfully loads the model, false otherwise
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
			// needed to read the file
			InputStream in = this.getClass().getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			// ArrayList that will contain all the lines of the file
			ArrayList<String> data = new ArrayList<String>();
			// line to be read
			String line = reader.readLine();
			while (line != null) {
				// add the line to the ArrayList
				data.add(line);
				// readies the next line
				line = reader.readLine();
			}
			// close them
			reader.close();
			in.close();

			// if the file is not corrupted by manual modifications, import the
			// data
			if (checkData(data)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Import configurations from an external file
	 * 
	 * Author: Madson
	 * 
	 * @return true if successfully loads the model, false otherwise
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
		// file explorer to define the file
		JFileChooser fileOpener = new JFileChooser();
		int result = fileOpener.showOpenDialog(null);
		// if the user do not approve the access to the files of the system,
		// returns false
		if (result != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, messages[1], messages[0], JOptionPane.INFORMATION_MESSAGE, icon);
			return false;
		}
		// receives the location/file selected
		File openSelectedFile = fileOpener.getSelectedFile();
		try {
			// needed to read the file
			FileInputStream fis = new FileInputStream(openSelectedFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			// ArrayList that will receive all the lines of the file
			ArrayList<String> data = new ArrayList<String>();
			// line to be read
			String line = reader.readLine();
			while (line != null) {
				// add the line to the ArrayList
				data.add(line);
				// readies the next line
				line = reader.readLine();
			}
			// close them
			reader.close();
			fis.close();

			// if the file is not corrupted by manual modifications, import the
			// data
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
	 * Checks if there is any irregularity in a file to be imported. If there is
	 * not, imports it
	 * 
	 * Author: Madson
	 * 
	 * @param data
	 *            ArrayList with the lines of the file
	 * @return true if imported, false otherwise
	 */
	private boolean checkData(ArrayList<String> data) {
		// temp values of the data to be imported
		int activeStates;
		int neighborwoodType;
		int outerTotalistic;
		ArrayList<int[]> rules = new ArrayList<int[]>();
		int vectorSize;
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
			// some data
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

			// maximum amount of neighbors in the rules
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

			// RULES
			// position of the first rule
			int pos = 6;
			// while the size is 5 or 6, it's a rule
			int size = data.get(pos).length();
			while (size == 5 || size == 6) {
				// grants the they are integers and valid
				int refersTo = Character.getNumericValue(data.get(pos).charAt(0));
				int operator = Character.getNumericValue(data.get(pos).charAt(1));
				int newState = Character.getNumericValue(data.get(pos).charAt(2));
				int neighborState = Character.getNumericValue(data.get(pos).charAt(3));
				int amount = Character.getNumericValue(data.get(pos).charAt(4));
				if (size == 6) {
					amount = Integer.valueOf(data.get(pos).substring(4, 6));
				}
				// verifications
				if (refersTo < 0 || refersTo > 7) {
					return false;
				}
				if (operator < 0 || operator > 5) {
					return false;
				}
				if (newState < 0 || newState > 7) {
					return false;
				}
				if (neighborState < 0 || neighborState > 7) {
					return false;
				}
				if (amount < 0 || amount > maxVizinhos) {
					return false;
				}

				// rule is valid, adds to the temp array
				rules.add(new int[] { refersTo, operator, newState, neighborState, amount });
				pos++;
				size = data.get(pos).length();
			}

			// size of the vector
			vectorSize = Integer.valueOf(data.get(pos));
			if (vectorSize == 20 || vectorSize == 40 || vectorSize == 80 || vectorSize == 160 || vectorSize == 320) {
				// if it is not one of these sizes, its invalid
			} else {
				return false;
			}
			vector = new int[vectorSize][vectorSize];
			pos++;

			// COLORS
			// it is possible to use different colors than the 8 standard ones,
			// just need to inser the correct RGB code in the file to be
			// imported, in the right line
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

			// LANGUAGE
			language = Integer.valueOf(data.get(pos));
			pos++;

			// NAME OF THE STATES
			// checks if any name is empty or longer than the maximum size
			for (int i = 0; i < 8; i++) {
				if (data.get(pos + i).length() > config.maxStateNameSize || data.get(pos + i).isEmpty()
						|| data.get(pos + i).trim().length() == 0) {
					return false;
				}
			}
			// save the name of the states
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

			// VECTOR
			// walks the lines of the array
			for (int i = 0; i < vectorSize; i++) {
				// walks each character of the array
				for (int j = 0; j < vectorSize; j++) {
					int num = Character.getNumericValue(data.get(pos).charAt(j));
					// checks if the state is valid
					if (num < 0 || num > (activeStates - 1)) {
						return false;
					} else {
						vector[i][j] = num;
					}
				}
				pos++;
			}

			// IMPORT THE DATA
			config.activeStates = activeStates;
			config.neighborwoodType = neighborwoodType;
			config.outerTotalistic = outerTotalistic;
			config.rules.clear();
			config.rules.addAll(rules);
			config.vectorSize = vectorSize;
			config.vector = new int[vectorSize][vectorSize];
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
			for (int i = 0; i < vectorSize; i++) {
				for (int j = 0; j < vectorSize; j++) {
					config.vector[i][j] = vector[i][j];
				}
			}
			// reset the unimportable data
			resetVectorSaver();
			config.currentCycle = 0;
			config.sqrSize = new AppConfig().sqrSize;

			return true;
		} catch (Exception e) {
			// any exception that occours in the process invalidates the
			// importing
			return false;
		}
	}

	/**
	 * Export the automaton configurations
	 * 
	 * Author: Madson
	 * 
	 * @return true if exported, false if not
	 */
	public boolean exportData() {
		// warning to who open the exported file and tries to modify it
		String warning = "Arquivo de configurações do ESACEL.\nAVISO: alterações neste arquivo podem invalidá-lo.\n\n";
		if (config.language == 1) {
			warning = "ESACEL settings file.\nNOTE: Changes in this file can invalidate it.\n\n";
		}
		// file explorer to define the destination/file
		JFileChooser fileSaving = new JFileChooser();
		int result = fileSaving.showSaveDialog(null);
		// if the user does not approve the access to the file system, return
		// false
		if (result != JFileChooser.APPROVE_OPTION) {
			return false;
		}
		// gets the destination/file selected
		File saveSelectedFile = fileSaving.getSelectedFile();
		try {
			// needed to write in the file
			FileWriter fW = new FileWriter(saveSelectedFile, false);
			PrintWriter printWriter = new PrintWriter(fW);
			// warning and configurations
			String data = warning;
			data += config.activeStates + "\n";
			data += config.neighborwoodType + "\n";
			data += config.outerTotalistic + "\n";
			data += arrayListToString(config.rules) + "\n";
			data += config.vectorSize + "\n";
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
			if (config.currentCycle == 0) {
				data += arrayToString(config.vector);
			} else {
				data += arrayToString(config.vectorSaver.get(0));
			}
			// data is saved on the file
			printWriter.printf(data);
			// closed the file writer
			fW.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Converts an ArrayList of integers in a String
	 * 
	 * Author: Madson
	 * 
	 * @param ArrayList
	 *            ArrayList to be converted
	 * @return String with all the numbers of the ArrayList in sequence
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
	 * Converts an array of integers in a String
	 * 
	 * Author: Madson
	 * 
	 * @param array
	 *            array to be converted
	 * @return String with all the numbers of the array in sequence
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
	 * Returns an array with the 8 current colors of the states
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
	 * Changes the color of an specific state
	 * 
	 * Author: Madson
	 * 
	 * @param color
	 *            new color
	 * @param position
	 *            position of the new color
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
	 * Changes the size of the vector
	 * 
	 * Author: Madson
	 * 
	 * @param size
	 */
	public void changeVectorSize(int size) {
		int currentSize = config.vectorSize;
		// if the new size is iqual to the actual size, do nothing
		if (size == currentSize) {
			return;
		}
		// temp vector to copy as much as possible of the current vector
		int[][] tempVector = new int[size][size];
		config.vectorSize = size;
		// copies everything that is in range of the current vector
		if (size < currentSize) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					tempVector[i][j] = config.vector[i][j];
				}
			}
		} else if (size > currentSize) {
			for (int i = 0; i < currentSize; i++) {
				for (int j = 0; j < currentSize; j++) {
					tempVector[i][j] = config.vector[i][j];
				}
			}
		}
		// transfers from the temp vector to the current one
		config.vector = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				config.vector[i][j] = tempVector[i][j];
			}
		}
		resetVectorSaver();
	}

	public int getVectorSize() {
		return config.vectorSize;
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
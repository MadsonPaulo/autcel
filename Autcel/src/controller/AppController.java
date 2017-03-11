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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.AppConfig;
import view.AppConfigAutomaton;

/**
 * Controller das 3 views, sendo sua �nica inst�ncia repassada entre as views
 * 
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

	/**
	 * Retorna a cor referente a um estado
	 * 
	 * Author: Madson
	 * 
	 * @param number
	 *            n�mero referente ao estado de uma c�lula
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
	 * Retorna o valor do pr�ximo estado poss�vel, de acordo com a quantidade de estados poss�veis
	 * 
	 * Author: Madson
	 * @param current estado atual
	 * @param states quantidade de estados poss�veis
	 * @return valor do pr�ximo estado poss�vel
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
	 * Checa se todos os estados da matriz s�o estados poss�veis
	 * 
	 * Author: Madson
	 * 
	 * @param vetor
	 *            matriz do aut�mato
	 * @return 'false' caso exista somente um estado ou existam estados al�m dos
	 *         poss�veis, 'true' caso n�o hajam irregularidades
	 */
	public boolean areStatesValid(int[][] vetor) {
		boolean value = true;
		boolean twoStates = false;
		int state = vetor[0][0];
		for (int i = 0; i < vetor.length; i++) {
			for (int y = 0; y < vetor[i].length; y++) {
				// se uma c�lula est� em estado que n�o � poss�vel
				if ((vetor[i][y] + 1) > config.activeStates) {
					value = false;
				}
				// checa se existem pelo menos 2 estados diferentes
				if (twoStates == false && vetor[i][y] != state) {
					twoStates = true;
				}
			}
		}
		// se todas as c�lulas estiverem no mesmo estado, retorna falso
		if (twoStates == false) {
			value = false;
		}
		return value;
	}

	/**
	 * Avan�a um ciclo de acordo com as regras definidas
	 * 
	 * Author: Madson
	 */
	public void nextCycle() {
		// copia a matriz original para fazer altera��es
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
				 * Checa as regras ativas e as lan�am sobre a c�lula atual at�
				 * que alguma valide ou n�o hajam mais regras. As regras
				 * respeitam a presced�ncia de cima para baixo, ou seja, uma vez
				 * que uma regra valide, as outras abaixo n�o ser�o consideradas
				 * para uma c�lula x num ciclo y
				 */
				boolean validou = false;
				/*
				 * [0] = ativo(1) ou inativo(0)
				 * 
				 * [1] = estado ao qual a regra se refere
				 * 
				 * [2] = operador: ">="(0), ">"(1), "=="(2), "<"(3), "<="(4)
				 * 
				 * [3] = quantidade de vizinhos
				 * 
				 * [4] = estado do vizinho
				 * 
				 * [5] = novo estado
				 */
				for (int j = 0; j < config.regras.size(); j++) {
					// prossegue validando as regras caso nenhuma tenha sido
					// validada para esta c�lula nesse ciclo
					if (validou == false) {
						validou = passRule(config.vector[i][y], vizinhos, config.regras.get(j)[0],
								config.regras.get(j)[1], config.regras.get(j)[2], config.regras.get(j)[3]);
						// se validar, muda o estado da c�lula
						if (validou) {
							tempVector[i][y] = config.regras.get(j)[4];
						}
					}
				}
			}
		}

		// vetor tempor�rio para guardar o ciclo atual
		int[][] newSave = new int[config.tamVector][config.tamVector];

		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				// grava o ciclo atual em newSave
				newSave[i][y] = config.vector[i][y];
				// grava as altera��es na matriz final
				config.vector[i][y] = tempVector[i][y];
			}
		}
		// ciclo atual salvo em vectorSaver
		config.vectorSaver.add(newSave);

		// avan�a 1 ciclo
		config.cicloAtual++;
	}

	/**
	 * Retorna um ciclo
	 * 
	 * Author: Madson
	 */
	public void previousCycle() {
		// carrega o valor salvo do ciclo anterior em vectorSaver na matriz
		// atual
		for (int i = 0; i < config.vector.length; i++) {
			for (int y = 0; y < config.vector[i].length; y++) {
				config.vector[i][y] = config.vectorSaver.get(config.cicloAtual - 1)[i][y];
			}
		}
		// retorna 1 ciclo
		config.cicloAtual--;
	}

	// TODO apagar quando finalizar
	public void printArray(int[][] array) {
		String texto = "[\n";
		for (int i = 0; i < array.length; i++) {
			texto += "[";
			for (int j = 0; j < array[i].length; j++) {
				if (j == (array[i].length - 1)) {
					texto += array[i][j] + "]";
				} else {
					texto += array[i][j] + ", ";
				}
			}
			if (i == (array.length - 1)) {
				texto += "\n";
			} else {
				texto += ",\n";
			}
		}
		texto += "]";

		System.out.println(texto);
	}

	// TODO usar para contar popula��o
	public int getEstados(int estado) {
		int num = 0;
		for (int i = 0; i < config.vectorSaver.get(estado).length; i++) {
			for (int y = 0; y < config.vectorSaver.get(estado)[i].length; y++) {
				if (config.vectorSaver.get(estado)[i][y] == 1) {
					num++;
				}
			}
		}
		return num;
	}

	/**
	 * Troca a posi��o dos itens de um ArrayList
	 * 
	 * Author: Madson
	 * 
	 * @param index
	 *            posi��o a ser trocada
	 * @param newIndex
	 *            nova posi��o
	 * @param array
	 *            O ArrayList em quest�o
	 */
	public void swapAt(int index, int newIndex, ArrayList<int[]> array) {
		int[] temp = array.get(newIndex).clone();
		array.set(newIndex, array.get(index).clone());
		array.set(index, temp.clone());
	}

	/**
	 * L� uma c�lula e atrav�s de seu estado e de um array com 8 posi��es
	 * contendo a quantidade de vizinhos de cada estado poss�vel e verifica a
	 * regra
	 * 
	 * @param estadoAtual
	 *            estado da c�lula atual
	 * @param vizinhos
	 *            array contendo 8 inteiros representando a quantidade de
	 *            vizinhos de estado x, sendo a primeira posi��o a contagem de
	 *            vizinhos em estado 1, a segunda posi��o a contagem de vizinhos
	 *            em estado 2 e assim sucessivamente
	 * @param regraReferese
	 *            estado ao qual a regra se refere
	 * @param regraVizinho
	 *            estado ao qual a quantidade de vizinhos ir� considerar
	 * @param regraOperador
	 *            operador relacional, de acordo com o seguinte dicion�rio:
	 *            0(>=), 1(>), 2(==), 3(<) e 4(<=)
	 * @param regraQuant
	 *            quantidade de vizinhos da c�lula atual de estado igual ao
	 *            determinado em regraVizinho
	 * @return caso os argumentos validem a regra, retorna verdadeiro
	 */
	public boolean passRule(int estadoAtual, int[] vizinhos, int regraReferese, int regraOperador, int regraQuant,
			int regraVizinho) {
		// incrementa a quantidade da regra para adapta��o ao valor real
		regraQuant++;
		// checa se a regra refere-se ao estado da c�lula atual
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
				if (vizinhos[regraVizinho] < regraQuant) {
					return true;
				}
				break;
			case 4:
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
	 * c�lula
	 * 
	 * Author: Madson
	 * 
	 * @param x
	 *            correspondente � posi��o no array de vizinhos da posi��o X
	 * @param y
	 *            correspondente � posi��o no array de vizinhos da posi��o Y
	 * @return array com a quantidade de vizinhos em cada estado
	 */
	public int[] getNeighborhood(int x, int y) {
		// array contendo a quantidade de vizinhos em cada estado
		int neighborhood[] = new int[8];
		// arrays das posi��es x e y em rela��o � c�lula
		int[] mapX = { x - 1, x - 1, x - 1, x + 0, x + 0, x + 1, x + 1, x + 1 };
		int[] mapY = { y - 1, y + 0, y + 1, y - 1, y + 1, y - 1, y + 0, y + 1 };
		// percorre a vizinhan�a
		for (int i = 0; i < 8; i++) {
			try {
				// identifica o estado da c�lula vizinha
				int neighborState = config.vector[mapX[i]][mapY[i]];
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
				// Acontece com c�lulas localizadas nas extremidades, quando n�o
				// h� vizinho nesta posi��o
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
	 *            vetor a receber a c�pia
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
		String sobre = "AUTCEL: Editor e Simulador de Aut�matos Celulares"
				+ "\n\nAutor: Madson Paulo Alexandre da Silva.\nE-mail: madson-paulo@hotmail.com"
				+ "\n\nEste projeto usa a licen�a AGPL v3.0. Voc� pode encontr�-lo em: https://github.com/MadsonPaulo/autcel"
				+ "\nSinta-se livre para contribuir com o projeto.";
		ImageIcon icon = new ImageIcon(AppController.class.getResource("/img/main64x64.png"));
		JOptionPane.showMessageDialog(null, sobre, "Sobre o Autcel", JOptionPane.INFORMATION_MESSAGE, icon);

	}

	/**
	 * Importa configura��es de um arquivo externo
	 * 
	 * Author: Madson
	 * 
	 * @param textArea
	 *            ter� seu texto alterado em caso de erro
	 * @return
	 */
	public boolean importData(JTextArea textArea) {
		// explorador de arquivos para definir o destino/arquivo
		JFileChooser abrindoArquivo = new JFileChooser();
		int resultado = abrindoArquivo.showOpenDialog(null);
		// se o usu�rio n�o aprovar o acesso aos arquivos, retorna falso
		if (resultado != JFileChooser.APPROVE_OPTION) {
			textArea.setText("Voc� precisa aprovar o acesso aos arquivos do seu computador para importar um arquivo.");
			return false;
		}
		// recebe a localiza��o/arquivo selecionada
		File abrirArquivoEscolhido = abrindoArquivo.getSelectedFile();
		try {
			// necess�rios para ler o arquivo
			FileInputStream fis = new FileInputStream(abrirArquivoEscolhido);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			// arrayList que receber� todas as linhas do arquivo
			ArrayList<String> data = new ArrayList<String>();
			// linha a ser lida
			String line = reader.readLine();
			while (line != null) {
				// adiciona a linha ao arrayList
				data.add(line);
				// prepara a pr�xima linha
				line = reader.readLine();
			}
			// fecha-os
			reader.close();
			fis.close();

			// se o arquivo n�o estiver corrompido, devido a modifica��es
			// manuais pelo usu�rio
			if (checkData(data)) {
				// atualiza as informa��es importadas
				updateConfigs(data);
				return true;
			} else {
				textArea.setText("N�o foi poss�vel importar as configura��es. O arquivo est� corrompido.");
				return false;
			}
		} catch (FileNotFoundException ex) {
			textArea.setText("N�o foi poss�vel localizar o arquivo.");
			ex.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Substitui as configura��es atuais pelas importadas
	 * 
	 * Author: Madson
	 * 
	 * @param data
	 */
	private void updateConfigs(ArrayList<String> data) {
		// nome dos estados
		config.nameState1 = data.get(4);
		config.nameState2 = data.get(5);
		config.nameState3 = data.get(6);
		config.nameState4 = data.get(7);
		config.nameState5 = data.get(8);
		config.nameState6 = data.get(9);
		config.nameState7 = data.get(10);
		config.nameState8 = data.get(11);

		// quantidade de estados poss�veis
		config.activeStates = Integer.parseInt(data.get(12));

		// vetor
		int pos0 = 0;
		for (int i = 0; i < config.vector.length; i++) {
			for (int j = 0; j < config.vector[0].length; j++) {
				config.vector[i][j] = Character.getNumericValue(data.get(14).charAt(pos0));
				pos0++;
			}
		}

		// regras
		int pos = 0;
		for (int i = 0; i < config.regras.size(); i++) {
			for (int j = 0; j < 6; j++) {
				config.regras.get(i)[j] = Character.getNumericValue(data.get(3).charAt(pos));
				pos++;
			}
		}
	}

	/**
	 * Checa se h� alguma irregularidade no arquivo importado
	 * 
	 * Author: Madson
	 * 
	 * @param data
	 *            ArrayList com as linhas do arquivo importado
	 * @return
	 */
	public boolean checkData(ArrayList<String> data) {

		// quantidade de linhas
		if (data.size() != 15) {
			return false;
		}

		// cada regra ocupa 6 espa�os
		if (data.get(3).length() % 6 != 0) {
			return false;
		}

		// checa se o nome do estado n�o � vazio nem maior que o tamanho limite
		for (int i = 4; i < 12; i++) {
			if (data.get(i).length() > config.maxStateNameSize || data.get(i).isEmpty()
					|| data.get(i).trim().length() == 0) {
				return false;
			}
		}

		// garante que hajam somente n�meros onde esperam-se apenas n�meros
		try {
			// percorre a regra e verifica se todos os caracteres s�o n�meros
			for (int i = 0; i < data.get(3).length(); i++) {
				Character.getNumericValue(data.get(3).charAt(i));
			}
			Integer.parseInt(data.get(12));
			Integer.parseInt(data.get(13));
			// percorre a matriz e verifica se todos os caracteres s�o n�meros
			for (int i = 0; i < data.get(14).length(); i++) {
				Character.getNumericValue(data.get(14).charAt(i));
			}
		} catch (Exception e) {
			return false;
		}

		// quantidade de estados poss�veis
		if (Integer.parseInt(data.get(12)) < 2 || Integer.parseInt(data.get(12)) > 8) {
			return false;
		}

		// tamanho do vetor
		if (Integer.parseInt(data.get(13)) != 10 && Integer.parseInt(data.get(13)) != 20
				&& Integer.parseInt(data.get(13)) != 40) {
			return false;
		}

		// tamanho da matriz
		if (data.get(14).length() != 100 && data.get(14).length() != 400 && data.get(14).length() != 1600) {
			return false;
		}

		// combina��o entre tamanho do vetor e matriz
		if ((Integer.parseInt(data.get(13)) == 10 && data.get(14).length() != 100)
				|| (Integer.parseInt(data.get(13)) == 20 && data.get(14).length() != 400)
				|| (Integer.parseInt(data.get(13)) == 40 && data.get(14).length() != 1600)) {
			return false;
		}

		// checa cada grupo de 6 numeros, referentes �s regras, com os limites:
		// 174777
		int pos = 0;
		for (int i = 0; i < data.get(3).length(); i++) {
			int num = Character.getNumericValue(data.get(3).charAt(i));
			switch (pos) {
			case 0:
				if (num != 0 && num != 1) {
					return false;
				}
				pos++;
				break;
			case 1:
				if (num < 0 || num > 7) {
					return false;
				}
				pos++;
				break;
			case 2:
				if (num < 0 || num > 4) {
					return false;
				}
				pos++;
				break;
			case 3:
				if (num < 0 || num > 7) {
					return false;
				}
				pos++;
				break;
			case 4:
				if (num < 0 || num > 7) {
					return false;
				}
				pos++;
				break;
			case 5:
				if (num < 0 || num > 7) {
					return false;
				}
				pos = 0;
				break;
			}
		}

		// checa se algum estado na matriz � maior que a quantidade de estados
		// poss�veis
		// l� a linha da quantidade de estados poss�veis
		int estadosPossiveis = Integer.valueOf(data.get(12)) - 1;
		// linha da matriz
		for (int i = 0; i < data.get(14).length(); i++) {
			int num = Character.getNumericValue(data.get(14).charAt(i));
			if (num > estadosPossiveis) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Exporta as configura��es do aut�mato.
	 * 
	 * Author: Madson
	 */
	public boolean exportData() {
		// explorador de arquivos para definir o destino/arquivo
		JFileChooser salvandoArquivo = new JFileChooser();
		int resultado = salvandoArquivo.showSaveDialog(null);
		// se o usu�rio n�o aprovar o acesso aos arquivos, retorna falso
		if (resultado != JFileChooser.APPROVE_OPTION) {
			return false;
		}
		// recebe a localiza��o/arquivo selecionada
		File salvarArquivoEscolhido = salvandoArquivo.getSelectedFile();
		try {
			// necess�rios para escrever no arquivo
			FileWriter arq = new FileWriter(salvarArquivoEscolhido, false);
			PrintWriter gravarArq = new PrintWriter(arq);
			// aviso para quem abrir o arquivo como de texto
			String aviso = "Arquivo de configura��es do Autcel.\nAVISO: altera��es neste arquivo podem invalid�-lo.\n\n";
			// aviso e configura��es
			String data = aviso + arrayListToLine(config.regras) + "\n" + config.nameState1 + "\n" + config.nameState2
					+ "\n" + config.nameState3 + "\n" + config.nameState4 + "\n" + config.nameState5 + "\n"
					+ config.nameState6 + "\n" + config.nameState7 + "\n" + config.nameState8 + "\n"
					+ config.activeStates + "\n" + config.tamVector + "\n" + arrayToLine(config.vector);
			// aviso e configura��es s�o gravadas no arquivo
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
	 * Converte um ArrayList de array de inteiros em string, com a finalidade de
	 * export�-la
	 * 
	 * Author: Madson
	 * 
	 * @param ArrayList
	 *            a ser convertido
	 * @return string como todos os n�meros em sequ�ncia e sem separadores
	 */
	public String arrayListToLine(ArrayList<int[]> array) {
		String text = "";

		for (int i = 0; i < array.size(); i++) {
			for (int j = 0; j < array.get(0).length; j++) {
				text += array.get(i)[j];
			}
		}

		return text;
	}

	/**
	 * Converte um array de inteiros em string, com a finalidade de export�-la
	 * 
	 * Author: Madson
	 * 
	 * @param array
	 *            a ser convertido
	 * @return string como todos os n�meros em sequ�ncia e sem separadores
	 */
	public String arrayToLine(int[][] array) {
		String text = "";

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				text += array[i][j];
			}
		}

		return text;
	}

	public Color[] getArrayOfCollors() {
		return new Color[] { config.color1, config.color2, config.color3, config.color4, config.color5, config.color6,
				config.color7, config.color8 };
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

}
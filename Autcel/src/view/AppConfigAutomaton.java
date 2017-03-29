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
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.AppController;
import model.DrawSquare;

public class AppConfigAutomaton extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DrawSquare squares2;
	private JComboBox<String> estadosPossiveisComboBox;
	private JTextArea infoTxtArea = new JTextArea();
	private String mensagemInicialTxtArea = "";
	private JSpinner densitySpinner = new JSpinner();
	private JTextField txtEstado1;
	private JTextField txtEstado2;
	private JTextField txtEstado3;
	private JTextField txtEstado4;
	private JTextField txtEstado5;
	private JTextField txtEstado6;
	private JTextField txtEstado7;
	private JTextField txtEstado8;
	private JLabel lblCor1 = new JLabel();
	private JLabel lblCor2 = new JLabel();
	private JLabel lblCor3 = new JLabel();
	private JLabel lblCor4 = new JLabel();
	private JLabel lblCor5 = new JLabel();
	private JLabel lblCor6 = new JLabel();
	private JLabel lblCor7 = new JLabel();
	private JLabel lblCor8 = new JLabel();
	private JLabel lblEstadosPossveis = new JLabel();
	private JLabel lblEscala = new JLabel();
	private JLabel lblCoord = new JLabel("[0, 0]");
	private JMenu mnArquivo = new JMenu();
	private JMenu mnchangeSize = new JMenu();
	private JMenu mnImportar = new JMenu();
	private JMenu mnModelos = new JMenu();
	private JMenu mnPrerncias = new JMenu();
	private JMenu mnIdioma = new JMenu();
	private JMenu mnAjuda = new JMenu();
	private JMenu mnNavigation = new JMenu();
	private JMenu mnNew = new JMenu();
	private JMenuItem mntmNew20 = new JMenuItem();
	private JMenuItem mntmNew40 = new JMenuItem();
	private JMenuItem mntmNew80 = new JMenuItem();
	private JMenuItem mntmNew160 = new JMenuItem();
	private JMenuItem mntmNew320 = new JMenuItem();
	private JMenuItem mntmSize20 = new JMenuItem();
	private JMenuItem mntmSize40 = new JMenuItem();
	private JMenuItem mntmSize80 = new JMenuItem();
	private JMenuItem mntmSize160 = new JMenuItem();
	private JMenuItem mntmSize320 = new JMenuItem();
	private JMenuItem mntmCenter = new JMenuItem();
	private JMenuItem mntmNEast = new JMenuItem();
	private JMenuItem mntmNWest = new JMenuItem();
	private JMenuItem mntmSEast = new JMenuItem();
	private JMenuItem mntmSWest = new JMenuItem();
	private JMenuItem mntmExportar = new JMenuItem();
	private JMenuItem mntmProcurar = new JMenuItem();
	private JMenuItem mntmManual = new JMenuItem();
	private JMenuItem mntmSobre = new JMenuItem();
	private JMenuItem mntmPortugus = new JMenuItem();
	private JMenuItem mntmEnglish = new JMenuItem();
	private JMenuItem mntmSair = new JMenuItem();
	private JMenuItem prefab1 = new JMenuItem("Conway's Game of Life");
	private JMenuItem prefab2 = new JMenuItem("Ulam's Crystals");
	private JMenuItem prefab3 = new JMenuItem("Rule 614");
	private JMenuItem prefab4 = new JMenuItem("Tsunami");
	private JButton btnImportar = new JButton();
	private JButton btnLimpar = new JButton();
	private JButton btnExportar = new JButton();
	private JButton btnRules = new JButton();
	private JButton btnExecution = new JButton();
	private JButton btnRandomize = new JButton();
	// temporários, usados para desenhar
	private int x;
	private int y;
	private int state;
	private int[] statesAmount;
	private JScrollPane scrollPane;

	/**
	 * Author: Madson
	 * 
	 * @return 'false' caso algum dos 8 txtEstado seja vazio ou formado apenas
	 *         por espaços, 'true' caso contrário
	 */
	private boolean areTxtEstadosValid() {
		// checa quais estados estão ativos
		int estadosPossiveis = Integer.parseInt(estadosPossiveisComboBox.getSelectedItem().toString());
		// arrayList com os campos de texto dos estados ativos
		ArrayList<JTextField> campos = new ArrayList<>();
		switch (estadosPossiveis) {
		case 8:
			campos.add(txtEstado8);
		case 7:
			campos.add(txtEstado7);
		case 6:
			campos.add(txtEstado6);
		case 5:
			campos.add(txtEstado5);
		case 4:
			campos.add(txtEstado4);
		case 3:
			campos.add(txtEstado3);
		case 2:
			campos.add(txtEstado2);
			campos.add(txtEstado1);
		}
		// verifica se é vario ou formado por espaços em branco
		for (int i = 0; i < campos.size(); i++) {
			if (campos.get(i).getText().isEmpty() || campos.get(i).getText().trim().length() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Atualiza os contadores de população
	 * 
	 * Author: Madson
	 */
	private void updateTexts() {
		lblCor1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor1.setText(NumberFormat.getIntegerInstance().format(statesAmount[0]));
		lblCor2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor2.setText(NumberFormat.getIntegerInstance().format(statesAmount[1]));
		lblCor3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor3.setText(NumberFormat.getIntegerInstance().format(statesAmount[2]));
		lblCor4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor4.setText(NumberFormat.getIntegerInstance().format(statesAmount[3]));
		lblCor5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor5.setText(NumberFormat.getIntegerInstance().format(statesAmount[4]));
		lblCor6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor6.setText(NumberFormat.getIntegerInstance().format(statesAmount[5]));
		lblCor7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor7.setText(NumberFormat.getIntegerInstance().format(statesAmount[6]));
		lblCor8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCor8.setText(NumberFormat.getIntegerInstance().format(statesAmount[7]));
	}

	/**
	 * Atualiza a cor da fonte dos contadores de população
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void updatePopBackground(AppController contr) {
		Color[] colors = contr.getArrayOfCollors();
		JLabel[] labels = { lblCor1, lblCor2, lblCor3, lblCor4, lblCor5, lblCor6, lblCor7, lblCor8 };
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == Color.GRAY || colors[i] == Color.BLUE || colors[i] == Color.RED) {
				labels[i].setForeground(Color.WHITE);
			} else {
				labels[i].setForeground(Color.BLACK);
			}
		}
	}

	private void changeColor(int state, AppController contr) {
		// array de cores
		Color[] colors = { Color.WHITE, Color.GRAY, Color.BLUE, Color.RED, Color.YELLOW, Color.CYAN, Color.MAGENTA,
				Color.LIGHT_GRAY };
		// array de nome de cores
		String[] words = { "Escolha a nova cor", "Alterar Cor", "Branco", "Cinza", "Azul", "Vermelho", "Amarelo",
				"Ciano", "Rosa", "Cinza Claro" };
		// tradução
		if (contr.getLanguage() == 1) {
			words[0] = "Change the new color";
			words[1] = "Change Color";
			words[2] = "White";
			words[3] = "Gray";
			words[4] = "Blue";
			words[5] = "Red";
			words[6] = "Yellow";
			words[7] = "Cyan";
			words[8] = "Pink";
			words[9] = "Light Gray";
		}
		// opções do joptionpane
		String[] options = { words[2], words[3], words[4], words[5], words[6], words[7], words[8], words[9] };
		Object res = JOptionPane.showInputDialog(null, words[0], words[1], JOptionPane.PLAIN_MESSAGE, null, options,
				"");

		if (res != null) {
			int selectedIndex = -1;
			// descobre qual o índice da escolha
			for (int i = 0; i < options.length; i++) {
				if (options[i].equals(res)) {
					selectedIndex = i;
					break;
				}
			}
			// se a cor escolhida for diferente da atual
			if (colors[selectedIndex] != contr.getArrayOfCollors()[state]) {
				int posNew = 0;
				Color[] currentColors = contr.getArrayOfCollors();
				for (int i = 0; i < currentColors.length; i++) {
					if (currentColors[i] == colors[selectedIndex]) {
						posNew = i;
						break;
					}
				}
				// salva a cor atual e faz SWAP
				Color tempColor = currentColors[state];
				contr.setColor(colors[selectedIndex], state);
				contr.setColor(tempColor, posNew);
				// altera a cor dos labels
				lblCor1.setBackground(contr.getArrayOfCollors()[0]);
				lblCor2.setBackground(contr.getArrayOfCollors()[1]);
				lblCor3.setBackground(contr.getArrayOfCollors()[2]);
				lblCor4.setBackground(contr.getArrayOfCollors()[3]);
				lblCor5.setBackground(contr.getArrayOfCollors()[4]);
				lblCor6.setBackground(contr.getArrayOfCollors()[5]);
				lblCor7.setBackground(contr.getArrayOfCollors()[6]);
				lblCor8.setBackground(contr.getArrayOfCollors()[7]);
				// atualiza o grid
				contr.drawMatriz(squares2);
				repaint();
			}
		}
		// altera a cor da fonte da população
		updatePopBackground(contr);
	}

	/**
	 * Tenta importar configurações. Se obtiver sucesso, fecha a tela atual e
	 * abre a tela de execução
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 *            controller
	 */
	private void importData(AppController contr) {
		// se obtiver sucesso em importar as configurações do autômato
		if (contr.importData()) {
			// cria uma nova instância de execução
			AppRun run = new AppRun(contr);
			// torna a nova instância visível
			run.setVisible(true);
			// encerra a instância atual
			dispose();
		}
	}

	/**
	 * Desfaz as alterações feitas e reseta a matriz
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void cleanData(AppController contr) {
		// renomeia estados
		txtEstado1.setText(contr.getNameState1());
		txtEstado2.setText(contr.getNameState2());
		txtEstado3.setText(contr.getNameState3());
		txtEstado4.setText(contr.getNameState4());
		txtEstado5.setText(contr.getNameState5());
		txtEstado6.setText(contr.getNameState6());
		txtEstado7.setText(contr.getNameState7());
		txtEstado8.setText(contr.getNameState8());
		// reseta a matriz
		contr.resetVector();
		// desenha a matriz
		contr.drawMatriz(squares2);
		repaint();
		// reseta txtArea
		infoTxtArea.setText(mensagemInicialTxtArea);
		// atualiza os contadores de estados
		statesAmount = contr.countStates();
		updateTexts();
	}

	/**
	 * Altera o idioma
	 * 
	 * Author: Madson
	 * 
	 * @param value
	 *            0:Português, 1 Inglês
	 * @param contr
	 */
	private void setLanguage(int value, AppController contr) {
		if (value == 0) {
			// título
			setTitle("Autcel: Configuração");
			// menus
			mnArquivo.setText("Arquivo");
			mnImportar.setText("Importar");
			mnModelos.setText("Modelos");
			mnPrerncias.setText("Preferências");
			mnIdioma.setText("Idioma");
			mntmProcurar.setText("Procurar");
			mnNew.setText("Novo Autômato");
			mntmExportar.setText("Exportar");
			mntmSair.setText("Sair");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmSobre.setText("Sobre o Autcel");
			mnAjuda.setText("Ajuda");
			mnNavigation.setText("Navegação");
			mntmCenter.setText("Centro");
			mntmNEast.setText("Nordeste");
			mntmNWest.setText("Noroeste");
			mntmSEast.setText("Sudeste");
			mntmSWest.setText("Sudoeste");
			mnchangeSize.setText("Tamanho da Grade");
			// botões
			btnImportar.setText("Importar");
			btnLimpar.setText("Limpar");
			btnRules.setText("Regras");
			btnExecution.setText("Execução");
			btnExportar.setText("Exportar");
			btnRandomize.setText("Pop. Aleatória");
			// labels
			lblEstadosPossveis.setText("Estados Possíveis");
			updateScale(contr);
			// textArea
			mensagemInicialTxtArea = "Por favor, defina a quantidade e o nome dos estados poss\u00EDveis, assim como o estado inicial de cada célula.";
			infoTxtArea.setText(mensagemInicialTxtArea);
		} else if (value == 1) {
			// título
			setTitle("Autcel: Configuration");
			// menus
			mnArquivo.setText("File");
			mnImportar.setText("Import");
			mnModelos.setText("Templates");
			mnPrerncias.setText("Settings");
			mnIdioma.setText("Language");
			mntmProcurar.setText("Search");
			mnNew.setText("New Automaton");
			mntmExportar.setText("Export");
			mntmSair.setText("Exit");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("User Manual");
			mntmSobre.setText("About Autcel");
			mnAjuda.setText("Help");
			mnNavigation.setText("Navigation");
			mntmCenter.setText("Center");
			mntmNEast.setText("North-East");
			mntmNWest.setText("North-West");
			mntmSEast.setText("South-East");
			mntmSWest.setText("South-West");
			mnchangeSize.setText("Grid Size");
			// botões
			btnImportar.setText("Import");
			btnLimpar.setText("Clean");
			btnRules.setText("Rules");
			btnExecution.setText("Execution");
			btnExportar.setText("Export");
			btnRandomize.setText("Random Pop.");
			// labels
			lblEstadosPossveis.setText("Possible States");
			updateScale(contr);
			// textArea
			mensagemInicialTxtArea = "Please, define the number and name of possible states, as well as the initial state of each cell.";
			infoTxtArea.setText(mensagemInicialTxtArea);
		}
		// independente do idioma
		mntmNew20.setText("20x20");
		mntmNew40.setText("40x40");
		mntmNew80.setText("80x80");
		mntmNew160.setText("160x160");
		mntmNew320.setText("320x320");
		mntmSize20.setText("20x20");
		mntmSize40.setText("40x40");
		mntmSize80.setText("80x80");
		mntmSize160.setText("160x160");
		mntmSize320.setText("320x320");
	}

	/**
	 * Atualiza o texto da escala
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void updateScale(AppController contr) {
		if (contr.getLanguage() == 0) {
			lblEscala.setText("Escala: 1:" + contr.getSqrSize());
		} else if (contr.getLanguage() == 1) {
			lblEscala.setText("Scale: 1:" + contr.getSqrSize());
		}
	}

	/**
	 * Salva o nome dos estados
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void saveStateNames(AppController controller) {
		controller.setNameState1(txtEstado1.getText());
		controller.setNameState2(txtEstado2.getText());
		controller.setNameState3(txtEstado3.getText());
		controller.setNameState4(txtEstado4.getText());
		controller.setNameState5(txtEstado5.getText());
		controller.setNameState6(txtEstado6.getText());
		controller.setNameState7(txtEstado7.getText());
		controller.setNameState8(txtEstado8.getText());
	}

	/**
	 * Author: Madson
	 * 
	 * @param ativos
	 *            quantidade de estados possiveis
	 * 
	 *            Ativa ou desativa os txtEstado de acordo com a quantidade de
	 *            estados possiveis
	 */
	private void updateActiveStates(int ativos) {
		switch (ativos) {
		case 2:
			txtEstado3.setEnabled(false);
			txtEstado4.setEnabled(false);
			txtEstado5.setEnabled(false);
			txtEstado6.setEnabled(false);
			txtEstado7.setEnabled(false);
			txtEstado8.setEnabled(false);
			break;
		case 3:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(false);
			txtEstado5.setEnabled(false);
			txtEstado6.setEnabled(false);
			txtEstado7.setEnabled(false);
			txtEstado8.setEnabled(false);
			break;
		case 4:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(true);
			txtEstado5.setEnabled(false);
			txtEstado6.setEnabled(false);
			txtEstado7.setEnabled(false);
			txtEstado8.setEnabled(false);
			break;
		case 5:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(true);
			txtEstado5.setEnabled(true);
			txtEstado6.setEnabled(false);
			txtEstado7.setEnabled(false);
			txtEstado8.setEnabled(false);
			break;
		case 6:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(true);
			txtEstado5.setEnabled(true);
			txtEstado6.setEnabled(true);
			txtEstado7.setEnabled(false);
			txtEstado8.setEnabled(false);
			break;
		case 7:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(true);
			txtEstado5.setEnabled(true);
			txtEstado6.setEnabled(true);
			txtEstado7.setEnabled(true);
			txtEstado8.setEnabled(false);
			break;
		case 8:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(true);
			txtEstado5.setEnabled(true);
			txtEstado6.setEnabled(true);
			txtEstado7.setEnabled(true);
			txtEstado8.setEnabled(true);
			break;
		default:
			txtEstado3.setEnabled(true);
			txtEstado4.setEnabled(true);
			txtEstado5.setEnabled(true);
			txtEstado6.setEnabled(true);
			txtEstado7.setEnabled(true);
			txtEstado8.setEnabled(true);
		}
	}

	/**
	 * Altera o foco dos scrolls para uma região específica
	 * 
	 * Author: Madson
	 * 
	 * @param area
	 */
	private void setFocus(int area) {
		// limite da borda - extensão, ou seja, a distância que pode dar scroll
		int maxLessExtent = scrollPane.getHorizontalScrollBar().getMaximum()
				- scrollPane.getHorizontalScrollBar().getModel().getExtent();

		if (area == 0) {// cetro
			scrollPane.getHorizontalScrollBar().setValue(maxLessExtent / 2);
			scrollPane.getVerticalScrollBar().setValue(maxLessExtent / 2);
		} else if (area == 1) {// nordeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 1.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.0));
		} else if (area == 2) {// noroeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.0));
		} else if (area == 3) {// sudeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 1.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 1.0));
		} else if (area == 4) {// sudoeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 1.0));
		}
	}

	/**
	 * Conserta a escala quando há alteração no tamanho da matriz
	 * 
	 * Author: Madson
	 */
	private void fixScale(AppController contr) {
		int size = 8;
		if (contr.getTamVector() == 20) {
			size = 32;
		} else if (contr.getTamVector() == 40) {
			size = 16;
		} else if (contr.getTamVector() == 80) {
			size = 8;
		} else if (contr.getTamVector() == 160) {
			size = 4;
		} else if (contr.getTamVector() == 320) {
			size = 2;
		}
		// altera a escala, tamanho dos quadrados
		contr.setSqrSize(size);
		// atualiza as barras de scroll de squares
		squares2.setPrefSize(contr.getSqrSize() * contr.getTamVector());
		// atualiza o texto de escala
		updateScale(contr);
		// redesenha o grid
		contr.drawMatriz(squares2);
		repaint();
	}

	public AppConfigAutomaton(AppController controller) {
		// caso a janela seja movida, redesenha o grid
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				try {
					controller.drawMatriz(squares2);
					repaint();
				} catch (NullPointerException n) {
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppConfigAutomaton.class.getResource("/img/main16x16.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 700);

		// cria a barra de menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// separator
		JSeparator separator = new JSeparator();
		// inclusões
		menuBar.add(mnArquivo);
		mnArquivo.add(mnNew);
		mnNew.add(mntmNew20);
		mnNew.add(mntmNew40);
		mnNew.add(mntmNew80);
		mnNew.add(mntmNew160);
		mnNew.add(mntmNew320);
		mnArquivo.add(mnImportar);
		mnImportar.add(mntmProcurar);
		mnImportar.add(mnModelos);
		mnModelos.add(prefab1);
		mnModelos.add(prefab2);
		mnModelos.add(prefab3);
		mnModelos.add(prefab4);
		mnArquivo.add(mntmExportar);
		mnArquivo.add(separator);
		mnArquivo.add(mntmSair);
		menuBar.add(mnNavigation);
		mnNavigation.add(mntmCenter);
		mnNavigation.add(mntmNEast);
		mnNavigation.add(mntmNWest);
		mnNavigation.add(mntmSEast);
		mnNavigation.add(mntmSWest);
		menuBar.add(mnPrerncias);
		mnPrerncias.add(mnchangeSize);
		mnchangeSize.add(mntmSize20);
		mnchangeSize.add(mntmSize40);
		mnchangeSize.add(mntmSize80);
		mnchangeSize.add(mntmSize160);
		mnchangeSize.add(mntmSize320);
		mnPrerncias.add(mnIdioma);
		mnIdioma.add(mntmPortugus);
		mnIdioma.add(mntmEnglish);
		menuBar.add(mnAjuda);
		mnAjuda.add(mntmManual);
		mnAjuda.add(mntmSobre);
		// ícones
		mnNew.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/plus.png")));
		mnImportar.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/import.gif")));
		mntmExportar.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/export.png")));
		mntmSair.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/exit.png")));
		mnIdioma.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/config.png")));
		mnchangeSize.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/size.png")));
		mntmPortugus.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/brasil.png")));
		mntmEnglish.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/United-States.png")));
		mntmSobre.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/main16x16.png")));
		mntmManual.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/manual.png")));
		// fonte dos menus
		mnNew.setFont(controller.getBoldFont());
		mntmNew20.setFont(controller.getBoldFont());
		mntmNew40.setFont(controller.getBoldFont());
		mntmNew80.setFont(controller.getBoldFont());
		mntmNew160.setFont(controller.getBoldFont());
		mntmNew320.setFont(controller.getBoldFont());
		mntmSize20.setFont(controller.getBoldFont());
		mntmSize40.setFont(controller.getBoldFont());
		mntmSize80.setFont(controller.getBoldFont());
		mntmSize160.setFont(controller.getBoldFont());
		mntmSize320.setFont(controller.getBoldFont());
		mnArquivo.setFont(controller.getBoldFont());
		mnImportar.setFont(controller.getBoldFont());
		mnModelos.setFont(controller.getBoldFont());
		mnPrerncias.setFont(controller.getBoldFont());
		mnIdioma.setFont(controller.getBoldFont());
		mnAjuda.setFont(controller.getBoldFont());
		mntmExportar.setFont(controller.getBoldFont());
		mntmProcurar.setFont(controller.getBoldFont());
		mntmManual.setFont(controller.getBoldFont());
		mntmSobre.setFont(controller.getBoldFont());
		mntmPortugus.setFont(controller.getBoldFont());
		mntmEnglish.setFont(controller.getBoldFont());
		mntmSair.setFont(controller.getBoldFont());
		mnNavigation.setFont(controller.getBoldFont());
		mntmCenter.setFont(controller.getBoldFont());
		mntmNEast.setFont(controller.getBoldFont());
		mntmNWest.setFont(controller.getBoldFont());
		mntmSEast.setFont(controller.getBoldFont());
		mntmSWest.setFont(controller.getBoldFont());
		// atalhos
		mntmCenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, true));
		mntmNEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true));
		mntmNWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true));
		mntmSEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, true));
		mntmSWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true));
		// desabilita um menu
		mntmExportar.setEnabled(false);
		// ações
		mntmSize20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeTamVector(20);
				fixScale(controller);
			}
		});
		mntmSize40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeTamVector(40);
				fixScale(controller);
			}
		});
		mntmSize80.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeTamVector(80);
				fixScale(controller);
			}
		});
		mntmSize160.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeTamVector(160);
				fixScale(controller);
			}
		});
		mntmSize320.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeTamVector(320);
				fixScale(controller);
			}
		});
		mntmNew20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppController contr = new AppController();
				contr.startApplication(20);
				dispose();
			}
		});
		mntmNew40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppController contr = new AppController();
				contr.startApplication(40);
				dispose();
			}
		});
		mntmNew80.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppController contr = new AppController();
				contr.startApplication(80);
				dispose();
			}
		});
		mntmNew160.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppController contr = new AppController();
				contr.startApplication(160);
				dispose();
			}
		});
		mntmNew320.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppController contr = new AppController();
				contr.startApplication(320);
				dispose();
			}
		});
		mntmProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmPortugus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.getLanguage() == 1) {
					controller.setLanguage(0);
					setLanguage(0, controller);
				}
			}
		});
		mntmEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.getLanguage() == 0) {
					controller.setLanguage(1);
					setLanguage(1, controller);
				}
			}
		});
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.showAboutPopUp();
			}
		});
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showManualPopUp();
			}
		});
		mntmCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFocus(0);
			}
		});
		mntmNEast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFocus(1);
			}
		});
		mntmNWest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFocus(2);
			}
		});
		mntmSEast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFocus(3);
			}
		});
		mntmSWest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFocus(4);
			}
		});
		prefab1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controller.loadPrefab(0)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		prefab2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controller.loadPrefab(1)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		prefab3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controller.loadPrefab(2)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		prefab4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controller.loadPrefab(3)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel configPanel = new JPanel();
		configPanel.setBounds(7, 3, 189, 643);
		contentPane.add(configPanel);
		configPanel.setLayout(null);

		JPanel estadosPanel = new JPanel();
		estadosPanel.setBounds(4, 186, 181, 284);
		configPanel.add(estadosPanel);
		estadosPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		estadosPanel.setLayout(null);

		JPanel esqPanel = new JPanel();
		esqPanel.setBounds(1, 2, 111, 280);
		estadosPanel.add(esqPanel);
		esqPanel.setLayout(null);
		lblEstadosPossveis.setHorizontalAlignment(SwingConstants.CENTER);

		lblEstadosPossveis.setBounds(3, 10, 105, 20);
		esqPanel.add(lblEstadosPossveis);
		lblEstadosPossveis.setFont(controller.getBoldFont());

		txtEstado1 = new JTextField();
		txtEstado1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado1.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado1.setText(txtEstado1.getText().substring(0, 11));
				}
			}
		});
		txtEstado1.setFont(controller.getNormalFont());
		txtEstado1.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado1.setText(controller.getNameState1());
		txtEstado1.setBounds(5, 40, 100, 20);
		esqPanel.add(txtEstado1);

		txtEstado2 = new JTextField();
		txtEstado2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado2.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado2.setText(txtEstado2.getText().substring(0, 11));
				}
			}
		});
		txtEstado2.setFont(controller.getNormalFont());
		txtEstado2.setText(controller.getNameState2());
		txtEstado2.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado2.setBounds(5, 70, 100, 20);
		esqPanel.add(txtEstado2);

		txtEstado3 = new JTextField();
		txtEstado3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado3.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado3.setText(txtEstado3.getText().substring(0, 11));
				}
			}
		});
		txtEstado3.setText(controller.getNameState3());
		txtEstado3.setFont(controller.getNormalFont());
		txtEstado3.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado3.setBounds(5, 100, 100, 20);
		esqPanel.add(txtEstado3);

		txtEstado4 = new JTextField();
		txtEstado4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado4.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado4.setText(txtEstado4.getText().substring(0, 11));
				}
			}
		});
		txtEstado4.setFont(controller.getNormalFont());
		txtEstado4.setText(controller.getNameState4());
		txtEstado4.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado4.setBounds(5, 130, 100, 20);
		esqPanel.add(txtEstado4);

		txtEstado5 = new JTextField();
		txtEstado5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado5.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado5.setText(txtEstado5.getText().substring(0, 11));
				}
			}
		});
		txtEstado5.setFont(controller.getNormalFont());
		txtEstado5.setText(controller.getNameState5());
		txtEstado5.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado5.setBounds(5, 160, 100, 20);
		esqPanel.add(txtEstado5);

		txtEstado6 = new JTextField();
		txtEstado6.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado6.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado6.setText(txtEstado6.getText().substring(0, 11));
				}
			}
		});
		txtEstado6.setFont(controller.getNormalFont());
		txtEstado6.setText(controller.getNameState6());
		txtEstado6.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado6.setBounds(5, 190, 100, 20);
		esqPanel.add(txtEstado6);

		txtEstado7 = new JTextField();
		txtEstado7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado7.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado7.setText(txtEstado7.getText().substring(0, 11));
				}
			}
		});
		txtEstado7.setFont(controller.getNormalFont());
		txtEstado7.setText(controller.getNameState7());
		txtEstado7.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado7.setBounds(5, 220, 100, 20);
		esqPanel.add(txtEstado7);

		txtEstado8 = new JTextField();
		txtEstado8.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// checa se o nome não é maior que o tamanho limite
				if (txtEstado8.getText().length() >= controller.getMaxStateNameSize()) {
					txtEstado8.setText(txtEstado8.getText().substring(0, 11));
				}
			}
		});
		txtEstado8.setFont(controller.getNormalFont());
		txtEstado8.setText(controller.getNameState8());
		txtEstado8.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado8.setBounds(5, 250, 100, 20);
		esqPanel.add(txtEstado8);

		JPanel dirPanel = new JPanel();
		dirPanel.setBounds(113, 2, 66, 280);
		estadosPanel.add(dirPanel);
		dirPanel.setLayout(null);

		estadosPossiveisComboBox = new JComboBox<String>();
		estadosPossiveisComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// altera quais estados estão ativos, habilitando ou
				// desabilitando os componentes a estes relacionados
				int ativos = Integer.parseInt((String) estadosPossiveisComboBox.getSelectedItem());
				updateActiveStates(ativos);
				controller.setActiveStates(ativos);
			}
		});
		estadosPossiveisComboBox.setBounds(3, 10, 60, 20);
		dirPanel.add(estadosPossiveisComboBox);
		estadosPossiveisComboBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "2", "3", "4", "5", "6", "7", "8" }));
		estadosPossiveisComboBox.setSelectedIndex(controller.getActiveStates() - 2);

		lblCor1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				changeColor(0, controller);
			}
		});
		lblCor1.setBounds(3, 40, 60, 20);
		dirPanel.add(lblCor1);
		lblCor1.setOpaque(true);
		lblCor1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor1.setBackground(controller.getColor(1));

		lblCor2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				changeColor(1, controller);
			}
		});
		lblCor2.setBounds(3, 70, 60, 20);
		dirPanel.add(lblCor2);
		lblCor2.setOpaque(true);
		lblCor2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor2.setBackground(controller.getColor(2));

		lblCor3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(2, controller);
			}
		});
		lblCor3.setBounds(3, 100, 60, 20);
		dirPanel.add(lblCor3);
		lblCor3.setOpaque(true);
		lblCor3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor3.setBackground(controller.getColor(3));

		lblCor4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(3, controller);
			}
		});
		lblCor4.setBounds(3, 130, 60, 20);
		dirPanel.add(lblCor4);
		lblCor4.setOpaque(true);
		lblCor4.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor4.setBackground(controller.getColor(4));

		lblCor5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(4, controller);
			}
		});
		lblCor5.setBounds(3, 160, 60, 20);
		dirPanel.add(lblCor5);
		lblCor5.setOpaque(true);
		lblCor5.setBackground(controller.getColor(5));
		lblCor5.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblCor6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(5, controller);
			}
		});
		lblCor6.setBounds(3, 190, 60, 20);
		dirPanel.add(lblCor6);
		lblCor6.setOpaque(true);
		lblCor6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor6.setBackground(controller.getColor(6));

		lblCor7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(6, controller);
			}
		});
		lblCor7.setBounds(3, 220, 60, 20);
		dirPanel.add(lblCor7);
		lblCor7.setOpaque(true);
		lblCor7.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor7.setBackground(controller.getColor(7));

		lblCor8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(7, controller);
			}
		});
		lblCor8.setBounds(3, 250, 60, 20);
		dirPanel.add(lblCor8);
		lblCor8.setOpaque(true);
		lblCor8.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor8.setBackground(controller.getColor(8));

		JPanel arquivoPanel = new JPanel();
		arquivoPanel.setBounds(4, 6, 181, 70);
		configPanel.add(arquivoPanel);
		arquivoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		arquivoPanel.setLayout(null);

		btnImportar.setBounds(30, 10, 120, 20);
		arquivoPanel.add(btnImportar);
		btnImportar.setFont(controller.getBoldFont());
		btnImportar.setFocusable(false);
		btnImportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		btnImportar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExportar.setBounds(30, 40, 120, 20);

		btnExportar.setEnabled(false);
		arquivoPanel.add(btnExportar);
		btnExportar.setFont(controller.getBoldFont());
		btnExportar.setFocusable(false);
		btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExportar.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// checa se os nomes dos estados são válidos
				if (areTxtEstadosValid() == false) {
					// se forem inválidos, informa mensagem de erro
					if (controller.getLanguage() == 0) {
						infoTxtArea.setText("Não podem haver estados com nome vazio.");
					} else if (controller.getLanguage() == 1) {
						infoTxtArea.setText("There can be no empty state names.");
					}
				} else if (controller.areStatesValid(controller.getVector()) == false) {
					// se houverem estados inválidos, informa mensagem de erro
					if (controller.getLanguage() == 0) {
						infoTxtArea.setText("Provavelmente há algum estado inválido, remova-o para continuar.");
					} else if (controller.getLanguage() == 1) {
						infoTxtArea.setText("Probably there is an invalid state, remove it to proceed.");
					}
					// se tudo estiver válido
				} else {
					// salva o nome atual dos estados
					saveStateNames(controller);
					// instancia a janela de regras
					AppConfigRules rules = new AppConfigRules(controller);
					// torna a janela de regras visível
					rules.setVisible(true);
					// encerra a janela atual
					dispose();
				}

			}
		});
		btnRules.setFont(controller.getBoldFont());
		btnRules.setBounds(14, 587, 160, 20);
		configPanel.add(btnRules);
		btnRules.setPreferredSize(new Dimension(90, 20));
		btnRules.setFocusable(false);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoPanel.setBounds(4, 476, 181, 105);
		configPanel.add(infoPanel);
		infoPanel.setLayout(null);

		infoTxtArea.setFont(new Font("Calibri", Font.PLAIN, 12));
		infoTxtArea.setWrapStyleWord(true);
		infoTxtArea.setEditable(false);
		infoTxtArea.setLineWrap(true);
		infoTxtArea.setOpaque(false);
		infoTxtArea.setBounds(8, 7, 165, 90);
		infoPanel.add(infoTxtArea);

		JPanel ZoomPanel = new JPanel();
		ZoomPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ZoomPanel.setBounds(4, 82, 180, 98);
		configPanel.add(ZoomPanel);
		ZoomPanel.setLayout(null);

		lblEscala.setHorizontalAlignment(SwingConstants.CENTER);
		lblEscala.setFont(controller.getNormalFont());
		lblEscala.setBounds(14, 70, 70, 16);
		ZoomPanel.add(lblEscala);

		lblCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoord.setFont(controller.getNormalFont());
		lblCoord.setBounds(98, 70, 66, 16);
		ZoomPanel.add(lblCoord);
		btnLimpar.setBounds(8, 40, 120, 20);
		ZoomPanel.add(btnLimpar);

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanData(controller);
			}
		});
		btnLimpar.setFocusable(false);
		btnLimpar.setFont(controller.getBoldFont());
		btnLimpar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLimpar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRandomize.setBounds(8, 10, 120, 20);
		ZoomPanel.add(btnRandomize);

		btnRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gera uma população aleatória
				Random r = new Random();
				// quanto menor, maior a densidade
				int density = (int) densitySpinner.getValue();
				// limpa o vetor
				cleanData(controller);
				for (int i = 0; i < controller.getVector().length;) {
					for (int j = 0; j < controller.getVector().length;) {
						controller.getVector()[i][j] = r.nextInt(controller.getActiveStates());
						j += r.nextInt(density);
					}
					i += r.nextInt(density);
				}
				// redesenha
				controller.drawMatriz(squares2);
				repaint();
				// atualiza os contadores de população
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		btnRandomize.setFont(controller.getBoldFont());
		btnRandomize.setFocusable(false);
		btnRandomize.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRandomize.setAlignmentX(Component.CENTER_ALIGNMENT);

		densitySpinner.setBounds(136, 10, 35, 20);
		densitySpinner.setFont(controller.getBoldFont());
		ZoomPanel.add(densitySpinner);
		densitySpinner.setModel(new SpinnerNumberModel(25, 2, 50, 1));
		((DefaultEditor) densitySpinner.getEditor()).getTextField().setEditable(false);

		btnExecution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String error = "É necessário definir as regras do autômato antes de poder executá-lo.";
				if (controller.getLanguage() == 1) {
					error = "It is necessary to define the rules of the automaton before you can execute it.";
				}
				// se já existir alguma regra
				if (controller.getRegras().size() > 0) {
					// checa se os nomes dos estados são válidos
					if (areTxtEstadosValid() == false) {
						// se forem inválidos, informa mensagem de erro
						if (controller.getLanguage() == 0) {
							infoTxtArea.setText("Não podem haver estados com nome vazio.");
						} else if (controller.getLanguage() == 1) {
							infoTxtArea.setText("There can be no empty state names.");
						}
					} else if (controller.areStatesValid(controller.getVector()) == false) {
						// se houverem estados inválidos, informa mensagem de
						// erro
						if (controller.getLanguage() == 0) {
							infoTxtArea.setText(
									"São necessários pelo menos dois estados diferentes para continuar. Caso hajam estados inválidos, remova-os.");
						} else if (controller.getLanguage() == 1) {
							infoTxtArea.setText(
									"It takes at least two different states to continue. If there are invalid states, remove them.");
						}
						// se tudo estiver válido
					} else {
						// salva o nome atual dos estados
						saveStateNames(controller);
						// instancia a janela de execução
						AppRun execution = new AppRun(controller);
						// torna a janela de execução visível
						execution.setVisible(true);
						// encerra a janela atual
						dispose();
					}
				} else {
					infoTxtArea.setText(error);
				}
			}
		});
		btnExecution.setPreferredSize(new Dimension(90, 20));
		btnExecution.setFont(controller.getBoldFont());
		btnExecution.setFocusable(false);
		btnExecution.setBounds(14, 613, 160, 20);

		configPanel.add(btnExecution);

		squares2 = new DrawSquare(controller.getSqrSize() * controller.getTamVector());
		squares2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// razão entre a largura de squares2 e tamanho da matriz
				double value = (squares2.getWidth() * 1.0) / controller.getTamVector();
				// posição x e y do local clicado
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// nas extremidades, pode detectar -1
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// nas extremidades, pode detectar valor acima do
				// permitido
				if (posX >= controller.getTamVector()) {
					posX = controller.getTamVector() - 1;
				}
				if (posY >= controller.getTamVector()) {
					posY = controller.getTamVector() - 1;
				}
				lblCoord.setText("[" + posY + ", " + posX + "]");
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// razão entre a largura de squares2 e tamanho da matriz
				double value = (squares2.getWidth() * 1.0) / controller.getTamVector();
				// posição x e y do local clicado
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// nas extremidades, pode detectar -1
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// nas extremidades, pode detectar valor acima do
				// permitido
				if (posX >= controller.getTamVector()) {
					posX = controller.getTamVector() - 1;
				}
				if (posY >= controller.getTamVector()) {
					posY = controller.getTamVector() - 1;
				}
				// atualiza o label de coordenada
				lblCoord.setText("[" + posY + ", " + posX + "]");
				// se a coordenada for diferente da anterior
				if (posX != x || posY != y) {
					// altera o vetor na posição clicada
					if (SwingUtilities.isRightMouseButton(arg0)) {
						// se foi utilizado o botão direito do mouse, muda para
						// estado 0
						controller.getVector()[posY][posX] = 0;
					} else {
						// se foi utilizado o botão esquerdo, muda para o ultimo
						// estado. Se o ultimo estado era 0, torna em 1
						if (state == 0) {
							state = 1;
						}
						controller.getVector()[posY][posX] = state;
					}

					// redesenha a matriz
					controller.drawMatriz(squares2);
					repaint();
					// atualiza a posição salva
					x = posX;
					y = posY;
					// atualiza os contadores de população
					statesAmount = controller.countStates();
					updateTexts();
				}

			}
		});
		squares2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// razão entre a largura de squares2 e tamanho da matriz
				double value = (squares2.getWidth() * 1.0) / controller.getTamVector();
				// posição x e y do local clicado
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// nas extremidades, pode detectar -1
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// nas extremidades, pode detectar valor acima do
				// permitido
				if (posX >= controller.getTamVector()) {
					posX = controller.getTamVector() - 1;
				}
				if (posY >= controller.getTamVector()) {
					posY = controller.getTamVector() - 1;
				}
				// se foi utilizado o botão direito do mouse, muda para estado 0
				if (SwingUtilities.isRightMouseButton(arg0)) {
					controller.getVector()[posY][posX] = 0;
				} else {// clico com botão esquerdo
					// quantidade de estados ativos
					int ativos = Integer.parseInt((String) estadosPossiveisComboBox.getSelectedItem());
					// altera o vetor na posição clicada
					controller.getVector()[posY][posX] = controller
							.getNextStateValue(controller.getVector()[posY][posX], ativos);
					// salva a posição do click e o estado, caso o usuário
					// queira
					// desenhar
					x = posX;
					y = posY;
					state = controller.getVector()[posY][posX];
				}
				// redesenha a matriz
				controller.drawMatriz(squares2);
				repaint();
				// atualiza os contadores de população
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		squares2.setBorder(new LineBorder(new Color(0, 0, 0)));

		scrollPane = new JScrollPane(squares2);
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				// determina a metade do scroll
				int middle;
				// posição do mouse
				Point mouseLoc = arg0.getPoint();
				// posição da view
				Point viewPort = scrollPane.getViewport().getViewPosition();
				Point novo;
				if (arg0.getWheelRotation() >= 0) {// zoom out
					// determina o zoom out máximo
					int max = 4;
					if (controller.getTamVector() == 20) {
						max = 32;
					} else if (controller.getTamVector() == 40) {
						max = 16;
					} else if (controller.getTamVector() == 80) {
						max = 8;
					} else if (controller.getTamVector() == 160) {
						max = 4;
					} else if (controller.getTamVector() == 320) {
						max = 2;
					}
					// checa a escala
					if (controller.getSqrSize() > max) {
						middle = (int) (scrollPane.getViewport().getExtentSize().getWidth());
						// nova posição após zoom
						novo = new Point((viewPort.x - (middle - mouseLoc.x)) / 2,
								(viewPort.y - (middle - mouseLoc.y)) / 2);
						// altera a escala, tamanho dos quadrados
						controller.setSqrSize(controller.getSqrSize() / 2);
						// atualiza as barras de scroll de squares
						squares2.setPrefSize(controller.getSqrSize() * controller.getTamVector());
						// atualiza o texto de escala
						updateScale(controller);
						// redesenha o grid
						controller.drawMatriz(squares2);
						repaint();
						// ajusta a visualização
						scrollPane.getViewport().setViewPosition(novo);
					}
				} else {// zoom in
					// checa a escala
					if (controller.getSqrSize() < 64) {
						middle = (int) (scrollPane.getViewport().getExtentSize().getWidth() * 0.25);
						// nova posição após zoom
						novo = new Point((viewPort.x - (middle - mouseLoc.x)) * 2,
								(viewPort.y - (middle - mouseLoc.y)) * 2);
						// altera a escala, tamanho dos quadrados
						controller.setSqrSize(controller.getSqrSize() * 2);
						// atualiza as barras de scroll de squares
						squares2.setPrefSize(controller.getSqrSize() * controller.getTamVector());
						// atualiza o texto de escala
						updateScale(controller);
						// redesenha o grid
						controller.drawMatriz(squares2);
						repaint();
						// ajusta a visualização
						scrollPane.getViewport().setViewPosition(novo);
					}
				}
			}
		});
		scrollPane.setAutoscrolls(false);
		scrollPane.setBounds(203, 3, 644, 644);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(40);
		scrollPane.getVerticalScrollBar().setUnitIncrement(40);
		contentPane.add(scrollPane);
		// remove o scroll utilizando a roda do mouse
		scrollPane.removeMouseWheelListener(scrollPane.getMouseWheelListeners()[0]);

		// corrige a escala, se necessário
		fixScale(controller);
		// determina o idioma
		setLanguage(controller.getLanguage(), controller);
		// atualiza os contadores de estados
		statesAmount = controller.countStates();
		updateTexts();
		// determina a cor da fonte da população
		updatePopBackground(controller);
	}
}
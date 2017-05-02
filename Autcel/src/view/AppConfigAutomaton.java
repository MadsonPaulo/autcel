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
	private JComboBox<String> possibleStatesComboBox;
	private JTextArea infoTxtArea = new JTextArea();
	private String initialMsgTxtArea = "";
	private JSpinner densitySpinner = new JSpinner();
	private JTextField txtState1;
	private JTextField txtState2;
	private JTextField txtState3;
	private JTextField txtState4;
	private JTextField txtState5;
	private JTextField txtState6;
	private JTextField txtState7;
	private JTextField txtState8;
	private JLabel lblColor1 = new JLabel();
	private JLabel lblColor2 = new JLabel();
	private JLabel lblColor3 = new JLabel();
	private JLabel lblColor4 = new JLabel();
	private JLabel lblColor5 = new JLabel();
	private JLabel lblColor6 = new JLabel();
	private JLabel lblColor7 = new JLabel();
	private JLabel lblColor8 = new JLabel();
	private JLabel lblPossibleStates = new JLabel();
	private JLabel lblScale = new JLabel();
	private JLabel lblCoord = new JLabel("[0, 0]");
	private JMenu mnFile = new JMenu();
	private JMenu mnchangeSize = new JMenu();
	private JMenu mnImport = new JMenu();
	private JMenu mnModels = new JMenu();
	private JMenu mnPrefs = new JMenu();
	private JMenu mnLanguage = new JMenu();
	private JMenu mnHelp = new JMenu();
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
	private JMenuItem mntmExport = new JMenuItem();
	private JMenuItem mntmSearch = new JMenuItem();
	private JMenuItem mntmManual = new JMenuItem();
	private JMenuItem mntmAbout = new JMenuItem();
	private JMenuItem mntmPortuguese = new JMenuItem();
	private JMenuItem mntmEnglish = new JMenuItem();
	private JMenuItem mntmExit = new JMenuItem();
	private JMenuItem prefab1 = new JMenuItem("Conway's Game of Life");
	private JMenuItem prefab2 = new JMenuItem("Ulam's Crystals");
	private JMenuItem prefab3 = new JMenuItem("Rule 614");
	private JMenuItem prefab4 = new JMenuItem("Tsunami");
	private JButton btnImport = new JButton();
	private JButton btnClean = new JButton();
	private JButton btnExport = new JButton();
	private JButton btnRules = new JButton();
	private JButton btnExecution = new JButton();
	private JButton btnRandomize = new JButton();
	// temps, used to draw
	private int x;
	private int y;
	private int state;
	private int[] statesAmount;
	private JScrollPane scrollPane;

	/**
	 * Checks if the nomes of the 8 states are valid
	 * 
	 * Author: Madson
	 * 
	 * @return 'false' if any of the 8 state names if empty or made of spaces,
	 *         'true' otherwise
	 */
	private boolean checkStateNames() {
		// checks how many states are active
		int possibleStates = Integer.parseInt(possibleStatesComboBox.getSelectedItem().toString());
		// ArrayList with the fields of the active states, to check only the
		// active ones
		ArrayList<JTextField> arrayFields = new ArrayList<>();
		switch (possibleStates) {
		case 8:
			arrayFields.add(txtState8);
		case 7:
			arrayFields.add(txtState7);
		case 6:
			arrayFields.add(txtState6);
		case 5:
			arrayFields.add(txtState5);
		case 4:
			arrayFields.add(txtState4);
		case 3:
			arrayFields.add(txtState3);
		case 2:
			arrayFields.add(txtState2);
			arrayFields.add(txtState1);
		}
		// verifies if its empty or made only with blanks
		for (int i = 0; i < arrayFields.size(); i++) {
			if (arrayFields.get(i).getText().trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Update the population counters
	 * 
	 * Author: Madson
	 */
	private void updateTexts() {
		lblColor1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor1.setText(NumberFormat.getIntegerInstance().format(statesAmount[0]));
		lblColor2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor2.setText(NumberFormat.getIntegerInstance().format(statesAmount[1]));
		lblColor3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor3.setText(NumberFormat.getIntegerInstance().format(statesAmount[2]));
		lblColor4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor4.setText(NumberFormat.getIntegerInstance().format(statesAmount[3]));
		lblColor5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor5.setText(NumberFormat.getIntegerInstance().format(statesAmount[4]));
		lblColor6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor6.setText(NumberFormat.getIntegerInstance().format(statesAmount[5]));
		lblColor7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor7.setText(NumberFormat.getIntegerInstance().format(statesAmount[6]));
		lblColor8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColor8.setText(NumberFormat.getIntegerInstance().format(statesAmount[7]));
	}

	/**
	 * Updates the font color of the population counters to make sure the number
	 * is visible, based on the state color
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void updatePopBackground(AppController contr) {
		Color[] colors = contr.getArrayOfCollors();
		JLabel[] labels = { lblColor1, lblColor2, lblColor3, lblColor4, lblColor5, lblColor6, lblColor7, lblColor8 };
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == Color.GRAY || colors[i] == Color.BLUE || colors[i] == Color.RED) {
				labels[i].setForeground(Color.WHITE);
			} else {
				labels[i].setForeground(Color.BLACK);
			}
		}
	}

	/**
	 * Changes the color of a state
	 * 
	 * Author: Madson
	 * 
	 * @param state
	 * @param contr
	 */
	private void changeColor(int state, AppController contr) {
		// array of colors
		Color[] colors = { Color.WHITE, Color.GRAY, Color.BLUE, Color.RED, Color.YELLOW, Color.CYAN, Color.MAGENTA,
				Color.LIGHT_GRAY };
		// array of state names
		String[] words = { "Escolha a nova cor", "Alterar Cor", "Branco", "Cinza", "Azul", "Vermelho", "Amarelo",
				"Ciano", "Rosa", "Cinza Claro" };
		// translation
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
		// options of the JOptionpane
		String[] options = { words[2], words[3], words[4], words[5], words[6], words[7], words[8], words[9] };
		Object choice = JOptionPane.showInputDialog(null, words[0], words[1], JOptionPane.PLAIN_MESSAGE, null, options,
				"");

		if (choice != null) {
			int selectedIndex = -1;
			// discovers which is the index of the choice
			for (int i = 0; i < options.length; i++) {
				if (options[i].equals(choice)) {
					selectedIndex = i;
					break;
				}
			}
			// if the chosen color is different from the current
			if (colors[selectedIndex] != contr.getArrayOfCollors()[state]) {
				int newPos = 0;
				Color[] currentColors = contr.getArrayOfCollors();
				for (int i = 0; i < currentColors.length; i++) {
					if (currentColors[i] == colors[selectedIndex]) {
						newPos = i;
						break;
					}
				}
				// saves the current color and SWAPs it
				Color tempColor = currentColors[state];
				contr.setColor(colors[selectedIndex], state);
				contr.setColor(tempColor, newPos);
				// changes the color of the labels
				lblColor1.setBackground(contr.getArrayOfCollors()[0]);
				lblColor2.setBackground(contr.getArrayOfCollors()[1]);
				lblColor3.setBackground(contr.getArrayOfCollors()[2]);
				lblColor4.setBackground(contr.getArrayOfCollors()[3]);
				lblColor5.setBackground(contr.getArrayOfCollors()[4]);
				lblColor6.setBackground(contr.getArrayOfCollors()[5]);
				lblColor7.setBackground(contr.getArrayOfCollors()[6]);
				lblColor8.setBackground(contr.getArrayOfCollors()[7]);
				// updates the grid
				contr.drawMatriz(squares2);
				repaint();
			}
		}
		// changes the color of the population counter's font
		updatePopBackground(contr);
	}

	/**
	 * Tries to import configurations. If succeed,closes the current screen and
	 * opens the execution screen
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void importData(AppController contr) {
		if (contr.importData()) {
			// creates a new instance of execution
			AppRun run = new AppRun(contr);
			// turns the new instance visible
			run.setVisible(true);
			// disposes of the current instance
			dispose();
		}
	}

	/**
	 * Undo the changes made and clean the matrix
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void cleanData(AppController contr) {
		// rename the states
		txtState1.setText(contr.getNameState1());
		txtState2.setText(contr.getNameState2());
		txtState3.setText(contr.getNameState3());
		txtState4.setText(contr.getNameState4());
		txtState5.setText(contr.getNameState5());
		txtState6.setText(contr.getNameState6());
		txtState7.setText(contr.getNameState7());
		txtState8.setText(contr.getNameState8());
		// resets the matrix
		contr.resetVector();
		// draws a matrix
		contr.drawMatriz(squares2);
		repaint();
		// resets the infoTxtArea
		infoTxtArea.setText(initialMsgTxtArea);
		// updates the state counters
		statesAmount = contr.countStates();
		updateTexts();
	}

	// TODO Implement Internacionalization
	/**
	 * Changes the language
	 * 
	 * Author: Madson
	 * 
	 * @param value
	 *            0:Portuguese, 1 English
	 * @param contr
	 */
	private void setLanguage(int value, AppController contr) {
		if (value == 0) {
			// title
			setTitle("ESACEL: Configuração");
			// menus
			mnFile.setText("Arquivo");
			mnImport.setText("Importar");
			mnModels.setText("Modelos");
			mnPrefs.setText("Preferências");
			mnLanguage.setText("Idioma");
			mntmSearch.setText("Procurar");
			mnNew.setText("Novo Autômato");
			mntmExport.setText("Exportar");
			mntmExit.setText("Sair");
			mntmPortuguese.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmAbout.setText("Sobre o ESACEL");
			mnHelp.setText("Ajuda");
			mnNavigation.setText("Navegação");
			mntmCenter.setText("Centro");
			mntmNEast.setText("Nordeste");
			mntmNWest.setText("Noroeste");
			mntmSEast.setText("Sudeste");
			mntmSWest.setText("Sudoeste");
			mnchangeSize.setText("Tamanho da Grade");
			// buttons
			btnImport.setText("Importar");
			btnClean.setText("Limpar");
			btnRules.setText("Regras");
			btnExecution.setText("Execução");
			btnExport.setText("Exportar");
			btnRandomize.setText("Pop. Aleatória");
			// labels
			lblPossibleStates.setText("Estados Possíveis");
			updateScale(contr);
			// textArea
			initialMsgTxtArea = "Por favor, defina a quantidade e o nome dos estados poss\u00EDveis, assim como o estado inicial de cada célula.";
			infoTxtArea.setText(initialMsgTxtArea);
		} else if (value == 1) {
			// title
			setTitle("ESACEL: Configuration");
			// menus
			mnFile.setText("File");
			mnImport.setText("Import");
			mnModels.setText("Templates");
			mnPrefs.setText("Settings");
			mnLanguage.setText("Language");
			mntmSearch.setText("Search");
			mnNew.setText("New Automaton");
			mntmExport.setText("Export");
			mntmExit.setText("Exit");
			mntmPortuguese.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("User Manual");
			mntmAbout.setText("About ESACEL");
			mnHelp.setText("Help");
			mnNavigation.setText("Navigation");
			mntmCenter.setText("Center");
			mntmNEast.setText("North-East");
			mntmNWest.setText("North-West");
			mntmSEast.setText("South-East");
			mntmSWest.setText("South-West");
			mnchangeSize.setText("Grid Size");
			// buttons
			btnImport.setText("Import");
			btnClean.setText("Clean");
			btnRules.setText("Rules");
			btnExecution.setText("Execution");
			btnExport.setText("Export");
			btnRandomize.setText("Random Pop.");
			// labels
			lblPossibleStates.setText("Possible States");
			updateScale(contr);
			// textArea
			initialMsgTxtArea = "Please, define the number and name of possible states, as well as the initial state of each cell.";
			infoTxtArea.setText(initialMsgTxtArea);
		}
		// language independent
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
	 * updates the scale text
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void updateScale(AppController contr) {
		if (contr.getLanguage() == 0) {
			lblScale.setText("Escala: 1:" + contr.getSqrSize());
		} else if (contr.getLanguage() == 1) {
			lblScale.setText("Scale: 1:" + contr.getSqrSize());
		}
	}

	/**
	 * Saves the name of the states
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void saveStateNames(AppController controller) {
		controller.setNameState1(txtState1.getText());
		controller.setNameState2(txtState2.getText());
		controller.setNameState3(txtState3.getText());
		controller.setNameState4(txtState4.getText());
		controller.setNameState5(txtState5.getText());
		controller.setNameState6(txtState6.getText());
		controller.setNameState7(txtState7.getText());
		controller.setNameState8(txtState8.getText());
	}

	/**
	 * Determinates which JTextField of state names are enabled
	 * 
	 * Author: Madson
	 * 
	 * @param activeStates
	 *            amount of possible states
	 */
	private void updateActiveStates(int activeStates) {
		switch (activeStates) {
		case 2:
			txtState3.setEnabled(false);
			txtState4.setEnabled(false);
			txtState5.setEnabled(false);
			txtState6.setEnabled(false);
			txtState7.setEnabled(false);
			txtState8.setEnabled(false);
			break;
		case 3:
			txtState3.setEnabled(true);
			txtState4.setEnabled(false);
			txtState5.setEnabled(false);
			txtState6.setEnabled(false);
			txtState7.setEnabled(false);
			txtState8.setEnabled(false);
			break;
		case 4:
			txtState3.setEnabled(true);
			txtState4.setEnabled(true);
			txtState5.setEnabled(false);
			txtState6.setEnabled(false);
			txtState7.setEnabled(false);
			txtState8.setEnabled(false);
			break;
		case 5:
			txtState3.setEnabled(true);
			txtState4.setEnabled(true);
			txtState5.setEnabled(true);
			txtState6.setEnabled(false);
			txtState7.setEnabled(false);
			txtState8.setEnabled(false);
			break;
		case 6:
			txtState3.setEnabled(true);
			txtState4.setEnabled(true);
			txtState5.setEnabled(true);
			txtState6.setEnabled(true);
			txtState7.setEnabled(false);
			txtState8.setEnabled(false);
			break;
		case 7:
			txtState3.setEnabled(true);
			txtState4.setEnabled(true);
			txtState5.setEnabled(true);
			txtState6.setEnabled(true);
			txtState7.setEnabled(true);
			txtState8.setEnabled(false);
			break;
		case 8:
			txtState3.setEnabled(true);
			txtState4.setEnabled(true);
			txtState5.setEnabled(true);
			txtState6.setEnabled(true);
			txtState7.setEnabled(true);
			txtState8.setEnabled(true);
			break;
		default:
			txtState3.setEnabled(true);
			txtState4.setEnabled(true);
			txtState5.setEnabled(true);
			txtState6.setEnabled(true);
			txtState7.setEnabled(true);
			txtState8.setEnabled(true);
		}
	}

	/**
	 * Changes the focus of the scrolls to a specified area
	 * 
	 * Author: Madson
	 * 
	 * @param area
	 */
	private void setFocus(int area) {
		// limit of the border, the scrollable range
		int maxLessExtent = scrollPane.getHorizontalScrollBar().getMaximum()
				- scrollPane.getHorizontalScrollBar().getModel().getExtent();

		if (area == 0) {// center
			scrollPane.getHorizontalScrollBar().setValue(maxLessExtent / 2);
			scrollPane.getVerticalScrollBar().setValue(maxLessExtent / 2);
		} else if (area == 1) {// north-east
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 1.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.0));
		} else if (area == 2) {// north-west
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.0));
		} else if (area == 3) {// south-east
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 1.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 1.0));
		} else if (area == 4) {// south-west
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.0));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 1.0));
		}
	}

	/**
	 * Fix the scale when a change in the matrix size is made
	 * 
	 * Author: Madson
	 */
	private void fixScale(AppController contr) {
		int size = 8;
		if (contr.getVectorSize() == 20) {
			size = 32;
		} else if (contr.getVectorSize() == 40) {
			size = 16;
		} else if (contr.getVectorSize() == 80) {
			size = 8;
		} else if (contr.getVectorSize() == 160) {
			size = 4;
		} else if (contr.getVectorSize() == 320) {
			size = 2;
		}
		// changes the scale, size of the rectangles
		contr.setSqrSize(size);
		// updates the scroll bars
		squares2.setPrefSize(contr.getSqrSize() * contr.getVectorSize());
		// updates the scale text
		updateScale(contr);
		// draws the matrix
		contr.drawMatriz(squares2);
		repaint();
	}

	public AppConfigAutomaton(AppController controller) {
		// if the window if moved, redraws the grid
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

		// creates the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// separator
		JSeparator separator = new JSeparator();
		// inclusions
		menuBar.add(mnFile);
		mnFile.add(mnNew);
		mnNew.add(mntmNew20);
		mnNew.add(mntmNew40);
		mnNew.add(mntmNew80);
		mnNew.add(mntmNew160);
		mnNew.add(mntmNew320);
		mnFile.add(mnImport);
		mnImport.add(mntmSearch);
		mnImport.add(mnModels);
		mnModels.add(prefab1);
		mnModels.add(prefab2);
		mnModels.add(prefab3);
		mnModels.add(prefab4);
		mnFile.add(mntmExport);
		mnFile.add(separator);
		mnFile.add(mntmExit);
		menuBar.add(mnNavigation);
		mnNavigation.add(mntmCenter);
		mnNavigation.add(mntmNEast);
		mnNavigation.add(mntmNWest);
		mnNavigation.add(mntmSEast);
		mnNavigation.add(mntmSWest);
		menuBar.add(mnPrefs);
		mnPrefs.add(mnchangeSize);
		mnchangeSize.add(mntmSize20);
		mnchangeSize.add(mntmSize40);
		mnchangeSize.add(mntmSize80);
		mnchangeSize.add(mntmSize160);
		mnchangeSize.add(mntmSize320);
		mnPrefs.add(mnLanguage);
		mnLanguage.add(mntmPortuguese);
		mnLanguage.add(mntmEnglish);
		menuBar.add(mnHelp);
		mnHelp.add(mntmManual);
		mnHelp.add(mntmAbout);
		// icons
		mnNew.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/plus.png")));
		mnImport.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/import.gif")));
		mntmExport.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/export.png")));
		mntmExit.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/exit.png")));
		mnLanguage.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/config.png")));
		mnchangeSize.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/size.png")));
		mntmPortuguese.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/brasil.png")));
		mntmEnglish.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/United-States.png")));
		mntmAbout.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/main16x16.png")));
		mntmManual.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/manual.png")));
		// menu fonts
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
		mnFile.setFont(controller.getBoldFont());
		mnImport.setFont(controller.getBoldFont());
		mnModels.setFont(controller.getBoldFont());
		mnPrefs.setFont(controller.getBoldFont());
		mnLanguage.setFont(controller.getBoldFont());
		mnHelp.setFont(controller.getBoldFont());
		mntmExport.setFont(controller.getBoldFont());
		mntmSearch.setFont(controller.getBoldFont());
		mntmManual.setFont(controller.getBoldFont());
		mntmAbout.setFont(controller.getBoldFont());
		mntmPortuguese.setFont(controller.getBoldFont());
		mntmEnglish.setFont(controller.getBoldFont());
		mntmExit.setFont(controller.getBoldFont());
		mnNavigation.setFont(controller.getBoldFont());
		mntmCenter.setFont(controller.getBoldFont());
		mntmNEast.setFont(controller.getBoldFont());
		mntmNWest.setFont(controller.getBoldFont());
		mntmSEast.setFont(controller.getBoldFont());
		mntmSWest.setFont(controller.getBoldFont());
		// shortcuts
		mntmCenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, true));
		mntmNEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true));
		mntmNWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true));
		mntmSEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, true));
		mntmSWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true));
		// disables a menu
		mntmExport.setEnabled(false);
		// actions
		mntmSize20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeVectorSize(20);
				fixScale(controller);
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		mntmSize40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeVectorSize(40);
				fixScale(controller);
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		mntmSize80.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeVectorSize(80);
				fixScale(controller);
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		mntmSize160.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeVectorSize(160);
				fixScale(controller);
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		mntmSize320.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.changeVectorSize(320);
				fixScale(controller);
				statesAmount = controller.countStates();
				updateTexts();
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
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmPortuguese.addActionListener(new ActionListener() {
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
		mntmAbout.addActionListener(new ActionListener() {
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

		JPanel statesPanel = new JPanel();
		statesPanel.setBounds(4, 186, 181, 284);
		configPanel.add(statesPanel);
		statesPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		statesPanel.setLayout(null);

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(1, 2, 111, 280);
		statesPanel.add(leftPanel);
		leftPanel.setLayout(null);
		lblPossibleStates.setHorizontalAlignment(SwingConstants.CENTER);

		lblPossibleStates.setBounds(3, 10, 105, 20);
		leftPanel.add(lblPossibleStates);
		lblPossibleStates.setFont(controller.getBoldFont());

		txtState1 = new JTextField();
		txtState1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checks if the name isn't longer than the size limit
				if (txtState1.getText().length() >= controller.getMaxStateNameSize()) {
					txtState1.setText(txtState1.getText().substring(0, 11));
				}
			}
		});
		txtState1.setFont(controller.getNormalFont());
		txtState1.setHorizontalAlignment(SwingConstants.CENTER);
		txtState1.setText(controller.getNameState1());
		txtState1.setBounds(5, 40, 100, 20);
		leftPanel.add(txtState1);

		txtState2 = new JTextField();
		txtState2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checks if the name isn't longer than the size limit
				if (txtState2.getText().length() >= controller.getMaxStateNameSize()) {
					txtState2.setText(txtState2.getText().substring(0, 11));
				}
			}
		});
		txtState2.setFont(controller.getNormalFont());
		txtState2.setText(controller.getNameState2());
		txtState2.setHorizontalAlignment(SwingConstants.CENTER);
		txtState2.setBounds(5, 70, 100, 20);
		leftPanel.add(txtState2);

		txtState3 = new JTextField();
		txtState3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// checks if the name isn't longer than the size limit
				if (txtState3.getText().length() >= controller.getMaxStateNameSize()) {
					txtState3.setText(txtState3.getText().substring(0, 11));
				}
			}
		});
		txtState3.setText(controller.getNameState3());
		txtState3.setFont(controller.getNormalFont());
		txtState3.setHorizontalAlignment(SwingConstants.CENTER);
		txtState3.setBounds(5, 100, 100, 20);
		leftPanel.add(txtState3);

		txtState4 = new JTextField();
		txtState4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checks if the name isn't longer than the size limit
				if (txtState4.getText().length() >= controller.getMaxStateNameSize()) {
					txtState4.setText(txtState4.getText().substring(0, 11));
				}
			}
		});
		txtState4.setFont(controller.getNormalFont());
		txtState4.setText(controller.getNameState4());
		txtState4.setHorizontalAlignment(SwingConstants.CENTER);
		txtState4.setBounds(5, 130, 100, 20);
		leftPanel.add(txtState4);

		txtState5 = new JTextField();
		txtState5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checks if the name isn't longer than the size limit
				if (txtState5.getText().length() >= controller.getMaxStateNameSize()) {
					txtState5.setText(txtState5.getText().substring(0, 11));
				}
			}
		});
		txtState5.setFont(controller.getNormalFont());
		txtState5.setText(controller.getNameState5());
		txtState5.setHorizontalAlignment(SwingConstants.CENTER);
		txtState5.setBounds(5, 160, 100, 20);
		leftPanel.add(txtState5);

		txtState6 = new JTextField();
		txtState6.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checks if the name isn't longer than the size limit
				if (txtState6.getText().length() >= controller.getMaxStateNameSize()) {
					txtState6.setText(txtState6.getText().substring(0, 11));
				}
			}
		});
		txtState6.setFont(controller.getNormalFont());
		txtState6.setText(controller.getNameState6());
		txtState6.setHorizontalAlignment(SwingConstants.CENTER);
		txtState6.setBounds(5, 190, 100, 20);
		leftPanel.add(txtState6);

		txtState7 = new JTextField();
		txtState7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// checks if the name isn't longer than the size limit
				if (txtState7.getText().length() >= controller.getMaxStateNameSize()) {
					txtState7.setText(txtState7.getText().substring(0, 11));
				}
			}
		});
		txtState7.setFont(controller.getNormalFont());
		txtState7.setText(controller.getNameState7());
		txtState7.setHorizontalAlignment(SwingConstants.CENTER);
		txtState7.setBounds(5, 220, 100, 20);
		leftPanel.add(txtState7);

		txtState8 = new JTextField();
		txtState8.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// checks if the name isn't longer than the size limit
				if (txtState8.getText().length() >= controller.getMaxStateNameSize()) {
					txtState8.setText(txtState8.getText().substring(0, 11));
				}
			}
		});
		txtState8.setFont(controller.getNormalFont());
		txtState8.setText(controller.getNameState8());
		txtState8.setHorizontalAlignment(SwingConstants.CENTER);
		txtState8.setBounds(5, 250, 100, 20);
		leftPanel.add(txtState8);

		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(113, 2, 66, 280);
		statesPanel.add(rightPanel);
		rightPanel.setLayout(null);

		possibleStatesComboBox = new JComboBox<String>();
		possibleStatesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// changes which JTextfields of state names are enabled based on
				// the possibleStatesComboBox selected amount
				int numberOfActiveStates = Integer.parseInt((String) possibleStatesComboBox.getSelectedItem());
				updateActiveStates(numberOfActiveStates);
				controller.setActiveStates(numberOfActiveStates);
			}
		});
		possibleStatesComboBox.setBounds(3, 10, 60, 20);
		rightPanel.add(possibleStatesComboBox);
		possibleStatesComboBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "2", "3", "4", "5", "6", "7", "8" }));
		possibleStatesComboBox.setSelectedIndex(controller.getActiveStates() - 2);

		lblColor1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				changeColor(0, controller);
			}
		});
		lblColor1.setBounds(3, 40, 60, 20);
		rightPanel.add(lblColor1);
		lblColor1.setOpaque(true);
		lblColor1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor1.setBackground(controller.getColor(1));

		lblColor2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				changeColor(1, controller);
			}
		});
		lblColor2.setBounds(3, 70, 60, 20);
		rightPanel.add(lblColor2);
		lblColor2.setOpaque(true);
		lblColor2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor2.setBackground(controller.getColor(2));

		lblColor3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(2, controller);
			}
		});
		lblColor3.setBounds(3, 100, 60, 20);
		rightPanel.add(lblColor3);
		lblColor3.setOpaque(true);
		lblColor3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor3.setBackground(controller.getColor(3));

		lblColor4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(3, controller);
			}
		});
		lblColor4.setBounds(3, 130, 60, 20);
		rightPanel.add(lblColor4);
		lblColor4.setOpaque(true);
		lblColor4.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor4.setBackground(controller.getColor(4));

		lblColor5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(4, controller);
			}
		});
		lblColor5.setBounds(3, 160, 60, 20);
		rightPanel.add(lblColor5);
		lblColor5.setOpaque(true);
		lblColor5.setBackground(controller.getColor(5));
		lblColor5.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblColor6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(5, controller);
			}
		});
		lblColor6.setBounds(3, 190, 60, 20);
		rightPanel.add(lblColor6);
		lblColor6.setOpaque(true);
		lblColor6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor6.setBackground(controller.getColor(6));

		lblColor7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(6, controller);
			}
		});
		lblColor7.setBounds(3, 220, 60, 20);
		rightPanel.add(lblColor7);
		lblColor7.setOpaque(true);
		lblColor7.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor7.setBackground(controller.getColor(7));

		lblColor8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor(7, controller);
			}
		});
		lblColor8.setBounds(3, 250, 60, 20);
		rightPanel.add(lblColor8);
		lblColor8.setOpaque(true);
		lblColor8.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblColor8.setBackground(controller.getColor(8));

		JPanel filePanel = new JPanel();
		filePanel.setBounds(4, 6, 181, 70);
		configPanel.add(filePanel);
		filePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		filePanel.setLayout(null);

		btnImport.setBounds(30, 10, 120, 20);
		filePanel.add(btnImport);
		btnImport.setFont(controller.getBoldFont());
		btnImport.setFocusable(false);
		btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		btnImport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExport.setBounds(30, 40, 120, 20);

		btnExport.setEnabled(false);
		filePanel.add(btnExport);
		btnExport.setFont(controller.getBoldFont());
		btnExport.setFocusable(false);
		btnExport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// checks if the name of the states are valid
				if (checkStateNames() == false) {
					// if they're invalid, show a message error
					if (controller.getLanguage() == 0) {
						infoTxtArea.setText("Não podem haver estados com nome vazio.");
					} else if (controller.getLanguage() == 1) {
						infoTxtArea.setText("There can be no empty state names.");
					}
				} else if (controller.areStatesValid(controller.getVector()) == false) {
					// if there are invalid states, show a message error
					if (controller.getLanguage() == 0) {
						infoTxtArea.setText("Provavelmente há algum estado inválido, remova-o para continuar.");
					} else if (controller.getLanguage() == 1) {
						infoTxtArea.setText("Probably there is an invalid state, remove it to proceed.");
					}
					// if everything is valid
				} else {
					// save the actual name of the states
					saveStateNames(controller);
					// instantiates the rules screen
					AppConfigRules rules = new AppConfigRules(controller);
					// show the rule screen
					rules.setVisible(true);
					// disposes the current screen
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

		lblScale.setHorizontalAlignment(SwingConstants.CENTER);
		lblScale.setFont(controller.getNormalFont());
		lblScale.setBounds(14, 70, 70, 16);
		ZoomPanel.add(lblScale);

		lblCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoord.setFont(controller.getNormalFont());
		lblCoord.setBounds(98, 70, 66, 16);
		ZoomPanel.add(lblCoord);
		btnClean.setBounds(8, 40, 120, 20);
		ZoomPanel.add(btnClean);

		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanData(controller);
			}
		});
		btnClean.setFocusable(false);
		btnClean.setFont(controller.getBoldFont());
		btnClean.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClean.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRandomize.setBounds(8, 10, 120, 20);
		ZoomPanel.add(btnRandomize);

		btnRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// generates a random population
				Random r = new Random();
				// the smaller, the more dense
				int density = (int) densitySpinner.getValue();
				// cleans the vector
				cleanData(controller);
				for (int i = 0; i < controller.getVector().length;) {
					for (int j = 0; j < controller.getVector().length;) {
						controller.getVector()[i][j] = r.nextInt(controller.getActiveStates());
						j += r.nextInt(density);
					}
					i += r.nextInt(density);
				}
				// redraws
				controller.drawMatriz(squares2);
				repaint();
				// update the population counters
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
				// if there is already a rule created
				if (controller.getRules().size() > 0) {
					// checks if the name of the states are valid
					if (checkStateNames() == false) {
						// if they're invalid, shows an error message
						if (controller.getLanguage() == 0) {
							infoTxtArea.setText("Não podem haver estados com nome vazio.");
						} else if (controller.getLanguage() == 1) {
							infoTxtArea.setText("There can be no empty state names.");
						}
					} else if (controller.areStatesValid(controller.getVector()) == false) {
						// if there are invalid states, show an error message
						if (controller.getLanguage() == 0) {
							infoTxtArea.setText(
									"São necessários pelo menos dois estados diferentes para continuar. Caso hajam estados inválidos, remova-os.");
						} else if (controller.getLanguage() == 1) {
							infoTxtArea.setText(
									"It takes at least two different states to continue. If there are invalid states, remove them.");
						}
						// if everything is valid
					} else {
						// saves the name of the states
						saveStateNames(controller);
						// instantiates the execution screen
						AppRun execution = new AppRun(controller);
						// turns the execution screen visible
						execution.setVisible(true);
						// disposes the current screen
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

		squares2 = new DrawSquare(controller.getSqrSize() * controller.getVectorSize());
		squares2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// ratio between the width of squares and the size of the matrix
				double value = (squares2.getWidth() * 1.0) / controller.getVectorSize();
				// x and y positions of the clicked place
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// it is possible to detect -1 in the borders
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// in the borders, its possible to detect values above the limit
				if (posX >= controller.getVectorSize()) {
					posX = controller.getVectorSize() - 1;
				}
				if (posY >= controller.getVectorSize()) {
					posY = controller.getVectorSize() - 1;
				}
				lblCoord.setText("[" + posY + ", " + posX + "]");
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// ratio between the width of squares and the size of the matrix
				double value = (squares2.getWidth() * 1.0) / controller.getVectorSize();
				// x and y positions of the clicked place
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// it is possible to detect -1 in the borders
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// in the borders, its possible to detect values above the limit
				if (posX >= controller.getVectorSize()) {
					posX = controller.getVectorSize() - 1;
				}
				if (posY >= controller.getVectorSize()) {
					posY = controller.getVectorSize() - 1;
				}
				// updates the coordinates label
				lblCoord.setText("[" + posY + ", " + posX + "]");
				// if the coordinate is different from the previous
				if (posX != x || posY != y) {
					// changes the vector in the clicked position
					if (SwingUtilities.isRightMouseButton(arg0)) {
						// if right mouse button was used, change to first state
						controller.getVector()[posY][posX] = 0;
					} else {
						// if left mouse button was used, change to the last
						// clicked state. If it were the first state, set to the
						// second
						if (state == 0) {
							state = 1;
						}
						controller.getVector()[posY][posX] = state;
					}

					// redraws the matrix
					controller.drawMatriz(squares2);
					repaint();
					// updates the saved position
					x = posX;
					y = posY;
					// updates the population counters
					statesAmount = controller.countStates();
					updateTexts();
				}

			}
		});
		squares2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// ratio between the width of squares and the size of the matrix
				double value = (squares2.getWidth() * 1.0) / controller.getVectorSize();
				// x and y positions of the clicked place
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// it is possible to detect -1 in the borders
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// in the borders, its possible to detect values above the limit
				if (posX >= controller.getVectorSize()) {
					posX = controller.getVectorSize() - 1;
				}
				if (posY >= controller.getVectorSize()) {
					posY = controller.getVectorSize() - 1;
				}
				// if right mouse button was used, change to first state
				if (SwingUtilities.isRightMouseButton(arg0)) {
					controller.getVector()[posY][posX] = 0;
				} else {// left mouse button used
					// quantidade de estados ativos
					int activeStates = Integer.parseInt((String) possibleStatesComboBox.getSelectedItem());
					// changes the vector at the clicked position
					controller.getVector()[posY][posX] = controller
							.getNextStateValue(controller.getVector()[posY][posX], activeStates);
					// saves the clicked position and the state
					x = posX;
					y = posY;
					state = controller.getVector()[posY][posX];
				}
				// redraws the matrix
				controller.drawMatriz(squares2);
				repaint();
				// updates the population counters
				statesAmount = controller.countStates();
				updateTexts();
			}
		});
		squares2.setBorder(new LineBorder(new Color(0, 0, 0)));

		scrollPane = new JScrollPane(squares2);
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				// determinates the middle of the scroll
				int middle;
				// position of the mouse
				Point mouseLoc = arg0.getPoint();
				// position of the view
				Point viewPort = scrollPane.getViewport().getViewPosition();
				Point positionAfterZoom;
				if (arg0.getWheelRotation() >= 0) {// zoom out
					// determinates the maximum zoom out
					int max = 4;
					if (controller.getVectorSize() == 20) {
						max = 32;
					} else if (controller.getVectorSize() == 40) {
						max = 16;
					} else if (controller.getVectorSize() == 80) {
						max = 8;
					} else if (controller.getVectorSize() == 160) {
						max = 4;
					} else if (controller.getVectorSize() == 320) {
						max = 2;
					}
					// checks the scale
					if (controller.getSqrSize() > max) {
						middle = (int) (scrollPane.getViewport().getExtentSize().getWidth());
						// new position after the zoom
						positionAfterZoom = new Point((viewPort.x - (middle - mouseLoc.x)) / 2,
								(viewPort.y - (middle - mouseLoc.y)) / 2);
						// changes the scale, size of the rectangles
						controller.setSqrSize(controller.getSqrSize() / 2);
						// updates the scroll bars
						squares2.setPrefSize(controller.getSqrSize() * controller.getVectorSize());
						// updates the scale text
						updateScale(controller);
						// redraws the grid
						controller.drawMatriz(squares2);
						repaint();
						// adjusts the visualization
						scrollPane.getViewport().setViewPosition(positionAfterZoom);
					}
				} else {// zoom in
					// checks the scale
					if (controller.getSqrSize() < 64) {
						middle = (int) (scrollPane.getViewport().getExtentSize().getWidth() * 0.25);
						// new position after zoom
						positionAfterZoom = new Point((viewPort.x - (middle - mouseLoc.x)) * 2,
								(viewPort.y - (middle - mouseLoc.y)) * 2);
						// changes the scale, size of the rectangles
						controller.setSqrSize(controller.getSqrSize() * 2);
						// updates the scroll bars
						squares2.setPrefSize(controller.getSqrSize() * controller.getVectorSize());
						// updates the scale text
						updateScale(controller);
						// redraws the grid
						controller.drawMatriz(squares2);
						repaint();
						// adjusts the visualization
						scrollPane.getViewport().setViewPosition(positionAfterZoom);
					}
				}
			}
		});
		scrollPane.setAutoscrolls(false);
		scrollPane.setBounds(203, 3, 644, 644);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(40);
		scrollPane.getVerticalScrollBar().setUnitIncrement(40);
		contentPane.add(scrollPane);
		// remove the scrolling by using the mouse wheel, to do not interfere
		// with the zoom
		scrollPane.removeMouseWheelListener(scrollPane.getMouseWheelListeners()[0]);

		// fix the scale, if needed
		fixScale(controller);
		// determinates the language
		setLanguage(controller.getLanguage(), controller);
		// updates the population counters
		statesAmount = controller.countStates();
		updateTexts();
		// determinates the population font colors
		updatePopBackground(controller);
	}
}
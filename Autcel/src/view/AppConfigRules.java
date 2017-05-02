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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.AppController;
import javax.swing.JScrollPane;

public class AppConfigRules extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCreateRule;
	private JPanel panelConfig = new JPanel();
	private JPanel panelActiveRules = new JPanel();
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> listOfRules = new JList<>(model);
	private ArrayList<int[]> rules = new ArrayList<>();
	private String[] center = { "Não", "Sim" };
	private String[] ruleList = { "--", "Conway's Game of Life", "Ulam's Crystals", "Rule 614" };
	private String outerExplanation = "";
	private JComboBox<String> refersTo = new JComboBox<>();
	private JComboBox<String> operator = new JComboBox<>();
	private JComboBox<String> amount = new JComboBox<>();
	private JComboBox<String> neighborState = new JComboBox<>();
	private JComboBox<String> newState = new JComboBox<>();
	private JComboBox<String> comboBoxNeighborwood = new JComboBox<String>();
	private JComboBox<String> comboCenterEven = new JComboBox<String>();
	private JTextArea infoTxtArea = new JTextArea();
	private JTextArea textRule = new JTextArea();
	private JMenu mnFile = new JMenu();
	private JMenu mnImport = new JMenu();
	private JMenu mnModels = new JMenu();
	private JMenu mnPrefs = new JMenu();
	private JMenu mnLanguage = new JMenu();
	private JMenu mnHelp = new JMenu();
	private JMenu mnNew = new JMenu();
	private JMenuItem mntmNew20 = new JMenuItem();
	private JMenuItem mntmNew40 = new JMenuItem();
	private JMenuItem mntmNew80 = new JMenuItem();
	private JMenuItem mntmNew160 = new JMenuItem();
	private JMenuItem mntmNew320 = new JMenuItem();
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
	private JButton btnNewRule = new JButton();
	private JButton btnCreateRule = new JButton();
	private JButton btnRemoveRule = new JButton();
	private JButton btnUp = new JButton();
	private JButton btnDown = new JButton();
	private JButton btnAdvance = new JButton();
	private JButton btnBack = new JButton();
	private JLabel lblNeighborwood = new JLabel();
	private JLabel lblActiveRules = new JLabel();
	private JLabel lblRefersTo = new JLabel();
	private JLabel lblNeighbor = new JLabel();
	private JLabel lblOperator = new JLabel();
	private JLabel lblAmount = new JLabel();
	private JLabel lblNewState = new JLabel();
	private JLabel lblRuleInProduction = new JLabel();
	private JLabel lblError = new JLabel();
	private JLabel lblCenterEven = new JLabel();
	private JLabel lblNeighborwoodType = new JLabel();
	private JLabel lblRuleType = new JLabel();
	private JLabel lblImportRules = new JLabel();
	private JLabel lblHelpOuter = new JLabel();

	/**
	 * Fills textRule with the rule in formal language based on the selected
	 * comboBoxes
	 * 
	 * Author: Madson
	 */
	private void updateRuleInProduction(AppController contr) {
		if (contr.getLanguage() == 0) {
			textRule.setText("Se no estado " + refersTo.getSelectedItem().toString()
					+ " e com quantidade de vizinhos no estado " + neighborState.getSelectedItem().toString() + " "
					+ operator.getSelectedItem().toString() + " " + amount.getSelectedItem().toString()
					+ ", então mude para o estado " + newState.getSelectedItem().toString() + ";");
		} else if (contr.getLanguage() == 1) {
			textRule.setText("If in state " + refersTo.getSelectedItem().toString()
					+ " and with amount of neighbors in state " + neighborState.getSelectedItem().toString() + " "
					+ operator.getSelectedItem().toString() + " " + amount.getSelectedItem().toString()
					+ ", then change to the state " + newState.getSelectedItem().toString() + ";");
		}
	}

	/**
	 * Converts the code of a rule to formal language
	 * 
	 * Author: Madson
	 * 
	 * @param code
	 *            5 digits array that represents a rule
	 * @return
	 */
	private String getRuleTextByCode(int[] code, AppController contr) {
		String text = "";
		if (contr.getLanguage() == 0) {
			text = "Se no estado " + refersTo.getItemAt(code[0]).toString() + " e com quantidade de vizinhos no estado "
					+ neighborState.getItemAt(code[3]).toString() + " " + operator.getItemAt(code[1]).toString() + " "
					+ amount.getItemAt(code[4]).toString() + ", então mude para o estado "
					+ newState.getItemAt(code[2]).toString() + ";";
		} else if (contr.getLanguage() == 1) {
			text = "If in state " + refersTo.getItemAt(code[0]).toString() + " and with amount of neighbors in state "
					+ neighborState.getItemAt(code[3]).toString() + " " + operator.getItemAt(code[1]).toString() + " "
					+ amount.getItemAt(code[4]).toString() + ", then change to the state "
					+ newState.getItemAt(code[2]).toString() + ";";
		}
		return text;
	}

	/**
	 * Updates the comboBox of amount of states
	 * 
	 * Author: Madson
	 */
	private void updateAmount(AppController contr) {
		int selected = comboBoxNeighborwood.getSelectedIndex();
		boolean outer;
		if (comboCenterEven.getSelectedIndex() == 0) {
			outer = false;
		} else {
			outer = true;
		}
		if (selected == 0 && outer == false) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m1.PNG")));
			amount.setModel(
					new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }));
		} else if (selected == 1 && outer == false) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m2.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8",
					"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
		} else if (selected == 2 && outer == false) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m3.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8",
					"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
					"26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
					"42", "43", "44", "45", "46", "47", "48" }));
		} else if (selected == 3 && outer == false) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/v1.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4" }));
		} else if (selected == 4 && outer == false) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/v2.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		} else if (selected == 5 && outer == false) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/v3.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8",
					"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
		} else if (selected == 0 && outer == true) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m1.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
		} else if (selected == 1 && outer == true) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m2.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
							"16", "17", "18", "19", "20", "21", "22", "23", "24", "25" }));
		} else if (selected == 2 && outer == true) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m3.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8",
					"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
					"26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
					"42", "43", "44", "45", "46", "47", "48", "49" }));
		} else if (selected == 3 && outer == true) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/v1.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5" }));
		} else if (selected == 4 && outer == true) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/v2.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));
		} else if (selected == 5 && outer == true) {
			lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/v3.PNG")));
			amount.setModel(new DefaultComboBoxModel<String>(
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
							"16", "17", "18", "19", "20", "21", "22", "23", "24", "25" }));
		}

		// checks the existing rules to see if any has an amount of neighbors
		// above the allowed
		boolean removed = false;
		for (int i = 0; i < rules.size(); i++) {
			if (rules.get(i)[4] >= amount.getModel().getSize()) {
				// removes from the rules model
				model.remove(i);
				rules.remove(i);
				i--;
				removed = true;
			}
		}

		if (removed) {
			// updates the model
			listOfRules.setModel(model);
			String error = "As regras com quantidade inválida de vizinhos foram removidas.";
			if (contr.getLanguage() == 1) {
				error = "The rules with invalid amount of neighbors have been removed.";
			}
			lblError.setText(error);
		}
	}

	/**
	 * Update the existing rules
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void updateCreatedRules(AppController controller) {
		// if there is any rule created
		if (rules.size() > 0) {
			// clean the JList model
			model.removeAllElements();
			// walks the rules, adding them to the model
			for (int i = 0; i < rules.size(); i++) {
				model.addElement(getRuleTextByCode(rules.get(i), controller));
			}
			listOfRules.setModel(model);
		}
	}

	/**
	 * Fills the JList with the rules in formal language and fills the array of
	 * rules according to the rules in the controller
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void configureJList(AppController controller) {
		// clean the ArrayList of rules and the JList model
		model.removeAllElements();
		rules.removeAll(rules);
		for (int i = 0; i < controller.getRules().size(); i++) {
			// fills the ArrayList of rules
			rules.add(controller.getRules().get(i));
			// fills the JList model and updates the list
			model.addElement(getRuleTextByCode(controller.getRules().get(i), controller));
		}
		listOfRules.setModel(model);
	}

	/**
	 * Configures the text of the label of the type of rule
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void setOuterTotalisticLabelText(AppController contr) {
		String[] text = { "Regra Totalista", "Regra Totalista Exterior" };
		if (contr.getLanguage() == 1) {
			text[0] = "Totalistic Rule";
			text[1] = "Outer Totalistic Rule";
		}
		if (contr.getOuterTotalistic() == 0) {
			lblRuleType.setText(text[0]);
		} else {
			lblRuleType.setText(text[1]);
		}
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
			setTitle("ESACEL: Regras");
			// menus
			mnFile.setText("Arquivo");
			mnImport.setText("Importar");
			mnModels.setText("Modelos");
			mnPrefs.setText("Preferências");
			mnLanguage.setText("Idioma");
			mntmSearch.setText("Procurar");
			mntmExport.setText("Exportar");
			mnNew.setText("Novo Autômato");
			mntmExit.setText("Sair");
			mntmPortuguese.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmAbout.setText("Sobre o ESACEL");
			mnHelp.setText("Ajuda");
			// buttons
			btnNewRule.setText("Nova Regra");
			btnCreateRule.setText("Criar Regra");
			btnRemoveRule.setText("Excluir");
			btnUp.setText("Acima");
			btnDown.setText("Abaixo");
			btnAdvance.setText("Execução");
			btnBack.setText("Configuração");
			// labels
			lblActiveRules.setText("Regras Ativas");
			lblRefersTo.setText("Refere-se ao");
			lblNeighbor.setText("Vizinho");
			lblOperator.setText("Operador");
			lblAmount.setText("Quantidade");
			lblNewState.setText("Novo Estado");
			lblRuleInProduction.setText("Regra em Produção");
			lblNeighborwood.setText("Tipo de Vizinhança");
			lblCenterEven.setText("O Centro é Incluso?");
			lblImportRules.setText("Importar Regras");
			// textArea
			infoTxtArea.setText(
					"Defina as regras do aut\u00F4mato. As regras seguem ordem de preced\u00EAncia de cima para baixo, logo, uma vez que uma regra seja validada para uma c\u00E9lula em um ciclo, as demais regras ser\u00E3o ignoradas para esta c\u00E9lula neste ciclo.");
			// comboBox
			center[0] = "Não";
			center[1] = "Sim";
			// text
			outerExplanation = "Define se o estado atual da célula é contabilizado na soma de estados dos vizinhos.";
		} else if (value == 1) {
			// title
			setTitle("ESACEL: Rules");
			// menus
			mnFile.setText("File");
			mnImport.setText("Import");
			mnModels.setText("Templates");
			mnPrefs.setText("Settings");
			mnLanguage.setText("Language");
			mntmSearch.setText("Search");
			mntmExport.setText("Export");
			mnNew.setText("New Automaton");
			mntmExit.setText("Exit");
			mntmPortuguese.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("User Manual");
			mntmAbout.setText("About ESACEL");
			mnHelp.setText("Help");
			// buttons
			btnNewRule.setText("New Rule");
			btnCreateRule.setText("Create Rule");
			btnRemoveRule.setText("Delete");
			btnUp.setText("Up");
			btnDown.setText("Down");
			btnAdvance.setText("Execution");
			btnBack.setText("Configuration");
			// labels
			lblActiveRules.setText("Active Rules");
			lblRefersTo.setText("Refers to");
			lblNeighbor.setText("Neighbor");
			lblOperator.setText("Operator");
			lblAmount.setText("Amount");
			lblNewState.setText("New State");
			lblRuleInProduction.setText("Rule in Production");
			lblNeighborwood.setText("Neighborwood Type");
			lblCenterEven.setText("Is the Center Even?");
			lblImportRules.setText("Import Rules");
			// textArea
			infoTxtArea.setText(
					"Define the rules of the automaton. The rules follow precedence order from top to bottom, so once a rule is validated for a cell in a cycle, the other rules are ignored for this cell in this cycle.");
			// comboBox
			center[0] = "No";
			center[1] = "Yes";
			// text
			outerExplanation = "Define if the current state of the cell is counted in the sum of neighbor states.";
		}
		// language independent
		mntmNew20.setText("20x20");
		mntmNew40.setText("40x40");
		mntmNew80.setText("80x80");
		mntmNew160.setText("160x160");
		mntmNew320.setText("320x320");
		// if there is a rule in production, updates it
		if (textRule.getText().length() > 0) {
			updateRuleInProduction(contr);
		}
		// updates the rules already created
		updateCreatedRules(contr);
		// updates the text of rule type
		setOuterTotalisticLabelText(contr);
		// update the comboBox of rule type
		comboCenterEven.setModel(new DefaultComboBoxModel<String>(center));
		// define which comboBox will be selected
		if (contr.getOuterTotalistic() == 0) {
			comboCenterEven.setSelectedIndex(0);
		} else {
			comboCenterEven.setSelectedIndex(1);
		}
		comboBoxNeighborwood.setSelectedIndex((contr.getNeighborwoodType()));
		// updates the type of rule tip
		lblHelpOuter.setToolTipText(outerExplanation);
		// clean the error label
		lblError.setText("");
	}

	/**
	 * Tries to import configurations. If have success, close the current screen
	 * and opens execution screen
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 *            controller
	 */
	private void importData(AppController contr) {
		// if succeeded
		if (contr.importData()) {
			// creates a new execution instance
			AppRun run = new AppRun(contr);
			// turns it visible
			run.setVisible(true);
			// disposes the current screen
			dispose();
		}
	}

	/**
	 * Configures the JComboBoxes model
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void configureModels(AppController controller) {
		// array that keeps the name of the possible states
		String[] nameOfPossibleStates = new String[controller.getActiveStates()];

		// array with the name of the 8 states
		String[] stateNames = new String[] { controller.getNameState1(), controller.getNameState2(),
				controller.getNameState3(), controller.getNameState4(), controller.getNameState5(),
				controller.getNameState6(), controller.getNameState7(), controller.getNameState8() };

		// fills the array with the name of the possible states
		for (int i = 0; i < nameOfPossibleStates.length; i++) {
			nameOfPossibleStates[i] = stateNames[i];
		}

		// creates a list with the possible operators
		String[] operators = new String[] { ">=", ">", "==", "!=", "<", "<=" };

		// defines the model of the JComboBoxes that will show the name of the
		// possible states
		refersTo.setModel(new DefaultComboBoxModel<String>(nameOfPossibleStates));
		neighborState.setModel(new DefaultComboBoxModel<String>(nameOfPossibleStates));
		newState.setModel(new DefaultComboBoxModel<String>(nameOfPossibleStates));
		operator.setModel(new DefaultComboBoxModel<String>(operators));
		comboCenterEven.setModel(new DefaultComboBoxModel<String>(center));

		JPanel panelComboBoxes = new JPanel();
		panelComboBoxes.setBounds(121, 49, 745, 51);
		panelCreateRule.add(panelComboBoxes);
		panelComboBoxes.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelComboBoxes.setLayout(null);
		panelComboBoxes.add(refersTo);
		panelComboBoxes.add(lblRefersTo);
		panelComboBoxes.add(operator);
		panelComboBoxes.add(lblOperator);
		panelComboBoxes.add(amount);
		panelComboBoxes.add(lblAmount);
		panelComboBoxes.add(neighborState);
		panelComboBoxes.add(lblNeighbor);
		panelComboBoxes.add(newState);
		panelComboBoxes.add(lblNewState);
		textRule.setBounds(121, 25, 745, 20);
		panelCreateRule.add(textRule);
		// defines which comboBox will be selected
		if (controller.getOuterTotalistic() == 0) {
			comboCenterEven.setSelectedIndex(0);
		} else {
			comboCenterEven.setSelectedIndex(1);
		}
		comboBoxNeighborwood.setSelectedIndex((controller.getNeighborwoodType()));
		updateAmount(controller);
		amount.setSelectedIndex(1);
		textRule.setText("");
	}

	public AppConfigRules(AppController controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppConfigRules.class.getResource("/img/main16x16.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 891, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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
		menuBar.add(mnPrefs);
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
		// disables a menu
		mntmExport.setEnabled(false);
		// actions
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

		JPanel panelRules = new JPanel();
		panelRules.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRules.setBounds(5, 4, 874, 584);
		contentPane.add(panelRules);
		panelRules.setLayout(null);
		refersTo.setBounds(30, 26, 120, 20);

		lblRefersTo.setBounds(40, 3, 100, 20);
		lblRefersTo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRefersTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefersTo.setFont(controller.getBoldFont());
		lblRefersTo.setFocusable(false);
		operator.setBounds(330, 26, 100, 20);

		lblOperator.setBounds(330, 3, 100, 20);
		lblOperator.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOperator.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperator.setFont(controller.getBoldFont());
		lblOperator.setFocusable(false);
		amount.setBounds(460, 26, 100, 20);

		lblAmount.setBounds(460, 3, 100, 20);
		lblAmount.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmount.setFont(controller.getBoldFont());
		lblAmount.setFocusable(false);
		neighborState.setBounds(180, 26, 120, 20);

		lblNeighbor.setBounds(190, 3, 100, 20);
		lblNeighbor.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNeighbor.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeighbor.setFont(controller.getBoldFont());
		lblNeighbor.setFocusable(false);
		newState.setBounds(590, 26, 120, 20);

		lblNewState.setBounds(600, 3, 100, 20);
		lblNewState.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewState.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewState.setFont(controller.getBoldFont());
		lblNewState.setFocusable(false);

		panelCreateRule = new JPanel();
		panelCreateRule.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCreateRule.setBounds(2, 129, 870, 104);
		panelRules.add(panelCreateRule);
		panelCreateRule.setLayout(null);
		btnNewRule.setBounds(3, 25, 115, 20);
		panelCreateRule.add(btnNewRule);

		btnNewRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// updates the rule in production
				updateRuleInProduction(controller);
			}
		});
		btnNewRule.setPreferredSize(new Dimension(90, 20));
		btnNewRule.setFont(controller.getBoldFont());
		btnNewRule.setFocusable(false);
		btnCreateRule.setBounds(3, 49, 115, 20);
		panelCreateRule.add(btnCreateRule);

		btnCreateRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// checks if there is any text in the rule in production field
				if (textRule.getText().length() > 0) {
					// adds the text to the JList model
					model.addElement(textRule.getText());
					listOfRules.setModel(model);
					// clean the rule in production field
					textRule.setText("");
					// adds the new rule to the ArrayList of rules
					rules.add(new int[] { refersTo.getSelectedIndex(), operator.getSelectedIndex(),
							newState.getSelectedIndex(), neighborState.getSelectedIndex(), amount.getSelectedIndex() });
				}
			}
		});
		btnCreateRule.setPreferredSize(new Dimension(90, 20));
		btnCreateRule.setFont(controller.getBoldFont());
		btnCreateRule.setFocusable(false);
		lblRuleInProduction.setBounds(121, 4, 745, 17);
		panelCreateRule.add(lblRuleInProduction);

		lblRuleInProduction.setHorizontalAlignment(SwingConstants.CENTER);
		lblRuleInProduction.setFont(controller.getBoldFont());

		textRule.setFont(controller.getNormalFont());
		textRule.setEditable(false);
		textRule.setText("");
		textRule.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelConfig.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelConfig.setBounds(2, 2, 870, 125);

		panelRules.add(panelConfig);
		panelConfig.setLayout(null);

		lblNeighborwoodType.setBounds(6, 6, 113, 113);
		panelConfig.add(lblNeighborwoodType);
		lblNeighborwoodType.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/m1.PNG")));

		String[] neighborwoodTypes = { "Moore               r=1", "Moore               r=2", "Moore               r=3",
				"von Neumann r=1", "von Neumann r=2", "von Neumann r=3" };
		comboBoxNeighborwood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateAmount(controller);
				controller.setNeighborwoodType(comboBoxNeighborwood.getSelectedIndex());
			}
		});
		comboBoxNeighborwood.setModel(new DefaultComboBoxModel<String>(neighborwoodTypes));
		comboBoxNeighborwood.setBounds(123, 48, 140, 20);
		comboBoxNeighborwood.setFont(controller.getNormalFont());
		panelConfig.add(comboBoxNeighborwood);

		lblNeighborwood.setHorizontalAlignment(SwingConstants.LEFT);
		lblNeighborwood.setBounds(123, 23, 125, 20);
		lblNeighborwood.setFont(controller.getBoldFont());
		panelConfig.add(lblNeighborwood);

		comboCenterEven.setFont(controller.getNormalFont());
		comboCenterEven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboCenterEven.getSelectedIndex() == 0) {
					controller.setOuterTotalistic(0);
				} else {
					controller.setOuterTotalistic(1);
				}
				setOuterTotalisticLabelText(controller);
				updateAmount(controller);
			}
		});
		comboCenterEven.setBounds(123, 99, 140, 20);

		panelConfig.add(comboCenterEven);
		lblCenterEven.setHorizontalAlignment(SwingConstants.LEFT);
		lblCenterEven.setFont(controller.getBoldFont());
		lblCenterEven.setBounds(123, 74, 125, 20);

		panelConfig.add(lblCenterEven);
		lblRuleType.setHorizontalAlignment(SwingConstants.CENTER);
		lblRuleType.setFont(controller.getBoldFont());
		lblRuleType.setBounds(129, 6, 731, 20);

		panelConfig.add(lblRuleType);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(493, 30, 1, 90);
		panelConfig.add(separator_1);

		lblImportRules.setHorizontalAlignment(SwingConstants.LEFT);
		lblImportRules.setFont(controller.getBoldFont());
		lblImportRules.setBounds(505, 74, 159, 20);
		panelConfig.add(lblImportRules);

		JComboBox<String> comboImportRules = new JComboBox<String>();
		comboImportRules.setModel(new DefaultComboBoxModel<>(ruleList));
		comboImportRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = comboImportRules.getSelectedIndex();
				if (selected == 1) {
					// sets to the Game of Life rules
					controller.setRules(controller.getGameOfLifeRules());
					comboBoxNeighborwood.setSelectedIndex(0);
					controller.setNeighborwoodType(0);
					comboCenterEven.setSelectedIndex(0);
					controller.setOuterTotalistic(0);
				} else if (selected == 2) {
					// sets to the Ulam Crystals rules
					controller.setRules(controller.getUlamCrystalsRules());
					comboBoxNeighborwood.setSelectedIndex(0);
					controller.setNeighborwoodType(0);
					comboCenterEven.setSelectedIndex(0);
					controller.setOuterTotalistic(0);
				} else if (selected == 3) {
					// sets to the Rule 614 rules
					controller.setRules(controller.getRule614());
					comboBoxNeighborwood.setSelectedIndex(3);
					controller.setNeighborwoodType(3);
					comboCenterEven.setSelectedIndex(1);
					controller.setOuterTotalistic(1);
				}
				// update the JList and its model and the ArrayList of rules
				configureJList(controller);
				// clean the text of the rule in production
				textRule.setText("");
				updateAmount(controller);
			}
		});
		comboImportRules.setFont(controller.getNormalFont());
		comboImportRules.setBounds(505, 99, 355, 20);
		panelConfig.add(comboImportRules);

		lblHelpOuter.setToolTipText(outerExplanation);
		lblHelpOuter.setIcon(new ImageIcon(AppConfigRules.class.getResource("/img/question.png")));
		lblHelpOuter.setBounds(247, 76, 16, 16);
		panelConfig.add(lblHelpOuter);

		panelActiveRules.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelActiveRules.setBounds(2, 235, 870, 345);
		panelRules.add(panelActiveRules);
		panelActiveRules.setLayout(null);

		JPanel panelButtons = new JPanel();
		panelButtons.setBounds(1, 27, 120, 75);
		panelActiveRules.add(panelButtons);
		panelButtons.setLayout(null);
		btnDown.setBounds(3, 50, 115, 20);
		panelButtons.add(btnDown);

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// receives the index of the selected rule
				int pos = listOfRules.getSelectedIndex();
				// if there is a rule selected and it is not the last one
				if ((pos > -1) && (pos < (model.size() - 1))) {
					// does the swap in the model and updates the JList
					String temp = model.getElementAt(pos + 1);
					model.set(pos + 1, model.getElementAt(pos));
					model.set(pos, temp);
					// keeps the moved rule selected
					listOfRules.setSelectedIndex(pos + 1);
					// does the swap in the ArrayList of rules
					controller.swapAt(pos, pos + 1, rules);
				}
			}
		});
		btnDown.setPreferredSize(new Dimension(90, 20));
		btnDown.setFont(controller.getBoldFont());
		btnDown.setFocusable(false);
		btnUp.setBounds(3, 25, 115, 20);
		panelButtons.add(btnUp);

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// receives the index of the selected rule
				int pos = listOfRules.getSelectedIndex();
				// if the selected rule is not the first
				if (pos > 0) {
					// does the swap in the model and updates the JList
					String temp = model.getElementAt(pos - 1);
					model.set(pos - 1, model.getElementAt(pos));
					model.set(pos, temp);
					// keeps the moved rule selected
					listOfRules.setSelectedIndex(pos - 1);
					// does the swap in the ArrayList of rules
					controller.swapAt(pos, pos - 1, rules);
				}
			}
		});
		btnUp.setPreferredSize(new Dimension(90, 20));
		btnUp.setFont(controller.getBoldFont());
		btnUp.setFocusable(false);
		btnRemoveRule.setBounds(3, 0, 115, 20);
		panelButtons.add(btnRemoveRule);

		btnRemoveRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// receives the index of the selected rule
				int pos = listOfRules.getSelectedIndex();
				// if there is any selected
				if (pos > -1) {
					// remove from the model and updates the JList
					model.remove(pos);
					listOfRules.setModel(model);
					// remove from the ArrayList of rules
					rules.remove(pos);
				}
			}
		});
		btnRemoveRule.setPreferredSize(new Dimension(90, 20));
		btnRemoveRule.setFont(controller.getBoldFont());
		btnRemoveRule.setFocusable(false);
		lblActiveRules.setBounds(121, 5, 745, 17);
		panelActiveRules.add(lblActiveRules);

		lblActiveRules.setHorizontalAlignment(SwingConstants.CENTER);
		lblActiveRules.setFont(controller.getBoldFont());

		listOfRules = new JList<String>();
		listOfRules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfRules.setFont(controller.getBoldFont());
		listOfRules.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPane = new JScrollPane(listOfRules);
		scrollPane.setBounds(121, 27, 745, 290);
		panelActiveRules.add(scrollPane);
		lblError.setBounds(121, 322, 745, 18);
		panelActiveRules.add(lblError);
		lblError.setFont(controller.getNormalFont());
		lblError.setForeground(Color.RED);

		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMenu.setBounds(5, 592, 874, 54);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);

		btnAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if there is at least one rule
				if (rules.size() > 0) {
					// saves the rules
					controller.setRules(rules);
					// instantiates the execution screen
					AppRun exe = new AppRun(controller);
					// turns it visible
					exe.setVisible(true);
					// dispose the current screen
					dispose();
				} else {
					if (controller.getLanguage() == 0) {
						lblError.setText("É necessário ter pelo menos uma regra ativa para continuar.");
					} else if (controller.getLanguage() == 1) {
						lblError.setText("You must have at least one active rule to continue.");
					}
				}
			}
		});
		btnAdvance.setPreferredSize(new Dimension(90, 20));
		btnAdvance.setFont(controller.getBoldFont());
		btnAdvance.setFocusable(false);
		btnAdvance.setBounds(714, 17, 155, 20);
		panelMenu.add(btnAdvance);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if there is at least one rule, save the rules
				if (rules.size() > 0) {
					controller.setRules(rules);
				}
				// instantiates the configurations screen
				AppConfigAutomaton config = new AppConfigAutomaton(controller);
				// turns it visible
				config.setVisible(true);
				// disposes the current screen
				dispose();
			}
		});
		btnBack.setPreferredSize(new Dimension(90, 20));
		btnBack.setFont(controller.getBoldFont());
		btnBack.setFocusable(false);
		btnBack.setBounds(5, 17, 155, 20);
		panelMenu.add(btnBack);

		infoTxtArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		infoTxtArea.setWrapStyleWord(true);
		infoTxtArea.setOpaque(false);
		infoTxtArea.setLineWrap(true);
		infoTxtArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		infoTxtArea.setEditable(false);
		infoTxtArea.setBounds(165, 3, 544, 48);
		panelMenu.add(infoTxtArea);

		// updates the text of the rule in production if the JComboBox is
		// changed
		refersTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction(controller);
			}
		});
		// updates the text of the rule in production if the JComboBox is
		// changed
		amount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction(controller);
			}
		});
		// updates the text of the rule in production if the JComboBox is
		// changed
		neighborState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateRuleInProduction(controller);
			}
		});
		// updates the text of the rule in production if the JComboBox is
		// changed
		newState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction(controller);
			}
		});
		// updates the text of the rule in production if the JComboBox is
		// changed
		operator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateRuleInProduction(controller);
			}
		});
		// configures the model of all JComboBoxes
		configureModels(controller);
		// configures the model of the JList
		configureJList(controller);
		// sets the language
		setLanguage(controller.getLanguage(), controller);
	}
}

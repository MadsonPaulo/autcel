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

public class AppConfigRules extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> listaRegras = new JList<>(model);
	private ArrayList<int[]> regras = new ArrayList<>();
	private JComboBox<String> refere = new JComboBox<>();
	private JComboBox<String> operador = new JComboBox<>();
	private JComboBox<String> quantidade = new JComboBox<>();
	private JComboBox<String> estadoVizinho = new JComboBox<>();
	private JComboBox<String> novoEstado = new JComboBox<>();
	private JTextArea infoTxtArea = new JTextArea();
	private JTextArea textRegra = new JTextArea();
	private JMenu mnArquivo = new JMenu();
	private JMenu mnImportar = new JMenu();
	private JMenu mnModelos = new JMenu();
	private JMenu mnPrerncias = new JMenu();
	private JMenu mnIdioma = new JMenu();
	private JMenu menuAjuda = new JMenu();
	private JMenuItem mntmExportar = new JMenuItem();
	private JMenuItem mntmProcurar = new JMenuItem();
	private JMenuItem mntmManual = new JMenuItem();
	private JMenuItem mntmSobre = new JMenuItem();
	private JMenuItem mntmPortugus = new JMenuItem();
	private JMenuItem mntmEnglish = new JMenuItem();
	private JMenuItem mntmSair = new JMenuItem();
	private JButton btnDefinirParaJogo = new JButton();
	private JButton btnDefinirParaCristais = new JButton();
	private JButton btnNovaRegra = new JButton();
	private JButton btnCriarRegra = new JButton();
	private JButton btnRemoverRegra = new JButton();
	private JButton btnAcima = new JButton();
	private JButton btnAbaixo = new JButton();
	private JButton btnAvancar = new JButton();
	private JButton btnVoltar = new JButton();
	private JLabel lblDefinirRegras = new JLabel();
	private JLabel lblRegrasAtivas = new JLabel();
	private JLabel lblRefereseAo = new JLabel();
	private JLabel lblVizinho = new JLabel();
	private JLabel lblOperador = new JLabel();
	private JLabel lblQtd = new JLabel();
	private JLabel lblNovoEstado = new JLabel();
	private JLabel lblRegraEmProduo = new JLabel();
	private JLabel lblErro = new JLabel();

	/**
	 * Preenche textRegra com a regra em linguagem formal de acordo com o estado
	 * atual dos JComboBoxes
	 * 
	 * Author: Madson
	 */
	private void updateRuleInProduction(AppController contr) {
		if (contr.getLanguage() == 0) {
			textRegra.setText("Se no estado " + refere.getSelectedItem().toString()
					+ " e com quantidade de vizinhos no estado " + estadoVizinho.getSelectedItem().toString() + " "
					+ operador.getSelectedItem().toString() + " " + quantidade.getSelectedItem().toString()
					+ ", então mude para o estado " + novoEstado.getSelectedItem().toString() + ";");
		} else if (contr.getLanguage() == 1) {
			textRegra.setText("If in state " + refere.getSelectedItem().toString()
					+ " and with amount of neighbors in state " + estadoVizinho.getSelectedItem().toString() + " "
					+ operador.getSelectedItem().toString() + " " + quantidade.getSelectedItem().toString()
					+ ", then change to the state " + novoEstado.getSelectedItem().toString() + ";");
		}
	}

	/**
	 * Transforma um array de inteiros relativo a uma regra, na regra em
	 * linguagem formal
	 * 
	 * Author: Madson
	 * 
	 * @param code
	 *            array de 6 inteiros, que representa uma regra
	 * @return regra em linguagem formal
	 */
	private String getRuleTextByCode(int[] code, AppController contr) {
		String text = "";
		if (contr.getLanguage() == 0) {
			text = "Se no estado " + refere.getItemAt(code[0]).toString() + " e com quantidade de vizinhos no estado "
					+ estadoVizinho.getItemAt(code[3]).toString() + " " + operador.getItemAt(code[1]).toString() + " "
					+ quantidade.getItemAt(code[2]).toString() + ", então mude para o estado "
					+ novoEstado.getItemAt(code[4]).toString() + ";";
		} else if (contr.getLanguage() == 1) {
			text = "If in state " + refere.getItemAt(code[0]).toString() + " and with amount of neighbors in state "
					+ estadoVizinho.getItemAt(code[3]).toString() + " " + operador.getItemAt(code[1]).toString() + " "
					+ quantidade.getItemAt(code[2]).toString() + ", then change to the state "
					+ novoEstado.getItemAt(code[4]).toString() + ";";
		}
		return text;
	}

	/**
	 * Atualiza as regras já criadas
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void updateCreatedRules(AppController controller) {
		// se houver alguma regra criada
		if (regras.size() > 0) {
			// limpa o model da JList
			model.removeAllElements();
			// percorre regras, adicionando-as ao model
			for (int i = 0; i < regras.size(); i++) {
				model.addElement(getRuleTextByCode(regras.get(i), controller));
			}
			listaRegras.setModel(model);
		}
	}

	/**
	 * Preenche a JList com as regras em linguagem formal e preenche o array
	 * regras, ambos de acordo com a matriz de regras salva no controller
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void configureJList(AppController controller) {
		// limpa o ArrayList regras e o model da JList
		model.removeAllElements();
		regras.removeAll(regras);
		for (int i = 0; i < controller.getRegras().size(); i++) {
			// preenche o ArrayList regras
			regras.add(controller.getRegras().get(i));
			// preenche o model da JList e atualiza a JList
			model.addElement(getRuleTextByCode(controller.getRegras().get(i), controller));
		}
		listaRegras.setModel(model);
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
			setTitle("Autcel: Regras");
			// menus
			mnArquivo.setText("Arquivo");
			mnImportar.setText("Importar");
			mnModelos.setText("Modelos");
			mnPrerncias.setText("Preferências");
			mnIdioma.setText("Idioma");
			mntmProcurar.setText("Procurar");
			mntmExportar.setText("Exportar");
			mntmSair.setText("Sair");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmSobre.setText("Sobre o Autcel");
			menuAjuda.setText("Ajuda");
			// botões
			btnDefinirParaJogo.setText("Jogo da Vida");
			btnDefinirParaCristais.setText("Cristais de Ulam");
			btnNovaRegra.setText("Nova Regra");
			btnCriarRegra.setText("Criar Regra");
			btnRemoverRegra.setText("Excluir");
			btnAcima.setText("Acima");
			btnAbaixo.setText("Abaixo");
			btnAvancar.setText("Execução");
			btnVoltar.setText("Configuração");
			// labels
			lblDefinirRegras.setText("Definir Regras");
			lblRegrasAtivas.setText("Regras Ativas");
			lblRefereseAo.setText("Refere-se ao");
			lblVizinho.setText("Vizinho");
			lblOperador.setText("Operador");
			lblQtd.setText("Quantidade");
			lblNovoEstado.setText("Novo Estado");
			lblRegraEmProduo.setText("Regra em Produção");
			// textArea
			infoTxtArea.setText(
					"Defina as regras do aut\u00F4mato. As regras seguem ordem de preced\u00EAncia de cima para baixo, logo, uma vez que uma regra seja validada para uma c\u00E9lula em um ciclo, as demais regras ser\u00E3o ignoradas para esta c\u00E9lula neste ciclo.");
		} else if (value == 1) {
			// título
			setTitle("Autcel: Rules");
			// menus
			mnArquivo.setText("File");
			mnImportar.setText("Import");
			mnModelos.setText("Templates");
			mnPrerncias.setText("Settings");
			mnIdioma.setText("Language");
			mntmProcurar.setText("Search");
			mntmExportar.setText("Export");
			mntmSair.setText("Exit");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("User Manual");
			mntmSobre.setText("About Autcel");
			menuAjuda.setText("Help");
			// botões
			btnDefinirParaJogo.setText("Game of Life");
			btnDefinirParaCristais.setText("Ulam Crystals");
			btnNovaRegra.setText("New Rule");
			btnCriarRegra.setText("Create Rule");
			btnRemoverRegra.setText("Delete");
			btnAcima.setText("Up");
			btnAbaixo.setText("Down");
			btnAvancar.setText("Execution");
			btnVoltar.setText("Configuration");
			// labels
			lblDefinirRegras.setText("Set Rules");
			lblRegrasAtivas.setText("Active Rules");
			lblRefereseAo.setText("Refers to");
			lblVizinho.setText("Neighbor");
			lblOperador.setText("Operator");
			lblQtd.setText("Amount");
			lblNovoEstado.setText("New State");
			lblRegraEmProduo.setText("Rule in Production");
			// textArea
			infoTxtArea.setText(
					"Define the rules of the automaton. The rules follow precedence order from top to bottom, so once a rule is validated for a cell in a cycle, the other rules are ignored for this cell in this cycle.");
		}
		// se houver alguma regra em produção, atualiza-a
		if (textRegra.getText().length() > 0) {
			updateRuleInProduction(contr);
		}
		// atualiza as regras já criadas
		updateCreatedRules(contr);
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
	 * Configura o model dos JComboBoxes
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void configureModels(AppController controller) {
		// array que guarda o nome dos estados possíveis
		String[] nomeEstadosPossiveis = new String[controller.getActiveStates()];

		// Array com o nome dos 8 estados possíveis
		String[] nomeEstados = new String[] { controller.getNameState1(), controller.getNameState2(),
				controller.getNameState3(), controller.getNameState4(), controller.getNameState5(),
				controller.getNameState6(), controller.getNameState7(), controller.getNameState8() };

		// preenche o array com o nome dos estados possíveis
		for (int i = 0; i < nomeEstadosPossiveis.length; i++) {
			nomeEstadosPossiveis[i] = nomeEstados[i];
		}

		// define o model dos JComboBox que apresentarão nomes de estados
		// possíveis
		refere.setModel(new DefaultComboBoxModel<String>(nomeEstadosPossiveis));
		estadoVizinho.setModel(new DefaultComboBoxModel<String>(nomeEstadosPossiveis));
		novoEstado.setModel(new DefaultComboBoxModel<String>(nomeEstadosPossiveis));

		// cria a lista dos operadores possíveis e define o model do comboBox
		String[] operadores = new String[] { ">=", ">", "==", "<", "<=" };
		operador.setModel(new DefaultComboBoxModel<String>(operadores));

		// cria a lista das quantidades de vizinho possíveis e define o model do
		// comboBox
		String[] quantidades = new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };
		quantidade.setModel(new DefaultComboBoxModel<String>(quantidades));
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

		// cria a barra de menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// separator
		JSeparator separator = new JSeparator();
		// inclusões
		menuBar.add(mnArquivo);
		mnArquivo.add(mnImportar);
		mnImportar.add(mntmProcurar);
		mnImportar.add(mnModelos);
		mnArquivo.add(mntmExportar);
		mnArquivo.add(separator);
		mnArquivo.add(mntmSair);
		menuBar.add(mnPrerncias);
		mnPrerncias.add(mnIdioma);
		mnIdioma.add(mntmPortugus);
		mnIdioma.add(mntmEnglish);
		menuBar.add(menuAjuda);
		menuAjuda.add(mntmManual);
		menuAjuda.add(mntmSobre);
		// ícones
		mnImportar.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/import.gif")));
		mntmExportar.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/export.png")));
		mntmSair.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/exit.png")));
		mnIdioma.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/config.png")));
		mntmPortugus.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/brasil.png")));
		mntmEnglish.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/United-States.png")));
		mntmSobre.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/main16x16.png")));
		mntmManual.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/manual.png")));
		// fonte dos menus
		mnArquivo.setFont(controller.getBoldFont());
		mnImportar.setFont(controller.getBoldFont());
		mnModelos.setFont(controller.getBoldFont());
		mnPrerncias.setFont(controller.getBoldFont());
		mnIdioma.setFont(controller.getBoldFont());
		menuAjuda.setFont(controller.getBoldFont());
		mntmExportar.setFont(controller.getBoldFont());
		mntmProcurar.setFont(controller.getBoldFont());
		mntmManual.setFont(controller.getBoldFont());
		mntmSobre.setFont(controller.getBoldFont());
		mntmPortugus.setFont(controller.getBoldFont());
		mntmEnglish.setFont(controller.getBoldFont());
		mntmSair.setFont(controller.getBoldFont());
		// desabilita um menu
		mntmExportar.setEnabled(false);
		// ações
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

		JPanel panelRegras = new JPanel();
		panelRegras.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRegras.setBounds(5, 57, 874, 530);
		contentPane.add(panelRegras);
		panelRegras.setLayout(null);

		textRegra.setFont(controller.getNormalFont());
		textRegra.setEditable(false);
		textRegra.setText("");
		textRegra.setBorder(new LineBorder(new Color(0, 0, 0)));
		textRegra.setBounds(123, 29, 745, 20);
		panelRegras.add(textRegra);

		lblRegraEmProduo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegraEmProduo.setFont(controller.getBoldFont());
		lblRegraEmProduo.setBounds(123, 7, 745, 17);
		panelRegras.add(lblRegraEmProduo);

		JPanel panelCombos = new JPanel();
		panelCombos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCombos.setBounds(123, 53, 745, 51);
		panelRegras.add(panelCombos);
		panelCombos.setLayout(null);
		refere.setBounds(30, 26, 120, 20);
		panelCombos.add(refere);

		lblRefereseAo.setBounds(40, 3, 100, 20);
		panelCombos.add(lblRefereseAo);
		lblRefereseAo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRefereseAo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefereseAo.setFont(controller.getBoldFont());
		lblRefereseAo.setFocusable(false);
		operador.setBounds(330, 26, 100, 20);
		panelCombos.add(operador);

		lblOperador.setBounds(330, 3, 100, 20);
		panelCombos.add(lblOperador);
		lblOperador.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOperador.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperador.setFont(controller.getBoldFont());
		lblOperador.setFocusable(false);
		quantidade.setBounds(460, 26, 100, 20);
		panelCombos.add(quantidade);

		lblQtd.setBounds(460, 3, 100, 20);
		panelCombos.add(lblQtd);
		lblQtd.setHorizontalTextPosition(SwingConstants.CENTER);
		lblQtd.setHorizontalAlignment(SwingConstants.CENTER);
		lblQtd.setFont(controller.getBoldFont());
		lblQtd.setFocusable(false);
		estadoVizinho.setBounds(180, 26, 120, 20);
		panelCombos.add(estadoVizinho);

		lblVizinho.setBounds(190, 3, 100, 20);
		panelCombos.add(lblVizinho);
		lblVizinho.setHorizontalTextPosition(SwingConstants.CENTER);
		lblVizinho.setHorizontalAlignment(SwingConstants.CENTER);
		lblVizinho.setFont(controller.getBoldFont());
		lblVizinho.setFocusable(false);
		novoEstado.setBounds(590, 26, 120, 20);
		panelCombos.add(novoEstado);

		lblNovoEstado.setBounds(600, 3, 100, 20);
		panelCombos.add(lblNovoEstado);
		lblNovoEstado.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNovoEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovoEstado.setFont(controller.getBoldFont());
		lblNovoEstado.setFocusable(false);

		btnNovaRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// atualiza a regra que está sendo criada
				updateRuleInProduction(controller);
			}
		});
		btnNovaRegra.setPreferredSize(new Dimension(90, 20));
		btnNovaRegra.setFont(controller.getBoldFont());
		btnNovaRegra.setFocusable(false);
		btnNovaRegra.setBounds(4, 29, 115, 20);
		panelRegras.add(btnNovaRegra);

		btnCriarRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// checa se há algum texto no campo de regra em produção
				if (textRegra.getText().length() > 0) {
					// adiciona o texto ao model da JList
					model.addElement(textRegra.getText());
					listaRegras.setModel(model);
					// limpa o campo da regra em produção
					textRegra.setText("");
					// adiciona a nova regra ao ArrayList de regras
					regras.add(new int[] { refere.getSelectedIndex(), operador.getSelectedIndex(),
							quantidade.getSelectedIndex(), estadoVizinho.getSelectedIndex(),
							novoEstado.getSelectedIndex() });
				}
			}
		});
		btnCriarRegra.setPreferredSize(new Dimension(90, 20));
		btnCriarRegra.setFont(controller.getBoldFont());
		btnCriarRegra.setFocusable(false);
		btnCriarRegra.setBounds(4, 53, 115, 20);
		panelRegras.add(btnCriarRegra);

		listaRegras = new JList<String>();
		listaRegras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaRegras.setFont(controller.getBoldFont());
		listaRegras.setBorder(new LineBorder(new Color(0, 0, 0)));
		listaRegras.setBounds(123, 175, 745, 326);
		panelRegras.add(listaRegras);

		lblRegrasAtivas.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegrasAtivas.setFont(controller.getBoldFont());
		lblRegrasAtivas.setBounds(123, 151, 745, 17);
		panelRegras.add(lblRegrasAtivas);

		btnRemoverRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pos recebe a posição da regra selecionada
				int pos = listaRegras.getSelectedIndex();
				// se há alguma selecionada
				if (pos > -1) {
					// remove do model e atualiza o JList
					model.remove(pos);
					listaRegras.setModel(model);
					// remove do ArrayList regras
					regras.remove(pos);
				}
			}
		});
		btnRemoverRegra.setPreferredSize(new Dimension(90, 20));
		btnRemoverRegra.setFont(controller.getBoldFont());
		btnRemoverRegra.setFocusable(false);
		btnRemoverRegra.setBounds(4, 175, 115, 20);
		panelRegras.add(btnRemoverRegra);

		btnAcima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pos recebe a posição da regra selecionada
				int pos = listaRegras.getSelectedIndex();
				// se a regra selecionada não for a primeira
				if (pos > 0) {
					// Faz a troca no model e atualiza a JList
					String temp = model.getElementAt(pos - 1);
					model.set(pos - 1, model.getElementAt(pos));
					model.set(pos, temp);
					// mantêm selecionado o item movido
					listaRegras.setSelectedIndex(pos - 1);
					// Faz a troca no ArrayList regras
					controller.swapAt(pos, pos - 1, regras);
				}
			}
		});
		btnAcima.setPreferredSize(new Dimension(90, 20));
		btnAcima.setFont(controller.getBoldFont());
		btnAcima.setFocusable(false);
		btnAcima.setBounds(4, 199, 115, 20);
		panelRegras.add(btnAcima);

		btnAbaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pos recebe a posição da regra selecionada
				int pos = listaRegras.getSelectedIndex();
				// se houver regra selecionada e não for a última
				if ((pos > -1) && (pos < (model.size() - 1))) {
					// Faz a troca no model e atualiza a JList
					String temp = model.getElementAt(pos + 1);
					model.set(pos + 1, model.getElementAt(pos));
					model.set(pos, temp);
					// mantêm selecionado o item movido
					listaRegras.setSelectedIndex(pos + 1);
					// Faz a troca no ArrayList regras
					controller.swapAt(pos, pos + 1, regras);
				}
			}
		});
		btnAbaixo.setPreferredSize(new Dimension(90, 20));
		btnAbaixo.setFont(controller.getBoldFont());
		btnAbaixo.setFocusable(false);
		btnAbaixo.setBounds(4, 223, 115, 20);
		panelRegras.add(btnAbaixo);

		lblErro.setFont(controller.getBoldFont());
		lblErro.setForeground(Color.RED);
		lblErro.setBounds(123, 506, 745, 18);
		panelRegras.add(lblErro);

		JPanel panelModelos = new JPanel();
		panelModelos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelModelos.setBounds(5, 3, 874, 51);
		contentPane.add(panelModelos);
		panelModelos.setLayout(null);

		lblDefinirRegras.setBounds(379, 6, 115, 17);
		panelModelos.add(lblDefinirRegras);
		lblDefinirRegras.setHorizontalAlignment(SwingConstants.CENTER);
		lblDefinirRegras.setFont(controller.getBoldFont());

		btnDefinirParaJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// define as regras para o Jogo da Vida
				controller.setRegras(controller.getRegrasJogoDaVida());
				// atualiza o JList e seu model, e o ArrayList regras
				configureJList(controller);
				// limpa o texto da regra em produção
				textRegra.setText("");
			}
		});
		btnDefinirParaJogo.setPreferredSize(new Dimension(90, 20));
		btnDefinirParaJogo.setFont(controller.getBoldFont());
		btnDefinirParaJogo.setFocusable(false);
		btnDefinirParaJogo.setBounds(204, 22, 130, 20);
		panelModelos.add(btnDefinirParaJogo);

		btnDefinirParaCristais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// define as regras para os Cristais de Ulam
				controller.setRegras(controller.getRegrasCristaisDeUlam());
				// atualiza o JList e seu model, e o ArrayList regras
				configureJList(controller);
				// limpa o texto da regra em produção
				textRegra.setText("");
			}
		});
		btnDefinirParaCristais.setPreferredSize(new Dimension(90, 20));
		btnDefinirParaCristais.setFont(controller.getBoldFont());
		btnDefinirParaCristais.setFocusable(false);
		btnDefinirParaCristais.setBounds(538, 22, 130, 20);
		panelModelos.add(btnDefinirParaCristais);

		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMenu.setBounds(5, 590, 874, 54);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);

		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Se houver pelo menos uma regra
				if (regras.size() > 0) {
					// salva o estado atual das regras
					controller.setRegras(regras);
					// instancia a janela de execução
					AppRun exe = new AppRun(controller);
					// torna a janela de execução visível
					exe.setVisible(true);
					// encerra a janela atual
					dispose();
				} else {
					if (controller.getLanguage() == 0) {
						lblErro.setText("É necessário ter pelo menos uma regra ativa para continuar.");
					} else if (controller.getLanguage() == 1) {
						lblErro.setText("You must have at least one active rule to continue.");
					}
				}
			}
		});
		btnAvancar.setPreferredSize(new Dimension(90, 20));
		btnAvancar.setFont(controller.getBoldFont());
		btnAvancar.setFocusable(false);
		btnAvancar.setBounds(714, 17, 155, 20);
		panelMenu.add(btnAvancar);

		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Salva as regras se existir ao menos uma
				if (regras.size() > 0) {
					controller.setRegras(regras);
				}
				// instancia a janela de configuração
				AppConfigAutomaton config = new AppConfigAutomaton(controller);
				// torna a janela de configuração visivel
				config.setVisible(true);
				// encerra a janela atual
				dispose();
			}
		});
		btnVoltar.setPreferredSize(new Dimension(90, 20));
		btnVoltar.setFont(controller.getBoldFont());
		btnVoltar.setFocusable(false);
		btnVoltar.setBounds(5, 17, 155, 20);
		panelMenu.add(btnVoltar);

		infoTxtArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		infoTxtArea.setWrapStyleWord(true);
		infoTxtArea.setOpaque(false);
		infoTxtArea.setLineWrap(true);
		infoTxtArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		infoTxtArea.setEditable(false);
		infoTxtArea.setBounds(165, 3, 544, 48);
		panelMenu.add(infoTxtArea);

		// altera o texto da regra em produção, caso o JComboBox seja alterado
		refere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction(controller);
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		quantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction(controller);
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		estadoVizinho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateRuleInProduction(controller);
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		novoEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction(controller);
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		operador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateRuleInProduction(controller);
			}
		});

		// configura o model de todos os JComboBox
		configureModels(controller);
		// configura o model da JList
		configureJList(controller);
		// determina o idioma
		setLanguage(controller.getLanguage(), controller);
	}
}

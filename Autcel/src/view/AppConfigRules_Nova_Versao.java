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
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.AppController;
import java.awt.Toolkit;

public class AppConfigRules_Nova_Versao extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> refere = new JComboBox<>();
	private JComboBox<String> operador = new JComboBox<>();
	private JComboBox<String> quantidade = new JComboBox<>();
	private JComboBox<String> estadoVizinho = new JComboBox<>();
	private JComboBox<String> novoEstado = new JComboBox<>();
	private JButton btnAvancar;
	private JButton btnVoltar;
	private JTextArea infoTxtArea;
	private JTextArea textRegra;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> listaRegras = new JList<>(model);
	private JLabel lblErro;
	private int qntLimiteRegras = 18;
	private ArrayList<Integer[]> regras = new ArrayList<>();

	/**
	 * Preenche textRegra com a regra em linguagem formal de acordo com o estado
	 * atual dos JComboBoxes
	 * 
	 * Author: Madson
	 */
	private void updateRuleInProduction() {
		textRegra.setText("Se no estado " + refere.getSelectedItem().toString()
				+ " e com quantidade de vizinhos no estado " + estadoVizinho.getSelectedItem().toString() + " "
				+ operador.getSelectedItem().toString() + " " + quantidade.getSelectedItem().toString()
				+ ", então mude para o estado " + novoEstado.getSelectedItem().toString() + ";");
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
	private String getRuleTextByCode(int[] code) {
		String text = "Se no estado " + refere.getItemAt(code[1]).toString()
				+ " e com quantidade de vizinhos no estado " + estadoVizinho.getItemAt(code[4]).toString() + " "
				+ operador.getItemAt(code[2]).toString() + " " + quantidade.getItemAt(code[3]).toString()
				+ ", então mude para o estado " + novoEstado.getItemAt(code[5]).toString() + ";";
		return text;
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
		for (int i = 0; i < 18; i++) {
			if (controller.getRegras()[i][0] == 0) {
				break;
			} else {
				// preenche o ArrayList regras
				regras.add(new Integer[] { controller.getRegras()[i][0], controller.getRegras()[i][1],
						controller.getRegras()[i][2], controller.getRegras()[i][3], controller.getRegras()[i][4],
						controller.getRegras()[i][5] });
				// preenche o model da JList e atualiza a JList
				model.addElement(getRuleTextByCode(controller.getRegras()[i]));
				listaRegras.setModel(model);
			}
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

	/**
	 * Create the frame.
	 */
	public AppConfigRules_Nova_Versao(AppController controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppConfigRules_Nova_Versao.class.getResource("/img/main16x16.png")));
		setResizable(false);
		setTitle("Autcel: Regras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuAjuda = new JMenu("Ajuda");
		menuAjuda.setBorder(new EmptyBorder(0, 0, 0, 0));
		menuBar.add(menuAjuda);

		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/main16x16.png")));
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.showAboutPopUp();
			}
		});
		mntmSobre.setBorder(new CompoundBorder());
		menuAjuda.add(mntmSobre);
		
		JPanel panelRegras = new JPanel();
		panelRegras.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRegras.setBounds(10, 59, 874, 530);
		contentPane.add(panelRegras);
		panelRegras.setLayout(null);

		textRegra = new JTextArea();
		textRegra.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textRegra.setEditable(false);
		textRegra.setText("");
		textRegra.setBorder(new LineBorder(new Color(0, 0, 0)));
		textRegra.setBounds(123, 29, 745, 20);
		panelRegras.add(textRegra);

		JLabel lblRegraEmProduo = new JLabel("Regra em Produ\u00E7\u00E3o");
		lblRegraEmProduo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegraEmProduo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRegraEmProduo.setBounds(123, 7, 745, 17);
		panelRegras.add(lblRegraEmProduo);

		JPanel panelCombos = new JPanel();
		panelCombos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCombos.setBounds(123, 53, 745, 51);
		panelRegras.add(panelCombos);
		panelCombos.setLayout(null);
		refere.setBounds(30, 26, 120, 20);
		panelCombos.add(refere);

		JLabel lblRefereseAo = new JLabel("Refere-se ao");
		lblRefereseAo.setBounds(40, 3, 100, 20);
		panelCombos.add(lblRefereseAo);
		lblRefereseAo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRefereseAo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefereseAo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRefereseAo.setFocusable(false);
		operador.setBounds(330, 26, 100, 20);
		panelCombos.add(operador);

		JLabel lblOperador = new JLabel("Operador");
		lblOperador.setBounds(330, 3, 100, 20);
		panelCombos.add(lblOperador);
		lblOperador.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOperador.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperador.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOperador.setFocusable(false);
		quantidade.setBounds(460, 26, 100, 20);
		panelCombos.add(quantidade);

		JLabel lblQtd = new JLabel("Quantidade");
		lblQtd.setBounds(460, 3, 100, 20);
		panelCombos.add(lblQtd);
		lblQtd.setHorizontalTextPosition(SwingConstants.CENTER);
		lblQtd.setHorizontalAlignment(SwingConstants.CENTER);
		lblQtd.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQtd.setFocusable(false);
		estadoVizinho.setBounds(180, 26, 120, 20);
		panelCombos.add(estadoVizinho);

		JLabel lblVizinho = new JLabel("Vizinho");
		lblVizinho.setBounds(190, 3, 100, 20);
		panelCombos.add(lblVizinho);
		lblVizinho.setHorizontalTextPosition(SwingConstants.CENTER);
		lblVizinho.setHorizontalAlignment(SwingConstants.CENTER);
		lblVizinho.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVizinho.setFocusable(false);
		novoEstado.setBounds(590, 26, 120, 20);
		panelCombos.add(novoEstado);

		JLabel lblNovoEstado = new JLabel("Novo Estado");
		lblNovoEstado.setBounds(600, 3, 100, 20);
		panelCombos.add(lblNovoEstado);
		lblNovoEstado.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNovoEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovoEstado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNovoEstado.setFocusable(false);

		JButton btnNovaRegra = new JButton("Nova Regra");
		btnNovaRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// atualiza a regra que está sendo criada
				updateRuleInProduction();
			}
		});
		btnNovaRegra.setPreferredSize(new Dimension(90, 20));
		btnNovaRegra.setFont(null);
		btnNovaRegra.setFocusable(false);
		btnNovaRegra.setBounds(4, 29, 115, 20);
		panelRegras.add(btnNovaRegra);

		JButton btnCriarRegra = new JButton("Criar Regra");
		btnCriarRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// checa se há algum texto no campo de regra em produção
				if (textRegra.getText().length() > 0) {
					// checa se a quantidade de regras existentes não ultrapassa
					// o limite
					if (model.size() < qntLimiteRegras) {
						// adiciona o texto ao model da JList
						model.addElement(textRegra.getText());
						listaRegras.setModel(model);
						// limpa o campo da regra em produção
						textRegra.setText("");
						// adiciona a nova regra ao ArrayList de regras
						regras.add(new Integer[] { 1, refere.getSelectedIndex(), operador.getSelectedIndex(),
								quantidade.getSelectedIndex(), estadoVizinho.getSelectedIndex(),
								novoEstado.getSelectedIndex() });
					} else {
						lblErro.setText("Número máximo de regras alcançado.");
					}

				}
			}
		});
		btnCriarRegra.setPreferredSize(new Dimension(90, 20));
		btnCriarRegra.setFont(null);
		btnCriarRegra.setFocusable(false);
		btnCriarRegra.setBounds(4, 53, 115, 20);
		panelRegras.add(btnCriarRegra);

		listaRegras = new JList<String>();
		listaRegras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaRegras.setBorder(new LineBorder(new Color(0, 0, 0)));
		listaRegras.setBounds(123, 175, 745, 326);
		panelRegras.add(listaRegras);

		JLabel lblRegrasAtivas = new JLabel("Regras Ativas");
		lblRegrasAtivas.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegrasAtivas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRegrasAtivas.setBounds(123, 151, 745, 17);
		panelRegras.add(lblRegrasAtivas);

		JButton btnRemoverRegra = new JButton("Excluir");
		btnRemoverRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pos recebe a posição da regra selecionada
				int pos = listaRegras.getSelectedIndex();
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
		btnRemoverRegra.setFont(null);
		btnRemoverRegra.setFocusable(false);
		btnRemoverRegra.setBounds(4, 175, 115, 20);
		panelRegras.add(btnRemoverRegra);

		JButton btnAcima = new JButton("Acima");
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
		btnAcima.setFont(null);
		btnAcima.setFocusable(false);
		btnAcima.setBounds(4, 199, 115, 20);
		panelRegras.add(btnAcima);

		JButton btnAbaixo = new JButton("Abaixo");
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
		btnAbaixo.setFont(null);
		btnAbaixo.setFocusable(false);
		btnAbaixo.setBounds(4, 223, 115, 20);
		panelRegras.add(btnAbaixo);

		lblErro = new JLabel("");
		lblErro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblErro.setForeground(Color.RED);
		lblErro.setBounds(123, 506, 745, 18);
		panelRegras.add(lblErro);

		JPanel panelModelos = new JPanel();
		panelModelos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelModelos.setBounds(10, 6, 874, 51);
		contentPane.add(panelModelos);
		panelModelos.setLayout(null);

		JLabel lblDefinirRegras = new JLabel("Definir Regras");
		lblDefinirRegras.setBounds(379, 6, 115, 17);
		panelModelos.add(lblDefinirRegras);
		lblDefinirRegras.setHorizontalAlignment(SwingConstants.CENTER);
		lblDefinirRegras.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnDefinirParaJogo = new JButton("Jogo da Vida");
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
		btnDefinirParaJogo.setFont(null);
		btnDefinirParaJogo.setFocusable(false);
		btnDefinirParaJogo.setBounds(204, 22, 130, 20);
		panelModelos.add(btnDefinirParaJogo);

		JButton btnDefinirParaCristais = new JButton("Cristais de Ulam");
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
		btnDefinirParaCristais.setFont(null);
		btnDefinirParaCristais.setFocusable(false);
		btnDefinirParaCristais.setBounds(538, 22, 130, 20);
		panelModelos.add(btnDefinirParaCristais);

		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMenu.setBounds(10, 595, 874, 54);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);

		btnAvancar = new JButton("Avan\u00E7ar");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Se houver pelo menos uma regra
				if (regras.size() > 0) {
					// salva o estado atual das regras
					controller.setRegras(controller.convertArrayList(regras));
					// instancia a janela de execução
					AppRun exe = new AppRun(controller);
					// torna a janela de execução visível
					exe.setVisible(true);
					// encerra a janela atual
					dispose();
				} else {
					lblErro.setText("É necessário ter pelo menos uma regra ativa para continuar.");
				}
			}
		});
		btnAvancar.setPreferredSize(new Dimension(90, 20));
		btnAvancar.setFont(controller.getButtonFont());
		btnAvancar.setFocusable(false);
		btnAvancar.setBounds(714, 17, 155, 20);
		panelMenu.add(btnAvancar);

		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Salva as regras se existir ao menos uma
				if (regras.size() > 0) {
					controller.setRegras(controller.convertArrayList(regras));
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
		btnVoltar.setFont(controller.getButtonFont());
		btnVoltar.setFocusable(false);
		btnVoltar.setBounds(5, 17, 155, 20);
		panelMenu.add(btnVoltar);

		infoTxtArea = new JTextArea();
		infoTxtArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		infoTxtArea.setWrapStyleWord(true);
		infoTxtArea.setText(
				"Defina as regras do aut\u00F4mato. As regras seguem ordem de preced\u00EAncia de cima para baixo, logo, uma vez que uma regra seja validada para uma c\u00E9lula em um ciclo, as demais regras ser\u00E3o ignoradas para esta c\u00E9lula.");
		infoTxtArea.setOpaque(false);
		infoTxtArea.setLineWrap(true);
		infoTxtArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		infoTxtArea.setEditable(false);
		infoTxtArea.setBounds(165, 3, 544, 48);
		panelMenu.add(infoTxtArea);

		// altera o texto da regra em produção, caso o JComboBox seja alterado
		refere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction();
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		quantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction();
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		estadoVizinho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateRuleInProduction();
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		novoEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRuleInProduction();
			}
		});
		// altera o texto da regra em produção, caso o JComboBox seja alterado
		operador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateRuleInProduction();
			}
		});

		// configura o model de todos os JComboBox
		configureModels(controller);
		// configura o model da JList
		configureJList(controller);
	}
}

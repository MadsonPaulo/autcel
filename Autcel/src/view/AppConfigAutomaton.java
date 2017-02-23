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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.AppController;

/**
 * @author Madson
 *
 */
public class AppConfigAutomaton extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEstado1;
	private JTextField txtEstado2;
	private JTextField txtEstado3;
	private JTextField txtEstado4;
	private JTextField txtEstado5;
	private JTextField txtEstado6;
	private JTextField txtEstado7;
	private JTextField txtEstado8;
	private JLabel lblCor1;
	private JLabel lblCor2;
	private JLabel lblCor3;
	private JLabel lblCor4;
	private JLabel lblCor5;
	private JLabel lblCor6;
	private JLabel lblCor7;
	private JLabel lblCor8;
	private JComboBox<String> dimensoesComboBox;
	private JComboBox<String> estadosPossiveisComboBox;
	private JPanel matrizPanel;
	private JTextArea infoTxtArea;
	private final String mensagemInicialTxtArea = "Por favor, defina a quantidade e o nome dos estados poss\u00EDveis, assim como as dimens\u00F4es da matriz e o estado inicial de cada célula.";
	private final int maxStateNameSize = 12;

	/**
	 * Author: Madson
	 * 
	 * @return 'false' caso algum dos 8 txtEstado seja vazio ou formado apenas
	 *         por espaços, 'true' caso contrário
	 */
	private boolean areTxtEstadosValid() {
		int estadosPossiveis = Integer.parseInt(estadosPossiveisComboBox.getSelectedItem().toString());
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

		for (int i = 0; i < campos.size(); i++) {
			if (campos.get(i).getText().isEmpty() || campos.get(i).getText().trim().length() == 0) {
				return false;
			}
		}

		return true;
	}

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
	 * Cria o frame
	 */
	public AppConfigAutomaton(AppController controller) {
		setResizable(false);
		setTitle("Autcel: Configura\u00E7\u00E3o");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		matrizPanel = new JPanel();
		matrizPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int estadosPossiveis = Integer.parseInt(estadosPossiveisComboBox.getSelectedItem().toString());
				controller.setNextColor(AppConfigAutomaton.this, arg0, estadosPossiveis);
			}
		});
		matrizPanel.setBounds(209, 11, 640, 640);
		matrizPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(matrizPanel);
		matrizPanel.setLayout(null);

		JPanel configPanel = new JPanel();
		configPanel.setBounds(10, 11, 189, 640);
		contentPane.add(configPanel);
		configPanel.setLayout(null);

		JPanel estadosPanel = new JPanel();
		estadosPanel.setBounds(4, 113, 181, 317);
		configPanel.add(estadosPanel);
		estadosPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		estadosPanel.setLayout(null);

		JPanel esqPanel = new JPanel();
		esqPanel.setBounds(1, 1, 100, 314);
		estadosPanel.add(esqPanel);
		esqPanel.setLayout(null);

		JLabel lblDimensoes = new JLabel("Dimens\u00F5es");
		lblDimensoes.setFont(controller.getLabelFont());
		lblDimensoes.setBounds(8, 10, 84, 20);
		esqPanel.add(lblDimensoes);

		JLabel lblEstadosPossveis = new JLabel("Estados Poss\u00EDveis");
		lblEstadosPossveis.setBounds(8, 40, 84, 20);
		esqPanel.add(lblEstadosPossveis);
		lblEstadosPossveis.setFont(controller.getLabelFont());

		txtEstado1 = new JTextField();
		txtEstado1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtEstado1.getText().length() > maxStateNameSize) {
					txtEstado1.setText(txtEstado1.getText().substring(0, 12));
				}
			}
		});
		txtEstado1.setFont(controller.getLabelFont());
		txtEstado1.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado1.setText(controller.getNameState1());
		txtEstado1.setBounds(8, 70, 84, 20);
		esqPanel.add(txtEstado1);

		txtEstado2 = new JTextField();
		txtEstado2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtEstado2.getText().length() > maxStateNameSize) {
					txtEstado2.setText(txtEstado2.getText().substring(0, 12));
				}
			}
		});
		txtEstado2.setFont(controller.getLabelFont());
		txtEstado2.setText(controller.getNameState2());
		txtEstado2.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado2.setBounds(8, 100, 84, 20);
		esqPanel.add(txtEstado2);

		txtEstado3 = new JTextField();
		txtEstado3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if (txtEstado3.getText().length() > maxStateNameSize) {
					txtEstado3.setText(txtEstado3.getText().substring(0, 12));
				}
			}
		});
		txtEstado3.setText(controller.getNameState3());
		txtEstado3.setFont(controller.getLabelFont());
		txtEstado3.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado3.setBounds(8, 130, 84, 20);
		esqPanel.add(txtEstado3);

		txtEstado4 = new JTextField();
		txtEstado4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtEstado4.getText().length() > maxStateNameSize) {
					txtEstado4.setText(txtEstado4.getText().substring(0, 12));
				}
			}
		});
		txtEstado4.setFont(controller.getLabelFont());
		txtEstado4.setText(controller.getNameState4());
		txtEstado4.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado4.setBounds(8, 160, 84, 20);
		esqPanel.add(txtEstado4);

		txtEstado5 = new JTextField();
		txtEstado5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtEstado5.getText().length() > maxStateNameSize) {
					txtEstado5.setText(txtEstado5.getText().substring(0, 12));
				}
			}
		});
		txtEstado5.setFont(controller.getLabelFont());
		txtEstado5.setText(controller.getNameState5());
		txtEstado5.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado5.setBounds(8, 190, 84, 20);
		esqPanel.add(txtEstado5);

		txtEstado6 = new JTextField();
		txtEstado6.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtEstado6.getText().length() > maxStateNameSize) {
					txtEstado6.setText(txtEstado6.getText().substring(0, 12));
				}
			}
		});
		txtEstado6.setFont(controller.getLabelFont());
		txtEstado6.setText(controller.getNameState6());
		txtEstado6.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado6.setBounds(8, 220, 84, 20);
		esqPanel.add(txtEstado6);

		txtEstado7 = new JTextField();
		txtEstado7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtEstado7.getText().length() > maxStateNameSize) {
					txtEstado7.setText(txtEstado7.getText().substring(0, 12));
				}
			}
		});
		txtEstado7.setFont(controller.getLabelFont());
		txtEstado7.setText(controller.getNameState7());
		txtEstado7.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado7.setBounds(8, 250, 84, 20);
		esqPanel.add(txtEstado7);

		txtEstado8 = new JTextField();
		txtEstado8.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if (txtEstado8.getText().length() > maxStateNameSize) {
					txtEstado8.setText(txtEstado8.getText().substring(0, 12));
				}
			}
		});
		txtEstado8.setFont(controller.getLabelFont());
		txtEstado8.setText(controller.getNameState8());
		txtEstado8.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado8.setBounds(8, 280, 84, 20);
		esqPanel.add(txtEstado8);

		JPanel dirPanel = new JPanel();
		dirPanel.setBounds(102, 1, 78, 314);
		estadosPanel.add(dirPanel);
		dirPanel.setLayout(null);

		dimensoesComboBox = new JComboBox<String>();
		dimensoesComboBox.setBounds(4, 10, 70, 20);
		dirPanel.add(dimensoesComboBox);
		dimensoesComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "10x10", "20x20", "40x40" }));
		dimensoesComboBox.setFocusable(false);
		int indiceTamanho = 0;
		if (controller.getTamVector() == 20) {
			indiceTamanho = 1;
		} else if (controller.getTamVector() == 40) {
			indiceTamanho = 2;
		}
		dimensoesComboBox.setSelectedIndex(indiceTamanho);
		dimensoesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = dimensoesComboBox.getSelectedIndex();
				int tamanho = 10;
				if (index == 1) {
					tamanho = 20;
				} else if (index == 2) {
					tamanho = 40;
				}
				controller.setTamVector(tamanho);
				controller.fillPanel(matrizPanel);
			}
		});

		estadosPossiveisComboBox = new JComboBox<String>();
		estadosPossiveisComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ativos = Integer.parseInt((String) estadosPossiveisComboBox.getSelectedItem());
				updateActiveStates(ativos);
				controller.setActiveStates(ativos);
			}
		});
		estadosPossiveisComboBox.setBounds(4, 40, 70, 20);
		dirPanel.add(estadosPossiveisComboBox);
		estadosPossiveisComboBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "2", "3", "4", "5", "6", "7", "8" }));
		estadosPossiveisComboBox.setSelectedIndex(controller.getActiveStates() - 2);

		lblCor1 = new JLabel("");
		lblCor1.setToolTipText("");
		lblCor1.setBounds(4, 70, 70, 20);
		dirPanel.add(lblCor1);
		lblCor1.setOpaque(true);
		lblCor1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor1.setBackground(controller.getDefaultCollor(1));

		lblCor2 = new JLabel("");
		lblCor2.setBounds(4, 100, 70, 20);
		dirPanel.add(lblCor2);
		lblCor2.setOpaque(true);
		lblCor2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor2.setBackground(controller.getDefaultCollor(2));

		lblCor3 = new JLabel("");
		lblCor3.setBounds(4, 130, 70, 20);
		dirPanel.add(lblCor3);
		lblCor3.setOpaque(true);
		lblCor3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor3.setBackground(controller.getDefaultCollor(3));

		lblCor4 = new JLabel("");
		lblCor4.setBounds(4, 160, 70, 20);
		dirPanel.add(lblCor4);
		lblCor4.setOpaque(true);
		lblCor4.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor4.setBackground(controller.getDefaultCollor(4));

		lblCor5 = new JLabel("");
		lblCor5.setBounds(4, 190, 70, 20);
		dirPanel.add(lblCor5);
		lblCor5.setOpaque(true);
		lblCor5.setBackground(controller.getDefaultCollor(5));
		lblCor5.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblCor6 = new JLabel("");
		lblCor6.setBounds(4, 220, 70, 20);
		dirPanel.add(lblCor6);
		lblCor6.setOpaque(true);
		lblCor6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor6.setBackground(controller.getDefaultCollor(6));

		lblCor7 = new JLabel("");
		lblCor7.setBounds(4, 250, 70, 20);
		dirPanel.add(lblCor7);
		lblCor7.setOpaque(true);
		lblCor7.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor7.setBackground(controller.getDefaultCollor(7));

		lblCor8 = new JLabel("");
		lblCor8.setBounds(4, 280, 70, 20);
		dirPanel.add(lblCor8);
		lblCor8.setOpaque(true);
		lblCor8.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor8.setBackground(controller.getDefaultCollor(8));

		JPanel arquivoPanel = new JPanel();
		arquivoPanel.setBounds(4, 0, 181, 80);
		configPanel.add(arquivoPanel);
		arquivoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));

		JButton btnImportar = new JButton("Importar");
		btnImportar.setToolTipText("Importar configura\u00E7\u00F5es atrav\u00E9s de um arquivo externo");
		arquivoPanel.add(btnImportar);
		btnImportar.setFont(controller.getButtonFont());
		btnImportar.setFocusable(false);
		btnImportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnImportar.setPreferredSize(new Dimension(120, 20));
		btnImportar.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		arquivoPanel.add(btnExportar);
		btnExportar.setFont(controller.getButtonFont());
		btnExportar.setFocusable(false);
		btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExportar.setMaximumSize(new Dimension(120, 25));
		btnExportar.setMinimumSize(new Dimension(95, 25));
		btnExportar.setPreferredSize(new Dimension(120, 20));
		btnExportar.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// renomeia estados
				txtEstado1.setText(controller.getNameState1());
				txtEstado2.setText(controller.getNameState2());
				txtEstado3.setText(controller.getNameState3());
				txtEstado4.setText(controller.getNameState4());
				txtEstado5.setText(controller.getNameState5());
				txtEstado6.setText(controller.getNameState6());
				txtEstado7.setText(controller.getNameState7());
				txtEstado8.setText(controller.getNameState8());
				// reseta dimensão
				dimensoesComboBox.setSelectedIndex(1);
				controller.setTamVector(20);
				controller.fillPanel(matrizPanel);
				// reseta estados possiveis
				estadosPossiveisComboBox.setSelectedIndex(0);
				int ativos = Integer.parseInt((String) estadosPossiveisComboBox.getSelectedItem());
				updateActiveStates(ativos);
				controller.setActiveStates(ativos);
				// reseta txtArea
				infoTxtArea.setText(mensagemInicialTxtArea);
			}
		});
		btnLimpar.setToolTipText("Limpar todas as configura\u00E7\u00F5es atuais");
		arquivoPanel.add(btnLimpar);
		btnLimpar.setFocusable(false);
		btnLimpar.setFont(controller.getButtonFont());
		btnLimpar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLimpar.setPreferredSize(new Dimension(120, 20));
		btnLimpar.setAlignmentX(0.5f);

		JButton btnAvancar = new JButton("Avan\u00E7ar");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// convertel o JPanel em uma matriz
				int[][] vetorEstadosIniciais = controller.getVectorFromPanel(matrizPanel);
				if (areTxtEstadosValid() == false) {
					infoTxtArea.setText("Não podem haver estados com nome vazio.");
				} else if (controller.areStatesValid(vetorEstadosIniciais) == false) {
					infoTxtArea.setText(
							"São necessários pelo menos dois estados diferentes para continuar. Caso hajam estados inválidos, remova-os.");
				} else {
					// salva o nome atual dos estados
					saveStateNames(controller);
					// salva o vetor atual
					controller.setVector(vetorEstadosIniciais);
					// instancia a janela de regras
					AppConfigRules rules = new AppConfigRules(controller);
					// torna a janela de regras visível
					rules.setVisible(true);
					// encerra a janela atual
					dispose();
				}

			}
		});
		btnAvancar.setFont(controller.getButtonFont());
		btnAvancar.setBounds(14, 609, 160, 20);
		configPanel.add(btnAvancar);
		btnAvancar.setPreferredSize(new Dimension(90, 20));
		btnAvancar.setFocusable(false);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoPanel.setBounds(4, 463, 181, 140);
		configPanel.add(infoPanel);
		infoPanel.setLayout(null);

		infoTxtArea = new JTextArea();
		infoTxtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		infoTxtArea.setWrapStyleWord(true);
		infoTxtArea.setEditable(false);
		infoTxtArea.setLineWrap(true);
		infoTxtArea.setText(mensagemInicialTxtArea);
		infoTxtArea.setOpaque(false);
		infoTxtArea.setBounds(10, 11, 161, 118);
		infoPanel.add(infoTxtArea);

		// Desenha a matriz
		controller.fillPanel(matrizPanel);
		updateActiveStates(controller.getActiveStates());

	}
}

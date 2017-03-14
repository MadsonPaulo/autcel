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
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
	private JLabel lblEstadosPossveis = new JLabel();
	private JLabel lblEscala = new JLabel();
	private JLabel lblCoord = new JLabel("[0, 0]");
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
	private JButton btnImportar = new JButton();
	private JButton btnLimpar = new JButton();
	private JButton btnExportar = new JButton();
	private JButton btnAvancar = new JButton();

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
		// verifica
		for (int i = 0; i < campos.size(); i++) {
			if (campos.get(i).getText().isEmpty() || campos.get(i).getText().trim().length() == 0) {
				return false;
			}
		}
		return true;
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
		// reseta estados possiveis
		estadosPossiveisComboBox.setSelectedIndex(0);
		int ativos = Integer.parseInt((String) estadosPossiveisComboBox.getSelectedItem());
		updateActiveStates(ativos);
		contr.setActiveStates(ativos);
		// reseta txtArea
		infoTxtArea.setText(mensagemInicialTxtArea);
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
			mntmExportar.setText("Exportar");
			mntmSair.setText("Sair");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmSobre.setText("Sobre o Autcel");
			menuAjuda.setText("Ajuda");
			// botões
			btnImportar.setText("Importar");
			btnLimpar.setText("Limpar");
			btnAvancar.setText("Avançar");
			btnExportar.setText("Exportar");
			// labels
			lblEstadosPossveis.setText("Estados Possíveis");
			updateScale(contr);
			// textArea
			mensagemInicialTxtArea = "Por favor, defina a quantidade e o nome dos estados poss\u00EDveis, assim como o estado inicial de cada célula.";
			infoTxtArea.setText(mensagemInicialTxtArea);
		} else if (value == 1) {
			// título
			setTitle("Autcel: Configurations");
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
			btnImportar.setText("Import");
			btnLimpar.setText("Clean");
			btnAvancar.setText("Next");
			btnExportar.setText("Export");
			// labels
			lblEstadosPossveis.setText("Possible States");
			updateScale(contr);
			// textArea
			mensagemInicialTxtArea = "Please, define the number and name of possible states, as well as the initial state of each cell.";
			infoTxtArea.setText(mensagemInicialTxtArea);
		}
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
		setBounds(100, 100, 863, 700);

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

		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel configPanel = new JPanel();
		configPanel.setBounds(9, 5, 189, 640);
		contentPane.add(configPanel);
		configPanel.setLayout(null);

		JPanel estadosPanel = new JPanel();
		estadosPanel.setBounds(4, 169, 181, 284);
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

		lblCor1 = new JLabel("");
		lblCor1.setToolTipText("");
		lblCor1.setBounds(3, 40, 60, 20);
		dirPanel.add(lblCor1);
		lblCor1.setOpaque(true);
		lblCor1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor1.setBackground(controller.getDefaultCollor(1));

		lblCor2 = new JLabel("");
		lblCor2.setBounds(3, 70, 60, 20);
		dirPanel.add(lblCor2);
		lblCor2.setOpaque(true);
		lblCor2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor2.setBackground(controller.getDefaultCollor(2));

		lblCor3 = new JLabel("");
		lblCor3.setBounds(3, 100, 60, 20);
		dirPanel.add(lblCor3);
		lblCor3.setOpaque(true);
		lblCor3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor3.setBackground(controller.getDefaultCollor(3));

		lblCor4 = new JLabel("");
		lblCor4.setBounds(3, 130, 60, 20);
		dirPanel.add(lblCor4);
		lblCor4.setOpaque(true);
		lblCor4.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor4.setBackground(controller.getDefaultCollor(4));

		lblCor5 = new JLabel("");
		lblCor5.setBounds(3, 160, 60, 20);
		dirPanel.add(lblCor5);
		lblCor5.setOpaque(true);
		lblCor5.setBackground(controller.getDefaultCollor(5));
		lblCor5.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblCor6 = new JLabel("");
		lblCor6.setBounds(3, 190, 60, 20);
		dirPanel.add(lblCor6);
		lblCor6.setOpaque(true);
		lblCor6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor6.setBackground(controller.getDefaultCollor(6));

		lblCor7 = new JLabel("");
		lblCor7.setBounds(3, 220, 60, 20);
		dirPanel.add(lblCor7);
		lblCor7.setOpaque(true);
		lblCor7.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor7.setBackground(controller.getDefaultCollor(7));

		lblCor8 = new JLabel("");
		lblCor8.setBounds(3, 250, 60, 20);
		dirPanel.add(lblCor8);
		lblCor8.setOpaque(true);
		lblCor8.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCor8.setBackground(controller.getDefaultCollor(8));

		JPanel arquivoPanel = new JPanel();
		arquivoPanel.setBounds(4, 9, 181, 80);
		configPanel.add(arquivoPanel);
		arquivoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));

		arquivoPanel.add(btnImportar);
		btnImportar.setFont(controller.getBoldFont());
		btnImportar.setFocusable(false);
		btnImportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		btnImportar.setPreferredSize(new Dimension(120, 20));
		btnImportar.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnExportar.setEnabled(false);
		arquivoPanel.add(btnExportar);
		btnExportar.setFont(controller.getBoldFont());
		btnExportar.setFocusable(false);
		btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExportar.setMaximumSize(new Dimension(120, 25));
		btnExportar.setMinimumSize(new Dimension(95, 25));
		btnExportar.setPreferredSize(new Dimension(120, 20));
		btnExportar.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanData(controller);
			}
		});
		arquivoPanel.add(btnLimpar);
		btnLimpar.setFocusable(false);
		btnLimpar.setFont(controller.getBoldFont());
		btnLimpar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLimpar.setPreferredSize(new Dimension(120, 20));
		btnLimpar.setAlignmentX(0.5f);

		btnAvancar.addActionListener(new ActionListener() {
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
					// instancia a janela de regras
					AppConfigRules rules = new AppConfigRules(controller);
					// torna a janela de regras visível
					rules.setVisible(true);
					// encerra a janela atual
					dispose();
				}

			}
		});
		btnAvancar.setFont(controller.getBoldFont());
		btnAvancar.setBounds(14, 611, 160, 20);
		configPanel.add(btnAvancar);
		btnAvancar.setPreferredSize(new Dimension(90, 20));
		btnAvancar.setFocusable(false);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoPanel.setBounds(4, 462, 181, 140);
		configPanel.add(infoPanel);
		infoPanel.setLayout(null);

		infoTxtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		infoTxtArea.setWrapStyleWord(true);
		infoTxtArea.setEditable(false);
		infoTxtArea.setLineWrap(true);
		infoTxtArea.setOpaque(false);
		infoTxtArea.setBounds(10, 11, 161, 118);
		infoPanel.add(infoTxtArea);

		JPanel ZoomPanel = new JPanel();
		ZoomPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ZoomPanel.setBounds(4, 98, 181, 62);
		configPanel.add(ZoomPanel);
		ZoomPanel.setLayout(null);

		JButton btnZoomIn = new JButton("");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// checa a escala
				if (controller.getSqrSize() < 64) {
					// altera a escala, tamanho dos quadrados
					controller.setSqrSize(controller.getSqrSize() * 2);
					// atualiza as barras de scroll de squares
					squares2.setPrefSize(controller.getSqrSize() * controller.getTamVector());
					// atualiza o texto de escala
					updateScale(controller);
					// redesenha o grid
					controller.drawMatriz(squares2);
					repaint();
				}
			}
		});
		btnZoomIn.setFocusable(false);
		btnZoomIn.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/zoom-in.gif")));
		btnZoomIn.setBounds(20, 5, 60, 30);
		ZoomPanel.add(btnZoomIn);

		JButton btnZoomOut = new JButton("");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// checa a escala
				if (controller.getSqrSize() > 4) {
					// altera a escala, tamanho dos quadrados
					controller.setSqrSize(controller.getSqrSize() / 2);
					// atualiza as barras de scroll de squares
					squares2.setPrefSize(controller.getSqrSize() * controller.getTamVector());
					// atualiza o texto de escala
					updateScale(controller);
					// redesenha o grid
					controller.drawMatriz(squares2);
					repaint();
				}
			}
		});
		btnZoomOut.setFocusable(false);
		btnZoomOut.setBounds(100, 5, 60, 30);
		btnZoomOut.setIcon(new ImageIcon(AppConfigAutomaton.class.getResource("/img/zoom-out.gif")));
		ZoomPanel.add(btnZoomOut);

		lblEscala.setHorizontalAlignment(SwingConstants.CENTER);
		lblEscala.setFont(controller.getNormalFont());
		lblEscala.setBounds(15, 40, 70, 16);
		ZoomPanel.add(lblEscala);

		lblCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoord.setFont(controller.getNormalFont());
		lblCoord.setBounds(97, 40, 66, 16);
		ZoomPanel.add(lblCoord);

		squares2 = new DrawSquare(controller.getSqrSize() * controller.getTamVector());
		squares2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// razão entre a largura de squares2 e tamanho da matriz
				double value = (squares2.getWidth() * 1.0) / controller.getTamVector();
				// posição x e y do local clicado
				int posX = (int) Math.ceil(arg0.getX() / value) - 1;
				int posY = (int) Math.ceil(arg0.getY() / value) - 1;
				// raramente, nas extremidades, é detectado -1
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				lblCoord.setText("[" + posY + ", " + posX + "]");
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
				// raramente, nas extremidades, é detectado -1
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				// quantidade de estados ativos
				int ativos = Integer.parseInt((String) estadosPossiveisComboBox.getSelectedItem());
				// altera o vetor na posição clicada
				controller.getVector()[posY][posX] = controller.getNextStateValue(controller.getVector()[posY][posX],
						ativos);
				// redesenha a matriz
				controller.drawMatriz(squares2);
				repaint();
			}
		});
		squares2.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPane = new JScrollPane(squares2);
		scrollPane.setBounds(207, 4, 641, 641);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
		scrollPane.getVerticalScrollBar().setUnitIncrement(50);
		contentPane.add(scrollPane);

		// desenha a matriz
		controller.drawMatriz(squares2);
		// corrige a escala, caso necessário
		if (controller.getSqrSize() < 4) {
			controller.setSqrSize(4);
			squares2.setPrefSize(controller.getSqrSize() * controller.getTamVector());
		}
		// determina o idioma
		setLanguage(controller.getLanguage(), controller);
	}
}
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.AppController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("unchecked")
public class AppConfigRules extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox<String> R1Refere = new JComboBox<>();
	private JComboBox<String> R2Refere = new JComboBox<>();
	private JComboBox<String> R3Refere = new JComboBox<>();
	private JComboBox<String> R4Refere = new JComboBox<>();
	private JComboBox<String> R5Refere = new JComboBox<>();
	private JComboBox<String> R6Refere = new JComboBox<>();
	private JComboBox<String> R7Refere = new JComboBox<>();
	private JComboBox<String> R8Refere = new JComboBox<>();
	private JComboBox<String> R9Refere = new JComboBox<>();
	private JComboBox<String> R10Refere = new JComboBox<>();
	private JComboBox<String> R11Refere = new JComboBox<>();
	private JComboBox<String> R12Refere = new JComboBox<>();
	private JComboBox<String> R13Refere = new JComboBox<>();
	private JComboBox<String> R14Refere = new JComboBox<>();
	private JComboBox<String> R15Refere = new JComboBox<>();
	private JComboBox<String> R16Refere = new JComboBox<>();
	private JComboBox<String> R17Refere = new JComboBox<>();
	private JComboBox<String> R18Refere = new JComboBox<>();
	private JComboBox<String> R1Operador = new JComboBox<>();
	private JComboBox<String> R2Operador = new JComboBox<>();
	private JComboBox<String> R3Operador = new JComboBox<>();
	private JComboBox<String> R4Operador = new JComboBox<>();
	private JComboBox<String> R5Operador = new JComboBox<>();
	private JComboBox<String> R6Operador = new JComboBox<>();
	private JComboBox<String> R7Operador = new JComboBox<>();
	private JComboBox<String> R8Operador = new JComboBox<>();
	private JComboBox<String> R9Operador = new JComboBox<>();
	private JComboBox<String> R10Operador = new JComboBox<>();
	private JComboBox<String> R11Operador = new JComboBox<>();
	private JComboBox<String> R12Operador = new JComboBox<>();
	private JComboBox<String> R13Operador = new JComboBox<>();
	private JComboBox<String> R14Operador = new JComboBox<>();
	private JComboBox<String> R15Operador = new JComboBox<>();
	private JComboBox<String> R16Operador = new JComboBox<>();
	private JComboBox<String> R17Operador = new JComboBox<>();
	private JComboBox<String> R18Operador = new JComboBox<>();
	private JComboBox<String> R1Qtd = new JComboBox<>();
	private JComboBox<String> R2Qtd = new JComboBox<>();
	private JComboBox<String> R3Qtd = new JComboBox<>();
	private JComboBox<String> R4Qtd = new JComboBox<>();
	private JComboBox<String> R5Qtd = new JComboBox<>();
	private JComboBox<String> R6Qtd = new JComboBox<>();
	private JComboBox<String> R7Qtd = new JComboBox<>();
	private JComboBox<String> R8Qtd = new JComboBox<>();
	private JComboBox<String> R9Qtd = new JComboBox<>();
	private JComboBox<String> R10Qtd = new JComboBox<>();
	private JComboBox<String> R11Qtd = new JComboBox<>();
	private JComboBox<String> R12Qtd = new JComboBox<>();
	private JComboBox<String> R13Qtd = new JComboBox<>();
	private JComboBox<String> R14Qtd = new JComboBox<>();
	private JComboBox<String> R15Qtd = new JComboBox<>();
	private JComboBox<String> R16Qtd = new JComboBox<>();
	private JComboBox<String> R17Qtd = new JComboBox<>();
	private JComboBox<String> R18Qtd = new JComboBox<>();
	private JComboBox<String> R1Vizinho = new JComboBox<>();
	private JComboBox<String> R2Vizinho = new JComboBox<>();
	private JComboBox<String> R3Vizinho = new JComboBox<>();
	private JComboBox<String> R4Vizinho = new JComboBox<>();
	private JComboBox<String> R5Vizinho = new JComboBox<>();
	private JComboBox<String> R6Vizinho = new JComboBox<>();
	private JComboBox<String> R7Vizinho = new JComboBox<>();
	private JComboBox<String> R8Vizinho = new JComboBox<>();
	private JComboBox<String> R9Vizinho = new JComboBox<>();
	private JComboBox<String> R10Vizinho = new JComboBox<>();
	private JComboBox<String> R11Vizinho = new JComboBox<>();
	private JComboBox<String> R12Vizinho = new JComboBox<>();
	private JComboBox<String> R13Vizinho = new JComboBox<>();
	private JComboBox<String> R14Vizinho = new JComboBox<>();
	private JComboBox<String> R15Vizinho = new JComboBox<>();
	private JComboBox<String> R16Vizinho = new JComboBox<>();
	private JComboBox<String> R17Vizinho = new JComboBox<>();
	private JComboBox<String> R18Vizinho = new JComboBox<>();
	private JComboBox<String> R1NovoEstado = new JComboBox<>();
	private JComboBox<String> R2NovoEstado = new JComboBox<>();
	private JComboBox<String> R3NovoEstado = new JComboBox<>();
	private JComboBox<String> R4NovoEstado = new JComboBox<>();
	private JComboBox<String> R5NovoEstado = new JComboBox<>();
	private JComboBox<String> R6NovoEstado = new JComboBox<>();
	private JComboBox<String> R7NovoEstado = new JComboBox<>();
	private JComboBox<String> R8NovoEstado = new JComboBox<>();
	private JComboBox<String> R9NovoEstado = new JComboBox<>();
	private JComboBox<String> R10NovoEstado = new JComboBox<>();
	private JComboBox<String> R11NovoEstado = new JComboBox<>();
	private JComboBox<String> R12NovoEstado = new JComboBox<>();
	private JComboBox<String> R13NovoEstado = new JComboBox<>();
	private JComboBox<String> R14NovoEstado = new JComboBox<>();
	private JComboBox<String> R15NovoEstado = new JComboBox<>();
	private JComboBox<String> R16NovoEstado = new JComboBox<>();
	private JComboBox<String> R17NovoEstado = new JComboBox<>();
	private JComboBox<String> R18NovoEstado = new JComboBox<>();
	private JButton btnAvancar;
	private JButton btnVoltar;
	private JTextArea infoTxtArea;
	private JRadioButton rdbtnDeterminar;
	private JRadioButton rdbtnJogoDaVida;
	private JRadioButton rdbtnCristaisDeUlam;
	private JCheckBox R1Ativo = new JCheckBox("");
	private JCheckBox R2Ativo = new JCheckBox("");
	private JCheckBox R3Ativo = new JCheckBox("");
	private JCheckBox R4Ativo = new JCheckBox("");
	private JCheckBox R5Ativo = new JCheckBox("");
	private JCheckBox R6Ativo = new JCheckBox("");
	private JCheckBox R7Ativo = new JCheckBox("");
	private JCheckBox R8Ativo = new JCheckBox("");
	private JCheckBox R9Ativo = new JCheckBox("");
	private JCheckBox R10Ativo = new JCheckBox("");
	private JCheckBox R11Ativo = new JCheckBox("");
	private JCheckBox R12Ativo = new JCheckBox("");
	private JCheckBox R13Ativo = new JCheckBox("");
	private JCheckBox R14Ativo = new JCheckBox("");
	private JCheckBox R15Ativo = new JCheckBox("");
	private JCheckBox R16Ativo = new JCheckBox("");
	private JCheckBox R17Ativo = new JCheckBox("");
	private JCheckBox R18Ativo = new JCheckBox("");
	private Object regras1[] = new Object[] { R1Ativo, R1Refere, R1Operador, R1Qtd, R1Vizinho, R1NovoEstado };
	private Object regras2[] = new Object[] { R2Ativo, R2Refere, R2Operador, R2Qtd, R2Vizinho, R2NovoEstado };
	private Object regras3[] = new Object[] { R3Ativo, R3Refere, R3Operador, R3Qtd, R3Vizinho, R3NovoEstado };
	private Object regras4[] = new Object[] { R4Ativo, R4Refere, R4Operador, R4Qtd, R4Vizinho, R4NovoEstado };
	private Object regras5[] = new Object[] { R5Ativo, R5Refere, R5Operador, R5Qtd, R5Vizinho, R5NovoEstado };
	private Object regras6[] = new Object[] { R6Ativo, R6Refere, R6Operador, R6Qtd, R6Vizinho, R6NovoEstado };
	private Object regras7[] = new Object[] { R7Ativo, R7Refere, R7Operador, R7Qtd, R7Vizinho, R7NovoEstado };
	private Object regras8[] = new Object[] { R8Ativo, R8Refere, R8Operador, R8Qtd, R8Vizinho, R8NovoEstado };
	private Object regras9[] = new Object[] { R9Ativo, R9Refere, R9Operador, R9Qtd, R9Vizinho, R9NovoEstado };
	private Object regras10[] = new Object[] { R10Ativo, R10Refere, R10Operador, R10Qtd, R10Vizinho, R10NovoEstado };
	private Object regras11[] = new Object[] { R11Ativo, R11Refere, R11Operador, R11Qtd, R11Vizinho, R11NovoEstado };
	private Object regras12[] = new Object[] { R12Ativo, R12Refere, R12Operador, R12Qtd, R12Vizinho, R12NovoEstado };
	private Object regras13[] = new Object[] { R13Ativo, R13Refere, R13Operador, R13Qtd, R13Vizinho, R13NovoEstado };
	private Object regras14[] = new Object[] { R14Ativo, R14Refere, R14Operador, R14Qtd, R14Vizinho, R14NovoEstado };
	private Object regras15[] = new Object[] { R15Ativo, R15Refere, R15Operador, R15Qtd, R15Vizinho, R15NovoEstado };
	private Object regras16[] = new Object[] { R16Ativo, R16Refere, R16Operador, R16Qtd, R16Vizinho, R16NovoEstado };
	private Object regras17[] = new Object[] { R17Ativo, R17Refere, R17Operador, R17Qtd, R17Vizinho, R17NovoEstado };
	private Object regras18[] = new Object[] { R18Ativo, R18Refere, R18Operador, R18Qtd, R18Vizinho, R18NovoEstado };

	private Object arrayRegras[][] = new Object[][] { regras1, regras2, regras3, regras4, regras5, regras6, regras7,
			regras8, regras9, regras10, regras11, regras12, regras13, regras14, regras15, regras16, regras17,
			regras18 };

	/**
	 * Array contendo os 18 JComboBox de Refere, Vizinho e NovoEstado
	 */
	private JComboBox<String>[] arrayNomeEstados = new JComboBox[] { R1Refere, R2Refere, R3Refere, R4Refere, R5Refere,
			R6Refere, R7Refere, R8Refere, R9Refere, R10Refere, R11Refere, R12Refere, R13Refere, R14Refere, R15Refere,
			R16Refere, R17Refere, R18Refere, R1Vizinho, R2Vizinho, R3Vizinho, R4Vizinho, R5Vizinho, R6Vizinho,
			R7Vizinho, R8Vizinho, R9Vizinho, R10Vizinho, R11Vizinho, R12Vizinho, R13Vizinho, R14Vizinho, R15Vizinho,
			R16Vizinho, R17Vizinho, R18Vizinho, R1NovoEstado, R2NovoEstado, R3NovoEstado, R4NovoEstado, R5NovoEstado,
			R6NovoEstado, R7NovoEstado, R8NovoEstado, R9NovoEstado, R10NovoEstado, R11NovoEstado, R12NovoEstado,
			R13NovoEstado, R14NovoEstado, R15NovoEstado, R16NovoEstado, R17NovoEstado, R18NovoEstado };

	private JComboBox<String>[] arrayOperadores = new JComboBox[] { R1Operador, R2Operador, R3Operador, R4Operador,
			R5Operador, R6Operador, R7Operador, R8Operador, R9Operador, R10Operador, R11Operador, R12Operador,
			R13Operador, R14Operador, R15Operador, R16Operador, R17Operador, R18Operador };

	private JComboBox<String>[] arrayQtd = new JComboBox[] { R1Qtd, R2Qtd, R3Qtd, R4Qtd, R5Qtd, R6Qtd, R7Qtd, R8Qtd,
			R9Qtd, R10Qtd, R11Qtd, R12Qtd, R13Qtd, R14Qtd, R15Qtd, R16Qtd, R17Qtd, R18Qtd };

	private int getSelectedModel() {
		if (rdbtnDeterminar.isSelected()) {
			return 0;
		} else if (rdbtnJogoDaVida.isSelected()) {
			return 1;
		} else {
			return 2;
		}
	}

	private void configureModels(AppController controller) {
		// Array de tamanho igual à quantidade de estados possíveis
		String[] nomeEstadosPossiveis = new String[controller.getActiveStates()];

		// Array com o nome dos 8 estados possíveis
		String[] nomeEstados = new String[] { controller.getNameState1(), controller.getNameState2(),
				controller.getNameState3(), controller.getNameState4(), controller.getNameState5(),
				controller.getNameState6(), controller.getNameState7(), controller.getNameState8() };

		// preenche o array com o nome dos estados possíveis
		for (int i = 0; i < nomeEstadosPossiveis.length; i++) {
			nomeEstadosPossiveis[i] = nomeEstados[i];
		}

		// modelo dos JComboBox que farão referência aos estados possíveis
		for (int i = 0; i < arrayNomeEstados.length; i++) {
			arrayNomeEstados[i].setModel(new DefaultComboBoxModel<String>(nomeEstadosPossiveis));
		}

		// lista dos operadores possíveis
		String[] operadores = new String[] { ">=", ">", "==", "<", "<=" };

		// define o model dos operadores
		for (int i = 0; i < arrayOperadores.length; i++) {
			arrayOperadores[i].setModel(new DefaultComboBoxModel<String>(operadores));
		}

		// lista das quantidades de vizinhos possíveis
		String[] quantidades = new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };

		// define o model dos vizinhos
		for (int i = 0; i < arrayQtd.length; i++) {
			arrayQtd[i].setModel(new DefaultComboBoxModel<String>(quantidades));
		}

		// define qual radioButton estará selecionado
		if (controller.getLastSelected() == 0) {
			rdbtnDeterminar.setSelected(true);
		} else if (controller.getLastSelected() == 1) {
			rdbtnJogoDaVida.setSelected(true);
		} else if (controller.getLastSelected() == 2) {
			rdbtnCristaisDeUlam.setSelected(true);
		}
	}

	private int numberOfActiveRules() {
		int activeRules = 0;

		for (int i = 0; i < arrayRegras.length; i++) {
			if (((JCheckBox) arrayRegras[i][0]).isSelected()) {
				activeRules++;
			}
		}

		return activeRules;
	}

	/**
	 * Author: Madson
	 * 
	 * @param rules
	 *            array contendo as regras
	 * 
	 *            Organiza quais JCheckBox estarão marcados e quais itens dos
	 *            JComboBox estarão selecionados, de acordo com um array de
	 *            regras
	 */
	private void organizeIndexes(int rules[][], int activeStates) {
		// percorre o array de JCheckBox e JComboBox relativo às regras
		for (int i = 0; i < arrayRegras.length; i++) {
			for (int j = 0; j < arrayRegras[i].length; j++) {
				// se j = 0 então se trata do JCheckBox
				if (j == 0) {
					if (rules[i][j] == 0) {
						((JCheckBox) arrayRegras[i][j]).setSelected(false);
					} else {
						((JCheckBox) arrayRegras[i][j]).setSelected(true);
					}
					// se se tratar de algum dos campos que fazem referência aos
					// estados
				} else if (j == 1 || j == 4 || j == 5) {
					// se o usuário voltou para a tela de configuração e avançou
					// novamente, restando algum indice inválido após uma
					// alteração na quantidade de estados possíveis, os estados
					// inválidos serão alterados para estado 1(index 0)
					if ((rules[i][j] + 1) > (activeStates)) {
						((JComboBox<String>) arrayRegras[i][j]).setSelectedIndex(0);
					} else {
						((JComboBox<String>) arrayRegras[i][j]).setSelectedIndex(rules[i][j]);
					}
				} else if (j == 3) {
					((JComboBox<String>) arrayRegras[i][j]).setSelectedIndex(rules[i][j]);
				} else {
					((JComboBox<String>) arrayRegras[i][j]).setSelectedIndex(rules[i][j]);
				}
			}
		}
	}

	/**
	 * Author: Madson
	 * 
	 * @return array com a configuração de todas as regras assim como foram
	 *         configuradas pelo usuário
	 */
	private int[][] getArrayRegras() {
		int array[][] = new int[18][6];
		// percorre o array de JCheckBox e JComboBox relativo às regras
		for (int i = 0; i < arrayRegras.length; i++) {
			for (int j = 0; j < arrayRegras[i].length; j++) {
				// se j = 0 então se trata do JCheckBox
				if (j == 0) {
					// Se o JCheckBox estiver marcado
					if (((JCheckBox) arrayRegras[i][j]).isSelected()) {
						array[i][j] = 1;
					} else {
						array[i][j] = 0;
					}
					// se j != 0, trata-se de um JComboBox
				} else if (j == 3) {
					array[i][j] = ((JComboBox<String>) arrayRegras[i][j]).getSelectedIndex();
				} else {
					array[i][j] = ((JComboBox<String>) arrayRegras[i][j]).getSelectedIndex();
				}
			}
		}

		return array;
	}

	private String getRuleDescription(int ruleNumber) {
		return "Quando uma célula no estado "
				+ ((JComboBox<String>) arrayRegras[ruleNumber - 1][1]).getSelectedItem().toString() + " possuir "
				+ ((JComboBox<String>) arrayRegras[ruleNumber - 1][2]).getSelectedItem().toString() + " "
				+ ((JComboBox<String>) arrayRegras[ruleNumber - 1][3]).getSelectedItem().toString()
				+ " vizinhos no estado "
				+ ((JComboBox<String>) arrayRegras[ruleNumber - 1][4]).getSelectedItem().toString()
				+ ", mude para o estado "
				+ ((JComboBox<String>) arrayRegras[ruleNumber - 1][5]).getSelectedItem().toString() + ".";
	}

	/**
	 * Create the frame.
	 */
	public AppConfigRules(AppController controller) {
		setResizable(false);
		setTitle("Autcel: Regras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelRegras = new JPanel();
		panelRegras.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRegras.setBounds(10, 68, 839, 521);
		contentPane.add(panelRegras);
		panelRegras.setLayout(null);

		JLabel lblAtivo = new JLabel("Ativo");
		lblAtivo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAtivo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtivo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAtivo.setFocusable(false);
		lblAtivo.setBounds(-4, 7, 100, 20);
		panelRegras.add(lblAtivo);
		R1Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				R1Ativo.setToolTipText(getRuleDescription(1));
			}
		});

		R1Ativo.setBounds(36, 34, 21, 20);
		panelRegras.add(R1Ativo);
		R2Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R2Ativo.setToolTipText(getRuleDescription(2));
			}
		});

		R2Ativo.setBounds(36, 61, 21, 20);
		panelRegras.add(R2Ativo);
		R4Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R4Ativo.setToolTipText(getRuleDescription(4));
			}
		});

		R4Ativo.setBounds(36, 115, 21, 20);
		panelRegras.add(R4Ativo);
		R3Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R3Ativo.setToolTipText(getRuleDescription(3));
			}
		});

		R3Ativo.setBounds(36, 88, 21, 20);
		panelRegras.add(R3Ativo);
		R6Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R6Ativo.setToolTipText(getRuleDescription(6));
			}
		});

		R6Ativo.setBounds(36, 169, 21, 20);
		panelRegras.add(R6Ativo);
		R5Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R5Ativo.setToolTipText(getRuleDescription(5));
			}
		});

		R5Ativo.setBounds(36, 142, 21, 20);
		panelRegras.add(R5Ativo);
		R7Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R7Ativo.setToolTipText(getRuleDescription(7));
			}
		});

		R7Ativo.setBounds(36, 196, 21, 20);
		panelRegras.add(R7Ativo);
		R8Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R8Ativo.setToolTipText(getRuleDescription(8));
			}
		});

		R8Ativo.setBounds(36, 223, 21, 20);
		panelRegras.add(R8Ativo);
		R10Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R10Ativo.setToolTipText(getRuleDescription(10));
			}
		});

		R10Ativo.setBounds(36, 277, 21, 20);
		panelRegras.add(R10Ativo);
		R9Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R9Ativo.setToolTipText(getRuleDescription(9));
			}
		});

		R9Ativo.setBounds(36, 250, 21, 20);
		panelRegras.add(R9Ativo);
		R12Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R12Ativo.setToolTipText(getRuleDescription(12));
			}
		});

		R12Ativo.setBounds(36, 331, 21, 20);
		panelRegras.add(R12Ativo);
		R11Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R11Ativo.setToolTipText(getRuleDescription(11));
			}
		});

		R11Ativo.setBounds(36, 304, 21, 20);
		panelRegras.add(R11Ativo);
		R13Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R13Ativo.setToolTipText(getRuleDescription(13));
			}
		});

		R13Ativo.setBounds(36, 358, 21, 20);
		panelRegras.add(R13Ativo);
		R14Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R14Ativo.setToolTipText(getRuleDescription(14));
			}
		});

		R14Ativo.setBounds(36, 385, 21, 20);
		panelRegras.add(R14Ativo);
		R16Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R16Ativo.setToolTipText(getRuleDescription(16));
			}
		});

		R16Ativo.setBounds(36, 439, 21, 20);
		panelRegras.add(R16Ativo);
		R15Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R15Ativo.setToolTipText(getRuleDescription(15));
			}
		});

		R15Ativo.setBounds(36, 412, 21, 20);
		panelRegras.add(R15Ativo);
		R18Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R18Ativo.setToolTipText(getRuleDescription(18));
			}
		});

		R18Ativo.setBounds(36, 493, 21, 20);
		panelRegras.add(R18Ativo);
		R17Ativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				R17Ativo.setToolTipText(getRuleDescription(17));
			}
		});

		R17Ativo.setBounds(36, 466, 21, 20);
		panelRegras.add(R17Ativo);

		JLabel lblRefereseAo = new JLabel("Refere-se ao");
		lblRefereseAo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRefereseAo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefereseAo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRefereseAo.setFocusable(false);
		lblRefereseAo.setBounds(103, 7, 100, 20);
		panelRegras.add(lblRefereseAo);

		R1Refere.setBounds(93, 34, 120, 20);
		panelRegras.add(R1Refere);

		R2Refere.setBounds(93, 61, 120, 20);
		panelRegras.add(R2Refere);

		R3Refere.setBounds(93, 88, 120, 20);
		panelRegras.add(R3Refere);

		R4Refere.setBounds(93, 115, 120, 20);
		panelRegras.add(R4Refere);

		R5Refere.setBounds(93, 142, 120, 20);
		panelRegras.add(R5Refere);

		R6Refere.setBounds(93, 169, 120, 20);
		panelRegras.add(R6Refere);

		R7Refere.setBounds(93, 196, 120, 20);
		panelRegras.add(R7Refere);

		R8Refere.setBounds(93, 223, 120, 20);
		panelRegras.add(R8Refere);

		R9Refere.setBounds(93, 250, 120, 20);
		panelRegras.add(R9Refere);

		R10Refere.setBounds(93, 277, 120, 20);
		panelRegras.add(R10Refere);

		R11Refere.setBounds(93, 304, 120, 20);
		panelRegras.add(R11Refere);

		R12Refere.setBounds(93, 331, 120, 20);
		panelRegras.add(R12Refere);

		R13Refere.setBounds(93, 358, 120, 20);
		panelRegras.add(R13Refere);

		R14Refere.setBounds(93, 385, 120, 20);
		panelRegras.add(R14Refere);

		R15Refere.setBounds(93, 412, 120, 20);
		panelRegras.add(R15Refere);

		R16Refere.setBounds(93, 439, 120, 20);
		panelRegras.add(R16Refere);

		R17Refere.setBounds(93, 466, 120, 20);
		panelRegras.add(R17Refere);

		R18Refere.setBounds(93, 493, 120, 20);
		panelRegras.add(R18Refere);

		JLabel lblOperador = new JLabel("Operador");
		lblOperador.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOperador.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperador.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOperador.setFocusable(false);
		lblOperador.setBounds(249, 7, 100, 20);
		panelRegras.add(lblOperador);

		R1Operador.setBounds(249, 34, 100, 20);
		panelRegras.add(R1Operador);

		R2Operador.setBounds(249, 61, 100, 20);
		panelRegras.add(R2Operador);

		R3Operador.setBounds(249, 88, 100, 20);
		panelRegras.add(R3Operador);

		R4Operador.setBounds(249, 115, 100, 20);
		panelRegras.add(R4Operador);

		R5Operador.setBounds(249, 142, 100, 20);
		panelRegras.add(R5Operador);

		R6Operador.setBounds(249, 169, 100, 20);
		panelRegras.add(R6Operador);

		R7Operador.setBounds(249, 196, 100, 20);
		panelRegras.add(R7Operador);

		R8Operador.setBounds(249, 223, 100, 20);
		panelRegras.add(R8Operador);

		R9Operador.setBounds(249, 250, 100, 20);
		panelRegras.add(R9Operador);

		R10Operador.setBounds(249, 277, 100, 20);
		panelRegras.add(R10Operador);

		R11Operador.setBounds(249, 304, 100, 20);
		panelRegras.add(R11Operador);

		R12Operador.setBounds(249, 331, 100, 20);
		panelRegras.add(R12Operador);

		R13Operador.setBounds(249, 358, 100, 20);
		panelRegras.add(R13Operador);

		R14Operador.setBounds(249, 385, 100, 20);
		panelRegras.add(R14Operador);

		R15Operador.setBounds(249, 412, 100, 20);
		panelRegras.add(R15Operador);

		R16Operador.setBounds(249, 439, 100, 20);
		panelRegras.add(R16Operador);

		R17Operador.setBounds(249, 466, 100, 20);
		panelRegras.add(R17Operador);

		R18Operador.setBounds(249, 493, 100, 20);
		panelRegras.add(R18Operador);

		JLabel lblQtd = new JLabel("Quantidade");
		lblQtd.setHorizontalTextPosition(SwingConstants.CENTER);
		lblQtd.setHorizontalAlignment(SwingConstants.CENTER);
		lblQtd.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQtd.setFocusable(false);
		lblQtd.setBounds(385, 7, 100, 20);
		panelRegras.add(lblQtd);

		R1Qtd.setBounds(385, 34, 100, 20);
		panelRegras.add(R1Qtd);

		R2Qtd.setBounds(385, 61, 100, 20);
		panelRegras.add(R2Qtd);

		R3Qtd.setBounds(385, 88, 100, 20);
		panelRegras.add(R3Qtd);

		R4Qtd.setBounds(385, 115, 100, 20);
		panelRegras.add(R4Qtd);

		R5Qtd.setBounds(385, 142, 100, 20);
		panelRegras.add(R5Qtd);

		R6Qtd.setBounds(385, 169, 100, 20);
		panelRegras.add(R6Qtd);

		R7Qtd.setBounds(385, 196, 100, 20);
		panelRegras.add(R7Qtd);

		R8Qtd.setBounds(385, 223, 100, 20);
		panelRegras.add(R8Qtd);

		R9Qtd.setBounds(385, 250, 100, 20);
		panelRegras.add(R9Qtd);

		R10Qtd.setBounds(385, 277, 100, 20);
		panelRegras.add(R10Qtd);

		R11Qtd.setBounds(385, 304, 100, 20);
		panelRegras.add(R11Qtd);

		R12Qtd.setBounds(385, 331, 100, 20);
		panelRegras.add(R12Qtd);

		R13Qtd.setBounds(385, 358, 100, 20);
		panelRegras.add(R13Qtd);

		R14Qtd.setBounds(385, 385, 100, 20);
		panelRegras.add(R14Qtd);

		R15Qtd.setBounds(385, 412, 100, 20);
		panelRegras.add(R15Qtd);

		R16Qtd.setBounds(385, 439, 100, 20);
		panelRegras.add(R16Qtd);

		R17Qtd.setBounds(385, 466, 100, 20);
		panelRegras.add(R17Qtd);

		R18Qtd.setBounds(385, 493, 100, 20);
		panelRegras.add(R18Qtd);

		JLabel lblVizinho = new JLabel("Vizinho");
		lblVizinho.setHorizontalTextPosition(SwingConstants.CENTER);
		lblVizinho.setHorizontalAlignment(SwingConstants.CENTER);
		lblVizinho.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVizinho.setFocusable(false);
		lblVizinho.setBounds(531, 7, 100, 20);
		panelRegras.add(lblVizinho);

		R1Vizinho.setBounds(521, 34, 120, 20);
		panelRegras.add(R1Vizinho);

		R2Vizinho.setBounds(521, 61, 120, 20);
		panelRegras.add(R2Vizinho);

		R3Vizinho.setBounds(521, 88, 120, 20);
		panelRegras.add(R3Vizinho);

		R4Vizinho.setBounds(521, 115, 120, 20);
		panelRegras.add(R4Vizinho);

		R5Vizinho.setBounds(521, 142, 120, 20);
		panelRegras.add(R5Vizinho);

		R6Vizinho.setBounds(521, 169, 120, 20);
		panelRegras.add(R6Vizinho);

		R7Vizinho.setBounds(521, 196, 120, 20);
		panelRegras.add(R7Vizinho);

		R8Vizinho.setBounds(521, 223, 120, 20);
		panelRegras.add(R8Vizinho);

		R9Vizinho.setBounds(521, 250, 120, 20);
		panelRegras.add(R9Vizinho);

		R10Vizinho.setBounds(521, 277, 120, 20);
		panelRegras.add(R10Vizinho);

		R11Vizinho.setBounds(521, 304, 120, 20);
		panelRegras.add(R11Vizinho);

		R12Vizinho.setBounds(521, 331, 120, 20);
		panelRegras.add(R12Vizinho);

		R13Vizinho.setBounds(521, 358, 120, 20);
		panelRegras.add(R13Vizinho);

		R14Vizinho.setBounds(521, 385, 120, 20);
		panelRegras.add(R14Vizinho);

		R15Vizinho.setBounds(521, 412, 120, 20);
		panelRegras.add(R15Vizinho);

		R16Vizinho.setBounds(521, 439, 120, 20);
		panelRegras.add(R16Vizinho);

		R17Vizinho.setBounds(521, 466, 120, 20);
		panelRegras.add(R17Vizinho);

		R18Vizinho.setBounds(521, 493, 120, 20);
		panelRegras.add(R18Vizinho);

		JLabel lblNovoEstado = new JLabel("Novo Estado");
		lblNovoEstado.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNovoEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovoEstado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNovoEstado.setFocusable(false);
		lblNovoEstado.setBounds(687, 7, 100, 20);
		panelRegras.add(lblNovoEstado);

		R1NovoEstado.setBounds(677, 34, 120, 20);
		panelRegras.add(R1NovoEstado);

		R2NovoEstado.setBounds(677, 61, 120, 20);
		panelRegras.add(R2NovoEstado);

		R3NovoEstado.setBounds(677, 88, 120, 20);
		panelRegras.add(R3NovoEstado);

		R4NovoEstado.setBounds(677, 115, 120, 20);
		panelRegras.add(R4NovoEstado);

		R5NovoEstado.setBounds(677, 142, 120, 20);
		panelRegras.add(R5NovoEstado);

		R6NovoEstado.setBounds(677, 169, 120, 20);
		panelRegras.add(R6NovoEstado);

		R7NovoEstado.setBounds(677, 196, 120, 20);
		panelRegras.add(R7NovoEstado);

		R8NovoEstado.setBounds(677, 223, 120, 20);
		panelRegras.add(R8NovoEstado);

		R9NovoEstado.setBounds(677, 250, 120, 20);
		panelRegras.add(R9NovoEstado);

		R10NovoEstado.setBounds(677, 277, 120, 20);
		panelRegras.add(R10NovoEstado);

		R11NovoEstado.setBounds(677, 304, 120, 20);
		panelRegras.add(R11NovoEstado);

		R12NovoEstado.setBounds(677, 331, 120, 20);
		panelRegras.add(R12NovoEstado);

		R13NovoEstado.setBounds(677, 358, 120, 20);
		panelRegras.add(R13NovoEstado);

		R14NovoEstado.setBounds(677, 385, 120, 20);
		panelRegras.add(R14NovoEstado);

		R15NovoEstado.setBounds(677, 412, 120, 20);
		panelRegras.add(R15NovoEstado);

		R16NovoEstado.setBounds(677, 439, 120, 20);
		panelRegras.add(R16NovoEstado);

		R17NovoEstado.setBounds(677, 466, 120, 20);
		panelRegras.add(R17NovoEstado);

		R18NovoEstado.setBounds(677, 493, 120, 20);
		panelRegras.add(R18NovoEstado);

		JPanel panelModelos = new JPanel();
		panelModelos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelModelos.setBounds(10, 6, 839, 56);
		contentPane.add(panelModelos);
		panelModelos.setLayout(null);

		JLabel lblDefinirRegras = new JLabel("Definir Regras");
		lblDefinirRegras.setBounds(362, 6, 115, 17);
		panelModelos.add(lblDefinirRegras);
		lblDefinirRegras.setHorizontalAlignment(SwingConstants.CENTER);
		lblDefinirRegras.setFont(new Font("Tahoma", Font.BOLD, 14));

		rdbtnDeterminar = new JRadioButton("Determinar");
		rdbtnDeterminar.setBounds(116, 29, 130, 20);
		panelModelos.add(rdbtnDeterminar);
		rdbtnDeterminar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnDeterminar.setFocusable(false);
		buttonGroup.add(rdbtnDeterminar);

		rdbtnJogoDaVida = new JRadioButton("Jogo da Vida");
		rdbtnJogoDaVida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				organizeIndexes(controller.getRegrasJogoDaVida(), controller.getActiveStates());
			}
		});
		rdbtnJogoDaVida.setBounds(362, 29, 115, 20);
		panelModelos.add(rdbtnJogoDaVida);
		rdbtnJogoDaVida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnJogoDaVida.setFocusable(false);
		buttonGroup.add(rdbtnJogoDaVida);

		rdbtnCristaisDeUlam = new JRadioButton("Cristais de Ulam");
		rdbtnCristaisDeUlam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				organizeIndexes(controller.getRegrasCristaisDeUlam(), controller.getActiveStates());
			}
		});
		rdbtnCristaisDeUlam.setBounds(593, 29, 130, 20);
		panelModelos.add(rdbtnCristaisDeUlam);
		rdbtnCristaisDeUlam.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnCristaisDeUlam.setFocusable(false);
		buttonGroup.add(rdbtnCristaisDeUlam);

		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMenu.setBounds(10, 595, 839, 54);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);

		btnAvancar = new JButton("Avan\u00E7ar");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// há pelo menos uma regra selecionada
				if (numberOfActiveRules() > 0) {
					// salva o estado atual das regras
					controller.setRegras(getArrayRegras());
					// salva o ultimo modelo selecionado
					controller.setLastSelected(getSelectedModel());
					// instancia a janela de execução
					AppRun exe = new AppRun(controller);
					// torna a janela de execução visível
					exe.setVisible(true);
					// encerra a janela atual
					dispose();
				} else {
					infoTxtArea.setText("É necessário ter pelo menos uma regra ativa para continuar.");
				}
			}
		});
		btnAvancar.setPreferredSize(new Dimension(90, 20));
		btnAvancar.setFont(controller.getButtonFont());
		btnAvancar.setFocusable(false);
		btnAvancar.setBounds(681, 17, 155, 20);
		panelMenu.add(btnAvancar);

		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// salva o estado atual das regras
				controller.setRegras(getArrayRegras());
				// salva o ultimo modelo selecionado
				controller.setLastSelected(getSelectedModel());
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
		btnVoltar.setBounds(3, 17, 155, 20);
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
		infoTxtArea.setBounds(161, 3, 517, 48);
		panelMenu.add(infoTxtArea);

		// configura o model de todos os JComboBox
		configureModels(controller);

		organizeIndexes(controller.getRegras(), controller.getActiveStates());

	}
}

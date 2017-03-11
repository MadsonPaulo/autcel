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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.AppController;
import model.DrawSquare;

public class AppRun extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnExportar;
	private JButton btnReiniciar;
	private JButton btnImportar;
	private JLabel lblCiclo;
	private boolean avancoAutomatico = false;
	private JButton btnAnterior;
	private JButton btnPrximo;
	private JButton btnAvancoAutomatico;
	private JComboBox<String> comboBoxVelocidade;
	private int delay = 1000;
	private Timer timer;
	private boolean firstCycleRan = false;
	private DrawSquare squares;

	/**
	 * Atualiza o label que indica o ciclo atual
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void updateCycle(AppController controller) {
		lblCiclo.setText("  Ciclo: " + controller.getCiclo());
	}

	/**
	 * Inicia ou para o avanço automático a cada intervalo de tempo determinado
	 * 
	 * Author: Madson
	 * 
	 * @param iniciar
	 *            booleano para iniciar ou para o avanço automático
	 * @param controller
	 */
	private void setAutoAdvance(boolean iniciar, AppController controller) {
		if (iniciar) {
			callNextCycle(delay, controller);
			btnAvancoAutomatico.setText("Parar");
		} else {
			timer.stop();
			btnAvancoAutomatico.setText("Iniciar");
		}
	}

	/**
	 * Inicializa o timer de avanço automático
	 * 
	 * Author: Madson
	 * 
	 * @param delay
	 *            intervalo em milissegundos entre cada avanço de ciclo
	 * @param contr
	 */
	private void callNextCycle(int delay, AppController contr) {
		// cria a tarefa que irá se repetir de acordo com o delay
		ActionListener task = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chama o próximo ciclo no controller
				contr.nextCycle();
				// desenha a matriz
				drawMatriz(contr.getVector(), squares, (squares.getBounds().width / contr.getVector().length), contr);
				// atualiza o label de ciclo atual
				updateCycle(contr);
			}
		};

		// inicializa timer
		timer = new Timer(delay, task);
		// define o tempo de espera antes da primeira execução da tarefa
		timer.setInitialDelay(0);
		// inicia o timer
		timer.start();
	}

	private void drawMatriz(int[][] array, DrawSquare square, int size, AppController contr) {
		Color[] configColors = contr.getArrayOfCollors();
		int posX = 0;
		int posY = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				square.addSquare(posX, posY, size, size, configColors[array[i][j]]);
				posX += size;
			}
			posX = 0;
			posY += size;
		}
		repaint();
	}

	public AppRun(AppController controller) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				drawMatriz(controller.getVector(), squares, (squares.getBounds().width / controller.getVector().length),
						controller);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppRun.class.getResource("/img/main16x16.png")));
		setResizable(false);
		setTitle("Autcel: Execução");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 710);
		setPreferredSize(new Dimension(865, 710));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
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

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(null);
		panelMenu.setBounds(10, 11, 189, 640);
		contentPane.add(panelMenu);

		JPanel panelManual = new JPanel();
		panelManual.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelManual.setBounds(4, 196, 181, 80);
		panelMenu.add(panelManual);
		panelManual.setLayout(null);

		btnPrximo = new JButton("Pr\u00F3ximo");
		btnPrximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// caso nenhum ciclo tenha sido avançado
				if (firstCycleRan == false) {
					firstCycleRan = true;
				}
				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					// para o avanço automático
					setAutoAdvance(false, controller);
					avancoAutomatico = false;
				}
				// chama o próximo ciclo no controller
				controller.nextCycle();
				// desenha a matriz
				drawMatriz(controller.getVector(), squares, (squares.getBounds().width / controller.getVector().length),
						controller);
				// atualiza o label de ciclo atual
				updateCycle(controller);
			}
		});
		btnPrximo.setBounds(91, 46, 85, 20);
		btnPrximo.setPreferredSize(new Dimension(120, 20));
		btnPrximo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrximo.setFont(controller.getBoldFont());
		btnPrximo.setFocusable(false);
		btnPrximo.setAlignmentX(0.5f);
		panelManual.add(btnPrximo);

		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					// para o avanço automático
					setAutoAdvance(false, controller);
					avancoAutomatico = false;
				}
				// se não estiver no ciclo 0
				if (controller.getCiclo() > 0) {
					// retorna ao ciclo anterior
					controller.previousCycle();
					// desenha a matriz
					drawMatriz(controller.getVector(), squares,
							(squares.getBounds().width / controller.getVector().length), controller);
					// atualiza o label de ciclo atual
					updateCycle(controller);
				}
			}
		});
		btnAnterior.setBounds(3, 46, 85, 20);
		btnAnterior.setPreferredSize(new Dimension(120, 20));
		btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAnterior.setFont(controller.getBoldFont());
		btnAnterior.setFocusable(false);
		btnAnterior.setAlignmentX(0.5f);
		panelManual.add(btnAnterior);

		JLabel lblAvanoManual = new JLabel("Avan\u00E7o Manual");
		lblAvanoManual.setFont(controller.getBoldFont());
		lblAvanoManual.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvanoManual.setBounds(32, 13, 117, 20);
		panelManual.add(lblAvanoManual);

		JPanel panelAutomatico = new JPanel();
		panelAutomatico.setLayout(null);
		panelAutomatico.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelAutomatico.setBounds(4, 294, 181, 80);
		panelMenu.add(panelAutomatico);

		btnAvancoAutomatico = new JButton("Iniciar");
		btnAvancoAutomatico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// caso nenhum ciclo tenha sido avançado
				if (firstCycleRan == false) {
					firstCycleRan = true;
				}
				// Define o valor em milissegundos de intervalo entre as
				// repetições de acordo com índice de comboBox_Intervalo
				if (comboBoxVelocidade.getSelectedIndex() == 0) {
					delay = 50;
				} else if (comboBoxVelocidade.getSelectedIndex() == 1) {
					delay = 200;
				} else if (comboBoxVelocidade.getSelectedIndex() == 2) {
					delay = 500;
				} else if (comboBoxVelocidade.getSelectedIndex() == 3) {
					delay = 1000;
				}else if (comboBoxVelocidade.getSelectedIndex() == 4) {
					delay = 2000;
				}
				// se já estiver avançando automaticamente, cancela o avanço
				if (avancoAutomatico) {
					setAutoAdvance(false, controller);
					avancoAutomatico = false;
				} else {// senão, habilita o avanço automático
					setAutoAdvance(true, controller);
					avancoAutomatico = true;
				}
			}
		});
		btnAvancoAutomatico.setPreferredSize(new Dimension(120, 20));
		btnAvancoAutomatico.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAvancoAutomatico.setFont(controller.getBoldFont());
		btnAvancoAutomatico.setFocusable(false);
		btnAvancoAutomatico.setAlignmentX(0.5f);
		btnAvancoAutomatico.setBounds(3, 46, 85, 20);
		panelAutomatico.add(btnAvancoAutomatico);

		JLabel lblAvanoAutomtico = new JLabel("Avan\u00E7o Autom\u00E1tico");
		lblAvanoAutomtico.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvanoAutomtico.setFont(controller.getBoldFont());
		lblAvanoAutomtico.setBounds(18, 13, 144, 20);
		panelAutomatico.add(lblAvanoAutomtico);

		comboBoxVelocidade = new JComboBox<String>();
		comboBoxVelocidade.setFocusable(false);
		comboBoxVelocidade.setModel(
				new DefaultComboBoxModel<String>(new String[] { "     50 ms", "   200 ms", "   500 ms", " 1000 ms", " 2000 ms" }));
		comboBoxVelocidade.setBounds(91, 46, 85, 20);
		comboBoxVelocidade.setSelectedIndex(2);
		panelAutomatico.add(comboBoxVelocidade);

		JPanel panelEstados = new JPanel();
		panelEstados.setLayout(null);
		panelEstados.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelEstados.setBounds(4, 392, 181, 197);
		panelMenu.add(panelEstados);

		JPanel panelNomes = new JPanel();
		panelNomes.setLayout(null);
		panelNomes.setBounds(1, 3, 100, 191);
		panelEstados.add(panelNomes);

		JLabel nomeEstado1 = new JLabel("");
		nomeEstado1.setOpaque(true);
		nomeEstado1.setFont(controller.getNormalFont());
		nomeEstado1.setText(" " + controller.getNameState1());
		nomeEstado1.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado1.setBackground(Color.WHITE);
		nomeEstado1.setBounds(7, 7, 85, 16);
		panelNomes.add(nomeEstado1);

		JLabel nomeEstado2 = new JLabel("");
		nomeEstado2.setOpaque(true);
		nomeEstado2.setFont(controller.getNormalFont());
		nomeEstado2.setText(" " + controller.getNameState2());
		nomeEstado2.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado2.setBackground(Color.WHITE);
		nomeEstado2.setBounds(7, 30, 85, 16);
		panelNomes.add(nomeEstado2);

		JLabel nomeEstado3 = new JLabel("");
		nomeEstado3.setOpaque(true);
		nomeEstado3.setFont(controller.getNormalFont());
		nomeEstado3.setText(" " + controller.getNameState3());
		nomeEstado3.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado3.setBackground(Color.WHITE);
		nomeEstado3.setBounds(7, 53, 85, 16);
		panelNomes.add(nomeEstado3);

		JLabel nomeEstado4 = new JLabel("");
		nomeEstado4.setOpaque(true);
		nomeEstado4.setFont(controller.getNormalFont());
		nomeEstado4.setText(" " + controller.getNameState4());
		nomeEstado4.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado4.setBackground(Color.WHITE);
		nomeEstado4.setBounds(7, 76, 85, 16);
		panelNomes.add(nomeEstado4);

		JLabel nomeEstado5 = new JLabel("");
		nomeEstado5.setBounds(7, 99, 85, 16);
		panelNomes.add(nomeEstado5);
		nomeEstado5.setOpaque(true);
		nomeEstado5.setFont(controller.getNormalFont());
		nomeEstado5.setText(" " + controller.getNameState5());
		nomeEstado5.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado5.setBackground(Color.WHITE);

		JLabel nomeEstado6 = new JLabel("");
		nomeEstado6.setOpaque(true);
		nomeEstado6.setFont(controller.getNormalFont());
		nomeEstado6.setText(" " + controller.getNameState6());
		nomeEstado6.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado6.setBackground(Color.WHITE);
		nomeEstado6.setBounds(7, 122, 85, 16);
		panelNomes.add(nomeEstado6);

		JLabel nomeEstado7 = new JLabel("");
		nomeEstado7.setOpaque(true);
		nomeEstado7.setFont(controller.getNormalFont());
		nomeEstado7.setText(" " + controller.getNameState7());
		nomeEstado7.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado7.setBackground(Color.WHITE);
		nomeEstado7.setBounds(7, 145, 85, 16);
		panelNomes.add(nomeEstado7);

		JLabel nomeEstado8 = new JLabel("");
		nomeEstado8.setOpaque(true);
		nomeEstado8.setFont(controller.getNormalFont());
		nomeEstado8.setText(" " + controller.getNameState8());
		nomeEstado8.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado8.setBackground(Color.WHITE);
		nomeEstado8.setBounds(7, 168, 85, 16);
		panelNomes.add(nomeEstado8);

		JPanel panelCores = new JPanel();
		panelCores.setLayout(null);
		panelCores.setBounds(102, 3, 78, 191);
		panelEstados.add(panelCores);

		JLabel corEstado1 = new JLabel("");
		corEstado1.setToolTipText("");
		corEstado1.setOpaque(true);
		corEstado1.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado1.setBackground(controller.getDefaultCollor(1));
		corEstado1.setBounds(4, 7, 70, 16);
		panelCores.add(corEstado1);

		JLabel corEstado2 = new JLabel("");
		corEstado2.setOpaque(true);
		corEstado2.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado2.setBackground(controller.getDefaultCollor(2));
		corEstado2.setBounds(4, 30, 70, 16);
		panelCores.add(corEstado2);

		JLabel corEstado3 = new JLabel("");
		corEstado3.setOpaque(true);
		corEstado3.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado3.setBackground(controller.getDefaultCollor(3));
		corEstado3.setBounds(4, 53, 70, 16);
		panelCores.add(corEstado3);

		JLabel corEstado4 = new JLabel("");
		corEstado4.setOpaque(true);
		corEstado4.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado4.setBackground(controller.getDefaultCollor(4));
		corEstado4.setBounds(4, 76, 70, 16);
		panelCores.add(corEstado4);

		JLabel corEstado5 = new JLabel("");
		corEstado5.setOpaque(true);
		corEstado5.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado5.setBackground(controller.getDefaultCollor(5));
		corEstado5.setBounds(4, 99, 70, 16);
		panelCores.add(corEstado5);

		JLabel corEstado6 = new JLabel("");
		corEstado6.setOpaque(true);
		corEstado6.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado6.setBackground(controller.getDefaultCollor(6));
		corEstado6.setBounds(4, 122, 70, 16);
		panelCores.add(corEstado6);

		JLabel corEstado7 = new JLabel("");
		corEstado7.setOpaque(true);
		corEstado7.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado7.setBackground(controller.getDefaultCollor(7));
		corEstado7.setBounds(4, 145, 70, 16);
		panelCores.add(corEstado7);

		JLabel corEstado8 = new JLabel("");
		corEstado8.setOpaque(true);
		corEstado8.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado8.setBackground(controller.getDefaultCollor(8));
		corEstado8.setBounds(4, 168, 70, 16);
		panelCores.add(corEstado8);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// se já estiver avançando automaticamente, cancela o avanço
				// automático
				if (avancoAutomatico) {
					setAutoAdvance(false, controller);
					avancoAutomatico = false;
				}
				// abre um JOptionPane pedindo para confirmar ou cancelar a
				// voltar para tela anterior
				Object[] options = { "Confirmar", "Cancelar" };
				int option = JOptionPane.showOptionDialog(null,
						"Voltar para a tela de confuguração e apagar os ciclos percorridos?", "Atenção",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				// confirma
				if (option == 0) {
					// se já tiver avançado ao menos 1 ciclo
					if (firstCycleRan) {
						// reseta o vetor atual e vectorSaver
						controller.setVector(controller.getInitialCycle());
						controller.resetVectorSaver();
					}
					// reseta o ciclo
					controller.setCiclo(0);
					// instancia a janela de regras
					AppConfigRules rules = new AppConfigRules(controller);
					// torna a janela de regras visível
					rules.setVisible(true);
					// encerra a janela atual
					dispose();
				}
			}
		});
		btnVoltar.setPreferredSize(new Dimension(90, 20));
		btnVoltar.setFont(controller.getBoldFont());
		btnVoltar.setFocusable(false);
		btnVoltar.setBounds(14, 609, 160, 20);
		panelMenu.add(btnVoltar);

		JPanel arquivoPanel = new JPanel();
		arquivoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		arquivoPanel.setBounds(4, 0, 181, 80);
		panelMenu.add(arquivoPanel);

		btnImportar = new JButton("Importar");
		btnImportar.setEnabled(false);
		btnImportar.setPreferredSize(new Dimension(120, 20));
		btnImportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImportar.setFont(controller.getBoldFont());
		btnImportar.setFocusable(false);
		btnImportar.setAlignmentX(0.5f);
		arquivoPanel.add(btnImportar);

		btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exporta configurações atuais
				controller.exportData();
			}
		});
		btnExportar.setPreferredSize(new Dimension(120, 20));
		btnExportar.setMinimumSize(new Dimension(95, 25));
		btnExportar.setMaximumSize(new Dimension(120, 25));
		btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExportar.setFont(controller.getBoldFont());
		btnExportar.setFocusable(false);
		btnExportar.setAlignmentX(0.5f);
		arquivoPanel.add(btnExportar);

		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// se já estiver avançando automaticamente, cancela o avanço
				// automático
				if (avancoAutomatico) {
					setAutoAdvance(false, controller);
					avancoAutomatico = false;
				}
				// se já tiver avançado ao menos 1 ciclo
				if (firstCycleRan) {
					// copia o vetor inicial no vetor
					controller.copyVector(controller.getVector(), controller.getInitialCycle());
					// reseta o vectorSaver
					controller.resetVectorSaver();
				}
				// reseta o ciclo
				controller.setCiclo(0);
				// atualiza a parte visual
				drawMatriz(controller.getVector(), squares, (squares.getBounds().width / controller.getVector().length),
						controller);
				updateCycle(controller);
			}
		});
		btnReiniciar.setPreferredSize(new Dimension(120, 20));
		btnReiniciar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReiniciar.setFont(controller.getBoldFont());
		btnReiniciar.setFocusable(false);
		btnReiniciar.setAlignmentX(0.5f);
		arquivoPanel.add(btnReiniciar);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(4, 98, 181, 80);
		panelMenu.add(panel);
		panel.setLayout(null);

		lblCiclo = new JLabel("  Ciclo: 0");
		lblCiclo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCiclo.setFont(controller.getBoldFont());
		lblCiclo.setBounds(4, 26, 173, 27);
		panel.add(lblCiclo);

		squares = new DrawSquare();
		squares.setBorder(new LineBorder(new Color(0, 0, 0)));
		squares.setBounds(209, 11, 641, 641);
		getContentPane().add(squares);
		squares.setLayout(null);

		// desenha a matriz
		drawMatriz(controller.getVector(), squares, (squares.getBounds().width / controller.getVector().length),
				controller);

	}
}
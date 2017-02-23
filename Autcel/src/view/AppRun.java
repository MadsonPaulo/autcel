package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.AppController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppRun extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel matrizPanel;
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
	private int[][] copyVector;

	private void atualizarCiclo(AppController controller) {
		lblCiclo.setText("  Ciclo: " + controller.getCiclo());
	}

	private void setAvancoAutomatico(boolean iniciar, AppController controller) {
		if (iniciar) {
			callNextCycle(delay, controller);
			btnAvancoAutomatico.setText("Parar");
		} else {
			timer.stop();
			btnAvancoAutomatico.setText("Iniciar");
		}
	}

	private void callNextCycle(int delay, AppController contr) {
		ActionListener task = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contr.nextCycle();
				contr.draw(matrizPanel);
				atualizarCiclo(contr);
			}
		};

		// inicializa timer
		timer = new Timer(delay, task);
		// define o tempo de espera inicial
		timer.setInitialDelay(0);
		// inicia o timer
		timer.start();
	}

	/**
	 * Create the frame.
	 */
	public AppRun(AppController controller) {
		setResizable(false);
		setTitle("Autcel: Execução");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		matrizPanel = new JPanel();
		matrizPanel.setLayout(null);
		matrizPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		matrizPanel.setBounds(209, 11, 640, 640);
		contentPane.add(matrizPanel);

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
				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					setAvancoAutomatico(false, controller);
					avancoAutomatico = false;
				}
				controller.nextCycle();
				controller.draw(matrizPanel);
				atualizarCiclo(controller);
			}
		});
		btnPrximo.setBounds(91, 46, 85, 20);
		btnPrximo.setPreferredSize(new Dimension(120, 20));
		btnPrximo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrximo.setFont(controller.getButtonFont());
		btnPrximo.setFocusable(false);
		btnPrximo.setAlignmentX(0.5f);
		panelManual.add(btnPrximo);

		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					setAvancoAutomatico(false, controller);
					avancoAutomatico = false;
				}
				if (controller.getCiclo() > 0) {
					controller.previousCycle();
					controller.draw(matrizPanel);
					atualizarCiclo(controller);
				}
			}
		});
		btnAnterior.setBounds(3, 46, 85, 20);
		btnAnterior.setPreferredSize(new Dimension(120, 20));
		btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAnterior.setFont(controller.getButtonFont());
		btnAnterior.setFocusable(false);
		btnAnterior.setAlignmentX(0.5f);
		panelManual.add(btnAnterior);

		JLabel lblAvanoManual = new JLabel("Avan\u00E7o Manual");
		lblAvanoManual.setFont(new Font("Tahoma", Font.BOLD, 12));
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
				// Define o valor em milissegundos de intervalo entre as
				// repetições de acordo com indice em comboBox_Intervalo
				if (comboBoxVelocidade.getSelectedIndex() == 0) {
					delay = 200;
				} else if (comboBoxVelocidade.getSelectedIndex() == 1) {
					delay = 500;
				} else if (comboBoxVelocidade.getSelectedIndex() == 2) {
					delay = 1000;
				} else if (comboBoxVelocidade.getSelectedIndex() == 3) {
					delay = 2000;
				}
				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					setAvancoAutomatico(false, controller);
					avancoAutomatico = false;
				} else {
					setAvancoAutomatico(true, controller);
					avancoAutomatico = true;
				}
			}
		});
		btnAvancoAutomatico.setPreferredSize(new Dimension(120, 20));
		btnAvancoAutomatico.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAvancoAutomatico.setFont(controller.getButtonFont());
		btnAvancoAutomatico.setFocusable(false);
		btnAvancoAutomatico.setAlignmentX(0.5f);
		btnAvancoAutomatico.setBounds(3, 46, 85, 20);
		panelAutomatico.add(btnAvancoAutomatico);

		JLabel lblAvanoAutomtico = new JLabel("Avan\u00E7o Autom\u00E1tico");
		lblAvanoAutomtico.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvanoAutomtico.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAvanoAutomtico.setBounds(18, 13, 144, 20);
		panelAutomatico.add(lblAvanoAutomtico);

		comboBoxVelocidade = new JComboBox<String>();
		comboBoxVelocidade.setFocusable(false);
		comboBoxVelocidade.setModel(
				new DefaultComboBoxModel<String>(new String[] { "   200 ms", "   500 ms", " 1000 ms", " 2000 ms" }));
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
		nomeEstado1.setFont(controller.getLabelFont());
		nomeEstado1.setText(" " + controller.getNameState1());
		nomeEstado1.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado1.setBackground(Color.WHITE);
		nomeEstado1.setBounds(7, 7, 85, 16);
		panelNomes.add(nomeEstado1);

		JLabel nomeEstado2 = new JLabel("");
		nomeEstado2.setOpaque(true);
		nomeEstado2.setFont(controller.getLabelFont());
		nomeEstado2.setText(" " + controller.getNameState2());
		nomeEstado2.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado2.setBackground(Color.WHITE);
		nomeEstado2.setBounds(7, 30, 85, 16);
		panelNomes.add(nomeEstado2);

		JLabel nomeEstado3 = new JLabel("");
		nomeEstado3.setOpaque(true);
		nomeEstado3.setFont(controller.getLabelFont());
		nomeEstado3.setText(" " + controller.getNameState3());
		nomeEstado3.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado3.setBackground(Color.WHITE);
		nomeEstado3.setBounds(7, 53, 85, 16);
		panelNomes.add(nomeEstado3);

		JLabel nomeEstado4 = new JLabel("");
		nomeEstado4.setOpaque(true);
		nomeEstado4.setFont(controller.getLabelFont());
		nomeEstado4.setText(" " + controller.getNameState4());
		nomeEstado4.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado4.setBackground(Color.WHITE);
		nomeEstado4.setBounds(7, 76, 85, 16);
		panelNomes.add(nomeEstado4);

		JLabel nomeEstado5 = new JLabel("");
		nomeEstado5.setBounds(7, 99, 85, 16);
		panelNomes.add(nomeEstado5);
		nomeEstado5.setOpaque(true);
		nomeEstado5.setFont(controller.getLabelFont());
		nomeEstado5.setText(" " + controller.getNameState5());
		nomeEstado5.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado5.setBackground(Color.WHITE);

		JLabel nomeEstado6 = new JLabel("");
		nomeEstado6.setOpaque(true);
		nomeEstado6.setFont(controller.getLabelFont());
		nomeEstado6.setText(" " + controller.getNameState6());
		nomeEstado6.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado6.setBackground(Color.WHITE);
		nomeEstado6.setBounds(7, 122, 85, 16);
		panelNomes.add(nomeEstado6);

		JLabel nomeEstado7 = new JLabel("");
		nomeEstado7.setOpaque(true);
		nomeEstado7.setFont(controller.getLabelFont());
		nomeEstado7.setText(" " + controller.getNameState7());
		nomeEstado7.setBorder(new LineBorder(new Color(0, 0, 0)));
		nomeEstado7.setBackground(Color.WHITE);
		nomeEstado7.setBounds(7, 145, 85, 16);
		panelNomes.add(nomeEstado7);

		JLabel nomeEstado8 = new JLabel("");
		nomeEstado8.setOpaque(true);
		nomeEstado8.setFont(controller.getLabelFont());
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

				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					setAvancoAutomatico(false, controller);
					avancoAutomatico = false;
				}

				Object[] options = { "Confirmar", "Cancelar" };
				int option = JOptionPane.showOptionDialog(null,
						"Voltar para a tela de confuguração e apagar os ciclos percorridos?", "Atenção",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				// confirma
				if (option == 0) {
					// reseta o vetor atual e o ciclo
					controller.setVector(copyVector);
					controller.resetVectorSaver();
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
		btnVoltar.setFont(controller.getButtonFont());
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
		btnImportar.setFont(controller.getButtonFont());
		btnImportar.setFocusable(false);
		btnImportar.setAlignmentX(0.5f);
		arquivoPanel.add(btnImportar);

		btnExportar = new JButton("Exportar");
		btnExportar.setToolTipText("Exportar configura\u00E7\u00F5es para um arquivo externo");
		btnExportar.setPreferredSize(new Dimension(120, 20));
		btnExportar.setMinimumSize(new Dimension(95, 25));
		btnExportar.setMaximumSize(new Dimension(120, 25));
		btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExportar.setFont(controller.getButtonFont());
		btnExportar.setFocusable(false);
		btnExportar.setAlignmentX(0.5f);
		arquivoPanel.add(btnExportar);

		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// se já estiver avançando automaticamente
				if (avancoAutomatico) {
					setAvancoAutomatico(false, controller);
					avancoAutomatico = false;
				}

				// reseta o vetor atual e o ciclo
				controller.copiarVetor(controller.getVector(), copyVector);
				controller.resetVectorSaver();
				controller.setCiclo(0);
				// atualiza a parte visual
				controller.draw(matrizPanel);
				atualizarCiclo(controller);
			}
		});
		btnReiniciar.setToolTipText("Reiniciar aut\u00F4mato para o ciclo 0");
		btnReiniciar.setPreferredSize(new Dimension(120, 20));
		btnReiniciar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReiniciar.setFont(controller.getButtonFont());
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
		lblCiclo.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCiclo.setBounds(4, 26, 173, 27);
		panel.add(lblCiclo);

		// Desenha a matriz
		controller.fillPanel(matrizPanel);
		// Copia o vetor em seu estado inicial
		copyVector = new int[controller.getTamVector()][controller.getTamVector()];
		controller.copiarVetor(copyVector, controller.getVector());
	}
}
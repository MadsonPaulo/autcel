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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.NumberFormat;

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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.AppController;
import model.DrawSquare;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.border.EtchedBorder;

public class AppRun extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel infoPanel;
	private DrawSquare squares;
	private JScrollPane scrollPane;
	private Timer timer;
	private int[] statesAmount;
	private int delay = 1000;
	private boolean avancoAutomatico = false;
	private boolean firstCycleRan = false;
	private JComboBox<String> comboBoxVelocidade;
	private JButton btnExportar = new JButton();
	private JButton btnReiniciar = new JButton();
	private JButton btnImportar = new JButton();
	private JButton btnAnterior = new JButton();
	private JButton btnPrximo = new JButton();
	private JButton btnRules = new JButton();
	private JButton btnAvancoAutomatico = new JButton();
	private JButton btnConfiguration = new JButton();
	private JLabel corEstado1;
	private JLabel corEstado2;
	private JLabel corEstado3;
	private JLabel corEstado4;
	private JLabel corEstado5;
	private JLabel corEstado6;
	private JLabel corEstado7;
	private JLabel corEstado8;
	private JLabel lblAvanoManual = new JLabel();
	private JLabel lblAvanoAutomtico = new JLabel();
	private JLabel lblCiclo = new JLabel();
	private JLabel lblEscala = new JLabel();
	private JLabel lblCoord = new JLabel("[0, 0]");
	private JMenu mnArquivo = new JMenu();
	private JMenu mnControls = new JMenu();
	private JMenu mnImportar = new JMenu();
	private JMenu mnModelos = new JMenu();
	private JMenu mnPrerncias = new JMenu();
	private JMenu mnIdioma = new JMenu();
	private JMenu menuAjuda = new JMenu();
	private JMenu mnNavigation = new JMenu();
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
	private JMenuItem mntmAvançar = new JMenuItem();
	private JMenuItem mntmVoltar = new JMenuItem();
	private JMenuItem mntmAuto = new JMenuItem();
	private JMenuItem mntmRestart = new JMenuItem();

	/**
	 * Avança 1 ciclo
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void nextCycle(AppController controller) {
		// caso nenhum ciclo tenha sido avançado
		if (firstCycleRan == false) {
			firstCycleRan = true;
		}
		// se já estiver avançando automaticamente
		if (avancoAutomatico) {
			// para o avanço automático
			setTimer(false, controller);
			avancoAutomatico = false;
		}
		// chama o próximo ciclo no controller
		statesAmount = controller.nextCycle();
		// desenha a matriz
		controller.drawMatriz(squares);
		repaint();
		// atualiza o label de ciclo atual
		updateTexts(controller);
	}

	/**
	 * Retorna 1 ciclo
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void previousCycle(AppController controller) {
		// se já estiver avançando automaticamente
		if (avancoAutomatico) {
			// para o avanço automático
			setTimer(false, controller);
			avancoAutomatico = false;
		}
		// se não estiver no ciclo 0
		if (controller.getCiclo() > 0) {
			// retorna ao ciclo anterior
			statesAmount = controller.previousCycle();
			// desenha a matriz
			controller.drawMatriz(squares);
			repaint();
			// atualiza o label de ciclo atual
			updateTexts(controller);
		}
	}

	/**
	 * Carrega o delay e chama setTimer
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void autoAdvance(AppController controller) {
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
		} else if (comboBoxVelocidade.getSelectedIndex() == 4) {
			delay = 2000;
		}
		// se já estiver avançando automaticamente, cancela o avanço
		if (avancoAutomatico) {
			setTimer(false, controller);
			avancoAutomatico = false;
		} else {// senão, habilita o avanço automático
			setTimer(true, controller);
			avancoAutomatico = true;
		}
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
	private void setTimer(boolean iniciar, AppController controller) {
		String stop = "";
		String start = "";
		if (controller.getLanguage() == 0) {
			stop = "Parar";
			start = "Iniciar";
		} else if (controller.getLanguage() == 1) {
			stop = "Stop";
			start = "Start";
		}
		if (iniciar) {
			runTask(delay, controller);
			btnAvancoAutomatico.setText(stop);
		} else {
			timer.stop();
			btnAvancoAutomatico.setText(start);
		}
	}

	/**
	 * Inicializa a tarefa de avançar ciclos automaticamente
	 * 
	 * Author: Madson
	 * 
	 * @param delay
	 *            intervalo em milissegundos entre cada avanço de ciclo
	 * @param contr
	 */
	private void runTask(int delay, AppController contr) {
		// cria a tarefa que irá se repetir de acordo com o delay
		ActionListener task = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chama o próximo ciclo no controller
				statesAmount = contr.nextCycle();
				// desenha a matriz
				contr.drawMatriz(squares);
				repaint();
				// atualiza o label de ciclo atual
				updateTexts(contr);
			}
		};

		// inicializa timer
		timer = new Timer(delay, task);
		// define o tempo de espera antes da primeira execução da tarefa
		timer.setInitialDelay(0);
		// inicia o timer
		timer.start();
	}

	/**
	 * Atualiza o label que indica o ciclo atual e os contadores de população
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void updateTexts(AppController controller) {
		String cycle = "";
		if (controller.getLanguage() == 0) {
			cycle = "  Ciclo: ";
		} else if (controller.getLanguage() == 1) {
			cycle = "  Cycle: ";
		}
		lblCiclo.setBounds(4, 12, 173, 27);
		infoPanel.add(lblCiclo);
		lblCiclo.setText(cycle + controller.getCiclo());
		corEstado1.setText(NumberFormat.getIntegerInstance().format(statesAmount[0]));
		corEstado2.setText(NumberFormat.getIntegerInstance().format(statesAmount[1]));
		corEstado3.setText(NumberFormat.getIntegerInstance().format(statesAmount[2]));
		corEstado4.setText(NumberFormat.getIntegerInstance().format(statesAmount[3]));
		corEstado5.setText(NumberFormat.getIntegerInstance().format(statesAmount[4]));
		corEstado6.setText(NumberFormat.getIntegerInstance().format(statesAmount[5]));
		corEstado7.setText(NumberFormat.getIntegerInstance().format(statesAmount[6]));
		corEstado8.setText(NumberFormat.getIntegerInstance().format(statesAmount[7]));
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
		JLabel[] labels = { corEstado1, corEstado2, corEstado3, corEstado4, corEstado5, corEstado6, corEstado7,
				corEstado8 };
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == Color.GRAY || colors[i] == Color.BLUE || colors[i] == Color.RED) {
				labels[i].setForeground(Color.WHITE);
			} else {
				labels[i].setForeground(Color.BLACK);
			}
		}
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
	 * Retorna para o ciclo inicial
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void restartAutomaton(AppController contr) {
		// se já estiver avançando automaticamente, cancela o avanço
		// automático
		if (avancoAutomatico) {
			setTimer(false, contr);
			avancoAutomatico = false;
		}
		// se já tiver avançado ao menos 1 ciclo
		if (firstCycleRan) {
			// copia o vetor inicial no vetor
			contr.copyVector(contr.getVector(), contr.getInitialCycle());
			// reseta o vectorSaver
			contr.resetVectorSaver();
		}
		// reseta o ciclo
		contr.setCiclo(0);
		statesAmount = contr.countStates();
		// atualiza a parte visual
		contr.drawMatriz(squares);
		repaint();
		updateTexts(contr);

		firstCycleRan = false;
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
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.75));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.25));
		} else if (area == 2) {// noroeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.25));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.25));
		} else if (area == 3) {// sudeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.75));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.75));
		} else if (area == 4) {// sudoeste
			scrollPane.getHorizontalScrollBar().setValue((int) (maxLessExtent * 0.25));
			scrollPane.getVerticalScrollBar().setValue((int) (maxLessExtent * 0.75));
		}
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
			setTitle("Autcel: Execução");
			// menus
			mnArquivo.setText("Arquivo");
			mnImportar.setText("Importar");
			mnModelos.setText("Modelos");
			mnPrerncias.setText("Preferências");
			mnIdioma.setText("Idioma");
			mntmProcurar.setText("Procurar");
			mntmExportar.setText("Exportar");
			mnControls.setText("Controles");
			mntmSair.setText("Sair");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmSobre.setText("Sobre o Autcel");
			menuAjuda.setText("Ajuda");
			mntmAvançar.setText("Próximo Ciclo");
			mntmVoltar.setText("Ciclo Anterior");
			mntmAuto.setText("Avanço Automático");
			mntmRestart.setText("Reiniciar");
			mnNavigation.setText("Navegação");
			mntmCenter.setText("Centro");
			mntmNEast.setText("Nordeste");
			mntmNWest.setText("Noroeste");
			mntmSEast.setText("Sudeste");
			mntmSWest.setText("Sudoeste");
			// botões
			btnImportar.setText("Importar");
			btnExportar.setText("Exportar");
			btnReiniciar.setText("Reiniciar");
			btnAnterior.setText("Anterior");
			btnPrximo.setText("Próximo");
			btnAvancoAutomatico.setText("Iniciar");
			btnRules.setText("Regras");
			btnConfiguration.setText("Configuração");
			// labels
			lblCiclo.setText("  Ciclo: 0");
			lblAvanoManual.setText("Avanço Manual");
			lblAvanoAutomtico.setText("Avanço Automático");
			updateScale(contr);
		} else if (value == 1) {
			// título
			setTitle("Autcel: Execution");
			// menus
			mnArquivo.setText("File");
			mnImportar.setText("Import");
			mnModelos.setText("Templates");
			mnPrerncias.setText("Settings");
			mnIdioma.setText("Language");
			mntmProcurar.setText("Search");
			mntmExportar.setText("Export");
			mnControls.setText("Controls");
			mntmSair.setText("Exit");
			mntmPortugus.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("User Manual");
			mntmSobre.setText("About Autcel");
			menuAjuda.setText("Help");
			mntmAvançar.setText("Next Cycle");
			mntmVoltar.setText("Previous Cycle");
			mntmAuto.setText("Auto Advance");
			mntmRestart.setText("Restart");
			mnNavigation.setText("Navigation");
			mntmCenter.setText("Center");
			mntmNEast.setText("North-East");
			mntmNWest.setText("North-West");
			mntmSEast.setText("South-East");
			mntmSWest.setText("South-West");
			// botões
			btnImportar.setText("Import");
			btnExportar.setText("Export");
			btnReiniciar.setText("Restart");
			btnAnterior.setText("Previous");
			btnPrximo.setText("Next");
			btnAvancoAutomatico.setText("Start");
			btnRules.setText("Rules");
			btnConfiguration.setText("Configuration");
			// labels
			lblCiclo.setText("  Cycle: 0");
			lblAvanoManual.setText("Manual Advance");
			lblAvanoAutomtico.setText("Auto Advance");
			updateScale(contr);
		}
	}

	public AppRun(AppController controller) {
		// caso a janela seja movida, redesenha o grid
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				controller.drawMatriz(squares);
				repaint();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppRun.class.getResource("/img/main16x16.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 700);
		setPreferredSize(new Dimension(865, 710));
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
		menuBar.add(mnControls);
		mnControls.add(mntmAvançar);
		mnControls.add(mntmVoltar);
		mnControls.add(mntmAuto);
		mnControls.add(mntmRestart);
		menuBar.add(mnNavigation);
		mnNavigation.add(mntmCenter);
		mnNavigation.add(mntmNEast);
		mnNavigation.add(mntmNWest);
		mnNavigation.add(mntmSEast);
		mnNavigation.add(mntmSWest);
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
		mnControls.setFont(controller.getBoldFont());
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
		mntmAvançar.setFont(controller.getBoldFont());
		mntmVoltar.setFont(controller.getBoldFont());
		mntmAuto.setFont(controller.getBoldFont());
		mntmRestart.setFont(controller.getBoldFont());
		mnNavigation.setFont(controller.getBoldFont());
		mntmCenter.setFont(controller.getBoldFont());
		mntmNEast.setFont(controller.getBoldFont());
		mntmNWest.setFont(controller.getBoldFont());
		mntmSEast.setFont(controller.getBoldFont());
		mntmSWest.setFont(controller.getBoldFont());
		// atalhos
		mntmAvançar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true));
		mntmVoltar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true));
		mntmAuto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true));
		mntmRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, true));
		mntmCenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, true));
		mntmNEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true));
		mntmNWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true));
		mntmSEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, true));
		mntmSWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true));
		// ações
		mntmAvançar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextCycle(controller);
			}
		});
		mntmVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				previousCycle(controller);
			}
		});
		mntmAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoAdvance(controller);
			}
		});
		mntmRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				restartAutomaton(controller);
			}
		});
		mntmProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		mntmExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// exporta configurações atuais
				controller.exportData();
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
		
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(null);
		panelMenu.setBounds(9, 3, 189, 643);
		contentPane.add(panelMenu);

		JPanel manualPanel = new JPanel();
		manualPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		manualPanel.setBounds(4, 204, 181, 80);
		panelMenu.add(manualPanel);
		manualPanel.setLayout(null);

		btnPrximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextCycle(controller);
			}
		});
		btnPrximo.setBounds(91, 46, 85, 20);
		btnPrximo.setPreferredSize(new Dimension(120, 20));
		btnPrximo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrximo.setFont(controller.getBoldFont());
		btnPrximo.setFocusable(false);
		btnPrximo.setAlignmentX(0.5f);
		manualPanel.add(btnPrximo);

		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousCycle(controller);
			}
		});
		btnAnterior.setBounds(3, 46, 85, 20);
		btnAnterior.setPreferredSize(new Dimension(120, 20));
		btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAnterior.setFont(controller.getBoldFont());
		btnAnterior.setFocusable(false);
		btnAnterior.setAlignmentX(0.5f);
		manualPanel.add(btnAnterior);

		lblAvanoManual.setFont(controller.getBoldFont());
		lblAvanoManual.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvanoManual.setBounds(32, 13, 117, 20);
		manualPanel.add(lblAvanoManual);

		JPanel autoPanel = new JPanel();
		autoPanel.setLayout(null);
		autoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		autoPanel.setBounds(4, 291, 181, 80);
		panelMenu.add(autoPanel);

		btnAvancoAutomatico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoAdvance(controller);
			}
		});
		btnAvancoAutomatico.setPreferredSize(new Dimension(120, 20));
		btnAvancoAutomatico.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAvancoAutomatico.setFont(controller.getBoldFont());
		btnAvancoAutomatico.setFocusable(false);
		btnAvancoAutomatico.setAlignmentX(0.5f);
		btnAvancoAutomatico.setBounds(3, 46, 85, 20);
		autoPanel.add(btnAvancoAutomatico);

		lblAvanoAutomtico.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvanoAutomtico.setFont(controller.getBoldFont());
		lblAvanoAutomtico.setBounds(18, 13, 144, 20);
		autoPanel.add(lblAvanoAutomtico);

		comboBoxVelocidade = new JComboBox<String>();
		comboBoxVelocidade.setFocusable(false);
		comboBoxVelocidade.setModel(new DefaultComboBoxModel<String>(
				new String[] { "     50 ms", "   200 ms", "   500 ms", " 1000 ms", " 2000 ms" }));
		comboBoxVelocidade.setBounds(91, 46, 85, 20);
		comboBoxVelocidade.setSelectedIndex(0);
		autoPanel.add(comboBoxVelocidade);

		JPanel statesPanel = new JPanel();
		statesPanel.setLayout(null);
		statesPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		statesPanel.setBounds(4, 378, 181, 197);
		panelMenu.add(statesPanel);

		JPanel panelNomes = new JPanel();
		panelNomes.setLayout(null);
		panelNomes.setBounds(1, 3, 100, 191);
		statesPanel.add(panelNomes);

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
		statesPanel.add(panelCores);

		corEstado1 = new JLabel("102400");
		corEstado1.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado1.setToolTipText("");
		corEstado1.setOpaque(true);
		corEstado1.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado1.setBackground(controller.getColor(1));
		corEstado1.setBounds(4, 7, 70, 16);
		panelCores.add(corEstado1);

		corEstado2 = new JLabel("102400");
		corEstado2.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado2.setOpaque(true);
		corEstado2.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado2.setBackground(controller.getColor(2));
		corEstado2.setBounds(4, 30, 70, 16);
		panelCores.add(corEstado2);

		corEstado3 = new JLabel("102400");
		corEstado3.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado3.setOpaque(true);
		corEstado3.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado3.setBackground(controller.getColor(3));
		corEstado3.setBounds(4, 53, 70, 16);
		panelCores.add(corEstado3);

		corEstado4 = new JLabel("102400");
		corEstado4.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado4.setOpaque(true);
		corEstado4.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado4.setBackground(controller.getColor(4));
		corEstado4.setBounds(4, 76, 70, 16);
		panelCores.add(corEstado4);

		corEstado5 = new JLabel("102400");
		corEstado5.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado5.setOpaque(true);
		corEstado5.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado5.setBackground(controller.getColor(5));
		corEstado5.setBounds(4, 99, 70, 16);
		panelCores.add(corEstado5);

		corEstado6 = new JLabel("102400");
		corEstado6.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado6.setOpaque(true);
		corEstado6.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado6.setBackground(controller.getColor(6));
		corEstado6.setBounds(4, 122, 70, 16);
		panelCores.add(corEstado6);

		corEstado7 = new JLabel("102400");
		corEstado7.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado7.setOpaque(true);
		corEstado7.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado7.setBackground(controller.getColor(7));
		corEstado7.setBounds(4, 145, 70, 16);
		panelCores.add(corEstado7);

		corEstado8 = new JLabel("102400");
		corEstado8.setHorizontalAlignment(SwingConstants.RIGHT);
		corEstado8.setOpaque(true);
		corEstado8.setBorder(new LineBorder(new Color(0, 0, 0)));
		corEstado8.setBackground(controller.getColor(8));
		corEstado8.setBounds(4, 168, 70, 16);
		panelCores.add(corEstado8);

		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// se já estiver avançando automaticamente, cancela o avanço
				// automático
				if (avancoAutomatico) {
					setTimer(false, controller);
					avancoAutomatico = false;
				}

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
		});
		btnRules.setPreferredSize(new Dimension(90, 20));
		btnRules.setFont(controller.getBoldFont());
		btnRules.setFocusable(false);
		btnRules.setBounds(14, 609, 160, 20);
		panelMenu.add(btnRules);

		JPanel filePanel = new JPanel();
		filePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		filePanel.setBounds(4, 7, 181, 70);
		panelMenu.add(filePanel);
		btnImportar.setBounds(30, 10, 120, 20);
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		filePanel.setLayout(null);

		btnImportar.setPreferredSize(new Dimension(120, 20));
		btnImportar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImportar.setFont(controller.getBoldFont());
		btnImportar.setFocusable(false);
		btnImportar.setAlignmentX(0.5f);
		filePanel.add(btnImportar);
		btnExportar.setBounds(30, 40, 120, 20);

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
		filePanel.add(btnExportar);

		infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoPanel.setBounds(4, 84, 181, 113);
		panelMenu.add(infoPanel);
		infoPanel.setLayout(null);
		btnReiniciar.setBounds(30, 51, 120, 20);
		infoPanel.add(btnReiniciar);

		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				restartAutomaton(controller);
			}
		});
		btnReiniciar.setPreferredSize(new Dimension(120, 20));
		btnReiniciar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReiniciar.setFont(controller.getBoldFont());
		btnReiniciar.setFocusable(false);
		btnReiniciar.setAlignmentX(0.5f);

		lblEscala.setHorizontalAlignment(SwingConstants.CENTER);
		lblEscala.setFont(controller.getNormalFont());
		lblEscala.setBounds(15, 83, 70, 16);
		infoPanel.add(lblEscala);

		lblCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoord.setFont(controller.getNormalFont());
		lblCoord.setBounds(100, 83, 66, 16);
		infoPanel.add(lblCoord);

		lblCiclo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCiclo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		lblCiclo.setFont(controller.getBoldFont());
		btnConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// se já estiver avançando automaticamente, cancela o avanço
				// automático
				if (avancoAutomatico) {
					setTimer(false, controller);
					avancoAutomatico = false;
				}

				// se já tiver avançado ao menos 1 ciclo
				if (firstCycleRan) {
					// reseta o vetor atual e vectorSaver
					controller.setVector(controller.getInitialCycle());
					controller.resetVectorSaver();
				}
				// reseta o ciclo
				controller.setCiclo(0);
				// instancia a janela de configuração
				AppConfigAutomaton config = new AppConfigAutomaton(controller);
				// torna a janela de configuração visível
				config.setVisible(true);
				// encerra a janela atual
				dispose();
			}
		});

		btnConfiguration.setPreferredSize(new Dimension(90, 20));
		btnConfiguration.setFont(controller.getBoldFont());
		btnConfiguration.setFocusable(false);
		btnConfiguration.setBounds(14, 582, 160, 20);
		panelMenu.add(btnConfiguration);

		squares = new DrawSquare(controller.getSqrSize() * controller.getTamVector());
		squares.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// razão entre a largura de squares2 e tamanho da matriz
				double value = (squares.getWidth() * 1.0) / controller.getTamVector();
				// posição x e y do local clicado
				int posX = (int) Math.ceil(e.getX() / value) - 1;
				int posY = (int) Math.ceil(e.getY() / value) - 1;
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
		squares.setLayout(null);

		scrollPane = new JScrollPane(squares);
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				// determina a metade do scroll
				int middle;
				// posição do mouse
				Point mouseLoc = e.getPoint();
				// posição da view
				Point viewPort = scrollPane.getViewport().getViewPosition();
				Point novo;
				if (e.getWheelRotation() >= 0) {// zoom out
					// checa a escala
					if (controller.getSqrSize() > 2) {
						middle = (int) (scrollPane.getViewport().getExtentSize().getWidth());
						// nova posição após zoom
						novo = new Point((viewPort.x - (middle - mouseLoc.x)) / 2,
								(viewPort.y - (middle - mouseLoc.y)) / 2);
						// altera a escala, tamanho dos quadrados
						controller.setSqrSize(controller.getSqrSize() / 2);
						// atualiza as barras de scroll de squares
						squares.setPrefSize(controller.getSqrSize() * controller.getTamVector());
						// atualiza o texto de escala
						updateScale(controller);
						// redesenha o grid
						controller.drawMatriz(squares);
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
						squares.setPrefSize(controller.getSqrSize() * controller.getTamVector());
						// atualiza o texto de escala
						updateScale(controller);
						// redesenha o grid
						controller.drawMatriz(squares);
						repaint();
						// ajusta a visualização
						scrollPane.getViewport().setViewPosition(novo);
					}
				}
			}
		});
		scrollPane.setBounds(207, 3, 643, 643);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
		scrollPane.getVerticalScrollBar().setUnitIncrement(50);
		contentPane.add(scrollPane);
		// remove o scroll utilizando a roda do mouse
		scrollPane.removeMouseWheelListener(scrollPane.getMouseWheelListeners()[0]);

		// determina o idioma
		setLanguage(controller.getLanguage(), controller);
		// desenha a matriz
		controller.drawMatriz(squares);
		// atualiza os contadores de estados
		statesAmount = controller.countStates();
		updateTexts(controller);
		// determina a cor da fonte da população
		updatePopBackground(controller);
	}
}
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
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.border.EtchedBorder;
import controller.AppController;
import model.DrawSquare;

public class AppRun extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel infoPanel;
	private DrawSquare squares;
	private JScrollPane scrollPane;
	private Timer timer;
	private int[] statesAmount;
	private int delay = 1000;
	private boolean automaticAdvance = false;
	private boolean firstCycleRan = false;
	private JComboBox<String> comboBoxSpeed;
	private JButton btnExport = new JButton();
	private JButton btnRestart = new JButton();
	private JButton btnImport = new JButton();
	private JButton btnPrevious = new JButton();
	private JButton btnNext = new JButton();
	private JButton btnRules = new JButton();
	private JButton btnAutoAdvance = new JButton();
	private JButton btnConfiguration = new JButton();
	private JLabel colorState1;
	private JLabel colorState2;
	private JLabel colorState3;
	private JLabel colorState4;
	private JLabel colorState5;
	private JLabel colorState6;
	private JLabel colorState7;
	private JLabel colorState8;
	private JLabel lblManualAdvance = new JLabel();
	private JLabel lblAutoAdvance = new JLabel();
	private JLabel lblCycle = new JLabel();
	private JLabel lblScale = new JLabel();
	private JLabel lblCoord = new JLabel("[0, 0]");
	private JMenu mnFile = new JMenu();
	private JMenu mnControls = new JMenu();
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
	private JMenuItem mntmNext = new JMenuItem();
	private JMenuItem mntmPrevious = new JMenuItem();
	private JMenuItem mntmAuto = new JMenuItem();
	private JMenuItem mntmRestart = new JMenuItem();
	private JMenuItem prefab1 = new JMenuItem("Conway's Game of Life");
	private JMenuItem prefab2 = new JMenuItem("Ulam's Crystals");
	private JMenuItem prefab3 = new JMenuItem("Rule 614");
	private JMenuItem prefab4 = new JMenuItem("Tsunami");

	/**
	 * Advances one cycle
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void nextCycle(AppController controller) {
		// if no cycle has been advanced yetif no cycle has been advanced yet
		if (firstCycleRan == false) {
			firstCycleRan = true;
		}
		// if it is auto advancing
		if (automaticAdvance) {
			// stops the auto advancing
			setTimer(false, controller);
			automaticAdvance = false;
		}
		// call the next cycle on the controller
		statesAmount = controller.nextCycle(squares);
		// updates the screen
		controller.drawMatriz(squares);
		repaint();
		// updates the labels
		updateTexts(controller);
	}

	/**
	 * Returns one cycle
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void previousCycle(AppController controller) {
		// if it is auto advancing
		if (automaticAdvance) {
			// stops the auto advancing
			setTimer(false, controller);
			automaticAdvance = false;
		}
		// if it is not on cycle 0
		if (controller.getCycle() > 0) {
			// returns to the previous cycle
			statesAmount = controller.previousCycle();
			// draw the matrix
			controller.drawMatriz(squares);
			repaint();
			// updates the labels
			updateTexts(controller);
		}
	}

	/**
	 * Loads the delay value and calls setTimer
	 * 
	 * Author: Madson
	 * 
	 * @param controller
	 */
	private void autoAdvance(AppController controller) {
		// if no cycle has been advanced yet
		if (firstCycleRan == false) {
			firstCycleRan = true;
		}
		// defines the delay in milliseconds of the turns advance
		if (comboBoxSpeed.getSelectedIndex() == 0) {
			delay = 20;
		} else if (comboBoxSpeed.getSelectedIndex() == 1) {
			delay = 50;
		} else if (comboBoxSpeed.getSelectedIndex() == 2) {
			delay = 100;
		} else if (comboBoxSpeed.getSelectedIndex() == 3) {
			delay = 200;
		} else if (comboBoxSpeed.getSelectedIndex() == 4) {
			delay = 1000;
		}
		// if it is auto advancing, cancela o avanço
		if (automaticAdvance) {
			setTimer(false, controller);
			automaticAdvance = false;
		} else {// if not, starts the auto advance
			setTimer(true, controller);
			automaticAdvance = true;
		}
	}

	/**
	 * Starts or stops the auto advancing
	 * 
	 * Author: Madson
	 * 
	 * @param start
	 *            determines if is to start the auto advancing
	 * @param controller
	 */
	private void setTimer(boolean start, AppController controller) {
		String stopText = "Parar";
		String startText = "Iniciar";
		if (controller.getLanguage() == 1) {
			stopText = "Stop";
			startText = "Start";
		}
		if (start) {
			runTask(delay, controller);
			btnAutoAdvance.setText(stopText);
		} else {
			timer.stop();
			btnAutoAdvance.setText(startText);
		}
	}

	/**
	 * Starts the task of auto advancing
	 * 
	 * Author: Madson
	 * 
	 * @param delay
	 *            interval in milliseconds between cycle advances
	 * @param contr
	 */
	private void runTask(int delay, AppController contr) {
		// creates a task that will be repeated after the delay time until be
		// stopped
		ActionListener task = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// calls the next cycle in the controller
				statesAmount = contr.nextCycle(squares);
				// updates the screen
				contr.drawMatriz(squares);
				repaint();
				// updates the labels
				updateTexts(contr);
			}
		};

		// initializes the timer
		timer = new Timer(delay, task);
		// defines the delay before the first execution of the task
		timer.setInitialDelay(0);
		// starts the timer
		timer.start();
	}

	/**
	 * Fix the scale when there is a change in the matrix size
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
		// changes the scale, the size of the rectangles
		contr.setSqrSize(size);
		// updates the scroll bars
		squares.setPrefSize(contr.getSqrSize() * contr.getVectorSize());
		// updates the scale text
		updateScale(contr);
		// redraws the grid
		contr.drawMatriz(squares);
		repaint();
	}

	/**
	 * Updates the cycle and population labels
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
		lblCycle.setBounds(4, 12, 173, 27);
		infoPanel.add(lblCycle);
		lblCycle.setText(cycle + controller.getCycle());
		colorState1.setText(NumberFormat.getIntegerInstance().format(statesAmount[0]));
		colorState2.setText(NumberFormat.getIntegerInstance().format(statesAmount[1]));
		colorState3.setText(NumberFormat.getIntegerInstance().format(statesAmount[2]));
		colorState4.setText(NumberFormat.getIntegerInstance().format(statesAmount[3]));
		colorState5.setText(NumberFormat.getIntegerInstance().format(statesAmount[4]));
		colorState6.setText(NumberFormat.getIntegerInstance().format(statesAmount[5]));
		colorState7.setText(NumberFormat.getIntegerInstance().format(statesAmount[6]));
		colorState8.setText(NumberFormat.getIntegerInstance().format(statesAmount[7]));
	}

	/**
	 * Updates the population counters font color to make sure the numbers are
	 * visible
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void updatePopBackground(AppController contr) {
		Color[] colors = contr.getArrayOfCollors();
		JLabel[] labels = { colorState1, colorState2, colorState3, colorState4, colorState5, colorState6, colorState7,
				colorState8 };
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == Color.GRAY || colors[i] == Color.BLUE || colors[i] == Color.RED) {
				labels[i].setForeground(Color.WHITE);
			} else {
				labels[i].setForeground(Color.BLACK);
			}
		}
	}

	/**
	 * Tries to import configurations. If succeed, closes the current screen and
	 * opens another execution screen
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 *            controller
	 */
	private void importData(AppController contr) {
		// if it is auto advancing
		if (automaticAdvance) {
			// stops the auto advancing
			setTimer(false, contr);
			automaticAdvance = false;
		}
		// if succeeded in importing the configurations
		if (contr.importData()) {
			// creates a new instance of execution
			AppRun run = new AppRun(contr);
			// turns it visible
			run.setVisible(true);
			// disposes the current screen
			dispose();
		}
	}

	/**
	 * Updates the scale text
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
	 * Returns to the initial turn
	 * 
	 * Author: Madson
	 * 
	 * @param contr
	 */
	private void restartAutomaton(AppController contr) {
		// if it is auto advancing, cancels it
		if (automaticAdvance) {
			setTimer(false, contr);
			automaticAdvance = false;
		}
		// if has advanced at least one cycle
		if (firstCycleRan) {
			// copies the initial vector in the vector
			contr.copyVector(contr.getVector(), contr.getInitialCycle());
			// resets the vectorSaver
			contr.resetVectorSaver();
		}
		// resets the cycle
		contr.setCycle(0);
		statesAmount = contr.countStates();
		// redraws the grid and updates the texts
		contr.drawMatriz(squares);
		repaint();
		updateTexts(contr);

		firstCycleRan = false;
	}

	/**
	 * Changes the focus of the scrolls in a specific area
	 * 
	 * Author: Madson
	 * 
	 * @param area
	 */
	private void setFocus(int area) {
		// limit of the border extension, scrollable range
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

	// TODO Implement Internalization
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
			setTitle("ESACEL: Execução");
			// menus
			mnFile.setText("Arquivo");
			mnImport.setText("Importar");
			mnModels.setText("Modelos");
			mnPrefs.setText("Preferências");
			mnLanguage.setText("Idioma");
			mntmSearch.setText("Procurar");
			mntmExport.setText("Exportar");
			mnNew.setText("Novo Autômato");
			mnControls.setText("Controles");
			mntmExit.setText("Sair");
			mntmPortuguese.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("Manual de Uso");
			mntmAbout.setText("Sobre o ESACEL");
			mnHelp.setText("Ajuda");
			mntmNext.setText("Próximo Ciclo");
			mntmPrevious.setText("Ciclo Anterior");
			mntmAuto.setText("Avanço Automático");
			mntmRestart.setText("Reiniciar");
			mnNavigation.setText("Navegação");
			mntmCenter.setText("Centro");
			mntmNEast.setText("Nordeste");
			mntmNWest.setText("Noroeste");
			mntmSEast.setText("Sudeste");
			mntmSWest.setText("Sudoeste");
			// buttons
			btnImport.setText("Importar");
			btnExport.setText("Exportar");
			btnRestart.setText("Reiniciar");
			btnPrevious.setText("Anterior");
			btnNext.setText("Próximo");
			btnAutoAdvance.setText("Iniciar");
			btnRules.setText("Regras");
			btnConfiguration.setText("Configuração");
			// labels
			lblCycle.setText("  Ciclo: 0");
			lblManualAdvance.setText("Avanço Manual");
			lblAutoAdvance.setText("Avanço Automático");
			updateScale(contr);
		} else if (value == 1) {
			// title
			setTitle("ESACEL: Execution");
			// menus
			mnFile.setText("File");
			mnImport.setText("Import");
			mnModels.setText("Templates");
			mnPrefs.setText("Settings");
			mnLanguage.setText("Language");
			mntmSearch.setText("Search");
			mntmExport.setText("Export");
			mnControls.setText("Controls");
			mnNew.setText("New Automaton");
			mntmExit.setText("Exit");
			mntmPortuguese.setText("Português");
			mntmEnglish.setText("English");
			mntmManual.setText("User Manual");
			mntmAbout.setText("About ESACEL");
			mnHelp.setText("Help");
			mntmNext.setText("Next Cycle");
			mntmPrevious.setText("Previous Cycle");
			mntmAuto.setText("Auto Advance");
			mntmRestart.setText("Restart");
			mnNavigation.setText("Navigation");
			mntmCenter.setText("Center");
			mntmNEast.setText("North-East");
			mntmNWest.setText("North-West");
			mntmSEast.setText("South-East");
			mntmSWest.setText("South-West");
			// buttons
			btnImport.setText("Import");
			btnExport.setText("Export");
			btnRestart.setText("Restart");
			btnPrevious.setText("Previous");
			btnNext.setText("Next");
			btnAutoAdvance.setText("Start");
			btnRules.setText("Rules");
			btnConfiguration.setText("Configuration");
			// labels
			lblCycle.setText("  Cycle: 0");
			lblManualAdvance.setText("Manual Advance");
			lblAutoAdvance.setText("Auto Advance");
			updateScale(contr);
		}
		// independente do idioma
		mntmNew20.setText("20x20");
		mntmNew40.setText("40x40");
		mntmNew80.setText("80x80");
		mntmNew160.setText("160x160");
		mntmNew320.setText("320x320");
	}

	public AppRun(AppController controller) {
		// if the screen is moved, redraws the grid
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

		// create the menu bar
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
		menuBar.add(mnControls);
		mnControls.add(mntmNext);
		mnControls.add(mntmPrevious);
		mnControls.add(mntmAuto);
		mnControls.add(mntmRestart);
		menuBar.add(mnNavigation);
		mnNavigation.add(mntmCenter);
		mnNavigation.add(mntmNEast);
		mnNavigation.add(mntmNWest);
		mnNavigation.add(mntmSEast);
		mnNavigation.add(mntmSWest);
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
		mnControls.setFont(controller.getBoldFont());
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
		mntmNext.setFont(controller.getBoldFont());
		mntmPrevious.setFont(controller.getBoldFont());
		mntmAuto.setFont(controller.getBoldFont());
		mntmRestart.setFont(controller.getBoldFont());
		mnNavigation.setFont(controller.getBoldFont());
		mntmCenter.setFont(controller.getBoldFont());
		mntmNEast.setFont(controller.getBoldFont());
		mntmNWest.setFont(controller.getBoldFont());
		mntmSEast.setFont(controller.getBoldFont());
		mntmSWest.setFont(controller.getBoldFont());
		// shortcuts
		mntmNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true));
		mntmPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true));
		mntmAuto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true));
		mntmRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, true));
		mntmCenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, true));
		mntmNEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true));
		mntmNWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true));
		mntmSEast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, true));
		mntmSWest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true));
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
		mntmNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextCycle(controller);
			}
		});
		mntmPrevious.addActionListener(new ActionListener() {
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
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if it is auto advancing, cancels it
				if (automaticAdvance) {
					setTimer(false, controller);
					automaticAdvance = false;
				}
				importData(controller);
			}
		});
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if it is auto advancing, cancels it
				if (automaticAdvance) {
					setTimer(false, controller);
					automaticAdvance = false;
				}
				// export the current configurations
				controller.exportData();
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
				// if it is auto advancing, cancels it
				if (automaticAdvance) {
					setTimer(false, controller);
					automaticAdvance = false;
				}
				controller.showAboutPopUp();
			}
		});
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if it is auto advancing, cancels it
				if (automaticAdvance) {
					setTimer(false, controller);
					automaticAdvance = false;
				}
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
				// if it is auto advancing
				if (automaticAdvance) {
					// stops the auto advancing
					setTimer(false, controller);
					automaticAdvance = false;
				}
				if (controller.loadPrefab(0)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		prefab2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if it is auto advancing
				if (automaticAdvance) {
					// stops the auto advancing
					setTimer(false, controller);
					automaticAdvance = false;
				}
				if (controller.loadPrefab(1)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		prefab3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if it is auto advancing
				if (automaticAdvance) {
					// stops the auto advancing
					setTimer(false, controller);
					automaticAdvance = false;
				}
				if (controller.loadPrefab(2)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});
		prefab4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if it is auto advancing
				if (automaticAdvance) {
					// stops the auto advancing
					setTimer(false, controller);
					automaticAdvance = false;
				}
				if (controller.loadPrefab(3)) {
					AppRun run = new AppRun(controller);
					run.setVisible(true);
					dispose();
				}
			}
		});

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(null);
		panelMenu.setBounds(9, 3, 189, 643);
		contentPane.add(panelMenu);

		JPanel panelManualAdvance = new JPanel();
		panelManualAdvance.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelManualAdvance.setBounds(4, 204, 181, 80);
		panelMenu.add(panelManualAdvance);
		panelManualAdvance.setLayout(null);

		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextCycle(controller);
			}
		});
		btnNext.setBounds(91, 46, 85, 20);
		btnNext.setPreferredSize(new Dimension(120, 20));
		btnNext.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNext.setFont(controller.getBoldFont());
		btnNext.setFocusable(false);
		btnNext.setAlignmentX(0.5f);
		panelManualAdvance.add(btnNext);

		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousCycle(controller);
			}
		});
		btnPrevious.setBounds(3, 46, 85, 20);
		btnPrevious.setPreferredSize(new Dimension(120, 20));
		btnPrevious.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrevious.setFont(controller.getBoldFont());
		btnPrevious.setFocusable(false);
		btnPrevious.setAlignmentX(0.5f);
		panelManualAdvance.add(btnPrevious);

		lblManualAdvance.setFont(controller.getBoldFont());
		lblManualAdvance.setHorizontalAlignment(SwingConstants.CENTER);
		lblManualAdvance.setBounds(32, 13, 117, 20);
		panelManualAdvance.add(lblManualAdvance);

		JPanel panelAutoAdvance = new JPanel();
		panelAutoAdvance.setLayout(null);
		panelAutoAdvance.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelAutoAdvance.setBounds(4, 291, 181, 80);
		panelMenu.add(panelAutoAdvance);

		btnAutoAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				autoAdvance(controller);
			}
		});
		btnAutoAdvance.setPreferredSize(new Dimension(120, 20));
		btnAutoAdvance.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAutoAdvance.setFont(controller.getBoldFont());
		btnAutoAdvance.setFocusable(false);
		btnAutoAdvance.setAlignmentX(0.5f);
		btnAutoAdvance.setBounds(3, 46, 85, 20);
		panelAutoAdvance.add(btnAutoAdvance);

		lblAutoAdvance.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoAdvance.setFont(controller.getBoldFont());
		lblAutoAdvance.setBounds(18, 13, 144, 20);
		panelAutoAdvance.add(lblAutoAdvance);

		comboBoxSpeed = new JComboBox<String>();
		comboBoxSpeed.setFocusable(false);
		comboBoxSpeed.setModel(new DefaultComboBoxModel<String>(
				new String[] { "     20 ms", "     50 ms", "   100 ms", "   200 ms", " 1000 ms" }));
		comboBoxSpeed.setBounds(91, 46, 85, 20);
		comboBoxSpeed.setSelectedIndex(0);
		panelAutoAdvance.add(comboBoxSpeed);

		JPanel panelStates = new JPanel();
		panelStates.setLayout(null);
		panelStates.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelStates.setBounds(4, 378, 181, 197);
		panelMenu.add(panelStates);

		JPanel panelNames = new JPanel();
		panelNames.setLayout(null);
		panelNames.setBounds(1, 3, 100, 191);
		panelStates.add(panelNames);

		JLabel nameState1 = new JLabel("");
		nameState1.setOpaque(true);
		nameState1.setFont(controller.getNormalFont());
		nameState1.setText(" " + controller.getNameState1());
		nameState1.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState1.setBackground(Color.WHITE);
		nameState1.setBounds(7, 7, 85, 16);
		panelNames.add(nameState1);

		JLabel nameState2 = new JLabel("");
		nameState2.setOpaque(true);
		nameState2.setFont(controller.getNormalFont());
		nameState2.setText(" " + controller.getNameState2());
		nameState2.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState2.setBackground(Color.WHITE);
		nameState2.setBounds(7, 30, 85, 16);
		panelNames.add(nameState2);

		JLabel nameState3 = new JLabel("");
		nameState3.setOpaque(true);
		nameState3.setFont(controller.getNormalFont());
		nameState3.setText(" " + controller.getNameState3());
		nameState3.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState3.setBackground(Color.WHITE);
		nameState3.setBounds(7, 53, 85, 16);
		panelNames.add(nameState3);

		JLabel nameState4 = new JLabel("");
		nameState4.setOpaque(true);
		nameState4.setFont(controller.getNormalFont());
		nameState4.setText(" " + controller.getNameState4());
		nameState4.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState4.setBackground(Color.WHITE);
		nameState4.setBounds(7, 76, 85, 16);
		panelNames.add(nameState4);

		JLabel nameState5 = new JLabel("");
		nameState5.setBounds(7, 99, 85, 16);
		panelNames.add(nameState5);
		nameState5.setOpaque(true);
		nameState5.setFont(controller.getNormalFont());
		nameState5.setText(" " + controller.getNameState5());
		nameState5.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState5.setBackground(Color.WHITE);

		JLabel nameState6 = new JLabel("");
		nameState6.setOpaque(true);
		nameState6.setFont(controller.getNormalFont());
		nameState6.setText(" " + controller.getNameState6());
		nameState6.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState6.setBackground(Color.WHITE);
		nameState6.setBounds(7, 122, 85, 16);
		panelNames.add(nameState6);

		JLabel nameState7 = new JLabel("");
		nameState7.setOpaque(true);
		nameState7.setFont(controller.getNormalFont());
		nameState7.setText(" " + controller.getNameState7());
		nameState7.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState7.setBackground(Color.WHITE);
		nameState7.setBounds(7, 145, 85, 16);
		panelNames.add(nameState7);

		JLabel nameState8 = new JLabel("");
		nameState8.setOpaque(true);
		nameState8.setFont(controller.getNormalFont());
		nameState8.setText(" " + controller.getNameState8());
		nameState8.setBorder(new LineBorder(new Color(0, 0, 0)));
		nameState8.setBackground(Color.WHITE);
		nameState8.setBounds(7, 168, 85, 16);
		panelNames.add(nameState8);

		JPanel panelColors = new JPanel();
		panelColors.setLayout(null);
		panelColors.setBounds(102, 3, 78, 191);
		panelStates.add(panelColors);

		colorState1 = new JLabel("102400");
		colorState1.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState1.setToolTipText("");
		colorState1.setOpaque(true);
		colorState1.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState1.setBackground(controller.getColor(1));
		colorState1.setBounds(4, 7, 70, 16);
		panelColors.add(colorState1);

		colorState2 = new JLabel("102400");
		colorState2.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState2.setOpaque(true);
		colorState2.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState2.setBackground(controller.getColor(2));
		colorState2.setBounds(4, 30, 70, 16);
		panelColors.add(colorState2);

		colorState3 = new JLabel("102400");
		colorState3.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState3.setOpaque(true);
		colorState3.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState3.setBackground(controller.getColor(3));
		colorState3.setBounds(4, 53, 70, 16);
		panelColors.add(colorState3);

		colorState4 = new JLabel("102400");
		colorState4.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState4.setOpaque(true);
		colorState4.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState4.setBackground(controller.getColor(4));
		colorState4.setBounds(4, 76, 70, 16);
		panelColors.add(colorState4);

		colorState5 = new JLabel("102400");
		colorState5.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState5.setOpaque(true);
		colorState5.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState5.setBackground(controller.getColor(5));
		colorState5.setBounds(4, 99, 70, 16);
		panelColors.add(colorState5);

		colorState6 = new JLabel("102400");
		colorState6.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState6.setOpaque(true);
		colorState6.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState6.setBackground(controller.getColor(6));
		colorState6.setBounds(4, 122, 70, 16);
		panelColors.add(colorState6);

		colorState7 = new JLabel("102400");
		colorState7.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState7.setOpaque(true);
		colorState7.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState7.setBackground(controller.getColor(7));
		colorState7.setBounds(4, 145, 70, 16);
		panelColors.add(colorState7);

		colorState8 = new JLabel("102400");
		colorState8.setHorizontalAlignment(SwingConstants.RIGHT);
		colorState8.setOpaque(true);
		colorState8.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorState8.setBackground(controller.getColor(8));
		colorState8.setBounds(4, 168, 70, 16);
		panelColors.add(colorState8);

		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if it is auto advancing, cancels it
				if (automaticAdvance) {
					setTimer(false, controller);
					automaticAdvance = false;
				}
				// if has advanced at least one cycle
				if (firstCycleRan) {
					// resets the current vector and vectorSaver
					controller.setVector(controller.getInitialCycle());
					controller.resetVectorSaver();
				}
				// resets the cycle
				controller.setCycle(0);
				// instantiates the rules screen
				AppConfigRules rules = new AppConfigRules(controller);
				// turns it visible
				rules.setVisible(true);
				// disposes the current screen
				dispose();
			}
		});
		btnRules.setPreferredSize(new Dimension(90, 20));
		btnRules.setFont(controller.getBoldFont());
		btnRules.setFocusable(false);
		btnRules.setBounds(14, 609, 160, 20);
		panelMenu.add(btnRules);

		JPanel panelFile = new JPanel();
		panelFile.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFile.setBounds(4, 7, 181, 70);
		panelMenu.add(panelFile);
		btnImport.setBounds(30, 10, 120, 20);
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importData(controller);
			}
		});
		panelFile.setLayout(null);

		btnImport.setPreferredSize(new Dimension(120, 20));
		btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImport.setFont(controller.getBoldFont());
		btnImport.setFocusable(false);
		btnImport.setAlignmentX(0.5f);
		panelFile.add(btnImport);
		btnExport.setBounds(30, 40, 120, 20);

		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if it is auto advancing
				if (automaticAdvance) {
					// stops the auto advancing
					setTimer(false, controller);
					automaticAdvance = false;
				}
				// export the current configurations
				controller.exportData();
			}
		});
		btnExport.setPreferredSize(new Dimension(120, 20));
		btnExport.setMinimumSize(new Dimension(95, 25));
		btnExport.setMaximumSize(new Dimension(120, 25));
		btnExport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExport.setFont(controller.getBoldFont());
		btnExport.setFocusable(false);
		btnExport.setAlignmentX(0.5f);
		panelFile.add(btnExport);

		infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoPanel.setBounds(4, 84, 181, 113);
		panelMenu.add(infoPanel);
		infoPanel.setLayout(null);
		btnRestart.setBounds(30, 51, 120, 20);
		infoPanel.add(btnRestart);

		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				restartAutomaton(controller);
			}
		});
		btnRestart.setPreferredSize(new Dimension(120, 20));
		btnRestart.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRestart.setFont(controller.getBoldFont());
		btnRestart.setFocusable(false);
		btnRestart.setAlignmentX(0.5f);

		lblScale.setHorizontalAlignment(SwingConstants.CENTER);
		lblScale.setFont(controller.getNormalFont());
		lblScale.setBounds(15, 83, 70, 16);
		infoPanel.add(lblScale);

		lblCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoord.setFont(controller.getNormalFont());
		lblCoord.setBounds(100, 83, 66, 16);
		infoPanel.add(lblCoord);

		lblCycle.setHorizontalAlignment(SwingConstants.CENTER);
		lblCycle.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		lblCycle.setFont(controller.getBoldFont());
		btnConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if it is auto advancing, cancels it
				if (automaticAdvance) {
					setTimer(false, controller);
					automaticAdvance = false;
				}
				// if has advanced at least one cycle
				if (firstCycleRan) {
					// resets the current vector and vectorSaver
					controller.setVector(controller.getInitialCycle());
					controller.resetVectorSaver();
				}
				// resets the cycle
				controller.setCycle(0);
				// instantiates the configuration screen
				AppConfigAutomaton config = new AppConfigAutomaton(controller);
				// turns it visible
				config.setVisible(true);
				// dispose the current screen
				dispose();
			}
		});

		btnConfiguration.setPreferredSize(new Dimension(90, 20));
		btnConfiguration.setFont(controller.getBoldFont());
		btnConfiguration.setFocusable(false);
		btnConfiguration.setBounds(14, 582, 160, 20);
		panelMenu.add(btnConfiguration);

		squares = new DrawSquare(controller.getSqrSize() * controller.getVectorSize());
		squares.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// ratio between the width of squares2 and the matrix size
				double value = (squares.getWidth() * 1.0) / controller.getVectorSize();
				// x e y positions of the clicked place
				int posX = (int) Math.ceil(e.getX() / value) - 1;
				int posY = (int) Math.ceil(e.getY() / value) - 1;
				// in the borders, is possible to detect -1
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
				// determinates the middle of the scroll
				int middle;
				// position of the mouse
				Point mouseLoc = e.getPoint();
				// position of the view
				Point viewPort = scrollPane.getViewport().getViewPosition();
				Point newPositionAfterZoom;
				if (e.getWheelRotation() >= 0) {// zoom out
					// determines the maximum zoom out
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
						// new position after zoom
						newPositionAfterZoom = new Point((viewPort.x - (middle - mouseLoc.x)) / 2,
								(viewPort.y - (middle - mouseLoc.y)) / 2);
						// changes the scale, size of the rectangles
						controller.setSqrSize(controller.getSqrSize() / 2);
						// updates the scroll bars
						squares.setPrefSize(controller.getSqrSize() * controller.getVectorSize());
						// updates the scale text
						updateScale(controller);
						// redraws the grid
						controller.drawMatriz(squares);
						repaint();
						// adjusts the visualization
						scrollPane.getViewport().setViewPosition(newPositionAfterZoom);
					}
				} else {// zoom in
					// checks the scale
					if (controller.getSqrSize() < 64) {
						middle = (int) (scrollPane.getViewport().getExtentSize().getWidth() * 0.25);
						// new position after zoom
						newPositionAfterZoom = new Point((viewPort.x - (middle - mouseLoc.x)) * 2,
								(viewPort.y - (middle - mouseLoc.y)) * 2);
						// changes the scale, size of the rectangles
						controller.setSqrSize(controller.getSqrSize() * 2);
						// updates the scroll bars
						squares.setPrefSize(controller.getSqrSize() * controller.getVectorSize());
						// updates the scale text
						updateScale(controller);
						// redraws the grid
						controller.drawMatriz(squares);
						repaint();
						// adjusts the visualization
						scrollPane.getViewport().setViewPosition(newPositionAfterZoom);
					}
				}
			}
		});
		scrollPane.setBounds(207, 3, 644, 644);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
		scrollPane.getVerticalScrollBar().setUnitIncrement(50);
		contentPane.add(scrollPane);
		// remove the scroll by the mouse wheel so it will not affect the zoom
		// function
		scrollPane.removeMouseWheelListener(scrollPane.getMouseWheelListeners()[0]);

		// sets the language
		setLanguage(controller.getLanguage(), controller);
		// adjusts the scale, if needed
		fixScale(controller);
		// updates the population counters
		statesAmount = controller.countStates();
		updateTexts(controller);
		// determines the population counters font color
		updatePopBackground(controller);
		comboBoxSpeed.setSelectedIndex(0);
	}
}
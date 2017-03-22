package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Manual extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int screen = 0;
	private String[] topics = new String[4];
	private String[] texts = new String[4];
	private JButton btnAnterior = new JButton();
	private JButton btnPrximo = new JButton();
	private JLabel lblTpico;
	private JEditorPane editorPane;

	public Manual(int language) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 685, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppConfigAutomaton.class.getResource("/img/main16x16.png")));

		if (language == 0) {
			// t�tulo
			setTitle("Autcel: Manual de Uso");
			// bot�es
			btnAnterior.setText("Anterior");
			btnPrximo.setText("Pr�ximo");
			// t�picos
			topics[0] = "Autcel";
			topics[1] = "Tela Configura��o";
			topics[2] = "Tela Regras";
			topics[3] = "Tela Execu��o";
			// texto
			texts[0] = "<h2>O que � o Autcel?</h2>"
					+ "Autcel permite criar, editar e executar aut�matos celulares. Possui 3 telas: Configura��o, Regras e Execu��o.<br><br>"
					+ "<h2>Licen�a</h2>Este projeto usa a licen�a AGPL v3.0. Mais detalhes em: <a href=\"https://www.gnu.org/licenses/\">https://www.gnu.org/licenses/</a>.<br><br>"
					+ "<h2>GitHub</h2>Voc� pode encontrar o c�digo fonte deste projeto no GitHub em: <a href=\"https://github.com/MadsonPaulo/autcel\">https://github.com/MadsonPaulo/autcel</a>.<br>"
					+ "Sinta-se livre para contribuir com este projeto.<br><br>"
					+ "<h2>Bugs e Sugest�es</h2>Caso encontre algum bug, tenha alguma sugest�o ou crie um modelo de aut�mato interessante e queira o tornar padr�o, assim como os outros quatro j� presentes, envie um email para o criador do Autcel, Madson Paulo, pelo endere�o: <a href=\"madson-paulo@hotmail.com\">madson-paulo@hotmail.com</a>.<br><br>";

			texts[1] = "<h2>Configura��o</h2>A tela inicial, Configura��o, permite definir o estado inicial das c�lulas, a quantidade de estados poss�veis, o nome dos estados e a cor que os representa. � poss�vel tamb�m importar um modelo anteriormente criado atrav�s do Autcel ou utilizar um dos modelos prontos. Note que ao importar ou utilizar um modelo pronto, a tela Configura��o ser� fechada, a tela de Regras ser� pulada e ser� aberta a tela de Execu��o.<br><br>"
					+ "<h2>Alterando o estado inicial de uma c�lula</h2>Existem tr�s maneiras de alterar o estado inicial de uma c�lula:"
					+ "<ul><li>Um click simples numa c�lula vai alterar o seu estado para o pr�ximo poss�vel.</li>"
					+ "<li>Arrastar o mouse segurando o bot�o esquerdo do mouse ir� alterar as c�lulas por onde a seta passar para o estado do �ltimo click simples.</li>"
					+ "<li>Arrastar o mouse segurando o bot�o direito do mouse ir� alterar as c�lulas por onde a seta passar para o primeiro estado.</li></ul>"
					+ "Al�m disso, no canto superior esquerdo est� o bot�o \"Pop. Aleat�ria\" e � sua direita uma caixa de sele��o. Ao clicar nesse bot�o, ser� gerada uma popula��o aleat�ria utilizando os estados poss�veis. Quanto menor o n�mero na caixa de sele��o, maior ser� a densidade populacional.<br>"
					+ "Logo abaixo est� o bot�o \"Limpar\", que altera todas as c�lulas para o primeiro estado.<br><br>"
					+ "<h2>Nome e Cor dos estados poss�veis</h2>Na parte central do menu � esquerda � poss�vel definir quantos estados ser�o poss�veis atrav�s da caixa de sele��o. Ser� permitido alterar o nome dos estados que forem poss�veis, eles n�o podem no entanto, serem vazios.<p>� direita de cada nome est� uma caixa com a cor que o representa e dentro dessa caixa, um n�mero que representa a popula��o desse estado. Basta clicar na caixa de cor e uma caixa de sele��o ser� apresentada, onde ser� poss�vel trocar a cor do estado clicado.</p><br><br>"
					+ "<h2>Escala</h2>Para melhor visualiza��o, � poss�vel alterar a escala utilizando a roda do mouse. Empurrando-a para frente ir� aproximar as c�lulas enquanto que para tr�s ir� afast�-las.<br><br>"
					+ "<p>Ao finalizar as configura��es, clique em \"Avan�ar\" para prosseguir para a tela de Regras.<br><br>";

			texts[2] = "<h2>Regras</h2>A tela Regras permite definir as regras v�lidas no aut�mato. No canto superior direito h� uma caixa de sele��o que permite configurar o aut�mato para as regras do \"Jogo da Vida\", dos \"Cristais de Ulam\" ou da \"Regra 614\". Ao selecionar alguma, qualquer regra existente ser� apagada e as regras ser�o configuradas para as do aut�mato selecionado.<br><br>"
					+ "<h2>Criando uma regra</h2>Para criar uma regra, clique numa das caixas de sele��o ou no bot�o \"Nova Regra\". O campo de texto logo � direita ir� mostrar a regra em linguagem natural. Ao alterar alguma caixa de sele��o, a regra em linguagem natural ser� atualizada. Quando a regra estiver pronta, clique em \"Criar Regra\". No canto superior esquerdo existem duas caixas de sele��o: \"Tipo de vizinhan�a\" e \"O Centro � Incluso?\"."
					+ "<p>Basta selecionar alguma das op��es e o tipo de vizinhan�a ser� alterado para a op��o escolhida. Adicionalmente, a imagem � esquerda ser� alterada exibindo em cinza escuro a c�lula atual e em cinza claro as posi��es que ser�o consideradas no tipo de vizinhan�a escolhido. Caso \"O Centro � Incluso\" esteja selecionado como sim, a c�lula atual tamb�m ser� contabilizada na contagem de vizinhos do estado atual da c�lula.</p><br><br>"
					+ "<h2>Preced�ncia das Regras</h2>As regras seguem ordem de preced�ncia de cima para baixo, logo, uma vez que uma regra seja validada para uma c�lula em um ciclo, as demais regras ser�o ignoradas para esta c�lula neste ciclo. � poss�vel alterar a preced�ncia de uma regra selecionando-a e clicando nos bot�es \"Acima\" e \"Abaixo\".<p>Para excluir uma regra, selecione-a e clique em \"Excluir\". � necess�rio definir pelo menos uma regra para avan�ar.</p><br><br>"
					+ "Para prosseguir, clique no bot�o \"Execu��o\" ou clique em \"Configura��o\" para voltar para a tela anterior.<br><br>";

			texts[3] = "<h2>Execu��o</h2>A tela de Execu��o permite executar o aut�mato e export�-lo. No canto superior esquerdo encontra-se o painel de arquivo que permite importar as configura��es e regras de um aut�mato ou exportar o aut�mato atual."
					+ "<p>Logo abaixo est� o painel de informa��es, onde � poss�vel ver o ciclo atual, a escala, a coordenada da c�lula onde o mouse se encontra e reiniciar para o ciclo 0 clicando no bot�o \"Reiniciar\".<br><br>"
					+ "<h2>Avan�ando Ciclos</h2>O painel de avan�o manual fica abaixo do painel de informa��es. Clicando em \"Pr�ximo\" avan�a para o pr�ximo ciclo e em \"Anterior\", retorna um ciclo. Logo abaixo est� o painel de avan�o autom�tico. Basta selecionar o intervalo em milissegundos para o avan�o de cada turno e clicar em \"Iniciar\" para iniciar o avan�o autom�tico. � poss�vel par�-lo clicando no bot�o \"Parar\" que aparece somente quando o avan�o autom�tico est� ativo.<br><br>"
					+ "<h2>Popula��o</h2>No canto inferior esquerdo est� o painel de estados. � esquerda s�o exibidos o nome dos oito estados e � direita, a cor e a popula��o de cada estado no ciclo atual.<br><br>"
					+ "<h2>Atalhos</h2>No menu controles s�o exibidos o quatro atalhos relacionados aos ciclos:<ul><li>Seta direita do teclado: avan�a um ciclo;</li><li>Seta esquerda do teclado: retorna um ciclo;</li><li>Tecla Enter: inicia/para o avan�o autom�tico;</li><li>Tecla Backspace: reinicia para o ciclo 0.</li></ul><br>"
					+ "<h2>Navega��o</h2>No menu navega��o s�o exibidos os atalhos relacionados � visualiza��o do grid:<ul><li>F1: visualiza a regi�o nordeste do grid;</li><li>F2: visualiza a regi�o noroeste do grid;</li><li>F3: visualiza a regi�o central do grid;</li><li>F4: visualiza a regi�o sudeste do grid;</li><li>F5: visualiza a regi�o sudoeste do grid.</li></ul><br><br>"
					+ "Clique em \"Regras\" para voltar para a tela Regras ou em \"Configura��o\" para retornar � tela inicial.";

		} else if (language == 1) {
			// t�tulo
			setTitle("Autcel: User Manual");
			// bot�es
			btnAnterior.setText("Previous");
			btnPrximo.setText("Next");
			// t�picos
			topics[0] = "Autcel";
			topics[1] = "Configurations Screen";
			topics[2] = "Rules Screen";
			topics[3] = "Execution Screen";
			// texto
			texts[0] = "<h2>What is Autcel?</h2>Autcel allows you to create, edit and run cellular automatons. It has 3 screens: Configurations, Rules and Execution.<br><br>"
					+ "<h2>License</h2>This project uses the AGPL v3.0 license. More details at: <a href=\"https://www.gnu.org/licenses/\">https://www.gnu.org/licenses/</a>.<br><br>"
					+ "<h2>GitHub</h2>You can find the source code of this project in GitHub at: <a href=\"https://github.com/MadsonPaulo/autcel\">https://github.com/MadsonPaulo/autcel</a>.<br>"
					+ "Feel free to contribute with this project.<br><br>"
					+ "<h2>Bugs and Suggestions</h2>If you find a bug, have some suggestions or create an interesting automaton model and want to make it standard, just like the other four already present, send an email to the creator of Autcel, Madson Paulo, by the address: <a href=\"madson-paulo@hotmail.com\">madson-paulo@hotmail.com</a>.<br><br>";

			texts[1] = "<h2>Configuration</h2>The initial screen, Configuration, allows you to define the initial state of the cells, the number of possible states, the name of the states and the color that represents them. You can also import a previously created template using Autcel or use one of the ready templates. Note that when you import or use a ready template, the Configuration screen closes, the Rules screen is skipped and the Execute screen opens.<br><br>"
					+ "<h2>Changing the initial state of a cell</h2>There are three ways to change the initial state of a cell:"
					+ "<ul><li>A simple click on a cell will change it's state to the nearest possible one.</li>"
					+ "<li>Dragging the mouse holding the left mouse button will change the cells where the arrow moves to the state of the last simple click.</li>"
					+ "<li>Dragging the mouse holding the right mouse button will change the cells where the arrow moves to the first state.</li></ul>"
					+ "Also, in the upper left corner is the \"Pop. Random\" button and on it's right a selection box. Clicking this button will generate a random population using the possible states. The smaller the number in the selection box, the greater the population density.<br>"
					+ "<p>Just below is the \"Clean\" button, which changes all cells to the first state.</p><br><br>"
					+ "<h2>Name and Color of possible states</h2>In the middle part of the left menu you can define how many states are possible through the selection box. It will be possible to change the name of the states that are possible, but they can not be empty.<p>To the right of each name is a box with the color that represents it and within that box, a number that represents the population of that state. Just click on the color box and a selection box will be displayed, where you can change the color of the clicked state.</p><br><br>"
					+ "<h2>Scale</h2>For better visualization, you can change the scale using the mouse wheel. Pushing it forward will bring the cells closer, whereas the back will pull them apart.<br><br>"
					+ "<p>At the end of the settings, click \"Next\" to proceed to the Rules screen.<br><br>";

			texts[2] = "<h2>Rules</h2>The Rules screen allows you to define valid rules in the automaton. In the upper right corner there is a selection box that allows you to configure the automaton for the rules of \"Game of Life\", the \"Ulam Crystals\" or the \"Rule 614\". When selecting any, any existing rule will be deleted and the rules will be set to those of the selected automaton.<br><br>"
					+ "<h2>Creating a Rule</h2>To create a rule, click in any selection box or in the \"New Rule\" button. The text field on the right will show the rule in natural language. When you change any selection box, the natural language rule will be updated. When the rule is ready, click \"Create Rule\" button. In the upper left corner there are two selection boxes: \"Neighborhood Type\" and \"Is the Center Even?\"."
					+ "<p>Just select any of the options and the type of neighborhood will change to the chosen option. In addition, the image to the left will be changed by displaying the current cell in dark gray and in light gray the positions that will be considered in the type of neighborhood chosen. If \"Is the Center Even\" is selected as yes, the current cell will also be counted in the neighbor count of the current state of the cell.</p><br><br>"
					+ "<h2>Precedence of Rules</h2>The rules follow precedence order from top to bottom, so once a rule is validated for a cell in a cycle, the other rules will be ignored for this cell in this cycle. You can change the precedence of a rule by selecting it and clicking the \"Up\" and \"Down\" buttons.<p>To delete a rule, select it and click \"Delete\". You must define at least one rule to proceed.</p><br><br>"
					+ "To proceed, click the \"Execution\" button or click \"Configuration\" to return to the previous screen.<br><br>";

			texts[3] = "<h2>Execution</h2>The Execution screen allows you to run the automaton and export it. In the upper left corner is the file panel that allows you to import the configurations and rules of an automaton or export the current automaton."
					+ "<p>Just below is the information panel, where you can see the current cycle, the scale, the coordinate of the cell where the mouse is and restart to the cycle 0 by clicking in the \"Restart\" button.<br><br>"
					+ "<h2>Advancing Cycles</h2>The manual advance panel is below the information panel. By clicking \"Next\" it advances to the next cycle and in \"Previous\", returns a cycle. Just below is the automatic advance panel. Just select the interval in milliseconds for the advance of each turn and click \"Start\" to start the automatic advance. You can stop it by clicking on the \"Stop\" button that appears only when auto-advance is active.<br><br>"
					+ "<h2>Population</h2>In the bottom left corner is the status panel. On the left are the name of the eight states and in the right, the color and population of each state in the current cycle.<br><br>"
					+ "<h2>Shortcuts</h2>In the controls menu the four shortcuts related to the cycles are displayed:<ul><li>Right keyboard key: Advances one cycle;</li><li>Left keyboard key: Returns one cycle;</li>Enter key: starts/stops automatic cycle advance;</li><li>Backspace key: resets to cycle 0.</li></ul><br>"
					+ "<h2>Navigation</h2>In the navigation menu, the shortcuts related to the grid view are displayed:<ul><li>F1: displays the northeast region of the grid;</li><li>F2: displays the northwest region of the grid;</li><li>F3: displays the central region of the grid;</li><li>F4: displays the southeast region of the grid;</li><li>F5: displays the southwest region of the grid.</li></ul><br><br>"
					+ "Click \"Rules\" to return to the Rules screen or in \"Configuration\" to return to the first screen.";
		}

		editorPane = new JEditorPane("text/html", "");
		editorPane.setEditable(false);
		editorPane.setText(texts[0]);
		editorPane.setCaretPosition(0);
		editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		editorPane.setFont(new Font("Calibri", Font.BOLD, 14));

		JScrollPane jScrollPane = new JScrollPane(editorPane);
		jScrollPane.setBounds(9, 55, 660, 309);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(jScrollPane);

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuPanel.setBounds(9, 6, 660, 43);
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);

		btnAnterior.setEnabled(false);
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controla a passagem de telas
				if (screen == 1) {
					btnAnterior.setEnabled(false);
					screen--;
					lblTpico.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 2) {
					screen--;
					lblTpico.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 3) {
					btnPrximo.setEnabled(true);
					screen--;
					lblTpico.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				}
			}
		});
		btnAnterior.setFocusable(false);
		btnAnterior.setBounds(65, 11, 100, 20);
		btnAnterior.setFont(new Font("Calibri", Font.BOLD, 14));
		menuPanel.add(btnAnterior);

		btnPrximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controla a passagem de telas
				if (screen == 0) {
					btnAnterior.setEnabled(true);
					screen++;
					lblTpico.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 1) {
					screen++;
					lblTpico.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 2) {
					btnPrximo.setEnabled(false);
					screen++;
					lblTpico.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				}
			}
		});
		btnPrximo.setFocusable(false);
		btnPrximo.setBounds(495, 11, 100, 20);
		btnPrximo.setFont(new Font("Calibri", Font.BOLD, 14));
		menuPanel.add(btnPrximo);

		lblTpico = new JLabel();
		lblTpico.setHorizontalAlignment(SwingConstants.CENTER);
		lblTpico.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTpico.setBounds(230, 10, 200, 23);
		lblTpico.setText(topics[0]);
		menuPanel.add(lblTpico);
	}
}

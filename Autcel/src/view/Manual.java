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
			texts[0] = "<h2>O que � o Autcel?</h2>Autcel permite criar, editar e executar aut�matos celulares. Possui 3 telas: Configura��o, Regras e Execu��o.<br><br><h2>Licen�a</h2>Este projeto usa a licen�a AGPL v3.0. Mais detalhes em: <a href=\"https://www.gnu.org/licenses/\">https://www.gnu.org/licenses/</a><br><br><h2>GitHub</h2>Voc� pode encontrar o c�digo fonte deste projeto no GitHub em: <a href=\"https://github.com/MadsonPaulo/autcel\">https://github.com/MadsonPaulo/autcel</a>.<br>Sinta-se livre para contribuir com este projeto. ";
			texts[1] = "<h2>Configura��o</h2>A tela inicial, Configura��o, permite definir o estado inicial das c�lulas, a quantidade de estados poss�veis e o nome dos estados. � poss�vel tamb�m importar um modelo anteriormente criado atrav�s do Autcel ou utilizar um dos modelos prontos. Note que ao importar ou utilizar um modelo pronto, a tela Configura��o ser� fechada, a tela de Regras ser� pulada e ser� aberta a tela de Execu��o.<br><br><h2>Alterando o estado inicial de uma c�lula</h2>Para alterar o estado inicial de uma c�lula, basta clicar na c�lula com o bot�o esquerdo do mouse e ela ir� mudar sua cor para a correspondente ao pr�ximo estado poss�vel. Ao finalizar, clique em no bot�o \"Avan�ar\".";
			texts[2] = "<h2>Regras</h2>A tela Regras permite definir as regras v�lidas no aut�mato. No canto superior existem dois bot�es, \"Jogo da Vida\" e \"Cristais de Ulam\". Ao clicar em um desses, qualquer regra existente ser� apagada e as regras ser�o configuradas para as do aut�mato do bot�o clicado.<br><br><h2>Criando uma regra</h2>Para criar uma regra, clique numa das caixas de sele��o ou no bot�o \"Nova Regra\". O campo de texto logo � direita ir� mostrar a regra em linguagem natural. Ao alterar alguma caixa de sele��o, a regra em linguagem natural ser� atualizada. Quando a regra estiver pronta, clique em \"Criar Regra\".<br><br><h2>Preced�ncia das Regras</h2>As regras seguem ordem de preced�ncia de cima para baixo, logo, uma vez que uma regra seja validada para uma c�lula em um ciclo, as demais regras ser�o ignoradas para esta c�lula neste ciclo. � poss�vel alterar a preced�ncia de uma regra selecionando-a e clicando nos bot�es \"Acima\" e \"Abaixo\".<p>Para excluir uma regra, selecione-a e clique em \"Excluir\". � necess�rio definir pelo menos uma regra para avan�ar.</p>";
			texts[3] = "<h2>Execu��o</h2>A tela de Execu��o permite executar o aut�mato e export�-lo. No canto superior esquerdo � poss�vel ver o ciclo atual. Logo abaixo est� o painel de escala, onde est�o as op��es de aumentar ou diminuir a escala para melhor visualiza��o.<br><br><h2>Avan�ando Ciclos</h2>O painel de avan�o manual fica abaixo do painel de escala. Clicando em \"Pr�ximo\" avan�a para o pr�ximo ciclo e em \"Anterior\", retorna um ciclo. Logo abaixo est� o painel de avan�o autom�tico. Basta selecionar o intervalo em milissegundos para o avan�o de cada turno e clicar em \"Iniciar\" para iniciar o avan�o autom�tico. � poss�vel par�-lo clicando no bot�o \"Parar\", que aparece somente quando o avan�o autom�tico est� ativo.<br><br><h2>Popula��o</h2>No canto inferior esquerdo est� o painel de estados. � esquerda s�o exibidos o nome dos oito estados e � direita, a cor e a popula��o de cada estado no ciclo atual.<p>Para voltar para a tela Regras basta clicar em \"Voltar\".</p>";
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
			texts[0] = "<h2>What is Autcel?</h2>Autcel allows you to create, edit and run cellular automatons. It has 3 screens: Configurations, Rules and Execution.<br><br><h2>License</h2>This project uses the AGPL v3.0 license. More details at: <a href=\"https://www.gnu.org/licenses/\">https://www.gnu.org/licenses/</a><br><br><h2>GitHub</h2>You can find the source code of this project in GitHub at: <a href=\"https://github.com/MadsonPaulo/autcel\">https://github.com/MadsonPaulo/autcel</a>.<br>Feel free to contribute with this project. ";
			texts[1] = "<h2>Configurations</h2>The initial screen, Configurations, allows you to define the initial state of the cells, the number of possible states and the name of the states. You can also import a previously created template using Autcel or use one of the ready templates. Note that when you import or use a ready template, the Setup screen closes, the Rules screen is skipped and the Execution screen opens.<br><br><h2>Changing the initial state of a cell</h2>To change the initial state of a cell, just click it with the left mouse button and it will change it's color to the one of the next possible state. When finished, click on the \"Next\" button.";
			texts[2] = "<h2>Rules</h2>The Rules screen allows you to define valid rules in the automaton. In the upper corner there are two buttons, \"Game of Life\" and \"Ulam Crystals\". When you click on one of these, any existing rules will be deleted and the rules will be set to those of the automaton's button clicked.<br><br><h2>Creating a rule</h2>To create a rule, click in one of the selection boxes or in the \"New Rule\" button. The text field on the right will show the rule in natural language. When you change any selection boxe, the natural language rule will be updated. When the rule is ready, click in \"Create Rule\".<br><br><h2>Precedence of Rules</h2>The rules follow precedence order from top to bottom so once a rule is validated for a cell in a cycle, the other rules will be ignored for this cell in this cycle. You can change the precedence of a rule by selecting it and clicking in the \"Up\" and \"Down\" buttons.<p>To delete a rule, select it and click \"Delete \". It is necessary to define at least one rule to move forward.</p>";
			texts[3] = "<h2>Execution</h2>The Execution screen allows you to run the automaton and export it. In the top left corner you can see the current cycle. Just below is the scale panel, where you have the options to increase or decrease the scale for better visualization.<br><br><h2>Advancing Cycles</h2>The manual advance panel is below the scale panel. By clicking \"Next\" it advances to the next cycle and in \"Previous\", returns one cycle. Just below is the automatic advance panel. Just select the interval in milliseconds for the advance of each turn and click \"Start\" to start the automatic advance. You can stop it by clicking on the \"Stop\" button, which appears only when the automatic advance is active.<br><br><h2>Population</h2>In the bottom left corner is the status panel. On the left is the name of the eight states and in the right, the color and population of each state in the current cycle are displayed.<p>To return to the Rules screen, simply click \"Back\".</p>";
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

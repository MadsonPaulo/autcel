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

/**
 * Frame that shows the user manual
 * 
 * @author Madson
 *
 */
public class Manual extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int screen = 0;
	private String[] topics = new String[4];
	private String[] texts = new String[4];
	private JButton btnPrevious = new JButton();
	private JButton btnNext = new JButton();
	private JLabel lblTopic;
	private JEditorPane editorPane;
	private String imgUrl = this.getClass().getResource("/img/borda_periodica.PNG").toString();

	public Manual(int language) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 685, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppConfigAutomaton.class.getResource("/img/main16x16.png")));

		// TODO Implement Internacionalization
		if (language == 0) {
			// title
			setTitle("ESACEL: Manual de Uso");
			// buttons
			btnPrevious.setText("Anterior");
			btnNext.setText("Próximo");
			// topics
			topics[0] = "ESACEL";
			topics[1] = "Tela Configuração";
			topics[2] = "Tela Regras";
			topics[3] = "Tela Execução";
			// text
			texts[0] = "<h2>O que é o ESACEL?</h2>"
					+ "ESACEL permite criar, editar e executar autômatos celulares. Possui 3 telas: Configuração, Regras e Execução.<br><br>"
					+ "<h2>Licença</h2>Este projeto usa a licença AGPL v3.0. Mais detalhes em: <a href=\"https://www.gnu.org/licenses/\">https://www.gnu.org/licenses/</a>.<br><br>"
					+ "<h2>GitHub</h2>Você pode encontrar o código fonte deste projeto no GitHub em: <a href=\"https://github.com/MadsonPaulo/ESACEL\">https://github.com/MadsonPaulo/ESACEL</a>.<br>"
					+ "Sinta-se livre para contribuir com este projeto.<br><br>"
					+ "<h2>Bugs e Sugestões</h2>Caso encontre algum bug, tenha alguma sugestão ou crie um modelo de autômato interessante e queira o tornar padrão, assim como os outros quatro já presentes, envie um email para o criador do ESACEL, Madson Paulo, pelo endereço: <a href=\"madson-paulo@hotmail.com\">madson-paulo@hotmail.com</a>.<br><br>";

			texts[1] = "<h2>Configuração</h2>A tela inicial, Configuração, permite definir o estado inicial das células, a quantidade de estados possíveis, o tamanho da grade, o nome dos estados e a cor que os representa. É possível também importar um modelo anteriormente criado através do ESACEL ou utilizar um dos modelos prontos. Note que ao importar ou utilizar um modelo pronto, a tela Configuração será fechada, a tela de Regras será pulada e será aberta a tela de Execução.<br><br>"
					+ "<h2>Alterando o estado inicial de uma célula</h2>Existem três maneiras de alterar o estado inicial de uma célula:"
					+ "<ul><li>Um click simples numa célula vai alterar o seu estado para o próximo possível.</li>"
					+ "<li>Arrastar o mouse segurando o botão esquerdo do mouse irá alterar as células por onde a seta passar para o estado do último click simples.</li>"
					+ "<li>Arrastar o mouse segurando o botão direito do mouse irá alterar as células por onde a seta passar para o primeiro estado.</li></ul>"
					+ "Além disso, no canto superior esquerdo está o botão \"Pop. Aleatória\" e à sua direita uma caixa de seleção. Ao clicar nesse botão, será gerada uma população aleatória utilizando os estados possíveis. Quanto menor o número na caixa de seleção, maior será a densidade populacional.<br>"
					+ "Logo abaixo está o botão \"Limpar\", que altera todas as células para o primeiro estado.<br><br>"
					+ "<h2>Alterando o tamanho da grade</h2>Na barra de menu, em preferências, na opção \"Tamanho da Grade\" é possível escolher entre 20x20, 40x40, 80x80, 160x160 e 320x320. Será mantido o estado das células que não forem afetadas pela mudança de tamanho da grade.<br><br>"
					+ "<h2>Nome e Cor dos estados possíveis</h2>Na parte central do menu à esquerda é possível definir quantos estados serão possíveis através da caixa de seleção. Será permitido alterar o nome dos estados que forem possíveis, eles não podem no entanto, serem vazios.<p>À direita de cada nome está uma caixa com a cor que o representa e dentro dessa caixa, um número que representa a população desse estado. Basta clicar na caixa de cor e uma caixa de seleção será apresentada, onde será possível trocar a cor do estado clicado.</p><br><br>"
					+ "<h2>Escala</h2>Para melhor visualização, é possível alterar a escala utilizando a roda do mouse. Empurrando-a para frente irá aproximar as células enquanto que para trás irá afastá-las.<br><br>"
					+ "<p>Ao finalizar as configurações, clique em \"Avançar\" para prosseguir para a tela de Regras.<br><br>";

			texts[2] = "<h2>Regras</h2>A tela Regras permite definir as regras válidas no autômato. No canto superior direito há uma caixa de seleção que permite configurar o autômato para as regras do \"Jogo da Vida\", dos \"Cristais de Ulam\" ou da \"Regra 614\". Ao selecionar alguma, qualquer regra existente será apagada e as regras serão configuradas para as do autômato selecionado.<br><br>"
					+ "<h2>Criando uma regra</h2>Para criar uma regra, clique numa das caixas de seleção ou no botão \"Nova Regra\". O campo de texto logo à direita irá mostrar a regra em linguagem natural. Ao alterar alguma caixa de seleção, a regra em linguagem natural será atualizada. Quando a regra estiver pronta, clique em \"Criar Regra\". No canto superior esquerdo existem duas caixas de seleção: \"Tipo de vizinhança\" e \"O Centro é Incluso?\"."
					+ "<p>Basta selecionar alguma das opções e o tipo de vizinhança será alterado para a opção escolhida. Adicionalmente, a imagem à esquerda será alterada exibindo em cinza escuro a célula atual e em cinza claro as posições que serão consideradas no tipo de vizinhança escolhido. Caso \"O Centro é Incluso\" esteja selecionado como sim, a célula atual também será contabilizada na contagem de vizinhos do estado atual da célula.</p><br><br>"
					+ "<h2>Precedência das Regras</h2>As regras seguem ordem de precedência de cima para baixo, logo, uma vez que uma regra seja validada para uma célula em um ciclo, as demais regras serão ignoradas para esta célula neste ciclo. É possível alterar a precedência de uma regra selecionando-a e clicando nos botões \"Acima\" e \"Abaixo\".<p>Para excluir uma regra, selecione-a e clique em \"Excluir\". É necessário definir pelo menos uma regra para avançar.</p><br><br>"
					+ "<h2>Autores das Regras dos Modelos Prontos</h2>Tenha em mente que são listados somente os autores das regras, a configuração inicial foi criada pelo autor desta ferramenta.<br>"
					+ "<ul><li>Conway's Game of Life: criado por John Horton Conway em 1970.</li>"
					+ "<li>Ulam's Crystals: criado por Stanislaw Marcin Ulam em 1940.</li>"
					+ "<li>Rule 614: criado por Miklos Kristof em 2002.</li>"
					+ "<li>Tsunami: criado por Madson P. A. da Silva em 2017.</li></ul><br><br>"
					+ "Para prosseguir, clique no botão \"Execução\" ou clique em \"Configuração\" para voltar para a tela anterior.<br><br>";

			texts[3] = "<h2>Execução</h2>A tela de Execução permite executar o autômato e exportá-lo. No canto superior esquerdo encontra-se o painel de arquivo que permite importar as configurações e regras de um autômato ou exportar o autômato atual."
					+ "<p>Logo abaixo está o painel de informações, onde é possível ver o ciclo atual, a escala, a coordenada da célula onde o mouse se encontra e reiniciar para o ciclo 0 clicando no botão \"Reiniciar\".<br><br>"
					+ "<h2>Avançando Ciclos</h2>O painel de avanço manual fica abaixo do painel de informações. Clicando em \"Próximo\" avança para o próximo ciclo e em \"Anterior\", retorna um ciclo. Logo abaixo está o painel de avanço automático. Basta selecionar o intervalo em milissegundos para o avanço de cada turno e clicar em \"Iniciar\" para iniciar o avanço automático. É possível pará-lo clicando no botão \"Parar\" que aparece somente quando o avanço automático está ativo.<br><br>"
					+ "<h2>População</h2>No canto inferior esquerdo está o painel de estados. À esquerda são exibidos o nome dos oito estados e à direita, a cor e a população de cada estado no ciclo atual.<br><br>"
					+ "<h2>Tratamento da borda</h2>As células que estão nas extremidades não possuem vizinhança completa. Para tratar esse problema, o ESACEL utiliza o tipo de borda periódica. Na borda periódica, as extremidades se conectam para suprir as células que estão faltando na borda, assim como na imagem abaixo:<br><img src='"
					+ imgUrl + "'</img>"
					+ "<h2>Atalhos</h2>No menu controles são exibidos o quatro atalhos relacionados aos ciclos:<ul><li>Seta direita do teclado: avança um ciclo;</li><li>Seta esquerda do teclado: retorna um ciclo;</li><li>Tecla Enter: inicia/para o avanço automático;</li><li>Tecla Backspace: reinicia para o ciclo 0.</li></ul><br>"
					+ "<h2>Navegação</h2>No menu navegação são exibidos os atalhos relacionados à visualização do grid:<ul><li>F1: visualiza a região nordeste do grid;</li><li>F2: visualiza a região noroeste do grid;</li><li>F3: visualiza a região central do grid;</li><li>F4: visualiza a região sudeste do grid;</li><li>F5: visualiza a região sudoeste do grid.</li></ul><br><br>"
					+ "Clique em \"Regras\" para voltar para a tela Regras ou em \"Configuração\" para retornar à tela inicial.";

		} else if (language == 1) {
			// title
			setTitle("ESACEL: User Manual");
			// buttons
			btnPrevious.setText("Previous");
			btnNext.setText("Next");
			// topics
			topics[0] = "ESACEL";
			topics[1] = "Configurations Screen";
			topics[2] = "Rules Screen";
			topics[3] = "Execution Screen";
			// text
			texts[0] = "<h2>What is ESACEL?</h2>ESACEL allows you to create, edit and run cellular automatons. It has 3 screens: Configurations, Rules and Execution.<br><br>"
					+ "<h2>License</h2>This project uses the AGPL v3.0 license. More details at: <a href=\"https://www.gnu.org/licenses/\">https://www.gnu.org/licenses/</a>.<br><br>"
					+ "<h2>GitHub</h2>You can find the source code of this project in GitHub at: <a href=\"https://github.com/MadsonPaulo/ESACEL\">https://github.com/MadsonPaulo/ESACEL</a>.<br>"
					+ "Feel free to contribute with this project.<br><br>"
					+ "<h2>Bugs and Suggestions</h2>If you find a bug, have some suggestions or create an interesting automaton model and want to make it standard, just like the other four already present, send an email to the creator of ESACEL, Madson Paulo, by the address: <a href=\"madson-paulo@hotmail.com\">madson-paulo@hotmail.com</a>.<br><br>";

			texts[1] = "<h2>Configuration</h2>The initial screen, Configuration, allows you to define the initial state of the cells, the number of possible states, the grid size, the name of the states and the color that represents them. You can also import a previously created template using ESACEL or use one of the ready templates. Note that when you import or use a ready template, the Configuration screen closes, the Rules screen is skipped and the Execute screen opens.<br><br>"
					+ "<h2>Changing the initial state of a cell</h2>There are three ways to change the initial state of a cell:"
					+ "<ul><li>A simple click on a cell will change it's state to the nearest possible one.</li>"
					+ "<li>Dragging the mouse holding the left mouse button will change the cells where the arrow moves to the state of the last simple click.</li>"
					+ "<li>Dragging the mouse holding the right mouse button will change the cells where the arrow moves to the first state.</li></ul>"
					+ "Also, in the upper left corner is the \"Pop. Random\" button and on it's right a selection box. Clicking this button will generate a random population using the possible states. The smaller the number in the selection box, the greater the population density.<br>"
					+ "<p>Just below is the \"Clean\" button, which changes all cells to the first state.</p><br><br>"
					+ "<h2>Changing the grid size</h2>In the menu bar, in settings, in the option \"Grid Size\" it is possible to select between 20x20, 40x40, 80x80, 160x160 e 320x320. Will be kept the state of the cells unaffected by the change in the grid size.<br><br>"
					+ "<h2>Name and Color of possible states</h2>In the middle part of the left menu you can define how many states are possible through the selection box. It will be possible to change the name of the states that are possible, but they can not be empty.<p>To the right of each name is a box with the color that represents it and within that box, a number that represents the population of that state. Just click on the color box and a selection box will be displayed, where you can change the color of the clicked state.</p><br><br>"
					+ "<h2>Scale</h2>For better visualization, you can change the scale using the mouse wheel. Pushing it forward will bring the cells closer, whereas the back will pull them apart.<br><br>"
					+ "<p>At the end of the settings, click \"Next\" to proceed to the Rules screen.<br><br>";

			texts[2] = "<h2>Rules</h2>The Rules screen allows you to define valid rules in the automaton. In the upper right corner there is a selection box that allows you to configure the automaton for the rules of \"Game of Life\", the \"Ulam Crystals\" or the \"Rule 614\". When selecting any, any existing rule will be deleted and the rules will be set to those of the selected automaton.<br><br>"
					+ "<h2>Creating a Rule</h2>To create a rule, click in any selection box or in the \"New Rule\" button. The text field on the right will show the rule in natural language. When you change any selection box, the natural language rule will be updated. When the rule is ready, click \"Create Rule\" button. In the upper left corner there are two selection boxes: \"Neighborhood Type\" and \"Is the Center Even?\"."
					+ "<p>Just select any of the options and the type of neighborhood will change to the chosen option. In addition, the image to the left will be changed by displaying the current cell in dark gray and in light gray the positions that will be considered in the type of neighborhood chosen. If \"Is the Center Even\" is selected as yes, the current cell will also be counted in the neighbor count of the current state of the cell.</p><br><br>"
					+ "<h2>Precedence of Rules</h2>The rules follow precedence order from top to bottom, so once a rule is validated for a cell in a cycle, the other rules will be ignored for this cell in this cycle. You can change the precedence of a rule by selecting it and clicking the \"Up\" and \"Down\" buttons.<p>To delete a rule, select it and click \"Delete\". You must define at least one rule to proceed.</p><br><br>"
					+ "<h2>Authors of the Template Rules</h2>Keep in mind that only the authors of the rules are listed, the initial configurations were created by the author of this tool.<br>"
					+ "<ul><li>Conway's Game of Life: created by John Horton Conway in 1970.</li>"
					+ "<li>Ulam's Crystals: created by Stanislaw Marcin Ulam in 1940.</li>"
					+ "<li>Rule 614: created by Miklos Kristof in 2002.</li>"
					+ "<li>Tsunami: created by Madson P. A. da Silva in 2017.</li></ul><br><br>"
					+ "To proceed, click the \"Execution\" button or click \"Configuration\" to return to the previous screen.<br><br>";

			texts[3] = "<h2>Execution</h2>The Execution screen allows you to run the automaton and export it. In the upper left corner is the file panel that allows you to import the configurations and rules of an automaton or export the current automaton."
					+ "<p>Just below is the information panel, where you can see the current cycle, the scale, the coordinate of the cell where the mouse is and restart to the cycle 0 by clicking in the \"Restart\" button.<br><br>"
					+ "<h2>Advancing Cycles</h2>The manual advance panel is below the information panel. By clicking \"Next\" it advances to the next cycle and in \"Previous\", returns a cycle. Just below is the automatic advance panel. Just select the interval in milliseconds for the advance of each turn and click \"Start\" to start the automatic advance. You can stop it by clicking on the \"Stop\" button that appears only when auto-advance is active.<br><br>"
					+ "<h2>Population</h2>In the bottom left corner is the status panel. On the left are the name of the eight states and in the right, the color and population of each state in the current cycle.<br><br>"
					+ "<h2>Handling the Border</h2>The cells that are in the ends of the grid do not have a complete neighborhood. To handle this problem, ESACEL uses the periodic border type. In the periodic border, the ends connect with each other to supply the missing cells, just like the image bellow:<br><img src='"
					+ imgUrl + "'</img>"
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

		btnPrevious.setEnabled(false);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controls the switching of screens
				if (screen == 1) {
					btnPrevious.setEnabled(false);
					screen--;
					lblTopic.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 2) {
					screen--;
					lblTopic.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 3) {
					btnNext.setEnabled(true);
					screen--;
					lblTopic.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				}
			}
		});
		btnPrevious.setFocusable(false);
		btnPrevious.setBounds(65, 11, 100, 20);
		btnPrevious.setFont(new Font("Calibri", Font.BOLD, 14));
		menuPanel.add(btnPrevious);

		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controls the switching of screens
				if (screen == 0) {
					btnPrevious.setEnabled(true);
					screen++;
					lblTopic.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 1) {
					screen++;
					lblTopic.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				} else if (screen == 2) {
					btnNext.setEnabled(false);
					screen++;
					lblTopic.setText(topics[screen]);
					editorPane.setText(texts[screen]);
					editorPane.setCaretPosition(0);
				}
			}
		});
		btnNext.setFocusable(false);
		btnNext.setBounds(495, 11, 100, 20);
		btnNext.setFont(new Font("Calibri", Font.BOLD, 14));
		menuPanel.add(btnNext);

		lblTopic = new JLabel();
		lblTopic.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopic.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTopic.setBounds(230, 10, 200, 23);
		lblTopic.setText(topics[0]);
		menuPanel.add(lblTopic);
	}
}

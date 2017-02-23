package collections;

import controller.*;


/**
 * @author Madson
 *
 */
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// inicializa o controller
		AppController controller = new AppController();
		// inicia a aplicação
		controller.startApplication();
	}
}
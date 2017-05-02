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
package collections;

import controller.*;

/**
 * @author Madson
 *
 */
public class Application {

	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		// starts the controller
		AppController controller = new AppController();
		// starts the application
		// in parentheses is the size of the vector, recomended values are 20,
		// 40, 80, 160 or 320
		controller.startApplication(160);
	}
}
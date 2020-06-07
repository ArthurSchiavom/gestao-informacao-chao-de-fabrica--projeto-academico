package UI;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
	private List<String> menuOptions;

	/**
	 * Instancia o menu e adiciona as opções que o utilizador pode selecionar
	 */
	public MainMenu() {
		menuOptions = new ArrayList<>();
	}

	/**
	 * Draws the main menu
	 */
	public void doShow() {
		int index = 0;
		while(index != -1) {
			index = UIUtils.displayTableAndSelectIndex(menuOptions, "Escolher opção", "Encerrar Programa");
			switch (index) {
				case -1:
					break;
			}
		}
	}
}

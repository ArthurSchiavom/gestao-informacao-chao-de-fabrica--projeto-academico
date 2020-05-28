package eapli.base.app.backoffice.console.presentation;

import eapli.framework.util.Console;

import java.util.List;

public class UIUtils {
	public static int displayTableAndSelectIndex(List list, String header) {
		displayNumberedTable(list, header);
		System.out.println("0 - Cancelar");
		int option = Console.readOption(1, list.size(), 0);
		return option - 1;
	}

	public static void displayNumberedTable(List list, String header) {
		System.out.println(header);
		int index = 1;
		int numCount = 0;
		int listSize = list.size();
		for(; listSize != 0; listSize/=10, ++numCount);
		for (Object o : list) {
			System.out.printf("%"+numCount+"d - %s\n",index, o.toString());
			index++;
		}
	}
}

package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UIUtils {
	public static int displayTableAndSelectIndex(List list, String header, String cancelPrompt) {
		displayNumberedTable(list, header);
		System.out.println("0 - " + cancelPrompt);
		int option = readOption(1, list.size(), 0);
		return option - 1;
	}

	public static void displayNumberedTable(List list, String header) {
		System.out.println(header);
		int index = 1;
		int numCount = 0;
		int listSize = list.size();
		for (; listSize != 0; listSize /= 10, ++numCount) ;
		for (Object o : list) {
			System.out.printf("%" + numCount + "d - %s\n", index, o.toString());
			index++;
		}
	}

	public static int readOption(int low, int high, int exit) {
		int option;
		do {
			option = readInteger("Escolher uma opção: ");
			if (option == exit) {
				break;
			}
		} while (option < low || option > high);
		return option;
	}

	public static String readLine(String prompt) {
		try {
			System.out.println(prompt);
			final InputStreamReader converter = new InputStreamReader(System.in);
			final BufferedReader in = new BufferedReader(converter);
			return in.readLine();
		} catch (final IOException ex) {
			return "";
		}
	}

	public static int readInteger(String prompt) {
		do {
			try {
				final String input = readLine(prompt);
				return Integer.parseInt(input);
			} catch (@SuppressWarnings("unused") final NumberFormatException ex) {
				// nothing to do
			}
		} while (true);
	}
}

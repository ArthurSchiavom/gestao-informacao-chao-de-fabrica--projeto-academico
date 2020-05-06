package eapli.base.app.backoffice.console.presentation.utilities;

import java.util.Scanner;

public class UserInteractionControl {
    public static void enterToContinue() {
        System.out.println("\n\nPress enter to continue\n");
        new Scanner(System.in).nextLine();
    }
}

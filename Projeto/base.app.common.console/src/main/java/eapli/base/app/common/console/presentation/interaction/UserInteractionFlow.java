package eapli.base.app.common.console.presentation.interaction;

import java.util.Scanner;

public class UserInteractionFlow {
    public static void enterToContinue() {
        System.out.println("\n\nPresssione enter para continuar\n");
        new Scanner(System.in).nextLine();
    }
}

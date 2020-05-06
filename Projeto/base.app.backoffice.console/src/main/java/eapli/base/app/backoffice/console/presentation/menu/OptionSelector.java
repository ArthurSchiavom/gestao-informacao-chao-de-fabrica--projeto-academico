package eapli.base.app.backoffice.console.presentation.menu;

import eapli.framework.actions.Action;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.List;

public class OptionSelector {
    private int optionCount;
    private List<String> optionsNames;
    private List<Action> optionsActions;

    public OptionSelector() {
        optionsNames = new ArrayList<>();
        optionsActions = new ArrayList<>();
    }

    public void registerOption (String optionName, Action optionAction) {
        optionCount++;

        optionsNames.add(optionCount + ". " + optionName);
        optionsActions.add(optionAction);
    }

    public void show() {
        if (optionCount == 0)
            return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < optionsNames.size(); i++) {
            sb.append(optionsNames.get(i)).append("\n");
        }

        System.out.print(sb.toString());

        int option = 0;
        while (option == 0) {
            option = Console.readOption(1, optionCount, 0);
        }

        optionsActions.get(option - 1).execute();
    }
}

package eapli.base.app.user.console.presentation.myuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.myclientuser.application.SignupController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
@SuppressWarnings("squid:S106")
public class SignupRequestUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignupRequestUI.class);

    private final SignupController theController = new SignupController();

    @Override
    protected boolean doShow() {
        final UserDataWidget userData = new UserDataWidget();

        userData.show();

        final String mecanographicNumber = Console.readLine("Mecanographic Number");

        try {
            this.theController.signup(userData.username(), userData.password(),
                    userData.firstName(), userData.lastName(), userData.email(),
                    mecanographicNumber);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Sign Up";
    }
}

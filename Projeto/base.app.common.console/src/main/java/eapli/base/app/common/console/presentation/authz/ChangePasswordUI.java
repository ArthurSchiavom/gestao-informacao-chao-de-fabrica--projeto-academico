package eapli.base.app.common.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 * UI for user login action. Created by nuno on 21/03/16.
 */
@SuppressWarnings("squid:S106")
public class ChangePasswordUI extends AbstractUI {

    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    @Override
    protected boolean doShow() {
        final String oldPassword = Console.readLine("Old Password:");
        final String newPassword = Console.readLine("New Password:");

        try {
            if (this.authenticationService.changePassword(oldPassword, newPassword)) {
                System.out.println("Password Successfuly changed");
                return true;
            } else {
                System.out.println("Invalid authentication");
                return false;
            }
        } catch (ConcurrencyException | IntegrityViolationException e) {
            System.out.println("An error has occurred> " + e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public String headline() {
        return "Change Password";
    }
}

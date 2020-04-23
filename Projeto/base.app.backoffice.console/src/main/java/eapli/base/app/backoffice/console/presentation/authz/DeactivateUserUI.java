/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.app.backoffice.console.presentation.authz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.usermanagement.application.DeactivateUserController;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Fernando
 */
@SuppressWarnings("squid:S106")
public class DeactivateUserUI extends AbstractUI {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeactivateUserUI.class);

	private final DeactivateUserController theController = new DeactivateUserController();

	protected Controller controller() {
		return this.theController;
	}

	@Override
	protected boolean doShow() {
		final List<SystemUser> list = new ArrayList<>();
		final Iterable<SystemUser> iterable = this.theController.activeUsers();
		if (!iterable.iterator().hasNext()) {
			System.out.println("There is no registered User");
		} else {
			int cont = 1;
			System.out.println("SELECT User to deactivate\n");
			// FIXME use select widget, see, ChangeDishTypeUI
			System.out.printf("%-6s%-10s%-30s%-30s%n", "Nº:", "Username", "Firstname", "Lastname");
			for (final SystemUser user : iterable) {
				list.add(user);
				System.out.printf("%-6d%-10s%-30s%-30s%n", cont, user.username(), user.name().firstName(),
						user.name().lastName());
				cont++;
			}
			final int option = Console.readInteger("Enter user nº to deactivate or 0 to finish ");
			if (option == 0) {
				System.out.println("No user selected");
			} else {
				try {
					this.theController.deactivateUser(list.get(option - 1));
				} catch (IntegrityViolationException | ConcurrencyException ex) {
					LOGGER.error("Error performing the operation", ex);
					System.out.println(
							"Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
				}
			}
		}
		return true;
	}

	@Override
	public String headline() {
		return "Deactivate User";
	}
}

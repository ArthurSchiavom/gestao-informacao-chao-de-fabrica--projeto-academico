/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.app.backoffice.console.presentation.clientuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.clientusermanagement.application.AcceptRefuseSignupFactory;
import eapli.base.clientusermanagement.application.AcceptRefuseSignupRequestController;
import eapli.base.clientusermanagement.domain.SignupRequest;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

/**
 *
 * Created by AJS on 08/04/2016.
 */
@SuppressWarnings("squid:S106")
public class AcceptRefuseSignupRequestUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptRefuseSignupRequestUI.class);

    private final AcceptRefuseSignupRequestController theController = AcceptRefuseSignupFactory
            .build();

    @Override
    protected boolean doShow() {
        final SelectWidget<SignupRequest> selector = new SelectWidget<>("Pending signups",
                this.theController.listPendingSignupRequests(), new SignupRequestPrinter());
        selector.show();
        final SignupRequest theSignupRequest = selector.selectedElement();
        if (theSignupRequest != null) {
            System.out.println("1. Accept Signup Request");
            System.out.println("2. Refuse Signup Request");
            System.out.println("0. Exit");

            final int option = Console.readOption(1, 2, 0);
            try {
                switch (option) {
                case 1:
                    this.theController.acceptSignupRequest(theSignupRequest);
                    break;
                case 2:
                    this.theController.refuseSignupRequest(theSignupRequest);
                    break;
                default:
                    System.out.println("No valid option selected");
                    break;
                }
            } catch (IntegrityViolationException | ConcurrencyException ex) {
                LOGGER.error("Error performing the operation", ex);
                System.out.println(
                        "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
            }
        }
        return false;
    }

    @Override
    public String headline() {
        return "Accept of Refuse Signup Requests";
    }
}

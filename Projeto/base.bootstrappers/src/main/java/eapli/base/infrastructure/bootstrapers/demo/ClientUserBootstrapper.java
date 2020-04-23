/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.clientusermanagement.application.AcceptRefuseSignupFactory;
import eapli.base.clientusermanagement.application.AcceptRefuseSignupRequestController;
import eapli.base.clientusermanagement.domain.SignupRequest;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.myclientuser.application.SignupController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

/**
 *
 * @author Paulo Sousa
 */
public class ClientUserBootstrapper implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ClientUserBootstrapper.class);

    private final SignupController signupController = new SignupController();
    private final AcceptRefuseSignupRequestController acceptController = AcceptRefuseSignupFactory
            .build();

    @Override
    public boolean execute() {
        signupAndApprove(TestDataConstants.USER_TEST1, "Password1", "John", "Smith",
                "john@smith.com", TestDataConstants.USER_TEST1);
        signupAndApprove("isep959", "Password1", "Mary", "Smith", "mary@smith.com", "isep959");
        return true;
    }

    private SignupRequest signupAndApprove(final String username, final String password,
            final String firstName, final String lastName, final String email,
            final String mecanographicNumber) {
        SignupRequest request = null;
        try {
            request = signupController.signup(username, password, firstName, lastName, email,
                    mecanographicNumber);
            acceptController.acceptSignupRequest(request);
        } catch (final ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", username);
            LOGGER.trace("Assuming existing record", e);
        }
        return request;
    }
}

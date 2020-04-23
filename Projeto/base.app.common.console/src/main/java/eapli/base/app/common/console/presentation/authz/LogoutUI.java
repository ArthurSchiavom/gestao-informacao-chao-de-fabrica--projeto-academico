/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.app.common.console.presentation.authz;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;

/**
 *
 * @author mcn
 */
public class LogoutUI extends AbstractUI {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public String headline() {
        return "Logout sucessful!!\n Make a new Login";
    }

    @Override
    protected boolean doShow() {
        authz.clearSession();
        return true;
    }
}

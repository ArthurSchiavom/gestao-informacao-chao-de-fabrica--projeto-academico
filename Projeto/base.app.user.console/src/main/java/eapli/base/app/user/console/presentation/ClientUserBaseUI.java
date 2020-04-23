/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.app.user.console.presentation;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;

/**
 *
 * @author mcn
 */
@SuppressWarnings("squid:S106")
public abstract class ClientUserBaseUI extends AbstractUI {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();


    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ] ")
                .orElse("Base [ ==Anonymous== ]");
    }

    @Override
    protected void drawFormTitle(final String title) {
        final String titleBorder = BORDER.substring(0, 2) + " " + title;
        System.out.println(titleBorder);
        drawFormBorder();
    }
}

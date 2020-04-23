/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import java.util.HashSet;
import java.util.Set;

import eapli.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * @author Paulo Gandra Sousa
 */
public class BackofficeUsersBootstrapper extends UsersBootstrapperBase implements Action {

    @SuppressWarnings("squid:S2068")
    private static final String PASSWORD1 = "Password1";

    @Override
    public boolean execute() {
        registerCashier("cashier", PASSWORD1, "Johny", "Cash", "johny.doe@emai.l.com");
        registerKitchenManager("kitchen", PASSWORD1, "Oven", "Stove", "Oven.and.stove@emai.l.com");
        registerMenuManager("chef", PASSWORD1, "Master", "Chef", "master.chef@emai.l.com");
        return true;
    }

    private void registerCashier(final String username, final String password,
            final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CASHIER);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerKitchenManager(final String username, final String password,
            final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.KITCHEN_MANAGER);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerMenuManager(final String username, final String password,
            final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.MENU_MANAGER);

        registerUser(username, password, firstName, lastName, email, roles);
    }
}

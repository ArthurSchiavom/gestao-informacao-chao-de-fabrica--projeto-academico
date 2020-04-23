package eapli.base.app.common.console.presentation.authz;

import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * Menu action for user login. Created by nuno on 20/03/16.
 */
public class LoginAction implements Action {

    private final Role onlyWithThis;

    public LoginAction() {
        onlyWithThis = null;
    }

    /**
     *
     * @param onlyWithThis
     *            only if the user has this specific action right will be
     *            allowed to login
     */
    public LoginAction(Role onlyWithThis) {
        this.onlyWithThis = onlyWithThis;
    }

    @Override
    public boolean execute() {
        return new LoginUI(onlyWithThis).show();
    }
}

package eapli.base.app.user.console.presentation.myuser;

import eapli.framework.util.Console;

/**
 * TODO move to console.common to allow reuse from both backoffice and UtenteApp
 *
 * widget for reading user data Jorge Santos ajs@isp.ipp.pt
 */
class UserDataWidget {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public void show() {
        this.username = Console.readLine("Username");
        this.password = Console.readLine("Password");
        this.firstName = Console.readLine("First Name");
        this.lastName = Console.readLine("Last Name");
        this.email = Console.readLine("E-Mail");
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String email() {
        return this.email;
    }
}

package eapli.base;

public interface ExceptionAction1Checked<E extends Throwable> {
    void execute() throws E;
}

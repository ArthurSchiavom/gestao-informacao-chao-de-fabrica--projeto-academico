package eapli.base.infrastructure.domain;

public class IllegalDomainValueException extends Exception {
    public final IllegalDomainValueType type;
    public IllegalDomainValueException(String message, IllegalDomainValueType type) {
        super(message);
        this.type = type;
    }
}

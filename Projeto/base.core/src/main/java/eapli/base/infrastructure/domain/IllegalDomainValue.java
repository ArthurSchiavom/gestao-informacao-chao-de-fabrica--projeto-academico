package eapli.base.infrastructure.domain;

public class IllegalDomainValue extends Exception {
    public final IllegalDomainValueType type;
    public IllegalDomainValue(String message, IllegalDomainValueType type) {
        super(message);
        this.type = type;
    }
}

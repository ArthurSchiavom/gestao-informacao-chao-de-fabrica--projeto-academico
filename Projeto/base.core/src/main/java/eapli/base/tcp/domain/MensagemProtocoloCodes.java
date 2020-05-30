package eapli.base.tcp.domain;

public enum MensagemProtocoloCodes {
    HELLO(0), MSG(1), CONFIG(2), RESET(3), ACK(150), NACK(151);
    private final int code;

    private MensagemProtocoloCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

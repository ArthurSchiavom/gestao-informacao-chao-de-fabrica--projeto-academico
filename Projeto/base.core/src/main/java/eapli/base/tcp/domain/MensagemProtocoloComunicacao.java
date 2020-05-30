package eapli.base.tcp.domain;

/**
 * Mensagem do protocolo, entre máquinas e SCM
 */
public class MensagemProtocoloComunicacao {

    public final String mensagem;
    public final Byte version;
    public final Byte code;
    public final char idProtocolo; // char is 2 unsigned bytes in java
    public final char tamanhoRawData; // ^

    public MensagemProtocoloComunicacao(Byte version, Byte code, char idProtocolo, char tamanhoRawData,
                                        String conteudoMensagem) {

        if (version == null || code == null) {
            throw new IllegalArgumentException("Argumentos fornecidos para mensagem de protocolo estão incorretos.");
        }

        this.version = version;
        this.code = code;
        this.idProtocolo = idProtocolo;
        this.tamanhoRawData = tamanhoRawData;
        this.mensagem = conteudoMensagem;

    }
}

package eapli.base.tcp.processamento;

import eapli.base.tcp.domain.MensagemProtocoloComunicacao;

import java.net.Socket;

public interface ProcessarMensagensProtocolosStrategy {

    public MensagemProtocoloComunicacao processarMensagem(Socket s);
}

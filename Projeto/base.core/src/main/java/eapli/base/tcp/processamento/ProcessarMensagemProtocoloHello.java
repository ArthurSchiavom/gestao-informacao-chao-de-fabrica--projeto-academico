package eapli.base.tcp.processamento;

import eapli.base.gestaoproducao.gestaomaquina.domain.IdentificadorProtocoloComunicacao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.tcp.domain.MensagemProtocoloCodes;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;

import java.net.*;
import java.util.Optional;

/**
 * Quando uma máquina industrial é iniciada, ela envia um pedido HELLO baseada em TCP para o sistema
 * central. Por sua vez, o sistema central verifica se o ID da máquina é seu conhecido. Se sim, este envia
 * de volta uma resposta ACK e atualiza/define no seu repositório o endereço de rede da máquina
 * industrial. Caso contrário, uma resposta NACK é retornada
 */
public class ProcessarMensagemProtocoloHello implements ProcessarMensagensProtocolosStrategy {

    public final MensagemProtocoloComunicacao mensagemProtocoloComunicacao;

    private MaquinaRepository repo = PersistenceContext.repositories().maquinas();

    public ProcessarMensagemProtocoloHello(Byte version, Byte code, char idMaquinaProtocolo,
                                           char tamanhoData, String rawData) {
        mensagemProtocoloComunicacao = new MensagemProtocoloComunicacao(version, code, idMaquinaProtocolo,
                tamanhoData, rawData);
        //TODO: guardar no log
    }

    @Override
    public MensagemProtocoloComunicacao processarMensagem(Socket s) {

        Maquina maq;
        if ((maq = maquinaIdentificadorExiste(mensagemProtocoloComunicacao.idProtocolo)) == null) {

            //falha, retorna NACK
            return mensagemNACK();
        }

        InetAddress sockaddr = s.getInetAddress();


        // guardar IP na máquina

        maq.setIp(sockaddr);

        repo.save(maq);

        //sucesso retorna ACK
        return mensagemACK();
    }

    /**
     * Verifica se uma maquina com o Identificador de protocolo existe
     */
    private Maquina maquinaIdentificadorExiste(char maquinaProtocoloID) {
        IdentificadorProtocoloComunicacao codMaquina;

        try {
            codMaquina = new IdentificadorProtocoloComunicacao(maquinaProtocoloID);
        } catch (IllegalArgumentException ex) {
            return null;
        }

        Optional<Maquina> maqOpt = repo.findByidentificadorProtocoloComunicacao(codMaquina);
        if (maqOpt.isPresent()) {
            return maqOpt.get();
        }

        return null;
    }

    /**
     * Constroi uma MensagemProtocoloComunicao do tipo ACK, com texto vazio
     */
    private MensagemProtocoloComunicacao mensagemACK() {

        return new MensagemProtocoloComunicacao(mensagemProtocoloComunicacao.version,
                (byte) MensagemProtocoloCodes.ACK.getCode(),
                mensagemProtocoloComunicacao.idProtocolo, (char) 0, null);
    }

    /**
     * Constroi uma MensagemProtocoloComunicao do tipo NACK, com texto vazio
     */
    private MensagemProtocoloComunicacao mensagemNACK() {

        return new MensagemProtocoloComunicacao(mensagemProtocoloComunicacao.version,
                (byte) MensagemProtocoloCodes.NACK.getCode(),
                mensagemProtocoloComunicacao.idProtocolo, (char) 0, null);
    }

}

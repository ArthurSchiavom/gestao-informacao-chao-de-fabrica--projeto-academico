package eapli.base.tcp.processamento;

import eapli.base.gestaoproducao.gestaomaquina.domain.IdentificadorProtocoloComunicacao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;
import java.net.*;
import java.util.Optional;

/**
 * Quando uma máquina industrial é iniciada, ela envia um pedido HELLO baseada em TCP para o sistema
 * central. Por sua vez, o sistema central verifica se o ID da máquina é seu conhecido. Se sim, este envia
 * de volta uma resposta ACK e atualiza/define no seu repositório o endereço de rede da máquina
 * industrial. Caso contrário, uma resposta NACK é retornada
 */
public class ProcessarMensagemProtocoloHello extends ProcessarMensagensProtocolosStrategy {

    public final MensagemProtocoloComunicacao mensagemProtocoloComunicacao;

    private MaquinaRepository repo = PersistenceContext.repositories().maquinas();

    public ProcessarMensagemProtocoloHello(Byte version, Byte code, char idMaquinaProtocolo,
                                           char tamanhoData, String rawData) {
        mensagemProtocoloComunicacao = new MensagemProtocoloComunicacao(version, code, idMaquinaProtocolo,
                tamanhoData, rawData);
    }

    @Override
    public MensagemProtocoloComunicacao processarMensagem(Socket s) {
        criarLog(mensagemProtocoloComunicacao.version, mensagemProtocoloComunicacao.code,
                mensagemProtocoloComunicacao.idProtocolo, mensagemProtocoloComunicacao.tamanhoRawData,
                mensagemProtocoloComunicacao.mensagem);
        Maquina maq;
        if ((maq = maquinaIdentificadorExiste(mensagemProtocoloComunicacao.idProtocolo)) == null || mensagemProtocoloComunicacao.version != ProcessarMensagensProtocolosStrategy.getVersion()) {

            //falha, retorna NACK
            return mensagemNACK(mensagemProtocoloComunicacao.version,mensagemProtocoloComunicacao.idProtocolo);
        }

        InetAddress sockaddr = s.getInetAddress();


        // guardar IP na máquina

        maq.setIp(sockaddr);

        repo.save(maq);

        //sucesso retorna ACK
        return mensagemACK(mensagemProtocoloComunicacao.version,mensagemProtocoloComunicacao.idProtocolo);
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
}

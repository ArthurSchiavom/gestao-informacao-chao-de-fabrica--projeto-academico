package eapli.base.tcp.processamento;

import eapli.base.gestaoproducao.gestaomaquina.domain.IdentificadorProtocoloComunicacao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.servicoComunicacaoComMaquinas.MessageFactory;
import eapli.base.tcp.domain.MensagemProtocoloCodes;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

/**
 * As máquinas industriais enviam pedidos MSG baseadas em TCP ao sistema central e recebem uma
 * resposta ACK caso o ID e endereço de rede da máquina coincidam com a informação disponível no
 * sistema central. Caso contrário, o pedido é ignorado e a resposta NACK é retornada
 */
public class ProcessarMensagemProtocoloMSG implements ProcessarMensagensProtocolosStrategy {

    public final MensagemProtocoloComunicacao mensagemProtocoloComunicacao;

    private MaquinaRepository repo = PersistenceContext.repositories().maquinas();

    public ProcessarMensagemProtocoloMSG(Byte version, Byte code, char idMaquinaProtocolo,
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

        // verifica se o IP recebido é igual ao IP na máquina
        if (!maq.getIp().equals(sockaddr)) {
            // o IP recebido é diferente do IP recebido
            return mensagemNACK();
        }

        //Processar mensagens


        Mensagem mens ;
        MessageFactory mf = new MessageFactory();
        try {
            mens = mf.getMessageType(mensagemProtocoloComunicacao.mensagem.split(";"));
        } catch (Exception e) {
            return mensagemNACK();
        }

        PersistenceContext.repositories().mensagem().save(mens);

        System.out.println("MSG sucesso");
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

package eapli.base.servicoComunicacaoComMaquinas;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaoproduto.application.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MessageFactory {
    OrdemProducaoRepository ordemProducaoRepository=null;

    public Mensagem getMessageType(String vec[]) throws IllegalDomainValueException {
        if ((vec.length<3 || vec.length>6 ||vec==null))
            return null;
        Date date=new Date(Long.parseLong(vec[2].trim()));
        ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        CodigoInternoMaquina codigoInternoMaquina= new CodigoInternoMaquina(vec[0]);
        CodigoDeposito codigoDeposito=null;
        IdentificadorDeLote identificadorDeLote=null;
        CodigoUnico codigoUnico;
        OrdemProducao ordemPrd=null;
        Identificador ordemID;
        String erro;
        Optional<OrdemProducao> ordemProducao;
        int quantidade;
        switch (vec[1]) {
            case "C0":
                System.out.println("C0");
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito
                //CodigoDeposito opcional
                quantidade = Integer.parseInt(vec[4]);
                if (vec.length==6) //Caso seja empty fica null como foi inicializado
                     codigoDeposito= new CodigoDeposito(vec[5].trim());
                return new MensagemConsumo(codigoDeposito, codigoInternoMaquina, date, quantidade);
            case "C9":
                System.out.println("C9");
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito;Lote
                //Lote é opcional
                codigoDeposito=new CodigoDeposito(vec[5]);
                quantidade=Integer.parseInt(vec[4]);
                if (vec.length==7)
                    identificadorDeLote=new IdentificadorDeLote(vec[6]);
                return new MensagemEntregaDeProducao(codigoDeposito,codigoInternoMaquina,date,quantidade,identificadorDeLote);
            case "P1":
                System.out.println("P1");
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Lote
                //Lote opc
                codigoUnico=CodigoUnico.valueOf(vec[3],null);
                quantidade=Integer.parseInt(vec[4]);
                if (vec.length==6)
                    identificadorDeLote=new IdentificadorDeLote(vec[5]);
                return new MensagemProducao(codigoInternoMaquina,date,codigoUnico,quantidade,identificadorDeLote);
            case "P2":
                System.out.println("P2");
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito
                //Deposito opc
                codigoUnico=CodigoUnico.valueOf(vec[3],null);
                quantidade=Integer.parseInt(vec[4]);
                if (!vec[5].isEmpty()) //Caso seja empty fica null como foi inicializado
                    codigoDeposito= new CodigoDeposito(vec[5].trim());
                return  new MensagemEstorno(codigoUnico,codigoDeposito,codigoInternoMaquina,date,quantidade);
            case "S0":
                //Máquina;TipoMsg;DataHora;OrdemProducao
                //Ordem opc
                System.out.println("S0");
                if (vec.length==4) {
                    ordemID = new Identificador(vec[3].trim());
                    ordemProducao=ordemProducaoRepository.findByIdentifier(ordemID);
                    try{
                        ordemPrd = ordemProducao.get();
                    }catch (NoSuchElementException e){
                        throw new NoSuchElementException("Ordem de producao nao existe, verifique a sua mensagem!");
                    }
                }
                return new MensagemFimDeAtividade(codigoInternoMaquina,date,ordemPrd);
            case "S1":
                System.out.println("S1");
                //Máquina;TipoMsg;DataHora;Erro
                erro=vec[3];
                return new MensagemRetomoDeActividade(codigoInternoMaquina,date,erro);
            case "S8":
                System.out.println("S8");
                //Máquina;TipoMsg;DataHora
                return new MensagemParagemForcada(codigoInternoMaquina,date);
            case "S9":
                System.out.println("S9");
                //Máquina;TipoMsg;DataHora;OrdemProducao
                if (vec.length==4) {
                    ordemID = new Identificador(vec[3].trim());
                    ordemProducao=ordemProducaoRepository.findByIdentifier(ordemID);
                    try{
                        ordemPrd = ordemProducao.get();
                    }catch (NoSuchElementException e){
                        throw new NoSuchElementException("Ordem de producao nao existe, verifique a sua mensagem!");
                    }
                }
                return new MensagemFimDeAtividade(codigoInternoMaquina,date,ordemPrd);
            default:
                throw new IllegalArgumentException("Nao foi encontrado o tipo de mensagem correspondente -> Tipo Mensagem nao encontrada: " + vec[0]);
        }
    }
}

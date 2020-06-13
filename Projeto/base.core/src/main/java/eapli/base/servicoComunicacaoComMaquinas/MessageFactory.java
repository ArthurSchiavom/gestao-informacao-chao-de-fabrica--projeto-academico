package eapli.base.servicoComunicacaoComMaquinas;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.infrastructure.domain.IllegalDomainValueException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageFactory {


    public Mensagem getMessageType(String vec[]) throws IllegalDomainValueException, ParseException {
        if ((vec.length<3 || vec.length>6 ||vec==null))
            return null;
        Date date=verificarDataImportada(vec[2].trim());
        CodigoInternoMaquina codigoInternoMaquina= new CodigoInternoMaquina(vec[0]);
        CodigoDeposito codigoDeposito=null;
        IdentificadorDeLote identificadorDeLote=null;
        CodigoUnico codigoUnico;
        OrdemProducao ordemPrd=null;
        IdentificadorOrdemProducao ordemID=null;
        String erro;
        double quantidade;
        switch (vec[1]) {
            case "C0":
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito
                //CodigoDeposito opcional
                quantidade = Double.parseDouble(vec[4]);
                codigoUnico=CodigoUnico.valueOf(vec[3],null);
                if (vec.length==6) //Caso seja empty fica null como foi inicializado
                     codigoDeposito= new CodigoDeposito(vec[5].trim());
                return new MensagemConsumo(codigoDeposito, codigoInternoMaquina, date, quantidade,codigoUnico);
            case "C9":
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito;Lote
                //Lote é opcional
                codigoDeposito=new CodigoDeposito(vec[5]);
                quantidade=Double.parseDouble((vec[4]));
                if (vec.length==7)
                    identificadorDeLote=new IdentificadorDeLote(vec[6]);
                return new MensagemEntregaDeProducao(codigoDeposito,codigoInternoMaquina,date,quantidade,identificadorDeLote);
            case "P1":
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Lote
                //Lote opc
                codigoUnico=CodigoUnico.valueOf(vec[3],null);
                quantidade=Double.parseDouble((vec[4]));
                if (vec.length==6)
                    identificadorDeLote=new IdentificadorDeLote(vec[5]);
                return new MensagemProducao(codigoInternoMaquina,date,codigoUnico,quantidade,identificadorDeLote);
            case "P2":
                //Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito
                //Deposito opc
                codigoUnico=CodigoUnico.valueOf(vec[3],null);
                quantidade=Double.parseDouble((vec[4]));
                if (!vec[5].isEmpty()) //Caso seja empty fica null como foi inicializado
                    codigoDeposito= new CodigoDeposito(vec[5].trim());
                return  new MensagemEstorno(codigoUnico,codigoDeposito,codigoInternoMaquina,date,quantidade);
            case "S0":
                //Máquina;TipoMsg;DataHora;OrdemProducao
                //Ordem opc
//                System.out.println("S0");
                if (vec.length==4) {
                    ordemID = new IdentificadorOrdemProducao(vec[3].trim());
                }
                return new MensagemInicioDeAtividade(date,codigoInternoMaquina,ordemID);
            case "S1":
                //Máquina;TipoMsg;DataHora;Erro
                erro=vec[3];
                return new MensagemRetomoDeActividade(codigoInternoMaquina,date,erro);
            case "S8":
                //Máquina;TipoMsg;DataHora
                return new MensagemParagemForcada(codigoInternoMaquina,date);
            case "S9":
                //Máquina;TipoMsg;DataHora;OrdemProducao
                if (vec.length==4) {
                    ordemID = new IdentificadorOrdemProducao(vec[3].trim());
                }
                return new MensagemFimDeAtividade(codigoInternoMaquina,date,ordemID);
            default:
                throw new IllegalArgumentException("Nao foi encontrado o tipo de mensagem correspondente -> Tipo Mensagem nao encontrada: " + vec[0]);
        }
    }

    private Date verificarDataImportada(String data) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long validar = Long.parseLong(data);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Data/Tempo fornecido é invalido");
        }
        if (data.length() != 14)
            throw new IllegalArgumentException("Data/Tempo com formato invalido!");
        String ano = data.substring(0, 4);
        String mes = data.substring(4, 6);
        String dia = data.substring(6, 8);
        String hora = data.substring(8, 10);
        String minutos = data.substring(10, 12);
        String segundos = data.substring(12, 14);
        String make = ano + "-" + mes + "-" + dia + " " + hora + ":" + minutos + ":" + segundos;
        Date date = format.parse(make);
        return date;
    }

}

package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.CONSUMO)
public class MensagemConsumo extends Mensagem implements AggregateRoot<Long> {


    //C0 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito
//    public final CodigoInternoMaquina maquinaID;
//    public final Date dataHora;
//    public final CodigoUnico produtoID;
//    public final int quantidade;
//    public final CodigoUnico depositoID;




}

package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value = TipoDeMensagem.Values.INICIO_DE_ATIVIDADE)
public class MensagemInicioDeAtividade extends Mensagem implements AggregateRoot<MensagemID> {

	protected MensagemInicioDeAtividade() {
		super();
	}

	public MensagemInicioDeAtividade(Date tempoEmissao, CodigoInternoMaquina maquinaID, IdentificadorOrdemProducao ordem) {
		super(TipoDeMensagem.INICIO_DE_ATIVIDADE, new TimestampEmissao(tempoEmissao), maquinaID, ordem);
	}
}
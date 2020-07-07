package eapli.base.gestaoproducao.ordemProducao.domain;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;

@Embeddable
public class TempoProducao {
	@XmlValue
	public final Integer tempoExecucao;

	public TempoProducao(int tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	public TempoProducao() {
		tempoExecucao = null;
	}
}

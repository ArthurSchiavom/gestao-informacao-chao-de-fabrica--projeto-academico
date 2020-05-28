package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.EstadoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class JpaNotificacoesErroRepository
		extends JpaAutoTxRepository<NotificacaoErro, Long, Long>
		implements NotificacaoErroRepository {
	public JpaNotificacoesErroRepository(TransactionalContext autoTx) {
		super(autoTx, LinhaProducao.identityAttributeName());
	}

	public JpaNotificacoesErroRepository(String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				LinhaProducao.identityAttributeName());
	}

	/*
	 * Eu queria ter utilizado um CriteriaQuery para poder escolher se é obrigatório filtrar ou não
	 * (tal como no https://www.objectdb.com/java/jpa/query/jpql/where)
	 * No entanto isto não é possivel com este repositório
	 */
	@Override
	public List<NotificacaoErro> findAllPorTratarfilteredByTipoErroAndLinhaProd(TipoErroNotificacao tipo,
	                                                                            IdentificadorLinhaProducao idLinhaProd) {
		TypedQuery<NotificacaoErro> tq = this.createQuery("SELECT e FROM NotificacaoErro e " +
				"WHERE e.tipoErroNotificacao = :erro AND " +
				"e.idLinhaProd = :idLinha AND " +
				"e.estadoErro = :estado", NotificacaoErro.class);
		tq.setParameter("erro", tipo);
		tq.setParameter("idLinha", idLinhaProd);
		tq.setParameter("estado", EstadoErroNotificacao.ATIVO);
		return tq.getResultList();
	}

	@Override
	public List<NotificacaoErro> findAllNaoArquivados() {
		TypedQuery<NotificacaoErro> tq = this.createQuery("SELECT e FROM NotificacaoErro e " +
				"WHERE e.estadoErro = :estado", NotificacaoErro.class);
		tq.setParameter("estado", EstadoErroNotificacao.ARQUIVADO);
		return tq.getResultList();
	}
}

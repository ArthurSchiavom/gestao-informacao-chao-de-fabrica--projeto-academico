package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.EstadoNotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
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
		tq.setParameter("estado", EstadoNotificacaoErro.ATIVO);
		return tq.getResultList();
	}

	@Override
	public List<NotificacaoErro> findAllNaoArquivados() {
		TypedQuery<NotificacaoErro> tq = this.createQuery("SELECT e FROM NotificacaoErro e " +
				"WHERE e.estadoErro = :estado", NotificacaoErro.class);
		tq.setParameter("estado", EstadoNotificacaoErro.ARQUIVADO);
		return tq.getResultList();
	}

	@Override
	public List<NotificacaoErro> findAll(List<String> idsLinhasProducaoSelecionadas, List<TipoErroNotificacao> tiposNotificaoErroSelecionados, List<EstadoNotificacaoErro> estadoErroNotificacaosSelecionados) {
		// Pode ser facilmente melhorado com a Spring Data API
		int lengthLinhasDeProducao = idsLinhasProducaoSelecionadas.size();
		int lengthTipos = tiposNotificaoErroSelecionados.size();
		int lengthEstados = estadoErroNotificacaosSelecionados.size();

		StringBuilder queryRaw = new StringBuilder();

		queryRaw.append("SELECT e FROM NotificacaoErro e");
		if (lengthLinhasDeProducao > 0 || lengthTipos > 0 || lengthEstados > 0) {
			queryRaw.append(" WHERE");
		}

		if (lengthLinhasDeProducao > 0) {
			queryRaw.append(" (");
			queryRaw.append(" e.idLinhaProd.identifier = '")
					.append(idsLinhasProducaoSelecionadas.get(0))
					.append("'");

			for (int i = 1; i < lengthLinhasDeProducao; i++) {
				queryRaw.append(" OR e.idLinhaProd.identifier = '")
						.append(idsLinhasProducaoSelecionadas.get(i))
						.append("'");
			}

			queryRaw.append(" )");
		}

		int counter = 1;
		if (lengthTipos > 0) {
			if (lengthLinhasDeProducao > 0) {
				queryRaw.append(" AND");
			}

			queryRaw.append(" (");
			queryRaw.append(" e.tipoErroNotificacao = :val").append(counter++);

			for (int i = 1; i < lengthTipos; i++) {
				queryRaw.append(" OR e.tipoErroNotificacao = :val").append(counter++);
			}

			queryRaw.append(" )");
		}


		if (lengthEstados > 0) {
			if (lengthLinhasDeProducao > 0 || lengthTipos > 0) {
				queryRaw.append(" AND");
			}

			queryRaw.append(" (");
			queryRaw.append(" e.estadoErro = :val").append(counter++);

			for (int i = 1; i < lengthEstados; i++) {
				queryRaw.append(" OR e.estadoErro = :val").append(counter++);
			}

			queryRaw.append(" )");
		}

		TypedQuery<NotificacaoErro> tq = this.createQuery(queryRaw.toString(), NotificacaoErro.class);
		counter = 1;

		for (int i = 0; i < lengthTipos; i++) {
			tq.setParameter("val" + counter++, tiposNotificaoErroSelecionados.get(i));
		}
		for (int i = 0; i < lengthEstados; i++) {
			tq.setParameter("val" + counter++, estadoErroNotificacaosSelecionados.get(i));
		}

		return tq.getResultList();
	}

	@Override
	public boolean mensagemTemErroPorTratar(MensagemID mensagemID) {
		TypedQuery<NotificacaoErro> tq = this.createQuery(" SELECT n FROM NotificacaoErro n, Mensagem m " +
				" WHERE m.mensagemID = :idmsg1 " +
				" AND n.idMensagem = :idmsg2 " +
				" AND n.estadoErro = :estado ", NotificacaoErro.class);
		tq.setParameter("idmsg1", mensagemID);
		tq.setParameter("idmsg2", mensagemID);
		tq.setParameter("estado", EstadoNotificacaoErro.ATIVO);
		List<NotificacaoErro> resultado = tq.getResultList();
		return !resultado.isEmpty();
	}
}

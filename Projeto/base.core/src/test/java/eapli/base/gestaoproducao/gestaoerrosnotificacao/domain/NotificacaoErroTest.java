package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotificacaoErroTest {
	private LinhaProducaoRepository lProdRepo = Mockito.mock(LinhaProducaoRepository.class);
	private MensagemRepository msgRepo = Mockito.mock(MensagemRepository.class);
	private IdentificadorLinhaProducao idDummy = new IdentificadorLinhaProducao("dummy");

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNotificacaoDeErroTemLinhaProducao() {
		new NotificacaoErro(null, TipoErroNotificacao.DADOS_INVALIDOS, 2L, lProdRepo, msgRepo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNotificacaoDeErroTemTipoErro() {
		new NotificacaoErro(idDummy, null, 2L, lProdRepo, msgRepo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNotificacaoDeErroTemIdMensagem() {
		new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, null, lProdRepo, msgRepo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNotificacaoDeErroTemRepoLProd() {
		new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L, null, msgRepo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNotificacaoDeErroTemRepoMensagens() {
		new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L, lProdRepo, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueIDLinhaProdExiste() {
		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(false);
		new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L, lProdRepo, msgRepo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueIDMensagemExiste() {
		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(2L)).thenReturn(false);
		new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L, lProdRepo, msgRepo);
	}

	@Test
	public void garantirQueNaoSePodeArquivarNotificacoesArquivadas() {
		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(2L)).thenReturn(true);
		NotificacaoErro notifErro = new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L,
				lProdRepo, msgRepo);
		assertTrue(notifErro.arquivar());
		assertFalse(notifErro.arquivar());
	}

	@Test
	public void garantirQueOEstadoFicaArquivado() {
		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(2L)).thenReturn(true);
		NotificacaoErro notifErro = new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L,
				lProdRepo, msgRepo);
		notifErro.arquivar();
		assertTrue(notifErro.isArquivado());
	}

	@Test
	public void garantirEqualsNaoUsaOEstado() {
		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(2L)).thenReturn(true);
		NotificacaoErro notifErro = new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L,
				lProdRepo, msgRepo);
		notifErro.arquivar();
		NotificacaoErro notificacaoErro = new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, 2L,
				lProdRepo, msgRepo);
		assertTrue(notifErro.equals(notificacaoErro));
	}
}
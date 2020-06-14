package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.base.gestaoproducao.gestaomensagens.domain.TimestampEmissao;
import eapli.base.gestaoproducao.gestaomensagens.domain.TipoDeMensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;


public class ServicoArquivacaoNotificacoesErroTest {
	private final NotificacaoErroRepository repo = Mockito.mock(NotificacaoErroRepository.class);
	private final ServicoArquivacaoNotificacoesErro servico = new ServicoArquivacaoNotificacoesErro(repo);
	MensagemID mid = new MensagemID(TipoDeMensagem.CONSUMO,new TimestampEmissao(new Date()),new CodigoInternoMaquina("123"));

	private LinhaProducaoRepository lProdRepo = Mockito.mock(LinhaProducaoRepository.class);
	private MensagemRepository msgRepo = Mockito.mock(MensagemRepository.class);
	private IdentificadorLinhaProducao idDummy = new IdentificadorLinhaProducao("dummy");

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueIdNaoPodeSerNull() {
		servico.arquivar(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void garantirQueIdExisteNaBaseDeDados() {
		NotificacaoErro notifErro = Mockito.mock(NotificacaoErro.class);
		Mockito.when(repo.ofIdentity(2L)).thenReturn(Optional.of(notifErro));
		servico.arquivar(2L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNaoSePodeArquivarNotificacaoArquivada() {
		MensagemID mid = new MensagemID(TipoDeMensagem.CONSUMO,new TimestampEmissao(new Date()),new CodigoInternoMaquina(""));
		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(mid)).thenReturn(true);
		NotificacaoErro notifErro = new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, mid,
				lProdRepo, msgRepo);
		notifErro.arquivar();
		Mockito.when(repo.ofIdentity(2L)).thenReturn(Optional.of(notifErro));
		servico.arquivar(2L);
	}

	@Test
	public void garantirQueNotificacaoFicaArquivada() {
		MensagemID mid = new MensagemID(TipoDeMensagem.CONSUMO,new TimestampEmissao(new Date()),new CodigoInternoMaquina("123"));

		Mockito.when(lProdRepo.containsOfIdentity(idDummy)).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(mid)).thenReturn(true);
		NotificacaoErro notifErro = new NotificacaoErro(idDummy, TipoErroNotificacao.DADOS_INVALIDOS, mid,
				lProdRepo, msgRepo);
		Mockito.when(repo.ofIdentity(2L)).thenReturn(Optional.of(notifErro));
		Mockito.when(repo.save(notifErro)).thenReturn(notifErro);
		NotificacaoErro arquivada = servico.arquivar(2L);
		assertTrue(notifErro.isArquivado());
	}
}
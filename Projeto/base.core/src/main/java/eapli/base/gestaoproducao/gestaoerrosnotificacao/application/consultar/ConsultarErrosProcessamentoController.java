package eapli.base.gestaoproducao.gestaoerrosnotificacao.application.consultar;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.EstadoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.EstadoNotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.TipoNotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.base.gestaoproducao.gestaomensagens.domain.TimestampEmissao;
import eapli.base.gestaoproducao.gestaomensagens.domain.TipoDeMensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;

import java.util.*;

public class ConsultarErrosProcessamentoController {
    private final List<LinhaProducaoDTO> linhasProducaoDTO;
    private final List<TipoNotificacaoErroDTO> tiposNotificaoErroDTO;
    private final List<EstadoNotificacaoErroDTO> estadosNotificacaoErroDTO;

    private final List<String> idsLinhasProducaoSelecionadas;
    private final List<TipoErroNotificacao> tiposNotificaoErroSelecionados;
    private final List<EstadoErroNotificacao> estadoErroNotificacaosSelecionados;

    private final RepositoryFactory repositoryFactory;

    public ConsultarErrosProcessamentoController() {
        repositoryFactory = PersistenceContext.repositories();
        LinhaProducaoRepository linhaProducaoRepository = repositoryFactory.linhasProducao();

        idsLinhasProducaoSelecionadas = new ArrayList<>();
        tiposNotificaoErroSelecionados = new ArrayList<>();
        estadoErroNotificacaosSelecionados = new ArrayList<>();

        List<LinhaProducao> linhasDeProducao = linhaProducaoRepository.findAllList();

        linhasProducaoDTO = Collections.unmodifiableList(DTOUtils.toDTOList(linhasDeProducao));
        tiposNotificaoErroDTO = Collections.unmodifiableList(DTOUtils.toDTOList(TipoErroNotificacao.values()));
        estadosNotificacaoErroDTO = Collections.unmodifiableList(DTOUtils.toDTOList(EstadoErroNotificacao.values()));
    }

    /**
     *
     * @return Lista ordenada de linhas de produção
     */
    public List<LinhaProducaoDTO> linhasProducao() {
        return linhasProducaoDTO;
    }

    /**
     *
     * @return Lista ordenada de tipos de notificações de erro
     */
    public List<TipoNotificacaoErroDTO> tiposNotificaoErro() {
        return tiposNotificaoErroDTO;
    }

    /**
     *
     * @return Lista ordenada de estados de notificação de erro
     */
    public List<EstadoNotificacaoErroDTO> estadosNotificacaoErro() {
        return estadosNotificacaoErroDTO;
    }

    private void selecionarLinhasDeProducao(Collection<LinhaProducaoDTO> linhasSelecionadas) {
        for (LinhaProducaoDTO l : linhasSelecionadas) {
            idsLinhasProducaoSelecionadas.add(l.identificadorLinhaProducao);
        }
    }

    private void selecionarTiposNotificao(Collection<TipoNotificacaoErroDTO> selecao) {
        for (TipoNotificacaoErroDTO t : selecao) {
            tiposNotificaoErroSelecionados.add(TipoErroNotificacao.actualValueOf(t.nomeDisplay));
        }
    }

    private void selecionarEstadosNotificacao(Collection<EstadoNotificacaoErroDTO> selecao) {
        for (EstadoNotificacaoErroDTO e : selecao) {
            estadoErroNotificacaosSelecionados.add(EstadoErroNotificacao.actualValueOf(e.estado));
        }
    }

    public <T> void selecionar(Collection<T> selecao) {
        if (selecao.isEmpty())
            return;

        T elem = selecao.iterator().next();

        if (elem instanceof LinhaProducaoDTO) {
            selecionarLinhasDeProducao((Collection) selecao);
            return;
        }
        if (elem instanceof TipoNotificacaoErroDTO) {
            selecionarTiposNotificao((Collection) selecao);
            return;
        }
        if (elem instanceof EstadoNotificacaoErroDTO) {
            selecionarEstadosNotificacao((Collection) selecao);
            return;
        }
    }

    public List<NotificacaoErroDTO> buscarResultado() {
        NotificacaoErroRepository notificacaoErroRepository = repositoryFactory.notificacoesErros();

        List<NotificacaoErro> resultado = notificacaoErroRepository.findAll(idsLinhasProducaoSelecionadas, tiposNotificaoErroSelecionados, estadoErroNotificacaosSelecionados);
        return DTOUtils.toDTOList(resultado);
    }

    public void selecionarEstadoArquivado() {
        estadoErroNotificacaosSelecionados.add(EstadoErroNotificacao.ARQUIVADO);
    }

    public void selecionarEstadoAtivo() {
        estadoErroNotificacaosSelecionados.add(EstadoErroNotificacao.ATIVO);
    }
}

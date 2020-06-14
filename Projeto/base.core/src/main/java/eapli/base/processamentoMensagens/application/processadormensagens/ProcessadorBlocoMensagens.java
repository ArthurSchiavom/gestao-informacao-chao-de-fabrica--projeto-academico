package eapli.base.processamentoMensagens.application.processadormensagens;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.application.ClassTypeUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.processamentoMensagens.compare.ComparatorMensagemPorIdEmissao;
import eapli.base.processamentoMensagens.compare.ComparatorMensagemPorIdEmissaoCodigoTipo;

import java.util.*;

public class ProcessadorBlocoMensagens {

    public NotificacaoErro processar(@NotNull Collection<Mensagem> mensagensRaw) {
        return do_processar(mensagensRaw, null);
    }

    public NotificacaoErro processamentoInativo(@NotNull Collection<Mensagem> mensagensRaw, MensagemID aPartirDesta) {
        return do_processar(mensagensRaw, aPartirDesta);
    }

    private NotificacaoErro do_processar(@NotNull Collection<Mensagem> mensagensRaw, MensagemID aPartirDesta) {
        List<Mensagem> mensagens = new ArrayList<>(mensagensRaw);
        // Garante que o processamento é sempre feito por ordem,
        // pelo que no caso de erros, o processamento apenas processe as mensagens que falharam
        Collections.sort(mensagens, new ComparatorMensagemPorIdEmissaoCodigoTipo());

        // Buscar ordem de Produção
        RepositoryFactory repositoryFactory = PersistenceContext.repositories();
        IdentificadorOrdemProducao identificadorOrdemProducao = encontrarIdOrdem(mensagens);
        OrdemProducaoRepository ordemRepo = repositoryFactory.ordemProducao();
        Optional<OrdemProducao> possivelOrdemProducao = ordemRepo.findByIdentifier(identificadorOrdemProducao);
        IdentificadorLinhaProducao identificadorLinhaProducao = encontrarLinhaProducao(mensagens.get(0));
        if (!possivelOrdemProducao.isPresent()) {
            return new NotificacaoErro(
                    identificadorLinhaProducao,
                    TipoErroNotificacao.ELEMENTOS_INEXISTENTES,
                    mensagens.get(0).mensagemID,
                    repositoryFactory.linhasProducao(),
                    repositoryFactory.mensagem());
        }

        int indexPrimeira = 0;
        if (aPartirDesta != null) {
            for (Mensagem mensagem : mensagens) {
                if (mensagem.identity().equals(aPartirDesta)) {
                    break;
                }
                indexPrimeira++;
            }
        }

        OrdemProducao ordemProducao = possivelOrdemProducao.get();

        // Definir datas da ordem
        Date dataInicio = encontrarDataInicio(mensagens);
        Date dataFim = encontrarDataFim(mensagens);

        ordemProducao.inicioExecucao = dataInicio;
        ordemProducao.fimExecucao = dataFim;


        // Processamento
        for (int i = indexPrimeira; i < mensagens.size(); i++) {
            // Enviar para processar
            Mensagem mensagem = mensagens.get(i);
            ProcessadorMensagem processador = ProcessadorMensagemFactory.processador(mensagem);
            if (processador == null) {
                throw new IllegalStateException("Processador não existente para o tipo de mensagem especificado");
            }
            NotificacaoErro erro = processador.processarMensagem(mensagem, ordemProducao);
            // Gerar erro caso tal exista
            if (erro != null)
                return erro;
        }

        // retornar null
        return null;
    }

    private Date encontrarDataInicio(Collection<Mensagem> mensagens) {
        Date smallest = new Date();

        List<MensagemInicioDeAtividade> mensagensInicioAtividade = ClassTypeUtils.encontrarObjetosDoTipo(mensagens, MensagemInicioDeAtividade.class);
        if (mensagensInicioAtividade.isEmpty()) {
            return new Date(0);
        }

        for (MensagemInicioDeAtividade mensagem : mensagensInicioAtividade) {
            Date aVerificar = mensagem.mensagemID.tempoEmissao.timestamp;
            if (aVerificar.before(smallest)) {
                smallest = aVerificar;
            }
        }
        return smallest;
    }

    private Date encontrarDataFim(Collection<Mensagem> mensagens) {
        Date biggest = new Date(0);

        List<MensagemFimDeAtividade> mensagensFimAtividade = ClassTypeUtils.encontrarObjetosDoTipo(mensagens, MensagemFimDeAtividade.class);
        if (mensagensFimAtividade.isEmpty()) {
            return new Date();
        }

        for (MensagemFimDeAtividade mensagem : mensagensFimAtividade) {
            Date aVerificar = mensagem.mensagemID.tempoEmissao.timestamp;
            if (aVerificar.after(biggest)) {
                biggest = aVerificar;
            }
        }
        return biggest;
    }

    private IdentificadorOrdemProducao encontrarIdOrdem(Collection<Mensagem> mensagens) {
        List<Mensagem> mensagensInicioFim = ClassTypeUtils.encontrarObjetosDoTipo(mensagens, Mensagem.class,
                MensagemInicioDeAtividade.class, MensagemFimDeAtividade.class);
        if (mensagensInicioFim.isEmpty()) {
            return null;
        }

        for (Mensagem mensagem : mensagensInicioFim) {
            IdentificadorOrdemProducao id = mensagem.getIdentificadorOrdemDeProducao();
            if (id != null) {
                return id;
            }
        }
        return null;
    }

    private IdentificadorLinhaProducao encontrarLinhaProducao(Mensagem mensagem) {
        CodigoInternoMaquina codigoInternoMaquina = mensagem.identity().codigoInternoMaquina;
        MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
        Optional<Maquina> possivelMaquina = maquinaRepository.findByIdentifier(codigoInternoMaquina);
        if (!possivelMaquina.isPresent()) {
            return null;
        }

        return possivelMaquina.get().getLinhaProducao();
    }

    @Nullable
    public static List<Mensagem> proximoBloco(MensagemRepository mensagemRepository, IdentificadorLinhaProducao idLinhaProducao) {
        List<Mensagem> mensagens = mensagemRepository.listaMensagensNaoProcessadas();
        if (mensagens.size() == 0) {
            return null;
        }
        Collections.sort(mensagens, new ComparatorMensagemPorIdEmissao());
        int mensagensLength = mensagens.size();

        NotificacaoErroRepository notificacaoErroRepo = PersistenceContext.repositories().notificacoesErros();

        int primeiro = -1;
        int ultimo = -1;

        for (int i = 0; i < mensagensLength; i++) {
            Mensagem mensagem = mensagens.get(i);
            if (mensagem.getIdentificadorLinhaProducao().equals(idLinhaProducao)
                    && mensagem.mensagemID.tipoDeMensagem == TipoDeMensagem.INICIO_DE_ATIVIDADE
                    && !notificacaoErroRepo.mensagemTemErroPorTratar(mensagem.mensagemID)) {
                primeiro = i;
                break;
            }
        }

        if (primeiro == -1) {
            return null;
        }

        for (int i = primeiro + 1; i < mensagensLength; i++) {
            Mensagem mensagem = mensagens.get(i);
            if (mensagem.getIdentificadorLinhaProducao().equals(idLinhaProducao)
                    && mensagem.mensagemID.tipoDeMensagem == TipoDeMensagem.FIM_DE_ATIVIDADE) {
                ultimo = i;
                break;
            }
        }

        if (ultimo == -1) {
            return null;
        }

        List<Mensagem> resultado = new ArrayList<>();
        for (int i = primeiro; i <= ultimo; i++) {
            resultado.add(mensagens.get(i));
        }
        return resultado;
    }
}

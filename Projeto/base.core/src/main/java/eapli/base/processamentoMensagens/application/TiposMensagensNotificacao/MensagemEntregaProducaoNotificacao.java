package eapli.base.processamentoMensagens.application.TiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemEntregaDeProducao;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import java.util.Date;

public class MensagemEntregaProducaoNotificacao implements CriacaoNotificacaoStrategy {
    @Override
    public NotificacaoErro validarMensagem(LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemEntregaDeProducao mensagemEntregaDeProducao=(MensagemEntregaDeProducao)mensagem;
        int quantidadeAProduzir=mensagemEntregaDeProducao.getQuantidadeProduzir();
        Date dataEmissao=mensagemEntregaDeProducao.mensagemID.tempoEmissao.timestamp;
        CodigoDeposito codigoDeposito=mensagemEntregaDeProducao.codigo;

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;


        //DATA ou QUANTIDADE A PRODUZIR
        if (!validacao.validarData(dataEmissao)|| !validacao.validarQuantidade(quantidadeAProduzir))
            return NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemEntregaDeProducao);
        //CODIGO DEPOSITO
        if (codigoDeposito!=null){
            if(!codigoDeposito.validarDadosCodigoDeDeposito(mensagemEntregaDeProducao.codigo.codigo))
                return NotificacaoErro.gerarNotificacaoDeErro(ELEMENTOS_INEXISTENTES,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemEntregaDeProducao);
        }
        return null;
    }
}

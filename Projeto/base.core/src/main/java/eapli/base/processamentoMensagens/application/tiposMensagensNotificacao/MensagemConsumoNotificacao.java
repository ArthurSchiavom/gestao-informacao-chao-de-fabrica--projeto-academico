package eapli.base.processamentoMensagens.application.tiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemConsumo;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import java.util.Date;

public class MensagemConsumoNotificacao implements  CriacaoNotificacaoStrategy {
    @Override
    public NotificacaoErro validarMensagem(LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemConsumo mensagemConsumo=(MensagemConsumo)mensagem;
        double quantidadeAProduzir=mensagemConsumo.getQuantidadeProduzir();
        CodigoDeposito codigoDeposito=mensagemConsumo.codigo;
        Date dataEmissao=mensagemConsumo.mensagemID.tempoEmissao.timestamp;
        Produto produto =validacao.getProdutoPorCodigoUnico(mensagemConsumo.codigoUnico);

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;
        IdentificadorLinhaProducao identificadorLinhaProducao=linhaProducao.identifier;
        NotificacaoErro dados=NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemConsumo);
        NotificacaoErro elementos=NotificacaoErro.gerarNotificacaoDeErro(ELEMENTOS_INEXISTENTES,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemConsumo);

        //DATA ou QUANTIDADE A PRODUZIR
        if (!validacao.validarData(dataEmissao)||!validacao.validarQuantidade(quantidadeAProduzir))
            return dados;
        //CODIGO UNICO
        if (produto==null)
            return elementos;
        //CODIGO DEPOSITO
        if (codigoDeposito!=null){
            if(!codigoDeposito.validarDadosCodigoDeDeposito(mensagemConsumo.codigo.codigo))
                return dados;
        }
        return null;
    }
}

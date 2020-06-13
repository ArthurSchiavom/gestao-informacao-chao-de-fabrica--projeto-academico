package eapli.base.processamentoMensagens.application.tiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemProducao;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;
import java.util.Date;

public class MensagemProducaoNotificacao implements ValidadorMensagem {
    @Override
    public NotificacaoErro validarMensagem( LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemProducao mensagemProducao=(MensagemProducao)mensagem;
        Date dataEmissao=mensagemProducao.mensagemID.tempoEmissao.timestamp;
        int quantidadeAProduzir=mensagemProducao.getQuantidade();
        Produto produto =validacao.getProdutoPorCodigoUnico(mensagemProducao.codigoUnico);

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;
        IdentificadorLinhaProducao identificadorLinhaProducao=linhaProducao.identifier;

        //DATA ou QUANTIDADE A PRODUZIR
        if (!validacao.validarData(dataEmissao)|| !validacao.validarQuantidade(quantidadeAProduzir))
            return NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemProducao);
        //CODIGO UNICO
        if (produto==null)
            return NotificacaoErro.gerarNotificacaoDeErro(ELEMENTOS_INEXISTENTES,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemProducao);
        return null;
    }
}

package eapli.base.processamentoMensagens.application.tiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemEstorno;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import java.util.Date;

public class MensagemEstornoNotificacao implements ValidadorMensagem {

    @Override
    public NotificacaoErro validarMensagem(LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemEstorno mensagemEstorno=(eapli.base.gestaoproducao.gestaomensagens.domain.MensagemEstorno) mensagem;
        double quantidadeAProduzir=mensagemEstorno.getQuantidadeProduzir();
        MateriaPrima materiaPrima=validacao.validarEObterMateriaPrima(mensagemEstorno.idMateriaPrima,mensagem.getIdentificadorOrdemDeProducao());
        CodigoDeposito codigoDeposito=mensagemEstorno.codigoDeposito;
        Date dataEmissao=mensagemEstorno.mensagemID.tempoEmissao.timestamp;

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;

        //DATA
        if (!validacao.validarData(dataEmissao)|| !validacao.validarQuantidade(quantidadeAProduzir))
            return  NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem);
        //Materia Prima
        if (materiaPrima==null)
            return  NotificacaoErro.gerarNotificacaoDeErro(ELEMENTOS_INEXISTENTES,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem);
        //CODIGO DEPOSITO
        if (codigoDeposito!=null){
            if(!codigoDeposito.validarDadosCodigoDeDeposito(mensagemEstorno.codigoDeposito.codigo))
                return  NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem);
        }
      return null;
    }
}

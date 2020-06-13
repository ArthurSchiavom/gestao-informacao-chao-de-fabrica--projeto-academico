package eapli.base.processamentoMensagens.application;

import eapli.base.comum.domain.medicao.QuantidadePositiva;
import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.infrastructure.domain.IllegalDomainValueException;

import java.util.List;

public class OperacoesUsoDeMaquina {

    ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;

    public OperacoesUsoDeMaquina(ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico) {
        this.validacaoParametrosMensagensServico = validacaoParametrosMensagensServico;
    }

    public MateriaPrima obterMateriaPrima(String idMateriaPrima, OrdemProducao ordemProducao){
        Produto produto;
        produto = validacaoParametrosMensagensServico.getProdutoPorCodigoUnico(ordemProducao.produto);
        FichaDeProducao fichaDeProducao = produto.fichaDeProducao;
        MateriaPrima materiaPrima=fichaDeProducao.obterMateriaPrimaPorID(idMateriaPrima);
        return materiaPrima;
    }

    public MovimentoStock adicionarMovimentosDeStock(double quantidadeAProduzir, MateriaPrima materiaPrima, List<MovimentoStock> listaDeMovimentosDeStock, CodigoDeposito codigoDeposito) throws IllegalDomainValueException {
        QuantidadePositiva quantidade;
        QuantidadeDeMateriaPrima quantidadeDeMateriaPrima = null;
        quantidade = QuantidadePositiva.valueOf(quantidadeAProduzir);
        quantidadeDeMateriaPrima = QuantidadeDeMateriaPrima.valueOf(quantidade, materiaPrima);
        return new MovimentoStock(codigoDeposito,quantidadeDeMateriaPrima);
    }
}

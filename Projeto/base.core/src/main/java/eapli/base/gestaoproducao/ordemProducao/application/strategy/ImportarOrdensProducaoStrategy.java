package eapli.base.gestaoproducao.ordemProducao.application.strategy;

import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;

/**
 * Uma interface para aplicar o padrao Strategy
 */
public interface ImportarOrdensProducaoStrategy {

    public ResultadoImportacaoLinhaALinha importarOrdensProducao(String path, boolean substituirSeExistir);

}

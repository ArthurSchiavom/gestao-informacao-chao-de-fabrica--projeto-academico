package eapli.base.gestaoproducao.ordemProducao.application;


import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;
import eapli.base.gestaoproducao.ordemProducao.application.strategy.ImportarOndensProducaoCsv;
import eapli.base.gestaoproducao.ordemProducao.application.strategy.ImportarOrdensProducaoStrategy;

public class ImportarOrdensProducaoController {

    /**
     *  Neste momento só existe uma maneira de importar (ficheiros csv)
     *  no futuro se houver mais maneiras, como foi usado uma Strategy
     *  poderá/deve ser usado uma factory para criar o tipo Strategy
     *  a partir de uma variavel recebida nos parametros da funçao
     */
    public ResultadoImportacaoLinhaALinha importarOrdensProducao(String caminhoParaOFicheiro, boolean substituirSeExistir){
        ImportarOrdensProducaoStrategy imp = new ImportarOndensProducaoCsv();
        return imp.importarOrdensProducao(caminhoParaOFicheiro,substituirSeExistir);
    }

}

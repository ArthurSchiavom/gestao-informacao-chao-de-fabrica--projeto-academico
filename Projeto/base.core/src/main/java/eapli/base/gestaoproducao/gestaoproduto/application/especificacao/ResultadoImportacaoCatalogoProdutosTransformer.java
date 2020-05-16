package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

import java.util.HashMap;
import java.util.Map;

public class ResultadoImportacaoCatalogoProdutosTransformer {
    private int nSucessos;
    private int nFalhas;
    private Map<Integer, String> erros;

    public ResultadoImportacaoCatalogoProdutosTransformer() {
        this.nSucessos = 0;
        this.nFalhas = 0;
        erros = new HashMap<>();
    }

    public void incrementarFalhas() {
        nFalhas++;
    }

    public void incrementarSucessos() {
        nSucessos++;
    }

    public void addFalha(int linha, String erro) {
        erros.put(linha, erro);
    }

    public ResultadoImportacaoCatalogoProdutos gerarDTO() {
        return new ResultadoImportacaoCatalogoProdutos(nSucessos, nFalhas, erros);
    }
}

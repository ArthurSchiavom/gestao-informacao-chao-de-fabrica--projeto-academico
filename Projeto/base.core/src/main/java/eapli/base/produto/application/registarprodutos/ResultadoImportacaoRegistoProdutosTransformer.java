package eapli.base.produto.application.registarprodutos;

import java.util.HashMap;
import java.util.Map;

public class ResultadoImportacaoRegistoProdutosTransformer {
    private int nSucessos;
    private int nFalhas;
    private Map<Integer, String> erros;

    public ResultadoImportacaoRegistoProdutosTransformer() {
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

    public ResultadoImportacaoRegistoProdutos gerarDTO() {
        return new ResultadoImportacaoRegistoProdutos(nSucessos, nFalhas, erros);
    }
}

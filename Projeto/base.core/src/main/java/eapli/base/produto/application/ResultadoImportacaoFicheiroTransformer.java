package eapli.base.produto.application;

import java.util.HashMap;
import java.util.Map;

public class ResultadoImportacaoFicheiroTransformer {
    private int nSucessos;
    private int nFalhas;
    private Map<Integer, String> erros;

    public ResultadoImportacaoFicheiroTransformer() {
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

    public ResultadoImportacaoFicheiro gerarDTO() {
        return new ResultadoImportacaoFicheiro(nSucessos, nFalhas, erros);
    }
}

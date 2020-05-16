package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

import eapli.framework.representations.dto.DTO;

import java.util.Collections;
import java.util.Map;

@DTO
public class ResultadoImportacaoCatalogoProdutos {
    public final int nSucessos;
    public final int nFalhas;
    public final Map<Integer, String> erros;

    public ResultadoImportacaoCatalogoProdutos(int nSucessos, int nFalhas, Map<Integer, String> erros) {
        this.nSucessos = nSucessos;
        this.nFalhas = nFalhas;
        this.erros = Collections.unmodifiableMap(erros);
    }
}

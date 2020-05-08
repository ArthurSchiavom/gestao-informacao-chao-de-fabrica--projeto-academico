package eapli.base.produto.application;

import eapli.framework.representations.dto.DTO;

import java.util.Collections;
import java.util.Map;

@DTO
public class ResultadoImportacaoFicheiro {
    public final int nSucessos;
    public final int nFalhas;
    public final Map<Integer, String> erros;

    public ResultadoImportacaoFicheiro(int nSucessos, int nFalhas, Map<Integer, String> erros) {
        this.nSucessos = nSucessos;
        this.nFalhas = nFalhas;
        this.erros = Collections.unmodifiableMap(erros);
    }
}

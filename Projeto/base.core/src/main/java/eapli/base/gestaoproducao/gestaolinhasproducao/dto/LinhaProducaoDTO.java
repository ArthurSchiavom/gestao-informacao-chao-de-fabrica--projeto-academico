package eapli.base.gestaoproducao.gestaolinhasproducao.dto;

/**
 * Classe DTO sque vai segurar a informação de uma linha de produçao
 */
public class LinhaProducaoDTO {
    public final String identificadorLinhaProducao;

    public LinhaProducaoDTO(String identificadorLinhaProducao) {
        this.identificadorLinhaProducao = identificadorLinhaProducao;
    }

    @Override
    public String toString() {
        return identificadorLinhaProducao;
    }
}

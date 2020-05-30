package eapli.base.gestaoproducao.gestaolinhasproducao.dto;

/**
 * Classe DTO sque vai segurar a informação de uma linha de produçao
 */
public class LinhaProducaoDTO {
    public final String identificadorLinhaProducao;
    public final String estado;

    public LinhaProducaoDTO(String identificadorLinhaProducao,String estado) {
        this.identificadorLinhaProducao = identificadorLinhaProducao;
        this.estado=estado;
    }

    @Override
    public String toString() {
        return identificadorLinhaProducao;
    }
}

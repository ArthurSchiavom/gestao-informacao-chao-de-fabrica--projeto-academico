package eapli.base.gestaoproducao.gestaolinhasproducao.dto;

/**
 * Classe dto sque vai segurar a informação de uma linha de produçao
 */
public class LinhaProducaoDTO {
    public final String identificadorLinhaProducao;
    public final String estado;
    public final String ultimaVezEstadoAtualizado;

    public LinhaProducaoDTO(String identificadorLinhaProducao,String estado, String ultimaVezEstadoAtualizado) {
        this.identificadorLinhaProducao = identificadorLinhaProducao;
        this.estado=estado;
        this.ultimaVezEstadoAtualizado = ultimaVezEstadoAtualizado;
    }

    @Override
    public String toString() {
        return identificadorLinhaProducao + " - " + estado + " - Última vez estado alterado : " + ultimaVezEstadoAtualizado;
    }
}

package eapli.base.gestaoproducao.gestaomaquina.aplication.dto;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;

/**
 * Classe DTO sque vai segurar a informação de uma linha de produçao
 */
public class LinhaProducaoDTO {
    public final IdentificadorLinhaProducao identificadorLinhaProducao;

    public LinhaProducaoDTO(IdentificadorLinhaProducao identificadorLinhaProducao) {
        this.identificadorLinhaProducao = identificadorLinhaProducao;
    }
}

package eapli.base.registarmaquina.aplication;

import eapli.base.gestaolinhasproducao.domain.IdentificadorLinhaProducao;

/**
 * Classe DTO sque vai segurar a informação de uma linha de produçao
 */
public class LinhaProducaoDTO {
    public final IdentificadorLinhaProducao identificadorLinhaProducao;

    public LinhaProducaoDTO(IdentificadorLinhaProducao identificadorLinhaProducao) {
        this.identificadorLinhaProducao = identificadorLinhaProducao;
    }
}

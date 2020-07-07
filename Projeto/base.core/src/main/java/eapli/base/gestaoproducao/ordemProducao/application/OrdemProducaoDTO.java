package eapli.base.gestaoproducao.ordemProducao.application;

import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;

public class OrdemProducaoDTO {

    public final String information;

    public OrdemProducaoDTO(OrdemProducao op) {
        information = op.toString();
    }

}

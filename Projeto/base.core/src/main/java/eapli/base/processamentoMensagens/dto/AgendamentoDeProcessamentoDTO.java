package eapli.base.processamentoMensagens.dto;


import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;

import java.util.Date;

public class AgendamentoDeProcessamentoDTO {
    public final Date dataTempoInicio;
    public final Date dataTempoFinal;
    public final String estado;
    public final IdentificadorLinhaProducao identifier;

    public AgendamentoDeProcessamentoDTO(Date dataTempoInicio, Date dataTempoFinal,String estado,IdentificadorLinhaProducao identificadorLinhaProducao) {
        this.dataTempoInicio = dataTempoInicio;
        this.dataTempoFinal = dataTempoFinal;
        this.estado=estado;
        this.identifier=identificadorLinhaProducao;
    }
}

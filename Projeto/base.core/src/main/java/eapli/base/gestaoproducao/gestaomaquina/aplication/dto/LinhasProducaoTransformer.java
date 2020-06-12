package eapli.base.gestaoproducao.gestaomaquina.aplication.dto;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Aplicado o padrão transformer para passar informação a UI mais facilmente e mantendo as camadas isoladas
 */
public class LinhasProducaoTransformer {

    public static List<LinhaProducaoDTO> gerarLinhasDTO(List<LinhaProducao> linhas){
        List<LinhaProducaoDTO> linhasDTO = new ArrayList<>();

        for(LinhaProducao linha : linhas){
            linhasDTO.add(new LinhaProducaoDTO(linha.identity().toString(),linha.estadoProcessamentoMensagens(), linha.obterUltimaVezAtualizado()));
        }

        return linhasDTO;
    }
}

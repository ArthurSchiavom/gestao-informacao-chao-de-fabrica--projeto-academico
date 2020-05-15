package eapli.base.registarmaquina.aplication;

import eapli.base.gestaolinhasproducao.domain.LinhaProducao;

import java.util.ArrayList;
import java.util.List;

/**
 * Aplicado o padrão transformer para passar informação a UI mais facilmente e mantendo as camadas isoladas
 */
public class LinhasProducaoTransformer {

    public static List<LinhaProducaoDTO> gerarLinhasDTO(List<LinhaProducao> linhas){
        List<LinhaProducaoDTO> linhasDTO = new ArrayList<>();

        for(LinhaProducao linha : linhas){

            linhasDTO.add(new LinhaProducaoDTO(linha.identifier));
        }

        return linhasDTO;
    }
}

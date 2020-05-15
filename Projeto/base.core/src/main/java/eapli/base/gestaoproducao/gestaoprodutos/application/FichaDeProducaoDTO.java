package eapli.base.gestaoproducao.gestaoprodutos.application;

import java.util.Collections;
import java.util.List;

public class FichaDeProducaoDTO {
    public final List<QuantidadeDeMateriaPrimaDTO> materiais;

    public FichaDeProducaoDTO(List<QuantidadeDeMateriaPrimaDTO> materiais) {
        this.materiais = Collections.unmodifiableList(materiais);
    }
}

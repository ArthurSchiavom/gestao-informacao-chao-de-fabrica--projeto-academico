package eapli.base.solicitarConfiguracaoMaquina.application;

import java.util.List;

public class FicheirosConfiguracaoDTO {
    public final List<String> descricaoBreve;

    public FicheirosConfiguracaoDTO(List<String> descricaoBreve) {
        this.descricaoBreve = descricaoBreve;
    }

    public void adicionarConfig(String descricaoBreve){
        this.descricaoBreve.add(descricaoBreve);
    }
}

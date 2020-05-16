package eapli.base.gestaoproducao.gestaoproduto.application.dto;

import eapli.base.gestaoproducao.gestaomateriaprima.application.dto.MateriaPrimaDTO;

public class QuantidadeDeMateriaPrimaDTO {
    public final MateriaPrimaDTO materiaPrimaDTO;
    public final double quantidade;

    public QuantidadeDeMateriaPrimaDTO(MateriaPrimaDTO materiaPrimaDTO, double quantidade) {
        this.materiaPrimaDTO = materiaPrimaDTO;
        this.quantidade = quantidade;
    }
}

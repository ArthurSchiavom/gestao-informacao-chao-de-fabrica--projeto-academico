package eapli.base.produto.application;

import eapli.base.materiaprima.application.MateriaPrimaDTO;

public class QuantidadeDeMateriaPrimaDTO {
    public final MateriaPrimaDTO materiaPrimaDTO;
    public final double quantidade;

    public QuantidadeDeMateriaPrimaDTO(MateriaPrimaDTO materiaPrimaDTO, double quantidade) {
        this.materiaPrimaDTO = materiaPrimaDTO;
        this.quantidade = quantidade;
    }
}

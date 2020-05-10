package eapli.base.definircategoriamaterial.domain;

public class MaterialDTO {
    public final String codigointerno;
    public final String descricao;

    public MaterialDTO(String codigointerno, String descricao) {
        this.codigointerno = codigointerno;
        this.descricao = descricao;
    }
}

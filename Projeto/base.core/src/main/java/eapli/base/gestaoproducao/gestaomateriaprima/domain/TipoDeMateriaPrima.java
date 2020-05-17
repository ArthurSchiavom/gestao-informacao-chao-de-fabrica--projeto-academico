package eapli.base.gestaoproducao.gestaomateriaprima.domain;

public enum TipoDeMateriaPrima {
    MATERIAL("Material"), PRODUTO("Produto");

    public final String name;

    TipoDeMateriaPrima(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

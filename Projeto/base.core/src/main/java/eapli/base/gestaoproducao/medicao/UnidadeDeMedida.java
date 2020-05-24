package eapli.base.gestaoproducao.medicao;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

public enum UnidadeDeMedida {
    UNIDADES("UN", "unidades"),
    KILOGRAMA("Kg", "kilogramas"),
    GRAMA("g", "gramas"),
    METRO("m", "metros"),
    CENTIMETRO("cm", "centímetros"),
    MILIMETRO("mm", "milímetros"),
    LITRO("L", "litros"),
    DECILITRO("dl", "decilitros"),
    CENTILITRO("cl", "centilitros");

    @XmlElement
    public final String abreviatura;
    @XmlElement
    public final String nome;

    UnidadeDeMedida(String abreviatura, String nome) {
        this.abreviatura = abreviatura;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return abreviatura;
    }

    /**
     * Encontra a unidade de medida especificada.
     * <br><b>Nota: o método predefinido valueOf() não pode ser redefinido. Este método deve ser utilizado em vez de valueOf()</b>
     *
     * @param identificador o identificador da unidade, como "metro", "m", "litro" e "L"
     * @return (1) a unidade se tal for encontrada ou (2) null se a unidade não for encontrada
     */
    public static UnidadeDeMedida actualValueOf(String identificador) throws IllegalDomainValueException {
        identificador = identificador.trim();

        for (UnidadeDeMedida unidade : UnidadeDeMedida.values()) {
            if (identificador.equalsIgnoreCase(unidade.abreviatura)) {
                return unidade;
            }
        }

        identificador = identificador.toLowerCase();
        if (identificador.equals("unidades") || identificador.equals("unidade"))
            return UNIDADES;
        if (identificador.equals("kilo") || identificador.equals("kilograma") || identificador.equals("kilogramas"))
            return KILOGRAMA;
        if (identificador.equals("grama") || identificador.equals("gramas"))
            return GRAMA;
        if (identificador.equals("metro") || identificador.equals("metros"))
            return METRO;
        if (identificador.equals("centimetro") || identificador.equals("centimetros")
                || identificador.equals("centímetro") || identificador.equals("centímetros"))
            return CENTIMETRO;
        if (identificador.equals("milimetro") || identificador.equals("milimetros")
                || identificador.equals("milímetro") || identificador.equals("milímetros"))
            return MILIMETRO;
        if (identificador.equals("litro") || identificador.equals("litros"))
            return LITRO;
        if (identificador.equals("decilitro") || identificador.equals("decilitros"))
            return DECILITRO;
        if (identificador.equals("centilitro") || identificador.equals("centilitros"))
            return CENTILITRO;

        throw new IllegalDomainValueException("Unidade de medida inexistente.", IllegalDomainValueType.DOESNT_EXIST);
    }

    //    private static final long serialVersionUID = 1L;
//
//    public final String unidadeDeMedidaValor;
//
//    public UnidadeDeMedida() {
//        unidadeDeMedidaValor = null;
//    }
//
//    public UnidadeDeMedida(String unidadeDeMedida) {
//        this.unidadeDeMedidaValor = unidadeDeMedida;
//    }
//
//    public static UnidadeDeMedida valueOf(String unidadeDeMedida) {
//        return new UnidadeDeMedida(unidadeDeMedida);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof UnidadeDeMedida)) {
//            return false;
//        }
//
//        final UnidadeDeMedida that = (UnidadeDeMedida) o;
//
//        return this.unidadeDeMedidaValor.equalsIgnoreCase(that.unidadeDeMedidaValor);
//    }
//
//    @Override
//    public int hashCode() {
//        return unidadeDeMedidaValor.hashCode();
//    }
//
//    @Override
//    public String toString() {
//        return unidadeDeMedidaValor;
//    }
//
//    @Override
//    public int compareTo(UnidadeDeMedida obj) {
//        return unidadeDeMedidaValor.toLowerCase().compareTo(obj.unidadeDeMedidaValor.toLowerCase());
//    }
}


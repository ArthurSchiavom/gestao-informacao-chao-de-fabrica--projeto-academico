package eapli.base.materiaprima.domain;

import eapli.base.infrastructure.application.DTO;
import eapli.base.materiaprima.application.MateriaPrimaDTO;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MateriaPrima implements ValueObject, Serializable, DTO<MateriaPrimaDTO> {

    private static final long serialVersionUID = 1L;

    /* O Hibernate falha se for final */
    public TipoDeMateriaPrima tipoDeMateriaPrima;
    /* O Hibernate falha se for final */
    public String idMateria;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MateriaPrima() {
        tipoDeMateriaPrima = null;
        idMateria = null;
    }

    private MateriaPrima(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) {
        this.tipoDeMateriaPrima = tipoDeMateriaPrima;
        this.idMateria = idMateria;
    }

    /** O Hibernate falha sem este método */
    public TipoDeMateriaPrima getTipoDeMateriaPrima() {
        return tipoDeMateriaPrima;
    }

    /** O Hibernate falha sem este método */
    public void setTipoDeMateriaPrima(TipoDeMateriaPrima tipoDeMateriaPrima) {
        this.tipoDeMateriaPrima = tipoDeMateriaPrima;
    }

    /** O Hibernate falha sem este método */
    public String getIdMateria() {
        return idMateria;
    }

    /** O Hibernate falha sem este método */
    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public MateriaPrima valueOf(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) {
        return new MateriaPrima(tipoDeMateriaPrima, idMateria);
    }

    @Override
    public MateriaPrimaDTO toDTO() {
        return new MateriaPrimaDTO(tipoDeMateriaPrima.name, idMateria);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MateriaPrima)) {
            return false;
        }

        final MateriaPrima that = (MateriaPrima) o;

        return this.idMateria.equals(that.idMateria) && tipoDeMateriaPrima.equals(that.tipoDeMateriaPrima);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMateria, tipoDeMateriaPrima);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", tipoDeMateriaPrima, idMateria);
    }
}


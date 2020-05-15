package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.gestaoproducao.gestaomateriaprima.application.MateriaPrimaDTO;
import eapli.base.infrastructure.application.HasDTO;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MateriaPrima implements ValueObject, Serializable, HasDTO<MateriaPrimaDTO> {

    private static final long serialVersionUID = 1L;

    /* O hibernate falha se for final. Isto acontece a todos os valores de Embeddables que
     * pertençam outro Embeddable, que por sua vez façam parte de uma @ElementCollection + @CollectionTable
     * */
    public TipoDeMateriaPrima tipoDeMateriaPrima;
    /* O Hibernate falha se for final */
    public String idMateria;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    protected MateriaPrima() {
        tipoDeMateriaPrima = null;
        idMateria = null;
    }

    private MateriaPrima(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) {
        this.tipoDeMateriaPrima = tipoDeMateriaPrima;
        this.idMateria = idMateria;
    }

    public static MateriaPrima valueOf(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) {
        return new MateriaPrima(tipoDeMateriaPrima, idMateria);
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


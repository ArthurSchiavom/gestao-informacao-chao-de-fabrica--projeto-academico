package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.gestaoproducao.gestaomateriaprima.application.dto.MateriaPrimaDTO;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MateriaPrima implements ValueObject, Serializable, ConvertableToDTO<MateriaPrimaDTO> {

    private static final long serialVersionUID = 1L;

    /* O hibernate falha se for final. Isto acontece a todos os valores de Embeddables que
     * pertençam outro Embeddable, que por sua vez façam parte de uma @ElementCollection + @CollectionTable
     * */
    @XmlElement
    public TipoDeMateriaPrima tipoDeMateriaPrima;
    /* O Hibernate falha se for final */
    @XmlElement
    public String idMateria;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    protected MateriaPrima() {
        tipoDeMateriaPrima = null;
        idMateria = null;
    }

    private MateriaPrima(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) throws IllegalDomainValueException {
        if (tipoDeMateriaPrima == null || idMateria == null) {
            throw new IllegalArgumentException("Nenhum valor pode ser nulo");
        }
        if (idMateria.isEmpty()) {
            throw new IllegalDomainValueException("O ID da matéria deve ser especificado", IllegalDomainValueType.ILLEGAL_VALUE);
        }

        this.tipoDeMateriaPrima = tipoDeMateriaPrima;
        this.idMateria = idMateria;
    }

    /* O hibernate falha se não tiver getters e setters públicos */
    @XmlTransient
    public TipoDeMateriaPrima getTipoDeMateriaPrima() {
        return tipoDeMateriaPrima;
    }

    /* O hibernate falha se não tiver getters e setters públicos */
    public void setTipoDeMateriaPrima(TipoDeMateriaPrima tipoDeMateriaPrima) {
        this.tipoDeMateriaPrima = tipoDeMateriaPrima;
    }

    /* O hibernate falha se não tiver getters e setters públicos */
    @XmlTransient
    public String getIdMateria() {
        return idMateria;
    }

    /* O hibernate falha se não tiver getters e setters públicos */
    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public static MateriaPrima valueOf(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) throws IllegalDomainValueException {
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
        return String.format("%s (%s)", idMateria, tipoDeMateriaPrima.toString());
    }
}


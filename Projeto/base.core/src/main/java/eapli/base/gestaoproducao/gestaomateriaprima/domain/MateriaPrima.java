package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaomateriaprima.application.dto.MateriaPrimaDTO;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.infrastructure.application.HasDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.repositories.DomainRepository;

import javax.annotation.Nullable;
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

    public static MateriaPrima valueOf(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria) throws IllegalDomainValueException {
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
        return String.format("%s (%s)", idMateria, tipoDeMateriaPrima.toString());
    }
}


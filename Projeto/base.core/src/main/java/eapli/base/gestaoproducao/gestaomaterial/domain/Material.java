
package eapli.base.gestaoproducao.gestaomaterial.domain;

import eapli.base.gestaoproducao.gestaomaterial.application.dto.MaterialDTO;
import eapli.base.comum.domain.medicao.UnidadeDeMedida;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class Material implements AggregateRoot<CodigoInternoMaterial>, ConvertableToDTO<MaterialDTO> {

	@Version
	private Long version;

	@EmbeddedId
	@XmlAttribute
	private CodigoInternoMaterial codigoInternoMaterial;

	@XmlElement
	private UnidadeDeMedida unidadeDeMedida;
	@XmlElement
	private String descricao;
	@XmlElement
	private FichaTecnicaPDF fichaTecnicaPDF;
	@XmlElement
	private CodigoAlfanumericoCategoria categoria;

	public Material() {
	}

	/**
	 * @param descricaoMaterial     Descricao do material
	 * @param codigoInternoMaterial Identificador com que queremos identificar o material
	 * @param categoria             Categoria onde se encaixa o material
	 * @param unidadeDeMedida       Unidade de medida usada para medir o material
	 */
	public Material(String descricaoMaterial, CodigoInternoMaterial codigoInternoMaterial, CodigoAlfanumericoCategoria categoria, UnidadeDeMedida unidadeDeMedida, FichaTecnicaPDF fichaTecnicaPDF) {
		if (!podeGerarMaterial(codigoInternoMaterial, unidadeDeMedida, descricaoMaterial, categoria, fichaTecnicaPDF))
			throw new IllegalArgumentException("Nenhum valor do produto pode ser nulo");
		this.codigoInternoMaterial = codigoInternoMaterial;
		this.unidadeDeMedida = unidadeDeMedida;
		this.descricao = descricaoMaterial;
		this.categoria = categoria;
		this.fichaTecnicaPDF = fichaTecnicaPDF;
	}

	public static String identityAttributeName() {
		return Reflection.retrieveAttributeName(Material.class, CodigoInternoMaterial.class);
	}

	private boolean podeGerarMaterial(CodigoInternoMaterial codigoInternoMaterial, UnidadeDeMedida unidadeDeMedida, String descricao, CodigoAlfanumericoCategoria categoria, FichaTecnicaPDF fichaTecnicaPDF) {
		return codigoInternoMaterial != null && categoria != null && unidadeDeMedida != null && descricao != null && fichaTecnicaPDF != null;
	}

	@Override
	public boolean equals(final Object o) {
		return DomainEntities.areEqual(this, o);
	}

	@Override
	public int hashCode() {
		return DomainEntities.hashCode(this);
	}

	@Override
	public boolean sameAs(final Object other) {
		return DomainEntities.areEqual(this, other);
	}

	@Override
	public CodigoInternoMaterial identity() {
		return this.codigoInternoMaterial;
	}

	@Override
	public String toString() {
		return "Material{" +
				"version=" + version +
				", codigoInternoMaterial=" + codigoInternoMaterial +
				", unidadeDeMedida=" + unidadeDeMedida +
				", descricao='" + descricao + '\'' +
				", categoria=" + categoria +
				'}';
	}

	@Override
	public MaterialDTO toDTO() {
		return new MaterialDTO(codigoInternoMaterial.codigoInternoValor, descricao, unidadeDeMedida.abreviatura, fichaTecnicaPDF.fichaTecnica, fichaTecnicaPDF.path);
	}
}
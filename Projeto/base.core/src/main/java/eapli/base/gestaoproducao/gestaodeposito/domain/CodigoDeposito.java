package eapli.base.gestaoproducao.gestaodeposito.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.StringPredicates;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

@Embeddable
public class CodigoDeposito implements ValueObject, Comparable<CodigoDeposito> {
	private static final long serialVersionUID = 1L;

	@XmlValue
	private final String codigo;

	public CodigoDeposito(String codigo) {
		if(codigo == null || codigo.trim().isEmpty() || !StringPredicates.isSingleWord(codigo)) {
			throw new IllegalArgumentException("Codigo têm que ser um valor alfanumérico sem espaços " +
					"e não pode estar vazio");
		}
		this.codigo = codigo;
	}

	public CodigoDeposito() {
		//FOR ORM
		this.codigo = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CodigoDeposito that = (CodigoDeposito) o;
		return codigo.equals(that.codigo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public String toString() {
		return "CodigoDeposito{" +
				"codigo='" + codigo + '\'' +
				'}';
	}

	@Override
	public int compareTo(CodigoDeposito o) {
		throw new UnsupportedOperationException("Não há nada especificado sobre como comparar CodigoDepositos");
	}
}
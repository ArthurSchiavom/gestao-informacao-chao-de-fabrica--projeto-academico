package eapli.base.gestaoproducao.gestaolinhaproducao.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class IdentificadorLinhaProducao implements ValueObject, Comparable<IdentificadorLinhaProducao> {
	private static final long serialVersionUID = 1L;

	private final String identifier;

	public IdentificadorLinhaProducao(String identifier) {
		if(identifier == null || identifier.trim().isEmpty()) {
			throw new IllegalArgumentException("Production Line Identifier can't be null");
		}
		this.identifier = identifier;
	}

	public IdentificadorLinhaProducao() {
		//FOR ORM
		this.identifier = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IdentificadorLinhaProducao that = (IdentificadorLinhaProducao) o;
		return identifier.equals(that.identifier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier);
	}

	@Override
	public String toString() {
		return "IdentificadorLinhaProducao{" +
				"identifier='" + identifier + '\'' +
				'}';
	}

	@Override
	public int compareTo(IdentificadorLinhaProducao o) {
		throw new UnsupportedOperationException("Production Line identifier comparator is not implemented");
	}
}
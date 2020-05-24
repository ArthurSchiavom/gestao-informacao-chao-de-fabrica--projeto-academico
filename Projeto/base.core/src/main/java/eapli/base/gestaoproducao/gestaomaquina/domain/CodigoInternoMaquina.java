package eapli.base.gestaoproducao.gestaomaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

@Embeddable
public class CodigoInternoMaquina implements ValueObject, Comparable<CodigoInternoMaquina> {

	private static final long serialVersionUID = 1L;

	@XmlValue
	private String codigoInterno;

	protected CodigoInternoMaquina() {
	}

	public CodigoInternoMaquina(String codigoInterno) throws IllegalArgumentException {
		if (codigoInterno == null || codigoInterno.trim().isEmpty()) {
			throw new IllegalArgumentException("Código interno não válido");
		}
		this.codigoInterno = codigoInterno;

	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CodigoInternoMaquina that = (CodigoInternoMaquina) o;
		return codigoInterno.equals(that.codigoInterno);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoInterno);
	}

	@Override
	public String toString() {
		return "CodigoInternoMaquina{" +
				"codigoInterno='" + codigoInterno + '\'' +
				'}';
	}

	@Override
	public int compareTo(CodigoInternoMaquina obj) {
		return this.codigoInterno.compareTo(obj.codigoInterno);
	}
}
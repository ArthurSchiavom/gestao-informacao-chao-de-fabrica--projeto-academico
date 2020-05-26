package eapli.base.gestaoproducao.gestaomensagens.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class TimestampEmissao implements ValueObject, Comparable<TimestampEmissao> {
	private static final long serialVersionUID = 1L;

	private LocalDateTime timestamp;

	public TimestampEmissao(LocalDateTime timestamp) {
		if(timestamp == null) { //TODO adicionar mais codigo de verificaçao
			throw new IllegalArgumentException();
		}
		this.timestamp = timestamp;
	}

	public TimestampEmissao() {
		//FOR ORM
		this.timestamp = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TimestampEmissao that = (TimestampEmissao) o;
		return timestamp.equals(that.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(timestamp);
	}

	@Override
	public String toString() {
		return timestamp.toString();
	}

	@Override
	public int compareTo(TimestampEmissao obj) {
		return timestamp.compareTo(obj.timestamp);
	}
}
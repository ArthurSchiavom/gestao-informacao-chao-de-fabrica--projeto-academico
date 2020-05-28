package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

//TODO THIS IS BAREBONES CLASS NEEDS PROPER IMPLEMENTATION
public class Mensagem implements AggregateRoot<Long> {

	@Version
	private Long version;

	@Id
	@GeneratedValue
	private Long pk;

	public static String identityAttributeName() {
		return "id";
	}

	@Override
	public boolean sameAs(Object other) {
		return false;
	}

	@Override
	public Long identity() {
		return null;
	}
}

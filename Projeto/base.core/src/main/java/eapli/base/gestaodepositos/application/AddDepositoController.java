package eapli.base.gestaodepositos.application;

import eapli.base.gestaodepositos.domain.Deposito;
import eapli.base.gestaodepositos.repository.DepositoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class AddDepositoController {
	//Todo add authz maybe
	private final DepositoRepository repository = PersistenceContext.repositories().depositos();

	public Deposito registarDeposito(final String codigo, final String descricao) {
		final Deposito deposito = new Deposito(codigo, descricao);
		return this.repository.save(deposito);
	}
}

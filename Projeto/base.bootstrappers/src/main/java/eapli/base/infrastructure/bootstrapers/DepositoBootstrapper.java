package eapli.base.infrastructure.bootstrapers;

import eapli.base.gestaoproducao.gestaodeposito.application.especificacao.EspecificarDepositoController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DepositoBootstrapper implements Action {
	private static final Logger LOGGER = LogManager.getLogger(LinhaProducaoBootstrapper.class);

	private final EspecificarDepositoController controller = new EspecificarDepositoController();
	@Override
	public boolean execute() {
		registar("DEP001", "Deposito de Madeira");
		registar("DEP002", "Deposito de Ferro");
		registar("DEP003", "Deposito de Aço");
		return true;
	}

	private void registar(String codigo, String descricao) {
		try {
			controller.registarDeposito(codigo, descricao);
		} catch (final IntegrityViolationException | ConcurrencyException e) {
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigo);
			LOGGER.trace("Assuming existing record", e);
		}
	}
}

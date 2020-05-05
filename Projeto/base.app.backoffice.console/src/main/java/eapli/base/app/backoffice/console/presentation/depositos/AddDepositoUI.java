package eapli.base.app.backoffice.console.presentation.depositos;

import eapli.base.gestaodepositos.application.AddDepositoController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddDepositoUI extends AbstractUI {
	private static final Logger LOGGER = LogManager.getLogger(AddDepositoController.class);
	private final AddDepositoController theController = new AddDepositoController();

	@Override
	protected boolean doShow() {
		final String codigo = Console.readNonEmptyLine("Código Alfanumérico do Depósito",
				"Código não pode ser vazio");
		final String descricao = Console.readLine("Descrição do Depósito");
		try {
			this.theController.registarDeposito(codigo, descricao);
		} catch(IllegalArgumentException e) {
			LOGGER.warn("Depósito introduzido é inválido");
		} catch(final IntegrityViolationException | ConcurrencyException e) {
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigo);
			LOGGER.trace("Assuming existing record", e);
		}
		return false;
	}

	@Override
	public String headline() {
		return "Adicionar Depósito";
	}
}

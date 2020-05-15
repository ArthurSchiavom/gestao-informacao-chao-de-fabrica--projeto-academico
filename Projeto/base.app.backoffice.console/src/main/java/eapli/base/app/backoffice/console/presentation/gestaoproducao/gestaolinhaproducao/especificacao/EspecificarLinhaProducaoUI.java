package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaolinhaproducao.especificacao;

import eapli.base.gestaoproducao.gestaolinhasproducao.application.AddLinhaProducaoController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Menu para adicionar uma nova linha de produção
 */
public class EspecificarLinhaProducaoUI extends AbstractUI {
	private static final Logger LOGGER = LogManager.getLogger(EspecificarLinhaProducaoUI.class);
	private final AddLinhaProducaoController theController = new AddLinhaProducaoController();

	@Override
	public boolean doShow() {
		final String identifier = Console.readNonEmptyLine("Identificador",
				"Identificador não pode ser vazio");
		//TODO verificar o retorno aqui
		try {
			this.theController.registarLinhaProducao(identifier);
		} catch (final IllegalArgumentException ex) {
			LOGGER.warn("Identificador inválido");
		} catch (final IntegrityViolationException | ConcurrencyException e) {
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", identifier);
			LOGGER.trace("Assuming existing record", e);
		}
		return false;
	}

	@Override
	public String headline() {
		return "Adicionar Linha de Produção";
	}
}

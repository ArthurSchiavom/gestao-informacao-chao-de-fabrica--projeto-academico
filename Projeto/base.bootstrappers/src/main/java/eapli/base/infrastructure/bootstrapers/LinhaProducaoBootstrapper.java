package eapli.base.infrastructure.bootstrapers;

import eapli.base.gestaolinhasproducao.application.AddLinhaProducaoController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinhaProducaoBootstrapper implements Action {
	private static final Logger LOGGER = LogManager.getLogger(LinhaProducaoBootstrapper.class);

	private final AddLinhaProducaoController controller = new AddLinhaProducaoController();

	@Override
	public boolean execute() {
		registar("Linha de Produção 1");
		registar("Linha de Produção 2");
		return true;
	}

	private void registar(final String identifier) {
		try {
			controller.registarLinhaProducao(identifier);
			LOGGER.info(identifier);
		} catch (final IntegrityViolationException | ConcurrencyException e) {
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", identifier);
			LOGGER.trace("Assuming existing record", e);
		}
	}
}

package eapli.base.app.backoffice.console.presentation.authz;

import eapli.base.gestaolinhasproducao.application.AddLinhaProducaoController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 * Menu para adicionar uma nova linha de produção
 */
public class AddLinhaProducaoUI extends AbstractUI {

	private final AddLinhaProducaoController theController = new AddLinhaProducaoController();

	@Override
	protected boolean doShow() {
		final String identifier = Console.readNonEmptyLine("Identificador",
				"Identificador não pode ser vazio");
		//TODO verificar o retorno aqui
		try {
			this.theController.registarLinhaProducao(identifier);
		} catch (IllegalArgumentException ex) {
			System.out.println("Identificador inválido");
		}
		return false;
	}

	@Override
	public String headline() {
		return "Adicionar Linha de Produção";
	}
}

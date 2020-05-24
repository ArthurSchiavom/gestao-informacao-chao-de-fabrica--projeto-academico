package eapli.base.gestaoproducao.exportacao.application;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ExportacaoFicheiroXMLChaoDeFabricaUI extends AbstractUI {
	private final ExportacaoFicheiroXMLChaoFabricaController controller = new ExportacaoFicheiroXMLChaoFabricaController();

	@Override
	protected boolean doShow() {
		final String fileName = Console.readNonEmptyLine("Introduza o nome do ficheiro XML a ser exportado",
				"Nome do ficheiro XML não pode ser vazio");
		controller.export(fileName);
		System.out.println("A Exportação correu com sucesso");
		return true;
	}

	@Override
	public String headline() {
		return "Exportação Ficheiro XML";
	}
}

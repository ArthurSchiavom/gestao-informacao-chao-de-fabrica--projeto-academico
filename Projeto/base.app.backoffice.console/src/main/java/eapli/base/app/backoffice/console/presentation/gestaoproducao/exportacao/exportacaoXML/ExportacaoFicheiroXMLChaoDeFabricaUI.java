package eapli.base.app.backoffice.console.presentation.gestaoproducao.exportacao.exportacaoXML;

import eapli.base.gestaoproducao.exportacao.application.xml.ExportacaoFicheiroXMLChaoFabricaController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.Date;

public class ExportacaoFicheiroXMLChaoDeFabricaUI extends AbstractUI {
	private final ExportacaoFicheiroXMLChaoFabricaController controller = new ExportacaoFicheiroXMLChaoFabricaController();

	@Override
	protected boolean doShow() {
		final String path = Console.readNonEmptyLine("Introduza o nome do ficheiro XML a ser exportado",
				"Nome do ficheiro XML não pode ser vazio");
		final boolean exportarTempoProd = Console.readBoolean("Pretende exportar tempos de produção? (S/N)");
		final boolean exportarDesvios = Console.readBoolean("Pretende exportar desvios? (S/N)");
		final Date dataAFiltrar = Console.readDate(
				"Escolher data a partir da qual todos os registos que conteem uma data de emissão vão ser " +
						"exportados (dd-MM-yyyy)", "dd-MM-yyyy");
		final boolean exportacaoTeveSucesso = controller.exportar(path, exportarTempoProd, exportarDesvios, dataAFiltrar);
		if (exportacaoTeveSucesso) {
			System.out.println("A Exportação correu com sucesso");
		} else {
			System.out.println("Houve problemas a exportar o ficheiro");
		}
		return true;
	}

	@Override
	public String headline() {
		return "Exportação Ficheiro XML";
	}
}

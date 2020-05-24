package eapli.base.gestaoproducao.exportacao.application;

import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;
import eapli.base.gestaoproducao.exportacao.domain.ExportadorStrategy;
import eapli.base.gestaoproducao.exportacao.domain.ExportadorXMLStrategy;

public class ExportacaoFicheiroXMLChaoFabricaController {
	private final ExportadorStrategy exportador = new ExportadorXMLStrategy();

	public boolean export(String fileName) {
		ChaoDeFabrica chaoDeFabrica = exportador.loadAllData();
		return exportador.export(fileName, chaoDeFabrica);
	}
}

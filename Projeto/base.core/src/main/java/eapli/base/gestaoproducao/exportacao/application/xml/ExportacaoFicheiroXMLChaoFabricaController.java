package eapli.base.gestaoproducao.exportacao.application.xml;

import eapli.base.gestaoproducao.exportacao.application.FormatoExportacao;
import eapli.base.gestaoproducao.exportacao.application.ServicoExportacao;

import java.util.Date;

public class ExportacaoFicheiroXMLChaoFabricaController {
	private final ServicoExportacao servicoExportacao = new ServicoExportacao();

	public boolean exportar(String path, boolean exportarTempoProd, boolean exportarDesvios, Date dataAFiltrar) {
		return servicoExportacao.exportar(path, exportarTempoProd, exportarDesvios,
				dataAFiltrar, FormatoExportacao.XML_JAXB);
	}
}

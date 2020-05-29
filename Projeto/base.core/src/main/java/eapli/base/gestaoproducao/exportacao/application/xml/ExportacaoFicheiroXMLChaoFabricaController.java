package eapli.base.gestaoproducao.exportacao.application.xml;

import eapli.base.gestaoproducao.exportacao.application.FormatoExportacao;
import eapli.base.gestaoproducao.exportacao.domain.ServicoExportacao;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;

import java.util.Date;

public class ExportacaoFicheiroXMLChaoFabricaController {
	private final RepositoryFactory repoFact = PersistenceContext.repositories();
	private final ServicoExportacao servicoExportacao = new ServicoExportacao(repoFact);

	public boolean exportar(String path, boolean exportarTempoProd, boolean exportarDesvios, Date dataAFiltrar) {
		return servicoExportacao.exportar(path, exportarTempoProd, exportarDesvios,
				dataAFiltrar, FormatoExportacao.XML_JAXB);
	}
}

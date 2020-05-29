package eapli.base.gestaoproducao.exportacao.domain;

import eapli.base.gestaoproducao.exportacao.application.ExportadorFactory;
import eapli.base.gestaoproducao.exportacao.application.FormatoExportacao;
import eapli.base.gestaoproducao.exportacao.application.PathAdapter;
import eapli.base.gestaoproducao.exportacao.application.xml.ExportadorXMLJABX;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ServicoExportacao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportadorXMLJABX.class);
	private final RepositoryFactory repoFact;

	public ServicoExportacao(RepositoryFactory repoFact) {
		this.repoFact = repoFact;
	}

	/**
	 * Exporta os dados da base de dados para um formato escolhido
	 *
	 * @param pathStr           o caminho para o ficheiro para exportar(não precisa de ser indicada a extensão)
	 * @param exportarTempoProd se é para exportar os tempos de produção
	 * @param exportarDesvios   se é para exportar os desvios
	 * @param dataAFiltrar      a data pela qual começar a filtrar as coisas
	 * @param formato           o formato que pretendemos exportar
	 * @return verdadeiro se a exportação correr bem <br>
	 * falso se não correr bem
	 */
	public boolean exportar(String pathStr, boolean exportarTempoProd, boolean exportarDesvios, Date dataAFiltrar,
	                        FormatoExportacao formato) {
		Exportador exportador = new ExportadorFactory().getEstrategiaExportacao(formato);
		String extensao = exportador.fileExtension();
		File path = null;

		try {
			path = PathAdapter.makeFileFromPathString(pathStr+extensao);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		ChaoDeFabrica.Builder builder = new ChaoDeFabrica.Builder(repoFact);
		builder.loadCategorias()
				.loadDepositos()
				.loadFichasProducao()
				.loadLinhasProducao()
				.loadMaquinas()
				.loadMateriais()
				.loadProdutos()
				.loadNotificacaoesErros()
				.loadOrdensProducao(dataAFiltrar);
		if (exportarTempoProd) {
			builder.loadTemposDeProducao(dataAFiltrar);
		}
		if (exportarDesvios) {
			builder.loadDesvios(dataAFiltrar);
		}
		ChaoDeFabrica chaoDeFabrica = builder.build();

		return exportador.export(path, chaoDeFabrica);
	}
}

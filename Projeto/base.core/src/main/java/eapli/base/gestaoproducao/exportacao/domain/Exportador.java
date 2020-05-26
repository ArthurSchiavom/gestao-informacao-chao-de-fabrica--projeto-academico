package eapli.base.gestaoproducao.exportacao.domain;

import java.io.File;

public interface Exportador {
	/**
	 * Exporta o chao de fabrica
	 *
	 * @param chaoDeFabrica o chao de fabrica com os dados todos que precisamos
	 * @param file          o ficheiro para onde estamos a exportar
	 * @return true if the export was successful <br>
	 * false if it couldn't export successfully
	 */
	boolean export(File file, ChaoDeFabrica chaoDeFabrica);

	/**
	 * @return a extensão que o exportador gera ficheiros
	 */
	String fileExtension();
}

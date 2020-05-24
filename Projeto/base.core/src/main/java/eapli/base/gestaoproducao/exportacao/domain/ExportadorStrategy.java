package eapli.base.gestaoproducao.exportacao.domain;

import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;

public interface ExportadorStrategy {

	/**
	 * Loads all the data in Chao de Fabrica to one object
	 *
	 * @return an object with all the date of the Chao de Fabrica
	 */
	ChaoDeFabrica loadAllData();

	/**
	 * Exports o chao de
	 *
	 * @param chaoDeFabrica o chao de fabrica com os dados todos que precisamos
	 * @param fileName      o nome do ficheiro para onde estamos a exportar
	 * @return true if the export was successful <br>
	 * false if it couldn't export successfully
	 */
	boolean export(String fileName, ChaoDeFabrica chaoDeFabrica);
}

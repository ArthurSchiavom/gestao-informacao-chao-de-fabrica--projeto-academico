package eapli.base.gestaoproducao.exportacao.application;

import eapli.base.gestaoproducao.exportacao.application.xml.ExportadorXMLJABX;
import eapli.base.gestaoproducao.exportacao.domain.Exportador;

public class ExportadorFactory {
	private final Exportador xmlJaxb = new ExportadorXMLJABX();

	public Exportador getEstrategiaExportacao(FormatoExportacao formato) {
		switch(formato) {
			case XML_JAXB: return xmlJaxb;
			default: throw new UnsupportedOperationException("Formato exportação inválido");
		}
	}
}

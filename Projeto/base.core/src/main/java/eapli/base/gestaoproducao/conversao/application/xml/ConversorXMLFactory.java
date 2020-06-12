package eapli.base.gestaoproducao.conversao.application.xml;

import eapli.base.gestaoproducao.conversao.application.FormatoConversao;
import eapli.base.gestaoproducao.conversao.application.xml.html.ConversorXMLtoHTMLA;
import eapli.base.gestaoproducao.conversao.application.xml.json.ConversorXMLtoJSONA;
import eapli.base.gestaoproducao.conversao.application.xml.txt.ConversorXMLtoTXTA;
import eapli.base.gestaoproducao.conversao.domain.xml.ConversorXML;

class ConversorXMLFactory {
	ConversorXML getEstrategiaConversao(FormatoConversao formato) {
		switch(formato) {
			case HTML_A:
				return new ConversorXMLtoHTMLA();
			case TXT_A:
				return new ConversorXMLtoTXTA();
			case JSON_A:
				return new ConversorXMLtoJSONA();
			default:
				throw new IllegalArgumentException("Formato inválido passado");
		}
	}
}

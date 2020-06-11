package eapli.base.gestaoproducao.conversao.application.xml.json;

import eapli.base.gestaoproducao.conversao.application.xml.ConversorXMLtoJSON;

import java.io.InputStream;

public class ConversorXMLtoJSONA extends ConversorXMLtoJSON {
	@Override
	protected InputStream carregarXSLT() {
		return ConversorXMLtoJSONA.class.getResourceAsStream("JSON_A.xsl");
	}
}

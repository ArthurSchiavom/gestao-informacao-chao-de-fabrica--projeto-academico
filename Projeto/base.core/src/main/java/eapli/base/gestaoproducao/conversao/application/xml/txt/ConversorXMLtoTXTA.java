package eapli.base.gestaoproducao.conversao.application.xml.txt;

import eapli.base.gestaoproducao.conversao.application.xml.ConversorXMLtoTXT;

import java.io.InputStream;

public class ConversorXMLtoTXTA extends ConversorXMLtoTXT {
	@Override
	protected InputStream carregarXSLT() {
		return ConversorXMLtoTXTA.class.getResourceAsStream("TXT_A.xsl");
	}
}

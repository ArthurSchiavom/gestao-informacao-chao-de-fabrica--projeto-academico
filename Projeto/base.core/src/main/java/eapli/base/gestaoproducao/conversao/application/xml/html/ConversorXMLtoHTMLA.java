package eapli.base.gestaoproducao.conversao.application.xml.html;

import eapli.base.gestaoproducao.conversao.application.xml.ConversorXMLtoHTML;

import java.io.InputStream;

public class ConversorXMLtoHTMLA extends ConversorXMLtoHTML {
	@Override
	protected InputStream carregarXSLT() {
		return ConversorXMLtoHTMLA.class.getResourceAsStream("HTML_A.xsl");
	}
}

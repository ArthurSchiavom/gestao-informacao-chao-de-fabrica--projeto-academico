package eapli.base.gestaoproducao.conversao.application.xml;

import eapli.base.gestaoproducao.conversao.application.FormatoConversao;
import eapli.base.gestaoproducao.conversao.domain.xml.ConversorXML;

class ConversorXMLFactory {
	private static final String EXTENSAO_HTML = ".html";
	private static final String EXTENSAO_JSON = ".json";
	private static final String EXTENSAO_TXT = ".txt";

	/**
	 * Cria uma estratégia adequada para converter um ficheiro de acordo com o formato pretendido
	 * @param formato o formato pretendido para a conversão
	 * @return uma estratégia de conversão
	 */
	static ConversorXML getEstrategiaConversao(FormatoConversao formato) {
		switch(formato) {
			case HTML_A:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_HTML, "HTML_A.xsl");
			case HTML_B:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_HTML, "HTML_B.xslt");
			case HTML_C:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_HTML, "HTML_C.xsl");
			case TXT_A:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_TXT, "TXT_A.xsl");
			case TXT_B:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_TXT, "TXT_B.xsl");
			case TXT_C:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_TXT, "TXT_C.xsl");
			case JSON_A:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_JSON, "JSON_A.xsl");
			case JSON_B:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_JSON, "JSON_B.xslt");
			case JSON_C:
				return new EstrategiaConversaoXMLcomUsoXSLT(EXTENSAO_JSON, "JSON_C.xslt");
			default:
				throw new IllegalArgumentException("Formato inválido passado");
		}
	}
}

package eapli.base.gestaoproducao.conversao.domain.xml;

import java.io.File;

public interface ConversorXML {
	/**
	 * Converte um ficheiro de XML para um determinado formato através do uso de
	 * XSLT e XPATH
	 * @param file o ficheiro que pretendemos converter
	 * @return verdadeiro se a conversão correu bem ou falso se a conversão falhou
	 */
	boolean converter(File file);
}

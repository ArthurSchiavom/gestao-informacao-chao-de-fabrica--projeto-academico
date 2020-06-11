package eapli.base.gestaoproducao.conversao.application.xml;

import eapli.base.gestaoproducao.conversao.application.FormatoConversao;
import eapli.base.gestaoproducao.conversao.domain.xml.ConversorXML;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ConversaoFicheiroXMLController {
	private File ficheiroXML;
	private List<FormatoConversao> formatosConversao;
	private FormatoConversao formato;

	/**
	 * @return O diretorio em que o programa está a correr
	 */
	public String fetchCurrentWorkingDirectory() {
		return System.getProperty("user.dir");
	}

	/**
	 * @param pathParaFicheiro O caminho introduzido pelo utilizador
	 * @return verdadeiro se for um ficheiro válido, falso se não for um ficheiro
	 */
	public boolean ficheiroIsValido(String pathParaFicheiro) {
		ficheiroXML = new File(pathParaFicheiro);
		return ficheiroXML.isFile();
	}

	/**
	 * @return a lista com todos os formatos de conversao
	 */
	public List<FormatoConversao> formatosConversao() {
		formatosConversao = Arrays.asList(FormatoConversao.values());
		return formatosConversao;
	}

	public void escolherFormatoConversao(int opcaoFormato) {
		formato = formatosConversao.get(opcaoFormato);
	}

	public boolean converter() {
		ConversorXML conversor = new ConversorXMLFactory().getEstrategiaConversao(formato);
		return conversor.converter(ficheiroXML);
	}
}

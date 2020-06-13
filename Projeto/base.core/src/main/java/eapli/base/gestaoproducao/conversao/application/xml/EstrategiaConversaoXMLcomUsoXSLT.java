package eapli.base.gestaoproducao.conversao.application.xml;

import eapli.base.gestaoproducao.conversao.domain.xml.ConversorXML;
import net.sf.saxon.BasicTransformerFactory;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;

public class EstrategiaConversaoXMLcomUsoXSLT implements ConversorXML {
	private static final Logger LOGGER = LoggerFactory.getLogger(EstrategiaConversaoXMLcomUsoXSLT.class);
	private final String extensao;
	private final InputStream xslt;

	protected EstrategiaConversaoXMLcomUsoXSLT(String extensao, String xslt) {
		this.extensao = extensao;
		this.xslt = EstrategiaConversaoXMLcomUsoXSLT.class.getResourceAsStream(xslt);
	}

	@Override
	public boolean converter(File file) {
		//Carrega o template XSLT
		Templates templates = null;
		try {
			templates = new BasicTransformerFactory().newTemplates(new StreamSource(xslt));
		} catch (TransformerConfigurationException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		assert templates != null;

		//Carrega o transformer a partir do template
		Transformer transformer = null;
		try {
			transformer = templates.newTransformer();
		} catch (TransformerConfigurationException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		assert transformer != null;

		//Vai buscar o ficheiro XSLT correto(depende da implementação)
		Source text = new StreamSource(file);
		try {
			transformer.transform(text, new StreamResult(new File(
					FilenameUtils.removeExtension(file.getAbsolutePath())+extensao)));
		} catch (TransformerException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		return true;
	}
}

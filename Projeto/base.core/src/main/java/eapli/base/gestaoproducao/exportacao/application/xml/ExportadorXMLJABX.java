package eapli.base.gestaoproducao.exportacao.application.xml;

import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;
import eapli.base.gestaoproducao.exportacao.domain.Exportador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExportadorXMLJABX implements Exportador {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportadorXMLJABX.class);

	@Override
	public boolean export(File file, ChaoDeFabrica chaoDeFabrica) {
		if(chaoDeFabrica == null || chaoDeFabrica.nothingWasLoaded()) {
			throw new IllegalArgumentException("Chão de Fábrica introduzido está vazio, não há nada para exportar");
		}

		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(ChaoDeFabrica.class);
		} catch (JAXBException e) {
			e.printStackTrace();
			LOGGER.error("Erro a criar uma nova instancia de contexto JAXB");
			return false;
		}

		Marshaller marshaller = null;
		try {
			assert context != null;
			marshaller = context.createMarshaller();
		} catch (JAXBException e) {
			LOGGER.error("Erro a criar um marshaller dado o contexto");
			return false;
		}

		try {
			assert marshaller != null;
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://bitbucket.org/1180842/lei_isep_2019_20_sem4_2dd_1180842_1161752_1170554_1181477/issues/38/p1-user-story-como-gestor-de-projeto-eu/ns/chaofabrica chaoDeFabrica.xsd");
		} catch (PropertyException e) {
			LOGGER.error("Erro a definir o formato de output");
			return false;
		}

		try {
			marshaller.marshal(chaoDeFabrica, file);
		} catch (JAXBException e) {
			LOGGER.error("Erro a exportar para XML. Verificar nome de ficheiro");
			return false;
		}

		//Valida o XSD
		JAXBContext jaxbContext;
		try
		{
			jaxbContext = JAXBContext.newInstance(ChaoDeFabrica.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema employeeSchema = sf.newSchema(new File("../XML_XSD_XSLT/chaoDeFabrica.xsd"));
			jaxbUnmarshaller.setSchema(employeeSchema);
			jaxbUnmarshaller.unmarshal(file);
		}
		catch (JAXBException | SAXException e)
		{
			file.delete();
			e.printStackTrace();
			LOGGER.error("Exportação falhou a validação de XSD");
			return false;
		}
		return true;
	}

	@Override
	public String fileExtension() {
		return ".xml";
	}

}

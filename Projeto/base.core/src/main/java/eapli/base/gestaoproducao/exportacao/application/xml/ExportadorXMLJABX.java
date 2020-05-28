package eapli.base.gestaoproducao.exportacao.application.xml;

import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;
import eapli.base.gestaoproducao.exportacao.domain.Exportador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;

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
			e.printStackTrace();
			LOGGER.error("Erro a exportar para XML. Verificar nome de ficheiro");
			return false;
		}
		return true;
	}

	@Override
	public String fileExtension() {
		return ".xml";
	}

}

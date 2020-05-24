package eapli.base.gestaoproducao.exportacao.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;

public class ExportadorXMLStrategy implements ExportadorStrategy {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportadorXMLStrategy.class);

	@Override
	public ChaoDeFabrica loadAllData() {
		final ChaoDeFabrica chaoDeFabrica = new ChaoDeFabrica();
		if(!chaoDeFabrica.loadAllData()) {
			return null;
		}
		return chaoDeFabrica;
	}

	@Override
	public boolean export(String filename, ChaoDeFabrica chaoDeFabrica) {
		if(chaoDeFabrica == null || !chaoDeFabrica.verifyAllIsLoaded()) {
			LOGGER.error("Chão de Fábrica introduzido está vazio, não há nada para exportar");
			return false;
		}

		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(ChaoDeFabrica.class);
		} catch (JAXBException e) {
			LOGGER.error("Erro a criar uma nova instancia de contexto JAXB");
		}

		Marshaller marshaller = null;
		try {
			assert context != null;
			marshaller = context.createMarshaller();
		} catch (JAXBException e) {
			LOGGER.error("Erro a criar um marshaller dado o contexto");
		}

		try {
			assert marshaller != null;
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (PropertyException e) {
			LOGGER.error("Erro a definir o formato de output");
		}

		File file = new File(filename + ".xml");

		try {
			marshaller.marshal(chaoDeFabrica, file);
		} catch (JAXBException e) {
			LOGGER.error("Erro a exportar para XML");
		}
		return true;
	}

}

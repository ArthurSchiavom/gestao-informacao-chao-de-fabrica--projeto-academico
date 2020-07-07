package eapli.base.app.backoffice.console.presentation.gestaoproducao.conversao.conversaoXML;

import eapli.base.app.backoffice.console.presentation.UIUtils;
import eapli.base.gestaoproducao.conversao.application.FormatoConversao;
import eapli.base.gestaoproducao.conversao.application.xml.ConversaoFicheiroXMLController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class ConversaoFicheiroXMLUI extends AbstractUI {
	private final ConversaoFicheiroXMLController controller = new ConversaoFicheiroXMLController();

	@Override
	protected boolean doShow() {
		String workingDir = controller.fetchCurrentWorkingDirectory();
		String pathParaFicheiro;
		do {
			pathParaFicheiro = Console.readNonEmptyLine("Insira o caminho para o ficheiro, para cancelar escreva" +
							" CANCELAR : " + workingDir,
					"Têm que ser escolhido um ficheiro para transformar");
			if(pathParaFicheiro.equals("CANCELAR")) {
				return false;
			}
		}while(!controller.ficheiroIsValido(pathParaFicheiro));
		List<FormatoConversao> formatos = controller.formatosConversao();
		int opcaoformato = UIUtils.displayTableAndSelectIndex(formatos, "Escolha um formato para exportar");
		if(opcaoformato == -1) {
			return false;
		}
		controller.escolherFormatoConversao(opcaoformato);
		if(controller.converter()) {
			System.out.println("A conversão correu com sucesso");
		}
		return false;
	}

	@Override
	public String headline() {
		return "Conversão de ficheiros XML para outros Formatos";
	}
}

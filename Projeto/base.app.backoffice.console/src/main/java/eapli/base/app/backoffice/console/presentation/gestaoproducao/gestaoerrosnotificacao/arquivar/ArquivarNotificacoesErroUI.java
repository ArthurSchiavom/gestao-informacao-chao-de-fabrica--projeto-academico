package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoerrosnotificacao.arquivar;

import eapli.base.app.backoffice.console.presentation.UIUtils;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.application.arquivar.ArquivarNotificacoesErroController;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class ArquivarNotificacoesErroUI extends AbstractUI {

	private final ArquivarNotificacoesErroController controller = new ArquivarNotificacoesErroController();

	@Override
	protected boolean doShow() {
		int opcaoRemover = 1;
		while (opcaoRemover != 0) {
			List<NotificacaoErroDTO> lista = controller.notificacoesErroNaoArquivadas();
			if(lista.size() == 0) {
				System.out.println("Lista de Notificações de Erro Não Arquivadas está vazia");
				Console.readLine("Pressione ENTER para confirmar");
				return false;
			}
			opcaoRemover = UIUtils.displayTableAndSelectIndex(lista,
					"Lista de Notificações de Erros Não Arquivadas (Selecione cancelar para terminar a arquivação)");
			controller.arquivarNotificacao(lista.get(opcaoRemover).identifier);
		}
		Console.readLine("Processo de arquivação de notifcações terminado. Pressione ENTER para confirmar");
		return true;
	}

	@Override
	public String headline() {
		return "Arquivação de notificações de erro";
	}
}

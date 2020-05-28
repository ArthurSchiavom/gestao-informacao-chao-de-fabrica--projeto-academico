package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoerrosnotificacao.consultar;

import eapli.base.app.backoffice.console.presentation.UIUtils;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.application.consultar.ConsultarErrosProcessamentoController;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class ConsultarErrosProcessamentoUI extends AbstractUI {

	private final ConsultarErrosProcessamentoController controller = new ConsultarErrosProcessamentoController();

	@Override
	protected boolean doShow() {
		List<LinhaProducaoDTO> linhasProducao = controller.linhasProducao();
		int opcaoLinhaProd = UIUtils.displayTableAndSelectIndex(linhasProducao,
				"Escolha uma linha de produção pela qual pretende filtrar");
		if (opcaoLinhaProd == -1) {
			return true;
		}
		controller.definirFiltroLinhaProducao(opcaoLinhaProd);
		List<TipoErroNotificacao> tiposErro = controller.tiposErro();
		int opcaoTipoErro = UIUtils.displayTableAndSelectIndex(tiposErro,
				"Escolha o tipo de erro pelo qual pretende filtrar");
		if (opcaoTipoErro == -1) {
			return true;
		}
		controller.definirTipoErro(opcaoTipoErro);
		List<NotificacaoErroDTO> listNotifsErro = controller.listarErrosProcessamento();
		if (listNotifsErro.isEmpty()) {
			System.out.println("Não existem notificações que correspondam aos critérios inseridos");
			Console.readLine("Pressione ENTER para confirmar");
		} else {
			UIUtils.displayNumberedTable(listNotifsErro, "Lista de Notificações de Erro (Filtrada):");
			Console.readLine("Pressione ENTER para acabar a consulta");
		}
		return false;
	}

	@Override
	public String headline() {
		return "Consultar Notificações de Erro por tratar";
	}
}

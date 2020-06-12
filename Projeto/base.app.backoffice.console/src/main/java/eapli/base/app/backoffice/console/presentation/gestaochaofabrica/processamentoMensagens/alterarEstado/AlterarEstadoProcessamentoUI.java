package eapli.base.app.backoffice.console.presentation.gestaochaofabrica.processamentoMensagens.alterarEstado;

import eapli.base.app.backoffice.console.presentation.UIUtils;
import eapli.base.app.backoffice.console.presentation.servicoDeProcessamentoMensagens.ProcessamentoDeMensagensUI;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.EstadoProcessamentoMensagens;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.processamentoMensagens.application.alterarEstado.AlterarEstadoProcessamentoController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class AlterarEstadoProcessamentoUI extends AbstractUI {
	private final AlterarEstadoProcessamentoController controller = new AlterarEstadoProcessamentoController();
	private static final int PAGE_SIZE = 100;
	private static final String CONFIRMACAO = "Pressione ENTER para confirmar";

	@Override
	protected boolean doShow() {
		int pagina = 0;
		int opcaoLinhaProd = 0;
		do {
			List<LinhaProducaoDTO> linhasProducao = controller.linhasProducao(PAGE_SIZE, pagina);
			opcaoLinhaProd = UIUtils.displayPaginationTableAndSelectIndex(linhasProducao,
					"Escolha uma linha de produção");
			if (opcaoLinhaProd == -1) {
				return false;//utilizador escolheu cancelar
			}
			if (opcaoLinhaProd == linhasProducao.size() && pagina > 0) {
				pagina--;
			}
			if (opcaoLinhaProd == linhasProducao.size() + 1 && linhasProducao.size() != 0) {
				pagina++;
			}
			if (opcaoLinhaProd != linhasProducao.size() && opcaoLinhaProd != linhasProducao.size() + 1) {
				pagina = -1;
			}
		} while (pagina != -1);
		LinhaProducaoDTO dto = controller.definirLinhaProducao(opcaoLinhaProd);
		System.out.println("Selecionado: " + dto);
		List<EstadoProcessamentoMensagens> estadosProcessamento = controller.estadosProcessamento();
		int opcaoEstado = UIUtils.displayTableAndSelectIndex(estadosProcessamento,
				"Escolha o estado que pretende selecionar para a linha de produção");
		if (!controller.escolherEstado(opcaoEstado)) {
			System.out.println("Foi escolhido alterar o estado de processamento para o estado atual.");
			Console.readLine(CONFIRMACAO);
			return false;
		}
		if (controller.podeEfetuarReprocessamento()){
			boolean reprocressamento = Console.readBoolean("Pretende efetuar um reprocessamento de mensagens? (S/N)");
			if (reprocressamento) {
				new ProcessamentoDeMensagensUI().show();
			}
		}
		if (controller.alterarEstado()) {
			System.out.println("Estado alterado para " + estadosProcessamento.get(opcaoEstado) + " com sucesso!");
		} else {
			System.out.println("A alteração de estado não teve sucesso.");
		}
		Console.readLine(CONFIRMACAO);
		return true;
	}

	@Override
	public String headline() {
		return "Alterar o estado de processamento de mensagens";
	}
}

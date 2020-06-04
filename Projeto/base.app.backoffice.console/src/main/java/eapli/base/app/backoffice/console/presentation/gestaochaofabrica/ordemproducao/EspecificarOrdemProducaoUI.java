package eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao;

import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.formatter.SimpleConsoleMessages;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.gestaoproducao.ordemProducao.application.EspecificarOrdemProducaoController;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.Date;
import java.util.List;

public class EspecificarOrdemProducaoUI extends AbstractUI {

    @Override
    public String headline() {
        return "Registar Ordem de Produção";
    }

    @Override
    protected boolean doShow() {
        EspecificarOrdemProducaoController controller = new EspecificarOrdemProducaoController();

        selecionarDataEmissao(controller);
        selecionarIdEncomenda(controller);
        selecionarDataPrevistaExecucao(controller);
        selecionarEncomendas(controller);
        selecionarProduto(controller);
        selecionarQuantidadeAProduzir(controller);

        registar(controller);

        return false;
    }

    private void selecionarIdEncomenda(EspecificarOrdemProducaoController controller) {
        System.out.print(SimpleConsoleMessages.CLEAR_SCREEN);
        selecionarEmLoopAteSucesso(() -> {
            String idEncomenda = Console.readNonEmptyLine("Identificador da ordem: ", "O identificador de ordem é obrigatório");
            controller.selecionarIdentificadorOrdem(idEncomenda);
        });
    }

    private void selecionarEncomendas(EspecificarOrdemProducaoController controller) {
        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + "Por favor indique as encomendas que esta ordem visa tratar. Pressione enter sem escrever para terminar.");
        String identificador = "a";
        boolean continuar = true;
        while (continuar) {
            identificador = Console.readLine("Identificador de uma encomenda: ");
            if (!identificador.equalsIgnoreCase("")) {
                controller.addIdentificadoresEncomenda(identificador);
            }
            else {
                continuar = false;
            }
        }
    }

    private void selecionarProduto(EspecificarOrdemProducaoController controller) {
        List<ProdutoDTO> produtos = controller.produtos();
        String tabelaProdutosDisplay = ConsoleTables.tabela(produtos, false, 0);
        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + tabelaProdutosDisplay);

        selecionarEmLoopAteSucesso(() -> {
            String codigoUnico = Console.readNonEmptyLine("Código de fabrico do produto a produzir: ", "Deve indicar o código de fabrico do produto");
            controller.selecionarCodigoUnicoProduto(codigoUnico);
        });
    }

    private void selecionarDataPrevistaExecucao(EspecificarOrdemProducaoController controller) {
        System.out.print(SimpleConsoleMessages.CLEAR_SCREEN);
        selecionarEmLoopAteSucesso(() -> {
            Date dataPrevistaExecucao = Console.readDate("Data prevista de execucao (dd/mm/aaaa): ", "dd/MM/yyyy");
            controller.selecionarDataPrevistaExecucao(dataPrevistaExecucao);
        });
    }

    private void selecionarQuantidadeAProduzir(EspecificarOrdemProducaoController controller) {
        System.out.print(SimpleConsoleMessages.CLEAR_SCREEN);
        selecionarEmLoopAteSucesso(() -> {
            double quantidade = Console.readDouble("Quantidade a produzir:");
            controller.selecionarQuantidadeAProduzir(quantidade);
        });
    }

    private void selecionarDataEmissao(EspecificarOrdemProducaoController controller) {
        controller.selecionarDataEmissao(null);
    }

    private void registar(EspecificarOrdemProducaoController controller) {
        try {
            controller.registar();
            System.out.println("Produto registado com sucesso!");
        } catch (IllegalDomainValueException e) {
            System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + "O produto não foi registado corretamente: " + e.getMessage());
        } catch (ConcurrencyException e) {
            System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + "O sistema encontra-se ocupado, por favor tente mais tarde.");
        }
    }

    private void selecionarEmLoopAteSucesso(AcaoSelecionar acaoSelecionar) {
        boolean sucesso = false;
        while (!sucesso) {
            try {
                acaoSelecionar.executar();
                sucesso = true;
            } catch (IllegalDomainValueException e) {
                System.out.println("\n" + e.getMessage());
            }
        }
    }



    private interface AcaoSelecionar {
        void executar() throws IllegalDomainValueException;
    }
}

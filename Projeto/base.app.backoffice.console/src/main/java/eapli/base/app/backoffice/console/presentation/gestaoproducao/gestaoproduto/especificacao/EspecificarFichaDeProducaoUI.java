package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoproduto.especificacao;

import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.formatter.SimpleConsoleMessages;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaomaterial.application.dto.MaterialDTO;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.EspecificarFichaDeProducaoController;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class EspecificarFichaDeProducaoUI extends AbstractUI {

    private interface AcaoRegisto {
        void executar (String id, double quantidade) throws IllegalDomainValueException;
    }

    @Override
    protected boolean doShow() {
        EspecificarFichaDeProducaoController controller = new EspecificarFichaDeProducaoController();
        List<ProdutoDTO> produtosSemFichaDeProducao = controller.produtosSemFichaDeProducao();

        if (produtosSemFichaDeProducao.isEmpty()) {
            System.out.println("Não há nenhum produto sem ficha de produção.\n");
            UserInteractionFlow.enterToContinue();
            return false;
        }

        String produtosSemFichaDeProducaoDisplay = ConsoleTables.tabelaDeProdutos(produtosSemFichaDeProducao);
        System.out.println(produtosSemFichaDeProducaoDisplay + "\n\n");

        String idProduto = null;
        boolean continuar = true;
        while (continuar) {
            idProduto = Console.readNonEmptyLine("Indique o código único do produto cuja ficha de produção deseja alterar.", "Indique um código válido.");
            continuar = !controller.selecionarProdutoAlvo(idProduto);
            if (continuar) {
                System.out.println("O código indicado não se encontra registado.\n");
            }
        }

        List<ProdutoDTO> produtos = controller.produtos();
        String produtosDisplay = ConsoleTables.tabelaDeProdutos(produtos);
        System.out.println(produtosDisplay + "\n\n");

        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + produtosDisplay + "\n\nO Produto " + idProduto + " foi selecionado.\n");
        String promptMateria = "\nIndique o código único de um produto a adicionar à ficha de produção " +
                "(pressione enter sem escrever nada para terminar a adição de produtos): ";
        String promptQuantidade = "Indique agora a quantidade deste produto: ";
        lerMateriais(promptMateria, promptQuantidade, controller::adicionarProduto);

        List<MaterialDTO> materiais = controller.materiais();
        String materiaisDisplay = ConsoleTables.tabelaDeMaterial(materiais);
        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + materiaisDisplay + "\n\nSelecione agora os materiais que constituem a ficha de produção.\n");
        promptMateria = "\nIndique o código interno de um material a adicionar à ficha de produção " +
                "(pressione enter sem escrever nada para terminar a adição de materiais): ";
        promptQuantidade = "Indique agora a quantidade deste material: ";
        lerMateriais(promptMateria, promptQuantidade, controller::adicionarMaterial);

        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + "Registo da ficha de produção em progresso...\n");
        try {
            controller.registar();
            System.out.println("Ficha de produção registada com sucesso!\n");
        } catch (IllegalDomainValueException e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }

        UserInteractionFlow.enterToContinue();
        return false;
    }

    private void lerMateriais(String promptMateria, String promptQuantidade, AcaoRegisto acaoRegisto) {
        double quantidade;
        String id = Console.readLine(promptMateria).trim();
        while (!id.isEmpty()) {
            quantidade = Console.readDouble(promptQuantidade);
            if (quantidade <= 0) {
                System.out.println("Valor incorreto: A quantidade tem que ser superior a 0.\n");
            }
            else {
                try {
                    acaoRegisto.executar(id, quantidade);
                } catch (IllegalDomainValueException e) {
                    System.out.printf("Erro: %s\n", e.getMessage());
                }
            }
            id = Console.readLine(promptMateria).trim();
        }
    }

    @Override
    public String headline() {
        return "Registar Ficha de Produção";
    }
}

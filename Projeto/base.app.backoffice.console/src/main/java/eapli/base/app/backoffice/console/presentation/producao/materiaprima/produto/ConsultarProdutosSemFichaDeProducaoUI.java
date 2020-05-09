package eapli.base.app.backoffice.console.presentation.producao.materiaprima.produto;

import eapli.base.app.backoffice.console.presentation.utilities.UserInteractionControl;
import eapli.base.app.common.console.presentation.formatter.ConsoleTable;
import eapli.base.produto.application.ConsultarProdutosSemFichaDeProducaoController;
import eapli.base.produto.application.ProdutoDTO;
import eapli.framework.presentation.console.AbstractUI;

import java.util.ArrayList;
import java.util.List;

public class ConsultarProdutosSemFichaDeProducaoUI extends AbstractUI {
    ConsultarProdutosSemFichaDeProducaoController controller = new ConsultarProdutosSemFichaDeProducaoController();

    @Override
    protected boolean doShow() {
        List<ProdutoDTO> resultado = controller.iniciar();
        if (resultado.size() == 0) {
            System.out.println("Nenhum produto sem ficha de produção registado.");
            UserInteractionControl.enterToContinue();
            return false;
        }

        ArrayList<String> headers = new ArrayList<>();
        headers.add("Código de fabrico");
        headers.add("Código comercial");
        headers.add("Descrição breve");
        headers.add("Categoria");
        headers.add("Unidade de medida");
        ArrayList<ArrayList<String>> tabelaRaw = new ArrayList<>();
        for (ProdutoDTO produtoDTO : resultado) {
            ArrayList<String> linha = new ArrayList<>();
            linha.add(produtoDTO.codigoUnico);
            linha.add(produtoDTO.codigoComercial);
            linha.add(produtoDTO.descricaoBreve);
            linha.add(produtoDTO.categoriaDeProduto);
            linha.add(produtoDTO.unidadeDeMedida);
            tabelaRaw.add(linha);
        }
        ConsoleTable tabela = new ConsoleTable(headers, tabelaRaw);
        String resultadoDisplay = tabela.generateTable();
        System.out.println(resultadoDisplay);
        UserInteractionControl.enterToContinue();
        return false;
    }

    @Override
    public String headline() {
        return "Produtos Sem Ficha de Produção";
    }
}

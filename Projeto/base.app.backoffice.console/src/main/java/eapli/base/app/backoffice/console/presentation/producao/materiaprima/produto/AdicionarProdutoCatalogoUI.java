package eapli.base.app.backoffice.console.presentation.producao.materiaprima.produto;

import eapli.base.app.backoffice.console.presentation.menu.OptionSelector;
import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.produto.application.RegistarProdutoController;
import eapli.base.utilities.wrappers.Updateable;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class AdicionarProdutoCatalogoUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        System.out.println("\nCaso seja encontrado algum produto com código único já registado, pretende que a aplicação substitua o produto atual com o indicado?\n");
        OptionSelector optionSelector = new OptionSelector();
        final Updateable<Boolean> substituir = new Updateable<>();
        optionSelector.registerOption("Sim", () -> substituir.val = true);
        optionSelector.registerOption("Não", () -> substituir.val = false);
        optionSelector.show();
        RegistarProdutoController registarProdutoController=new RegistarProdutoController(substituir.val);
        final String codigoUnico= Console.readNonEmptyLine("Insira um codigo unico: ","Campo codigo unico nao pode ser vazio");
        final String codigoComercial= Console.readNonEmptyLine("Insira um codigo comercial: ","Campo codigo comercial nao pode ser vazio");
        final String categoria= Console.readNonEmptyLine("Insira uma categoria: ","Campo categoria nao pode ser vazio");
        final String descricaoBreve= Console.readNonEmptyLine("Insira uma descricao breve: ","Campo descricao breve nao pode ser vazio");
        final String descricaoCompleta= Console.readNonEmptyLine("Insira uma descricao completa: ","Campo descricao completa nao pode ser vazio");
        final String unidadeDeMedida= Console.readNonEmptyLine("Insira uma unidade de medida: ","Campo unidade de medida nao pode ser vazio");
        try{
            registarProdutoController.setCodigoUnico(codigoUnico);
            registarProdutoController.setCodigoComercial(codigoComercial);
            registarProdutoController.setCategoriaDeProduto(categoria);
            registarProdutoController.setDescricaoBreve(descricaoBreve);
            registarProdutoController.setDescricaoCompleta(descricaoCompleta);
            registarProdutoController.setUnidadeDeMedida(unidadeDeMedida);
            registarProdutoController.register();
            System.out.println("Inserido com sucesso");
            return true;
        } catch (IllegalDomainValue illegalDomainValue) {
        }
        return false;
    }

    @Override
    public String headline() {
        return "Adicionar novo produto ao catalogo";
    }
}

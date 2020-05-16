package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.especificacao;

import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial.ListCategoriaService;
import eapli.base.app.backoffice.console.presentation.menu.OptionSelector;
import eapli.base.gestaoproducao.gestaomaterial.application.EspecificarMaterialController;
import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.utilities.wrappers.Updateable;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.io.IOException;

public class EspecificarMaterialUI extends AbstractUI {
    EspecificarMaterialController especificarMaterialController = new EspecificarMaterialController();
    ListCategoriaService listCategoriaService=new ListCategoriaService();

    @Override
    protected boolean doShow() {
        boolean flag=false;
        final String nomeMaterial=Console.readNonEmptyLine("Insira o nome do Material: ","Nome do material nao pode ser vazio");
        final String codigoInterno = Console.readNonEmptyLine("Insira o codigo Interno: ", "Codigo interno não pode ser vazio");
        if (verificaoCodigoInterno(codigoInterno)){
            System.out.println("Codigo Interno ja existente! Prendente substituir?\n");
            OptionSelector optionSelector = new OptionSelector();
            final Updateable<Boolean> substituir = new Updateable<>();
            optionSelector.registerOption("Sim", () -> substituir.val = true);
            optionSelector.registerOption("Não", () -> substituir.val = false);
            optionSelector.show();
            if (substituir.val){
                especificarMaterialController.removerMaterialPorCodigoInterno(codigoInterno);
            }else{
                flag=true;
            }
        }
        if (flag==false) {
            final String unidadeMedida = Console.readNonEmptyLine("Insira a unidade Medida: ", "Unidade de medida nao pode ser vazio");
            final String path = Console.readNonEmptyLine("Insira o caminho onde prentende guardar o ficheiro: ", "Caminho nao pode ser vazio");
            final String descricaoDoMaterial = Console.readNonEmptyLine("Insira a descricao do Material: ", "Descricao do material nao pode ser vazio");
            final String conteudoFichaTecnica = Console.readNonEmptyLine("Insira conteudo da Ficha tecnica: ", "Conteudo da Ficha tecnica nao pode ser vazio");
            final Categoria categoria = selecionarCategoria();
            try {
                this.especificarMaterialController.registarMaterial(unidadeMedida, descricaoDoMaterial, nomeMaterial, path, conteudoFichaTecnica, codigoInterno, categoria);
                return true;
            } catch (IllegalArgumentException ex) {
                System.out.println("Material Invalido!!"); //Melhorar a apresentacao!!!!!
            } catch (NullPointerException ex) {
                System.out.println(ex.getCause());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Categoria selecionarCategoria(){
        System.out.println("Lista de categorias  - Selecione uma Categoria\n");
        final Iterable<Categoria> listaDeCategorias= listCategoriaService.todasCategorias();
        final SelectWidget<Categoria> selectorCategoria=new SelectWidget<>("Selecione uma categoria",listaDeCategorias);
        selectorCategoria.show();
        final Categoria categoriaSelecionada=selectorCategoria.selectedElement();
        return categoriaSelecionada;
    }

    private boolean verificaoCodigoInterno(String codigoInterno){
        Material teste= especificarMaterialController.obterMaterialPorCodigoInterno(codigoInterno);
        if (teste==null){
            return false;
        }else{
            System.out.println("O codigo interno ja inserido ja existe!\n");
            return true;
        }
    }

    @Override
    public String headline() { return "Adicionar material ao catalogo ";}

}

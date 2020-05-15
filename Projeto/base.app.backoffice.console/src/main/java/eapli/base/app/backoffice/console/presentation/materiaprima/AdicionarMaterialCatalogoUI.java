package eapli.base.app.backoffice.console.presentation.materiaprima;

import eapli.base.definircategoriamaterial.domain.Categoria;
import eapli.base.gestaomateriasprimas.application.AdicionarMaterialCatalogoController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.io.IOException;

public class AdicionarMaterialCatalogoUI extends AbstractUI {
    AdicionarMaterialCatalogoController adicionarMaterialCatalogoController= new AdicionarMaterialCatalogoController();
    ListCategoriaService listCategoriaService=new ListCategoriaService();

    @Override
    protected boolean doShow() {
        final String nomeMaterial=Console.readNonEmptyLine("Insira o nome do Material: ","Nome do material nao pode ser vazio");
        final String codigoInterno = Console.readNonEmptyLine("Insira o codigo Interno: ", "Codigo interno não pode ser vazio");
        final String unidadeMedida= Console.readNonEmptyLine("Insira a unidade Medida: ","Unidade de medida nao pode ser vazio");
        final String path=Console.readNonEmptyLine("Insira o caminho onde prentende guardar o ficheiro: ", "Caminho nao pode ser vazio");
        final String descricaoDoMaterial=Console.readNonEmptyLine("Insira a descricao do Material: ","Descricao do material nao pode ser vazio");
        final String conteudoFichaTecnica=Console.readNonEmptyLine("Insira conteudo da Ficha tecnica: ","Conteudo da Ficha tecnica nao pode ser vazio");
        final Categoria categoria=selecionarCategoria();
        try {
            this.adicionarMaterialCatalogoController.registarMaterial(unidadeMedida,descricaoDoMaterial,nomeMaterial,path,conteudoFichaTecnica,codigoInterno,categoria);
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println("Material Invalido!!"); //Melhorar a apresentacao!!!!!
        } catch (NullPointerException ex){
            System.out.println(ex.getCause());
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public String headline() { return "Adicionar material ao catalogo ";}

}

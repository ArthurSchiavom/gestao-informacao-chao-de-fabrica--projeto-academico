package eapli.base.app.common.console.presentation.formatter;

import eapli.base.gestaoproducao.gestaomaterial.domain.MaterialDTO;
import eapli.base.gestaoproducao.gestaoprodutos.application.ProdutoDTO;

import java.util.ArrayList;
import java.util.Collection;

public class ConsoleTables {
    public static String tabelaDeProdutos(Collection<ProdutoDTO> produtos) {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Código de fabrico");
        headers.add("Código comercial");
        headers.add("Descrição breve");
        headers.add("Categoria");
        headers.add("Unidade de medida");
        ArrayList<ArrayList<String>> tabelaRaw = new ArrayList<>();
        for (ProdutoDTO produtoDTO : produtos) {
            ArrayList<String> linha = new ArrayList<>();
            linha.add(produtoDTO.codigoUnico);
            linha.add(produtoDTO.codigoComercial);
            linha.add(produtoDTO.descricaoBreve);
            linha.add(produtoDTO.categoriaDeProduto);
            linha.add(produtoDTO.unidadeDeMedida);
            tabelaRaw.add(linha);
        }
        ConsoleTable tabela = new ConsoleTable(headers, tabelaRaw);
        return tabela.generateTable();
    }

    public static String tabelaDeMaterial(Collection<MaterialDTO> materiais) {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Código Interno");
        headers.add("Descrição");
        headers.add("Unidade de Medida");
        ArrayList<ArrayList<String>> tabelaRaw = new ArrayList<>();
        for (MaterialDTO materialDTO : materiais) {
            ArrayList<String> linha = new ArrayList<>();
            linha.add(materialDTO.codigointerno);
            linha.add(materialDTO.descricao);
            linha.add(materialDTO.unidadeDeMedida);
            tabelaRaw.add(linha);
        }
        ConsoleTable tabela = new ConsoleTable(headers, tabelaRaw);
        return tabela.generateTable();
    }
}

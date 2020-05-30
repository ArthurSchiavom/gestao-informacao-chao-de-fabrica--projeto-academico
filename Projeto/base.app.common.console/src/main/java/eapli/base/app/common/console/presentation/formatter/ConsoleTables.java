package eapli.base.app.common.console.presentation.formatter;

import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.MaquinaDTO;
import eapli.base.gestaoproducao.gestaomaterial.application.dto.MaterialDTO;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.processamentoMensagens.application.DTO.AgendamentoDeProcessamentoDTO;

import java.util.ArrayList;
import java.util.Collection;

public class ConsoleTables {

    public static String tabelaDeAgendamentoDeProcessamentoDeMensagens(Collection<AgendamentoDeProcessamentoDTO> agendamentoDeProcessamento){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("ID Linha Produção");
        headers.add("Data/Hora Inicial");
        headers.add("Data/Hora Final");
        headers.add("Estado");
        ArrayList<ArrayList<String>> tabelaRaw = new ArrayList<>();
        for (AgendamentoDeProcessamentoDTO agendamentoDeProcessamentoDTO : agendamentoDeProcessamento) {
            ArrayList<String> linha = new ArrayList<>();
            linha.add(agendamentoDeProcessamentoDTO.identifier.identifier);
            linha.add(agendamentoDeProcessamentoDTO.dataTempoInicio.toString());
            linha.add(agendamentoDeProcessamentoDTO.dataTempoFinal.toString());
            linha.add(agendamentoDeProcessamentoDTO.estado);
            tabelaRaw.add(linha);
        }
        ConsoleTable tabela = new ConsoleTable(headers, tabelaRaw);
        return tabela.generateTable();
    }

    public static String tabelaDeLinhasDeProducao(Collection<LinhaProducaoDTO> linhaProducao){
        int contador=0;
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Numero");
        headers.add("Identificador");
        headers.add("Estado");
        ArrayList<ArrayList<String>> tabelaRaw = new ArrayList<>();
        for (LinhaProducaoDTO linhaProducaoDTO: linhaProducao) {
            ArrayList<String> linha = new ArrayList<>();
            linha.add(""+contador);
            linha.add(linhaProducaoDTO.identificadorLinhaProducao);
            linha.add(linhaProducaoDTO.estado);
            tabelaRaw.add(linha);
            contador++;
        }
        ConsoleTable tabela = new ConsoleTable(headers, tabelaRaw);
        return tabela.generateTable();
    }
    public static String tabelaDeMaquinas(Collection<MaquinaDTO> maquinas){
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Numero de Serie");
        headers.add("Código interno");
        headers.add("Identificador Protocolo  Comunicacao");
        headers.add("Marca");
        headers.add("Modelo");
        headers.add("Ordem Linha Producao");
        headers.add("Linha de producao");
        ArrayList<ArrayList<String>> tabelaRaw = new ArrayList<>();
        for (MaquinaDTO maquinaDTO : maquinas) {
            ArrayList<String> linha = new ArrayList<>();
            linha.add(maquinaDTO.numeroSerie);
            linha.add(maquinaDTO.codigoInterno);
            linha.add(maquinaDTO.identificadorProtocoloComunicacao);
            linha.add(maquinaDTO.marca);
            linha.add(maquinaDTO.modelo);
            linha.add(maquinaDTO.ordemLinhaProducao);
            linha.add(maquinaDTO.identificadorLinhaProducao);
            tabelaRaw.add(linha);
        }
        ConsoleTable tabela = new ConsoleTable(headers, tabelaRaw);
        return tabela.generateTable();
    }
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

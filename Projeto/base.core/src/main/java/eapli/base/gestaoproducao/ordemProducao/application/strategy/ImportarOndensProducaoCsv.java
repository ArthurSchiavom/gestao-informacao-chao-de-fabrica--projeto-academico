package eapli.base.gestaoproducao.ordemProducao.application.strategy;

import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;
import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinhaTransformer;
import eapli.base.gestaoproducao.ordemProducao.domain.*;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.application.files.CsvFileScanner;
import eapli.base.infrastructure.application.files.EmptyFileException;
import eapli.base.infrastructure.application.files.FileScanner;
import eapli.base.infrastructure.application.files.InvalidHeaderException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportarOndensProducaoCsv implements ImportarOrdensProducaoStrategy {

    private static final String SEPARADOR = ";";
    private static final int INDEX_IDENTIFICADOR = 0;
    private static final int INDEX_QUANTIDADE_A_PRODUZIR = 1;
    private static final int INDEX_DATA_EMISSAO = 2;
    private static final int INDEX_DATA_PREVISTA_EXECUCAO = 3;
    private static final int INDEX_ESTADO = 4;
    private static final int INDEX_ENCOMENDAS_ID = 5;
    private static final int N_CAMPOS = 6;

    private final String CHARSET_NAME = "UTF-8";

    @Override
    public ResultadoImportacaoLinhaALinha importarOrdensProducao(String path, boolean substituirSeExistir) {
        ResultadoImportacaoLinhaALinhaTransformer transformer = new ResultadoImportacaoLinhaALinhaTransformer();
        FileScanner<String[]> scanner;
        try {
            scanner = new CsvFileScanner(SEPARADOR, path,
                    CHARSET_NAME, "Identificador", "Quantidade a produzir", "Data emissão", "Data prevista execução",
                    "Estado", "EncomendasID");
        } catch (FileNotFoundException | InvalidHeaderException | EmptyFileException e) {
            transformer.addFalha(0, e.getMessage());
            transformer.incrementarFalhas();
            return transformer.gerarDTO();
        }

        int nLinha = 1;

        String next[];

        Identificador id;
        QuantidadeAProduzir quantidadeAProduzir;
        Estado estado;
        Date dataEmissao, dataPrevistaExecucao;
        List<IdentificadorEncomenda> encomendas;
        OrdemProducao op;
        OrdemProducaoRepository repo = PersistenceContext.repositories().ordemProducao();

        while (scanner.hasNext()) {
            nLinha++;

            next = scanner.next();

            if (next.length != N_CAMPOS) {
                transformer.addFalha(nLinha, "A linha não tem o número de campos esperado");
                transformer.incrementarFalhas();

                continue;
            }

            try {
                id = new Identificador(next[INDEX_IDENTIFICADOR]);
                estado = Estado.valueOf(next[INDEX_ESTADO]);
                encomendas = getEncomendas(next[INDEX_ENCOMENDAS_ID]);
                dataEmissao = getData(next[INDEX_DATA_EMISSAO], false);
                dataPrevistaExecucao = getData(next[INDEX_DATA_PREVISTA_EXECUCAO], false);

                try {
                    quantidadeAProduzir = new QuantidadeAProduzir(Integer.parseInt(next[INDEX_QUANTIDADE_A_PRODUZIR]));
                } catch (NumberFormatException ex) {
                    transformer.addFalha(nLinha, "Quantidade a produzir não é um número");
                    transformer.incrementarFalhas();

                    continue;
                }

                try {
                    op = new OrdemProducao(id, quantidadeAProduzir, encomendas, dataEmissao, dataPrevistaExecucao,estado);
                } catch(IllegalArgumentException ex){
                    transformer.addFalha(0, ex.getMessage());
                    transformer.incrementarFalhas();

                    continue;
                }

                if (substituirSeExistir) {
                    repo.saveRewrite(op);
                    transformer.incrementarSucessos();

                } else {
                    repo.save(op);
                    transformer.incrementarSucessos();
                }
            } catch (Exception ex) {
                transformer.addFalha(nLinha, ex.getMessage());
                transformer.incrementarFalhas();

            }
        }

        return transformer.gerarDTO();
    }

    /**
     * Checks if the given string can be converted into a Date in the format:
     * <p>
     * day-month-year
     */
    private Date getData(String s, boolean validoNoFuturo) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date data = sdf.parse(s);
            if (!validoNoFuturo) { // a data nao pode ser no futuro
                Date dataAgora = new Date(System.currentTimeMillis());

                if (data.compareTo(dataAgora) > 0) { // é no futuro e nao é valida
                    return null;
                }
            }
            return data;
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * cria instancias de todos as encomendas ID
     */
    private List<IdentificadorEncomenda> getEncomendas(String s) {
        String[] encomendas = s.split(",");

        List<IdentificadorEncomenda> lista = new ArrayList<>();

        for (String encomendaId : encomendas) {
            lista.add(new IdentificadorEncomenda(encomendaId.trim()));
        }

        return lista;
    }
}

package eapli.base.materiaprima.produto.application;

import eapli.base.infrastructure.application.files.CsvFileScanner;
import eapli.base.infrastructure.application.files.EmptyFileException;
import eapli.base.infrastructure.application.files.FileScanner;
import eapli.base.infrastructure.application.files.InvalidHeaderException;
import eapli.base.infrastructure.domain.IllegalDomainValue;

import java.io.FileNotFoundException;

public class RegistarProdutosDeCsvController implements RegistarProdutosDeFicheiroController {
    private final String SEPARADOR = ";";
    private final int INDEX_CODIGO_FABRICO = 0;
    private final int INDEX_CODIGO_COMERCIAL = 1;
    private final int INDEX_DESCRICAO_BREVE = 2;
    private final int INDEX_DESCRICAO_COMPLETA = 3;
    private final int INDEX_UNIDADE = 4;
    private final int INDEX_CATEGORIA = 5;
    private final int N_CAMPOS = 6;

    private final String CHARSET_NAME = "UTF-8";

    @Override
    public ResultadoImportacaoFicheiro iniciar(String filePath, boolean substituirSeExistir) {
        ResultadoImportacaoFicheiroTransformer transformer = new ResultadoImportacaoFicheiroTransformer();
        FileScanner<String[]> scanner;
        try {
            scanner = new CsvFileScanner(SEPARADOR, filePath,
                    CHARSET_NAME, "CódigoFabrico", "CódigoComercial", "Descrição Breve", "Descrição Completa", "Unidade", "Categoria");
        } catch (FileNotFoundException|InvalidHeaderException|EmptyFileException e) {
            transformer.addFalha(0, e.getMessage());
            return transformer.gerarDTO();
        }

        RegistarProdutoController registarProdutoController;
        int nLinha = 1;

        String next[];
        while (scanner.hasNext()) {
            nLinha++;

            registarProdutoController = new RegistarProdutoController(substituirSeExistir);
            next = scanner.next();

            if (next.length != N_CAMPOS) {
                transformer.addFalha(nLinha, "A linha não tem o número de campos esperado");
                continue;
            }

            try {
                registarProdutoController.setCodigoUnico(next[INDEX_CODIGO_FABRICO]);
                registarProdutoController.setCategoriaDeProduto(next[INDEX_CATEGORIA]);
                registarProdutoController.setCodigoComercial(next[INDEX_CODIGO_COMERCIAL]);
                registarProdutoController.setDescricaoBreve(next[INDEX_DESCRICAO_BREVE]);
                registarProdutoController.setDescricaoCompleta(next[INDEX_DESCRICAO_COMPLETA]);
                registarProdutoController.setUnidadeDeMedida(next[INDEX_UNIDADE]);

                if (!registarProdutoController.register()) {
                    transformer.addFalha(nLinha, "*** ERRO INTERNO: Nem todos os parâmetros do produto foram especificados\n\n");
                }
                else {
                    transformer.incrementarSucessos();
                }
            } catch (IllegalDomainValue illegalDomainValue) {
                transformer.addFalha(nLinha, illegalDomainValue.getMessage());
                transformer.incrementarFalhas();
            }
        }

        return transformer.gerarDTO();
    }
}

package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

import eapli.base.infrastructure.application.files.CsvFileScanner;
import eapli.base.infrastructure.application.files.EmptyFileException;
import eapli.base.infrastructure.application.files.FileScanner;
import eapli.base.infrastructure.application.files.InvalidHeaderException;
import eapli.base.infrastructure.domain.IllegalDomainValueException;

import java.io.FileNotFoundException;

public class ImportarCatalogoCsvProdutosController implements ImportarCatalogoProdutosController {
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
    public ResultadoImportacaoCatalogoProdutos iniciar(String filePath, boolean substituirSeExistir) {
        ResultadoImportacaoCatalogoProdutosTransformer transformer = new ResultadoImportacaoCatalogoProdutosTransformer();
        FileScanner<String[]> scanner;
        try {
            scanner = new CsvFileScanner(SEPARADOR, filePath,
                    CHARSET_NAME, "CódigoFabrico", "CódigoComercial", "Descrição Breve", "Descrição Completa", "Unidade", "Categoria");
        } catch (FileNotFoundException|InvalidHeaderException|EmptyFileException e) {
            transformer.addFalha(0, e.getMessage());
            return transformer.gerarDTO();
        }

        EspecificarProdutoController especificarProdutoController;
        int nLinha = 1;

        String next[];
        while (scanner.hasNext()) {
            nLinha++;

            especificarProdutoController = new EspecificarProdutoController(substituirSeExistir);
            next = scanner.next();

            if (next.length != N_CAMPOS) {
                transformer.addFalha(nLinha, "A linha não tem o número de campos esperado");
                continue;
            }

            try {
                especificarProdutoController.setCodigoUnico(next[INDEX_CODIGO_FABRICO]);
                especificarProdutoController.setCategoriaDeProduto(next[INDEX_CATEGORIA]);
                especificarProdutoController.setCodigoComercial(next[INDEX_CODIGO_COMERCIAL]);
                especificarProdutoController.setDescricaoBreve(next[INDEX_DESCRICAO_BREVE]);
                especificarProdutoController.setDescricaoCompleta(next[INDEX_DESCRICAO_COMPLETA]);
                especificarProdutoController.setUnidadeDeMedida(next[INDEX_UNIDADE]);

                if (!especificarProdutoController.register()) {
                    transformer.addFalha(nLinha, "*** ERRO INTERNO: Nem todos os parâmetros do produto foram especificados\n\n");
                }
                else {
                    transformer.incrementarSucessos();
                }
            } catch (IllegalDomainValueException illegalDomainValueException) {
                transformer.addFalha(nLinha, illegalDomainValueException.getMessage());
                transformer.incrementarFalhas();
            }
        }

        return transformer.gerarDTO();
    }
}

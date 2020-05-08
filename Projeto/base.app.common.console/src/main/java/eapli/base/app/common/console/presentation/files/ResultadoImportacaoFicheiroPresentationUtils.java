package eapli.base.app.common.console.presentation.files;

import eapli.base.produto.application.ResultadoImportacaoFicheiro;

import java.util.Map;

public class ResultadoImportacaoFicheiroPresentationUtils {

    public static String construirMensagemResultado(ResultadoImportacaoFicheiro resultado) {
        StringBuilder sb = new StringBuilder();
        sb.append("Número de produtos importados com sucesso: ").append(resultado.nSucessos)
                .append("\nNúmero de falhas: ").append(resultado.nFalhas);

        if (resultado.erros.size() > 0) {
            sb.append("\n\nAvisos:");
            for (Map.Entry<Integer, String> erro : resultado.erros.entrySet()) {
                sb.append("\nLinha nº").append(erro.getKey()).append(": ").append(erro.getValue());
            }
        }
        return sb.toString();
    }
}

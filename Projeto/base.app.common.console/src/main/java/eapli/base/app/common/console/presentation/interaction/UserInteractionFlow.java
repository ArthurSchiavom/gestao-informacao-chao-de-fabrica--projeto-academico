package eapli.base.app.common.console.presentation.interaction;

import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteractionFlow {
    public static void enterParaContinuar() {
        System.out.println("\n\nPresssione enter para continuar\n");
        new Scanner(System.in).nextLine();
    }

    public static int[] solicitarListaInteiros(String prompt, String separador) {
        boolean inputValido = false;
        String valoresRaw;
        String[] valoresRawSeparados;
        int[] valores = null;
        while (!inputValido) {
            valoresRaw = Console.readLine(prompt).trim();
            if (valoresRaw.isEmpty()) {
                continue;
            }
            valoresRawSeparados = valoresRaw.split(separador);

            valores = new int[valoresRawSeparados.length];
            int index = 0;
            boolean listaValida = true;
            for (String valorRaw : valoresRawSeparados) {
                valorRaw = valorRaw.trim();
                try {
                    valores[index++] = Integer.parseInt(valorRaw);
                } catch (NumberFormatException e) {
                    System.out.printf("Número inválido: %s. Por favor apenas introduza números separados por vírgulas como mostrado a seguir: 1,2,3,4\n", valorRaw);
                    listaValida = false;
                    break;
                }
            }

            inputValido = listaValida;
        }

        return valores;
    }

    public static <T> List<T> solicitarEscolhaItensSeparadosPorVirgula(String promptRepetitivo, String separador, String mensagemInputForaDosLimites, List<T> opcoesOrdenadas, int numero_primeira_opcao, boolean usarWildcard, int wildcard) {
        List<T> resultado = null;

        int limiteInferior = numero_primeira_opcao;
        int limiteSuperior = limiteInferior + opcoesOrdenadas.size() - 1;

        boolean valido = false;
        while (!valido) {
            int[] valores = solicitarListaInteiros(promptRepetitivo, separador);
            resultado = new ArrayList<>();

            if (usarWildcard && valores[0] == wildcard) {
                resultado = new ArrayList<>(opcoesOrdenadas);
                break;
            }

            boolean dentroDaRange = true;
            for (int val : valores) {
                if (val < limiteInferior || val > limiteSuperior) {
                    System.out.println(mensagemInputForaDosLimites);
                    dentroDaRange = false;
                    break;
                }

                resultado.add(opcoesOrdenadas.get(val - numero_primeira_opcao));
            }

            valido = dentroDaRange;
        }

        return resultado;
    }

    public static <T> List<T> solicitarEscolhaItensSeparadosPorVirgula(String primeiroPrompt, String promptRepetitivo, String separador, String mensagemInputForaDosLimites, List<T> opcoesOrdenadas, int numero_primeira_opcao, boolean usarWildcard, int wildcard) {
        System.out.println(primeiroPrompt + "\n");
        return solicitarEscolhaItensSeparadosPorVirgula(promptRepetitivo, separador, mensagemInputForaDosLimites, opcoesOrdenadas, numero_primeira_opcao, usarWildcard, wildcard);
    }
}

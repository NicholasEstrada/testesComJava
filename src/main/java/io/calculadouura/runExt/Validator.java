package io.calculadouura.runExt;

import java.util.HashMap;
import java.util.List;

public class Validator {
    private static Float INDICE_LEVENSHTEIN = 0.9F;

    public static String validar(String conteudoArquivo) {
        String[] stringsConteudo= conteudoArquivo.split(";");
        StringBuilder resultado = new StringBuilder();

        List<PadraoValidacao> padroes = PadraoValidacao.mockCarregarPadroes();

        for (PadraoValidacao padrao : padroes) {
            HashMap<ExpressaoValidacao, Boolean> validacaoHashMap = new HashMap<>();

            for (ExpressaoValidacao expressaoValidacao : padrao.getExpressoes()) {

                validacaoHashMap.putIfAbsent(expressaoValidacao, false);

                if (expressaoValidacao.getExpressao().contains(";")) {
                    String[] conteudoArquivoSplit = conteudoArquivo.split(";");
                    String[] expressaoValidacaoSplit = expressaoValidacao.getExpressao().split(";");

                    boolean encontrouSequencia = false;

                    for (int i = 0; i < conteudoArquivoSplit.length; i++) {
                        double similaridade = calcularSimilaridadeLevenshtein(expressaoValidacaoSplit[0], conteudoArquivoSplit[i]);

                        // Se a primeira palavra for similar, verificar as próximas
                        if (similaridade >= INDICE_LEVENSHTEIN) {
                            encontrouSequencia = true;

                            // Verificar as próximas palavras da expressão
                            for (int j = 1; j < expressaoValidacaoSplit.length; j++) {
                                if (i + j >= conteudoArquivoSplit.length) {
                                    // Se não houver mais conteúdo para comparar, interromper
                                    encontrouSequencia = false;
                                    break;
                                }

                                double similaridadeProxima = calcularSimilaridadeLevenshtein(expressaoValidacaoSplit[j], conteudoArquivoSplit[i + j]);

                                if (similaridadeProxima < INDICE_LEVENSHTEIN) {
                                    // Se uma das próximas palavras não corresponder, interromper
                                    encontrouSequencia = false;
                                    break;
                                }
                            }

                            if (encontrouSequencia) {
                                validacaoHashMap.put(expressaoValidacao, true);
                                break; // Encontramos uma sequência válida, podemos parar
                            }
                        }
                    }

                    if (!encontrouSequencia) {
                        validacaoHashMap.put(expressaoValidacao, false);
                    }
                }

                if (expressaoValidacao.isRegex()) {
                    for (String conteudo : stringsConteudo){
                        if(conteudo.matches(expressaoValidacao.getExpressao())) validacaoHashMap.put(expressaoValidacao, true);
                    }

                } else if(!expressaoValidacao.getExpressao().contains(";")){
                    for(String conteudo : stringsConteudo){
                        double similaridade = calcularSimilaridadeLevenshtein(expressaoValidacao.getExpressao(), conteudo);
                        if (similaridade < INDICE_LEVENSHTEIN) validacaoHashMap.put(expressaoValidacao, true);
                    }

                }
            }

            //iterator ALTERAR LOCAL DE RETURN EM CASO DE HAVER MAIS PADROES

            boolean allTrue = validacaoHashMap.values().stream().allMatch(Boolean::booleanValue);
            if(allTrue) resultado.append(padrao.getNomePadrao()).append(";");

        }

        return resultado.toString();
    }

    private static double calcularSimilaridadeLevenshtein(String str1, String str2) {
        int distancia = distanciaLevenshtein(str1, str2);
        int maxLen = Math.max(str1.length(), str2.length());
        return 1.0 - (double) distancia / maxLen;
    }

    private static int distanciaLevenshtein(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1] + cost,
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }

        return dp[len1][len2];
    }
}

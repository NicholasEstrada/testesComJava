package io.calculadouura.runExt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Validator {

    public static String validar(String conteudoArquivo) {
        String[] stringsConteudo= conteudoArquivo.split(";");
        StringBuilder resultado = new StringBuilder();

        List<PadraoValidacao> padroes = PadraoValidacao.mockCarregarPadroes();

        for (PadraoValidacao padrao : padroes) {
            HashMap<ExpressaoValidacao, Boolean> validacaoHashMap = new HashMap<>();

            for (ExpressaoValidacao expressaoValidacao : padrao.getExpressoes()) {

                if(validacaoHashMap.get(expressaoValidacao) == null) validacaoHashMap.put(expressaoValidacao, false);

                if (expressaoValidacao.getExpressao().contains(";")){
                    // IDEIA implementar algo semelhante ao "public String getTipoPlanilha"
                    if (conteudoArquivo.contains(expressaoValidacao.getExpressao())) {
                        validacaoHashMap.put(expressaoValidacao, true);
                    }
                }

                if (expressaoValidacao.isRegex()) {
                    for (String conteudo : stringsConteudo){
                        if(conteudo.matches(expressaoValidacao.getExpressao())) validacaoHashMap.put(expressaoValidacao, true);
                    }

                } else if(!expressaoValidacao.getExpressao().contains(";")){
                    for(String conteudo : stringsConteudo){
                        double similaridade = calcularSimilaridadeLevenshtein(expressaoValidacao.getExpressao(), conteudo);
                        if (similaridade < 0.90) validacaoHashMap.put(expressaoValidacao, true);
                    }

                }
            }

            //iterator ALTERAR LOCAL DE RETURN EM CASO DE HAVER MAIS PADROES

            boolean allTrue = validacaoHashMap.values().stream().allMatch(Boolean::booleanValue);
            if(allTrue) resultado.append(padrao.getNomePadrao()).append(";");

            /*try {
                Iterator<ExpressaoValidacao> itValidacoes = validacaoHashMap.keySet().iterator();
                while (itValidacoes.hasNext()) {

                    ExpressaoValidacao keyer = itValidacoes.next();
                    Boolean resultado = validacaoHashMap.get(keyer);

                    System.out.println("resultado: "+resultado + " |HUMMMM: " + keyer.getExpressao());

                    if(!resultado){
                        return "Validação não encontrou os termos: " + keyer.getExpressao();
                    }
                }
            } catch (Exception e) {}


            return "Validado como: " + padrao.getNomePadrao();*/
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

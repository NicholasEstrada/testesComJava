package io.calculadouura;

public class PalavraValidador {

    public static String validarPalavra(String original, String comparada) {
        if (original.equals(comparada)) {
            return "Completamente Igual";
        } else if (original.contains(comparada) || comparada.contains(original)) {
            return "Parcialmente Igual";
        } else if (similaridadeFraca(original, comparada)) {
            return "Fraca";
        } else {
            return "Nada Igual";
        }
    }

    private static boolean similaridadeFraca(String original, String comparada) {
        // Verificar se uma subsequência significativa está presente
        // Você pode usar técnicas como LCS (Longest Common Subsequence) ou comparar prefixos/sufixos
        int minLength = Math.min(original.length(), comparada.length());

        // Defina um limiar para considerar como fraco
        int threshold = minLength / 2;

        // Contagem de correspondências
        int matchCount = 0;

        for (int i = 0; i < minLength; i++) {
            if (original.charAt(i) == comparada.charAt(i)) {
                matchCount++;
            }
        }

        return matchCount >= threshold;
    }

    public static void main(String[] args) {
        String original = "descricaododocumento";
        String comparada1 = "descricaododocumento";
        String comparada2 = "descricododocumento";
        String comparada3 = "documentododescricao";
        String comparada4 = "documento";

        System.out.println(validarPalavra(original, comparada1)); // Completamente Igual
        System.out.println(validarPalavra(original, comparada2)); // Parcialmente Igual
        System.out.println(validarPalavra(original, comparada3)); // Fraca
        System.out.println(validarPalavra(original, comparada4)); // Nada Igual
    }
}

package io.calculadouura;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class TextoValidador {

    public static String validarTexto(String original, String comparado) {
        LevenshteinDistance ld = new LevenshteinDistance();

        // Calcula a distância de Levenshtein entre os textos
        int distancia = ld.apply(original, comparado);

        // Determina o nível de similaridade baseado na distância e no comprimento do texto original
        double similaridade = 1.0 - ((double) distancia / Math.max(original.length(), comparado.length()));

        if (similaridade == 1.0) {
            return String.format("Nada Igual (Similaridade: %.2f)",similaridade);
        } else if (similaridade >= 0.7) {
            return String.format("Nada Igual (Similaridade: %.2f)",similaridade);
        } else if (similaridade >= 0.4) {
            return String.format("Nada Igual (Similaridade: %.2f)",similaridade);
        } else {
            return String.format("Nada Igual (Similaridade: %.2f)",similaridade);
        }
    }

    public static void main(String[] args) {
        String textoOriginal = "https://luzerna.ifc.edu.br/events/mes/2081-02/";
        String textoComparado1 = "https://luzerna.ifc.edu.br/events/mes/2081-03/";
        String textoComparado2 = "https://luzerna.ifc.edu.br/events/mes/2081-04/";
        String textoComparado3 = "https://luzerna.ifc.edu.br/events/mes/2081-05/";
        String textoComparado4 = "https://luzerna.ifc.edu.br/events/mes/2081-06/";
        String textoComparado5 = "https://luzerna.ifc.edu.br/events/mes/2081-07/";
        String textoComparado6 = "https://luzerna.ifc.edu.br/events/mes/2081-08/";
        String textoComparado7 = "https://luzerna.ifc.edu.br/events/mes/2081-09/";
        String textoComparado8 = "https://luzerna.ifc.edu.br/events/mes/2081-10/";

        System.out.println(validarTexto(textoOriginal, textoComparado1)); // Completamente Igual
        System.out.println(validarTexto(textoOriginal, textoComparado2)); // Parcialmente Igual
        System.out.println(validarTexto(textoOriginal, textoComparado3)); // Fraca
        System.out.println(validarTexto(textoOriginal, textoComparado4)); // Nada Igual
        System.out.println(validarTexto(textoOriginal, textoComparado5)); // Nada Igual
        System.out.println(validarTexto(textoOriginal, textoComparado6)); // Nada Igual
        System.out.println(validarTexto(textoOriginal, textoComparado7)); // Nada Igual
        System.out.println(validarTexto(textoOriginal, textoComparado8)); // Nada Igual
    }
}

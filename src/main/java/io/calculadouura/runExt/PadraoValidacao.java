package io.calculadouura.runExt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PadraoValidacao {
    private String nomePadrao;
    private String categoria;
    private List<ExpressaoValidacao> expressoes;

    // Lista de padrões de validação estática para testes
    private static List<PadraoValidacao> padroes = new ArrayList<>();

    // Getters e Setters
    public String getNomePadrao() {
        return nomePadrao;
    }

    public void setNomePadrao(String nomePadrao) {
        this.nomePadrao = nomePadrao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<ExpressaoValidacao> getExpressoes() {
        return expressoes;
    }

    public void setExpressoes(List<ExpressaoValidacao> expressoes) {
        this.expressoes = expressoes;
    }

    // Método para obter os padrões de validação
    public static List<PadraoValidacao> getPadroes() {
        return padroes;
    }

    // Método para definir os padrões de validação (para testes)
    public static void setPadroes(List<PadraoValidacao> padroes) {
        PadraoValidacao.padroes = padroes;
    }

    // Método para simular o carregamento dos padrões
    public static List<PadraoValidacao> mockCarregarPadroes() {
        List<PadraoValidacao> padroes = new ArrayList<>();

        // Criar expressões de validação simuladas
        ExpressaoValidacao regexExpressao = new ExpressaoValidacao("^[a-zA-Z]+$", true);
        ExpressaoValidacao levenshteinExpressao = new ExpressaoValidacao("example", false);
        ExpressaoValidacao cabecalhoExpressao = new ExpressaoValidacao("data;valor;documento", false);

        // Criar um padrão de validação e adicionar expressões a ele
        PadraoValidacao padraoValidacao = new PadraoValidacao();
        padraoValidacao.setNomePadrao("Padrão Teste");
        padraoValidacao.setCategoria("Teste");

        List adicionarExpressoes = new ArrayList<>();
        adicionarExpressoes.add(regexExpressao);
        adicionarExpressoes.add(levenshteinExpressao);
        adicionarExpressoes.add(cabecalhoExpressao);

        padraoValidacao.setExpressoes(adicionarExpressoes);

        padroes.add(padraoValidacao);

        // TESTE COM TRIBANCO
        ExpressaoValidacao tribancolevenshteinExpressao = new ExpressaoValidacao("TRIBANCOONLINE.COM.BR", false);
        ExpressaoValidacao tribancolevenshteinExpressao2 = new ExpressaoValidacao("TRIBANCO ONLINE", false);
        ExpressaoValidacao tribancoCabecalhoExpressao = new ExpressaoValidacao("DATA;MOVIMENTACAO;TIPO;DOCUMENTO;VALOR", false);
        ExpressaoValidacao tribancoCabecalhoExpressao2 = new ExpressaoValidacao("HOME;CONTA CORRENTE;EXTRATO", false);

        PadraoValidacao padraoValidacaotribanco = new PadraoValidacao();
        padraoValidacaotribanco.setNomePadrao("Extrato_Tribanco_PDF");
        padraoValidacaotribanco.setCategoria("EXTRATO");


        List adicionarExpressoestribanco = new ArrayList<>();
        adicionarExpressoestribanco.add(tribancolevenshteinExpressao);
        adicionarExpressoestribanco.add(tribancolevenshteinExpressao2);
        adicionarExpressoestribanco.add(tribancoCabecalhoExpressao);
        adicionarExpressoestribanco.add(tribancoCabecalhoExpressao2);

        padraoValidacaotribanco.setExpressoes(adicionarExpressoestribanco);

        padroes.add(padraoValidacaotribanco);

        return padroes;
    }


    public static List<PadraoValidacao> carregarPadroesDeJson(String filePath) {
        Gson gson = new Gson();
        List<PadraoValidacao> padroes = null;
        try (FileReader reader = new FileReader(filePath)) {
            Type padraoListType = new TypeToken<List<PadraoValidacao>>() {}.getType();
            padroes = gson.fromJson(reader, padraoListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return padroes;
    }
}

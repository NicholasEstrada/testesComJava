package io.calculadouura.runExt;

class ExpressaoValidacao {
    private String expressao;
    private boolean isRegex;

    public ExpressaoValidacao(String expressao, boolean isRegex) {
        this.expressao = expressao;
        this.isRegex = isRegex;
    }

    public boolean isRegex() {
        return isRegex;
    }

    public String getExpressao() {
        return expressao;
    }

}

package org.example.Model;

public class Cotacao {
    private Moeda moeda;
    private boolean sucesso;
    private String mensagemErro;

    public Cotacao() {}

    public Cotacao(Moeda moeda) {
        this.moeda = moeda;
        this.sucesso = true;
    }

    public Cotacao(String mensagemErro) {
        this.mensagemErro = mensagemErro;
        this.sucesso = false;
    }

    public Moeda getMoeda() { return moeda; }
    public void setMoeda(Moeda moeda) { this.moeda = moeda; }

    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

    public String getMensagemErro() { return mensagemErro; }
    public void setMensagemErro(String mensagemErro) { this.mensagemErro = mensagemErro; }
}
package org.example.Controller;

import org.example.Model.Moeda;
import org.example.Model.Cotacao;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class CotacaoController {
    private static final String API_BASE_URL = "https://economia.awesomeapi.com.br/json/last/";
    private final HttpClient httpClient;

    public CotacaoController() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public Cotacao buscarCotacao(String codigoMoeda) {
        try {

            String par = codigoMoeda.toUpperCase() + "-BRL";
            String url = API_BASE_URL + par;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {
                String json = response.body();
                Moeda moeda = parseJSON(json, par.replace("-", ""));

                if (moeda != null) {
                    return new Cotacao(moeda);
                } else {
                    return new Cotacao("Erro ao processar dados da moeda");
                }
            } else if (response.statusCode() == 404) {
                return new Cotacao("Moeda não encontrada. Verifique o código informado.");
            } else {
                return new Cotacao("Erro na API: Status " + response.statusCode());
            }

        } catch (java.net.ConnectException e) {
            return new Cotacao("Erro de conexão. Verifique sua internet.");
        } catch (java.net.http.HttpTimeoutException e) {
            return new Cotacao("Tempo limite de conexão excedido.");
        } catch (Exception e) {
            return new Cotacao("Erro ao buscar cotação: " + e.getMessage());
        }
    }

    private Moeda parseJSON(String json, String chave) {
        try {

            json = json.trim();

            int inicio = json.indexOf("\"" + chave + "\"");
            if (inicio == -1) return null;

            inicio = json.indexOf("{", inicio);
            int fim = json.indexOf("}", inicio) + 1;
            String objetoMoeda = json.substring(inicio, fim);

            Moeda moeda = new Moeda();

            moeda.setCode(extrairValor(objetoMoeda, "code"));
            moeda.setName(extrairValor(objetoMoeda, "name"));
            moeda.setHigh(extrairValorNumerico(objetoMoeda, "high"));
            moeda.setLow(extrairValorNumerico(objetoMoeda, "low"));
            moeda.setVarBid(extrairValorNumerico(objetoMoeda, "varBid"));
            moeda.setPctChange(extrairValorNumerico(objetoMoeda, "pctChange"));
            moeda.setBid(extrairValorNumerico(objetoMoeda, "bid"));
            moeda.setAsk(extrairValorNumerico(objetoMoeda, "ask"));
            moeda.setTimestamp(extrairValor(objetoMoeda, "timestamp"));
            moeda.setCreateDate(extrairValor(objetoMoeda, "create_date"));

            return moeda;

        } catch (Exception e) {
            System.err.println("Erro ao fazer parse do JSON: " + e.getMessage());
            return null;
        }
    }

    private String extrairValor(String json, String campo) {
        try {
            String busca = "\"" + campo + "\"";
            int inicio = json.indexOf(busca);
            if (inicio == -1) return "";

            inicio = json.indexOf(":", inicio) + 1;
            inicio = json.indexOf("\"", inicio) + 1;
            int fim = json.indexOf("\"", inicio);

            return json.substring(inicio, fim);
        } catch (Exception e) {
            return "";
        }
    }

    private double extrairValorNumerico(String json, String campo) {
        try {
            String valor = extrairValor(json, campo);
            return Double.parseDouble(valor);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public String[] getMoedasDisponiveis() {
        return new String[]{
                "USD - Dólar Americano",
                "EUR - Euro",
                "GBP - Libra Esterlina",
                "JPY - Iene Japonês",
                "CAD - Dólar Canadense",
                "AUD - Dólar Australiano",
                "CHF - Franco Suíço",
                "ARS - Peso Argentino",
                "BTC - Bitcoin"
        };
    }
}
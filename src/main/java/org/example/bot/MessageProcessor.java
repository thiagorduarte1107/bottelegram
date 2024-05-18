package org.example.service;

import org.json.JSONObject;

public class MessageProcessor {

    private final BetanoService betanoService;

    public MessageProcessor() {
        this.betanoService = new BetanoService();
    }

    public String processMessage(String message) {
        if (message.equalsIgnoreCase("futebol")) {
            try {
                JSONObject footballData = betanoService.getFootballData();
                // Processa os dados do futebol e gera uma resposta
                return parseFootballData(footballData);
            } catch (Exception e) {
                return "Erro ao obter dados de futebol: " + e.getMessage();
            }
        }
        return "Você disse: " + message;
    }

    private String parseFootballData(JSONObject footballData) {
        // Exemplo de processamento de dados. Ajuste conforme a estrutura real da resposta da API da Betano.
        return "Dados de futebol: " + footballData.toString();
    }
}

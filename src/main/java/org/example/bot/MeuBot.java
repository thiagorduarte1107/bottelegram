package org.example;

import lombok.extern.slf4j.Slf4j; // Importa a biblioteca Lombok para logging
import org.telegram.telegrambots.bots.TelegramLongPollingBot; // Importa a classe base para bots de polling longo
import org.telegram.telegrambots.meta.api.methods.send.SendMessage; // Importa a classe para enviar mensagens
import org.telegram.telegrambots.meta.api.objects.Update; // Importa a classe que representa uma atualização do Telegram
import org.telegram.telegrambots.meta.exceptions.TelegramApiException; // Importa a classe para exceções da API do Telegram
import org.telegram.telegrambots.meta.TelegramBotsApi; // Importa a classe para inicializar a API de bots do Telegram
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession; // Importa a classe para a sessão padrão dos bots

@Slf4j // Anotação do Lombok para gerar um logger
public class MeuBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Método chamado quando uma atualização é recebida
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Verifica se a atualização contém uma mensagem com texto
            String messageText = update.getMessage().getText(); // Obtém o texto da mensagem
            Long chatId = update.getMessage().getChatId(); // Obtém o ID do chat
            String response = processMessage(messageText); // Processa a mensagem recebida
            sendResponse(chatId.toString(), response); // Envia a resposta
        }
    }

    private String processMessage(String message) {
        // Processa a mensagem recebida e gera uma resposta
        return "Você disse: " + message;
    }

    private void sendResponse(String chatId, String response) {
        // Envia uma mensagem de resposta ao chat especificado
        SendMessage message = new SendMessage();
        message.setChatId(chatId); // Define o ID do chat para enviar a mensagem
        message.setText(response); // Define o texto da mensagem
        try {
            execute(message); // Executa o envio da mensagem
            log.info("Resposta enviada com sucesso para o chat ID: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Erro ao enviar mensagem para o chat ID {}: {}", chatId, e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        // Obtém o token do bot a partir das variáveis de ambiente
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public String getBotUsername() {
        // Obtém o nome de usuário do bot a partir das variáveis de ambiente
        return System.getenv("TELEGRAM_BOT_USERNAME");
    }

    public void init() throws TelegramApiException {
        // Inicializa a API do Telegram Bots
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        // Registra o bot
        botsApi.registerBot(this);
        log.info("Bot iniciado com sucesso!");
    }

    public static void main(String[] args) {
        // Método principal para iniciar o bot
        try {
            MeuBot bot = new MeuBot(); // Cria uma nova instância do bot
            bot.init(); // Chama o método init para inicializar o bot
        } catch (Exception e) {
            log.error("Erro ao iniciar o bot: {}", e.getMessage());
        }
    }
}

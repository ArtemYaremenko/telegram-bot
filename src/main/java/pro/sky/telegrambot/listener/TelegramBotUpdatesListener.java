package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.service.UpdateService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private UpdateService service;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            Long chatId = update.message().chat().id();
            String message = update.message().text();
            if (message.equals("/start")) {
                telegramBot.execute(new SendMessage(chatId, "Hello!"));
            }
            Matcher matcher = service.datePattern.matcher(message);
            if (!service.updateSave(chatId, matcher)) {
                telegramBot.execute(new SendMessage(chatId, "Input - {dd.MM.yyyy HH:mm some text}"));
            }
           service.run();
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
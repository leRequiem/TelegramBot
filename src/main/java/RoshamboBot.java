import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RoshamboBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "itis_roshambobot";
    }

    @Override
    public String getBotToken() {
        return "6528185437:AAFFVRZTp-2q5vZmX4hp0fp4koirfVe3too";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        MessageSender messageSender = new MessageSender(message, this);
        new Thread(messageSender).start();
    }
}

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender implements Runnable {

    private Message message;
    private Long who;
    private TelegramLongPollingBot bot;
    private String[] signs = {"камень", "ножницы", "бумага"};
    private String helpMessage = "В традиционной игре всего существует 3 знака - Камень, Ножницы и Бумага. Рассмотрим их взаимоотношения (Для упрощения скажем, что знаки \">\" - Победа, \"<\" - Поражение, \"=\" - Ничья). \n(Камень > Ножницы)   (Камень < Бумага)   (Камень = Камень) \n(Ножницы > Бумага)   (Ножницы < Камень)   (Ножницы = Ножницы) \n(Бумага > Камень)   (Бумага < Ножницы)   (Бумага = Бумага)";
    private String startMessage = "Привет, я бот, который любит поиграть в игру \"Камень-Ножницы-Бумага\", напиши название выбранного тобой знака. Для получения списка всех знаков и правил - напиши /help";

    public MessageSender(Message message, TelegramLongPollingBot bot){
        this.who = message.getChatId();
        this.bot = bot;
        this.message = message;
    }

    @Override
    public void run() {

        if (message.isCommand()) {
            System.out.println(message.getText() + " is command");

            if (message.getText().equals("/start")) {
                sendText(who, startMessage, bot);
            }
            if (message.getText().equals("/help")) {
                sendText(who, helpMessage, bot);
            }
        }

        else if (!message.isCommand() && message.getText().matches("[А-Яа-я]*")) {
            String word = message.getText().toLowerCase();
            int randomSignNumber = (int) (Math.random() * 10000 % 3); // generates num from 0 to 2, which will be sign number from signs[]
            String randomlyGeneratedSign = signs[randomSignNumber];

            if (word.equals("камень")) {
                if (randomlyGeneratedSign.equals("камень")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nНичья! ", bot);
                }
                else if (randomlyGeneratedSign.equals("ножницы")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nВы победили!", bot);
                }
                else if (randomlyGeneratedSign.equals("бумага")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nВы проиграли!", bot);
                }
            }

            else if (word.equals("ножницы")) {
                if (randomlyGeneratedSign.equals("ножницы")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nНичья!", bot);
                }
                else if (randomlyGeneratedSign.equals("бумага")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nВы победили!", bot);
                }
                else if (randomlyGeneratedSign.equals("камень")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nВы проиграли!", bot);
                }
            }

            else if (word.equals("бумага")) {
                if (randomlyGeneratedSign.equals("бумага")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nНичья!", bot);
                }
                else if (randomlyGeneratedSign.equals("камень")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nВы победили!", bot);
                }
                else if (randomlyGeneratedSign.equals("ножницы")) {
                    sendText(who, "" + randomlyGeneratedSign + "\nВы проиграли!", bot);
                }
            }
            else sendText(who, "Отправлен неизвестный знак!", bot);
        }
        else sendText(who, "Ваше сообщение не удалось расшифровать!", bot);

        System.out.println(Thread.currentThread().threadId() + "," +
                message.getText() + ", " +
                message.getFrom().getUserName());
    }

    public void sendText(Long who, String what, TelegramLongPollingBot bot) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try {
            bot.execute(sm);
        } catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
}

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LucasTelegranBot extends TelegramLongPollingBot {

    Boolean isDepositing = false;
    Boolean isWithdrawing = false;
    Boolean isBalancing = false;
    Integer accountBalance = 0;

    /**
     * @param update
     */
    public void onUpdateReceived(Update update) {

//        System.out.println(update.getMessage().getText());
//        System.out.println(update.getMessage().getFrom().getFirstName());

        String command = update.getMessage().getText();
        String value = new String();

        SendMessage message = new SendMessage();

        if(isDepositing){
            value = update.getMessage().getText();
            accountBalance += Integer.parseInt(value);
//            System.out.println(Integer.parseInt(value));
            isDepositing = false;
            message.setText("your deposit of $"+ value +" was successful");
        }

        if(isWithdrawing){
            value = update.getMessage().getText();
            if(Integer.parseInt(value) < 0) {
                message.setText("You can't withdraw a value less than zero");
            } else {
                accountBalance -= Integer.parseInt(value);
                if (accountBalance < 0 ){
                    accountBalance += Integer.parseInt(value);
                    message.setText("You don't have enough, your balance is $"+ accountBalance);
                } else {
                    message.setText("Here's your $"+ update.getMessage().getText() +", thanks for using our service");
                }
            }
            isWithdrawing = false;
        }


        if(command.equals("/deposit")){
//            System.out.println(update.getMessage().getFrom().getFirstName());
            message.setText("how mutch do you want to deposit?");
            isDepositing = true;
//            message.setText(update.getMessage().getFrom().getFirstName());
        }


        if(command.equals("/withdraw")){
            message.setText("how mutch do you want to withdraw?");
            isWithdrawing = true;
//            System.out.println("500");
//            System.out.println(update.getMessage().getFrom().getLastName());
//
//            message.setText(update.getMessage().getFrom().getLastName());
        }

        if(command.equals("/balance")){
             message.setText("you already have $"+ accountBalance +" in your account");
//             System.out.println("500");
//            System.out.println(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
//            message.setText(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
        }

        message.setChatId(update.getMessage().getChatId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "lucastelegran_bot";
    }

    public String getBotToken() {
        return "1006876122:AAFXasJAcf_AOZ9mP0FuXJD2_JeremzN0Wc";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpublicita;

import TelegramApi.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author skorolitniy_daniel
 */
public class BotPublicita {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        String Token = "5289178489:AAGNqdWsbQcFq5Wbg4agZ9AQjr8bGTBQ_NA";
        GestisciTelegram TeleApi=new GestisciTelegram(Token);
        while(true)
        {
            TeleApi.GetUpdates();
            TeleApi.GetAllMessageWithCitta();
            Thread.sleep(10000);
        }
        
    }
    
}

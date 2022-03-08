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
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String Token = "5289178489:AAGNqdWsbQcFq5Wbg4agZ9AQjr8bGTBQ_NA";
        JSONfile json=new JSONfile();
        json.CreaFile("GetUpdates.txt",Token);
        List<String> id = json.getChatID("GetUpdates.txt");
        for(int i=0;i<id.size();i++)
        {
            json.SendMessage(Token, id.get(i), "scherzo ti voglio bene");
        }
        
    }
    
}

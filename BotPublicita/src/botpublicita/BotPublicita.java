/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpublicita;

import TelegramApi.*;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author skorolitniy_daniel
 */
public class BotPublicita {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        // TODO code application logic here
        String Token = "5289178489:AAGNqdWsbQcFq5Wbg4agZ9AQjr8bGTBQ_NA";
        GestisciTelegram TeleApi=new GestisciTelegram(Token);
        OpenStreetMap osm= new OpenStreetMap();
        osm.myGetLocation("via brusa 6");
        List<JSONObject> ciao = TeleApi.GetAllMessageWith("ciao");
        String ciao1="ciao";
        if(ciao1 == "")
        {}
        
    }
    
}

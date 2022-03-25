/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TelegramApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author skoro
 */
public class GestisciTelegram {

    private String uriBase = "https://api.telegram.org/bot";
    private List<Persona> VetPersone = new ArrayList<Persona>();
    private String Token;
    private ReadWriteFile rw = new ReadWriteFile();

    public GestisciTelegram(String Token) throws IOException {
        this.Token = Token;
    }

    public GestisciTelegram() throws IOException {
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public void GetUpdates() throws MalformedURLException, IOException {
        URL url = new URL(uriBase + Token + "/getUpdates");
        rw.ScriviSuFileURL(url, "GetUpdates.txt");
    }

    public void SendMessageAll(String text) throws MalformedURLException, IOException {
        List<String> ChatID = getChatID();
        for (int i = 0; i < ChatID.size(); i++) {
            SendMessage(ChatID.get(i), text);
        }
    }

    public void SendMessage(String Chat_id, String text) throws MalformedURLException, IOException {
        String ris = uriBase + Token + "/sendMessage" + "?chat_id=" + Chat_id + "&text=" + text;
        URL url = new URL(ris);
        rw.ScriviSuFileURL(url, "RisultatoSendMessage.txt");
    }

    public List<String> getChatID() throws IOException {
        List<String> Ris = new ArrayList();
        //leggo il file
        String jsonString = rw.LeggiDaFile("GetUpdates.txt");
        //parso il file
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("result");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj1 = new JSONObject(arr.get(i).toString());
            JSONObject message = obj1.getJSONObject("message");
            JSONObject chat = message.getJSONObject("chat");
            int id = chat.getInt("id");
            System.out.println(id);
            Ris.add(String.valueOf(id));
        }
        return Ris;
    }

    public List<String> GetAllMessageWith(String text) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        
        List<String> Ris = new ArrayList();
        String jsonString = rw.LeggiDaFile("GetUpdates.txt");

        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("result");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject update = arr.getJSONObject(i);
            JSONObject mess = update.getJSONObject("message");
            rw.ScriviSuFileAppend("MessageWith.txt", mess.toString());
            Pubblicita(update, text);
        }
        return Ris;
    }

    public int VetPersoneContainsGetPOS(String i) {
        int pos = -1;

        for (int j = 0; j < VetPersone.size(); j++) {
            if (VetPersone.get(j).getIdChat().equals(i)) {
                pos = j;
            }
        }
        return pos;
    }

    public void VetPersonetoCSV() {
        try {

            FileWriter fw = new FileWriter("Persone.csv");
            for (int i = 0; i < VetPersone.size(); i++) {
                String p = VetPersone.get(i).toCSV() + "\r\n";
                fw.write(p);
            }

            fw.flush();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(GestisciTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CaricaVetPersonaFromCSV() {
        try {

            VetPersone.clear();

            FileReader fr = new FileReader("Persone.csv");
            BufferedReader br = new BufferedReader(fr);

            String riga;
            while ((riga = br.readLine()) != null) {
                String[] elementi = riga.split(";");

                String id = elementi[0];
                int IDLastMessage = Integer.valueOf(elementi[1]);
                Place place = new Place();
                place.setLat(elementi[2]);
                place.setLon(elementi[3]);

                Persona p = new Persona(id, IDLastMessage, place);
                VetPersone.add(p);
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestisciTelegram.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestisciTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Pubblicita(JSONObject update, String text) throws ParserConfigurationException, SAXException, IOException {

        JSONObject mess = update.getJSONObject("message");
        JSONObject chat = mess.getJSONObject("chat");
        int id_lastMessage = mess.getInt("message_id");
        
        if (mess.getString("text").contains(text)) {
            //controllo se esiste gia una persona con questo id
            int pos = VetPersoneContainsGetPOS(String.valueOf(chat.getInt("id")));
            if (pos!=-1) {
                //prendo la persona
                Persona temp = VetPersone.get(pos);
                //guardo l'ultimo id del lastmessage
                if (temp.getIDLastMessage() < id_lastMessage) {
                    //modifico la persona
                    temp.setIDLastMessage(id_lastMessage);
                    Place p = OpenStreetMap.myGetLocation(mess.getString("text").substring(text.length() + 1));
                    temp.setPlace(p);
                    VetPersone.set(pos, temp);
                }
                //se non esiste nel vettore creo la persona
            } else {
                Persona temp = new Persona();
                temp.setIDLastMessage(id_lastMessage);
                Place p = OpenStreetMap.myGetLocation(mess.getString("text").substring(text.length() + 1));
                temp.setPlace(p);
                temp.setIdChat(String.valueOf(chat.getInt("id")));
                VetPersone.add(temp);
            }
        }
    }

    public void InviaPubblicita(String citta, double raggio, String messaggio) throws ParserConfigurationException, SAXException, IOException {
        Place p = OpenStreetMap.myGetLocation(citta);
        double tempDistanza = 0;
        for (int j = 0; j < VetPersone.size(); j++) {
            tempDistanza = OpenStreetMap.calcolaDistanza(p, VetPersone.get(j).getPlace());
            if (tempDistanza <= raggio) {
                SendMessage(VetPersone.get(j).getIdChat(), messaggio);
            }
        }
    }

}

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
import java.util.List;
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
    private JSONArray VetMessaggi;
    private List<Persona> VetPersone = new ArrayList<Persona>();
    private String Token;
    private ReadWriteFile rw = new ReadWriteFile();

    public GestisciTelegram(String Token) throws IOException {
        /*this.Token=Token;
        CaricaVetPersonaFromCSV();
        GetUpdates();
        GetAllMessageWithCitta();*/
    }

    public void GetUpdates() throws MalformedURLException, IOException {
        URL url = new URL(uriBase + Token + "/getUpdates");
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");
        File file = new File("GetUpdates.txt");
        FileWriter fw = new FileWriter(file);
        fw.write(sc.next());
        fw.flush();
        fw.close();
        CaricaVetMessaggi();
    }

    public void SendMessageAll(String text) throws MalformedURLException, IOException {
        List<String> ChatID = getChatID("GetUpdates.txt");
        for (int i = 0; i < ChatID.size(); i++) {
            SendMessage(ChatID.get(i), text);
        }
    }

    public void SendMessage(String Chat_id, String text) throws MalformedURLException, IOException {
        String ris = uriBase + Token + "/sendMessage" + "?chat_id=" + Chat_id + "&text=" + text;
        URL url = new URL(ris);
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");
        File file = new File("RisultatoSendMessage.txt");
        FileWriter fw = new FileWriter(file);
        fw.write(sc.next());
        fw.flush();
        fw.close();
    }

    public List<String> getChatID(String NomeFile) throws IOException {
        List<String> Ris = new ArrayList();

        BufferedReader reader = new BufferedReader(new FileReader(NomeFile));
        String jsonString = "";
        String line = reader.readLine();
        while (line != null) {
            //System.out.println(line);
            jsonString += line;
            line = reader.readLine();
        }

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

    public List<JSONObject> GetAllMessageWith(String text) throws FileNotFoundException, IOException {
        List<JSONObject> Ris = new ArrayList();
        BufferedReader reader = new BufferedReader(new FileReader("GetUpdates.txt"));
        String jsonString = "";
        String line = reader.readLine();
        while (line != null) {
            //System.out.println(line);
            jsonString += line;
            line = reader.readLine();
        }

        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("result");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject update = arr.getJSONObject(i);
            JSONObject mess = update.getJSONObject("message");
            if(mess.getString("text").contains(text))
            {
                //aggiungo al vettore
                //Ris.add("");
            }
        }
        return Ris;
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
                String nome = elementi[1];
                Float lat = Float.parseFloat(elementi[2]);
                Float lon = Float.parseFloat(elementi[3]);

                Persona p = new Persona(id, nome, lat, lon);
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

    private void CaricaVetMessaggi() throws IOException {
        String Response = rw.LeggiDaFile("GetUpdates.txt");
        //parso la stringa
        JSONObject obj = new JSONObject(Response);
        //VetMesssaggi contiene tutti i messaggi inviati al bot
        VetMessaggi = obj.getJSONArray("result");
    }


}

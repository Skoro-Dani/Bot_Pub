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

    String uriBase = "https://api.telegram.org/bot";
    private JSONArray VetMessaggi;
    private List<Persona> VetPersone = new ArrayList<Persona>();
    String Token;
    
    public GestisciTelegram(String Token) throws IOException
    {
        this.Token=Token;
        GetUpdates();
        GetAllMessageWithCitta();
        CaricaVetPersonaFromCSV();
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
            SendMessage(ChatID.get(i),text);
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

    private File ScriviSuFile(String urlParziale, String NomeFile) throws MalformedURLException, IOException {
        URL url = new URL(urlParziale);
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");

        File f = new File(NomeFile);
        FileWriter fw = new FileWriter(f);

        fw.write(sc.next());
        fw.flush();
        fw.close();

        return f;
    }

    private String LeggiDaFile(String nomeFile) throws FileNotFoundException, IOException {
        //variabile di appoggio
        File file = new File(nomeFile);
        String jsString = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = br.readLine();
        while (line != null) {
            jsString += line;
            line = br.readLine();
        }

        return jsString;
    }
    
    public void GetAllMessageWithCitta() throws FileNotFoundException, IOException
    {
        List<String> Ris = new ArrayList();

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
            JSONObject appoggio = new JSONObject(VetMessaggi.get(i).toString());
            JSONObject messaggio = appoggio.getJSONObject("message");
            JSONObject chat = messaggio.getJSONObject("chat");
            String ID = Integer.toString(chat.getInt("id"));
            String Nome = chat.getString("first_name");
            //controllo che l'id non sia già stato inserito, se manca lo aggiungo alla lista
            if (!VetPersone.contains(ID.toString())) {
                VetPersone.add(new Persona(ID, Nome, 0.0f, 0.0f));
            }
        }
    }
    private void VetPersonetoCSV() {
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

    private void CaricaVetPersonaFromCSV() {
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
    
    private void CaricaVetMessaggi() throws IOException
    {
        String Response = LeggiDaFile("GetUpdates.txt");
        //parso la stringa
        JSONObject obj = new JSONObject(Response);
        //VetMesssaggi contiene tutti i messaggi inviati al bot
        VetMessaggi = obj.getJSONArray("result");
    }
    
    public void myGetLocation(String Indirizzo, String Id, String CAP) throws ParserConfigurationException, SAXException, IOException {
        //genero l'URL e scrivo il risultato su file
        String urlParziale = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(Indirizzo, StandardCharsets.UTF_8) + "&format=xml&addressdetails=1";
        File RispostaSito = ScriviSuFile(urlParziale, "RispostaSito.txt");
        System.out.println(urlParziale);
        //Parso il file XML scritto prima per ricavare gli oggetti
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, node;
        NodeList nodelist;

        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        document = builder.parse(RispostaSito);

        root = document.getDocumentElement();

        nodelist = root.getElementsByTagName("place");

        //ciclo la lista per trovare la persona con lo stesso ID passato
        GetUpdates();
        int i = 0;
        boolean trovato = false, capTrovato = false;
        while (!trovato && i < VetPersone.size()) {
            if (VetPersone.get(i).getIdChat().equals(Id)) {
                for (int j = 0; j < nodelist.getLength(); j++) {
                    node = (Element) nodelist.item(j);
                    String strcap = trovaCAP(node.getAttribute("display_name"));
                    if (CAP.equals(strcap)){
                        VetPersone.get(i).setLat(Float.parseFloat(node.getAttribute("lat")));
                        VetPersone.get(i).setLon(Float.parseFloat(node.getAttribute("lon")));
                        capTrovato = true;
                    }
                }
                trovato = true;
            }
        }
        if (capTrovato) {
            VetPersonetoCSV();
            System.out.println("Poszione trovata, CSV aggiornato");
        } else {
            System.out.println("Posizione non trovata");
        }
        
    }

    private String trovaCAP(String displayname) {
        String strCAP = "";
        if(displayname != null) {
            String[] elementi = displayname.split(",");
            strCAP = elementi[elementi.length - 2];
            strCAP = strCAP.substring(1);
            int i = Integer.parseInt(strCAP);
        }
        return strCAP;
    }

}

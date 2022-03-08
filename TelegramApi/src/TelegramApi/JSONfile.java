/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TelegramApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author skoro
 */
public class JSONfile {

    String uriBase="https://api.telegram.org/bot";
    public void CreaFile(String nomeFile, String Token) throws MalformedURLException, IOException {
        URL url = new URL(uriBase+Token+"/getUpdates");
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");
        File file = new File(nomeFile);
        FileWriter fw = new FileWriter(file);
        fw.write(sc.next());
        fw.flush();
        fw.close();
    }

    public void SendMessage(String Token, String Chat_id, String text) throws MalformedURLException, IOException {
        String ris=uriBase+Token+"/sendMessage"+"?chat_id="+Chat_id+"&text="+text;
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
        String jsonString="";
        String line = reader.readLine();
        while (line != null) {
            //System.out.println(line);
            jsonString += line;
            line = reader.readLine();
        }

        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("result");
        for(int i =0;i<arr.length();i++)
        {
            JSONObject obj1 = new JSONObject(arr.get(i).toString());
            JSONObject message = obj1.getJSONObject("message");
            JSONObject chat = message.getJSONObject("chat");
            int id = chat.getInt("id");
            System.out.println(id);
            Ris.add(String.valueOf(id));
        }
        return Ris;
    }
}

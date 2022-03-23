/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramApi;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author skorolitniy_daniel
 */
public class OpenStreetMap {

    private static ReadWriteFile rw = new ReadWriteFile();

    public static String myGetLocation(String Indirizzo) throws ParserConfigurationException, SAXException, IOException {
        //genero l'URL e scrivo il risultato su file
        String urlParziale = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(Indirizzo, StandardCharsets.UTF_8) + "&format=xml&addressdetails=1";
        File RispostaSito = rw.ScriviSuFile(urlParziale, "RispostaSito.txt");
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
        String strlat = "";
        String strlon = "";
        for (int i = 0; i < nodelist.getLength(); i++) {
            node = (Element) nodelist.item(i);
            strlat = node.getAttribute("lat");
            strlon = node.getAttribute("lon");

        }
        return (strlat + ";" + strlon);
    }

    public static double calcolaDistanza(String lat1,String lon1,String lat2,String lon2){
        return Math.acos(Math.cos(Math.toRadians(90-Integer.parseInt(lat1)))*Math.cos(Math.toRadians(90-Integer.parseInt(lat2)))+Math.sin(Math.toRadians(90-Integer.parseInt(lat1)))*Math.sin(Math.toRadians(90-Integer.parseInt(lat2)))*Math.cos(Math.toRadians(Integer.parseInt(lon1)-Integer.parseInt(lon2))))*6371*1000;
    }
}

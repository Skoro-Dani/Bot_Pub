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

    public static Place myGetLocation(String Indirizzo) throws ParserConfigurationException, SAXException, IOException {

        //genero l'URL e scrivo il risultato su file
        String urlParziale = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(Indirizzo, StandardCharsets.UTF_8) + "&format=xml&addressdetails=1";
        File RispostaSito = rw.ScriviSuFile(urlParziale, "RispostaSito.txt");

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

        return new Place((Element) nodelist.item(0));

    }

    public static double calcolaDistanza(Place place1, Place place2) {
        return Math.acos(Math.cos(Math.toRadians(90 - Double.valueOf(place1.getLat()))) * Math.cos(Math.toRadians(90 - Double.valueOf(place2.getLat()))) + Math.sin(Math.toRadians(90 - Double.valueOf(place1.getLat()))) * Math.sin(Math.toRadians(90 - Double.valueOf(place2.getLat()))) * Math.cos(Math.toRadians(Double.valueOf(place1.getLon()) - Double.valueOf(place2.getLon())))) * 6371 * 1000;
    }
}

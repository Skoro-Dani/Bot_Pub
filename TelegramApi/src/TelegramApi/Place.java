/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramApi;

import org.w3c.dom.Element;

/**
 *
 * @author skorolitniy_daniel
 */
public class Place {

    String lat, lon;

    public Place() {

    }

    public Place(Element n) {
        lat = n.getAttribute("lat");
        lon = n.getAttribute("lon");
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String toCSV() {
        return lat + ";" + lon;
    }
}

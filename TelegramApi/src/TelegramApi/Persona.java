/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TelegramApi;

/**
 *
 * @author skoro
 */
public class Persona {
    
    private String IdChat, IDLastMessage, Indirizzo;
    private Float lat, lon; 

    public Persona() {
    }
    
    public Persona(Persona p){
        this.IdChat = p.getIdChat();
        this.IDLastMessage = p.getIDLastMessage();
        this.lat = p.getLat();
        this.lon = p.getLon();
    }

    public Persona(String IdChat, String First_Name, Float lat, Float lon) {
        this.IdChat = IdChat;
        this.IDLastMessage = First_Name;
        this.lat = lat;
        this.lon = lon;
    }
    
    @Override
    public String toString() {
        String  s = "IDLastMessage:" +  IDLastMessage + "-IdChat:" + IdChat + "-Indirizzo:" + Indirizzo + "-Latitudine:" + Float.toString(lat) + "-Longitudine:" + Float.toString(lon);
        return s;
    }
    
    public String toCSV(){
        String  s = IDLastMessage + ";" + IdChat + ";" + Float.toString(lat) + ";" + Float.toString(lon);
        return s;
    }

    public String getIdChat() {
        return IdChat;
    }

    public String getIDLastMessage() {
        return IDLastMessage;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setIdChat(String IdChat) {
        this.IdChat = IdChat;
    }

    public void setIDLastMessage(String First_Name) {
        this.IDLastMessage = First_Name;
    }

    public void setIndirizzo(String Indirizzo) {
        this.Indirizzo = Indirizzo;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }
    
    
}

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
    
    private String IdChat, Indirizzo;
    private Float lat, lon; 
    private int IDLastMessage;

    public Persona() {
    }
    
    public Persona(Persona p){
        this.IdChat = p.getIdChat();
        this.IDLastMessage = p.getIDLastMessage();
        this.lat = p.getLat();
        this.lon = p.getLon();
    }

    public Persona(String IdChat, int IDLastMessage, Float lat, Float lon) {
        this.IdChat = IdChat;
        this.IDLastMessage = IDLastMessage;
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

    public int getIDLastMessage() {
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

    public void setIDLastMessage(int IDLastMessage) {
        this.IDLastMessage = IDLastMessage;
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

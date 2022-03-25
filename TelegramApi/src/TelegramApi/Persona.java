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
    private Place place;
    private int IDLastMessage;

    public Persona() {
    }
    //
    //public Persona() {
    //}
    public Persona(Persona p){
        this.IdChat = p.getIdChat();
        this.IDLastMessage = p.getIDLastMessage();
        this.place = p.getPlace();
    }

    public Persona(String IdChat, int IDLastMessage, Place place) {
        this.IdChat = IdChat;
        this.IDLastMessage = IDLastMessage;
        this.place = place;
    }
    
    @Override
    public String toString() {
        String  s = "IDLastMessage:" +  IDLastMessage + "-IdChat:" + IdChat + "-Indirizzo:" + Indirizzo + "-Latitudine:" + place.getLat() + "-Longitudine:" + place.getLon();
        return s;
    }
    
    public String toCSV(){
        String  s = IDLastMessage + ";" + IdChat + ";" + place.toCSV();
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

    public Place getPlace() {
        return place;
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

    public void setPlace(Place place) {
        this.place = place;
    }

    
    
    
}

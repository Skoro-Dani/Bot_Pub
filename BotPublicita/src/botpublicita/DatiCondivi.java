/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package botpublicita;

import TelegramApi.GestisciTelegram;
import TelegramApi.Persona;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author skoro
 */
public class DatiCondivi {

    private static DatiCondivi istanza = null;
    private ThreadGestione TG;
    private GestisciTelegram TeleApi;

    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private DatiCondivi() throws IOException {
        TeleApi = new GestisciTelegram();
    }

    // Metodo della classe impiegato per accedere al singleton
    public static synchronized DatiCondivi getMioSingolo() throws IOException {
        if (istanza == null) {
            istanza = new DatiCondivi();
        }
        return istanza;
    }

    public void newThreadGestione() throws IOException {
        TG = new ThreadGestione();
    }

    public void StartThreadGestione() {
        TG.start();
    }
    public void setToken(String Token)
    {
        TeleApi.setToken(Token);
    }
    public GestisciTelegram GetGestisciTelegram()
    {
        return TeleApi;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package botpublicita;

import TelegramApi.Persona;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author skoro
 */
public class DatiCondivi {
    private static DatiCondivi istanza = null;
    private Map<String, Persona> Persone = new HashMap<String, Persona>();

    //Il costruttore private impedisce l'istanza di oggetti da parte di classi esterne
    private DatiCondivi() {}

    // Metodo della classe impiegato per accedere al singleton
    public static synchronized DatiCondivi getMioSingolo() {
        if (istanza == null) {
            istanza = new DatiCondivi();
        }
        return istanza;
    }
    
}

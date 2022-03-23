/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpublicita;

import TelegramApi.GestisciTelegram;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author skorolitniy_daniel
 */
public class ThreadGestione extends Thread {

    private String Token;
    private int waitMS;
    private boolean Finito;
    private Object FinitoOBJ = new Object();
    private GestisciTelegram TeleApi;
    
    public ThreadGestione(String token) throws IOException {
        this.Token = Token;
        Finito = false;
        TeleApi = new GestisciTelegram(Token);
        waitMS = 10000;
    }

    @Override
    public void run() {
        TeleApi.CaricaVetPersonaFromCSV();
        while (isFinito() == false) {
            try {
                TeleApi.GetUpdates();
                TeleApi.GetAllMessageWith("/citta");
                Thread.sleep(waitMS);
            } catch (IOException ex) {
                Logger.getLogger(ThreadGestione.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGestione.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(ThreadGestione.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(ThreadGestione.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        TeleApi.VetPersonetoCSV();
    }

    public void setFinito(boolean Finito) {
        synchronized (FinitoOBJ) {
            this.Finito = Finito;
        }
    }

    public boolean isFinito() {
        synchronized (FinitoOBJ) {
            return Finito;
        }
    }

    public String getToken() {
        return Token;
    }

    public int getWaitMS() {
        return waitMS;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public void setWaitMS(int waitMS) {
        this.waitMS = waitMS;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author skorolitniy_daniel
 */
public class ReadWriteFile {

    public File ScriviSuFile(String urlParziale, String NomeFile) throws MalformedURLException, IOException {
        URL url = new URL(urlParziale);
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");

        File f = new File(NomeFile);
        FileWriter fw = new FileWriter(f);

        fw.write(sc.next());
        fw.flush();
        fw.close();

        return f;
    }

    public String LeggiDaFile(String nomeFile) throws FileNotFoundException, IOException {
        //variabile di appoggio
        File file = new File(nomeFile);
        String jsString = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = br.readLine();
        while (line != null) {
            jsString += line;
            line = br.readLine();
        }

        return jsString;
    }
}

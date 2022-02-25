/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramApi;

import org.json.*;

/**
 *
 * @author skorolitniy_daniel
 */
public class Test {

    public void foo() {

        String jsonString = "{nome:'mario', messaggi:['ciao mondo','ciao daniel']}"; //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
        String Name = obj.getString("nome");
        System.out.println(Name);
        JSONArray arr = obj.getJSONArray("messaggi"); // notice that `"posts": [...]`
        for (int i = 0; i < arr.length(); i++) {
            String messaggio = arr.getString(i);
            System.out.println(messaggio);

        }
    }
}

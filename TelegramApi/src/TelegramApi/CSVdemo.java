/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramApi;

/**
 *
 * @author skorolitniy_daniel
 */
public class CSVdemo {
    String c1,c2,c3,c4;
    Double d1,d2,d3,d4;
    int i1,i2,i3,i4;

    public CSVdemo(String c1, String c2, String c3, String c4, Double d1, Double d2, Double d3, Double d4, int i1, int i2, int i3, int i4) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.i1 = i1;
        this.i2 = i2;
        this.i3 = i3;
        this.i4 = i4;
    }
    public CSVdemo() {
        this.c1 = "";
        this.c2 = "";
        this.c3 = "";
        this.c4 = "";
        this.d1 = 0.0d;
        this.d2 = 0.0d;
        this.d3 = 0.0d;
        this.d4 = 0.0d;
        this.i1 = 0;
        this.i2 = 0;
        this.i3 = 0;
        this.i4 = 0;
    }
    public String toCSV()
    {
        return c1+";"+c2+";"+c3+";"+c4+";"+d1+";"+d2+";"+d3+";"+d4+";"+i1+";"+i2+";"+i3+";"+i4;
    }
    public void FromCSV(String CSV)
    {
        String s[]=CSV.split(";");
        c1=s[0];
        c2=s[1];
        c3=s[2];
        c4=s[3];
        d1=Double.valueOf(s[4]);
        d2=Double.valueOf(s[5]);
        d3=Double.valueOf(s[6]);
        d4=Double.valueOf(s[7]);
        i1= Integer.valueOf(s[8]);
        i2= Integer.valueOf(s[9]);
        i3= Integer.valueOf(s[10]);
        i4= Integer.valueOf(s[11]);
    }
    
    
}

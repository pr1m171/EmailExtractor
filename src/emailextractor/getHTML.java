/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailextractor;


import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Cris-Gabi
 */
public class getHTML extends SwingWorker<Void, Void>{
    
    HashSet<String> silk = new HashSet<String>();
    HashSet<String> silkb = new HashSet<String>();
    HashSet<String> cEmail = new HashSet<String>();
    JTextArea jtext, jtextHTML;
    JProgressBar progreso;
    JLabel lbNivel, lbURLA, lbURLB, lbEmail;
    String DATA;
    boolean STATE;
    int Nivel;


    getHTML(JTextArea txt1, JTextArea txt2, HashSet<String> silk, JProgressBar progreso, String DATA, boolean STATE, JLabel lbNivel, JLabel lbURLA, JLabel lbURLB, JLabel lbEmail) {
        
        this.jtextHTML = txt1;
        this.jtext = txt2;
        this.silk = silk;
        this.progreso = progreso;
        this.DATA = DATA;
        this.STATE = STATE;
        this.lbNivel = lbNivel;
        this.lbURLA = lbURLA;
        this.lbURLB = lbURLB;
        this.lbEmail = lbEmail;
        jtextHTML.setText("");
    }
    
    public void scan(){
        int cantidad = silk.size();
        jtext.setText("");
        
        lbURLB.setText(cantidad + "");
        int estado = 0;
        int porcentaje = 0;
        String web,sub;
        silkb.clear();
        silkb.addAll(silk);
        silk.clear();
        Iterator<String> it = silkb.iterator();
        while(it.hasNext() && STATE == true){
            estado++;
            lbURLA.setText(estado + "");
            
            sub=it.next().toString();
            
            
            jtext.setText(jtext.getText() + "\n" + sub);
                        
            try{
                
                Document doc = Jsoup.connect(sub).userAgent("Mozilla/5.0").get();
                getEmail(doc.html());
                getURLS(doc.html());                
            }catch(Exception e){
                
            }
            
            porcentaje = (estado * 100) / cantidad;
            progreso.setValue(porcentaje);
        }
        if(porcentaje == 100 && STATE == true){
            getNivel();
        }
    }
    
    
    public void getNivel(){
            DATA="";
            Nivel++;
            lbNivel.setText("" + Nivel);
            scan();
    }
    public void getURLS(String datos){
            Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(datos);
 
            while(matcher.find()) {
                String miurl = matcher.group();
                String st=miurl.substring(miurl.length()-3, miurl.length());
                if(st.equals("png")){
                }else if(st.equals("dtd")){
                }else if(st.equals("ttf")){
                }else if(st.equals("jpg")){
                }else if(st.equals("pdf")){
                }else if(st.equals("css")){
                }else if(st.equals("gif")){
                }else if(st.equals(".js")){
                }else if(st.equals("DTD")){
                }else if(st.equals("PNG")){
                }else if(st.equals("TTF")){
                }else if(st.equals("JPG")){
                }else if(st.equals("PDF")){
                }else if(st.equals("CSS")){
                }else if(st.equals("GIF")){
                }else if(st.equals(".JS")){
                }else{
                    silk.add(matcher.group());
                }
            }
    }
    public void limpiar(){
        cEmail.clear();
        silk.clear();
        jtextHTML.setText("");
        jtext.setText("");
        DATA = "";
        STATE = false;
        lbNivel.setText("0");
        lbURLA.setText("0");
        lbURLB.setText("0");
        lbEmail.setText("0");
        progreso.setValue(0);
        
        
    }
    public void getEmail(String datos){
        Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
            Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(datos);
            int cante = 0;
            int totaliza = 0;
            while(matcher.find()) {
                String email = matcher.group();
                String str=email.substring(email.length()-3, email.length());
                String str2=email.substring(email.length()-10, email.length());
                String str3=email.substring(email.length()-9, email.length());
                String str4=email.substring(email.length()-12, email.length());
                String str5=email.substring(email.length()-14, email.length());
                if(str.equals("png")){
                }else if(str.equals("jpg")){
                }else if(str.equals("pdf")){
                }else if(str.equals("css")){
                }else if(str.equals("gif")){
                }else if(str.equals(".js")){
                }else if(str.equals("PNG")){
                }else if(str.equals("JPG")){
                }else if(str.equals("PDF")){
                }else if(str.equals("CSS")){
                }else if(str.equals("GIF")){
                }else if(str.equals(".JS")){
                }else if(str2.equals("@email.com")){
                }else if(str3.equals("@mail.com")){
                }else if(str4.equals("@example.com")){
                }else if(str4.equals("@address.com")){
                }else if(str5.equals("@webmaster.com")){
                }else{
                    cEmail.add(email);
                    totaliza++;
                }
                
            }
                if(totaliza > 0){
                    cante = cEmail.size();
                    lbEmail.setText("" + cante);
                    String emailss = "";
                    Iterator<String> mails = cEmail.iterator();
                    while(mails.hasNext()){
                        emailss = mails.next() + "\n" + emailss;
                    }
                    jtextHTML.setText(emailss);
                }
                
    }
    private static void esperar(){
        try{
            Thread.sleep(300);
        }catch(InterruptedException e){
        
        }
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        scan();    
        return null;
    }

    void limpiarEmail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}

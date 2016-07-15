/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propiedades;


import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mgocx
 */
public class loadConfig extends Properties {
    public loadConfig(){
        getProperties("config.properties");
    }

    private void getProperties(String propiedad) {
                try{
                    this.load(getClass().getResourceAsStream(propiedad));
                }catch(IOException e){
                    
                }
    }


 
}

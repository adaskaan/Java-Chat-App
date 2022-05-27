/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaan
 */
public class ServerStart {
    public static void main(String[] args) {
        try {
            int port = 5000;
            new Server(port).StartServer();
            System.out.println("Server Started...");
        } catch (IOException ex) {
            Logger.getLogger(Frm_Server.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}

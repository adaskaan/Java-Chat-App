/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat_app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author skaya
 */
public class Client {

    static Socket clientSocket;
    static String serverIp = "localhost";//"localhost";//127.0.0.1
    static int serverPort = 5000;
    static ObjectOutputStream clientOutput;
    static ObjectInputStream clientInput;

    public static void main(String[] args) {

        try {
            clientSocket = new Socket(serverIp, serverPort);
            clientInput = new ObjectInputStream(clientSocket.getInputStream());
            clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());

            clientOutput.writeObject("merhaba server");
            clientOutput.writeObject("nasılsın server");
          
            String value = clientInput.readObject().toString();//blocking
            System.out.println("Server said:" + value);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

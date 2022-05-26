/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Kaan
 */
public class Listener extends Thread{

    public Client client;
    public Socket clientSocket;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
    public int id;

    public Listener(Socket clientSocket) {
        try {
 
            this.clientSocket = clientSocket;
            this.clientOutput = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.clientInput = new ObjectInputStream(this.clientSocket.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Listen() {
        this.start();
    }

    @Override
    public void run() {
        try {
            while (this.clientSocket.isConnected()) {
                String value = this.clientInput.readObject().toString();//blocking
                Message inc = (Message) (this.clientInput.readObject());
                switch (inc.type) {
                    case Start:
                        // code block
                        MainScreen.lst_clientsModel.addElement(inc.content);
                        break;
                    case Bitis:
                        // code block
                        break;
                    default:
                    // code block
                }
                Frm_Server.lst_messageModel.addElement(this.id + ": " + value);
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}

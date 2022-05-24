/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaan
 */
public class SRoom extends Thread {

    int id;
    boolean type; // False = Private , True = Public
    int clientCount;
    ArrayList<SClient> clients;
    String name;
    public Server server;
    public Socket roomSocket;
    public ObjectOutputStream roomOutput;
    public ObjectInputStream roomInput;

    public SRoom(boolean type, int id, String name) {
        this.type = type;
        this.id = id;
        this.clientCount = clients.size();
        this.clients = new ArrayList<>();
        this.name = name;
    }

    public void Listen() {
        this.start();
    }

    @Override
    public void run() {
        try {
            while (this.roomSocket.isConnected()) {
                String value = this.roomInput.readObject().toString();//blocking
                System.out.println(value);
            }

        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.server.RemoveRoom(this);
        }
    }
}

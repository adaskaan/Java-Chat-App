/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

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
public class SClient extends Thread {

    public Server server;
    public Socket clientSocket;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
    public int id;

    public SClient(Server server, Socket clientSocket, int id) {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            this.clientOutput = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.clientInput = new ObjectInputStream(this.clientSocket.getInputStream());
            this.id = id;


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
                if(value.equals("REQRID")){
                System.out.println(value);
                this.clientOutput.write(server.roomId);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.server.RemoveClient(this);
        }
    }
}

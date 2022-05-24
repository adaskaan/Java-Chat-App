/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaan
 */
public class Server extends Thread {
    
    public ServerSocket serverSocket;
    public int serverPort = 5000;
    public Thread listen;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
    public int clientId;
    public int roomId;
    public ArrayList<SClient> clients;
    public ArrayList<SRoom> rooms;
    

    public Server(int serverPort) throws IOException {
        this.serverPort = serverPort;
        this.serverSocket = new ServerSocket(this.serverPort);
        this.clientId = 0;
        this.clients = new ArrayList<>();
        this.roomId = 0;
        this.rooms = new ArrayList<>();

    }

    public void StartServer() {

        this.start();
    }

    public void RemoveClient(int id) {

        SClient refClient = null;
        for (SClient client : clients) {
            if (client.id == id) {
                refClient = client;
                break;
            }
        }
        if (refClient != null) {
            this.clients.remove(refClient);
        }
        //RefreshClientList();

    }

    

    public void SendAll(String message) {
        for (SClient client : clients) {
            try {
                client.clientOutput.writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void SendToClient(SClient client, String message) {

        try {
            client.clientOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void SendToClient(int index, String message) {
        try {
            this.clients.get(index).clientOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {

        try {

            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept(); //blocking
                this.clientId++;
                SClient newClient = new SClient(this, client, this.clientId);
                System.out.println("New Client Connected ID: "+this.clientId);
                this.clients.add(newClient);
                newClient.Listen();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    void RemoveClient(SClient client) {
        this.clients.remove(client);
       // RefreshClientList();
    }
        void RemoveRoom(SRoom room) {
        this.rooms.remove(room);
       // RefreshClientList();
    }
       public void SendAllRooms(String message) {
        for (SRoom room : rooms) {
            try {
                room.roomOutput.writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void SendToRoom(SRoom room, String message) {

        try {
            room.roomOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void SendToRoom(int index, String message) {
        try {
            this.rooms.get(index).roomOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
/**
    public void RefreshClientList() {
        Frm_Server.lst_clientsModel.removeAllElements();
        for (SClient client : clients) {
            Frm_Server.lst_clientsModel.addElement("id: " + client.id
                    + " ip: " + client.clientSocket.getInetAddress().toString()
                    + "port: " + client.clientSocket.getPort());
        }
    }
    **/
}

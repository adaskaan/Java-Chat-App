/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

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
public class SClient extends Thread {

    public Server server;
    public Socket clientSocket;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
    String username;
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
                Message value = (Message) this.clientInput.readObject();
                switch (value.type) {
                    case Connect:
                        Frm_Server.lst_clientsModel.addElement(value.content.toString());
                        System.out.println("Client Connected");
                        this.username = value.content.toString();
                        Message updateListMsg = new Message(Message.Message_Type.UpdateClientList);
                        ArrayList<String> users = new ArrayList<>();
                        for (int i = 0; i < server.clients.size(); i++) {
                            users.add(server.clients.get(i).username);
                        }
                        updateListMsg.content = users;
                        server.SendAll(updateListMsg);
                        break;
                        
                   case CreateRoom:
                      Room r = (Room)value.content;
                      r.id=server.roomId;
                      r.userCount=0;
                       server.rooms.add(r);
                       server.roomId++;
                        ArrayList<Room> roomList = new ArrayList<>();
                        for (int i = 0; i < roomList.size(); i++) {
                           roomList.add(r);
                       }
                       Message UpdateRoomList = new Message(Message.Message_Type.UpdateRoomList);
                       UpdateRoomList.content = roomList;
                       server.SendAll(UpdateRoomList);
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.server.RemoveClient(this);
            
        }
    }
}

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
    String room = "lobby";

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
                        String roomName = value.content.toString();
                        server.rooms.add(roomName);
                        Message updateroom = new Message(Message.Message_Type.UpdateRoomList);
                        updateroom.content = server.rooms;
                        server.SendAll(updateroom);
                        break;
                    case UserChat:
                        String search = value.reciever;
                        for (int i = 0; i < server.clients.size(); i++) {
                            if (server.clients.get(i).username.equals(search)) {
                                server.SendToClient(server.clients.get(i), value);
                            }
                        }
                        break;
                    case RoomChat:

                        String r = room;
                        for (int i = 0; i < server.clients.size(); i++) {
                            if (r.equals(server.clients.get(i).room)) {
                                server.SendToRoom(server.clients.get(i), value);

                            }
                        }
                        break;
                    case Refresh:
                        Message roomUpdate = new Message(Message.Message_Type.UpdateRoomList);
                        roomUpdate.content = server.rooms;
                        server.SendToClient(this, roomUpdate);
                        break;
                    case JoinRoom:
                        this.room = value.content.toString();
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

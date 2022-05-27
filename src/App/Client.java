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
import javax.swing.DefaultListModel;

/**
 *
 * @author Kaan
 */
public class Client {

    Socket clientSocket;
    String serverIp = "localhost";//"localhost";//127.0.0.1
    int serverPort = 5000;
    ObjectOutputStream clientOutput;
    ObjectInputStream clientInput;
    Listen listen;
    MainScreen ms;
    String username;
    String room = "lobby";
    int roomId;

    public Client(MainScreen ms) {
        this.ms = ms;
    }

    public void Start(String usr) throws IOException {
        clientSocket = new Socket(serverIp, serverPort);
        clientInput = new ObjectInputStream(clientSocket.getInputStream());   
        clientOutput = new ObjectOutputStream(clientSocket.getOutputStream()); 
        listen = new Listen(this);  
        listen.start();
        username = usr;  
        Message name = new Message(Message.Message_Type.Connect);  
        name.content = usr;
        Send(name); 

    }

    public void Send(Message msg) { 
        try {
            clientOutput.writeObject(msg);   
            clientOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class Listen extends Thread {  

        Client client; 
        DefaultListModel<String> modelRoomList = new DefaultListModel<>(); 

        Listen(Client c) {
            client = c;  
        }

        @Override
        public void run() {
            while (client.clientSocket.isConnected()) {  
                try {
                    Message received = (Message) (client.clientInput.readObject());
                    switch (received.type) {
                        case UpdateClientList:
                            ArrayList clients = (ArrayList) received.content;
                            MainScreen.users.removeAllElements();
                            for (int i = 0; i < clients.size(); i++) {
                                MainScreen.users.addElement(clients.get(i).toString());
                            }
                            break;
                        case UpdateRoomList:
                            ArrayList<String> rooms = (ArrayList)received.content;
                            MainScreen.Rooms = rooms;
                            MainScreen.lst_roomsModel.removeAllElements();
                            for (int i = 0; i < rooms.size(); i++) {
                                MainScreen.lst_roomsModel.addElement(rooms.get(i));
                            }
                            break;
                        case UserChat:
                            MainScreen.lst_clientsModel.addElement(received.sender+" : "+received.content.toString());
                            break;
                        case RoomChat:
                            MainScreen.lst_messageModel.addElement(received.sender+" : "+received.content.toString());
                            break;
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

/**
 *
 * @author Kaan
 */
public class Message implements java.io.Serializable {
    
        public static enum Message_Type {
        Connect,UpdateClientList,Chat,CreateRoom, UpdateRoomList
    }

    public Message_Type type;
    public Object content;
    String sender;
    
    public Message(Message_Type t) {
        this.type = t;
    }

    public Message(Message_Type type, Object content, String sender) {
        this.type = type;
        this.content = content;
        this.sender=sender;
    }
    
}

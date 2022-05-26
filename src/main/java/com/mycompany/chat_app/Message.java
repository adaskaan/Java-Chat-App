/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat_app;

/**
 *
 * @author Kaan
 */
public class Message implements java.io.Serializable {
    
        public static enum Message_Type {
        None, Name, Disconnect , Text , Bitis, Start, UpdateUserList, Rename, 
        PrivateMsg, CreateNewRoom, NewRoom, SendAllRooms, RoomNameExist, 
        CompleteCreation, RequestJoinRoom, PasswordRejected,
        PasswordAccepted, UpdateChatRoomUserList, RoomMSG, 
        GetOldRoomUsers, RoomUserLeft,RemoveFromMyRoomList,
    }

    public Message_Type type;
    public Object content;

    public Message(Message_Type t) {
        this.type = t;
    }

}

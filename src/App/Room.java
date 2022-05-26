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
public class Room {
    int id;
    String name;
    Client creator;
    int userCount;

    public Room(int id, String name, int userCount) {
        this.id = id;
        this.name = name;
        this.userCount = userCount;
    }
        public Room(String name, Client c) {
        this.name = name;
        this.creator = c;

    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.DataPackage;

/**
 *
 * @author Csaba
 */
public class TurnData extends Data {

    public TurnData(int recipientID) {
        super(-1);
        System.out.println("FA" + recipientID);
        super.recipientID = recipientID;
        System.out.println("ClientID: " + super.clientID + " recipientID: " + super.recipientID);
    }

}

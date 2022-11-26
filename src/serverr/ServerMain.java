/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverr;

import serverr.Server;

/**
 *
 * @author adars
 */
public class ServerMain
{
    public static void main(String[] args)
    {
       Server s= new Server();//It Will Invoke The GUI Part
       s.waitingForClient();  //It Will Wait For The Client
       s.setIostream();       //It Will Set The Stream Through Which We Will Transfer The Data
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author adars
 */
public class Client
{
   private JFrame clientframe;
   private JTextArea ta;
   private JScrollPane scrollpane;
   private JTextField tf;
   private  Socket socket;
   private Font font;
   String ipaddress;
   
   private DataInputStream dis;
   private  DataOutputStream dos;
   
   //--------------------------------------------Thread Creation Started------------------------------------------------------------
    Thread thread =new Thread()
    {
         public void run()
         {
             while(true)
             {
                 readMessage();
             }
         }  
    };
    //--------------------------------------------Thread Creation Ended-------------------------------------------------------------
    
    Client()
    {
        try
        {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
      
        ipaddress= JOptionPane.showInputDialog("Enter IP Address");
        if(ipaddress !=null)
        {
           if(!ipaddress.equals(""))
           {
              connectToServer();
               
              clientframe=new JFrame("Client");
              clientframe.setSize(500, 500);
              clientframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
              ta=new JTextArea();
              ta.setEditable(false); 
              font=new Font("Arial",1,16);
              ta.setFont(font);
              scrollpane=new JScrollPane(ta);
              clientframe.add(scrollpane);
       
              tf=new JTextField();
              tf.addActionListener(new ActionListener() {

                  @Override
                  public void actionPerformed(ActionEvent e) 
                  {
                      sendMessage(tf.getText());
                      ta.append(tf.getText()+"\n");
                      tf.setText("");  
                  }
              });
              
              clientframe.add(tf,BorderLayout.SOUTH);
               
              clientframe.setVisible(true);
           }
        } 
    }  
    void connectToServer()
    {
        try
        {
            socket=new Socket(ipaddress, 1111);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }   
    } 
    void setIostream()
    {
      try
      {
         dis=new DataInputStream(socket.getInputStream());
         dos=new DataOutputStream(socket.getOutputStream());
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
      thread.start();
    }
    public void sendMessage(String message)
    {
       try
       {
          dos.writeUTF(message);
          dos.flush();
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
    }
      public void readMessage()
    {
      try
      {
        String message=  dis.readUTF();
        showMessage("Server : "+message);                
      }
      catch(Exception e)
      {
          System.out.println(e);
      } 
    }
    public void showMessage(String message)
    {
        ta.append(message+"\n");
        chatSound();
    }
     public void chatSound()
    {
        try
        {
          FileInputStream fis =new FileInputStream("C:\\Users\\adars\\Documents\\NetBeansProjects\\ChatApplication\\src\\Sound\\iphone_sound.mp3");
          javazoom.jl.player.Player p =new javazoom.jl.player.Player(fis);
          p.play();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

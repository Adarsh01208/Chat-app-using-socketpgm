/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author adars
 */
public class Server  
{
   private JFrame Serverframe;
   private JTextArea ta;
   private JScrollPane scrollpane;
   private JTextField tf;
   
   private ServerSocket serversocket;
   private InetAddress inet_address;
   private DataInputStream dis;
   private  DataOutputStream dos;
   private Font font;
   
    private Socket socket;
    
    
    //---------------------------------------Thread Creation Started-----------------------------------------------------
    
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
    
     //--------------------------------------Thread Creation Ended----------------------------------------------------------
                      
    Server()
    {  
      try
      {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
        
       Serverframe=new JFrame("Server");
       Serverframe.setSize(500, 500);
       Serverframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       ta=new JTextArea();
       ta.setEditable(false); 
       font=new Font("Arial",1,16);
       ta.setFont(font);
       scrollpane=new JScrollPane(ta);
       Serverframe.add(scrollpane);
       
       tf=new JTextField();
       tf.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              sendMessage(tf.getText());
              ta.append(tf.getText()+"\n");
              tf.setText("");   
           }
       });
       tf.setEditable(false);
       Serverframe.add(tf,BorderLayout.SOUTH);        
       
       Serverframe.setVisible(true);
    }
    public void waitingForClient() 
    {
        try
        {
           String ipaddress= getIpAddress();
           
           serversocket= new ServerSocket(1111);
           ta.setText("To Connect With Server,Please Provide IP Address : " +ipaddress);
           socket= serversocket.accept();
           ta.setText("Client Connected\n");
           ta.append("----------------------------------------------------\n");
           
           tf.setEditable(true);    
        }
        catch(Exception e)
        {
              System.out.println(e);      
        } 
    }
    public String getIpAddress()
    {
       String ip_address="";
       try
       {
          inet_address=InetAddress.getLocalHost();
          ip_address = inet_address.getHostAddress();
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
        return ip_address;
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
        String message=dis.readUTF();
        showMessage("client : "+message);               
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

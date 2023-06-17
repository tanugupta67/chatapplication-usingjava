/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
/**
 *
 * @author Tanishka Gupta
 */
public class Client extends JFrame {
    Socket socket;
    //for reading
    BufferedReader br;
    //for writing
    PrintWriter out;
    private JLabel heading=new JLabel("Client Area");
    private JTextArea messagearea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);
    private JButton btn=new JButton();
//    b.setText("Send Image");
    
    public Client(){
        try{
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
createGui();
handleEvents();
            startReading();
//            startWriting();
        }catch(Exception e){
            e.printStackTrace();
        }
    }public void startReading(){
        //concept of multithreading
        //thread-read krke deta rahega
        Runnable r1=()->{
            System.out.println("Reader started........");
            try {
            while(true){
                String msg;
                
                    msg = br.readLine();
                    if(msg.equals("exit")){
//                   System.out.println("Server terminated") ;
JOptionPane.showMessageDialog(this,"Server Terminated");
             messageInput.setEnabled(false);
socket.close();
                   break;
                }
//                    System.out.println("server:"+msg);
                messagearea.append("\n"+"Server:   "+msg+ "\n");
                
            }
            } catch (IOException ex) {
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //concept of multithreading
        //thread-data user lega and then send krega
        Runnable r2 =()->{
            System.out.println("Writer Started");
            try{
        while(true && socket.isClosed()){
            
                BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                String  content=br1.readLine();
                out.println(content);
                out.flush();
                 if(content.equals("exit")){
                    socket.close();
                    break;
                }
            
        }
        }catch(Exception e){
                e.printStackTrace();
            }
    };
        new Thread(r2).start();
    }
    public void createGui(){
        this.setTitle("Client Messager[END]");
        this.setSize(700,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        heading.setFont(font);
        messagearea.setFont(font);
        messageInput.setFont(font);
//        btn.setFont(font);
        btn.setText("Send Image");
        
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("C:\\Users\\Tanishka Gupta\\OneDrive\\Documents\\NetBeansProjects\\ChatApp\\src\\main\\java\\com\\mycompany\\chatapp\\clientside.png").getImage().getScaledInstance(70, 70,70));
        heading.setIcon(imageIcon);
//        heading.getIcon(new ImageIcon("clientside.png"));
//        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setVerticalAlignment(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messagearea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER); 
         this.setLayout(new BorderLayout());
         this.add(heading,BorderLayout.NORTH);
         JScrollPane jscrollPane=new JScrollPane(messagearea);
         this.add(jscrollPane,BorderLayout.CENTER);
         this.add(messageInput,BorderLayout.SOUTH);
         btn.setPreferredSize(new Dimension(100,10));
btn.setBounds(100,100,100,80);
         this.add(btn,BorderLayout.BEFORE_LINE_BEGINS);
         btn.setHorizontalAlignment(SwingConstants.CENTER);
         btn.setVerticalAlignment(SwingConstants.BOTTOM);
       this.setVisible(true);  
    }
    public  void handleEvents(){
        //adding interface key listener
        messageInput.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
//                throw new  UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//          System.out.println("KEY released"+e.getKeyCode());
          if(e.getKeyCode()==10){
//              System.out.println();
String contentTosend=messageInput.getText();
messagearea.append("Me:    "+contentTosend);
out.println(contentTosend);
out.flush();
messageInput.setText(" ");
          }
            }
            
        });
    }
    public static void main(String[] args){
        System.out.println("This is client");
        new Client();
    }
}

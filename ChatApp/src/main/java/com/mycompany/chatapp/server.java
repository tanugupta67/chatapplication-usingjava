/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Tanishka Gupta
 */
public class server {
    //constructor
    ServerSocket server;
    Socket socket;
    //for reading
    BufferedReader br;
    //for writing
    PrintWriter out;
    public server(){
        try{
            server=new ServerSocket(7777);
            System.out.println("Server is ready");
            System.out.println("waiting..........");
            socket=server.accept();
            //character mein change hojaeyga
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void startReading(){
        //concept of multithreading
        //thread-read krke deta rahega
        Runnable r1=()->{
            System.out.println("Reader started........");
            try {
            while(true){
                String msg;
                
                    msg = br.readLine();
                    if(msg.equals("exit")){
                   System.out.println("CLient terminated") ;
                   socket.close(); 
                    break;
                }
                    System.out.println("Client:"+msg);  
            }
            }catch (IOException ex) {
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
               while(true && !socket.isClosed()){
            
                BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                String  content=br1.readLine();
                out.println(content);
                out.flush();
            if(content.equals("exit")){
                socket.close();
                break;
            }
        }  
            }
       
        catch(Exception e){
                e.printStackTrace();
            }
    };
        new Thread(r2).start();
    }
    
    public static void main(String[] args){
        System.out.println("THIS IS A SERVER");
    new server();
    }
}

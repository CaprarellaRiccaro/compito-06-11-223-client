package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class App 
{
    public static void main( String[] args )
    {
        try {
            Socket mioSocket = new Socket("localhost", 6789);

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));       
            DataOutputStream outVersoServer = new DataOutputStream(mioSocket.getOutputStream());

            int scelta = 0;

            do{
                System.out.println(
                    "> digita:\n" +
                    "> 'Aggiungi nota' se vuoi inserire una nota\n" +
                    "> '@' se vuoi visualizzare le note memorizzate\n" +
                    "> 'ESCI' nel caso vuoi disconneterti"
                );

                String st =in.readLine();
                outVersoServer.writeBytes(st + '\n');

                if(st.equals("Aggiungi nota")){
                    System.out.println("> Digita la nota da inserire");
                    String nota = in.readLine();
                    outVersoServer.writeBytes(nota + '\n');
                    String salvato = inDalServer.readLine();
                    System.out.println(salvato + "\n");
                     
                    scelta = 1;
                }

                else if (st.equals("@")){
                    System.out.println("> Lista : \n");
                    
                    String lista = inDalServer.readLine();
                    System.out.println(lista);
                    
                    scelta = 2;
                }
                
                else if (st.equals("ESCI")){
                    scelta = 4;
                    System.out.println("> Chiusura dell'applicazione");
                }

                else{
                    scelta = 3;
                    System.out.println("\n > Non hai inserito un'opzione valida; riprova\n \n");
                }
              
                
            } while (scelta != 4);

            mioSocket.close();
        } 
            
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione!");
            System.exit(1);
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.controller;

import com.google.gson.Gson;
import com.mycompany.clientemessengerclone.model.Cliente;
import com.mycompany.clientemessengerclone.utils.Desconectar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Soares
 */
public class GerenciaComunicaoCliente extends Thread {

    private List<Observador> observadores = new ArrayList<>();
    private ComunicacaoServidorImpl controller;
    private boolean clienteOnline = true;
    private Socket conn;

    public boolean isClienteOnline() {
        return clienteOnline;
    }

    public void setClienteOnline(boolean clienteOnline) {
        this.clienteOnline = clienteOnline;
    }

    public GerenciaComunicaoCliente(Socket conn) {
        this.conn = conn;
        this.controller = ComunicacaoServidorImpl.getInstance();
    }

    @Override
    public void run() {
        while (clienteOnline) {
            BufferedReader in = null;
            String opcao = null;

            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                opcao = in.readLine();

                System.out.println("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n");
                System.out.println("Requisição: " + opcao + " | Usuario: " + conn.getInetAddress());
                System.out.println("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n");

            } catch (IOException e) {
                System.out.println("Erro na criação do socket!");
                e.printStackTrace();
            }
            if (opcao != null) {
                switch (opcao) {
                    case "ReceberMsg":
                        receberMsg(in);
                        break;
                    case "ReceberArquivo":

                        break;
                    case "ReceberAudioConf":

                        break;
                    case "ReceberVideoConf":

                        break;
                    case "FimChat":
                        
                        break;
                }
            }
        }
    }

    public void receberMsg(BufferedReader in) {
        PrintWriter out = null;
        Gson gson = new Gson();
        try {

            String msgCliente = "";

            while (!(msgCliente = in.readLine()).equalsIgnoreCase("fimCliente")) {
                this.controller.abreChat(gson.fromJson(msgCliente, Cliente.class));
            }
            msgCliente = in.readLine();
            if (msgCliente.equalsIgnoreCase("InicioMsg")) {
                while (!(msgCliente = in.readLine()).equalsIgnoreCase("fimmsg")) {
                    this.controller.exibeMsgVindaCliente(msgCliente);
                }
                if (msgCliente.equalsIgnoreCase("fimmsg")) {
                    out = new PrintWriter(conn.getOutputStream(), true);
                    out.println("MsgRecebida");
                    this.clienteOnline = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GerenciaComunicaoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.model;

import com.mycompany.clientemessengerclone.controller.GerenciaComunicaoCliente;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Gabriel Soares
 */
public class ServidorCliente extends Thread {

    private ServerSocket server;
    private int port;
    private boolean userOnline = true;

    public boolean isUserOnline() {
        return userOnline;
    }

    public ServidorCliente(int port) {
        this.port = port;
    }

    public void setUserOnline(boolean userOnline) {
        this.userOnline = userOnline;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(this.port);
            server.setReuseAddress(true);
            while (userOnline) {
                Socket conn = server.accept();
                if (conn != null) {
                    System.out.println("~~~Aguardando requisição Cliente: " + conn.getLocalAddress() + " Cliente:" + conn.getInetAddress() + "~~~");
                    GerenciaComunicaoCliente escutaMsg = new GerenciaComunicaoCliente(conn);
                    escutaMsg.start();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPort(int port) {
        this.port = port;
    }
}

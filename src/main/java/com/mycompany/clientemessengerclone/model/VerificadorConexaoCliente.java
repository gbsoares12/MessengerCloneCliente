/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.model;

import com.mycompany.clientemessengerclone.controller.ComunicacaoServidorImpl;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Soares
 */
public class VerificadorConexaoCliente extends Thread {

    private int countVerificador = 0;
    private ComunicacaoServidorImpl controller;

    public VerificadorConexaoCliente() {
        this.controller = ComunicacaoServidorImpl.getInstance();
    }

    
    public int getCountVerificador() {
        return countVerificador;
    }

    public void setCountVerificador(int countVerificador) {
        this.countVerificador = countVerificador;
    }

    @Override
    public void run() {
        while (true) {
            // Caso haja alguém conectado o numero de msg de atualização tem que ser a mesma do countVerificador.
            this.countVerificador++;
            System.out.println("O count de tempo de verificação do cliente está em: " + this.countVerificador);
            try {
                Thread.sleep(3000);
                controller.atualizarConexao();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(VerificadorConexaoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

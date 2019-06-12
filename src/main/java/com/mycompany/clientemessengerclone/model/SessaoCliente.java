/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.model;

import com.mycompany.clientemessengerclone.controller.ComunicacaoServidorImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Soares
 */
public class SessaoCliente implements Serializable {
    
    private Cliente cliente;
    
    private static SessaoCliente instance;//Padrão Singleton

    public synchronized static SessaoCliente getInstance() {//Padrão Singleton
        if (instance == null) {
            instance = new SessaoCliente();
        }
        return instance;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

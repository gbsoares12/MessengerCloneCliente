/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.model;

/**
 *
 * @author Gabriel Soares
 */
public class ServidorInfo {

    private static ServidorInfo instance;
    private String ip = "192.168.2.6";//TIRAR!!!! SOMENTE PARA TESTE O IP CHUMBADO
    private int porta = 56000;//TIRAR!!!! SOMENTE PARA TESTE A PORTA CHUMBADa

    public static ServidorInfo getInstance() {
        if (instance == null) {
            instance = new ServidorInfo();
        }

        return instance;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.model;

import com.google.gson.Gson;
import com.mycompany.clientemessengerclone.utils.Desconectar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Soares
 */
public class EnviaMensagemToCliente extends Thread {

    private SessaoCliente sessaoAtual = SessaoCliente.getInstance();
    private final Socket conn;
    private String msgToEnviar;

    public EnviaMensagemToCliente(Socket conn, String msgToEnviar) {
        this.conn = conn;
        this.msgToEnviar = msgToEnviar;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();

        try {
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("ReceberMsg");

            out.println(gson.toJson(this.sessaoAtual.getCliente()));
            out.println("fimCliente");

            out.println("InicioMsg");
            out.println(this.msgToEnviar);
            out.println("fimmsg");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String primeiraResposta = in.readLine();

            if (primeiraResposta.equalsIgnoreCase("MsgRecebida")) {

                System.out.println("MSG ENVIADA COM SUCESSO!");

            }
        } catch (IOException ex) {
            Logger.getLogger(EnviaMensagemToCliente.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            Desconectar.fechar(in, out, conn);
        }

    }

}

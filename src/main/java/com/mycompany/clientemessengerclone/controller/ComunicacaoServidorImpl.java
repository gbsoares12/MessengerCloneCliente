/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.controller;

import com.google.gson.Gson;
import com.mycompany.clientemessengerclone.model.Cliente;
import com.mycompany.clientemessengerclone.model.ServidorInfo;
import com.mycompany.clientemessengerclone.model.SessaoCliente;
import com.mycompany.clientemessengerclone.utils.Desconectar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Soares
 */
public class ComunicacaoServidorImpl implements ComunicacaoServidorController {

    private SessaoCliente sessaoAtual = SessaoCliente.getInstance();
    private ServidorInfo servInfo = ServidorInfo.getInstance();

    private List<Observador> observadores = new ArrayList<>();
    private static ComunicacaoServidorImpl instance;//Padrão Singleton

    public synchronized static ComunicacaoServidorImpl getInstance() {//Padrão Singleton
        if (instance == null) {
            instance = new ComunicacaoServidorImpl();
        }
        return instance;
    }

    @Override
    public void addObservador(Observador obs) {
        this.observadores.add(obs);
    }

    public boolean entrar(String email, String senha) throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();

        Cliente infoCliLogin = new Cliente();
        infoCliLogin.setEmail(email);
        infoCliLogin.setSenha(senha);
        try {
            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
            infoCliLogin.setIp("" + conn.getLocalAddress());
            String jsonInfoLogin = gson.toJson(infoCliLogin);

            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("Entrar");
            out.println(jsonInfoLogin);
            out.println("fimlogin");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            Cliente cli = gson.fromJson(in.readLine(), Cliente.class);
            this.sessaoAtual.setCliente(cli);
            exibeClienteInfoLogado();
            atualizaListaContato();
            return true;
        } catch (Exception e) {
            exibeClienteLogadoResponse("Usuario ou senha errado!");
            e.printStackTrace();
            return false;
        } finally {
            Desconectar.fechar(in, out, conn);
        }
    }

    public void registrar(String nome, String email, String senha) throws IOException {

        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setEmail(email);
        novoCliente.setSenha(senha);
        novoCliente.setStatus(false);

        Gson gson = new Gson();
        String clienteJson = gson.toJson(novoCliente);

        try {
            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("Registrar");
            out.println(clienteJson);
            out.println("fimcliente");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            if (in.readLine().equalsIgnoreCase("200")) {
                exibeClienteCriadoResponse("Usuário criado com sucesso!");
            } else if (in.readLine().equalsIgnoreCase("500")) {
                exibeClienteCriadoResponse("Erro no servidor!\n*(esse email registrado)");
            }

            System.out.println("resposta servidor: " + in.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Desconectar.fechar(in, out, conn);

        }
    }

    public void sair() throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        try {
            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("Sair");
            out.println(gson.toJson(sessaoAtual.getCliente()));
            out.println("fimsair");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            if (in.readLine().equalsIgnoreCase("200")) {
                exibeClienteCriadoResponse("Logout feito com sucesso!");
            } else if (in.readLine().equalsIgnoreCase("500")) {
                exibeClienteCriadoResponse("Erro no servidor!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao sair!");
            e.printStackTrace();
        } finally {
            Desconectar.fechar(in, out, conn);
        }
    }

    public void exibeClienteCriadoResponse(String response) {
        for (Observador obs : observadores) {
            obs.notificaMensagemRespostaServidor(response);
        }
    }

    public void exibeClienteLogadoResponse(String response) {
        for (Observador obs : observadores) {
            obs.notificaMensagemRespostaServidor(response);
        }
    }

    public void exibeClienteInfoLogado() {
        for (Observador obs : observadores) {
            obs.exibeInfoClienteLogado();
        }
    }
    
    public void atualizaListaContato() {
        for (Observador obs : observadores) {
            obs.atualizarListaContato();
        }
    }

}

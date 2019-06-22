/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.controller;

import com.mycompany.clientemessengerclone.model.Cliente;

/**
 *
 * @author Gabriel Soares
 */
public interface Observador {

    public void notificaMensagemRespostaServidor(String responseDoServer);
    public void exibeInfoClienteLogado();
    public void atualizarListaContato();
    public void exibeUserBuscado(String nome, String email);
    public int escolhaAdicionar();
    public void exibeMsg(String msg);
    public void exibeMsgVindaCliente(String msg);
    public void abreChat(Cliente cliContato);
    
}

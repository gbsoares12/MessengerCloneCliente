/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Soares
 */
public class Cliente implements Serializable {

    private long id;
    private String ip;
    private String senha;
    private String email;
    private int porta;
    private boolean status;
    private String nome;
    private List<Cliente> listaContatos = new ArrayList<>();

    public Cliente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Cliente> getListaContatos() {
        return listaContatos;
    }

    public void setListaContatos(List<Cliente> listaContatos) {
        this.listaContatos = listaContatos;
    }

    public void addContato(Cliente cli) {
        this.listaContatos.add(cli);
    }

    public void removeContato(Cliente cli) {
        this.listaContatos.remove(cli);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", ip=" + ip + ", senha=" + senha + ", email=" + email + ", porta=" + porta + ", status=" + status + ", nome=" + nome + ", listaContatos=" + listaContatos + '}';
    }

}
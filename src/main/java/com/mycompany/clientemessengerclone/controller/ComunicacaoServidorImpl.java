/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientemessengerclone.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.clientemessengerclone.model.Cliente;
import com.mycompany.clientemessengerclone.model.ServidorInfo;
import com.mycompany.clientemessengerclone.model.SessaoCliente;
import com.mycompany.clientemessengerclone.model.VerificadorConexaoCliente;
import com.mycompany.clientemessengerclone.utils.Desconectar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    private int countVerificadorDoCliente;
    private VerificadorConexaoCliente verificadorCliente;
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
        this.verificadorCliente = new VerificadorConexaoCliente();
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

            String line = "";
            while (!(line = in.readLine()).equalsIgnoreCase("fimverificador")) {
                if (line.equalsIgnoreCase("countVerificadorServidor")) {
                    verificadorCliente.setCountVerificador(Integer.parseInt(in.readLine()));
                    verificadorCliente.start();
                }
            }

            System.out.println("Cont do cliente recebida do servidor vai ser: " + this.countVerificadorDoCliente);
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

    public void atualizarConexao() throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        try {
            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("Atualizar");
            out.println("objCliente");
            out.println(gson.toJson(sessaoAtual.getCliente()));

            out.println("countVerificadorCliente");
            out.println(this.verificadorCliente.getCountVerificador());
            out.println("fimcountcliente");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            Cliente cli = gson.fromJson(in.readLine(), Cliente.class);
            this.sessaoAtual.setCliente(cli);
            exibeClienteInfoLogado();
            atualizaListaContato();

            if (in.readLine().equalsIgnoreCase("200")) {
                System.out.println("Atualizada a conexão!!");
            } else if (in.readLine().equalsIgnoreCase("500")) {
                exibeClienteCriadoResponse("Você perdeu a conexão com o servidor");
                System.exit(0);
            }

        } catch (JsonSyntaxException | IOException e) {
            exibeClienteCriadoResponse("Você perdeu a conexão com o servidor");
            System.exit(0);
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

    /*
       static public void buscarUser() throws IOException {
        Gson gson = new Gson();
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String json = "{\"email\":\"test123123123@gmail.com\"}";
        try {
            conn = new Socket("192.168.2.6", 56000);
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("BuscaUser");
            out.println(json);
            out.println("fimbusca");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Cliente cliBuscado = gson.fromJson(in.readLine(), Cliente.class);
            System.out.println("USUÁRIO PESQUISADO: " + cliBuscado);

            //LOGICA DE ADD O USUARIO BUSCADO
            if (JOptionPane.showInputDialog("deseja adicionar o usuario? 1 sim 2 nao").equalsIgnoreCase("1")) {

                getCliSessao().addContato(cliBuscado);
                fechar(in, out, conn);
                System.out.println("OBJ DO CLIENTE SESSAO: " + getCliSessao());

                //NOVA CONEXAO PARA DAR O MERGE NO CLIENTE SALVO NO BANCO
                conn = new Socket("192.168.2.6", 56000);
                out = new PrintWriter(conn.getOutputStream(), true);
                out.println("AddUser");
                out.println(gson.toJson(getCliSessao()));
                out.println("fimadd");
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                setCliSessao(gson.fromJson(in.readLine(), Cliente.class));

                //O CLIENTE JA ESTÁ COM A LISTA ATUALIZADA DEPOIS DE ADICIONAR UM USUÁRIO
                System.out.println("TESTE SESSAO DO CLIENTE: " + getCliSessao());
            } else if (in.readLine().equalsIgnoreCase("404")) {
                System.out.println("usuario não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fechar(in, out, conn);
        }
    }

    static public void editar() throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        try {
            conn = new Socket("192.168.2.6", 56000);
            Cliente cliEditado = getCliSessao();
            cliEditado.setNome("Beltrano");
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("EditarUser");
            out.println(gson.toJson(cliEditado));
            out.println("fimeditar");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Cliente cli = gson.fromJson(in.readLine(), Cliente.class);
            setCliSessao(cli);
            //Cliente da sessão com as informações depois de editado
            System.out.println(getCliSessao());
        } catch (Exception e) {
            System.out.println("Erro ao editar o Usuário!");
            e.printStackTrace();
        } finally {
            fechar(in, out, conn);
        }
    }

    static public void removerUser() throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        try {
            conn = new Socket("192.168.2.6", 56000);
            Cliente cliAux = getCliSessao();
            List<Cliente> listaContadosClienteSessao = cliAux.getListaContatos();

            //encontra o usuário pelo nome que será removido.
            Cliente cliARemover = null;
            for (Cliente cliente : listaContadosClienteSessao) {
                if (cliente.getEmail().equalsIgnoreCase("test123123123@gmail.com")) {
                    cliARemover = cliente;
                }
            }
            cliAux.getListaContatos().remove(cliARemover);

            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("RemoverUser");
            out.println(gson.toJson(cliAux));
            out.println("fimremover");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Cliente cli = gson.fromJson(in.readLine(), Cliente.class);
            setCliSessao(cli);
            //Cliente da sessão com as informações depois de editado
            System.out.println(getCliSessao());
        } catch (Exception e) {
            System.out.println("Erro ao excluir o contato!");
            e.printStackTrace();
        } finally {
            fechar(in, out, conn);
        }
    }
    
     */
}

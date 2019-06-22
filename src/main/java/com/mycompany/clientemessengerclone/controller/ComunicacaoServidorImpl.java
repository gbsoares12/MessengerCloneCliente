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
import com.mycompany.clientemessengerclone.view.Login;
import com.mycompany.clientemessengerclone.view.MenuPrincipal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

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
        boolean jaCadastrado = false;
        for (Observador obsJaInserido : observadores) {
            if (obsJaInserido.getClass() == obs.getClass()) {
                jaCadastrado = true;
            }
        }
        if (!jaCadastrado) {
            this.observadores.add(obs);
        }
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

            String primeiraLinha = in.readLine();
            if (!primeiraLinha.equalsIgnoreCase("503")) {
                Cliente cli = gson.fromJson(primeiraLinha, Cliente.class);
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
                return true;
            } else {
                exibeClienteLogadoResponse("Sua conta já está online!");
                return false;
            }

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
            this.verificadorCliente.setUserOnline(false);

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            line = in.readLine();
            if (line.equalsIgnoreCase("200")) {
                this.sessaoAtual.setCliente(null);
                exibeMsg("Logout feito com sucesso!");
            } else if (line.equalsIgnoreCase("500")) {
                exibeMsg("Erro no servidor!");
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
        if (this.sessaoAtual.getCliente() != null) {
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
                String primeiraLinha = in.readLine();

                Cliente cli = gson.fromJson(primeiraLinha, Cliente.class);
                this.sessaoAtual.setCliente(cli);
                atualizaListaContato();

                String segundaLinha = "";
                segundaLinha = in.readLine();
                if (segundaLinha.equalsIgnoreCase("200")) {
                    System.out.println("Atualizada a conexão!!");
                } else if (segundaLinha.equalsIgnoreCase("500")) {
                    exibeClienteCriadoResponse("Você perdeu a conexão com o servidor");
                    System.exit(0);
                }

            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
                exibeClienteCriadoResponse("Você perdeu a conexão com o servidor");
                System.exit(0);
            } finally {
                Desconectar.fechar(in, out, conn);
            }
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

    public void exibeMsg(String msg) {
        for (Observador obs : observadores) {
                obs.exibeMsg(msg);
        }
    }

    public void exibeClienteInfoLogado() {
        for (Observador obs : observadores) {
            obs.exibeInfoClienteLogado();
        }
    }

    public void exibeClienteBuscado(String nome, String email) {
        for (Observador obs : observadores) {
            obs.exibeUserBuscado(nome, email);
        }
    }

    public int escolhaAdicionar() {
        int escolha = -1;
        for (Observador obs : observadores) {
            if (obs.getClass() == MenuPrincipal.class) {
                escolha = obs.escolhaAdicionar();
            }
        }
        return escolha;
    }

    public void atualizaListaContato() {
        for (Observador obs : observadores) {
            if (obs.getClass() == MenuPrincipal.class) {
                obs.atualizarListaContato();
            }
        }
    }

    public void buscarUser(String email) throws IOException {
        Gson gson = new Gson();
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String json = "{\"email\":\"" + email + "\"}";
        if (email.equalsIgnoreCase(this.sessaoAtual.getCliente().getEmail())) {
            exibeMsg("Por que você está tentando adicionar você mesmo? Isso não faz sentido (0.o) .");
        } else {
            try {
                conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
                out = new PrintWriter(conn.getOutputStream(), true);
                out.println("BuscaUser");
                out.println(json);
                out.println("fimbusca");
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String primeiraResposta = in.readLine();
                if (!primeiraResposta.equalsIgnoreCase("404")) {
                    Cliente cliBuscado = gson.fromJson(primeiraResposta, Cliente.class);
                    exibeClienteBuscado(cliBuscado.getNome(), cliBuscado.getEmail());
                    //LOGICA DE ADD O USUARIO BUSCADO
                    if (escolhaAdicionar() == 0) {
                        boolean jaCadastrado = false;
                        for (Cliente clienteDaListaContato : this.sessaoAtual.getCliente().getListaContatos()) {
                            if (clienteDaListaContato.getEmail().equalsIgnoreCase(cliBuscado.getEmail())) {
                                jaCadastrado = true;
                            }
                        }
                        if (!jaCadastrado) {
                            this.sessaoAtual.getCliente().addContato(cliBuscado);
                            Desconectar.fechar(in, out, conn);

                            //NOVA CONEXAO PARA DAR O MERGE NO CLIENTE SALVO NO BANCO
                            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
                            out = new PrintWriter(conn.getOutputStream(), true);
                            out.println("AddUser");
                            out.println(gson.toJson(this.sessaoAtual.getCliente()));
                            out.println("fimadd");
                            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            this.sessaoAtual.setCliente(gson.fromJson(in.readLine(), Cliente.class));
                            exibeMsg("Usuário adicionado!");
                            atualizaListaContato();
                        } else {
                            exibeMsg("Usuário já cadastrado na sua lista de contato!");
                        }
                    }
                } else {
                    exibeMsg("Usuário não encontrado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Desconectar.fechar(in, out, conn);
            }
        }

    }

    public void editar(String nome, String senha) throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        try {
            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());

            Cliente cliEditado = this.sessaoAtual.getCliente();
            cliEditado.setNome(nome);
            cliEditado.setSenha(senha);
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println("EditarUser");
            out.println(gson.toJson(cliEditado));
            out.println("fimeditar");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Cliente cli = gson.fromJson(in.readLine(), Cliente.class);
            this.sessaoAtual.setCliente(cli);
            exibeMsg("Dados atualizados com sucesso!");
            //Cliente da sessão com as informações depois de editado
            exibeClienteInfoLogado();

        } catch (Exception e) {
            exibeMsg("Erro ao editar o Usuário!");
            e.printStackTrace();
        } finally {
            Desconectar.fechar(in, out, conn);
        }
    }

    public void removerUser(String email) throws IOException {
        Socket conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        try {
            conn = new Socket(this.servInfo.getIp(), servInfo.getPorta());
            Cliente cliAux = this.sessaoAtual.getCliente();
            List<Cliente> listaContadosClienteSessao = cliAux.getListaContatos();
            boolean encontrouNosContatos = false;
            //encontra o usuário pelo nome que será removido.
            Cliente cliARemover = null;
            for (Cliente cliente : listaContadosClienteSessao) {
                if (cliente.getEmail().equalsIgnoreCase(email)) {
                    cliARemover = cliente;
                    encontrouNosContatos = true;
                }
            }
            if (encontrouNosContatos) {
                cliAux.getListaContatos().remove(cliARemover);
                out = new PrintWriter(conn.getOutputStream(), true);
                out.println("RemoverUser");
                out.println(gson.toJson(cliAux));
                out.println("fimremover");
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Cliente cli = gson.fromJson(in.readLine(), Cliente.class);
                this.sessaoAtual.setCliente(cli);
                exibeMsg("Usuário removido da sua lista de contato!");
                atualizaListaContato();
            } else {
                exibeMsg("Usuário não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir o contato!");
            e.printStackTrace();
        } finally {
            Desconectar.fechar(in, out, conn);
        }
    }
}

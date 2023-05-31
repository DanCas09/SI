package presentation;

import businessLogic.executor.ExecutorDB;
import businessLogic.executor.ExecutorOperation;
import businessLogic.register.entities.JogadorRM;
import jakarta.persistence.EntityManager;
import businessLogic.scopes.DataScope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ServiceMain {

    static ExecutorOperation exe;

    public static void main(String[] args) {
        try {
            showCommandMenu();
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCommandMenu() {
        System.out.println(" Menu:\n" +
                "1 -> (d) : criarJogador\n" +
                "2 -> (d) : desativarJogador\n" +
                "3 -> (d) : banirJogador\n" +
                "4 -> (e) : totalPontosJogador\n" +
                "5 -> (f) : totalJogosJogador\n" +
                "exit -> exit\n" +
                "l -> menu\n" +
                "\n");
    }

    public static void menu() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String n = br.readLine();
        EntityManager em = null;

        while (!Objects.equals(n, "exit")) {

            try (DataScope ds = new DataScope()) {
                em = ds.getEntityManager();
                exe = new ExecutorOperation(em);

                switch (n) {
                    case "1" -> criarJogadorOption();
                    case "2" -> desativarJogadorOption();
                    case "3" -> banirJogadorOption();
                    case "4" -> totalPontosJogadorOption();
                    case "5" -> totalJogosJogadorOption();
                    case "l" -> showCommandMenu();
                    default -> menu();
                }
                n = br.readLine();
                //em.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void criarJogadorOption() {
        try {
            System.out.println("---- Criar Jogador ----");
            System.out.println("Email: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String email = br.readLine();
            System.out.println("Username: ");
            String username = br.readLine();
            System.out.println("Estado: ");
            String estado = br.readLine();
            System.out.println("Regiao: ");
            String regiao = br.readLine();
            exe.criarJogador(email, username, estado, regiao);
            System.out.println("---- Jogador Criado ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void desativarJogadorOption() {
        try {
            System.out.println("---- Desativar Jogador ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            exe.desativarJogador(idJogador);
            System.out.println("---- Jogador Desativado ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void banirJogadorOption() {
        try {
            System.out.println("---- Banir Jogador ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            exe.banirJogador(idJogador);
            System.out.println("---- Jogador Banido ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void totalPontosJogadorOption() {
        try {
            System.out.println("---- Total Pontos Jogador ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            exe.totalPontosJogador(idJogador);
            System.out.println("---- Total Pontos Jogador ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void totalJogosJogadorOption() {
        try {
            System.out.println("---- Total Jogos Jogador ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            exe.totalJogosJogador(idJogador);
            System.out.println("---- Total Jogos Jogador ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

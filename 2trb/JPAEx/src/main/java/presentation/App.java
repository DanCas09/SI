package presentation;

import businessLogic.executor.ExecutorOperation;
import concrete.operations.RepoProcedures;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import scopes.DataScope;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class App {

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
                "6 -> (g) : pontosJogoPorJogador\n" +
                "7 -> (h) : associarCracha\n" +
                "8 -> (i) : iniciarConversa\n" +
                "9 -> (j) : juntarConversa\n" +
                "10 -> (k) : enviarMensagem\n" +
                "11 -> (l) : jogadorTotalInfo\n" +
                "12 -> (?) : associarCrachaRepoMapper\n" +
                "13 -> (?) : crachaAcrescentarPontos\n" +
                "exit -> exit\n" +
                "l -> menu\n" +
                "\n");
    }

    public static void menu() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String n = br.readLine();
        //EntityManager em;

        while (!Objects.equals(n, "exit")) {

            try (DataScope ds = new DataScope()) {
                EntityManager em = ds.getEntityManager();
                System.out.println(em.toString());

                exe = new ExecutorOperation(em);

                switch (n) {
                    case "1" -> criarJogadorOption();
                    case "2" -> desativarJogadorOption();
                    case "3" -> banirJogadorOption();
                    case "4" -> totalPontosJogadorOption();
                    case "5" -> totalJogosJogadorOption();
                    case "6" -> pontosJogoPorJogadorOption();
                    case "7" -> associarCrachaOption();
                    case "8" -> iniciarConversaOption();
                    case "9" -> juntarConversaOption();
                    case "10" -> enviarMensagemOption();
                    case "11" -> jogadorTotalInfoOption();
                    case "12" -> associarCrachaOptionRepoMapper();
                    case "13" -> crachaAcrescentarPontosOption();
                    //case "l" -> showCommandMenu();
                    default -> menu();
                }
                ds.validateWork();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                showCommandMenu();
                n = br.readLine();
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

    //TODO
    private static void pontosJogoPorJogadorOption() {
        try {
            System.out.println("---- Pontos Jogo Por Jogador ----");
            System.out.println("Id Jogo: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String idJogo = br.readLine();

            exe.pontosJogoPorJogador(idJogo);
            System.out.println("---- Pontos Jogo Por Jogador ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void associarCrachaOption() {
        try {
            System.out.println("---- Associar Cracha SQL ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            System.out.println("Id Jogo: ");
            String idJogo = br.readLine();
            System.out.println("Nome Cracha: ");
            String nomeCracha = br.readLine();

            exe.associarCracha(idJogador, idJogo, nomeCracha);
            System.out.println("---- Associar Cracha SQL ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TOFINISH
    private static void iniciarConversaOption() {
        try {
            System.out.println("---- Iniciar Conversa ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            System.out.println("Nome Conversa: ");
            String nomeConversa = br.readLine();

            exe.iniciarConversa(idJogador, nomeConversa);
            System.out.println("---- Iniciar Conversa ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void juntarConversaOption() {
        try {
            System.out.println("---- Juntar Conversa ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            System.out.println("Id Conversa: ");
            int idConversa = Integer.parseInt(br.readLine());

            exe.juntarConversa(idJogador, idConversa);
            System.out.println("---- Juntar Conversa ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviarMensagemOption() {
        try {
            System.out.println("---- Enviar Mensagem ----");
            System.out.println("Id Conversa: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idConversa = Integer.parseInt(br.readLine());
            System.out.println("Id Jogador: ");
            int idJogador = Integer.parseInt(br.readLine());
            System.out.println("Texto Mensagem: ");
            String textoMensagem = br.readLine();

            exe.enviarMensagem(idConversa, idJogador, textoMensagem);
            System.out.println("---- Enviar Mensagem ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO
    private static void jogadorTotalInfoOption() {
        try {
            System.out.println("---- Jogador Total Info ----");
            exe.jogadorTotalInfo();
            System.out.println("---- Jogador Total Info ----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void associarCrachaOptionRepoMapper() {
        try {
            System.out.println("---- Associar Cracha ----");
            System.out.println("Id Jogador: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int idJogador = Integer.parseInt(br.readLine());
            System.out.println("Id Jogo: ");
            String idJogo = br.readLine();
            System.out.println("Nome Cracha: ");
            String nomeCracha = br.readLine();

            RepoProcedures.associarCracha(idJogador, idJogo, nomeCracha);
            System.out.println("---- Associar Cracha Repo/Mapper----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void crachaAcrescentarPontosOption() {
        try {
            System.out.println("---- Acrescentar Pontos----");
            System.out.println("Id Jogo: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String idJogo = br.readLine();
            System.out.println("Nome Cracha: ");
            String nomeCracha = br.readLine();
            System.out.println("1 -> Modo Otimista\n2 -> Modo Pessimista ");
            int num = Integer.parseInt(br.readLine());
            LockModeType mode;
            if (num == 1)
                mode = LockModeType.OPTIMISTIC_FORCE_INCREMENT;
            else
                mode = LockModeType.PESSIMISTIC_FORCE_INCREMENT;
            RepoProcedures.crachaIncreasePoints(idJogo, nomeCracha, mode);
            System.out.println("---- Acrescentar Pontos----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
package presentation;

import concrete.entities.JogadorRM;
import concrete.GenericRepository;
import concrete.operations.RepoProcedures;
import jakarta.persistence.EntityManager;
import model.Cracha;
import model.Jogador;
import model.Jogo;
import scopes.DataScope;

public class RepoMapperMain {

    public static void main(String[] args) {

        GenericRepository<Jogador, Integer> genericJogador = new GenericRepository<>(Jogador.class, Integer.class);
        GenericRepository<Cracha, Integer> genericCracha = new GenericRepository<>(Cracha.class, Integer.class);
        GenericRepository<Jogo, String> genericJogo = new GenericRepository<>(Jogo.class, String.class);
        try (DataScope ds = new DataScope()) {
            // Can you create an example of Jogador to test the method add and delete?
            Jogador jogador = new JogadorRM("koff@gmail.com", "koff", "Ativo", "Lisboa").createJogador();

            RepoProcedures.crachaIncreasePoints("jg1", "Fantasma Preto");
//            System.out.println("------READ-----");
//            Jogador jogador1 = genericJogador.Find(49);
//            System.out.println(jogador1);

 //           RepoProcedures.associarCracha(66, "jg1", "Fantasma Branco");

//            System.out.println("------CREATE-----");
//            genericJogador.Add(jogador);
//
//
//            System.out.println("------DELETE-----");
//            genericJogador.Delete(jogador1);
//
//            System.out.println("------UPDATE-----");
//            String newEmail = "changed@gmail.com";
//            jogador1.setEmail(newEmail);
//            System.out.println(jogador1);
//            genericJogador.Save(jogador1);
//
//            System.out.println("------HERE-----");
//            System.out.println("Jogador added successfully. ID: " + jogador.getId());

            // Printing all the Jogadores in the database
//            genericJogador.GetAll().forEach(System.out::println);
            //System.out.println(genericJogador.Find(1));

            // NOW FOR CRACHA
//            Jogo jogo = genericJogo.Find("jg1");
//            System.out.println(jogo);
//
//            Cracha cracha = new CrachaRM(jogo, "NOVO CRACHA", 100, "https://www.google.com").createCracha();
//            genericCracha.Add(cracha);
        ds.validateWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

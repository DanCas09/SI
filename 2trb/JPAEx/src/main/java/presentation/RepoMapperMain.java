package presentation;

import businessLogic.register.entities.CrachaRM;
import businessLogic.register.entities.JogadorRM;
import concrete.GenericRepository;
import model.Cracha;
import model.Jogador;
import model.Jogo;

public class RepoMapperMain {

    public static void main(String[] args) {

        GenericRepository<Jogador, Integer> genericJogador = new GenericRepository<>(Jogador.class, Integer.class);
        GenericRepository<Cracha, Integer> genericCracha = new GenericRepository<>(Cracha.class, Integer.class);
        GenericRepository<Jogo, String> genericJogo = new GenericRepository<>(Jogo.class, String.class);
        try {
            // Can you create an example of Jogador to test the method add and delete?
            Jogador jogador = new JogadorRM("koff@gmail.com", "koff", "Ativo", "Lisboa").createJogador();

//            System.out.println("------READ-----");
//            Jogador jogador1 = genericJogador.Find(1);
//            System.out.println(jogador1);

//            System.out.println("------CREATE-----");
//            genericJogador.Add(jogador);
//
//
//            System.out.println("------DELETE-----");
//            genericJogador.Delete(jogador);
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
            genericJogador.GetAll().forEach(System.out::println);
            //System.out.println(genericJogador.Find(1));

            // NOW FOR CRACHA
            Jogo jogo = genericJogo.Find("jg1");
            System.out.println(jogo);

            Cracha cracha = new CrachaRM(jogo, "NOVO CRACHA", 100, "https://www.google.com").createCracha();
            genericCracha.Add(cracha);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

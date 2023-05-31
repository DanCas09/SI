package presentation;

import businessLogic.register.entities.JogadorRM;
import concrete.GenericRepository;
import model.Jogador;

public class RepoMapperMain {

    public static void main(String[] args) {

        GenericRepository<Jogador, Integer> genericR = new GenericRepository<>(Jogador.class, Integer.class);
        try {
            // Can you create an example of Jogador to test the method add and delete?
            Jogador jogador = new JogadorRM("koff@gmail.com", "koff", "Ativo", "Lisboa").createJogador();

//            System.out.println("------READ-----");
//            Jogador jogador1 = genericR.Find(1);
//            System.out.println(jogador1);

//            System.out.println("------CREATE-----");
//            genericR.Add(jogador);
//
//
//            System.out.println("------DELETE-----");
//            genericR.Delete(jogador);
//
//            System.out.println("------UPDATE-----");
//            String newEmail = "changed@gmail.com";
//            jogador1.setEmail(newEmail);
//            System.out.println(jogador1);
//            genericR.Save(jogador1);
//
//            System.out.println("------HERE-----");
//            System.out.println("Jogador added successfully. ID: " + jogador.getId());

            // Printing all the Jogadores in the database
            genericR.GetAll().forEach(System.out::println);
            //System.out.println(genericR.Find(1));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

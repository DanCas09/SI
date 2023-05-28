package presentation;

import concrete.GenericRepository;
import model.Jogador;
import model.Regiao;

public class main {

    public static void main(String[] args) {

        GenericRepository<Jogador, Integer> genericR = new GenericRepository<>(Jogador.class, Integer.class);
        try {
            // Can you create an example of Jogador to test the method add and delete?
            Regiao r = new Regiao();
            r.setNome("Lisboa");

            Jogador jogador = new Jogador();
            jogador.setEmail("koff@gmail.com");
            jogador.setUsername("koff");
            jogador.setEstado("Ativo");
            jogador.setRegiao(r);

            System.out.println("------CREATE-----");
            //genericR.Add(jogador);

//            System.in.read();

//            System.out.println("------DELETE-----");
//            genericR.Delete(jogador);

            System.out.println("------HERE-----");
            System.out.println("Jogador added successfully. ID: " + jogador.getId());

            // Printing all the Jogadores in the database
            genericR.GetAll().forEach(System.out::println);
            //System.out.println(genericR.Find(1));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

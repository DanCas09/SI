package presentation;

import concrete.GenericRepository;
import model.Jogador;

public class main {

    public static void main(String[] args) {

        GenericRepository<Jogador, Integer> genericR = new GenericRepository<>(Jogador.class, Integer.class);
        try {
            //genericR.GetAll().forEach(System.out::println);
            //System.out.println(genericR.Find(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

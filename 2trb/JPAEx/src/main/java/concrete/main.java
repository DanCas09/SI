package concrete;

import model.Jogador;

public class main {

    public static void main(String[] args) {

        GenericRepository<Jogador, Integer> genericR = new GenericRepository<>();
        try {
            genericR.GetAll().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

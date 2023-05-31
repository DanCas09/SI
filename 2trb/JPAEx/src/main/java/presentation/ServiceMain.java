package presentation;

import businessLogic.executor.ExecutorDB;
import businessLogic.executor.ExecutorOperation;
import businessLogic.register.entities.JogadorRM;
import jakarta.persistence.EntityManager;
import businessLogic.scopes.DataScope;

public class ServiceMain {

    static DataScope ds = new DataScope();
    static EntityManager em = ds.getEntityManager();

    public static void main(String[] args){
        try {
            ExecutorOperation exe = new ExecutorOperation(em);

            //exe.criarJogador("koff@gmail.com", "koff", "Ativo", "Lisboa");
            //exe.totalJogosJogador(1);
            exe.totalPontosJogador(1);
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

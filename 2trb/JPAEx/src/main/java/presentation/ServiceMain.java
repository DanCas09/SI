package presentation;

import businessLogic.executor.ExecutorDB;
import businessLogic.register.entities.JogadorRM;
import jakarta.persistence.EntityManager;
import businessLogic.scopes.DataScope;

public class ServiceMain {

    static DataScope ds = new DataScope();
    static EntityManager em = ds.getEntityManager();

    public static void main(String[] args) throws Exception {
        try {
            ExecutorDB exe = new ExecutorDB(em);

            int idJogador = 1;
            String functionName = "totalJogosJogador";
            Object[] argsFun = { idJogador }; // idJogador

            exe.execute(argsFun, functionName, em);

            idJogador = 2;
            functionName = "totalPontosJogador";
            argsFun = new Object[]{ idJogador }; // idJogador

            exe.execute(argsFun, functionName, em);

//            functionName = "criarJogador";
//            argsFun = new Object[]{ "daniel@gmail.com", "daniel", "Ativo", "Lisboa" }; // email, username, estado, regiao
//
//            exe.execute(argsFun, functionName, em);

//            functionName = "desativarJogador";
//            idJogador = 44;
//            argsFun = new Object[]{ idJogador }; // idJogador
//
//            exe.execute(argsFun, functionName, em);

            // NOW FOR CRACHA OPERATION



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

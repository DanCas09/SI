package presentation;

import businessLogic.executor.ExecutorDB;
import jakarta.persistence.EntityManager;
import businessLogic.scopes.DataScope;

public class ServiceMain {

    static DataScope ds = new DataScope();
    static EntityManager em = ds.getEntityManager();

    public static void main(String[] args) throws Exception {
        try (DataScope ds = new DataScope()) {
            int idJogador = 1;
            String functionName = "totalJogosJogador";
            Object[] argsFun = { idJogador }; // idJogador

            ExecutorDB.registerAndExecuteFunction(functionName, argsFun, em);

            //idJogador = 1;
            functionName = "totalPontosJogador";
            argsFun = new Object[]{ idJogador }; // idJogador

            ExecutorDB.registerAndExecuteFunction(functionName, argsFun, em);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

package presentation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import scopes.DataScope;
import service.ParameterFunction;
import service.Service;

public class ServiceMain {

        static DataScope ds = new DataScope();
        static EntityManager em = ds.getEntityManager();

        public static void main(String[] args) throws Exception {
            try (DataScope ds = new DataScope()){

                ParameterFunction idJogador = new ParameterFunction(Integer.class, ParameterMode.IN);
                ParameterFunction totalJogosParameter = new ParameterFunction(Integer.class, ParameterMode.OUT);

                ParameterFunction[] funArgs = {idJogador, totalJogosParameter};

                Service.registerFunction("dbo.totalJogosJogador", funArgs, ds.getEntityManager());

                System.out.println("totalJogosJogador function");

                String functionName = "dbo.totalJogosJogador";

                Object[] args2 = {1}; //idJogador
                Integer totalJogos = (Integer) Service.executeFunction(functionName, args2);

                System.out.println("Total games of player 1 is " + totalJogos);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
}

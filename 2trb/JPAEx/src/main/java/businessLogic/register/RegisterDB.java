package businessLogic.register;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import businessLogic.service.ParameterFunction;
import businessLogic.service.Service;
import model.Regiao;

public class RegisterDB {
    public static void registerTotalJogosJogadorFunction(EntityManager entityManager) throws Exception {
        ParameterFunction idJogador = new ParameterFunction(Integer.class, ParameterMode.IN);
        ParameterFunction totalJogosParameter = new ParameterFunction(Integer.class, ParameterMode.OUT);

        ParameterFunction[] funArgs = { idJogador, totalJogosParameter };

        Service.registerFunction("dbo.totalJogosJogador", funArgs, entityManager);
    }

    public static void registerTotalPontosJogadorFunction(EntityManager entityManager) throws Exception {
        ParameterFunction idJogador = new ParameterFunction(Integer.class, ParameterMode.IN);
        ParameterFunction totalPontosParameter = new ParameterFunction(Integer.class, ParameterMode.OUT);

        ParameterFunction[] funArgs = { idJogador, totalPontosParameter };

        Service.registerFunction("dbo.totalPontosJogador", funArgs, entityManager);
    }

}

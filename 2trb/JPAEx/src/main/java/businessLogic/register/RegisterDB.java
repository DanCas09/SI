package businessLogic.register;

import businessLogic.executor.ExecutorDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import businessLogic.service.ParameterFunction;
import businessLogic.service.Service;
import model.Regiao;

import java.util.List;

public class RegisterDB {

    EntityManager em;


    public RegisterDB(EntityManager em) {
        this.em = em;
    }

    public void registerTotalJogosJogadorFunction() throws Exception {
        ParameterFunction idJogador = new ParameterFunction(Integer.class, ParameterMode.IN);
        ParameterFunction[] funArgs = { idJogador };
        Service.registerFunction("dbo.totalJogosJogador", funArgs, em);
    }

    public void registerTotalPontosJogadorFunction() throws Exception {
        ParameterFunction idJogador = new ParameterFunction(Integer.class, ParameterMode.IN);
        ParameterFunction[] funArgs = { idJogador };
        Service.registerFunction("dbo.totalPontosJogador", funArgs, em);
    }


    public void registerPontosJogoPorJogadorFunction() {
        ParameterFunction idJogo = new ParameterFunction(String.class, ParameterMode.IN);
        ParameterFunction[] funArgs = { idJogo};
        Service.registerFunction("dbo.pontosJogoPorJogador", funArgs, em);
    }

    public void iniciarConversaFunction() {
        ParameterFunction idJogador = new ParameterFunction(Integer.class, ParameterMode.IN);
        ParameterFunction nomeConversa = new ParameterFunction(String.class, ParameterMode.IN);
        ParameterFunction idConversa = new ParameterFunction(Integer.class, ParameterMode.OUT);

        ParameterFunction[] funArgs = { idJogador, nomeConversa, idConversa };

        Service.registerFunction("dbo.iniciarConversa", funArgs, em);
    }

}

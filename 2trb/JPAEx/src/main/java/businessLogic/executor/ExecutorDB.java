package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import jakarta.persistence.EntityManager;

public class ExecutorDB implements Executor {

    private static final String SCHEMA = "dbo";
    @Override
    public void execute(Object[] args, String functionName, EntityManager em) throws Exception {
        registerAndExecuteFunction(functionName, args, em);
    }

    public static void registerAndExecuteFunction(String functionName, Object[] args, EntityManager em) throws Exception {
        String functionCanonicalName = SCHEMA + "." + functionName;

        switch (functionName) {
            case "totalJogosJogador" -> RegisterDB.registerTotalJogosJogadorFunction(em);
            case "totalPontosJogador" -> RegisterDB.registerTotalPontosJogadorFunction(em);
            default -> throw new IllegalArgumentException("Function " + functionName + " not found");
        }
        executeFunction(functionCanonicalName, args);
    }

    private static void executeFunction(String functionName, Object[] args) throws Exception {
        Object result = Service.executeFunction(functionName, args);
        System.out.println("Function " + functionName + " executed with result: " + result);
    }

}

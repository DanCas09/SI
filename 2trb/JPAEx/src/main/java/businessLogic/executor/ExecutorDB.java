package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import jakarta.persistence.EntityManager;

public class ExecutorDB implements Executor {

    public ExecutorDB(EntityManager em) {
        registerAllFunctions(em);
    }

    private static final String SCHEMA = "dbo";

    @Override
    public void execute(Object[] args, String functionName, EntityManager em) throws Exception {
        registerAndExecuteFunction(functionName, args, em);
    }

    private void registerAllFunctions(EntityManager em) {
        try {
            RegisterDB.registerTotalJogosJogadorFunction(em);
            RegisterDB.registerTotalPontosJogadorFunction(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerAndExecuteFunction(String functionName, Object[] args, EntityManager em) throws Exception {
        String functionCanonicalName = SCHEMA + "." + functionName;

        // Verify if function or procedure then execute
        if (Service.isFunction(functionCanonicalName)) {
            executeFunction(functionCanonicalName, args);
        }
        else executeProc(functionCanonicalName, args, em);
    }

    private void executeFunction(String functionName, Object[] args) {
        Object result = Service.executeFunction(functionName, args);
        System.out.println("Function " + functionName + " executed with result: " + result);
    }

    private void executeProc(String procName, Object[] args, EntityManager em) throws Exception {
        Service.executeProcedure(procName, args, em);
        System.out.println("Procedure " + procName + " executed");
    }

}

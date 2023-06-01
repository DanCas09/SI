package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import jakarta.persistence.EntityManager;

public class ExecutorDB implements Executor {

    private RegisterDB register;

    public ExecutorDB(EntityManager em) {
        register = new RegisterDB(em);
        registerAllFunctions();
    }

    private static final String SCHEMA = "dbo";

    @Override
    public void execute(Object[] args, String functionName, EntityManager em) throws Exception {
        registerAndExecuteFunction(functionName, args, em);
    }

    private void registerAllFunctions() {
        try {
            register.registerTotalJogosJogadorFunction();
            register.registerTotalPontosJogadorFunction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerAndExecuteFunction(String functionName, Object[] args, EntityManager em) throws Exception {
        String functionCanonicalName = SCHEMA + "." + functionName;

        // Verify if function or procedure then execute
        if (Service.isFunction(functionCanonicalName)) {
            executeFunction(functionCanonicalName, args, em);
        }
        else executeProc(functionCanonicalName, args, em);
    }

    private void executeFunction(String functionName, Object[] args, EntityManager em) throws Exception {
        Object result = Service.executeFunction(functionName, args, em);
        System.out.println("Function " + functionName + " executed with result: " + result);
    }

    private void executeProc(String procName, Object[] args, EntityManager em) throws Exception {
        Service.executeProcedure(procName, args, em);
        System.out.println("Procedure " + procName + " executed");
    }

}

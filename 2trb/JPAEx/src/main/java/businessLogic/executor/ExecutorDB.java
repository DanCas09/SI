package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import businessLogic.annotations.Function;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ExecutorDB implements Executor {

    private EntityManager em;

    private RegisterDB register;

    public ExecutorDB(EntityManager em) {
        this.em = em;
        this.register = new RegisterDB(em);
    }

    private static final String SCHEMA = "dbo";

    @Override
    public Object execute(Object[] args, String functionName) throws Exception {
        return registerAndExecuteFunction(functionName, args);
    }

    private Object registerAndExecuteFunction(String functionName, Object[] args) throws Exception {
        String functionCanonicalName = SCHEMA + "." + functionName;
        Optional<Method> m = Arrays.stream(ExecutorOperation.class.getMethods()).filter(it->it.getName().equals(functionName)).findFirst();

        // Verify if function or procedure then execute
        if (m.isPresent() && m.get().isAnnotationPresent(Function.class)) {
                return executeFunction(args);
        }
        else executeProc(functionCanonicalName, args);
        return null;
    }

    private Object executeFunction(Object[] args) {
        List results = Service.executeFunction(args);
        displayResults(results);
        return results;
    }

    private void displayResults(List results) {
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        System.out.println("\nResults:\n");
        for (int i = 0; i < results.size(); i++) {
            String str =  results.get(i).toString();
            System.out.println(str.replaceAll("[,()]", " "));
        }
        System.out.println("\n");
    }



    private void executeProc(String procName, Object[] args) throws Exception {
        Service.executeProcedure(procName, args, em);
        System.out.println("Procedure " + procName + " executed");
    }

}

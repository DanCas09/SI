package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import businessLogic.annotations.Function;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Method;
import java.util.Arrays;
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
            if (m.get().getAnnotation(Function.class).returnsMultipleValues())
                return executeReturnsMultipleValuesFunction(functionCanonicalName, args);
            else
                return executeFunction(functionCanonicalName, args);
        }
        else executeProc(functionCanonicalName, args);
        return null;
    }

    private Object executeFunction(String functionName, Object[] args) {
        Object result = Service.executeFunction(functionName, args);
        System.out.println("Function " + functionName + " executed with result: " + result);
        return result;
    }

    private Object executeReturnsMultipleValuesFunction(String functionName, Object[] args) {
        Object[] result = Service.executeReturnsMultipleValuesFunction(functionName, args);
        System.out.println("Function " + functionName + " executed with result: " + Arrays.toString(result));
        return result;
    }

    private void executeProc(String procName, Object[] args) throws Exception {
        Service.executeProcedure(procName, args, em);
        System.out.println("Procedure " + procName + " executed");
    }

}

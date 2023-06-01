package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import interfaces.Function;
import jakarta.persistence.EntityManager;

import java.lang.annotation.Annotation;
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
    public void execute(Object[] args, String functionName) throws Exception {
        registerAndExecuteFunction(functionName, args);
    }

    private void registerAndExecuteFunction(String functionName, Object[] args) throws Exception {
        String functionCanonicalName = SCHEMA + "." + functionName;
        Optional<Method> m = Arrays.stream(ExecutorOperation.class.getMethods()).filter(it->it.getName().equals(functionName)).findFirst();

        // Verify if function or procedure then execute
        if (m.isPresent() && m.get().isAnnotationPresent(Function.class)) {
            executeFunction(functionCanonicalName, args);
        }
        else executeProc(functionCanonicalName, args);
    }

    private void executeFunction(String functionName, Object[] args) {
        Object result = Service.executeFunction(functionName, args);
        System.out.println("Function " + functionName + " executed with result: " + result);
    }

    private void executeProc(String procName, Object[] args) throws Exception {
        Service.executeProcedure(procName, args, em);
        System.out.println("Procedure " + procName + " executed");
    }

}

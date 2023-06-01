package businessLogic.executor;

import jakarta.persistence.EntityManager;

public interface Executor {

    Object execute(Object[] args, String functionName) throws Exception;
}

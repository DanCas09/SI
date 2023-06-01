package businessLogic.executor;

import jakarta.persistence.EntityManager;

public interface Executor {

    void execute(Object[] args, String functionName) throws Exception;
}

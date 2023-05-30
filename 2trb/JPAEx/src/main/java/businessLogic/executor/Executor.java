package businessLogic.executor;

import jakarta.persistence.EntityManager;

public interface Executor {

    void execute(Object[] args, String functionName, EntityManager em) throws Exception;
}

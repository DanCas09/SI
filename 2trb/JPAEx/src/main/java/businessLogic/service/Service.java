package businessLogic.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EnumType;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

import java.util.HashMap;
import java.util.Map;


public class Service {
    //private  Map<String, StoredProcedureQuery> functionMap = new HashMap<>();

//    public Service(EntityManager em) {
//        this.em = em;
//    }

    private static StoredProcedureQuery currentFunction;

    public static void executeProcedure(String procedureName, Object[] args, EntityManager em) {
        Query q = em.createNativeQuery("call " + procedureName + prepareArgs(args));
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        q.executeUpdate();

    }

    private static String prepareArgs(Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < args.length; i++) {
            sb.append("?");
            if (i < args.length - 1)
                sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    public static void registerFunction(String funName, ParameterFunction[] funArgs, EntityManager em) {
        StoredProcedureQuery f = em.createStoredProcedureQuery(funName);
            for (int i = 0; i < funArgs.length; i++) {
                f.registerStoredProcedureParameter(i + 1, funArgs[i].classParameter(), funArgs[i].mode());
            }
        currentFunction = f;

    }

    // check this implementation
    public static Object executeFunction(String procName, Object[] args ) {
        for (int i = 0; i < args.length; i++) {
            currentFunction.setParameter(i + 1, args[i]);
        }
        currentFunction.execute();
        return currentFunction.getOutputParameterValue(args.length + 1);
    }
}

package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import scopes.DataScope;

import java.util.HashMap;
import java.util.Map;


public class Service {
    private static Map<String, StoredProcedureQuery> functionMap = new HashMap<>();

    public static void executeProcedure(String procedureName, Object[] args, EntityManager em) throws Exception {
        em.getTransaction().begin();
        Query q = em.createNativeQuery("call " + procedureName + prepareArgs(args));
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        q.executeUpdate();
        em.getTransaction().commit();
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

    public static void registerFunction(String funName, ParameterFunction[] funArgs, EntityManager em) throws Exception {
        //em.getTransaction().begin();
        StoredProcedureQuery f = em.createStoredProcedureQuery(funName);
        if(functionMap.get(funName) == null){
            for (int i = 0; i < funArgs.length; i++) {
                f.registerStoredProcedureParameter(i + 1, funArgs[i].classParameter(), funArgs[i].mode());
            }
            functionMap.put(funName, f);
        }
    }

    public static Object executeFunction(String procName, Object[] args) {
        StoredProcedureQuery q = functionMap.get(procName);
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        q.execute();
        return q.getOutputParameterValue(args.length + 1);
    }
}

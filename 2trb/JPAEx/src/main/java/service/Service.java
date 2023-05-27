package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import scopes.DataScope;

import java.util.HashMap;


public class Service {

    private HashMap<String, StoredProcedureQuery> functionMap = new HashMap<>();

    public static void executeProcedure(String procedureName, Object... args) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            //StoredProcedureQuery q = em.createStoredProcedureQuery(procedureName);
            Query q = em.createNativeQuery("call" + procedureName + prepareArgs(args));
            for (int i = 0; i < args.length; i++) {
                q.setParameter(i + 1, args[i]);
            }
            q.executeUpdate();
            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
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

    public static Object registerFunction(String funName, ParameterFunction[] funArgs, Object... args) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            StoredProcedureQuery q = em.createStoredProcedureQuery(funName);
            for (int i = 0; i < funArgs.length; i++) {
                q.registerStoredProcedureParameter(i + 1, funArgs[i].classParameter(), funArgs[i].mode());
            }
            q.registerStoredProcedureParameter(args.length + 1, Object.class, ParameterMode.OUT);
            for (int i = 0; i < args.length; i++) {
                q.setParameter(i + 1, args[i]);
            }
            q.execute();
            return q.getOutputParameterValue(args.length + 1);
            //functionMap.put(funName, q);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public Object executeFunction(String procName, Object... args){
        StoredProcedureQuery q = functionMap.get(procName);
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        q.execute();
        return q.getOutputParameterValue(2);
    }
}

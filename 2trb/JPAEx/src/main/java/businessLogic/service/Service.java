package businessLogic.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

import java.util.HashMap;
import java.util.Map;


public class Service {
    private  Map<String, StoredProcedureQuery> functionMap = new HashMap<>();



    public Service(EntityManager em) {
        this.em = em;
    }

    public void executeProcedure(String procedureName, Object[] args) throws Exception {
        //em.getTransaction().begin();
        Query q = em.createNativeQuery("call " + procedureName + prepareArgs(args));
        for (int i = 0; i < args.length; i++) {
            q.setParameter(i + 1, args[i]);
        }
        q.executeUpdate();
        //em.getTransaction().commit();
    }

    private String prepareArgs(Object... args) {
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

    public void registerFunction(String funName, ParameterFunction[] funArgs) throws Exception {
        //em.getTransaction().begin();
        StoredProcedureQuery f = em.createStoredProcedureQuery(funName);
        if(functionMap.get(funName) == null){
            for (int i = 0; i < funArgs.length; i++) {
                f.registerStoredProcedureParameter(i + 1, funArgs[i].classParameter(), funArgs[i].mode());
            }
            functionMap.put(funName, f);
        }
    }

    public Object executeFunction(String procName, Object[] args ) {
        var a = em;
        System.out.println(em);
        //StoredProcedureQuery q = functionMap.get(procName);
        for (int i = 0; i < args.length; i++) {
            func.setParameter(i + 1, args[i]);
        }
        func.execute();
        return func.getOutputParameterValue(args.length + 1);
    }

    public boolean isFunction(String functionName) {
        return functionMap.containsKey(functionName);
    }
}

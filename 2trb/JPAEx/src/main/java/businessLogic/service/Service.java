package businessLogic.service;

import jakarta.persistence.*;
import model.JogadorTotalInfo;

import java.util.*;


public class Service {
    private static Query currentFunction;

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
        String[] placeholders = createPlaceholders(funArgs);
        Query q = em.createNativeQuery("SELECT " + funName + prepareArgs(placeholders));
        //Query q = em.createNativeQuery("SELECT id, num_partidas  FROM dbo.jogadorTotalInfo ");

        for (int i = 0; i < funArgs.length; i++) {
            q.setParameter(i + 1, funArgs[i].classParameter());
        }
        currentFunction =  q;
    }

    public static void registerView(EntityManager em) {
        currentFunction = em.createQuery("select jg  from JogadorTotalInfo jg", JogadorTotalInfo.class);
    }

    private static String[] createPlaceholders(ParameterFunction[] funArgs) {
        List<String> placeholders = new ArrayList<>();
        for (int i = 0; i < funArgs.length; i++) {
            if (funArgs[i].mode() == ParameterMode.IN) {
                placeholders.add("(?)");
            }
        }
        return placeholders.toArray(new String[0]);
    }

    // check this implementation
    public static List<JogadorTotalInfo> executeFunction(Object[] args ) {
        for (int i = 0; i < args.length; i++) {
            currentFunction.setParameter(i + 1, args[i]);
        }
        return currentFunction.getResultList();

    }

    public static List executeView() {
        return currentFunction.getResultList();
    }

}

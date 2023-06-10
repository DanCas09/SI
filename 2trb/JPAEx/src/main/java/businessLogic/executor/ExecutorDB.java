package businessLogic.executor;

import businessLogic.annotations.View;
import businessLogic.register.RegisterDB;
import businessLogic.service.Service;
import businessLogic.annotations.Function;
import jakarta.persistence.EntityManager;
import model.JogadorTotalInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ExecutorDB implements Executor {

    private EntityManager em;

    public ExecutorDB(EntityManager em) {
        this.em = em;
    }

    private static final String SCHEMA = "dbo";

    @Override
    public Object execute(Object[] args, String functionName) throws Exception {
        return executeMethod(functionName, args);
    }

    private Object executeMethod(String functionName, Object[] args) throws Exception {
        String functionCanonicalName = SCHEMA + "." + functionName;
        Optional<Method> m = Arrays.stream(ExecutorOperation.class.getMethods()).filter(it->it.getName().equals(functionName)).findFirst();

        // Verify if function, procedure or view then execute
        if (m.isPresent() && m.get().isAnnotationPresent(Function.class)) {
                return executeFunction(args);
        } else if (m.isPresent() && m.get().isAnnotationPresent(View.class)){
                return executeView();
        } else executeProc(functionCanonicalName, args);
        return null;
    }

    private Object executeFunction(Object[] args) {
        List results = Service.executeFunction(args);
        displayResults(results);
        return results;
    }

    private Object executeView() {
        List results = Service.executeView();

        displayView(results);
        return results;
    }

    private void displayView(List<JogadorTotalInfo> results) {
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        System.out.println("\nResults:\n");
        System.out.printf("%-9s%-20s%-20s%-15s%-22s%-20s%-10s%n",
                "\tId", "Estado", "Email", "Username", "Num.Partidas", "Num.Jogos", "Pontuação\n");

        for (JogadorTotalInfo r : results) {
            System.out.printf("\t");
            System.out.printf("%-8d", r.getId());
            System.out.printf("%-15s", r.getEstado());
            System.out.printf("%-25s", r.getEmail());
            System.out.printf("%-20s", r.getUsername());
            System.out.printf("%-20d", r.getNum_partidas());
            System.out.printf("%-20d", r.getNum_jogos());
            System.out.printf("%-10s%n", r.getPontuacao() != null ? r.getPontuacao() : "0");
        }
        System.out.println("\n");

    }


    private void displayResults(List results) {
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        System.out.println("\nResults:\n");
        for (int i = 0; i < results.size(); i++) {

            String str =  results.get(i).toString();
            System.out.println(str.replaceAll("[,()]", " "));

        }
        System.out.println("\n");
    }



    private void executeProc(String procName, Object[] args) throws Exception {
        Service.executeProcedure(procName, args, em);
        System.out.println("Procedure " + procName + " executed");
    }

}

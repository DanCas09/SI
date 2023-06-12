package tests;

import jakarta.persistence.LockModeType;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static concrete.operations.RepoProcedures.*;
import static org.junit.Assert.assertEquals;

public class IncreasePointsOptTest {
    @Test
    public void increasePointsOptTest() {
        String idJogo = "jg1"; // ID do jogo
        String nomeCracha = "Fantasma Preto"; // Nome do crachá

        // Executa a função em duas threads simultaneamente
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(() -> crachaIncreasePoints(idJogo, nomeCracha, LockModeType.OPTIMISTIC_FORCE_INCREMENT));
        Future<?> future2 = executor.submit(() -> crachaIncreasePoints(idJogo, nomeCracha, LockModeType.OPTIMISTIC_FORCE_INCREMENT));

        try {
            // Espera a conclusão das duas threads
            future1.get();
            future2.get();
        } catch (ExecutionException e) {
            assertEquals("java.lang.RuntimeException: Unable to update badge points due to conflicting concurrent modification. Please try again later.", e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("The test was interrupted");
        } finally {
            executor.shutdown();
        }
    }

}


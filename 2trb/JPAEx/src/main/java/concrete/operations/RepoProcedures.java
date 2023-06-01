package concrete.operations;

import businessLogic.executor.ExecutorOperation;
import concrete.GenericRepository;
import concrete.entities.CrachaJogadorRM;
import jakarta.persistence.EntityManager;
import model.*;
import scopes.DataScope;

import java.util.NoSuchElementException;

public class RepoProcedures {

    public static void associarCracha(Integer idJogador, String idJogo, String nomeCracha) {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            ExecutorOperation exe = new ExecutorOperation(em);

            GenericRepository<Jogador, Integer> jogadorRepository = new GenericRepository<>(Jogador.class, Integer.class);
            Jogador jogador = jogadorRepository.Find(idJogador);
            if (jogador == null) {
                throw new NoSuchElementException("Jogador não existe");
            }

            GenericRepository<Cracha, Integer> crachaRepository = new GenericRepository<>(Cracha.class, Integer.class);

            // Filter by name
            Cracha cracha = crachaRepository.GetAll().stream()
                    .filter(it -> it.getNome().equals(nomeCracha))
                    .findFirst().orElse(null);

            if (cracha == null) {
                throw new NoSuchElementException("Cracha não existe");
            }

            GenericRepository<Jogo, String> jogoRepository = new GenericRepository<>(Jogo.class, String.class);
            Jogo jogo = jogoRepository.Find(idJogo);
            if (jogo == null) {
                throw new NoSuchElementException("Jogo não existe");
            }

            int res = exe.totalPontosJogador(jogador.getId());
            if (res < cracha.getPontuacao()) {
                throw new Exception("Pontuação insuficiente");
            }

            GenericRepository<CrachasJogador, CrachasJogadorId> crachasJogadorRepository = new GenericRepository<>(CrachasJogador.class, CrachasJogadorId.class);
            CrachasJogador crachaJogador = new CrachaJogadorRM(jogador, cracha).createCrachaJogador();
            crachasJogadorRepository.Add(crachaJogador);

            ds.cancelWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

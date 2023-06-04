package concrete.operations;

import businessLogic.executor.ExecutorOperation;
import concrete.GenericRepository;
import concrete.entities.CrachaJogadorRM;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import model.*;
import scopes.DataScope;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class RepoProcedures {

    public static void associarCracha(Integer idJogador, String idJogo, String nomeCracha) {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();


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

            GenericRepository<Partida, Integer> partidaRepository = new GenericRepository<>(Partida.class, Integer.class);
            List<Partida> partidaList = partidaRepository.GetAll();

            List<Partida> partidaJogo = partidaList.stream()
                    .filter(it -> it.getIdJogo() == jogo && it.getDataFim() != null).toList();


            GenericRepository<Pontuacao, PontuacaoId> pontuacaoRepository = new GenericRepository<>(Pontuacao.class, PontuacaoId.class);

            Stream<Pontuacao> pontuacaoList = pontuacaoRepository.GetAll().stream()
                    .filter(it -> Objects.equals(it.getIdJogador().getId(), jogador.getId()))
                    .filter(it -> partidaJogo.stream().anyMatch(partida -> partida == it.getIdPartida()));

            int res = pontuacaoList.mapToInt(Pontuacao::getPontuacao).sum();
            System.out.println(res);

            if (res <= cracha.getPontuacao()) {
                throw new IllegalArgumentException("Pontuação insuficiente");
            }

            GenericRepository<CrachasJogador, CrachasJogadorId> crachasJogadorRepository = new GenericRepository<>(CrachasJogador.class, CrachasJogadorId.class);
            CrachasJogador crachaJogador = new CrachaJogadorRM(jogador, cracha).createCrachaJogador();
            crachasJogadorRepository.Add(crachaJogador);

            ds.validateWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crachaIncreasePoints(String idJogo, String nomeCracha) {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();

            GenericRepository<Cracha, Integer> crachaRepository = new GenericRepository<>(Cracha.class, Integer.class);
            Cracha cracha = crachaRepository.GetAll().stream()
                    .filter(it -> it.getNome().equals(nomeCracha))
                    .filter(it -> it.getIdJogo().getId().equals(idJogo))
                    .findFirst().orElse(null);

            if (cracha == null) {
                throw new NoSuchElementException("Cracha não existe");
            }

            // Increase the points by 20%
            cracha.increasePointsByTwentyPercent();

            try {
                em.lock(cracha, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                // Update the entity while considering optimistic locking
                crachaRepository.Save(cracha);
                ds.validateWork();
            } catch (OptimisticLockException ex) {
                // Retry the update operation if an optimistic lock exception occurs
                System.out.println("Optimistic lock exception occurred. Retrying...");
                crachaRepository.Refresh(cracha); // Refresh the entity to obtain the latest version
                cracha.increasePointsByTwentyPercent(); // Increase the points again
                crachaRepository.Save(cracha); // Save the updated entity

                ds.validateWork();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
package businessLogic.executor;

import businessLogic.register.RegisterDB;
import interfaces.Function;
import interfaces.Procedure;
import jakarta.persistence.EntityManager;

public class ExecutorOperation  {

    private final ExecutorDB exe;

    private final RegisterDB register;

    private final EntityManager em;
    public ExecutorOperation(EntityManager em) {
        this.exe = new ExecutorDB(em);
        this.em = em;
        this.register = new RegisterDB(em);
    }


    // Criar Jogador - dbo.criarJogador
    @Procedure
    public void criarJogador(String email, String username, String estado, String regiao) throws Exception {
        Object[] args = { email, username, estado, regiao };
        String functionName = "criarJogador";
        exe.execute(args, functionName);
    }


    // Desativar Jogador - dbo.desativarJogador
    @Procedure
    public void desativarJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "desativarJogador";
        exe.execute(args, functionName);
    }


    // Banir Jogador - dbo.banirJogador
    @Procedure
    public void banirJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "banirJogador";
        exe.execute(args, functionName);
    }

    // Total de Jogos do Jogador - dbo.totalJogosJogador
    @Function
    public void totalJogosJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "totalJogosJogador";
        register.registerTotalJogosJogadorFunction();
        exe.execute(args, functionName);
    }

    // Total de Pontos do Jogador - dbo.totalPontosJogador
    @Function
    public void totalPontosJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "totalPontosJogador";
        register.registerTotalPontosJogadorFunction();
        exe.execute(args, functionName);
    }

}
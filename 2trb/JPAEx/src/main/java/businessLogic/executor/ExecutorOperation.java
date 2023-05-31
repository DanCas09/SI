package businessLogic.executor;

import jakarta.persistence.EntityManager;

public class ExecutorOperation  {

    private final ExecutorDB exe;
    private final EntityManager em;
    public ExecutorOperation(EntityManager em) {
        this.exe = new ExecutorDB(em);
        this.em = em;
    }

    // Criar Jogador - dbo.criarJogador
    public void criarJogador(String email, String username, String estado, String regiao) throws Exception {
        Object[] args = { email, username, estado, regiao };
        String functionName = "criarJogador";
        exe.execute(args, functionName, em);
    }

    // Desativar Jogador - dbo.desativarJogador
    public void desativarJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "desativarJogador";
        exe.execute(args, functionName, em);
    }

    // Banir Jogador - dbo.banirJogador
    public void banirJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "banirJogador";
        exe.execute(args, functionName, em);
    }

    // Total de Jogos do Jogador - dbo.totalJogosJogador
    public void totalJogosJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "totalJogosJogador";
        exe.execute(args, functionName, em);
    }

    // Total de Pontos do Jogador - dbo.totalPontosJogador
    public void totalPontosJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "totalPontosJogador";
        exe.execute(args, functionName, em);
    }

}

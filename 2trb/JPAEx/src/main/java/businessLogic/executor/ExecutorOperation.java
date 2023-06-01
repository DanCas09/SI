package businessLogic.executor;

import businessLogic.register.RegisterDB;
import businessLogic.annotations.Function;
import businessLogic.annotations.Procedure;
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
    public int totalPontosJogador(int idJogador) throws Exception {
        Object[] args = { idJogador };
        String functionName = "totalPontosJogador";
        register.registerTotalPontosJogadorFunction();
        return (int) exe.execute(args, functionName);
    }

    @Function
    public void pontosJogoPorJogador(String idJogo) throws Exception {
        Object[] args = { idJogo };
        String functionName = "totalPontosJogador";
        register.registerPontosJogoPorJogadorFunction();
        exe.execute(args, functionName);
    }

    @Procedure
    public void associarCracha(int idJogador, String idJogo, String nomeCracha) throws Exception {
        Object[] args = { idJogador, idJogo, nomeCracha };
        String functionName = "associarCracha";
        exe.execute(args, functionName);
    }

    @Procedure
    public void iniciarConversa(int idJogador, String nomeConversa, int idConversa) throws Exception {
        Object[] args = { idJogador, nomeConversa, idConversa };
        String functionName = "iniciarConversa";
        exe.execute(args, functionName);
    }

    @Procedure
    public void juntarConversa(int idJogador, int idConversa) throws Exception {
        Object[] args = { idJogador, idConversa };
        String functionName = "juntarConversa";
        exe.execute(args, functionName);
    }

    @Procedure
    public void enviarMensagem(int idConversa, int idJogador, String textoMensagem) throws Exception {
        Object[] args = { idConversa, idJogador, textoMensagem };
        String functionName = "enviarMensagem";
        exe.execute(args, functionName);
    }
}

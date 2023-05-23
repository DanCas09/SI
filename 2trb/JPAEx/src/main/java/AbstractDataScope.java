import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class AbstractDataScope implements AutoCloseable {
    protected class Session {
        private EntityManagerFactory ef;
        private EntityManager em;
        private boolean ok = true;
    }

    boolean isMine = true;
    boolean voted = false;
    private static final ThreadLocal<Session>
            threadLocal = ThreadLocal.withInitial(() -> null);

    public AbstractDataScope() {
        if (threadLocal.get() == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAExemplo");
            EntityManager em = emf.createEntityManager();
            Session s = new Session();
            s.ef = emf;
            s.ok = true;
            s.em = em;
            threadLocal.set(s);
            em.getTransaction().begin();
            isMine = true;
        } else
            isMine = false;
    }

    @Override
    public void close() throws Exception {
        if (isMine) {
            if (threadLocal.get().ok && voted) {
                threadLocal.get().em.getTransaction().commit();
            } else
                threadLocal.get().em.getTransaction().rollback();
            threadLocal.get().em.close();
            threadLocal.get().ef.close();
            threadLocal.remove();
        } else if (!voted)
            cancelWork();
    }

    public void validateWork() {
        voted = true;
    }

    public void cancelWork() {
        threadLocal.get().ok = false;
        voted = true;
    }

    public EntityManager getEntityManager() {
        return threadLocal.get().em;
    }
}
package concrete;

import interfaces.IMapper;
import jakarta.persistence.EntityManager;
import scopes.DataScope;

import java.lang.reflect.Method;

public class GenericMapper<T, TId> implements IMapper<T, TId> {

    private final Class<T> entityClass;
    private final Class<TId> keyClass;

    public GenericMapper(Class<T> entityClass, Class<TId> keyClass) {
        this.entityClass = entityClass;
        this.keyClass = keyClass;
    }

    @Override
    public TId create(T elem) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.persist(elem);
            ds.validateWork();
            return extractId(elem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public T read(TId id) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            return em.find(entityClass, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(T elem) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.merge(elem);
            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void delete(T elem) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            TId elementId = extractId(elem);
            if (elementId == null)
                throw new IllegalAccessException("Entidade inexistente");
            em.remove(elem);
            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private TId extractId(T e) {
        try {
            Method idGetter = entityClass.getMethod("getId");
            return keyClass.cast(idGetter.invoke(e));
        } catch (Exception ex) {
            // Handle exception appropriately based on your application's error handling strategy
            return null;
        }
    }
}

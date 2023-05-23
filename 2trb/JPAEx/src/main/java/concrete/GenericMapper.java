package concrete;

import interfaces.IMapper;
import jakarta.persistence.EntityManager;
import scopes.DataScope;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class GenericMapper<T, TId> implements IMapper<T, TId> {


    @Override
    public TId create(T elem) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.getTransaction().begin();
            em.persist(elem);
            em.getTransaction().commit();
            return extractId(elem);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public T read(TId id) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            return em.find(getEntityClass(), id)    ;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(T elem) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.getTransaction().begin();
            em.merge(elem);
            em.getTransaction().commit();
            ds.validateWork();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private TId extractId(T e) {
        try {
            Class<?> entityClass = getEntityClass();
            Method idGetter = entityClass.getMethod("getId");
            return (TId) idGetter.invoke(e);
        } catch (Exception ex) {
            // Handle exception appropriately based on your application's error handling strategy
            return null;
        }
    }

    private Class<T> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

}

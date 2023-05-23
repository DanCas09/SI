package concrete;

import interfaces.IMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import "./DataScope"

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class GenericMapper<T, TId> implements IMapper<T, TId> {


    @Override
    public TId create(T e) throws Exception {
        try (DataScope ds = new DataScope()) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
        return extractId(e);
    }

    @Override
    public T read(TId id) throws Exception {
        return em.find(getEntityClass(), id);
    }

    @Override
    public void update(T e) throws Exception {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
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

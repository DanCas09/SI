package concrete;

import interfaces.IMapper;
import interfaces.IRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import scopes.DataScope;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericRepository<Tentity, Tkey> implements IRepository<Tentity, Tkey> {


    private IMapper<Tentity, Tkey> mapper = new GenericMapper<>();

    @Override
    public List<Tentity> GetAll() throws Exception {
       try (DataScope ds = new DataScope()){
           EntityManager em = ds.getEntityManager();
           Class<?> entityClass = getEntityClass();
           String entityName = entityClass.getSimpleName();
           List<Tentity> l =  em.createQuery("select * from " + entityName,
                   (Class<Tentity>) entityClass).getResultList();
           return l;
       } catch (Exception e) {
           System.out.println(e.getMessage());
           throw e;
       }
    }

    @Override
    public Tentity Find(Tkey k) throws Exception {
        try (DataScope ds = new DataScope()){
            EntityManager em = ds.getEntityManager();

            //TODO

            return mapper.read(k);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void Add(Tentity entity) throws Exception {
        try (DataScope ds = new DataScope()){
            EntityManager em = ds.getEntityManager();

            //TODO
            mapper.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void Delete(Tentity entity) throws Exception {
        try (DataScope ds = new DataScope()){
            EntityManager em = ds.getEntityManager();

            //TODO
            mapper.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void Save(Tentity entity) throws Exception {
        try (DataScope ds = new DataScope()){
            EntityManager em = ds.getEntityManager();

            //TODO
            mapper.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    private Class<Tentity> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<Tentity>) parameterizedType.getActualTypeArguments()[0];
    }
}



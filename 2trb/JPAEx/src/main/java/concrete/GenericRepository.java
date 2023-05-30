package concrete;

import concrete.interfaces.IMapper;
import concrete.interfaces.IRepository;

import jakarta.persistence.EntityManager;
import businessLogic.scopes.DataScope;

import java.util.List;

public class GenericRepository<Tentity, Tkey> implements IRepository<Tentity, Tkey> {

    private final Class<Tentity> entityClass;
    private final Class<Tkey> idClass;
    private final IMapper<Tentity, Tkey> mapper;

    public GenericRepository(Class<Tentity> entityClass, Class<Tkey> idClass) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.mapper = new GenericMapper<>(entityClass, idClass);
    }

    @Override
    public List<Tentity> GetAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            String entityName = entityClass.getSimpleName();
            List<Tentity> l = em.createQuery("select e from " + entityName + " e", entityClass)
                    .getResultList();
            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Tentity Find(Tkey k) throws Exception {
        try {
            return mapper.read(k);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void Add(Tentity entity) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            mapper.create(entity);
            ds.validateWork(); // Validate the work and mark the transaction for commit
            em.getTransaction().commit(); // Commit the transaction
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void Delete(Tentity entity) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            mapper.delete(entity);
            ds.validateWork();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void Save(Tentity entity) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            mapper.update(entity);
            ds.validateWork(); // Validate the work and mark the transaction for commit
            em.getTransaction().commit(); // Commit the transaction
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}

package concrete;

import interfaces.IMapper;
import interfaces.IRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class GenericRepository<Tentity, Tkey> implements IRepository<Tentity, Tkey> {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAExemplo");
    EntityManager em = emf.createEntityManager();


    private IMapper<Tentity, Tkey> mapper = new GenericMapper<>(em);



    @Override
    public List<Tentity> GetAll() {
        return null;
    }

    @Override
    public Tentity Find(Tkey k) {
        return null;
    }

    @Override
    public void Add(Tentity entity) {
        try

    }

    @Override
    public void Delete(Tentity entity) {

    }

    @Override
    public void Save(Tentity e) {

    }
}



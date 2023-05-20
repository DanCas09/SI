package interfaces;

import java.util.List;

public interface IRepository<Tentity,Tkey> {
    List<Tentity> GetAll();
    Tentity Find(Tkey k);
    //outras operações envolvendo coleções
    void Add(Tentity entity);
    void Delete(Tentity entity);
    void Save(Tentity e);
}
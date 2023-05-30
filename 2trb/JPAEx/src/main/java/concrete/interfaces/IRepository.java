package concrete.interfaces;

import java.util.List;

public interface IRepository<Tentity,Tkey> {
    List<Tentity> GetAll() throws Exception;
    Tentity Find(Tkey k) throws Exception;
    //outras operações envolvendo coleções
    void Add(Tentity entity) throws Exception;
    void Delete(Tentity entity) throws Exception;
    void Save(Tentity e) throws Exception;
}
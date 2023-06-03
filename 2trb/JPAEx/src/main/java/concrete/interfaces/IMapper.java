package concrete.interfaces;
public interface IMapper<T, TId> {
    TId create(T e) throws Exception;

    T read(TId id) throws Exception;

    void update(T e) throws Exception;

    void delete(T e) throws Exception;

    void refresh(T cracha) throws Exception;
}
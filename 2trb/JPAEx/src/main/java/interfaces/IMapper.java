package interfaces;
public interface IMapper<T, TId> {
    TId create(T e) throws Exception;

    T read(TId id) throws Exception;

    void update(T e) throws Exception;
}
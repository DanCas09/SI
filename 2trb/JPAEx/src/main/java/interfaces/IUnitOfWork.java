package interfaces;

public interface IUnitOfWork {
    void NotifyInsert(Object entity);
    void NotifyUpdate(Object entity);
    void NotifyDelete(Object entity);
    void Commit();
    void Rollback();
}
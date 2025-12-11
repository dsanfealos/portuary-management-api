package app.portuary_management_api.services;

import java.util.List;

public interface CRUDServiceInterface<T,D> {
    T create(D dto);
    T retrieve(Long id);
    List<T> retrieveAll();
    boolean remove(Long id);
    T update(Long id, D dto);

}

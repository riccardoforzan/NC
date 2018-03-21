package nc.service;

import java.util.List;
import nc.model.NonConformita;

/**
 *
 * @author riccardo
 */
public interface NonConformitaService {
    NonConformita findByCodice(int codice);
    void saveNonConformita (NonConformita toSave);
    void updateNonConformita(NonConformita toUpdate);
    List<NonConformita> findAll();
}
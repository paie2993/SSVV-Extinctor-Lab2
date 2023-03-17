package org.ssvv.repository;

import java.util.HashMap;
import java.util.Map;
import org.ssvv.domain.HasID;
import org.ssvv.validation.ValidationException;
import org.ssvv.validation.Validator;

public abstract class AbstractCRUDRepository<ID, E extends HasID<ID>> implements CRUDRepository<ID, E>{
    Map<ID, E> entities;
    Validator<E> validator;

    public AbstractCRUDRepository(Validator validator) {
        entities = new HashMap<ID, E>();
        this.validator = validator;
    }

    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null! \n");
        }
        else {
            return entities.get(id);
        }
    }

    @Override
    public Iterable<E> findAll() { return entities.values(); }

    /**
     * @param entity; entity must be not null
     * @return null if entity could not be saved, otherwise the saved entity is returned
     * @throws ValidationException
     */
    @Override
    public E save(E entity) throws ValidationException
    {
        try {
            validator.validate(entity);
            final var mapEntity = entities.putIfAbsent(entity.getID(), entity);
            if (mapEntity != null) {
                return null;
            }
            return entity;
        }
        catch (ValidationException ve) {
            System.out.println("Entitatea nu este valida! \n");
            return null;
        }
    }

    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi nul! \n");
        }
        else {
            return entities.remove(id);
        }
    }

    @Override
    public E update(E entity) {
        try {
            validator.validate(entity);
            return entities.replace(entity.getID(), entity);
        }
        catch (ValidationException ve) {
            System.out.println("Entitatea nu este valida! \n");
            return null;
        }
    }
}

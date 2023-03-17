package org.ssvv.repository;

import org.ssvv.domain.Nota;
import org.ssvv.domain.Pair;
import org.ssvv.validation.Validator;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}

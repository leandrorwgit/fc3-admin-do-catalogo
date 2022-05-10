package com.fullcycle.admin.catalogo;

import com.fullcycle.admin.catalogo.Entity;
import com.fullcycle.admin.catalogo.Identifier;

public class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(final ID id) {
        super(id);
    }

}

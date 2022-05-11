package com.fullcycle.admin.catalogo.category;

import com.fullcycle.admin.catalogo.validation.Error;
import com.fullcycle.admin.catalogo.validation.ValidationHandler;
import com.fullcycle.admin.catalogo.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }

    }
}

package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.category.Category;
import com.fullcycle.admin.catalogo.category.CategoryID;

public record CreateCategoryOutput(String id) {

    public static CreateCategoryOutput from(final String anId) {
        return new CreateCategoryOutput(anId);
    }

    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId().getValue());
    }

}

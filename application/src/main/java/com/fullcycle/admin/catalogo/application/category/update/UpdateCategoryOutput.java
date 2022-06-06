package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.category.Category;
import com.fullcycle.admin.catalogo.category.CategoryID;

public record UpdateCategoryOutput(String id) {

    public static UpdateCategoryOutput from(final String anId) {
        return new UpdateCategoryOutput(anId);
    }

    public static UpdateCategoryOutput from(final Category aCategory) {
        return from(aCategory.getId().getValue());
    }

}

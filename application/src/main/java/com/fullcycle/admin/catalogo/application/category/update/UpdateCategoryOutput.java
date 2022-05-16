package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.category.Category;
import com.fullcycle.admin.catalogo.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {

    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }

}

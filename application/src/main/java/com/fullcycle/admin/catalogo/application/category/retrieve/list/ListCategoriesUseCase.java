package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.application.UseCase;
import com.fullcycle.admin.catalogo.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}

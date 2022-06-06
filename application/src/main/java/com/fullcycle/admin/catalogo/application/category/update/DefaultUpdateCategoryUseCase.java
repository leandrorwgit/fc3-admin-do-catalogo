package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.category.Category;
import com.fullcycle.admin.catalogo.category.CategoryGateway;
import com.fullcycle.admin.catalogo.category.CategoryID;
import com.fullcycle.admin.catalogo.exceptions.DomainException;
import com.fullcycle.admin.catalogo.exceptions.NotFoundException;
import com.fullcycle.admin.catalogo.validation.Error;
import com.fullcycle.admin.catalogo.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));
        final var notification = Notification.create();
        aCategory.update(aCommand.name(), aCommand.description(), aCommand.isActive()).validate(notification);

        return notification.hasError() ? API.Left(notification) : createUpdateOutput(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> createUpdateOutput(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory)).toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}

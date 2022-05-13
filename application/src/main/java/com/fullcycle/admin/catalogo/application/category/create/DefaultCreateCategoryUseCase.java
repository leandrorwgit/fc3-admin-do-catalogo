package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.category.Category;
import com.fullcycle.admin.catalogo.category.CategoryGateway;
import com.fullcycle.admin.catalogo.validation.handler.Notification;
import com.fullcycle.admin.catalogo.validation.handler.ThrowsValidationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var aCategory = Category.newCategory(aCommand.name(), aCommand.description(), aCommand.isActive());
        final var notification = Notification.create();
        aCategory.validate(notification);

        return notification.hasError() ? API.Left(notification) : createCategoryOutput(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> createCategoryOutput(final Category aCategory) {
//        try {
//            return API.Right(CreateCategoryOutput.from(this.categoryGateway.create(aCategory)));
//        } catch (Throwable t) {
//            return API.Left(Notification.create(t));
//        }

        return API.Try(() -> this.categoryGateway.create(aCategory)).toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}

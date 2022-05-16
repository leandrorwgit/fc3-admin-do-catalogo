package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalogo.category.Category;
import com.fullcycle.admin.catalogo.category.CategoryGateway;
import com.fullcycle.admin.catalogo.category.CategoryID;
import com.fullcycle.admin.catalogo.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());
        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).update(
                Mockito.argThat(anUpdatedCategory -> {
                    return Objects.equals(expectedName, anUpdatedCategory.getName())
                            && Objects.equals(expectedDescription, anUpdatedCategory.getDescription())
                            && Objects.equals(expectedIsActive, anUpdatedCategory.isActive())
                            && Objects.equals(expectedId, anUpdatedCategory.getId())
                            && Objects.equals(aCategory.getCreatedAt(), anUpdatedCategory.getCreatedAt())
                            && aCategory.getUpdatedAt().isBefore(anUpdatedCategory.getUpdatedAt())
                            && Objects.isNull(anUpdatedCategory.getDeletedAt());
                })
        );
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", null, true);
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);
        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    public void givenAValidInactivatedCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());
        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());
        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).update(
                Mockito.argThat(anUpdatedCategory -> {
                    return Objects.equals(expectedName, anUpdatedCategory.getName())
                            && Objects.equals(expectedDescription, anUpdatedCategory.getDescription())
                            && Objects.equals(expectedIsActive, anUpdatedCategory.isActive())
                            && Objects.equals(expectedId, anUpdatedCategory.getId())
                            && Objects.equals(aCategory.getCreatedAt(), anUpdatedCategory.getCreatedAt())
                            && aCategory.getUpdatedAt().isBefore(anUpdatedCategory.getUpdatedAt())
                            && Objects.nonNull(anUpdatedCategory.getDeletedAt());
                })
        );
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;
        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenThrow(new IllegalStateException(expectedErrorMessage));
        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Mockito.verify(categoryGateway, Mockito.times(1)).update(
                Mockito.argThat(anUpdatedCategory -> {
                    return Objects.equals(expectedName, anUpdatedCategory.getName())
                            && Objects.equals(expectedDescription, anUpdatedCategory.getDescription())
                            && Objects.equals(expectedIsActive, anUpdatedCategory.isActive())
                            && Objects.equals(expectedId, anUpdatedCategory.getId())
                            && Objects.equals(aCategory.getCreatedAt(), anUpdatedCategory.getCreatedAt())
                            && aCategory.getUpdatedAt().isBefore(anUpdatedCategory.getUpdatedAt())
                            && Objects.isNull(anUpdatedCategory.getDeletedAt());
                })
        );
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Category with ID 123 was not found";

        final var aCommand = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(CategoryID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(CategoryID.from(expectedId)));
        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());

    }

}

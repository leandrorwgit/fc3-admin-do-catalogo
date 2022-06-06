package com.fullcycle.admin.catalogo.infrastructure.models;

import com.fullcycle.admin.catalogo.JacksonTest;
import com.fullcycle.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
public class CategoryListResponseTest {

    @Autowired
    private JacksonTester<CategoryListResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();
        final var response = new CategoryListResponse(expectedId, expectedName, expectedDescription, expectedIsActive,
                expectedCreatedAt, expectedDeletedAt);

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPath("$.id", expectedId)
                .hasJsonPath("$.name", expectedName)
                .hasJsonPath("$.description", expectedDescription)
                .hasJsonPath("$.is_active", expectedIsActive)
                .hasJsonPath("$.created_at", expectedCreatedAt.toString())
                .hasJsonPath("$.deleted_at", expectedDeletedAt.toString());
    }

}

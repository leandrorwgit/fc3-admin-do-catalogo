package com.fullcycle.admin.catalogo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@DataJpaTest
@ComponentScan(includeFilters = {
        //@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".[MySQLGateway]")
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class)
})
@ExtendWith(CleanUpExtension.class)
public @interface MySQLGatewayTest {

}

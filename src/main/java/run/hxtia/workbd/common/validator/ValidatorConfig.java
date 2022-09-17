package run.hxtia.workbd.common.validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 1、后端验证的配置：快速失败【若验证失败，立即抛出异常】
 * 2、https://github.com/hibernate/hibernate-validator
 */
@Configuration
public class ValidatorConfig {
    @Bean
    public Validator validator() {
        return Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory().getValidator();
    }
}

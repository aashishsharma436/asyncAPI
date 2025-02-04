package com.practice.validator;

import com.practice.model.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}

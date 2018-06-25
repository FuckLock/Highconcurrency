package com.bao.miaosha.validator;

import com.bao.miaosha.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidateUtil.isMoblie(value);
        }else {
            if (StringUtils.isBlank(value)){
                return true;
            }else {
                return ValidateUtil.isMoblie(value);
            }
        }
    }
}

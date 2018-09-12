package com.springwebservice.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtils {

    static String asymmetricPrivateKeyFileName;

    @Value("${asymmetric.private.key.file}")
    public void setAsymmetricPrivateKeyFileName(String value) {
        asymmetricPrivateKeyFileName = value;
    }

}
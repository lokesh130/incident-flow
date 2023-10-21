package com.flipkart.fdsg.planning.ip.core.config;

import com.google.inject.Injector;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuiceInjectorProvider {

    private static GuiceInjectorProvider instance = new GuiceInjectorProvider();
    private Injector injector;

    private GuiceInjectorProvider() {
    }

    public static GuiceInjectorProvider getInstance() {
        return instance;
    }
}

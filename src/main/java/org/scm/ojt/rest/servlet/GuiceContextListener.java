package org.scm.ojt.rest.servlet;

import org.scm.ojt.rest.module.ApiModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

@WebListener
public class GuiceContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        //return Guice.createInjector(Stage.PRODUCTION, new ApiModule());
        return Guice.createInjector(new ApiModule());
    }
}
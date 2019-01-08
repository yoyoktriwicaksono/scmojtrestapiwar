package org.scm.ojt.rest.config;

/**
 * Created by Yoyok_T on 27/12/2018.
 */
public interface HibernateConfigData {
    Boolean enable();
    String dialect();
    String driverClass();
    String url();
    String username();
    String password();
    String showSQL();
    String poolSize();
    String hbm2ddl();
}

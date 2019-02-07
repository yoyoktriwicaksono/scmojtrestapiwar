package org.scm.ojt.rest.utils;

/**
 * Created by Yoyok_T on 08/10/2018.
 */
public class AppConstants {

    public class Jersey {
        public static final String SERVICEPACKAGE = "org.scm.ojt.rest.services";
        public static final String ENTITYPACKAGE = "org.scm.ojt.rest.entity";
        public static final String JAVAXRS = "javax.ws.rs.Application";
        public static final String JERSEYCONFIG = "jersey.config.server.wadl.disableWadl";
    }

    public class Authentication {
        public static final String HEADER_PROPERTY_ID = "id";
        public static final String AUTHORIZATION_PROPERTY = "x-access-token";
        // Do not use static responses, rebuild reponses every time
        public static final String ACCESS_REFRESH = "Token expired. You need to relogin";
        public static final String ACCESS_INVALID_TOKEN = "Token invalid. You need to relogin";
        public static final String ACCESS_DENIED = "Service is secret";
        public static final String ACCESS_FORBIDDEN = "Access forbidden!";
    }
}

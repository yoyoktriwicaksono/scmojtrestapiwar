package org.scm.ojt.rest.dao;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.scm.ojt.rest.config.ConfigurationManager;
import org.scm.ojt.rest.config.HibernateConfigData;
import org.scm.ojt.rest.config.MongoConfigData;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.scm.ojt.rest.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
//@Singleton
public class ConnectionManager {
    private final Morphia morphia = new Morphia();
    private final Datastore datastore;

    private final SessionFactory sessionFactoryObj;

    public ConnectionManager(){
        /* INITIALIZE MONGO DB CONNECTION USING MORPHIA */
        MongoConfigData mongoConfigData = ConfigurationManager.getInstance().getMongoConfigData();
        if (mongoConfigData.enable()) {
            // tell Morphia where to find your classes
            // can be called multiple times with different packages or classes
            morphia.mapPackage(AppConstants.Jersey.ENTITYPACKAGE);

            // create the Datastore connecting to the default port on the local host
            /*
            security:
                authorization: enabled

            We can use like below if authorization is not enable, just use host, port and database

            datastore = morphia.createDatastore(
                    new MongoClient(
                            mongoConfigData.host(),
                            mongoConfigData.port()),
                    mongoConfigData.database()
            );

            */

            if (mongoConfigData.username().isEmpty() && mongoConfigData.password().isEmpty()) {
                datastore = morphia.createDatastore(
                        new MongoClient(
                                mongoConfigData.host(),
                                mongoConfigData.port()),
                        mongoConfigData.database()
                );
            } else {
                ServerAddress addr = new ServerAddress(
                        mongoConfigData.host(),
                        mongoConfigData.port()
                );
                List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
                MongoCredential credential = MongoCredential.createCredential(
                        mongoConfigData.username(), mongoConfigData.database(), mongoConfigData.password().toCharArray());
                credentialsList.add(credential);
                MongoClient client = new MongoClient(addr, credentialsList);

                datastore = morphia.createDatastore(
                        client,
                        mongoConfigData.database()
                );
            }
            datastore.ensureIndexes();
        } else {
            datastore = null;
        }

        /* INITIALIZE RELATIONAL DB CONNECTION USING HIBERNATE */
        HibernateConfigData hibernateConfigData = ConfigurationManager.getInstance().getHibernateConfigData();
        if (hibernateConfigData.enable()) {
            // Creating Configuration Instance & Passing Hibernate Configuration File
            Configuration configObj = new Configuration();
            //configObj.configure("hibernate.cfg.xml");

            // USING CODE
            configObj.setProperty("hibernate.dialect", hibernateConfigData.dialect());
            configObj.setProperty("hibernate.connection.driver_class", hibernateConfigData.driverClass());
            configObj.setProperty("hibernate.connection.url", hibernateConfigData.url());
            configObj.setProperty("hibernate.connection.username", hibernateConfigData.username());
            if (!hibernateConfigData.password().isEmpty()){
                configObj.setProperty("hibernate.connection.password", hibernateConfigData.password());
            }
            configObj.setProperty("hibernate.show_sql", hibernateConfigData.showSQL());
            configObj.setProperty("hibernate.hbm2ddl.auto", hibernateConfigData.hbm2ddl());
            configObj.setProperty("hibernate.connection.pool_size", hibernateConfigData.poolSize());
            configObj.setProperty("hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext");

            configObj.addAnnotatedClass(org.scm.ojt.rest.entity.User.class);
            //configObj.addPackage(AppConstants.ENTITYPACKAGE);
            // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
            ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

            // Creating Hibernate SessionFactory Instance
            sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        } else {
            sessionFactoryObj = null;
        }
    }

    public Datastore getDatastore(){
        return datastore;
    }

    public SessionFactory getSessionFactoryObj(){
        return sessionFactoryObj;
    }
}
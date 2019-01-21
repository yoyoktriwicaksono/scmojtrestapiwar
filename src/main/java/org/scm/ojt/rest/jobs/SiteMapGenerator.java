package org.scm.ojt.rest.jobs;

import com.google.inject.Inject;
import org.scm.ojt.rest.config.ConfigurationManager;
import org.scm.ojt.rest.config.HibernateConfigData;
import org.scm.ojt.rest.config.JobConfigData;
import org.scm.ojt.rest.dao.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yoyok_T on 20/11/2018.
 */
public class SiteMapGenerator implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(SiteMapGenerator.class);

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        JobConfigData jobConfig = ConfigurationManager.getInstance().getJobConfigData();
        if (jobConfig.enable()){
            scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable command = new SiteMapThread(event.getServletContext());
            // Delay 1 Minute to first execution
            long initialDelay = 1;
            TimeUnit unit;
            if (jobConfig.timeUnit().equalsIgnoreCase("SECONDS")){
                unit = TimeUnit.SECONDS;
            } else if (jobConfig.timeUnit().equalsIgnoreCase("MINUTES")){
                unit = TimeUnit.MINUTES;
            } else if (jobConfig.timeUnit().equalsIgnoreCase("HOURS")){
                unit = TimeUnit.HOURS;
            } else {
                unit = TimeUnit.SECONDS;
            }
            // period the period between successive executions
            long period = jobConfig.period();

            scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
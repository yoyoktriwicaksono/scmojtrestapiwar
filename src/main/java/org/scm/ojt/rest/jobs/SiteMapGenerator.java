package org.scm.ojt.rest.jobs;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yoyok_T on 20/11/2018.
 */
public class SiteMapGenerator implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new SiteMapThread(event.getServletContext());
        // Delay 1 Minute to first execution
        long initialDelay = 1;
        TimeUnit unit = TimeUnit.SECONDS;
        // period the period between successive executions
        long period = 30;// 60 Minute!

        scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
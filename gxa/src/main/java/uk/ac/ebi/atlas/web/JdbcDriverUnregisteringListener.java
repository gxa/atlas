package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.ojdbc.PoolDriverManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;

public class JdbcDriverUnregisteringListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        PoolDriverManager.deregisterDrivers(DriverManager.getDrivers());
    }

}




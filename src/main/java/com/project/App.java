package com.project;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Base application class
 */
public class App {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);

        ServletHolder servletHolder = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter("javax.ws.rs.Application", "com.project.config.AppConfig");
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                "org.glassfish.jersey.moxy.json.MoxyJsonFeature");

        BasicConfigurator.configure();

        server.start();
        server.join();
    }
}

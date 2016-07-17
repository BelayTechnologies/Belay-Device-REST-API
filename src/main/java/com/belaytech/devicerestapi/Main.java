package com.belaytech.devicerestapi;

import com.belaytech.device.injection.DeviceAPIModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;

/**
 * Main class.
 *
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8082/device/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
     * application.
     *
     * @param injector
     * @return Grizzly HTTP server.
     * @throws java.io.IOException
     */
    public static HttpServer startServer(Injector injector) throws IOException {
        ResourceConfig RC = new PackagesResourceConfig("com.belaytech.devicerestapi");
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(RC, injector);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyServerFactory.createHttpServer(URI.create(BASE_URI), RC, ioc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Injector i = Guice.createInjector(new DeviceAPIModule());
        final HttpServer server = startServer(i);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

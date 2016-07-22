/*
 * Copyright (C) 2016 Belay Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.belaytech.server;

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
import org.glassfish.grizzly.http.server.StaticHttpHandler;

/**
 * EmbeddedServer class.
 *
 */
public class EmbeddedServer {

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
     * EmbeddedServer method.
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

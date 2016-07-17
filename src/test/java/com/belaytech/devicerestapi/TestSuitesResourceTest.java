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
package com.belaytech.devicerestapi;

import com.belaytech.device.injection.DeviceAPIModule;
import com.google.inject.Guice;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.server.HttpServer;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Joshua
 */
@RunWith(JukitoRunner.class)
@UseModules({DeviceAPIModule.class})
public class TestSuitesResourceTest {

    private HttpServer server;
    WebResource service;

    @Before
    public void startUp() {
        try {
            server = Main.startServer(Guice.createInjector(new DeviceAPIModule()));

            Client client = Client.create(new DefaultClientConfig());
            service = client.resource(Main.BASE_URI);
        } catch (java.io.IOException ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testGetTestSuites() {
        ClientResponse resp = service.path("testsuites")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        String responseMsg = resp.getEntity(String.class);

        Assert.assertEquals(200, resp.getStatus());
        Assert.assertNotNull("The response was null", responseMsg);
    }

}

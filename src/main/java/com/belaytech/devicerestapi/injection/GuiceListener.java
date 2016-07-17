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
package com.belaytech.devicerestapi.injection;

import com.belaytech.device.injection.DeviceAPIModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.mybatis.guice.datasource.helper.JdbcHelper;

/**
 *
 * @author Joshua
 */
public class GuiceListener extends GuiceServletContextListener {

    @Override
    public Injector getInjector() {
        return Guice.createInjector(JdbcHelper.MySQL, new DeviceAPIModule(), new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                
                ResourceConfig RC = new PackagesResourceConfig("com.belaytech.devicerestapi");
                for (Class<?> resource : RC.getClasses()) {
                    System.out.println("Binding resource: " + resource.getName());
                    bind(resource);
                }
                serve("/*").with(GuiceContainer.class);
            }
        });
    }
}

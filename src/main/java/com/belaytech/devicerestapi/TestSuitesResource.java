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

import com.belaytech.bootgrid.BootGridData;
import com.belaytech.device.DAO.TestSuitesDAO;
import com.belaytech.device.DO.TestSuite;
import com.belaytech.device.types.Status;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.Data;

/**
 *
 * @author Joshua
 */
@Path("testsuites")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class TestSuitesResource {

    private final TestSuitesDAO DAO;
    private final Gson GSON = new Gson();

    @Inject
    public TestSuitesResource(TestSuitesDAO dao) {
        DAO = dao;
    }

    @GET
    public String getTestSuites() {
        List<TestSuite> testSuites = DAO.getAllTestSuites();
        return GSON.toJson(testSuites);
    }

    @Data
    class TestSuitesGridData {

        private int id;
        private String name;
        @SerializedName("status_id")
        private int statusID;
        private String status;
        private int abort = 1;
    }

    @POST
    @Path("/gridData")
    public String getTestSuitesGrid(@DefaultValue("1") @FormParam("current") int current,
            @DefaultValue("10") @FormParam("rowCount") int rowCount,
            @FormParam("sort") String sort,
            @FormParam("searchPhrase") String searchPhrase) {
        List<TestSuite> testSuites = DAO.getAllTestSuites();
        List<TestSuitesGridData> gridData = Lists.newArrayList();
        for (TestSuite ts : testSuites) {
            TestSuitesGridData d = new TestSuitesGridData();
            d.setId(ts.getId());
            d.setName(ts.getName());
            Status s = ts.getStatus();
            //TEST CODE!!! Just want to see the thing return what it needs to return
            if (s == null) {
                s = Status.COMPLETE;
            }
            d.setStatusID(s.getCode());
            d.setStatus(s.getDesc());
            gridData.add(d);
        }
        BootGridData bootData = new BootGridData<>(current, rowCount);
        bootData.setRows(gridData);
        return GSON.toJson(bootData);
    }

    @GET
    @Path("/ids/{id}")
    public String getTestSuite(@PathParam("id") int id) {
        TestSuite testSuite = DAO.getTestSuite(id);
        return GSON.toJson(testSuite);
    }
}

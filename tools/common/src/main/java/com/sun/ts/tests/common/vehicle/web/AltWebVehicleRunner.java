/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package com.sun.ts.tests.common.vehicle.web;

import com.sun.ts.lib.harness.RemoteStatus;
import com.sun.ts.lib.harness.Status;
import com.sun.ts.lib.porting.TSURL;
import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.common.vehicle.VehicleRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Simplified version of WebVehicleRunner that is used by the persistence appclient tests.
 */
public class AltWebVehicleRunner implements VehicleRunnable {

    protected String sVehicle = "";

    protected Status sTestStatus = Status.passed("");

    String urlSuffix = "";

    Status sServletStatus = Status.passed("Not run");

    Status sJspStatus = Status.passed("Not run");

    String sVehicleArchiveName = "";

    String contextRootPrefix;

    String[] argv;

    Properties p;

    /**
     * Run the test in a servlet using a URL of the form "/{vehicle_archive_name}/{vehicle}_vehicle". For example, a test
     * with vehicle_archive_name="appmanagedNoTx_vehicle_web" and vehicle="appmanagedNoTx" would be run by accessing the URL
     * "http://{webServerHost}:{webServerPort}/appmanagedNoTx_vehicle_web/appmanagedNoTx_vehicle".
     *
     * The
     *
     * @param argv - test arguments
     * @param p - test properties
     * @return test run status
     */
    public Status run(String[] argv, Properties p) {
        this.argv = argv;
        this.p = p;
        sVehicle = TestUtil.getProperty(p, "vehicle");

        // use this name for the context root or jndi name to eliminate
        // naming conflicts for apps deployed at the same time
        sVehicleArchiveName = TestUtil.getProperty(p, "vehicle_archive_name");
        contextRootPrefix = sVehicleArchiveName;

        // default urlSuffix
        urlSuffix = "/" + contextRootPrefix + "/" + sVehicle + "_vehicle";

        sServletStatus = runWebVehicleTest("servlet");

        if (sServletStatus.isPassed() && sJspStatus.isPassed()) {
            sTestStatus = Status.passed("Test passed in a servlet and in a jsp");
        } else if (sServletStatus.isFailed() && sServletStatus.isFailed()) {
            sTestStatus = Status.failed("Test failed in a servlet and in a jsp");
        } else if (sJspStatus.isFailed()) {
            sTestStatus = Status.failed("Test passed in a jsp but failed in a servlet");
        } else {
            sTestStatus = Status.failed("Test passed in a servlet but failed in a jsp");
        }
        return sTestStatus;
    }

    protected Status runWebVehicleTest(String vehicle) {
        URLConnection connection = null;
        URL url = null;
        ObjectOutputStream objOut = null;
        ObjectInputStream objIn = null;
        Status status;

        try {
            if (vehicle.indexOf("jsp") != -1) {
                urlSuffix += ".jsp";
            }

            TSURL ctsURL = new TSURL();
            url = ctsURL.getURL("http", TestUtil.getProperty(p, "webServerHost"),
                    Integer.parseInt(TestUtil.getProperty(p, "webServerPort")), urlSuffix);
            connection = url.openConnection();
            TestUtil.logMsg("Opened connection to " + url);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "java-internal/" + p.getClass().getName());
            // connection.connect();
            objOut = new ObjectOutputStream(connection.getOutputStream());
            TestUtil.logTrace("got outputstream");
            objOut.writeObject(p);
            objOut.writeObject(argv);
            TestUtil.logTrace("wrote objects to the " + vehicle + " vehicle");
            objOut.flush();
            objOut.close();
            objOut = null;

            // read the status when it comes back
            if (vehicle.indexOf("jsp") != -1) {
                Properties sprop = TestUtil.getResponseProperties(connection);
                int type = Integer.parseInt(TestUtil.getProperty(sprop, "type"));
                String reason = TestUtil.getProperty(sprop, "reason");
                status = new Status(type, reason);
            } else {
                objIn = new ObjectInputStream(connection.getInputStream());
                status = ((RemoteStatus) objIn.readObject()).toStatus();
            }
            TestUtil.logMsg("Test status from a " + vehicle + ":  " + status.getType() + ":" + status.getReason());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            status = Status.failed("Fatal: Improper URL");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            status = Status.failed("Please set an appropriate value for the property:  webServerPort");
        } catch (IOException e) {
            e.printStackTrace();
            status = Status.failed("Fatal: Problem with connection: " + e);
        } catch (Exception e) {
            e.printStackTrace();
            status = Status.failed("ServiceTest failed inside a " + vehicle + ": " + e.getMessage());
        } finally {

            if (objOut != null) {
                try {
                    objOut.close();
                } catch (Exception e) {
                }
            }

            if (objIn != null) {
                try {
                    objIn.close();
                } catch (Exception e) {
                }
            }
        }
        return status;
    }
}

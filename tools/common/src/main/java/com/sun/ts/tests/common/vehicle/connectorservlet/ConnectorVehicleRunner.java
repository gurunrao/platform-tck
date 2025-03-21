/*
 * Copyright (c) 2008, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.common.vehicle.connectorservlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import com.sun.ts.lib.harness.Status;
import com.sun.ts.lib.harness.RemoteStatus;
import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.common.vehicle.VehicleRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectorVehicleRunner implements VehicleRunnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectorVehicleRunner.class);

    protected String sVehicle = "connectorservlet";

    protected Status sTestStatus = Status.passed("");

    String urlSuffix = "";

    Status sServletStatus = Status.passed("");

    String sVehicleArchiveName = "";

    String contextRootPrefix;

    String[] argv;

    Properties p;

    public Status run(String[] argv, Properties p) {
        this.argv = argv;
        this.p = p;
        sVehicle = TestUtil.getProperty(p, "vehicle");

        // use this name for the context root or jndi name to eliminate
        // naming conflicts for apps deployed at the same time
        sVehicleArchiveName = TestUtil.getProperty(p, "vehicle_archive_name");

        if (sVehicleArchiveName.indexOf("_vehicles") != -1) {
            contextRootPrefix = sVehicleArchiveName.substring(0, sVehicleArchiveName.indexOf("_vehicles") + 1) + sVehicle + "_vehicle_web";
        } else {
            if (sVehicleArchiveName.endsWith("_web")) {
                contextRootPrefix = sVehicleArchiveName;
            } else {
                contextRootPrefix = sVehicleArchiveName + "_web";
            }
        }

        // default urlSuffix
        urlSuffix = "/" + contextRootPrefix + "/" + sVehicle + "_vehicle";

        return run();
    } // run

    protected Status run() {
        // run in a connectorservlet
        urlSuffix = "/" + contextRootPrefix + "/connectorservlet_vehicle";
        sServletStatus = runWebVehicleTest("connectorservlet");

        LOGGER.info("Test: returning from running in connectorservlet vehicles");

        if (sServletStatus.isPassed()) {
            sTestStatus = Status.passed("Test passed in a connectorservlet ");
        } else {
            sTestStatus = Status.failed("Test failed in a connectorservlet ");
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
            String webServerHost = TestUtil.getProperty(p, "webServerHost");
            String webServerPort = TestUtil.getProperty(p, "webServerPort");
            url = new URL("http://" + webServerHost + ":" + Integer.parseInt(webServerPort) + urlSuffix);
            connection = url.openConnection();
            LOGGER.info("Opened connection to {}", url);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "java-internal/" + p.getClass().getName());
            // connection.connect();
            objOut = new ObjectOutputStream(connection.getOutputStream());
            LOGGER.trace("got outputstream");
            objOut.writeObject(p);
            objOut.writeObject(argv);
            LOGGER.trace("wrote objects to the {} vehicle", vehicle);
            objOut.flush();
            objOut.close();
            objOut = null;

            // read the status when it comes back
            objIn = new ObjectInputStream(connection.getInputStream());
            status = ((RemoteStatus) objIn.readObject()).toStatus();
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

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

package com.sun.ts.tests.common.vehicle;

import com.sun.ts.tests.common.vehicle.appmanaged.AppManagedVehicleRunner;
import com.sun.ts.tests.common.vehicle.appmanagedNoTx.AppManagedNoTxVehicleRunner;
import com.sun.ts.tests.common.vehicle.connectorservlet.ConnectorServletVehicleRunner;
import com.sun.ts.tests.common.vehicle.customvehicle.CustomVehicleRunner;
import com.sun.ts.tests.common.vehicle.ejb.EJBVehicleRunner;
import com.sun.ts.tests.common.vehicle.ejblitejsf.EJBLiteJSFVehicleRunner;
import com.sun.ts.tests.common.vehicle.ejbliteshare.EJBLiteSecuredWebVehicleRunner;
import com.sun.ts.tests.common.vehicle.ejbliteshare.EJBLiteWebVehicleRunner;
import com.sun.ts.tests.common.vehicle.jsp.JSPVehicleRunner;
import com.sun.ts.tests.common.vehicle.pmservlet.PMServletVehicleRunner;
import com.sun.ts.tests.common.vehicle.puservlet.PUServletVehicleRunner;
import com.sun.ts.tests.common.vehicle.servlet.ServletVehicleRunner;
import com.sun.ts.tests.common.vehicle.stateful3.Stateful3VehicleRunner;
import com.sun.ts.tests.common.vehicle.stateless3.Stateless3VehicleRunner;
import com.sun.ts.tests.common.vehicle.wsejb.WSEJBVehicleRunner;
import com.sun.ts.tests.common.vehicle.wsservlet.WSServletVehicleRunner;

import java.lang.System.Logger;

public final class VehicleRunnerFactory {

    private static final Logger logger = System.getLogger(VehicleRunnerFactory.class.getName());

    private static VehicleRunnable ejbRunner;

    private static VehicleRunnable servletRunner;

    private static VehicleRunnable jspRunner;

    private static VehicleRunnable ejbLiteJsfRunner;

    private static VehicleRunnable ejbLiteJspRunner;

    private static VehicleRunnable ejbLiteSecuredJspRunner;

    private static VehicleRunnable emptyRunner;

    private static VehicleRunnable stateless3Runner;

    private static VehicleRunnable stateful3Runner;

    private static VehicleRunnable appmanagedRunner;

    private static VehicleRunnable appmanagedNoTxRunner;

    private static VehicleRunnable wsejbRunner;

    private static VehicleRunnable wsservletRunner;

    private static VehicleRunnable pmservletRunner;

    private static VehicleRunnable puservletRunner;

    private static VehicleRunnable connectorServletRunner;

    private static VehicleRunnable customVehicleRunner;

    private static VehicleRunnable webRunner;

    private VehicleRunnerFactory() {
    }

    private static VehicleRunnable getEJBRunner() {
        if (ejbRunner == null) {
            try {
                ejbRunner = EJBVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ejbRunner;
    }

    private static VehicleRunnable getServletRunner() {
        if (servletRunner == null) {
            try {
                servletRunner = ServletVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return servletRunner;
    }

    private static VehicleRunnable getJSPRunner() {
        if (jspRunner == null) {
            try {
                jspRunner = JSPVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return jspRunner;
    }

    private static VehicleRunnable getEJBLiteJSFRunner() {
        if (ejbLiteJsfRunner == null) {
            try {
                ejbLiteJsfRunner = EJBLiteJSFVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ejbLiteJsfRunner;
    }

    private static VehicleRunnable getEJBLiteWebRunner() {
        if (ejbLiteJspRunner == null) {
            try {
                ejbLiteJspRunner = EJBLiteWebVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ejbLiteJspRunner;
    }

    private static VehicleRunnable getEJBLiteSecuredWebRunner() {
        if (ejbLiteSecuredJspRunner == null) {
            try {
                ejbLiteSecuredJspRunner = EJBLiteSecuredWebVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ejbLiteSecuredJspRunner;
    }

    private static VehicleRunnable getWebRunner() {
        if (webRunner == null) {
            try {
                webRunner = EJBLiteSecuredWebVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return webRunner;
    }

    private static VehicleRunnable getEmptyRunner() {
        if (emptyRunner == null) {
            try {
                emptyRunner = EmptyVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return emptyRunner;
    }

    private static VehicleRunnable getStateless3Runner() {
        if (stateless3Runner == null) {
            try {
                stateless3Runner = Stateless3VehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return stateless3Runner;
    }

    private static VehicleRunnable getStateful3Runner() {
        if (stateful3Runner == null) {
            try {
                stateful3Runner = Stateful3VehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return stateful3Runner;
    }

    private static VehicleRunnable getAppManagedRunner() {
        if (appmanagedRunner == null) {
            try {
                appmanagedRunner = AppManagedVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return appmanagedRunner;
    }

    private static VehicleRunnable getAppManagedNoTxRunner() {
        if (appmanagedNoTxRunner == null) {
            try {
                appmanagedNoTxRunner = AppManagedNoTxVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return appmanagedNoTxRunner;
    }

    private static VehicleRunnable getWSEJBRunner() {
        if (wsejbRunner == null) {
            try {
                wsejbRunner = WSEJBVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return wsejbRunner;
    }

    private static VehicleRunnable getWSServletRunner() {
        if (wsservletRunner == null) {
            try {
                wsservletRunner = WSServletVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return wsservletRunner;
    }

    private static VehicleRunnable getPMServletRunner() {
        if (pmservletRunner == null) {
            try {
                pmservletRunner = PMServletVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return pmservletRunner;
    }

    private static VehicleRunnable getPUServletRunner() {
        if (puservletRunner == null) {
            try {
                puservletRunner = PUServletVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return puservletRunner;
    }

    private static VehicleRunnable getConnectorServletRunner() {
        if (connectorServletRunner == null) {
            try {
                connectorServletRunner = ConnectorServletVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return connectorServletRunner;
    }

    // this supports the rare case of a user defined custome vehicle
    private static VehicleRunnable getCustomVehicleRunner() {
        if (customVehicleRunner == null) {
            try {
                customVehicleRunner = CustomVehicleRunner.class.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return customVehicleRunner;
    }

    // runners are stateless and thus can be cached and reused.
    // But we cannot have reference to ejb vehicle directory in
    // order to compile this class in any tck's.
    public static VehicleRunnable getVehicleRunner(String vtype) {
        if (vtype.equalsIgnoreCase("ejb")) {
            return getEJBRunner();
        } else if (vtype.equalsIgnoreCase("servlet")) {
            return getServletRunner();
        } else if (vtype.equalsIgnoreCase("jsp")) {
            return getJSPRunner();
        } else if (vtype.equalsIgnoreCase("web")) {
            return getWebRunner();
        } else if (vtype.equalsIgnoreCase("stateless3")) {
            return getStateless3Runner();
        } else if (vtype.equalsIgnoreCase("stateful3")) {
            return getStateful3Runner();
        } else if (vtype.equalsIgnoreCase("appmanaged")) {
            return getAppManagedRunner();
        } else if (vtype.equalsIgnoreCase("appmanagedNoTx")) {
            return getAppManagedNoTxRunner();
        } else if (vtype.equalsIgnoreCase("wsejb")) {
            return getWSEJBRunner();
        } else if (vtype.equalsIgnoreCase("wsservlet")) {
            return getWSServletRunner();
        } else if (vtype.equalsIgnoreCase("pmservlet")) {
            return getPMServletRunner();
        } else if (vtype.equalsIgnoreCase("puservlet")) {
            return getPUServletRunner();
        } else if (vtype.equalsIgnoreCase("connectorservlet")) {
            return getConnectorServletRunner();
        } else if (vtype.equalsIgnoreCase("customvehicle")) {
            return getCustomVehicleRunner();
        } else if (vtype.equalsIgnoreCase("ejblitejsf")) {
            return getEJBLiteJSFRunner();
        } else if (vtype.equalsIgnoreCase("ejblitejsp") || vtype.equalsIgnoreCase("ejbliteservlet")
                || vtype.equalsIgnoreCase("ejbliteservlet2") || vtype.equalsIgnoreCase("ejbliteservletcal")) {
            return getEJBLiteWebRunner();
        } else if (vtype.equalsIgnoreCase("ejblitesecuredjsp")) {
            return getEJBLiteSecuredWebRunner();
        } else {
            if (!vtype.equalsIgnoreCase("appclient") && !vtype.equalsIgnoreCase("wsappclient") && !vtype.equalsIgnoreCase("standalone")) {
                logger.log(Logger.Level.WARNING, "Invalid vehicle {" + vtype + "}. Will run test directly.");

            }
            return getEmptyRunner();
        }
    }
}

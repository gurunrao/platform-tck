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
 * @(#)stmtClient3.java	1.18 03/05/16
 */

package com.sun.ts.tests.jdbc.ee.stmt.stmt3;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.sun.ts.lib.harness.Status;

import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;

// Merant DataSource class
//import com.merant.sequelink.jdbcx.datasource.*;

/**
 * The stmtClient3 class tests methods of Statement interface using Sun's J2EE
 * Reference Implementation.
 * 
 * @author
 * @version 1.7, 06/16/99
 */

@Tag("tck-javatest")
@Tag("web")

public class stmtClient3Servlet extends stmtClient3 implements Serializable {
	private static final String testName = "jdbc.ee.stmt.stmt3";

	@TargetsContainer("tck-javatest")
	@OverProtocol("javatest")
	@Deployment(name = "servlet", testable = true)
	public static WebArchive createDeploymentservlet(@ArquillianResource TestArchiveProcessor archiveProcessor)
			throws IOException {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "stmt3_servlet_vehicle_web.war");
		archive.addPackages(true, "com.sun.ts.tests.jdbc.ee.common");
		archive.addPackages(false, "com.sun.ts.tests.common.vehicle");
		archive.addPackages(false, "com.sun.ts.tests.common.vehicle.servlet");
		archive.addPackages(true, "com.sun.ts.lib.harness");
		archive.addClasses(stmtClient3Servlet.class, stmtClient3.class);
		// The servlet descriptor
		URL servletUrl = stmtClient3Servlet.class.getResource("servlet_vehicle_web.xml");
		if (servletUrl != null) {
			archive.addAsWebInfResource(servletUrl, "web.xml");
		}
// The sun servlet descriptor
		URL sunServletUrl = stmtClient3Servlet.class.getResource("stmt3_servlet_vehicle_web.war.sun-web.xml");
		if (sunServletUrl != null) {
			archive.addAsWebInfResource(sunServletUrl, "sun-web.xml");
		}
// Call the archive processor
		archiveProcessor.processWebArchive(archive, stmtClient3Servlet.class, sunServletUrl);

		return archive;
	};

	/* Run test in standalone mode */
	public static void main(String[] args) {
		stmtClient3Servlet theTests = new stmtClient3Servlet();
		Status s = theTests.run(args, System.out, System.err);
		s.exit();
	}

	/*
	 * @testName: testSetFetchSize05
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:JAVADOC:175; JDBC:JAVADOC:176;
	 *
	 * @test_Strategy: Get a Statement object and call the setFetchSize(int rows)
	 * method with the negative value and it should throw SQLException
	 *
	 */
	@Test
	@TargetVehicle("servlet")
	public void testSetFetchSize05() throws Exception {
		super.testSetFetchSize05();
	}

	/*
	 * @testName: testSetMaxFieldSize01
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:JAVADOC:143; JDBC:JAVADOC:144;
	 *
	 * @test_Strategy: Get a Statement object and call the setMaxFieldSize(int max)
	 * method and call getMaxFieldSize() method and it should return an integer
	 * value that is been set
	 *
	 */
	@Test
	@TargetVehicle("servlet")
	public void testSetMaxFieldSize01() throws Exception {
		super.testSetMaxFieldSize01();
	}

	/*
	 * @testName: testSetMaxFieldSize02
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:JAVADOC:143; JDBC:JAVADOC:144;
	 *
	 * @test_Strategy: Get a Statement object and call the setMaxFieldSize(int max)
	 * method with an invalid value (negative value) and It should throw a
	 * SQLException
	 *
	 */
	@Test
	@TargetVehicle("servlet")
	public void testSetMaxFieldSize02() throws Exception {
		super.testSetMaxFieldSize02();
	}

	/*
	 * @testName: testSetMaxRows01
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:JAVADOC:147; JDBC:JAVADOC:148;
	 *
	 * @test_Strategy: Get a Statement object and call the setMaxRows(int rows)
	 * method and call getMaxRows() method and it should return a integer value that
	 * is been set
	 *
	 */
	@Test
	@TargetVehicle("servlet")
	public void testSetMaxRows01() throws Exception {
		super.testSetMaxRows01();
	}

	/*
	 * @testName: testSetMaxRows02
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:JAVADOC:147; JDBC:JAVADOC:148;
	 *
	 * @test_Strategy: Get a Statement object and call the setMaxRows(int rows)
	 * method with an invalid value (negative value) and It should throw an
	 * SQLException
	 *
	 */
	@Test
	@TargetVehicle("servlet")
	public void testSetMaxRows02() throws Exception {
		super.testSetMaxRows02();
	}

	/*
	 * @testName: testSetQueryTimeout02
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:JAVADOC:153; JDBC:JAVADOC:154;
	 *
	 * @test_Strategy: Get a Statement object and call the setQueryTimeout(int
	 * secval) method with an invalid value (negative value)and It should throw an
	 * SQLException
	 *
	 */
	@Test
	@TargetVehicle("servlet")
	public void testSetQueryTimeout02() throws Exception {
		super.testSetQueryTimeout02();
	}

}

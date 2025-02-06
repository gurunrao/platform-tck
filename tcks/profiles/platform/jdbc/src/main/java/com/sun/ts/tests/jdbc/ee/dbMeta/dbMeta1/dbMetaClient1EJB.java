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
 * @(#)dbMetaClient1.java	1.27 03/05/16
 */

package com.sun.ts.tests.jdbc.ee.dbMeta.dbMeta1;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.sun.ts.lib.harness.Status;

import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;

// Merant DataSource class
// import com.merant.sequelink.jdbcx.datasource.*;

/**
 * The dbMetaClient1 class tests methods of DatabaseMetaData interface using
 * Sun's J2EE Reference Implementation.
 * 
 * @author
 * @version 1.7, 06/16/99
 */
@Tag("tck-appclient")

public class dbMetaClient1EJB extends dbMetaClient1 implements Serializable {
	private static final String testName = "jdbc.ee.dbMeta.dbMeta1";

	@TargetsContainer("tck-appclient")
	@OverProtocol("appclient")
	@Deployment(name = "ejb", testable = true)
	public static EnterpriseArchive createDeploymentejb(@ArquillianResource TestArchiveProcessor archiveProcessor)
			throws IOException {
		JavaArchive ejbClient = ShrinkWrap.create(JavaArchive.class, "dbMeta1_ejb_vehicle_client.jar");
		ejbClient.addPackages(true, "com.sun.ts.tests.jdbc.ee.common");
		ejbClient.addPackages(false, "com.sun.ts.tests.common.vehicle");
		ejbClient.addPackages(false, "com.sun.ts.tests.common.vehicle.ejb");
		ejbClient.addPackages(true, "com.sun.ts.lib.harness");
		ejbClient.addClasses(dbMetaClient1EJB.class, dbMetaClient1.class);

		URL resURL = dbMetaClient1EJB.class.getResource("/com/sun/ts/tests/common/vehicle/ejb/ejb_vehicle_client.xml");
		if (resURL != null) {
			ejbClient.addAsManifestResource(resURL, "application-client.xml");
		}
		ejbClient.addAsManifestResource(new StringAsset("Main-Class: com.sun.ts.tests.common.vehicle.VehicleClient\n"),
				"MANIFEST.MF");

		resURL = dbMetaClient1EJB.class.getResource(
				"/com/sun/ts/tests/jdbc/ee/dbMeta/dbMeta1/dbMeta1_ejb_vehicle_client.jar.sun-application-client.xml");
		if (resURL != null) {
			ejbClient.addAsManifestResource(resURL, "sun-application-client.xml");
		}

		JavaArchive ejb = ShrinkWrap.create(JavaArchive.class, "dbMeta1_ejb_vehicle_ejb.jar");
		ejb.addPackages(true, "com.sun.ts.tests.jdbc.ee.common");
		ejb.addPackages(false, "com.sun.ts.tests.common.vehicle");
		ejb.addPackages(false, "com.sun.ts.tests.common.vehicle.ejb");
		ejb.addPackages(true, "com.sun.ts.lib.harness");
		ejb.addClasses(dbMetaClient1EJB.class, dbMetaClient1.class);

		resURL = dbMetaClient1EJB.class
				.getResource("/com/sun/ts/tests/jdbc/ee/dbMeta/dbMeta1/dbMeta1_ejb_vehicle_ejb.jar.sun-ejb-jar.xml");

		if (resURL != null) {
			ejb.addAsManifestResource(resURL, "sun-ejb-jar.xml");
		}

		resURL = dbMetaClient1EJB.class.getResource("/com/sun/ts/tests/jdbc/ee/dbMeta/dbMeta1/ejb_vehicle_ejb.xml");

		if (resURL != null) {
			ejb.addAsManifestResource(resURL, "ejb-jar.xml");
		}

		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "dbMeta1_ejb_vehicle.ear");
		ear.addAsModule(ejbClient);
		ear.addAsModule(ejb);
		return ear;
	};

	/* Run test in standalone mode */
	public static void main(String[] args) {
		dbMetaClient1EJB theTests = new dbMetaClient1EJB();
		Status s = theTests.run(args, System.out, System.err);
		s.exit();
	}

	/*
	 * @testName: testSupportsStoredProcedures
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:962; JDBC:JAVADOC:963;
	 * JavaEE:SPEC:193; JavaEE:SPEC:180;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the supportsStoredprocedures() method It should return true
	 * value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testSupportsStoredProcedures() throws Exception {
		super.testSupportsStoredProcedures();
	}

	/*
	 * @testName: testAllProceduresAreCallable
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:808; JDBC:JAVADOC:809;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the allProceduresAreCallable() method It should return a
	 * boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testAllProceduresAreCallable() throws Exception {
		super.testAllProceduresAreCallable();
	}

	/*
	 * @testName: testAllTablesAreSelectable
	 * 
	 * @assertion_ids: JDBC:SPEC:8 ; JDBC:JAVADOC:810 ; JDBC:JAVADOC:811;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the allTablesAreSelectable() method It should return a
	 * boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testAllTablesAreSelectable() throws Exception {
		super.testAllTablesAreSelectable();
	}

	/*
	 * @testName: testGetURL
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:812; JDBC:JAVADOC:813;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getURL() method It should return a String or null if it
	 * cannot be generated
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetURL() throws Exception {
		super.testGetURL();
	}

	/*
	 * @testName: testGetUserName
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:814; JDBC:JAVADOC:815;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getUserName() method It should return a String
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetUserName() throws Exception {
		super.testGetUserName();
	}

	/*
	 * @testName: testIsReadOnly
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:816; JDBC:JAVADOC:817;
	 * JavaEE:SPEC:193;
	 * 
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the isReadOnly() method It should return a boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testIsReadOnly() throws Exception {
		super.testIsReadOnly();
	}

	/*
	 * @testName: testNullsAreSortedHigh
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:818; JDBC:JAVADOC:819;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the nullsAreSortedHigh() method It should return a boolean
	 * value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testNullsAreSortedHigh() throws Exception {
		super.testNullsAreSortedHigh();
	}

	/*
	 * @testName: testNullsAreSortedLow
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:820; JDBC:JAVADOC:821;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the nullsAreSortedLow() method It should return a boolean
	 * value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testNullsAreSortedLow() throws Exception {
		super.testNullsAreSortedLow();
	}

	/*
	 * @testName: testNullsAreSortedAtStart
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:822; JDBC:JAVADOC:823;
	 * JavaEE:SPEC:193;
	 *
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the nullsAreSortedAtStart() method It should return a
	 * boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testNullsAreSortedAtStart() throws Exception {
		super.testNullsAreSortedAtStart();
	}

	/*
	 * @testName: testNullsAreSortedAtEnd
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:824; JDBC:JAVADOC:825;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the nullsAreSortedAtEnd() method It should return a boolean
	 * value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testNullsAreSortedAtEnd() throws Exception {
		super.testNullsAreSortedAtEnd();
	}

	/*
	 * @testName: testGetDatabaseProductName
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:826; JDBC:JAVADOC:827;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getDatabaseProductName() method It should return a
	 * String
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetDatabaseProductName() throws Exception {
		super.testGetDatabaseProductName();
	}

	/*
	 * @testName: testGetDatabaseProductVersion
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:828; JDBC:JAVADOC:829;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getDatabaseProductVersion() method It should return a
	 * String
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetDatabaseProductVersion() throws Exception {
		super.testGetDatabaseProductVersion();
	}

	/*
	 * @testName: testGetDriverName
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:830; JDBC:JAVADOC:831;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getDriverName() method It should return a String
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetDriverName() throws Exception {
		super.testGetDriverName();
	}

	/*
	 * @testName: testGetDriverVersion
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:832; JDBC:JAVADOC:833;
	 * JavaEE:SPEC:193;
	 *
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getDriverVersion() method It should return a String
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetDriverVersion() throws Exception {
		super.testGetDriverVersion();
	}

	/*
	 * @testName: testGetDriverMajorVersion
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:834; JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getDriverMajorVersion() method It should return a
	 * Integer value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetDriverMajorVersion() throws Exception {
		super.testGetDriverMajorVersion();
	}

	/*
	 * @testName: testGetDriverMinorVersion
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:835; JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the getDriverMinorVersion() method It should return a
	 * Integer value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testGetDriverMinorVersion() throws Exception {
		super.testGetDriverMinorVersion();
	}

	/*
	 * @testName: testUsesLocalFilePerTable
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:838; JDBC:JAVADOC:839;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the usesLocalFilePerTable() method It should return a
	 * boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testUsesLocalFilePerTable() throws Exception {
		super.testUsesLocalFilePerTable();
	}

	/*
	 * @testName: testSupportsMixedCaseIdentifiers
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:840; JDBC:JAVADOC:841;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the supportsMixedCaseIdentifiers() method It should return
	 * a boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testSupportsMixedCaseIdentifiers() throws Exception {
		super.testSupportsMixedCaseIdentifiers();
	}

	/*
	 * @testName: testStoresUpperCaseIdentifiers
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:842; JDBC:JAVADOC:843;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the storesUpperCaseIdentifiers() method It should return a
	 * boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testStoresUpperCaseIdentifiers() throws Exception {
		super.testStoresUpperCaseIdentifiers();
	}

	/*
	 * @testName: testStoresLowerCaseIdentifiers
	 * 
	 * @assertion_ids: JDBC:SPEC:8; JDBC:JAVADOC:844; JDBC:JAVADOC:845;
	 * JavaEE:SPEC:193;
	 * 
	 * @test_Strategy: Get the DataBaseMetaData object from the Connection to the
	 * DataBase and call the storesLowerCaseIdentifiers() method It should return a
	 * boolean value
	 *
	 */
	@Test
	@TargetVehicle("ejb")
	public void testStoresLowerCaseIdentifiers() throws Exception {
		super.testStoresLowerCaseIdentifiers();
	}
}

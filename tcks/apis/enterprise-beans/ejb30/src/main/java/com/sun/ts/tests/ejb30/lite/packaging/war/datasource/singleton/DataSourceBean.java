/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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
package com.sun.ts.tests.ejb30.lite.packaging.war.datasource.singleton;

import static com.sun.ts.tests.ejb30.lite.packaging.war.datasource.common.DataSourceTest.verifyDataSource;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Local;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import javax.sql.DataSource;
import jakarta.transaction.UserTransaction;

import com.sun.ts.tests.ejb30.lite.packaging.war.datasource.common.DataSourceIF;
import com.sun.ts.tests.ejb30.lite.packaging.war.datasource.common.ComponentBase;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@Local(DataSourceIF.class)
@DataSourceDefinitions({
@DataSourceDefinition(name="defaultds",
        className="org.apache.derby.jdbc.ClientDataSource",
        portNumber=1527,
        serverName="localhost",
        databaseName="derbyDB",
        user="cts1",
        transactional=false,
        password="cts1",
        properties={}
),
@DataSourceDefinition(name="defaultds2",
        className="org.apache.derby.jdbc.ClientDataSource",
        portNumber=1527,
        serverName="localhost",
        databaseName="derbyDB",
        user="cts1",
        transactional=false,
        password="cts1",
        properties={}
)
})
public class DataSourceBean extends ComponentBase {
    @Resource(lookup="java:comp/env/compds")
    private DataSource compds;
    
    @Resource(lookup="java:comp/env/compds2")
    private DataSource compds2;
    
    @Resource(lookup="java:comp/env/defaultds")
    private DataSource defaultds;
    
    @Resource(lookup="java:comp/env/defaultds2")
    private DataSource defaultds2;
    
    @Resource
    private UserTransaction ut;

    @SuppressWarnings("unused")
    @PostConstruct
    private void postConstruct() {
        boolean c = true;
        getPostConstructRecords().append(String.format("In postConstruct of %s%n", this));
        
        verifyDataSource(getPostConstructRecords(), c, "java:comp/env/defaultds", "java:comp/env/defaultds2", 
                                                       "java:comp/env/compds", "java:comp/env/compds2");
        verifyDataSource(getPostConstructRecords(), c, defaultds, defaultds2, compds, compds2);
      
        verifyDataSource(getPostConstructRecords(), c, defaultds, defaultds2);
    }
    
    @Override
    public StringBuilder getConnection() {
        int j = 2;
        StringBuilder sb = new StringBuilder();
        try {
            ut.begin();
            for(int i = 0; i < j; i++) {
                verifyDataSource(getPostConstructRecords(), true, defaultds);
                verifyDataSource(getPostConstructRecords(), true, defaultds2);
            }
            ut.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb;
    }

}

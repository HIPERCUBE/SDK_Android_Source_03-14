/* 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.harmony.sql.tests.javax.sql;

import dalvik.annotation.TestTargets;
import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestTargetClass;

import junit.framework.TestCase;

import java.sql.SQLException;

import javax.sql.ConnectionEvent;
import javax.sql.PooledConnection;

@TestTargetClass(ConnectionEvent.class)
public class ConnectionEventTest extends TestCase {

    /**
     * @tests {@link javax.sql.ConnectionEvent#ConnectionEvent(PooledConnection)}
     * 
     */
    @TestTargetNew(
        level = TestLevel.SUFFICIENT,
        notes = "functional test missing but not feasible: no implementation available.",
        method = "ConnectionEvent",
        args = {javax.sql.PooledConnection.class}
    )  
    public void testConstructorConnection() {
        try {
            new ConnectionEvent(null);
            fail("illegal argument exception expected");
        } catch (IllegalArgumentException e) {
        }
        
        Impl_PooledConnection ipc = new Impl_PooledConnection();
        ConnectionEvent ce = new ConnectionEvent(ipc);
        assertSame(ipc, ce.getSource());
        assertNull(ce.getSQLException());
        
        //cross test
        ConnectionEvent ce2 = new ConnectionEvent(ipc,null);
        assertSame(ce2.getSource(),ce.getSource());
    }
    
    
    
    /**
     * @tests {@link javax.sql.ConnectionEvent#ConnectionEvent(PooledConnection, SQLException)}
     */
    @TestTargetNew(
        level = TestLevel.SUFFICIENT,
        notes = "functional test missing but not feasible: no implementation available.",
        method = "ConnectionEvent",
        args = {javax.sql.PooledConnection.class, java.sql.SQLException.class}
    )  
    public void testConstructorConnectionSQLException() {
        try {
            new ConnectionEvent(null, null);
            fail("illegal argument exception expected");
        } catch (IllegalArgumentException e) {
        }
        
        Impl_PooledConnection ipc = new Impl_PooledConnection();
        ConnectionEvent ce = new ConnectionEvent(ipc, null);
        assertSame(ipc, ce.getSource());
        assertNull(ce.getSQLException());
        
        SQLException e = new SQLException();
        ce = new ConnectionEvent(ipc, e);
        assertSame(ipc, ce.getSource());
        assertSame(e, ce.getSQLException());
    }
    
    /**
     * @tests {@link javax.sql.ConnectionEvent#getSQLException()}
     */
    @TestTargetNew(
        level = TestLevel.SUFFICIENT,
        notes = "functional test missing but not feasible: no implementation available.",
        method = "getSQLException",
        args = {}
    )    
    public void testGetSQLException() {

        Impl_PooledConnection ipc = new Impl_PooledConnection();
        ConnectionEvent ce = new ConnectionEvent(ipc);
        
        ConnectionEvent ce2 = new ConnectionEvent(ipc, null);
        assertNull(ce.getSQLException());
        assertEquals(ce2.getSQLException(), ce.getSQLException());
        
        SQLException e = new SQLException();
        ConnectionEvent ce3 = new ConnectionEvent(ipc, e);
        assertNotNull(ce3.getSQLException());
        assertNotSame(ce3.getSQLException(), ce2.getSQLException());

    }
}   


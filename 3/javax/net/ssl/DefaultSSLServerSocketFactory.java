/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package javax.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * Default inoperative implementation of javax.net.ssl.SSLServerSocketFactory
 * 
 * @since Android 1.0
 */
class DefaultSSLServerSocketFactory extends SSLServerSocketFactory {

    private String errMessage;
    
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        throw new SocketException(errMessage);
    }


    public ServerSocket createServerSocket(int port, int backlog)
            throws IOException {
        throw new SocketException(errMessage);
    }

    public ServerSocket createServerSocket(int port, int backlog,
            InetAddress iAddress) throws IOException {
        throw new SocketException(errMessage);
    }
    
    DefaultSSLServerSocketFactory(String mes) {
        errMessage = mes;
    }

}
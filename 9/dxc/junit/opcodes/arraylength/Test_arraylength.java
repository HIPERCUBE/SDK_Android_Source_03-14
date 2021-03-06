/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dxc.junit.opcodes.arraylength;

import dxc.junit.DxTestCase;
import dxc.junit.DxUtil;
import dxc.junit.opcodes.arraylength.jm.T_arraylength_1;

public class Test_arraylength extends DxTestCase {

    /**
     * @title normal test
     */
    public void testN1() {
        T_arraylength_1 t = new T_arraylength_1();
        String[] a = new String[5];
        assertEquals(5, t.run(a));
    }

    /**
     * @title NullPointerException expected
     */
    public void testNPE1() {
        T_arraylength_1 t = new T_arraylength_1();
        try {
            t.run(null);
            fail("NPE expected");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    /**
     * @constraint 4.8.2.1
     * @title types of arguments - Object
     */
    public void testVFE2() {
        try {
            Class.forName("dxc.junit.opcodes.arraylength.jm.T_arraylength_2");
            fail("expected a verification exception");
        } catch (Throwable t) {
            DxUtil.checkVerifyException(t);
        }
    }

    /**
     * @constraint 4.8.2.1
     * @title types of arguments - int
     */
    public void testVFE1() {
        try {
            Class.forName("dxc.junit.opcodes.arraylength.jm.T_arraylength_3");
            fail("expected a verification exception");
        } catch (Throwable t) {
            DxUtil.checkVerifyException(t);
        }
    }

}

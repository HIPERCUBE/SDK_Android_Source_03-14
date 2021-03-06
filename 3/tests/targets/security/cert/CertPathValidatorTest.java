/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tests.targets.security.cert;

import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestTargets;

import junit.framework.TestCase;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;

public abstract class CertPathValidatorTest extends TestCase {

    private final String algorithmName;


    public CertPathValidatorTest(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    abstract CertPathParameters getParams();
    abstract CertPath getCertPath();
    abstract void validateResult(CertPathValidatorResult validatorResult);

    @TestTargets({
        @TestTargetNew(
                level=TestLevel.ADDITIONAL,
                method="getInstance",
                args={String.class}
        ),
        @TestTargetNew(
                level=TestLevel.ADDITIONAL,
                method="validate",
                args={CertPath.class, CertPathParameters.class}
        ),
        @TestTargetNew(
                level=TestLevel.COMPLETE,
                method="method",
                args={}
        )
    })
    public void testCertPathValidator() {
        CertPathValidator certPathValidator = null;
        try {
            certPathValidator = CertPathValidator.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }

        CertPathValidatorResult validatorResult = null;
        try {
            validatorResult = certPathValidator.validate(getCertPath(),
                    getParams());
        } catch (CertPathValidatorException e) {
            fail(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            fail(e.getMessage());
        }

        validateResult(validatorResult);
    }


}

/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.android.sdklib.internal.repository;

import com.android.sdklib.IAndroidTarget;
import com.android.sdklib.internal.repository.Archive.Arch;
import com.android.sdklib.internal.repository.Archive.Os;
import com.android.sdklib.repository.PkgProps;

import java.util.Properties;

public class PlatformPackageTest extends MinToolsPackageTest {

    /**
     * PlatformPackage implicitly generates a local archive wrapper
     * that matches the current platform OS and architecture. Since this
     * is not convenient for testing, this class overrides it to always
     * create archives for any OS and any architecture.
     */
    private static class PlatformPackageWithFakeArchive extends PlatformPackage {
        protected PlatformPackageWithFakeArchive(IAndroidTarget target, Properties props) {
            super(target, props);
        }

        @Override
        protected Archive[] initializeArchives(
                Properties props,
                Os archiveOs,
                Arch archiveArch,
                String archiveOsPath) {
            assert archiveOs == Os.getCurrentOs();
            assert archiveArch == Arch.getCurrentArch();
            return super.initializeArchives(props, Os.ANY, Arch.ANY, LOCAL_ARCHIVE_PATH);
        }
    }

    private PlatformPackage createPlatformPackage(Properties props) {
        PlatformPackage p = new PlatformPackageWithFakeArchive(
                new MockPlatformTarget(5 /*apiLevel*/, 1 /*revision*/),
                props);

        return p;
    }

    @Override
    protected Properties createProps() {
        Properties props = super.createProps();

        // PlatformPackage properties
        props.setProperty(PkgProps.VERSION_API_LEVEL, "5");
        props.setProperty(PkgProps.PLATFORM_VERSION, "android-5");
        props.setProperty(PkgProps.PLATFORM_INCLUDED_ABI, "armeabi");

        return props;
    }

    protected void testCreatedPlatformPackage(PlatformPackage p) {
        super.testCreatedPackage(p);

        // Package properties
        assertEquals("API 5", p.getVersion().toString());
        assertEquals("armeabi", p.getIncludedAbi());
    }

    // ----

    @Override
    public final void testCreate() {
        Properties props = createProps();
        PlatformPackage p = createPlatformPackage(props);

        testCreatedPlatformPackage(p);
    }

    @Override
    public void testSaveProperties() {
        Properties props = createProps();
        PlatformPackage p = createPlatformPackage(props);

        Properties props2 = new Properties();
        p.saveProperties(props2);

        assertEquals(props2.toString(), props.toString());
        assertEquals(props2, props);
    }

    public void testInstallId() {
        Properties props = createProps();
        PlatformPackage p = createPlatformPackage(props);

        assertEquals("android-5", p.installId());
    }
}

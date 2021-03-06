/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.cakephp.netbeans.options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 *
 * @author junichi11
 */
public class CakePhpOptions {

    private static final int NAME = 0;
    private static final int URL = 1;
    private static final String PLUGINS = "plugins"; // NOI18N
    private static final String NEW_PROJECT = "new-project"; // NOI18N
    private static final String LOCAL_ZIP_FILE_PATH = "local-zip-file-path"; // NOI18N
    private static CakePhpOptions INSTANCE = new CakePhpOptions();

    private CakePhpOptions() {
    }

    public static CakePhpOptions getInstance() {
        return INSTANCE;
    }

    public List<CakePhpPlugin> getPlugins() {
        ArrayList<CakePhpPlugin> plugins = new ArrayList<CakePhpPlugin>();
        Preferences p = getPreferences().node(PLUGINS).node(PLUGINS);
        String s = "";
        if (p != null) {
            s = p.get(PLUGINS, ""); // NOI18N
        }
        if (s.isEmpty()) {
            s = NbBundle.getMessage(CakePhpOptions.class, "CakePhpOptions.defaultPlugins"); // NOI18N
        }

        String[] rows = s.split(";"); // NOI18N
        Arrays.sort(rows);
        for (String row : rows) {
            String[] cells = row.split(","); // NOI18N
            if (cells.length == 2) {
                plugins.add(new CakePhpPlugin(cells[NAME], cells[URL]));
            }
        }
        return plugins;
    }

    public void setPlugins(List<CakePhpPlugin> plugins) {
        Preferences p = getPreferences().node(PLUGINS).node(PLUGINS);
        String lists = ""; // NOI18N
        boolean first = true;
        for (CakePhpPlugin plugin : plugins) {
            if (first) {
                first = false;
            } else {
                lists += ";"; // NOI18N
            }
            lists += plugin.getName() + "," + plugin.getUrl(); // NOI18N
        }
        p.put(PLUGINS, lists);
    }

    public String getLocalZipFilePath() {
        return getPreferences().node(NEW_PROJECT).get(LOCAL_ZIP_FILE_PATH, ""); // NOI18N
    }

    public void setLocalZipFilePath(String path) {
        getPreferences().node(NEW_PROJECT).put(LOCAL_ZIP_FILE_PATH, path);
    }

    public Preferences getPreferences() {
        return NbPreferences.forModule(CakePhpOptions.class);
    }
}

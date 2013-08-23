/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.sdk.persistence.page;

import org.eclipse.scout.sdk.ui.internal.view.outline.pages.project.AbstractBundleNodeTablePage;
import org.eclipse.scout.sdk.ui.internal.view.outline.pages.project.ScoutBundleNode;
import org.eclipse.scout.sdk.ui.view.outline.pages.IPage;

/**
 * <h3>{@link PersistenceBundleNodePage}</h3> ...
 *
 */
public class PersistenceBundleNodePage extends AbstractBundleNodeTablePage {

  public final static String PERSISTENCE_NODE_PAGE = "org.eclipse.scout.sdk.page.persistence";

  public PersistenceBundleNodePage(IPage parentPage, ScoutBundleNode node) {
    super(parentPage, node);
  }

  @Override
  public String getPageId() {
    return PERSISTENCE_NODE_PAGE;
  }

}

package org.eclipse.scout.sdk.persistence.page;

import org.eclipse.scout.sdk.persistence.ScoutSdkPersistence;
import org.eclipse.scout.sdk.persistence.SdkPersistenceIcons;
import org.eclipse.scout.sdk.persistence.action.WhatEverAction;
import org.eclipse.scout.sdk.ui.action.IScoutHandler;
import org.eclipse.scout.sdk.ui.view.outline.pages.AbstractPage;
import org.eclipse.scout.sdk.workspace.IScoutBundle;

public class WhatEverTablePage extends AbstractPage {

	public static final String WHAT_EVER_TABLE_PAGE_ID = "whatEverTablePageId";

	public WhatEverTablePage() {
		setName("What ever");
		setImageDescriptor(ScoutSdkPersistence
				.getImageDescriptor(SdkPersistenceIcons.EMOTION_HAPPY));
	}

	@Override
	public String getPageId() {
		return WHAT_EVER_TABLE_PAGE_ID;
	}

	@Override
	protected void loadChildrenImpl() {
		IScoutBundle persistenceBundle = getScoutBundle();
		System.out.println(persistenceBundle);
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends IScoutHandler>[] getSupportedMenuActions() {
		return new Class[] { WhatEverAction.class };
	}

	@Override
	public void prepareMenuAction(IScoutHandler menu) {
		if (menu instanceof WhatEverAction) {
			WhatEverAction action = (WhatEverAction) menu;
			action.setPersistenceBundle(getScoutBundle());
		}
	}

}

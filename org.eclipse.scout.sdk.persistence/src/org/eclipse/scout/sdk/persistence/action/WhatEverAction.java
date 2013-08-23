package org.eclipse.scout.sdk.persistence.action;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scout.sdk.persistence.ScoutSdkPersistence;
import org.eclipse.scout.sdk.persistence.SdkPersistenceIcons;
import org.eclipse.scout.sdk.ui.action.AbstractScoutHandler;
import org.eclipse.scout.sdk.ui.view.outline.pages.IPage;
import org.eclipse.scout.sdk.workspace.IScoutBundle;
import org.eclipse.swt.widgets.Shell;

public class WhatEverAction extends AbstractScoutHandler{

	private IScoutBundle persistenceBundle;

	public WhatEverAction(){
		super("What ever action...", ScoutSdkPersistence.getImageDescriptor(SdkPersistenceIcons.EMOTION_HAPPY));
	}
	@Override
	public Object execute(Shell shell, IPage[] selection, ExecutionEvent event)
			throws ExecutionException {
		MessageDialog.openInformation(shell, "Whatever to do...", "have fun with '"+getPersistenceBundle().getSymbolicName()+"'!");

		return null;
	}
	public void setPersistenceBundle(IScoutBundle persistenceBundle) {
		this.persistenceBundle = persistenceBundle;

	}

	public IScoutBundle getPersistenceBundle() {
		return persistenceBundle;
	}



}

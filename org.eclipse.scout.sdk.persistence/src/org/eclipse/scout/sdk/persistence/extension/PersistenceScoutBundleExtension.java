package org.eclipse.scout.sdk.persistence.extension;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.scout.sdk.Texts;
import org.eclipse.scout.sdk.operation.project.IScoutProjectNewOperation;
import org.eclipse.scout.sdk.persistence.ScoutSdkPersistence;
import org.eclipse.scout.sdk.persistence.operation.CreatePersistenceBundleOperation;
import org.eclipse.scout.sdk.ui.extensions.bundle.INewScoutBundleHandler;
import org.eclipse.scout.sdk.ui.extensions.bundle.ScoutBundleUiExtension;
import org.eclipse.scout.sdk.ui.internal.ScoutSdkUi;
import org.eclipse.scout.sdk.ui.wizard.project.IScoutProjectWizard;
import org.eclipse.scout.sdk.util.PropertyMap;
import org.eclipse.scout.sdk.workspace.IScoutBundle;
import org.eclipse.scout.sdk.workspace.ScoutBundleFilters;

public class PersistenceScoutBundleExtension implements INewScoutBundleHandler {

  public final static String ID = "org.eclipse.scout.sdk.WebBundle";

  @Override
  public void init(IScoutProjectWizard wizard, ScoutBundleUiExtension extension) {
    IScoutBundle selected = wizard.getScoutProject();
    boolean available = selected == null || selected.getType().equals(ScoutSdkPersistence.BUNDLE_TYPE_PERSISTENCE) || selected.getType().equals(IScoutBundle.TYPE_SERVER);
    wizard.getProjectWizardPage().setBundleNodeAvailable(available, available, ID);
  }

  @Override
  public void bundleSelectionChanged(IScoutProjectWizard wizard, boolean selected) {
  }

  @Override
  public IStatus getStatus(IScoutProjectWizard wizard) {
    IScoutBundle selected = wizard.getScoutProject();
    if (selected == null) {
      if (!wizard.getProjectWizardPage().hasSelectedBundle(IScoutBundle.TYPE_SERVER)) {
        return new Status(IStatus.ERROR, ScoutSdkUi.PLUGIN_ID, Texts.get("AServerBundleIsRequired"));
      }
    }
    return Status.OK_STATUS;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void putProperties(IScoutProjectWizard wizard, PropertyMap properties) {
    IScoutBundle selected = wizard.getScoutProject();
    if (selected != null) {
      IScoutBundle persistenceBundle = selected.getParentBundle(ScoutBundleFilters.getBundlesOfTypeFilter(ScoutSdkPersistence.BUNDLE_TYPE_PERSISTENCE), true);
      if (persistenceBundle != null && persistenceBundle.getJavaProject() != null) {
        properties.setProperty(CreatePersistenceBundleOperation.PROP_BUNDLE_PERSISTENCE_NAME, persistenceBundle.getProject().getName());
        properties.getProperty(IScoutProjectNewOperation.PROP_CREATED_BUNDLES, List.class).add(persistenceBundle.getJavaProject());
      }
    }
  }
}

package org.eclipse.scout.sdk.persistence.operation;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.scout.sdk.operation.project.AbstractCreateScoutBundleOperation;
import org.eclipse.scout.sdk.operation.template.InstallTextFileOperation;
import org.eclipse.scout.sdk.persistence.ScoutSdkPersistence;
import org.eclipse.scout.sdk.util.typecache.IWorkingCopyManager;
import org.osgi.framework.Bundle;

public class CreatePersistenceBundleOperation extends AbstractCreateScoutBundleOperation {
  public final static String PROP_BUNDLE_PERSISTENCE_NAME = "BUNDLE_PERSISTENCE_NAME";

  public final static String PERSISTENCE_PROJECT_NAME_SUFFIX = "persistence";
  public final static String BUNDLE_ID = "org.eclipse.scout.sdk.persistence.BundleId";

  @Override
  public boolean isRelevant() {
    return isNodeChecked(CreatePersistenceBundleOperation.BUNDLE_ID);
  }

  @Override
  public void init() {
    setSymbolicName(getPluginName(PERSISTENCE_PROJECT_NAME_SUFFIX));
  }

  @Override
  public String getOperationName() {
    return "Create Persistence Plugin";
  }

  @Override
  public void run(IProgressMonitor monitor, IWorkingCopyManager workingCopyManager) throws CoreException {
    super.run(monitor, workingCopyManager);
    IProject project = getCreatedProject();
    getProperties().setProperty(PROP_BUNDLE_PERSISTENCE_NAME, getSymbolicName());

    Map<String, String> props = getStringProperties();
    Bundle persistenceSdkBundle = Platform.getBundle(ScoutSdkPersistence.PLUGIN_ID);
    new InstallTextFileOperation("templates/persistence/META-INF/MANIFEST.MF", "META-INF/MANIFEST.MF", persistenceSdkBundle, project, props).run(monitor, workingCopyManager);
    new InstallTextFileOperation("templates/persistence/build.properties", "build.properties", persistenceSdkBundle, project, props).run(monitor, workingCopyManager);

  }
}

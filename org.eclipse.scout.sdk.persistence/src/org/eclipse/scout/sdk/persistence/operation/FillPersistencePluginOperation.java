package org.eclipse.scout.sdk.persistence.operation;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.ClasspathComputer;
import org.eclipse.pde.internal.core.builders.PDEMarkerFactory;
import org.eclipse.scout.sdk.internal.ScoutSdk;
import org.eclipse.scout.sdk.operation.project.AbstractScoutProjectNewOperation;
import org.eclipse.scout.sdk.operation.template.InstallJavaFileOperation;
import org.eclipse.scout.sdk.persistence.ScoutSdkPersistence;
import org.eclipse.scout.sdk.util.type.TypeUtility;
import org.eclipse.scout.sdk.util.typecache.IWorkingCopyManager;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
public class FillPersistencePluginOperation extends AbstractScoutProjectNewOperation {
  private IProject m_persistenceProject;

  @Override
  public String getOperationName() {
    return "Fill Scout Persistence Plugin";
  }

  @Override
  public boolean isRelevant() {
    return isNodeChecked(CreatePersistenceBundleOperation.BUNDLE_ID);
  }

  @Override
  public void init() {
    String persistencePluginName = getProperties().getProperty(CreatePersistenceBundleOperation.PROP_BUNDLE_PERSISTENCE_NAME, String.class);
    m_persistenceProject = getCreatedBundle(persistencePluginName).getProject();
  }

  @Override
  public void validate() throws IllegalArgumentException {
    super.validate();
    if (m_persistenceProject == null) {
      throw new IllegalArgumentException("project can not be null.");
    }
  }

  @Override
  public void run(IProgressMonitor monitor, IWorkingCopyManager workingCopyManager) throws CoreException, IllegalArgumentException {
    Map<String, String> props = getStringProperties();
    String destPathPref = TypeUtility.DEFAULT_SOURCE_FOLDER_NAME + "/" + m_persistenceProject.getName().replace('.', '/') + "/";
    try {
      Bundle persistenceSdkBundle = Platform.getBundle(ScoutSdkPersistence.PLUGIN_ID);
      new InstallJavaFileOperation("templates/persistence/src/Activator.java", destPathPref + "Activator.java", persistenceSdkBundle, m_persistenceProject, props).run(monitor, workingCopyManager);
    }
    catch (Exception e) {
      throw new CoreException(new Status(IStatus.ERROR, ScoutSdk.PLUGIN_ID, "could not install files in '" + m_persistenceProject.getName() + "'.", e));
    }

    IProject project = m_persistenceProject;
    IProjectDescription projDesc = project.getDescription();
    projDesc.setReferencedProjects(new IProject[0]);
    project.setDescription(projDesc, null);
    IFile file = project.getFile(".project"); //$NON-NLS-1$
    if (file.exists()) {
      file.deleteMarkers(PDEMarkerFactory.MARKER_ID, true, IResource.DEPTH_ZERO);
    }
    ResourcesPlugin.getWorkspace().checkpoint(false);
    ClasspathComputer.setClasspath(project, PluginRegistry.findModel(project));
  }
}

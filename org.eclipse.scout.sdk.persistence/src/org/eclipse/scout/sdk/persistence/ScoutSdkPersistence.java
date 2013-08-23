package org.eclipse.scout.sdk.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.scout.sdk.ui.internal.ImageRegistry;
import org.eclipse.scout.sdk.ui.internal.ScoutSdkUi;
import org.eclipse.scout.sdk.util.log.SdkLogManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ScoutSdkPersistence extends AbstractUIPlugin implements
		SdkPersistenceIcons {

	public static final String PLUGIN_ID = "org.eclipse.scout.sdk.persistence";
	public static final String BUNDLE_TYPE_PERSISTENCE = "PERSISTENCE";
	private static final String IMAGE_PATH = "resources/icons/";

	private static ScoutSdkPersistence plugin;

	private static SdkLogManager logManager;

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ScoutSdkPersistence getDefault() {
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);

		plugin = this;
		logManager = new SdkLogManager(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		logManager = null;
		super.stop(bundleContext);
	}

	public static void logInfo(Throwable t) {
		logManager.logInfo(t);
	}

	public static void logInfo(String message) {
		logManager.logInfo(message);
	}

	public static void logInfo(String message, Throwable t) {
		logManager.logInfo(message, t);
	}

	public static void logWarning(String message) {
		logManager.logWarning(message);
	}

	public static void logWarning(Throwable t) {
		logManager.logWarning(t);
	}

	public static void logWarning(String message, Throwable t) {
		logManager.logWarning(message, t);
	}

	public static void logError(Throwable t) {
		logManager.logError(t);
	}

	public static void logError(String message) {
		logManager.logError(message);
	}

	public static void logError(String message, Throwable t) {
		logManager.logError(message, t);
	}

	@Override
	protected ImageRegistry createImageRegistry() {
		// If we are in the UI Thread use that
		if (Display.getCurrent() != null) {
			return new ImageRegistry(Display.getCurrent());
		}
		if (PlatformUI.isWorkbenchRunning()) {
			return new ImageRegistry(PlatformUI.getWorkbench().getDisplay());
		}
		// Invalid thread access if it is not the UI Thread
		// and the workbench is not created.
		throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);
	}

	@Override
	public ImageRegistry getImageRegistry() {
		return (ImageRegistry) super.getImageRegistry();
	}

	/**
	 * Returns the image for the given descriptor.
	 */
	public static Image getImage(ImageDescriptor imageDescriptor) {
		return getDefault().getImageImpl(imageDescriptor);
	}

	private Image getImageImpl(ImageDescriptor imageDescriptor) {
		return getImageRegistry().get(imageDescriptor);
	}

	/**
	 * To get a cached image with one of the extensions [gif | png | jpg]
	 *
	 * @param name
	 *            the name without extension located under resources/icons e.g.
	 *            "person"
	 * @return the cached image
	 */
	public static Image getImage(String name) {
		return getDefault().getImageImpl(name);
	}

	private Image getImageImpl(String name) {
		Image image = getImageRegistry().get(name);
		if (image == null) {
			loadImage(name);
			image = getImageRegistry().get(name);
		}
		return image;
	}

	/**
	 * To get a cached image with one of the extensions [gif | png | jpg]
	 *
	 * @param name
	 *            the file name (with or without extension) located under
	 *            resources/icons
	 * @return the cached image
	 */
	public static ImageDescriptor getImageDescriptor(String name) {
		return getDefault().getImageDescriptorImpl(name);
	}

	/**
	 * @param imageName
	 *            the file name (with or without extension) of the base image
	 *            located under resources/icons
	 * @param decorationImageName
	 *            the file name (with or without extension) of the decoration
	 *            image located under resources/icons
	 * @param quadrant
	 *            specifies where on the base image the decoration should be
	 *            placed (one of {@link IDecoration#TOP_LEFT},
	 *            {@link IDecoration#TOP_RIGHT}, {@link IDecoration#BOTTOM_LEFT}
	 *            , {@link IDecoration#BOTTOM_RIGHT} or
	 *            {@link IDecoration#UNDERLAY})
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(String imageName,
			String decorationImageName, int quadrant) {
		ImageDescriptor baseIcon = getImageDescriptor(imageName);
		return getImageDescriptor(baseIcon, decorationImageName, quadrant);
	}

	/**
	 * @param baseIcon
	 *            base icon on which the decoration should be placed.
	 * @param decorationImageName
	 *            the file name (with or without extension) of the decoration
	 *            image located under resources/icons
	 * @param quadrant
	 *            specifies where on the base image the decoration should be
	 *            placed (one of {@link IDecoration#TOP_LEFT},
	 *            {@link IDecoration#TOP_RIGHT}, {@link IDecoration#BOTTOM_LEFT}
	 *            , {@link IDecoration#BOTTOM_RIGHT} or
	 *            {@link IDecoration#UNDERLAY})
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(ImageDescriptor baseIcon,
			String decorationImageName, int quadrant) {
		// get the base image
		Image baseImage = ScoutSdkUi.getImage(baseIcon);

		// get the decoration image
		ImageDescriptor decorationIcon = ScoutSdkUi
				.getImageDescriptor(decorationImageName);

		// combine
		ImageDescriptor decoratedIcon = new DecorationOverlayIcon(baseImage,
				decorationIcon, quadrant);
		return decoratedIcon;
	}

	private ImageDescriptor getImageDescriptorImpl(String name) {
		ImageDescriptor imageDesc = getImageRegistry().getDescriptor(name);
		if (imageDesc == null) {
			loadImage(name);
			imageDesc = getImageRegistry().getDescriptor(name);
		}
		return imageDesc;
	}

	private void loadImage(String name) {
		ImageDescriptor desc = null;
		if (name.startsWith(IMAGE_PATH)) {
			desc = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, name);
		}
		if (desc == null) {
			// try already extension
			desc = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
					IMAGE_PATH + name);
		}
		// gif
		if (desc == null) {
			desc = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
					IMAGE_PATH + name + ".gif");
		}
		// png
		if (desc == null) {
			desc = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
					IMAGE_PATH + name + ".png");
		}
		// jpg
		if (desc == null) {
			desc = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
					IMAGE_PATH + name + ".jpg");
		}
		if (desc == null) {
			logWarning("could not find image for plugin: '" + PLUGIN_ID
					+ "' under: '" + IMAGE_PATH + name + "'.");
		} else {
			getImageRegistry().put(name, desc);
		}
	}


}

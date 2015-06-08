package it.escape.client.view.gui;

import it.escape.client.controller.gui.UpdaterSwingToDisplayerInterface;

/**
 * The view will use this interface to associate herself with
 * an existing updater
 * @author michele
 *
 */
public interface BindUpdaterInterface {
	public void bindView(UpdaterSwingToDisplayerInterface view);
}

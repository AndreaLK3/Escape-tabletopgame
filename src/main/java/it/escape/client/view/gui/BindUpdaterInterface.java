package it.escape.client.view.gui;

import it.escape.client.controller.gui.UpdaterSwingToViewInterface;

/**
 * The view will use this interface to associate herself with
 * an existing updater
 * (AL, Note: is it really necessary?)
 * (MM: yes, the way SwingView is started, that's the best way to
 * do it, while keeping dependancy cycles at a minimum)
 * @author michele
 *
 */
public interface BindUpdaterInterface {
	public void bindView(UpdaterSwingToViewInterface view);
}

/*
 * FileSync.java
 * William added comments to this file.
 * Line one // Paste it. This is how I reolve this, these two lines conflicts.
 * Another comments added.
 */

package org.leo.app.filesync;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class FileSync extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
    	System.out.println("check out this,again");
        show(new FileSyncCriteriaView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of FileSync
     */
    public static FileSync getApplication() {
        return Application.getInstance(FileSync.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(FileSync.class, args);
    }
}

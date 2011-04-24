/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.leo.app.filesync;

import java.io.File;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author haoliu
 */
public class ReportTask extends Task<Void, Void> {

	private long count = 0;
	private String dir;

    public ReportTask(Application app, String dir) {
	super(app);
	this.dir = dir;
    }


    @Override
    protected Void doInBackground() throws Exception {
        File start = new File(dir);
	if (start.exists()) {
	    message("startMessage", dir);
            countFile(start);
	} else {
            message("errorMessage", dir);
	}

	return null;
    }

    @Override
    protected void succeeded(Void result) {
	super.succeeded(result);
	message("finishedMessage", String.valueOf(count));
    }



    public void countFile(File f) {
	if (f.isFile()) {
	    count++;
	} else if (f.isDirectory()) {
            File[] fs = f.listFiles();
	    if (fs != null) {
		for (File fl : fs) {
		    countFile(fl);
		}
	    }
	}
    }

}

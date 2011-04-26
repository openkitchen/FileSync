/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.leo.app.filesync;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author haoliu
 */
public class ReportTask extends Task<Void, Void> {

    private long count = 0;
    final private Map<File, File> sourceFiles = new HashMap<File, File>();
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
	    exportReport();
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

    private void countFile(File f) {
	if (f.isFile()) {
	    sourceFiles.put(f, f);
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

    private void exportReport() throws Exception {
	String reportPath = null;
        JFileChooser jc = new JFileChooser();
	jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = jc.showOpenDialog(((FileSync)getApplication()).getMainFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            reportPath = jc.getSelectedFile().getAbsolutePath();
        }
        final BufferedWriter bw =
		new BufferedWriter(new FileWriter(reportPath + "//FileSync.txt"));
	try {
            bw.write("Total: " + sourceFiles.size());
	    bw.write("\n");
	    Iterator<File> iter = sourceFiles.keySet().iterator();
	    while (iter.hasNext()) {
		File f = sourceFiles.get(iter.next());
		bw.write(f.getAbsolutePath());
		bw.write("\n");
	    }
	} finally {
	    bw.flush();
            bw.close();
	}
    }

}

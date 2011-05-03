/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.leo.app.filesync;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.HashMap;
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
    private long tgtCount = 0;
    final private Map<String, File> targetFiles = new HashMap<String, File>();
    private final File source;
    private final File target;

    public ReportTask(Application app, String source, String target) {
	super(app);
	this.source = new File(source);
	this.target = new File(target);
    }


    @Override
    protected Void doInBackground() throws Exception {
        if (!source.exists()) {
            message("errorMessage", source.getName());
            return null;
        }
        
        if (!target.exists()) {
            message("errorMessage", target.getName());
            return null;
        }
	
	message("scanTargetMessage", target.getName());
        countFile(target);
        
        message("scanSourceMessage", source.getName());
	    exportReport();
	
	    return null;
    }

    @Override
    protected void succeeded(Void result) {
	super.succeeded(result);
	message("finishedMessage", String.valueOf(count));
    }

    private static String createHash(File f) {
        return f.getName() + String.valueOf(f.length());
    }

    private void countFile(File f) {
	if (f.isFile()) {
	    targetFiles.put(createHash(f), f);
	    tgtCount++;
	} else if (f.isDirectory()) {
            File[] fs = f.listFiles();
	    if (fs != null) {
		for (File fl : fs) {
		    countFile(fl);
		}
	    }
	}
    }

    private void analyzeSource(File f, BufferedWriter bw) throws Exception {
        if (f.isFile()) {
            count++;
            if (targetFiles.containsKey(createHash(f))) {
                bw.write("-" + f.getAbsolutePath());
            } else {
                bw.write("+" + f.getAbsolutePath());
	    }
            bw.write("\n");
	} else if (f.isDirectory()) {
            File[] fs = f.listFiles();
	    if (fs != null) {
		for (File fl : fs) {
		    analyzeSource(fl, bw);
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
            analyzeSource(source, bw);
            bw.write("Total Source: " + count);
            bw.write(", Total Target: " + tgtCount);
	    bw.write("\n");
	} finally {
	    bw.flush();
            bw.close();
	}
    }

}

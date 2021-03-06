/*
 * FileSyncCriteriaView.java
 */

package org.leo.app.filesync;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.application.Task;
import org.jdesktop.application.Task.BlockingScope;
import org.jdesktop.application.TaskService;

/**
 * The application's main frame.
 */
public class FileSyncCriteriaView extends FrameView {

    public FileSyncCriteriaView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = FileSync.getApplication().getMainFrame();
            aboutBox = new FileSyncAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        FileSync.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                mainPanel = new javax.swing.JPanel();
                lblSrc = new javax.swing.JLabel();
                lblTgt = new javax.swing.JLabel();
                jTxtSrc = new javax.swing.JTextField();
                jTxtTgt = new javax.swing.JTextField();
                btnSync = new javax.swing.JButton();
                btnReport = new javax.swing.JButton();
                btnSrcBrowse = new javax.swing.JButton();
                btnTrgBrowse = new javax.swing.JButton();
                menuBar = new javax.swing.JMenuBar();
                javax.swing.JMenu fileMenu = new javax.swing.JMenu();
                javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
                javax.swing.JMenu helpMenu = new javax.swing.JMenu();
                javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
                statusPanel = new javax.swing.JPanel();
                javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
                statusMessageLabel = new javax.swing.JLabel();
                statusAnimationLabel = new javax.swing.JLabel();
                progressBar = new javax.swing.JProgressBar();

                mainPanel.setName("mainPanel"); // NOI18N

                org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.leo.app.filesync.FileSync.class).getContext().getResourceMap(FileSyncCriteriaView.class);
                lblSrc.setText(resourceMap.getString("lblSrc.text")); // NOI18N
                lblSrc.setName("lblSrc"); // NOI18N

                lblTgt.setText(resourceMap.getString("lblTgt.text")); // NOI18N
                lblTgt.setName("lblTgt"); // NOI18N

                jTxtSrc.setName("jTxtSrc"); // NOI18N

                jTxtTgt.setName("jTxtTgt"); // NOI18N

                btnSync.setText(resourceMap.getString("btnSync.text")); // NOI18N
                btnSync.setName("btnSync"); // NOI18N

                javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.leo.app.filesync.FileSync.class).getContext().getActionMap(FileSyncCriteriaView.class, this);
                btnReport.setAction(actionMap.get("generateReport")); // NOI18N
                btnReport.setText(resourceMap.getString("btnReport.text")); // NOI18N
                btnReport.setName("btnReport"); // NOI18N

                btnSrcBrowse.setAction(actionMap.get("showDirChooser")); // NOI18N
                btnSrcBrowse.setText(resourceMap.getString("btnSrcBrowse.text")); // NOI18N
                btnSrcBrowse.setActionCommand(resourceMap.getString("btnSrcBrowse.actionCommand")); // NOI18N
                btnSrcBrowse.setName("btnSrcBrowse"); // NOI18N

                btnTrgBrowse.setText(resourceMap.getString("btnTrgBrowse.text")); // NOI18N
                btnTrgBrowse.setActionCommand(resourceMap.getString("btnTrgBrowse.actionCommand")); // NOI18N
                btnTrgBrowse.setName("btnTrgBrowse"); // NOI18N

                org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                        mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(mainPanelLayout.createSequentialGroup()
                                                .add(btnSync)
                                                .add(18, 18, 18)
                                                .add(btnReport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(mainPanelLayout.createSequentialGroup()
                                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                        .add(lblSrc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .add(lblTgt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                                .add(18, 18, 18)
                                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                                        .add(jTxtTgt)
                                                        .add(jTxtSrc, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                        .add(btnTrgBrowse)
                                                        .add(btnSrcBrowse))))
                                .addContainerGap())
                );
                mainPanelLayout.setVerticalGroup(
                        mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(mainPanelLayout.createSequentialGroup()
                                .add(25, 25, 25)
                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(jTxtSrc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(btnSrcBrowse)
                                        .add(lblSrc))
                                .add(13, 13, 13)
                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(jTxtTgt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(btnTrgBrowse)
                                        .add(lblTgt))
                                .add(18, 18, 18)
                                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(btnSync)
                                        .add(btnReport))
                                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                btnSrcBrowse.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnBrowseActionPerformed(evt);
                        }
                });
                btnTrgBrowse.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnBrowseActionPerformed(evt);
                        }
                });

                menuBar.setName("menuBar"); // NOI18N

                fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
                fileMenu.setName("fileMenu"); // NOI18N

                exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
                exitMenuItem.setName("exitMenuItem"); // NOI18N
                fileMenu.add(exitMenuItem);

                menuBar.add(fileMenu);

                helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
                helpMenu.setName("helpMenu"); // NOI18N

                aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
                aboutMenuItem.setName("aboutMenuItem"); // NOI18N
                helpMenu.add(aboutMenuItem);

                menuBar.add(helpMenu);

                statusPanel.setName("statusPanel"); // NOI18N

                statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

                statusMessageLabel.setName("statusMessageLabel"); // NOI18N

                statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

                progressBar.setName("progressBar"); // NOI18N

                org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
                statusPanel.setLayout(statusPanelLayout);
                statusPanelLayout.setHorizontalGroup(
                        statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .add(statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(statusMessageLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 244, Short.MAX_VALUE)
                                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(statusAnimationLabel)
                                .addContainerGap())
                );
                statusPanelLayout.setVerticalGroup(
                        statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(statusPanelLayout.createSequentialGroup()
                                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(statusMessageLabel)
                                        .add(statusAnimationLabel)
                                        .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(3, 3, 3))
                );

                setComponent(mainPanel);
                setMenuBar(menuBar);
                setStatusBar(statusPanel);
        }// </editor-fold>//GEN-END:initComponents

        private void btnBrowseActionPerformed(ActionEvent evt) {
            if ("SourceBrowse".equals(evt.getActionCommand())) {
		showDirChooser(jTxtSrc);
	    } else if ("TargetBrowse".equals(evt.getActionCommand())) {
		showDirChooser(jTxtTgt);
	    }
	}

	private void showDirChooser(final JTextField jt) {
            if (jc == null) {
		jc = new JFileChooser();
	    }
	    JFrame mainFrame = FileSync.getApplication().getMainFrame();
	    jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int returnVal = jc.showOpenDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                jt.setText(jc.getSelectedFile().getAbsolutePath());
	    }
	}

	@Action(block=BlockingScope.ACTION)
	public Task generateReport() {
	    String src = jTxtSrc.getText();
            String tgt = jTxtTgt.getText();
	    if (src == null || "".equals(src)) {
	        JFrame mainFrame = FileSync.getApplication().getMainFrame();
	        JOptionPane.showMessageDialog(mainFrame, "Eggs are not supposed to be green.");
	    } else if (tgt == null || "".equals(tgt)) {
                JFrame mainFrame = FileSync.getApplication().getMainFrame();
	        JOptionPane.showMessageDialog(mainFrame, "Eggs are not supposed to be green.");                 
            } else {
                return new ReportTask(FileSync.getApplication(), src, tgt);
	    }

	    return null;
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnReport;
        private javax.swing.JButton btnSrcBrowse;
        private javax.swing.JButton btnSync;
        private javax.swing.JButton btnTrgBrowse;
        private javax.swing.JTextField jTxtSrc;
        private javax.swing.JTextField jTxtTgt;
        private javax.swing.JLabel lblSrc;
        private javax.swing.JLabel lblTgt;
        private javax.swing.JPanel mainPanel;
        private javax.swing.JMenuBar menuBar;
        private javax.swing.JProgressBar progressBar;
        private javax.swing.JLabel statusAnimationLabel;
        private javax.swing.JLabel statusMessageLabel;
        private javax.swing.JPanel statusPanel;
        // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private JFileChooser jc;
}

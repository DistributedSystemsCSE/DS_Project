package gui;

import configs.Configs;
import ds_project.Node;
import helper.BsRegisterException;
import helper.NeighbourConnectionException;
import helper.SearchResult;
import helper.SearchResultTable;
import helper.TCPException;
import java.io.IOException;
import java.net.BindException;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Hareen Udayanath
 */
public class NodeGUI extends javax.swing.JFrame implements Observer{

    private Node node;
    private final Configs configs = new Configs();
    private SearchResultTable searchResultTable;
    private String search_keyword;
    private boolean isConnected = false;
    /**
     * Creates new form NodeGUI
     */
    public NodeGUI() {
        initComponents();
        buttonGroup1.add(rbtnRPC);
        buttonGroup1.add(rbtnUDP);
        
        init();
    }
    
    private void init(){
        if(configs.isUDP())
            rbtnUDP.setSelected(true);
        else
            rbtnRPC.setSelected(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaSearch = new javax.swing.JTextArea();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        pnlShowFiles = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaShowFiles = new javax.swing.JTextArea();
        btnShowFiles = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        txtPort = new javax.swing.JTextField();
        btnSetPort = new javax.swing.JButton();
        txtIP = new javax.swing.JTextField();
        btnSetIP = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();
        btnSetName = new javax.swing.JButton();
        txtServerPort = new javax.swing.JTextField();
        btnSetServerPort = new javax.swing.JButton();
        txtServerIP = new javax.swing.JTextField();
        btnSetServerIP = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaDetails = new javax.swing.JTextArea();
        btnShowNeighbours = new javax.swing.JButton();
        rbtnUDP = new javax.swing.JRadioButton();
        rbtnRPC = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtaSearch.setEditable(false);
        txtaSearch.setColumns(20);
        txtaSearch.setRows(5);
        jScrollPane2.setViewportView(txtaSearch);

        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pnlShowFiles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtaShowFiles.setEditable(false);
        txtaShowFiles.setColumns(20);
        txtaShowFiles.setRows(5);
        jScrollPane1.setViewportView(txtaShowFiles);

        btnShowFiles.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnShowFiles.setText("Show Files");
        btnShowFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowFilesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlShowFilesLayout = new javax.swing.GroupLayout(pnlShowFiles);
        pnlShowFiles.setLayout(pnlShowFilesLayout);
        pnlShowFilesLayout.setHorizontalGroup(
            pnlShowFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlShowFilesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlShowFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlShowFilesLayout.createSequentialGroup()
                        .addComponent(btnShowFiles)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        pnlShowFilesLayout.setVerticalGroup(
            pnlShowFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlShowFilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShowFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnStart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        txtPort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPortMouseClicked(evt);
            }
        });
        txtPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPortActionPerformed(evt);
            }
        });

        btnSetPort.setText("Set Port");
        btnSetPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetPortActionPerformed(evt);
            }
        });

        txtIP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtIPMouseClicked(evt);
            }
        });
        txtIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIPActionPerformed(evt);
            }
        });

        btnSetIP.setText("Set IP");
        btnSetIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetIPActionPerformed(evt);
            }
        });

        txtName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNameMouseClicked(evt);
            }
        });
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        btnSetName.setText("Set Name");
        btnSetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetNameActionPerformed(evt);
            }
        });

        txtServerPort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtServerPortMouseClicked(evt);
            }
        });
        txtServerPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServerPortActionPerformed(evt);
            }
        });

        btnSetServerPort.setText("Set Server Port");
        btnSetServerPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetServerPortActionPerformed(evt);
            }
        });

        txtServerIP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtServerIPMouseClicked(evt);
            }
        });
        txtServerIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServerIPActionPerformed(evt);
            }
        });

        btnSetServerIP.setText("Set Server IP");
        btnSetServerIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetServerIPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtServerIP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(txtServerPort, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPort, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSetServerPort, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(btnSetServerIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSetIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSetPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSetName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSetServerIP)
                    .addComponent(txtServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSetServerPort)
                    .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSetIP)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtaDetails.setEditable(false);
        txtaDetails.setColumns(20);
        txtaDetails.setRows(5);
        jScrollPane3.setViewportView(txtaDetails);

        btnShowNeighbours.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnShowNeighbours.setText("Show Neighbours");
        btnShowNeighbours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowNeighboursActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnShowNeighbours)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnShowNeighbours)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        rbtnUDP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbtnUDP.setText("UDP");
        rbtnUDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnUDPActionPerformed(evt);
            }
        });

        rbtnRPC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbtnRPC.setText("RPC");
        rbtnRPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnRPCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtnUDP)
                            .addComponent(rbtnRPC))
                        .addGap(58, 58, 58)
                        .addComponent(pnlShowFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlShowFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(rbtnUDP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnRPC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
        search_keyword = txtSearch.getText();
        String result = "";
        result = searchResultTable.getResults(search_keyword)
                .stream().map((s_result) -> s_result.toString()+"\n")
                .reduce(result, String::concat);
        txtaSearch.setText(result);
        node.seachFile(search_keyword);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnShowNeighboursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowNeighboursActionPerformed
        txtaDetails.setText(node.getNeighbourString());        
    }//GEN-LAST:event_btnShowNeighboursActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        node = Node.getInstance();
        node.configureVariables();
        searchResultTable = node.getSearchResultTable();
        searchResultTable.addObserver(this);
        start();
    }//GEN-LAST:event_btnStartActionPerformed

    private void start(){        
        try{            
            node.register();
            setStartedContions();            
             
        }catch(NeighbourConnectionException ex){
            String[] buttons = { "Retry", "Force Register", "Cancel"};    
            int returnValue = JOptionPane
                    .showOptionDialog(null, ex.getMessage(), "Could not register",
            JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[2]);
            if(returnValue==0){
                node.setForceRegsister(false);
                start();
            }else if(returnValue==1){
                node.setForceRegsister(true);
                start();
            }
                  
        }catch(BsRegisterException ex){
            if(ex.getErrorValue()==9998){
                try {
                    node.unregister();
                } catch (IOException ex1) {                    
                }
            }
                
            JOptionPane.showMessageDialog(this,ex.getMessage()); 
        }catch(BindException ex){    
            JOptionPane.showMessageDialog(this,"The port is already using"); 
        }catch(SocketTimeoutException ex){            
            JOptionPane.showMessageDialog(this,
                    "Unable to communicate with server\n"
                            + "Registation fail"); 
        }catch(IOException ex){            
            JOptionPane.showMessageDialog(this,ex.getMessage());  
        }  

    }
    
    
    private void btnSetPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetPortActionPerformed
        configs.setProperty("CLIENT_PORT", txtPort.getText());
    }//GEN-LAST:event_btnSetPortActionPerformed

    private void txtPortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPortMouseClicked
        txtPort.setText(configs.getStringProperty("CLIENT_PORT"));
    }//GEN-LAST:event_txtPortMouseClicked

    private void btnShowFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowFilesActionPerformed
        String files = "";
        files = node.getFileNames().stream()
                .map((file) -> file+"\n").reduce(files, String::concat);
        txtaShowFiles.setText(files);
    }//GEN-LAST:event_btnShowFilesActionPerformed

    private void txtIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIPMouseClicked
        txtIP.setText(configs.getStringProperty("CLIENT_IP"));
    }//GEN-LAST:event_txtIPMouseClicked

    private void btnSetIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetIPActionPerformed
        configs.setProperty("CLIENT_IP", txtIP.getText());
    }//GEN-LAST:event_btnSetIPActionPerformed

    private void txtNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNameMouseClicked
        txtName.setText(configs.getStringProperty("CLIENT_NAME"));
    }//GEN-LAST:event_txtNameMouseClicked

    private void btnSetNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetNameActionPerformed
        configs.setProperty("CLIENT_NAME", txtName.getText());
    }//GEN-LAST:event_btnSetNameActionPerformed

    private void txtIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIPActionPerformed
        configs.setProperty("CLIENT_IP", txtIP.getText());
    }//GEN-LAST:event_txtIPActionPerformed

    private void txtPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPortActionPerformed
        configs.setProperty("CLIENT_PORT", txtPort.getText());
    }//GEN-LAST:event_txtPortActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        configs.setProperty("CLIENT_NAME", txtName.getText());
    }//GEN-LAST:event_txtNameActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        txtName.setText(configs.getStringProperty("CLIENT_NAME"));
        txtIP.setText(configs.getStringProperty("CLIENT_IP"));
        txtPort.setText(configs.getStringProperty("CLIENT_PORT"));
        txtServerIP.setText(configs.getStringProperty("SERVER_IP"));
        txtServerPort.setText(configs.getStringProperty("SERVER_PORT"));
        
        btnSearch.setEnabled(false);
        btnShowFiles.setEnabled(false);
        btnShowNeighbours.setEnabled(false);
        
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(isConnected){
            try{
                if(node!=null){
                    node.leave();
                    node.unregister();
                }
                isConnected = false;
            }catch(SocketTimeoutException ex){
                JOptionPane.showMessageDialog(this,
                        "Unable to communicate with server\n"
                                + "Unregistation fail"); 
            }catch(IOException ex){
                JOptionPane.showMessageDialog(this,ex.getMessage());  
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void goToStartConditions(){
        btnSetIP.setEnabled(true);
        txtIP.setEditable(true);
        btnSetPort.setEnabled(true);
        txtPort.setEditable(true);
        btnSetName.setEnabled(true);
        txtName.setEditable(true);
        btnSetServerIP.setEnabled(true);
        txtServerIP.setEditable(true);
        btnSetServerPort.setEnabled(true);
        txtServerPort.setEditable(true);
        btnStart.setEnabled(true);
        rbtnRPC.setEnabled(true);
        rbtnUDP.setEnabled(true);
        
       
        btnSearch.setEnabled(false);
        btnShowFiles.setEnabled(false);
        btnShowNeighbours.setEnabled(false);
        
        isConnected = false;
    }
    
    private void setStartedContions(){
        btnSetIP.setEnabled(false);
        txtIP.setEditable(false);
        btnSetPort.setEnabled(false);
        txtPort.setEditable(false);
        btnSetName.setEnabled(false);
        txtName.setEditable(false);
        btnSetServerIP.setEnabled(false);
        txtServerIP.setEditable(false);
        btnSetServerPort.setEnabled(false);
        txtServerPort.setEditable(false);
        btnStart.setEnabled(false);

        btnSearch.setEnabled(true);
        btnShowFiles.setEnabled(true);
        btnShowNeighbours.setEnabled(true);
        rbtnRPC.setEnabled(false);
        rbtnUDP.setEnabled(false);
        
        isConnected = true;
    }
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if(isConnected){
            try{
                if(node!=null){
                    node.leave();
                    node.unregister();
                }
            }catch(IOException ex){
                JOptionPane.showMessageDialog(this,ex.getMessage());  
            }
        }
    }//GEN-LAST:event_formWindowClosed

    private void txtServerPortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtServerPortMouseClicked
        txtServerIP.setText(configs.getStringProperty("SERVER_PORT"));
    }//GEN-LAST:event_txtServerPortMouseClicked

    private void txtServerPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServerPortActionPerformed
        configs.setProperty("SERVER_PORT", txtServerPort.getText());
    }//GEN-LAST:event_txtServerPortActionPerformed

    private void btnSetServerPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetServerPortActionPerformed
        configs.setProperty("SERVER_PORT", txtServerPort.getText());
    }//GEN-LAST:event_btnSetServerPortActionPerformed

    private void txtServerIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtServerIPMouseClicked
        txtServerIP.setText(configs.getStringProperty("SERVER_IP"));
    }//GEN-LAST:event_txtServerIPMouseClicked

    private void txtServerIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServerIPActionPerformed
        configs.setProperty("SERVER_IP", txtServerIP.getText());
    }//GEN-LAST:event_txtServerIPActionPerformed

    private void btnSetServerIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetServerIPActionPerformed
        configs.setProperty("SERVER_IP", txtServerIP.getText());
    }//GEN-LAST:event_btnSetServerIPActionPerformed

    private void rbtnUDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnUDPActionPerformed
        configs.setIsUDP("true");
    }//GEN-LAST:event_rbtnUDPActionPerformed

    private void rbtnRPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnRPCActionPerformed
        configs.setIsUDP("false");
    }//GEN-LAST:event_rbtnRPCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NodeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NodeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NodeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NodeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NodeGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSetIP;
    private javax.swing.JButton btnSetName;
    private javax.swing.JButton btnSetPort;
    private javax.swing.JButton btnSetServerIP;
    private javax.swing.JButton btnSetServerPort;
    private javax.swing.JButton btnShowFiles;
    private javax.swing.JButton btnShowNeighbours;
    private javax.swing.JButton btnStart;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlShowFiles;
    private javax.swing.JRadioButton rbtnRPC;
    private javax.swing.JRadioButton rbtnUDP;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtServerIP;
    private javax.swing.JTextField txtServerPort;
    private javax.swing.JTextArea txtaDetails;
    private javax.swing.JTextArea txtaSearch;
    private javax.swing.JTextArea txtaShowFiles;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        if(o==searchResultTable){
            if(searchResultTable.getUpdatedKeyword().equals(search_keyword)){
                String result = "";
                result = searchResultTable.getResults(search_keyword)
                        .stream().map((s_result) -> s_result.toString()+"\n")
                        .reduce(result, String::concat);
                txtaSearch.setText(result);
            }
        }
    }
}

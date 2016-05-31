
public class ArchitectFrame extends javax.swing.JFrame {
	private static MainAgent myAgent2;

    public ArchitectFrame(MainAgent myAgent2) {
    	super(myAgent2.getLocalName());
    	this.myAgent2 = myAgent2;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jColorChooser1 = new javax.swing.JColorChooser();
        SubCoordLbl = new javax.swing.JLabel();
        AgentsNumberLbl = new javax.swing.JLabel();
        TFagentsNumber = new javax.swing.JTextField();
        CreateBtn = new javax.swing.JButton();
        ComboSubCoord = new javax.swing.JComboBox();
        RefreshBtn = new javax.swing.JButton();
        TFaddress = new javax.swing.JTextField();
        AddressLbl = new javax.swing.JLabel();
        TickerLbl = new javax.swing.JLabel();
        TFticker = new javax.swing.JTextField();
        AttackBtn = new javax.swing.JButton();
        WithdrawBtn = new javax.swing.JButton();
        AttackAgentsLbl2 = new javax.swing.JLabel();
        AttackAgentsLbl1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SubCoordLbl.setText("Sub co-ordinators");
        AddressLbl.setText("Enter the hostname and port to be attacked");
        TickerLbl.setText("Enter duration of ticker behaviour");
        AgentsNumberLbl.setText("Enter the number of agents to be created");
        AttackAgentsLbl2.setText("Ready agents:");

        CreateBtn.setText("Create");
        CreateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String ticker = TFticker.getText(); //ticker
            	String agentsNumber = TFagentsNumber.getText(); //no agents
            	String address = TFaddress.getText(); //host port
            	String newAddress[]=address.split(":");
        		String host=newAddress[0];
        		String port = newAddress[1];
        		String action="Create";
        		myAgent2.SendMessage(action,agentsNumber, host, port, ticker);
            }
        });
 
        RefreshBtn.setText("Refresh");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //call function from listing all sub coord
            	myAgent2.updateCombo();
            }
        });       

        AttackBtn.setText("Attack!");
        AttackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { 
            	String ticker = TFticker.getText(); //ticker
            	String agentsNumber = TFagentsNumber.getText(); //no agents
            	String address = TFaddress.getText(); //host port
            	String newAddress[]=address.split(":");
        		String host2=newAddress[0];
        		String port = newAddress[1];
        		String action="Attack";
            	myAgent2.SendMessage(action, agentsNumber, host2, port, ticker);
            }
        });

        WithdrawBtn.setText("Withdraw");
        WithdrawBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	AttackAgentsLbl1.setText("0");
            	String ticker = TFticker.getText(); //ticker
            	String agentsNumber = TFagentsNumber.getText(); //no agents
            	String address = TFaddress.getText(); //host port
            	String newAddress[]=address.split(":");
        		String host2=newAddress[0];
        		String port = newAddress[1];
        		String action="StopAttack";
            	myAgent2.SendMessage(action, agentsNumber, host2, port, ticker);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SubCoordLbl)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AddressLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AgentsNumberLbl)
                                .addComponent(TFagentsNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TFaddress, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(ComboSubCoord, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(RefreshBtn)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(AttackAgentsLbl2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AttackAgentsLbl1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AttackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TickerLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFticker, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(WithdrawBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CreateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(SubCoordLbl)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboSubCoord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RefreshBtn))
                        .addGap(24, 24, 24)
                        .addComponent(AgentsNumberLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFagentsNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(AddressLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TickerLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFticker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CreateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AttackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WithdrawBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AttackAgentsLbl2)
                    .addComponent(AttackAgentsLbl1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AttackBtn;
    private javax.swing.JButton CreateBtn;
    private javax.swing.JButton WithdrawBtn;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JComboBox ComboSubCoord;
    private javax.swing.JLabel SubCoordLbl;
    private javax.swing.JLabel AgentsNumberLbl;
    private javax.swing.JLabel AddressLbl;
    private javax.swing.JLabel TickerLbl;
    private javax.swing.JTextField TFticker;
    private javax.swing.JTextField TFagentsNumber;
    private javax.swing.JTextField TFaddress;
    private javax.swing.JLabel AttackAgentsLbl2;
    private javax.swing.JLabel AttackAgentsLbl1;
    // End of variables declaration//GEN-END:variables
    
	public void addItemDLL(String name) {
		String tmp[]=name.split("@");
		Object a = tmp[0];
		ComboSubCoord.addItem(a);
	}

	public String getSubCoordinator() {
		String name=ComboSubCoord.getSelectedItem().toString();
		//String name = tmp.split("@")[0];
		return name;
	}

	public void clearDDL() {
		ComboSubCoord.removeAllItems();
	}
	
	public void readyAgents(int number){
		String tmp = Integer.toString(number);
		AttackAgentsLbl1.setText(tmp);
	}
	
}

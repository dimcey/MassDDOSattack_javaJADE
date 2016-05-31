
import jade.core.AID;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.wrapper.*;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

public class MainAgent extends Agent {
	private AgentController ac = null;
	private AID[] subCoord;
	ArchitectFrame myGui;
	String host;
	int AgentCounter=0;
	protected void setup() {
		myGui = new ArchitectFrame(this);
		myGui.setVisible(true);
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("MainAgent");
		sd.setName("JADE-attack");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	    ReceiveMsg();
		//System.out.println("Hello World! My name is " + getAID().getLocalName());
	}

	// When the cancel button is clicked 
	protected void takeDown() {
		System.out.println("REMOVING AGEEENT");
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the GUI
		myGui.dispose();
		// Printout a dismissal message
		System.out.println(getAID().getName()+" terminating.");
	}

	//when Send button is clicked
	public void SendMessage(String action,String agentsNumber, String host2, String port, String ticker){
		addBehaviour(new OneShotBehaviour() {
        public void action() {
        	//get the names from ddl
        	String subCoordinatorName=myGui.getSubCoordinator();
        	System.out.println(subCoordinatorName+" is selected, and ready for creating sub-agents");
        	if (action.contains("Create")){
        		ACLMessage msg = new ACLMessage(16);
                msg.addReceiver(new AID(subCoordinatorName, AID.ISLOCALNAME));
                msg.setLanguage("English");
                String msgToSend=action+";"+agentsNumber+";"+host2+";"+port+";"+ticker; //getting the message from the gui the we want to send
                msg.setContent(msgToSend);
                send(msg);
                int tmp=Integer.parseInt(agentsNumber);
                AgentCounter=AgentCounter+tmp;
                myGui.readyAgents(AgentCounter);
                System.out.println("****I Sent Message to::>"+subCoordinatorName+"*****"+"\n"+
                                    "The Content of My Message is::>"+ msg.getContent());
        	}
        	if (action.contains("Attack")||action.contains("StopAttack"))
        	{
        		ACLMessage msg = new ACLMessage(16);
                msg.addReceiver(new AID(subCoordinatorName, AID.ISLOCALNAME));
                msg.setLanguage("English");
                String msgToSend=action+";"+agentsNumber+";"+host2+";"+port+";"+ticker; //getting the message from the gui the we want to send
                msg.setContent(msgToSend);
                send(msg);
                System.out.println("****I Sent Message to::>"+subCoordinatorName+"*****"+"\n"+
                                    "The Content of My Message is::>"+ msg.getContent());
        	 	}       	    	
        	}
		} );
    }
	
	//function called from the setup(). It is making the main agent able to listen for messaged addressed to him
	public void ReceiveMsg(){
		addBehaviour(new CyclicBehaviour() {
			    private String Message_Performative;
		        private String Message_Content;
		        private String SenderName;
		        public void action() {
		            ACLMessage msg = receive();
		            if(msg != null) {
		                Message_Performative = msg.getPerformative(msg.getPerformative());
		                Message_Content = msg.getContent();
		                SenderName = msg.getSender().getLocalName();
		                System.out.println(" ****The Architect received msg from ***"+ SenderName+ ":" + Message_Content);		               
		            }
		        }
		} );
    } 

	public void updateCombo() {
		addBehaviour(new OneShotBehaviour() {	 
			
			@Override
			public void action() {
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("SubCoordinator");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent, template); 
					System.out.println("Found the following agents:");
					subCoord = new AID[result.length];
					myGui.clearDDL();
					for (int i = 0; i < result.length; ++i) {
						subCoord[i] = result[i].getName();
						System.out.println(subCoord[i].getName());					
						myGui.addItemDLL(subCoord[i].getName()); //ading the subCoordinators agents to the ComboBox 
					}					
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}
		});
	}
}

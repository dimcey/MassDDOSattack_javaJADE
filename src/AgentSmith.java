
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AgentSmith extends Agent {

	Socket server = null;
	String TargetHost = "";
	int Port = 0;
	long TickInterval = 1000;
	boolean doAttack = false;
	SendRequest sr;

	protected void setup() {
		
		Object[] args = getArguments();
			TickInterval = Integer.parseInt((String) args[0]);
			TargetHost = (String) args[1];
			Port = Integer.parseInt((String) args[2]);
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Smith");
		sd.setName(getName());
		sd.addOntologies("SmithAgent");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.err.println(getLocalName()
					+ " registration with DF unsucceeded. Reason: "
					+ e.getMessage());
			doDelete();
		}
		addBehaviour(new ReceiveMessage());

	}
	
	public class SendRequest extends TickerBehaviour {
		
		long currentTick = 0;

		public SendRequest(Agent a, long period) {
			super(a, period);
			currentTick = period;
		}
		
		protected void onTick() {
			try {
				if (currentTick != TickInterval) {
					reset(TickInterval);
				}
				if (!doAttack) {
					done();
				}
				System.out.println("I am opening socket to "+TargetHost+":"+Port);
				//sumeet-test-lb-28911755.eu-west-1.elb.amazonaws.com
				//dimidansum-lb-600203420.eu-west-1.elb.amazonaws.com
				server = new Socket(TargetHost, Port);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("connection established");
		} 
	}
	
	public class ReceiveMessage extends CyclicBehaviour {

		private String Message_Content;
		private String SenderName;

		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {

				Message_Content = msg.getContent();
				SenderName = msg.getSender().getLocalName();

				System.out.println(this.myAgent+" received a Message from" + SenderName +": "+ Message_Content);
				String str = msg.getContent();
				System.out.println(str);
				if (str.contains("Attack")){
					SendRequest sr = new SendRequest(this.myAgent, 1000);
					addBehaviour(sr);
				}
				if (str.contains("StopAttack")){
					System.out.println("attack terminated");
					sr.stop();
//					try {
//						DFService.deregister(myAgent);
//					} catch (FIPAException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			} else {
				block();
			}
		}
	}

}

# MassDDOSattack_javaJADE
A project part of labaratory work withing the course "Network Programming and Distributed Systems" at Luleå University of Technology (Sweden).

# Overal Scenario
In a controlled manner the traffic load is increased towards a TCP server, which have to cope with the situation and adapt its performance dynamically.
The objective is to create a multi-agent system, which in a coordinated manner performs actions to achieve certain goal of increasing the load, and to create a scalable server architecture, which dynamically adapts to an increasing load and study its properties.

# The Agents Attack
In this step, a Multi Agent System is created using JADE. The system has one main or coordinator agent which will create soldier agents for attacking. The coordinator(The Architect) has a functionality to control the number of soldier agents (Agent Smith) to be created and parameters to be passed to soldiers. Those created soldier agents will have a ticker behavior for periodically opening a TCP socket and run fibonacci sequence of numbers on the server.
We have followed hierarchical design approach to achieve our objectives. Hence The Architect agent which manages the attacker agents will have Sub-coordinator agents which will manage its own division of the attacker agents.  
The overall architecture of this system is shown in the following diagram.
![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/Overall_Architecture.png)

# Description of the Multi Agent System that we have implemented
The overall system has two sub-systems: 

The Attacker Side --- has the main agent(The Architect), Sub-coordinator agents and soldier agents which will do the attack.

The Server Side --- accepts clients connecting to a TCP socket and handles them in multi-threaded way. It has also associated load balancer and auto-scaling group to defend the attack.

These subsystems are described in detail below.

# The Attacker side 
As it is displayed in the architecture, There is an Architect agent hosted on one Amazon Ec2 instance and there are 10 subordinate agents hosted on other ten Ec2 instances. The main reason for having these sub coordinating agents is to implement a real world scenario where the attacks come from different machines with different IP addresses and different attack intensity levels. Therefore by having these sub coordinators, the system has flexibility to control the attack parameters.
All these sub coordinators run jade platform at the boot time by firing up a script, and by that also joining to The Architect’s platform so that a communication between them can be achieved.

The Architect has a GUI interface to select in drop down list the sub coordinators and passing the list of parameters (No. of agents to be created in that sub coordinate, host, port, ticker interval ) and when “Create” is pressed, the specified number of agents (Agent Smith) are created in that specific sub coordinator and they are ready for attacking. And once we press the “Attack” button, those agents created in each sub coordinator instances will open TCP connection with the TCP server and run fibonacci on the server. Therefore the Architect creates and launches agents automatically by pressing create and attack buttons in its GUI.
![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/GUI.png)
The Figure above illustrates two scenarios of The Architect’s capabilities. The one on the left is creating 3 soldier agents (Agent Smith) in the SubCoordinator1’s Amazon instance, where the Architect is passing the parameters for the host, port and ticker to the SubCoordinator1 who will then pass them to each of his soldier agents. On the right is the scenario when the button “Attack” is pressed. The Architect sends an “attack” notification to SubCoordinator1, who will coordinate his soldier agents to open TCP connection with the already specified host and port.

The following sequence diagram illustrates the sequence of actions and message passing between the 3 critical lifelines in the architecture, 
- the main agent (aka the architect)
- the sub-coordinators (aka partners in crime)
- soldier agents (aka Agent Smiths)
![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/SequenceDiagram.png)

# The Server Side 
The server is another Amazon Ec2 instance  which accepts TCP socket connection requests from clients and handles them in multi-threaded manner. In our implementation, to emulate very intensive computation, the server computes fibonacci sequences once the client opens connection.
The TCP server which is attacked by agents from those sub-coordinators have a load-balancer and an Auto Scaling Group associated with it to cope up with the intensity of the attack. In the auto-scaling group we specified a policy to scale up and down based on the cpu utilization percentage of the server. All of the instances in the Auto Scaling Group are configured to accept the TCP connection automatically on startup. Therefore all TCP traffic will be redirected to the new instances in the group without manually opening the socket on each of them.
we can observe the increase in the number of instances that are added because of the attack which requires the scale up policy to be executed. And once we stop the attack a gradual decrease in the number of instances (scale down) is observed.

![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/LoadBalancer_data.png)

As more and more agents are creating sockets on the server and executing the Fibonacci computation on the server, the load increases - the instances get gradually created and the requests get served which have queued up in the buffer. This explains the gradual load decrease when the attack stops, there are still requests to be serviced and the load remains high till the computation is completed, after which there are instances removed according to the scale down policy applied to the auto-scaling group.

# The War of worlds - System adaptation under attack 
The decision was to enable the Architect to have to full control for the planning of the attack. This means that the Architect will decide how many soldiers he needs for certain attack. He has 10 SubCoordinators running on their own Amazon instance and the Architect can decide how much of them to use, and the main advantage of this is that a possible attack to the server can be performed from 10 different IP addresses, with various number of soldier agents in each of the SubCoordinators. Every SubCoordinator is waiting for a message from the Architect that will include the number of soldier agents to be created, and the creation for each soldier will happen in a separate thread. With all this, we are rapidly reducing the time needed to create and make 10000 soldier agents ready to attack in less than 2 minutes. But when the Architect calls for an attack, each of the soldier agents will also perform the TCP server connection in a multithreaded fashion. Moreover, with the modification of the ticker behavior the Architect will have full control on how often each of the solder agents will do the attack. This means that all of the soldier agents will open TCP connection to the server almost in the same time which will just add more burden to the server performances.

This will emulate a real attack, performing it on different machines (instances) with different ports, thus having a scenario similar to a distributed denial of service (DDOS) attack flooding the server. Thankfully in this case though, the server is equipped with an auto scaling group and a load balancer which somewhat efficiently handle the onslaught of a flood of connections upto a certain number of agents!

![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/NumberOfInstances.png)

With 10 agents created and fibonacci number sequence execution at 40, the load balancer fired up the first instance and the load went beyond 90% CPU utilization(seen from the top command). This in turn fired up another instance according to the scaling rule. But soon the load came down and one instance was able to keep up with the performance. 

With 50 agents, the same scenario was executed and the instances created were 3 and they came down, and with 100 agents attacking the server the auto scaling group created 4 instances and the load went up and gradually decreased as shown in figure above. 

The CPU utilization varied, there is no absolute number we can give for this variation as it depends on the no of instances fired by the server to handle the load. 

![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/autoScaling.png)![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/vncImage.png)

As it is shown in figures above, the cpu utilization in the server quickly went up to greater than 95% as the attack is launched and the autoscaling group creates desired instances according to the load and at 4 number of instances the cpu utilization was at 79.9%. Stopping the attack does not immediately lower the cpu utilization as the requests are scheduled and processed one by one until the last TCP request is handled by the server. After some time of stopping the attack, a gradual decrease in the cpu utilization is observed and the auto scaling group gradually decreases the number of instances that are desired to do the task.

![alt tag](https://github.com/dimcey/MassDDOSattack_javaJADE/blob/master/JADEimage.png)

On the image above is illustrate the attacker side where it is specified the number of attacking agents under each sub-coordinators. After the attack is lunched, the auto scaling group launches one instance in each 5 minutes interval. When the attack is stopped, a gradual drop in the number of instances is observed.


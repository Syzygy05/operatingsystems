import java.util.LinkedList ;
import java.util.Random ; 
import java.util.ListIterator ;

public class ProcessMgmt 
{
	public static void main(String args[]) 	
	{
		//QREADY__T = # of Processes
		//status = Keeps track of count for 25% wait list to ready state (move process from wait to ready every 4 turns)
		int QREADY__T	= 15 ;				int status = 0;		
		final int BLOCKIO	= 3 ;			final int BLOCKPAGE	= 4 ;		
		final int INTERRUPT	= 2 ;			final int COMPLETED	= 1 ; 
			

		Random random__X	= new Random();
		CPU_event event		= new CPU_event();
		
		int CPU_runtime ;		int event__X = 0;
  
		
		//List for QReady
		LinkedList<PCB> QReady = new LinkedList<>();
		//List for QWaiting
		LinkedList<PCB> QWaiting = new LinkedList<>();

		//Iterators for checking if lists are empty, etc.
		ListIterator QReadyIterator = QReady.listIterator();
		ListIterator QWaitingIterator = QWaiting.listIterator();
	
		//Current Process that system is focusing on
		PCB PCB_Running	= null ; 		
			
		//Adds the processes to the Ready List 
		for (int ii = 0; ii < QREADY__T; ii++)
			QReady.add(new PCB());

		///////////////////////////////////////////////////////////////////////
		//#080	===> end of Initialization 
		
		System.out.println("\n*****\tReady Queue\t*****");  

		for (PCB pcbLoop : QReady)		//#090 Loop that executes based on the no. of nodes in the LL
			System.out.println(pcbLoop.showPCB());

		
		
		
		//#120	change to iterate until both QReady and QWait are empty
		while ( QReadyIterator.hasNext() || QWaitingIterator.hasNext() )	
		{																	 
			QReadyIterator = QReady.listIterator();
			QWaitingIterator = QWaiting.listIterator();
			
			//Check if the Ready List has a process
			if (QReadyIterator.hasNext())
			{
				//If Process exists, set process state to running, remove from Ready List
				PCB_Running = QReady.getFirst();
				QReady.removeFirst();
				PCB_Running.set_state("Running");
			
			
				CPU_runtime	= random__X.nextInt(20) + 1 ;	//#0150 Get a random no. between 0 and 20
				
				
				System.out.println("\n*****\tRunning Process: " + PCB_Running.get_ID() + "\t*****");  
				
				//Set the CPU Used runtime and print the running process
				PCB_Running.set_CPU_used(CPU_runtime);	  
				System.out.println(PCB_Running.showPCB());
				
				
				
				//#0180 Increment the wait times for all other processes (Time Waiting + Current CPU runtime)
				for (PCB pcbLoop : QReady)
					pcbLoop.set_timeWaiting(pcbLoop.get_timeWaiting() + CPU_runtime);

				for (PCB pcbLoop : QWaiting)
					pcbLoop.set_timeWaiting(pcbLoop.get_timeWaiting() + CPU_runtime);


				//QReadyIterator = QReady.listIterator();
				//QWaitingIterator = QWaiting.listIterator();
					
				//#0190 IF statement for termination based on CPU Max
				if (CPU_runtime >= PCB_Running.get_CPU_max())
				{
					System.out.println("\n*****\tProcess Completed\t*****CPU_max reached*****");   
					PCB_Running.set_state("Terminated<>Complete");	  
					System.out.println(PCB_Running.showPCB());
					PCB_Running = null;
				}	
				else
				{
					//Enter this block if Process did not complete to termination
					//#0200 Simulate the type of Block on the Process (I/O Block, Memory Paging Block, Interrupt)
				
					event__X	= event.get_CPU_event() ;
				
					if (event__X == COMPLETED)			// 5% Termination
					{
						System.out.println("\n*****\tProcess: " + PCB_Running.get_ID() + " Completed. \t*****Process Terminated*****");
						PCB_Running.set_state("Terminated");
						System.out.println(PCB_Running.showPCB());		
					}
					else
					{
						switch (event__X)
						{
							case INTERRUPT :
							{
								//#240 Add to QReady @ Top of list
								PCB_Running.set_state("Ready");	
								QReady.addFirst(PCB_Running);
								break;
							}				
							case BLOCKPAGE :
							{	
								//#242 Add to QReady @ Middle of list	
								int count = 0;
								for (PCB items : QReady)
								{
									count++;
								}
								PCB_Running.set_state("Ready");
								QReady.add((count + 1) / 2, PCB_Running);
								break;
							}
							case BLOCKIO :
							{
								//#244 Add to QWait	
								//Resets 25% waiting rate counter(status) if it is the first PCB added to Waiting List
								if (QWaitingIterator.hasNext() == false)	
									status = 0;

								PCB_Running.set_state("Waiting");
								QWaiting.add(PCB_Running);
								PCB_Running = null;
								break;
							}
							default :
							{
								//#246 Add to QReady @ Bottom of List
								PCB_Running.set_state("Ready");
								QReady.addLast(PCB_Running);
								break;
							}
						}
					}
					//Prints the Context Switch Event Identifier if there is more than one Process in Ready List
					System.out.println("\n*****\tContext Switch\t" + event__X + "\t*****\n"); 
				}

				//#300 print out PCB's in both QReady and QWait	
				System.out.println("\n*****\tReady Queue\t*****");
				for (PCB x : QReady)
					System.out.println(x.showPCB());

				
				System.out.println("\n*****\tWaiting Queue\t*****");
				for (PCB x : QWaiting)
					System.out.println(x.showPCB());
			}
			else
			{
				//QReady is Empty. Used to show no Ready Processes available for CPU
				System.out.println("\n*****QReady Empty*****");
			}
			//END OF IF-ELSE STATEMENT CHECKING FOR EMPTY QREADY


			System.out.print("\n\n");
			
			//Checks if QWaiting has a process and can be released to Ready state
			if (status == 4 && QWaitingIterator.hasNext())
			{
				PCB q = QWaiting.getFirst();
				System.out.println(">>>>>I/O Completed: " + q.get_ID() + "<<<<<");
				QReady.addFirst(QWaiting.getFirst());
				QWaiting.removeFirst();
				status = 0;
			}

			//Checks if both QReady and QWaiting are empty, if so, end program
			if ((QReadyIterator.hasNext() == false) && (QWaitingIterator.hasNext() == false))
			{
				System.out.println("All Processes Completed!");
				break;
			}
			
			status++;
			System.out.println("----------------------------Process Complete/Loop Restart----------------------------");
		}	
	}
}
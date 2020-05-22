import java.util.LinkedList;

public class OpenMem 
{

	public OpenMem ()
	{}
	
	public boolean findOpenMem	(PCB PCB_Ready, LinkedList<PCB> QMemOpen, LinkedList<PCB> QMemUsed)
	{
		boolean memFound__B = false ;
			
		int memNeed = PCB_Ready.get_Limit() ;	//@0100
		System.out.println("\t==>@0100 OpenMem\t looking for " + PCB_Ready.get_Limit() + " memory");
		
		for (int ii = 0; ii < QMemOpen.size(); ii ++)
		{
			PCB memOpen = QMemOpen.get(ii) ;	//@0120
			if (memOpen.get_Limit() > memNeed )	//@0200
			{
				memFound__B = true;
				int base0 = memOpen.get_memBase() ;
				
				//@0220	split the open memory	@@ QMemOpen @@

				
				//@0240	replace the open memory

					
				//@0260	allocate the used memory	@@ QMemUsed @@
					
				//@0280	set the base for the process
					
				//@0300	push the used memory
					
				//System.out.printf("@0300 Used\t%s\n"	,memUsed.showPCB());
					
				break ;	// exit out of the FOR loop for memory
			}
		}
		return memFound__B ;
	}
}
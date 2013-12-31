package cores;

public class Waitstate {

	private static final int[] WAIT_TABLE = { 4, 3, 2, 8 };

	private int state0First, state0Second;
	private int state1First, state1Second;
	private int state2First, state2Second;
	private int stateSRAM, stateWRAM;
	
	private int waitCNT, configWRAM;
	private int postBoot;
	
	private boolean prefetch, nonSequential; 

	public Waitstate() {
		state0First = state1First = state2First = 4;
		state0Second = 2;
		state1Second = 4;
		state2Second = 8;
		stateSRAM = 4;
		configWRAM = 0xD000020; //4000800h - 32bit - Undocumented - Internal Memory Control (R/W)
		stateWRAM = 3;
	}
	
	public int getWaitCNT() {
		return waitCNT;
	}
	
	public void setWaitCNT(int i) {
		waitCNT = i & 0x5FFF; //Force Bit 13, 15, 16-31 to CLEAR
		stateSRAM = WAIT_TABLE[i & 0x3];
		
		state0First = WAIT_TABLE[(i >>> 2) & 0x3];
		state0Second = ((i & 0x10) == 0x10) ? 1 : 2;
		state1First = WAIT_TABLE[(i >>> 5) & 0x3];
		state1Second = ((i & 0x80) == 0x80) ? 1 : 4;
		state2First = WAIT_TABLE[(i >>> 8) & 0x3];
		state2Second = ((i & 0x400) == 0x400) ? 1 : 8;
		prefetch = ((i & 0x4000) == 0x4000);
	}
	
	public int getPostBoot() {
		return postBoot;
	}
	
	public void setPostBoot(int i) {
		postBoot = i;
	}
	
}
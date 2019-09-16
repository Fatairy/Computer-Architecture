
public class FetchStage {
	public int pc = 0;
	
	public int instruction = 0;
	public Memory instructionMem = new Memory(0x1000000);
	
	public void run(int nextPc) {
		instruction = instructionMem.readWord(nextPc);
		pc = nextPc + 4;

		System.out.println("FETCH STAGE pc=" + nextPc);
		System.out.println("instruction=" + Integer.toBinaryString(instruction));
		System.out.println("========================");
	}
}

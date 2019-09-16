
public class WriteBackStage {
	public int writeReg;
	public int writeData;
	
	public void run(ControlUnit controlUnit, int memOutput, int pc,
			int destination) {
		if (controlUnit.jal) {
			writeReg = 31;
			writeData = pc;
		} else {
			writeReg = destination;
			writeData = memOutput;
		}

		System.out.println("WRITE BACK STAGE pc=" + (pc-4));
		System.out.println("writeReg=" + writeReg + " writeData=" + writeData);
		System.out.println("========================");
	}
}

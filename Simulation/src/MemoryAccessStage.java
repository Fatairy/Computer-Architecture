
public class MemoryAccessStage {
	public ControlUnit controlUnit;
	public Memory dataMemory = new Memory(0x1000000);
	public int memOutput;
	public int destination;
	public int pc;
	
	public void run(ControlUnit controlUnit, ALU alu, RegisterFile registerFile, int destination, int pc) {
		if (controlUnit.load) {
			memOutput = dataMemory.readWord(alu.output);
		} else {
			memOutput = alu.output;
		}
		
		if (controlUnit.store) {
			if (controlUnit.storeWidth == 0b01) {
				dataMemory.writeByte(registerFile.read1Data, (byte)alu.output);
			} else if (controlUnit.storeWidth == 0b10) {
				dataMemory.writeHalfword(registerFile.read1Data, (short)alu.output);
			} else {
				dataMemory.writeWord(registerFile.read1Data, alu.output);
			}
		}
		
		this.controlUnit = controlUnit;
		this.destination = destination;
		this.pc = pc;

		System.out.println("MEMORY ACCESS STAGE pc=" + (pc-4));
		System.out.println("memOutput=" + memOutput);
		System.out.println("========================");
	}
}

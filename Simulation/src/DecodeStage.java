
public class DecodeStage {
	public ControlUnit controlUnit = new ControlUnit();
	public RegisterFile registerFile = new RegisterFile();
	public boolean type, immediate;
	public int opcode, destination, operand1, operand2mux, imm16, imm26;
	public int pc;
	
	public void run(int instruction, int pc) {
		type = (instruction & (1<<31)) != 0;
		immediate = (instruction & (1<<30)) != 0;
		
		opcode = (instruction >>> 26) & 0xF;
		destination = (instruction >>> 21) & 0x1F;
		operand1 = (instruction >>> 16) & 0x1F;
		int operand2 = (instruction >>> 11) & 0x1F;
		imm16 = instruction & 0xFFFF;
		imm26 = instruction & 0x1FFFFFF;
		
		controlUnit.input(type, immediate, opcode);
		
		registerFile.input(false, operand1, 
				opcode >= 0b1000 ? destination : operand2, 0, 0);
		if (immediate && opcode < 0b1000) {
			operand2mux = imm16;
		} else {
			operand2mux = registerFile.read2Data;
		}
		
		this.pc = pc;

		System.out.println("DECODE STAGE pc=" + (pc-4));
		System.out.println("type=" + (type?1:0) + " immediate=" + (immediate?1:0));
		System.out.println("opcode=" + Integer.toBinaryString(opcode) +
				" destination=" + Integer.toBinaryString(destination) +
				" operand1=" + Integer.toBinaryString(operand1) +
				" operand2mux=" + Integer.toBinaryString(operand2mux) +
				" imm16=" + Integer.toBinaryString(imm16) +
				" imm26=" + Integer.toBinaryString(imm26));
		System.out.println("control signals:");
		System.out.println("storeWidth=" + Integer.toBinaryString(controlUnit.storeWidth) +
				" aluCtrl=" + Integer.toBinaryString(controlUnit.aluCtrl) +
				" branchEq=" + (controlUnit.branchEq?1:0) +
				" branchNe=" + (controlUnit.branchNe?1:0) +
				" load=" + (controlUnit.load?1:0) +
				" store=" + (controlUnit.store?1:0) +
				" jal=" + (controlUnit.jal?1:0) +
				" jr=" + (controlUnit.jr?1:0) +
				" writeReg=" + (controlUnit.writeReg?1:0));
		System.out.println("========================");
	}
}

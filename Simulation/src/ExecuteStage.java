
public class ExecuteStage {
	public boolean reached = false;

	public RegisterFile registerFile;
	public ControlUnit controlUnit;
	public ALU alu = new ALU();
	public boolean branch;
	public int pc;
	public int nextPc;
	public int destination;
	public boolean changePc;
	
	public void run(ControlUnit controlUnit, RegisterFile registerFile,
			boolean type, boolean jr, int pc,
			int opcode, int destination, int operand1, int operand2mux, int imm16, int imm26) {
		alu.input(registerFile.read1Data, operand2mux, controlUnit.aluCtrl);
		
		if ((alu.zero && controlUnit.branchEq) || (!alu.zero && controlUnit.branchNe)) {
			branch = true;
		} else {
			branch = false;
		}
		
		if (!jr && !type && !branch) {
			nextPc = pc;
			changePc = false;
		} else if (!jr && !type && branch) {
			nextPc = pc + (imm16 << 2);
			changePc = true;
		} else if (!jr && type && !branch) {
			nextPc = imm26;
			changePc = true;
		} else if (jr && !type && !branch) {
			nextPc = registerFile.read1Data;
			changePc = true;
		}
		
		reached = true;
		this.controlUnit = controlUnit;
		this.registerFile = registerFile;
		this.destination = destination;
		this.pc = pc;

		System.out.println("EXECUTE STAGE pc=" + (pc-4));
		System.out.println("aluOutput=" + alu.output + " branch=" + (branch?1:0));
		System.out.println("========================");
	}
}

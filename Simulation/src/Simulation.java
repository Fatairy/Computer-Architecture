
public class Simulation {
	FetchStage fetchStage;
	DecodeStage decodeStage;
	ExecuteStage executeStage;
	MemoryAccessStage memoryAccessStage;
	WriteBackStage writeBackStage;
	
	int cycles = 0;
	
	public Simulation() {
		fetchStage = new FetchStage();
		decodeStage = new DecodeStage();
		executeStage = new ExecuteStage();
		memoryAccessStage = new MemoryAccessStage();
		writeBackStage = new WriteBackStage();
	}
	
	public void run() {
		if (cycles >= 4) {
			//System.out.println("write back");
			writeBackStage.run(memoryAccessStage.controlUnit, memoryAccessStage.memOutput,
					memoryAccessStage.pc, memoryAccessStage.destination);
			if (memoryAccessStage.controlUnit.writeReg) {
				decodeStage.registerFile.writeReg(writeBackStage.writeReg, writeBackStage.writeData);
			}
		}
		if (cycles >= 3) {
			//System.out.println("mem access");
			memoryAccessStage.run(executeStage.controlUnit, executeStage.alu, executeStage.registerFile,
					executeStage.destination, executeStage.pc);
		}
		if (cycles >= 2) {
			//System.out.println("execute " + (decodeStage.pc - 4));
			executeStage.run(decodeStage.controlUnit.clone(), decodeStage.registerFile.clone(),
					decodeStage.type, decodeStage.controlUnit.jr, decodeStage.pc,
					decodeStage.opcode, decodeStage.destination, decodeStage.operand1,
					decodeStage.operand2mux, decodeStage.imm16, decodeStage.imm26);
		}
		if (cycles >= 1) {
			//System.out.println("decode " + (fetchStage.pc - 4));
			decodeStage.run(fetchStage.instruction, fetchStage.pc);
		}
		//System.out.println("fetch " + fetchStage.pc);
		fetchStage.run(executeStage.changePc ? executeStage.nextPc : fetchStage.pc);
		cycles++;
	}
	
	public static void main(String[] args) {
		Simulation simulation = new Simulation();

		simulation.fetchStage.instructionMem.writeWord(0,  0b00000001000000100001100000000000); //add
		simulation.fetchStage.instructionMem.writeWord(4,  0b00000101001000100001100000000000); //sub
		simulation.fetchStage.instructionMem.writeWord(8,  0b00001001010000100001100000000000); //sll
		simulation.fetchStage.instructionMem.writeWord(12, 0b00001101011000100001100000000000); //slr
		simulation.fetchStage.instructionMem.writeWord(16, 0b01010001100000100000000000000111); //and imm
		simulation.fetchStage.instructionMem.writeWord(20, 0b01010101101000100000000000000100); //or imm
		simulation.fetchStage.instructionMem.writeWord(24, 0b01011001110000100000000000000111); //xor imm
		simulation.fetchStage.instructionMem.writeWord(28, 0b00011101111000110001000000000000); //cmp

		simulation.fetchStage.instructionMem.writeWord(32, 0b01000010000000000000000001000000); //add imm
		simulation.fetchStage.instructionMem.writeWord(48, 0b01100100000100000000000000000000); //jr

		simulation.fetchStage.instructionMem.writeWord(64, 0b01101000011000100000000000000100); //beq
		simulation.fetchStage.instructionMem.writeWord(68, 0b01101100011000100000000000000010); //bne

		simulation.fetchStage.instructionMem.writeWord(80, 0b01110100011000000000000000000000); //sw
		simulation.fetchStage.instructionMem.writeWord(92, 0b01110010001000000000000000000000); //lw

		simulation.fetchStage.instructionMem.writeWord(96, 0b10000000000000000000000100000000); //j
		simulation.fetchStage.instructionMem.writeWord(256, 0b10000100000000000000001000000000); //jal
		
		for (int i = 0; i < 100; i++) {
			simulation.run();
			
			System.out.println("--REGISTER FILE--");
			for (int reg = 0; reg < 32; reg++) {
				System.out.print("@" + reg + "=" + simulation.decodeStage.registerFile.readReg(reg) + "\t");
				if (reg % 8 == 7) {
					System.out.println();
				}
			}
			System.out.println("--DATA MEMORY--");
			for (int addr = 0; addr < simulation.memoryAccessStage.dataMemory.data.length; addr += 4) {
				int data = simulation.memoryAccessStage.dataMemory.readWord(addr);
				if (data != 0) {
					System.out.println("mem[" + addr + "]=" + data);
				}
			}
			System.out.println("=============================================");
			System.out.println("=============================================");
		}
	}
}

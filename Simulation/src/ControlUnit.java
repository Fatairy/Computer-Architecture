
public class ControlUnit {
	public int storeWidth;
	public int aluCtrl;
	public boolean branchEq;
	public boolean branchNe;
	public boolean load;
	public boolean store;
	public boolean jal;
	public boolean jr;
	public boolean writeReg;
	
	public ControlUnit clone() {
		ControlUnit cloned = new ControlUnit();
		cloned.storeWidth = storeWidth;
		cloned.aluCtrl = aluCtrl;
		cloned.branchEq = branchEq;
		cloned.branchNe = branchNe;
		cloned.load = load;
		cloned.store = store;
		cloned.jal = jal;
		cloned.jr = jr;
		cloned.writeReg = writeReg;
		return cloned;
	}
	
	public void input(boolean type, boolean immediate, int opcode) {
		storeWidth = 0;
		aluCtrl = 0;
		branchEq = false;
		branchNe = false;
		load = false;
		store = false;
		jal = false;
		jr = false;
		writeReg = false;
		
		if (!type) {
			//26-bit imm instructions
			switch (opcode) {
			case 0b0000: //add
				aluCtrl = ALU.CTRL_ADD;
				writeReg = true;
				break;
			case 0b0001: //sub
				aluCtrl = ALU.CTRL_SUB;
				writeReg = true;
				break;
			case 0b0010: //sll
				aluCtrl = ALU.CTRL_SLL;
				writeReg = true;
				break;
			case 0b0011: //slr
				aluCtrl = ALU.CTRL_SLR;
				writeReg = true;
				break;
			case 0b0100: //and
				aluCtrl = ALU.CTRL_AND;
				writeReg = true;
				break;
			case 0b0101: //or
				aluCtrl = ALU.CTRL_OR;
				writeReg = true;
				break;
			case 0b0110: //xor
				aluCtrl = ALU.CTRL_XOR;
				writeReg = true;
				break;
			case 0b0111: //cmp
				aluCtrl = ALU.CTRL_CMP;
				writeReg = true;
				break;
			case 0b1001: //jr
				jr = true;
				break;
			case 0b1010: //beq
				aluCtrl = ALU.CTRL_SUB;
				branchEq = true;
				break;
			case 0b1011: //bne
				aluCtrl = ALU.CTRL_SUB;
				branchNe = true;
				break;
			case 0b1100: //lw
				load = true;
				writeReg = true;
				aluCtrl = ALU.CTRL_PASSOP2;
				break;
			case 0b1101: //sw
				store = true;
				storeWidth = 0b11;
				aluCtrl = ALU.CTRL_PASSOP2;
				break;
			case 0b1110: //sh
				store = true;
				storeWidth = 0b10;
				aluCtrl = ALU.CTRL_PASSOP2;
				break;
			case 0b1111: //sb
				store = true;
				storeWidth = 0b01;
				aluCtrl = ALU.CTRL_PASSOP2;
				break;
			}
		} else {
			//2 operand instructions
			if (opcode == 0b0001) {
				jal = true;
				writeReg = true;
			}
		}
	}
}

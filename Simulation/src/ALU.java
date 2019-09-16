
public class ALU {
	public static final int CTRL_ADD = 0;
	public static final int CTRL_SUB = 1;
	public static final int CTRL_SLL = 2;
	public static final int CTRL_SLR = 3;
	public static final int CTRL_AND = 4;
	public static final int CTRL_OR = 5;
	public static final int CTRL_XOR = 6;
	public static final int CTRL_CMP = 7;
	public static final int CTRL_PASSOP2 = 8;
	
	public boolean zero;
	public int output;
	
	public ALU clone() {
		ALU cloned = new ALU();
		cloned.zero = zero;
		cloned.output = output;
		return cloned;
	}
	
	public void input(int op1, int op2, int ctrl) {
		switch (ctrl) {
		case CTRL_ADD:
			output = op1 + op2;
			break;
		case CTRL_SUB:
			output = op1 - op2;
			break;
		case CTRL_SLL:
			output = op1 << op2;
			break;
		case CTRL_SLR:
			output = op1 >> op2;
			break;
		case CTRL_AND:
			output = op1 & op2;
			break;
		case CTRL_OR:
			output = op1 | op2;
			break;
		case CTRL_XOR:
			output = op1 ^ op2;
			break;
		case CTRL_CMP:
			if (op1 < op2) output = 0b01;
			else if (op1 > op2) output = 0b10;
			else if (op1 == op2) output = 0b11;
			break;
		case CTRL_PASSOP2:
			output = op2;
			break;
		}
		
		zero = output == 0;
	}
}

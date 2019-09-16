
public class RegisterFile {
	public int[] registers = new int[32];
	
	public int read1Data;
	public int read2Data;
	
	public RegisterFile clone() {
		RegisterFile cloned = new RegisterFile();
		for (int i = 0; i < 32; i++) {
			cloned.registers[i] = registers[i];
		}
		cloned.read1Data = read1Data;
		cloned.read2Data = read2Data;
		return cloned;
	}
	
	public void input(boolean writeCtrl, int read1Code, int read2Code, int writeCode, int writeData) {
		read1Data = readReg(read1Code);
		read2Data = readReg(read2Code);
		if (writeCtrl) {
			writeReg(writeCode, writeData);
		}
	}
	
	public int readReg(int code) {
		if (code == 0) return 0;
		if (code == 1) return 1;
		if (code == 2) return 2;
		if (code == 3) return 3;
		if (code >= 32) return 0xFFFFFFFF;
		return registers[code];
	}
	
	public void writeReg(int code, int val) {
		if (code >= 4 && code <= 31) {
			registers[code] = val;
		}
	}
}

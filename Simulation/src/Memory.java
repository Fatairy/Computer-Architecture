
public class Memory {
	public byte data[];
	
	public Memory(int size) {
		data = new byte[size];
	}
	
	public int readWord(int address) {
		return (data[address] & 0xFF) | ((data[address+1] & 0xFF) << 8) | ((data[address+2] & 0xFF) << 16) | ((data[address+3] & 0xFF) << 24);
	}
	
	public void writeWord(int address, int val) {
		data[address] = (byte)(val & 0xFF);
		data[address+1] = (byte)((val >> 8) & 0xFF);
		data[address+2] = (byte)((val >> 16) & 0xFF);
		data[address+3] = (byte)((val >> 24) & 0xFF);
	}
	
	public void writeHalfword(int address, short val) {
		data[address] = (byte)(val & 0xFF);
		data[address+1] = (byte)((val >> 8) & 0xFF);
	}
	
	public void writeByte(int address, byte val) {
		data[address] = val;
	}
}

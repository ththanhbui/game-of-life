package gol;

public class PackedWorld extends World implements Cloneable {
	private long mWorld;


	public PackedWorld(String format) throws PatternFormatException {
		this(new Pattern(format));
	}
	
	
	public PackedWorld(Pattern p) throws PatternFormatException {
		super(p);
		p.initialise(this);
	}
	
	public PackedWorld(PackedWorld pw) {
		super(pw);
		this.mWorld = pw.mWorld;
	}
	
	public Object clone() throws CloneNotSupportedException{
		PackedWorld copy = (PackedWorld) super.clone();
		return copy;
	}

	@Override
	public boolean getCell(int col, int row) {
		long check = 1L;
		check &= (mWorld >> (row * 8 + col));
		return (check == 1);
	}

	@Override
	public void setCell(int col, int row, boolean val) {
		long check = 1L << (row * 8 + col);
		if (val) {
			mWorld |= check;
		} else {
			mWorld &= ~check;
		}
	}

	@Override
	protected void nextGenerationImpl() {
		boolean[][] temp = new boolean[getHeight()][getWidth()];
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				temp[row][col] = computeCell(col, row);
			}
		}
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				setCell(col, row, temp[row][col]);
			}
		}
	}
}

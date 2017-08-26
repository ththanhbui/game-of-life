package gol;

import java.util.Arrays;

public class ArrayWorld extends World implements Cloneable {
	private boolean[][] mWorld;
	private boolean[] mDeadRow;

	public ArrayWorld(String serial) throws PatternFormatException {
		this(new Pattern(serial));
	}

	public ArrayWorld(Pattern p) throws PatternFormatException { // normal
																	// constructor
		super(p);
		mWorld = new boolean[getHeight()][getWidth()];
		// Setup mDeadRow
		mDeadRow = new boolean[getWidth()];
		for (int c = 0; c < getWidth(); c++) mDeadRow[c] = false;
		// Initialize
		p.initialise(this);
		// Set dead rows point to mDeadRow
		
/*		for (int r = 0; r < mWorld.length; r++) {
			boolean allDead = true;
			for (int c = 0; c < mWorld.length; c++) {
				if (mWorld[r][c]) allDead = false;
			}
			if (allDead) mWorld[r] = mDeadRow;
		}*/
		
		for (int r = 0; r < mWorld.length; r++) {
			if (Arrays.equals(mWorld[r], mDeadRow)) {
				mWorld[r] = mDeadRow;
			}
		}
	}

	public ArrayWorld(ArrayWorld aw) { // copy constructor
		super(aw);
		this.mWorld = new boolean[aw.getHeight()][aw.getWidth()];
		this.mDeadRow = aw.mDeadRow;
		for (int r = 0; r < aw.getHeight(); r++) {
			for (int c = 0; c < aw.getWidth(); c++) {
				this.mWorld[r][c] = aw.mWorld[r][c];
			}
		}
		
		for (int r = 0; r < mWorld.length; r++) {
			if (Arrays.equals(mWorld[r], mDeadRow)) {
				mWorld[r] = mDeadRow;
			}
		}

	}

	public Object clone() throws CloneNotSupportedException { // cloning
		ArrayWorld copy = (ArrayWorld) super.clone();
		copy.mWorld = new boolean[this.getHeight()][this.getWidth()];
		for (int i = 0; i < this.getHeight(); i++) {
			System.arraycopy(this.mWorld[i], 0, copy.mWorld[i], 0, this.mWorld[i].length);
		}

		/*
		 * for (int r = 0; r < this.getHeight(); r++) { for (int c = 0; c <
		 * this.getWidth(); c++) { copy.mWorld[r][c] = this.mWorld[r][c]; } }
		 */

// 			Set dead rows point to mDeadRow
//		for (int r = 0; r < mWorld.length; r++) {
//			if (Arrays.equals(copy.mWorld[r], mDeadRow)) {
//				copy.mWorld[r] = mDeadRow;
//			}
//		}
		return copy;

	}

	@Override
	public boolean getCell(int col, int row) {
		if (row < 0 || row >= getHeight())
			return false;
		if (col < 0 || col >= getWidth())
			return false;

		return mWorld[row][col];
	}

	@Override
	public void setCell(int col, int row, boolean value) {
		if (col >= 0 && row >= 0 && col < getWidth() && row < getHeight()) {
			mWorld[row][col] = value;
		}
	}

	@Override
	public void nextGenerationImpl() {
		boolean[][] nextGeneration = new boolean[mWorld.length][];
		for (int r = 0; r < mWorld.length; ++r) {
			nextGeneration[r] = new boolean[mWorld[r].length];
			for (int c = 0; c < mWorld[r].length; ++c) {
				boolean nextCell = computeCell(c, r);
				nextGeneration[r][c] = nextCell;
			}
		}
		mWorld = nextGeneration;
		for (int r = 0; r < mWorld.length; r++) {
			if (Arrays.equals(mWorld[r], mDeadRow)) {
				mWorld[r] = mDeadRow;
			}
		}
	}

}

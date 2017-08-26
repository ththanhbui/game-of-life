package gol;

public abstract class World implements Cloneable {
	private int mGeneration;
	private Pattern mPattern;
		
	public World(Pattern p) throws PatternFormatException {
		mGeneration = 0;
		mPattern = p;
	}
	
	public World(String pattern) throws PatternFormatException {
		mGeneration = 0;
		mPattern = new Pattern(pattern);
	}
	
	public World(World w) { // copy constructor
		mGeneration = w.mGeneration;
		mPattern = w.mPattern;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	public abstract boolean getCell(int col, int row);

	public abstract void setCell(int col, int row, boolean val);

	protected abstract void nextGenerationImpl();

	protected Pattern getPattern() {
		return mPattern;
	}

	public int getWidth() {
		return mPattern.getWidth();
	}

	public int getHeight() {
		return mPattern.getHeight();
	}

	public int getGenerationCount() {
		return mGeneration;
	}
	
	protected void incrementGenerationCount() {
		mGeneration++;
	}
	
	public void nextGeneration() {
		nextGenerationImpl();
		mGeneration++;		
	}

	protected int countNeighbours(int col, int row) {
		int neighbourCount = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1)
					continue;
				if (col - 1 + i > getWidth() - 1 || col - 1 + i < 0 || row - 1 + j > getHeight() - 1 || row - 1 + j < 0)
					continue;
				neighbourCount += getCell(col - 1 + i, row - 1 + j) ? 1 : 0;
			}
		return neighbourCount;
	}

	protected boolean computeCell(int col, int row) {
		boolean liveCell = getCell(col, row);
		int neighbours = countNeighbours(col, row);
		boolean nextCell = false;

		if (liveCell) {
			if (neighbours == 2 || neighbours == 3)
				nextCell = true;
			else if (neighbours > 3)
				nextCell = false;
		} else {
			if (neighbours == 3)
				nextCell = true;
		}

		return nextCell;
	}
}

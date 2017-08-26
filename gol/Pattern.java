package gol;

public class Pattern implements Comparable<Pattern> { 
	private String mName;
	private String mAuthor;
	private int mWidth;
	private int mHeight;
	private int mStartCol;
	private int mStartRow;
	private String mCells;
	
	@Override
	public int compareTo(Pattern o) {
		return mName.compareTo(o.getName());
	}
	
	public Pattern(String format) throws PatternFormatException { // Format:
																	// NAME:AUTHOR:WIDTH:HEIGHT:STARTUPPERCOL:STARTUPPERROW:CELLS
		String[] info = format.split(":");
		if (info.length != 7)
			throw new PatternFormatException(
					"Invalid pattern format: Incorrect number of fields in pattern" + "(found " + info.length + " .)");

		mName = info[0];
		mAuthor = info[1];

		try {
			mWidth = Integer.parseInt(info[2]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Could not interpret the Width field as a number('" + info[2] + "' given.)");
		}

		try {
			mHeight = Integer.parseInt(info[3]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Could not interpret the Height field as a number ('" + info[3] + "' given.)");
		}

		try {
			mStartCol = Integer.parseInt(info[4]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Could not interpret the StartX field as a number ('" + info[4] + "' given.)");
		}

		try {
			mStartRow = Integer.parseInt(info[5]);
		} catch (NumberFormatException e) {
			throw new PatternFormatException(
					"Could not interpret the StartY field as a number ('" + info[5] + "' given.)");
		}

		mCells = info[6];
	}

	public void initialise(World world) throws PatternFormatException {
		String[] pattern = mCells.split(" ");

	/*	for (String s : pattern) {
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				throw new PatternFormatException("Malformed pattern '" + mCells + "'.");
			}
		}
*/
		for (int i = 0; i < pattern.length; i++) {
			for (int j = 0; j < pattern[i].length(); j++) {
				world.setCell(mStartCol + j, mStartRow + i, pattern[i].charAt(j) == '1');
			}
		}
	}

	public String getName() {
		return mName;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public int getStartCol() {
		return mStartCol;
	}

	public int getStartRow() {
		return mStartRow;
	}

	public String getCell() {
		return mCells;
	}
	
	@Override
	public String toString() {
		return (mName + " (" + mAuthor + ")");
		
	}

	public static void main(String[] args) {
		try {
			Pattern p = new Pattern(args[0]);
			System.out.println("Name: " + p.getName());
			System.out.println("Author: " + p.getAuthor());
			System.out.println("Width: " + p.getWidth());
			System.out.println("Height :" + p.getHeight());
			System.out.println("StartCol: " + p.getStartCol());
			System.out.println("StartRow: " + p.getStartRow());
			System.out.println("Pattern: " + p.getCell());
		}

		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please specify a pattern.");
		} catch (PatternFormatException e) {
			System.out.println(e.getMessage());
		}
	}

}
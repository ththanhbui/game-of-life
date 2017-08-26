package gol;

import java.io.*;
import java.util.*;

public class GameOfLife {
	private PatternStore mStore;
	private World mWorld;
	private ArrayList<World> mCachedWorlds;

	public GameOfLife(PatternStore ps) {
		mStore = ps;
	}
	
	private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
		if (useCloning) {
			World copy = (World) mWorld.clone();
			return copy;
		} else {
			if (mWorld instanceof ArrayWorld) {
				World copy = new ArrayWorld((ArrayWorld) mWorld);
				return copy;
			} else {
				World copy = new PackedWorld((PackedWorld) mWorld);
				return copy;
			}
		}

	}

	public void print() {
		System.out.println("- " + Integer.toString(mWorld.getGenerationCount()));
		for (int row = 0; row < mWorld.getHeight(); row++) {
			for (int col = 0; col < mWorld.getWidth(); col++) {
				System.out.print(mWorld.getCell(col, row) ? "#" : "_");
			}
			System.out.println();
		}
	}

	public void play() throws IOException, CloneNotSupportedException {

		String response = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Please select a pattern to play (l to list):");
		while (!response.equals("q")) {
			response = in.readLine();
			if (response.equals("b")) {
				if (mWorld.getGenerationCount() != 0) {
					mWorld = mCachedWorlds.get(mWorld.getGenerationCount() - 1);
					print();
				} else {
					print();
				}
			} else if (response.startsWith("f")) {
				if (mWorld == null) {
					System.out.println("Please select a pattern to play (l to list):");
				} else if (mWorld.getGenerationCount() >= mCachedWorlds.size()-1) {
					mWorld = copyWorld(true);
					mWorld.nextGeneration();
					mCachedWorlds.add(mWorld);
					print();
	
				} else {
					mWorld = mCachedWorlds.get(mWorld.getGenerationCount() + 1);
					print();
				}
			} else if (response.equals("l")) {
				List<Pattern> names = mStore.getPatternsNameSorted();
				int i = 0;
				for (Pattern p : names) {
					System.out.println(i + " " + p.getName() + "  (" + p.getAuthor() + ")");
					i++;
				}
			} else if (response.startsWith("p")) {
				List<Pattern> names = mStore.getPatternsNameSorted();
				String[] input = response.split(" ");
				int patternIndex = 0;
				try {
					patternIndex = Integer.parseInt(input[1]);
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid number");
				}
				Pattern p = names.get(patternIndex);
				mCachedWorlds = new ArrayList<>();
				if (p.getWidth() * p.getHeight() > 64) {
					try {
						mWorld = new ArrayWorld(p);

					} catch (PatternFormatException e) {
						System.out.println(e.getMessage());
					}
				} else {
					try {
						mWorld = new PackedWorld(p);
					} catch (PatternFormatException e) {
						System.out.println(e.getMessage());
					}
				}
				mCachedWorlds.add(mWorld);
				print();
			}
		}

	}
}
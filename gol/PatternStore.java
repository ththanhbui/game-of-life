package gol;

import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
	private List<Pattern> mPatterns = new LinkedList<>();
	private Map<String, List<Pattern>> mMapAuths = new HashMap<>();
	private Map<String, Pattern> mMapName = new HashMap<>();

	public PatternStore(String source) throws IOException {
		if (source.startsWith("http://")) {
			loadFromURL(source);
		} else {
			loadFromDisk(source);
		}
	}

	public PatternStore(Reader source) throws IOException {
		load(source);
	}

	private void load(Reader r) throws IOException {
		BufferedReader b = new BufferedReader(r);
		String line = b.readLine();
		int lineNumber = 1;
		while (line != null) {
			Pattern p = null;
			try {
				p = new Pattern(line);
			} catch (PatternFormatException e) { System.out.println("Error in line " + Integer.toString(lineNumber));}
			
			mPatterns.add(p); // add p to mPatterns
			
			if (mMapAuths.containsKey(p.getAuthor())) { // if the Author's already exist, add
				mMapAuths.get(p.getAuthor()).add(p);
			} else {
				mMapAuths.put(p.getAuthor(),new LinkedList<>()); // create new entry, then add
				mMapAuths.get(p.getAuthor()).add(p);
			}
			
			mMapName.put(p.getName(), p);
			lineNumber++;
			line = b.readLine();
		}
	}

	private void loadFromURL(String url) throws IOException {
		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();
		Reader r = new java.io.InputStreamReader(conn.getInputStream());
		BufferedReader b = new BufferedReader(r);
		this.load(b);
	}

	private void loadFromDisk(String filename) throws IOException {
		Reader r = new FileReader(filename);
		BufferedReader b = new BufferedReader(r);
		this.load(b);
	}

	public List<Pattern> getPatternsNameSorted() {
		List<Pattern> copy = new LinkedList<>(mPatterns);
		Collections.sort(copy);
		return copy;
	}

	public List<Pattern> getPatternsAuthorSorted() {
		List<Pattern> copy = new LinkedList<>(mPatterns);
		Collections.sort(copy, new Comparator<Pattern>() {
			   public int compare(Pattern p1, Pattern p2) {
			      int result = (p1.getAuthor()).compareTo(p2.getAuthor());
				  if (result == 0) {
			    	 result = p1.getName().compareTo(p2.getName());
			      }
			      return result;
			   }
			 });
		return copy;
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
		List<Pattern> copy = new LinkedList<>();
		if (mMapAuths.containsKey(author)) {
			for (Pattern p : mMapAuths.get(author)) {
				copy.add(p);
			}
		} else { throw new PatternNotFound("Authors not found");}
		Collections.sort(copy);
		return copy;
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		if (mMapName.containsKey(name)) {
			return mMapName.get(name);
		} else { throw new PatternNotFound("Pattern not found");}
	}

	public List<String> getPatternAuthors() {
		List<String> list = new LinkedList<>();
		for (Pattern p : mPatterns) {
			if (list.contains(p.getAuthor())) continue;
			else { list.add(p.getAuthor()); }
		}
		return list;
	}

	public List<String> getPatternNames() {
		List<String> list = new LinkedList<>();
		for (Pattern s : mPatterns) {
			list.add(s.getName());
		}
		Collections.sort(list, new Comparator<String>() {
			   public int compare(String p1, String p2) {
				      return (p1.compareTo(p2));
				   }
				 });
		return list;
	}

	public static void main(String args[]) throws PatternNotFound {
		try {
			PatternStore p = new PatternStore(args[0]);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
	}
}
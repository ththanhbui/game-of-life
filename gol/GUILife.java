package gol;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUILife extends JFrame implements ListSelectionListener {
	private PatternStore mStore;
	private World mWorld;
	private ArrayList<World> mCachedWorlds;
	private GamePanel mGamePanel;
	private JButton mPlayButton;
	private java.util.Timer mTimer;
	private boolean mPlaying;

	public GUILife(PatternStore ps) throws PatternFormatException {
		super("Game of Life");
		mStore = ps;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);

		add(createPatternsPanel(), BorderLayout.WEST);
		add(createControlPanel(), BorderLayout.SOUTH);
		add(createGamePanel(), BorderLayout.CENTER);

	}

	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch, title);
		component.setBorder(tb);
	}

	private JPanel createGamePanel() throws PatternFormatException {
		mGamePanel = new GamePanel();
		addBorder(mGamePanel, "Game Panel");
		mGamePanel.display(mWorld);
		return mGamePanel;
	}

	private JPanel createPatternsPanel() {
		// Panel on the Left as BorderLayout
		JPanel patt = new JPanel();
		BorderLayout layout = new BorderLayout();
		patt.setLayout(layout);

		// Initial list for JList
		List<Pattern> names = mStore.getPatternsNameSorted();

		// Add list to JList
		JList pattlist = new JList(names.toArray()); 
		pattlist.setFont(new Font("Arial", Font.BOLD, 14));

		// Make the list scroll-able by adding to JScrollPane
		JScrollPane scrl = new JScrollPane(pattlist);

		// Add JScrollPane to JPanel
		patt.add(scrl);
		addBorder(patt, "Patterns");

		// Add ListSelectionListener
		pattlist.addListSelectionListener(this); // Object instead of anonymous function -> will override and run valueChanged() automatically
		
		return patt;
	}

	private JPanel createControlPanel() {
		JPanel ctrl = new JPanel();
		GridLayout layout = new GridLayout(1, 4);
		ctrl.setLayout(layout);

		addBorder(ctrl, "Controls");
		// Set Back Button to back
		JButton back = new JButton("< Back");
		back.addActionListener(e -> moveBack());

		// Set Forward Button to forward
		JButton forward = new JButton(" Forward >");
		forward.addActionListener(e -> {
			try {
				moveForward();
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		});
		
		// Set Restart Button to restart 
		JButton restart = new JButton("Restart");
		restart.addActionListener(e -> restart());
		
		// Setup mPlayButton
		mPlayButton = new JButton("Play");
		mPlayButton.addActionListener(e -> runOrPause());

		// Add the buttons to Control Panel
		ctrl.add(back);
		ctrl.add(mPlayButton);
		ctrl.add(forward);
		ctrl.add(restart);

		return ctrl;
	}
	
	private void restart() {
		mWorld = mCachedWorlds.get(0);
		mGamePanel.display(mWorld);
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

	private void moveBack() {
		if (mWorld == null) return;
		else if (mWorld.getGenerationCount() != 0) {
			mWorld = mCachedWorlds.get(mWorld.getGenerationCount() - 1);
			mGamePanel.display(mWorld);
		} else {
			mGamePanel.display(mWorld);
		}
	}

	private void moveForward() throws CloneNotSupportedException {
		if (mWorld == null) {
			return;
		} else if (mWorld.getGenerationCount() >= mCachedWorlds.size() - 1) {
			mWorld = copyWorld(true);
			mWorld.nextGeneration();
			mCachedWorlds.add(mWorld);
			mGamePanel.display(mWorld);
		} else {
			mWorld = mCachedWorlds.get(mWorld.getGenerationCount() + 1);
			mGamePanel.display(mWorld);
		}
	}

	private void runOrPause() {
		if (mPlaying) {
			mTimer.cancel();
			mPlaying = false;
			mPlayButton.setText("Play");
		} else {
			mPlaying = true;
			mPlayButton.setText("Stop");
			mTimer = new Timer(true);
			mTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						moveForward();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}, 0, 500);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<Pattern> list = (JList<Pattern>) e.getSource();
		Pattern p = list.getSelectedValue();
		mCachedWorlds = new ArrayList<>();
		if (p.getWidth() * p.getHeight() > 64) {
			try {
				mWorld = new ArrayWorld(p);

			} catch (PatternFormatException e1) {
				System.out.println(e1.getMessage());
			}
		} else {
			try {
				mWorld = new PackedWorld(p);
			} catch (PatternFormatException e2) {
				System.out.println(e2.getMessage());
			}
		}
		mCachedWorlds.add(mWorld);
		mGamePanel.display(mWorld);

	}

	public static void main(String[] args) throws IOException, PatternFormatException {
		PatternStore ps = new PatternStore("http://www.cl.cam.ac.uk/teaching/1617/OOProg/ticks/life.txt");
		GUILife gui = new GUILife(ps);
		gui.setVisible(true);
	}
}

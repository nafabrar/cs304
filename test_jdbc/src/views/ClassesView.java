package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClassesView extends JPanel implements ActionListener {
		
	private enum ClassViewMode {
		AGGREGATE,
		DIVIDE,
	}
	
	private JButton refreshButton;
	private ClassViewMode mode;
	private JButton modeButton;
	private JTable classesTable;
	
	public ClassesView() {
		initPanel();
	}
	
	/*
	 * Set up what the classes view should look like
	 * Functionality:
	 * See how many people are in each class and waitlist (aggregation)
	 * See classes all customers have taken (division)
	 */
	public void initPanel() {
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		this.add(refreshButton);
		mode = ClassViewMode.AGGREGATE;
		modeButton = new JButton("Show Classes Everyone takes");
		modeButton.addActionListener(this);
		this.add(modeButton);
		
		String [] colNames = {"Class Type", "Capacity", "In Class", "WaitList", "Times"};
		// TODO(billy) Load rows somehow (or defer it until visible)
		classesTable = new JTable(null, colNames);
		//this.add(classesTable);
    }
	
	private void refresh() {
		// TODO reload date fromDB
		System.out.println("Classes View: refresh");
	}
	
	private void changeMode() {
		switch (mode) {
		case AGGREGATE:
			mode = ClassViewMode.DIVIDE;
			modeButton.setText("Show all classes");
			// TODO rearrange table for divide and load data
			break;
		case DIVIDE:
			mode = ClassViewMode.AGGREGATE;
			modeButton.setText("Show Classes Everyone takes");
			// TODO rearrange table for Aggregate and load data
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refreshButton) {
			refresh();
		} else if (e.getSource() == modeButton) {
			changeMode();
		}
	}
}

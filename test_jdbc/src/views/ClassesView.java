package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pojos.AverageWaitlistResult;
import pojos.DivisionResult;
import pojos.GymClassListItem;
import repository.DataAccess;

public class ClassesView extends JPanel implements ActionListener {
		
	/**
	 * This view shows the list of classes as a table
	 */
	private static final long serialVersionUID = 1L;

	// There are three view modes, one to see the classes the current user is in or waitlisted
	// One to see All classes
	// And one to see any classes that all customers have taken (this is purely to demo division)
	private enum ClassViewMode {
		WAITLIST,
		ALL,
		DIVIDE,
	}
	
	public enum waitListAggregateMode {
		AVERAGE,
		MIN,
		MAX,
	}
	
	private JButton refreshButton;
	private ClassViewMode mode;
	// These are the actual buttons to change modes -- there are two at any given time and they don't change
	private JButton[] modeButtons;
	// These are references to the two modeButtons, since the function changes depending on what mode they are in
	// At any given time one of these will be null
	private JButton waitlistButton;
	private JButton allClassesButton;
	private JButton divideButton;
	// Buttons to get the min and max of the average waitlist by type
	private JButton waitlistButtons[];
	
	private JLabel tableLabel;
	private JTable classesTable;
	private DefaultTableModel tableModel;
	
	private JPanel buttonsPane;
	private JPanel tableTitlePane;
	private JPanel tablePane;
	
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
		buttonsPane = new JPanel();
		tableTitlePane = new JPanel();
		tablePane = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(buttonsPane);
		this.add(tableTitlePane);
		this.add(tablePane);
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		buttonsPane.add(refreshButton);
		mode = ClassViewMode.ALL;
		tableLabel = new JLabel("Average Waitlist by Type");
		modeButtons = new JButton[2];
		for (int i = 0; i < 2; i++) {
			modeButtons[i] = new JButton();
			modeButtons[i].addActionListener(this);
			buttonsPane.add(modeButtons[i]);
		}
		buttonsPane.add(modeButtons[0]);
		changeMode(mode);
		tableTitlePane.add(tableLabel);
		resetMaximums();
		if (modeButtons[0] == null || modeButtons[1] == null ) {
			System.out.println("Mode buttons are null");
		}
		
		tableModel = new DefaultTableModel(0, 0);
		classesTable = new JTable(tableModel);
		refresh();
		tablePane.add(new JScrollPane(classesTable));
    }
	
	// Make sure to keep layout looking okay when we change text
	// This should get called any time anything in the top two panels changes or stuff
	// might disappear from the screen
	private void resetMaximums() {
		buttonsPane.setMaximumSize(buttonsPane.getPreferredSize());
		tableTitlePane.setMaximumSize(tableTitlePane.getPreferredSize());
	}
	
	private void refresh() {
		// TODO reload date fromDB
		switch(mode) {
		case ALL:
			List<GymClassListItem> allData = DataAccess.getInstance().getAllClassesWithCounts();

			// Clear the table Model
			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);
			String[] allCols = {"ClassID",
					"Size",
					"Times",
					"Class Type",
					"Teacher",
					"Branch",
					"Enrolled",
					"Waitlist",
			};
			tableModel.setColumnCount(allCols.length);
			tableModel.setColumnIdentifiers(allCols);
			for (GymClassListItem item: allData) {
				String[] row = new String[allCols.length];
				row[0] = "" + item.classID;
				row[1] = "" + item.size;
				row[2] = item.classTimeAsString();
				row[3] = item.classType;
				row[4] = item.teacherName;
				row[5] = item.address;
				row[6] = "" + item.inClass;
				row[7] = "" + item.waitList;
				tableModel.addRow(row);
			}
			resetMaximums();
			break;
		case DIVIDE:
			List<DivisionResult> divideData = DataAccess.getInstance().classesEveryoneTakes();
			// Clear the table Model
			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);
			String[] divideCols = {"ClassID",
					"Class Name",
			};
			tableModel.setColumnCount(divideCols.length);
			tableModel.setColumnIdentifiers(divideCols);
			for (DivisionResult item: divideData) {
				String[] row = new String[divideCols.length];
				row[0] = "" + item.classID;
				row[1] = item.typeName;
				tableModel.addRow(row);
			}
			resetMaximums();
			break;
		case WAITLIST:
			//List<AverageWaitlistResult> wlData = DataAccess.getInstance().averageWaitlistByType();
			AverageWaitlistResult maxWL = DataAccess.getInstance().averageWaitlistMinORMax(false);
			AverageWaitlistResult minWL = DataAccess.getInstance().averageWaitlistMinORMax(true);

			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);	
			String[] waitlistCols = {"MIN/MAX",
					"Class Type",
					"Average Wait List",
			};
			tableModel.setColumnCount(waitlistCols.length);
			tableModel.setColumnIdentifiers(waitlistCols);
			String[] row = new String[waitlistCols.length];
			row[0] = "MAX";
			row[1] = maxWL.classType;
			row[2] = "" + maxWL.averageWaitlist;
			tableModel.addRow(row);
			row = new String[waitlistCols.length];
			row[0] = "MIN";
			row[1] = minWL.classType;
			row[2] = "" + minWL.averageWaitlist;
			tableModel.addRow(row);
			resetMaximums();
			break;
		}	
	}
	
	private void changeMode(ClassViewMode newMode) {
		switch (newMode) {
		case WAITLIST:
			tableLabel.setText("Waitlist Info");
			waitlistButton = null;
			modeButtons[0].setText("All Classes");
			allClassesButton = modeButtons[0];
			modeButtons[1].setText("Classes Everyone takes");
			divideButton = modeButtons[1];
			mode = ClassViewMode.WAITLIST;
			resetMaximums();
			
			// TODO rearrange table for My Classes and load data
			break;
		case ALL:
			tableLabel.setText("All Classes");
			mode = ClassViewMode.ALL;
			allClassesButton = null;
			modeButtons[0].setText("Classes Everyone takes");
			divideButton = modeButtons[0];
			modeButtons[1].setText("Waitlist Info");
			waitlistButton = modeButtons[1];
			resetMaximums();

			// TODO rearrange table for All Classes and load data
			break;
		case DIVIDE:
			tableLabel.setText("Classes Everyone takes");
			mode = ClassViewMode.DIVIDE;
			divideButton = null;
			modeButtons[0].setText("All Classes");
			allClassesButton = modeButtons[0];
			modeButtons[1].setText("Waitlist Info");
			waitlistButton = modeButtons[1];
			resetMaximums();
			
			// TODO rearrange table for Divide and load data
			break;
		}
		mode = newMode;
		if (tableModel != null) {
			refresh();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refreshButton) {
			refresh();
		} else if (e.getSource() == waitlistButton) {
			changeMode(ClassViewMode.WAITLIST);
		} else if (e.getSource() == allClassesButton) {
			changeMode(ClassViewMode.ALL);
		} else if (e.getSource() == divideButton) {
			changeMode(ClassViewMode.DIVIDE);
		}
	}
}

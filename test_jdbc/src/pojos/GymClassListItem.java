package pojos;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// Data type for the aggregation query
public class GymClassListItem {
	public int classID;
	public int size;
	public Date startTime;
	public Date endTime;
	public String classType;
	public String description;
	
	public String teacherName;
	public String address;
	
	public int inClass;
	public int waitList;

	
	// Returns the class time as a string
	// In the form "Tues 11:00-11:30"
	public String classTimeAsString() {
		// TODO string like Tues 11:00-11:30
		if (startTime == null ||
			endTime == null) {
				return "";
			}
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		String result = "";
		result += c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT_FORMAT, Locale.getDefault());
		result += " ";
		
		SimpleDateFormat sdfm = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat sdfh = new SimpleDateFormat("hh a");
		if (c.get(Calendar.MINUTE) == 0) {
			result += sdfh.format(startTime);
		} else {
			result += sdfm.format(startTime);
		}
		result += "-";
		c.setTime(endTime);
		if (c.get(Calendar.MINUTE) == 0) {
			result += sdfh.format(endTime);
		} else {
			result += sdfm.format(endTime);
		}

		return result;
	}
}

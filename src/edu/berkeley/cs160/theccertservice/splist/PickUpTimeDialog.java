package edu.berkeley.cs160.theccertservice.splist;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class PickUpTimeDialog extends DialogFragment
implements TimePickerDialog.OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				false);
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		PickUpActivity parentAct = (PickUpActivity) getActivity();
        parentAct.onFinishAddPickUpTime(hourOfDay, minute);
	}
}

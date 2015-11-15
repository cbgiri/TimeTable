package timetable.teamdevops.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by king on 16-11-2015.
 */
public class DescriptionDialog extends DialogFragment {

    Button ok, cancel;
    EditText et;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator=(Communicator)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description_dialog, null);
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        et = (EditText) view.findViewById(R.id.editText);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.onDialogMessage(et.getText().toString());
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.onDialogMessage("-1");
                dismiss();
            }
        });
        return view;
    }
    interface Communicator{
        public void onDialogMessage(String message);
    }
}

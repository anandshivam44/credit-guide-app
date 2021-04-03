package emeraldrating.creditguideapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentDialog extends DialogFragment {

    public FragmentDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FragmentDialog newInstance(String title,
                                             String riskClass,
                                             String paymentTerms,
                                             String paymentTermTolerance,
                                             String maxOrderSize,
                                             String creditLimit,
                                             String average) {
        FragmentDialog frag = new FragmentDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("riskClass",riskClass);
        args.putString("paymentTerms",paymentTerms);
        args.putString("paymentTermTolerance",paymentTermTolerance);
        args.putString("maxOrderSize",maxOrderSize);
        args.putString("creditLimit",creditLimit);
        args.putString("average",average);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        Button Okay=view.findViewById(R.id.okay);
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        String riskClass=getArguments().getString("riskClass","Something went wrong");
        String paymentTerms=getArguments().getString("paymentTerms","Something went wrong");
        String paymentTermTolerance=getArguments().getString("paymentTermTolerance","Something went wrong");
        String maxOrderSize=getArguments().getString("maxOrderSize","Something went wrong");
        maxOrderSize=addCommas(maxOrderSize);

        String creditLimit=getArguments().getString("creditLimit","Something went wrong");
        creditLimit=addCommas(creditLimit);
//        String average=getArguments().getString("average","Something went wrong");

        TextView textView1 =  view.findViewById(R.id.text_view_1);
        TextView textView2 =  view.findViewById(R.id.text_view_2);
        TextView textView3 =  view.findViewById(R.id.text_view_3);
        TextView textView4 =  view.findViewById(R.id.text_view_4);
        TextView textView5 =  view.findViewById(R.id.text_view_5);

        textView1.setText(riskClass);
        if (riskClass=="Low"){
            textView1.setTextColor(Color.GREEN);
        }
        else if (riskClass=="Medium"){
            textView1.setTextColor(Color.parseColor("#d35400"));
        }
        else if (riskClass=="High"){
            textView1.setTextColor(Color.RED);
        }

        textView2.setText(paymentTerms);
        textView3.setText(paymentTermTolerance);
        textView4.setText(maxOrderSize);
        textView5.setText(creditLimit);


    }

    private String addCommas(String number) {
        int l=number.length();
        String response="";
        int counter=0;
        for (int i=l-1;i>=0;i--){
            counter++;
            response=number.charAt(i)+response;
            if (counter==3 && i!=0){
                response=","+response;
                counter=0;
            }

        }
        return response;
    }

}

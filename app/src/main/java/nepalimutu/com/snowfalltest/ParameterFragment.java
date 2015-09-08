package nepalimutu.com.snowfalltest;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Enumeration;

/**
 * Created by pujan paudel on 9/6/2015.
 */
public class ParameterFragment extends DialogFragment {
    private  String[] spinnerarray;
    public enum Parameter{SPEED,DENSITY};
    private ParameterFragment reference;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        reference=this;
        spinnerarray=new String[]{"Low","Medium","High"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promptdialog, container,
                false);
        getDialog().setTitle("Simulation Parameters");
        Spinner speed=(Spinner)rootView.findViewById(R.id.spinner_speed);
        Spinner density=(Spinner)rootView.findViewById(R.id.spinner_density);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, spinnerarray);
        speed.setAdapter(adapter);
        density.setAdapter(adapter);
        speed.setOnItemSelectedListener(new CustomOnItemSelectedListener(Parameter.SPEED));
        density.setOnItemSelectedListener(new CustomOnItemSelectedListener(Parameter.DENSITY));
        Button animate=(Button)rootView.findViewById(R.id.simulate);
        animate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.dismiss();
                SnowFallActivity.myreference.SnowFall();
            }
        });

        return  rootView;
    }




    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

            Parameter myparam;
        int check=0;
            public  CustomOnItemSelectedListener(Parameter param){
                this.myparam=param;
            }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           // parent.getItemAtPosition(pos).toString(),
check++;
            //Set The ConstantStatic Thing here
            if(this.myparam==Parameter.SPEED){
                switch (parent.getItemAtPosition(pos).toString()){
                    case "Low": //slow
                        Constants.ANIM_DURATION=10000;
                        break;

                    case "Medium":
                        Constants.ANIM_DURATION=6000;
                        break;

                    case "High":
                        Constants.ANIM_DURATION=3000;



                }



            }else{
                if(this.myparam==Parameter.DENSITY){
                    switch (parent.getItemAtPosition(pos).toString()){
                        case "Low": //slow
                            Constants.ANIM_DENSITY=500;
                            break;

                        case "Medium":
                            Constants.ANIM_DENSITY=300;
                            break;

                        case "High":
                            Constants.ANIM_DENSITY=100;
                            break;


                    }
                }

            }



        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

}

package com.example.hstalk;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hstalk.Retrofit.RetroCallback;
import com.example.hstalk.Retrofit.RetroClient;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CreateBoardActivity extends AppCompatActivity  {

    String writer;
    String title;
    String startAt;
    String endAt;
    String createdAt;
    String desc;
    int postState;
    int freeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createboard);
        final EditText editTitle = (EditText)findViewById(R.id.Edit_Title);
        Button date = (Button)findViewById(R.id.createboard_button_date);
        Button startTime = (Button)findViewById(R.id.button_start_time);
        Button endTime = (Button)findViewById(R.id.button_end_time);
        Button finish = (Button)findViewById(R.id.button_finish);
        final EditText description = (EditText)findViewById(R.id.edit_description);
        RadioButton pay = (RadioButton)findViewById(R.id.createboard_radio_pay);
        final RadioButton free = (RadioButton)findViewById(R.id.createboard_radio_free);

        final TextView textViewDate = (TextView)findViewById(R.id.createboard_textview_date);
        final TextView textViewStartTime = (TextView)findViewById(R.id.textview_start_time);
        final TextView textViewEndTime = (TextView)findViewById(R.id.textview_end_time);

        final Calendar startCal = Calendar.getInstance();
        final Calendar endCal = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CreateBoardActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String strDate = Integer.toString(year) + "-" + Integer.toString(month+1) + "-" + Integer.toString(dayOfMonth);
                        textViewDate.setText(strDate);
                        startCal.set(year,month,dayOfMonth);
                    }
                },startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DATE));
                dialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateBoardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String strTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                        textViewStartTime.setText(strTime);

                        TimePickerDialog timePickerDialog2 = new TimePickerDialog(CreateBoardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String strTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                                textViewEndTime.setText(strTime);
                            }
                        },endCal.get(Calendar.HOUR_OF_DAY),endCal.get(Calendar.MINUTE),false);
                        timePickerDialog2.show();
                    }
                },startCal.get(Calendar.HOUR_OF_DAY),startCal.get(Calendar.MINUTE),false);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(CreateBoardActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String strTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                        textViewEndTime.setText(strTime);
                    }
                },endCal.get(Calendar.HOUR_OF_DAY),endCal.get(Calendar.MINUTE),false);
                timePickerDialog2.show();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writer = FirebaseAuth.getInstance().getCurrentUser().getUid();
                title = editTitle.getText().toString();
                startAt = textViewDate.getText().toString() + " " + textViewStartTime.getText().toString();
                endAt = textViewDate.getText().toString() +  " " + textViewEndTime.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                createdAt =  df.format(new Date());
                desc = description.getText().toString();

                if(free.isChecked()){
                    freeState = 1;
                }else{
                    freeState = 0;
                }
                postState = 0;

                createBoard();
                CreateBoardActivity.this.finish();
            }
        });

    }

    protected void createBoard(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("title",title);
        data.put("writeId",writer);
        data.put("started_at",startAt);
        data.put("ended_at",endAt);
        data.put("created_at",createdAt);
        data.put("description",desc);
        data.put("postState",postState);
        data.put("freeState",freeState);

        RetroClient retroClient = RetroClient.getInstance(this).createBaseApi();
        retroClient.createBoard(data, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(CreateBoardActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Toast.makeText(CreateBoardActivity.this,"글 작성 완료",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(CreateBoardActivity.this,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
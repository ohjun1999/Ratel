package com.link.ratel.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.link.ratel.R;
import com.link.ratel.fragment.GroupFiveFragment;
import com.link.ratel.fragment.GroupFourFragment;
import com.link.ratel.fragment.GroupOneFragment;
import com.link.ratel.fragment.GroupTwoFragment;

public class ExplainGroupActivity extends AppCompatActivity implements OnClickListener {

    private RadioGroup mRgLine1;
    private RadioGroup mRgLine2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_group);


        mRgLine1 = (RadioGroup) findViewById(R.id.radioGroup1);
        mRgLine1.clearCheck();
//        mRgLine1.setOnCheckedChangeListener(listener1);
//        mRgLine2 = (RadioGroup) findViewById(R.id.radioGroup2);
//        mRgLine2.clearCheck();
//        mRgLine2.setOnCheckedChangeListener(listener2);

        RadioButton btn1 = findViewById(R.id.radioBtn1);
        RadioButton btn2 = findViewById(R.id.radioBtn2);
//        RadioButton btn3 = findViewById(R.id.radioBtn3);
        RadioButton btn4 = findViewById(R.id.radioBtn4);
        RadioButton btn5 = findViewById(R.id.radioBtn5);

        ImageButton backKey = findViewById(R.id.backKey);

        backKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        btn1.setChecked(true);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GroupOneFragment groupOneFragment = new GroupOneFragment();
                transaction.replace(R.id.frameLayout, groupOneFragment);
                transaction.commit();


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GroupTwoFragment groupTwoFragment = new GroupTwoFragment();
                transaction.replace(R.id.frameLayout, groupTwoFragment);
                transaction.commit();


            }
        });
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                GroupThreeFragment groupThreeFragment = new GroupThreeFragment();
//                transaction.replace(R.id.frameLayout, groupThreeFragment);
//                transaction.commit();
//
//
//            }
//        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GroupFourFragment groupFourFragment = new GroupFourFragment();
                transaction.replace(R.id.frameLayout, groupFourFragment);
                transaction.commit();


            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GroupFiveFragment groupFiveFragment = new GroupFiveFragment();
                transaction.replace(R.id.frameLayout, groupFiveFragment);
                transaction.commit();


            }
        });

    }



//    private OnCheckedChangeListener listener1 = new OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//            if (checkedId != -1) {
//                mRgLine2.setOnCheckedChangeListener(null);
//                mRgLine2.clearCheck();
//                mRgLine2.setOnCheckedChangeListener(listener2);
//            }
//        }
//    };

    private OnCheckedChangeListener listener2 = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mRgLine1.setOnCheckedChangeListener(null);
                mRgLine1.clearCheck();
//                mRgLine1.setOnCheckedChangeListener(listener1);
            }
        }
    };


    @Override
    public void onClick(View v) {

    }
}
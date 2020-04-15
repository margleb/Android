package com.bawp.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bawp.myapplication.data.Course;
import com.bawp.myapplication.data.CourseData;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        if(savedInstanceState == null) {

            Bundle bundle = getIntent().getExtras();
            Course course = new CourseData().courseList().get(bundle.getInt("course_id"));
            // Toast.makeText(getApplicationContext(), course.getCourseName(), Toast.LENGTH_SHORT).show();

            FragmentDetailCourse fragment = new FragmentDetailCourse();
            fragment.setArguments(bundle);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.detail_list_container, fragment).commit();
        }
    }
}

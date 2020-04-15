package com.bawp.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bawp.myapplication.data.Course;

public class MainActivity extends AppCompatActivity implements FragmentListCourse.Callbacks  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.myContainer);
//
//        if(fragment == null) {
//            fragment = new FragmentListCourse();
//            fm.beginTransaction().add(R.id.myContainer, fragment).commit();
//        }

    }

    @Override
    public void onItemSelected(Course course, int position) {
        // Toast.makeText(MainActivity.this, course.getCourseName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("course_id", position);
        startActivity(intent);
    }
}

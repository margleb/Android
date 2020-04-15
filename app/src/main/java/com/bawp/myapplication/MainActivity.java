package com.bawp.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bawp.myapplication.data.Course;

public class MainActivity extends AppCompatActivity implements FragmentListCourse.Callbacks  {

    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.detailContainer) != null) {
            isTwoPane = true;
        }



    }

    @Override
    public void onItemSelected(Course course, int position) {
        if(isTwoPane) {
        Bundle bundle = new Bundle();
        bundle.putInt("course_id", position);

        FragmentManager fm = getSupportFragmentManager();
        FragmentDetailCourse fragment = new FragmentDetailCourse();
        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.detailContainer, fragment).commit();

        } else {
            // Toast.makeText(MainActivity.this, course.getCourseName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("course_id", position);
            startActivity(intent);
        }
    }
}

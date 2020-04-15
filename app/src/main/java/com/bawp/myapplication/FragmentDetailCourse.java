package com.bawp.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bawp.myapplication.data.Course;
import com.bawp.myapplication.data.CourseData;

public class FragmentDetailCourse extends Fragment {
    Course course;

    public FragmentDetailCourse() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("course_id")) {
            course = new CourseData().courseList().get(bundle.getInt("course_id"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.course_detail_fragment, container, false);

        if(course != null) {
            TextView courseName = view.findViewById(R.id.courseName);
            courseName.setText(course.getCourseName());

            ImageView imageView = view.findViewById(R.id.detailCourseImage);
            imageView.setImageResource(course.getImageResourceId(getActivity()));

            TextView courseDescription = view.findViewById(R.id.course_description);
            courseDescription.setText(course.getCourseName());
        }

        return view;
    }
}

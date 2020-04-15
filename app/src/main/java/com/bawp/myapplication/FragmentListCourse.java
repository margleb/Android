package com.bawp.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.bawp.myapplication.data.Course;
import com.bawp.myapplication.data.CourseArrayAdapter;
import com.bawp.myapplication.data.CourseData;
import com.bawp.myapplication.util.ScreenUtility;

import java.util.List;

public class FragmentListCourse extends ListFragment {

    List<Course> courses = new CourseData().courseList();

    public FragmentListCourse() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtility screenUtility = new ScreenUtility(getActivity());

        Log.d("Width", String.valueOf(screenUtility.getDpWidth()));

        CourseArrayAdapter adapter = new CourseArrayAdapter(getActivity(), R.layout.course_listitem, courses);
        setListAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment, container, false);
        return view;
    }


}

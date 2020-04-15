package com.bawp.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
    private Callbacks activity;

    public FragmentListCourse() { }

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

    public interface Callbacks {
      public void onItemSelected(Course course);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        // super.onListItemClick(l, v, position, id);
        Course course = courses.get(position);
        this.activity.onItemSelected(course);
        // Toast.makeText(getActivity(), course.getCourseName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Callbacks) context;
    }
}

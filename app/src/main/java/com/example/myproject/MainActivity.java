package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.data.AnswerListAsyncResponse;
import com.example.myproject.data.QuestionBank;
import com.example.myproject.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);

        questionCounterTextView = findViewById(R.id.counter_text);
        questionTextView = findViewById(R.id.question_textview);

         nextButton.setOnClickListener(this);
         prevButton.setOnClickListener(this);

         trueButton.setOnClickListener(this);
         falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionCounterTextView.setText(currentQuestionIndex + " /" + questionArrayList.size());
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                // Log.d("Inside", "processFinished: " + questionArrayList);
            }
        });
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessage = 0;
        if(userChooseCorrect == answerIsTrue) {
            toastMessage = R.string.correct_answer;
        } else {
            toastMessage= R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void updateQuestion() {
       String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(currentQuestionIndex + " /" + questionList.size());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.next_button:
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.prev_button:
                currentQuestionIndex = currentQuestionIndex != 0 ? currentQuestionIndex - 1 : questionList.size() - 1;
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                break;
            case R.id.false_button:
                checkAnswer(false);
                break;
        }
    }
}

// The code for the page showing the lesson list for the clicked topic

package mathmo.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TopicActivity extends AppCompatActivity {

    private TextView topicTitle;
    private LinearLayout linearLayout;
    private List<String> lessons = new ArrayList<>(Arrays.asList("Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5", "Lesson 6", "Lesson 7", "Lesson 8", "Lesson 9"));
    public HashMap<Integer, Integer> lessonButtonStartIds;
    public HashMap<Integer, Integer> lessonButtonPracticeIds;
    public HashMap<Integer, Integer> lessonButtonLayoutIds;
    public HashMap<Integer, Integer> parentIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        // Create the hashmaps for storing the ids
        this.lessonButtonStartIds = new HashMap<>();
        this.lessonButtonPracticeIds = new HashMap<>();
        this.lessonButtonLayoutIds = new HashMap<>();
        this.parentIds = new HashMap<>();

        // Set title of topic in layout
        String topic = getIntent().getStringExtra("topic");
        topicTitle = findViewById(R.id.topic_title);
        topicTitle.setText(topic);

        // If we have just completed a lesson, set this to be "true" in our preferences
        int id = getIntent().getIntExtra("completed_lesson_id", -1);
        if (id != -1) {
            String stringID = Integer.toString(id) + topic;
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean(stringID, true).commit();

        }

        // Get floating action button and set onClickListener
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener((v) -> {
        });

        // Get our main linear layout
        linearLayout = findViewById(R.id.linear_topic_menu);

        // For each lesson in the topic, create a lesson button and an expanding view with a start and a
        // practice button
        for (int i = 0; i < lessons.size(); i++) {

            // Create the lesson button, the start button, the practice button and the linear layout that
            // will contain the latter two buttons
            Button lessonButton = new Button(this);
            Button startLesson = new Button(this);
            Button practiceLesson = new Button(this);
            LinearLayout parentLayout = new LinearLayout(this);
            LinearLayout expandedLayout = new LinearLayout(this);

            // Get the sizes of the margins & padding we need
            int verticalMargin = getResources().getDimensionPixelSize(R.dimen._15sdp);
            int verticalMarginExp = getResources().getDimensionPixelSize(R.dimen._5sdp);
            int horizontalMargin = getResources().getDimensionPixelSize(R.dimen._20sdp);
            int horizontalMarginExp = getResources().getDimensionPixelSize(R.dimen._20sdp);
            int padding = getResources().getDimensionPixelSize(R.dimen._15sdp);
            int expPadding = getResources().getDimensionPixelSize(R.dimen._35sdp);
            int horizontalGapExp = getResources().getDimensionPixelSize(R.dimen._3sdp);

            // Set the layout params for the buttons and the linear layout
            // Params for the parent layout
            LinearLayout.LayoutParams paramsParent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsParent.setMargins(horizontalMarginExp, verticalMargin, horizontalMarginExp, 0);

            // Params for the main lesson button
            LinearLayout.LayoutParams paramsMain = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsMain.setMargins(horizontalMargin, verticalMarginExp, horizontalMargin, 0);

            // Params for the expanded linear layout
            LinearLayout.LayoutParams paramsExp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsExp.setMargins(horizontalMargin, verticalMarginExp, horizontalMargin, verticalMarginExp);

            // Params for the start button
            LinearLayout.LayoutParams paramsExpButtonLeft = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsExpButtonLeft.setMargins(0, 0, horizontalGapExp, 0);

            // Params for the practice button
            LinearLayout.LayoutParams paramsExpButtonRight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsExpButtonRight.setMargins(horizontalGapExp, 0, 0, 0);

            // Set the padding and layout params for the lesson button
            lessonButton.setPadding(padding, padding, padding, padding);
            lessonButton.setLayoutParams(paramsMain);

            // Set params for the expanded layout, and add the start and practice buttons to it
            expandedLayout.setLayoutParams(paramsExp);
            expandedLayout.setOrientation(LinearLayout.HORIZONTAL);
            expandedLayout.addView(startLesson);
            expandedLayout.addView(practiceLesson);
            expandedLayout.setGravity(Gravity.CENTER);

            // Set the padding and layout params for the start and practice buttons
            startLesson.setPadding(expPadding, padding, expPadding, padding);
            startLesson.setLayoutParams(paramsExpButtonLeft);
            practiceLesson.setPadding(expPadding, padding, expPadding, padding);
            practiceLesson.setLayoutParams(paramsExpButtonRight);

            // Set the params for the parent layout
            parentLayout.setLayoutParams(paramsParent);
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            parentLayout.addView(lessonButton);
            parentLayout.addView(expandedLayout);

            // Generate and set ids for the start and practice buttons, and the expanded layout, and store them
            // in the corresponding hashmaps to be retrieved later; lessonButton id is the number of the lesson
            int startId = View.generateViewId();
            int practiceId = View.generateViewId();
            int expLayoutId = View.generateViewId();
            int parentId = View.generateViewId();
            this.lessonButtonStartIds.put(i, startId);
            this.lessonButtonPracticeIds.put(i, practiceId);
            this.lessonButtonLayoutIds.put(i, expLayoutId);
            this.parentIds.put(i, parentId);
            lessonButton.setId(i);
            startLesson.setId(startId);
            practiceLesson.setId(practiceId);
            expandedLayout.setId(expLayoutId);
            parentLayout.setId(parentId);

            // Set background and text for start button
            startLesson.setBackground(getResources().getDrawable(R.drawable.rounded_corners_start));
            startLesson.setText("Learn");

            // Set background and text for practice button
            practiceLesson.setBackground(getResources().getDrawable(R.drawable.rounded_corners_practice));
            practiceLesson.setText("Practice");

            // Set background and text for main lesson button, choosing colour depending on whether the
            // lesson has been completed or not
            lessonButton.setText(lessons.get(i));
            String stringID = Integer.toString(lessonButton.getId()) + topic;
            if (getPreferences(Context.MODE_PRIVATE).getBoolean(stringID, false)) {
                lessonButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_complete));
            }
            else {
                lessonButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button));
            }

            // Set the onClickListener for the main lesson button, which toggles the visibility of the expanded layout
            lessonButton.setOnClickListener((v) -> {

                // Get the buttons and the layout
                int lessonId = lessonButton.getId();
                Button start = findViewById(this.lessonButtonStartIds.get(lessonId));
                Button practice = findViewById(this.lessonButtonPracticeIds.get(lessonId));
                LinearLayout layout = findViewById(this.lessonButtonLayoutIds.get(lessonId));
                LinearLayout parent = findViewById(this.parentIds.get(lessonId));

                // Toggle visibility of expanded layout
                if (layout.getVisibility() == View.GONE) {

                    //Prepare transition
                    Transition transition = new Fade();
                    transition.setDuration(400);
                    transition.addTarget(layout);

                    // Begin transition and set layout to visible
                    TransitionManager.beginDelayedTransition(linearLayout, transition);
                    layout.setVisibility(View.VISIBLE);
                    parent.setBackground(getResources().getDrawable(R.drawable.rounded_corners_highlighted));

                    // Set onClickListener for start button
                    start.setOnClickListener((view) -> {
                        Intent intent = new Intent(v.getContext(), LessonActivity.class);
                        intent.putExtra("lesson", lessonButton.getText());
                        intent.putExtra("topic", topic);
                        intent.putExtra("id", lessonButton.getId());

                        TopicActivity.this.finish();
                        v.getContext().startActivity(intent);
                    });

                    // Set onClickListener for practice button
                    practice.setOnClickListener((view) -> {
                        Intent intent = new Intent(v.getContext(), PracticeActivity.class);
                        intent.putExtra("lesson", lessonButton.getText());
                        intent.putExtra("topic", topic);
                        intent.putExtra("id", lessonButton.getId());

                        TopicActivity.this.finish();
                        v.getContext().startActivity(intent);
                    });
                }
                else {
                    parent.setBackground(null);
                    layout.setVisibility(View.GONE);
                }
            });

            // Set initial visibility of expanded layout to GONE, and add both the main lesson button
            // and the expanded layout to the main linear layout
            expandedLayout.setVisibility(View.GONE);
            linearLayout.addView(parentLayout);
        }
    }
}

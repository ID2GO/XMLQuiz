package eu.id2go.xmlquiz;

import eu.id2go.xmlquiz.R;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
//import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
//import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    //    Here we define out of Method values for different Object Data Types (like: Classes, primitives, boolean,
//      String))  with different Variable names (like: int (Object Data Type) points (variable
//      name), etc.) for reference by different Methods throughout the app.
    int points;
    TextView final_summary;
    boolean hasAnswerQ1_1, hasAnswerQ1_2, hasAnswerQ1_3, hasAnswerQ4_1, hasAnswerQ4_2, hasAnswerQ4_3;
    EditText textInputQ3;
    String summaryOfAnswers;
    int radioButtonId;


    /**
     * @param savedInstanceState together with outState saves & restores the user's input otherwise
     *                           lost when the phone is being turned to landscape or portrait mode.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final_summary = findViewById(R.id.summary_text);
        final_summary.setEnabled(false);


        //As proof of having saved & restored the saved info, a message string is toasted to the
        // screen!
        if (savedInstanceState != null) {
            String Message = savedInstanceState.getString("Message");
            Toast.makeText(this, Message, Toast.LENGTH_LONG).show();
        }


    }

    /**
     * @param outState together with savedInstanceState should save & restore the user's input
     *                 otherwise lost when the phone is being turned to landscape or portrait mode.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("Message", "Your input is being reloaded");
        super.onSaveInstanceState(outState);
    }


    /**
     * @onClick checkAnswers creates on the screen a summary of the points collected & answers given
     * @Toast toasts the result of the points collected
     */
    public void checkAnswers(View view) {
        calculateScore();
        Toast.makeText(this, getString(R.string.toast_message, "" + points),
                Toast.LENGTH_SHORT).show();

//      These methods call the Variable contents stored in the outside Method Variables defined
//      int points & String summaryOfAnswers
        displayPoints(points);
        displayMessage(summaryOfAnswers);
    }

    /**
     * @onClick submitAnswers &
     * @Intent creates outputstring to email app.
     */
    public void submitAnswers(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: ")); // only email apps can handle @Intent.ACTION_SENDTO
        // with @mailto:
        intent.putExtra(Intent.EXTRA_SUBJECT, "Summary of Quizz answers: ");
        intent.putExtra(Intent.EXTRA_TEXT, summaryOfAnswers);
        // @if there is no app to handle the request, then  it will be skipped
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * @onClick checkAnswers calls the Method: calculateScore to check if the checkboxes are checked
     * and to add-up the points collected in the quiz.
     * The predefined Variables in the method refer to the checkQuestion methods below the
     * calculateScore method where the right answers & point count are defined.
     */
    private void calculateScore() {
//      Q1
        hasAnswerQ1_1 = ((CheckBox) findViewById(R.id.checkboxQ1_1)).isChecked();
        hasAnswerQ1_2 = ((CheckBox) findViewById(R.id.checkboxQ1_2)).isChecked();
        hasAnswerQ1_3 = ((CheckBox) findViewById(R.id.checkboxQ1_3)).isChecked();

//      Q2
        radioButtonId = ((RadioGroup) findViewById(R.id.radio_group_question_two))
                .getCheckedRadioButtonId();
//      Q4
        textInputQ3 = findViewById(R.id.text_input);
//      Q4
        hasAnswerQ4_1 = ((CheckBox) findViewById(R.id.checkboxQ4_1)).isChecked();
        hasAnswerQ4_2 = ((CheckBox) findViewById(R.id.checkboxQ4_2)).isChecked();
        hasAnswerQ4_3 = ((CheckBox) findViewById(R.id.checkboxQ4_3)).isChecked();


        points = calculatePoints(hasAnswerQ1_1, hasAnswerQ1_2, hasAnswerQ1_3, textInputQ3,
                radioButtonId, hasAnswerQ4_1, hasAnswerQ4_2, hasAnswerQ4_3);
        summaryOfAnswers = createAnswerSummary();

    }

    /**
     * In the checkQuestions methods right answers & point count are defined per question
     */

    private void checkQuestionOneA() {
        // Figuring out the possible answers
        CheckBox checkbox_answerQ1_1 = findViewById(R.id.checkboxQ1_1);
        hasAnswerQ1_1 = checkbox_answerQ1_1.isChecked();
//        Log.v("MainActivity", "Checkbox: " + checkbox_answerQ1_1);
        if (hasAnswerQ1_1) {
            final_summary.setText(final_summary.getText() + "\n" + "Answer Q1_1: TextView");
            final_summary.setEnabled(true);
            points++;
        } else {
            final_summary.setEnabled(false);
        }
    }

    private void checkQuestionOneB() {
        CheckBox checkbox_answerQ1_2 = findViewById(R.id.checkboxQ1_2);
        hasAnswerQ1_2 = checkbox_answerQ1_2.isChecked();
        if (hasAnswerQ1_2) {
            final_summary.setText(final_summary.getText() + "\n" + "Answer Q1_2: ImageView");
            final_summary.setEnabled(true);
            points++;
        } else {
            final_summary.setEnabled(false);
        }
    }

    private void checkQuestionOneC() {
        CheckBox checkbox_answerQ1_3 = findViewById(R.id.checkboxQ1_3);
        boolean hasAnswerQ1_3 = checkbox_answerQ1_3.isChecked();
        if (hasAnswerQ1_3) {
            final_summary.setText(final_summary.getText() + "\n" + "Answer Q1_3: ButtonView");
            final_summary.setEnabled(true);
        } else {
            final_summary.setEnabled(false);
        }
    }

    //    @RadioGroup is an easier way of configuring radioButtons
    private void checkQuestionTwo() {
        RadioGroup radioGroup = findViewById(R.id.radio_group_question_two);
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if (radioButtonId == R.id.q_2_right_answer)
            points++;

/**
 *      @boolean checked is a more elaborate way to configure the individual RadioButtons
 *      See the exemple here below.
 *
 *   boolean checked = ((RadioButton) view).isChecked();
 *       switch (view.getId()) {
 *           case R.id.q_2_right_answer:
 *               if (checked) {
 *                   final_summary.setText("Q2: You selected: PascalCase");
 *                   final_summary.setEnabled(true);
 *                       points =0;
 *               } else {
 *                   final_summary.setEnabled(false);
 *               }
 *               break;
 *           case R.id.q_2_wrong_answer:
 *               if (checked) {
 *                   final_summary.setText("Q2: You selected: camelCase");
 *                   final_summary.setEnabled(true);
 *                       points ++;
 *               } else {
 *                   final_summary.setEnabled(false);
 *               }
 *               break;
 *       }
 */
    }

    private void checkQuestionThree() {
//      Get user's input
//      EditText nameField = (EditText) findViewById(R.id.name_field);
//      String name = nameEditable.getText().toString();
        EditText edittext = findViewById(R.id.text_input);
        String textInputQ3 = edittext.getText().toString();
//      by use of a Log, one can check while debugging if the method is working correct.
//      Log.v("MainActivity", "Input: " + textInputQ3);

//      here the code checks for exact spelling of the input of the user
        if (edittext.getText().toString().trim().equals("Extensible Markup Language")) {
            final_summary.setText(final_summary.getText() + "\n" + "Q3: Extensible Markup Language");
            points++;

        }
    }

    private void checkQuestionFourA() {
        CheckBox checkbox_answerQ4_1 = findViewById(R.id.checkboxQ4_1);
        boolean hasAnswerQ4_1 = checkbox_answerQ4_1.isChecked();
        if (hasAnswerQ4_1) {
            final_summary.setText(final_summary.getText() + "\n" + "Answer Q4_A: ImageView");
            final_summary.setEnabled(true);
            points++;
        } else {
            final_summary.setEnabled(false);
        }
    }

    private void checkQuestionFourB() {
        CheckBox checkbox_answerQ4_2 = findViewById(R.id.checkboxQ4_2);
        boolean hasAnswerQ4_2 = checkbox_answerQ4_2.isChecked();
        if (hasAnswerQ4_2) {
            final_summary.setText(final_summary.getText() + "\n" + "Answer Q4_B: RootView");
            final_summary.setEnabled(true);
        } else {
            final_summary.setEnabled(false);
        }
    }

    private void checkQuestionFourC() {
        CheckBox checkbox_answerQ4_3 = findViewById(R.id.checkboxQ4_3);
        boolean hasAnswerQ4_3 = checkbox_answerQ4_3.isChecked();
        if (hasAnswerQ4_3) {
            final_summary.setText(final_summary.getText() + "\n" + "Answer Q4_C:Button ");
            final_summary.setEnabled(true);
            points++;
        } else {
            final_summary.setEnabled(false);
        }
//        Toast.makeText(this, getString(R.string.toast_message, ""+points), Toast.LENGTH_SHORT).show();
    }


    /**
     * @return total number of points collected
     */
    private int calculatePoints(boolean hasAnswerQ1_1, boolean hasAnswerQ1_2, boolean hasAnswerQ1_3,
                                EditText edittext, int radioButtonId, boolean hasAnswerQ4_1,
                                boolean hasAnswerQ4_2, boolean hasAnswerQ4_3) {

        /**
         * Keeping the points value private, avoids adding up beyond the number of questions
         */
        points = 0;
        int pointsCollected = points;

//Q1
        if (hasAnswerQ1_1) {
            pointsCollected = pointsCollected + 1;
        }

        if (hasAnswerQ1_2) {
            pointsCollected = pointsCollected + 1;
        }

        if (hasAnswerQ1_3) {
            pointsCollected = pointsCollected + 0;
        }

//EditText
        if (edittext.getText().toString().trim().equals("Extensible Markup Language")) {
            pointsCollected = pointsCollected + 1;
        }

//Radiobutton
        if (radioButtonId == R.id.q_2_right_answer) {
            final_summary.setText(final_summary.getText() + "\n" + "You selected: camelCase");
            pointsCollected = pointsCollected + 1;
        }

//Q4
        if (hasAnswerQ4_1) {
            pointsCollected = pointsCollected + 1;
        }

        if (hasAnswerQ4_2) {
            pointsCollected = pointsCollected + 0;
        }

        if (hasAnswerQ4_3) {
            pointsCollected = pointsCollected + 1;
        }
//        Toast.makeText(this, getString(R.string.toast_message, ""+points), Toast.LENGTH_SHORT)
// .show();

        return pointsCollected;

    }


    //-----------------------------

    /**
     * @return creates a summaryOfAnswers that is returned to the String  summaryOfAnswers
     * @createAnswerSummary is being called for by the end of the Method: calculateScore
     * by calling "summaryOfAnswers = createAnswerSummary ();" it calls for this string to generate
     * a summary of answers.
     */

    private String createAnswerSummary() {

//        String summaryOfAnswers = getString(R.string.answer_summary_name, name);

        String summaryOfAnswers = getString(R.string.collection_of_points) + points;

        summaryOfAnswers += "\n" + getString(R.string.answer_summary);
//        summaryOfAnswers += "\n" + getString(R.string.collection_of_points) + points;
//Checkboxes Q1
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q1_1) + hasAnswerQ1_1;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q1_2) + hasAnswerQ1_2;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q1_3) + hasAnswerQ1_3;
//radioButton Q2
        summaryOfAnswers += "\n" + (radioButtonId == R.id.q_2_right_answer);

        summaryOfAnswers += "\n" + getString(R.string.text_input) + textInputQ3.getText();
//Checkboxes Q4
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q4_1) + hasAnswerQ4_1;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q4_2) + hasAnswerQ4_2;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q4_3) + hasAnswerQ4_3;

//        Toast.makeText(this, getString(R.string.toast_message, ""+points), Toast.LENGTH_SHORT).show();

        return summaryOfAnswers;

    }

    /**
     * This method displays the given quantity value of points on the screen.
     */
    private void displayPoints(int points) {
        TextView quantityTextView = findViewById(R.id.total_points);
        quantityTextView.setText("Your total score out of 6 is:" + "\n" + points);
    }

    /**
     * This method displays the given String value summaryOfAnswers on the screen.
     */
    private void displayMessage(String summaryOfAnswers) {
        TextView summaryTextView = findViewById(R.id.summary_text);
        summaryTextView.setText(summaryOfAnswers);
    }

}

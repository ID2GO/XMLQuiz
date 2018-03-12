package eu.id2go.xmlquiz;

import eu.id2go.xmlquiz.R;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {

    /**
     * Here we define out of Method values for different Object Data Types like: Classes,
     * primitives, boolean, String with different Variable names like: int (Object Data
     * Type) points (variable name, etc.) for reference by different Methods throughout the app.
     */

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


        /**
         *  As proof of having saved & restored the saved info, a message string is toasted to the
         *  screen!
         */
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
     * onClick checkAnswers creates on the screen a summary of the points collected & answers given
     * Toast toasts the result of the points collected
     */
    public void checkAnswers(View view) {
        calculateScore();
        Toast.makeText(this, getString(R.string.toast_message, "" + points),
                Toast.LENGTH_SHORT).show();

        /**
         *  These methods call the Variable contents stored in the outside Method Variables defined
         *  in: int points & String summaryOfAnswers
         */
        displayPoints(points);
        displayMessage(summaryOfAnswers);
    }

    /**
     * onClick submitAnswers &
     * Intent creates outputstring to email app.
     */
    public void submitAnswers(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: xml-quiz@id2go.eu"));
        // only email apps can handle @Intent.ACTION_SENDTO with @mailto:
        intent.putExtra(Intent.EXTRA_SUBJECT, "Summary of Quizz answers: ");
        intent.putExtra(Intent.EXTRA_TEXT, summaryOfAnswers);
        // @if there is no app to handle the request, then  it will be skipped
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * onClick checkAnswers calls the Method: calculateScore to check if the checkboxes are checked
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
//      Q3
        textInputQ3 = findViewById(R.id.text_input);
//      Q4
        hasAnswerQ4_1 = ((CheckBox) findViewById(R.id.checkboxQ4_1)).isChecked();
        hasAnswerQ4_2 = ((CheckBox) findViewById(R.id.checkboxQ4_2)).isChecked();
        hasAnswerQ4_3 = ((CheckBox) findViewById(R.id.checkboxQ4_3)).isChecked();


        points = calculatePoints();
        summaryOfAnswers = createAnswerSummary();

    }


    /**
     * @return total number of points collected
     */
    private int calculatePoints() {

        /**
         * Keeping the points value private, avoids adding up beyond the number of questions
         */
        points = 0;

//Q1
        if (hasAnswerQ1_1 && hasAnswerQ1_2 && !hasAnswerQ1_3) {
            points++;
        }

//Radiobutton
        if (radioButtonId == R.id.q_2_wrong_answer) {
            final_summary.setText(getString(R.string.pascal_case));
        }
        if (radioButtonId == R.id.q_2_right_answer) {
            final_summary.setText(getString(R.string.camel_case));
            points++;
        }

//EditText
        if (textInputQ3.getText().toString().trim().equals("Extensible Markup Language")) {
            points++;
        }

//Q4
        if (hasAnswerQ4_1 && !hasAnswerQ4_2 && hasAnswerQ4_3) {
            points++;
        }

        return points;

    }


    /**
     * @return creates a summaryOfAnswers that is returned to the String  summaryOfAnswers
     * createAnswerSummary is being called for by the end of the Method: calculateScore
     * by calling "summaryOfAnswers = createAnswerSummary ();" it calls for this string to generate
     * a summary of answers.
     */

    private String createAnswerSummary() {


        /**
         *       Exemple:  String summaryOfAnswers = getString(R.string.answer_summary_name, name);
         */
        String summaryOfAnswers = getString(R.string.collection_of_points) + points;

        summaryOfAnswers += "\n" + getString(R.string.answer_summary);

//Checkboxes Q1
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q1_1) + hasAnswerQ1_1;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q1_2) + hasAnswerQ1_2;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q1_3) + hasAnswerQ1_3;
//radioButton Q2
        summaryOfAnswers += "\n" + getString(R.string.button_selection) + final_summary.getText() +
                (radioButtonId == R.id.q_2_right_answer);
//textInput Q3
        summaryOfAnswers += "\n" + getString(R.string.text_input) + textInputQ3.getText();
//Checkboxes Q4
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q4_1) + hasAnswerQ4_1;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q4_2) + hasAnswerQ4_2;
        summaryOfAnswers += "\n" + getString(R.string.answer_summary_q4_3) + hasAnswerQ4_3;

//      For testing:  Toast.makeText(this, getString(R.string.toast_message, ""+points), Toast.LENGTH_SHORT).show();

        return summaryOfAnswers;

    }

    /**
     * This method displays the given quantity value of points on the screen.
     */
    private void displayPoints(int points) {
        TextView quantityTextView = findViewById(R.id.total_points);
        quantityTextView.setText(getString(R.string.total_score) + ("\n" + points));
    }

    /**
     * This method displays the given String value summaryOfAnswers on the screen.
     */
    private void displayMessage(String summaryOfAnswers) {
        TextView summaryTextView = findViewById(R.id.summary_text);
        summaryTextView.setText(summaryOfAnswers);
    }

}

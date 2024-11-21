package com.example.designproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etNumberOfSemesters;
    private Button btnCalculateCgpa, btnClear;
    private LinearLayout semesterContainer;
    private TextView tvResult;
    private final ArrayList<BigDecimal> cred = new ArrayList<>();
    private final ArrayList<BigDecimal> gradd =new ArrayList<>();

    private final ArrayList<LinearLayout> semesterLayouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNumberOfSemesters = findViewById(R.id.et_number_of_semesters);
        Button btnGenerateSemesters = findViewById(R.id.btn_generate_semesters);
        btnCalculateCgpa = findViewById(R.id.btn_calculate_cgpa);
        btnClear = findViewById(R.id.btn_clear);
        semesterContainer = findViewById(R.id.semester_container);
        tvResult = findViewById(R.id.tv_result);

        btnGenerateSemesters.setOnClickListener(view -> generateSemesters());

        btnClear.setOnClickListener(view -> {
            gradd.clear();
            cred.clear();
            semesterLayouts.clear();
            semesterContainer.removeAllViews();
            btnClear.setVisibility(View.INVISIBLE);
            btnCalculateCgpa.setVisibility(View.INVISIBLE);
            tvResult.setText("");
        });
        btnCalculateCgpa.setOnClickListener(view ->{
            try {
                calculateCgpa();
            }catch(Exception e){
                android.widget.Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateSemesters() {
        gradd.clear();
        cred.clear();
        String numSemestersStr = etNumberOfSemesters.getText().toString().trim();
        if (numSemestersStr.isEmpty()) {
            Toast.makeText(this, "Please enter the number of semesters", Toast.LENGTH_SHORT).show();
            return;
        }
        int numSemesters = Integer.parseInt(numSemestersStr);
        semesterContainer.removeAllViews();
        semesterLayouts.clear();

        for (int i = 0; i < numSemesters; i++) {
            LinearLayout semesterLayout = new LinearLayout(this);
            semesterLayout.setOrientation(LinearLayout.VERTICAL);
            semesterLayout.setPadding(0, 16, 0, 16);

            TextView semesterTitle = new TextView(this);
            semesterTitle.setText("Semester " + (i + 1));
            semesterTitle.setTextSize(18);
            semesterLayout.addView(semesterTitle);

            EditText etNumberOfSubjects = new EditText(this);
            etNumberOfSubjects.setHint("Enter number of subjects");
            etNumberOfSubjects.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            semesterLayout.addView(etNumberOfSubjects);

            Button btnGenerateSubjects = new Button(this);
            btnGenerateSubjects.setText("Generate Subjects");
            semesterLayout.addView(btnGenerateSubjects);

            LinearLayout subjectContainer = new LinearLayout(this);
            subjectContainer.setOrientation(LinearLayout.VERTICAL);
            semesterLayout.addView(subjectContainer);

            int finalI = i;
            btnGenerateSubjects.setOnClickListener(view -> generateSubjects(etNumberOfSubjects, subjectContainer, finalI));

            semesterContainer.addView(semesterLayout);
            semesterLayouts.add(semesterLayout);
        }

        btnCalculateCgpa.setVisibility(View.VISIBLE);
        btnClear.setVisibility(View.VISIBLE);
    }

    private void generateSubjects(EditText etNumberOfSubjects, LinearLayout subjectContainer, int semesterIndex) {
        String numSubjectsStr = etNumberOfSubjects.getText().toString().trim();
        if (numSubjectsStr.isEmpty()) {
            Toast.makeText(this, "Enter the number of subjects", Toast.LENGTH_SHORT).show();
            return;
        }

        int numSubjects = Integer.parseInt(numSubjectsStr);
        subjectContainer.removeAllViews();

        for (int j = 0; j < numSubjects; j++) {
            LinearLayout subjectRow = new LinearLayout(this);
            subjectRow.setOrientation(LinearLayout.HORIZONTAL);

            EditText etGradePoint = new EditText(this);
            etGradePoint.setHint("Grade Point " + (j + 1));
            etGradePoint.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            subjectRow.addView(etGradePoint);

            EditText etCredit = new EditText(this);
            etCredit.setHint("Credit " + (j + 1));
            subjectRow.addView(etCredit);

            subjectContainer.addView(subjectRow);
        }

        Button btnCalculateSgpa = new Button(this);
        btnCalculateSgpa.setText("Calculate SGPA");
        subjectContainer.addView(btnCalculateSgpa);

        btnCalculateSgpa.setOnClickListener(view -> {
            try {
                calculateSgpa(subjectContainer, semesterIndex);
            } catch (Exception e) {}
        });
    }

    private void calculateSgpa(LinearLayout subjectContainer, int semesterIndex) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal totalCredits = BigDecimal.ZERO;

        for (int i = 0; i < subjectContainer.getChildCount() - 1; i++) {
            LinearLayout subjectRow = (LinearLayout) subjectContainer.getChildAt(i);
            EditText etGradePoint = (EditText) subjectRow.getChildAt(0);
            EditText etCredit = (EditText) subjectRow.getChildAt(1);

            String gradePointStr = etGradePoint.getText().toString().trim();
            String creditStr = etCredit.getText().toString().trim();

            if (!gradePointStr.isEmpty() && !creditStr.isEmpty()) {
                try {
                    BigDecimal gradePoint = new BigDecimal(gradePointStr);
                    BigDecimal credit = new BigDecimal(creditStr);
                    if (gradePoint.compareTo(BigDecimal.valueOf(5)) >= 0 && gradePoint.compareTo(BigDecimal.valueOf(10)) <= 0) {
                        cred.add(credit);
                        BigDecimal tc = gradePoint.multiply(credit);
                        sum = sum.add(tc);
                        gradd.add(tc);
                        totalCredits = totalCredits.add(credit);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if (totalCredits.compareTo(BigDecimal.ZERO) == 0) {
            Toast.makeText(this, "Total credits cannot be zero", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate SGPA using BigDecimal division
        BigDecimal sgpa = sum.divide(totalCredits, 2, RoundingMode.HALF_UP);

        // Add SGPA to the list

        // Display SGPA dynamically on the screen
        TextView sgpaTextView = new TextView(this);
        sgpaTextView.setText("SGPA for Semester " + (semesterIndex + 1) + ": " + sgpa.toPlainString());
        sgpaTextView.setTextSize(16);

        // Add the SGPA TextView to the semester's container
        subjectContainer.addView(sgpaTextView);
    }

    private void calculateCgpa() {
        BigDecimal grade = BigDecimal.ZERO;
        BigDecimal tcredits = BigDecimal.ZERO;

        // Sum up grades and credits
        for (int i = 0; i < gradd.size(); i++) {
            BigDecimal gradePoint = gradd.get(i);
            BigDecimal credit = cred.get(i);

            // Check if the grade point is in the valid range (5 to 10)
            if (gradePoint.compareTo(BigDecimal.ZERO) > 0 ) {
                grade = grade.add(gradePoint);
                tcredits = tcredits.add(credit);
            }
        }

        // Check for zero credits to prevent division by zero
        if (tcredits.compareTo(BigDecimal.ZERO) == 0) {
            Toast.makeText(this, "Total credits cannot be zero", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, tcredits.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, grade.toString(), Toast.LENGTH_SHORT).show();
        // Divide grade by total credits with rounding
        BigDecimal cgpa = grade.divide(tcredits, 2, RoundingMode.HALF_UP);
        // Display the CGPA
        tvResult.setText("CGPA: " + cgpa.toPlainString());
    }
}
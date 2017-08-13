package com.example.thilinab.tsprep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thilinab.tsprep.sqldb.SqlUtility;

public class ConfigureActivity extends AppCompatActivity {

    TextView saveConfigs;
    Details details = Details.getInstance();
    EditText classesText;
    EditText subjectsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        classesText = (EditText) findViewById(R.id.configure_class);
        subjectsText = (EditText) findViewById(R.id.configure_subjects);

        SqlUtility sqlUtility = details.getSqlUtility();
        final String classArr = sqlUtility.getConfigClasses();
        final String subjectsArr = sqlUtility.getConfigSubjects();

        if (classArr != null) {
            if (!classArr.isEmpty())
                classesText.setText(classArr);
        }
        if (subjectsArr != null) {
            if(!subjectsArr.isEmpty())
                subjectsText.setText(subjectsArr);
        }
        saveConfigs = (TextView) findViewById(R.id.save_configs_btn);
        saveConfigs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SqlUtility sqlUtiity = details.getSqlUtility();
                //sqlUtiity.deleteClasses();
                if (classArr == null || classArr.isEmpty())
                    sqlUtiity.insertClasses(classesText.getText().toString());
                else
                    sqlUtiity.updateClasses(classesText.getText().toString());

                if (subjectsArr == null || subjectsArr.isEmpty())
                    sqlUtiity.insertSubjects(subjectsText.getText().toString());
                else
                    sqlUtiity.updateSubjects(subjectsText.getText().toString());
                finish();
            }
        });
    }
}

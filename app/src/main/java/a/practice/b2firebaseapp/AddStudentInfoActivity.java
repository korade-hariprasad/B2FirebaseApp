package a.practice.b2firebaseapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddStudentInfoActivity extends AppCompatActivity {

    TextInputEditText etName;
    TextInputEditText etAge;
    Button btnAdd;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_info);
        etName=findViewById(R.id.etName);
        etAge=findViewById(R.id.etAge);
        btnAdd=findViewById(R.id.btnAdd);
        db=FirebaseFirestore.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DocumentReference docRef=db.collection("students").document();
                String docId=docRef.getId();
                db.collection("students")
                        .document(docId)
                        .set(new Student(docId,etName.getText().toString().trim(),Integer.parseInt(etAge.getText().toString())))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(AddStudentInfoActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                                    finish();

                                }else{
                                    Toast.makeText(AddStudentInfoActivity.this, "Failed to add Student", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddStudentInfoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }


}
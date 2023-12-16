package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText ipName, ipRN, ipMarks;
    private Button add, modify, delete, show;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipName=findViewById(R.id.ipName);
        ipRN=findViewById(R.id.ipRN);
        ipMarks=findViewById(R.id.ipMarks);
        add=findViewById(R.id.addStudent);
        modify=findViewById(R.id.modifyStudent);
        delete=findViewById(R.id.delStudent);
        show=findViewById(R.id.show);

        db=openOrCreateDatabase("Student_manage", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER, name VARCHAR, marks INTEGER);");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ipName.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Enter name of student", Toast.LENGTH_SHORT).show();
                }
                else if(ipRN.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this, "Enter Roll Number of student", Toast.LENGTH_SHORT).show();
                }
                else if(ipMarks.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Enter Marks of student", Toast.LENGTH_SHORT).show();
                }else {
                    db.execSQL("INSERT INTO student VALUES('"+ipRN.getText()+"','"+ipName.getText()+"','"+ipMarks.getText()+"');");
                    Toast.makeText(MainActivity.this, "Student data added successfully", Toast.LENGTH_SHORT).show();
                    clearText();
                }
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ipName.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Enter name of student", Toast.LENGTH_SHORT).show();
                }
                else if(ipRN.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this, "Enter Roll Number of student", Toast.LENGTH_SHORT).show();
                }
                else if(ipMarks.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Enter Marks of student", Toast.LENGTH_SHORT).show();
                }else {
                    Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+ipRN.getText()+"'", null);
                    if(c.moveToFirst()) {
                        db.execSQL("UPDATE student SET name ='"+ipName.getText()+"', marks='"+ipMarks.getText()+
                                "' WHERE rollno='"+ipRN.getText()+"';");
                        Toast.makeText(MainActivity.this, "Record updated", Toast.LENGTH_SHORT).show();
                        clearText();
                    }else {
                        Toast.makeText(MainActivity.this, "Invalid roll number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ipRN.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Enter roll number", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+ipRN.getText()+"'", null);
                if(c.moveToFirst()) {
                    db.execSQL("DELETE FROM student WHERE rollno='"+ipRN.getText()+"'");
                    Toast.makeText(MainActivity.this, "Record deleted", Toast.LENGTH_SHORT).show();
                    clearText();
                }else {
                    Toast.makeText(MainActivity.this, "Invalid roll number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM student", null);
                if (c.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No record fuund!", Toast.LENGTH_SHORT).show();
                } else{
                    StringBuilder stringBuilder = new StringBuilder();
                    while (c.moveToNext()) {
                        stringBuilder.append("Roll Number: "+c.getString(0)+"\n");
                        stringBuilder.append("Name: "+c.getString(1)+"\n");
                        stringBuilder.append("Marks: "+c.getString(2)+"\n\n");
                    }
                    Builder builder=new Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Student's Data");
                    builder.setMessage(stringBuilder.toString());
                    builder.show();
                }
            }
        });
    }

    private void clearText() {
        ipMarks.setText("");
        ipName.setText("");
        ipRN.setText("");
        ipRN.requestFocus();
    }
}
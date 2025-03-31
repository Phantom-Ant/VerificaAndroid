package com.example.verificaandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class ActivitiesActivity extends AppCompatActivity {
    EditText etxtAttivita;
    Button btnRegistra;
    ListView listViewAttivita;

    ArrayList<String> attivitas;
    ArrayAdapter adapterAttivita;

    String attivita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        etxtAttivita = (EditText) findViewById(R.id.editTextAttivita);
        btnRegistra = (Button) findViewById(R.id.btnRegistra);

        SharedPreferences sharedPreferences = getSharedPreferences("Attivita", MODE_PRIVATE);

        if(savedInstanceState == null){
            attivitas = new ArrayList<>(sharedPreferences.getStringSet("attivitas", new HashSet<>()));
        }else{
            attivitas = savedInstanceState.getStringArrayList("attivitas");
        }
        adapterAttivita = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attivitas);

        listViewAttivita = (ListView) findViewById(R.id.listViewAttivita);
        listViewAttivita.setAdapter(adapterAttivita);


        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attivita = etxtAttivita.getText().toString().trim();
                if(attivita.isEmpty()){
                    Toast.makeText(ActivitiesActivity.this, "Inserisci un'attività", Toast.LENGTH_SHORT).show();
                }else{
                    etxtAttivita.setText("");
                    attivitas.add(attivita);
                    adapterAttivita.notifyDataSetChanged();
                }
            }
        });

        listViewAttivita.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                attivitas.remove(position);
                adapterAttivita.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("attivitas", attivitas);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences salvaAttivita = getSharedPreferences("Attivita", MODE_PRIVATE);
        SharedPreferences.Editor editor = salvaAttivita.edit();

        editor.putStringSet("attivitas", new HashSet<>(attivitas));
        editor.apply();
    }
}
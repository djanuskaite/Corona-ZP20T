package com.corona.coronazp20t;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NewEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        setTitle("New Entry ");

        Intent intent = getIntent();
        Corona corona  = (Corona) intent.getSerializableExtra(Adapter.ENTRY);
        Toast.makeText(this, "Country: " + corona.getKeyId(), Toast.LENGTH_SHORT).show();

        // pasiimsim elementus is xml
        final CheckBox checkBoxLithuania = findViewById(R.id. country_lithuania);
        final CheckBox checkBoxLatvia = findViewById(R.id. country_latvia);
        final CheckBox checkBoxEstonia = findViewById(R.id. country_estonia);
        final CheckBox checkBoxPoland = findViewById(R.id. country_poland);

        final RadioGroup deaths = findViewById(R.id.deaths);
        final RadioButton radio500 = findViewById(R.id.radio500);

        final Spinner spinner = findViewById(R.id.last_update_dates);
        ArrayList <String> updateList = new ArrayList<String>();
        updateList.add(corona.getLastUpdate());
        updateList.add(getResources().getString(R.string.new_entry_date1));
        updateList.add(getResources().getString(R.string.new_entry_date2));
        updateList.add(getResources().getString(R.string.new_entry_date3));
        updateList.add(getResources().getString(R.string.new_entry_date4));
        updateList.add(getResources().getString(R.string.new_entry_date5));
        updateList.add(getResources().getString(R.string.new_entry_date6));

        //adapteris reikalingas sujungti isdestyma su sarasu
        ArrayAdapter <String> dataAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                updateList
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapteri idedame(susiejame) i spineri
        spinner.setAdapter(dataAdapter);

        final EditText editTextConfirmed = findViewById(R.id.confirmed_input);

        Button btnNewEntry = findViewById(R.id.display_selected_btn);

        //uzpildysime visus elementus coronos  informacija

        checkBoxLithuania.setText(corona.getKeyId());
        radio500.setText(String.valueOf(corona.getDeaths())); //konvertuojame int i string, nes
        // setText tikis kad mes jam duosim stringa, todel reikia papildomai konvertuoti
        editTextConfirmed.setText(String.valueOf(corona.getConfirmed()));

        //ant mygt paspaudimo rodysime vartotojo iversta informacija

        btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countries = "";
                if(checkBoxLithuania.isChecked()) {
                    // countries = countries + kazkas
                    countries += checkBoxLithuania.getText().toString() + ", ";
                }
                if (checkBoxLatvia.isChecked()) {
                    countries += checkBoxLatvia.getText().toString() + ", ";
                }
                if (checkBoxEstonia.isChecked()) {
                    countries += checkBoxEstonia.getText().toString() + ", ";
                }
                if (checkBoxPoland.isChecked()) {
                    countries += checkBoxPoland.getText().toString() + ", ";
                }

                //gauname pasirinkta radio buttona is radio group'o
                int selectedRadioGroupId = deaths.getCheckedRadioButtonId();

                //surandam radio button pg grazinta id
                RadioButton selectedButton = (RadioButton) findViewById(selectedRadioGroupId);
                int deaths = Integer.parseInt(selectedButton.getText().toString());

                String updateDate = String.valueOf(spinner.getSelectedItem());

                String confirmed = editTextConfirmed.getText().toString();

                //issivalom klaidu pranesima
                editTextConfirmed.setError(null);
                if (Validation.isValidNumber(confirmed)) {
                  // public Corona(String lastUpdate, String keyId, int confirmed, int deaths) {
                    Corona corona = new Corona(updateDate, countries, Integer.parseInt(confirmed), deaths);
                    //atvaizduojama vartotojui objekto informacija
                    Toast.makeText(
                            NewEntryActivity.this,
                            "Country(ies): " + corona.getKeyId() + "\n" +
                            "Last Update: " + corona.getLastUpdate() + "\n" +
                            "Confirmed: " + corona.getConfirmed() + "\n" +
                            "Deaths: " + corona.getDeaths(),
                            Toast.LENGTH_SHORT
                    ).show();
                // blogai ivesti confirmed duomenys
                } else {
                    editTextConfirmed.setError(getResources().getString(R.string.new_entry_invalid_confirmed));
                    editTextConfirmed.requestFocus(); // kad klaida 'blykciotu'
                }

            }
        });


    }
}
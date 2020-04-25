package com.example.prak_3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class EndActivity extends AppCompatActivity {
    Spinner sp;
    Spinner sp0;
    Button bt;
    EditText name;
    EditText desc;
    EditText quantity;
    DbHelper dbHelper;
    List<Info> lt;
    List<String> names;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.end_activity);

        sp = findViewById(R.id.spinner);
        sp0 = findViewById(R.id.spinner0);
        bt = findViewById(R.id.button);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.description);
        quantity = findViewById(R.id.quantity);
        dbHelper = new DbHelper(this);
        lt = dbHelper.getAllInfo();
        names = new ArrayList<>();
        for(Info item : lt){
            names.add(item.getName() + " (id = " + Integer.toString(item.getId()) + ")");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"Add", "Update", "Delete"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp0.setAdapter(adapter0);
        sp.setPrompt("Name");
        sp0.setPrompt("Action");
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setText(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int activePos = sp.getSelectedItemPosition();
                int actionPos = sp0.getSelectedItemPosition();

                if(((name.getText().length() == 0) || (desc.getText().length() == 0) ||
                        (quantity.getText().length() == 0)) && (actionPos != 2)){
                    Toast toast = Toast.makeText(EndActivity.this,
                            "Как минимум одно из полей не заполнено!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    return;
                }
                String text = "";
                String s1 = name.getText().toString();
                String s2 = desc.getText().toString();
                String s3 = quantity.getText().toString();
                Info dt = lt.get(activePos);
                switch(actionPos){
                    case 0:
                        text = "Товар добавлен!"; setText(activePos);
                        break;
                    case 1:
                        text = "Информация о товаре изменена!";
                        dt.setSwitcher(false);
                        bt.setEnabled(false);
                        break;
                    case 2:
                        text = "Товар удалён!";
                        dt.setSwitcher(false);
                        bt.setEnabled(false);
                        break;
                }

                new MyAsync(dt, text, actionPos, activePos).execute(new String[]{s1, s2, s3});
            }
        });
    }

    public void setText(int position){
        Info dt = lt.get(position);

        if(dt.getSwitcher()) bt.setEnabled(true);
        else bt.setEnabled(false);

        name.clearFocus();
        name.setText(dt.getName());
        desc.clearFocus();
        desc.setText(dt.getDescription());
        quantity.clearFocus();
        String s = Integer.toString(dt.getQuantity());
        quantity.setText(s);
    }

    public String createStr(String name, int id){
        return name + " (id = " + id + ")";
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.first)
        {
            startActivity(new Intent(EndActivity.this, FrontActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }

    class MyAsync extends AsyncTask<String, Void, Void>
    {
        Info dt;
        String text;
        int actionPos;
        int activePos;

        public MyAsync(Info dt, String text, int actionPos, int activePos){
            this.dt = dt;
            this.text = text;
            this.actionPos = actionPos;
            this.activePos = activePos;
        }
        @Override
        protected Void doInBackground(String [] s) {
            try {
                int pause = (new Random().nextInt(3) + 3) * 1000;
                sleep(pause);

                dt.setSwitcher(true);
                if(actionPos == 0){
                    int num = Integer.parseInt(s[2]);
                    int id = dbHelper.insert(num, s[0], s[1]);
                    lt.add(new Info(s[0], s[1], id, num));
                    names.add(createStr(s[0], id));
                }
                else if(actionPos == 1){
                    int num = Integer.parseInt(s[2]);
                    dt.setQuantity(num);
                    dt.setName(s[0]);
                    dt.setDescription(s[1]);
                    dbHelper.update(dt);
                    names.set(activePos, createStr(s[0], dt.getId()));
                }
                else if(actionPos == 2){
                    dbHelper.delete(dt.getId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void v) {
            Toast toast = Toast.makeText(EndActivity.this, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();

            if(actionPos != 0){
                if(sp.getSelectedItemPosition() == activePos){
                    bt.setEnabled(true);
                }
            }

            if(actionPos == 2){
                if(sp.getSelectedItemPosition() == activePos){
                    if(activePos == 0) setText(1);
                    else if(activePos == lt.size() - 1) setText(activePos - 1);
                    else setText(activePos + 1);
                }
                lt.remove(activePos);
                names.remove(activePos);
            }
            adapter.notifyDataSetChanged();
        }
    }
}

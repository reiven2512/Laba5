package com.example.prak_3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Random;

import static com.example.prak_3.Frag.pagerAdapter;
import static java.lang.Thread.sleep;

public class FragItem extends Fragment {
    private List<Info> data;
    private int pos;
    TextView textView1;
    Button bt;

    public static FragItem newInstance(List<Info> data, int pos) {
        FragItem fragment = new FragItem();
        fragment.data = data;
        fragment.pos = pos;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag, container, false);

        TextView textView = view.findViewById(R.id.Title);
        textView1 = view.findViewById(R.id.Quantity);
        TextView textView2 = view.findViewById(R.id.Description);
        bt = view.findViewById(R.id.Button);
        final Info dt = data.get(pos);
        if(dt.getSwitcher()) bt.setEnabled(true);
        else bt.setEnabled(false);
        textView.setText(dt.getName());
        textView2.setText(dt.getDescription());

        String s;
        s = "Количество: " + dt.getQuantity();
        textView1.setText(s);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt.setSwitcher(false);
                pagerAdapter.notifyDataSetChanged();
                new MyAsync().execute(dt);
            }
        });
        return view;
    }

    class MyAsync extends AsyncTask<Info, Void, Integer>
    {
        Info dt;
        @Override
        protected Integer doInBackground(Info... dts) {
            dt = dts[0];
            DbHelper dbHelper = new DbHelper(getContext());
            int a = dt.getQuantity() - 1;
            try {
                int pause = (new Random().nextInt(3) + 3) * 1000;
                sleep(pause);
                dt.setQuantity(a);
                dbHelper.update(dt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return a;
        }
        protected void onPostExecute(Integer a) {
            Toast toast = Toast.makeText(getContext(), "Товар " + dt.getName() + " куплен!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();

            if(a != 0){
                String s = "Количество: " + a;
                textView1.setText(s);
                dt.setSwitcher(true);
            }
            else{
                data.remove(dt);
            }
            pagerAdapter.notifyDataSetChanged();
        }
    }
}

package com.myapplicationdev.android.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Boolean loggedIn = false;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        // Defined Array values to show in ListView
        String[] values = new String[]{"Singapore National Day is on 9 Aug",
                "Singapore is 53 years old",
                "Theme is 'We are Singapore'"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", null);
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.sendtofriend) {

            final String[] list = new String[]{"Email", "SMS"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            String str = "";
                            if (which == 0) {
//                                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                                intent.setType("text/plain");
//                                intent.putExtra(Intent.EXTRA_SUBJECT, "Test Email from C347");
//                                intent.putExtra(Intent.EXTRA_TEXT, "P11- KnowYourNatinalDay");
//                                intent.setData(Uri.parse("mailto:jason_lim@rp.edu.sg"));
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
                                str =  "Email Sent";
                            } else {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 81560848));
//                                intent.putExtra("sms_body", "P11- KnowYourNatinalDay");
//                                startActivity(intent);
                                str =  "SMS Sent";
                            }
                            Snackbar sb = Snackbar.make(listView, str, Snackbar.LENGTH_SHORT);

                            sb.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });

                            sb.show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.quiz) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout quiz = (LinearLayout) inflater.inflate(R.layout.quiz, null);
            final RadioGroup rg1 = (RadioGroup) quiz.findViewById(R.id.rg1);
            final RadioGroup rg2 = (RadioGroup) quiz.findViewById(R.id.rg2);
            final RadioGroup rg3 = (RadioGroup) quiz.findViewById(R.id.rg3);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(quiz)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            int score = 0;
                            if(rg1.getCheckedRadioButtonId() == R.id.rdNationalDayNo){
                                score++;
                            }
                            if(rg2.getCheckedRadioButtonId() == R.id.rdOldYes){
                                score++;
                            }
                            if(rg3.getCheckedRadioButtonId() == R.id.rdthemeYes){
                                score++;
                            }
                            Toast.makeText(MainActivity.this, "Your score is : " + score, Toast.LENGTH_LONG).show();

                        }
                    })
                    .setNegativeButton("Don't Know LAH", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("loggedIn", loggedIn);
        editor.apply();


    }

    @Override
    protected void onResume() {
        super.onResume();

        loggedIn = prefs.getBoolean("loggedIn", false);
        if (!loggedIn) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout passPhrase = (LinearLayout) inflater.inflate(R.layout.passphrase, null);
            final EditText etPassphrase = (EditText) passPhrase.findViewById(R.id.editTextPassPhrase);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Login")
                    .setView(passPhrase)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // Toast.makeText(MainActivity.this, "You had entered " + etPassphrase.getText().toString(), Toast.LENGTH_LONG).show();
                            if (etPassphrase.getText().toString().equals("738964")) {
                                loggedIn = true;
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong access code.Try again!" + etPassphrase.getText().toString(), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    })
                    .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}

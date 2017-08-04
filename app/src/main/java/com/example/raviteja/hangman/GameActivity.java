package com.example.raviteja.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//"@mipmap/ic_launcher"

public class GameActivity extends AppCompatActivity {


    // 0 - 20 animals //
    // 21 - 38 fruits //
    String[] strings = {"buffalo","cow","hen","cat","dog","elephant","fox","goose","horse","jaguar","kangaroo","lion","mouse","mongoose","ox","rat","rabbit","rhinoceros","sheep","tiger","yak",
                        "apple","banana","cherry","dates","grapes", "gooseberry", "guava","jackfruit","kiwi","lemon","mango","orange","olive","peach","papaya","pomegranate",
                        "strawberry","watermelon"};
    Button ok;
    EditText input_val;
    ImageView game_image_view;
    LinearLayout my_output_layout;
//    TextView hint;
    int wrong_attempts = 0,successfull_attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // components initialisation //
        ok = (Button)findViewById(R.id.input_button);
        input_val = (EditText)findViewById(R.id.input_value);
        game_image_view = (ImageView)findViewById(R.id.game_images);
        my_output_layout = (LinearLayout)findViewById(R.id.output_layout);
//        hint = (TextView)findViewById(R.id.hint);
        // components initialisation //

        int index = (int)(Math.random()*strings.length); //generating random index between 0 to length of input strings array //
        final String selected_string = strings[index]; // extracting the corresponding string with generated random index //
        final ArrayList<String> used_characters = new ArrayList<>();

//        if(index>=0 && index<=20)
//            hint.setText("Animal");
//        else if(index>=21 && index<=38)
//            hint.setText("Fruit");


        // for dynamically adding the text views with the length of selected_String //
        final TextView[] myTextViews = new TextView[selected_string.length()];
        for (int i = 0; i < selected_string.length(); i++)
        {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            rowTextView.setText("*");
            rowTextView.setTextColor(0xFF303F9F);
            rowTextView.setTextSize(20);
            // add the textview to the linearlayout
            my_output_layout.addView(rowTextView);
            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
        // for dynamically adding the text views with the length of selected_String //

        ok.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                try{
                    String entered = input_val.getText().toString(); // input character entered by the user //
                    input_val.setText(""); // removing the character from the display //
                    if(entered.matches("")) // when the user press the OK button without enetering any character //
                    {
                        Toast.makeText(GameActivity.this,"Enter any character and then press OK",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(used_characters.contains(entered))
                    {
                        Toast.makeText(GameActivity.this,"USED",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    used_characters.add(entered);

                    int matched = 0;

                    for(int i=0;i<selected_string.length();i++)
                    {
                        if(String.valueOf(selected_string.charAt(i)).equals(entered))
                        {
                            myTextViews[i].setText(entered);
                            matched = 1;
                            successfull_attempts+=1;
                        }
                    }

                    if(matched!=1){
                        wrong_attempts+=1;
                        switch(wrong_attempts){
                            case 1 : game_image_view.setImageResource(R.drawable.first);
                                     break;
                            case 2 : game_image_view.setImageResource(R.drawable.second);
                                break;
                            case 3 : game_image_view.setImageResource(R.drawable.third);
                                break;
                            case 4 : game_image_view.setImageResource(R.drawable.fourth);
                                break;
                            case 5 : game_image_view.setImageResource(R.drawable.fifth);
                                break;
                            case 6 : game_image_view.setImageResource(R.drawable.sixth);
                                break;
                        }
                    }

                    if(successfull_attempts == selected_string.length()){
                        Intent i = new Intent(GameActivity.this,EndActivity.class);
                        i.putExtra("game_report","Congratulations You have \n\t Won");
                        startActivity(i);
                    }
                    else if(wrong_attempts == 6)
                    {
                        Intent i = new Intent(GameActivity.this,EndActivity.class);
                        i.putExtra("game_report","Oops you have lost\n\t" + selected_string);
                        startActivity(i);
                    }

                }
                catch (Exception e){
                    // do something //
                    Toast.makeText(GameActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

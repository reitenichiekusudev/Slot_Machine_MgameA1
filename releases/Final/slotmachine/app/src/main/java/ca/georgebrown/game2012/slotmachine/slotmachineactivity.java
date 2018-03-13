/**
 * Slot machine game
 *
 *
 * @author      Suyang Song, Timofei Serebriakov
 * @copyright   2018 Spinbot games
 * @license     GPL-2.0+
 * @Student IDs 101082848,
 * @Datecreated 03/09/2018
 * @Description Main activity file handling buttons and all logic
 * Version:     2.0.0
 */




package ca.georgebrown.game2012.slotmachine;

import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class slotmachineactivity extends AppCompatActivity {
    //variable initialization
    private int startingcredit = 12000;
    private int bet= 10;
    private int jackpot = 0;
    private TextView spin;
    private ImageView img1, img2, img3, Jackpothit;
    private spinner spinner1, spinner2, spinner3;
    private Button spinbttn, resetbttn, betpten, betptwenty, betm10, betm20;
    private boolean isSpinning;
    //generating random start time for each reel
    public static final Random RANDOM = new Random();

    public static long randomstart(long lower, long upper)
    {
        return lower + (long) (RANDOM.nextDouble()* (upper-lower));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //referencing xml elements
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slotmachineactivity);
        Jackpothit = (ImageView) findViewById(R.id.jackpothit);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        spinbttn = (Button) findViewById(R.id.spin);
        resetbttn = (Button) findViewById(R.id.reset);
        betpten = (Button) findViewById(R.id.plusten);
        betptwenty = (Button) findViewById(R.id.plustwenty);
        betm10 = (Button) findViewById(R.id.min10);
        betm20 = (Button) findViewById(R.id.min20);
        spin = findViewById(R.id.textView4);
        final TextView tx = (TextView)findViewById(R.id.numbers);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/BNMachine.ttf");
        final TextView tx1 = (TextView)findViewById(R.id.numbersmon);
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(),  "fonts/BNMachine.ttf");
        final TextView tx2 = (TextView)findViewById(R.id.numbersbet);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(),  "fonts/BNMachine.ttf");

        tx.setTypeface(custom_font);
        tx1.setTypeface(custom_font1);
        tx2.setTypeface(custom_font2);


        tx.setText(String.valueOf(jackpot));
        tx1.setText(String.valueOf(startingcredit));
        tx2.setText(String.valueOf(bet));
        //setting event listener for spin button
       resetbttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              bet = 10;
              startingcredit = 10000;
              jackpot = 0;
              tx.setText(String.valueOf(jackpot));
              tx1.setText(String.valueOf(startingcredit));
              tx2.setText(String.valueOf(bet));
            }
        });
       betpten.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               bet+= 10;
               tx2.setText(String.valueOf(bet));
           }
       });
        betptwenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bet+= 20;
                tx2.setText(String.valueOf(bet));
            }
        });
        betm10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bet-= 10;
                tx2.setText(String.valueOf(bet));
            }
        });
        betm20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bet-= 20;
                tx2.setText(String.valueOf(bet));
            }
        });
        spinbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSpinning == true)
                {
                    //stops the spinning if slot machine is in motion
                    spinner1.stopspin();
                    spinner2.stopspin();
                    spinner3.stopspin();
                    if(spinner1.currspinindex == spinner2.currspinindex && spinner2.currspinindex == spinner3.currspinindex) {
                        Jackpothit.setImageResource(R.drawable.jakepot);
                        jackpot = 0;
                        startingcredit+=jackpot;
                        tx.setText(String.valueOf(jackpot));
                        tx1.setText(String.valueOf(startingcredit));
                    }
                    else
                    {
                        spin.setText("you lose");
                    }
                    isSpinning = false;
                }
                else
                {
                    Jackpothit.setImageResource(android.R.color.transparent);
                    startingcredit-= bet;
                    jackpot += bet;
                    tx1.setText(String.valueOf(startingcredit));
                    tx.setText(String.valueOf(jackpot));
                    //this instantiates a new spinner object
                    spinner1 = new spinner(new spinner.spinlistener() {
                        @Override
                        public void newimgs(final int img) {
                                //we make a thread to handle independent spinning of the reels, which has the
                            //handling of the image changes in the spinner class
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        img1.setImageResource(img);
                                    }
                                });
                        }
                    },200, randomstart(0,200)//randomly starts between 0-200 ms
                             );
                    spinner1.start();
                    //same code for other 2 spinners
                    spinner2 = new spinner(new spinner.spinlistener() {
                        @Override
                        public void newimgs(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img2.setImageResource(img);
                                }
                            });
                        }
                    },200, randomstart(150,400));
                    spinner2.start();

                    spinner3 = new spinner(new spinner.spinlistener() {
                        @Override
                        public void newimgs(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img3.setImageResource(img);
                                }
                            });
                        }
                    },200, randomstart(250,700));
                    spinner3.start();
                    isSpinning = true;
                }

            }
        });
    }
}

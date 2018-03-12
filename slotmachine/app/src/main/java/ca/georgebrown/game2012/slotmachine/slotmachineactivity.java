package ca.georgebrown.game2012.slotmachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class slotmachineactivity extends AppCompatActivity {
    //variable initialization
    private TextView spin;
    private ImageView img1, img2, img3;
    private spinner spinner1, spinner2, spinner3;
    private Button spinbttn;
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
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        spinbttn = (Button) findViewById(R.id.button);
        spin = findViewById(R.id.textView4);
        //setting event listener for spin button
        spinbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSpinning == true)
                {
                    //stops the spinning if slot machine is in motion
                    spinner1.stopspin();
                    spinner2.stopspin();
                    spinner3.stopspin();
                    if(spinner1.currspinindex == spinner2.currspinindex && spinner2.currspinindex == spinner3.currspinindex)
                        spin.setText("you win the jackpot!");
                    else
                    {
                        spin.setText("you lose");
                    }
                    isSpinning = false;
                }
                else
                {
                    //this bit is somewhat convoluted... this instantiates a new spinner object
                    //pass into it a spinlistener object, which can't be instantiated due to it
                    //being an interface, we make an anonymous class which implements the interface
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

package ca.georgebrown.game2012.slotmachine;

/**
 * Created by suyang on 2018-03-11.
 */

public class spinner extends Thread {
    interface spinlistener {
        void newimgs(int img);
    }
    private static int [] imgs = {R.drawable.image_01, R.drawable.image_02, R.drawable.image_03,
    R.drawable.image_04, R.drawable.image_05};
    public int currspinindex;
    private spinlistener spinlistener;
    private long framelength;
    private long startdelay;
    private boolean isSpinning;

    spinner(spinlistener spinlistener, long framelength, long startdelay)
    {
        this.spinlistener = spinlistener;
        this.framelength = framelength;
        this.startdelay = startdelay;
        currspinindex = 0;
        isSpinning = true;
    }
    public void NextImg()
    {
        currspinindex++;
        if (currspinindex == imgs.length)
            currspinindex = 0;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(startdelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(isSpinning)
        {
            try {
                Thread.sleep(framelength);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NextImg();

            if (spinlistener != null)
                spinlistener.newimgs(imgs[currspinindex]);
        }
    }
    public void stopspin()
    {
        isSpinning = false;
    }


}

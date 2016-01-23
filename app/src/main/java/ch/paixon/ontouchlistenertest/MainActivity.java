package ch.paixon.ontouchlistenertest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    RelativeLayout relativeLayout;

    float xStart;
    float yStart;

    float xEnd;
    float yEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.drawingLayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        relativeLayout.setOnTouchListener(new CustomerListener());
    }

    private View findView(float x, float y) {
        int numberOfChilds = relativeLayout.getChildCount();

        for (int i = 0; i < numberOfChilds; i++) {
            View child = relativeLayout.getChildAt(i);
            Rect bounds = new Rect();
            child.getHitRect(bounds);
            if (bounds.contains((int) x, (int) y)) {
                return child;
            }
        }

        return null;
    }

    class CustomerListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xStart = event.getX() + v.getX();
                    yStart = event.getY() + v.getY();
                    xEnd = event.getX() + v.getX();
                    yEnd = event.getY() + v.getY();

                    findView(event.getX(), event.getY()).setBackgroundColor(Color.BLUE);
                    break;

                case MotionEvent.ACTION_MOVE:
                    xEnd = event.getX() + v.getX();
                    yEnd = event.getY() + v.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    xEnd = event.getX() + v.getX();
                    yEnd = event.getY() + v.getY();

                    findView(event.getX(), event.getY()).setBackgroundColor(Color.YELLOW);
                    break;
            }

            drawLine();
            return true;
        }
    }

    ;

    public void drawLine() {
        imageView.setImageResource(0);

        Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        imageView.draw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(8);
        paint.setColor(Color.GREEN);
        canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
        imageView.setImageBitmap(bitmap);
    }
}

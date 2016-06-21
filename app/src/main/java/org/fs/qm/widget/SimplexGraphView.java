package org.fs.qm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.fs.qm.model.Line;
import org.fs.qm.model.Position;
import org.fs.util.Collections;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fatih on 21/06/16.
 * as org.fs.qm.widget.SimplexGraphView
 */
public class SimplexGraphView extends ImageView {

    private OnAttachStateListener listener;
    private List<Line>            lines;

    private static final float STOKE_WIDTH = 2.0f;
    private static final float PADDING     = 5.0f;

    private float   density;
    private float   widgetPadding;

    private float   strokeWidth;
    private int     strokeColor;

    private float   lineWidth;
    private float   lineHeight;

    private Paint   pLine;
    private Paint   pConstraint;
    private Paint   pFeasible;

    private double  maxY;
    private double  maxX;

    public SimplexGraphView(Context context) {
        super(context);
        init();
    }

    public SimplexGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimplexGraphView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (listener != null) {
            listener.onAttached(this);
        }
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (listener != null) {
            listener.onDetached(this);
        }
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(w, h);

        //calculate those value
        this.lineWidth = min - left();//padding and widget padding
        this.lineHeight = min - top();//padding and widget padding

        setMeasuredDimension(min, min);
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.drawLines(lineForY(), pLine);
        canvas.drawLines(lineForX(), pLine);
        if(!Collections.isNullOrEmpty(lines)) {
            for (Line l : lines) {
                float[] items = lineForLine(l);
                Log.println(Log.ERROR, getClassTag(), "start************");
                for (float indicy : items) {
                    Log.println(Log.ERROR, getClassTag(), String.valueOf(indicy));
                }
                Log.println(Log.ERROR, getClassTag(), "************end");
                canvas.drawLines(items, pConstraint);
            }
        }
    }

    //listener setter
    public void setOnAttachStateListener(OnAttachStateListener listener) {
        this.listener = listener;
    }

    public void setLines(@NonNull Line... array) {
        setLines(Arrays.asList(array));
    }

    public void setLines(@NonNull List<Line> lines) {
        this.lines = lines;
        findMaxXandY();
        invalidate();
    }

    float[] lineForY() {
        return new float[] {
                left(),                    //startX
                top(),                     //startY
                right(),                   //endX
                lineHeight                 //endY
        };
    }

    float[] lineForX() {
        return new float[] {
                left(),                   //startX
                lineHeight,               //startY
                lineWidth,                //endX
                lineHeight                //endY
        };
    }

    //TODO implement intersection and feasible religion finder
    float[] lineForLine(Line line) {
        Position p1 = line.getFirst();
        Position p2 = line.getSecond();
//        log(Log.ERROR,
//            String.format(Locale.getDefault(),
//                          "p1 --> x: %f\ty: %f", p1.getX(), p1.getY()));
//        log(Log.ERROR,
//            String.format(Locale.getDefault(),
//                          "p2 --> x: %f\ty: %f", p2.getX(), p2.getY()));

        if(line.isFirstEqualsSecond()) {
            if(p1.getX() == 0d) {
                //x zero
                return new float[] {
                        left(),
                        lineHeight - positionY(p1.getY()) + top(),
                        lineWidth,
                        lineHeight - positionY(p1.getY()) + top(),
                };
            } else {
                //y zero
                return new float[] {
                        positionX(p1.getX()) + left(),
                        top(),
                        positionX(p1.getX()) + left(),
                        lineHeight
                };
            }
        } else {
            //means two different position
            return new float[] {
                    positionX(p1.getX()),                                        //startX
                    lineHeight - positionY(p1.getY()),                           //startY
                    positionX(p2.getX()) + right(),                              //endX
                    lineHeight + top() - positionY(p2.getY())                    //endY
            };
        }
    }

    float left() {
        return getPaddingLeft() + widgetPadding;
    }

    float top() {
        return getPaddingTop() + widgetPadding;
    }

    float bottom() {
        return getPaddingBottom() + widgetPadding;
    }

    float right() {
        return getPaddingRight() + widgetPadding;
    }

    float positionX(double x) {
        return Math.round(lineWidth / maxX * x);
    }

    float positionY(double y) {
        return Math.round(lineHeight / maxY * y);
    }

    void findMaxXandY() {
        //init with defaults
        maxX = 0; maxY = 0;
        for (Line l : lines) {
            Position p1 = l.getFirst();
            Position p2 = l.getSecond();
            maxY = maxY < p1.getY() ? p1.getY() : maxY < p2.getY() ? p2.getY() : maxY;
            maxX = maxX < p1.getX() ? p1.getX() : maxX < p2.getX() ? p2.getX() : maxX;
        }
    }

    void init() {
        this.density = getResources().getDisplayMetrics().density;
        this.strokeWidth = STOKE_WIDTH * this.density;
        this.strokeColor = Color.BLACK;
        this.widgetPadding = PADDING * this.density;
        loadLinePaint();
        loadConstraintPaint();
    }

    void loadLinePaint() {
        pLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        pLine.setColor(strokeColor);
        pLine.setStyle(Paint.Style.STROKE);
        pLine.setStrokeWidth(strokeWidth);
        pLine.setStrokeJoin(Paint.Join.ROUND);
    }

    void loadConstraintPaint() {
        pConstraint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pConstraint.setColor(Color.parseColor("#888888"));
        pConstraint.setStyle(Paint.Style.STROKE);
        pConstraint.setStrokeWidth(1.25f * density);
        pConstraint.setStrokeJoin(Paint.Join.ROUND);
    }

    protected void log(String msg) {
        log(Log.DEBUG, msg);
    }

    protected void log(Exception error) {
        StringWriter strWriter = new StringWriter(128);
        PrintWriter prtWriter = new PrintWriter(strWriter);
        error.printStackTrace(prtWriter);
        log(Log.ERROR, strWriter.toString());
    }

    protected void log(int lv, String msg) {
        if (isLogEnabled()) {
            Log.println(lv, getClassTag(), msg);
        }
    }

    protected String getClassTag() {
        return SimplexGraphView.class.getSimpleName();
    }

    protected boolean isLogEnabled() {
        return true;
    }

    //attached state listener
    public interface OnAttachStateListener {
        void onAttached(View view);
        void onDetached(View view);
    }
}
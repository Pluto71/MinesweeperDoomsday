package com.example.dgoodell.doomsday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dgoodell on 9/26/16.
 */
public class BoardView extends View{

    private Bitmap backGround;
    private Bitmap flag;
    private Bitmap explosion;
    private Bitmap fakeBomb;
    private Bitmap saved;
    private Bitmap zero;
    private Bitmap one;
    private Bitmap two;
    private Bitmap three;
    private Bitmap four;
    private Bitmap five;
    private Bitmap six;
    private Bitmap seven;
    private Bitmap eight;



    private Paint paintLine;
    private Paint paintText;
    private boolean flagMode;
    private boolean gameActive;
    private boolean playerLost;
    private boolean allIsWell;
    private int dim;
    private int bombNum;


    // The square of int dimension is the number of boxes in the field
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        backGround = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fire);
        flag = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.flagtwo);
        explosion = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bomb);
        fakeBomb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fakebomb);
        saved = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.saved);
        zero = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.zero);
        one = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.one);
        two = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.two);
        three = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.three);
        four = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.four);
        five = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.five);
        six = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.six);
        seven = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.seven);
        eight = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.eight);

        dim = 6;
        BoardRep.getInstance().createBoxArrays(dim);
        BoardRep.getInstance().shuffleArray();
        BoardRep.getInstance().setBoardRep(dim);

        flagMode = false;
        gameActive = true;
        playerLost = false;
        allIsWell = false;

        paintLine = new Paint();
        paintLine.setColor(Color.CYAN);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(3);


        paintText = new Paint();
        paintText.setColor(Color.RED);
        // Determine text size based on how small the squares will be
        if (dim == 4) {
            paintText.setTextSize(80);
            bombNum = 3;
        } else if (dim == 5) {
            paintText.setTextSize(60);
            bombNum = 5;
        } else if (dim == 6) {
            paintText.setTextSize(40);
            bombNum = 7;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        backGround = Bitmap.createScaledBitmap(backGround, getWidth(), getHeight(), false);
        saved = Bitmap.createScaledBitmap(saved, getWidth(), getHeight(), false);

        int scaledDim = getWidth()/dim;

        flag = Bitmap.createScaledBitmap(flag, scaledDim, scaledDim, false);
        explosion = Bitmap.createScaledBitmap(explosion, scaledDim, scaledDim, false);
        fakeBomb = Bitmap.createScaledBitmap(fakeBomb, scaledDim, scaledDim, false);
        zero = Bitmap.createScaledBitmap(zero, scaledDim, scaledDim, false);
        one = Bitmap.createScaledBitmap(one, scaledDim, scaledDim, false);
        two = Bitmap.createScaledBitmap(two, scaledDim, scaledDim, false);
        three = Bitmap.createScaledBitmap(three, scaledDim, scaledDim, false);
        four = Bitmap.createScaledBitmap(four, scaledDim, scaledDim, false);
        five = Bitmap.createScaledBitmap(five, scaledDim, scaledDim, false);
        six = Bitmap.createScaledBitmap(six, scaledDim, scaledDim, false);
        seven = Bitmap.createScaledBitmap(seven, scaledDim, scaledDim, false);
        eight = Bitmap.createScaledBitmap(eight, scaledDim, scaledDim, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(backGround, 0, 0, null);

        drawGameField(canvas);

        drawChecked(canvas);

        allIsWell = checkSafety(BoardRep.getInstance());

        if(allIsWell){
            drawWorldPeace(canvas);
        }

        if(playerLost){
            drawWorldEnds(canvas);
        }
    }

    private void drawGameField(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // Set the grid
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        int dimMinusOne = dim-1;
        for (int i = 0; i < dimMinusOne; i++) {
            int i0 = (i + 1) * getWidth() / dim;
            int i1 = (i + 1) * getHeight() / dim;
            canvas.drawLine(0, i1, getWidth(), i1, paintLine);
            canvas.drawLine(i0, 0, i0, getHeight(), paintLine);
        }
    }

    private void drawChecked(Canvas canvas) {
        BoardRep model = BoardRep.getInstance();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                float centerX = i * getWidth() / dim;
                float centerY = j * getHeight() / dim;

                if (model.getBoxAtIndex(i, j).getCounter() == 0) {
                    drawNoBombs(canvas, model.getBoxAtIndex(i, j), centerX, centerY);

                } else if (model.getBoxAtIndex(i, j).getCounter() == 9) {
                    drawFlag(canvas, centerX, centerY);

                } else {
                    drawNumber(canvas, model.getBoxAtIndex(i, j).getCounter(), centerX, centerY);

                }
            }
        }
    }

    private void drawNumber(Canvas canvas, int counter, float x, float y){
        if(counter==1){
            canvas.drawBitmap(one, x, y, null);
        } else if(counter==2){
            canvas.drawBitmap(two, x, y, null);
        } else if(counter==3){
            canvas.drawBitmap(three, x, y, null);
        } else if(counter==4){
            canvas.drawBitmap(four, x, y, null);
        } else if(counter==5){
            canvas.drawBitmap(five, x, y, null);
        } else if(counter==6){
            canvas.drawBitmap(six, x, y, null);
        } else if(counter==7){
            canvas.drawBitmap(seven, x, y, null);
        } else if(counter==8){
            canvas.drawBitmap(eight, x, y, null);
        }
    }

    private void drawNoBombs(Canvas canvas, Box box, float x, float y){
        canvas.drawBitmap(zero, x, y, null);
    }

    private void drawFlag(Canvas canvas, float x, float y) {
        canvas.drawBitmap(flag, x, y, null);

    }

    private void drawWorldEnds(Canvas canvas){
        BoardRep model = BoardRep.getInstance();
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim;j++){

                float centerX = i * getWidth() / dim;
                float centerY = j * getHeight() / dim;

                if(model.getBoxAtIndex(i, j).hasBomb()) {
                    if(model.getBoxAtIndex(i, j).hasFlag()) {
                        canvas.drawBitmap(flag, centerX, centerY, null);
                    } else {
                        canvas.drawBitmap(explosion, centerX, centerY, null);
                    }

                } else if(model.getBoxAtIndex(i, j).hasFlag()){
                    canvas.drawBitmap(fakeBomb, centerX, centerY, null);
                }
            }
        }
    }

    private void drawWorldPeace(Canvas canvas) {
        canvas.drawBitmap(saved, 0, 0, null);
    }

    public void flagModeON() {
        flagMode = true;
    }

    public void flagModeOFF() {
        flagMode = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && gameActive) {

            int tX = ((int) event.getX()) / (getWidth() / dim);
            int tY = ((int) event.getY()) / (getHeight() / dim);

            BoardRep model = BoardRep.getInstance();

            if (tX < dim && tY < dim) {
                if (!flagMode){
                    if(!model.getBoxAtIndex(tX, tY).hasFlag()) {

                        // If the clicked square doesn't have a bomb and isn't searched,
                        // check its perimeter
                        if (!(model.getBoxAtIndex(tX, tY).hasBomb())) {
                            if (!(model.getBoxAtIndex(tX, tY).isSearched())) {
                                model.check(model.getBoxAtIndex(tX, tY), dim);
                            }

                            // If the clicked square does have a bomb, game over.
                        } else if (model.getBoxAtIndex(tX, tY).hasBomb()) {
                            loseGame(model);
                            invalidate();
                        }
                    }
                } else if (!(model.getBoxAtIndex(tX, tY).isSearched())) {
                    model.getBoxAtIndex(tX, tY).changeHasFlag();
                } invalidate();
            }
        }
        return true;
    }

    public boolean checkSafety(BoardRep model) {
        int n = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!model.getBoxAtIndex(i, j).isSearched()){
                    n++;
                }
            }
        } if(n == bombNum){
            showYouWin();
            gameActive = false;
            return true;
        } else {
            return false;
        }
    }


    public void loseGame(BoardRep model){
        gameActive = false;
        playerLost = true;
        showYouLose();
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){

                model.getBoxAtIndex(i, j).setGameOver();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void restartGame() {
        resetBoard(dim);
        playerLost = false;
        allIsWell = false;
        gameActive = true;
        invalidate();
    }


    public void resetBoard(int dim){
        BoardRep.getInstance().createBoxArrays(dim);
        BoardRep.getInstance().shuffleArray();
        BoardRep.getInstance().setBoardRep(dim);
    }


    private void showYouWin() {
        ((MainActivity)getContext()).showSimpleSnackbarMessage(getContext().getString(R.string.you_win_text));
    }

    private void showYouLose() {
        ((MainActivity)getContext()).showSimpleSnackbarMessage(getContext().getString(R.string.you_lose_text));
    }

}

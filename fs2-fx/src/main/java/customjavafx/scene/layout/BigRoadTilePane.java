package customjavafx.scene.layout;


import customjavafx.scene.control.BeadRoadResult;
import customjavafx.scene.control.BigRoadLabel;
import customjavafx.scene.control.BigRoadResult;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;

public class BigRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;
    public IntegerProperty count = new SimpleIntegerProperty(0);

    public BigRoadTilePane() {
        super();
        super.setOrientation(Orientation.VERTICAL);
        super.setAlignment(Pos.TOP_LEFT);
    }

    private int sizeLimit() {
        return (getPrefColumns() * getPrefRows());
    }

    private boolean childrenLimitReached() {
        return (super.getChildren().size() == sizeLimit());
    }


    private void Insert() {
        BigRoadLabel temp = new BigRoadLabel(BigRoadResult.EMPTY);
        temp.setResult(BigRoadResult.EMPTY);
        super.getChildren().add(temp);
    }

    private void InsertElement(BigRoadLabel e) {
        if(row < 6) {
            row++;
        }else {
            row = 0;
            column ++;
        }
        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(e.getResult());
    }

    public void Initialize() {
        while (!childrenLimitReached()) {
            Insert();
        }
    }

    public void Initialize(int row, int column) {
        super.setPrefRows(row);
        super.setPrefColumns(column);
        while (!childrenLimitReached()) {
            Insert();
        }
        this.column = 0;
        this.row = -1;
    }


    public void AddElement(BeadRoadResult next) {
        MoveToNextPositionFront(next);
        if(getPosition() >= (sizeLimit() - 1)) return;
        switch (next) {
            case BANKER_WIN:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.BANKER_WIN);
                break;

            case BANKER_WIN_BANKER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.BANKER_WIN_BANKER_PAIR);
                break;

            case BANKER_WIN_PLAYER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.BANKER_WIN_PLAYER_PAIR);
                break;

            case BANKER_WIN_BOTH_PAIR:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.BANKER_WIN_BOTH_PAIR);
                break;

            case PLAYER_WIN:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.PLAYER_WIN);
                break;

            case PLAYER_WIN_BANKER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.PLAYER_WIN_BANKER_PAIR);
                break;

            case PLAYER_WIN_PLAYER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.PLAYER_WIN_PLAYER_PAIR);
                break;

            case PLAYER_WIN_BOTH_PAIR:
                ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.PLAYER_WIN_BOTH_PAIR);
                break;

            case TIE_WIN:
            case TIE_WIN_BANKER_PAIR:
            case TIE_WIN_PLAYER_PAIR:
            case TIE_WIN_BOTH_PAIR: {
                switch (((BigRoadLabel) super.getChildren().get(getPosition())).getResult()) {
                    case BANKER_WIN:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN);
                        break;
                    case BANKER_WIN_BANKER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN_BANKER_PAIR);
                        break;
                    case BANKER_WIN_PLAYER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN_PLAYER_PAIR);
                        break;
                    case BANKER_WIN_BOTH_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN_BOTH_PAIR);
                        break;
                    case PLAYER_WIN:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN);
                        break;
                    case PLAYER_WIN_BANKER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN_BANKER_PAIR);
                        break;
                    case PLAYER_WIN_PLAYER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN_PLAYER_PAIR);
                        break;
                    case PLAYER_WIN_BOTH_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN_BOTH_PAIR);
                        break;
                    default:
                }
                break;
            }

            default:
                break;
        }
    }

    public int getSize() {
        return getPosition() + 1;
    }

    public int getPosition() {
        return (column * getPrefRows()) + row;
    }

    private boolean isCurrentWinRed() {
        switch (((BigRoadLabel) super.getChildren().get(getPosition())).getResult()) {
            case BANKER_WIN:
            case BANKER_WIN_BANKER_PAIR:
            case BANKER_WIN_PLAYER_PAIR:
            case BANKER_WIN_BOTH_PAIR:
            case TIE_AFTER_BANKER_WIN:
            case TIE_AFTER_BANKER_WIN_BANKER_PAIR:
            case TIE_AFTER_BANKER_WIN_PLAYER_PAIR:
            case TIE_AFTER_BANKER_WIN_BOTH_PAIR:
                return true;
            default:
                return false;
        }
    }

    private boolean isCurrentWinBlue() {
        switch (((BigRoadLabel) super.getChildren().get(getPosition())).getResult()) {
            case PLAYER_WIN:
            case PLAYER_WIN_BANKER_PAIR:
            case PLAYER_WIN_PLAYER_PAIR:
            case PLAYER_WIN_BOTH_PAIR:
            case TIE_AFTER_PLAYER_WIN:
            case TIE_AFTER_PLAYER_WIN_BANKER_PAIR:
            case TIE_AFTER_PLAYER_WIN_PLAYER_PAIR:
            case TIE_AFTER_PLAYER_WIN_BOTH_PAIR:
                return true;
            default:
                return false;
        }
    }

    private boolean isNextWinRed(BeadRoadResult win) {
        switch (win) {
            case BANKER_WIN:
            case BANKER_WIN_BANKER_PAIR:
            case BANKER_WIN_PLAYER_PAIR:
            case BANKER_WIN_BOTH_PAIR:
                return true;
            default:
                return false;
        }
    }

    private boolean isNextWinBlue(BeadRoadResult win) {
        switch (win) {
            case PLAYER_WIN:
            case PLAYER_WIN_BANKER_PAIR:
            case PLAYER_WIN_PLAYER_PAIR:
            case PLAYER_WIN_BOTH_PAIR:
                return true;
            default:
                return false;
        }
    }

    private boolean isNextWinTie(BeadRoadResult win) {
        switch (win) {
            case TIE_WIN:
            case TIE_WIN_BANKER_PAIR:
            case TIE_WIN_PLAYER_PAIR:
            case TIE_WIN_BOTH_PAIR:
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    private void MoveForSameColor() {
        if ((getPrefRows() - (row + 1)) == 0) {
            column++;
        } else {
            row++;
        }
    }

    private void MoveForDifferentColor() {
        column++;
        row = 0;
    }

    private void MoveToNextPositionFront(BeadRoadResult next) {
        if (getSize() != 0) {
            if (isCurrentWinRed()) {
                if (isNextWinRed(next)) {
                    MoveForSameColor();
                } else {
                    if (isNextWinBlue(next)) {
                        MoveForDifferentColor();
                    }
                }
            }
            else if (isCurrentWinBlue()) {
                if (isNextWinRed(next)) {
                    MoveForDifferentColor();
                } else {
                    if (isNextWinBlue(next)) {
                        MoveForSameColor();
                    }
                }
            }
        } else {
            row++;
        }
        if (getSize() >= sizeLimit()) {
            ShiftCellsBack(6);
        }
        count.setValue(getSize());
    }


    public void ShiftCellsBack(int size) {

        ArrayList<BigRoadLabel> lst  = new ArrayList<BigRoadLabel>();

        getChildren().forEach(t -> {
            lst.add((BigRoadLabel)t);
        });
        for(int i = 0; i < size; i++) {
            lst.remove(i);
        }

        getChildren().clear();
        Initialize(6,20);

        for(int j = 0; j < (getSize() - size);j++) {
            this.InsertElement(lst.get(j));
        }

    }

    private void MovePostionBack() {

        count.setValue(getSize());
    }

}

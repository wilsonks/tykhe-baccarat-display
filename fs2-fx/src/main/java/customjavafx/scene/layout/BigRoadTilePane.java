package customjavafx.scene.layout;


import com.sun.org.apache.xpath.internal.functions.FuncSubstring;
import customjavafx.scene.control.BeadRoadResult;
import customjavafx.scene.control.BigRoadLabel;
import customjavafx.scene.control.BigRoadResult;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import scala.Int;

import javax.tools.JavaCompiler;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class BigRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;
    private int savedColumn = -1;
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
        return (getChildren().size() == sizeLimit());
    }

    private boolean notStartPosition(){
        return (row >= 0);
    }

    private boolean isCurrentWinRed() {
        switch (((BigRoadLabel) super.getChildren().get(getCurrentPosition())).getResult()) {
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
        switch (((BigRoadLabel) super.getChildren().get(getCurrentPosition())).getResult()) {
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

    private BigRoadLabel getLabel(int pos){
        return ((BigRoadLabel) super.getChildren().get(pos));
    }

    private void ClearLabel(BigRoadLabel t) {
        t.setResult(BigRoadResult.EMPTY);
        t.setText("");
        t.setTieCount(1);
    }

    private void MoveForSameColor() {
        if ((getPrefRows() - (row + 1)) == 0) {
            if(savedColumn == -1) savedColumn = column;
            column++;

        } else {
            if (((BigRoadLabel) super.getChildren().get(getCurrentPosition() + 1)).getResult() == BigRoadResult.EMPTY){
                row++;
            }
            else {
                if(savedColumn == -1) savedColumn = column;
                column++;

            }
        }
    }

    private void MoveForDifferentColor() {
        if(savedColumn != -1) {
            column = savedColumn + 1;
            savedColumn = -1;
            row = 0;
        }
        else {
            column++;
            row = 0;

        }
    }

    private void Insert() {
        this.getChildren().add(new BigRoadLabel(BigRoadResult.EMPTY));
    }

    private void CopyLabelToCurrentPosition(BigRoadLabel e) {
        if(row != -1) {
            if(row < (getPrefRows() - 1)) {
                row++;
            }else {
                row = 0;
                column ++;
            }
        }
        else {
            row = 0;
        }
        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(e.getResult());
        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setText(e.getText());
        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setTieCount(e.getTieCount());
    }

    public int getSize() {
        return getCurrentPosition() + 1;
    }

    public void Initialize() {
        while (!childrenLimitReached()) {
            Insert();
        }
    }

    public void Initialize(int row, int column) {
        this.setPrefRows(row);
        this.setPrefColumns(column);
        while (!childrenLimitReached()) {
            Insert();
        }
        this.column = 0;
        this.row = -1;
    }

    public int getCurrentPosition() {
        return (column * getPrefRows()) + row;
    }

    public void ShiftColumn() {
        int localSaveRow= row;
        int localSaveColumn = column;
        row = -1;column = 0;
        getChildren().stream().skip(getPrefRows()).map(x -> (BigRoadLabel)x).forEach(t -> {
            CopyLabelToCurrentPosition(t);
        });
        getChildren().stream().skip(sizeLimit() - getPrefRows()).map(x -> (BigRoadLabel)x).forEach(t -> {
            ClearLabel(t);
        });

        row = localSaveRow;
        column= localSaveColumn - 1;
        if(savedColumn != -1) savedColumn--;
    }

    public void AddElement(BeadRoadResult next) {
        MoveToNextPositionFront(next);
        switch (next) {
            case BANKER_WIN:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.BANKER_WIN);
                break;

            case BANKER_WIN_BANKER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.BANKER_WIN_BANKER_PAIR);
                break;

            case BANKER_WIN_PLAYER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.BANKER_WIN_PLAYER_PAIR);
                break;

            case BANKER_WIN_BOTH_PAIR:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.BANKER_WIN_BOTH_PAIR);
                break;

            case PLAYER_WIN:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.PLAYER_WIN);
                break;

            case PLAYER_WIN_BANKER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.PLAYER_WIN_BANKER_PAIR);
                break;

            case PLAYER_WIN_PLAYER_PAIR:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.PLAYER_WIN_PLAYER_PAIR);
                break;

            case PLAYER_WIN_BOTH_PAIR:
                ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.PLAYER_WIN_BOTH_PAIR);
                break;

            case TIE_WIN:
            case TIE_WIN_BANKER_PAIR:
            case TIE_WIN_PLAYER_PAIR:
            case TIE_WIN_BOTH_PAIR: {
                switch (((BigRoadLabel) super.getChildren().get(getCurrentPosition())).getResult()) {
                    case BANKER_WIN:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN);
                        break;
                    case BANKER_WIN_BANKER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN_BANKER_PAIR);
                        break;
                    case BANKER_WIN_PLAYER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN_PLAYER_PAIR);
                        break;
                    case BANKER_WIN_BOTH_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_BANKER_WIN_BOTH_PAIR);
                        break;
                    case PLAYER_WIN:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN);
                        break;
                    case PLAYER_WIN_BANKER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN_BANKER_PAIR);
                        break;
                    case PLAYER_WIN_PLAYER_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN_PLAYER_PAIR);
                        break;
                    case PLAYER_WIN_BOTH_PAIR:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setResult(BigRoadResult.TIE_AFTER_PLAYER_WIN_BOTH_PAIR);
                        break;
                    default:
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).incTieCount();
                        int tmp =  ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).getTieCount();
                        ((BigRoadLabel) super.getChildren().get(getCurrentPosition())).setText(String.valueOf(tmp));
                        break;
                }
                break;
            }

            default:
                break;
        }
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
        if (getCurrentPosition() >= sizeLimit()) {
            ShiftColumn();
        }
        count.setValue(getSize());
    }

    private void MoveToPrevPosition(){
        BigRoadLabel labelNow = getLabel(getCurrentPosition());
        ClearLabel(getLabel(getCurrentPosition()));
        if(getCurrentPosition() == 0) { row--; return;}
        System.out.println("MovePrevPosition<----row=" + row + " column=" + column);
        if(row == 0) {
            column--;
            while(((BigRoadLabel)getChildren().get(getCurrentPosition())).getResult() != BigRoadResult.EMPTY) {
                if(row == (getPrefRows()-1)) {
                    column++;
                }
                else {
                    row++;

                }
            }
            if(row == (getPrefRows()-1)) {
                column--;
            }else {
                row--;
            }
        }else if (row == (getPrefRows() - 1)) {
            if (((BigRoadLabel)getChildren().get(getCurrentPosition())).getResult() != BigRoadResult.EMPTY){
                row--;
            }
            else {
                column--;
            }
        }else {
            row--;
        }
        System.out.println("MovePrevPosition<----row=" + row + " column=" + column);
    }

    public void RemoveElement() {
        if (notStartPosition()) MoveToPrevPosition();

    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }


}

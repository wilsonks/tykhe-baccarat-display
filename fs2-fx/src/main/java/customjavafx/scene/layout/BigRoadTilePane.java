package customjavafx.scene.layout;


import customjavafx.scene.control.BeadRoadLabel;
import customjavafx.scene.control.BeadRoadResult;
import customjavafx.scene.control.BigRoadLabel;
import customjavafx.scene.control.BigRoadResult;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;

public class BigRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;
    private int savedColumn = -1;
    private Long ballsBefore = 0L;

    private int c0,c1,c2,c3,c4 = 0;
    
    private ListProperty<String> bigEyeRoadList= new SimpleListProperty<String>(FXCollections.observableList(new ArrayList<String>()));

    public ListProperty<String> bigEyeRoadListProperty() {
        return bigEyeRoadList;
    }

    public BigRoadTilePane() {
        super();
        super.setOrientation(Orientation.VERTICAL);
        super.setAlignment(Pos.TOP_LEFT);
    }

    public int getSizeLimit() {
        return (getPrefColumns() * getPrefRows());
    }

    private boolean childrenLimitReached() {
        return (getChildren().size() == getSizeLimit());
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

    private BigRoadLabel getLabel(int pos){
        return ((BigRoadLabel) super.getChildren().get(pos));
    }

    private void ClearLabel(BigRoadLabel t) {
        t.setResult(BigRoadResult.EMPTY);
        t.setText("");
        t.setTieCount(1);
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

    private int getSize() {
        return getCurrentPosition() + 1;
    }

    public int getCurrentPosition() {
        return (column * getPrefRows()) + row;
    }

    private void ShiftColumnNew() {
        int localSaveRow= row;
        int localSaveColumn = column;
        this.getChildren().remove(0, (getPrefRows()));
        for(int i=0; i < (getPrefRows());i++){
            Insert();
        }
        row = localSaveRow;
        column= localSaveColumn - 1;
        if(savedColumn != -1) savedColumn--;
    }

    private void ShiftColumn() {
//        UpdateBigEyeRoadList();
        int localSaveRow= row;
        int localSaveColumn = column;
        row = -1;column = 0;
        getChildren().stream().skip(getPrefRows()).map(x -> (BigRoadLabel)x).forEach(t -> {
            CopyLabelToCurrentPosition(t);
        });
        getChildren().stream().skip(getSizeLimit() - getPrefRows()).map(x -> (BigRoadLabel)x).forEach(t -> {
            ClearLabel(t);
        });
        row = localSaveRow;
        column= localSaveColumn - 1;
        if(savedColumn != -1) savedColumn--;
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
        c0++;
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
        c4 = c3;
        c3 = c2;
        c2 = c1;
        c1 = c0;
        c0 = 1;
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
            c0++;
        }
        if (getCurrentPosition() >= getSizeLimit()) {
            ShiftColumnNew();

        }
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

    private Long getCount() {
        return getChildren()
                .stream()
                .map(x->(BigRoadLabel)x)
                .filter(t->t.getResult()!= BigRoadResult.EMPTY)
                .count();
    }

    public void ReArrangeElements(BeadRoadTilePane bead) {
        ballsBefore = getCount();
        getChildren().stream().map(x -> (BigRoadLabel)x ).forEach(t-> ClearLabel(t));
        row = -1;column = 0;savedColumn= -1;
        c0 = c1 = c2 = c3 = c4 = 0;
        bead.getChildren().stream().map(x->(BeadRoadLabel)x).forEach(t->{
            AddElement(t.getResult());
        });

        long ballsAfter = getCount();
        if(ballsAfter > ballsBefore) {
            if(c0 >= 2) {
                if(c1 > 0) {
                    if(c0 == (c1+1)) {
                        bigEyeRoadList.add("blue");
                    }else{
                        bigEyeRoadList.add("red");
                    }
                }
            }else {
                //Last Element is in First Row
                if(c2 > 0) {
                    //You compare you
                    if((c1+1) == (c2+1)) {
                        bigEyeRoadList.add("red");
                    }else{
                        bigEyeRoadList.add("blue");
                    }
                }
            }
        }else if (ballsAfter < ballsBefore) {
            if(!bigEyeRoadList.isEmpty()) bigEyeRoadList.remove(bigEyeRoadList.size() - 1);
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

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }


}

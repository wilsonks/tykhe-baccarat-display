package customjavafx.scene.layout;


import customjavafx.scene.control.*;
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
    private boolean shiftedNow = false;
    private int c0,c1,c2,c3,c4 = 0;
    
    private ListProperty<BigEyeRoadLabel> bigEyeRoadList= new SimpleListProperty<BigEyeRoadLabel>(FXCollections.observableList(new ArrayList<BigEyeRoadLabel>()));
    public ListProperty<BigEyeRoadLabel> bigEyeRoadListProperty() {
        return bigEyeRoadList;
    }

    private ListProperty<SmallRoadLabel> smallRoadList= new SimpleListProperty<SmallRoadLabel>(FXCollections.observableList(new ArrayList<SmallRoadLabel>()));
    public ListProperty<SmallRoadLabel> smallRoadListProperty() {
        return smallRoadList;
    }

    private ListProperty<CockroachRoadLabel> cockroachRoadList= new SimpleListProperty<CockroachRoadLabel>(FXCollections.observableList(new ArrayList<CockroachRoadLabel>()));
    public ListProperty<CockroachRoadLabel> cockroachRoadListProperty() {
        return cockroachRoadList;
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


    private void ClearLabel(BigRoadLabel t) {
        t.setResult(BigRoadResult.EMPTY);
        t.setText("");
        t.setTieCount(1);
    }

    private void Insert() {
        this.getChildren().add(new BigRoadLabel(BigRoadResult.EMPTY));
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
        c4 =  c3;
        c3 =  c2;
        c2 = c1;
        c1 = c0;
        c0 = 0;
        shiftedNow = true;
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
        getChildren().stream().map(x -> (BigRoadLabel)x ).forEach(t-> ClearLabel(t));
        row = -1;column = 0;savedColumn= -1;
        c0 = c1 = c2 = c3 = c4 = 0;
        bead.getChildren().stream().map(x->(BeadRoadLabel)x).forEach(t->{
            AddElement(t.getResult());
        });
    }

    private void UpdateBigEyeRoadList(int c0,int c1, int c2){
        if(c0 >= 2) {
            if(c1 > 0) {
                if(c0 == (c1+1)) {
                    bigEyeRoadList.add(new BigEyeRoadLabel(BigEyeRoadResult.BLUE));
                }else{
                    bigEyeRoadList.add(new BigEyeRoadLabel(BigEyeRoadResult.RED));
                }
            }
        }else {
            //Last Element is in First Row
            if(c2 > 0) {
                //You compare you
                if((c1+1) == (c2+1)) {
                    bigEyeRoadList.add(new BigEyeRoadLabel(BigEyeRoadResult.RED));

                }else{
                    bigEyeRoadList.add(new BigEyeRoadLabel(BigEyeRoadResult.BLUE));

                }
            }
        }
    }

    private BigEyeRoadResult RepeatPatternForBigEyeRoad(int c0,int c1, int c2){
        if(c0 >= 2) {
            if(c1 > 0) {
                if(c0 == (c1+1)) {
                    return BigEyeRoadResult.BLUE;
                }else{
                    return BigEyeRoadResult.RED;
                }
            }
        }else {
            //Last Element is in First Row
            if(c2 > 0) {
                //You compare you
                if((c1+1) == (c2+1)) {
                    return BigEyeRoadResult.RED;

                }else{
                    return BigEyeRoadResult.BLUE;

                }
            }
        }
         return BigEyeRoadResult.EMPTY;
    }

    private void UpdateSmallRoadList(int c0,int c1, int c2, int c3){
        if(c0 >= 2) {
            if(c2 > 0) {
                if(c0 == (c2+1)) {
                    smallRoadList.add(new SmallRoadLabel(SmallRoadResult.BLUE));
                }else{
                    smallRoadList.add(new SmallRoadLabel(SmallRoadResult.RED));
                }
            }
        }else {
            if(c3 > 0) {
                if((c1+1) == (c3+1)) {
                    smallRoadList.add(new SmallRoadLabel(SmallRoadResult.RED));

                }else{
                    smallRoadList.add(new SmallRoadLabel(SmallRoadResult.BLUE));

                }
            }
        }
    }

    private SmallRoadResult RepeatPatternForSmallRoad(int c0,int c1, int c2, int c3){
        if(c0 >= 2) {
            if(c2 > 0) {
                if(c0 == (c2+1)) {
                    return SmallRoadResult.BLUE;
                }else{
                    return SmallRoadResult.RED;
                }
            }
        }else {
            if(c3 > 0) {
                if((c1+1) == (c3+1)) {
                    return SmallRoadResult.RED;

                }else{
                    return SmallRoadResult.BLUE;

                }
            }
        }
        return SmallRoadResult.EMPTY;
    }

    private void UpdateCockroachRoadList(int c0,int c1, int c2,int c3,int c4){
        if(c0 >= 2) {
            if(c3 > 0) {
                if(c0 == (c3+1)) {
                    cockroachRoadList.add(new CockroachRoadLabel(CockroachRoadResult.BLUE));
                }else{
                    cockroachRoadList.add(new CockroachRoadLabel(CockroachRoadResult.RED));
                }
            }
        }else {
            if(c4 > 0) {
                if((c1+1) == (c4+1)) {
                    cockroachRoadList.add(new CockroachRoadLabel(CockroachRoadResult.RED));

                }else{
                    cockroachRoadList.add(new CockroachRoadLabel(CockroachRoadResult.BLUE));

                }
            }
        }
    }

    private CockroachRoadResult RepeatPatternForCockroachRoad(int c0,int c1, int c2,int c3,int c4){
        if(c0 >= 2) {
            if(c3 > 0) {
                if(c0 == (c3+1)) {
                    return CockroachRoadResult.BLUE;
                }else{
                    return CockroachRoadResult.RED;
                }
            }
        }else {
            if(c4 > 0) {
                if((c1+1) == (c4+1)) {
                    return CockroachRoadResult.RED;

                }else{
                    return CockroachRoadResult.BLUE;

                }
            }
        }
        return CockroachRoadResult.EMPTY;
    }


    public void AddElement(BeadRoadTilePane bead) {
        Long ballsBefore = getCount();
        ReArrangeElements(bead);
        Long ballsAfter = getCount();

        if((ballsBefore < ballsAfter) || (shiftedNow)){
            UpdateBigEyeRoadList(c0,c1,c2);
            UpdateSmallRoadList(c0,c1,c2,c3);
            UpdateCockroachRoadList(c0,c1,c2,c3,c4);
        }

        shiftedNow=false;

    }

    public void RemoveElement(BeadRoadTilePane bead) {
        Long ballsBefore = getCount();
        ReArrangeElements(bead);
        Long ballsAfter = getCount();
        if (ballsAfter < ballsBefore) {
            if(!bigEyeRoadList.isEmpty()) bigEyeRoadList.remove(bigEyeRoadList.size() - 1);
            if(!smallRoadList.isEmpty()) smallRoadList.remove(smallRoadList.size() - 1);
            if(!cockroachRoadList.isEmpty()) cockroachRoadList.remove(cockroachRoadList.size() - 1);
        }
    }

    public void UpdatePredictions(BigEyeRoadLabel b1,
                                  SmallRoadLabel b2,
                                  CockroachRoadLabel b3,
                                  BigEyeRoadLabel p1,
                                  SmallRoadLabel p2,
                                  CockroachRoadLabel p3) {

    if(getCurrentPosition() < 0) return;

    if(isCurrentWinRed()){
        b1.setResult(RepeatPatternForBigEyeRoad(c0+1,c1,c2));
        b2.setResult(RepeatPatternForSmallRoad(c0+1,c1,c2,c3));
        b3.setResult(RepeatPatternForCockroachRoad(c0+1,c1,c2,c3,c4));
        p1.setResult(RepeatPatternForBigEyeRoad(1,c0,c1));
        p2.setResult(RepeatPatternForSmallRoad(1,c0,c1,c2));
        p3.setResult(RepeatPatternForCockroachRoad(1,c0,c1,c2,c3));
    } else {
        p1.setResult(RepeatPatternForBigEyeRoad(c0+1,c1,c2));
        p2.setResult(RepeatPatternForSmallRoad(c0+1,c1,c2,c3));
        p3.setResult(RepeatPatternForCockroachRoad(c0+1,c1,c2,c3,c4));
        b1.setResult(RepeatPatternForBigEyeRoad(1,c0,c1));
        b2.setResult(RepeatPatternForSmallRoad(1,c0,c1,c2));
        b3.setResult(RepeatPatternForCockroachRoad(1,c0,c1,c2,c3));
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

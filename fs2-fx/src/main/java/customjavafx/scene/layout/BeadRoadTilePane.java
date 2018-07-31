package customjavafx.scene.layout;


import customjavafx.scene.control.BeadRoadLabel;
import customjavafx.scene.control.BeadRoadResult;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class BeadRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;

    private IntegerProperty count = new SimpleIntegerProperty(0);
    public IntegerProperty getCountProperty() {return count;}

    private IntegerProperty playerWinCount = new SimpleIntegerProperty(0);
    public IntegerProperty getPlayerWinCount() {return playerWinCount;}


    private IntegerProperty bankerWinCount = new SimpleIntegerProperty(0);
    public IntegerProperty getBankerWinCount() {return bankerWinCount;}


    private IntegerProperty tieWinCount = new SimpleIntegerProperty(0);
    public IntegerProperty getTieWinCount() {return tieWinCount;}


    private IntegerProperty playerPairCount = new SimpleIntegerProperty(0);
    public IntegerProperty getPlayerPairCount() {return playerPairCount;}

    private IntegerProperty bankerPairCount = new SimpleIntegerProperty(0);
    public IntegerProperty getBankerPairCount() {return bankerPairCount;}



    public BeadRoadTilePane() {
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

    private void ResultAdded(BeadRoadResult res) {
        count.setValue(count.getValue() + 1);
        switch(res) {
            case BANKER_WIN:
                bankerWinCount.setValue(bankerWinCount.getValue()+1);
                break;
            case BANKER_WIN_BANKER_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()+1);
                bankerWinCount.setValue(bankerWinCount.getValue()+1);
                break;
            case BANKER_WIN_PLAYER_PAIR:
                playerPairCount.setValue(playerPairCount.getValue()+1);
                bankerWinCount.setValue(bankerWinCount.getValue()+1);
                break;

            case BANKER_WIN_BOTH_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()+1);
                playerPairCount.setValue(playerPairCount.getValue()+1);
                bankerWinCount.setValue(bankerWinCount.getValue()+1);
                break;

            case PLAYER_WIN:
                playerWinCount.setValue(playerWinCount.getValue()+1);
                break;
            case PLAYER_WIN_BANKER_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()+1);
                playerWinCount.setValue(playerWinCount.getValue()+1);
                break;
            case PLAYER_WIN_PLAYER_PAIR:
                playerPairCount.setValue(playerPairCount.getValue()+1);
                playerWinCount.setValue(playerWinCount.getValue()+1);
                break;

            case PLAYER_WIN_BOTH_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()+1);
                playerPairCount.setValue(playerPairCount.getValue()+1);
                playerWinCount.setValue(playerWinCount.getValue()+1);
                break;
            case TIE_WIN:
                tieWinCount.setValue(tieWinCount.getValue()+1);
                break;
            case TIE_WIN_BANKER_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()+1);
                tieWinCount.setValue(tieWinCount.getValue()+1);
                break;
            case TIE_WIN_PLAYER_PAIR:
                playerPairCount.setValue(playerPairCount.getValue()+1);
                tieWinCount.setValue(tieWinCount.getValue()+1);
                break;

            case TIE_WIN_BOTH_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()+1);
                playerPairCount.setValue(playerPairCount.getValue()+1);
                tieWinCount.setValue(tieWinCount.getValue()+1);
                break;

            default:break;
        }
    }


    private void ResultRemoved(BeadRoadResult res) {
        count.setValue(count.getValue() - 1);
        switch(res) {
            case BANKER_WIN:
                bankerWinCount.setValue(bankerWinCount.getValue()-1);
                break;
            case BANKER_WIN_BANKER_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()-1);
                bankerWinCount.setValue(bankerWinCount.getValue()-1);
                break;
            case BANKER_WIN_PLAYER_PAIR:
                playerPairCount.setValue(playerPairCount.getValue()-1);
                bankerWinCount.setValue(bankerWinCount.getValue()-1);
                break;

            case BANKER_WIN_BOTH_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()-1);
                playerPairCount.setValue(playerPairCount.getValue()-1);
                bankerWinCount.setValue(bankerWinCount.getValue()-1);
                break;

            case PLAYER_WIN:
                playerWinCount.setValue(playerWinCount.getValue()-1);
                break;
            case PLAYER_WIN_BANKER_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()-1);
                playerWinCount.setValue(playerWinCount.getValue()-1);
                break;
            case PLAYER_WIN_PLAYER_PAIR:
                playerPairCount.setValue(playerPairCount.getValue()-1);
                playerWinCount.setValue(playerWinCount.getValue()-1);
                break;

            case PLAYER_WIN_BOTH_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()-1);
                playerPairCount.setValue(playerPairCount.getValue()-1);
                playerWinCount.setValue(playerWinCount.getValue()-1);
                break;
            case TIE_WIN:
                tieWinCount.setValue(tieWinCount.getValue()-1);
                break;
            case TIE_WIN_BANKER_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()-1);
                tieWinCount.setValue(tieWinCount.getValue()-1);
                break;
            case TIE_WIN_PLAYER_PAIR:
                playerPairCount.setValue(playerPairCount.getValue()-1);
                tieWinCount.setValue(tieWinCount.getValue()-1);
                break;

            case TIE_WIN_BOTH_PAIR:
                bankerPairCount.setValue(bankerPairCount.getValue()-1);
                playerPairCount.setValue(playerPairCount.getValue()-1);
                tieWinCount.setValue(tieWinCount.getValue()-1);
                break;

                default:break;
        }
    }

    private void RemoveLast() {
        BeadRoadResult tmp = ((BeadRoadLabel)getChildren().get(0)).getResult();
        super.getChildren().remove(0);
        MovePostionBack();
        ResultRemoved(tmp);
    }

    private void Update(BeadRoadResult res) {
        MovePositionFront();
        ((BeadRoadLabel) super.getChildren().get(getPosition())).setResult(res);
    }

    private void Insert() {
        BeadRoadLabel temp = new BeadRoadLabel(BeadRoadResult.EMPTY);
        temp.setResult(BeadRoadResult.EMPTY);
        super.getChildren().add(temp);
    }

    public void Initialize() {
        while (!childrenLimitReached()) {
            Insert();
        }
    }

    public void Initialize(int row, int column) {
        setPrefRows(row);
        setPrefColumns(column);
        while (!childrenLimitReached()) {
            Insert();
        }
    }

    public void RemoveElement() {
        if (getPosition() >= 0) {
            BeadRoadResult tmp = ((BeadRoadLabel) super.getChildren().get(getPosition())).getResult();
            ((BeadRoadLabel) super.getChildren().get(getPosition())).setResult(BeadRoadResult.EMPTY);
            MovePostionBack();
            ResultRemoved(tmp);
        }
    }

    public void AddElement(BeadRoadResult res) {
        if (getSize() == sizeLimit()) {
            RemoveLast();
            Insert();
        }
        Update(res);
        ResultAdded(res);
    }

    public BeadRoadResult GetLastElement() {

        return  ((BeadRoadLabel)super.getChildren().get(getPosition())).getResult();
    }


    public int getSize() {
        return getPosition() + 1;
    }

    public int getPosition() {
        return (column * 8) + row;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    private void MovePositionFront() {
        if (row == (getPrefRows() - 1)) {
            column++;
            row = 0;
        } else {
            row++;
        }

    }

    private void MovePostionBack() {
        if (row == 0) {
            column--;
            row = 7;
        } else {
            row--;
        }

    }

}

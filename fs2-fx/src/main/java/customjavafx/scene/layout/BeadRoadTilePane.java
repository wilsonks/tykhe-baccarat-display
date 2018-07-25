package customjavafx.scene.layout;


import customjavafx.scene.control.BeadRoadLabel;
import customjavafx.scene.control.BeadRoadResult;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class BeadRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;

    public BeadRoadTilePane() {
        super();
        super.setOrientation(Orientation.VERTICAL);
        super.setAlignment(Pos.TOP_LEFT);
    }

    private int sizeLimit() {
        return (getPrefColumns() * getPrefRows());
    }

    private boolean childrenLimitReached() {
        return (super.getChildren().size() == sizeLimit()) ;
    }


    private void RemoveLast(){
        super.getChildren().remove(0);
        MovePostionBack();
    }

    private void Update(BeadRoadResult res) {
        MovePositionFront();
        ((BeadRoadLabel)super.getChildren().get(getPosition())).setResult(res);
    }

    private void Insert() {
        BeadRoadLabel temp = new BeadRoadLabel(BeadRoadResult.EMPTY);
        temp.setResult(BeadRoadResult.EMPTY);
        super.getChildren().add(temp);
    }

    public void Add(BeadRoadResult res) {
        if (getSize() == sizeLimit()) {
            RemoveLast();
            Insert();
        }
        Update(res);
    }

    public void Initialize() {
        while(!childrenLimitReached()) {
            Insert();
        }
    }

    public void Initialize(int row, int column) {
        setPrefRows(row);
        setPrefColumns(column);
        while(!childrenLimitReached()) {
            Insert();
        }
    }

    public void Remove() {
        if(getPosition() >= 0) {
            ((BeadRoadLabel)super.getChildren().get(getPosition())).setResult(BeadRoadResult.EMPTY);
            MovePostionBack();
        }
    }

    public int getSize(){
        return getPosition() + 1;
    }

    public int getPosition(){
        return (column*8)+row;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    private void MovePositionFront(){
        if(row == (getPrefRows()-1)) {
            column++;
            row = 0;
        }
        else {
            row++;
        }
    }

    private void MovePostionBack(){
        if(row == 0) {
            column--;
            row = 7;
        }
        else {
            row--;
        }
    }

}

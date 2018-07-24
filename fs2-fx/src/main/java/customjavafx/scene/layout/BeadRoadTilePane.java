package customjavafx.scene.layout;


import customjavafx.scene.control.BeadRoadLabel;
import customjavafx.scene.control.BeadRoadResult;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

public class BeadRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;

    public BeadRoadTilePane() {
        super();
        setOrientation(Orientation.VERTICAL);
        setAlignment(Pos.TOP_LEFT);
        setPrefColumns(20);
        setPrefRows(8);
    }

    public void fillArea() {
        int maxSize = super.getPrefColumns() * super.getPrefRows();
        while(super.getChildren().size() < maxSize) {
            BeadRoadLabel temp = new BeadRoadLabel(BeadRoadResult.EMPTY);
            super.getChildren().add(temp);
        }
    }

    public void Remove() {
        BeadRoadLabel e = (BeadRoadLabel)super.getChildren().get((column*8)+row);
        e.setResult(BeadRoadResult.EMPTY);
        DeleteElement();
    }

    public void Add(BeadRoadLabel r) {
        int maxSize = super.getPrefColumns() * super.getPrefRows();
        if (super.getChildren().size() >= maxSize) {
            super.getChildren().remove(0);
            DeleteElement();
        }
        super.getChildren().add(r);
        AddElement();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    protected void AddElement(){
        if(row == (getPrefRows()-1)) {
            column++;
            row = 0;
        }
        else {
            row++;
        }
    }

    protected void DeleteElement(){
        if(row == 0) {
            column--;
            row = 7;
        }
        else {
            row--;
        }
    }

}

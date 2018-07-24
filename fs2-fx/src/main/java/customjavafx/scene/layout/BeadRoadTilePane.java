package customjavafx.scene.layout;


import customjavafx.scene.control.BeadRoadLabel;
import customjavafx.scene.control.BeadRoadResult;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class BeadRoadTilePane extends TilePane {

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
    public void Add(BeadRoadLabel r) {
        int maxSize = super.getPrefColumns() * super.getPrefRows();
        if (super.getChildren().size() >= maxSize) {
            super.getChildren().remove(0);
        }
        super.getChildren().add(r);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }


}

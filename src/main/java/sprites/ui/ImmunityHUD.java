package sprites.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Ellipse;
import sprites.Sprite;
import utils.TimeHelper;

public class ImmunityHUD extends Sprite {
    private double ARC_START_ANGLE = 90.0;
    private Arc inner;
    private Ellipse outer;
    private double immunityDuration = 0.0;
    private double immunityLeft = 0.0;

    public ImmunityHUD() {
        super();
        inner = new Arc();
        inner.setCenterX(0);
        inner.setCenterY(0);
        inner.setRadiusX(30);
        inner.setRadiusY(45);
        inner.setStartAngle(ARC_START_ANGLE);
        inner.setLength(360.0);
        inner.setType(ArcType.CHORD);
        inner.setFill(Color.PURPLE);

        outer = new Ellipse(0, 0, 35, 50);
        outer.setFill(Color.PURPLE);
        outer.setOpacity(0.5);

        getChildren().add(inner);
        getChildren().add(outer);
    }

    public void startImmunity(double duration) {
        immunityDuration = duration;
        immunityLeft = duration;
    }

    public void stopImmunity() {
        immunityDuration = 0;
        immunityLeft = 0;
    }

    @Override
    public void update() {
        if (immunityLeft > 0 || immunityDuration > 0) {
            setVisible(true);

            immunityLeft -= TimeHelper.getDeltaTime();

            updateProgress(immunityLeft / immunityDuration);
        }
        else {
            setVisible(false);
        }
    }

    private void updateProgress(double percentage) {
        final double step = 180.0 * (1 - percentage);
        inner.setStartAngle(ARC_START_ANGLE + step);
        inner.setLength(360.0 -2 * step);
    }
}

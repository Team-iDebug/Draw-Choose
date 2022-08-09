package com.iDebug.pickloose.animation;

import com.iDebug.pickloose.animation.AnimationFX;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * @author Loïc Sculier aka typhon0
 */

public class FadeIn extends AnimationFX {

    /**
     * Create a new FadeIn animation
     *
     * @param node the node to affect
     */
    public FadeIn(Node node) {
        super(node);
    }

    public FadeIn() {
    }

    @Override
    AnimationFX resetNode() {
        return this;
    }

    @Override
    void initTimeline() {
        setTimeline(new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(getNode().opacityProperty(), 0, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(getNode().opacityProperty(), 1, Interpolator.EASE_IN)
                )

        ));
    }
}


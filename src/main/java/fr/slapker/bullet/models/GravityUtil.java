package fr.slapker.bullet.models;

import fr.slapker.bullet.launcher.GameParam;

import java.awt.geom.Point2D;

public class GravityUtil {


    public static double computeForceStrength(MovingObject body0, MovingObject body1) {
        if (body0 != body1) {

            double G = GameParam.getInstance().getG().getValue();

            double distance = Point2D.distance(body0.getPosX().getValue(), body0.getPosY().getValue(), body1.getPosX().getValue(), body1.getPosY().getValue());

            double f = G * body0.getMassValue() * body1.getMassValue() / distance;
            return f;
        }
        return 0;
    }

}

package PoolGame.Config;

import org.json.simple.JSONObject;

public class PocketConfig implements Configurable{
    private PositionConfig position;
    private double radius;

    /**
     * Initialise the instance with the provided JSONObject
     * @param obj A JSONObject containing the pocket configuration
     */
    public PocketConfig(Object obj) {
        this.parseJSON(obj);
    }

    private void init(PositionConfig posConf, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius of pocket must be greater than 0");
        }
        this.position = posConf;
        this.radius = radius;
    }

    @Override
    public Configurable parseJSON(Object obj) {
        JSONObject json = (JSONObject) obj;
        PositionConfig posConf = new PositionConfig(json.get("position"));
        double radius = (double)json.get("radius");
        this.init(posConf, radius);
        return null;
    }

    /**
     * Get the PositionConfig of the pocket
     * @return The PositionConfig
     */
    public PositionConfig getPositionConfig() {
        return position;
    }

    /**
     * Get the radius of the pocket
     * @return The radius
     */
    public double getRadius() {
        return radius;
    }
}

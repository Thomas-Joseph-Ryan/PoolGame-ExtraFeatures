package PoolGame.Config;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

/** A config class that will contain all the pocket configuration**/
public class PocketsConfig implements Configurable{
    private List<PocketConfig> pocket;

    /**
     * Initialise the instance with the provided JSONArray of pocket
     * @param obj A JSONArray containing all the pocket configuration
     */
    public PocketsConfig(Object obj) {
        this.parseJSON(obj);
    }

    private void init(List<PocketConfig> pocketList) {
        this.pocket = pocketList;
    }
    @Override
    public Configurable parseJSON(Object obj) {
        List<PocketConfig> list = new ArrayList<>();
        JSONArray json = (JSONArray) obj;

        for (Object b : json) {
            list.add(new PocketConfig(b));
        }
        this.init(list);
        return this;
    }

    public List<PocketConfig> getPockets() {
        return pocket;
    }
}

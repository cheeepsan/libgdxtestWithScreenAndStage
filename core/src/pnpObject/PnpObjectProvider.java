package pnpObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import pnpMap.PnpTile;


import java.util.ArrayList;

public class PnpObjectProvider {
    private final String UNIT = "unit";
    private final String ITEM = "item";
    private final String TILE = "tile";

    private final String itemsPath = "core/assets/res/items.json";
    private final String unitsPath = "core/assets/res/units.json";
    private final String tilesPath = "core/assets/res/tiles.json";
    /*
    public PnpObject getObject(String objectType) {
        if (objectType == UNIT) {
            return new PnpUnit();
        } else if (objectType == ITEM) {
            //return new PnpItem();
        }

        return null;
    }*/
    public ArrayList<PnpObject> getObjects(String objectType) {
        JsonReader reader = new JsonReader();

        if (objectType == UNIT) {
            return this.getUnits(reader);
        } else if (objectType == ITEM) {

        } else if (objectType == TILE) {

        }
        return null;
    }

    private ArrayList<PnpObject> getUnits(JsonReader reader) {
        JsonValue value = reader.parse(Gdx.files.internal(this.unitsPath));
        ArrayList<PnpObject> objects = new ArrayList<PnpObject>();
        for (JsonValue unit : value.get("units")) {
            PnpUnit u = new PnpUnit();
            u.setHp(unit.getInt("hp"));
            u.setAttack(unit.getInt("attack"));
            u.setName(unit.getString("name"));
            u.setTeam(unit.getString("team"));
            u.setTexture(unit.getString("texture"));
            objects.add(u);
        }
        return objects;
    }

    public ArrayList<String> getTileTypes() {
        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(Gdx.files.internal(this.tilesPath));

        ArrayList<String> types = new ArrayList<String>();
        for (JsonValue tile : value.get("tiles")) {
            types.add(tile.getString("type"));
        }

        return types;
    }

    public JsonValue getTileDataByType(String type) {
        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(Gdx.files.internal(this.tilesPath));
        for (JsonValue tile : value.get("tiles")) {
            if (tile.getString("type").equals(type)) {
                return tile;
            }
        }

        return null;
    }
    /*private JsonValue readJson(String path) {
        if (path == null) {
            return null;
        }
        JsonReader r = new JsonReader();

        return r.parse(path);
    }*/
}

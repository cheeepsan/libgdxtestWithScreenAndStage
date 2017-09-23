package pnpObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


import java.util.ArrayList;

public class PnpObjectProvider {
    private final String UNIT = "unit";
    private final String ITEM = "item";

    private final String itemsPath = "core/assets/res/items.json";
    private final String unitsPath = "core/assets/res/units.json";
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

        }
        return null;
    }

    private ArrayList<PnpObject> getUnits(JsonReader reader) {
        JsonValue value = reader.parse(Gdx.files.internal(this.unitsPath));
        ArrayList<PnpObject> objects = new ArrayList<PnpObject>();
        for (JsonValue unit : value.get("units")) {
            PnpUnit u = new PnpUnit();
            u.setHp(Integer.parseInt(unit.getString("hp")));
            u.setAttack(Integer.parseInt(unit.getString("attack")));
            u.setName(unit.getString("name"));
            objects.add(u);
        }
        return objects;
    }
    /*private JsonValue readJson(String path) {
        if (path == null) {
            return null;
        }
        JsonReader r = new JsonReader();

        return r.parse(path);
    }*/
}

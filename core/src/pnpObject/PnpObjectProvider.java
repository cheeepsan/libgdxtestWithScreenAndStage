package pnpObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import pnpMap.PnpTile;


import java.util.ArrayList;
import java.util.stream.Collectors;

public class PnpObjectProvider {
    private final String UNIT = "unit";
    private final String ITEM = "item";
    private final String TILE = "tile";

    private final String itemsPath = "core/assets/res/items.json";
    private final String unitsPath = "core/assets/res/units.json";
    private final String tilesPath = "core/assets/res/tiles.json";

    private JsonReader reader;
    private AssetManager assetManager;

    public PnpObjectProvider(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.reader = new JsonReader();
    }
    public ArrayList<PnpObject> getObjects(String objectType) {
        if (objectType == UNIT) {
            return this.getUnits(reader);
        } else if (objectType == ITEM) {
            return this.getItems(reader);
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
            u.setTexture(this.assetManager.get(unit.getString("texture"), Texture.class));
            ArrayList<PnpItem> item = this.getItems(reader).stream().map(x -> (PnpItem)x).collect(Collectors.toCollection(ArrayList::new));
            u.getInventory().addAll(item); //TODO: remove all items
            objects.add(u);
        }
        return objects;
    }

    private ArrayList<PnpObject> getItems(JsonReader reader) {
        JsonValue value = reader.parse(Gdx.files.internal(this.itemsPath));
        ArrayList<PnpObject> objects = new ArrayList<>();
            for (JsonValue item : value.get("items").get("item")) {
                PnpItem i = new PnpWorldItem();
                objects.add(this.fillItemValues(item, i));
            }
            for (JsonValue item : value.get("items").get("equipment")) {
                PnpItem i = new PnpEquipment();
                objects.add(this.fillItemValues(item, i));
            }

        return objects;
    }

    private PnpItem fillItemValues(JsonValue item, PnpItem i){
        i.setName(item.get("name").asString());
        i.setDescription(item.get("description").asString());
        return i;
    }

    public ArrayList<String> getTileTypes() {

        JsonValue value = this.reader.parse(Gdx.files.internal(this.tilesPath));

        ArrayList<String> types = new ArrayList<String>();
        for (JsonValue tile : value.get("tiles")) {
            types.add(tile.getString("type"));
        }

        return types;
    }

    public JsonValue getTileDataByType(String type) {

        JsonValue value = this.reader.parse(Gdx.files.internal(this.tilesPath));
        for (JsonValue tile : value.get("tiles")) {
            if (tile.getString("type").equals(type)) {
                return tile;
            }
        }

        return null;
    }

    public ArrayList<String> getTexturePath() {
        ArrayList<String> textures = new ArrayList<String>();
        JsonValue value = this.reader.parse(Gdx.files.internal(this.tilesPath));
        for (JsonValue tile : value.get("tiles")) {
            textures.add(tile.getString("texture"));
        }
        value = this.reader.parse(Gdx.files.internal(this.unitsPath));
        for (JsonValue unit : value.get("units")) {
            textures.add(unit.getString("texture"));
        }
        textures.add("core/assets/res/textures/no_text.png"); //Maybe clean later
        return textures;

    }

    public Texture getTexture(String path) {
        return this.assetManager.get(path, Texture.class);
    }
    /*private JsonValue readJson(String path) {
        if (path == null) {
            return null;
        }
        JsonReader r = new JsonReader();

        return r.parse(path);
    }*/
}

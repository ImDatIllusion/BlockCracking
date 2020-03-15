package me.imillusion.blockcracking.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Crack implements ConfigurationSerializable {

    private Location loc;
    private int id;
    private int power;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("location", loc);
        map.put("id", id);
        map.put("power", power);

        return map;
    }

    public static Crack deserialize(Map<String, Object> map)
    {
        return new Crack((Location) map.get("location"), (int) map.get("id"), (int) map.get("power"));
    }

    public static Crack valueOf(Map<String, Object> map)
    {
        return deserialize(map);
    }
}

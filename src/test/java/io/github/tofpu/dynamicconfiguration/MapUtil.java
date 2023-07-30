package io.github.tofpu.dynamicconfiguration;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
}

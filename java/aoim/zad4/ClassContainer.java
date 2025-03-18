package aoim.zad4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    Map<String, ClassEmployee> map;

    public ClassContainer() {
        map = new HashMap<>();
    }

    public Map<String, ClassEmployee> getMap() {
        return map;
    }

    public void addClass(String name, int max) {
        if (!map.containsKey(name)) {
            map.put(name, new ClassEmployee(name, max));
            //System.out.println("Utworzono grupę " + name);
        } else {
           // System.out.println("Grupa o podanej nazwie już istnieje");
        }
    }

    public void removeClass(String name) {
        if (map.containsKey(name)) {
            map.remove(name);
            //System.out.println("Usunięto grupę " + name);
        } else {
            //System.out.println("Grupa o podanej nazwie nie istnieje");
        }
    }
}

package computergraphics.datastructures;

import java.util.List;

/**
 * Generische Kurve
 * Created by Gery on 12.12.2015.
 */
public abstract class Kurve {
    private List<Kontrollpunkt> kontrollpunktList;

    public int getGrad(){
        return kontrollpunktList.size();
    }
}
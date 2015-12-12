package computergraphics.datastructures;

import java.util.ArrayList;
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

    public Kurve(List<Kontrollpunkt> punkte){
        kontrollpunktList = new ArrayList<>();
        kontrollpunktList.addAll(punkte);
    }

    public List<Kontrollpunkt> getKontrollpunktList() {
        return kontrollpunktList;
    }
}
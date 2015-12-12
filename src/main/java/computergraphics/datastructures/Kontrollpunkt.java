package computergraphics.datastructures;

import computergraphics.math.Vector3;

/**
 * Kontrollpunkt einer Kurve
 * Created by Gery on 12.12.2015.
 */
public class Kontrollpunkt {
    private final Vector3 position = new Vector3();

    public Kontrollpunkt(Vector3 position){
        this();
        this.position.copy(position);
    }

    public Kontrollpunkt(){

    }

    public void setPosition(Vector3 position){
        this.position.copy(position);
    }
}

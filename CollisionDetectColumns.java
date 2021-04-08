import java.util.Iterator;
import org.jogamp.java3d.*;
import org.jogamp.vecmath.Color3f;

/* This behavior of collision detection highlights the
    object when it is in a state of collision. */
public class CollisionDetectColumns extends Behavior {
    private boolean inCollision;
    private Shape3D shape;
    private ColoringAttributes shapeColoring;
    private TransparencyAttributes transparencyAttributes;
    private Appearance shapeAppearance;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;

    public Boolean getCr() {
        return cr;
    }

    public void setCr(Boolean cr) {
        this.cr = cr;
    }

    public Boolean cr;

    public CollisionDetectColumns(Shape3D s) {
        cr=false;
        shape = s; // save the original color of 'shape"
        shapeAppearance = shape.getAppearance();
        shapeColoring = shapeAppearance.getColoringAttributes();
        inCollision = false;
    }

    public void initialize() { // USE_GEOMETRY USE_BOUNDS
        wEnter = new WakeupOnCollisionEntry(shape, WakeupOnCollisionEntry.USE_GEOMETRY);
        wExit = new WakeupOnCollisionExit(shape, WakeupOnCollisionExit.USE_GEOMETRY);
        wakeupOn(wEnter); // initialize the behavior
    }

    public void processStimulus(Iterator<WakeupCriterion> criteria) {
        Color3f hilightClr = Commons.Green;
        ColoringAttributes highlight = new ColoringAttributes(hilightClr, ColoringAttributes.SHADE_GOURAUD);
        inCollision = !inCollision; // collision has taken place

        if (inCollision) { // change color to highlight 'shape'
            shapeAppearance.setColoringAttributes(highlight);
            System.out.println("col");
            cr=true;
            wakeupOn(wExit); // keep the color until no collision
        } else { // change color back to its original
            shapeAppearance.setColoringAttributes(shapeColoring);
            wakeupOn(wEnter); // wait for collision happens
        }
    }
}

/* *********************************************************
 * For use by students to work on assignments and project.
 * Permission required material. Contact: xyuan@uwindsor.ca
 **********************************************************/

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jdesktop.j3d.examples.sound.PointSoundBehavior;
import org.jdesktop.j3d.examples.sound.audio.JOALMixer;
import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.PointSound;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.ColorCube;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.Viewer;
import org.jogamp.java3d.utils.universe.ViewingPlatform;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;

public class CodeLab1 extends JPanel {

    private static final long serialVersionUID = 1L;


    /* a function to create a rotation behavior and refer it to 'my_TG' */
    private RotationInterpolator rotateBehavior(TransformGroup my_TG) {

        my_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, 10000);
        RotationInterpolator rot_beh = new RotationInterpolator(
                rotationAlpha, my_TG, yAxis, 0.0f, (float) Math.PI * 2.0f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        rot_beh.setSchedulingBounds(bounds);
        return rot_beh;
    }

    /* a function to position viewer to 'eye' location */
    private void defineViewer(SimpleUniverse simple_U, Point3d eye) {

        TransformGroup viewTransform = simple_U.getViewingPlatform().getViewPlatformTransform();
        Point3d center = new Point3d(0, 0, 0);               // define the point where the eye looks at
        Vector3d up = new Vector3d(0, 1, 0);                 // define camera's up direction
        Transform3D view_TM = new Transform3D();
        view_TM.lookAt(eye, center, up);
        view_TM.invert();
        viewTransform.setTransform(view_TM);                 // set the TransformGroup of ViewingPlatform
    }

    /* a function to enable audio device via JOAL */
    private void enableAudio(SimpleUniverse simple_U) {

        JOALMixer mixer = null;		                         // create a null mixer as a joalmixer
        Viewer viewer = simple_U.getViewer();
        viewer.getView().setBackClipDistance(20.0f);         // make object(s) disappear beyond 20f

        if (mixer == null && viewer.getView().getUserHeadToVworldEnable()) {
            mixer = new JOALMixer(viewer.getPhysicalEnvironment());
            if (!mixer.initialize()) {                       // add mixer as audio device if successful
                System.out.println("Open AL failed to init");
                viewer.getPhysicalEnvironment().setAudioDevice(null);
            }
        }
    }

    /* a function to create a PointSound at the origin of its reference frame */
//    private PointSound pointSound() {
//        URL url = null;
//        String filename = "sounds/magic_bells.wav";
//        try {
//            url = new URL("file", "localhost", filename);
//        } catch (Exception e) {
//            System.out.println("Can't open " + filename);
//        }
//        PointSound ps = new PointSound();                    // create and position a point sound
//        //PointSoundBehavior player = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
//        player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
//        return ps;
//    }

    /* a function to allow key navigation with the ViewingPlateform */
    private KeyNavigatorBehavior keyNavigation(SimpleUniverse simple_U) {

        ViewingPlatform view_platfm = simple_U.getViewingPlatform();
        TransformGroup view_TG = view_platfm.getViewPlatformTransform();
        KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(view_TG);
        BoundingSphere view_bounds = new BoundingSphere(new Point3d(), 20.0);
        keyNavBeh.setSchedulingBounds(view_bounds);
        return keyNavBeh;
    }

    /* a function to build the content branch and attach to 'scene' */
    private void createScene(BranchGroup scene) {
        TransformGroup content_TG = new TransformGroup();    // create a TransformGroup (TG)
        content_TG.addChild(new ColorCube(0.4));             // attach a ColorCube to TG
        //content_TG.addChild(pointSound());	                 // attach a PointSound to TG
        scene.addChild(content_TG);	                         // add TG to the scene BranchGroup
        scene.addChild(rotateBehavior(content_TG));          // make TG continuously rotating
    }

    /* a constructor to set up and run the application */
    public CodeLab1() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas_3D = new Canvas3D(config);
        SimpleUniverse su = new SimpleUniverse(canvas_3D);   // create a SimpleUniverse
        //enableAudio(su);                                     // enable audio device
        defineViewer(su, new Point3d(1.35, 0.35, 2.0));    // set the viewer's location

        BranchGroup scene = new BranchGroup();
        createScene(scene);                           // add contents to the scene branch
        scene.addChild(keyNavigation(su));                   // allow key navigation

        scene.compile();		                             // optimize the BranchGroup
        su.addBranchGraph(scene);                            // attach the scene to SimpleUniverse

        setLayout(new BorderLayout());
        add("Center", canvas_3D);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java3D Demo with Sound"); // NOTE: change XY to your initials
        frame.getContentPane().add(new CodeLab1());         // create an instance of the class
        frame.setSize(600, 600);                             // set the size of the JFrame
        frame.setVisible(true);
    }
}
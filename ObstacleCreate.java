import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

import java.io.FileNotFoundException;
import java.util.Random;

public class ObstacleCreate {
    private static Point3d pt_zero = new Point3d(0d, 0d, 0d);
    public TransformGroup tg;
    public BranchGroup bg;
    public SimpleUniverse uni;
    public ObstacleCreate(SimpleUniverse su){
        uni=su;
    }
    public BranchGroup MakeObstacles(Vector3d pos) {
        tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D transM = new Transform3D();
        transM.set(pos);  // Create base TG with 'scale' and 'position'
        TransformGroup baseTG = new TransformGroup(transM);
        baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        baseTG.addChild(loadShape("images/Bomb.obj"));

        PositionInterpolator p=moveObstacle(baseTG);
        tg.addChild(baseTG);
//        tg.addChild(p);
        bg=new BranchGroup();
        bg.setName("bomb");
        bg.addChild(p);
        bg.addChild(tg);
        return bg;
    }

    public PositionInterpolator moveObstacle( TransformGroup t) {
        Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE|Alpha.DECREASING_ENABLE, 0, 0, 0, 0, 0, 3500, 0, 0);
        //alpha which takes 5 seconds to increase and decrease and loops infinitely
        Transform3D axisPosition = new Transform3D();
        axisPosition.rotY(Math.PI / 2.0);//making it along z axis
        PositionInterpolator pi = new PositionInterpolator(alpha, tg, axisPosition, -35f, 20f);//generating the translation between -0.5f and 0.5f
        pi.setSchedulingBounds(new BoundingSphere());//setting bounds
        return pi;
    }


    public TransformGroup loadShape(String st) {//this is the function we added to load the cow object
        int flags= ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY;
        ObjectFile f=new ObjectFile(flags, (float)(60*Math.PI/180.0));
        Scene s=null;
        try {
            s=f.load(st);//loading the image
        }
        catch(FileNotFoundException e) {//handling exception
            System.err.println(e);
            System.exit(1);
        }
        catch(ParsingErrorException e) {//handling exception
            System.err.println(e);
            System.exit(1);
        }
        catch(IncorrectFormatException e) {//handling exception
            System.err.println(e);
            System.exit(1);
        }
        BranchGroup bg=s.getSceneGroup();
        Shape3D cows=(Shape3D)bg.getChild(0);//getting the shape
        if(st=="images/model.obj"){
            Random rand=new Random();
            int r=rand.nextInt(7);
            cows.setAppearance(setApp(Commons.Clrs[r]));
            cows.setUserData(0);
            cows.setName("Car");
        }
        else if(st=="images/arbre 1.obj"){

        }
        else if(st=="images/sapin 2.obj"){

        }
        else
            cows.setAppearance(setApp(Commons.Red));//setting an appearance to the shape to change its color
        TransformGroup loadedShape=new TransformGroup();
        loadedShape.addChild(bg);//ADDING THE SHAPE TO THE TG
        Transform3D rot=new Transform3D();//transformation to rotate the object
        Transform3D scale=new Transform3D();//transformation to change the scale
        scale.setScale(0.4);
        rot.rotX(3*Math.PI/2);
        rot.mul(scale);
        return loadedShape;
    }
    public Appearance setApp(Color3f clr) {//function to set the appearance
        Appearance app = new Appearance();
        app.setMaterial(setMaterial(clr));
        ColoringAttributes colorAtt = new ColoringAttributes();
        colorAtt.setColor(clr);
        app.setColoringAttributes(colorAtt);
        return app;
    }
    public static Material setMaterial(Color3f clr) {
        int SH = 128;               // 10
//        Material ma = new Material();
//        Color3f c = new Color3f(0.6f*clr.x, 0.6f*clr.y, 0.6f*clr.z);
//        ma.setAmbientColor(c);
//        ma.setDiffuseColor(c);
//        ma.setSpecularColor(clr);
//        ma.setShininess(SH);
//        ma.setLightingEnable(true);

        Material ma = new Material();//creating material
        ma.setAmbientColor(new Color3f(0,0,0));
        ma.setEmissiveColor(new Color3f(0,0,0));
        ma.setDiffuseColor(clr);
        ma.setSpecularColor(new Color3f(1.0f,1.0f,1.0f));
        ma.setShininess(SH);
        ma.setLightingEnable(true);

        return ma;
    }
}
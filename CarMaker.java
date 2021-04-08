import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Vector3d;

import java.io.FileNotFoundException;
import java.util.Random;

public class CarMaker {
    public TransformGroup Car1;

    public TransformGroup Car(){
        TransformGroup tg =new TransformGroup();

        Car1=new TransformGroup();
        TransformGroup car=new TransformGroup();
        car.addChild(loadShape("images/model.obj"));
        Transform3D rotCar=new Transform3D();
        Transform3D setDown=new Transform3D();
        rotCar.rotY((Math.PI/3.4));
        setDown.set(new Vector3d(0,-0.7,10));
        setDown.mul(rotCar);
        car.setTransform(setDown);
        Car1.addChild(car);
        Car1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.addChild(Car1);

        return tg;
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
            cows.setAppearance(setApp(CommonsXY.Yellow));//setting an appearance to the shape to change its color
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
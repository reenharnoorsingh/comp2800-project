import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.vecmath.*;

import java.io.FileNotFoundException;
import java.util.Random;

public class Lamps {

    public TransformGroup tg;

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
    public Appearance setApp(Color3f clr) {//function to set the appearance
        Appearance app = new Appearance();
        app.setMaterial(setMaterial(clr));
        ColoringAttributes colorAtt = new ColoringAttributes();
        colorAtt.setColor(clr);
        app.setColoringAttributes(colorAtt);
        return app;
    }
    public TransformGroup LightPoles(){
        TransformGroup pole=new TransformGroup();
        TransformGroup poles=new TransformGroup();
        tg=new TransformGroup();
        pole.addChild(loadShape("images/lampPost.obj"));
        Transform3D rotater=new Transform3D();
        rotater.rotX(Math.PI/2);
        pole.setTransform(rotater);
        Transform3D scaled=new Transform3D();
        scaled.setScale(1.5);
        pole.setTransform(scaled);

        SpotLight myLight=new SpotLight();
        myLight.setEnable(true);
        myLight.setColor(Commons.White);
        myLight.setPosition(new Point3f(new Point3d(0.7,1.5f,0)));
        myLight.setAttenuation(new Point3f(1,0,0));
        myLight.setDirection(new Vector3f(0,-1,0));
        myLight.setSpreadAngle(0.785f);
        myLight.setConcentration(3f);
        myLight.setInfluencingBounds(new BoundingSphere(new Point3d(),1000));

        TransformGroup sp=new TransformGroup();
        Transform3D spSet=new Transform3D();
        spSet.setTranslation(new Vector3f(0.27f,0.76f,0));
        sp.setTransform(spSet);
        Sphere sphere=new Sphere(0.1f, Sphere.GENERATE_NORMALS,80);
        sphere.setAppearance(setApp(Commons.White));
        sp.addChild(sphere);
        pole.addChild(sp);

        SharedGroup shared = new SharedGroup();
        shared.addChild(pole);

        TransformGroup tg_link = null;
        Transform3D t3d_link = new Transform3D();

        for (float x = -2.9f; x <= 2.9f; x += 5.8f) {
            for (float z = -100f; z <= 100f; z += 5.0f) {

                t3d_link.setScale(1);
                if(x!=-2.9f)
                    t3d_link.rotY(Math.PI);
                t3d_link.setTranslation(new Vector3d(x, 0.4, z));
                tg_link = new TransformGroup(t3d_link);

                tg_link.addChild(new Link(shared));
                tg.addChild(tg_link);
            }
        }
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

}
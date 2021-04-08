/*
Group Project COMP-2800
Members:
        Harnoor Singh Reen - 110006294
        Shubham Mehta - 110027013
        Bill Shema - 104822276
        Ariya Rasekh - 105127455
        Vaishnavi Alhuwalia - 110026852
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;


import org.jdesktop.j3d.examples.collision.Box;
import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.Cone;
import org.jogamp.java3d.utils.geometry.Cylinder;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.utils.universe.PlatformGeometry;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.ViewingPlatform;
import org.jogamp.vecmath.*;

import static java.lang.System.exit;

public class Project extends JPanel implements MouseListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private static JFrame frame;
    private Canvas3D canvas;
    public PickTool pickTool;
    private static SoundUtilityJOAL soundJOAL;               // needed for sound
    private static TransformGroup viewtrans;
    private static TransformGroup Car1;
    public BranchGroup obstacleTG;
    public boolean Crashed;
    public boolean Menu;

    public boolean isExitFlag() {
        return ExitFlag;
    }

    public void setExitFlag(boolean exitFlag) {
        ExitFlag = exitFlag;
    }

    public  boolean ExitFlag;
    private static SimpleUniverse su;

    public CollisionDetectColumns getCd() {
        return cd;
    }

    public CollisionDetectColumns cd;
    public class BehaviorArrowKey extends Behavior {
        private TransformGroup navigatorTG;
        private WakeupOnAWTEvent wEnter;

        public BehaviorArrowKey(ViewingPlatform targetVP, TransformGroup chasedTG) {
            navigatorTG = chasedTG;
            targetVP.getViewPlatformTransform();
        }

        public void initialize() {
            wEnter = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
            wakeupOn(wEnter);                              // decide when behavior becomes live
        }

        public void processStimulus(Iterator<WakeupCriterion> criteria) {
            Transform3D navigatorTF = new Transform3D();   // get Transform3D from 'navigatorTG'
            Transform3D test=new Transform3D();
            test.set(new Vector3d(0,0,-0.03));
            navigatorTG.getTransform(navigatorTF);
            navigatorTF.mul(test);
            navigatorTG.setTransform(navigatorTF);
//            Vector3d vct = new Vector3d();
//            navigatorTF.get(vct);                          // get position of 'navigatorTG'

            wakeupOn(wEnter);                              // decide when behavior becomes live
        }
    }

    public static void initialSound() {
        soundJOAL = new SoundUtilityJOAL();
        if (!soundJOAL.load("carInit", 0f, 0f, 10f, true))
            System.out.println("Could not load " + "carInit");
        if (!soundJOAL.load("crash", 0f, 0f, 10f, false))
            System.out.println("Could not load " + "crash");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();

        if (key == 'w' || key=='W') {
            Transform3D navigatorTF = new Transform3D();   // get Transform3D from 'navigatorTG'
            Transform3D test=new Transform3D();
            test.set(new Vector3d(0,0,-0.3));
            Car1.getTransform(navigatorTF);
            navigatorTF.mul(test);
            Car1.setTransform(navigatorTF);
        }else
        if (key == 'a' || key=='A') {
            Transform3D navigatorTF = new Transform3D();   // get Transform3D from 'navigatorTG'
            Transform3D test=new Transform3D();
            test.set(new Vector3d(-0.3,0,0));
            Car1.getTransform(navigatorTF);
            Vector3d current=new Vector3d();
            navigatorTF.get(current);
            if(current.x>=-2.4) {
                navigatorTF.mul(test);
                Car1.setTransform(navigatorTF);
            }
        }else
        if (key == 's' || key=='S') {
            Transform3D navigatorTF = new Transform3D();   // get Transform3D from 'navigatorTG'
            Transform3D test=new Transform3D();
            test.set(new Vector3d(0,0,0.3));
            Car1.getTransform(navigatorTF);
            navigatorTF.mul(test);
            Car1.setTransform(navigatorTF);
        }else
        if (key == 'd' || key== 'D') {
            Transform3D navigatorTF = new Transform3D();   // get Transform3D from 'navigatorTG'
            Transform3D test=new Transform3D();
            test.set(new Vector3d(0.3,0,0));
            Car1.getTransform(navigatorTF);
            Vector3d current=new Vector3d();
            navigatorTF.get(current);
            if(current.x<=2.4) {
                navigatorTF.mul(test);
                Car1.setTransform(navigatorTF);
            }
        }else
        if (key == 'r' ) {
            Transform3D navigatorTF = new Transform3D();   // get Transform3D from 'navigatorTG'
            Transform3D test=new Transform3D();
            test.set(new Vector3d(0.3,0,0));
            Car1.getTransform(navigatorTF);
            navigatorTF.set(new Vector3d(0,0,0));
            Car1.setTransform(navigatorTF);
        }else
        if(key=='I' || key=='i'){
            Point3d current=Commons.getEye();
            current.z-=0.5;
            Commons.setEye(current);
            Commons.defineViewer(su);
        }else
        if(key=='j' || key=='J'){
            Point3d current=Commons.getEye();
            current.x-=0.5;
            Commons.setEye(current);
            Commons.defineViewer(su);
        }else
        if(key=='k' || key=='K'){
            Point3d current=Commons.getEye();
            current.z+=0.5;
            Commons.setEye(current);
            Commons.defineViewer(su);
        }else
        if(key=='L' || key=='l'){
            Point3d current=Commons.getEye();
            current.x+=0.5;
            Commons.setEye(current);
            Commons.defineViewer(su);
        }else
        if(key=='-'){
            Point3d current=Commons.getEye();
            current.y-=0.5;
            Commons.setEye(current);
            Commons.defineViewer(su);
        }else
        if(key=='='){
            Point3d current=Commons.getEye();
            current.y+=0.5;
            Commons.setEye(current);
            Commons.defineViewer(su);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    public Background addBackground(Color3f clr, BoundingSphere sphere){
        Background bg=new Background();//create a background
        bg.setImage(new TextureLoader("images/Retrosunset.jpg",null).getImage());//load image
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);//set scaling
        bg.setApplicationBounds(sphere);//setting bounds
        bg.setColor(clr);//setting color
        return bg;
    }

    public Texture texturedApp(String name) {//this maps the texture
        String filename =name;
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        if(image == null)
            System.out.println("File not found");
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        return texture;
    }

    public Material setMaterial() {//sets the material
        Material mat= new Material();
        int SH = 128;
        mat.setAmbientColor(new Color3f(0.6f, 0.6f, 0.6f));
        mat.setEmissiveColor(0.0f, 0.0f, 0.0f);
        mat.setDiffuseColor(new Color3f(0.6f, 0.6f, 0.6f));
        mat.setSpecularColor(1.0f, 1.0f, 1.0f);
        mat.setShininess(SH);
        mat.setLightingEnable(true);
        return mat;
    }

    public Appearance setApp(Color3f clr) {//function to set the appearance
        Appearance app = new Appearance();
        app.setMaterial(setMaterial(clr));
        ColoringAttributes colorAtt = new ColoringAttributes();
        colorAtt.setColor(clr);
        app.setColoringAttributes(colorAtt);
        return app;
    }

    private static TransformGroup txt1(String txt, float scl, Color3f clr, Point3f p, String click) {
        Font my2DFont = new Font("New Tegomin", Font.PLAIN, 1);
        FontExtrusion myExtrude = new FontExtrusion();
        Font3D font3D = new Font3D(my2DFont, myExtrude);
        Text3D text3D = new Text3D(font3D,txt,p);

        Transform3D scalar = new Transform3D();
        scalar.setScale(scl);
        Appearance look = new Appearance();
        look.setMaterial(new Material(clr, clr, clr, clr, 1));
        TransformGroup text = new TransformGroup(scalar);
        Shape3D s2 = new Shape3D(text3D, look);
        s2.setUserData(0);
        s2.setName(click);
        text.addChild(s2);

        return text;
    }
    private static TransformGroup button(Point3f[] verts, Color3f clr) {
        // Base Quadarray for the front side
        QuadArray sqr = new QuadArray(4, GeometryArray.NORMALS| GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        sqr.setCoordinates(0, verts);
        // 1/sqrt(3) as there are 3 normals
        float normal = (float)(1/Math.sqrt(3));
        float [] f1 = {normal,normal,normal};
        Appearance app = new Appearance();
        TransparencyAttributes T1 = new TransparencyAttributes(TransparencyAttributes.NICEST,0.9f);
        for(int i =0; i<4;i++) {
            sqr.setColor(i, clr);
            sqr.setNormal(i, f1);
        }

        Shape3D FrontF = new Shape3D(sqr);
        app.setTransparencyAttributes(T1);
        FrontF.setAppearance(app);
        TransformGroup tg = new TransformGroup();
        tg.addChild(FrontF);
        Transform3D scence = new Transform3D();
        TransformGroup stg = null;
        scence.setTranslation(new Vector3f(0,0,0.4f));
        stg = new TransformGroup(scence);

        tg.addChild(stg);
        return tg;
    }
    public Background addBackgroundMenu(Color3f clr, BoundingSphere sphere){
        Background bg=new Background();//create a background
        bg.setImage(new TextureLoader("images/menuScreen.jpg",null).getImage());//load image
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);//set scaling
        bg.setApplicationBounds(sphere);//setting bounds
        bg.setColor(clr);//setting color
        return bg;
    }
    public TransformGroup front(Boolean b,int sc) {
        TransformGroup stg = new TransformGroup();
        //Writing Instruction
        // With txt() we are inputing the text string, scale of the text, color of the text,
        // Point3f of the text and "no-effect" is for mouse click action, to identify the shape
//        Point3f [] verts = {  new Point3f(-1.5f,5f,0f), new Point3f(-1.5f,6f,0f), new Point3f(-0.5f,6f,0f),new Point3f(-0.5f,5f,0f)};
//        stg.addChild(button(verts,CommonsSM.Red));



//        Point3f [] verts1 = {   new Point3f(0.5f,5f,0f), new Point3f(0.5f,6f,0f), new Point3f(1.5f,6f,0f),new Point3f(1.5f,5f,0f)};
//        stg.addChild(button(verts1,CommonsSM.Red));
        if(b){
            Point3f b0 = new Point3f((float) -4,(float) 4,0);
            stg.addChild(txt1("Your Score was: "+sc,1f,Commons.Magenta,b0,"score"));
            Point3f b2 = new Point3f((float) -2.4,(float) 0,0);
            stg.addChild(txt1("Play Again?",1f,Commons.Magenta,b2,"again"));
            Point3f b3 = new Point3f((float) -1,(float) -4,0);
            stg.addChild(txt1("Exit",1f,Commons.Magenta,b3,"exit"));
            stg.addChild(addBackgroundMenu(Commons.Grey, new BoundingSphere()));
        }
        else {
            Point3f b1 = new Point3f((float) -2,(float) 6,0);
            stg.addChild(txt1("START",1f,Commons.Red,b1,"start"));
        }

        stg.setName("Text");

        return stg;
    }

    //    public TransformGroup BuildingMaker1(){
//        TransformGroup tg=new TransformGroup();
//
//        Appearance app=new Appearance();
//        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\buildingFace2.jpg"));
//        PolygonAttributes pa=new PolygonAttributes();
//        pa.setCullFace(PolygonAttributes.CULL_NONE);
//        app.setPolygonAttributes(pa);
//        TextureAttributes ta=new TextureAttributes();
//        ta.setTextureMode(TextureAttributes.REPLACE);
//        app.setTextureAttributes(ta);
//        Vector3d scaling=new Vector3d(1,1,8);
//        Transform3D trans=new Transform3D();
//        trans.setScale(scaling);
//        ta.setTextureTransform(trans);
//
//        Appearance app3=new Appearance();
//        app3.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app3.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\top.jpg"));
//        PolygonAttributes pa3=new PolygonAttributes();
//        pa3.setCullFace(PolygonAttributes.CULL_NONE);
//        app3.setPolygonAttributes(pa3);
//        TextureAttributes ta3=new TextureAttributes();
//        ta3.setTextureMode(TextureAttributes.REPLACE);
//        app3.setTextureAttributes(ta3);
//        Vector3d scaling3=new Vector3d(1,1,8);
//        Transform3D trans3=new Transform3D();
//        trans3.setScale(scaling3);
//        ta3.setTextureTransform(trans3);
//
//        QuadArray faceRight=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceRight.setCoordinate(0,new Point3f(4f,0,-4f));
//        faceRight.setCoordinate(1,new Point3f(4f,10,-4f));
//        faceRight.setCoordinate(2,new Point3f(4f,10,4f));
//        faceRight.setCoordinate(3,new Point3f(4f,0,4f));
//        float uv0[]={0f,0f};
//        float uv1[]={1f,0f};
//        float uv2[]={1f,1f};
//        float uv3[]={0f,1f};
//        faceRight.setTextureCoordinate(0,0,uv0);
//        faceRight.setTextureCoordinate(0,1,uv1);
//        faceRight.setTextureCoordinate(0,2,uv2);
//        faceRight.setTextureCoordinate(0,3,uv3);
//
//        QuadArray faceLeft=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceLeft.setCoordinate(0,new Point3f(-4f,0,-4f));
//        faceLeft.setCoordinate(1,new Point3f(-4f,10,-4f));
//        faceLeft.setCoordinate(2,new Point3f(-4f,10,4f));
//        faceLeft.setCoordinate(3,new Point3f(-4f,0,4f));
//        faceLeft.setTextureCoordinate(0,0,uv0);
//        faceLeft.setTextureCoordinate(0,1,uv1);
//        faceLeft.setTextureCoordinate(0,2,uv2);
//        faceLeft.setTextureCoordinate(0,3,uv3);
//
//        Shape3D r=new Shape3D(faceRight,app);
//        Shape3D l=new Shape3D(faceLeft,app);
//
//        Appearance app2=new Appearance();
//        app2.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app2.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\sideFace.jpg"));
//        PolygonAttributes pa2=new PolygonAttributes();
//        pa2.setCullFace(PolygonAttributes.CULL_NONE);
//        app2.setPolygonAttributes(pa);
//        TextureAttributes ta2=new TextureAttributes();
//        ta2.setTextureMode(TextureAttributes.REPLACE);
//        app2.setTextureAttributes(ta);
//        Vector3d scaling2=new Vector3d(1,1,8);
//        Transform3D trans2=new Transform3D();
//        trans2.setScale(scaling2);
//        ta2.setTextureTransform(trans2);
//
//        QuadArray faceUp=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceUp.setCoordinate(0,new Point3f(-4f,0,-4f));
//        faceUp.setCoordinate(1,new Point3f(-4f,10,-4f));
//        faceUp.setCoordinate(2,new Point3f(4f,10,-4f));
//        faceUp.setCoordinate(3,new Point3f(4f,0,-4f));
//        faceUp.setTextureCoordinate(0,0,uv0);
//        faceUp.setTextureCoordinate(0,1,uv1);
//        faceUp.setTextureCoordinate(0,2,uv2);
//        faceUp.setTextureCoordinate(0,3,uv3);
//
//        QuadArray faceDown=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceDown.setCoordinate(0,new Point3f(-4f,0,4f));
//        faceDown.setCoordinate(1,new Point3f(-4f,10,4f));
//        faceDown.setCoordinate(2,new Point3f(4f,10,4f));
//        faceDown.setCoordinate(3,new Point3f(4f,0,4f));
//        faceDown.setTextureCoordinate(0,0,uv0);
//        faceDown.setTextureCoordinate(0,1,uv1);
//        faceDown.setTextureCoordinate(0,2,uv2);
//        faceDown.setTextureCoordinate(0,3,uv3);
//
//        QuadArray top=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        top.setCoordinate(0,new Point3f(-4f,10,4f));
//        top.setCoordinate(1,new Point3f(-4f,10,-4f));
//        top.setCoordinate(2,new Point3f(4f,10,-4f));
//        top.setCoordinate(3,new Point3f(4f,10,4f));
//        top.setTextureCoordinate(0,0,uv0);
//        top.setTextureCoordinate(0,1,uv1);
//        top.setTextureCoordinate(0,2,uv2);
//        top.setTextureCoordinate(0,3,uv3);
//
//        Shape3D t=new Shape3D(top,app3);
//        Shape3D u=new Shape3D(faceUp,app2);
//        Shape3D d=new Shape3D(faceDown,app2);
//
//        tg.addChild(r);
//        tg.addChild(l);
//        tg.addChild(u);
//        tg.addChild(d);
//        tg.addChild(t);
//        return  tg;
//    }
//
//    public TransformGroup BuildingMaker2(){
//        TransformGroup tg=new TransformGroup();
//
//        Appearance app=new Appearance();
//        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\buildingFace.jpg"));
//        PolygonAttributes pa=new PolygonAttributes();
//        pa.setCullFace(PolygonAttributes.CULL_NONE);
//        app.setPolygonAttributes(pa);
//        TextureAttributes ta=new TextureAttributes();
//        ta.setTextureMode(TextureAttributes.REPLACE);
//        app.setTextureAttributes(ta);
//        Vector3d scaling=new Vector3d(1,1,8);
//        Transform3D trans=new Transform3D();
//        trans.setScale(scaling);
//        ta.setTextureTransform(trans);
//
//        Appearance app3=new Appearance();
//        app3.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app3.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\top2.jpg"));
//        PolygonAttributes pa3=new PolygonAttributes();
//        pa3.setCullFace(PolygonAttributes.CULL_NONE);
//        app3.setPolygonAttributes(pa3);
//        TextureAttributes ta3=new TextureAttributes();
//        ta3.setTextureMode(TextureAttributes.REPLACE);
//        app3.setTextureAttributes(ta3);
//        Vector3d scaling3=new Vector3d(1,1,8);
//        Transform3D trans3=new Transform3D();
//        trans3.setScale(scaling3);
//        ta3.setTextureTransform(trans3);
//
//        QuadArray faceRight=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceRight.setCoordinate(0,new Point3f(4f,0,-4f));
//        faceRight.setCoordinate(1,new Point3f(4f,6,-4f));
//        faceRight.setCoordinate(2,new Point3f(4f,6,4f));
//        faceRight.setCoordinate(3,new Point3f(4f,0,4f));
//        float uv0[]={0f,0f};
//        float uv1[]={1f,0f};
//        float uv2[]={1f,1f};
//        float uv3[]={0f,1f};
//        faceRight.setTextureCoordinate(0,0,uv0);
//        faceRight.setTextureCoordinate(0,1,uv1);
//        faceRight.setTextureCoordinate(0,2,uv2);
//        faceRight.setTextureCoordinate(0,3,uv3);
//
//        QuadArray faceLeft=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceLeft.setCoordinate(0,new Point3f(-4f,0,-4f));
//        faceLeft.setCoordinate(1,new Point3f(-4f,6,-4f));
//        faceLeft.setCoordinate(2,new Point3f(-4f,6,4f));
//        faceLeft.setCoordinate(3,new Point3f(-4f,0,4f));
//        faceLeft.setTextureCoordinate(0,0,uv0);
//        faceLeft.setTextureCoordinate(0,1,uv1);
//        faceLeft.setTextureCoordinate(0,2,uv2);
//        faceLeft.setTextureCoordinate(0,3,uv3);
//
//        Shape3D r=new Shape3D(faceRight,app);
//        Shape3D l=new Shape3D(faceLeft,app);
//
//        Appearance app2=new Appearance();
//        app2.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app2.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\sideFace.jpg"));
//        PolygonAttributes pa2=new PolygonAttributes();
//        pa2.setCullFace(PolygonAttributes.CULL_NONE);
//        app2.setPolygonAttributes(pa);
//        TextureAttributes ta2=new TextureAttributes();
//        ta2.setTextureMode(TextureAttributes.REPLACE);
//        app2.setTextureAttributes(ta);
//        Vector3d scaling2=new Vector3d(1,1,8);
//        Transform3D trans2=new Transform3D();
//        trans2.setScale(scaling2);
//        ta2.setTextureTransform(trans2);
//
//        QuadArray faceUp=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceUp.setCoordinate(0,new Point3f(-4f,0,-4f));
//        faceUp.setCoordinate(1,new Point3f(-4f,6,-4f));
//        faceUp.setCoordinate(2,new Point3f(4f,6,-4f));
//        faceUp.setCoordinate(3,new Point3f(4f,0,-4f));
//        faceUp.setTextureCoordinate(0,0,uv0);
//        faceUp.setTextureCoordinate(0,1,uv1);
//        faceUp.setTextureCoordinate(0,2,uv2);
//        faceUp.setTextureCoordinate(0,3,uv3);
//
//        QuadArray faceDown=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        faceDown.setCoordinate(0,new Point3f(-4f,0,4f));
//        faceDown.setCoordinate(1,new Point3f(-4f,6,4f));
//        faceDown.setCoordinate(2,new Point3f(4f,6,4f));
//        faceDown.setCoordinate(3,new Point3f(4f,0,4f));
//        faceDown.setTextureCoordinate(0,0,uv0);
//        faceDown.setTextureCoordinate(0,1,uv1);
//        faceDown.setTextureCoordinate(0,2,uv2);
//        faceDown.setTextureCoordinate(0,3,uv3);
//
//        QuadArray top=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        top.setCoordinate(0,new Point3f(-4f,6,4f));
//        top.setCoordinate(1,new Point3f(-4f,6,-4f));
//        top.setCoordinate(2,new Point3f(4f,6,-4f));
//        top.setCoordinate(3,new Point3f(4f,6,4f));
//        top.setTextureCoordinate(0,0,uv0);
//        top.setTextureCoordinate(0,1,uv1);
//        top.setTextureCoordinate(0,2,uv2);
//        top.setTextureCoordinate(0,3,uv3);
//
//        Shape3D u=new Shape3D(faceUp,app2);
//        Shape3D d=new Shape3D(faceDown,app2);
//        Shape3D t=new Shape3D(top,app3);
//
//        tg.addChild(r);
//        tg.addChild(l);
//        tg.addChild(u);
//        tg.addChild(d);
//        tg.addChild(t);
//        return  tg;
//    }
    public TransformGroup positionBuilding(){
        TransformGroup tg=new TransformGroup();

        TransformGroup both=new TransformGroup();

        BuildingMaker b=new BuildingMaker();

        TransformGroup t1=b.BuildingMaker1();
        TransformGroup t2=b.BuildingMaker2();

        Transform3D td1=new Transform3D();
        Transform3D td2=new Transform3D();

        td1.set(new Vector3d(0,-1,0));
        td2.set(new Vector3d(0,-1,9));

        t1.setTransform(td1);
        t2.setTransform(td2);

        both.addChild(t1);
        both.addChild(t2);

        SharedGroup shared = new SharedGroup();
        shared.addChild(both);

        TransformGroup tg_link = null;
        Transform3D t3d_link = new Transform3D();

        for (float x = -12f; x <= 12f; x += 24f) {
            for (float z = -100f; z <= 100f; z +=18f) {

                t3d_link.setScale(1);
                if(x!=-10f)
                    t3d_link.rotY(Math.PI);
                t3d_link.setTranslation(new Vector3d(x, -1, z));
                tg_link = new TransformGroup(t3d_link);

                tg_link.addChild(new Link(shared));
                tg.addChild(tg_link);
            }
        }
        tg.setCollidable(false);
        return tg;
    }

    //    public TransformGroup Road(){
//        TransformGroup tg=new TransformGroup();
//
//
//        Appearance app=new Appearance();
//        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
//        app.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\road2.png"));
//        PolygonAttributes pa=new PolygonAttributes();
//        pa.setCullFace(PolygonAttributes.CULL_NONE);
//        app.setPolygonAttributes(pa);
//        TextureAttributes ta=new TextureAttributes();
//        ta.setTextureMode(TextureAttributes.REPLACE);
//        app.setTextureAttributes(ta);
//        Vector3d scaling=new Vector3d(1,1,8);
//        Transform3D trans=new Transform3D();
//        trans.setScale(scaling);
//        ta.setTextureTransform(trans);
//
//        QuadArray road=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        road.setCoordinate(0,new Point3f(3f,-1,100));
//        road.setCoordinate(1,new Point3f(3f,-1,-100));
//        road.setCoordinate(2,new Point3f(-3f,-1,-100));
//        road.setCoordinate(3,new Point3f(-3f,-1,100));
//        float uv0[]={0f,0f};
//        float uv1[]={1f,0f};
//        float uv2[]={1f,1f};
//        float uv3[]={0f,1f};
//        road.setTextureCoordinate(0,0,uv0);
//        road.setTextureCoordinate(0,1,uv1);
//        road.setTextureCoordinate(0,2,uv2);
//        road.setTextureCoordinate(0,3,uv3);
//        Shape3D r=new Shape3D(road,app);
//        r.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
//        r.setName("Road");
//        r.setUserData(0);
//        tg.addChild(r);
//
//        return tg;
//    }
    public TransformGroup MakeRoad(){
        RoadMaker r=new RoadMaker();
        TransformGroup tg=new TransformGroup();
        tg.addChild(r.Road());
        tg.setCollidable(false);
        return tg;
    }

    //    public TransformGroup LightPoles(){
//        TransformGroup pole=new TransformGroup();
//        TransformGroup poles=new TransformGroup();
//        TransformGroup tg=new TransformGroup();
//        pole.addChild(loadShape("C:\\Users\\Shubham\\Desktop\\lampPost.obj"));
//        Transform3D rotater=new Transform3D();
//        rotater.rotX(Math.PI/2);
//        pole.setTransform(rotater);
//        Transform3D scaled=new Transform3D();
//        scaled.setScale(1.5);
//        pole.setTransform(scaled);
//
//        SpotLight myLight=new SpotLight();
//        myLight.setEnable(true);
//        myLight.setColor(CommonsSM.White);
//        myLight.setPosition(new Point3f(new Point3d(0.7,1.5f,0)));
//        myLight.setAttenuation(new Point3f(1,0,0));
//        myLight.setDirection(new Vector3f(0,-1,0));
//        myLight.setSpreadAngle(0.785f);
//        myLight.setConcentration(3f);
//        myLight.setInfluencingBounds(new BoundingSphere(new Point3d(),1000));
//
//        TransformGroup sp=new TransformGroup();
//        Transform3D spSet=new Transform3D();
//        spSet.setTranslation(new Vector3f(0.27f,0.76f,0));
//        sp.setTransform(spSet);
//        Sphere sphere=new Sphere(0.1f, Sphere.GENERATE_NORMALS,80);
//        sphere.setAppearance(setApp(CommonsSM.White));
//        sp.addChild(sphere);
//        pole.addChild(sp);
//
//        SharedGroup shared = new SharedGroup();
//        shared.addChild(pole);
//
//        TransformGroup tg_link = null;
//        Transform3D t3d_link = new Transform3D();
//
//        for (float x = -2.9f; x <= 2.9f; x += 5.8f) {
//            for (float z = -100f; z <= 100f; z += 5.0f) {
//
//                t3d_link.setScale(1);
//                if(x!=-2.9f)
//                    t3d_link.rotY(Math.PI);
//                t3d_link.setTranslation(new Vector3d(x, 0.4, z));
//                tg_link = new TransformGroup(t3d_link);
//
//                tg_link.addChild(new Link(shared));
//                tg.addChild(tg_link);
//            }
//        }
//        return tg;
//    }
    public  TransformGroup MakeLamps(){
        Lamps l=new Lamps();
        TransformGroup tg=l.LightPoles();
        tg.setCollidable(false);
        return tg;
    }

    //    public TransformGroup SurroundAreaLeft(){
//        TransformGroup tg=new TransformGroup();
//
//        Appearance app=new Appearance();
//        app.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\soil.jpg"));
//        PolygonAttributes pa=new PolygonAttributes();
//        pa.setCullFace(PolygonAttributes.CULL_NONE);
//        app.setPolygonAttributes(pa);
//        TextureAttributes ta=new TextureAttributes();
//        ta.setTextureMode(TextureAttributes.REPLACE);
//        app.setTextureAttributes(ta);
//        Vector3d scaling=new Vector3d(1,1,8);
//        Transform3D trans=new Transform3D();
//        trans.setScale(scaling);
//        ta.setTextureTransform(trans);
//
//
//        QuadArray surround=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        surround.setCoordinate(0,new Point3f(-3f,-1,100));
//        surround.setCoordinate(1,new Point3f(-3f,-1,-100));
//        surround.setCoordinate(2,new Point3f(-11f,-1,-100));
//        surround.setCoordinate(3,new Point3f(-11f,-1,100));
//        float uv0[]={0f,0f};
//        float uv1[]={1f,0f};
//        float uv2[]={1f,1f};
//        float uv3[]={0f,1f};
//        surround.setTextureCoordinate(0,0,uv0);
//        surround.setTextureCoordinate(0,1,uv1);
//        surround.setTextureCoordinate(0,2,uv2);
//        surround.setTextureCoordinate(0,3,uv3);
//        tg.addChild(new Shape3D(surround,app));
//
//        return tg;
//    }
//
//    public TransformGroup SurroundAreaRight(){
//        TransformGroup tg=new TransformGroup();
//
//        Appearance app=new Appearance();
//        app.setTexture(texturedApp("C:\\Users\\Shubham\\Desktop\\soil.jpg"));
//        PolygonAttributes pa=new PolygonAttributes();
//        pa.setCullFace(PolygonAttributes.CULL_NONE);
//        app.setPolygonAttributes(pa);
//        TextureAttributes ta=new TextureAttributes();
//        ta.setTextureMode(TextureAttributes.REPLACE);
//        app.setTextureAttributes(ta);
//        Vector3d scaling=new Vector3d(1,1,8);
//        Transform3D trans=new Transform3D();
//        trans.setScale(scaling);
//        ta.setTextureTransform(trans);
//
//
//        QuadArray surround=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
//        surround.setCoordinate(0,new Point3f(3f,-1,100));
//        surround.setCoordinate(1,new Point3f(3f,-1,-100));
//        surround.setCoordinate(2,new Point3f(11f,-1,-100));
//        surround.setCoordinate(3,new Point3f(11f,-1,100));
//        float uv0[]={0f,0f};
//        float uv1[]={1f,0f};
//        float uv2[]={1f,1f};
//        float uv3[]={0f,1f};
//        surround.setTextureCoordinate(0,0,uv0);
//        surround.setTextureCoordinate(0,1,uv1);
//        surround.setTextureCoordinate(0,2,uv2);
//        surround.setTextureCoordinate(0,3,uv3);
//        tg.addChild(new Shape3D(surround,app));
//
//        return tg;
//    }
    public TransformGroup MakeGround(){
        GroundMaker g=new GroundMaker();
        TransformGroup tg=new TransformGroup();
        tg.addChild(g.SurroundAreaLeft());
        tg.addChild(g.SurroundAreaRight());
        tg.setCollidable(false);
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
            exit(1);
        }
        catch(ParsingErrorException e) {//handling exception
            System.err.println(e);
            exit(1);
        }
        catch(IncorrectFormatException e) {//handling exception
            System.err.println(e);
            exit(1);
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

    public TransformGroup MakeCar(){
        TransformGroup tg=new TransformGroup();
        CarMaker c=new CarMaker();
        Car1=c.Car();
        Car1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Car1.setCollidable(false);

        TransformGroup tg2=new TransformGroup();
//        tg2.setCollidable(true);

        Appearance app=new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(0.6f, 0.3f, 0.0f); // set column's color and make changeable
        app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        TransparencyAttributes ta=new TransparencyAttributes(TransparencyAttributes.FASTEST,1);
        app.setTransparencyAttributes(ta);
        app.setColoringAttributes(ca);

        QuadArray test=new QuadArray(4,QuadArray.COORDINATES);
        test.setCoordinate(0,new Point3f(0.5f,0.5f,-0.7f));
        test.setCoordinate(1,new Point3f(-0.5f,0.5f,-0.7f));
        test.setCoordinate(2,new Point3f(-0.5f,-0.5f,-0.7f));
        test.setCoordinate(3,new Point3f(0.5f,-0.5f,-0.7f));
        Shape3D shape3D=new Shape3D(test,app);

        tg2.addChild(shape3D);
        Transform3D transform=new Transform3D();
        transform.set(new Vector3d(0,-1,9.3));
        tg2.setTransform(transform);

        cd=new CollisionDetectColumns(shape3D);
        cd.setSchedulingBounds(new BoundingSphere());
        Car1.addChild(cd);
        Car1.addChild(tg2);

        return Car1;
    }

    public TransformGroup Trees(){
        TransformGroup tg=new TransformGroup();

        TransformGroup t1=new TransformGroup();
        TransformGroup t2=new TransformGroup();
        Transform3D td1=new Transform3D();
        Transform3D td2=new Transform3D();
        td1.set(new Vector3d(0,0,1.5));
        td1.setScale(1.5);
        td2.setScale(1.5);
        t1.addChild(loadShape("images/arbre 1.obj"));
        t2.addChild(loadShape("images/sapin 2.obj"));
        t1.setTransform(td1);
        t2.setTransform(td2);
        TransformGroup trees2=new TransformGroup();
        trees2.addChild(t1);
        trees2.addChild(t2);

        SharedGroup shared = new SharedGroup();
        shared.addChild(trees2);

        TransformGroup tg_link = null;
        Transform3D t3d_link = new Transform3D();

        for (float x = -4f; x <= 4f; x += 8f) {
            for (float z = -100f; z <= 100f; z += 4.0f) {

                t3d_link.setScale(1);
                if(x!=-4f)
                    t3d_link.rotY(Math.PI);
                t3d_link.setTranslation(new Vector3d(x, 0.4, z));
                tg_link = new TransformGroup(t3d_link);

                tg_link.addChild(new Link(shared));
                tg.addChild(tg_link);
            }
        }
        TransformGroup tg_link2 = null;
        Transform3D t3d_link2 = new Transform3D();

        for (float x = -6f; x <= 6f; x += 12f) {
            for (float z = -100f; z <= 100f; z += 4.0f) {

                t3d_link2.setScale(1);
                if(x!=-4f)
                    t3d_link2.rotY(Math.PI);
                t3d_link2.setTranslation(new Vector3d(x, 0.4, z));
                tg_link2 = new TransformGroup(t3d_link2);

                tg_link2.addChild(new Link(shared));
                tg.addChild(tg_link2);
            }
        }
        return tg;
    }

    public void keyPressed(KeyEvent e){
//        char c=e.getKeyChar();
//        if(c=='w' || c=='a' || c=='s' || c=='d')
//            System.out.println("asdasd");
    }

    public KeyNavigatorBehavior keyNavigation(SimpleUniverse simple_U){
        ViewingPlatform view_plat=simple_U.getViewingPlatform();
        TransformGroup view_TG=view_plat.getViewPlatformTransform();
        KeyNavigatorBehavior keyNavBeh=new KeyNavigatorBehavior(view_TG);
        BoundingSphere view_bounds=new BoundingSphere(new Point3d(),20);
        keyNavBeh.setSchedulingBounds(view_bounds);
        return  keyNavBeh;
    }


    public BranchGroup RandomObs(Vector3d vd){
        ObstacleCreate c=new ObstacleCreate(su);
        obstacleTG=new BranchGroup();
        obstacleTG.addChild(c.MakeObstacles(vd));
        return obstacleTG;
    }


    public int PlayGame(SimpleUniverse su, Project p){

        Random rand=new Random();
        double lane[]={-2.3,0,2.3};
        int score=0;

        BranchGroup sceneBG=p.createScene(su);
        sceneBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        TransformGroup frontTG=(TransformGroup)sceneBG.getChild(0);
        TransformGroup textTG=(TransformGroup)frontTG.getChild(0);
        TransformGroup startText=(TransformGroup)textTG.getChild(0);
        Shape3D test=(Shape3D)startText.getChild(0);
        Text3D text3d=(Text3D)test.getGeometry();

        text3d.setString("Score: 0");
        text3d.setCapability(Text3D.ALLOW_STRING_WRITE);

        sceneBG.setCapability(BranchGroup.ALLOW_DETACH);
        addLights(sceneBG, Commons.White,new Point3f(4,3,100));
        addLights(sceneBG, Commons.White,new Point3f(4,3,0));
        addLights(sceneBG, Commons.White,new Point3f(4,3,-50));
        soundJOAL.play("carInit");
        sceneBG.compile();
        su.addBranchGraph(sceneBG); // attach the scene to SimpleUniverse

        while (!p.getCd().getCr()){
            int r=rand.nextInt(2);
            int k=0;
            if(r==0){
                int j=rand.nextInt(2);
                if(j==0)
                    k=1;
                else k=2;
            }
            else if(r==1){
                int j=rand.nextInt(2);
                if(j==0)
                    k=0;
                else k=2;
            }
            BranchGroup g=new BranchGroup();
            g.setCapability(BranchGroup.ALLOW_DETACH);
            g.addChild(p.RandomObs(new Vector3d(lane[r],0,-20)));
            BranchGroup g2=new BranchGroup();
            g2.setCapability(BranchGroup.ALLOW_DETACH);
            g2.addChild(p.RandomObs(new Vector3d(lane[k],0,-20)));
            su.addBranchGraph(g);
            su.addBranchGraph(g2);
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!p.getCd().getCr()) {
                score += 10;
                text3d.setString("Score: " + score);
            }
            g.detach();
            g2.detach();
        }
        soundJOAL.stop("carInit");
        soundJOAL.play("crash");
        sceneBG.detach();
        p.getCd().setCr(false);

        return score;
    }

    /* a function to create and return the scene BranchGroup */
    public BranchGroup createScene(SimpleUniverse su) {

        BranchGroup sceneBG = new BranchGroup();
        TransformGroup sceneTG=new TransformGroup();
        sceneBG.addChild(sceneTG);
        sceneTG.addChild(front(false,0));
        sceneTG.addChild(MakeRoad());
        sceneTG.addChild(MakeCar());
        sceneTG.addChild(positionBuilding());
        sceneTG.addChild(Trees());
        sceneTG.addChild(MakeGround());
        sceneTG.addChild(MakeLamps());

        sceneBG.addChild(addBackground(Commons.Grey, new BoundingSphere()));	///add Background

        pickTool = new PickTool( sceneBG );                   // allow picking of objs in 'sceneBG'
        pickTool.setMode(PickTool.BOUNDS);

        return sceneBG;
    }

    /* a constructor to set up and run the application */
    public Project() {
        ExitFlag=false;
        Menu=true;
        Crashed=false;
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        initialSound();
        canvas.addMouseListener(this);                        // NOTE: enable mouse clicking
        canvas.addKeyListener(this);
        su = new SimpleUniverse(canvas);       // create a SimpleUniverse
        Commons.setEye(new Point3d(0, 4, 20.0));
        Commons.defineViewer(su);                           // set the viewer's location

        su.getViewer().getView().setBackClipDistance(25);
        setLayout(new BorderLayout());
        add("Center", canvas);

        frame.setSize(600, 600);                              // set the size of the JFrame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        frame = new JFrame("Group Project 6M");
        Project p=new Project();
        frame.getContentPane().add(p);
//        BranchGroup b=new BranchGroup();
//        b.addChild(p.front());
//        su.addBranchGraph(b);
        while (!p.ExitFlag){

            int s=p.PlayGame(su,p);
            BranchGroup bg=new BranchGroup();
            bg.setCapability(BranchGroup.ALLOW_DETACH);
            bg.addChild(p.front(true,s));
            p.pickTool = new PickTool( bg );                   // allow picking of objs in 'sceneBG'
            p.pickTool.setMode(PickTool.BOUNDS);
            su.addBranchGraph(bg);
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bg.detach();
        }
        exit(0);
        System.out.println("game ended");

    }
    @Override
    public void mouseClicked(MouseEvent event) {
        int x = event.getX(); int y = event.getY();           // mouse coordinates
        Point3d point3d = new Point3d(), center = new Point3d();
        canvas.getPixelLocationInImagePlate(x, y, point3d);   // obtain AWT pixel in ImagePlate coordinates
        canvas.getCenterEyeInImagePlate(center);              // obtain eye's position in IP coordinates

        Transform3D transform3D = new Transform3D();          // matrix to relate ImagePlate coordinates~
        canvas.getImagePlateToVworld(transform3D);            // to Virtual World coordinates
        transform3D.transform(point3d);                       // transform 'point3d' with 'transform3D'
        transform3D.transform(center);                        // transform 'center' with 'transform3D'

        Vector3d mouseVec = new Vector3d();
        mouseVec.sub(point3d, center);
        mouseVec.normalize();
        pickTool.setShapeRay(point3d, mouseVec);              // send a PickRay for intersection

        if (pickTool.pickClosest() != null) {
            PickResult pickResult = pickTool.pickClosest();   // obtain the closest hit
            Shape3D box = (Shape3D) pickResult.getNode(PickResult.SHAPE3D);
            Appearance app = box.getAppearance();                // originally a PRIMITIVE as a box
            String s=box.getName();                 // we idnetify the sphere from its name
            if ((int) box.getUserData() == 0) {               // retrieve 'UserData'
                if(s=="Car")    //if the biggest sphere
                    System.out.println("Selected");
                else if (s=="Road"){
                    app.setTexture(texturedApp("images/road1.png"));
                    box.setUserData(1);                           // set 'UserData' to a new value
                }
                else if(s=="start")
                    System.out.println("you selected start");
                else if(s=="again"){
                    setExitFlag(false);
                }
                else if(s=="exit"){
                    exit(0);
                }
            }
            else { //if they have been changed with the image then reset
                if(s=="Road")
                    app.setTexture(texturedApp("images/road2.png"));
                box.setUserData(0);                           // reset 'UserData'
            }
//            box.setAppearance(app);                           // change box's appearance
        }
    }
    public void mouseEntered(MouseEvent arg0) { }
    public void mouseExited(MouseEvent arg0) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    /* a function to add ambient light and a point light to 'sceneBG' */
    public static void addLights(BranchGroup sceneBG, Color3f clr, Point3f point) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100000.0);
        AmbientLight amLgt = new AmbientLight(new Color3f(0.2f, 0.2f, 0.2f));
        amLgt.setInfluencingBounds(bounds);
        sceneBG.addChild(amLgt);
        Point3f pt  = new Point3f(point);
        Point3f atn = new Point3f(1.0f, 0.0f, 0.0f);
        PointLight ptLight = new PointLight(clr, pt, atn);
        ptLight.setInfluencingBounds(bounds);
        sceneBG.addChild(ptLight);
    }

    /* a function to create and return material definition */
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
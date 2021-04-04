/*
Group Project COMP-2800
Members:
        Harnoor Singh Reen - 110006294
        Shubham Mehta -
        Bill Shema - 104822276
        Ariya Rasekh - 105127455
        Vaishnavi Alhuwalia - 110026852
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

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
import org.jogamp.vecmath.*;

public class Project extends JPanel implements MouseListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private static JFrame frame;

    private Canvas3D canvas;
    private static PickTool pickTool;
    private static RotationInterpolator r1;
    private static RotationInterpolator r2;
    private static RotationInterpolator r3;
    private static TransformGroup viewtrans;

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    private static Background addBackground(Color3f clr, BoundingSphere sphere){
        Background bg=new Background();//create a background
        bg.setImage(new TextureLoader("images/sunset2.jpg",null).getImage());//load image
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);//set scaling
        bg.setApplicationBounds(sphere);//setting bounds
        bg.setColor(clr);//setting color
        return bg;
    }
    public static Texture texturedApp(String name) {//this maps the texture
        String filename =name;
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        if(image == null)
            System.out.println("File not found");
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        return texture;
    }
    private static Material setMaterial() {//sets the material
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

    private static Appearance setApp(Color3f clr) {//function to set the appearance
        Appearance app = new Appearance();
        app.setMaterial(setMaterial(clr));
        ColoringAttributes colorAtt = new ColoringAttributes();
        colorAtt.setColor(clr);
        app.setColoringAttributes(colorAtt);
        return app;
    }

    private static TransformGroup Road(){
        TransformGroup tg=new TransformGroup();


        Appearance app=new Appearance();
        app.setTexture(texturedApp("images/road2.png"));
        PolygonAttributes pa=new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(pa);
        TextureAttributes ta=new TextureAttributes();
        ta.setTextureMode(TextureAttributes.REPLACE);
        app.setTextureAttributes(ta);
        Vector3d scaling=new Vector3d(1,1,8);
        Transform3D trans=new Transform3D();
        trans.setScale(scaling);
        ta.setTextureTransform(trans);


        QuadArray road=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        road.setCoordinate(0,new Point3f(3f,-1,100));
        road.setCoordinate(1,new Point3f(3f,-1,-100));
        road.setCoordinate(2,new Point3f(-3f,-1,-100));
        road.setCoordinate(3,new Point3f(-3f,-1,100));
        float uv0[]={0f,0f};
        float uv1[]={1f,0f};
        float uv2[]={1f,1f};
        float uv3[]={0f,1f};
        road.setTextureCoordinate(0,0,uv0);
        road.setTextureCoordinate(0,1,uv1);
        road.setTextureCoordinate(0,2,uv2);
        road.setTextureCoordinate(0,3,uv3);
        tg.addChild(new Shape3D(road,app));

        return tg;
    }

    private static TransformGroup LightPoles(){
        TransformGroup pole=new TransformGroup();
        TransformGroup poles=new TransformGroup();
        TransformGroup tg=new TransformGroup();
        pole.addChild(loadShape("C:images/lampPost.obj"));
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

    private  static TransformGroup SurroundAreaLeft(){
        TransformGroup tg=new TransformGroup();

        Appearance app=new Appearance();
        app.setTexture(texturedApp("images/soil.jpg"));
        PolygonAttributes pa=new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(pa);
        TextureAttributes ta=new TextureAttributes();
        ta.setTextureMode(TextureAttributes.REPLACE);
        app.setTextureAttributes(ta);
        Vector3d scaling=new Vector3d(1,1,8);
        Transform3D trans=new Transform3D();
        trans.setScale(scaling);
        ta.setTextureTransform(trans);


        QuadArray surround=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        surround.setCoordinate(0,new Point3f(-3f,-1,100));
        surround.setCoordinate(1,new Point3f(-3f,-1,-100));
        surround.setCoordinate(2,new Point3f(-11f,-1,-100));
        surround.setCoordinate(3,new Point3f(-11f,-1,100));
        float uv0[]={0f,0f};
        float uv1[]={1f,0f};
        float uv2[]={1f,1f};
        float uv3[]={0f,1f};
        surround.setTextureCoordinate(0,0,uv0);
        surround.setTextureCoordinate(0,1,uv1);
        surround.setTextureCoordinate(0,2,uv2);
        surround.setTextureCoordinate(0,3,uv3);
        tg.addChild(new Shape3D(surround,app));

        return tg;
    }

    private  static TransformGroup SurroundAreaRight(){
        TransformGroup tg=new TransformGroup();

        Appearance app=new Appearance();
        app.setTexture(texturedApp("images/soil.jpg"));
        PolygonAttributes pa=new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(pa);
        TextureAttributes ta=new TextureAttributes();
        ta.setTextureMode(TextureAttributes.REPLACE);
        app.setTextureAttributes(ta);
        Vector3d scaling=new Vector3d(1,1,8);
        Transform3D trans=new Transform3D();
        trans.setScale(scaling);
        ta.setTextureTransform(trans);


        QuadArray surround=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        surround.setCoordinate(0,new Point3f(3f,-1,100));
        surround.setCoordinate(1,new Point3f(3f,-1,-100));
        surround.setCoordinate(2,new Point3f(11f,-1,-100));
        surround.setCoordinate(3,new Point3f(11f,-1,100));
        float uv0[]={0f,0f};
        float uv1[]={1f,0f};
        float uv2[]={1f,1f};
        float uv3[]={0f,1f};
        surround.setTextureCoordinate(0,0,uv0);
        surround.setTextureCoordinate(0,1,uv1);
        surround.setTextureCoordinate(0,2,uv2);
        surround.setTextureCoordinate(0,3,uv3);
        tg.addChild(new Shape3D(surround,app));

        return tg;
    }

    private static TransformGroup loadShape(String st) {//this is the function we added to load the cow object
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

    private static TransformGroup Trees(){
        TransformGroup tg =new TransformGroup();

        TransformGroup t1bot=new TransformGroup();
        Cylinder stalk1=new Cylinder(0.5f,1);
        t1bot.addChild(stalk1);
        TransformGroup t1top =new TransformGroup();
        Cone top1=new Cone(0.5f,3);
        t1top.addChild(top1);
        Transform3D top1Transform=new Transform3D();
        top1Transform.setTranslation(new Vector3f(0,2f,0));
        t1top.setTransform(top1Transform);
        TransformGroup tree1=new TransformGroup();
//        tree1.addChild(t1bot);
        tree1.addChild(t1top);
        tg.addChild(tree1);

        return tg;
    }


    public void keyPressed(KeyEvent e){
        if((e.getKeyCode()==KeyEvent.VK_Z)){//if the key pressed is Z then change the rotation of largest sphere
            long r=r1.getAlpha().getIncreasingAlphaDuration();//get the inc alpha value
            if(r==10000)//if its spinning then make it stop
                r1.setAlpha(new Alpha(-1,0));
            else //if its stopped then start spinning again
                r1.setAlpha(new Alpha(-1,10000));
        }
        if((e.getKeyCode()==KeyEvent.VK_X)){//if key presesd is X do the same thing but for the medium sphere
            long r=r2.getAlpha().getIncreasingAlphaDuration();
            if(r==5000)
                r2.setAlpha(new Alpha(-1,0));
            else
                r2.setAlpha(new Alpha(-1,5000));
        }
        if((e.getKeyCode()==KeyEvent.VK_C)){// if key pressed is c then change the rotation for the smallest sphere
            long r=r3.getAlpha().getIncreasingAlphaDuration();
            if(r==2500)
                r3.setAlpha(new Alpha(-1,0));
            else
                r3.setAlpha(new Alpha(-1,2500));
        }
    }


    /* a function to create and return the scene BranchGroup */
    public static BranchGroup createScene() {
        BranchGroup sceneBG = new BranchGroup();
        TransformGroup sceneTG=new TransformGroup();
        sceneBG.addChild(sceneTG);
        sceneTG.addChild(Road());
        sceneTG.addChild(Trees());
        sceneTG.addChild(SurroundAreaLeft());
        sceneTG.addChild(SurroundAreaRight());
        sceneTG.addChild(LightPoles());
        //sceneBG.addChild(addBackground(CommonsSM.Grey, new BoundingSphere()));	///add Background
        pickTool = new PickTool( sceneBG );                   // allow picking of objs in 'sceneBG'
        pickTool.setMode(PickTool.BOUNDS);

        return sceneBG;
    }


    /* a constructor to set up and run the application */
    public Project(BranchGroup sceneBG) {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        canvas.addMouseListener(this);                        // NOTE: enable mouse clicking
        canvas.addKeyListener(this);

        SimpleUniverse su = new SimpleUniverse(canvas);       // create a SimpleUniverse
        Commons.setEye(new Point3d(0, 6, 10.0));
        Commons.defineViewer(su);                           // set the viewer's location

        addLights(sceneBG, Commons.White,new Point3f(4,3,100));
        addLights(sceneBG, Commons.White,new Point3f(4,3,0));
        addLights(sceneBG, Commons.White,new Point3f(4,3,-50));



        sceneBG.compile();
        su.addBranchGraph(sceneBG);                           // attach the scene to SimpleUniverse

        setLayout(new BorderLayout());
        add("Center", canvas);
        frame.setSize(600, 600);                              // set the size of the JFrame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        frame = new JFrame("Group Project 6M");
        frame.getContentPane().add(new Project(createScene()));
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
            Sphere box = (Sphere) pickResult.getNode(PickResult.PRIMITIVE);
            Appearance app = box.getAppearance();                // originally a PRIMITIVE as a box
            if ((int) box.getUserData() == 0) {               // retrieve 'UserData'
                String s=box.getName();                 // we idnetify the sphere from its name
                if(s=="Sun")    //if the biggest sphere
                    app.setTexture(texturedApp("images/sun.jpg"));
                else if(s=="Earth") // if the medium sphere
                    app.setTexture(texturedApp("images/earth.jpg"));
                else    // if the smallest sphere
                    app.setTexture(texturedApp("images/moon.jpg"));
                box.setUserData(1);                           // set 'UserData' to a new value
            }
            else { //if they have been changed with the image then reset
                app.setTexture(texturedApp("images/MarbleTexture.jpg"));
                box.setUserData(0);                           // reset 'UserData'
            }
            box.setAppearance(app);                           // change box's appearance
        }
    }

    public void mouseEntered(MouseEvent arg0) { }
    public void mouseExited(MouseEvent arg0) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    /* a function to add ambient light and a point light to 'sceneBG' */
    public static void addLights(BranchGroup sceneBG, Color3f clr, Point3f point) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
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
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;

public class BuildingMaker {

    public TransformGroup tg;
    public TransformGroup tg2;
    public TransformGroup BuildingMaker1(){
        tg=new TransformGroup();

        Appearance app=new Appearance();
        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app.setTexture(texturedApp("images/buildingFace2.jpg"));
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

        Appearance app3=new Appearance();
        app3.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app3.setTexture(texturedApp("images/top.jpg"));
        PolygonAttributes pa3=new PolygonAttributes();
        pa3.setCullFace(PolygonAttributes.CULL_NONE);
        app3.setPolygonAttributes(pa3);
        TextureAttributes ta3=new TextureAttributes();
        ta3.setTextureMode(TextureAttributes.REPLACE);
        app3.setTextureAttributes(ta3);
        Vector3d scaling3=new Vector3d(1,1,8);
        Transform3D trans3=new Transform3D();
        trans3.setScale(scaling3);
        ta3.setTextureTransform(trans3);

        QuadArray faceRight=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceRight.setCoordinate(0,new Point3f(4f,0,-4f));
        faceRight.setCoordinate(1,new Point3f(4f,10,-4f));
        faceRight.setCoordinate(2,new Point3f(4f,10,4f));
        faceRight.setCoordinate(3,new Point3f(4f,0,4f));
        float uv0[]={0f,0f};
        float uv1[]={1f,0f};
        float uv2[]={1f,1f};
        float uv3[]={0f,1f};
        faceRight.setTextureCoordinate(0,0,uv0);
        faceRight.setTextureCoordinate(0,1,uv1);
        faceRight.setTextureCoordinate(0,2,uv2);
        faceRight.setTextureCoordinate(0,3,uv3);

        QuadArray faceLeft=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceLeft.setCoordinate(0,new Point3f(-4f,0,-4f));
        faceLeft.setCoordinate(1,new Point3f(-4f,10,-4f));
        faceLeft.setCoordinate(2,new Point3f(-4f,10,4f));
        faceLeft.setCoordinate(3,new Point3f(-4f,0,4f));
        faceLeft.setTextureCoordinate(0,0,uv0);
        faceLeft.setTextureCoordinate(0,1,uv1);
        faceLeft.setTextureCoordinate(0,2,uv2);
        faceLeft.setTextureCoordinate(0,3,uv3);

        Shape3D r=new Shape3D(faceRight,app);
        Shape3D l=new Shape3D(faceLeft,app);

        Appearance app2=new Appearance();
        app2.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app2.setTexture(texturedApp("images/sideFace.jpg"));
        PolygonAttributes pa2=new PolygonAttributes();
        pa2.setCullFace(PolygonAttributes.CULL_NONE);
        app2.setPolygonAttributes(pa);
        TextureAttributes ta2=new TextureAttributes();
        ta2.setTextureMode(TextureAttributes.REPLACE);
        app2.setTextureAttributes(ta);
        Vector3d scaling2=new Vector3d(1,1,8);
        Transform3D trans2=new Transform3D();
        trans2.setScale(scaling2);
        ta2.setTextureTransform(trans2);

        QuadArray faceUp=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceUp.setCoordinate(0,new Point3f(-4f,0,-4f));
        faceUp.setCoordinate(1,new Point3f(-4f,10,-4f));
        faceUp.setCoordinate(2,new Point3f(4f,10,-4f));
        faceUp.setCoordinate(3,new Point3f(4f,0,-4f));
        faceUp.setTextureCoordinate(0,0,uv0);
        faceUp.setTextureCoordinate(0,1,uv1);
        faceUp.setTextureCoordinate(0,2,uv2);
        faceUp.setTextureCoordinate(0,3,uv3);

        QuadArray faceDown=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceDown.setCoordinate(0,new Point3f(-4f,0,4f));
        faceDown.setCoordinate(1,new Point3f(-4f,10,4f));
        faceDown.setCoordinate(2,new Point3f(4f,10,4f));
        faceDown.setCoordinate(3,new Point3f(4f,0,4f));
        faceDown.setTextureCoordinate(0,0,uv0);
        faceDown.setTextureCoordinate(0,1,uv1);
        faceDown.setTextureCoordinate(0,2,uv2);
        faceDown.setTextureCoordinate(0,3,uv3);

        QuadArray top=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        top.setCoordinate(0,new Point3f(-4f,10,4f));
        top.setCoordinate(1,new Point3f(-4f,10,-4f));
        top.setCoordinate(2,new Point3f(4f,10,-4f));
        top.setCoordinate(3,new Point3f(4f,10,4f));
        top.setTextureCoordinate(0,0,uv0);
        top.setTextureCoordinate(0,1,uv1);
        top.setTextureCoordinate(0,2,uv2);
        top.setTextureCoordinate(0,3,uv3);

        Shape3D t=new Shape3D(top,app3);
        Shape3D u=new Shape3D(faceUp,app2);
        Shape3D d=new Shape3D(faceDown,app2);

        tg.addChild(r);
        tg.addChild(l);
        tg.addChild(u);
        tg.addChild(d);
        tg.addChild(t);
        return  tg;
    }

    public TransformGroup BuildingMaker2(){
        tg2=new TransformGroup();

        Appearance app=new Appearance();
        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app.setTexture(texturedApp("images/buildingFace.jpg"));
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

        Appearance app3=new Appearance();
        app3.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app3.setTexture(texturedApp("images/top2.jpg"));
        PolygonAttributes pa3=new PolygonAttributes();
        pa3.setCullFace(PolygonAttributes.CULL_NONE);
        app3.setPolygonAttributes(pa3);
        TextureAttributes ta3=new TextureAttributes();
        ta3.setTextureMode(TextureAttributes.REPLACE);
        app3.setTextureAttributes(ta3);
        Vector3d scaling3=new Vector3d(1,1,8);
        Transform3D trans3=new Transform3D();
        trans3.setScale(scaling3);
        ta3.setTextureTransform(trans3);

        QuadArray faceRight=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceRight.setCoordinate(0,new Point3f(4f,0,-4f));
        faceRight.setCoordinate(1,new Point3f(4f,6,-4f));
        faceRight.setCoordinate(2,new Point3f(4f,6,4f));
        faceRight.setCoordinate(3,new Point3f(4f,0,4f));
        float uv0[]={0f,0f};
        float uv1[]={1f,0f};
        float uv2[]={1f,1f};
        float uv3[]={0f,1f};
        faceRight.setTextureCoordinate(0,0,uv0);
        faceRight.setTextureCoordinate(0,1,uv1);
        faceRight.setTextureCoordinate(0,2,uv2);
        faceRight.setTextureCoordinate(0,3,uv3);

        QuadArray faceLeft=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceLeft.setCoordinate(0,new Point3f(-4f,0,-4f));
        faceLeft.setCoordinate(1,new Point3f(-4f,6,-4f));
        faceLeft.setCoordinate(2,new Point3f(-4f,6,4f));
        faceLeft.setCoordinate(3,new Point3f(-4f,0,4f));
        faceLeft.setTextureCoordinate(0,0,uv0);
        faceLeft.setTextureCoordinate(0,1,uv1);
        faceLeft.setTextureCoordinate(0,2,uv2);
        faceLeft.setTextureCoordinate(0,3,uv3);

        Shape3D r=new Shape3D(faceRight,app);
        Shape3D l=new Shape3D(faceLeft,app);

        Appearance app2=new Appearance();
        app2.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app2.setTexture(texturedApp("images/sideFace.jpg"));
        PolygonAttributes pa2=new PolygonAttributes();
        pa2.setCullFace(PolygonAttributes.CULL_NONE);
        app2.setPolygonAttributes(pa);
        TextureAttributes ta2=new TextureAttributes();
        ta2.setTextureMode(TextureAttributes.REPLACE);
        app2.setTextureAttributes(ta);
        Vector3d scaling2=new Vector3d(1,1,8);
        Transform3D trans2=new Transform3D();
        trans2.setScale(scaling2);
        ta2.setTextureTransform(trans2);

        QuadArray faceUp=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceUp.setCoordinate(0,new Point3f(-4f,0,-4f));
        faceUp.setCoordinate(1,new Point3f(-4f,6,-4f));
        faceUp.setCoordinate(2,new Point3f(4f,6,-4f));
        faceUp.setCoordinate(3,new Point3f(4f,0,-4f));
        faceUp.setTextureCoordinate(0,0,uv0);
        faceUp.setTextureCoordinate(0,1,uv1);
        faceUp.setTextureCoordinate(0,2,uv2);
        faceUp.setTextureCoordinate(0,3,uv3);

        QuadArray faceDown=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        faceDown.setCoordinate(0,new Point3f(-4f,0,4f));
        faceDown.setCoordinate(1,new Point3f(-4f,6,4f));
        faceDown.setCoordinate(2,new Point3f(4f,6,4f));
        faceDown.setCoordinate(3,new Point3f(4f,0,4f));
        faceDown.setTextureCoordinate(0,0,uv0);
        faceDown.setTextureCoordinate(0,1,uv1);
        faceDown.setTextureCoordinate(0,2,uv2);
        faceDown.setTextureCoordinate(0,3,uv3);

        QuadArray top=new QuadArray(4,QuadArray.COORDINATES|QuadArray.COLOR_3|QuadArray.TEXTURE_COORDINATE_2);
        top.setCoordinate(0,new Point3f(-4f,6,4f));
        top.setCoordinate(1,new Point3f(-4f,6,-4f));
        top.setCoordinate(2,new Point3f(4f,6,-4f));
        top.setCoordinate(3,new Point3f(4f,6,4f));
        top.setTextureCoordinate(0,0,uv0);
        top.setTextureCoordinate(0,1,uv1);
        top.setTextureCoordinate(0,2,uv2);
        top.setTextureCoordinate(0,3,uv3);

        Shape3D u=new Shape3D(faceUp,app2);
        Shape3D d=new Shape3D(faceDown,app2);
        Shape3D t=new Shape3D(top,app3);

        tg2.addChild(r);
        tg2.addChild(l);
        tg2.addChild(u);
        tg2.addChild(d);
        tg2.addChild(t);
        return  tg2;
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

}
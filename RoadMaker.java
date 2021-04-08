import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;

public class RoadMaker {

    public  TransformGroup tg;

    public TransformGroup Road(){
        tg=new TransformGroup();


        Appearance app=new Appearance();
        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
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
        Shape3D r=new Shape3D(road,app);
        r.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        r.setName("Road");
        r.setUserData(0);
        tg.addChild(r);

        return tg;
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
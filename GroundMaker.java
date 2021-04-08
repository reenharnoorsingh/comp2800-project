import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;

public class GroundMaker {

    public TransformGroup tg;
    public TransformGroup tg2;

    public TransformGroup SurroundAreaLeft(){
        tg=new TransformGroup();

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

    public TransformGroup SurroundAreaRight(){
        tg2=new TransformGroup();

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
        tg2.addChild(new Shape3D(surround,app));

        return tg2;
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
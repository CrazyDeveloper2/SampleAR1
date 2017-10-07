package prabhat.artk6_test;

import android.opengl.GLES20;

import org.artoolkit.ar6.base.ARToolKit;
import org.artoolkit.ar6.base.NativeInterface;
import org.artoolkit.ar6.base.rendering.ARRenderer;
import org.artoolkit.ar6.base.rendering.shader_impl.Cube;
import org.artoolkit.ar6.base.rendering.shader_impl.SimpleFragmentShader;
import org.artoolkit.ar6.base.rendering.shader_impl.SimpleShaderProgram;
import org.artoolkit.ar6.base.rendering.shader_impl.SimpleVertexShader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Me on 10/7/2017.
 */

class ARTrackingRenderer extends ARRenderer {

    private static final class Trackable
    {
        String name;
        float height;
        Trackable(String name, float height)
        {
            this.name = name;
            this.height = height;
        }
    }

    private  static  final  Trackable trackables[] = new Trackable[]{
            new Trackable("Alterra_Ticket_1", 95.3f),
            new Trackable("Alterra_Postcard_2", 95.3f),
            new Trackable("Alterra_Postcard_3", 127.0f),
            new Trackable("Alterra_Postcard_4", 95.3f),
            new Trackable("patt2", 95.3f)
    };

    private int trackableUIDs[] = new int[trackables.length];
    private Cube cube;

    @Override
    public boolean configureARScene() {

        int i = 0;

        for (Trackable trackable : trackables){


            trackableUIDs[i] = ARToolKit.getInstance().addMarker("2d;Data/2d/" + trackable.name + ".jpg;" + trackable.height + ";0;0");
            if (trackableUIDs[i] < 0) return false;
            i++;
        }

        NativeInterface.arwSetTrackerOptionInt(NativeInterface.ARW_TRACKER_OPTION_2D_MAX_IMAGES, trackables.length);
        return true;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        this.shaderProgram = new SimpleShaderProgram(new SimpleVertexShader(), new SimpleFragmentShader());
        cube = new Cube(40.0f, 0.0f,0.0f, 0.0f);
        cube.setShaderProgram(this.shaderProgram);
        super.onSurfaceCreated(unused, config);
    }

    @Override
    public void draw() {
        super.draw();

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CCW);


        for (int trackableUID : trackableUIDs)
        {

            if (ARToolKit.getInstance().queryMarkerVisible(trackableUID))
            {
                float [] projectionViewMatrix = ARToolKit.getInstance().getProjectionMatrix();
                float [] modelViewMatrix = ARToolKit.getInstance().queryMarkerTransformation(trackableUID);

                cube.draw(projectionViewMatrix, modelViewMatrix);


            }
        }
    }
}

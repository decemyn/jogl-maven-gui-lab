package org.uvt;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL2;


//https://en.wikiversity.org/wiki/Computer_graphics/2013-2014/Laboratory_4

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame implements GLEventListener {

    // Number of textures we want to create
    private final int NO_TEXTURES = 2;

    private int texture[] = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];

    // GLU object used for mipmapping.
    private GLU glu;
    public GLCanvas canvas;


    public MainFrame() {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl() {

        // Obtaining a reference to the default GL profile
        GLProfile glProfile = GLProfile.getDefault();
        // Creating an object to manipulate OpenGL parameters.
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        // Creating an OpenGL display widget -- canvas.
        this.canvas = new GLCanvas();

        // Adding the canvas in the center of the frame.
        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);

    }

    public void init(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        // Create a new GLU object.
        glu = GLU.createGLU();

        // Generate a name (id) for the texture.
        // This is called once in init no matter how many textures we want to generate in the texture vector
        gl.glGenTextures(NO_TEXTURES, texture, 0);

        // Define the filters used when the texture is scaled.
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        // Do not forget to enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);

        // The following lines are for creating ONE texture
        // If you want TWO textures modify NO_TEXTURES=2 and copy-paste again the next lines of code
        // up until (and including) this.makeRGBTexture(...)
        // Modify texture[0] and tex[0] to texture[1] and tex[1] in the new code and that's it

        // Bind (select) the texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Read the texture from the image.
        try {
            tex[0] = TextureReader.readTexture("textura2.jpg");
            // This line reads another image that will be used to replace a part of the previous
            tex[1] = TextureReader.readTexture("textura3.jpg");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        // Construct the texture and use mipmapping in the process.
        this.makeRGBTexture(gl, glu, tex[0], GL.GL_TEXTURE_2D, true);
        gl.glEndList();
    }


    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        // Replace all of our texture with another one.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]); // the pixel data for this texture is given by tex[0] in our example.
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, tex[1].getWidth(), tex[1].getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, tex[1].getPixels());


        // Draw a square and apply a texture on it.
        gl.glBegin(GL2.GL_QUADS);
        // Lower left corner.
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.1f, 0.1f);

        // Lower right corner.
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.9f, 0.1f);

        // Upper right corner.
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.9f, 0.9f);

        // Upper left corner.
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.1f, 0.9f);
        gl.glEnd();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {

    }

    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }

}
package org.uvt;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;


//https://en.wikiversity.org/wiki/Computer_graphics/2013-2014/Laboratory_4

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame implements GLEventListener {

    // Number of textures we want to create

    // GLU object used for mipmapping.
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private static final int SQUARE_SIZE = 50;
    private Texture whiteTexture, blackTexture;
    GLCanvas canvas;


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

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, COLUMNS * SQUARE_SIZE, 0, ROWS * SQUARE_SIZE, -1, 1);

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Create white texture
        BufferedImage whiteImage = new BufferedImage(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D whiteGraphics = whiteImage.createGraphics();
        whiteGraphics.setColor(Color.WHITE);
        whiteGraphics.fillRect(0, 0, SQUARE_SIZE, SQUARE_SIZE);
        whiteGraphics.dispose();
        whiteTexture = AWTTextureIO.newTexture(drawable.getGLProfile(), whiteImage, true);

        // Create black texture
        BufferedImage blackImage = new BufferedImage(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D blackGraphics = blackImage.createGraphics();
        blackGraphics.setColor(Color.BLACK);
        blackGraphics.fillRect(0, 0, SQUARE_SIZE, SQUARE_SIZE);
        blackGraphics.dispose();
        blackTexture = AWTTextureIO.newTexture(drawable.getGLProfile(), blackImage, true);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        whiteTexture.destroy((GL) drawable.getGL().getGLProfile());
        blackTexture.destroy((GL) drawable.getGL().getGLProfile());
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Texture texture = (i + j) % 2 == 0 ? whiteTexture : blackTexture;
                texture.enable(gl);
                texture.bind(gl);
                gl.glBegin(GL2.GL_QUADS);
                gl.glTexCoord2f(0, 0);
                gl.glVertex2f(j * SQUARE_SIZE, i * SQUARE_SIZE);
                gl.glTexCoord2f(1, 0);
                gl.glVertex2f(j * SQUARE_SIZE + SQUARE_SIZE, i * SQUARE_SIZE);
                gl.glTexCoord2f(1, 1);
                gl.glVertex2f(j * SQUARE_SIZE + SQUARE_SIZE, i * SQUARE_SIZE + SQUARE_SIZE);
                gl.glTexCoord2f(0, 1);
                gl.glVertex2f(j * SQUARE_SIZE, i * SQUARE_SIZE + SQUARE_SIZE);
                gl.glEnd();
                texture.disable(gl);
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

}
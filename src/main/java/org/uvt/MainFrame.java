package org.uvt;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;


//https://en.wikiversity.org/wiki/Computer_graphics/2013-2014/Laboratory_4

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame implements GLEventListener {

    private static final int SCREEN_WIDTH = 1500;
    private static final int SCREEN_HEIGHT = 1000;
    private static final int SQUARE_SIZE = 1000;
    private static final float SQUARE_SPEED = 1f;

    private GLCanvas canvas;
    private Texture texture1, texture2;
    private float square1X = 0, square2X = SCREEN_WIDTH - SQUARE_SIZE;
    private float square1Direction = 1, square2Direction = -1;
    private int currentBlendingOption = 1;


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

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, -1, 1);

        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // Create texture 1
        BufferedImage image1 = new BufferedImage(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics1 = image1.createGraphics();
        graphics1.setColor(Color.RED);
        graphics1.fillRect(0, 0, SQUARE_SIZE, SQUARE_SIZE);
        graphics1.dispose();
        texture1 = AWTTextureIO.newTexture(drawable.getGLProfile(), image1, true);

        // Create texture 2
        BufferedImage image2 = new BufferedImage(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2 = image2.createGraphics();
        graphics2.setColor(Color.BLUE);
        graphics2.fillRect(0, 0, SQUARE_SIZE, SQUARE_SIZE);
        graphics2.dispose();
        texture2 = AWTTextureIO.newTexture(drawable.getGLProfile(), image2, true);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Clear screen
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Apply blending
        switch (currentBlendingOption) {
            case 0:
                gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
                break;
            case 1:
                gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
                break;
            case 2:
                gl.glBlendFunc(GL.GL_DST_COLOR, GL.GL_ZERO);
                break;
            case 3:
                gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_DST_COLOR);
                break;
            case 4:
                gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
                break;
            default:
                break;
        }

        // Draw square 1
        texture1.enable(gl);
        texture1.bind(gl);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(square1X, (float) SCREEN_HEIGHT / 2 - (float) SQUARE_SIZE / 2, 0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(0, 0);
        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(0, SQUARE_SIZE);
        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(SQUARE_SIZE, SQUARE_SIZE);
        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(SQUARE_SIZE, 0);
        gl.glEnd();
        texture1.disable(gl);

        // Draw square 2
        texture2.enable(gl);
        texture2.bind(gl);
        gl.glTranslatef(square2X, 0, 0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(0, 0);
        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(0, SQUARE_SIZE);
        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(SQUARE_SIZE, SQUARE_SIZE);
        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(SQUARE_SIZE, 0);
        gl.glEnd();
        texture2.disable(gl);

        // Update square positions
        square1X += square1Direction * SQUARE_SPEED;
        square2X += square2Direction * SQUARE_SPEED;

        // Check if squares have hit the screen edges
        if (square1X < 0) {
            square1Direction = 1;
        } else if (square1X + SQUARE_SIZE > SCREEN_WIDTH) {
            square1Direction = -1;
        }
        if (square2X < 0) {
            square2Direction = 1;
        } else if (square2X + SQUARE_SIZE > SCREEN_WIDTH) {
            square2Direction = -1;
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Do nothing
    }

}
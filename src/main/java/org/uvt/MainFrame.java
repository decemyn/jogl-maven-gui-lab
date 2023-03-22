package org.uvt;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL2;


//https://en.wikiversity.org/wiki/Computer_graphics/2013-2014/Laboratory_3

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements GLEventListener {


    public MainFrame() {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    public Animator animator;

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

        this.animator = new Animator();

        this.animator.add(this.canvas);

        this.animator.start();
    }


    public GLCanvas canvas;

    public void init(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glViewport(0, 0, 640, 480);
    }


    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable canvas) {
        drawCircle(canvas);
    }

    public void drawCircle(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        // Set the color of the circle
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        // Set the number of points to draw the circle
        int numPoints = 360;

        // Draw the circle
        gl.glBegin(GL2.GL_LINE_LOOP);
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            gl.glVertex2d(Math.cos(angle), Math.sin(angle));
        }
        gl.glEnd();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
    }


}
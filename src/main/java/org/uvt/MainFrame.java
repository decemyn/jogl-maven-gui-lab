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

    private GLU glu;
    private float sunX = 0;
    private float sunY = 0;
    private float sunRadius = 0.1f;
    private int houseDisplayList;
    public GLCanvas canvas;

    public void init(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        glu = new GLU();

        houseDisplayList = gl.glGenLists(1);
        gl.glNewList(houseDisplayList, GL2.GL_COMPILE);
        drawHouse(gl);
        gl.glEndList();
    }

    private void drawHouse(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);

        // Roof
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(0.0f, 0.75f);

        // Left wall
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(0.0f, 0.0f);

        // Right wall
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glVertex2f(0.0f, 0.0f);

        // Floor
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glVertex2f(0.0f, 0.0f);

        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        // Draw the sun
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            double angle = Math.toRadians(i);
            gl.glVertex2d(sunX + sunRadius * Math.cos(angle), sunY + sunRadius * Math.sin(angle));
        }
        gl.glEnd();

        // Draw the house
        gl.glCallList(houseDisplayList);

        // Update the sun position
        sunX += 0.01f;
        if (sunX > 1.0f) {
            sunX = -1.0f;
        }
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

}
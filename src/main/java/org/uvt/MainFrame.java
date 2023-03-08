package org.uvt;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements GLEventListener {

    private GLCanvas canvas;
    private Animator animator;

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

        this.animator = new Animator();

        this.animator.add(this.canvas);

        this.animator.start();
    }


    public void init(GLAutoDrawable canvas) {
        return;
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {
        return;
    }

    public void display(GLAutoDrawable canvas)
    {
        // Retrieve a reference to a GL object. We need it because it contains all the useful OGL methods.
        GL2 gl = canvas.getGL().getGL2();

        // Each time the scene is redrawn we clear the color buffers which is perceived by the user as clearing the scene.
        // Clear the color buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Forcing the scene to be rendered.
        gl.glFlush();

    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
        return;
    }

    public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged) {
        return;
    }
}
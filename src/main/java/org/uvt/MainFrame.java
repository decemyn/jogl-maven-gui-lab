package org.uvt;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas; // !! CU AWT
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL2;
import org.javatuples.Triplet;
import org.jetbrains.annotations.*;



//https://en.wikiversity.org/wiki/Computer_graphics/2013-2014/Laboratory_2

import javax.swing.*;
import java.awt.*;

public class MainFrame
        extends JFrame
        implements GLEventListener {

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
    @SuppressWarnings("unused")
    double equation[] = {1f, 1f, 1f, 1f};

    static Triplet<Float, Float, Float> colorRed = Triplet.with(1.0f, 0.0f, 0.0f);
    static Triplet<Float, Float, Float> colorBlue = Triplet.with(0.0f, 1.0f, 0.0f);
    static Triplet<Float, Float, Float> colorGreen = Triplet.with(0.0f, 0.0f, 1.0f);
    static Triplet<Float, Float, Float> colorWhite = Triplet.with(1.0f, 1.0f, 1.0f);

    public void init(GLAutoDrawable canvas) {
        // Obtain the GL instance associated with the canvas.
        GL2 gl = canvas.getGL().getGL2();

        gl.glEnable(GL.GL_LINE_SMOOTH);

        // Activate the GL_BLEND state variable. Means activating blending.
        gl.glEnable(GL.GL_BLEND);

        // Set the blend function. For antialiasing, it is set to GL_SRC_ALPHA for the source
        // and GL_ONE_MINUS_SRC_ALPHA for the destination pixel.
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // Control GL_LINE_SMOOTH_HINT by applying the GL_DONT_CARE behavior.
        // Other behaviours include GL_FASTEST or GL_NICEST.
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
        // Uncomment the following two lines in case of polygon antialiasing
        //gl.glEnable(GL.GL_POLYGON_SMOOTH);
        //glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable canvas) {

        GL2 gl = canvas.getGL().getGL2();

        // Each time the scene is redrawn we clear the color buffers which is perceived by the user as clearing the scene.

        // Set the color buffer to be filled with the color black when cleared.
        // It can be defined in the init function (method) also.
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0);

        // Clear the color buffer.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glPointSize(5.0f); // Set point size to 5 pixels

        gl.glLineWidth(1.5f);

        gl.glColor3f(1.f, 0.f, 0.f);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.9f, 0.9f);
        gl.glEnd();

        gl.glColor3f(0.f, 1.f, 0.f);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.9f, 0.2f);
        gl.glVertex2f(0.2f, 0.9f);
        gl.glEnd();


    }

    @SuppressWarnings("unused")
    private static void square_loop(GL2 gl) {
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.2f, 0.4f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();
    }

    @SuppressWarnings("unused")
    private static void circle(GL2 gl) {
        gl.glBegin(GL2.GL_LINE_LOOP);
        for (int i = 0; i < 360; i++) {
            double angle = Math.PI * i / 180;
            gl.glColor3f(colorRed.getValue0(), colorRed.getValue1(), colorRed.getValue2());
            gl.glVertex2d(0.5 + Math.cos(angle) * 0.2, 0.5 + Math.sin(angle) * 0.2);
        }
        gl.glEnd();
    }

    @SuppressWarnings("unused")
    private static void square_lines_s(GL2 gl) {
        gl.glLineStipple(1, (short) 0x3F07);
        gl.glEnable(GL2.GL_LINE_STIPPLE);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.4f, 0.2f);
        gl.glColor3f(colorRed.getValue0(), colorRed.getValue1(), colorRed.getValue2());

        gl.glVertex2f(0.2f, 0.4f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glColor3f(colorBlue.getValue0(), colorBlue.getValue1(), colorBlue.getValue2());

        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.2f, 0.4f);
        gl.glColor3f(colorGreen.getValue0(), colorGreen.getValue1(), colorGreen.getValue2());

        gl.glVertex2f(0.4f, 0.2f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glColor3f(colorWhite.getValue0(), colorWhite.getValue1(), colorWhite.getValue2());
        gl.glEnd();
    }

    @SuppressWarnings("unused")
    private static void square_lines(GL2 gl) {
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.4f, 0.2f);
        gl.glColor3f(colorRed.getValue0(), colorRed.getValue1(), colorRed.getValue2());

        gl.glVertex2f(0.2f, 0.4f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glColor3f(colorBlue.getValue0(), colorBlue.getValue1(), colorBlue.getValue2());

        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.2f, 0.4f);
        gl.glColor3f(colorGreen.getValue0(), colorGreen.getValue1(), colorGreen.getValue2());

        gl.glVertex2f(0.4f, 0.2f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glColor3f(colorWhite.getValue0(), colorWhite.getValue1(), colorWhite.getValue2());
        gl.glEnd();
    }

    @SuppressWarnings("unused")
    private static void point_square(GL2 gl) {


        gl.glBegin(GL2.GL_POINTS);
        // Set the vertex color to Red.
        gl.glColor3f(colorRed.getValue0(), colorRed.getValue1(), colorRed.getValue2());
        gl.glVertex2f(0.2f, 0.2f);

        // Set the vertex color to Green.
        gl.glColor3f(colorGreen.getValue0(), colorGreen.getValue1(), colorGreen.getValue2());
        gl.glVertex2f(0.4f, 0.2f);

        // Set the vertex color to Blue.
        gl.glColor3f(colorBlue.getValue0(), colorBlue.getValue1(), colorBlue.getValue2());
        gl.glVertex2f(0.2f, 0.4f);

        // Set the vertex color to White.
        gl.glColor3f(colorWhite.getValue0(), colorWhite.getValue1(), colorWhite.getValue2());
        gl.glVertex2f(0.4f, 0.4f);
        gl.glEnd();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();

        // Select the viewport -- the display area -- to be the entire widget.
        gl.glViewport(0, 0, width, height);

        // Determine the width to height ratio of the widget.
        double ratio = (double) width / (double) height;

        // Select the Projection matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();

        // Select the view volume to be x in the range of 0 to 1, y from 0 to 1 and z from -1 to 1.
        // We are careful to keep the aspect ratio and enlarging the width or the height.
        if (ratio < 1)
            gl.glOrtho(0, 1, 0, 1 / ratio, -1, 1);
        else
            gl.glOrtho(0, 1 * ratio, 0, 1, -1, 1);

        // Return to the Model-view matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }

    @SuppressWarnings("unused")
    public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged) {
    }
}
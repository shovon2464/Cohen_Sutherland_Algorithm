
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author sajib
 */
public class CohenSutherland  implements GLEventListener {
    /**
     * Interface to the GLU library.
     */
    private GLU glu;

    /**
     * Take care of initialization here.
     * @param gld
     */
    @Override
    public void init(GLAutoDrawable gld) {
        GL2 gl = gld.getGL().getGL2();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(-250, -150, 250, 150);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-250.0, 250.0, -150.0, 150.0);
    }

    /**
     * Take care of drawing here.
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        /*
         * put your code here
         */
        
        DrawCS(gl,40,40,100,80,50,50,70,70);
        DrawCS(gl,40,40,100,80,70,90,110,40);
        DrawCS(gl,40,40,100,80,10,50,40,10);
        
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //do nothing
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //do nothing
    }
    
    double dx, dy;
    private void DrawCS(GL2 gl, double xmin, double ymin, double xmax, double ymax, double lx1, double ly1, double lx2, double ly2) {
       //write your own code
    	
       gl.glPointSize(1.0f);
       gl.glColor3f(1.0f, 1.0f, 0.0f);//yellow
       gl.glBegin(GL2.GL_LINES);
       gl.glVertex2d(lx1, ly1);
       gl.glVertex2d(lx2, ly2);
       gl.glEnd();
       
       gl.glPointSize(1.0f);
       gl.glColor3d(1, 0, 0);//red
       
       gl.glBegin(GL2.GL_LINE_LOOP);
       gl.glVertex2d(xmin, ymax);
       gl.glVertex2d(xmax, ymax);
       gl.glVertex2d(xmax, ymin);
       gl.glVertex2d(xmin, ymin);
       gl.glEnd();
       
       
       double x1 = lx1;
       double x2 = lx2;
       double y1 = ly1;
       double y2 = ly2;
       
       
       int code1 = makeCode(xmin, ymin, xmax, ymax, lx1, ly1);
       int code2 = makeCode(xmin, ymin, xmax, ymax,lx2,ly2);
       
       
       while(true) {
    	   if(code1==0 && code2==0) {
    		   gl.glBegin(GL2.GL_LINES);
    	       gl.glVertex2d(x1, y1);
    	       gl.glVertex2d(x2, y2);
    	       gl.glEnd();  
    	       break;
    	   } 
    	   else if ((code1 & code2)  > 0 ) {
    		   System.out.println("Completely Rejected");
    		   break;
    	   }
    	   else {
    		   if (code1>0) {
    			    if ((code1 & 1) > 0){   		    		
    		    		y1 = y1 + (y2-y1)*((xmin-x1)/(x2-x1));
    		    		x1 = xmin;
    		    	}
    		    	else if ((code1 & 2)> 0){
    		    		y1 = y1 + (y2-y1)*((xmax-x1)/(x2-x1));
    		    		x1 = xmax;    		    		
    		    	}
    		    	else if ((code1 & 4) > 0){
    		    		x1 = x1 + (x2-x1)*((ymin-y1)/(y2-y1));
    		    		y1 = ymin;
    		    	}
    		    	else if ((code1 & 8) > 0){
    		    		x1 = x1 + (x2-x1)*((ymax-y1)/(y2-y1));
    		    		y1 = ymax;
    		    	}		   
    		   }
    		   else if (code2>0) {    			   
	    			if ((code2 & 1) > 0){	   		    		
	   		    		y2 = y1 + (y2-y1)*((xmin-x1)/(x2-x1));
	   		    		x2 = xmin;
	   		    	}
	   		    	else if ((code2 & 2) > 0){	   		    		
	   		    		y2 = y1 + (y2-y1)*((xmax-x1)/(x2-x1));
	   		    		x2 = xmax;
	   		    	}
	   		    	else if ((code2 & 4) > 0){	   		    		
	   		    		x2 = x1 + (x2-x1)*((ymin-y1)/(y2-y1));
	   		    		y2 = ymin;
	   		    	}
	   		    	else if ((code2 & 8) > 0){	   		    		
	   		    		x2 = x1 + (x2-x1)*((ymax-y1)/(y2-y1));
	   		    		y2 = ymax;
	   		    	}		   
   		      }
    			   
    		}
    		   code1 = makeCode(xmin, ymin, xmax, ymax, x1, y1);
    		   code2 = makeCode(xmin, ymin, xmax, ymax,x2,y2);
    	   }
       }
       
    
    public int makeCode(double xmin, double ymin, double xmax, double ymax, double lx, double ly) {
    	int left = 1;
    	int right = 2;
    	int below = 4;
    	int above = 8;
    	
    	int code=0;
    	
    	if(lx<xmin) {
    		code +=left;
    	}
    	else if(lx>xmax) {
    		code +=right;
    	}
    	if(ly<ymin) {
    		code +=below;
    	}
    	else if(ly>ymax) {
    		code +=above;
    	}
    	
    	return code;
    }
    
    
    
    
    
    public void dispose(GLAutoDrawable arg0) {
        //do nothing
    }
}
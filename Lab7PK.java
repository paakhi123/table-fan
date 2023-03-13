package codesPK280;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.*;

public class Lab7PK extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;

	private static final int OBJ_NUM = 10;
	private static Lab7ObjectsPK[] object3D = new Lab7ObjectsPK[OBJ_NUM];

	/* a public function to build the base labeled with 'str' */
	public static TransformGroup create_Base(String str) {
		Lab7ShapesPK baseShape = new BaseShape2();
		//Lab7ShapesPK button = new Button();
		Transform3D scaler = new Transform3D();
		scaler.setScale(new Vector3d(4d, 2d, 4d));         // set scale for the 4x4 matrix
		TransformGroup baseTG = new TransformGroup(scaler); 
		baseTG.addChild(baseShape.position_Object());

		Button button = new Button();
		Button1 button1 = new Button1();
		ColorString1 clr_str = new ColorString1(str, CommonsPK.Red, 0.06, new Point3f(-str.length() / 4f, -9.4f, 8.2f));
		Transform3D r_axis = new Transform3D();            // default: rotate around Y-axis
		r_axis.rotY(Math.PI);                              
		TransformGroup objRG = new TransformGroup(r_axis); 
		objRG.addChild(clr_str.position_Object());         // move string to baseShape's other side
		objRG.addChild(button.position_Object());
		objRG.addChild(button1.position_Object());
		baseTG.addChild(objRG);

		return baseTG;
	}
	
	/* a function to create the desk fan */
	private static TransformGroup create_Fan() {
		TransformGroup fanTG = new TransformGroup();

		object3D[0] = new StandObjectA1();                  // create "FanStand"
		fanTG = object3D[0].position_Object();             // set 'fan_baseTG' to FanStand's 'objTG'  
		object3D[1] = new SwitchObjectA1();                 // create and attach "Switch" to "Stand"
		object3D[0].add_Child(object3D[1].position_Object());
		object3D[2] = new Shaft1();
		object3D[0].add_Child(object3D[2].position_Object());
		object3D[3] = new Motor1();
		object3D[0].add_Child(object3D[3].position_Object());
		object3D[4] = new Blade1();
		object3D[0].add_Child(object3D[4].position_Object());
		object3D[5] = new Cylinder2();
		object3D[0].add_Child(object3D[5].position_Object());
		object3D[6] = new Guard1();
		object3D[0].add_Child(object3D[6].position_Object());
		
		
		fanTG.addChild(create_Base("PK's Lab7"));          // create and attach "Base" to "FanStand"
		return fanTG;
	}

	/* a function to build the content branch, including the fan and other environmental settings */
	public static BranchGroup create_Scene() {
		BranchGroup sceneBG = new BranchGroup();
		TransformGroup sceneTG = new TransformGroup();	   // make 'sceneTG' continuously rotating
		sceneTG.addChild(CommonsPK.rotate_Behavior(7500, sceneTG));
		sceneTG.addChild(create_Fan());                    // add the fan to the rotating 'sceneTG'

		sceneBG.addChild(sceneTG);                         // keep the following stationary
		sceneBG.addChild(CommonsPK.add_Lights(CommonsPK.White, 1));

		return sceneBG;
	}

	/* NOTE: Keep the constructor for each of the labs and assignments */
	public Lab7PK(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		
		SimpleUniverse su = new SimpleUniverse(canvas);    // create a SimpleUniverse
		CommonsPK.define_Viewer(su, new Point3d(0.25d, 0.25d, 10.0d));   // set the viewer's location
		
		sceneBG.addChild(CommonsPK.key_Navigation(su));               // allow key navigation
		sceneBG.compile();		                           // optimize the BranchGroup
		su.addBranchGraph(sceneBG);                        // attach the scene to SimpleUniverse

		setLayout(new BorderLayout());
		add("Center", canvas);
		frame.setSize(800, 800);                           // set the size of the JFrame
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		frame = new JFrame("PK's Lab7");                   // NOTE: change XY to student's initials
		frame.getContentPane().add(new Lab7PK(create_Scene()));  // start the program
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.DefaultHighlighter;
import java.awt.Color;

public class MyJavaEditor extends JFrame implements ActionListener
{
	JFrame jf;
	JLabel jl,jl1,jl2,jl3;
	JTextField jtf,jtf1;
	JTextField jtfs;
	JFileChooser jfcs;
	static JTextArea jta;
	JTextArea jta1,lines;
	JButton jbcompile,jbrun,jclear,jbclearFind;
	JScrollPane jsp,jsp1;
	Runtime r;
	private String result="";
	private String newPath="";
	JFileChooser jfc;	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem1,menuItem2,menuItem3,menuItem4,menuItem5,menuItem6,menuItem7,menuItem8,menuItem9,menuItem10,exit;
	
	MyJavaEditor()
	{
		jf = new JFrame("My Visual Editor -- OOM Project"); // JFrame Object is Formed
		jf.getContentPane().setBackground(new Color(189,189,205)); //Setting Its Background
		
		newPath = System.getProperty("user.dir"); // Dir In Which Java Source File Is
		
		jfc = new JFileChooser(newPath); //Default Path From Where File is To Be Choosen
		
		menuBar = new JMenuBar(); // Adding Menu Bar


		//Menu DropDown
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		
		menuItem1 = new JMenuItem("Open",KeyEvent.VK_T);
		menuItem1.addActionListener(this);
		menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(menuItem1);

		menuItem2 = new JMenuItem("NewFile");
		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		menuItem2.addActionListener(this);
		menu.add(menuItem2);
		
		menu.addSeparator();

		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		/******/
		

		//Edit DropDown
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_N);
		menuBar.add(menu);
		
		menuItem3 = new JMenuItem("Copy",KeyEvent.VK_T);
		menuItem3.addActionListener(this);
		menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		menu.add(menuItem3);
		
		menuItem4 = new JMenuItem("Cut",KeyEvent.VK_T);
		menuItem4.addActionListener(this);
		menuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menu.add(menuItem4);
		
		menuItem5 = new JMenuItem("Paste",KeyEvent.VK_T);
		menuItem5.addActionListener(this);
		menuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		menu.add(menuItem5);
		
		menuItem6 = new JMenuItem("Find",KeyEvent.VK_T);
		menuItem6.addActionListener(this);
		menuItem6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menu.add(menuItem6);

		menu.addSeparator();
		
		menuItem7 = new JMenuItem("Text Color");
		menuItem7.addActionListener(this);
		menu.add(menuItem7);

		/******/


	    //Tools DropDown
		menu =new JMenu("Tools");
		menu.setMnemonic(KeyEvent.VK_N);
		menuBar.add(menu);
		
		menuItem8 = new JMenuItem("Compile",KeyEvent.VK_T);
		menuItem8.addActionListener(this);
		menuItem8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.ALT_MASK));
		menu.add(menuItem8);
		
		menuItem9 = new JMenuItem("Run",KeyEvent.VK_T);
		menuItem9.addActionListener(this);
		menuItem9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.CTRL_MASK));
		menu.add(menuItem9);
		
		/******/
		

		//About DropDown
		menu = new JMenu("About");
		menu.setMnemonic(KeyEvent.VK_N);
		menuBar.add(menu);
		
		menuItem10 = new JMenuItem("About",KeyEvent.VK_T);
		menuItem10.addActionListener(this);
		menuItem10.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menu.add(menuItem10);

		/****/

		menuBar.add(menu); //Adding Menu Dropdowns To MenuBar
		
		jf.setJMenuBar(menuBar); //Adding JMenuBar To JFrame

        
		//Labels For Text Fields
		
		//Java Class Name Label
		jl=new JLabel("Enter Java Class Name : ");
		jl.setBounds(50,20,140,25);
		
		//Present Directory Label
		jl1=new JLabel("Present Directory : ");
		jl1.setBounds(750,20,130,25);	
	    
		//Output Label
		jl3 = new JLabel("Output : ");
		jl3.setBounds(1200,370,100,25);
		jl3.setFont(new Font("varinda",Font.PLAIN,15));

		//Adding Labels to JFrames
		jf.add(jl);
		jf.add(jl1);
		jf.add(jl3);

		/*****/
	    
		
		//Text Fields Objects

        //Java Class Name 
		jtf = new JTextField(); 
		jtf.setBounds(190,20,230,25);
		jtf.setFont(new Font("varinda",Font.BOLD,15));
		
		//Present Directory
		jtf1=new JTextField();
		jtf1.setBounds(860,20,365,25);
		jtf1.setFont(new Font("varinda",Font.BOLD,15));
		jtf1.setText(newPath);
		jtf1.setEditable(false);
        
		//Adding TF to JFrame
		jf.add(jtf);
		jf.add(jtf1);
		
		/****/

        
		//Text Area  Objects

		//Main Editor
		jta = new JTextArea(50,50);
		jta.setFont(new Font("varinda",Font.BOLD,17));
		jta.setCaretColor(new Color(189,189,205));
		jta.setBackground(new Color(3,4,45));
		jta.setForeground(new Color(189,189,205));
		
		//Row Header Giving Number
		lines = new JTextArea("1");
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
		lines.setFont(new Font("varinda",Font.BOLD,17));
		lines.setForeground(new Color(50,50,55));
		
		jsp = new JScrollPane(); //New Scroller For Text Area
		jsp.setBounds(50,70,1100,680);

//**************************************
        // Background Outline and Showing Line 
		jta.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = jta.getDocument().getLength();
				Element root = jta.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void insertUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
		});
		
		jsp.getViewport().add(jta); // Adding Text Field To Scroll Pane
		jsp.setRowHeaderView(lines); //Adding Lines TF To Row Header
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	
    //**************************************//
        
		//Output Text Area
		jta1 = new JTextArea(50,18);
		jta1.setFont(new Font("monospace",Font.BOLD,15));
		jta1.setEditable(false);
		jta1.setBackground(new Color(189,189,205));
		jta1.setForeground(new Color(0,0,0));
		
		jsp1 = new JScrollPane(jta1); // Adding Scroll To Output Text Area
		jsp1.setBounds(1200,400,300,150);
		jsp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
		//Add To Scroll Panes To JFrame
		jf.add(jsp);
		jf.add(jsp1);

		/*****/

        
		//Buttons	
		
		//Compile Button
		jbcompile = new JButton("Compile");
		jbcompile.setBounds(1280,100,100,40);
		jbcompile.setBackground(new Color(50,50,54));
		
		//Run Button
		jbrun = new JButton("Run");
		jbrun.setBounds(1280,150,100,40);
		jbrun.setBackground(new Color(50,50,54));

        //Clear Button
		jclear = new JButton("Clear");
		jclear.setBounds(1420,560,60,20);
		jclear.setBackground(new Color(50,50,54));
        
		//Clear Highlight Button
		jbclearFind = new JButton("Clear HighLight");
		jbclearFind.setBounds(1160,730,110,20);
		jbclearFind.setBackground(new Color(50,50,54));

		//Adding Buttons To JFrame
		jf.add(jbcompile);
		jf.add(jbrun);
		jf.add(jclear);
		jf.add(jbclearFind);

		//Functions Declaring For Buttons
		jbcompile.addActionListener(this);
		jbrun.addActionListener(this);
		jclear.addActionListener(this);
		jbclearFind.addActionListener(this);
		
		/*****/
		
		r = Runtime.getRuntime(); //Runtime class is a subclass of Object class, can provide access to various information about the environment in which a program is running
		
        //Properties Of JFrames
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(null);
		jf.setSize(550,550);
		jf.setVisible(true);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);	
	}
	
	public void actionPerformed(ActionEvent e){        
        // Menu Functions

		//Open Option
		if(e.getActionCommand().equals("Open")){
			int r = jfc.showOpenDialog(null);
			if(r == JFileChooser.APPROVE_OPTION)
			{
				String filePath = jfc.getSelectedFile().getPath();
				File file = jfc.getSelectedFile();
				String fn = file.getName().replaceFirst("[.][^.]+$", "");
				try {
					FileInputStream fr = new FileInputStream(filePath);
					InputStreamReader isr = new InputStreamReader(fr, "UTF-8");
					BufferedReader reader = new BufferedReader(isr);
					StringBuffer buffer = new StringBuffer();

					String line = null;
					while ((line = reader.readLine()) != null) 
					{
						buffer.append(line+"\n");
					}

					reader.close();
					jtf.setText(fn);
					jta.setText(buffer.toString());
					jf.setTitle(fn + ".java");
				    } 
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
			}
		}		
        
		//New File Option
		if(e.getActionCommand().equals("NewFile")){
			new MyJavaEditor().setVisible(true);
		}
        
        //Exit Option
		if(e.getActionCommand().equals("Exit"))
			System.exit(0);

	    /*****/
		
		if(e.getActionCommand().equals("Copy"))
			jta.copy();
		
		if(e.getActionCommand().equals("Cut"))
			jta.cut();
		
		if(e.getActionCommand().equals("Paste"))
			jta.paste();
		
		 if(e.getActionCommand().equals("Find")){
			 JFrame textframe2 = new JFrame();
				String inputText = JOptionPane.showInputDialog(textframe2,"Enter Text");
				System.out.println(inputText);
				try {

					int idx2 = 0;
					
					String str = jta.getText();
					
					while(idx2 < jta.getText().length()) {
						
						if(str.substring(idx2,idx2+inputText.length()).equals(inputText)) {
							jta.getHighlighter().addHighlight(idx2, idx2+inputText.length(),
									new DefaultHighlighter.DefaultHighlightPainter(new Color(43,128,0)));
							
						}
						
					    idx2++;
					}				
				}
				catch(Exception ex){	
				}
	     }
		
		if(e.getActionCommand().equals("Text Color")){
			System.out.println("Checked");
			setColor();
		}
		
		/*****/


		//Tools Functions

		//Clear Option
		if(e.getActionCommand().equals("Clear")){
			jta1.setText(""); 
			result="";
		}

		//Clear Hightlight
		if(e.getSource() == jbclearFind){
			jta.getHighlighter().removeAllHighlights();  // highlighter.removeAllHighlights();
		}
        
		//Compile Option
		if((e.getSource()==jbcompile)||(e.getActionCommand().equals("Compile"))){

			if(!jtf.getText().equals("")){
				JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(MyJavaEditor.this) != JFileChooser.APPROVE_OPTION)
                    return;
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter out = new FileWriter(file);
                    jta.write(out);
                    out.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                String filepath = file.getPath();
                String filepath2 = filepath.substring(0, filepath.lastIndexOf(File.separator));
                String name = file.getName();


                 String name2 = file.getName().substring(0, file.getName().lastIndexOf("."));
                 String folder = filepath2+"\\";
          
                ProcessBuilder pb=new ProcessBuilder();
                  try {
                   
                    pb = new ProcessBuilder("cmd", "/C", "javac " + "\"" + filepath2 + "\\" + name);
                    
                    pb.directory(new File(filepath2));
                    Process p = pb.start();
                    p.waitFor();
                    int x = p.exitValue();

                    if (x == 0) {
                        jta1.setText("= Compilation Finished");
						jf.setTitle(name);
						jtf.setText(name2);
                    } else {
                        BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                        String out;
                        jta1.setText("");
                        while ((out = r.readLine()) != null){
                            jta1.append(out + System.getProperty("line.separator"));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
			}
			else{
				jta1.setText("Write A Java Program Class");
			}
		}
        
		//Run Option
		if((e.getSource()==jbrun)||(e.getActionCommand().equals("Run"))){
			
			    JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(MyJavaEditor.this) != JFileChooser.APPROVE_OPTION)
                    return;
                File file = fileChooser.getSelectedFile();
                String filepath = file.getPath();
                String filepath2 = filepath.substring(0, filepath.lastIndexOf(File.separator));
                String name = file.getName();


                 String name2 = file.getName().substring(0, file.getName().lastIndexOf("."));
                        Runtime rt = Runtime.getRuntime();
                        try {
                            String username = System.getProperty("user.name");
                            String c = "@echo off\n" + "\n" 
									+ "java" + " " + name2 + "\n" 
									+ "\n"+ "echo. \n"+"echo Process Terminated\n" +
                                    "pause\n" +
                                    "exit";


                            File dir = new File("C:\\Users\\" + username + "\\OneDrive\\Documents\\CodeEditor");
                            dir.mkdir();

                            try {
                                File file2 = new File("C:\\Users\\" + username + "\\OneDrive\\Documents\\CodeEditor" + "\\run.bat");
                                file2.createNewFile();
                                PrintWriter writer = new PrintWriter(file2);
                                writer.println(c);
                                writer.close();


                                Process p2 = Runtime.getRuntime().exec("cmd /c start run.bat", null, new File("C:\\Users\\" + username + "\\OneDrive\\Documents\\CodeEditor"));
                            } catch (Exception ex) {

                            }

                        } catch (Exception ex) {
							JOptionPane.showMessageDialog(MyJavaEditor.this, "Compilation Error", "Error", JOptionPane.ERROR_MESSAGE);
                        }
					}

			/*****/
		
		 //About Function

		 //About Option
		 if(e.getActionCommand().equals("About")){
			 JFrame jfs1;
			 jfs1 = new JFrame("About");
			 JLabel jl6;
			 jl6 = new JLabel("<html> Visual Java Editor - OOM <br><br> Grp Members : <br> Tarun Dabi <br> Piyush Agrawal <br> Ankit Jha <br> Rishabh Singal</html>",SwingConstants.CENTER);
             jl6.setBounds(300,100,200,150);
			 jl6.setFont(new Font("monospace",Font.BOLD,15));
            
			jfs1.add(jl6);
			jfs1.setLayout(null);
			jfs1.pack();
			jfs1.setLocation(350,250);
			jfs1.setSize(800,400);
			jfs1.setVisible(true);
			jfs1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 }

	}
	
	//Set Color Pallette Fuction
	public void setColor(){
		Color bgColor= JColorChooser.showDialog(null,"Advance Color Selection",Color.RED);
    		if (bgColor != null)
      			jta.setForeground(bgColor);
	}

    //Main Function Of Java Editor
	public static void main(String s[])
	{
		try {
            		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        	} catch (Exception exc) 
		{
            		System.err.println("Error loading L&F: " + exc);
        	}
		new MyJavaEditor();
	}
}




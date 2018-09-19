package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;


/*Legend:
 * double slash is notes to remain for organization, double slash arrow is stuff to add, triple slash 
 * is code commented out but possibly to be added back later, //SUR is where Surajit's code will go
 */
public class IntragenicmiRNAExplorer11 implements Runnable{
	static Thread one = new Thread(new IntragenicmiRNAExplorer11());

	public static void main(String[] args){
		//create and start initial thread
		one.start();
		
		
	}
	
	public void run(){
		//allow the UI to look the same on Mac, Windows, Linux
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		//homepage
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startUp();
				}
			}
		);
	}
	
	public static void startUp(){
		final JFrame homePg = new JFrame("IntramiRExploreR");
		homePg.setLayout(new BorderLayout());
		homePg.setLocation(585,200);
		homePg.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		homePg.setSize(new Dimension(750,600));
		homePg.setBackground(Color.CYAN);
		homePg.setResizable(false);
		
		//button panel; should have 3 buttons
		final JPanel homePanel = new JPanel();
		///ImagePanel homePanel = new ImagePanel();
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("C:\\Users\\nyasa\\Documents\\Cox Lab\\Intragenic miR Explorer (OLD)\\flyPic.jpg"));
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		//picLabel.setPreferredSize(new Dimension(750,600));
		homePanel.add(picLabel);
		
		homePanel.setLayout(null);
		homePanel.setPreferredSize(new Dimension(750,600));
		homePanel.setBackground(Color.LIGHT_GRAY);
		
		//buttons
		final JButton miRNA = new JButton("Query Intragenic MicroRNA");
		miRNA.setFont(new Font("Arial", Font.PLAIN, 24));
		miRNA.setBounds(105,50, 550,150);
		miRNA.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == miRNA){
					showmiRinput();
					homePg.setVisible(false);
				}
			}
		});
		homePanel.add(miRNA);
		
		final JButton mRNA = new JButton("Query mRNA");
		mRNA.setFont(new Font("Arial", Font.PLAIN, 24));
		mRNA.setBounds(105,250, 550,150);
		mRNA.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == mRNA){
					mRNAFrame();
					homePg.setVisible(false);
				}
			}
		});
		homePanel.add(mRNA);
		
		final JButton info = new JButton("info");
		info.setFont(new Font("Arial", Font.PLAIN, 18));
		info.setBounds((homePg.getWidth()/2-70),475, 150,75);
		homePanel.add(info);
		
		
		homePg.add(homePanel);
		homePg.pack(); 
		homePg.setVisible(true);
		
	}
	
	public static void mRNAFrame(){
		final JFrame mRNAframe = new JFrame("mRNA");
		mRNAframe.setLayout(new BorderLayout());
		mRNAframe.setLocation(585,200);
		mRNAframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mRNAframe.setPreferredSize(new Dimension(750,600));
		mRNAframe.setBackground(Color.CYAN);
		mRNAframe.setResizable(false);
		
		final JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setPreferredSize(new Dimension(750, 300));
		
		final JPanel onemRNA = new JPanel();
		onemRNA.setPreferredSize(new Dimension(750,145));
		onemRNA.setLayout(new BorderLayout());
		final JLabel selectOne = new JLabel("Single mRNA"); selectOne.setFont(new Font("Arial", Font.PLAIN, 30));
		selectOne.setHorizontalAlignment(SwingConstants.CENTER);
		selectOne.setPreferredSize(new Dimension(325,145));
		onemRNA.add(selectOne, BorderLayout.WEST);
		final JTextField queryOne = new JTextField("Type a single mRNA to search for.");
		queryOne.setPreferredSize(new Dimension(750/2,145));
		queryOne.setEditable(true);
		onemRNA.add(queryOne, BorderLayout.EAST);
		///onemRNA.setBackground(Color.BLUE);
		
		final JPanel multmRNA = new JPanel();
		multmRNA.setPreferredSize(new Dimension(750,145));
		multmRNA.setLayout(new BorderLayout());
		final JLabel selectmult = new JLabel("Multiple mRNAs"); selectmult.setFont(new Font("Arial", Font.PLAIN, 30));
		selectmult.setHorizontalAlignment(SwingConstants.CENTER);
		selectmult.setPreferredSize(new Dimension(325,145));
		multmRNA.add(selectmult, BorderLayout.WEST);
		final JTextField querymult = new JTextField("Type multiple mRNAs, separated by ONLY a semicolon.");
		///querymult.setHorizontalAlignment(SwingConstants.CENTER);
		querymult.setPreferredSize(new Dimension(750/2,multmRNA.getHeight()));
		querymult.setEditable(true);
		multmRNA.add(querymult, BorderLayout.EAST);
		///multmRNA.setBackground(Color.GREEN);
		
		top.add(onemRNA, BorderLayout.NORTH);
		top.add(multmRNA, BorderLayout.SOUTH);
		
		final JPanel bot = new JPanel();
		bot.setLayout(new BorderLayout());
		bot.setPreferredSize(new Dimension(750,250));
		///bot.setBackground(Color.DARK_GRAY);
		
		final JPanel lbl = new JPanel();
		lbl.setPreferredSize(new Dimension(325, 180));
		lbl.setLayout(new BorderLayout());
		final JLabel lbl1 = new JLabel("mRNA input identifier:");
		lbl1.setFont(new Font("Arial", Font.PLAIN, 24));lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.add(lbl1, BorderLayout.CENTER);
		
		final JPanel rad = new JPanel();
		rad.setPreferredSize(new Dimension(375,lbl.getHeight()));
		rad.setLayout(new BorderLayout());
		final JRadioButton symb = new JRadioButton("Gene Symbol"); symb.setFont(new Font("Arial", Font.PLAIN, 18));
		final JRadioButton f = new JRadioButton("FBGN"); f.setFont(new Font("Arial", Font.PLAIN, 18));
		final JRadioButton cg = new JRadioButton("CGID"); cg.setFont(new Font("Arial", Font.PLAIN, 18));
		/*symb.setPreferredSize(new Dimension(325,lbl.getHeight()/3));
		f.setPreferredSize(new Dimension(325,lbl.getHeight()/3));
		cg.setPreferredSize(new Dimension(325,lbl.getHeight()/3));*/
		/*symb.setHorizontalAlignment(SwingConstants.CENTER);
		f.setHorizontalAlignment(SwingConstants.CENTER);
		cg.setHorizontalAlignment(SwingConstants.CENTER);*/
		rad.add(symb, BorderLayout.NORTH);
		rad.add(f, BorderLayout.CENTER);
		rad.add(cg, BorderLayout.SOUTH);
		
		final JPanel goPanel = new JPanel();
		goPanel.setPreferredSize(new Dimension(750,80));
		goPanel.setLayout(new BorderLayout());
		goPanel.setBackground(Color.RED);
		final JButton go = new JButton("SEARCH");
		go.setPreferredSize(new Dimension(180,50));
		go.setFont(new Font("Arial", Font.PLAIN, 22));
		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent t){
				if(t.getSource() == go){
					final String txt1 = queryOne.getText();
					final String txt2 = querymult.getText();
					if((txt1 == "Type a single mRNA to search for.")&&(txt2=="Type multiple mRNAs, separated by ONLY a semicolon.")){
						//ERROR - if nothing is typed into single mRNA and nothing is typed into multiple mRNAs
						
					}
					else if((txt1 != "Type a single mRNA to search for.")&&(txt2=="Type multiple mRNAs, separated by ONLY a semicolon.")){
						//if single but not mult
						System.out.println("One queried");
					}
					else if((txt1 == "Type a single mRNA to search for.")&&(txt2!="Type multiple mRNAs, separated by ONLY a semicolon.")){
						//if not single but yes mult
						System.out.println("Mult queried");
					}
					else if((txt1 != "Type a single mRNA to search for.")&&(txt2!="Type multiple mRNAs, separated by ONLY a semicolon.")){
						//ERROR - something was typed into both
						
					}

				}
			}
		});
		final JButton backB = new JButton("Home");
		backB.setPreferredSize(new Dimension(160,50));
		backB.setFont(new Font("Arial", Font.PLAIN, 18));
		backB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backB){
					startUp();
					mRNAframe.dispose();
				}
			}
		});
		goPanel.add(go,BorderLayout.CENTER);
		goPanel.add(backB,BorderLayout.LINE_START);
		
		bot.add(lbl, BorderLayout.WEST);
		bot.add(rad, BorderLayout.EAST);
		bot.add(goPanel, BorderLayout.SOUTH);
		
		
		mRNAframe.add(top, BorderLayout.NORTH);
		mRNAframe.add(bot, BorderLayout.SOUTH);
		
		mRNAframe.pack();
		mRNAframe.setVisible(true);
	}
	
	public static void showmiRinput(){
	//make input frame
		final JFrame inputFrame  = new JFrame("Intragenic miR Explorer");
		inputFrame.setLayout(new BorderLayout());
		inputFrame.setLocation(585,200);
		inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFrame.setSize(new Dimension(750,600));
		inputFrame.setResizable(false);
	
	//CONTENT PANES FOR USER QUERY	
		//create checkboxes for protein-coding miRs and put them into checkPane/miR panel
			final JPanel checkPane = new JPanel();
			checkPane.setLayout(null);
			checkPane.setPreferredSize(new Dimension(250,1500));
			///checkPane.setBackground(Color.GREEN);
			final JLabel checkLabel = new JLabel("miRNA w/ Protein-Coding Host mRNAs");
			checkLabel.setBounds(5,5,300,15);
			checkLabel.setHorizontalAlignment(SwingConstants.LEFT);
			checkPane.add(checkLabel);

		
		//makes panel of host IDs
			final JPanel hostPane = new JPanel();
			hostPane.setLayout(null);
			hostPane.setPreferredSize(new Dimension(230,3800));
			///hostPane.setBackground(Color.BLUE);
			final JLabel hostLabel = new JLabel("mRNA Host Gene Symbol");
			hostLabel.setHorizontalAlignment(SwingConstants.CENTER);
			hostLabel.setBounds(5,10,200,10);
			hostPane.add(hostLabel);
			//-> method for the display of host gene symbols as well as ItemListener/ActionListener for alternate display option
			
			hSym(hostPane);

			
			//->alternately, show gene symbols
			
		//make boolean array to store state of checks listened for	
			final boolean selected[][] = new boolean[1][155];//all values false by default; first index is target/info, second is which miR it pertains to
		
			//make checkboxes and itemlisteners
			final JCheckBox mir307a = new JCheckBox("miR-307a");mir307a.setBackground(Color.WHITE);  mir307a.setBounds(5, 25, 250, 25);  mir307a.setOpaque(true);/*->add action listener*/mir307a.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir307a);
			final JCheckBox mir307b = new JCheckBox("miR-307b"); mir307b.setBackground(Color.lightGray);  mir307b.setBounds(5, 50, 250, 25);  mir307b.setOpaque(true);/*->add action listener*/mir307b.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir307b);  
			final JCheckBox mir2a1 = new JCheckBox("miR-2a-1");mir2a1.setBackground(Color.WHITE);  mir2a1.setBounds(5, 75, 250, 25);  mir2a1.setOpaque(true);/*->add action listener*/mir2a1.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir2a1);
			final JCheckBox mir2a2 = new JCheckBox("miR-2a-2");mir2a2.setBackground(Color.lightGray);  mir2a2.setBounds(5, 100, 250, 25);  mir2a2.setOpaque(true);/*->add action listener*/mir2a2.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir2a2);
			final JCheckBox mir2b1 = new JCheckBox("miR-2b-1");mir2b1.setBackground(Color.WHITE);  mir2b1.setBounds(5, 125, 250, 25);  mir2b1.setOpaque(true);/*->add action listener*/mir2b1.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir2b1);
			final JCheckBox mir2b2 = new JCheckBox("miR-2b-2");mir2b2.setBackground(Color.lightGray);  mir2b2.setBounds(5, 150, 250, 25);  mir2b2.setOpaque(true);/*->add action listener*/mir2b2.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir2b2);
			final JCheckBox mir7 = new JCheckBox("miR-7");  mir7.setBackground(Color.WHITE);  mir7.setBounds(5, 175, 250, 25);  mir7.setOpaque(true);/*->add action listener*/mir7.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir7);
			final JCheckBox mir11 = new JCheckBox("miR-11");mir11.setBackground(Color.lightGray);  mir11.setBounds(5, 200, 250, 25);  mir11.setOpaque(true);/*->add action listener*/mir11.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir11);  
			final JCheckBox mir12 = new JCheckBox("miR-12");mir12.setBackground(Color.WHITE);  mir12.setBounds(5, 225, 250, 25);  mir12.setOpaque(true);/*->add action listener*/mir12.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir12);  
			final JCheckBox mir13b2 = new JCheckBox("miR-13b-2");  mir13b2.setBackground(Color.lightGray);  mir13b2.setBounds(5, 250, 250, 25);  mir13b2.setOpaque(true);/*->add action listener*/mir13b2.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir13b2);
			final JCheckBox mir274 = new JCheckBox("miR-274");mir274.setBackground(Color.WHITE);  mir274.setBounds(5, 275, 250, 25);  mir274.setOpaque(true);/*->add action listener*/mir274.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir274); 
			final JCheckBox mir92a = new JCheckBox("miR-92a");mir92a.setBackground(Color.lightGray);  mir92a.setBounds(5, 300, 250, 25);  mir92a.setOpaque(true);/*->add action listener*/mir92a.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir92a); 
			final JCheckBox mir278 = new JCheckBox("miR-278");mir278.setBackground(Color.WHITE);  mir278.setBounds(5, 325, 250, 25);  mir278.setOpaque(true);/*->add action listener*/mir278.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir278); 
			final JCheckBox mir33 = new JCheckBox("miR-33");mir33.setBackground(Color.lightGray);  mir33.setBounds(5, 350, 250, 25);  mir33.setOpaque(true);/*->add action listener*/mir33.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir33);  
			final JCheckBox mir2811 = new JCheckBox("miR-281-1");  mir2811.setBackground(Color.WHITE);  mir2811.setBounds(5, 375, 250, 25);  mir2811.setOpaque(true);/*->add action listener*/mir2811.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir2811);
			final JCheckBox mir283 = new JCheckBox("miR-283");mir283.setBackground(Color.lightGray);  mir283.setBounds(5, 400, 250, 25);  mir283.setOpaque(true);/*->add action listener*/mir283.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir283); 
			final JCheckBox mir284 = new JCheckBox("miR-284");mir284.setBackground(Color.WHITE);  mir284.setBounds(5, 425, 250, 25);  mir284.setOpaque(true);/*->add action listener*/mir284.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir284); 
			final JCheckBox mir2812 = new JCheckBox("miR-281-2");  mir2812.setBackground(Color.lightGray);  mir2812.setBounds(5, 450, 250, 25);  mir2812.setOpaque(true);/*->add action listener*/mir2812.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir2812);
			final JCheckBox mir79 = new JCheckBox("miR-79"); mir79.setBackground(Color.WHITE);  mir79.setBounds(5, 475, 250, 25);  mir79.setOpaque(true);/*->add action listener*/mir79.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir79);  
			final JCheckBox mir92b = new JCheckBox("miR-92b");mir92b.setBackground(Color.lightGray);  mir92b.setBounds(5, 500, 250, 25);  mir92b.setOpaque(true);/*->add action listener*/mir92b.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir92b); 
			final JCheckBox mir263b = new JCheckBox("miR-263b");mir263b.setBackground(Color.WHITE);  mir263b.setBounds(5, 525, 250, 25);  mir263b.setOpaque(true);/*->add action listener*/mir263b.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir263b);
			final JCheckBox mir289 = new JCheckBox("miR-289");mir289.setBackground(Color.lightGray);  mir289.setBounds(5, 550, 250, 25);  mir289.setOpaque(true);/*->add action listener*/mir289.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir289); 
			final JCheckBox mir304 = new JCheckBox("miR-304");mir304.setBackground(Color.WHITE);  mir304.setBounds(5, 575, 250, 25);  mir304.setOpaque(true);/*->add action listener*/mir304.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir304); 
			final JCheckBox mir9c = new JCheckBox("miR-9c");mir9c.setBackground(Color.lightGray);  mir9c.setBounds(5, 600, 250, 25);  mir9c.setOpaque(true);/*->add action listener*/mir9c.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir9c);  
			final JCheckBox mir306 = new JCheckBox("miR-306");mir306.setBackground(Color.WHITE);  mir306.setBounds(5, 625, 250, 25);  mir306.setOpaque(true);/*->add action listener*/mir306.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir306); 
			final JCheckBox mir9b = new JCheckBox("miR-9b");mir9b.setBackground(Color.lightGray);  mir9b.setBounds(5, 650, 250, 25);  mir9b.setOpaque(true);/*->add action listener*/mir9b.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir9b);  
			final JCheckBox mir308 = new JCheckBox("miR-308");mir308.setBackground(Color.WHITE);  mir308.setBounds(5, 675, 250, 25);  mir308.setOpaque(true);/*->add action listener*/mir308.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir308); 
			final JCheckBox mir190 = new JCheckBox("miR-190");mir190.setBackground(Color.lightGray);  mir190.setBounds(5, 700, 250, 25);  mir190.setOpaque(true);/*->add action listener*/mir190.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir190); 
			final JCheckBox mir959 = new JCheckBox("miR-959");mir959.setBackground(Color.WHITE);  mir959.setBounds(5, 725, 250, 25);  mir959.setOpaque(true);/*->add action listener*/mir959.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir959); 
			final JCheckBox mir960 = new JCheckBox("miR-960");mir960.setBackground(Color.lightGray);  mir960.setBounds(5, 750, 250, 25);  mir960.setOpaque(true);/*->add action listener*/mir960.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir960); 
			final JCheckBox mir961 = new JCheckBox("miR-961");mir961.setBackground(Color.WHITE);  mir961.setBounds(5, 775, 250, 25);  mir961.setOpaque(true);/*->add action listener*/mir961.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir961); 
			final JCheckBox mir962 = new JCheckBox("miR-962");mir962.setBackground(Color.lightGray);  mir962.setBounds(5, 800, 250, 25);  mir962.setOpaque(true);/*->add action listener*/mir962.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir962); 
			final JCheckBox mir963 = new JCheckBox("miR-963");mir963.setBackground(Color.WHITE);  mir963.setBounds(5, 825, 250, 25);  mir963.setOpaque(true);/*->add action listener*/mir963.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir963); 
			final JCheckBox mir964 = new JCheckBox("miR-964");mir964.setBackground(Color.lightGray);  mir964.setBounds(5, 850, 250, 25);  mir964.setOpaque(true);/*->add action listener*/mir964.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir964); 
			final JCheckBox mir932 = new JCheckBox("miR-932");mir932.setBackground(Color.WHITE);  mir932.setBounds(5, 875, 250, 25);  mir932.setOpaque(true);/*->add action listener*/mir932.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir932); 
			final JCheckBox mir965 = new JCheckBox("miR-965");mir965.setBackground(Color.lightGray);  mir965.setBounds(5, 900, 250, 25);  mir965.setOpaque(true);/*->add action listener*/mir965.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir965); 
			final JCheckBox mir966 = new JCheckBox("miR-966");mir966.setBackground(Color.WHITE);  mir966.setBounds(5, 925, 250, 25);  mir966.setOpaque(true);/*->add action listener*/mir966.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir966); 
			final JCheckBox mir967 = new JCheckBox("miR-967");mir967.setBackground(Color.lightGray);  mir967.setBounds(5, 950, 250, 25);  mir967.setOpaque(true);/*->add action listener*/mir967.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir967); 
			final JCheckBox mir970 = new JCheckBox("miR-970");mir970.setBackground(Color.WHITE);  mir970.setBounds(5, 975, 250, 25);  mir970.setOpaque(true);/*->add action listener*/mir970.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir970); 
			final JCheckBox mir971 = new JCheckBox("miR-971");mir971.setBackground(Color.lightGray);  mir971.setBounds(5, 1000, 250, 25);  mir971.setOpaque(true);/*->add action listener*/mir971.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir971); 
			final JCheckBox mir978 = new JCheckBox("miR-978");mir978.setBackground(Color.WHITE);  mir978.setBounds(5, 1025, 250, 25);  mir978.setOpaque(true);/*->add action listener*/mir978.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir978); 
			final JCheckBox mir979 = new JCheckBox("miR-979");mir979.setBackground(Color.lightGray);  mir979.setBounds(5, 1050, 250, 25);  mir979.setOpaque(true);/*->add action listener*/mir979.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir979); 
			final JCheckBox mir980 = new JCheckBox("miR-980");mir980.setBackground(Color.WHITE);  mir980.setBounds(5, 1075, 250, 25);  mir980.setOpaque(true);/*->add action listener*/mir980.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir980); 
			final JCheckBox mir981 = new JCheckBox("miR-981");mir981.setBackground(Color.lightGray);  mir981.setBounds(5, 1100, 250, 25);  mir981.setOpaque(true);/*->add action listener*/mir981.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir981); 
			final JCheckBox mir9831 = new JCheckBox("miR-983-1");mir9831.setBackground(Color.WHITE);  mir9831.setBounds(5, 1125, 250, 25);  mir9831.setOpaque(true);/*->add action listener*/mir9831.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir9831);
			final JCheckBox mir9832 = new JCheckBox("miR-983-2");mir9832.setBackground(Color.lightGray);  mir9832.setBounds(5, 1150, 250, 25);  mir9832.setOpaque(true);/*->add action listener*/mir9832.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir9832);
			final JCheckBox mir984 = new JCheckBox("miR-984");mir984.setBackground(Color.WHITE);  mir984.setBounds(5, 1175, 250, 25);  mir984.setOpaque(true);/*->add action listener*/mir984.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir984);
			final JCheckBox mir927 = new JCheckBox("miR-927");mir927.setBackground(Color.lightGray);  mir927.setBounds(5, 1200, 250, 25);  mir927.setOpaque(true);/*->add action listener*/mir927.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir927);
			final JCheckBox mir986 = new JCheckBox("miR-986");mir986.setBackground(Color.WHITE);  mir986.setBounds(5, 1225, 250, 25);  mir986.setOpaque(true);/*->add action listener*/mir986.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir986);
			final JCheckBox mir988 = new JCheckBox("miR-988");mir988.setBackground(Color.lightGray);  mir988.setBounds(5, 1250, 250, 25);  mir988.setOpaque(true);/*->add action listener*/mir988.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir988);
			final JCheckBox mir990 = new JCheckBox("miR-990"); mir990.setBackground(Color.WHITE);  mir990.setBounds(5, 1275, 250, 25);  mir990.setOpaque(true);/*->add action listener*/mir990.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir990);
			final JCheckBox mir929 = new JCheckBox("miR-929");mir929.setBackground(Color.lightGray);  mir929.setBounds(5, 1300, 250, 25);  mir929.setOpaque(true);/*->add action listener*/mir929.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir929);
			final JCheckBox mir995 = new JCheckBox("miR-995");mir995.setBackground(Color.WHITE);  mir995.setBounds(5, 1325, 250, 25);  mir995.setOpaque(true);/*->add action listener*/mir995.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir995);
			final JCheckBox mir998 = new JCheckBox("miR-998");mir998.setBackground(Color.lightGray);  mir998.setBounds(5, 1350, 250, 25);  mir998.setOpaque(true);/*->add action listener*/mir998.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir998);
			final JCheckBox mir999 = new JCheckBox("miR-999");mir999.setBackground(Color.WHITE);  mir999.setBounds(5, 1375, 250, 25);  mir999.setOpaque(true);/*->add action listener*/mir999.setHorizontalAlignment(SwingConstants.CENTER);checkPane.add(mir999);
			final JCheckBox mir1001 = new JCheckBox("miR-1001"); mir1001.setBackground(Color.lightGray);  mir1001.setBounds(5, 1400, 250, 25);  mir1001.setOpaque(true);/*->add action listener*/mir1001.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1001);  
			final JCheckBox mir1003 = new JCheckBox("miR-1003"); mir1003.setBackground(Color.WHITE);  mir1003.setBounds(5, 1425, 250, 25);  mir1003.setOpaque(true);/*->add action listener*/mir1003.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1003);  
			final JCheckBox mir1004 = new JCheckBox("miR-1004"); mir1004.setBackground(Color.lightGray);  mir1004.setBounds(5, 1450, 250, 25);  mir1004.setOpaque(true);/*->add action listener*/mir1004.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1004);  
			final JCheckBox mir1005 = new JCheckBox("miR-1005"); mir1005.setBackground(Color.WHITE);  mir1005.setBounds(5, 1475, 250, 25);  mir1005.setOpaque(true);/*->add action listener*/mir1005.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1005);  
			final JCheckBox mir1006 = new JCheckBox("miR-1006"); mir1006.setBackground(Color.lightGray);  mir1006.setBounds(5, 1500, 250, 25);  mir1006.setOpaque(true);/*->add action listener*/mir1006.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1006);  
			final JCheckBox mir1007 = new JCheckBox("miR-1007"); mir1007.setBackground(Color.WHITE);  mir1007.setBounds(5, 1525, 250, 25);  mir1007.setOpaque(true);/*->add action listener*/mir1007.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1007);  
			final JCheckBox mir1008 = new JCheckBox("miR-1008"); mir1008.setBackground(Color.lightGray);  mir1008.setBounds(5, 1550, 250, 25);  mir1008.setOpaque(true);/*->add action listener*/mir1008.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1008);  
			final JCheckBox mir1009 = new JCheckBox("miR-1009"); mir1009.setBackground(Color.WHITE);  mir1009.setBounds(5, 1575, 250, 25);  mir1009.setOpaque(true);/*->add action listener*/mir1009.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1009);  
			final JCheckBox mir1010 = new JCheckBox("miR-1010"); mir1010.setBackground(Color.lightGray);  mir1010.setBounds(5, 1600, 250, 25);  mir1010.setOpaque(true);/*->add action listener*/mir1010.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1010);  
			final JCheckBox mir1011 = new JCheckBox("miR-1011"); mir1011.setBackground(Color.WHITE);  mir1011.setBounds(5, 1625, 250, 25);  mir1011.setOpaque(true);/*->add action listener*/mir1011.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1011);  
			final JCheckBox mir1012 = new JCheckBox("miR-1012"); mir1012.setBackground(Color.lightGray);  mir1012.setBounds(5, 1650, 250, 25);  mir1012.setOpaque(true);/*->add action listener*/mir1012.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1012);  
			final JCheckBox mir1013 = new JCheckBox("miR-1013"); mir1013.setBackground(Color.WHITE);  mir1013.setBounds(5, 1675, 250, 25);  mir1013.setOpaque(true);/*->add action listener*/mir1013.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1013);  
			final JCheckBox mir1014 = new JCheckBox("miR-1014"); mir1014.setBackground(Color.lightGray);  mir1014.setBounds(5, 1700, 250, 25);  mir1014.setOpaque(true);/*->add action listener*/mir1014.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1014);  
			final JCheckBox mir1015 = new JCheckBox("miR-1015"); mir1015.setBackground(Color.WHITE);  mir1015.setBounds(5, 1725, 250, 25);  mir1015.setOpaque(true);/*->add action listener*/mir1015.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1015);  
			final JCheckBox mir1016 = new JCheckBox("miR-1016"); mir1016.setBackground(Color.lightGray);  mir1016.setBounds(5, 1750, 250, 25);  mir1016.setOpaque(true);/*->add action listener*/mir1016.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1016);  
			final JCheckBox mir1017 = new JCheckBox("miR-1017"); mir1017.setBackground(Color.WHITE);  mir1017.setBounds(5, 1775, 250, 25);  mir1017.setOpaque(true);/*->add action listener*/mir1017.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir1017);  
			final JCheckBox mir2279 = new JCheckBox("miR-2279"); mir2279.setBackground(Color.lightGray);  mir2279.setBounds(5, 1800, 250, 25);  mir2279.setOpaque(true);/*->add action listener*/mir2279.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2279);  
			final JCheckBox mir2280 = new JCheckBox("miR-2280"); mir2280.setBackground(Color.WHITE);  mir2280.setBounds(5, 1825, 250, 25);  mir2280.setOpaque(true);/*->add action listener*/mir2280.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2280);  
			final JCheckBox mir2281 = new JCheckBox("miR-2281"); mir2281.setBackground(Color.lightGray);  mir2281.setBounds(5, 1850, 250, 25);  mir2281.setOpaque(true);/*->add action listener*/mir2281.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2281);  
			final JCheckBox mir2282 = new JCheckBox("miR-2282"); mir2282.setBackground(Color.WHITE);  mir2282.setBounds(5, 1875, 250, 25);  mir2282.setOpaque(true);/*->add action listener*/mir2282.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2282);  
			final JCheckBox mir2489 = new JCheckBox("miR-2489"); mir2489.setBackground(Color.lightGray);  mir2489.setBounds(5, 1900, 250, 25);  mir2489.setOpaque(true);/*->add action listener*/mir2489.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2489);  
			final JCheckBox mir2490 = new JCheckBox("miR-2490"); mir2490.setBackground(Color.WHITE);  mir2490.setBounds(5, 1925, 250, 25);  mir2490.setOpaque(true);/*->add action listener*/mir2490.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2490);  
			final JCheckBox mir2492 = new JCheckBox("miR-2492"); mir2492.setBackground(Color.lightGray);  mir2492.setBounds(5, 1950, 250, 25);  mir2492.setOpaque(true);/*->add action listener*/mir2492.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2492);  
			final JCheckBox mir2493 = new JCheckBox("miR-2493"); mir2493.setBackground(Color.WHITE);  mir2493.setBounds(5, 1975, 250, 25);  mir2493.setOpaque(true);/*->add action listener*/mir2493.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2493);  
			final JCheckBox mir2494 = new JCheckBox("miR-2494"); mir2494.setBackground(Color.lightGray);  mir2494.setBounds(5, 2000, 250, 25);  mir2494.setOpaque(true);/*->add action listener*/mir2494.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2494);  
			final JCheckBox mir2495 = new JCheckBox("miR-2495"); mir2495.setBackground(Color.WHITE);  mir2495.setBounds(5, 2025, 250, 25);  mir2495.setOpaque(true);/*->add action listener*/mir2495.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2495);  
			final JCheckBox mir2497 = new JCheckBox("miR-2497"); mir2497.setBackground(Color.lightGray);  mir2497.setBounds(5, 2050, 250, 25);  mir2497.setOpaque(true);/*->add action listener*/mir2497.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2497);  
			final JCheckBox mir2500 = new JCheckBox("miR-2500"); mir2500.setBackground(Color.WHITE);  mir2500.setBounds(5, 2075, 250, 25);  mir2500.setOpaque(true);/*->add action listener*/mir2500.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2500);  
			final JCheckBox mir2501 = new JCheckBox("miR-2501"); mir2501.setBackground(Color.lightGray);  mir2501.setBounds(5, 2100, 250, 25);  mir2501.setOpaque(true);/*->add action listener*/mir2501.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2501);  
			final JCheckBox mir3641 = new JCheckBox("miR-3641"); mir3641.setBackground(Color.WHITE);  mir3641.setBounds(5, 2125, 250, 25);  mir3641.setOpaque(true);/*->add action listener*/mir3641.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir3641);  
			final JCheckBox mir3642 = new JCheckBox("miR-3642"); mir3642.setBackground(Color.lightGray);  mir3642.setBounds(5, 2150, 250, 25);  mir3642.setOpaque(true);/*->add action listener*/mir3642.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir3642);  
			final JCheckBox mir3643 = new JCheckBox("miR-3643"); mir3643.setBackground(Color.WHITE);  mir3643.setBounds(5, 2175, 250, 25);  mir3643.setOpaque(true);/*->add action listener*/mir3643.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir3643);  
			final JCheckBox mir3644 = new JCheckBox("miR-3644"); mir3644.setBackground(Color.lightGray);  mir3644.setBounds(5, 2200, 250, 25);  mir3644.setOpaque(true);/*->add action listener*/mir3644.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir3644);  
			final JCheckBox mir3645 = new JCheckBox("miR-3645"); mir3645.setBackground(Color.WHITE);  mir3645.setBounds(5, 2225, 250, 25);  mir3645.setOpaque(true);/*->add action listener*/mir3645.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir3645);  
			final JCheckBox mir4910 = new JCheckBox("miR-4910"); mir4910.setBackground(Color.lightGray);  mir4910.setBounds(5, 2250, 250, 25);  mir4910.setOpaque(true);/*->add action listener*/mir4910.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4910);  
			final JCheckBox mir4911 = new JCheckBox("miR-4911"); mir4911.setBackground(Color.WHITE);  mir4911.setBounds(5, 2275, 250, 25);  mir4911.setOpaque(true);/*->add action listener*/mir4911.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4911);  
			final JCheckBox mir4912 = new JCheckBox("miR-4912"); mir4912.setBackground(Color.lightGray);  mir4912.setBounds(5, 2300, 250, 25);  mir4912.setOpaque(true);/*->add action listener*/mir4912.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4912);  
			final JCheckBox mir4913 = new JCheckBox("miR-4913"); mir4913.setBackground(Color.WHITE);  mir4913.setBounds(5, 2325, 250, 25);  mir4913.setOpaque(true);/*->add action listener*/mir4913.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4913);  
			final JCheckBox mir4914 = new JCheckBox("miR-4914"); mir4914.setBackground(Color.lightGray);  mir4914.setBounds(5, 2350, 250, 25);  mir4914.setOpaque(true);/*->add action listener*/mir4914.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4914);  
			final JCheckBox mir4915 = new JCheckBox("miR-4915"); mir4915.setBackground(Color.WHITE);  mir4915.setBounds(5, 2375, 250, 25);  mir4915.setOpaque(true);/*->add action listener*/mir4915.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4915);  
			final JCheckBox mir4916 = new JCheckBox("miR-4916"); mir4916.setBackground(Color.lightGray);  mir4916.setBounds(5, 2400, 250, 25);  mir4916.setOpaque(true);/*->add action listener*/mir4916.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4916);  
			final JCheckBox mir2535b = new JCheckBox("miR-2535b");mir2535b.setBackground(Color.WHITE);  mir2535b.setBounds(5, 2425, 250, 25);  mir2535b.setOpaque(true);/*->add action listener*/mir2535b.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir2535b); 
			final JCheckBox mir4917 = new JCheckBox("miR-4917"); mir4917.setBackground(Color.lightGray);  mir4917.setBounds(5, 2450, 250, 25);  mir4917.setOpaque(true);/*->add action listener*/mir4917.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4917);  
			final JCheckBox mir4918 = new JCheckBox("miR-4918"); mir4918.setBackground(Color.WHITE);  mir4918.setBounds(5, 2475, 250, 25);  mir4918.setOpaque(true);/*->add action listener*/mir4918.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4918);  
			final JCheckBox mir4919 = new JCheckBox("miR-4919"); mir4919.setBackground(Color.lightGray);  mir4919.setBounds(5, 2500, 250, 25);  mir4919.setOpaque(true);/*->add action listener*/mir4919.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4919);  
			final JCheckBox mir4908 = new JCheckBox("miR-4908"); mir4908.setBackground(Color.WHITE);  mir4908.setBounds(5, 2525, 250, 25);  mir4908.setOpaque(true);/*->add action listener*/mir4908.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4908);  
			final JCheckBox mir4909 = new JCheckBox("miR-4909"); mir4909.setBackground(Color.lightGray);  mir4909.setBounds(5, 2550, 250, 25);  mir4909.setOpaque(true);/*->add action listener*/mir4909.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4909);  
			final JCheckBox mir4939 = new JCheckBox("miR-4939"); mir4939.setBackground(Color.WHITE);  mir4939.setBounds(5, 2575, 250, 25);  mir4939.setOpaque(true);/*->add action listener*/mir4939.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4939);  
			final JCheckBox mir4940 = new JCheckBox("miR-4940"); mir4940.setBackground(Color.lightGray);  mir4940.setBounds(5, 2600, 250, 25);  mir4940.setOpaque(true);/*->add action listener*/mir4940.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4940);  
			final JCheckBox mir4941 = new JCheckBox("miR-4941"); mir4941.setBackground(Color.WHITE);  mir4941.setBounds(5, 2625, 250, 25);  mir4941.setOpaque(true);/*->add action listener*/mir4941.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4941);  
			final JCheckBox mir4942 = new JCheckBox("miR-4942"); mir4942.setBackground(Color.lightGray);  mir4942.setBounds(5, 2650, 250, 25);  mir4942.setOpaque(true);/*->add action listener*/mir4942.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4942);  
			final JCheckBox mir4943 = new JCheckBox("miR-4943"); mir4943.setBackground(Color.WHITE);  mir4943.setBounds(5, 2675, 250, 25);  mir4943.setOpaque(true);/*->add action listener*/mir4943.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4943);  
			final JCheckBox mir4944 = new JCheckBox("miR-4944"); mir4944.setBackground(Color.lightGray);  mir4944.setBounds(5, 2700, 250, 25);  mir4944.setOpaque(true);/*->add action listener*/mir4944.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4944);  
			final JCheckBox mir4946 = new JCheckBox("miR-4946"); mir4946.setBackground(Color.WHITE);  mir4946.setBounds(5, 2725, 250, 25);  mir4946.setOpaque(true);/*->add action listener*/mir4946.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4946);  
			final JCheckBox mir4947 = new JCheckBox("miR-4947"); mir4947.setBackground(Color.lightGray);  mir4947.setBounds(5, 2750, 250, 25);  mir4947.setOpaque(true);/*->add action listener*/mir4947.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4947);  
			final JCheckBox mir4948 = new JCheckBox("miR-4948"); mir4948.setBackground(Color.WHITE);  mir4948.setBounds(5, 2775, 250, 25);  mir4948.setOpaque(true);/*->add action listener*/mir4948.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4948);  
			final JCheckBox mir4949 = new JCheckBox("miR-4949"); mir4949.setBackground(Color.lightGray);  mir4949.setBounds(5, 2800, 250, 25);  mir4949.setOpaque(true);/*->add action listener*/mir4949.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4949);  
			final JCheckBox mir4952 = new JCheckBox("miR-4952"); mir4952.setBackground(Color.WHITE);  mir4952.setBounds(5, 2825, 250, 25);  mir4952.setOpaque(true);/*->add action listener*/mir4952.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4952);  
			final JCheckBox mir4954 = new JCheckBox("miR-4954"); mir4954.setBackground(Color.lightGray);  mir4954.setBounds(5, 2850, 250, 25);  mir4954.setOpaque(true);/*->add action listener*/mir4954.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4954);  
			final JCheckBox mir4955 = new JCheckBox("miR-4955"); mir4955.setBackground(Color.WHITE);  mir4955.setBounds(5, 2875, 250, 25);  mir4955.setOpaque(true);/*->add action listener*/mir4955.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4955);  
			final JCheckBox mir4956 = new JCheckBox("miR-4956"); mir4956.setBackground(Color.lightGray);  mir4956.setBounds(5, 2900, 250, 25);  mir4956.setOpaque(true);/*->add action listener*/mir4956.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4956);  
			final JCheckBox mir4957 = new JCheckBox("miR-4957"); mir4957.setBackground(Color.WHITE);  mir4957.setBounds(5, 2925, 250, 25);  mir4957.setOpaque(true);/*->add action listener*/mir4957.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4957);  
			final JCheckBox mir4958 = new JCheckBox("miR-4958"); mir4958.setBackground(Color.lightGray);  mir4958.setBounds(5, 2950, 250, 25);  mir4958.setOpaque(true);/*->add action listener*/mir4958.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4958);  
			final JCheckBox mir4959 = new JCheckBox("miR-4959"); mir4959.setBackground(Color.WHITE);  mir4959.setBounds(5, 2975, 250, 25);  mir4959.setOpaque(true);/*->add action listener*/mir4959.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4959);  
			final JCheckBox mir4960 = new JCheckBox("miR-4960"); mir4960.setBackground(Color.lightGray);  mir4960.setBounds(5, 3000, 250, 25);  mir4960.setOpaque(true);/*->add action listener*/mir4960.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4960);  
			final JCheckBox mir4961 = new JCheckBox("miR-4961"); mir4961.setBackground(Color.WHITE);  mir4961.setBounds(5, 3025, 250, 25);  mir4961.setOpaque(true);/*->add action listener*/mir4961.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4961);  
			final JCheckBox mir4962 = new JCheckBox("miR-4962"); mir4962.setBackground(Color.lightGray);  mir4962.setBounds(5, 3050, 250, 25);  mir4962.setOpaque(true);/*->add action listener*/mir4962.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4962);  
			final JCheckBox mir4963 = new JCheckBox("miR-4963"); mir4963.setBackground(Color.WHITE);  mir4963.setBounds(5, 3075, 250, 25);  mir4963.setOpaque(true);/*->add action listener*/mir4963.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4963);  
			final JCheckBox mir4964 = new JCheckBox("miR-4964"); mir4964.setBackground(Color.lightGray);  mir4964.setBounds(5, 3100, 250, 25);  mir4964.setOpaque(true);/*->add action listener*/mir4964.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4964);  
			final JCheckBox mir4965 = new JCheckBox("miR-4965"); mir4965.setBackground(Color.WHITE);  mir4965.setBounds(5, 3125, 250, 25);  mir4965.setOpaque(true);/*->add action listener*/mir4965.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4965);  
			final JCheckBox mir4967 = new JCheckBox("miR-4967"); mir4967.setBackground(Color.lightGray);  mir4967.setBounds(5, 3150, 250, 25);  mir4967.setOpaque(true);/*->add action listener*/mir4967.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4967);  
			final JCheckBox mir4968 = new JCheckBox("miR-4968"); mir4968.setBackground(Color.WHITE);  mir4968.setBounds(5, 3175, 250, 25);  mir4968.setOpaque(true);/*->add action listener*/mir4968.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4968);  
			final JCheckBox mir4969 = new JCheckBox("miR-4969"); mir4969.setBackground(Color.lightGray);  mir4969.setBounds(5, 3200, 250, 25);  mir4969.setOpaque(true);/*->add action listener*/mir4969.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4969);  
			final JCheckBox mir4970 = new JCheckBox("miR-4670"); mir4970.setBackground(Color.WHITE);  mir4970.setBounds(5, 3225, 250, 25);  mir4970.setOpaque(true);/*->add action listener*/mir4970.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4970);  
			final JCheckBox mir4971 = new JCheckBox("miR-4971"); mir4971.setBackground(Color.lightGray);  mir4971.setBounds(5, 3250, 250, 25);  mir4971.setOpaque(true);/*->add action listener*/mir4971.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4971);  
			final JCheckBox mir4972 = new JCheckBox("miR-4972"); mir4972.setBackground(Color.WHITE);  mir4972.setBounds(5, 3275, 250, 25);  mir4972.setOpaque(true);/*->add action listener*/mir4972.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4972);  
			final JCheckBox mir4976 = new JCheckBox("miR-4976"); mir4976.setBackground(Color.lightGray);  mir4976.setBounds(5, 3300, 250, 25);  mir4976.setOpaque(true);/*->add action listener*/mir4976.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4976);  
			final JCheckBox mir4977 = new JCheckBox("miR-4977"); mir4977.setBackground(Color.WHITE);  mir4977.setBounds(5, 3325, 250, 25);  mir4977.setOpaque(true);/*->add action listener*/mir4977.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4977);  
			final JCheckBox mir4978 = new JCheckBox("miR-4978"); mir4978.setBackground(Color.lightGray);  mir4978.setBounds(5, 3350, 250, 25);  mir4978.setOpaque(true);/*->add action listener*/mir4978.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4978);  
			final JCheckBox mir4979 = new JCheckBox("miR-4979"); mir4979.setBackground(Color.WHITE);  mir4979.setBounds(5, 3375, 250, 25);  mir4979.setOpaque(true);/*->add action listener*/mir4979.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4979);  
			final JCheckBox mir4980 = new JCheckBox("miR-4980"); mir4980.setBackground(Color.lightGray);  mir4980.setBounds(5, 3400, 250, 25);  mir4980.setOpaque(true);/*->add action listener*/mir4980.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4980);  
			final JCheckBox mir4982 = new JCheckBox("miR-4982"); mir4982.setBackground(Color.WHITE);  mir4982.setBounds(5, 3425, 250, 25);  mir4982.setOpaque(true);/*->add action listener*/mir4982.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4982);  
			final JCheckBox mir4984 = new JCheckBox("miR-4984"); mir4984.setBackground(Color.lightGray);  mir4984.setBounds(5, 3450, 250, 25);  mir4984.setOpaque(true);/*->add action listener*/mir4984.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4984);  
			final JCheckBox mir4985 = new JCheckBox("miR-4985"); mir4985.setBackground(Color.WHITE);  mir4985.setBounds(5, 3475, 250, 25);  mir4985.setOpaque(true);/*->add action listener*/mir4985.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4985);  
			final JCheckBox mir4986 = new JCheckBox("miR-4986"); mir4986.setBackground(Color.lightGray);  mir4986.setBounds(5, 3500, 250, 25);  mir4986.setOpaque(true);/*->add action listener*/mir4986.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4986);  
			final JCheckBox mir4987 = new JCheckBox("miR-4987"); mir4987.setBackground(Color.WHITE);  mir4987.setBounds(5, 3525, 250, 25);  mir4987.setOpaque(true);/*->add action listener*/mir4987.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir4987);  
			final JCheckBox mir9370 = new JCheckBox("miR-9370"); mir9370.setBackground(Color.lightGray);  mir9370.setBounds(5, 3550, 250, 25);  mir9370.setOpaque(true);/*->add action listener*/mir9370.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9370);  
			final JCheckBox mir9371 = new JCheckBox("miR-9371"); mir9371.setBackground(Color.WHITE);  mir9371.setBounds(5, 3575, 250, 25);  mir9371.setOpaque(true);/*->add action listener*/mir9371.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9371);  
			final JCheckBox mir9372 = new JCheckBox("miR-9372"); mir9372.setBackground(Color.lightGray);  mir9372.setBounds(5, 3600, 250, 25);  mir9372.setOpaque(true);/*->add action listener*/mir9372.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9372);  
			final JCheckBox mir9373 = new JCheckBox("miR-9373"); mir9373.setBackground(Color.WHITE);  mir9373.setBounds(5, 3625, 250, 25);  mir9373.setOpaque(true);/*->add action listener*/mir9373.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9373);  
			final JCheckBox mir9374 = new JCheckBox("miR-9374"); mir9374.setBackground(Color.lightGray);  mir9374.setBounds(5, 3650, 250, 25);  mir9374.setOpaque(true);/*->add action listener*/mir9374.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9374);  
			final JCheckBox mir9375 = new JCheckBox("miR-9375"); mir9375.setBackground(Color.WHITE);  mir9375.setBounds(5, 3675, 250, 25);  mir9375.setOpaque(true);/*->add action listener*/mir9375.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9375);  
			final JCheckBox mir9377 = new JCheckBox("miR-9377"); mir9377.setBackground(Color.lightGray);  mir9377.setBounds(5, 3700, 250, 25);  mir9377.setOpaque(true);/*->add action listener*/mir9377.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9377);  
			final JCheckBox mir9378 = new JCheckBox("miR-9378"); mir9378.setBackground(Color.WHITE);  mir9378.setBounds(5, 3725, 250, 25);  mir9378.setOpaque(true);/*->add action listener*/mir9378.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9378);  
			final JCheckBox mir9379 = new JCheckBox("miR-9379"); mir9379.setBackground(Color.lightGray);  mir9379.setBounds(5, 3750, 250, 25);  mir9379.setOpaque(true);/*->add action listener*/mir9379.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9379);  
			final JCheckBox mir9380 = new JCheckBox("miR-9380"); mir9380.setBackground(Color.WHITE);  mir9380.setBounds(5, 3775, 250, 25);  mir9380.setOpaque(true);/*->add action listener*/mir9380.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9380);  
			final JCheckBox mir9382 = new JCheckBox("miR-9382"); mir9382.setBackground(Color.lightGray);  mir9382.setBounds(5, 3800, 250, 25);  mir9382.setOpaque(true);/*->add action listener*/mir9382.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9382);  
			final JCheckBox mir9383 = new JCheckBox("miR-9383"); mir9383.setBackground(Color.WHITE);  mir9383.setBounds(5, 3825, 250, 25);  mir9383.setOpaque(true);/*->add action listener*/mir9383.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9383);  
			final JCheckBox mir9384 = new JCheckBox("miR-9384"); mir9384.setBackground(Color.lightGray);  mir9384.setBounds(5, 3850, 250, 25);  mir9384.setOpaque(true);/*->add action listener*/mir9384.setHorizontalAlignment(SwingConstants.CENTER); checkPane.add(mir9384);  
			final JCheckBox mir4974 = new JCheckBox("miR-4974"); checkPane.add(mir4974);mir4974.setBackground(Color.WHITE);mir4974.setBounds(5, 3875, 250, 25);mir4974.setOpaque(true);/*->add action listener*/		mir4974	.setHorizontalAlignment(SwingConstants.CENTER);

			
			mir307a.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir307a.isSelected()){selected[0][0]=true;}else{selected[0][0]= false;}}});
			mir307b.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir307b.isSelected()){selected[0][1]=true;}else{selected[0][1]= false;}}});
			mir2a1.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2a1.isSelected()){selected[0][2]=true;}else{selected[0][2]= false;}}});
			mir2a2.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2a2.isSelected()){selected[0][3]=true;}else{selected[0][3]= false;}}});
			mir2b1.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2b1.isSelected()){selected[0][4]=true;}else{selected[0][4]= false;}}});
			mir2b2.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2b2.isSelected()){selected[0][5]=true;}else{selected[0][5]= false;}}});
			mir7.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir7.isSelected()){selected[0][6]=true;}else{selected[0][6]= false;}}});
			mir11.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir11.isSelected()){selected[0][7]=true;}else{selected[0][7]= false;}}});
			mir12.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir12.isSelected()){selected[0][8]=true;}else{selected[0][8]= false;}}});
			mir13b2.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir13b2.isSelected()){selected[0][9]=true;}else{selected[0][9]= false;}}});
			mir274.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir274.isSelected()){selected[0][10]=true;}else{selected[0][10]= false;}}});
			mir92a.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir92a.isSelected()){selected[0][11]=true;}else{selected[0][11]= false;}}});
			mir278.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir278.isSelected()){selected[0][12]=true;}else{selected[0][12]= false;}}});
			mir33.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir33.isSelected()){selected[0][13]=true;}else{selected[0][13]= false;}}});
			mir2811.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2811.isSelected()){selected[0][14]=true;}else{selected[0][14]= false;}}});
			mir283.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir283.isSelected()){selected[0][15]=true;}else{selected[0][15]= false;}}});
			mir284.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir284.isSelected()){selected[0][16]=true;}else{selected[0][16]= false;}}});
			mir2812.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2812.isSelected()){selected[0][17]=true;}else{selected[0][17]= false;}}});
			mir79.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir79.isSelected()){selected[0][18]=true;}else{selected[0][18]= false;}}});
			mir92b.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir92b.isSelected()){selected[0][19]=true;}else{selected[0][19]= false;}}});
			mir263b.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir263b.isSelected()){selected[0][20]=true;}else{selected[0][20]= false;}}});
			mir289.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir289.isSelected()){selected[0][21]=true;}else{selected[0][21]= false;}}});
			mir304.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir304.isSelected()){selected[0][22]=true;}else{selected[0][22]= false;}}});
			mir9c.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9c.isSelected()){selected[0][23]=true;}else{selected[0][23]= false;}}});
			mir306.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir306.isSelected()){selected[0][24]=true;}else{selected[0][24]= false;}}});
			mir9b.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9b.isSelected()){selected[0][25]=true;}else{selected[0][25]= false;}}});
			mir308.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir308.isSelected()){selected[0][26]=true;}else{selected[0][26]= false;}}});
			mir190.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir190.isSelected()){selected[0][27]=true;}else{selected[0][27]= false;}}});
			mir959.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir959.isSelected()){selected[0][28]=true;}else{selected[0][28]= false;}}});
			mir960.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir960.isSelected()){selected[0][29]=true;}else{selected[0][29]= false;}}});
			mir961.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir961.isSelected()){selected[0][30]=true;}else{selected[0][30]= false;}}});
			mir962.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir962.isSelected()){selected[0][31]=true;}else{selected[0][31]= false;}}});
			mir963.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir963.isSelected()){selected[0][32]=true;}else{selected[0][32]= false;}}});
			mir964.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir964.isSelected()){selected[0][33]=true;}else{selected[0][33]= false;}}});
			mir932.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir932.isSelected()){selected[0][34]=true;}else{selected[0][34]= false;}}});
			mir965.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir965.isSelected()){selected[0][35]=true;}else{selected[0][35]= false;}}});
			mir966.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir966.isSelected()){selected[0][36]=true;}else{selected[0][36]= false;}}});
			mir967.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir967.isSelected()){selected[0][37]=true;}else{selected[0][37]= false;}}});
			mir970.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir970.isSelected()){selected[0][38]=true;}else{selected[0][38]= false;}}});
			mir971.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir971.isSelected()){selected[0][39]=true;}else{selected[0][39]= false;}}});
			mir978.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir978.isSelected()){selected[0][40]=true;}else{selected[0][40]= false;}}});
			mir979.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir979.isSelected()){selected[0][41]=true;}else{selected[0][41]= false;}}});
			mir980.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir980.isSelected()){selected[0][42]=true;}else{selected[0][42]= false;}}});
			mir981.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir981.isSelected()){selected[0][43]=true;}else{selected[0][43]= false;}}});
			mir9831.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9831.isSelected()){selected[0][44]=true;}else{selected[0][44]= false;}}});
			mir9832.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9832.isSelected()){selected[0][45]=true;}else{selected[0][45]= false;}}});
			mir984.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir984.isSelected()){selected[0][46]=true;}else{selected[0][46]= false;}}});
			mir927.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir927.isSelected()){selected[0][47]=true;}else{selected[0][47]= false;}}});
			mir986.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir986.isSelected()){selected[0][48]=true;}else{selected[0][48]= false;}}});
			mir988.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir988.isSelected()){selected[0][49]=true;}else{selected[0][49]= false;}}});
			mir990.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir990.isSelected()){selected[0][50]=true;}else{selected[0][50]= false;}}});
			mir929.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir929.isSelected()){selected[0][51]=true;}else{selected[0][51]= false;}}});
			mir995.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir995.isSelected()){selected[0][52]=true;}else{selected[0][52]= false;}}});
			mir998.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir998.isSelected()){selected[0][53]=true;}else{selected[0][53]= false;}}});
			mir999.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir999.isSelected()){selected[0][54]=true;}else{selected[0][54]= false;}}});
			mir1001.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1001.isSelected()){selected[0][55]=true;}else{selected[0][55]= false;}}});
			mir1003.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1003.isSelected()){selected[0][56]=true;}else{selected[0][56]= false;}}});
			mir1004.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1004.isSelected()){selected[0][57]=true;}else{selected[0][57]= false;}}});
			mir1005.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1005.isSelected()){selected[0][58]=true;}else{selected[0][58]= false;}}});
			mir1006.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1006.isSelected()){selected[0][59]=true;}else{selected[0][59]= false;}}});
			mir1007.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1007.isSelected()){selected[0][60]=true;}else{selected[0][60]= false;}}});
			mir1008.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1008.isSelected()){selected[0][61]=true;}else{selected[0][61]= false;}}});
			mir1009.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1009.isSelected()){selected[0][62]=true;}else{selected[0][62]= false;}}});
			mir1010.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1010.isSelected()){selected[0][63]=true;}else{selected[0][63]= false;}}});
			mir1011.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1011.isSelected()){selected[0][64]=true;}else{selected[0][64]= false;}}});
			mir1012.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1012.isSelected()){selected[0][65]=true;}else{selected[0][65]= false;}}});
			mir1013.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1013.isSelected()){selected[0][66]=true;}else{selected[0][66]= false;}}});
			mir1014.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1014.isSelected()){selected[0][67]=true;}else{selected[0][67]= false;}}});
			mir1015.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1015.isSelected()){selected[0][68]=true;}else{selected[0][68]= false;}}});
			mir1016.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1016.isSelected()){selected[0][69]=true;}else{selected[0][69]= false;}}});
			mir1017.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir1017.isSelected()){selected[0][70]=true;}else{selected[0][70]= false;}}});
			mir2279.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2279.isSelected()){selected[0][71]=true;}else{selected[0][71]= false;}}});
			mir2280.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2280.isSelected()){selected[0][72]=true;}else{selected[0][72]= false;}}});
			mir2281.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2281.isSelected()){selected[0][73]=true;}else{selected[0][73]= false;}}});
			mir2282.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2282.isSelected()){selected[0][74]=true;}else{selected[0][74]= false;}}});
			mir2489.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2489.isSelected()){selected[0][75]=true;}else{selected[0][75]= false;}}});
			mir2490.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2490.isSelected()){selected[0][76]=true;}else{selected[0][76]= false;}}});
			mir2492.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2492.isSelected()){selected[0][77]=true;}else{selected[0][77]= false;}}});
			mir2493.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2493.isSelected()){selected[0][78]=true;}else{selected[0][78]= false;}}});
			mir2494.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2494.isSelected()){selected[0][79]=true;}else{selected[0][79]= false;}}});
			mir2495.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2495.isSelected()){selected[0][80]=true;}else{selected[0][80]= false;}}});
			mir2497.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2497.isSelected()){selected[0][81]=true;}else{selected[0][81]= false;}}});
			mir2500.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2500.isSelected()){selected[0][82]=true;}else{selected[0][82]= false;}}});
			mir2501.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2501.isSelected()){selected[0][83]=true;}else{selected[0][83]= false;}}});
			mir3641.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir3641.isSelected()){selected[0][84]=true;}else{selected[0][84]= false;}}});
			mir3642.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir3642.isSelected()){selected[0][85]=true;}else{selected[0][85]= false;}}});
			mir3643.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir3643.isSelected()){selected[0][86]=true;}else{selected[0][86]= false;}}});
			mir3644.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir3644.isSelected()){selected[0][87]=true;}else{selected[0][87]= false;}}});
			mir3645.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir3645.isSelected()){selected[0][88]=true;}else{selected[0][88]= false;}}});
			mir4910.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4910.isSelected()){selected[0][89]=true;}else{selected[0][89]= false;}}});
			mir4911.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4911.isSelected()){selected[0][90]=true;}else{selected[0][90]= false;}}});
			mir4912.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4912.isSelected()){selected[0][91]=true;}else{selected[0][91]= false;}}});
			mir4913.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4913.isSelected()){selected[0][92]=true;}else{selected[0][92]= false;}}});
			mir4914.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4914.isSelected()){selected[0][93]=true;}else{selected[0][93]= false;}}});
			mir4915.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4915.isSelected()){selected[0][94]=true;}else{selected[0][94]= false;}}});
			mir4916.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4916.isSelected()){selected[0][95]=true;}else{selected[0][95]= false;}}});
			mir2535b.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir2535b.isSelected()){selected[0][96]=true;}else{selected[0][96]= false;}}});
			mir4917.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4917.isSelected()){selected[0][97]=true;}else{selected[0][97]= false;}}});
			mir4918.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4918.isSelected()){selected[0][98]=true;}else{selected[0][98]= false;}}});
			mir4919.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4919.isSelected()){selected[0][99]=true;}else{selected[0][99]= false;}}});
			mir4908.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4908.isSelected()){selected[0][100]=true;}else{selected[0][100]= false;}}});
			mir4909.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4909.isSelected()){selected[0][101]=true;}else{selected[0][101]= false;}}});
			mir4939.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4939.isSelected()){selected[0][102]=true;}else{selected[0][102]= false;}}});
			mir4940.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4940.isSelected()){selected[0][103]=true;}else{selected[0][103]= false;}}});
			mir4941.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4941.isSelected()){selected[0][104]=true;}else{selected[0][104]= false;}}});
			mir4942.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4942.isSelected()){selected[0][105]=true;}else{selected[0][105]= false;}}});
			mir4943.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4943.isSelected()){selected[0][106]=true;}else{selected[0][106]= false;}}});
			mir4944.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4944.isSelected()){selected[0][107]=true;}else{selected[0][107]= false;}}});
			mir4946.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4946.isSelected()){selected[0][108]=true;}else{selected[0][108]= false;}}});
			mir4947.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4947.isSelected()){selected[0][109]=true;}else{selected[0][109]= false;}}});
			mir4948.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4948.isSelected()){selected[0][110]=true;}else{selected[0][110]= false;}}});
			mir4949.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4949.isSelected()){selected[0][111]=true;}else{selected[0][111]= false;}}});
			mir4952.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4952.isSelected()){selected[0][112]=true;}else{selected[0][112]= false;}}});
			mir4954.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4954.isSelected()){selected[0][113]=true;}else{selected[0][113]= false;}}});
			mir4955.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4955.isSelected()){selected[0][114]=true;}else{selected[0][114]= false;}}});
			mir4956.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4956.isSelected()){selected[0][115]=true;}else{selected[0][115]= false;}}});
			mir4957.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4957.isSelected()){selected[0][116]=true;}else{selected[0][116]= false;}}});
			mir4958.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4958.isSelected()){selected[0][117]=true;}else{selected[0][117]= false;}}});
			mir4959.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4959.isSelected()){selected[0][118]=true;}else{selected[0][118]= false;}}});
			mir4960.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4960.isSelected()){selected[0][119]=true;}else{selected[0][119]= false;}}});
			mir4961.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4961.isSelected()){selected[0][120]=true;}else{selected[0][120]= false;}}});
			mir4962.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4962.isSelected()){selected[0][121]=true;}else{selected[0][121]= false;}}});
			mir4963.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4963.isSelected()){selected[0][122]=true;}else{selected[0][122]= false;}}});
			mir4964.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4964.isSelected()){selected[0][123]=true;}else{selected[0][123]= false;}}});
			mir4965.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4965.isSelected()){selected[0][124]=true;}else{selected[0][124]= false;}}});
			mir4967.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4967.isSelected()){selected[0][125]=true;}else{selected[0][125]= false;}}});
			mir4968.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4968.isSelected()){selected[0][126]=true;}else{selected[0][126]= false;}}});
			mir4969.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4969.isSelected()){selected[0][127]=true;}else{selected[0][127]= false;}}});
			mir4970.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4970.isSelected()){selected[0][128]=true;}else{selected[0][128]= false;}}});
			mir4971.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4971.isSelected()){selected[0][129]=true;}else{selected[0][129]= false;}}});
			mir4972.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4972.isSelected()){selected[0][130]=true;}else{selected[0][130]= false;}}});
			mir4976.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4976.isSelected()){selected[0][131]=true;}else{selected[0][131]= false;}}});
			mir4977.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4977.isSelected()){selected[0][132]=true;}else{selected[0][132]= false;}}});
			mir4978.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4978.isSelected()){selected[0][133]=true;}else{selected[0][133]= false;}}});
			mir4979.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4979.isSelected()){selected[0][134]=true;}else{selected[0][134]= false;}}});
			mir4980.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4980.isSelected()){selected[0][135]=true;}else{selected[0][135]= false;}}});
			mir4982.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4982.isSelected()){selected[0][136]=true;}else{selected[0][136]= false;}}});
			mir4984.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4984.isSelected()){selected[0][137]=true;}else{selected[0][137]= false;}}});
			mir4985.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4985.isSelected()){selected[0][138]=true;}else{selected[0][138]= false;}}});
			mir4986.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4986.isSelected()){selected[0][139]=true;}else{selected[0][139]= false;}}});
			mir4987.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4987.isSelected()){selected[0][140]=true;}else{selected[0][140]= false;}}});
			mir9370.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9370.isSelected()){selected[0][141]=true;}else{selected[0][141]= false;}}});
			mir9371.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9371.isSelected()){selected[0][142]=true;}else{selected[0][142]= false;}}});
			mir9372.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9372.isSelected()){selected[0][143]=true;}else{selected[0][143]= false;}}});
			mir9373.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9373.isSelected()){selected[0][144]=true;}else{selected[0][144]= false;}}});
			mir9374.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9374.isSelected()){selected[0][145]=true;}else{selected[0][145]= false;}}});
			mir9375.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9375.isSelected()){selected[0][146]=true;}else{selected[0][146]= false;}}});
			mir9377.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9377.isSelected()){selected[0][147]=true;}else{selected[0][147]= false;}}});
			mir9378.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9378.isSelected()){selected[0][148]=true;}else{selected[0][148]= false;}}});
			mir9379.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9379.isSelected()){selected[0][149]=true;}else{selected[0][149]= false;}}});
			mir9380.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9380.isSelected()){selected[0][150]=true;}else{selected[0][150]= false;}}});
			mir9382.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9382.isSelected()){selected[0][151]=true;}else{selected[0][151]= false;}}});
			mir9383.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9383.isSelected()){selected[0][152]=true;}else{selected[0][152]= false;}}});
			mir9384.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir9384.isSelected()){selected[0][153]=true;}else{selected[0][153]= false;}}});
			mir4974.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent c){if(mir4974.isSelected()){selected[0][154]=true;}else{selected[0][154]= false;}}});

		
	//GOPANE TO EXECUTE AND DISPLAY RESULTFRAME 		
		//makes the side panel to commence the code and ask for the info
			final JPanel sidePane = new JPanel();
			sidePane.setLayout(new BorderLayout());
			sidePane.setPreferredSize(new Dimension(250,600));
			///JLabel optionLabel = new JLabel("Options", SwingConstants.CENTER); optionLabel.setFont(new Font("Arial", Font.PLAIN, 18)); sidePane.add(optionLabel,BorderLayout.CENTER); 
			//back button to go back to start screen
				final JButton back = new JButton("Home");
				back.setPreferredSize(new Dimension(150,50));
				back.setBorder(BorderFactory.createLineBorder(Color.darkGray));
				back.setFont(new Font("Arial", Font.PLAIN, 18));
				back.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == back){
						startUp();
						inputFrame.dispose();
					}
				}
			});
				sidePane.add(back, BorderLayout.NORTH);
			
			final boolean[] platNmeth = new boolean[5];//{Affy1, Affy2, Pearson, Distance, Both}- shows what has been selected
			platNmeth[0]=true;platNmeth[2]=true;//sets the Affy1 and Pearson to default
			
			//right
			final JPanel optPane = new JPanel();
			optPane.setLayout(new BorderLayout());
			optPane.setBorder(BorderFactory.createLineBorder(Color.darkGray));
			///optPane.setBackground(Color.GREEN);
			optPane.setPreferredSize(new Dimension(sidePane.getWidth(),(sidePane.getHeight()-50-175)));
			
			final JPanel plat = new JPanel();
			plat.setLayout(null);
			plat.setPreferredSize(new Dimension(110,200));
			///plat.setBackground(Color.cyan);
			final JLabel PfLbl = new JLabel("Platform:",SwingConstants.CENTER); PfLbl.setFont(new Font("Arial",Font.PLAIN, 20));PfLbl.setBounds(30, 0, 150, 30);
			final JRadioButton Affy1 = new JRadioButton("Affymetrix 1: 80 microarray datasets"); 
			final JRadioButton Affy2 = new JRadioButton("Affymetrix 2: 284 microarray datasets");
			Affy1.setBounds(0,35,250,25);
			Affy2.setBounds(0,60,250,25);
			Affy1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == Affy1){
						Affy2.setSelected(false);
						platNmeth[0] =true;
						platNmeth[1]=false;
					}}
			});
			Affy2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == Affy2){
						Affy1.setSelected(false);
						platNmeth[1] =true;
						platNmeth[0]=false;
					}}
			});
			Affy1.setSelected(true);
			
			plat.add(PfLbl);
			plat.add(Affy1,BorderLayout.WEST);plat.add(Affy2,BorderLayout.EAST);
			
			
			final JPanel mth = new JPanel();
			mth.setLayout(null);
			///mth.setBackground(Color.MAGENTA);
			mth.setPreferredSize(new Dimension(110,250));
			final JLabel methodLbl = new JLabel("Method:",SwingConstants.CENTER);methodLbl.setFont(new Font("Arial",Font.PLAIN, 20));methodLbl.setBounds(30,150,150,30);
			final JRadioButton Pears = new JRadioButton("Pearson"); 
			final JRadioButton Dist = new JRadioButton("Distance");
			final JRadioButton Both = new JRadioButton("Both");
			Pears.setBounds(0,175,150,25);
			Dist.setBounds(0,200,150,25);
			Both.setBounds(0,225,150,25);
			
			Pears.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == Pears){
						Dist.setSelected(false);
						Both.setSelected(false);
						platNmeth[2] =true;
						platNmeth[3]=false;
						platNmeth[4]=false;
						//SUR
					}}
			});
			Dist.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == Dist){
						Both.setSelected(false);
						Pears.setSelected(false);
						platNmeth[3] =true;
						platNmeth[2]=false;
						platNmeth[4]=false;
						//SUR
					}}
			});
			Both.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == Both){
						Pears.setSelected(false);
						Dist.setSelected(false);
						platNmeth[4] =true;
						platNmeth[2]=false;
						platNmeth[3]=false;
					}}});
			Pears.setSelected(true);
			mth.add(Both);mth.add(Dist);mth.add(Pears);
			mth.add(methodLbl);
			
			
			optPane.add(plat, BorderLayout.NORTH);
			optPane.add(mth, BorderLayout.SOUTH);
			
			//We now have two arrays: selected that stores what miRs were chosen, and platNmeth which stores the method and platform
			
			//create goButton and wire ActionListener to take info from selected array
			final JButton goButton = new JButton("Search");
			goButton.setPreferredSize(new Dimension(150,175));
			goButton.setBorder(BorderFactory.createLineBorder(Color.darkGray));
			goButton.setFont(new Font("Arial", Font.PLAIN, 24));
			goButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){//if goButton is clicked
					if(e.getSource() == goButton){
						final JFrame resultFrame = new JFrame("Results: For full readout, use export button below.");//creates entire resultFrame
						resultFrame.setLayout(new BorderLayout());
						resultFrame.setSize(new Dimension(950,750));
						final JPanel resultBack = new JPanel();
						resultBack.setLayout(new BorderLayout());//creates the resultBack to be placed into JScrollPane
						
					//RESULTS (from user query; calls R commands to fill out JTable)
						//create int checkCnt to keep track of how much info is needed
							int checkCnt = 0;
							for(int t = 0;t<155;t++){
								if(selected[0][t]==true){//if targets for one miR with index t is queried
									checkCnt++;
								}}
							
					//create JTable 
						    //create column names
							final String[] colNames = {"miRNA", "tar_GS", "tar_FB", "tar_CG", "Score", "Function", "Experiment", "Method"};
							
							//determine threshold
								int threshold=0;
								if(checkCnt==1){threshold=100;}
								if(checkCnt==2){threshold=50;}
								if(checkCnt==3){threshold=33;}
								if(checkCnt==4){threshold=25;}
								if(checkCnt>=5){threshold=20;}
							
								/*create RCaller and wire query to buttons; 
								code handles table filling,
								///code1 handles graphic display*/
							     final RCaller caller = RCaller.create();
							     final RCaller caller1 = RCaller.create();
							     final RCode code = RCode.create();
							     final RCode code1 = RCode.create();
							     code.R_require("IntramiRExploreR");
							     code.R_require("futile.logger");
							     code.R_require("devtools");
							     code.R_require("Runiversal");
							     code1.R_require("IntramiRExploreR");
							     code1.R_require("futile.logger");
							     code1.R_require("devtools");
							     
							     /*code1.R_require("RedeR");
							     code1.addRCode("rdp<-RedPort()");*/
							     
							     
							     //create array of selected miRs to input to R code
							     final String[] chosen = new String[checkCnt];
							     for(int kk=0;kk<checkCnt;kk++){
							    	 chosen[kk] = litmiR(selected)[kk];
							     }
							     code.addStringArray("miR", chosen);
							     code.addInt("thresh", threshold);
							     code1.addStringArray("miR", chosen);
							     code1.addInt("thresh", threshold);
							     String method =new String();
							     
							     if(Pears.isSelected()){
							    	 method="Pearson";
							    	 code.addString("method", method);
							    	 code1.addString("method", method);
	                                }
							     else if(Dist.isSelected()){
							    	 method="Distance";
							    	 code.addString("method", method);
							    	 code1.addString("method", method);
	                                }
							     else{
							    	 method="Both";
							    	 code.addString("method", method);
							    	 code1.addString("method", method);
							     }
							     if(Affy1.isSelected()){
							    	 final String Platform="Affy1";
							    	 code.addString("Platform", Platform);
							    	 code1.addString("Platform", Platform);
							     }
							     else{
							    	 final String Platform="Affy2";
							    	 code.addString("Platform", Platform);
							    	 code1.addString("Platform", Platform);
							     }
							     
							     
							     code.addRCode("yy <-Visualisation(miR,mRNA_type=c('GeneSymbol'),method,thresh,platform=Platform)");
							     ///code1.addRCode("yy1<-Visualisation(miR,mRNA_type=c('GeneSymbol'),method,thresh,platform=Platform,visualisation = 'igraph',layout = 'interactive')"); 
							     ///code1.addRCode("yy1");
							     caller.setRCode(code);
							     ///caller1.setRCode(code1);
							     caller.runAndReturnResult("yy");
							      
							      final String [] aa= caller.getParser().getAsStringArray("miRNA"); 
							      final String [] aa1= caller.getParser().getAsStringArray("Target_GeneSymbol"); 
							      final String [] aa2= caller.getParser().getAsStringArray("Targets_FBID"); 
							      final String [] aa3= caller.getParser().getAsStringArray("Targets_CGID"); 
							      final double [] aa4= caller.getParser().getAsDoubleArray("Score"); 
							      //convert double array to string array
							      final String [] sa4= new String[aa4.length];
							      for(int ss=0;ss<aa4.length;ss++){
						    		  sa4[ss]= Double.toString(aa4[ss]);
							      }
							      final String [] aa5 = caller.getParser().getAsStringArray("GeneFunction");
							      final String [] aa6 = caller.getParser().getAsStringArray("Experiments");

							    
							//create JTable objects
							    final String[][] results = new String[checkCnt*threshold][8];
							    int w = 0;
							    int x = 0;
							    for(int n=0;n<checkCnt;n++){
								    for(int jj=0;jj<threshold;jj++){//first miR
								    	results[jj+w][0]=aa[jj+x*threshold];//the first miR, then the next one after n loops once
								    	results[jj+w][1]=aa1[jj+x*threshold];//tar_GS
								    	results[jj+w][2]=aa2[jj+x*threshold];//tar_FB
								    	results[jj+w][3]=aa3[jj+x*threshold];//tar_CG
								    	results[jj+w][4]= sa4[jj+x*threshold];//Score
								    	results[jj+w][5]=aa5[jj+x*threshold];//Function
								    	results[jj+w][6]=aa6[jj+x*threshold];//Experiment
								    	
								    }
								    w=w+threshold;
								    x++;
							    }
							    ///System.out.println(checkCnt);
	
							//make JTable
								final JTable resultTable = new JTable(results, colNames);	
							
						//create scroll pane to embed results JTable in; allow for vertical scrolling
							final JScrollPane scrollTable = new JScrollPane(resultTable);
							resultTable.setFillsViewportHeight(true);
							scrollTable.setPreferredSize(new Dimension(resultBack.getWidth(),(resultFrame.getHeight()-150)));
							scrollTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
							scrollTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
							scrollTable.getVerticalScrollBar().setUnitIncrement(12);
						
						//create bottom buttonPanel to allow for visualization, exportation, and ontological research
							final JPanel buttonPanel = new JPanel();
							buttonPanel.setPreferredSize(new Dimension(200, 150));
							buttonPanel.setBackground(Color.LIGHT_GRAY);
							buttonPanel.setLayout(null);
							
							
							//create buttons
								final JButton gOnt = new JButton("Gene Ontology");
								gOnt.setFont(new Font("Arial", Font.PLAIN, 18));
								gOnt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								gOnt.setBounds(50,50,250,100);
								buttonPanel.add(gOnt);
								final JButton vis = new JButton("Visualization");
								vis.setFont(new Font("Arial", Font.PLAIN, 18));
								vis.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								vis.setBounds(650,50,250,100);
								buttonPanel.add(vis);
								code1.addRCode("yy1<-Visualisation(miR,mRNA_type=c('GeneSymbol'),method,thresh,platform=Platform,visualisation = 'igraph',layout = 'interactive')");
								code1.addRCode("yy1");
								caller1.setRCode(code1);
								final SwingWorker<RCaller, Void> worker = new SwingWorker<RCaller,Void>(){
									@Override
									protected  RCaller doInBackground() throws Exception {
										javax.swing.SwingUtilities.invokeLater(new Runnable() {
											public void run() {
												caller1.runOnly();
												
											}});
										
										if(SwingUtilities.isEventDispatchThread()==true){
											System.out.println("It's still in the EDT");
										}else{
											System.out.println("It's NOT in the EDT");
										}
										return null;
										}
									
								};
								vis.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent v){
										if(v.getSource() == vis){
											/*xthread thread2 = new xthread(caller1);
											thread2.run();*/
											
											worker.execute();
										}
									}
								});
								buttonPanel.add(vis);
								final JButton exp = new JButton("Export as .txt file");
								exp.setFont(new Font("Arial", Font.PLAIN, 18));
								exp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								exp.setBounds(350, 50, 250, 100);
								buttonPanel.add(exp);
						
							
						resultFrame.setLocation(470,150);//add in the panels and display the resultFrame
						resultFrame.add(buttonPanel, BorderLayout.PAGE_END);
						resultFrame.add(scrollTable, BorderLayout.PAGE_START);
						resultFrame.setVisible(true);
						}}});
			
			sidePane.add(goButton,BorderLayout.SOUTH);//Now the goButton is set to diplay resultFrame with info from our queries and the pasted info from our R file
			sidePane.add(back, BorderLayout.NORTH);
			sidePane.add(optPane, BorderLayout.CENTER);
			
	//make instructions at the top	
		final JPanel instruct =  new JPanel();
		final String instructions = ("Query below via checkbox respective microRNAs or their host mRNA.");
		final JLabel instructTxt = new JLabel(instructions);
		instruct.add(instructTxt);
		
		//option to select all
		final JButton allmiR = new JButton("Select all miRs");
		allmiR.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == allmiR){
						for(int k=0;k<155;k++){
							///selected[k][0] = true;
							mir307a	.setSelected(true);
							mir307b	.setSelected(true);
							mir2a1	.setSelected(true);
							mir2a2	.setSelected(true);
							mir2b1	.setSelected(true);
							mir2b2	.setSelected(true);
							mir7	.setSelected(true);
							mir11	.setSelected(true);
							mir12	.setSelected(true);
							mir13b2	.setSelected(true);
							mir274	.setSelected(true);
							mir92a	.setSelected(true);
							mir278	.setSelected(true);
							mir33	.setSelected(true);
							mir2811	.setSelected(true);
							mir283	.setSelected(true);
							mir284	.setSelected(true);
							mir2812	.setSelected(true);
							mir79	.setSelected(true);
							mir92b	.setSelected(true);
							mir263b	.setSelected(true);
							mir289	.setSelected(true);
							mir304	.setSelected(true);
							mir9c	.setSelected(true);
							mir306	.setSelected(true);
							mir9b	.setSelected(true);
							mir308	.setSelected(true);
							mir190	.setSelected(true);
							mir959	.setSelected(true);
							mir960	.setSelected(true);
							mir961	.setSelected(true);
							mir962	.setSelected(true);
							mir963	.setSelected(true);
							mir964	.setSelected(true);
							mir932	.setSelected(true);
							mir965	.setSelected(true);
							mir966	.setSelected(true);
							mir967	.setSelected(true);
							mir970	.setSelected(true);
							mir971	.setSelected(true);
							mir978	.setSelected(true);
							mir979	.setSelected(true);
							mir980	.setSelected(true);
							mir981	.setSelected(true);
							mir9831	.setSelected(true);
							mir9832	.setSelected(true);
							mir984	.setSelected(true);
							mir927	.setSelected(true);
							mir986	.setSelected(true);
							mir988	.setSelected(true);
							mir990	.setSelected(true);
							mir929	.setSelected(true);
							mir995	.setSelected(true);
							mir998	.setSelected(true);
							mir999	.setSelected(true);
							mir1001	.setSelected(true);
							mir1003	.setSelected(true);
							mir1004	.setSelected(true);
							mir1005	.setSelected(true);
							mir1006	.setSelected(true);
							mir1007	.setSelected(true);
							mir1008	.setSelected(true);
							mir1009	.setSelected(true);
							mir1010	.setSelected(true);
							mir1011	.setSelected(true);
							mir1012	.setSelected(true);
							mir1013	.setSelected(true);
							mir1014	.setSelected(true);
							mir1015	.setSelected(true);
							mir1016	.setSelected(true);
							mir1017	.setSelected(true);
							mir2279	.setSelected(true);
							mir2280	.setSelected(true);
							mir2281	.setSelected(true);
							mir2282	.setSelected(true);
							mir2489	.setSelected(true);
							mir2490	.setSelected(true);
							mir2492	.setSelected(true);
							mir2493	.setSelected(true);
							mir2494	.setSelected(true);
							mir2495	.setSelected(true);
							mir2497	.setSelected(true);
							mir2500	.setSelected(true);
							mir2501	.setSelected(true);
							mir3641	.setSelected(true);
							mir3642	.setSelected(true);
							mir3643	.setSelected(true);
							mir3644	.setSelected(true);
							mir3645	.setSelected(true);
							mir4910	.setSelected(true);
							mir4911	.setSelected(true);
							mir4912	.setSelected(true);
							mir4913	.setSelected(true);
							mir4914	.setSelected(true);
							mir4915	.setSelected(true);
							mir4916	.setSelected(true);
							mir2535b.setSelected(true);
							mir4917	.setSelected(true);
							mir4918	.setSelected(true);
							mir4919	.setSelected(true);
							mir4908	.setSelected(true);
							mir4909	.setSelected(true);
							mir4939	.setSelected(true);
							mir4940	.setSelected(true);
							mir4941	.setSelected(true);
							mir4942	.setSelected(true);
							mir4943	.setSelected(true);
							mir4944	.setSelected(true);
							mir4946	.setSelected(true);
							mir4947	.setSelected(true);
							mir4948	.setSelected(true);
							mir4949	.setSelected(true);
							mir4952	.setSelected(true);
							mir4954	.setSelected(true);
							mir4955	.setSelected(true);
							mir4956	.setSelected(true);
							mir4957	.setSelected(true);
							mir4958	.setSelected(true);
							mir4959	.setSelected(true);
							mir4960	.setSelected(true);
							mir4961	.setSelected(true);
							mir4962	.setSelected(true);
							mir4963	.setSelected(true);
							mir4964	.setSelected(true);
							mir4965	.setSelected(true);
							mir4967	.setSelected(true);
							mir4968	.setSelected(true);
							mir4969	.setSelected(true);
							mir4970	.setSelected(true);
							mir4971	.setSelected(true);
							mir4972	.setSelected(true);
							mir4976	.setSelected(true);
							mir4977	.setSelected(true);
							mir4978	.setSelected(true);
							mir4979	.setSelected(true);
							mir4980	.setSelected(true);
							mir4982	.setSelected(true);
							mir4984	.setSelected(true);
							mir4985	.setSelected(true);
							mir4986	.setSelected(true);
							mir4987	.setSelected(true);
							mir9370	.setSelected(true);
							mir9371	.setSelected(true);
							mir9372	.setSelected(true);
							mir9373	.setSelected(true);
							mir9374	.setSelected(true);
							mir9375	.setSelected(true);
							mir9377	.setSelected(true);
							mir9378	.setSelected(true);
							mir9379	.setSelected(true);
							mir9380	.setSelected(true);
							mir9382	.setSelected(true);
							mir9383	.setSelected(true);
							mir9384	.setSelected(true);
							mir4974	.setSelected(true);}}}});
			instruct.add(allmiR);
			
		//->option to switch to CG IDs
			
	
	//panel where all query options will be embedded
		final JPanel queryBack = new JPanel();
		queryBack.setLayout(new BorderLayout());
		queryBack.setPreferredSize(new Dimension(480,3900));
		queryBack.add(checkPane, BorderLayout.LINE_START);
		queryBack.add(hostPane,BorderLayout.LINE_END);
		
		
	//scroll panel to embed query panel so that it scrolls
		final JScrollPane scrollPane = new JScrollPane(queryBack);
		scrollPane.setPreferredSize(new Dimension(480,(inputFrame.getHeight()-150)));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(12);
		
				
	inputFrame.add(instruct,BorderLayout.NORTH);	
	inputFrame.add(sidePane,BorderLayout.EAST);
	inputFrame.add(scrollPane,BorderLayout.WEST);		
	inputFrame.setVisible(true);
	
	
	}
	
	
	public static String forShow(int ind, boolean[][] selected){//static method which accepts index and selected array and returns String detailing what type of info is to be added in; Strings are to be later replaced with ouptut from R commands
		String result = "";
		if(selected[0][ind]==true){//if a target has been selected
			result = "Here is where we'd put info on "+ getmiR(ind)+"\'s target information.";
		}
		else{//a miR has been selected
			result =  "Here is where we'd put info on "+ getmiR(ind)+"\'s microRNA information.";
		}
		return result;
	}
	
	public static String getmiR(int index){//accepts index and returns the miR it pertains to
		final String[] mirNames = new String[155];
		mirNames[0] = "dme-miR-307a";
		mirNames[1] = "dme-miR-307b";
		mirNames[2] = "dme-miR-2a-1";
		mirNames[3] = "dme-miR-2a-2";
		mirNames[4] = "dme-miR-2b-1";
		mirNames[5] = "dme-miR-2b-2";
		mirNames[6] = "dme-miR-7";
		mirNames[7] = "dme-miR-11";
		mirNames[8] = "dme-miR-12";
		mirNames[9] = "dme-miR-13b-2";
		mirNames[10] = "dme-miR-274";
		mirNames[11] = "dme-miR-92a";
		mirNames[12] = "dme-miR-278";
		mirNames[13] = "dme-miR-33";
		mirNames[14] = "dme-miR-281-1";
		mirNames[15] = "dme-miR-283";
		mirNames[16] = "dme-miR-284";
		mirNames[17] = "dme-miR-281-2";
		mirNames[18] = "dme-miR-79";
		mirNames[19] = "dme-miR-92b";
		mirNames[20] = "dme-miR-263b";
		mirNames[21] = "dme-miR-289";
		mirNames[22] = "dme-miR-304";
		mirNames[23] = "dme-miR-9c";
		mirNames[24] = "dme-miR-306";
		mirNames[25] = "dme-miR-9b";
		mirNames[26] = "dme-miR-308";
		mirNames[27] = "dme-miR-190";
		mirNames[28] = "dme-miR-959";
		mirNames[29] = "dme-miR-960";
		mirNames[30] = "dme-miR-961";
		mirNames[31] = "dme-miR-962";
		mirNames[32] = "dme-miR-963";
		mirNames[33] = "dme-miR-964";
		mirNames[34] = "dme-miR-932";
		mirNames[35] = "dme-miR-965";
		mirNames[36] = "dme-miR-966";
		mirNames[37] = "dme-miR-967";
		mirNames[38] = "dme-miR-970";
		mirNames[39] = "dme-miR-971";
		mirNames[40] = "dme-miR-978";
		mirNames[41] = "dme-miR-979";
		mirNames[42] = "dme-miR-980";
		mirNames[43] = "dme-miR-981";
		mirNames[44] = "dme-miR-983-1";
		mirNames[45] = "dme-miR-983-2";
		mirNames[46] = "dme-miR-984";
		mirNames[47] = "dme-miR-927";
		mirNames[48] = "dme-miR-986";
		mirNames[49] = "dme-miR-988";
		mirNames[50] = "dme-miR-990";
		mirNames[51] = "dme-miR-929";
		mirNames[52] = "dme-miR-995";
		mirNames[53] = "dme-miR-998";
		mirNames[54] = "dme-miR-999";
		mirNames[55] = "dme-miR-1001";
		mirNames[56] = "dme-miR-1003";
		mirNames[57] = "dme-miR-1004";
		mirNames[58] = "dme-miR-1005";
		mirNames[59] = "dme-miR-1006";
		mirNames[60] = "dme-miR-1007";
		mirNames[61] = "dme-miR-1008";
		mirNames[62] = "dme-miR-1009";
		mirNames[63] = "dme-miR-1010";
		mirNames[64] = "dme-miR-1011";
		mirNames[65] = "dme-miR-1012";
		mirNames[66] = "dme-miR-1013";
		mirNames[67] = "dme-miR-1014";
		mirNames[68] = "dme-miR-1015";
		mirNames[69] = "dme-miR-1016";
		mirNames[70] = "dme-miR-1017";
		mirNames[71] = "dme-miR-2279";
		mirNames[72] = "dme-miR-2280";
		mirNames[73] = "dme-miR-2281";
		mirNames[74] = "dme-miR-2282";
		mirNames[75] = "dme-miR-2489";
		mirNames[76] = "dme-miR-2490";
		mirNames[77] = "dme-miR-2492";
		mirNames[78] = "dme-miR-2493";
		mirNames[79] = "dme-miR-2494";
		mirNames[80] = "dme-miR-2495";
		mirNames[81] = "dme-miR-2497";
		mirNames[82] = "dme-miR-2500";
		mirNames[83] = "dme-miR-2501";
		mirNames[84] = "dme-miR-3641";
		mirNames[85] = "dme-miR-3642";
		mirNames[86] = "dme-miR-3643";
		mirNames[87] = "dme-miR-3644";
		mirNames[88] = "dme-miR-3645";
		mirNames[89] = "dme-miR-4910";
		mirNames[90] = "dme-miR-4911";
		mirNames[91] = "dme-miR-4912";
		mirNames[92] = "dme-miR-4913";
		mirNames[93] = "dme-miR-4914";
		mirNames[94] = "dme-miR-4915";
		mirNames[95] = "dme-miR-4916";
		mirNames[96] = "dme-miR-2535b";
		mirNames[97] = "dme-miR-4917";
		mirNames[98] = "dme-miR-4918";
		mirNames[99] = "dme-miR-4919";
		mirNames[100] = "dme-miR-4908";
		mirNames[101] = "dme-miR-4909";
		mirNames[102] = "dme-miR-4939";
		mirNames[103] = "dme-miR-4940";
		mirNames[104] = "dme-miR-4941";
		mirNames[105] = "dme-miR-4942";
		mirNames[106] = "dme-miR-4943";
		mirNames[107] = "dme-miR-4944";
		mirNames[108] = "dme-miR-4946";
		mirNames[109] = "dme-miR-4947";
		mirNames[110] = "dme-miR-4948";
		mirNames[111] = "dme-miR-4949";
		mirNames[112] = "dme-miR-4952";
		mirNames[113] = "dme-miR-4954";
		mirNames[114] = "dme-miR-4955";
		mirNames[115] = "dme-miR-4956";
		mirNames[116] = "dme-miR-4957";
		mirNames[117] = "dme-miR-4958";
		mirNames[118] = "dme-miR-4959";
		mirNames[119] = "dme-miR-4960";
		mirNames[120] = "dme-miR-4961";
		mirNames[121] = "dme-miR-4962";
		mirNames[122] = "dme-miR-4963";
		mirNames[123] = "dme-miR-4964";
		mirNames[124] = "dme-miR-4965";
		mirNames[125] = "dme-miR-4967";
		mirNames[126] = "dme-miR-4968";
		mirNames[127] = "dme-miR-4969";
		mirNames[128] = "dme-miR-4670";
		mirNames[129] = "dme-miR-4971";
		mirNames[130] = "dme-miR-4972";
		mirNames[131] = "dme-miR-4976";
		mirNames[132] = "dme-miR-4977";
		mirNames[133] = "dme-miR-4978";
		mirNames[134] = "dme-miR-4979";
		mirNames[135] = "dme-miR-4980";
		mirNames[136] = "dme-miR-4982";
		mirNames[137] = "dme-miR-4984";
		mirNames[138] = "dme-miR-4985";
		mirNames[139] = "dme-miR-4986";
		mirNames[140] = "dme-miR-4987";
		mirNames[141] = "dme-miR-9370";
		mirNames[142] = "dme-miR-9371";
		mirNames[143] = "dme-miR-9372";
		mirNames[144] = "dme-miR-9373";
		mirNames[145] = "dme-miR-9374";
		mirNames[146] = "dme-miR-9375";
		mirNames[147] = "dme-miR-9377";
		mirNames[148] = "dme-miR-9378";
		mirNames[149] = "dme-miR-9379";
		mirNames[150] = "dme-miR-9380";
		mirNames[151] = "dme-miR-9382";
		mirNames[152] = "dme-miR-9383";
		mirNames[153] = "dme-miR-9384";
		mirNames[154] = "dme-miR-4974";
		
		return mirNames[index];
	}
	
	public static class xthread implements Runnable{
		public RCaller caller;
		public xthread(RCaller call){
			caller = call;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			caller.runAndReturnResult("yy1");
			if(SwingUtilities.isEventDispatchThread()==true){
				System.out.println("It's in the event dispatch thread");
			}else{
				System.out.println("It's NOT in the event dispatch thread");
			}

		}
		
	}
		
	//labels for miRs and host gene IDs
		public static void miRs(JPanel checkPane){//static void method for creation of JLabels
				final JCheckBox mir307a = new JCheckBox("miR-307a"); checkPane.add(mir307a);mir307a.setBackground(Color.WHITE);mir307a.setBounds(5, 25, 150, 25);mir307a.setOpaque(true);/*->add action listener*/		mir307a	.setHorizontalAlignment(SwingConstants.LEFT);
				final JCheckBox mir307b = new JCheckBox("miR-307b"); checkPane.add(mir307b);mir307b.setBackground(Color.lightGray);mir307b.setBounds(5, 50, 150, 25);mir307b.setOpaque(true);/*->add action listener*/		mir307b	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2a1 = new JCheckBox("miR-2a-1"); checkPane.add(mir2a1);mir2a1.setBackground(Color.WHITE);mir2a1.setBounds(5, 75, 150, 25);mir2a1.setOpaque(true);/*->add action listener*/		mir2a1	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2a2 = new JCheckBox("miR-2a-2"); checkPane.add(mir2a2);mir2a2.setBackground(Color.lightGray);mir2a2.setBounds(5, 100, 150, 25);mir2a2.setOpaque(true);/*->add action listener*/		mir2a2	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2b1 = new JCheckBox("miR-2b-1"); checkPane.add(mir2b1);mir2b1.setBackground(Color.WHITE);mir2b1.setBounds(5, 125, 150, 25);mir2b1.setOpaque(true);/*->add action listener*/		mir2b1	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2b2 = new JCheckBox("miR-2b-2"); checkPane.add(mir2b2);mir2b2.setBackground(Color.lightGray);mir2b2.setBounds(5, 150, 150, 25);mir2b2.setOpaque(true);/*->add action listener*/		mir2b2	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir7 = new JCheckBox("miR-7"); checkPane.add(mir7);mir7.setBackground(Color.WHITE);mir7.setBounds(5, 175, 150, 25);mir7.setOpaque(true);/*->add action listener*/		mir7	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir11 = new JCheckBox("miR-11"); checkPane.add(mir11);mir11.setBackground(Color.lightGray);mir11.setBounds(5, 200, 150, 25);mir11.setOpaque(true);/*->add action listener*/		mir11	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir12 = new JCheckBox("miR-12"); checkPane.add(mir12);mir12.setBackground(Color.WHITE);mir12.setBounds(5, 225, 150, 25);mir12.setOpaque(true);/*->add action listener*/		mir12	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir13b2 = new JCheckBox("miR-13b-2"); checkPane.add(mir13b2);mir13b2.setBackground(Color.lightGray);mir13b2.setBounds(5, 250, 150, 25);mir13b2.setOpaque(true);/*->add action listener*/		mir13b2	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir274 = new JCheckBox("miR-274"); checkPane.add(mir274);mir274.setBackground(Color.WHITE);mir274.setBounds(5, 275, 150, 25);mir274.setOpaque(true);/*->add action listener*/		mir274	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir92a = new JCheckBox("miR-92a"); checkPane.add(mir92a);mir92a.setBackground(Color.lightGray);mir92a.setBounds(5, 300, 150, 25);mir92a.setOpaque(true);/*->add action listener*/		mir92a	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir278 = new JCheckBox("miR-278"); checkPane.add(mir278);mir278.setBackground(Color.WHITE);mir278.setBounds(5, 325, 150, 25);mir278.setOpaque(true);/*->add action listener*/		mir278	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir33 = new JCheckBox("miR-33"); checkPane.add(mir33);mir33.setBackground(Color.lightGray);mir33.setBounds(5, 150, 150, 25);mir33.setOpaque(true);/*->add action listener*/		mir33	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2811 = new JCheckBox("miR-281-1"); checkPane.add(mir2811);mir2811.setBackground(Color.WHITE);mir2811.setBounds(5, 375, 150, 25);mir2811.setOpaque(true);/*->add action listener*/		mir2811	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir283 = new JCheckBox("miR-283"); checkPane.add(mir283);mir283.setBackground(Color.lightGray);mir283.setBounds(5, 400, 150, 25);mir283.setOpaque(true);/*->add action listener*/		mir283	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir284 = new JCheckBox("miR-284"); checkPane.add(mir284);mir284.setBackground(Color.WHITE);mir284.setBounds(5, 425, 150, 25);mir284.setOpaque(true);/*->add action listener*/		mir284	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2812 = new JCheckBox("miR-281-2"); checkPane.add(mir2812);mir2812.setBackground(Color.lightGray);mir2812.setBounds(5, 450, 150, 25);mir2812.setOpaque(true);/*->add action listener*/		mir2812	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir79 = new JCheckBox("miR-79"); checkPane.add(mir79);mir79.setBackground(Color.WHITE);mir79.setBounds(5, 475, 150, 25);mir79.setOpaque(true);/*->add action listener*/		mir79	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir92b = new JCheckBox("miR-92b"); checkPane.add(mir92b);mir92b.setBackground(Color.lightGray);mir92b.setBounds(5, 500, 150, 25);mir92b.setOpaque(true);/*->add action listener*/		mir92b	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir263b = new JCheckBox("miR-263b"); checkPane.add(mir263b);mir263b.setBackground(Color.WHITE);mir263b.setBounds(5, 525, 150, 25);mir263b.setOpaque(true);/*->add action listener*/		mir263b	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir289 = new JCheckBox("miR-289"); checkPane.add(mir289);mir289.setBackground(Color.lightGray);mir289.setBounds(5, 550, 150, 25);mir289.setOpaque(true);/*->add action listener*/		mir289	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir304 = new JCheckBox("miR-304"); checkPane.add(mir304);mir304.setBackground(Color.WHITE);mir304.setBounds(5, 575, 150, 25);mir304.setOpaque(true);/*->add action listener*/		mir304	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9c = new JCheckBox("miR-9c"); checkPane.add(mir9c);mir9c.setBackground(Color.lightGray);mir9c.setBounds(5, 600, 150, 25);mir9c.setOpaque(true);/*->add action listener*/		mir9c	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir306 = new JCheckBox("miR-306"); checkPane.add(mir306);mir306.setBackground(Color.WHITE);mir306.setBounds(5, 625, 150, 25);mir306.setOpaque(true);/*->add action listener*/		mir306	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9b = new JCheckBox("miR-9b"); checkPane.add(mir9b);mir9b.setBackground(Color.lightGray);mir9b.setBounds(5, 650, 150, 25);mir9b.setOpaque(true);/*->add action listener*/		mir9b	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir308 = new JCheckBox("miR-308"); checkPane.add(mir308);mir308.setBackground(Color.WHITE);mir308.setBounds(5, 675, 150, 25);mir308.setOpaque(true);/*->add action listener*/		mir308	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir190 = new JCheckBox("miR-190"); checkPane.add(mir190);mir190.setBackground(Color.lightGray);mir190.setBounds(5, 700, 150, 25);mir190.setOpaque(true);/*->add action listener*/		mir190	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir959 = new JCheckBox("miR-959"); checkPane.add(mir959);mir959.setBackground(Color.WHITE);mir959.setBounds(5, 725, 150, 25);mir959.setOpaque(true);/*->add action listener*/		mir959	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir960 = new JCheckBox("miR-960"); checkPane.add(mir960);mir960.setBackground(Color.lightGray);mir960.setBounds(5, 750, 150, 25);mir960.setOpaque(true);/*->add action listener*/		mir960	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir961 = new JCheckBox("miR-961"); checkPane.add(mir961);mir961.setBackground(Color.WHITE);mir961.setBounds(5, 775, 150, 25);mir961.setOpaque(true);/*->add action listener*/		mir961	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir962 = new JCheckBox("miR-962"); checkPane.add(mir962);mir962.setBackground(Color.lightGray);mir962.setBounds(5, 800, 150, 25);mir962.setOpaque(true);/*->add action listener*/		mir962	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir963 = new JCheckBox("miR-963"); checkPane.add(mir963);mir963.setBackground(Color.WHITE);mir963.setBounds(5, 825, 150, 25);mir963.setOpaque(true);/*->add action listener*/		mir963	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir964 = new JCheckBox("miR-964"); checkPane.add(mir964);mir964.setBackground(Color.lightGray);mir964.setBounds(5, 850, 150, 25);mir964.setOpaque(true);/*->add action listener*/		mir964	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir932 = new JCheckBox("miR-932"); checkPane.add(mir932);mir932.setBackground(Color.WHITE);mir932.setBounds(5, 875, 150, 25);mir932.setOpaque(true);/*->add action listener*/		mir932	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir965 = new JCheckBox("miR-965"); checkPane.add(mir965);mir965.setBackground(Color.lightGray);mir965.setBounds(5, 900, 150, 25);mir965.setOpaque(true);/*->add action listener*/		mir965	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir966 = new JCheckBox("miR-966"); checkPane.add(mir966);mir966.setBackground(Color.WHITE);mir966.setBounds(5, 925, 150, 25);mir966.setOpaque(true);/*->add action listener*/		mir966	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir967 = new JCheckBox("miR-967"); checkPane.add(mir967);mir967.setBackground(Color.lightGray);mir967.setBounds(5, 950, 150, 25);mir967.setOpaque(true);/*->add action listener*/		mir967	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir970 = new JCheckBox("miR-970"); checkPane.add(mir970);mir970.setBackground(Color.WHITE);mir970.setBounds(5, 975, 150, 25);mir970.setOpaque(true);/*->add action listener*/		mir970	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir971 = new JCheckBox("miR-971"); checkPane.add(mir971);mir971.setBackground(Color.lightGray);mir971.setBounds(5, 1000, 150, 25);mir971.setOpaque(true);/*->add action listener*/		mir971	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir978 = new JCheckBox("miR-978"); checkPane.add(mir978);mir978.setBackground(Color.WHITE);mir978.setBounds(5, 1025, 150, 25);mir978.setOpaque(true);/*->add action listener*/		mir978	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir979 = new JCheckBox("miR-979"); checkPane.add(mir979);mir979.setBackground(Color.lightGray);mir979.setBounds(5, 1050, 150, 25);mir979.setOpaque(true);/*->add action listener*/		mir979	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir980 = new JCheckBox("miR-980"); checkPane.add(mir980);mir980.setBackground(Color.WHITE);mir980.setBounds(5, 1075, 150, 25);mir980.setOpaque(true);/*->add action listener*/		mir980	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir981 = new JCheckBox("miR-981"); checkPane.add(mir981);mir981.setBackground(Color.lightGray);mir981.setBounds(5, 1100, 150, 25);mir981.setOpaque(true);/*->add action listener*/		mir981	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9831 = new JCheckBox("miR-983-1"); checkPane.add(mir9831);mir9831.setBackground(Color.WHITE);mir9831.setBounds(5, 1125, 150, 25);mir9831.setOpaque(true);/*->add action listener*/		mir9831	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9832 = new JCheckBox("miR-983-2"); checkPane.add(mir9832);mir9832.setBackground(Color.lightGray);mir9832.setBounds(5, 1150, 150, 25);mir9832.setOpaque(true);/*->add action listener*/		mir9832	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir984 = new JCheckBox("miR-984"); checkPane.add(mir984);mir984.setBackground(Color.WHITE);mir984.setBounds(5, 1175, 150, 25);mir984.setOpaque(true);/*->add action listener*/		mir984	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir927 = new JCheckBox("miR-927"); checkPane.add(mir927);mir927.setBackground(Color.lightGray);mir927.setBounds(5, 1200, 150, 25);mir927.setOpaque(true);/*->add action listener*/		mir927	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir986 = new JCheckBox("miR-986"); checkPane.add(mir986);mir986.setBackground(Color.WHITE);mir986.setBounds(5, 1225, 150, 25);mir986.setOpaque(true);/*->add action listener*/		mir986	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir988 = new JCheckBox("miR-988"); checkPane.add(mir988);mir988.setBackground(Color.lightGray);mir988.setBounds(5, 1250, 150, 25);mir988.setOpaque(true);/*->add action listener*/		mir988	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir990 = new JCheckBox("miR-990"); checkPane.add(mir990);mir990.setBackground(Color.WHITE);mir990.setBounds(5, 1275, 150, 25);mir990.setOpaque(true);/*->add action listener*/		mir990	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir929 = new JCheckBox("miR-929"); checkPane.add(mir929);mir929.setBackground(Color.lightGray);mir929.setBounds(5, 1300, 150, 25);mir929.setOpaque(true);/*->add action listener*/		mir929	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir995 = new JCheckBox("miR-995"); checkPane.add(mir995);mir995.setBackground(Color.WHITE);mir995.setBounds(5, 1325, 150, 25);mir995.setOpaque(true);/*->add action listener*/		mir995	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir998 = new JCheckBox("miR-998"); checkPane.add(mir998);mir998.setBackground(Color.lightGray);mir998.setBounds(5, 1150, 150, 25);mir998.setOpaque(true);/*->add action listener*/		mir998	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir999 = new JCheckBox("miR-999"); checkPane.add(mir999);mir999.setBackground(Color.WHITE);mir999.setBounds(5, 1375, 150, 25);mir999.setOpaque(true);/*->add action listener*/		mir999	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1001 = new JCheckBox("miR-1001"); checkPane.add(mir1001);mir1001.setBackground(Color.lightGray);mir1001.setBounds(5, 1400, 150, 25);mir1001.setOpaque(true);/*->add action listener*/		mir1001	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1003 = new JCheckBox("miR-1003"); checkPane.add(mir1003);mir1003.setBackground(Color.WHITE);mir1003.setBounds(5, 1425, 150, 25);mir1003.setOpaque(true);/*->add action listener*/		mir1003	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1004 = new JCheckBox("miR-1004"); checkPane.add(mir1004);mir1004.setBackground(Color.lightGray);mir1004.setBounds(5, 1450, 150, 25);mir1004.setOpaque(true);/*->add action listener*/		mir1004	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1005 = new JCheckBox("miR-1005"); checkPane.add(mir1005);mir1005.setBackground(Color.WHITE);mir1005.setBounds(5, 1475, 150, 25);mir1005.setOpaque(true);/*->add action listener*/		mir1005	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1006 = new JCheckBox("miR-1006"); checkPane.add(mir1006);mir1006.setBackground(Color.lightGray);mir1006.setBounds(5, 1500, 150, 25);mir1006.setOpaque(true);/*->add action listener*/		mir1006	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1007 = new JCheckBox("miR-1007"); checkPane.add(mir1007);mir1007.setBackground(Color.WHITE);mir1007.setBounds(5, 1525, 150, 25);mir1007.setOpaque(true);/*->add action listener*/		mir1007	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1008 = new JCheckBox("miR-1008"); checkPane.add(mir1008);mir1008.setBackground(Color.lightGray);mir1008.setBounds(5, 1550, 150, 25);mir1008.setOpaque(true);/*->add action listener*/		mir1008	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1009 = new JCheckBox("miR-1009"); checkPane.add(mir1009);mir1009.setBackground(Color.WHITE);mir1009.setBounds(5, 1575, 150, 25);mir1009.setOpaque(true);/*->add action listener*/		mir1009	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1010 = new JCheckBox("miR-1010"); checkPane.add(mir1010);mir1010.setBackground(Color.lightGray);mir1010.setBounds(5, 1600, 150, 25);mir1010.setOpaque(true);/*->add action listener*/		mir1010	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1011 = new JCheckBox("miR-1011"); checkPane.add(mir1011);mir1011.setBackground(Color.WHITE);mir1011.setBounds(5, 1625, 150, 25);mir1011.setOpaque(true);/*->add action listener*/		mir1011	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1012 = new JCheckBox("miR-1012"); checkPane.add(mir1012);mir1012.setBackground(Color.lightGray);mir1012.setBounds(5, 1650, 150, 25);mir1012.setOpaque(true);/*->add action listener*/		mir1012	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1013 = new JCheckBox("miR-1013"); checkPane.add(mir1013);mir1013.setBackground(Color.WHITE);mir1013.setBounds(5, 1675, 150, 25);mir1013.setOpaque(true);/*->add action listener*/		mir1013	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1014 = new JCheckBox("miR-1014"); checkPane.add(mir1014);mir1014.setBackground(Color.lightGray);mir1014.setBounds(5, 1700, 150, 25);mir1014.setOpaque(true);/*->add action listener*/		mir1014	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1015 = new JCheckBox("miR-1015"); checkPane.add(mir1015);mir1015.setBackground(Color.WHITE);mir1015.setBounds(5, 1725, 150, 25);mir1015.setOpaque(true);/*->add action listener*/		mir1015	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1016 = new JCheckBox("miR-1016"); checkPane.add(mir1016);mir1016.setBackground(Color.lightGray);mir1016.setBounds(5, 1750, 150, 25);mir1016.setOpaque(true);/*->add action listener*/		mir1016	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir1017 = new JCheckBox("miR-1017"); checkPane.add(mir1017);mir1017.setBackground(Color.WHITE);mir1017.setBounds(5, 1775, 150, 25);mir1017.setOpaque(true);/*->add action listener*/		mir1017	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2279 = new JCheckBox("miR-2279"); checkPane.add(mir2279);mir2279.setBackground(Color.lightGray);mir2279.setBounds(5, 1800, 150, 25);mir2279.setOpaque(true);/*->add action listener*/		mir2279	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2280 = new JCheckBox("miR-2280"); checkPane.add(mir2280);mir2280.setBackground(Color.WHITE);mir2280.setBounds(5, 1825, 150, 25);mir2280.setOpaque(true);/*->add action listener*/		mir2280	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2281 = new JCheckBox("miR-2281"); checkPane.add(mir2281);mir2281.setBackground(Color.lightGray);mir2281.setBounds(5, 1850, 150, 25);mir2281.setOpaque(true);/*->add action listener*/		mir2281	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2282 = new JCheckBox("miR-2282"); checkPane.add(mir2282);mir2282.setBackground(Color.WHITE);mir2282.setBounds(5, 1875, 150, 25);mir2282.setOpaque(true);/*->add action listener*/		mir2282	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2489 = new JCheckBox("miR-2489"); checkPane.add(mir2489);mir2489.setBackground(Color.lightGray);mir2489.setBounds(5, 1900, 150, 25);mir2489.setOpaque(true);/*->add action listener*/		mir2489	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2490 = new JCheckBox("miR-2490"); checkPane.add(mir2490);mir2490.setBackground(Color.WHITE);mir2490.setBounds(5, 1925, 150, 25);mir2490.setOpaque(true);/*->add action listener*/		mir2490	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2492 = new JCheckBox("miR-2492"); checkPane.add(mir2492);mir2492.setBackground(Color.lightGray);mir2492.setBounds(5, 1950, 150, 25);mir2492.setOpaque(true);/*->add action listener*/		mir2492	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2493 = new JCheckBox("miR-2493"); checkPane.add(mir2493);mir2493.setBackground(Color.WHITE);mir2493.setBounds(5, 1975, 150, 25);mir2493.setOpaque(true);/*->add action listener*/		mir2493	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2494 = new JCheckBox("miR-2494"); checkPane.add(mir2494);mir2494.setBackground(Color.lightGray);mir2494.setBounds(5, 2000, 150, 25);mir2494.setOpaque(true);/*->add action listener*/		mir2494	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2495 = new JCheckBox("miR-2495"); checkPane.add(mir2495);mir2495.setBackground(Color.WHITE);mir2495.setBounds(5, 2025, 150, 25);mir2495.setOpaque(true);/*->add action listener*/		mir2495	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2497 = new JCheckBox("miR-2497"); checkPane.add(mir2497);mir2497.setBackground(Color.lightGray);mir2497.setBounds(5, 2050, 150, 25);mir2497.setOpaque(true);/*->add action listener*/		mir2497	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2500 = new JCheckBox("miR-2500"); checkPane.add(mir2500);mir2500.setBackground(Color.WHITE);mir2500.setBounds(5, 2075, 150, 25);mir2500.setOpaque(true);/*->add action listener*/		mir2500	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2501 = new JCheckBox("miR-2501"); checkPane.add(mir2501);mir2501.setBackground(Color.lightGray);mir2501.setBounds(5, 2100, 150, 25);mir2501.setOpaque(true);/*->add action listener*/		mir2501	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir3641 = new JCheckBox("miR-3641"); checkPane.add(mir3641);mir3641.setBackground(Color.WHITE);mir3641.setBounds(5, 2125, 150, 25);mir3641.setOpaque(true);/*->add action listener*/		mir3641	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir3642 = new JCheckBox("miR-3642"); checkPane.add(mir3642);mir3642.setBackground(Color.lightGray);mir3642.setBounds(5, 2150, 150, 25);mir3642.setOpaque(true);/*->add action listener*/		mir3642	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir3643 = new JCheckBox("miR-3643"); checkPane.add(mir3643);mir3643.setBackground(Color.WHITE);mir3643.setBounds(5, 2175, 150, 25);mir3643.setOpaque(true);/*->add action listener*/		mir3643	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir3644 = new JCheckBox("miR-3644"); checkPane.add(mir3644);mir3644.setBackground(Color.lightGray);mir3644.setBounds(5, 2200, 150, 25);mir3644.setOpaque(true);/*->add action listener*/		mir3644	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir3645 = new JCheckBox("miR-3645"); checkPane.add(mir3645);mir3645.setBackground(Color.WHITE);mir3645.setBounds(5, 2225, 150, 25);mir3645.setOpaque(true);/*->add action listener*/		mir3645	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4910 = new JCheckBox("miR-4910"); checkPane.add(mir4910);mir4910.setBackground(Color.lightGray);mir4910.setBounds(5, 2250, 150, 25);mir4910.setOpaque(true);/*->add action listener*/		mir4910	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4911 = new JCheckBox("miR-4911"); checkPane.add(mir4911);mir4911.setBackground(Color.WHITE);mir4911.setBounds(5, 2275, 150, 25);mir4911.setOpaque(true);/*->add action listener*/		mir4911	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4912 = new JCheckBox("miR-4912"); checkPane.add(mir4912);mir4912.setBackground(Color.lightGray);mir4912.setBounds(5, 2300, 150, 25);mir4912.setOpaque(true);/*->add action listener*/		mir4912	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4913 = new JCheckBox("miR-4913"); checkPane.add(mir4913);mir4913.setBackground(Color.WHITE);mir4913.setBounds(5, 2325, 150, 25);mir4913.setOpaque(true);/*->add action listener*/		mir4913	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4914 = new JCheckBox("miR-4914"); checkPane.add(mir4914);mir4914.setBackground(Color.lightGray);mir4914.setBounds(5, 2150, 150, 25);mir4914.setOpaque(true);/*->add action listener*/		mir4914	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4915 = new JCheckBox("miR-4915"); checkPane.add(mir4915);mir4915.setBackground(Color.WHITE);mir4915.setBounds(5, 2375, 150, 25);mir4915.setOpaque(true);/*->add action listener*/		mir4915	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4916 = new JCheckBox("miR-4916"); checkPane.add(mir4916);mir4916.setBackground(Color.lightGray);mir4916.setBounds(5, 2400, 150, 25);mir4916.setOpaque(true);/*->add action listener*/		mir4916	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir2535b = new JCheckBox("miR-2535b"); checkPane.add(mir2535b);mir2535b.setBackground(Color.WHITE);mir2535b.setBounds(5, 2425, 150, 25);mir2535b.setOpaque(true);/*->add action listener*/		mir2535b	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4917 = new JCheckBox("miR-4917"); checkPane.add(mir4917);mir4917.setBackground(Color.lightGray);mir4917.setBounds(5, 2450, 150, 25);mir4917.setOpaque(true);/*->add action listener*/		mir4917	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4918 = new JCheckBox("miR-4918"); checkPane.add(mir4918);mir4918.setBackground(Color.WHITE);mir4918.setBounds(5, 2475, 150, 25);mir4918.setOpaque(true);/*->add action listener*/		mir4918	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4919 = new JCheckBox("miR-4919"); checkPane.add(mir4919);mir4919.setBackground(Color.lightGray);mir4919.setBounds(5, 2500, 150, 25);mir4919.setOpaque(true);/*->add action listener*/		mir4919	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4908 = new JCheckBox("miR-4908"); checkPane.add(mir4908);mir4908.setBackground(Color.WHITE);mir4908.setBounds(5, 2525, 150, 25);mir4908.setOpaque(true);/*->add action listener*/		mir4908	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4909 = new JCheckBox("miR-4909"); checkPane.add(mir4909);mir4909.setBackground(Color.lightGray);mir4909.setBounds(5, 2550, 150, 25);mir4909.setOpaque(true);/*->add action listener*/		mir4909	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4939 = new JCheckBox("miR-4939"); checkPane.add(mir4939);mir4939.setBackground(Color.WHITE);mir4939.setBounds(5, 2575, 150, 25);mir4939.setOpaque(true);/*->add action listener*/		mir4939	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4940 = new JCheckBox("miR-4940"); checkPane.add(mir4940);mir4940.setBackground(Color.lightGray);mir4940.setBounds(5, 2600, 150, 25);mir4940.setOpaque(true);/*->add action listener*/		mir4940	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4941 = new JCheckBox("miR-4941"); checkPane.add(mir4941);mir4941.setBackground(Color.WHITE);mir4941.setBounds(5, 2625, 150, 25);mir4941.setOpaque(true);/*->add action listener*/		mir4941	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4942 = new JCheckBox("miR-4942"); checkPane.add(mir4942);mir4942.setBackground(Color.lightGray);mir4942.setBounds(5, 2650, 150, 25);mir4942.setOpaque(true);/*->add action listener*/		mir4942	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4943 = new JCheckBox("miR-4943"); checkPane.add(mir4943);mir4943.setBackground(Color.WHITE);mir4943.setBounds(5, 2675, 150, 25);mir4943.setOpaque(true);/*->add action listener*/		mir4943	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4944 = new JCheckBox("miR-4944"); checkPane.add(mir4944);mir4944.setBackground(Color.lightGray);mir4944.setBounds(5, 2700, 150, 25);mir4944.setOpaque(true);/*->add action listener*/		mir4944	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4946 = new JCheckBox("miR-4946"); checkPane.add(mir4946);mir4946.setBackground(Color.WHITE);mir4946.setBounds(5, 2725, 150, 25);mir4946.setOpaque(true);/*->add action listener*/		mir4946	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4947 = new JCheckBox("miR-4947"); checkPane.add(mir4947);mir4947.setBackground(Color.lightGray);mir4947.setBounds(5, 2750, 150, 25);mir4947.setOpaque(true);/*->add action listener*/		mir4947	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4948 = new JCheckBox("miR-4948"); checkPane.add(mir4948);mir4948.setBackground(Color.WHITE);mir4948.setBounds(5, 2775, 150, 25);mir4948.setOpaque(true);/*->add action listener*/		mir4948	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4949 = new JCheckBox("miR-4949"); checkPane.add(mir4949);mir4949.setBackground(Color.lightGray);mir4949.setBounds(5, 2800, 150, 25);mir4949.setOpaque(true);/*->add action listener*/		mir4949	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4952 = new JCheckBox("miR-4952"); checkPane.add(mir4952);mir4952.setBackground(Color.WHITE);mir4952.setBounds(5, 2825, 150, 25);mir4952.setOpaque(true);/*->add action listener*/		mir4952	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4954 = new JCheckBox("miR-4954"); checkPane.add(mir4954);mir4954.setBackground(Color.lightGray);mir4954.setBounds(5, 2850, 150, 25);mir4954.setOpaque(true);/*->add action listener*/		mir4954	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4955 = new JCheckBox("miR-4955"); checkPane.add(mir4955);mir4955.setBackground(Color.WHITE);mir4955.setBounds(5, 2875, 150, 25);mir4955.setOpaque(true);/*->add action listener*/		mir4955	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4956 = new JCheckBox("miR-4956"); checkPane.add(mir4956);mir4956.setBackground(Color.lightGray);mir4956.setBounds(5, 2900, 150, 25);mir4956.setOpaque(true);/*->add action listener*/		mir4956	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4957 = new JCheckBox("miR-4957"); checkPane.add(mir4957);mir4957.setBackground(Color.WHITE);mir4957.setBounds(5, 2925, 150, 25);mir4957.setOpaque(true);/*->add action listener*/		mir4957	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4958 = new JCheckBox("miR-4958"); checkPane.add(mir4958);mir4958.setBackground(Color.lightGray);mir4958.setBounds(5, 2950, 150, 25);mir4958.setOpaque(true);/*->add action listener*/		mir4958	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4959 = new JCheckBox("miR-4959"); checkPane.add(mir4959);mir4959.setBackground(Color.WHITE);mir4959.setBounds(5, 2975, 150, 25);mir4959.setOpaque(true);/*->add action listener*/		mir4959	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4960 = new JCheckBox("miR-4960"); checkPane.add(mir4960);mir4960.setBackground(Color.lightGray);mir4960.setBounds(5, 3000, 150, 25);mir4960.setOpaque(true);/*->add action listener*/		mir4960	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4961 = new JCheckBox("miR-4961"); checkPane.add(mir4961);mir4961.setBackground(Color.WHITE);mir4961.setBounds(5, 3025, 150, 25);mir4961.setOpaque(true);/*->add action listener*/		mir4961	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4962 = new JCheckBox("miR-4962"); checkPane.add(mir4962);mir4962.setBackground(Color.lightGray);mir4962.setBounds(5, 3050, 150, 25);mir4962.setOpaque(true);/*->add action listener*/		mir4962	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4963 = new JCheckBox("miR-4963"); checkPane.add(mir4963);mir4963.setBackground(Color.WHITE);mir4963.setBounds(5, 3075, 150, 25);mir4963.setOpaque(true);/*->add action listener*/		mir4963	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4964 = new JCheckBox("miR-4964"); checkPane.add(mir4964);mir4964.setBackground(Color.lightGray);mir4964.setBounds(5, 3100, 150, 25);mir4964.setOpaque(true);/*->add action listener*/		mir4964	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4965 = new JCheckBox("miR-4965"); checkPane.add(mir4965);mir4965.setBackground(Color.WHITE);mir4965.setBounds(5, 3125, 150, 25);mir4965.setOpaque(true);/*->add action listener*/		mir4965	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4967 = new JCheckBox("miR-4967"); checkPane.add(mir4967);mir4967.setBackground(Color.lightGray);mir4967.setBounds(5, 3150, 150, 25);mir4967.setOpaque(true);/*->add action listener*/		mir4967	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4968 = new JCheckBox("miR-4968"); checkPane.add(mir4968);mir4968.setBackground(Color.WHITE);mir4968.setBounds(5, 3175, 150, 25);mir4968.setOpaque(true);/*->add action listener*/		mir4968	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4969 = new JCheckBox("miR-4969"); checkPane.add(mir4969);mir4969.setBackground(Color.lightGray);mir4969.setBounds(5, 3200, 150, 25);mir4969.setOpaque(true);/*->add action listener*/		mir4969	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4970 = new JCheckBox("miR-4670"); checkPane.add(mir4970);mir4970.setBackground(Color.WHITE);mir4970.setBounds(5, 3225, 150, 25);mir4970.setOpaque(true);/*->add action listener*/		mir4970	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4971 = new JCheckBox("miR-4971"); checkPane.add(mir4971);mir4971.setBackground(Color.lightGray);mir4971.setBounds(5, 3250, 150, 25);mir4971.setOpaque(true);/*->add action listener*/		mir4971	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4972 = new JCheckBox("miR-4972"); checkPane.add(mir4972);mir4972.setBackground(Color.WHITE);mir4972.setBounds(5, 3275, 150, 25);mir4972.setOpaque(true);/*->add action listener*/		mir4972	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4976 = new JCheckBox("miR-4976"); checkPane.add(mir4976);mir4976.setBackground(Color.lightGray);mir4976.setBounds(5, 3300, 150, 25);mir4976.setOpaque(true);/*->add action listener*/		mir4976	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4977 = new JCheckBox("miR-4977"); checkPane.add(mir4977);mir4977.setBackground(Color.WHITE);mir4977.setBounds(5, 3325, 150, 25);mir4977.setOpaque(true);/*->add action listener*/		mir4977	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4978 = new JCheckBox("miR-4978"); checkPane.add(mir4978);mir4978.setBackground(Color.lightGray);mir4978.setBounds(5, 3150, 150, 25);mir4978.setOpaque(true);/*->add action listener*/		mir4978	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4979 = new JCheckBox("miR-4979"); checkPane.add(mir4979);mir4979.setBackground(Color.WHITE);mir4979.setBounds(5, 3375, 150, 25);mir4979.setOpaque(true);/*->add action listener*/		mir4979	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4980 = new JCheckBox("miR-4980"); checkPane.add(mir4980);mir4980.setBackground(Color.lightGray);mir4980.setBounds(5, 3400, 150, 25);mir4980.setOpaque(true);/*->add action listener*/		mir4980	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4982 = new JCheckBox("miR-4982"); checkPane.add(mir4982);mir4982.setBackground(Color.WHITE);mir4982.setBounds(5, 3425, 150, 25);mir4982.setOpaque(true);/*->add action listener*/		mir4982	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4984 = new JCheckBox("miR-4984"); checkPane.add(mir4984);mir4984.setBackground(Color.lightGray);mir4984.setBounds(5, 3450, 150, 25);mir4984.setOpaque(true);/*->add action listener*/		mir4984	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4985 = new JCheckBox("miR-4985"); checkPane.add(mir4985);mir4985.setBackground(Color.WHITE);mir4985.setBounds(5, 3475, 150, 25);mir4985.setOpaque(true);/*->add action listener*/		mir4985	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4986 = new JCheckBox("miR-4986"); checkPane.add(mir4986);mir4986.setBackground(Color.lightGray);mir4986.setBounds(5, 1500, 150, 25);mir4986.setOpaque(true);/*->add action listener*/		mir4986	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4987 = new JCheckBox("miR-4987"); checkPane.add(mir4987);mir4987.setBackground(Color.WHITE);mir4987.setBounds(5, 3525, 150, 25);mir4987.setOpaque(true);/*->add action listener*/		mir4987	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9370 = new JCheckBox("miR-9370"); checkPane.add(mir9370);mir9370.setBackground(Color.lightGray);mir9370.setBounds(5, 3550, 150, 25);mir9370.setOpaque(true);/*->add action listener*/		mir9370	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9371 = new JCheckBox("miR-9371"); checkPane.add(mir9371);mir9371.setBackground(Color.WHITE);mir9371.setBounds(5, 3575, 150, 25);mir9371.setOpaque(true);/*->add action listener*/		mir9371	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9372 = new JCheckBox("miR-9372"); checkPane.add(mir9372);mir9372.setBackground(Color.lightGray);mir9372.setBounds(5, 3600, 150, 25);mir9372.setOpaque(true);/*->add action listener*/		mir9372	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9373 = new JCheckBox("miR-9373"); checkPane.add(mir9373);mir9373.setBackground(Color.WHITE);mir9373.setBounds(5, 3625, 150, 25);mir9373.setOpaque(true);/*->add action listener*/		mir9373	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9374 = new JCheckBox("miR-9374"); checkPane.add(mir9374);mir9374.setBackground(Color.lightGray);mir9374.setBounds(5, 3650, 150, 25);mir9374.setOpaque(true);/*->add action listener*/		mir9374	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9375 = new JCheckBox("miR-9375"); checkPane.add(mir9375);mir9375.setBackground(Color.WHITE);mir9375.setBounds(5, 3675, 150, 25);mir9375.setOpaque(true);/*->add action listener*/		mir9375	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9377 = new JCheckBox("miR-9377"); checkPane.add(mir9377);mir9377.setBackground(Color.lightGray);mir9377.setBounds(5, 3700, 150, 25);mir9377.setOpaque(true);/*->add action listener*/		mir9377	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9378 = new JCheckBox("miR-9378"); checkPane.add(mir9378);mir9378.setBackground(Color.WHITE);mir9378.setBounds(5, 3725, 150, 25);mir9378.setOpaque(true);/*->add action listener*/		mir9378	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9379 = new JCheckBox("miR-9379"); checkPane.add(mir9379);mir9379.setBackground(Color.lightGray);mir9379.setBounds(5, 3750, 150, 25);mir9379.setOpaque(true);/*->add action listener*/		mir9379	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9380 = new JCheckBox("miR-9380"); checkPane.add(mir9380);mir9380.setBackground(Color.WHITE);mir9380.setBounds(5, 3775, 150, 25);mir9380.setOpaque(true);/*->add action listener*/		mir9380	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9382 = new JCheckBox("miR-9382"); checkPane.add(mir9382);mir9382.setBackground(Color.lightGray);mir9382.setBounds(5, 3800, 150, 25);mir9382.setOpaque(true);/*->add action listener*/		mir9382	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9383 = new JCheckBox("miR-9383"); checkPane.add(mir9383);mir9383.setBackground(Color.WHITE);mir9383.setBounds(5, 3825, 150, 25);mir9383.setOpaque(true);/*->add action listener*/		mir9383	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir9384 = new JCheckBox("miR-9384"); checkPane.add(mir9384);mir9384.setBackground(Color.lightGray);mir9384.setBounds(5, 3850, 150, 25);mir9384.setOpaque(true);/*->add action listener*/		mir9384	.setHorizontalAlignment(SwingConstants.CENTER);
				final JCheckBox mir4974 = new JCheckBox("miR-4974"); checkPane.add(mir4974);mir4974.setBackground(Color.WHITE);mir4974.setBounds(5, 3875, 150, 25);mir4974.setOpaque(true);/*->add action listener*/		mir4974	.setHorizontalAlignment(SwingConstants.CENTER);
		
			}
			
		public static void geneIDs(JPanel hostPane){//static void method for creation of JLabels for host IDs
		//in order of code
		final JLabel iD1= new JLabel("CG1794"); iD1.setBounds(0, 25, 200, 25); iD1.setBackground(Color.WHITE); hostPane.add(iD1); iD1.setOpaque(true); iD1.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD2= new JLabel("CG1794"); iD2.setBounds(0, 50, 200, 25); iD2.setBackground(Color.lightGray); hostPane.add(iD2); iD2.setOpaque(true); iD2.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD3= new JLabel("CG10334"); iD3.setBounds(0, 75, 200, 25); iD3.setBackground(Color.WHITE); hostPane.add(iD3); iD3.setOpaque(true); iD3.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD4= new JLabel("CG10334"); iD4.setBounds(0, 100, 200, 25); iD4.setBackground(Color.lightGray); hostPane.add(iD4); iD4.setOpaque(true); iD4.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD5= new JLabel("CG10334"); iD5.setBounds(0, 125, 200, 25); iD5.setBackground(Color.WHITE); hostPane.add(iD5); iD5.setOpaque(true); iD5.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD6= new JLabel("CG10334"); iD6.setBounds(0, 150, 200, 25); iD6.setBackground(Color.lightGray); hostPane.add(iD6); iD6.setOpaque(true); iD6.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD7= new JLabel("CG13425"); iD7.setBounds(0, 175, 200, 25); iD7.setBackground(Color.WHITE); hostPane.add(iD7); iD7.setOpaque(true); iD7.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD8= new JLabel("CG6376"); iD8.setBounds(0, 200, 200, 25); iD8.setBackground(Color.lightGray); hostPane.add(iD8); iD8.setOpaque(true); iD8.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD9= new JLabel("CG33206"); iD9.setBounds(0, 225, 200, 25); iD9.setBackground(Color.WHITE); hostPane.add(iD9); iD9.setOpaque(true); iD9.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD10= new JLabel("CG7033"); iD10.setBounds(0, 250, 200, 25); iD10.setBackground(Color.lightGray); hostPane.add(iD10); iD10.setOpaque(true); iD10.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD11= new JLabel("CG32085"); iD11.setBounds(0, 275, 200, 25); iD11.setBackground(Color.WHITE); hostPane.add(iD11); iD11.setOpaque(true); iD11.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD12= new JLabel("CG17383"); iD12.setBounds(0, 300, 200, 25); iD12.setBackground(Color.lightGray); hostPane.add(iD12); iD12.setOpaque(true); iD12.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD13= new JLabel("CG42524"); iD13.setBounds(0, 325, 200, 25); iD13.setBackground(Color.WHITE); hostPane.add(iD13); iD13.setOpaque(true); iD13.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD14= new JLabel("CG8522"); iD14.setBounds(0, 350, 200, 25); iD14.setBackground(Color.lightGray); hostPane.add(iD14); iD14.setOpaque(true); iD14.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD15= new JLabel("CG16747"); iD15.setBounds(0, 375, 200, 25); iD15.setBackground(Color.WHITE); hostPane.add(iD15); iD15.setOpaque(true); iD15.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD16= new JLabel("CG33206"); iD16.setBounds(0, 400, 200, 25); iD16.setBackground(Color.lightGray); hostPane.add(iD16); iD16.setOpaque(true); iD16.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD17= new JLabel("CG33976"); iD17.setBounds(0, 425, 200, 25); iD17.setBackground(Color.WHITE); hostPane.add(iD17); iD17.setOpaque(true); iD17.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD18= new JLabel("CG16747"); iD18.setBounds(0, 450, 200, 25); iD18.setBackground(Color.lightGray); hostPane.add(iD18); iD18.setOpaque(true); iD18.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD19= new JLabel("CG17161"); iD19.setBounds(0, 475, 200, 25); iD19.setBackground(Color.WHITE); hostPane.add(iD19); iD19.setOpaque(true); iD19.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD20= new JLabel("CG17383"); iD20.setBounds(0, 500, 200, 25); iD20.setBackground(Color.lightGray); hostPane.add(iD20); iD20.setOpaque(true); iD20.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD21= new JLabel("CG32150"); iD21.setBounds(0, 525, 200, 25); iD21.setBackground(Color.WHITE); hostPane.add(iD21); iD21.setOpaque(true); iD21.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD22= new JLabel("CG43744"); iD22.setBounds(0, 550, 200, 25); iD22.setBackground(Color.lightGray); hostPane.add(iD22); iD22.setOpaque(true); iD22.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD23= new JLabel("CG33206"); iD23.setBounds(0, 575, 200, 25); iD23.setBackground(Color.WHITE); hostPane.add(iD23); iD23.setOpaque(true); iD23.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD24= new JLabel("CG17161"); iD24.setBounds(0, 600, 200, 25); iD24.setBackground(Color.lightGray); hostPane.add(iD24); iD24.setOpaque(true); iD24.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD25= new JLabel("CG17161"); iD25.setBounds(0, 625, 200, 25); iD25.setBackground(Color.WHITE); hostPane.add(iD25); iD25.setOpaque(true); iD25.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD26= new JLabel("CG17161"); iD26.setBounds(0, 650, 200, 25); iD26.setBackground(Color.lightGray); hostPane.add(iD26); iD26.setOpaque(true); iD26.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD27= new JLabel("CG8415"); iD27.setBounds(0, 675, 200, 25); iD27.setBackground(Color.WHITE); hostPane.add(iD27); iD27.setOpaque(true); iD27.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD28= new JLabel("CG6831"); iD28.setBounds(0, 700, 200, 25); iD28.setBackground(Color.lightGray); hostPane.add(iD28); iD28.setOpaque(true); iD28.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD29= new JLabel("CG31646"); iD29.setBounds(0, 725, 200, 25); iD29.setBackground(Color.WHITE); hostPane.add(iD29); iD29.setOpaque(true); iD29.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD30= new JLabel("CG31646"); iD30.setBounds(0, 750, 200, 25); iD30.setBackground(Color.lightGray); hostPane.add(iD30); iD30.setOpaque(true); iD30.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD31= new JLabel("CG31646"); iD31.setBounds(0, 775, 200, 25); iD31.setBackground(Color.WHITE); hostPane.add(iD31); iD31.setOpaque(true); iD31.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD32= new JLabel("CG31646"); iD32.setBounds(0, 800, 200, 25); iD32.setBackground(Color.lightGray); hostPane.add(iD32); iD32.setOpaque(true); iD32.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD33= new JLabel("CG31646"); iD33.setBounds(0, 825, 200, 25); iD33.setBackground(Color.WHITE); hostPane.add(iD33); iD33.setOpaque(true); iD33.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD34= new JLabel("CG31646"); iD34.setBounds(0, 850, 200, 25); iD34.setBackground(Color.lightGray); hostPane.add(iD34); iD34.setOpaque(true); iD34.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD35= new JLabel("CG13772"); iD35.setBounds(0, 875, 200, 25); iD35.setBackground(Color.WHITE); hostPane.add(iD35); iD35.setOpaque(true); iD35.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD36= new JLabel("CG3696"); iD36.setBounds(0, 900, 200, 25); iD36.setBackground(Color.lightGray); hostPane.add(iD36); iD36.setOpaque(true); iD36.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD37= new JLabel("CG9127"); iD37.setBounds(0, 925, 200, 25); iD37.setBackground(Color.WHITE); hostPane.add(iD37); iD37.setOpaque(true); iD37.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD38= new JLabel("CG42281"); iD38.setBounds(0, 950, 200, 25); iD38.setBackground(Color.lightGray); hostPane.add(iD38); iD38.setOpaque(true); iD38.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD39= new JLabel("CG17762"); iD39.setBounds(0, 975, 200, 25); iD39.setBackground(Color.WHITE); hostPane.add(iD39); iD39.setOpaque(true); iD39.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD40= new JLabel("CG44424"); iD40.setBounds(0, 1000, 200, 25); iD40.setBackground(Color.lightGray); hostPane.add(iD40); iD40.setOpaque(true); iD40.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD41= new JLabel("CG3917"); iD41.setBounds(0, 1025, 200, 25); iD41.setBackground(Color.WHITE); hostPane.add(iD41); iD41.setOpaque(true); iD41.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD42= new JLabel("CG3917"); iD42.setBounds(0, 1050, 200, 25); iD42.setBackground(Color.lightGray); hostPane.add(iD42); iD42.setOpaque(true); iD42.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD43= new JLabel("CG3777"); iD43.setBounds(0, 1075, 200, 25); iD43.setBackground(Color.WHITE); hostPane.add(iD43); iD43.setOpaque(true); iD43.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD44= new JLabel("CG42666"); iD44.setBounds(0, 1100, 200, 25); iD44.setBackground(Color.lightGray); hostPane.add(iD44); iD44.setOpaque(true); iD44.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD45= new JLabel("CG3626"); iD45.setBounds(0, 1125, 200, 25); iD45.setBackground(Color.WHITE); hostPane.add(iD45); iD45.setOpaque(true); iD45.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD46= new JLabel("CG3626"); iD46.setBounds(0, 1150, 200, 25); iD46.setBackground(Color.lightGray); hostPane.add(iD46); iD46.setOpaque(true); iD46.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD47= new JLabel("CG3626"); iD47.setBounds(0, 1175, 200, 25); iD47.setBackground(Color.WHITE); hostPane.add(iD47); iD47.setOpaque(true); iD47.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD48= new JLabel("CG42252"); iD48.setBounds(0, 1200, 200, 25); iD48.setBackground(Color.lightGray); hostPane.add(iD48); iD48.setOpaque(true); iD48.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD49= new JLabel("CG2060"); iD49.setBounds(0, 1225, 200, 25); iD49.setBackground(Color.WHITE); hostPane.add(iD49); iD49.setOpaque(true); iD49.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD50= new JLabel("CG8877"); iD50.setBounds(0, 1250, 200, 25); iD50.setBackground(Color.lightGray); hostPane.add(iD50); iD50.setOpaque(true); iD50.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD51= new JLabel("CG8910"); iD51.setBounds(0, 1275, 200, 25); iD51.setBackground(Color.WHITE); hostPane.add(iD51); iD51.setOpaque(true); iD51.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD52= new JLabel("CG32490"); iD52.setBounds(0, 1300, 200, 25); iD52.setBackground(Color.lightGray); hostPane.add(iD52); iD52.setOpaque(true); iD52.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD53= new JLabel("CG10498"); iD53.setBounds(0, 1325, 200, 25); iD53.setBackground(Color.WHITE); hostPane.add(iD53); iD53.setOpaque(true); iD53.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD54= new JLabel("CG6376"); iD54.setBounds(0, 1350, 200, 25); iD54.setBackground(Color.lightGray); hostPane.add(iD54); iD54.setOpaque(true); iD54.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD55= new JLabel("CG6703"); iD55.setBounds(0, 1375, 200, 25); iD55.setBackground(Color.WHITE); hostPane.add(iD55); iD55.setOpaque(true); iD55.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD56= new JLabel("CG45110"); iD56.setBounds(0, 1400, 200, 25); iD56.setBackground(Color.lightGray); hostPane.add(iD56); iD56.setOpaque(true); iD56.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD57= new JLabel("CG6695"); iD57.setBounds(0, 1425, 200, 25); iD57.setBackground(Color.WHITE); hostPane.add(iD57); iD57.setOpaque(true); iD57.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD58= new JLabel("CG43707"); iD58.setBounds(0, 1450, 200, 25); iD58.setBackground(Color.lightGray); hostPane.add(iD58); iD58.setOpaque(true); iD58.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD59= new JLabel("CG2969"); iD59.setBounds(0, 1475, 200, 25); iD59.setBackground(Color.WHITE); hostPane.add(iD59); iD59.setOpaque(true); iD59.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD60= new JLabel("CG17332"); iD60.setBounds(0, 1500, 200, 25); iD60.setBackground(Color.lightGray); hostPane.add(iD60); iD60.setOpaque(true); iD60.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD61= new JLabel("CG1718"); iD61.setBounds(0, 1525, 200, 25); iD61.setBackground(Color.WHITE); hostPane.add(iD61); iD61.setOpaque(true); iD61.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD62= new JLabel("CG18004"); iD62.setBounds(0, 1550, 200, 25); iD62.setBackground(Color.lightGray); hostPane.add(iD62); iD62.setOpaque(true); iD62.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD63= new JLabel("CG3860"); iD63.setBounds(0, 1575, 200, 25); iD63.setBackground(Color.WHITE); hostPane.add(iD63); iD63.setOpaque(true); iD63.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD64= new JLabel("CG31163"); iD64.setBounds(0, 1600, 200, 25); iD64.setBackground(Color.lightGray); hostPane.add(iD64); iD64.setOpaque(true); iD64.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD65= new JLabel("CG42315"); iD65.setBounds(0, 1625, 200, 25); iD65.setBackground(Color.WHITE); hostPane.add(iD65); iD65.setOpaque(true); iD65.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD66= new JLabel("CG31072"); iD66.setBounds(0, 1650, 200, 25); iD66.setBackground(Color.lightGray); hostPane.add(iD66); iD66.setOpaque(true); iD66.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD67= new JLabel("CG12072"); iD67.setBounds(0, 1675, 200, 25); iD67.setBackground(Color.WHITE); hostPane.add(iD67); iD67.setOpaque(true); iD67.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD68= new JLabel("CG2196"); iD68.setBounds(0, 1700, 200, 25); iD68.setBackground(Color.lightGray); hostPane.add(iD68); iD68.setOpaque(true); iD68.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD69= new JLabel("CG6432"); iD69.setBounds(0, 1725, 200, 25); iD69.setBackground(Color.WHITE); hostPane.add(iD69); iD69.setOpaque(true); iD69.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD70= new JLabel("CG8479"); iD70.setBounds(0, 1750, 200, 25); iD70.setBackground(Color.lightGray); hostPane.add(iD70); iD70.setOpaque(true); iD70.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD71= new JLabel("CG6844"); iD71.setBounds(0, 1775, 200, 25); iD71.setBackground(Color.WHITE); hostPane.add(iD71); iD71.setOpaque(true); iD71.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD72= new JLabel("CG40263"); iD72.setBounds(0, 1800, 200, 25); iD72.setBackground(Color.lightGray); hostPane.add(iD72); iD72.setOpaque(true); iD72.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD73= new JLabel("CG15623"); iD73.setBounds(0, 1825, 200, 25); iD73.setBackground(Color.WHITE); hostPane.add(iD73); iD73.setOpaque(true); iD73.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD74= new JLabel("CG34389"); iD74.setBounds(0, 1850, 200, 25); iD74.setBackground(Color.lightGray); hostPane.add(iD74); iD74.setOpaque(true); iD74.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD75= new JLabel("CG1921"); iD75.setBounds(0, 1875, 200, 25); iD75.setBackground(Color.WHITE); hostPane.add(iD75); iD75.setOpaque(true); iD75.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD76= new JLabel("CG7147"); iD76.setBounds(0, 1900, 200, 25); iD76.setBackground(Color.lightGray); hostPane.add(iD76); iD76.setOpaque(true); iD76.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD77= new JLabel("CG12567"); iD77.setBounds(0, 1925, 200, 25); iD77.setBackground(Color.WHITE); hostPane.add(iD77); iD77.setOpaque(true); iD77.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD78= new JLabel("CG32666"); iD78.setBounds(0, 1950, 200, 25); iD78.setBackground(Color.lightGray); hostPane.add(iD78); iD78.setOpaque(true); iD78.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD79= new JLabel("CG42788"); iD79.setBounds(0, 1975, 200, 25); iD79.setBackground(Color.WHITE); hostPane.add(iD79); iD79.setOpaque(true); iD79.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD80= new JLabel("CG17560"); iD80.setBounds(0, 2000, 200, 25); iD80.setBackground(Color.lightGray); hostPane.add(iD80); iD80.setOpaque(true); iD80.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD81= new JLabel("CG14039"); iD81.setBounds(0, 2025, 200, 25); iD81.setBackground(Color.WHITE); hostPane.add(iD81); iD81.setOpaque(true); iD81.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD82= new JLabel("CG4694"); iD82.setBounds(0, 2050, 200, 25); iD82.setBackground(Color.lightGray); hostPane.add(iD82); iD82.setOpaque(true); iD82.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD83= new JLabel("CG3764"); iD83.setBounds(0, 2075, 200, 25); iD83.setBackground(Color.WHITE); hostPane.add(iD83); iD83.setOpaque(true); iD83.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD84= new JLabel("CG7927"); iD84.setBounds(0, 2100, 200, 25); iD84.setBackground(Color.lightGray); hostPane.add(iD84); iD84.setOpaque(true); iD84.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD85= new JLabel("CG3630"); iD85.setBounds(0, 2125, 200, 25); iD85.setBackground(Color.WHITE); hostPane.add(iD85); iD85.setOpaque(true); iD85.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD86= new JLabel("CG18815"); iD86.setBounds(0, 2150, 200, 25); iD86.setBackground(Color.lightGray); hostPane.add(iD86); iD86.setOpaque(true); iD86.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD87= new JLabel("CG6370"); iD87.setBounds(0, 2175, 200, 25); iD87.setBackground(Color.WHITE); hostPane.add(iD87); iD87.setOpaque(true); iD87.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD88= new JLabel("CG6752"); iD88.setBounds(0, 2200, 200, 25); iD88.setBackground(Color.lightGray); hostPane.add(iD88); iD88.setOpaque(true); iD88.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD89= new JLabel("CG3201"); iD89.setBounds(0, 2225, 200, 25); iD89.setBackground(Color.WHITE); hostPane.add(iD89); iD89.setOpaque(true); iD89.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD90= new JLabel("CG3225"); iD90.setBounds(0, 2250, 200, 25); iD90.setBackground(Color.lightGray); hostPane.add(iD90); iD90.setOpaque(true); iD90.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD91= new JLabel("CG3944"); iD91.setBounds(0, 2275, 200, 25); iD91.setBackground(Color.WHITE); hostPane.add(iD91); iD91.setOpaque(true); iD91.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD92= new JLabel("CG2976"); iD92.setBounds(0, 2300, 200, 25); iD92.setBackground(Color.lightGray); hostPane.add(iD92); iD92.setOpaque(true); iD92.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD93= new JLabel("CG17352"); iD93.setBounds(0, 2325, 200, 25); iD93.setBackground(Color.WHITE); hostPane.add(iD93); iD93.setOpaque(true); iD93.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD94= new JLabel("CG31689"); iD94.setBounds(0, 2150, 200, 25); iD94.setBackground(Color.lightGray); hostPane.add(iD94); iD94.setOpaque(true); iD94.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD95= new JLabel("CG40494"); iD95.setBounds(0, 2375, 200, 25); iD95.setBackground(Color.WHITE); hostPane.add(iD95); iD95.setOpaque(true); iD95.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD96= new JLabel("CG32704"); iD96.setBounds(0, 2400, 200, 25); iD96.setBackground(Color.lightGray); hostPane.add(iD96); iD96.setOpaque(true); iD96.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD97= new JLabel("CG1718"); iD97.setBounds(0, 2425, 200, 25); iD97.setBackground(Color.WHITE); hostPane.add(iD97); iD97.setOpaque(true); iD97.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD98= new JLabel("CG1372"); iD98.setBounds(0, 2450, 200, 25); iD98.setBackground(Color.lightGray); hostPane.add(iD98); iD98.setOpaque(true); iD98.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD99= new JLabel("CG9615"); iD99.setBounds(0, 2475, 200, 25); iD99.setBackground(Color.WHITE); hostPane.add(iD99); iD99.setOpaque(true); iD99.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD100= new JLabel("CG8302"); iD100.setBounds(0, 2500, 200, 25); iD100.setBackground(Color.lightGray); hostPane.add(iD100); iD100.setOpaque(true); iD100.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD101= new JLabel("CG15539"); iD101.setBounds(0, 2525, 200, 25); iD101.setBackground(Color.WHITE); hostPane.add(iD101); iD101.setOpaque(true); iD101.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD102= new JLabel("CG1941"); iD102.setBounds(0, 2550, 200, 25); iD102.setBackground(Color.lightGray); hostPane.add(iD102); iD102.setOpaque(true); iD102.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD103= new JLabel("CG32833"); iD103.setBounds(0, 2575, 200, 25); iD103.setBackground(Color.WHITE); hostPane.add(iD103); iD103.setOpaque(true); iD103.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD104= new JLabel("CG43479"); iD104.setBounds(0, 2600, 200, 25); iD104.setBackground(Color.lightGray); hostPane.add(iD104); iD104.setOpaque(true); iD104.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD105= new JLabel("CG13076"); iD105.setBounds(0, 2625, 200, 25); iD105.setBackground(Color.WHITE); hostPane.add(iD105); iD105.setOpaque(true); iD105.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD106= new JLabel("CG32230"); iD106.setBounds(0, 2650, 200, 25); iD106.setBackground(Color.lightGray); hostPane.add(iD106); iD106.setOpaque(true); iD106.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD107= new JLabel("CG5953"); iD107.setBounds(0, 2675, 200, 25); iD107.setBackground(Color.WHITE); hostPane.add(iD107); iD107.setOpaque(true); iD107.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD108= new JLabel("CG17117"); iD108.setBounds(0, 2700, 200, 25); iD108.setBackground(Color.lightGray); hostPane.add(iD108); iD108.setOpaque(true); iD108.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD109= new JLabel("CG10619"); iD109.setBounds(0, 2725, 200, 25); iD109.setBackground(Color.WHITE); hostPane.add(iD109); iD109.setOpaque(true); iD109.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD110= new JLabel("CG1443"); iD110.setBounds(0, 2750, 200, 25); iD110.setBackground(Color.lightGray); hostPane.add(iD110); iD110.setOpaque(true); iD110.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD111= new JLabel("CG1462"); iD111.setBounds(0, 2775, 200, 25); iD111.setBackground(Color.WHITE); hostPane.add(iD111); iD111.setOpaque(true); iD111.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD112= new JLabel("CG1746"); iD112.setBounds(0, 2800, 200, 25); iD112.setBackground(Color.lightGray); hostPane.add(iD112); iD112.setOpaque(true); iD112.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD113= new JLabel("CG7050"); iD113.setBounds(0, 2825, 200, 25); iD113.setBackground(Color.WHITE); hostPane.add(iD113); iD113.setOpaque(true); iD113.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD114= new JLabel("CG4376"); iD114.setBounds(0, 2850, 200, 25); iD114.setBackground(Color.lightGray); hostPane.add(iD114); iD114.setOpaque(true); iD114.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD115= new JLabel("CG33950"); iD115.setBounds(0, 2875, 200, 25); iD115.setBackground(Color.WHITE); hostPane.add(iD115); iD115.setOpaque(true); iD115.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD116= new JLabel("CG43770"); iD116.setBounds(0, 2900, 200, 25); iD116.setBackground(Color.lightGray); hostPane.add(iD116); iD116.setOpaque(true); iD116.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD117= new JLabel("CG6146"); iD117.setBounds(0, 2925, 200, 25); iD117.setBackground(Color.WHITE); hostPane.add(iD117); iD117.setOpaque(true); iD117.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD118= new JLabel("CG9214"); iD118.setBounds(0, 2950, 200, 25); iD118.setBackground(Color.lightGray); hostPane.add(iD118); iD118.setOpaque(true); iD118.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD119= new JLabel("CG7537"); iD119.setBounds(0, 2975, 200, 25); iD119.setBackground(Color.WHITE); hostPane.add(iD119); iD119.setOpaque(true); iD119.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD120= new JLabel("CG7727"); iD120.setBounds(0, 3000, 200, 25); iD120.setBackground(Color.lightGray); hostPane.add(iD120); iD120.setOpaque(true); iD120.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD121= new JLabel("CG3056"); iD121.setBounds(0, 3025, 200, 25); iD121.setBackground(Color.WHITE); hostPane.add(iD121); iD121.setOpaque(true); iD121.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD122= new JLabel("CG34412"); iD122.setBounds(0, 3050, 200, 25); iD122.setBackground(Color.lightGray); hostPane.add(iD122); iD122.setOpaque(true); iD122.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD123= new JLabel("CG15767"); iD123.setBounds(0, 3075, 200, 25); iD123.setBackground(Color.WHITE); hostPane.add(iD123); iD123.setOpaque(true); iD123.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD124= new JLabel("CG42667"); iD124.setBounds(0, 3100, 200, 25); iD124.setBackground(Color.lightGray); hostPane.add(iD124); iD124.setOpaque(true); iD124.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD125= new JLabel("CG4453"); iD125.setBounds(0, 3125, 200, 25); iD125.setBackground(Color.WHITE); hostPane.add(iD125); iD125.setOpaque(true); iD125.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD126= new JLabel("CG15862"); iD126.setBounds(0, 3150, 200, 25); iD126.setBackground(Color.lightGray); hostPane.add(iD126); iD126.setOpaque(true); iD126.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD127= new JLabel("CG11711"); iD127.setBounds(0, 3175, 200, 25); iD127.setBackground(Color.WHITE); hostPane.add(iD127); iD127.setOpaque(true); iD127.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD128= new JLabel("CG6703"); iD128.setBounds(0, 3200, 200, 25); iD128.setBackground(Color.lightGray); hostPane.add(iD128); iD128.setOpaque(true); iD128.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD129= new JLabel("CG6729"); iD129.setBounds(0, 3225, 200, 25); iD129.setBackground(Color.WHITE); hostPane.add(iD129); iD129.setOpaque(true); iD129.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD130= new JLabel("CG3241"); iD130.setBounds(0, 3250, 200, 25); iD130.setBackground(Color.lightGray); hostPane.add(iD130); iD130.setOpaque(true); iD130.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD131= new JLabel("CG10033"); iD131.setBounds(0, 3275, 200, 25); iD131.setBackground(Color.WHITE); hostPane.add(iD131); iD131.setOpaque(true); iD131.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD132= new JLabel("CG15236"); iD132.setBounds(0, 3300, 200, 25); iD132.setBackground(Color.lightGray); hostPane.add(iD132); iD132.setOpaque(true); iD132.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD133= new JLabel("CG11166"); iD133.setBounds(0, 3325, 200, 25); iD133.setBackground(Color.WHITE); hostPane.add(iD133); iD133.setOpaque(true); iD133.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD134= new JLabel("CG8118"); iD134.setBounds(0, 3350, 200, 25); iD134.setBackground(Color.lightGray); hostPane.add(iD134); iD134.setOpaque(true); iD134.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD135= new JLabel("CG11299"); iD135.setBounds(0, 3375, 200, 25); iD135.setBackground(Color.WHITE); hostPane.add(iD135); iD135.setOpaque(true); iD135.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD136= new JLabel("CG11217"); iD136.setBounds(0, 3400, 200, 25); iD136.setBackground(Color.lightGray); hostPane.add(iD136); iD136.setOpaque(true); iD136.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD137= new JLabel("CG33147"); iD137.setBounds(0, 3425, 200, 25); iD137.setBackground(Color.WHITE); hostPane.add(iD137); iD137.setOpaque(true); iD137.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD138= new JLabel("CG8552"); iD138.setBounds(0, 3450, 200, 25); iD138.setBackground(Color.lightGray); hostPane.add(iD138); iD138.setOpaque(true); iD138.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD139= new JLabel("CG3394"); iD139.setBounds(0, 3475, 200, 25); iD139.setBackground(Color.WHITE); hostPane.add(iD139); iD139.setOpaque(true); iD139.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD140= new JLabel("CG6749"); iD140.setBounds(0, 1500, 200, 25); iD140.setBackground(Color.lightGray); hostPane.add(iD140); iD140.setOpaque(true); iD140.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD141= new JLabel("CG6700"); iD141.setBounds(0, 3525, 200, 25); iD141.setBackground(Color.WHITE); hostPane.add(iD141); iD141.setOpaque(true); iD141.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD142= new JLabel("CG2446"); iD142.setBounds(0, 3550, 200, 25); iD142.setBackground(Color.lightGray); hostPane.add(iD142); iD142.setOpaque(true); iD142.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD143= new JLabel("CG34360"); iD143.setBounds(0, 3575, 200, 25); iD143.setBackground(Color.WHITE); hostPane.add(iD143); iD143.setOpaque(true); iD143.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD144= new JLabel("CG7486"); iD144.setBounds(0, 3600, 200, 25); iD144.setBackground(Color.lightGray); hostPane.add(iD144); iD144.setOpaque(true); iD144.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD145= new JLabel("CG6231"); iD145.setBounds(0, 3625, 200, 25); iD145.setBackground(Color.WHITE); hostPane.add(iD145); iD145.setOpaque(true); iD145.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD146= new JLabel("CG8676"); iD146.setBounds(0, 3650, 200, 25); iD146.setBackground(Color.lightGray); hostPane.add(iD146); iD146.setOpaque(true); iD146.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD147= new JLabel("CG9727"); iD147.setBounds(0, 3675, 200, 25); iD147.setBackground(Color.WHITE); hostPane.add(iD147); iD147.setOpaque(true); iD147.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD148= new JLabel("CG4070"); iD148.setBounds(0, 3700, 200, 25); iD148.setBackground(Color.lightGray); hostPane.add(iD148); iD148.setOpaque(true); iD148.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD149= new JLabel("CG17124"); iD149.setBounds(0, 3725, 200, 25); iD149.setBackground(Color.WHITE); hostPane.add(iD149); iD149.setOpaque(true); iD149.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD150= new JLabel("CG30485"); iD150.setBounds(0, 3750, 200, 25); iD150.setBackground(Color.lightGray); hostPane.add(iD150); iD150.setOpaque(true); iD150.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD151= new JLabel("CG7352"); iD151.setBounds(0, 3775, 200, 25); iD151.setBackground(Color.WHITE); hostPane.add(iD151); iD151.setOpaque(true); iD151.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD152= new JLabel("CG6214"); iD152.setBounds(0, 3800, 200, 25); iD152.setBackground(Color.lightGray); hostPane.add(iD152); iD152.setOpaque(true); iD152.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD153= new JLabel("CG12241"); iD153.setBounds(0, 3825, 200, 25); iD153.setBackground(Color.WHITE); hostPane.add(iD153); iD153.setOpaque(true); iD153.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD154= new JLabel("CG9765"); iD154.setBounds(0, 3850, 200, 25); iD154.setBackground(Color.lightGray); hostPane.add(iD154); iD154.setOpaque(true); iD154.setHorizontalAlignment(SwingConstants.CENTER);
		final JLabel iD155= new JLabel("CG8671"); iD155.setBounds(0, 3875, 200, 25); iD155.setBackground(Color.WHITE); hostPane.add(iD155); iD155.setOpaque(true); iD155.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
			
		public static void hSym(JPanel hostPane){
			final JLabel iD1= new JLabel("Mmp2");iD1.setBounds(0, 25, 230, 25); iD1.setBackground(Color.WHITE); hostPane.add(iD1); iD1.setOpaque(true); iD1.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD2= new JLabel("Mmp2");iD2.setBounds(0, 50, 230, 25); iD2.setBackground(Color.lightGray); hostPane.add(iD2); iD2.setOpaque(true); iD2.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD3= new JLabel("spi");iD3.setBounds(0, 75, 230, 25); iD3.setBackground(Color.WHITE); hostPane.add(iD3); iD3.setOpaque(true); iD3.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD4= new JLabel("spi");iD4.setBounds(0, 100, 230, 25); iD4.setBackground(Color.lightGray); hostPane.add(iD4); iD4.setOpaque(true); iD4.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD5= new JLabel("spi");iD5.setBounds(0, 125, 230, 25); iD5.setBackground(Color.WHITE); hostPane.add(iD5); iD5.setOpaque(true); iD5.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD6= new JLabel("spi");iD6.setBounds(0, 150, 230, 25); iD6.setBackground(Color.lightGray); hostPane.add(iD6); iD6.setOpaque(true); iD6.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD7= new JLabel("HnRNP-K");iD7.setBounds(0, 175, 230, 25); iD7.setBackground(Color.WHITE); hostPane.add(iD7); iD7.setOpaque(true); iD7.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD8= new JLabel("E2f1"); iD8.setBounds(0, 200, 230, 25); iD8.setBackground(Color.lightGray); hostPane.add(iD8); iD8.setOpaque(true); iD8.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD9= new JLabel("Gmap");iD9.setBounds(0, 225, 230, 25); iD9.setBackground(Color.WHITE); hostPane.add(iD9); iD9.setOpaque(true); iD9.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD10= new JLabel ("CG7033");iD10.setBounds(0, 250, 230, 25); iD10.setBackground(Color.lightGray); hostPane.add(iD10); iD10.setOpaque(true); iD10.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD11= new JLabel  ("CG32085");iD11.setBounds(0, 275, 230, 25); iD11.setBackground(Color.WHITE); hostPane.add(iD11); iD11.setOpaque(true); iD11.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD12= new JLabel  ("jigr1"); iD12.setBounds(0, 300, 230, 25); iD12.setBackground(Color.lightGray); hostPane.add(iD12); iD12.setOpaque(true); iD12.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD13= new JLabel  ("CG42524");iD13.setBounds(0, 325, 230, 25); iD13.setBackground(Color.WHITE); hostPane.add(iD13); iD13.setOpaque(true); iD13.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD14= new JLabel  ("SREBP"); iD14.setBounds(0, 350, 230, 25); iD14.setBackground(Color.lightGray); hostPane.add(iD14); iD14.setOpaque(true); iD14.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD15= new JLabel  ("Oda"); iD15.setBounds(0, 375, 230, 25); iD15.setBackground(Color.WHITE); hostPane.add(iD15); iD15.setOpaque(true); iD15.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD16= new JLabel  ("Gmap");iD16.setBounds(0, 400, 230, 25); iD16.setBackground(Color.lightGray); hostPane.add(iD16); iD16.setOpaque(true); iD16.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD17= new JLabel  ("Octbeta2R"); iD17.setBounds(0, 425, 230, 25); iD17.setBackground(Color.WHITE); hostPane.add(iD17); iD17.setOpaque(true); iD17.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD18= new JLabel  ("Oda"); iD18.setBounds(0, 450, 230, 25); iD18.setBackground(Color.lightGray); hostPane.add(iD18); iD18.setOpaque(true); iD18.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD19= new JLabel  ("grp"); iD19.setBounds(0, 475, 230, 25); iD19.setBackground(Color.WHITE); hostPane.add(iD19); iD19.setOpaque(true); iD19.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD20= new JLabel  ("jigr1"); iD20.setBounds(0, 500, 230, 25); iD20.setBackground(Color.lightGray); hostPane.add(iD20); iD20.setOpaque(true); iD20.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD21= new JLabel  ("CG321150");iD21.setBounds(0, 525, 230, 25); iD21.setBackground(Color.WHITE); hostPane.add(iD21); iD21.setOpaque(true); iD21.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD22= new JLabel  ("bru3");iD22.setBounds(0, 550, 230, 25); iD22.setBackground(Color.lightGray); hostPane.add(iD22); iD22.setOpaque(true); iD22.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD23= new JLabel  ("Gmap"); iD23.setBounds(0, 575, 230, 25); iD23.setBackground(Color.WHITE); hostPane.add(iD23); iD23.setOpaque(true); iD23.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD24= new JLabel  ("grp"); iD24.setBounds(0, 600, 230, 25); iD24.setBackground(Color.lightGray); hostPane.add(iD24); iD24.setOpaque(true); iD24.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD25= new JLabel  ("grp"); iD25.setBounds(0, 625, 230, 25); iD25.setBackground(Color.WHITE); hostPane.add(iD25); iD25.setOpaque(true); iD25.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD26= new JLabel  ("grp"); iD26.setBounds(0, 650, 230, 25); iD26.setBackground(Color.lightGray); hostPane.add(iD26); iD26.setOpaque(true); iD26.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD27= new JLabel  ("RpS23");iD27.setBounds(0, 675, 230, 25); iD27.setBackground(Color.WHITE); hostPane.add(iD27); iD27.setOpaque(true); iD27.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD28= new JLabel  ("rhea");iD28.setBounds(0, 700, 230, 25); iD28.setBackground(Color.lightGray); hostPane.add(iD28); iD28.setOpaque(true); iD28.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD29= new JLabel  ("DIP-theta"); iD29.setBounds(0, 725, 230, 25); iD29.setBackground(Color.WHITE); hostPane.add(iD29); iD29.setOpaque(true); iD29.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD30= new JLabel  ("DIP-theta"); iD30.setBounds(0, 750, 230, 25); iD30.setBackground(Color.lightGray); hostPane.add(iD30); iD30.setOpaque(true); iD30.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD31= new JLabel  ("DIP-theta");iD31.setBounds(0, 775, 230, 25); iD31.setBackground(Color.WHITE); hostPane.add(iD31); iD31.setOpaque(true); iD31.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD32= new JLabel  ("DIP-theta"); iD32.setBounds(0, 800, 230, 25); iD32.setBackground(Color.lightGray); hostPane.add(iD32); iD32.setOpaque(true); iD32.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD33= new JLabel  ("DIP-theta"); iD33.setBounds(0, 825, 230, 25); iD33.setBackground(Color.WHITE); hostPane.add(iD33); iD33.setOpaque(true); iD33.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD34= new JLabel  ("DIP-theta"); iD34.setBounds(0, 850, 230, 25); iD34.setBackground(Color.lightGray); hostPane.add(iD34); iD34.setOpaque(true); iD34.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD35= new JLabel  ("Nig2"); iD35.setBounds(0, 875, 230, 25); iD35.setBackground(Color.WHITE); hostPane.add(iD35); iD35.setOpaque(true); iD35.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD36= new JLabel  ("kis");iD36.setBounds(0, 900, 230, 25); iD36.setBackground(Color.lightGray); hostPane.add(iD36); iD36.setOpaque(true); iD36.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD37= new JLabel  ("ade2"); iD37.setBounds(0, 925, 230, 25); iD37.setBackground(Color.WHITE); hostPane.add(iD37); iD37.setOpaque(true); iD37.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD38= new JLabel  ("bun"); iD38.setBounds(0, 950, 230, 25); iD38.setBackground(Color.lightGray); hostPane.add(iD38); iD38.setOpaque(true); iD38.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD39= new JLabel  ("Tomosyn"); iD39.setBounds(0, 975, 230, 25); iD39.setBackground(Color.WHITE); hostPane.add(iD39); iD39.setOpaque(true); iD39.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD40= new JLabel  ("rad"); iD40.setBounds(0, 1000, 230, 25); iD40.setBackground(Color.lightGray); hostPane.add(iD40); iD40.setOpaque(true); iD40.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD41= new JLabel  ("Grip84"); iD41.setBounds(0, 1025, 230, 25); iD41.setBackground(Color.WHITE); hostPane.add(iD41); iD41.setOpaque(true); iD41.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD42= new JLabel  ("Grip84"); iD42.setBounds(0, 1050, 230, 25); iD42.setBackground(Color.lightGray); hostPane.add(iD42); iD42.setOpaque(true); iD42.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD43= new JLabel  ("CG3777");iD43.setBounds(0, 1075, 230, 25); iD43.setBackground(Color.WHITE); hostPane.add(iD43); iD43.setOpaque(true); iD43.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD44= new JLabel  ("CG42666"); iD44.setBounds(0, 1100, 230, 25); iD44.setBackground(Color.lightGray); hostPane.add(iD44); iD44.setOpaque(true); iD44.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD45= new JLabel  ("CG3626"); iD45.setBounds(0, 1125, 230, 25); iD45.setBackground(Color.WHITE); hostPane.add(iD45); iD45.setOpaque(true); iD45.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD46= new JLabel  ("CG3626");iD46.setBounds(0, 1150, 230, 25); iD46.setBackground(Color.lightGray); hostPane.add(iD46); iD46.setOpaque(true); iD46.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD47= new JLabel  ("CG3626");iD47.setBounds(0, 1175, 230, 25); iD47.setBackground(Color.WHITE); hostPane.add(iD47); iD47.setOpaque(true); iD47.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD48= new JLabel  ("mmd"); iD48.setBounds(0, 1200, 230, 25); iD48.setBackground(Color.lightGray); hostPane.add(iD48); iD48.setOpaque(true); iD48.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD49= new JLabel  ("Cyp4e2");iD49.setBounds(0, 1225, 230, 25); iD49.setBackground(Color.WHITE); hostPane.add(iD49); iD49.setOpaque(true); iD49.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD50= new JLabel  ("Prp8");iD50.setBounds(0, 1250, 230, 25); iD50.setBackground(Color.lightGray); hostPane.add(iD50); iD50.setOpaque(true); iD50.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD51= new JLabel  ("CG8910");iD51.setBounds(0, 1275, 230, 25); iD51.setBackground(Color.WHITE); hostPane.add(iD51); iD51.setOpaque(true); iD51.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD52= new JLabel  ("cpx"); iD52.setBounds(0, 1300, 230, 25); iD52.setBackground(Color.lightGray); hostPane.add(iD52); iD52.setOpaque(true); iD52.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD53= new JLabel  ("Cdk2"); iD53.setBounds(0, 1325, 230, 25); iD53.setBackground(Color.WHITE); hostPane.add(iD53); iD53.setOpaque(true); iD53.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD54= new JLabel  ("E2f1");iD54.setBounds(0, 1350, 230, 25); iD54.setBackground(Color.lightGray); hostPane.add(iD54); iD54.setOpaque(true); iD54.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD55= new JLabel  ("CASK");iD55.setBounds(0, 1375, 230, 25); iD55.setBackground(Color.WHITE); hostPane.add(iD55); iD55.setOpaque(true); iD55.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD56= new JLabel  ("tau"); iD56.setBounds(0, 1400, 230, 25); iD56.setBackground(Color.lightGray); hostPane.add(iD56); iD56.setOpaque(true); iD56.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD57= new JLabel  ("CG6695");iD57.setBounds(0, 1425, 230, 25); iD57.setBackground(Color.WHITE); hostPane.add(iD57); iD57.setOpaque(true); iD57.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD58= new JLabel  ("CG43707"); iD58.setBounds(0, 1450, 230, 25); iD58.setBackground(Color.lightGray); hostPane.add(iD58); iD58.setOpaque(true); iD58.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD59= new JLabel  ("Atet"); iD59.setBounds(0, 1475, 230, 25); iD59.setBackground(Color.WHITE); hostPane.add(iD59); iD59.setOpaque(true); iD59.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD60= new JLabel  ("VhaSFD"); iD60.setBounds(0, 1500, 230, 25); iD60.setBackground(Color.lightGray); hostPane.add(iD60); iD60.setOpaque(true); iD60.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD61= new JLabel  ("CG1718");iD61.setBounds(0, 1525, 230, 25); iD61.setBackground(Color.WHITE); hostPane.add(iD61); iD61.setOpaque(true); iD61.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD62= new JLabel  ("CG18004"); iD62.setBounds(0, 1550, 230, 25); iD62.setBackground(Color.lightGray); hostPane.add(iD62); iD62.setOpaque(true); iD62.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD63= new JLabel  ("CG3860");iD63.setBounds(0, 1575, 230, 25); iD63.setBackground(Color.WHITE); hostPane.add(iD63); iD63.setOpaque(true); iD63.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD64= new JLabel  ("SKIP");iD64.setBounds(0, 1600, 230, 25); iD64.setBackground(Color.lightGray); hostPane.add(iD64); iD64.setOpaque(true); iD64.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD65= new JLabel  ("Ir93a");iD65.setBounds(0, 1625, 230, 25); iD65.setBackground(Color.WHITE); hostPane.add(iD65); iD65.setOpaque(true); iD65.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD66= new JLabel  ("Lerp"); iD66.setBounds(0, 1650, 230, 25); iD66.setBackground(Color.lightGray); hostPane.add(iD66); iD66.setOpaque(true); iD66.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD67= new JLabel  ("wts"); iD67.setBounds(0, 1675, 230, 25); iD67.setBackground(Color.WHITE); hostPane.add(iD67); iD67.setOpaque(true); iD67.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD68= new JLabel  ("salt");iD68.setBounds(0, 1700, 230, 25); iD68.setBackground(Color.lightGray); hostPane.add(iD68); iD68.setOpaque(true); iD68.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD69= new JLabel  ("CG6432");iD69.setBounds(0, 1725, 230, 25); iD69.setBackground(Color.WHITE); hostPane.add(iD69); iD69.setOpaque(true); iD69.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD70= new JLabel  ("Opa1");iD70.setBounds(0, 1750, 230, 25); iD70.setBackground(Color.lightGray); hostPane.add(iD70); iD70.setOpaque(true); iD70.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD71= new JLabel  ("nAChRalpha2");iD71.setBounds(0, 1775, 230, 25); iD71.setBackground(Color.WHITE); hostPane.add(iD71); iD71.setOpaque(true); iD71.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD72= new JLabel  ("MFS17"); iD72.setBounds(0, 1800, 230, 25); iD72.setBackground(Color.lightGray); hostPane.add(iD72); iD72.setOpaque(true); iD72.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD73= new JLabel  ("c-cup"); iD73.setBounds(0, 1825, 230, 25); iD73.setBackground(Color.WHITE); hostPane.add(iD73); iD73.setOpaque(true); iD73.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD74= new JLabel  ("cv-c"); iD74.setBounds(0, 1850, 230, 25); iD74.setBackground(Color.lightGray); hostPane.add(iD74); iD74.setOpaque(true); iD74.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD75= new JLabel  ("sty"); iD75.setBounds(0, 1875, 230, 25); iD75.setBackground(Color.WHITE); hostPane.add(iD75); iD75.setOpaque(true); iD75.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD76= new JLabel  ("kuz");iD76.setBounds(0, 1900, 230, 25); iD76.setBackground(Color.lightGray); hostPane.add(iD76); iD76.setOpaque(true); iD76.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD77= new JLabel  ("CG12567");iD77.setBounds(0, 1925, 230, 25); iD77.setBackground(Color.WHITE); hostPane.add(iD77); iD77.setOpaque(true); iD77.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD78= new JLabel  ("Drak"); iD78.setBounds(0, 1950, 230, 25); iD78.setBackground(Color.lightGray); hostPane.add(iD78); iD78.setOpaque(true); iD78.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD79= new JLabel  ("CG42788");iD79.setBounds(0, 1975, 230, 25); iD79.setBackground(Color.WHITE); hostPane.add(iD79); iD79.setOpaque(true); iD79.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD80= new JLabel  ("CG17560");iD80.setBounds(0, 2000, 230, 25); iD80.setBackground(Color.lightGray); hostPane.add(iD80); iD80.setOpaque(true); iD80.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD81= new JLabel  ("qtc");iD81.setBounds(0, 2025, 230, 25); iD81.setBackground(Color.WHITE); hostPane.add(iD81); iD81.setOpaque(true); iD81.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD82= new JLabel  ("her");iD82.setBounds(0, 2050, 230, 25); iD82.setBackground(Color.lightGray); hostPane.add(iD82); iD82.setOpaque(true); iD82.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD83= new JLabel  ("CG3764");iD83.setBounds(0, 2075, 230, 25); iD83.setBackground(Color.WHITE); hostPane.add(iD83); iD83.setOpaque(true); iD83.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD84= new JLabel  ("CG7927"); iD84.setBounds(0, 2100, 230, 25); iD84.setBackground(Color.lightGray); hostPane.add(iD84); iD84.setOpaque(true); iD84.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD85= new JLabel  ("CG3630");iD85.setBounds(0, 2125, 230, 25); iD85.setBackground(Color.WHITE); hostPane.add(iD85); iD85.setOpaque(true); iD85.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD86= new JLabel  ("CG18815"); iD86.setBounds(0, 2150, 230, 25); iD86.setBackground(Color.lightGray); hostPane.add(iD86); iD86.setOpaque(true); iD86.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD87= new JLabel  ("Ostdelta"); iD87.setBounds(0, 2175, 230, 25); iD87.setBackground(Color.WHITE); hostPane.add(iD87); iD87.setOpaque(true); iD87.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD88= new JLabel  ("CG6752");iD88.setBounds(0, 2200, 230, 25); iD88.setBackground(Color.lightGray); hostPane.add(iD88); iD88.setOpaque(true); iD88.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD89= new JLabel  ("Mlc-c");iD89.setBounds(0, 2225, 230, 25); iD89.setBackground(Color.WHITE); hostPane.add(iD89); iD89.setOpaque(true); iD89.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD90= new JLabel  ("CG3225");iD90.setBounds(0, 2250, 230, 25); iD90.setBackground(Color.lightGray); hostPane.add(iD90); iD90.setOpaque(true); iD90.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD91= new JLabel  ("ND-23");iD91.setBounds(0, 2275, 230, 25); iD91.setBackground(Color.WHITE); hostPane.add(iD91); iD91.setOpaque(true); iD91.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD92= new JLabel  ("Fnta"); iD92.setBounds(0, 2300, 230, 25); iD92.setBackground(Color.lightGray); hostPane.add(iD92); iD92.setOpaque(true); iD92.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD93= new JLabel  ("CG17352"); iD93.setBounds(0, 2325, 230, 25); iD93.setBackground(Color.WHITE); hostPane.add(iD93); iD93.setOpaque(true); iD93.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD94= new JLabel  ("CG31689"); iD94.setBounds(0, 2350, 230, 25); iD94.setBackground(Color.lightGray); hostPane.add(iD94); iD94.setOpaque(true); iD94.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD95= new JLabel  ("RhoGAP1A");iD95.setBounds(0, 2375, 230, 25); iD95.setBackground(Color.WHITE); hostPane.add(iD95); iD95.setOpaque(true); iD95.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD96= new JLabel  ("Ir8a"); iD96.setBounds(0, 2400, 230, 25); iD96.setBackground(Color.lightGray); hostPane.add(iD96); iD96.setOpaque(true); iD96.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD97= new JLabel  ("CG1718"); iD97.setBounds(0, 2425, 230, 25); iD97.setBackground(Color.WHITE); hostPane.add(iD97); iD97.setOpaque(true); iD97.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD98= new JLabel  ("yl"); iD98.setBounds(0, 2450, 230, 25); iD98.setBackground(Color.lightGray); hostPane.add(iD98); iD98.setOpaque(true); iD98.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD99= new JLabel  ("tex"); iD99.setBounds(0, 2475, 230, 25); iD99.setBackground(Color.WHITE); hostPane.add(iD99); iD99.setOpaque(true); iD99.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD100= new JLabel ("Cyp4aa1");iD100.setBounds(0, 2500, 230, 25); iD100.setBackground(Color.lightGray); hostPane.add(iD100); iD100.setOpaque(true); iD100.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD101= new JLabel("CG15539"); iD101.setBounds(0, 2525, 230, 25); iD101.setBackground(Color.WHITE); hostPane.add(iD101); iD101.setOpaque(true); iD101.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD102= new JLabel("CG1941");iD102.setBounds(0, 2550, 230, 25); iD102.setBackground(Color.lightGray); hostPane.add(iD102); iD102.setOpaque(true); iD102.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD103= new JLabel("CG32833"); iD103.setBounds(0, 2575, 230, 25); iD103.setBackground(Color.WHITE); hostPane.add(iD103); iD103.setOpaque(true); iD103.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD104= new JLabel("nwk"); iD104.setBounds(0, 2600, 230, 25); iD104.setBackground(Color.lightGray); hostPane.add(iD104); iD104.setOpaque(true); iD104.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD105= new JLabel("Notum"); iD105.setBounds(0, 2625, 230, 25); iD105.setBackground(Color.WHITE); hostPane.add(iD105); iD105.setOpaque(true); iD105.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD106= new JLabel("ND-MLRQ"); iD106.setBounds(0, 2650, 230, 25); iD106.setBackground(Color.lightGray); hostPane.add(iD106); iD106.setOpaque(true); iD106.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD107= new JLabel("CG5953");iD107.setBounds(0, 2675, 230, 25); iD107.setBackground(Color.WHITE); hostPane.add(iD107); iD107.setOpaque(true); iD107.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD108= new JLabel("hth"); iD108.setBounds(0, 2700, 230, 25); iD108.setBackground(Color.lightGray); hostPane.add(iD108); iD108.setOpaque(true); iD108.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD109= new JLabel("tup"); iD109.setBounds(0, 2725, 230, 25); iD109.setBackground(Color.WHITE); hostPane.add(iD109); iD109.setOpaque(true); iD109.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD110= new JLabel("wat");iD110.setBounds(0, 2750, 230, 25); iD110.setBackground(Color.lightGray); hostPane.add(iD110); iD110.setOpaque(true); iD110.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD111= new JLabel("Alp4");iD111.setBounds(0, 2775, 230, 25); iD111.setBackground(Color.WHITE); hostPane.add(iD111); iD111.setOpaque(true); iD111.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD112= new JLabel("ATPsynC");iD112.setBounds(0, 2800, 230, 25); iD112.setBackground(Color.lightGray); hostPane.add(iD112); iD112.setOpaque(true); iD112.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD113= new JLabel("Nrx-1");iD113.setBounds(0, 2825, 230, 25); iD113.setBackground(Color.WHITE); hostPane.add(iD113); iD113.setOpaque(true); iD113.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD114= new JLabel("Actn");iD114.setBounds(0, 2850, 230, 25); iD114.setBackground(Color.lightGray); hostPane.add(iD114); iD114.setOpaque(true); iD114.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD115= new JLabel("trol"); iD115.setBounds(0, 2875, 230, 25); iD115.setBackground(Color.WHITE); hostPane.add(iD115); iD115.setOpaque(true); iD115.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD116= new JLabel("Sxl"); iD116.setBounds(0, 2900, 230, 25); iD116.setBackground(Color.lightGray); hostPane.add(iD116); iD116.setOpaque(true); iD116.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD117= new JLabel("Top1");iD117.setBounds(0, 2925, 230, 25); iD117.setBackground(Color.WHITE); hostPane.add(iD117); iD117.setOpaque(true); iD117.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD118= new JLabel("Tob");iD118.setBounds(0, 2950, 230, 25); iD118.setBackground(Color.lightGray); hostPane.add(iD118); iD118.setOpaque(true); iD118.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD119= new JLabel("mx5");iD119.setBounds(0, 2975, 230, 25); iD119.setBackground(Color.WHITE); hostPane.add(iD119); iD119.setOpaque(true); iD119.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD120= new JLabel("Appl");iD120.setBounds(0, 3000, 230, 25); iD120.setBackground(Color.lightGray); hostPane.add(iD120); iD120.setOpaque(true); iD120.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD121= new JLabel("ssx");iD121.setBounds(0, 3025, 230, 25); iD121.setBackground(Color.WHITE); hostPane.add(iD121); iD121.setOpaque(true); iD121.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD122= new JLabel("Tlk"); iD122.setBounds(0, 3050, 230, 25); iD122.setBackground(Color.lightGray); hostPane.add(iD122); iD122.setOpaque(true); iD122.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD123= new JLabel("CG15767"); iD123.setBounds(0, 3075, 230, 25); iD123.setBackground(Color.WHITE); hostPane.add(iD123); iD123.setOpaque(true); iD123.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD124= new JLabel("rdgA"); iD124.setBounds(0, 3100, 230, 25); iD124.setBackground(Color.lightGray); hostPane.add(iD124); iD124.setOpaque(true); iD124.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD125= new JLabel("Nup153");iD125.setBounds(0, 3125, 230, 25); iD125.setBackground(Color.WHITE); hostPane.add(iD125); iD125.setOpaque(true); iD125.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD126= new JLabel("Pka-R2"); iD126.setBounds(0, 3150, 230, 25); iD126.setBackground(Color.lightGray); hostPane.add(iD126); iD126.setOpaque(true); iD126.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD127= new JLabel("Mob2"); iD127.setBounds(0, 3175, 230, 25); iD127.setBackground(Color.WHITE); hostPane.add(iD127); iD127.setOpaque(true); iD127.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD128= new JLabel("CASK");iD128.setBounds(0, 3200, 230, 25); iD128.setBackground(Color.lightGray); hostPane.add(iD128); iD128.setOpaque(true); iD128.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD129= new JLabel("CG6729");iD129.setBounds(0, 3225, 230, 25); iD129.setBackground(Color.WHITE); hostPane.add(iD129); iD129.setOpaque(true); iD129.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD130= new JLabel("msl-2");iD130.setBounds(0, 3250, 230, 25); iD130.setBackground(Color.lightGray); hostPane.add(iD130); iD130.setOpaque(true); iD130.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD131= new JLabel("for"); iD131.setBounds(0, 3275, 230, 25); iD131.setBackground(Color.WHITE); hostPane.add(iD131); iD131.setOpaque(true); iD131.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD132= new JLabel("CG15236"); iD132.setBounds(0, 3300, 230, 25); iD132.setBackground(Color.lightGray); hostPane.add(iD132); iD132.setOpaque(true); iD132.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD133= new JLabel("Eaf"); iD133.setBounds(0, 3325, 230, 25); iD133.setBackground(Color.WHITE); hostPane.add(iD133); iD133.setOpaque(true); iD133.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD134= new JLabel("mam");iD134.setBounds(0, 3350, 230, 25); iD134.setBackground(Color.lightGray); hostPane.add(iD134); iD134.setOpaque(true); iD134.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD135= new JLabel("Sesn"); iD135.setBounds(0, 3375, 230, 25); iD135.setBackground(Color.WHITE); hostPane.add(iD135); iD135.setOpaque(true); iD135.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD136= new JLabel("CanB2"); iD136.setBounds(0, 3400, 230, 25); iD136.setBackground(Color.lightGray); hostPane.add(iD136); iD136.setOpaque(true); iD136.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD137= new JLabel("Hs3st-A"); iD137.setBounds(0, 3425, 230, 25); iD137.setBackground(Color.WHITE); hostPane.add(iD137); iD137.setOpaque(true); iD137.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD138= new JLabel("PAPLA1");iD138.setBounds(0, 3450, 230, 25); iD138.setBackground(Color.lightGray); hostPane.add(iD138); iD138.setOpaque(true); iD138.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD139= new JLabel("CG3394");iD139.setBounds(0, 3475, 230, 25); iD139.setBackground(Color.WHITE); hostPane.add(iD139); iD139.setOpaque(true); iD139.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD140= new JLabel("CG6749");iD140.setBounds(0, 3500, 230, 25); iD140.setBackground(Color.lightGray); hostPane.add(iD140); iD140.setOpaque(true); iD140.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD141= new JLabel("CG6700");iD141.setBounds(0, 3525, 230, 25); iD141.setBackground(Color.WHITE); hostPane.add(iD141); iD141.setOpaque(true); iD141.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD142= new JLabel("Amun");iD142.setBounds(0, 3550, 230, 25); iD142.setBackground(Color.lightGray); hostPane.add(iD142); iD142.setOpaque(true); iD142.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD143= new JLabel("Glut4EF"); iD143.setBounds(0, 3575, 230, 25); iD143.setBackground(Color.WHITE); hostPane.add(iD143); iD143.setOpaque(true); iD143.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD144= new JLabel("Dredd");iD144.setBounds(0, 3600, 230, 25); iD144.setBackground(Color.lightGray); hostPane.add(iD144); iD144.setOpaque(true); iD144.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD145= new JLabel("CG6231");iD145.setBounds(0, 3625, 230, 25); iD145.setBackground(Color.WHITE); hostPane.add(iD145); iD145.setOpaque(true); iD145.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD146= new JLabel("Hr39");iD146.setBounds(0, 3650, 230, 25); iD146.setBackground(Color.lightGray); hostPane.add(iD146); iD146.setOpaque(true); iD146.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD147= new JLabel("CG9727");iD147.setBounds(0, 3675, 230, 25); iD147.setBackground(Color.WHITE); hostPane.add(iD147); iD147.setOpaque(true); iD147.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD148= new JLabel("Tis11");iD148.setBounds(0, 3700, 230, 25); iD148.setBackground(Color.lightGray); hostPane.add(iD148); iD148.setOpaque(true); iD148.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD149= new JLabel("CG17124"); iD149.setBounds(0, 3725, 230, 25); iD149.setBackground(Color.WHITE); hostPane.add(iD149); iD149.setOpaque(true); iD149.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD150= new JLabel("CG30485"); iD150.setBounds(0, 3750, 230, 25); iD150.setBackground(Color.lightGray); hostPane.add(iD150); iD150.setOpaque(true); iD150.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD151= new JLabel("CG7352");iD151.setBounds(0, 3775, 230, 25); iD151.setBackground(Color.WHITE); hostPane.add(iD151); iD151.setOpaque(true); iD151.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD152= new JLabel("MRP");iD152.setBounds(0, 3800, 230, 25); iD152.setBackground(Color.lightGray); hostPane.add(iD152); iD152.setOpaque(true); iD152.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD153= new JLabel("CG12241"); iD153.setBounds(0, 3825, 230, 25); iD153.setBackground(Color.WHITE); hostPane.add(iD153); iD153.setOpaque(true); iD153.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD154= new JLabel("tacc");iD154.setBounds(0, 3850, 230, 25); iD154.setBackground(Color.lightGray); hostPane.add(iD154); iD154.setOpaque(true); iD154.setHorizontalAlignment(SwingConstants.CENTER);
			final JLabel iD155= new JLabel("CG8671");iD155.setBounds(0, 3875, 230, 25); iD155.setBackground(Color.WHITE); hostPane.add(iD155); iD155.setOpaque(true); iD155.setHorizontalAlignment(SwingConstants.CENTER);
			}

		public static String[] litmiR(boolean[][] selected){
			String rStrings[] = new String[155];
			if(selected[0][0]==true){rStrings[0] = getmiR(0);}
			if(selected[0][1]==true){rStrings[1] = getmiR(1);}
			if(selected[0][2]==true){rStrings[2] = getmiR(2);}
			if(selected[0][3]==true){rStrings[3] = getmiR(3);}
			if(selected[0][4]==true){rStrings[4] = getmiR(4);}
			if(selected[0][5]==true){rStrings[5] = getmiR(5);}
			if(selected[0][6]==true){rStrings[6] = getmiR(6);}
			if(selected[0][7]==true){rStrings[7] = getmiR(7);}
			if(selected[0][8]==true){rStrings[8] = getmiR(8);}
			if(selected[0][9]==true){rStrings[9] = getmiR(9);}
			if(selected[0][10]==true){rStrings[10] = getmiR(10);}
			if(selected[0][11]==true){rStrings[11] = getmiR(11);}
			if(selected[0][12]==true){rStrings[12] = getmiR(12);}
			if(selected[0][13]==true){rStrings[13] = getmiR(13);}
			if(selected[0][14]==true){rStrings[14] = getmiR(14);}
			if(selected[0][15]==true){rStrings[15] = getmiR(15);}
			if(selected[0][16]==true){rStrings[16] = getmiR(16);}
			if(selected[0][17]==true){rStrings[17] = getmiR(17);}
			if(selected[0][18]==true){rStrings[18] = getmiR(18);}
			if(selected[0][19]==true){rStrings[19] = getmiR(19);}
			if(selected[0][20]==true){rStrings[20] = getmiR(20);}
			if(selected[0][21]==true){rStrings[21] = getmiR(21);}
			if(selected[0][22]==true){rStrings[22] = getmiR(22);}
			if(selected[0][23]==true){rStrings[23] = getmiR(23);}
			if(selected[0][24]==true){rStrings[24] = getmiR(24);}
			if(selected[0][25]==true){rStrings[25] = getmiR(25);}
			if(selected[0][26]==true){rStrings[26] = getmiR(26);}
			if(selected[0][27]==true){rStrings[27] = getmiR(27);}
			if(selected[0][28]==true){rStrings[28] = getmiR(28);}
			if(selected[0][29]==true){rStrings[29] = getmiR(29);}
			if(selected[0][30]==true){rStrings[30] = getmiR(30);}
			if(selected[0][31]==true){rStrings[31] = getmiR(31);}
			if(selected[0][32]==true){rStrings[32] = getmiR(32);}
			if(selected[0][33]==true){rStrings[33] = getmiR(33);}
			if(selected[0][34]==true){rStrings[34] = getmiR(34);}
			if(selected[0][35]==true){rStrings[35] = getmiR(35);}
			if(selected[0][36]==true){rStrings[36] = getmiR(36);}
			if(selected[0][37]==true){rStrings[37] = getmiR(37);}
			if(selected[0][38]==true){rStrings[38] = getmiR(38);}
			if(selected[0][39]==true){rStrings[39] = getmiR(39);}
			if(selected[0][40]==true){rStrings[40] = getmiR(40);}
			if(selected[0][41]==true){rStrings[41] = getmiR(41);}
			if(selected[0][42]==true){rStrings[42] = getmiR(42);}
			if(selected[0][43]==true){rStrings[43] = getmiR(43);}
			if(selected[0][44]==true){rStrings[44] = getmiR(44);}
			if(selected[0][45]==true){rStrings[45] = getmiR(45);}
			if(selected[0][46]==true){rStrings[46] = getmiR(46);}
			if(selected[0][47]==true){rStrings[47] = getmiR(47);}
			if(selected[0][48]==true){rStrings[48] = getmiR(48);}
			if(selected[0][49]==true){rStrings[49] = getmiR(49);}
			if(selected[0][50]==true){rStrings[50] = getmiR(50);}
			if(selected[0][51]==true){rStrings[51] = getmiR(51);}
			if(selected[0][52]==true){rStrings[52] = getmiR(52);}
			if(selected[0][53]==true){rStrings[53] = getmiR(53);}
			if(selected[0][54]==true){rStrings[54] = getmiR(54);}
			if(selected[0][55]==true){rStrings[55] = getmiR(55);}
			if(selected[0][56]==true){rStrings[56] = getmiR(56);}
			if(selected[0][57]==true){rStrings[57] = getmiR(57);}
			if(selected[0][58]==true){rStrings[58] = getmiR(58);}
			if(selected[0][59]==true){rStrings[59] = getmiR(59);}
			if(selected[0][60]==true){rStrings[60] = getmiR(60);}
			if(selected[0][61]==true){rStrings[61] = getmiR(61);}
			if(selected[0][62]==true){rStrings[62] = getmiR(62);}
			if(selected[0][63]==true){rStrings[63] = getmiR(63);}
			if(selected[0][64]==true){rStrings[64] = getmiR(64);}
			if(selected[0][65]==true){rStrings[65] = getmiR(65);}
			if(selected[0][66]==true){rStrings[66] = getmiR(66);}
			if(selected[0][67]==true){rStrings[67] = getmiR(67);}
			if(selected[0][68]==true){rStrings[68] = getmiR(68);}
			if(selected[0][69]==true){rStrings[69] = getmiR(69);}
			if(selected[0][70]==true){rStrings[70] = getmiR(70);}
			if(selected[0][71]==true){rStrings[71] = getmiR(71);}
			if(selected[0][72]==true){rStrings[72] = getmiR(72);}
			if(selected[0][73]==true){rStrings[73] = getmiR(73);}
			if(selected[0][74]==true){rStrings[74] = getmiR(74);}
			if(selected[0][75]==true){rStrings[75] = getmiR(75);}
			if(selected[0][76]==true){rStrings[76] = getmiR(76);}
			if(selected[0][77]==true){rStrings[77] = getmiR(77);}
			if(selected[0][78]==true){rStrings[78] = getmiR(78);}
			if(selected[0][79]==true){rStrings[79] = getmiR(79);}
			if(selected[0][80]==true){rStrings[80] = getmiR(80);}
			if(selected[0][81]==true){rStrings[81] = getmiR(81);}
			if(selected[0][82]==true){rStrings[82] = getmiR(82);}
			if(selected[0][83]==true){rStrings[83] = getmiR(83);}
			if(selected[0][84]==true){rStrings[84] = getmiR(84);}
			if(selected[0][85]==true){rStrings[85] = getmiR(85);}
			if(selected[0][86]==true){rStrings[86] = getmiR(86);}
			if(selected[0][87]==true){rStrings[87] = getmiR(87);}
			if(selected[0][88]==true){rStrings[88] = getmiR(88);}
			if(selected[0][89]==true){rStrings[89] = getmiR(89);}
			if(selected[0][90]==true){rStrings[90] = getmiR(90);}
			if(selected[0][91]==true){rStrings[91] = getmiR(91);}
			if(selected[0][92]==true){rStrings[92] = getmiR(92);}
			if(selected[0][93]==true){rStrings[93] = getmiR(93);}
			if(selected[0][94]==true){rStrings[94] = getmiR(94);}
			if(selected[0][95]==true){rStrings[95] = getmiR(95);}
			if(selected[0][96]==true){rStrings[96] = getmiR(96);}
			if(selected[0][97]==true){rStrings[97] = getmiR(97);}
			if(selected[0][98]==true){rStrings[98] = getmiR(98);}
			if(selected[0][99]==true){rStrings[99] = getmiR(99);}
			if(selected[0][100]==true){rStrings[100] = getmiR(100);}
			if(selected[0][101]==true){rStrings[101] = getmiR(101);}
			if(selected[0][102]==true){rStrings[102] = getmiR(102);}
			if(selected[0][103]==true){rStrings[103] = getmiR(103);}
			if(selected[0][104]==true){rStrings[104] = getmiR(104);}
			if(selected[0][105]==true){rStrings[105] = getmiR(105);}
			if(selected[0][106]==true){rStrings[106] = getmiR(106);}
			if(selected[0][107]==true){rStrings[107] = getmiR(107);}
			if(selected[0][108]==true){rStrings[108] = getmiR(108);}
			if(selected[0][109]==true){rStrings[109] = getmiR(109);}
			if(selected[0][110]==true){rStrings[110] = getmiR(110);}
			if(selected[0][111]==true){rStrings[111] = getmiR(111);}
			if(selected[0][112]==true){rStrings[112] = getmiR(112);}
			if(selected[0][113]==true){rStrings[113] = getmiR(113);}
			if(selected[0][114]==true){rStrings[114] = getmiR(114);}
			if(selected[0][115]==true){rStrings[115] = getmiR(115);}
			if(selected[0][116]==true){rStrings[116] = getmiR(116);}
			if(selected[0][117]==true){rStrings[117] = getmiR(117);}
			if(selected[0][118]==true){rStrings[118] = getmiR(118);}
			if(selected[0][119]==true){rStrings[119] = getmiR(119);}
			if(selected[0][120]==true){rStrings[120] = getmiR(120);}
			if(selected[0][121]==true){rStrings[121] = getmiR(121);}
			if(selected[0][122]==true){rStrings[122] = getmiR(122);}
			if(selected[0][123]==true){rStrings[123] = getmiR(123);}
			if(selected[0][124]==true){rStrings[124] = getmiR(124);}
			if(selected[0][125]==true){rStrings[125] = getmiR(125);}
			if(selected[0][126]==true){rStrings[126] = getmiR(126);}
			if(selected[0][127]==true){rStrings[127] = getmiR(127);}
			if(selected[0][128]==true){rStrings[128] = getmiR(128);}
			if(selected[0][129]==true){rStrings[129] = getmiR(129);}
			if(selected[0][130]==true){rStrings[130] = getmiR(130);}
			if(selected[0][131]==true){rStrings[131] = getmiR(131);}
			if(selected[0][132]==true){rStrings[132] = getmiR(132);}
			if(selected[0][133]==true){rStrings[133] = getmiR(133);}
			if(selected[0][134]==true){rStrings[134] = getmiR(134);}
			if(selected[0][135]==true){rStrings[135] = getmiR(135);}
			if(selected[0][136]==true){rStrings[136] = getmiR(136);}
			if(selected[0][137]==true){rStrings[137] = getmiR(137);}
			if(selected[0][138]==true){rStrings[138] = getmiR(138);}
			if(selected[0][139]==true){rStrings[139] = getmiR(139);}
			if(selected[0][140]==true){rStrings[140] = getmiR(140);}
			if(selected[0][141]==true){rStrings[141] = getmiR(141);}
			if(selected[0][142]==true){rStrings[142] = getmiR(142);}
			if(selected[0][143]==true){rStrings[143] = getmiR(143);}
			if(selected[0][144]==true){rStrings[144] = getmiR(144);}
			if(selected[0][145]==true){rStrings[145] = getmiR(145);}
			if(selected[0][146]==true){rStrings[146] = getmiR(146);}
			if(selected[0][147]==true){rStrings[147] = getmiR(147);}
			if(selected[0][148]==true){rStrings[148] = getmiR(148);}
			if(selected[0][149]==true){rStrings[149] = getmiR(149);}
			if(selected[0][150]==true){rStrings[150] = getmiR(150);}
			if(selected[0][151]==true){rStrings[151] = getmiR(151);}
			if(selected[0][152]==true){rStrings[152] = getmiR(152);}
			if(selected[0][153]==true){rStrings[153] = getmiR(153);}
			if(selected[0][154]==true){rStrings[154] = getmiR(154);}
		     
		     //condense list to include only the selected
		     final List<String> list = new ArrayList<String>();
		     for(final String s : rStrings) {
		        if(s != null && s.length() > 0) {
		           list.add(s);}}

		     rStrings = list.toArray(new String[list.size()]);
		     final String[] inp = new String[rStrings.length];
		     for(int i =0;i<rStrings.length;i++){
		    	 inp[i] = rStrings[i];
		     }
		     return rStrings;
		}

		
}
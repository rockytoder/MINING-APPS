package test1;



import java.awt.BorderLayout;

import javax.swing.AbstractButton;
import javax.swing.JFrame;

import java.awt.Component;

import java.awt.Dimension;

import java.awt.TextArea;

import java.awt.Container;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.awt.event.WindowAdapter;

import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;

import javax.swing.JFileChooser;

import javax.swing.JMenu;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import javax.swing.Box;

import javax.swing.ButtonGroup;

import javax.swing.JButton;

import javax.swing.JCheckBox;

import javax.swing.JComboBox;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JRadioButton;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.JTextField;

import javax.swing.border.Border;

import javax.swing.border.TitledBorder;

import javax.swing.filechooser.FileFilter;

import javax.swing.filechooser.FileNameExtensionFilter;


public class interfaceTER extends JFrame {


	private String filePath;

	final JFileChooser fileChoose = new JFileChooser();

	FileFilter filter = new FileNameExtensionFilter("*.txt", "all file");

	JTextArea textArea;JTextField pruning;
	JCheckBox allCheckBox,adjCheckBox,verbCheckBox,nounCheckBox,advCheckBox,tfdfCheckBox,occCheckBox,tfCheckBox,dfCheckBox,tfidfCheckBox,idfCheckBox;
	JRadioButton Postag,All,Lemme;
	Map<String,Stats> donnees;
	ButtonGroup group;
	JComboBox combo;JRadioButton yesRadioButton,noRadioButton;
	JButton Impd;
	File Fileresult;
	BufferedWriter buf;
	

	public interfaceTER() throws IOException{

		//resultat res = new resultat("corpus/recette/corpusrecetteHTMLAsTEXTIttiqueter");

		//donnees = res.tableauinter();

		JPanel panel = new JPanel(new GridLayout(0, 1));   

		Box b2 = Box.createHorizontalBox();

		Border border = BorderFactory.createTitledBorder("Language : ");

		String[] tab = {"French","English"};

		combo = new JComboBox(tab);

		combo.setMaximumSize(new Dimension(100, 25));

		b2.setBorder(border);

		b2.add(Box.createHorizontalStrut(10));

		b2.add(combo, BorderLayout.CENTER);

		panel.add(b2);
		

		group = new ButtonGroup();

		Box b1 = Box.createHorizontalBox();

		b1.add(Box.createHorizontalStrut(10));

		Border boe1 = BorderFactory.createTitledBorder("Delete Stop-words :");

		panel.setLayout(new GridLayout(0,1,150,10));

		b1.setBorder(boe1);

		panel.add(b1);

		yesRadioButton = new JRadioButton("YES");

		b1.add(yesRadioButton);

		group.add(yesRadioButton);

		noRadioButton = new JRadioButton("NO", true);

		b1.add(noRadioButton);

		group.add(noRadioButton);

		Box b = Box.createVerticalBox();

		Border boe = BorderFactory.createTitledBorder("Pruning :");

		b.setBorder(boe);

		pruning = new JTextField();

		b.add(Box.createVerticalStrut(30));

		pruning.setMaximumSize(new Dimension(100, 25));

		b.add(pruning);

		panel.add(b);

		ButtonGroup group2 = new ButtonGroup();

		Box b3 = Box.createVerticalBox();

		b3.add(Box.createVerticalStrut(30));

		Border boe3 = BorderFactory.createTitledBorder("Text output");

		b3.setBorder(boe3);

		   /*----------------------even Textoutput----------------------------*/

		All = new JRadioButton("ALL");

		group2.add(All);

		b3.add(All, BorderLayout.NORTH);

		Postag = new JRadioButton("Pos tag");

		group2.add(Postag);

		b3.add(Postag);

		Lemme = new JRadioButton("Lemme");

		group2.add(Lemme);

		b3.add(Lemme);

		panel.add(b3);

		Container contentPane = this.getContentPane();

		contentPane.add(panel, BorderLayout.WEST);

		Box b6 = Box.createHorizontalBox();

		b6.add(Box.createHorizontalGlue());

		JButton Impf = new JButton("Import File");

		Impf.addActionListener(new openandimport ());

		b6.add(Impf);

		b6.add(Box.createHorizontalGlue());
		

		Impd = new JButton("Import Directory");

		Impd.addActionListener(new importDir ());

		b6.add(Impd);

		b6.add(Box.createHorizontalGlue());

		JButton Val = new JButton("Validate");
		Val.addActionListener(new Resultat());

		b6.add(Val);

		b6.add(Box.createHorizontalGlue());

		JButton Exp = new JButton("Export");
		
		Exp.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				JFileChooser fd = new JFileChooser();
				fd.showSaveDialog(null);
				File f = fd.getSelectedFile();
				
				try{
					buf = new BufferedWriter(new FileWriter(f));
					String str = textArea.getText();
					buf.write(str);
					buf.flush();
				}
				catch(IOException e1){
					e1.printStackTrace();
				}
			}
		});

		b6.add(Exp);

		b6.add(Box.createHorizontalGlue());

		textArea = new JTextArea(3,30);

		JScrollPane scrollPane = new JScrollPane(textArea);

		panel.add(b6);

		this.getContentPane().add(b6, BorderLayout.SOUTH);

		this.getContentPane().add(scrollPane, "Center");

		panel = new JPanel(new GridLayout(0, 1));

		Box b4 = Box.createVerticalBox();

		b4.add(Box.createVerticalStrut(20));

		Border boe4 = BorderFactory.createTitledBorder("PoS filtering");

		b4.setBorder(boe4);

		allCheckBox = new JCheckBox("ALL");

		b4.add(allCheckBox);

		adjCheckBox = new JCheckBox("ADJ");

		b4.add(adjCheckBox);

		advCheckBox = new JCheckBox("ADV");

		b4.add(advCheckBox);

		nounCheckBox = new JCheckBox("NOUN");

		b4.add(nounCheckBox);

		verbCheckBox = new JCheckBox("VERB");

		b4.add(verbCheckBox);

		panel.add(b4);


		Box b5 = Box.createVerticalBox();

		b5.add(Box.createVerticalStrut(20));

		Border boe5 = BorderFactory.createTitledBorder("Statistic fitering");

		b5.setBorder(boe5);

		occCheckBox = new JCheckBox("OCCU nbr", true);

		b5.add(occCheckBox);

		tfCheckBox = new JCheckBox("TF");

		b5.add(tfCheckBox);

		idfCheckBox = new JCheckBox("IDF");

		b5.add(idfCheckBox);

		dfCheckBox = new JCheckBox("DF");

		b5.add(dfCheckBox);

		tfidfCheckBox = new JCheckBox("TF*IDF");

		b5.add(tfidfCheckBox);

		tfdfCheckBox = new JCheckBox("TF*DF");

		b5.add(tfdfCheckBox);

		panel.add(b5);

		contentPane.add(panel, BorderLayout.EAST);

		JMenuBar menubar = new JMenuBar();

		this.setJMenuBar(menubar);

		JMenu file = new JMenu("File");

		menubar.add(file);

		JMenu edit = new JMenu("Edit");

		menubar.add(edit);

		JMenu help = new JMenu("Help");

		menubar.add(help);

		JMenuItem open = new JMenuItem("Open");

		open.addActionListener(new openandimport ());

		JMenuItem save = new JMenuItem("Save");

		JMenuItem saveas = new JMenuItem("Save as");

		JMenuItem quit = new JMenuItem("Quit");

		file.add(open);

		file.add(save);

		file.add(saveas);

		file.add(quit);

		JMenuItem cut = new JMenuItem("Cut");

		JMenuItem copy = new JMenuItem("Copy");

		JMenuItem paste = new JMenuItem("Paste");

		edit.add(cut);

		edit.add(copy);

		edit.add(paste);

		JMenuItem about = new JMenuItem("About");

		help.add(about);

		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		this.setTitle("Version 1");

		this.setSize(700, 600);

		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}


	class openandimport implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int returnVal = fileChoose.showOpenDialog(interfaceTER.this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				//filePath = fileChoose.getSelectedFile();
			}
			
		}
	}


	void readFile() throws FileNotFoundException // 显示打开文件的内容
	{

		
	}
	/******************combo langue****************/
	//Object valeurcombo=combo.getSelectedItem();
	/*************************************************/
	class  Resultat  implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resultat res;
			try {
				res = new resultat(Fileresult);
				donnees = res.tableauinter();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Iterator iter=donnees.keySet().iterator();
			
			PosTagFilter postTagFilter;
			if (allCheckBox.isSelected()) {
				postTagFilter = new PosTagFilter(allCheckBox.isSelected());
			} else {
				postTagFilter = new PosTagFilter();
				postTagFilter.ADJ = adjCheckBox.isSelected();
				postTagFilter.ADV = advCheckBox.isSelected();
				postTagFilter.NOUN = nounCheckBox.isSelected();
				postTagFilter.VERB = verbCheckBox.isSelected();
			}
			
			StatisticsFilter statisticsFilter = new StatisticsFilter();
			statisticsFilter.OCCU = occCheckBox.isSelected();
			statisticsFilter.TF = tfCheckBox.isSelected();
			statisticsFilter.DF= dfCheckBox.isSelected();
			statisticsFilter.IDF = idfCheckBox.isSelected();
			statisticsFilter.TF_DF = tfdfCheckBox.isSelected();
			statisticsFilter.TF_IDF = tfidfCheckBox.isSelected();

			StringBuilder contenu = new StringBuilder();
			while(iter.hasNext())
			{
				String clef = (String) iter.next();
				Stats StatsMot1 = ((Stats)donnees.get(clef));
				//String ligne = StatsMot1.formatOutLine(clef, deleteStopWord, posTag, lemme, postTagFilter, statisticsFilter);
				
				
				TextOutputCols textOutputCols;
				if (All.isSelected()) {
					textOutputCols = new TextOutputCols(All.isSelected());
				} else {
					textOutputCols = new TextOutputCols();
					textOutputCols.lemme = Lemme.isSelected();
					textOutputCols.posTag = Postag.isSelected();
				}
				
				int valeurpru = 0;
				if (pruning.getText().length() > 0) {
					try {
						valeurpru = Integer.parseInt(pruning.getText());
					} catch (Exception ex) {
						valeurpru = 0;
					}
				}
				
				String ligne = StatsMot1.formatOutLine(clef, yesRadioButton.isSelected() , textOutputCols, postTagFilter, statisticsFilter, valeurpru);
				if(ligne.length() > 1)
				{
					contenu.append(ligne + "\n");
				}
			}
			
			textArea.setText(contenu.toString());  
		}
	}
	
	/////////////////////// importer un repertoir ////////////////////////
	class importDir  implements ActionListener{

		   JFileChooser chooser;
		   String choosertitle;
		   public void actionPerformed(ActionEvent e) {
		    int result;
	        
		    chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle(choosertitle);
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		    chooser.setAcceptAllFileFilterUsed(false);
		    if (chooser.showOpenDialog(Impd) == JFileChooser.APPROVE_OPTION) { 
		   
		    	Fileresult = chooser.getSelectedFile();
		      
	      }
		    else {
		      System.out.println(" ");
		      }
		     }
		}

	public static void main(String args[]) throws IOException {
		interfaceTER TER = new interfaceTER();
	}

}

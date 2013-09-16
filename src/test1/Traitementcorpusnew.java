package test1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Traitementcorpusnew {
	Hashtable table; //1:occurrence
	Hashtable word_Posttag;//7
	Hashtable word_Lemme; //8
	Hashtable tableTF;
	Hashtable tableTFg;
	Hashtable tableTFgeneral;
	Hashtable tableIDF;//3
	Hashtable tableDF;//4
	Hashtable tableDFxtf;//5
	Hashtable tabletfxidf;//6
	Hashtable tablemotdocapparait;
	Hashtable tableStopword; 
	StringTokenizer st1;
	String mot;
	int nbOcc;
	float tfg;
	String ligne1;
	String etiquette;String clef;
	double  IDF;double DF;double dfxtf;double tfxidf; int nbrdocmotapparait;
	ArrayList tbl_StopWord,tbl_mot;
    StringTokenizer st;
    StringTokenizer stStopword; 
    String  ligne,StopWord;
	
	public Traitementcorpusnew(){}
	
	public Traitementcorpusnew(File path) throws IOException
	{
/*--------------------------------------------------Ettiquetage et calculs d'occurrence -------------------------------------------------------*/	
	//les fichier existant a l'interieur
		  File rep1 = path;    //"corpus/recette/corpusrecetteHTMLAsTEXTIttiqueter"; // déclaration du document
		//  File fichier1 = new File(rep1);// recuperation du dossier
		  
	      if (rep1.isDirectory()){  // condition pour verifier si le fichier1 et un repertoire
				System.out.println("Répertoire : " + rep1); // affichage du répertoire
				String str[] = rep1.list(); // recuperation du nom des fichiers dans une liste str
				System.out.println("le nombre de fichier = " + str.length); // affichage du nombre de fichier dans le dossier
				word_Posttag= new Hashtable();
				word_Lemme= new Hashtable();
				table = new Hashtable();
				tableTF = new Hashtable();
				tableTFgeneral = new Hashtable();
				tableDFxtf = new Hashtable();//6
				tabletfxidf = new Hashtable();
				tableDF = new Hashtable();
				tableIDF = new Hashtable();
				tablemotdocapparait= new Hashtable();
				tableStopword= new Hashtable();
				tbl_StopWord = new ArrayList();
				 
				
				for (int i=0; i<str.length; i++){ // boucle pour parcourir la list
					
					System.out.println("fichier : " + str[i]);// affichage de la list des nom de fichier			
					// declaration de fichier
					File f = new File (rep1+"/"+str[i]);
					// Lecture du fichier texte
					FileReader fr = new FileReader (f);
					// lecture du buffer
					BufferedReader entree = new BufferedReader(fr);
					// boucle de lecture de chaque ligne
					while ((ligne1 = entree.readLine()) != null)
					{
						// extraction des caracteres indiqués entre les paranthéses
						st1 = new StringTokenizer(ligne1, " ");
						// boucle pour la lecture des mots de chaque ligne
						while(st1.hasMoreTokens()){
							//recuperation du mot
							etiquette= st1.nextToken();		                       
							// remplie le dictionnaire
							//�tiquette.add(etiquette);
							
							Pattern p = Pattern.compile("(.*)_(.*)_(.*)");
							Matcher m=p.matcher(etiquette);
							while(m.find()){
								//word.add(m.group(1));
								String clef=m.group(1)+"/"+m.group(2);
								word_Posttag.put(clef, m.group(2));
								word_Lemme.put(clef , m.group(3));
								
		                      // si le mot existe il comptabilise

		                      if (table.containsKey(clef)){ // compteur du nombre d'occurrence

		                          nbOcc = ((Integer)table.get(clef)).intValue();

		                          nbOcc++;

		                      }                     

		                      else{

		                          nbOcc = 1;
		                      }
		                      table.put(clef,nbOcc);
							}
						}
					}
					/*--------calcul TF de chaque fichier -------*/
					 Enumeration lesMotsOccu = table.keys();
						
						 while (lesMotsOccu.hasMoreElements())
						 {
			                  clef = (String)lesMotsOccu.nextElement();

			                  nbOcc = ((Integer)table.get(clef)).intValue();

			                  float nbrmot=table.size();
			                  float TF=nbOcc/nbrmot;
			                  tableTF.put(clef, TF);

				              if (!(tablemotdocapparait.containsKey(clef)))
				                  {

				                      nbrdocmotapparait=1;

				                  }

				                  else

				                  {

				                      nbrdocmotapparait=((Integer)tablemotdocapparait.get(clef)).intValue();

				                      nbrdocmotapparait++;        

				                  }

				                  tablemotdocapparait.put(clef, nbrdocmotapparait);
							}	
						}
						 Enumeration lesMotsTF = tableTF.keys();

			              while(lesMotsTF.hasMoreElements())

			              {

			                  clef = (String)lesMotsTF.nextElement();

			                  if(!(tableTFgeneral.containsKey(clef)))

			                  {

			                      tfg=((Float) tableTF.get(clef)).floatValue();

			                      tableTFgeneral.put(clef,tfg);

			                  }

			                  else

			                  {

			                      tfg=((Float)tableTF.get(clef)).floatValue()+((Float)tableTFgeneral.get(clef)).floatValue();

			                      tableTFgeneral.put(clef,tfg);

			                  }

			              }
			              
			              Enumeration lesMots = tablemotdocapparait.keys();
			              
			              while (lesMots.hasMoreElements())

			              {

			                  clef = (String)(lesMots).nextElement();

			                  double nbrdocmot=((Integer)tablemotdocapparait.get(clef)).intValue();
			                  
			                  double taille = str.length;

			                  IDF= Math.log(taille)-Math.log(nbrdocmot);

			                  DF=1/IDF;

			                  tableIDF.put(clef, IDF);

			                  tableDF.put(clef, DF);

			                  dfxtf=(((Double)tableDF.get(clef)).doubleValue())*(((Float)tableTFgeneral.get(clef)).floatValue());

			                  tfxidf=(((Double)tableIDF.get(clef)).doubleValue())*(((Float)tableTFgeneral.get(clef)).floatValue());

			                  tabletfxidf.put(clef,tfxidf);

			                  tableDFxtf.put(clef,dfxtf);
			              }
					
	      /****************************************************** STOPword*************************************************************/
	      File StopWordFile = new File("stopWord.txt");// declaration de fichier	     
	      FileReader StopWordReader = new FileReader (StopWordFile); // Lecture du fichier texte
	      BufferedReader StopWordBufferedReader = new BufferedReader(StopWordReader);// lecture du buffer
	      
	      while ((ligne = StopWordBufferedReader.readLine()) != null){
	    	  stStopword = new StringTokenizer(ligne, " ");// extraction des caracteres indiqués entre les paranthéses
	           // boucle pour la lecture des mots de chaque ligne
	           while(stStopword.hasMoreTokens()){
	                  // recuperation du mot
	                   StopWord = stStopword.nextToken();                                          
	                   // remplie le dictionnaire
	                   tbl_StopWord.add(StopWord);          
	           }
	      }
	    Enumeration lesMotsstopword = table.keys();
	      while (lesMotsstopword.hasMoreElements())
	      			{
	      			clef = (String)lesMotsstopword.nextElement();
	      			Pattern p = Pattern.compile("(.*)/(.*)");
					Matcher m=p.matcher(clef);
					while(m.find())
					{
					String mot=m.group(1);
					boolean Stopword; 
					   if(tbl_StopWord.contains(mot))
					   {
	      				 Stopword=true;
	      			   }
					   else
	      				{
	      				Stopword=false;
	      				}
	      				tableStopword.put(mot+"/"+m.group(2), Stopword);				 
					}      			
	      			}				   
	      	}

					/*-------------------------------------------Ecriture des resulats dans des fichiers ---------------------------------------------------*/
					
	  	Enumeration lesMotsstopword = tableStopword.keys(); 
		// ecriture dans un fichier de sortie des mot + posttage
		FileWriter file13 = new FileWriter("sortie/sortieStopword.txt");
		BufferedWriter write13= new BufferedWriter(file13);
		PrintWriter ecrivain13 = new PrintWriter(write13);
			
	      
	      Enumeration lesMotsdoc = tablemotdocapparait.keys(); 
			// ecriture dans un fichier de sortie des mot + posttage
			FileWriter file12 = new FileWriter("sortie/sortiedocapparai.txt");
			BufferedWriter write12= new BufferedWriter(file12);
			PrintWriter ecrivain12 = new PrintWriter(write12);

	      
	      			System.out.println("nombre de mot dans le fichier ="+word_Posttag.size());
					Enumeration lesMotsposttag = word_Posttag.keys(); 
					// ecriture dans un fichier de sortie des mot + posttage
					FileWriter file8 = new FileWriter("sortie/sortiePosttag/sortiesortieMotetPosttage.txt");
					BufferedWriter write8= new BufferedWriter(file8);
					PrintWriter ecrivain8 = new PrintWriter(write8);
					
					Enumeration lesMotsLemme = word_Lemme.keys(); 
					// ecriture dans un fichier de sortie des mot + Lemme
					FileWriter file7 = new FileWriter("sortie/sortieLemme/sortieMotetLemme.txt");
					BufferedWriter write7= new BufferedWriter(file7);
					PrintWriter ecrivain7 = new PrintWriter(write7);
					
					 System.out.println("nombre de mot dans le fichier ="+table.size());
		             Enumeration lesMotsOccu = table.keys();
		             // ecriture dans un fichier de sortie des mot + occur
		              FileWriter file1 = new FileWriter("sortie/sortieMotetOCCu.txt");
		              BufferedWriter write1= new BufferedWriter(file1);
		              PrintWriter ecrivain1 = new PrintWriter(write1);
		              
		              Enumeration lesMotsTFg = tableTF.keys();
			          // ecriture dans un fichier de sortie des mot + occur
			           FileWriter file2 = new FileWriter("sortie/sortieMotetTF.txt");
			           BufferedWriter write2= new BufferedWriter(file2);
			           PrintWriter ecrivain2 = new PrintWriter(write2);
			           
			           Enumeration  lesMotsIDF =  tableIDF.keys();
				          // ecriture dans un fichier de sortie des mot + occur
				        FileWriter file3 = new FileWriter("sortie/sortieMotetIDF.txt");
				        BufferedWriter write3= new BufferedWriter(file3);
				        PrintWriter ecrivain3 = new PrintWriter(write3);
				           
				       Enumeration lesMotsDF = tableDF.keys();
					    // ecriture dans un fichier de sortie des mot + occur
					    FileWriter file4 = new FileWriter("sortie/sortieMotetDF.txt");
					    BufferedWriter write4= new BufferedWriter(file4);
					    PrintWriter ecrivain4 = new PrintWriter(write4);
					           
					   Enumeration lesMotsDFxtf = tableDFxtf.keys();
					 // ecriture dans un fichier de sortie des mot + occur
					 FileWriter file5 = new FileWriter("sortie/sortieMotetDFxtf.txt");
					 BufferedWriter write5= new BufferedWriter(file5);
					PrintWriter ecrivain5 = new PrintWriter(write5);
					
					  Enumeration lesMotsdfxidf = tabletfxidf.keys();
					// ecriture dans un fichier de sortie des mot + occur
					FileWriter file6 = new FileWriter("sortie/sortieMotettfxidf.txt");
					 BufferedWriter write6= new BufferedWriter(file6);
					PrintWriter ecrivain6 = new PrintWriter(write6);
					
					while(lesMotsstopword.hasMoreElements())
					{
						mot = (String)lesMotsstopword.nextElement();
						boolean StopWord = ((Boolean)tableStopword.get(mot));
						
						ecrivain13.println( mot +"  "+ StopWord );
						
					}
					ecrivain13.close();
					
					while(lesMotsdoc.hasMoreElements())
					{
						mot = (String)lesMotsdoc.nextElement();
						int motappar = ((Integer)tablemotdocapparait.get(mot)).intValue();
						
						ecrivain12.println( mot +"  " + motappar );
						
					}
					ecrivain12.close();

		              /*----------mot et Posttag-------------*/
					while(lesMotsposttag.hasMoreElements())
					{
						mot = (String)lesMotsposttag.nextElement();
						String Posttag = ((String)word_Posttag.get(mot));
						ecrivain8.println( mot +"  " + Posttag );
						
					}
					ecrivain8.close();
					/*----------mot et Lemme-------------*/
					while(lesMotsLemme.hasMoreElements())
					{
						mot = (String)lesMotsLemme.nextElement();
						String Lemme = ((String)word_Lemme.get(mot));
						ecrivain7.println( mot +"  " + Lemme );	
					}
					ecrivain7.close();
					/*----------mot et nbocurrence-------------*/
		            while (lesMotsOccu.hasMoreElements())
		              {
		                mot = (String)lesMotsOccu.nextElement();
		                nbOcc = ((Integer)table.get(mot)).intValue();
		                /*System.out.println( mot +"  " + nbOcc );*/
		                ecrivain1.println( mot +"  " + nbOcc );
		              }
		            ecrivain1.close();
		            /*----------mot et TFg-------------*/
		            while (lesMotsTFg.hasMoreElements())
		            {
		                mot = (String)lesMotsTFg.nextElement();
		                tfg=((Float)tableTFgeneral.get(mot)).floatValue();
		                ecrivain2.println( mot +"  " + tfg );
		                }
		                ecrivain2.close();
		                /*----------mot et IDF-------------*/
			            while (lesMotsIDF.hasMoreElements())
			            {
			                mot = (String)lesMotsIDF.nextElement();
			                IDF=((Double)tableIDF.get(mot)).doubleValue();
			                ecrivain3.println( mot +"  " + IDF);
			                }

			                ecrivain3.close();
		                /*----------mot et DF-------------*/
			            while (lesMotsDF.hasMoreElements())
			            {
			                mot = (String)lesMotsDF.nextElement();
			                DF=((Double)tableDF.get(mot)).doubleValue();
			                ecrivain4.println( mot +"  " + DF);
			                }
				                ecrivain4.close();
			                /*----------mot et DFxtf -------------*/
				         while (lesMotsDFxtf.hasMoreElements())
				            {
				            mot = (String)lesMotsDFxtf.nextElement();
					        dfxtf=((Double)tableDFxtf.get(mot)).doubleValue();
					        ecrivain5.println( mot +"  " + dfxtf); 
					          }

					           ecrivain5.close();
					       /*----------mot et tfxidf -------------*/
						  while (lesMotsdfxidf.hasMoreElements())
						   {
						    mot = (String)lesMotsdfxidf.nextElement();
						    tfxidf=((Double)tabletfxidf.get(mot)).doubleValue();
						    ecrivain6.println( mot +"  " + tfxidf);
						   }
						  ecrivain6.close();
				}
	
	
/*-------------------------------------------------------main---------------------------------------------------------------------*/
	/*public static void main(String[] args){
	  Traitementcorpusnew cm = new Traitementcorpusnew("jjjjjj");
		  
	  }*/

}

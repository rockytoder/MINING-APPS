package test1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class resultat extends Traitementcorpusnew {
		Map<String,Stats> tableResult ;
		 String mot;
	     int nbOcc;
	     Stats stats;
	     Enumeration mots = table.keys();
		
	   public resultat(File path) throws IOException
	   {	
		  super(path);
	      tableResult = new HashMap<String,Stats>();
	    
	       tableauinter();
	       }
	       /*FileWriter file0 = new FileWriter("sortie/Etapeintermid/tableauResultat.txt");
	       BufferedWriter write0= new BufferedWriter(file0);
	       PrintWriter ecrivain0 = new PrintWriter(write0);*/
	   
	       public Map<String,Stats> tableauinter(){
	    	   
	       while (mots.hasMoreElements()) {
	           // On r�cup�re le mot.
	           mot = mots.nextElement().toString();
	           // On r�cup�re le nbr d'occurences.
	           nbOcc = ((Integer)table.get(mot)).intValue();
	           // On instance l'objet Stats avec le nombre d'occurences.
	           stats = new Stats(nbOcc);
	           // R�cup�ration et regroupement des stats du mot courant.
	           stats.Posttag = (String) word_Posttag.get(mot);
	           stats.lemm = (String) word_Lemme.get(mot);
	           stats.TF=(Float) tableTF.get(mot);
	           stats.IDF=(Double) tableIDF.get(mot);
	           stats.dfxtf=(Double) tableDFxtf.get(mot);
	           stats.tfxidf=(Double) tabletfxidf.get(mot);
	           stats.Stopword=(Boolean) tableStopword.get(mot);

	           tableResult.put(mot, stats);
	          // ecrivain0.println(mot +"\t"+  nbOcc+"\t"+  stats.Posttag+"\t"+  stats.lemm +"\t"+ stats.TF+"\t"+ stats.DF+"\t"+ stats.IDF+"\t"+ stats.dfxtf+"\t"+  stats.tfxidf);
	             
	           /* R�cup�ration Nbr d'occurence d'un mot.
	           Stats StatsMot1 = ((Stats)tableResult.get('mot1'));
	           StatsMot1.nbOcc;
	           StatsMot1.Posttag;*/
	       } return tableResult;
	       }
	       //ecrivain0.close();
	       /*Set key=tableResult.keySet();
	       System.out.println("liste des cles"+key);*/
  
	   }
	  /* public static void main(String[] args) throws IOException {
			  resultat cm = new resultat();
			  
		  }*/



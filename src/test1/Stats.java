package test1;

public class Stats {
	    int nbOcc;
	    String lemm;
	    String Posttag;
	    float TF;
	    double IDF;
	    double DF;
	    double dfxtf;
	    double tfxidf;
	    boolean Stopword;
	    
	    public Stats(int nbOcc) {
	        this.nbOcc = nbOcc;
	    }
	    
	    public String formatOutLine(String word, boolean deleteStopWord, TextOutputCols textOutputCols, PosTagFilter postTagFilter, StatisticsFilter statisticsFilter, int valeurprunt) {
			String result = "";
	    	if (!this.Stopword || !deleteStopWord)
	    	{
	    		if (this.nbOcc >= valeurprunt) {
		    		// Filter Pos Tag
		    		if (postTagFilter.isAll()
		    			|| (postTagFilter.ADJ && this.Posttag.contains("ADJ"))
		    			|| (postTagFilter.ADV && this.Posttag.contains("ADV"))
		    			|| (postTagFilter.NOUN && (this.Posttag.contains("NOM") || this.Posttag.contains("NAM")))
		    			|| (postTagFilter.VERB && (this.Posttag.contains("VER"))))
		    		{
						// Pos Tag column
						if (textOutputCols.posTag) {
							result += this.Posttag;
						}
						
						// Lemme column
			    		if (textOutputCols.lemme) {
			    			result += result.length() > 1 ? "\t" + this.lemm : this.lemm;
			    		}
			    		
			    		if (statisticsFilter.OCCU){
			    			result += result.length() > 1 ? "\t" + this.nbOcc : this.nbOcc;
			    		}
			    		
			    		if (statisticsFilter.DF){
			    			result += result.length() > 1 ? "\t" + this.DF : this.DF;
			    		}
			    		
			    		if (statisticsFilter.TF){
			    			result += result.length() > 1 ? "\t" + this.TF : this.TF;
			    		}
			    		
			    		if (statisticsFilter.IDF){
			    			result += result.length() > 1 ? "\t" + this.IDF : this.IDF;
			    		}
			    		
			    		if (statisticsFilter.TF_DF){
			    			result += result.length() > 1 ? "\t" + this.dfxtf : this.dfxtf;
			    		}
			    		
			    		if (statisticsFilter.TF_IDF){
			    			result += result.length() > 1 ? "\t" + this.tfxidf : this.tfxidf;
			    		}
			    		
			    		result = word + "\t" + result;
		    		}
		    	}
	    	}
	    	
	    	return result;   	
	    }
}

package test1;

public class StatisticsFilter {
	public boolean OCCU = false;
	public boolean TF = false;
	public boolean IDF = false;
	public boolean DF = false;
	public boolean TF_IDF = false;
	public boolean TF_DF = false;
	
	public StatisticsFilter() {}

	
	public StatisticsFilter(boolean all) {
		if (all) {
			this.OCCU = true;
			this.TF = true;
			this.IDF = true;
			this.DF = true;
			this.TF_IDF = true;
			this.TF_DF = true;
		}
	}
}

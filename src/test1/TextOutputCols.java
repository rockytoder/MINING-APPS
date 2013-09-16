package test1;

public class TextOutputCols {
	public boolean lemme = false;
	public boolean posTag = false;
	
	public TextOutputCols() {}
	
	public TextOutputCols(boolean all) {
		if (all) {
			this.lemme = true;
			this.posTag = true;
		}
	}
}

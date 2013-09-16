package test1;

public class PosTagFilter {
	public boolean ADJ = false;
	public boolean ADV = false;
	public boolean NOUN = false;
	public boolean VERB = false;
	
	public PosTagFilter() {}


	public PosTagFilter(boolean all) {
		if (all) {
			this.ADJ = true;
			this.ADV = true;
			this.NOUN = true;
			this.VERB = true;
		}
	}
	
	public boolean isAll() {
		return (this.ADJ && this.ADV && this.NOUN && this.VERB);
	}
}

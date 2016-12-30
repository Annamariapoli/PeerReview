package bean;

public class Authorship {

	private int id_authorship;
	private int eprintid;
	private int id_creator;
	
	public Authorship(int id_authorship, int eprintid, int id_creator) {
		super();
		this.id_authorship = id_authorship;
		this.eprintid = eprintid;
		this.id_creator = id_creator;
	}
	public int getId_authorship() {
		return id_authorship;
	}
	public void setId_authorship(int id_authorship) {
		this.id_authorship = id_authorship;
	}
	public int getEprintid() {
		return eprintid;
	}
	public void setEprintid(int eprintid) {
		this.eprintid = eprintid;
	}
	public int getId_creator() {
		return id_creator;
	}
	public void setId_creator(int id_creator) {
		this.id_creator = id_creator;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_authorship;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authorship other = (Authorship) obj;
		if (id_authorship != other.id_authorship)
			return false;
		return true;
	}
	
	public String toString(){
		String ris;
		ris = id_authorship+" "+eprintid+" "+id_creator+" \n";
		return ris;
	}
}

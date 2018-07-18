/*
* 不用看
* */
public class Couple {
	private Person female=null;
	private Person male=null;
	public final static int FEMALE=0;
	public final static int MALE=1;
	public Couple() {
		load();
	}
	public void load() {
		male=new Person(0, 0);
		female=new Person(0, 0);
	}
	public Person getFemale() {
		return female;
	}
	public void setFemale(Person female) {
		this.female = female;
	}
	public Person getMale() {
		return male;
	}
	public void setMale(Person male) {
		this.male = male;
	}
	public void maleLeave() {
		male.setOnline(false);
	}
	public void femaleLeave() {
		female.setOnline(false);
	}
}


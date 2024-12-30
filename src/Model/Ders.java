package Model;

public class Ders {
	 private int id;
	    private String name;
	    private int departmentId;
	    private int classLevel;

	   
	    public Ders(int id, String name, int departmentId, int classLevel) {
	        this.id = id;
	        this.name = name;
	        this.departmentId = departmentId;
	        this.classLevel = classLevel;
	    }

	   
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getDepartmentId() {
	        return departmentId;
	    }

	    public void setDepartmentId(int departmentId) {
	        this.departmentId = departmentId;
	    }

	    public int getClassLevel() {
	        return classLevel;
	    }

	    public void setClassLevel(int classLevel) {
	        this.classLevel = classLevel;
	    }

	    @Override
	    public String toString() {
	        return name + " (Class: " + classLevel + ", Department: " + departmentId + ")";
	    }
	}

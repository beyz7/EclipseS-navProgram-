package Model;

public class Sınıf {
	 private int id;
	    private int classLevel;
	    private int departmentId;

	    
	    public Sınıf(int id, int classLevel, int departmentId) {
	        this.id = id;
	        this.classLevel = classLevel;
	        this.departmentId = departmentId;
	    }

	   
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getClassLevel() {
	        return classLevel;
	    }

	    public void setClassLevel(int classLevel) {
	        this.classLevel = classLevel;
	    }

	    public int getDepartmentId() {
	        return departmentId;
	    }

	    public void setDepartmentId(int departmentId) {
	        this.departmentId = departmentId;
	    }

	    @Override
	    public String toString() {
	        return "Class Level: " + classLevel + ", Department ID: " + departmentId;
	    }
	}

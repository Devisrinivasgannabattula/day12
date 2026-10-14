package Spring.EmployeeDetails;

public class Employee 
{
	//primitive datatype
	String name;
	String department;
	String empId;
	String salary;
	int experience;
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	//tostring
	
	@Override
	public String toString() {
		return "Employee [name=" + name + ", department=" + department + ", empId=" + empId + ", salary=" + salary
				+ ", experience=" + experience + "]";
	}
	
}

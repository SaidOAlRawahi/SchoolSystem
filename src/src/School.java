package src;
import java.io.Serializable;
import java.util.*;

class School implements Serializable{
	private static final long serialVersionUID = 4916282141041962252L;
	String schoolName = "";
	ArrayList<String> subjectsNamesList = new ArrayList<String>();
	HashMap<String,Student> studentsList = new HashMap<String,Student>();
}

class Student implements Serializable{
	private static final long serialVersionUID = -1446868309570135119L;
	String studentName;
	ArrayList<Subject> studentSubjectsList = new ArrayList<Subject>();
}

class Subject implements Serializable{
	private static final long serialVersionUID = -5728431845039280684L;
	String subjectName;
	int grade = -1;
}

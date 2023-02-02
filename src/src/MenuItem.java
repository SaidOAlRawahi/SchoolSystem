package src;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

abstract class MenuItem{
	int clicks = 0;
	String title = "myTitle";
	void triggerAction() {
		clicks++;
	}
}



//--------------------Students Menu Item-----------------------
class StudentItem extends MenuItem{
	String studentKey;
	StudentItem(String studentKey){
		this.title = Main.mySchool.studentsList.get(studentKey).studentName;
		this.studentKey = studentKey;
	}
	
	@Override
	void triggerAction(){
		Menu newMenu = new SubjectsMenu(studentKey);
		Main.menues.push(newMenu);
	}
}



//--------------------Subjects Menu Item-----------------------
class SubjectItem extends MenuItem{
	String studentKey;
	int subjectIndex;
	SubjectItem(int subjectIndex, String studentKey){
		this.title = Main.mySchool.studentsList.get(studentKey)
				.studentSubjectsList.get(subjectIndex).subjectName;
		this.subjectIndex = subjectIndex;
		this.studentKey = studentKey;
	}
	
	@Override
	void triggerAction(){
		System.out.print("Enter the grade for " + Main.mySchool.studentsList
			.get(studentKey).studentName + " in " + title + ": ");
		int input = Main.getInput(new StudentsMenu(),false);
		
		Main.mySchool.studentsList.get(studentKey)
		.studentSubjectsList.get(subjectIndex).grade = input;
		
		if (Main.repeatProcess("Grades For " + Main.mySchool.studentsList
				.get(studentKey).studentName)){
		}
		else if(Main.repeatProcess("Grades For Other Studends")){
			Main.menues.pop();
		}
		else {
			Main.menues.pop();
			Main.menues.pop();
		}
	}
}



//--------------------Schools Details-----------------------
class SchoolNameItem extends MenuItem{
	SchoolNameItem(){
		this.title = "Edit School Name.";
	}
	
	@Override
	void triggerAction(){
		System.out.print("Enter School Name: ");
		Main.mySchool.schoolName = Main.sc.next();
	}
}


class SubjectEntryItem extends MenuItem{
	SubjectEntryItem(){
		this.title = "Add Subjects";
	}
	
	@Override
	void triggerAction(){
		do{
			System.out.print("Enter The Name Of The Subject: ");
			String subjectName = Main.sc.next();
			
			if(subjectName.length()<3) {
				System.out.println("Enter a string more than 2 characters.");
			}
			else {		
				if(!Main.mySchool.subjectsNamesList.contains(subjectName)) {
					Main.mySchool.subjectsNamesList.add(subjectName);
					
					for(String key : Main.mySchool.studentsList.keySet()) {
						Subject newSubject = new Subject();
						newSubject.subjectName = subjectName;
						Main.mySchool.studentsList.get(key).studentSubjectsList.add(newSubject);
					}
				}
			}
		}while(Main.repeatProcess("Subjects"));
	}
}



//--------------------Main Menu-----------------------
class SchoolDetailsItem extends MenuItem{
	SchoolDetailsItem(){
		this.title = "Edit School Details.";
	}
	
	@Override
	void triggerAction(){
		super.triggerAction();
		Menu newMenu = new SchoolDetailsMenu();
		Main.menues.push(newMenu);
	}
}


class StudentDetailsItem extends MenuItem{
	StudentDetailsItem(){
		this.title = "Enter Students Names.";
	}
	
	@Override
	void triggerAction(){
		do{
			super.triggerAction();
			System.out.print("Enter The Name Of The Student: ");
			String studentName = Main.sc.next();
			
			System.out.print("Enter The Email Of The Student: ");
			String studentEmail = Main.sc.next();
			
			if(studentName.length() > Main.longestNameLength) {
				Main.longestNameLength = studentName.length();
			}
			Student newStudent = new Student();
			newStudent.studentName = studentName;
			
			for(String i : Main.mySchool.subjectsNamesList) {
				Subject subject = new Subject();
				subject.subjectName = i;
				newStudent.studentSubjectsList.add(subject);
			}
			
			Main.mySchool.studentsList.put(studentEmail, newStudent);
		}while(Main.repeatProcess("Students"));
	}
}


class EditGradesItem extends MenuItem{
	EditGradesItem(){
		this.title = "Edit Grades.";
	}
	
	@Override
	void triggerAction(){
		super.triggerAction();
		if(Main.mySchool.studentsList.isEmpty()) {
			System.out.println("Make Sure You Have Added Students Before.");
		}
		else if(Main.mySchool.subjectsNamesList.isEmpty()){
			System.out.println("Make Sure You Have Added Subjects Before.");
		}
		else {
			Menu newMenu = new StudentsMenu();
			Main.menues.push(newMenu);
		}
	}
}


class SearchItem extends MenuItem{
	SearchItem(){
		this.title = "Search A Word.";
	}
	
	@Override
	void triggerAction(){
		super.triggerAction();
		
		try {
			String path = "transcript-folder"+File.separator+"Transcript.txt";
			File file = new File(path);
			if (file.exists()) {
				
				System.out.print("Enter The Word You Want To Search For In The Transcript: ");
				String input = Main.sc.next();
				FileReader myReader = new FileReader(path);
				
				int fileIterator;
				int wordIterator = 0;
				int wordCount = 0; 
				
				while((fileIterator = myReader.read()) != -1) {
					if((char)fileIterator == input.charAt(wordIterator)) {
						wordIterator++;
						if(wordIterator == input.length()-1) {
							wordCount++;
							wordIterator = 0;
						}
					}
					else {
						wordIterator = 0;
					}
					
				}
				
				System.out.println("The Word " + input + "Exists In The Transcript "
						+ wordCount + " Times");
				myReader.close();
			}
			
			else {
				System.out.println("There Is No Saved Data, Try Printing And Saving Before.");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}


class PrintItem extends MenuItem{
	PrintItem(){
		this.title = "Print And Save Transcript.";
	}
	@Override
	void triggerAction(){
		super.triggerAction();
		if(Main.mySchool.schoolName.isBlank()) {
			System.out.println("Enter A Name For The School First.");
		}
		else if(Main.mySchool.studentsList.isEmpty()) {
			System.out.println("Please Add Some Students First.");
		}
		else if(Main.mySchool.subjectsNamesList.isEmpty()) {
			System.out.println("Please Add Some Subjects First.");
		}
		else {
			try {
				
				String path = "transcript-folder";
				File theDir = new File(path);
				if (!theDir.exists()){
				    theDir.mkdirs();
				}
				FileWriter myWriter = new FileWriter(path+File.separator+"Transcript.txt");	
				
				System.out.println("Studens Grades In " + Main.mySchool.schoolName + ":.....");
				myWriter.write("Studens Grades In " + Main.mySchool.schoolName + ":.....\n\n");
				
				int tableWidth = Main.longestNameLength;
				for (int i = 0; i < Main.longestNameLength;i++){
					System.out.print(" ");
					myWriter.write(" ");
				}
				for(String i : Main.mySchool.subjectsNamesList){
					System.out.print("|" + i);
					myWriter.write("|" + i);
					tableWidth += i.length()+1;
				}
				
				System.out.println();
				myWriter.write("\n");
				for(int i = 0; i < tableWidth; i++){
					System.out.print("=");
					myWriter.write("=");
				}
				
				System.out.println();
				myWriter.write("\n");
				
				for(Map.Entry<String, Student> i : Main.mySchool.studentsList.entrySet()){
					System.out.print(i.getValue().studentName);
					myWriter.write(i.getValue().studentName);
					
					int extraSpaces = Main.longestNameLength
							-i.getValue().studentName.length();
					for(int j = 0; j < extraSpaces; j++){
						System.out.print(" ");
						myWriter.write(" ");
					}
                
					for(int j = 0; j < i.getValue().studentSubjectsList.size(); j++){
						
						System.out.print("|");
						myWriter.write("|");
						
						int studentGrade = i.getValue().studentSubjectsList.get(j).grade;
						int noDigits = 0;
						if (studentGrade == -1) {
							System.out.print("N/A");
							myWriter.write("N/A");
							noDigits = 3;
						}
						
						else {
							System.out.print(studentGrade);
							myWriter.write(String.valueOf(studentGrade));
							do{
								studentGrade /= 10;
								++noDigits;
							}while(studentGrade!=0);
						}
						
						int spaces = i.getValue().studentSubjectsList.get(j).subjectName.length() 
								- noDigits;
						for (int k = 0; k < spaces; k++){
							System.out.print(" ");
							myWriter.write(" ");
						}
					}
					
					System.out.println();
					myWriter.write("\n");
					
					for(int j = 0; j < tableWidth; j++){
						System.out.print("-");
						myWriter.write("-");
					}
					
					System.out.println();
					myWriter.write("\n");
				}
				myWriter.close();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}


class StatisticsItem extends MenuItem{
	StatisticsItem(){
		this.title = "App Statistics.";
	}
	
	@Override
	void triggerAction(){
		super.triggerAction();
		for (MenuItem i : Main.menues.peek().menuItems) {
			System.out.println(i.title + ": " + i.clicks);
		}
	}
}


class SerializeStudentsDataItem extends MenuItem{
	SerializeStudentsDataItem(){
		this.title = "Serialize Student Data.";
	}
	
	@Override
	void triggerAction(){
		super.triggerAction();
		try {
			FileOutputStream fos = new FileOutputStream("Serialized Data.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(Main.mySchool.studentsList);
			
			oos.flush();
			oos.close();
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
}


class DeserializeStudentsDataItem extends MenuItem{
	DeserializeStudentsDataItem(){
		this.title = "Deserialize Student Data.";
	}
	
	@Override
	void triggerAction(){
		super.triggerAction();
		try {
			String path = "Serialized Data.txt";
			File file = new File(path);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream("Serialized Data.txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				HashMap<String,Student> deserializedStudentsData = new HashMap<String,Student>();
				deserializedStudentsData = (HashMap<String, Student>) ois.readObject();
				
				for(Map.Entry<String, Student> i : deserializedStudentsData.entrySet()) {
					System.out.println(i.getValue().studentName + " " + "(" +i.getKey()+ "): ");
					
					for(Subject j : i.getValue().studentSubjectsList) {
						System.out.print(j.subjectName +":....."+ j.grade);
					}
					System.out.println("\n--------------------------------------");
					
				}
			}
			else {
				System.out.println("There Is No Serialized File To Read");
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
}

//Tests


class ExitItem extends MenuItem{
	ExitItem(String title){
		this.title = title;
	}
	
	@Override
	void triggerAction(){
		Main.menues.pop();
	}
}
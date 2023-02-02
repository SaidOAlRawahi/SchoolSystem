package src;

import java.util.*;

abstract class Menu{
	String title;
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	void showMenu(){
		System.out.println(title);
		for (int i = 0; i < menuItems.size(); i++){
			System.out.println("["+(i+1)+"]" + menuItems.get(i).title);
		}
	}
}

class  MainMenu extends Menu{
	MainMenu(){
		this.title = "\n Select an Option.";
		this.menuItems.add(new SchoolDetailsItem());
		this.menuItems.add(new StudentDetailsItem());
		this.menuItems.add(new EditGradesItem());
		this.menuItems.add(new SearchItem());
		this.menuItems.add(new PrintItem());
		this.menuItems.add(new StatisticsItem());
		this.menuItems.add(new SerializeStudentsDataItem());
		this.menuItems.add(new DeserializeStudentsDataItem());
		this.menuItems.add(new ExitItem("Exit."));
	}
}
class  SchoolDetailsMenu extends Menu{
	SchoolDetailsMenu(){
		this.title = "\n Select an Option.";
		this.menuItems.add(new SchoolNameItem());
		this.menuItems.add(new SubjectEntryItem());
		this.menuItems.add(new ExitItem("Go Back"));
	}
}

class  StudentsMenu extends Menu{
	StudentsMenu(){
		this.title = "\n Select A Student.";
		for(String key : Main.mySchool.studentsList.keySet()) {
			this.menuItems.add(new StudentItem(key));
		}
	}
}

class  SubjectsMenu extends Menu{
	SubjectsMenu(String studentKey){
		
		this.title = "\n Select A Subject To Edit For " 
				+ Main.mySchool.studentsList.get(studentKey).studentName+".";
		for(int i = 0 ; i < Main.mySchool.studentsList.get(studentKey)
				.studentSubjectsList.size(); i++) {
			this.menuItems.add(new SubjectItem(i, studentKey));
		}
	}
}

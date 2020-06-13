import sqlite3
from Student import Student

conn= sqlite3.connect('StudentDB.db')

def database():
  c = conn.cursor()
  c.execute('''create table  if not exists StudentDB(
    StudentId INTEGER primary key ,
    Firstname varchar(25),
    Lastname varchar(25),
    GPA numeric,
    major varchar(25),
    FacultyAdvisor varchar(25))'''
    )

def display_stu():
  c = conn.cursor()
  c.execute("Select * from StudentDB")
  return c.fetchall()

def create_stu():
  c = conn.cursor()
  with conn:
    Firstname = input("Enter the students first name: ")
    Lastname = input("Enter the studnets last name: ")
    GPA = input("Enter students GPA: ")
    major = input("Enter students major: ")
    FacultyAdvisor = input("Enter students faculty advisor: ")
    stu = Student(Firstname, Lastname, GPA, major, FacultyAdvisor)

    c.execute("insert into StudentDB(Firstname,Lastname,GPA,major,FacultyAdvisor)"
              "values (?,?,?,?,?)", (stu.stuinfo))

    print("New student created")

def update_stu():
  while True:
    c = conn.cursor()
    with conn:
      choice = int(input("\nDo you want to update Major(1), Advisor(2), or back to menu(3): "))
      if choice == 1:
        StudentId = input("Enter the students id: ")
        major = input("What is the new major: ")
        c.execute("UPDATE StudentDB SET major = '{}' WHERE StudentId ='{}'".format(major,StudentId))
        print("Major changed")

      elif choice ==2:
        StudentId = input("Enter the students id: ")
        FacultyAdvisor = input("Who is the new advisor: ")
        c.execute("UPDATE StudentDB SET FacultyAdvisor = '{}' WHERE StudentId ='{}'".format(FacultyAdvisor, StudentId))
        print("Advisor changed")

      elif choice==3:
        break

def delete_stu():
  c = conn.cursor()
  with conn:
    StudentId=int(input("Enter an id to delete: "))
    c.execute("Delete from StudentDB Where StudentId ='{}'".format(StudentId))
  print("Student deleted")

def search_stu():
  while True:
    c = conn.cursor()
    with conn:
      choice = int(input("Do you want to search by Major(1), GPA(2), Advisor(3) or back to menu(4): "))
      if choice==1:
        major = input("Enter a Major: ")
        c.execute("Select * from StudentDB where major ='{}'".format(major))
        print c.fetchall()

      elif choice==2:
        GPA=int(input("Enter the GPA: "))
        c.execute("Select * from StudentDB where GPA ='{}'".format(GPA))
        print c.fetchall()

      elif choice==3:
        FacultyAdvisor = input("Enter a Advisor: ")
        c.execute("Select * from StudentDB where FacultyAdvisor ='{}'".format(FacultyAdvisor))
        print c.fetchall()

      elif choice==4:
        break

def choices():
  while True:
    c = conn.cursor()
    print("\nWelcome to student the database!")
    print("1. Display all the students")
    print("2. Create a new student")
    print("3. Update a student's major or advisor")
    print("4. Delete a student by Student id")
    print("5. Search a student by Major, GPA, and advisor")
    print("6. Exit")
    choice = input("Enter a choice: ")

    if choice==1:
      emps=display_stu()
      print(emps)

    elif choice==2:
      create_stu()

    elif choice==3:
      update_stu()

    elif choice==4:
      delete_stu()

    elif choice==5:
      search_stu()

    elif choice==6:
      break


database()
choices()
conn.close()
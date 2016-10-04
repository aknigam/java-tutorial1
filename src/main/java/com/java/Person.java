package com.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.w3c.dom.ls.LSInput;

public class Person {

	public enum Sex {
		MALE, FEMALE
	}
	
	public Person(Sex a, String n, LocalDate bd, String ea, int ag) {
		gender = a;
		name = n;
		birthday = bd;
		age = ag;
		emailAddress = ea;
	}

	String name;
	LocalDate birthday;
	Sex gender;
	String emailAddress;
	int age;

	public int getAge() {
		return age;
	}

	public void printPerson() {
		System.out.println(name+" "+age+" "+ gender+" "+ emailAddress);
	}
	
	public static void printPersonsOlderThan(List<Person> roster, int age) {
	    for (Person p : roster) {
	        if (p.getAge() >= age) {
	            p.printPerson();
	        }
	    }
	}
	
	public static void printPersonsWithinAgeRange(List<Person> roster, int low, int high) {
		for (Person p : roster) {
			if (low <= p.getAge() && p.getAge() < high) {
				p.printPerson();
			}
		}
	}
	
	public static <X, Y> void processElements(
			Iterable<X> source,
			Predicate<X> tester,
			Function <X, Y> mapper,
			Consumer<Y> block) {
		for (X p : source) {
			if (tester.test(p)) {
				Y data = mapper.apply(p);
				block.accept(data);
			}
		}
	}
	
	public static void main(String[] args) {
		List<Person> roster = getPersons();
		processElements(
			    roster,
			    p -> p.getGender() == Sex.MALE
			        && p.getAge() >= 18
			        && p.getAge() <= 25,
			    p -> p.getEmailAddress(),
			    email -> System.out.println(email)
			);
		
		roster
	    .stream()
	    .filter(
	        p -> p.getGender() == Sex.MALE
	            && p.getAge() >= 18
	            && p.getAge() <= 25)
	    .map(p -> p.getEmailAddress())
	    .forEach(email -> System.out.println(email));
	}

	private static List<Person> getPersons() {
		List<Person> ps = new ArrayList();
		ps.add(new Person(Sex.MALE, "Anand", LocalDate.now(), "a@gmail.com", 20));
		ps.add(new Person(Sex.FEMALE, "Poonam", LocalDate.now(), "b@gmail.com", 21));
		ps.add(new Person(Sex.MALE, "Nigam", LocalDate.now(), "c@gmail.com", 22));
		ps.add(new Person(Sex.FEMALE, "Priya", LocalDate.now(), "d@gmail.com", 23));
		ps.add(new Person(Sex.MALE, "Prakhar", LocalDate.now(), "e@gmail.com", 19));
		return ps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

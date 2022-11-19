package com.himewel.design.builder

case class Person(
  firstName: Option[String] = None,
  lastName: Option[String] = None,
  age: Option[Int] = None,
  position: Option[String] = None,
  companyName: Option[String] = None
) {
  def copy: Person = this.copy

  override def toString: String = {
    s"I am ${firstName.get} ${lastName.get}, I have ${age.get} years old \n" +
      s"Currently I work as ${position.get} at ${companyName.get}"
  }
}

class PersonBuilder(person: Person = new Person()) {
  def personal: PersonalDataBuilder = new PersonalDataBuilder(this.person)
  def job: JobDataBuilder           = new JobDataBuilder(this.person)
  def build(): Person               = this.person
}

case class PersonalDataBuilder(person: Person) extends PersonBuilder(person) {
  def firstName(firstName: String): PersonalDataBuilder =
    this.copy(person.copy(firstName = Some(firstName)))
  def lastName(lastName: String): PersonalDataBuilder =
    this.copy(person.copy(lastName = Some(lastName)))
  def age(age: Int): PersonalDataBuilder =
    this.copy(person.copy(age = Some(age)))
}

case class JobDataBuilder(person: Person) extends PersonBuilder(person) {
  def position(position: String): JobDataBuilder =
    this.copy(person.copy(position = Some(position)))
  def companyName(companyName: String): JobDataBuilder =
    this.copy(person.copy(companyName = Some(companyName)))
}

object BuilderFacets {
  def apply(): Unit = {
    val people = new PersonBuilder().personal
      .firstName("Herbert")
      .lastName("Richards")
      .age(34)
      .job
      .position("voice actor")
      .companyName("Álamo")
      .build()

    println(people)
  }
}

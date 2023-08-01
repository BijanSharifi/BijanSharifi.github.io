require 'set'
#Person
class Person
  def initialize(name, age)
    @name=name
    @age=age
  end 
  
  def getAge
    return @age

  end
  
  def setAge(x)
    @age=x
    return self

  end

  def getName
    @name
  end
  
  def to_s
    "Person: name = #{@name}, age = #{@age}"

  end
end



#Student
class Student < Person
  def initialize(name, age, grade)
    @name=name
    @age=age
    @grade=grade
  end 

  def getGrade
    return @grade
  end 

  def changeGrade(x)
    @grade=x
    return self

  end
  def to_s
    "Student: name = #{@name}, age = #{@age}, grade = #{@grade}"
  end
end



#Staff
class Staff < Person
  def initialize(name, age, position)
    @name=name
    @age=age
    @position=position
  end 

  def getPosition
    return @position
  end

  def changePosition(newPosition)
    @position=newPosition
    return self
  end 
  def to_s
    "Staff: name = #{@name}, age = #{@age}, position = #{@position}"
  end 
end 



#Roster
class Roster
  def initialize
    @roster=Set.new
  end

  def add(person)
    @roster.add(person)
    
  end 

  def size
    @roster.size
  end

  def remove(person)
    @roster.remove(person)
    
  end

  def getPerson(name)
    person=@roster.find{|person| person.getName==name }


  end

  def to_s
    @roster.to_s


  end

  def map
    if block_given?
    @roster.map do |person|
      yield(person)
      end 
    end 
    

  end 
end 

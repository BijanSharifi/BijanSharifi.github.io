
public class ComplexNumber {
	
	/* STUDENTS:  You may NOT add any further instance or static variables! */
	private final MyDouble real;   // To be initialized in constructors
	private final MyDouble imag;   // To be initialized in constructors
	
	
	/* STUDENTS: Put your methods here, as described in the project description. */
	
	//Constructor that initializes the parameters for ComplexNumber
	public ComplexNumber(MyDouble realIn, MyDouble imagIn){
		real=realIn;
		imag=imagIn;
	}
	//Constructor that initializes a real number and sets imag number to 0
	public ComplexNumber(MyDouble realIn){
		real=realIn;
		imag=new MyDouble(0.0);
	}
	//Copy constructor for ComplexNumber
	public ComplexNumber(ComplexNumber other){
		real=other.real;
		imag=other.imag;
	}
	//Getter which gets the real part of the complex number
	public MyDouble getReal(){
		return real;
	}
	//Getter which gets the imag part of complex number
	public MyDouble getImag(){
		return imag;
	}
	/* Instance Method which adds two complex numbers together
	 * by adding the real parts to each other and the imag parts
	 * to each other
	 * (a+bi)+(c+di)
	 */
	public ComplexNumber add(ComplexNumber otherCom){
		return new ComplexNumber(real.add(otherCom.real)
				,imag.add(otherCom.imag));
	}
	/* Instance Method which subtracts two complex  numbers 
	 * by subtracting the real parts and subtracting the imag parts
	 * (a+bi)-(c-di)
	 */
	public ComplexNumber subtract(ComplexNumber otherCom){
		return new ComplexNumber(real.subtract(otherCom.real),
				imag.subtract(otherCom.imag));
	}
	/* Instance Method which multiplies two complex numbers together
	 * by foiling the two complex numbers 
	 * (a+bi)(c+di)
	 */
	public ComplexNumber multiply(ComplexNumber otherCom) {
		return new ComplexNumber(real.multiply(otherCom.real)
				.subtract(imag.multiply(otherCom.imag)),
				real.multiply(otherCom.imag).
				add(imag.multiply(otherCom.real)));
				
	}
	/*Instance Method which divides two complex numbers together
	 * by dividing the two numerators by the denominator
	 * 
	 */
	public ComplexNumber divide(ComplexNumber otherCom) {
		MyDouble num1, num2, den;
		num1= (real.multiply(otherCom.real).add(imag.multiply
				(otherCom.imag)));
		num2= (imag.multiply(otherCom.real).subtract
				(real.multiply(otherCom.imag)));
		den= (otherCom.real.multiply(otherCom.real)
				.add(otherCom.imag.multiply(otherCom.imag)));
		return new ComplexNumber(num1.divide(den),
				num2.divide(den));
		
		
		
		
		
	}
	/*Equals method that checks both parts of the complex number
	 * and if they are both equal then it returns true, otherwise 
	 * it returns false
	 */
	public boolean equals(ComplexNumber other) {
		if (real.equals(other.real) && imag.equals
				(other.imag)) {
			return true;
		}else {
			return false;
		}
	}
	/*Similar to equals method but it tells user if number is the same, 
	 * greater, or less than current object. First part is the same as equals method
	 * but it returns 0 if they are the same. The next part takes the norm 
	 * of both complex numbers and if the norm is less than 0 it returns -1
	 * and if it greater than 0 it returns 1
	 * 
	 */
	public int compareTo(ComplexNumber otherCom) {
		if (real.equals(otherCom.real) && 
				imag.equals(otherCom.imag)) {
			return 0;
		}
		int normCompare=norm(this).
				compareTo(norm(otherCom));
		if (normCompare<0) {
			return -1;
		}else {
			return 1;
		}
	}
	/*Method that will take both the real and imag current objects and 
	 * compare them to zero and if they are both greater than zero a plus
	 * sign is added in between the real and imag parts. If the real part 
	 * is greater than zero and the imag part is greater than zero there is 
	 * no sign added because the MyDouble for the imag part already has a 
	 * negative sign. If the real part is negative and the imag part is 
	 * positive there is no sign placed in front of the real part because 
	 * there is already a negative sign and there is a plus sign inf ront of
	 * imag part. If they are both negative no sign is needed.
	 */
	public String toString() {
		
		int value1=real.compareTo(new MyDouble(0.0));
		int value2=imag.compareTo(new MyDouble(0.0));
		
		String completeNumber=null;
		if (value1>0 && value2>0) {
			completeNumber=real.toString()
					+ "+" + imag.toString() +"i";
		}else if (value1>0 && value2<0) {
			completeNumber=real.toString()
					+ imag.toString() +"i";
		}else if (value1<0 && value2>0) {
			completeNumber=real.toString()
					+ "+" + imag.toString()+"i";
		}else {
			completeNumber= real.toString()
					+ imag.toString()+"i";
		}
		return completeNumber;
		
		
	}
	//Squares both numbers and adds them together by using the sqrt method
	 
	 
	public static MyDouble norm(ComplexNumber otherCom) {
		MyDouble a=(otherCom.real.multiply(otherCom.real));
		MyDouble b=(otherCom.imag.multiply(otherCom.imag));
		return (a.add(b).sqrt());
	}
	/*Start of by using a method from the string class that replaced all the empty
	 * space with no spaces. Then used removeSpace to get rid of all 
	 * the spaces near the i part of the imag part of the complex number.
	 * Then used a boolean expression to determine if our string contained
	 * a plus sign. If it has a plus sign the boolean expression is true it removes
	 * the spaces around the plus sign and creates two new MyDoubles that start at 0
	 * and go until the plus sign for the real part. All the spaces are gone up until
	 * the plus sign and then the imag part of this goes from the plus sign to the 
	 * location of i, which is the end of the string or complex number.
	 * It does a similar process for the minus sign, but uses lastIndexOf instead of 
	 * index of because of the location of the minus sign. It then returns the parsed
	 * complex number.
	 * 
	 */
	public static ComplexNumber parseComplexNumber(String a) {
		String removeSpace=a.replaceAll(" ", "" );
		MyDouble parsedReal;
		MyDouble parsedImag;
		int locationOfI=removeSpace.indexOf("i");
		boolean plus=removeSpace.contains("+");
		
		if (plus==true) {
			int plusSign=removeSpace.indexOf("+");
			parsedReal=new MyDouble(Double.parseDouble
					(removeSpace.substring(0, plusSign)));
			parsedImag=new MyDouble(Double.parseDouble
					(removeSpace.substring(plusSign, locationOfI)));
		}else {
			int minusSign=removeSpace.lastIndexOf("-");
			parsedReal=new MyDouble(Double.parseDouble
					(removeSpace.substring(0, minusSign)));
			parsedImag=new MyDouble(Double.parseDouble
					(removeSpace.substring(minusSign, locationOfI)));
		}
		return new ComplexNumber(parsedReal, parsedImag);
		
		
		
		
		
	}
	
	
}
import static org.junit.Assert.*;

import org.junit.Test;


public class PublicTests {

	@Test
	public void testBasicConstructorsAndGetters() {
		
		MyDouble a = new MyDouble(5.7), b = new MyDouble(-3.7);
		MyDouble d = new MyDouble(555.729);
		
		ComplexNumber x = new ComplexNumber(a, b);
		assertTrue(x.getReal().compareTo(a) == 0 && x.getImag().compareTo(b) == 0);
		
		ComplexNumber z = new ComplexNumber(d);
		assertTrue(z.getReal().compareTo(d) == 0 && z.getImag().compareTo(new MyDouble(0)) == 0);
	}
	
	@Test
	public void testCopyConstructor() {
		
		MyDouble a = new MyDouble(5.7), b = new MyDouble(-3.7);
		
		ComplexNumber x = new ComplexNumber(a, b);
		ComplexNumber y = new ComplexNumber(x);
		assertTrue(x != y);     // Check to be sure they are not aliased!
		assertTrue(y.getReal().compareTo(a) == 0 && y.getImag().compareTo(b) == 0);
	}
	//Create a complex number and add them manually and see how it compares to add method
	@Test
	public void testAdd() {
		MyDouble a =new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber com1=new ComplexNumber(a,b);//2+3i
		ComplexNumber com2=new ComplexNumber(c,d);//4-5i
		ComplexNumber com3=new ComplexNumber(a,c);//2+4i
		ComplexNumber com4=new ComplexNumber(b,d);//3-5i
		ComplexNumber add1=com1.add(com2);//6-2i
		ComplexNumber add2=com3.add(com4);//5-i
		ComplexNumber testAdd1=new ComplexNumber(new MyDouble(6.0), new MyDouble(-2.0));
		assertTrue(testAdd1.equals(add1));
		ComplexNumber testAdd2=new ComplexNumber(new MyDouble(5.0), new MyDouble(-1.0));
		assertTrue(testAdd2.equals(add2));
		
		
	}
	//Create complex number and subtract them manually and see how it compares to subtract method
	@Test
	public void testSubtract() {
		MyDouble a=new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber com1=new ComplexNumber(a,b);//2+3i
		ComplexNumber com2=new ComplexNumber(c,d);//4-5i
		ComplexNumber com3=new ComplexNumber(a,c);//2+4i
		ComplexNumber com4=new ComplexNumber(b,d);//3-5i
		ComplexNumber sub1=com1.subtract(com2);//-2+8i
		ComplexNumber sub2=com3.subtract(com4);//-1+9i
		ComplexNumber sub3=com2.subtract(com1);//2-8i
		ComplexNumber sub4=com4.subtract(com3);//1-9i
		ComplexNumber testSub1=new ComplexNumber
				(new MyDouble(-2.0), new MyDouble(8.0));
		assertTrue(testSub1.equals(sub1));
		ComplexNumber testSub2=new ComplexNumber
				(new MyDouble(-1.0), new MyDouble(9.0));
		assertTrue(testSub2.equals(sub2));
		ComplexNumber testSub3=new ComplexNumber
				(new MyDouble(2.0), new MyDouble(-8.0));
		assertTrue(testSub3.equals(sub3));
		ComplexNumber testSub4=new ComplexNumber
				(new MyDouble(1.0), new MyDouble(-9.0));
		assertTrue(testSub4.equals(sub4));
	}
	//Create complex number a foil them and see how it compares to multiply method
	@Test
	public void testMultiply() {
		MyDouble a=new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber com1=new ComplexNumber(a,b);//2+3i
		ComplexNumber com2=new ComplexNumber(c,d);//4-5i
		ComplexNumber com3=new ComplexNumber(a,c);//2+4i
		ComplexNumber com4=new ComplexNumber(b,d);//3-5i
		ComplexNumber mult1=com1.multiply(com2);//22+2i
		ComplexNumber mult2=com3.multiply(com4);//26+2i
		ComplexNumber mult3=com1.multiply(com3);//-6+14i
		ComplexNumber mult4=com2.multiply(com4);//-13-35i
		ComplexNumber testMult1=new ComplexNumber
				(new MyDouble(22.0), new MyDouble(2.0));
		assertTrue(testMult1.equals(mult1));
		ComplexNumber testMult2=new ComplexNumber
				(new MyDouble(26.0), new MyDouble(2.0));
		assertTrue(testMult2.equals(mult2));
		ComplexNumber testMult3=new ComplexNumber
				(new MyDouble(-6.0), new MyDouble(14.0));
		assertTrue(testMult3.equals(mult3));
		ComplexNumber testMult4=new ComplexNumber
				(new MyDouble(-13.0), new MyDouble(-35.0));
		assertTrue(testMult4.equals(mult4));
		
		
	}
	//Create complex number and divide them and see how it compares to divide method
	@Test
	public void testDivide() {
		MyDouble a=new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber com1=new ComplexNumber(a,b);
		ComplexNumber com2=new ComplexNumber(c,d);
		ComplexNumber com3=new ComplexNumber(a,c);
		ComplexNumber com4=new ComplexNumber(b,d);
		ComplexNumber div1=com1.divide(com2);//-0.170731707317073+0.53658536585365i
		ComplexNumber div2=com3.divide(com4);//-0.411764705882353+0.647058823529412i
		ComplexNumber div3=com1.divide(com3);//0.8-0.1i
		ComplexNumber div4=com2.multiply(com4);//1.088235294117647+0.147058823529412i
		ComplexNumber testDiv1=new ComplexNumber
				(new MyDouble(-0.170731707317073), 
						new MyDouble(0.53658536585365));
		assertTrue(testDiv1.equals(div1));
		ComplexNumber testDiv2=new ComplexNumber
				(new MyDouble(-0.411764705882353), 
						new MyDouble(0.647058823529412));
		assertTrue(testDiv2.equals(div2));
		ComplexNumber testDiv3=new ComplexNumber
				(new MyDouble(0.8), new MyDouble(-0.1));
		assertTrue(testDiv3.equals(div3));
		ComplexNumber testDiv4=new ComplexNumber
				(new MyDouble(1.088235294117647), 
						new MyDouble(0.147058823529412));
		assertTrue(testDiv4.equals(div4));
	}
	//Create two complex numbers that are the same and use the compareTo method to see
	//if they are the same
	@Test
	public void testEqComp() {
		MyDouble a=new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber orgCom=new ComplexNumber(a,b);
		ComplexNumber com1=new ComplexNumber(a,b);
		assertTrue(orgCom.compareTo(com1)==0);
		ComplexNumber com2=new ComplexNumber(b,c);
		ComplexNumber com3=new ComplexNumber(b,c);
		assertTrue(com2.compareTo(com3)==0);
		ComplexNumber com4=new ComplexNumber(c,d);
		ComplexNumber com5=new ComplexNumber(c,d);
		assertTrue(com4.compareTo(com5)==0);
		ComplexNumber com6=new ComplexNumber(d,a);
		ComplexNumber com7=new ComplexNumber(d,a);
		assertTrue(com6.compareTo(com7)==0);
	
	}
	//Create a complex number and take the norm of that number and then
	//manually take the norm of the complex number and see if they are equal
	@Test
	public void testNorm() {
		MyDouble a=new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber com1=new ComplexNumber(a,b);
		ComplexNumber com2=new ComplexNumber(c,d);
		ComplexNumber com3=new ComplexNumber(a,c);
		ComplexNumber com4=new ComplexNumber(b,d);
		MyDouble norm1=ComplexNumber.norm(com1);
		System.out.println(norm1);
		System.out.println(Math.sqrt(13));
		assertTrue(norm1.equals(new MyDouble(Math.sqrt(13))));
		MyDouble norm2=ComplexNumber.norm(com2);
		assertTrue(norm2.equals(new MyDouble(Math.sqrt(41))));
		MyDouble norm3=ComplexNumber.norm(com3);
		assertTrue(norm3.equals(new MyDouble(Math.sqrt(20))));
		MyDouble norm4=ComplexNumber.norm(com4);
		assertTrue(norm4.equals(new MyDouble(Math.sqrt(34))));
		
		
		
	}
	//Construct a complex number and then see if it prints the proper 
	//complex number
	@Test
	public void testToString() {
		MyDouble a=new MyDouble(2.0);
		MyDouble b=new MyDouble(3.0);
		MyDouble c=new MyDouble(4.0);
		MyDouble d=new MyDouble(-5.0);
		ComplexNumber com1=new ComplexNumber(a,b);
		System.out.println(com1);
		ComplexNumber com2=new ComplexNumber(c,d);
		System.out.println(com2);
		ComplexNumber com3=new ComplexNumber(d,a);
		System.out.println(com3);
		
		
	}
	//Create a string with some with spaces and use the parse method and see if it 
	//equal to creating a new complex number with no spaces
	@Test
	public void testParse() {
		String string1="2+3i";
		ComplexNumber com1=ComplexNumber.parseComplexNumber(string1);
		ComplexNumber com2=new ComplexNumber
				(new MyDouble(2.0), new MyDouble(3.0));
		assertTrue(com1.equals(com2));
		String string2="    2+3i  ";
		ComplexNumber com3=ComplexNumber.parseComplexNumber(string2);
		ComplexNumber com4=new ComplexNumber
				(new MyDouble(2.0), new MyDouble(3.0));
		assertTrue(com3.equals(com4));
		String string3="2-3i";
		ComplexNumber com5=ComplexNumber.parseComplexNumber(string3);
		ComplexNumber com6=new ComplexNumber
				(new MyDouble(2.0), new MyDouble(-3.0));
		assertTrue(com5.equals(com6));
		String string4="   2   -   3i   ";
		ComplexNumber com7=ComplexNumber.parseComplexNumber(string4);
		ComplexNumber com8=new ComplexNumber
				(new MyDouble(2.0), new MyDouble(-3.0));
		assertTrue(com7.equals(com8));
		//System.out.println(com7);
		String string5="  -2      +     3i";
		ComplexNumber com9=ComplexNumber.parseComplexNumber(string5);
		ComplexNumber com10=new ComplexNumber
				(new MyDouble(-2.0), new MyDouble(3.0));
		assertTrue(com9.equals(com10));
		String string6="-   2    -    3i";
		ComplexNumber com11=ComplexNumber.parseComplexNumber(string6);
		ComplexNumber com12=new ComplexNumber
				(new MyDouble(-2.0), new MyDouble(-3.0));
		assertTrue(com11.equals(com12));
		

		
		
		
		
		
	}
}

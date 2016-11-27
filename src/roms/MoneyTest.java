/**
 * 
 */
package roms;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author pbj
 *
 */
public class MoneyTest {
    
    @Test
    public void testZero() {
        Money zero = new Money();
        assertEquals("0.00", zero.toString());
    }
    
    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for the Money class below.
     */
    
    //tests the empty constructor. 
    @Test
    public void testEmptyConstructor() {
        //It should return a non null object with value 0
        Money money = new Money();
        //check if empty constructor assigns the 0 value by default
        assertEquals("0.00", money.toString());
    }
    
    //tests the constructor with string argument. 
    @Test
    public void testStringConstructor(){
        //It should return a non null object with a rounded value of the given string, rounded and appended with 
        //trailing 0s if needed
        Money money = new Money("2.78");
        //check if this constructor returns null
        assertNotNull(money);
        //check if the value reflects the given string
        assertEquals("2.78", money.toString());
    }
    
    //tests that the add method returns correct results
    @Test
    public void testAdd() {
        Money money = new Money("2.15");
        Money sum=money.add(new Money("3.55"));
        //money should be 2.15+3.55=5.70
        assertEquals("5.70", sum.toString());
    }
    
    //tests that the multiply method returns correct results
    @Test
    public void testMultiply() {
        Money money = new Money("3.25");
        //multiplying with positive integer
        Money product=money.multiply(5);
        //should result in 3.25*5=16.25
        assertEquals("16.25", product.toString());
        //multiplying with negative integer
        product=product.multiply(-1);
        //should result in 16.25*(-1)=-16.25
        assertEquals("-16.25", product.toString());
        //multiplying with 0
        product=product.multiply(0);
        //should be 0.00
        assertEquals("0.00", product.toString());
    }
    
    //tests that the addPercent method returns correct results
    @Test
    public void testAddPercent() {
        Money money = new Money("100");
        //adding positive 5%
        Money result=money.addPercent(5);
        //should result in 105.00
        assertEquals("105.00", result.toString());
        //adding negative 10%
        result=result.addPercent(-10);
        //should result in 105.00*0.9=94.50
        assertEquals("94.50", result.toString());
        //adding zero percent
        result=result.addPercent(0);
        //should be unchanged
        assertEquals("94.50", result.toString());
    }
    
    //tests the toString method for proper results
    @Test
    public void testToString() {
        //Checking that the value assigned is the same as the value presented
        Money money=new Money("0.55");
        assertEquals("0.55", money.toString());
    }
    
    //tests the toString method for proper rounding
    @Test
    public void testRounding() {
        //checking rounding to 2 decimal places when more than 2 decimal places given and 
        //the 3rd digit > 5
        Money money=new Money("0.127");
        assertEquals("0.13", money.toString());
        //checking rounding to 2 decimal places when more than 2 decimal places given and 
        //the 3rd digit < 5
        money=new Money("0.123");
        assertEquals("0.12", money.toString());
        //checking rounding to 2 decimal places when more than 2 decimal places given and 
        //the 3rd digit = 5
        money=new Money("0.125");
        //should round to even digit
        assertEquals("0.12", money.toString());
    }
    
    //tests the toString method for proper addition of trailing zeros
    @Test
    public void testTrailingZeros() {
        //checking addition of 2 trailing 0s
        Money money = new Money("4");
        assertEquals("4.00", money.toString());
        //checking addition of 1 trailing 0s
        money=new Money("4.1");
        assertEquals("4.10", money.toString());
        //checking that there's no change to the number 
        //of decimal digits when the number has 2 decimal digits
        money=new Money("4.12");
        assertEquals("4.12", money.toString());
    }
    

   /*
    * Put all class modifications above.
    ***********************************************************************
    * END MODIFICATION AREA
    ***********************************************************************
    */


}

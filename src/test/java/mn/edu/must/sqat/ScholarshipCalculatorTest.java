package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {
    ScholarshipCalculator c = new ScholarshipCalculator();
    @Test 
    void  letterGrade_A() {
        assertEquals("A", c.letterGrade(95));
    }

    @Test 
    void letterGrade_B() {
        assertEquals("B", c.letterGrade(85));
    }
    @Test 
    void gpa_two(){
        assertEquals(3.5, c.gpa(new double[]{95,85}), 0.001);
    }
    @Test 
    void schol_100(){
        assertEquals(100, c.scholarship(3.9, 16, false));

    }
    @Test 
    void schol_voilation(){
        assertEquals(0, c.scholarship(3.9, 16, true));
    }
}
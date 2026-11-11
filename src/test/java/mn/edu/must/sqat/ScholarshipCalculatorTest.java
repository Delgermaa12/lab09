package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {
    ScholarshipCalculator c = new ScholarshipCalculator();

    // A heseg
    @Test 
    void letterGrade_A(){ 
        assertEquals("A", c.letterGrade(95)); 
    }
    @Test 
    void letterGrade_B(){ 
        assertEquals("B", c.letterGrade(85)); 
    }
    @Test 
    void gpa_two(){ 
        assertEquals(3.5, c.gpa(new double[]{95, 85}), 0.001); 
    }
    @Test 
    void schol_100(){ 
        assertEquals(100, c.scholarship(3.9, 16, false)); 
    }
    @Test 
    void schol_voilation(){ 
        assertEquals(0, c.scholarship(3.9, 16, true)); 
    }

    // B heseg 
    @Test void letterGradeC() { assertEquals("C", c.letterGrade(75)); }
    @Test void letterGradeD() { assertEquals("D", c.letterGrade(65)); }
    @Test void letterGradeF() { assertEquals("F", c.letterGrade(30)); }
    @Test void letterGradeNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.letterGrade(-1));
    }
    @Test void letterGradeAbove100Throws() {
        assertThrows(IllegalArgumentException.class, () -> c.letterGrade(101));
    }

    @Test void negativeGpaThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.scholarship(-0.1, 10, false));
    }
    @Test void gpaAboveFourThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.scholarship(4.1, 10, false));
    }
    @Test void negativeCreditsThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.scholarship(3.5, -1, false));
    }
    @Test void violationReturnsZero() { assertEquals(0, c.scholarship(3.9, 20, true)); }
    @Test void gpa38Credits15Gives100() { assertEquals(100, c.scholarship(3.9, 16, false)); }
    @Test void gpa38LowCreditsFallsTo70() { assertEquals(70, c.scholarship(3.9, 14, false)); }
    @Test void gpa34Credits12Gives70() { assertEquals(70, c.scholarship(3.5, 12, false)); }
    @Test void gpa34LowCreditsFallsTo50() { assertEquals(50, c.scholarship(3.5, 11, false)); }
    @Test void gpa30Gives50() { assertEquals(50, c.scholarship(3.2, 20, false)); }
    @Test void lowGpaGivesZero() { assertEquals(0, c.scholarship(2.9, 20, false)); }

    @Test void gpaOfFailingScoreIsZero() { assertEquals(0.0, c.gpa(new double[]{30}), 0.001); }
        @Test void gpaNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.gpa(null));
    }
    @Test void gpaEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.gpa(new double[]{}));
    }
    @Test void gpaOfCScoreIsTwo() { assertEquals(2.0, c.gpa(new double[]{75}), 0.001); }
    @Test void gpaOfDScoreIsOne() { assertEquals(1.0, c.gpa(new double[]{65}), 0.001); }

    // C heseg
    @Test void gpaLoopZero_nullThrows() {
    assertThrows(IllegalArgumentException.class, () -> c.gpa(null));
    }
    @Test void gpaLoopZero_emptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> c.gpa(new double[]{}));
    }
    @Test void gpaLoopOne_singleScoreIsFour() {
        assertEquals(4.0, c.gpa(new double[]{95}), 0.001);
    }
    @Test void gpaLoopMany_fourScores() {
        assertEquals(2.5, c.gpa(new double[]{95, 85, 75, 65}), 0.001);
    }

    // D heseg
    @Test 
    void boundary_60_isD(){ 
        assertEquals("D", c.letterGrade(60)); 
    }

    @Test 
    void boundary_5999_isF(){ 
        assertEquals("F", c.letterGrade(59.99)); 
    }

    @Test 
    void boundary_6999_isD(){ 
        assertEquals("D", c.letterGrade(69.99)); 
    }

    @Test
    void boundary_0_isF(){ 
        assertEquals("F", c.letterGrade(0)); 
    }

    @Test 
    void boundary_100_isA(){ 
        assertEquals("A", c.letterGrade(100)); 
    }

    @Test 
    void boundary_8999_isB(){ 
        assertEquals("B", c.letterGrade(89.99)); 
    }

    @Test 
    void boundary_90_isA(){ 
        assertEquals("A", c.letterGrade(90)); 
    }
}
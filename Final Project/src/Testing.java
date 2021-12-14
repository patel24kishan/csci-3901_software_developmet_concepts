/*  PLEASE NOTE:
    Before testing, do change the file Path of config files in each test case with your own path
*/

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Final Project Testing")
public class Testing {

    @Nested
    @DisplayName("Input Validation for MobileDevice class Methods")
    class ValidationForMobileDeviceMethods {
        @Test
        @DisplayName("Correct input for MobileDevice constructor")
        public void goodInput() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
        }

        @Test
        @DisplayName("Incorrect file path for MobileDevice object")
        public void badMobileDeviceInput() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertFalse(M1.recordContact(M2.device_Hash_ID, 23, 10));
        }

        @Test
        @DisplayName("Incorrect individual variable of size less than 64 in recordContact()")
        public void badIndividualInput() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertFalse(M1.recordContact("1234", 23, 10));
        }
        
        @Test
        @DisplayName("Correct individual variable, date & duration in recordContact()")
        public void goodIndividualInput() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
        }

        @Test
        @DisplayName("Incorrect and empty testhash variable of size less than 6 in positiveTest()")
        public void badTeshHash() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.positiveTest("KLmT2"));

            assertFalse(M1.positiveTest(""));
        }

        @Test
        @DisplayName("Correct testhash variable of size 6  in positiveTest()")
        public void goodTeshHash() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertTrue(M1.positiveTest("H5FtWx"));
        }

        @Test
        @DisplayName("Min Boundary Case of recordContact()")
        public void minBoundaryOfrecordContact() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertFalse(M1.recordContact("", 0, 0));

            assertFalse(M1.recordContact("", -2147483647, -2147483647));
        }

        @Test
        @DisplayName("Max Boundary Case of recordContact()")
        public void maxBoundaryOfrecordContact() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 2147483647 , 2147483647));
        }
    }

    @Nested
    @DisplayName("Input Validation for Goverment class Methods")
    class ValidationForGovermentMethods {

        @Test
        @DisplayName("Incorrect file path for Goverment object")
        public void badGovInput() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertFalse(contactTracer.clearDataBase());
            assertFalse(M1.recordContact(M2.device_Hash_ID, 23, 10));
        }

        @Test
        @DisplayName("Correct input variables in recordTestResult()")
        public void goodrecordTestResult() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData());
           
            assertTrue(contactTracer.recordTestResult("CV3SZX", 12, false));
            assertTrue(contactTracer.recordTestResult("DcXe4T", 67, true));
        }

        @Test
        @DisplayName("Incorrect and empty string teshHash variable in recordTestResult()")
        public void badTestHash() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData());
            assertFalse(contactTracer.recordTestResult("CV3ZX", 12, false)); 
      
            assertFalse(contactTracer.recordTestResult("", 34, true));
        }

        @Test
        @DisplayName("Incorrect date variable in recordTestResult()")
        public void badTestDate() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData()); 

            assertFalse(contactTracer.recordTestResult("zSq1Fe", -34, true));
            assertFalse(contactTracer.recordTestResult("zFq8FQ", 0, false));    
        }

        @Test
        @DisplayName("Min Boundary case recordTestResult()")
        public void minBoundaryOfrecordTestResult() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData()); 

            assertFalse(contactTracer.recordTestResult("", -2147483647, true));   
        }

        @Test
        @DisplayName("Max Boundary case recordTestResult()")
        public void maxBoundaryOfrecordTestResult() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData()); 

            assertTrue(contactTracer.recordTestResult("aXcyE3", 2147483647, true));   
        }

        @Test
        @DisplayName("Correct input variables for findGatherings()")
        public void goodfindGatherings() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData()); 

            //0 because no record is present in the database
            assertEquals(0, contactTracer.findGatherings(20, 2, 23, 0.8f));
        }

        @Test
        @DisplayName("Boundary Case for findGatherings()")
        public void  BoundaryOfgoodfindGatherings() {
            String govFilePath = "F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties";
            String deviceFilePath1="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties";
            String deviceFilePath2="F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties";
            Government contactTracer=new Government(govFilePath);
            MobileDevice M1=new MobileDevice(deviceFilePath1, contactTracer);
            MobileDevice M2=new MobileDevice(deviceFilePath2, contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 10));
            assertFalse(M1.synchronizeData()); 

            //0 because no record is present in the database
            assertEquals(0, contactTracer.findGatherings(2147483647, -2147483647, 2147483647,- 0.8f));
        }
    }

    @Nested
    @DisplayName("Test Cases")
    class TestCases
    {

        
        @Test
        @DisplayName("Test 1")
        public void Test1() 
        {
            Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
            MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
            MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
            MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 20,10));
            assertTrue(M1.recordContact(M3.device_Hash_ID, 20,14));
            assertTrue(M2.recordContact(M3.device_Hash_ID, 20,45));
            assertTrue(M2.recordContact(M1.device_Hash_ID, 20,10));
            assertTrue(M3.recordContact(M1.device_Hash_ID, 20,14));
            assertTrue(M3.recordContact(M2.device_Hash_ID, 20,45));
            assertFalse(M1.synchronizeData());
            assertFalse(M2.synchronizeData());
            assertFalse(M3.synchronizeData());
        }

        @Test
        @DisplayName("Test 2")
        public void Test2() 
        {
            Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
            MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
            MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
            assertTrue(contactTracer.clearDataBase());
      
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 12));
            assertTrue(M2.recordContact(M1.device_Hash_ID, 23, 12));
            assertFalse(M2.synchronizeData());
            assertFalse( M1.synchronizeData()); 
            assertTrue(M2.positiveTest("JHDC2X"));
            assertFalse(M2.synchronizeData());
            assertTrue(M1.synchronizeData());
        }

        @Test
        @DisplayName("Test 3")
        public void Test3() 
        {
        Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
        MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
        MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
        MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
        MobileDevice M4=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config4.properties", contactTracer);
        MobileDevice M5=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config5.properties", contactTracer);
        MobileDevice M6=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config6.properties", contactTracer);
        MobileDevice M7=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config7.properties", contactTracer);

        assertTrue(contactTracer.clearDataBase());
        assertTrue(M1.recordContact(M2.device_Hash_ID, 20,10));
        assertTrue( M1.recordContact(M3.device_Hash_ID, 20,14));
        assertTrue( M1.recordContact(M4.device_Hash_ID, 20,12));
        assertTrue( M2.recordContact(M3.device_Hash_ID, 20,45));
        assertTrue( M3.recordContact(M4.device_Hash_ID, 20,10));
        assertTrue( M5.recordContact(M6.device_Hash_ID, 20,23));
        assertTrue(M6.positiveTest("DsQ2EZ"));
        assertTrue(M5.recordContact(M7.device_Hash_ID, 20,33));
        assertTrue(M7.recordContact(M6.device_Hash_ID, 20,42));
        assertTrue(contactTracer.recordTestResult("DsQ2EZ", 25, true)); 
        assertFalse(M1.synchronizeData());
        assertFalse(M2.synchronizeData());
        assertFalse( M3.synchronizeData());
        assertFalse( M4.synchronizeData());
        assertFalse( M6.synchronizeData());
        assertTrue( M5.synchronizeData());
        assertTrue(M7.synchronizeData());

        assertEquals(1,contactTracer.findGatherings(20,4, 10, 0.83f));
  
        }

        @Test
        @DisplayName("Test 4")
        public void Test4() 
        {
        Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
        MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
        MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
        MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
        MobileDevice M4=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config4.properties", contactTracer);
        MobileDevice M5=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config5.properties", contactTracer);
 
        assertTrue(contactTracer.clearDataBase());
        assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 12));
        assertTrue(M1.positiveTest("KeL5AS"));

        assertTrue(M1.recordContact(M4.device_Hash_ID, 48, 20));
        assertTrue(M1.recordContact(M2.device_Hash_ID, 45, 12));
        assertFalse(M1.synchronizeData());

        assertTrue(M1.recordContact(M2.device_Hash_ID, 45, 12));
        assertTrue(M2.positiveTest("JHCj2X"));
            

        assertTrue(contactTracer.recordTestResult("FuFG2S", 45, true));
        assertFalse(contactTracer.recordTestResult("FyG2S", 45, false));
        assertFalse(M2.synchronizeData());
        assertTrue(contactTracer.recordTestResult("JHCj2X", 23, true));
        assertFalse(M2.synchronizeData());
        assertTrue(contactTracer.recordTestResult("KeL5AS", 23, true));

        assertTrue(M1.synchronizeData());

        assertTrue(M2.recordContact(M3.device_Hash_ID, 20, 5));
        assertTrue(M4.recordContact(M5.device_Hash_ID, 75, 13));
        assertTrue(M3.recordContact(M1.device_Hash_ID, 20, 37));

        assertFalse(M2.positiveTest("BC7Cse"));
        assertFalse(M2.positiveTest("BCx27C"));
        assertTrue(contactTracer.recordTestResult("DFEzx5", 100, true));
        assertTrue(M3.positiveTest("DFEzx5"));
        assertTrue(M4.positiveTest("FuFG2S"));
        assertFalse(M4.synchronizeData());
        assertTrue(M5.positiveTest("W2Qa3Z"));
        assertTrue(M3.synchronizeData());
        assertTrue(contactTracer.recordTestResult("W2Qa3Z", 78, true));
        assertFalse(M5.synchronizeData());
        assertTrue(M4.synchronizeData());
        }

        @Test
        @DisplayName("Test 5")
        public void Test5() 
        {
            Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
            MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
            MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
            MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
            assertTrue(contactTracer.clearDataBase());
      
            assertTrue(M2.positiveTest("JHDC2X"));
            assertTrue(M3.positiveTest(("KjBH3W")));
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 12));
            assertTrue(M2.recordContact(M1.device_Hash_ID, 23, 12));
            assertTrue(M3.recordContact(M2.device_Hash_ID, 45, 67)); 
            assertFalse(M2.synchronizeData());
            assertFalse( M1.synchronizeData()); 
            assertFalse(M2.synchronizeData());
            assertFalse(M3.synchronizeData());
        }

        @Test
        @DisplayName("Test 6")
        public void Test6() 
        {
            Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
            MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
            MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
            MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
            assertTrue(contactTracer.clearDataBase());
      
            assertTrue(contactTracer.recordTestResult("JHDC2X",34,true));
            assertTrue(M2.positiveTest("JHDC2X"));
            assertTrue(M3.positiveTest(("KjBH3W")));
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 12));
            assertTrue(M2.recordContact(M1.device_Hash_ID, 23, 12));
            assertTrue(M3.recordContact(M2.device_Hash_ID, 45, 67)); 
            assertTrue(M3.recordContact(M1.device_Hash_ID, 56, 23)); 
            assertFalse(M2.synchronizeData());
            assertTrue( M1.synchronizeData()); 
            assertTrue(M3.synchronizeData());
        }

        @Test
        @DisplayName("Test 7")
        public void Test7() 
        {
            Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
            MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
            MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
            MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
            assertTrue(contactTracer.clearDataBase());
      
            assertFalse( M1.synchronizeData()); 
            assertTrue(contactTracer.recordTestResult("JHDC2X",34,true));
            assertFalse(M2.synchronizeData());
            assertTrue(contactTracer.recordTestResult("dcF34S",67,false));
            assertTrue(M2.positiveTest("JHDC2X"));
            assertTrue(M3.positiveTest(("KjBH3W")));
            assertTrue(M1.recordContact(M2.device_Hash_ID, 23, 12));
            assertTrue(M2.recordContact(M1.device_Hash_ID, 23, 12));
            assertTrue(M3.recordContact(M2.device_Hash_ID, 45, 67)); 
            assertTrue(M3.recordContact(M1.device_Hash_ID, 56, 23)); 
            assertFalse(M2.synchronizeData());
            assertTrue( M1.synchronizeData()); 
            assertTrue(M3.synchronizeData());
        }

        @Test
        @DisplayName("Test 8")
        public void Test8() 
        {
        Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
        MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
        MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
        MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
        MobileDevice M4=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config4.properties", contactTracer);
        MobileDevice M5=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config5.properties", contactTracer);
        MobileDevice M6=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config6.properties", contactTracer);
        MobileDevice M7=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config7.properties", contactTracer);
        MobileDevice M8=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config8.properties", contactTracer);
        MobileDevice M9=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config9.properties", contactTracer);
        MobileDevice M10=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config10.properties", contactTracer);

        assertTrue(contactTracer.clearDataBase());
        assertTrue(M1.recordContact(M2.device_Hash_ID, 20,10));
        M1.positiveTest("xCVb4R");
        assertTrue( M1.recordContact(M3.device_Hash_ID, 20,14));
        assertTrue( M1.recordContact(M4.device_Hash_ID, 20,12));
        assertTrue( M2.recordContact(M3.device_Hash_ID, 20,45));
        assertTrue( M3.recordContact(M4.device_Hash_ID, 20,10));
        assertTrue( M5.recordContact(M6.device_Hash_ID, 20,23));
        assertTrue(M6.positiveTest("DsQ2EZ"));
        assertTrue(M5.recordContact(M7.device_Hash_ID, 20,33));
        assertTrue(M7.recordContact(M6.device_Hash_ID, 20,42));
        assertTrue(contactTracer.recordTestResult("DsQ2EZ", 25, true)); 
        assertFalse(contactTracer.recordTestResult("HQ2EZ", 75, false)); 
        assertFalse(M1.synchronizeData());
        assertFalse(M2.synchronizeData());
        assertFalse( M3.synchronizeData());
        assertFalse( M4.synchronizeData());
        assertTrue(contactTracer.recordTestResult("xCVb4R", 100, true)); 
        assertTrue( M7.recordContact(M3.device_Hash_ID, 56,23));
        assertTrue( M1.recordContact(M8.device_Hash_ID, 57,12));
        assertTrue( M2.recordContact(M10.device_Hash_ID, 45,89));
        assertTrue( M10.recordContact(M1.device_Hash_ID, 90,34));
        assertTrue( M9.recordContact(M7.device_Hash_ID, 34,180));
        assertFalse( M9.synchronizeData());
        assertTrue( M10.synchronizeData());
        assertFalse( M8.synchronizeData());
        assertFalse( M6.synchronizeData());
        assertTrue( M5.synchronizeData());
        assertTrue(M7.synchronizeData());
        }

        @Test
        @DisplayName("Test 9")
        public void Test9() 
        {
            Government contactTracer=new Government("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/Goverment config.properties");
            MobileDevice M1=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config1.properties", contactTracer);
            MobileDevice M2=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config2.properties", contactTracer);
            MobileDevice M3=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config3.properties", contactTracer);
            MobileDevice M4=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config4.properties", contactTracer);
            MobileDevice M5=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config5.properties", contactTracer);
            MobileDevice M6=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config6.properties", contactTracer);
            MobileDevice M7=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config7.properties", contactTracer);
            MobileDevice M8=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config8.properties", contactTracer);
            MobileDevice M9=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config9.properties", contactTracer);
            MobileDevice M10=new MobileDevice("F:/kishan/study/Dalhousie University/SDC (3901)/Final Project/My Final Project/config/config10.properties", contactTracer);

            assertTrue(contactTracer.clearDataBase());
            assertTrue(M1.recordContact(M2.device_Hash_ID, 67,23));
            assertTrue(M2.recordContact(M3.device_Hash_ID, 67,27));
            assertTrue(M4.recordContact(M6.device_Hash_ID, 67,47));
            assertTrue(M1.recordContact(M4.device_Hash_ID, 67,89));
            assertTrue(M1.recordContact(M7.device_Hash_ID, 67,20));
            assertTrue(M2.recordContact(M4.device_Hash_ID, 67,27));
            assertTrue(M5.recordContact(M7.device_Hash_ID, 67,33));
            assertTrue(M1.recordContact(M9.device_Hash_ID, 67,90));
            assertTrue(M9.recordContact(M10.device_Hash_ID, 67,120));
            assertTrue(M1.recordContact(M10.device_Hash_ID, 67,49));
            assertTrue(M9.recordContact(M7.device_Hash_ID, 67,77));
            assertTrue(M3.recordContact(M9.device_Hash_ID, 67,12));
            assertTrue(M8.recordContact(M3.device_Hash_ID, 67,18));
            assertTrue(M2.recordContact(M3.device_Hash_ID, 67,26));
            assertTrue(M9.recordContact(M2.device_Hash_ID, 67,51));

            assertFalse( M8.synchronizeData());
            assertFalse( M1.synchronizeData());
            assertFalse( M2.synchronizeData());
            assertFalse( M4.synchronizeData());
            assertFalse( M5.synchronizeData());
            assertFalse( M7.synchronizeData());
            assertFalse( M3.synchronizeData());
            assertFalse( M6.synchronizeData());
            assertFalse( M9.synchronizeData());
            assertFalse( M10.synchronizeData());
        
            assertEquals(8,contactTracer.findGatherings(67,2, 10,0.9f));   
        }
    }
}
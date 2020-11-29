
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class BonusTester {
    
    private int numStrings;
    private int numInsertActions = 0;
    private Random rand;
    
    private CuckooHashing myCuckoo;
    private HashMethods   myHF;
    
    private int testId;
    
    private boolean failedTest = false;
    
    public BonusTester(int testId,int numStrings)
    {
        this.numStrings           = numStrings;
        this.rand                 = new Random();
        this.myHF                 = new StringHashMethods();
        this.testId               = testId;
    }
    /* Returns a random integer within a given range */ 
    
    public int randInt(int min, int max) {
    int randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
    }
    /* bypasses the private reference*/
    public Object getFieldByReflection(Object classObject,String fieldName)
    {
       try{
        Field parentField = classObject.getClass().getDeclaredField(fieldName);  
        parentField.setAccessible(true);   
        Object parentObj = parentField.get(classObject); 
        
        return (Object)parentObj;
        }catch(Exception reflectionExceptino){
        }
       return null;
    }
    
    public boolean passedTest()
    {
        return !failedTest;
    }
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    public static String randomAlphaNumeric(int count) {
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
    int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
    builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
    }  
    
    private ArrayList<String> beforeArrayStatus   ;
    private ArrayList<String> beforeOverflowStatus;
    
    private ArrayList<String> afterArrayStatus   ;
    private ArrayList<String> afterOverflowStatus;
    
    
    public void printArray(ArrayList<String> array)
    {
        for(int i = 0 ; i < array.size(); i++){
            System.out.print(array.get(i)+",");
        }
        System.out.println("\r\n");
    }
      
    public void generateRandomTest()
    {
        int sizeOfArray     = randInt(2,9);
        int numOfInsertions = randInt(1,sizeOfArray);
        int sizeString      = randInt(5,10);
        String randomStr    = "Ron";
        
        myCuckoo  = new CuckooHashing(myHF,sizeOfArray);
        
        System.out.println("\r\n\r\nGenerated Test --> "+testId+"\r\n-----------------");
        System.out.println("HashMethods myHF                 = new StringHashMethods();");
        System.out.println("CuckooHashing myCuckoo = new CuckooHashing(myHF,"+sizeOfArray+");");
        
        for(int i = 0 ; i < numOfInsertions-1; i++ )
        {
            randomStr = randomAlphaNumeric(sizeString);
            sizeString = randInt(5,10);
            if(!randomStr.isEmpty()){
            myCuckoo.insert(randomStr);
            System.out.println("myCuckoo.insert(\""+randomStr+"\");");
            }
        } 
        System.out.println("\r\n");
        System.out.println("Current Array\r\n--------");
        System.out.println(myCuckoo.toString());
        System.out.println("--------");
        
        beforeArrayStatus    = new ArrayList<String>();
        beforeOverflowStatus = new ArrayList<String>();
        
        afterArrayStatus     = new ArrayList<String>();
        afterOverflowStatus  = new ArrayList<String>();
        
        String []         ourReflectedArr         =  (String [])        getFieldByReflection(myCuckoo,"array");
        ArrayList<String> ourReflectedOverflow    =  (ArrayList<String>)getFieldByReflection(myCuckoo,"stash");
        
        for(int i = 0 ; i < ourReflectedArr.length; i++)
            beforeArrayStatus.add(ourReflectedArr[i]+":"+i); 
        for(int i = 0;  i < ourReflectedOverflow.size() ; i++)
            beforeOverflowStatus.add(ourReflectedOverflow.get(i)+":"+i);
        
        System.out.println("Saved Array Status!");
        System.out.println("\r\nInserting Strings & Backtracking"); 
        
        int numExtraInserts = ourReflectedArr.length - numOfInsertions-1;
        
        if(numExtraInserts < 0)
            numExtraInserts = 0;
        else
            numExtraInserts = randInt(0,numExtraInserts);
        
        for(int i = 0 ; i < numExtraInserts ;i ++){
        randomStr = randomAlphaNumeric(6);  
        System.out.println("myCuckoo.insert(\""+randomStr+"\");");  
        myCuckoo.insert(randomStr);
        }
        
        System.out.println("\r\n-------Backtracking Test-------\r\n");
        for(int j= 0  ;j  < numExtraInserts ; j++) 
        myCuckoo.undo();
        
        
        for(int i = 0 ; i < ourReflectedArr.length; i++)
            afterArrayStatus.add(ourReflectedArr[i]+":"+i);
        
        for(int i = 0;  i < ourReflectedOverflow.size() ; i++)
            afterOverflowStatus.add(ourReflectedOverflow.get(i)+":"+i);
        
        if(afterArrayStatus.size() != beforeArrayStatus.size()){
            failedTest = true;
            System.out.println("Failed Test! Array Before Size != Array After Size");
            System.out.println("Before Array -");
            printArray(beforeArrayStatus);
            System.out.println("After Array -");
            printArray(afterArrayStatus);
        }
        if(afterOverflowStatus.size() != beforeOverflowStatus.size()){
            System.out.println("Failed Test! Array Overflow Before != Array Overflow After");
                   
            System.out.println("Before Array -");
            printArray(beforeOverflowStatus);
            System.out.println("After Array -");
            printArray(afterOverflowStatus);
            failedTest = true;
        }
        for(int i = 0 ; i < afterArrayStatus.size(); i++){
            if(!afterArrayStatus.get(i).equals(beforeArrayStatus.get(i))){
                System.out.println("Failed Test! After Array Is Not The Same!"); 
                System.out.println("Before Array -");
                printArray(beforeArrayStatus);
                System.out.println("After Array -");
                printArray(afterArrayStatus);
                failedTest = true;
                break;
            }
        }
        
        try{
        for(int i = 0 ; i < beforeOverflowStatus.size(); i++){
            if(!beforeOverflowStatus.get(i).equals(afterOverflowStatus.get(i))){
                System.out.println("Failed Test! After Overflow Array Is Not The Same!");
                
                System.out.println("Before Array -");
                printArray(beforeOverflowStatus);
                System.out.println("After Array -");
                printArray(afterOverflowStatus);
                failedTest = true;
                break;
            }
        }
        }catch(Exception overflowArrayException){
                System.out.println("Failed Test! After Overflow Array Is Not The Same!");
                System.out.println("Before Array -");
                printArray(beforeOverflowStatus);
                System.out.println("After Array -");
                printArray(afterOverflowStatus);
                failedTest = true; 
        }
        
        if(!failedTest){
        System.out.println("Backtracking Worked!\r\n-------------------------------");
        Collections.shuffle(afterArrayStatus); 
        String toRemove = afterArrayStatus.get(0);
        if(toRemove != null && !toRemove.split(":")[0].equals("null")){
        System.out.println("\r\nStarting Sanity Test & Delete ~ Checking Stash Being Cleared Properly\r\n-------------------------------"); 
      
        if(toRemove != null) toRemove = toRemove.split(":")[0];
            
        
        System.out.println("Deleting: " + toRemove+"\r\n");
        myCuckoo.remove(toRemove);
        
        afterArrayStatus.clear() ;
        beforeArrayStatus.clear();
        
        for(int i = 0 ; i < ourReflectedArr.length; i++)
            beforeArrayStatus.add(ourReflectedArr[i]);
        System.out.println("Array State After Delete-");
        printArray(beforeArrayStatus);
        
        int randUndo = randInt(5,15);
        System.out.println("Backtracking " + randUndo + " Times "+ "............\r\n");
        for(int i = 0 ; i <  randUndo;i++)
        myCuckoo.undo(); 
        for(int i = 0 ; i < ourReflectedArr.length; i++)
            afterArrayStatus.add(ourReflectedArr[i]);
        System.out.println("Array State After Backtracking "+"\r\n"); 
        printArray(afterArrayStatus); 
        failedTest = !afterArrayStatus.equals(beforeArrayStatus);
        System.out.println("Sanity Check Passed --> " + !failedTest); 
        if(!failedTest){
            System.out.println("Failed Test In Sanity Check!");
        }
        }
        }
        if(!failedTest){
            System.out.println("Passed Test ---> " + testId+" ");
        }
        System.out.println("-----------------\r\n");
        
    }
    
    public void startTest()
    {
        generateRandomTest();
    } 
}

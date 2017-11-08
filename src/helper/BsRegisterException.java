package helper;

/**
 *
 * @author yohan
 */
public class BsRegisterException extends Exception{
    private int errorValue;
    
    BsRegisterException(int value, String s){
        super(s);
        errorValue = value;
    }
    
    public int getErrorValue(){
        return errorValue;
    }
    
}

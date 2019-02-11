/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordchecker;

/**
 *
 * @author mfaux02
 */
public class StrengthChecker {
    private String[] requiredStrings;
    private String symbols;
    private boolean requiresSymbol;//!@#$%^&*~-_=+-/[]{}|\;:'"?.,
    private boolean requiresLowercase;//a-z
    private boolean requiresUppercase;//A-Z
    private boolean requiresNumber;//0-9
    private boolean allowNumber;//0-9
    private boolean allowSymbol;//0-9
    private int min;
    private int max;
    private String error;
    
    StrengthChecker(){  
        symbols = "!@#$%^&*~-_=+-/[]{}|\\;:'\"?.,";
        error = "";
        
        min = 0;
        max = 100;
        
        requiresSymbol = false;//!@#$%^&*~-_=+-/[]{}|\;:'"?.,
        requiresLowercase = true;//a-z
        requiresUppercase = true;//A-Z
        requiresNumber = false;//0-9
        allowNumber = true;//0-9
        allowSymbol = true;//0-9
    }
    
    /**
     * @param symbols the symbols to set
     */
    public void addSymbols(String[] symbols){
        for(int i = 0; i < symbols.length; i ++){
            if(!this.symbols.contains(symbols[i])){
                setSymbols(getSymbols() + symbols[i]);
            }
        }
    }
    
    /**
     * 
     * @return the Errors when checking a password
     */
    public String getError(){
        return error;
    }
    
    /**
     * Sets the required Strings for the password to use
     * @param req the Strings required in the password
     */
    public void setRequired(String[] req){
        requiredStrings = req;
    }
    
    /**
     * 
     * @return the Strings required by the password
     */
    public String[] getRequired(){
        return requiredStrings;
    }
    
    /**
     * Clears the Required Strings
     */
    public void clearRequired(){
        requiredStrings = null;
    }
    
    /**
     * Add Requirements to the Required Strings
     * @param required 
     */
    public void addRequired(String[] required){
        if(requiredStrings != null){
            String[] newArray = new String[required.length + requiredStrings.length];
            int i = 0;
            for(int a = 0; a < requiredStrings.length; a++){
                newArray[i] = requiredStrings[a];
                i++;
            }
            for(int a = 0; a < required.length; a++){
                newArray[i] = required[a];
                i++;
            }
            requiredStrings = newArray;
        }else{
            requiredStrings = required;
        }
        
    }
    
    /**
     * Sets the minimum and maximum based on the min and max input
     * @param min the min to set as the minimum
     * @param max the max to set as the maximum
     */
    public void setLength(int min,int max){
        this.min = min;
        this.max = max;
    }
    
    /**
     * Checks the input password against a series of requirements, returns whether it is valid or not.
     * @param password the password to check
     * @return the validity of the password
     */
    public boolean checkPassword(String password){
        error = "";
        if(requiredStrings != null){
            String result = "";
            for(int i = 0; i < requiredStrings.length; i++){
                if(password.contains(requiredStrings[i])){
                    result += "f";
                }else{
                    result += "p";
                }
            }
            if(result.contains("f")){
                error = error + "Required StringArray : Pass\n";
            }else{
                error = error + "Required StringArray : Fail\n";
            }
        }
        
        checkLetters(password.toCharArray());
        
        
        return true;
    }
    
    private boolean checkLetters(char[] pass){
        boolean uppercase = false;
        boolean lowercase = false;
        boolean number = false;
        boolean symbol = false;
        boolean illegal = false;
        
        boolean result = true;
        char[] sym = getSymbols().toCharArray();
        for(int i = 0; i < pass.length; i++){
            int r = checkLetter(pass[i],sym);
            
            switch(r){
                case 1:
                    uppercase = true;
                    break;
                case 2:
                    lowercase = true;
                    break;
                case 3:
                    number = true;
                    break;
                case 4:
                    symbol = true;
                    break;
                case 5:
                    illegal = true;
                    break;
            }
        }
        
        while(result){
            if(pass.length < min){
                result = false;
            }
            if(pass.length > max){
                result = false;
            }
            if(requiresUppercase){
                if(!uppercase){           
                    result = false;
                }
            }
            if(requiresLowercase){
                if(!lowercase){
                    result = false;
                }
            }

            if(allowNumber){
                if(requiresNumber){
                    if(!number){
                        result = false;
                    }
                }
            }else if(number){
                result = false;
            }
            if(allowSymbol){
                if(requiresSymbol){
                    if(!symbol){
                        result = false;
                    }
                }
            }else if(symbol){
                result = false;
            }
            if(illegal){
                result = false;
            }
            break;
        }
        
        error = error + "\n";
        error = error + "Requirements:\n";
        error = error + "   Minium met         : " + (pass.length >= min) + "\n";
        error = error + "   Under Maximum      : " + (pass.length <= max) + "\n";
        error = error + "   Requires Capital   : " + requiresUppercase + "\n";
        error = error + "   Has Capital        : " + uppercase + "\n";
        error = error + "   Requires Lowercase : " + requiresLowercase + "\n";
        error = error + "   Has Lowercase      : " + lowercase + "\n";
        error = error + "   Allows Numbers     : " + allowNumber + "\n";
        error = error + "   Requires Numbers   : " + requiresNumber + "\n";
        error = error + "   Has Number         : " + number + "\n";
        error = error + "   Allows Symbols     : " + allowSymbol + "\n";
        error = error + "   Requires Symbols   : " + requiresSymbol + "\n";
        error = error + "   Has Symbol         : " + symbol + "\n";
        error = error + "   Has Illegal char   : " + illegal + "\n";
        error = error + "       Status: " + result + "\n";
        
        return result;
    }
    
    private int checkLetter(char pass, char[] sym){
        if(Character.isUpperCase(pass)){
            return 1;
        }else if(Character.isLowerCase(pass)){
            return 2;
        }else if(Character.isDigit(pass)){
            return 3;
        }else{
            for(int e = 0; e < sym.length; e++){
                if(pass == sym[e]){
                    return 4;
                }
            }
        }
        return 5;
    }

    /**
     * @return the symbols
     */
    public String getSymbols() {
        return symbols;
    }

    /**
     * @param symbols the symbols to set
     */
    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    /**
     * @param requiresSymbol the requiresSymbol to set
     */
    public void setRequiresSymbol(boolean requiresSymbol) {
        this.requiresSymbol = requiresSymbol;
    }

    /**
     * @param requiresLowercase the requiresLowercase to set
     */
    public void setRequiresLowercase(boolean requiresLowercase) {
        this.requiresLowercase = requiresLowercase;
    }

    /**
     * @param requiresUppercase the requiresUppercase to set
     */
    public void setRequiresUppercase(boolean requiresUppercase) {
        this.requiresUppercase = requiresUppercase;
    }

    /**
     * @param requiresNumber the requiresNumber to set
     */
    public void setRequiresNumber(boolean requiresNumber) {
        this.requiresNumber = requiresNumber;
    }

    /**
     * @param allowNumber the allowNumber to set
     */
    public void setAllowNumber(boolean allowNumber) {
        this.allowNumber = allowNumber;
    }

    /**
     * @param allowSymbol the allowSymbol to set
     */
    public void setAllowSymbol(boolean allowSymbol) {
        this.allowSymbol = allowSymbol;
    }
}

package com.play.jpa.util;

/**
 *
 * @author window10
 */
public class Print {
    public static void out(String str){
        System.out.println(ColorSpec.CYAN+str+ColorSpec.RESET);
    }
    
    public static void out(String spec,String str){
        System.out.println(spec+str+ColorSpec.RESET);
    }
    
    public static void reverse(String spec,String str){
        System.out.println(ColorSpec.REVERSE+spec+str+ColorSpec.RESET);
    }
}

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
    
    public static void outB(String str){
        System.out.println(ColorSpec.BOLD+str+ColorSpec.RESET);
    }
    
    public static void outB(String spec,String str){
        System.out.println(ColorSpec.BOLD+spec+str+ColorSpec.RESET);
    }
    
    public static void outU(String spec,String str){
        System.out.println(ColorSpec.UNDERLINE+spec+str+ColorSpec.RESET);
    }
    
    public static void outU(String str){
        System.out.println(ColorSpec.UNDERLINE+str+ColorSpec.RESET);
    }
}

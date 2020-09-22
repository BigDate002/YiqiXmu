/*    */ package com.netcity.controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Test
/*    */ {
/*    */   public static void main(String[] args) {
/* 25 */     for (int i = 0; i < 100000; i++) {
/* 26 */       int sum = 11 * (i * i + 10);
/* 27 */       int x = (int)Math.sqrt(sum);
/* 28 */       if (x * x == sum)
/* 29 */         System.out.println(String.valueOf(i) + "\t:" + x + "---" + sum); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Tomcat\apache-tomcat-8.5.38\webapps\webapp\WEB-INF\classes\!\com\netcity\controller\Test.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
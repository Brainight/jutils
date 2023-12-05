/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package brainight.jutils.args.annotations;

import brainight.jutils.args.handlers.ArgHandler;
import brainight.jutils.args.handlers.CoffeeArgHandler;
import brainight.jutils.args.handlers.StringArgHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Brainight
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface A {

    public String placeHolder();
    
    public int position();
    
    public boolean required() default true;
    
    public String multiValueSeparator() default ",";

    public Class<? extends ArgHandler> handler() default CoffeeArgHandler.class;
}

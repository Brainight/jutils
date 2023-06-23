/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package brainight.jutils.args.annotations;

import brainight.jutils.args.handlers.ArgHandler;
import brainight.jutils.args.handlers.CoffeeArgHandler;
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
public @interface O {

    /**
     * Short name for the option. Always required. Must satisfy regex:
     * ^-[A-Za-z]{1,2}
     *
     * @return
     */
    public String sname();

    /**
     * Long name for the option. Optional. Must satisfy regex: ^--[A-Za-z]{3,}
     *
     * @return
     */
    public String lname() default "";

    /**
     * Option description. Will be shown in command manual.
     *
     * @return
     */
    public String descr() default "";

    /**
     * The handler used to parse the value of this option.
     *
     * @return
     */
    public Class<? extends ArgHandler> handler() default CoffeeArgHandler.class;

    /**
     * Defines if the option is required or optional.
     *
     * @return
     */
    public boolean required() default false;

    public String multiValueSeparator() default ",";

}

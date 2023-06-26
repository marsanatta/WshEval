package wsh.eval.command;

import com.google.inject.name.Named;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for Command Class
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnnotation {

    /**
     * Name of the Command. This is to be used as the mapping of user input to CommandClass
     * @return string
     */
    String name();

    /**
     * Description of the command
     * @return string
     */
    String desc();

    /**
     * Indicates is a user command or not
     * @return boolean
     */
    boolean isUserCommand() default true;
}

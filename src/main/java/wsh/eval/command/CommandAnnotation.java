package wsh.eval.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnnotation {

    /**
     * name of the Command. This is to be used as the mapping of user input to CommandClass
     * @return string
     */
    String name();

    /**
     * Description of the command
     * @return string
     */
    String desc();
}

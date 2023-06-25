package wsh.eval.command;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandHelper {
    private static final Map<String, Class<Command>> commands = new HashMap<>();
    private static final String COMMAND_PACKAGE_NAME = "wsh.eval.command";

    static {
        Set<Class<?>> classes = findCommandClasses();

        for (Class<?> clazz : classes) {
            commands.put(clazz.getAnnotation(CommandAnnotation.class).name(), (Class<Command>) clazz);
        }
    }

    public static Set<String> getCommandNames() {
        return commands.keySet();
    }

    public static Class<Command> getCommand(String name) {
        return commands.get(name);
    }

    public static String getCommandDescription() {
        StringBuilder sb = new StringBuilder("Supported Commands:\n");
        for (Class<Command> clazz : commands.values()) {
            CommandAnnotation anno = clazz.getAnnotation(CommandAnnotation.class);
            sb.append(" -").append(anno.name()).append(": ").append(anno.desc()).append('\n');
        }
        return sb.toString();
    }

    private static Set<Class<?>> findCommandClasses() {
        Reflections reflections = new Reflections(COMMAND_PACKAGE_NAME,
                new SubTypesScanner(false),
                new TypeAnnotationsScanner()
        );
        return new HashSet<>(reflections.getTypesAnnotatedWith(CommandAnnotation.class));
    }


}

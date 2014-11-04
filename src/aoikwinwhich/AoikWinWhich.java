//
package aoikwinwhich;
import static java.lang.System.out;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AoikWinWhich {

    public static List<String> find_executable(String prog) {
        // 8f1kRCu
        String env_var_PATHEXT = System.getenv("PATHEXT");
        /// can be null

        // 6qhHTHF
        // split into a list of extensions
        List<String> ext_s = (env_var_PATHEXT == null)
            ? new LinkedList<>()
            : Arrays.asList(env_var_PATHEXT.split(File.pathSeparator));

        // 2pGJrMW
        // strip
        ext_s = ext_s.stream().map(String::trim).collect(Collectors.toList());

        // 2gqeHHl
        // remove empty
        ext_s = ext_s.stream().filter(x -> !x.equals("")).collect(Collectors.toList());

        // 2zdGM8W
        // convert to lowercase
        ext_s = ext_s.stream().map(String::toLowerCase).collect(Collectors.toList());

        // 2fT8aRB
        // uniquify
        ext_s = new LinkedList<>(new LinkedHashSet<>(ext_s));
        /// LinkedHashSet keeps the original order.

        // 4ysaQVN
        String env_var_PATH = System.getenv("PATH");
        /// can be null

        // 6mPI0lg
        List<String> dir_path_s = (env_var_PATH == null)
            ? new LinkedList<>()
            : new LinkedList<>(Arrays.asList(env_var_PATH.split(File.pathSeparator)));

        // 5rT49zI
        // insert empty dir path to the beginning
        //
        // Empty dir handles the case that |prog| is a path, either relative or
        //  absolute. See code 7rO7NIN.
        dir_path_s.add(0, "");

        // 2klTv20
        // uniquify
        dir_path_s = new LinkedList<>(new LinkedHashSet<>(dir_path_s));
        /// LinkedHashSet keeps the original order.

        // 6bFwhbv
        List<String> exe_path_s = new LinkedList<>();

        for (String dir_path : dir_path_s) {
            // 7rO7NIN
            // synthesize a path with the dir and prog
            final String path = dir_path.equals("") ? prog :
                                Paths.get(dir_path, prog).toString();
            /// |final| is needed for |path| to be used in the lambda below.

            // 6kZa5cq
            // assume the path has extension, check if it is an executable
            if (ext_s.parallelStream().anyMatch(ext -> path.endsWith(ext))) {
                if (Files.isRegularFile(Paths.get(path))) {
                    exe_path_s.add(path);
                }
            }

            // 2sJhhEV
            // assume the path has no extension
            for (String ext : ext_s) {
                // 6k9X6GP
                // synthesize a new path with the path and the executable extension
                String path_plus_ext = path + ext;

                // 6kabzQg
                // check if it is an executable
                if (Files.isRegularFile(Paths.get(path_plus_ext))) {
                    exe_path_s.add(path_plus_ext);
                }
            }
        }

        //
        return exe_path_s;
    }

    public static void main(String[] args) {
        // 9mlJlKg
        // check if one cmd arg is given
        if (args.length != 1) {
            // 7rOUXFo
            // print program usage
            out.println("Usage: aoikwinwhich PROG");
            out.println("");
            out.println("#/ PROG can be either name or path");
            out.println("aoikwinwhich notepad.exe");
            out.println("aoikwinwhich C:\\Windows\\notepad.exe");
            out.println("");
            out.println("#/ PROG can be either absolute or relative");
            out.println("aoikwinwhich C:\\Windows\\notepad.exe");
            out.println("aoikwinwhich Windows\\notepad.exe");
            out.println("");
            out.println("#/ PROG can be either with or without extension");
            out.println("aoikwinwhich notepad.exe");
            out.println("aoikwinwhich notepad");
            out.println("aoikwinwhich C:\\Windows\\notepad.exe");
            out.println("aoikwinwhich C:\\Windows\\notepad");

            // 3nqHnP7
            return;
        }

        // 9m5B08H
        // get name or path of a program from cmd arg
        String prog = args[0];

        // 8ulvPXM
        // find executables
        List<String> path_s = find_executable(prog);

        // 5fWrcaF
        // has found none, exit
        if (path_s.size() == 0) {
            // 3uswpx0
            return;
        }

        // 9xPCWuS
        // has found some, output
        String txt = String.join("\n", path_s);

        out.println(txt);

        //
        return;
    }
}

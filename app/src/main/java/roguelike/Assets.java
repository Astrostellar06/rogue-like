package roguelike;

import java.awt.Color;
import java.util.prefs.Preferences;

import asciiPanel.AsciiPanel;

public class Assets {
  static Preferences prefs = Preferences.userNodeForPackage(App.class);

  public static Color background(){
    String theme = prefs.get("theme", "dark");
    if(theme.equals("dark")){
      return black;
    }
    if(theme.equals("lollipop")){
      return black;
    }
    if(theme.equals("cyber")){
      return black;
    } else {
      return black;
    }
  }

  public static Color primary(){
    String theme = prefs.get("theme", "dark");
    if(theme.equals("dark")){
      return white;
    }
    if(theme.equals("lollipop")){
      return darkPink;
    }
    if(theme.equals("cyber")){
      return green;
    } else {
      return white;
    }
  }

  public static Color primarySelected(){
    String theme = prefs.get("theme", "dark");
    if(theme.equals("dark")){
      return green;
    }
    if(theme.equals("lollipop")){
      return lightBlue;
    }
    if(theme.equals("cyber")){
      return lightBlue;
    } else {
      return green;
    }
  }

  public static Color secondary(){
    String theme = prefs.get("theme", "dark");
    if(theme.equals("dark")){
      return gray;
    }
    if(theme.equals("light")){
      return grey;
    }
    if(theme.equals("cyber")){
      return gray;
    } else {
      return gray;
    }
  }

  public static Color black = new Color(0, 0, 0);
  public static Color white = new Color(255, 255, 255);
  public static Color gray = new Color(150, 150, 150);
  public static Color grey = new Color(128, 128, 128);
  public static Color green = new Color(0, 255, 0);
  public static Color darkPink = new Color(231,84,128);
  public static Color blue = new Color(	0, 0, 139);
  public static Color lightBlue = new Color(84,202,231);

  public static void display(AsciiPanel terminal, String[] obj, int x, int y, Color color){
    for(int i=1; i<obj.length+1; i++){
      terminal.write(obj[i-1], x, i+y, color);
    }
  }

  public static void displayCenter(AsciiPanel terminal, String[] obj, int y, Color color){
    for(int i=1; i<obj.length+1; i++){
      terminal.writeCenter(obj[i-1], i+y, color);
    }
  }

  public static String[] title = {
    "  /$$$$$$                      /$$ /$$ /$$      /$$                    ",
    " /$$__  $$                    |__/|__/| $$  /$ | $$                    ",
    "| $$  \\ $$  /$$$$$$$  /$$$$$$$ /$$ /$$| $$ /$$$| $$  /$$$$$$   /$$$$$$ ",
    "| $$$$$$$$ /$$_____/ /$$_____/| $$| $$| $$/$$ $$ $$ |____  $$ /$$__  $$",
    "| $$__  $$|  $$$$$$ | $$      | $$| $$| $$$$_  $$$$  /$$$$$$$| $$  \\__/",
    "| $$  | $$ \\____  $$| $$      | $$| $$| $$$/ \\  $$$ /$$__  $$| $$      ",
    "| $$  | $$ /$$$$$$$/|  $$$$$$$| $$| $$| $$/   \\  $$|  $$$$$$$| $$      ",
    "|__/  |__/|_______/  \\_______/|__/|__/|__/     \\__/ \\_______/|__/      ",
  };
    
  public static String[] newgame = {
    "  _   _",
    " | \\ | |",
    " |  \\| | _____      __   __ _  __ _ _ __ ___   ___",
    " | . ` |/ _ \\ \\ /\\ / /  / _` |/ _` | '_ ` _ \\ / _ \\",
    " | |\\  |  __/\\ V  V /  | (_| | (_| | | | | | |  __/",
    " |_| \\_|\\___| \\_/\\_/    \\__, |\\__,_|_| |_| |_|\\___|",
    "                         __/ |",
    "                        |___/",
  };

  public static String[] resumegame = {
    "  _____",
    " |  __ \\",
    " | |__) |___  ___ _   _ _ __ ___   ___    __ _  __ _ _ __ ___   ___",
    " |  _  // _ \\/ __| | | | '_ ` _ \\ / _ \\  / _` |/ _` | '_ ` _ \\ / _ \\",
    " | | \\ \\  __/\\__ \\ |_| | | | | | |  __/ | (_| | (_| | | | | | |  __/",
    " |_|  \\_\\___||___/\\__,_|_| |_| |_|\\___|  \\__, |\\__,_|_| |_| |_|\\___|",
    "                                          __/ |",
    "                                         |___/",
  };

  public static String[] settings = {
    "   _____      _   _   _",
    "  / ____|    | | | | (_)",
    " | (___   ___| |_| |_ _ _ __   __ _ ___",
    "  \\___ \\ / _ \\ __| __| | '_ \\ / _` / __|",
    "  ____) |  __/ |_| |_| | | | | (_| \\__ \\",
    " |_____/ \\___|\\__|\\__|_|_| |_|\\__, |___/",
    "                               __/ |",
    "                              |___/",
  };

  public static String[] attack = {
    "          _   _             _    ",
    "     /\\  | | | |           | |   ",
    "    /  \\ | |_| |_ __ _  ___| | __",
    "   / /\\ \\| __| __/ _` |/ __| |/ /",
    "  / ____ \\ |_| || (_| | (__|   < ",
    " /_/    \\_\\__|\\__\\__,_|\\___|_|\\_\\"
  };

  public static String[] spell = {
    "   _____            _ _ ",
    "  / ____|          | | |",
    " | (___  _ __   ___| | |",
    "  \\___ \\| '_ \\ / _ \\ | |",
    "  ____) | |_) |  __/ | |",
    " |_____/| .__/ \\___|_|_|",
    "        | |             ",
    "        |_|             "
  };

  public static String[] item = {
    "  _____ _                     ",
    " |_   _| |                    ",
    "   | | | |_ ___ _ __ ___  ___ ",
    "   | | | __/ _ \\ '_ ` _ \\/ __|",
    "  _| |_| ||  __/ | | | | \\__ \\",
    " |_____|\\__\\___|_| |_| |_|___/"
  };

  public static String[] selected = {
    " __",
    " \\ \\",
    "  \\ \\",
    "   > >",
    "  / /",
    " /_/",
  };

  public static String[] warrior = {
    "                          _           ",
    " __ __ __ __ _  _ _  _ _ (_) ___  _ _ ",
    " \\ V  V // _` || '_|| '_|| |/ _ \\| '_|",
    "  \\_/\\_/ \\__,_||_|  |_|  |_|\\___/|_|  ",
    "                                      ",
  };

  public static String[] warriorIcon = {
    " .----------------. ",
    "| .--------------. |",
    "| | _____  _____ | |",
    "| ||_   _||_   _|| |",
    "| |  | | /\\ | |  | |",
    "| |  | |/  \\| |  | |",
    "| |  |   /\\   |  | |",
    "| |  |__/  \\__|  | |",
    "| |              | |",
    "| '--------------' |",
    " '----------------' ",
  };

  public static String[] warriorBg = {
    "   |^^^|",
    "    }_{",
    "    }_{",
    "/|_/---\\_|\\",
    "I _|\\_/|_ I",
    "\\| |   | |/",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   |   |",
    "   \\   /",
    "    \\ /",
    "     Y",
  };

  public static String[] archer = {
    "                 _              ",
    "  __ _  _ _  __ | |_   ___  _ _ ",
    " / _` || '_|/ _|| ' \\ / -_)| '_|",
    " \\__,_||_|  \\__||_||_|\\___||_|  ",
    "                                ",
  };

  public static String[] archerIcon = {
    " .----------------. ",
    "| .--------------. |",
    "| |      __      | |",
    "| |     /  \\     | |",
    "| |    / /\\ \\    | |",
    "| |   / ____ \\   | |",
    "| | _/ /    \\ \\_ | |",
    "| ||____|  |____|| |",
    "| |              | |",
    "| '--------------' |",
    " '----------------'",
  };

    public static String[] archerBg = {
    "  \\  ^  /",
    "  \\\\ ^ //",
    "  \\\\\\^///",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "    | |",
    "   \\   /",
    "    \\ /",
    "     Y",
  };

  public static String[] mage = {
    "                         ",
    "  _ __   __ _  __ _  ___ ",
    " | '  \\ / _` |/ _` |/ -_)",
    " |_|_|_|\\__,_|\\__, |\\___|",
    "              |___/      ",
  };

  public static String[] mageIcon = {
    " .----------------. ",
    "| .--------------. |",
    "| | ____    ____ | |",
    "| ||_   \\  /   _|| |",
    "| |  |   \\/   |  | |",
    "| |  | |\\  /| |  | |",
    "| | _| |_\\/_| |_ | |",
    "| ||_____||_____|| |",
    "| |              | |",
    "| '--------------' |",
    " '----------------' ",
  };

      public static String[] mageBg = {
    "    .--.",
    "    |  |",
    " ___|  |___",
    "|___    ___|",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "    |  |",
    "   (    )",
    "   |    |",
    "    `--'",
  };

  public static String[] knight = {
    "                                .-'`-.",
    "                               /  | | \\",
    "                              /   | |  \\",
    "                             |  __|_|___|",
    "                             |' |||",
    "                             |(   _L   ||",
    "                             \\|`-'__`-'|'",
    "                              |  `--'  |",
    "                             _|        |-.",
    "                         .-'| |  \\     /  `-.",
    "                        /   | |\\     .'      `-.",
    "                       /    | | `''''           \\",
    "                      J     | |             _____|",
    "                      |  |  J J         .-'   ___ `-.",
    "                      |  \\   L L      .'  .-'  |  `-.`.",
    "                      | \\|   | |     /  .'|    |    |\\ \\",
    "                      |  |   J J   .' .'  |    |    | \\ \\",
    "                      |  |    L L J  /    |    |    |  \\ L",
    "                     /   |     \\ \\F J|    |    |    |   LJ",
    "                     |   |      \\J F | () | () | () | ()| L",
    "                    J  \\ |       FJ  |    |  / _`-. |   J |",
    "                    |    |      J |  |    | //    \\ |   J |",
    "                   J     |\\     | |  |    ||:(     ||   J |",
    "                   |     | `----| |  |    ||::`._.:||   | F",
    "                   |     /\\_    | |  |    ||:::::::F|   | F",
    "                   |    |  /`---| |  |    | \\:::::/ |   FJ",
    "                   F    |  / |  J |  |    |  `-:-'  |  J F",
    "                  J_.--/  /  |  J J  | () | () | () |()FJ",
    "                   |  |    /     L L |    |    |    | / F",
    "                   |  |     |    \\ \\ |    |    |    |/ /",
    "                 |`-. |    |     |\\ \\|    |    |    / /",
    "                 J'\\ \\|    |     | `.`.   |    |  .'.'",
    "                / .'> |    |     |  `-.`-.|____.-'.'",
    "              .' /::'_|    |/    |    `-.______.-'",
    "             / .::/   \\    |     |           |  |",
    "           .' /::'     |--._     |           |--|",
    "          / .::/       |    `-.__|     ____.-|//|",
    "        .' /::'        |        F `--'      ||< |",
    "       / .::/          |       J   |        FL\\\\|",
    "     .' /::'           )       |   |        F| >|",
    "    / .::/            (        \\   |        F|//|",
    "  .' /::'              \\       /   |        F|--|",
    " / .::/                 |` `' '(   (      ' J|  |",
    "| /::'                  |  | ` \\   \\  `    / J  |",
    "|_:/                    |  | | |    |`-  ''F J  J",
    "                        |    ' F    |   \"\" |  `-'",
    "                        |     J     |      |",
    "                        |     /     |      |",
    "                        |   .'      |      F",
    "                       /---'(       J     J",
    "                     .'     \\        L    |",
    "                  .-'        )       L    F",
    "                .'       .---'       \\__.-'",
    "               (       .'             L   |",
    "                `-----'               |   \\",
    "                                      J    \\",
    "                                       L    L",
    "                                       |    F",
    "                                       `-.-'"
  };

  public static String[] goblin = {
    "                      _..",
    "                    .'   `\",",
    "                   ;        \\",
    "            .---._; ^,       ;",
    "         .-'      ;{ :  .-. ._;",
    "    .--\"\"          \\*'   o/ o/",
    "   /   ,  /         :    _`\";",
    "  ;     \\;          `.   `\"+'",
    "  |      }    /    _.'T\"--\"\\",
    "  :     /   .'.--\"\"-,_ \\    ;",
    "   \\   /   /_         `,\\   ;",
    "    : /   /  `-.,_      \\`.  :",
    "    |;   {     .' `-     ; `, \\",
    "    : \\  `;   {  `-,__..-'   \\ `}+=,",
    "     : \\  ;    `.   `,        `-,\\\"",
    "     ! |\\ `;     \\}?\\|}",
    "  .-'  | \\ ;",
    ".'}/ i.'  \\ `,",
    "``''-'    /   \\",
    "         /J|/{/",
    "           `'",
  };

  public static String[] dragon2 = {
    "      ^                       ^",
    "      |\\   \\        /        /|",
    "     /  \\  |\\__  __/|       /  \\",
    "    / /\\ \\ \\ _ \\/ _ /      /    \\",
    "   / / /\\ \\ {*}\\/{*}      /  / \\ \\",
    "   | | | \\ \\( (00) )     /  // |\\ \\",
    "   | | | |\\ \\(V\"\"V)\\    /  / | || \\|",
    "   | | | | \\ |^--^| \\  /  / || || ||",
    "  / / /  | |( WWWW__ \\/  /| || || ||",
    " | | | | | |  \\______\\  / / || || ||",
    " | | | / | | )|______\\ ) | / | || ||",
    " / / /  / /  /______/   /| \\ \\ || ||",
    "/ / /  / /  /\\_____/  |/ /__\\ \\ \\ \\ \\",
    "| | | / /  /\\______/    \\   \\__| \\ \\ \\",
    "| | | | | |\\______ __    \\_    \\__|_| \\",
    "| | ,___ /\\______ _  _     \\_       \\  |",
    "| |/    /\\_____  /    \\      \\__     \\ |    /\\",
    "|/ |   |\\______ |      |        \\___  \\ |__/  \\",
    "v  |   |\\______ |      |            \\___/     |",
    "   |   |\\______ |      |                    __/",
    "    \\   \\________\\_    _\\               ____/",
    "  __/   /\\_____ __/   /   )\\_,      _____/",
    " /  ___/  \\uuuu/  ___/___)    \\______/",
    " VVV  V        VVV  V ",
  };

  public static String[] skeleton = {
    "                              _.--\"\"-._",
    "  .                         .\"         \".",
    " / \\    ,^.         /(     Y             |      )\\",
    "/   `---. |--'\\    (  \\__..'--   -   -- -'\"\"-.-'  )",
    "|        :|    `>   '.     l_..-------.._l      .'",
    "|      __l;__ .'      \"-.__.||_.-'v'-._||`\"----\"",
    " \\  .-' | |  `              l._       _.'",
    "  \\/    | |                   l`^^'^^'j",
    "        | |                _   \\_____/     _",
    "        j |               l `--__)-'(__.--' |",
    "        | |               | /`---``-----'\"1 |  ,-----.",
    "        | |               )/  `--' '---'   \\'-'  ___  `-.",
    "        | |              //  `-'  '`----'  /  ,-'   I`.  \\",
    "      _ L |_            //  `-.-.'`-----' /  /  |   |  `. \\",
    "     '._' / \\         _/(   `/   )- ---' ;  /__.J   L.__.\\ :",
    "      `._;/7(-.......'  /        ) (     |  |            | |",
    "      `._;l _'--------_/        )-'/     :  |___.    _._./ ;",
    "        | |                 .__ )-'\\  __  \\  \\  I   1   / /",
    "        `-'                /   `-\\-(-'   \\ \\  `.|   | ,' /",
    "                           \\__  `-'    __/  `-. `---'',-'",
    "                              )-._.-- (        `-----'",
    "                             )(  l\\ o ('..-.",
    "                       _..--' _'-' '--'.-. |",
    "                __,,-'' _,,-''            \\ \\",
    "               f'. _,,-'                   \\ \\",
    "              ()--  |                       \\ \\",
    "                \\.  |                       /  \\",
    "                  \\ \\                      |._  |",
    "                   \\ \\                     |  ()|",
    "                    \\ \\                     \\  /",
    "                     ) `-.                   | |",
    "                    // .__)                  | |",
    "                 _.//7'                      | |",
    "               '---'                         j_| `",
    "                                            (| |",
    "                                             |  \\",
    "                                             |lllj",
    "                                             |||||  ",
  };

  public static String[] reaper = {
    "                                           .\"\"--.._",
    "                                           []      `'--.._",
    "                                           ||__           `'-,",
    "                                         `)||_ ```'--..       \\",
    "                     _                    /|//}        ``--._  |",
    "                  .'` `'.                /////}              `\\/",
    "                 /  .\"\"\".\\              //{///",
    "                /  /_  _`\\            // `||",
    "                | |(_)(_)||          _//   ||",
    "                | |  /\\  )|        _///\\   ||",
    "                | |L====J |       / |/ |   ||",
    "               /  /'-..-' /    .'`  \\  |   ||",
    "              /   |  :: | |_.-`      |  \\  ||",
    "             /|   `\\-::.| |          \\   | ||",
    "           /` `|   /    | |          |   / ||",
    "         |`    \\   |    / /          \\  |  ||",
    "        |       `\\_|    |/      ,.__. \\ |  ||",
    "        /                     /`    `\\ ||  ||",
    "       |           .         /        \\||  ||",
    "       |                     |         |/  ||",
    "       /         /           |         (   ||",
    "      /          .           /          )  ||",
    "     |            \\          |             ||",
    "    /             |          /             ||",
    "   |\\            /          |              ||",
    "   \\ `-._       |           /              ||",
    "    \\ ,//`\\    /`           |              ||",
    "     ///\\  \\  |             \\              ||",
    "    |||| ) |__/             |              ||",
    "    |||| `.(                |              ||",
    "    `\\` /`                  /              ||",
    "       /`                   /              ||",
    "      /                     |              ||",
    "     |                      \\              ||",
    "    /                        |             ||",
    "  /`                          \\            ||",
    "/`                            |            ||",
    "`-.___,-.      .-.        ___,'            ||",
    "         `---'`   `'----'`",
  };

  public static String[] dragon = {
          "                             'X)                                            ",
                  "                              )|                                            ",
                  "                              )|                                            ",
                  "                             _)|                                            ",
                  "                             )||                                  /X`       ",
                  "                            _)||                                 //(        ",
                  "                           _) ||                                // (        ",
                  "                          _)  ||                               // (         ",
                  "                         _)   ||                              //  (         ",
                  "                       __)    ||                             //   (         ",
                  "                     __)      ||                            //    /         ",
                  "                     )        ||                           //    (          ",
                  "                   'X\\        ||                          //     (          ",
                  "                    )\\\\       ||                         //      (          ",
                  "                    ) \\\\      ||                        //       /          ",
                  "                     ) \\\\     ||                       //       (           ",
                  "                     )  \\\\    ||                      //        (           ",
                  "                      \\  \\\\   ||                     //         (_          ",
                  "                       )  \\\\  ||                    //           (_         ",
                  "                        )  \\\\ ||                   //             (_        ",
                  "                        \\   \\\\||                  //                \\       ",
                  "                         )   >. )               _/(__________________X`     ",
                  "                         \\  //||               (  .------------------.>     ",
                  "                         _)// ||                )/\\\\             __.-'      ",
                  "                        _)//  ||               //  \\\\          _(           ",
                  "                        )//.. ||              //    \\\\       _(             ",
                  "                        X/'  \\||             //      \\\\     (               ",
                  "           __   _____,_,,_,,,,||            //        \\\\   (                ",
                  "         .-` \\_/.-._______)   )|           //.--------.\\\\ (                 ",
                  "         `  `-- )        /  \\'||   .--.__.//'-.________`\\X'                 ",
                  "               '        (   ,)||  <\\--<_(//-/> \\       `.                   ",
                  "                         \\ /(\\||  /   ^.'/_/(\\  \\        `.                 ",
                  "                         (   \\|`./  ^.' /<   .-  \\/\\___    \\                ",
                  "                          \\ |/) | ^ /  /  \\ /    /( \\  `-.  \\               ",
                  "                          ( `/  ^  ^   (  \\/>   /(   \\    \\  \\              ",
                  "                           >--) ^  L/     \\(   /(     )    )  )             ",
                  "                        .(\\  / ^/)/ \\   ( / \\ ((     /    /  /              ",
                  "                      .'  )\\/ ^/(/  (    \\)  `-\\\\   /\\   /  /               ",
                  "                     /    > ^   /,   |  //      `\\   /  ( .'                ",
                  "                    /    (=  =)).'  /  //,        ) (    \\`.                ",
                  "                   /  ,  <, ,_>/    > '/',       /   >    \\ )               ",
                  "                   ) '  _/ /\\>'    / ~ )'    ___/ _>'     (/                ",
                  "                  /  .-(^-^)/}-.  / / /     (---. `--.                      ",
                  "                 /  (  (-^-(/   ) \\' /       `   \\ \\--)                     ",
                  "                /  /    )   )   ._/ /             `-)'                      ",
                  "                ) /    (   (   (-^. \\-._           '                        ",
                  "               /  >             `  \\)`--)                                   ",
                  "              (   `--.             '   '                                    ",
                  "              / />----)                                                     ",
                  "             / /\\)   '                                                      ",
                  "            (-'  `                                                          ",
                  "             `"
  };

  public static String[] candles = {
    "                  )                    `",
    "                 /(l                   /)",
    "                (  \\                  / (",
    "                ) * )                ( , )",
    "                 \\#/                  \\#'",
    "               .-\"#'-.             .-\"#\"=,",
    "            (  |\"-.='|            '|\"-,-\"|",
    "            )\\ |     |  ,        /(|     | /(         ,",
    "   (       /  )|     | (\\       (  \\     | ) )       ((",
    "   )\\     (   (|     | ) )      ) , )    |/ (        ) \\",
    "  /  )     ) . )     |/  (     ( # (     ( , )      /   )",
    " ( * (      \\#/|     (`# )      `#/|     |`#/      (  '(",
    "  \\#/     .-\"#'-.   .-\"#'-,   .-\"#'-.   .-=#\"-;     `#/",
    ".-\"#'-.   |\"=,-\"|   |\"-.-\"|)  1\"-.-\"|   |\"-.-\"|   ,-\"#\"-.",
    "|\"-.-\"|   |  !  |   |     |   |     |   |     !   |\"-.-\"|",
    "|     |   |     |._,|     |   |     |._,|     a   |     |",
    "|     |   |     |   |     |   |     |   |     p   |     |",
    "|     |   |     |   |     |   |     |   |     x   |     |",
    "'-._,-'   '-._,-'   '-._,-'   '-._,-'   '-._,-\"   '-._,-'",
  };

  public static String[] gameOver = {"                                                                                                                                    ",
          "      _____           ____        ______  _______        ______                 _____     ____      ____      ______        _____   ",
          "  ___|\\    \\     ____|\\   \\      |      \\/       \\   ___|\\     \\           ____|\\    \\   |    |    |    | ___|\\     \\   ___|\\    \\  ",
          " /    /\\    \\   /    /\\    \\    /          /\\     \\ |     \\     \\         /     /\\    \\  |    |    |    ||     \\     \\ |    |\\    \\ ",
          "|    |  |____| |    |  |    |  /     /\\   / /\\     ||     ,_____/|       /     /  \\    \\ |    |    |    ||     ,_____/||    | |    |",
          "|    |    ____ |    |__|    | /     /\\ \\_/ / /    /||     \\--'\\_|/      |     |    |    ||    |    |    ||     \\--'\\_|/|    |/____/ ",
          "|    |   |    ||    .--.    ||     |  \\|_|/ /    / ||     /___/|        |     |    |    ||    |    |    ||     /___/|  |    |\\    \\ ",
          "|    |   |_,  ||    |  |    ||     |       |    |  ||     \\____|\\       |\\     \\  /    /||\\    \\  /    /||     \\____|\\ |    | |    |",
          "|\\ ___\\___/  /||____|  |____||\\____\\       |____|  /|____ '     /|      | \\_____\\/____/ || \\ ___\\/___ / ||____ '     /||____| |____|",
          "| |   /____ / ||    |  |    || |    |      |    | / |    /_____/ |       \\ |    ||    | / \\ |   ||   | / |    /_____/ ||    | |    |",
          " \\|___|    | / |____|  |____| \\|____|      |____|/  |____|     | /        \\|____||____|/   \\|___||___|/  |____|     | /|____| |____|",
          "   \\( |____|/    \\(      )/      \\(          )/       \\( |_____|/            \\(    )/        \\(    )/      \\( |_____|/   \\(     )/  ",
          "    '   )/        '      '        '          '         '    )/                '    '          '    '        '    )/       '     '   ",
          "        '                                                   '                                                    '                  "
  };

  public static String[] win = {
          " _____      _____        _____     ____   ____         _____                   _____  _____   ______         ",
          "|\\    \\    /    /|  ____|\\    \\   |    | |    |       |\\    \\   _____     ____|\\    \\|\\    \\ |\\     \\        ",
          "| \\    \\  /    / | /     /\\    \\  |    | |    |       | |    | /    /|   /     /\\    \\\\\\    \\| \\     \\       ",
          "|  \\____\\/    /  //     /  \\    \\ |    | |    |       \\/     / |    ||  /     /  \\    \\\\|    \\  \\     |      ",
          " \\ |    /    /  /|     |    |    ||    | |    |       /     /_  \\   \\/ |     |    |    ||     \\  |    |      ",
          "  \\|___/    /  / |     |    |    ||    | |    |      |     // \\  \\   \\ |     |    |    ||      \\ |    |      ",
          "      /    /  /  |\\     \\  /    /||    | |    |      |    |/   \\ |    ||\\     \\  /    /||    |\\ \\|    |      ",
          "     /____/  /   | \\_____\\/____/ ||\\___\\_|____|      |\\ ___/\\   \\|   /|| \\_____\\/____/ ||____||\\_____/|      ",
          "    |`    | /     \\ |    ||    | /| |    |    |      | |   | \\______/ | \\ |    ||    | /|    |/ \\|   ||      ",
          "    |_____|/       \\|____||____|/  \\|____|____|       \\|___|/\\ |    | |  \\|____||____|/ |____|   |___|/      ",
          "       )/             \\(    )/        \\(   )/            \\(   \\|____|/      \\(    )/      \\(       )/        ",
          "       '               '    '          '   '              '      )/          '    '        '       '         ",
          "                                                                 '                                           "
  };

  public static String[][] alphabet = {
    {
      "       ",
      "       ",
      "  __ _ ",
      " / _` |",
      "| (_| |",
      " \\__,_|",
      "       ",
      "       ",
    },

    {
      " _     ",
      "| |    ",
      "| |__  ",
      "| '_ \\ ",
      "| |_) |",
      "|_.__/ ",
      "       ",
      "       ",
    },

    {
      "      ",
      "      ",
      "  ___ ",
      " / __|",
      "| (__ ",
      " \\___|",
      "      ",
      "      ",
    },

    {
      "     _ ",
      "    | |",
      "  __| |",
      " / _` |",
      "| (_| |",
      " \\__,_|",
      "       ",
      "       ",
    },

    {
      "      ",
      "      ",
      " ___  ",
      "/ _ \\ ",
      "|  __/",
      "\\___| ",
      "      ",
      "      ",
    },

    {
      "  __ ",
      " / _|",
      "| |_ ",
      "|  _|",
      "| |  ",
      "|_|  ",
      "     ",
      "     ",
    },

    {
      "       ",
      "       ",
      "  __ _ ",
      " / _` |",
      "| (_| |",
      " \\__, |",
      "  __/ |",
      " |___/ ",
    },

    {
      " _     ",
      "| |    ",
      "| |__  ",
      "| '_ \\ ",
      "| | | |",
      "|_| |_|",
      "       ",
      "       ",
    },

    {
      " _ ",
      "(_)",
      " _ ",
      "| |",
      "| |",
      "|_|",
      "   ",
      "   ",
    },

    {
      "   _ ",
      "  (_)",
      "   _ ",
      "  | |",
      "  | |",
      "  | |",
      " _/ |",
      "|__/ ",
    },

    {
      " _    ",
      "| |   ",
      "| | __",
      "| |/ /",
      "|   < ",
      "|_|\\_\\",
      "      ",
      "      ",
    },

    {
      " _ ",
      "| |",
      "| |",
      "| |",
      "| |",
      "|_|",
      "   ",
      "   ",
    },

    {
      "           ",
      "           ",
      " _ __ ___  ",
      "| '_ ` _ \\ ",
      "| | | | | |",
      "|_| |_| |_|",
      "           ",
      "           ",
    },

    {
      "       ",
      "       ",
      " _ __  ",
      "| '_ \\ ",
      "| | | |",
      "|_| |_|",
      "       ",
      "       ",
    },

    {
      "       ",
      "       ",
      "  ___  ",
      " / _ \\ ",
      "| (_) |",
      " \\___/ ",
      "       ",
      "       ",
    },

    {
      "       ",
      "       ",
      " _ __  ",
      "| '_ \\ ",
      "| |_) |",
      "| .__/ ",
      "| |    ",
      "|_|    ",
    },

    {
      "       ",
      "       ",
      "  __ _ ",
      " / _` |",
      "| (_| |",
      " \\__, |",
      "    | |",
      "    |_|",
    },

    {
      "      ",
      "      ",
      " _ __ ",
      "| '__|",
      "| |   ",
      "|_|   ",
      "      ",
      "      ",
    },

    {
      "     ",
      "     ",
      " ___ ",
      "/ __|",
      "\\__ \\",
      "|___/",
      "     ",
      "     ",
    },

    {
      " _   ",
      "| |  ",
      "| |_ ",
      "| __|",
      "| |_ ",
      " \\__|",
      "     ",
      "     ",
    },

    {
      "       ",
      "       ",
      " _   _ ",
      "| | | |",
      "| |_| |",
      " \\__,_|",
      "       ",
      "       ",
    },

    {
      "       ",
      "       ",
      "__   __",
      "\\ \\ / /",
      " \\ V / ",
      "  \\_/  ",
      "       ",
      "       ",
    },

    {
      "          ",
      "          ",
      "__      __",
      "\\ \\ /\\ / /",
      " \\ V  V / ",
      "  \\_/\\_/  ",
      "          ",
      "          ",
    },

    {
      "      ",
      "      ",
      "__  __",
      "\\ \\/ /",
      " >  < ",
      "/_/\\_\\",
      "      ",
      "      ",
    },

    {
      "       ",
      "       ",
      " _   _ ",
      "| | | |",
      "| |_| |",
      " \\__, |",
      "  __/ |",
      " |___/ ",
    },

    {
      "     ",
      "     ",
      "____ ",
      "|_  /",
      " / / ",
      "/___|",
      "     ",
      "     ",
    },
  };



}
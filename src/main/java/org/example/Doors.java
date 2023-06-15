package org.example;

public class Doors {

    public static String[] generateRightDoor(String[] ref) {
        String[] res = new String[ref.length];

        for (int i = 0; i < ref.length; i++) {
            String line = ref[i];
            switch (i) {
                case 7:
                    line = line.substring(0, line.length()-4) + "3---";
                    break;
                case 8:
                    line = line.substring(0, line.length()-4) + "****";
                    break;
                case 9:
                    line = line.substring(0, line.length()-4) + "1---";
                    break;
                default:
                    break;
            }
            res[i] = line;
        }
        return res;
    }

    public static String[] generateLeftDoor(String[] ref) {
        String[] res = new String[ref.length];

        for (int i = 0; i < 17; i++) {
            String line = ref[i];
            switch (i) {
                case 7:
                    line = "---4" + line.substring(4);
                    break;
                case 8:
                    line = "****" + line.substring(4);
                    break;
                case 9:
                    line =  "---2" + line.substring(4);
                    break;
                default:
                    break;
            }
            res[i] = line;
        }
        return res;
    }

    public static String[] generateTopDoor(String[] ref) {
        String[] res = new String[ref.length];

        for (int i = 0; i < 17; i++) {
            String line = ref[i];
            switch (i) {
                case 0:
                    line = line.substring(0,7) + "|*|" + line.substring(10);
                    break;
                case 1:
                    line = line.substring(0,7) + "4*3" + line.substring(10);
                    break;
                default:
                    break;
            }
            res[i] = line;
        }
        return res;
    }

    public static String[] generateBotDoor(String[] ref) {
        String[] res = new String[ref.length];

        for (int i = 0; i < 17; i++) {
            String line = ref[i];
            switch (i) {
                case 15:
                    line = line.substring(0,7) + "|*|" + line.substring(10);
                    break;
                case 16:
                    line = line.substring(0,7) + "2*1" + line.substring(10);
                    break;
                default:
                    break;
            }
            res[i] = line;
        }
        return res;
    }

}

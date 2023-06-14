package org.example;

import java.util.*;

public class MapGenerator {

    public static List<Room> generate() {
        List<Room> rooms = new ArrayList<>();
        boolean[] taken = new boolean[40];
        int[] ids = new int[40];
        for (int i = 0; i<40; i++) {
            taken[i] = false;
        }

        generateBigRooms(taken, rooms, ids);

        generateMediumRooms(taken, rooms, ids);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(ids[i*8 + j]);
            }
            System.out.println();
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {

            }
            System.out.println();
        }

        generateSmallRoomAndDoors(taken, rooms, ids);
        return rooms;
    }

    public static void generateSmallRoomAndDoors(boolean[] taken, List<Room> rooms, int[] ids) {
        HashMap<Room, List<Room>> liaisons = new HashMap<Room, List<Room>>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 5; y++) {
                if (taken[x + y*8]) {
                    if (y == 4) {
                        if (x != 7) {
                            Room currentRoom = getRoomByXY(rooms, x, y);
                            if (taken[x + 8 * y + 1]) {
                                Room rightRoom = getRoomByXY(rooms, x+1,y);
                                System.out.println("The test: " + rightRoom.equals(currentRoom));
                                if (!rightRoom.equals(currentRoom)) {
                                    System.out.println("differents !");
                                    addLiaisons(liaisons, currentRoom, rightRoom);


                                    System.out.println(x + " : " + y);

                                    boolean verif = y == currentRoom.getY();
                                    String[] strs = Arrays.copyOfRange(currentRoom.getRoom(), y==currentRoom.getY() ? 0 : 17, y==currentRoom.getY() ? 16 : 33);
                                    String[] others = Arrays.copyOfRange(currentRoom.getRoom(), y==currentRoom.getY() ? 17 : 0, y==currentRoom.getY() ? 33 : 16);

                                    for (String f : strs) {
                                        System.out.println(f);
                                    }
                                    System.out.println();


                                    strs = Doors.generateRightDoor(strs);

                                    for (String s : strs) {
                                        System.out.println(s);
                                    }
                                    System.out.println();

                                    String[] def = new String[currentRoom.getRoom().length];
                                    for (int i = 0; i < def.length; i++) {
                                        def[i] = verif ? (i < 17 ? strs[i] : others[17-i]) : (i < 17 ? others[i] : strs[17-i]);
                                    }
                                    currentRoom.setRoom(def);

                                    for (String s : currentRoom.getRoom()) {
                                        System.out.println(s);
                                    }
                                    System.out.println();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void addLiaisons(HashMap<Room, List<Room>> liaisons, Room a, Room b) {
        if (liaisons.isEmpty()) liaisons.put(a, new ArrayList<Room>());
        if (!liaisons.keySet().contains(a)) liaisons.put(a, new ArrayList<Room>());
        List<Room> ar = liaisons.get(a);
        ar.add(b);
        liaisons.put(a, ar);
        if (!liaisons.keySet().contains(b)) liaisons.put(b, new ArrayList<Room>());
        List<Room> br = liaisons.get(b);
        br.add(a);
        liaisons.put(b, br);
    }

    public static Room getRoomByXY(List<Room> rooms, int x, int y) {
        for (Room room : rooms) {
            if ((room.getX()==x || room.getX() + room.getSx()-1==x) && (room.getY()==y || room.getY() + room.getSy() - 1 == y)) {
                /*System.out.println("getRoomByXY:");
                for (String s : room.getRoom()) {
                    System.out.println(s);
                }
                System.out.println();*/
                System.out.println("Sx: " + room.getSx() + " : Sy: " + room.getSy());
                return room;
            }
        }
        return null;
    }

    public static void generateMediumRooms(boolean[] taken, List<Room> rooms, int[] ids) {
        Random ran = new Random();

        for (int i = 0; i < 4; i++) {
            int x = ran.nextInt(8);
            int y = ran.nextInt(5);

            int choice = ran.nextInt(2);

            Room room;
            switch ((choice)) {
                case 0:
                    if (x == 7) {
                        i--;
                        break;
                    }
                    if (!taken[x + y*8] && !taken[x + y*8 +1]) {
                        room = new Room(x, y, 2, 1, Rooms.room2x1, 6);
                        rooms.add(room);

                        ids[x + y * 8] = 6;
                        ids[x + y * 8 + 1] = 6;
                        taken[x + y * 8] = true;
                        taken[x + y * 8 + 1] = true;
                    } else {
                        i--;
                    }
                    break;
                case 1:
                    if (y == 4) {
                        i--;
                        break;
                    }
                    if (!taken[x + y*8] && !taken[x + (y+1)*8]) {
                        room = new Room(x, y, 1, 2, Rooms.room1x2, 7);
                        rooms.add(room);

                        ids[x + y * 8] = 7;
                        ids[x + (y + 1) * 8] = 7;
                        taken[x + y * 8] = false;
                        taken[x + (y + 1) * 8] = false;
                    } else {
                        i--;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void generateBigRooms(boolean[] taken, List<Room> rooms, int[] ids) {
        Random ran = new Random();

        for (int i = 0; i < 4; i++) {
            int x = ran.nextInt(8);
            int y = ran.nextInt(5);

            if (x == 7 || y == 4) {
                i--;
                continue;
            }

            if (!taken[x + y*8] && !taken[x + y*8 +1] && !taken[x + (y+1)*8] && !taken[x + (y+1)*8 +1]) {

                taken[x + y*8] = true;
                taken[x + y*8 + 1] = true;
                taken[x + (y+1)*8] = true;
                taken[x + (y+1)*8 + 1] = true;
                int choice = ran.nextInt(5);

                Room room;
                switch ((choice)) {
                    case 0:
                        room = new Room(x,y,2,2,Rooms.room2x2v1, 1);
                        rooms.add(room);

                        ids[x + y*8] = 1;
                        ids[x + y*8 + 1] = 1;
                        ids[x + (y+1)*8] = 1;
                        ids[x + (y+1)*8 + 1] = 1;
                        break;
                    case 1:
                        room = new Room(x,y,2,2,Rooms.room2x2v2, 2);
                        rooms.add(room);

                        ids[x + y*8] = 2;
                        ids[x + y*8 + 1] = 2;
                        ids[x + (y+1)*8] = 2;
                        break;
                    case 2:
                        room = new Room(x,y,2,2,Rooms.room2x2v3, 3);
                        rooms.add(room);

                        ids[x + y*8] = 3;
                        ids[x + y*8 + 1] = 3;
                        ids[x + (y+1)*8 + 1] = 3;
                        break;
                    case 3:
                        room = new Room(x,y,2,2,Rooms.room2x2v4, 4);
                        rooms.add(room);

                        ids[x + y*8] = 4;
                        ids[x + (y+1)*8] = 4;
                        ids[x + (y+1)*8 + 1] = 4;
                        break;
                    case 4:
                        room = new Room(x,y,2,2,Rooms.room2x2v5, 5);
                        rooms.add(room);

                        ids[x + y*8 + 1] = 5;
                        ids[x + (y+1)*8] = 5;
                        ids[x + (y+1)*8 + 1] = 5;
                        break;
                    default:
                        break;
                }
            } else {
                i--;
            }
        }

    }

}

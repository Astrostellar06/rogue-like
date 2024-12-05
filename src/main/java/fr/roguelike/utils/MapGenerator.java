package fr.roguelike.utils;

import fr.roguelike.models.Room;

import java.util.*;

public class MapGenerator {

    public static ArrayList<Room> generate() {
        ArrayList<Room> rooms = new ArrayList<>();
        boolean[] taken = new boolean[40];
        int[] ids = new int[40];
        for (int i = 0; i<40; i++) {
            taken[i] = false;
        }

        generateBigRooms(taken, rooms, ids);

        generateMediumRooms(taken, rooms, ids);

        generateSmallRoomAndDoors(taken, rooms, ids);
        return rooms;
    }

    public static void generateSmallRoomAndDoors(boolean[] taken, List<Room> rooms, int[] ids) {
        HashMap<Room, List<Room>> liaisons = new HashMap<Room, List<Room>>();
        Random ran = new Random();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 5; y++) {
                Room currentRoom = getRoomByXY(rooms, x, y);
                if (taken[x + y*8]) {
                    if (y == 4) {
                        if (x != 7) {
                            if (taken[x + 8 * y + 1]) {
                                Room rightRoom = getRoomByXY(rooms, x+1,y);
                                if (rightRoom == null) continue;
                                if (!rightRoom.equals(currentRoom) && (liaisons.get(currentRoom) == null || !liaisons.get(currentRoom).contains(rightRoom))
                                        && !(currentRoom.getRoomtype() == 2 && currentRoom.getY() != y)
                                        && !(currentRoom.getRoomtype() == 4 && currentRoom.getY() == y)
                                        && !(rightRoom.getRoomtype() == 3 && rightRoom.getY() != y)
                                        && !(rightRoom.getRoomtype() == 5 && rightRoom.getY() == y)) {
                                    addLiaisons(liaisons, currentRoom, rightRoom);

                                    updateRoomWithRightDoors(currentRoom,y);

                                    updateRoomWithLeftDoors(rightRoom,y);
                                }
                            }
                        }
                    }
                    else if (x == 7) {
                        if (y != 4) {
                            if (taken[x + 8 * (y+1)]) {
                                Room botRoom = getRoomByXY(rooms, x,y+1);
                                if (botRoom == null) continue;
                                if (!botRoom.equals(currentRoom) && (liaisons.get(currentRoom) == null || !liaisons.get(currentRoom).contains(botRoom))
                                        && !(currentRoom.getRoomtype() == 2 && currentRoom.getX() != x)
                                        && !(currentRoom.getRoomtype() == 3 && currentRoom.getX() == x)
                                        && !(botRoom.getRoomtype() == 4 && botRoom.getX() != x)
                                        && !(botRoom.getRoomtype() == 5 && botRoom.getX() == x)) {
                                    addLiaisons(liaisons, currentRoom, botRoom);

                                    updateRoomWithBotDoors(currentRoom,x);

                                    updateRoomWithTopDoors(botRoom,x);
                                }
                            }
                        }
                    } else {
                        Room rightRoom = getRoomByXY(rooms, x+1,y);
                        if (rightRoom == null) {
                            int choice = ran.nextInt(4);
                            if (choice < 3) {
                                if (!(currentRoom.getRoomtype() == 2 && currentRoom.getY() != y)
                                        && !(currentRoom.getRoomtype() == 4 && currentRoom.getY() == y)) {
                                    rightRoom = new Room(x+1, y, 1, 1, Rooms.room1x1, 7);
                                    rooms.add(rightRoom);
                                    ids[x + y * 8+1] = 7;
                                    taken[x + y * 8+1] = true;
                                    addLiaisons(liaisons, currentRoom, rightRoom);

                                    updateRoomWithRightDoors(currentRoom,y);

                                    updateRoomWithLeftDoors(rightRoom,y);
                                }
                            }
                        }
                        if (taken[x + 8 * y + 1]) {
                            if (!rightRoom.equals(currentRoom) && (liaisons.get(currentRoom) == null || !liaisons.get(currentRoom).contains(rightRoom))
                                    && !(currentRoom.getRoomtype() == 2 && currentRoom.getY() != y)
                                    && !(currentRoom.getRoomtype() == 4 && currentRoom.getY() == y)
                                    && !(rightRoom.getRoomtype() == 3 && rightRoom.getY() != y)
                                    && !(rightRoom.getRoomtype() == 5 && rightRoom.getY() == y)) {
                                addLiaisons(liaisons, currentRoom, rightRoom);

                                updateRoomWithRightDoors(currentRoom,y);

                                updateRoomWithLeftDoors(rightRoom,y);
                            }
                        }
                        Room botRoom = getRoomByXY(rooms, x,y+1);
                        if (botRoom == null) {
                            int choice = ran.nextInt(4);
                            if (choice < 3) {
                                if (!(currentRoom.getRoomtype() == 2 && currentRoom.getX() != x)
                                        && !(currentRoom.getRoomtype() == 3 && currentRoom.getX() == x)) {
                                    botRoom = new Room(x, y+1, 1, 1, Rooms.room1x1, 7);
                                    rooms.add(botRoom);
                                    ids[x + (y+1) * 8] = 7;
                                    taken[x + (y+1) * 8] = true;
                                    addLiaisons(liaisons, currentRoom, botRoom);

                                    updateRoomWithBotDoors(currentRoom,x);

                                    updateRoomWithTopDoors(botRoom,x);
                                }
                            }
                        }
                        if (taken[x + 8 * (y+1)]) {
                            if (!botRoom.equals(currentRoom) && (liaisons.get(currentRoom) == null || !liaisons.get(currentRoom).contains(botRoom))
                                    && !(currentRoom.getRoomtype() == 2 && currentRoom.getX() != x)
                                    && !(currentRoom.getRoomtype() == 3 && currentRoom.getX() == x)
                                    && !(botRoom.getRoomtype() == 4 && botRoom.getX() != x)
                                    && !(botRoom.getRoomtype() == 5 && botRoom.getX() == x)) {
                                addLiaisons(liaisons, currentRoom, botRoom);

                                updateRoomWithBotDoors(currentRoom,x);

                                updateRoomWithTopDoors(botRoom,x);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void updateRoomWithRightDoors(Room room, int y) {
        boolean verif = y == room.getY();
        boolean doubleY = room.getSy() == 2;

        String[] strs = Arrays.copyOfRange(room.getRoom(), verif ? 0 : 17, verif ? 17 : 34);


        String[] others = new String[17];
        if (doubleY) others = Arrays.copyOfRange(room.getRoom(), verif ? 17 : 0, verif ? 34 : 17);


        strs = Doors.generateRightDoor(strs);

        String[] def = new String[room.getRoom().length];
        for (int i = 0; i < def.length; i++) {
            if (doubleY) {
                def[i] = verif ? (i < 17 ? strs[i] : others[i-17]) : (i < 17 ? others[i] : strs[i-17]);
            } else {
                def[i] = strs[i];
            }
        }
        room.setRoom(def);
    }

    public static void updateRoomWithTopDoors(Room room, int x) {
        boolean verif = x == room.getX();
        boolean doubleX = room.getSx() == 2;
        boolean doubleY = room.getSy() == 2;

        String[] strs = Arrays.copyOfRange(room.getRoom(), 0, 17);


        String[] others = new String[17];
        if (doubleY) others = Arrays.copyOfRange(room.getRoom(), 17, 34);

        String[] cutted = new String[17];
        if (doubleX) {
            for (int i = 0; i < 17; i++) {
                cutted[i] = strs[i].substring(verif ? 0 : 17,verif ? 17 : 34);
            }
        } else {
            cutted = strs;
        }

        cutted = Doors.generateTopDoor(cutted);

        if (doubleX) {
            for (int i = 0; i < 17; i++) {
                strs[i] = verif ? cutted[i] + strs[i].substring(17, 34) : strs[i].substring(0,17) + cutted[i];
            }
        } else {
            strs = cutted;
        }

        String[] def = new String[room.getRoom().length];
        for (int i = 0; i < def.length; i++) {
            if (i < 17) {
                def[i] = strs[i];
            } else {
                if (doubleY) def[i] = others[i-17];
            }
        }
        room.setRoom(def);
    }

    public static void updateRoomWithBotDoors(Room room, int x) {
        boolean verif = x == room.getX();
        boolean doubleX = room.getSx() == 2;
        boolean doubleY = room.getSy() == 2;

        String[] strs = Arrays.copyOfRange(room.getRoom(), doubleY ? 17 : 0, doubleY ? 34 : 17);


        String[] others = new String[17];
        if (doubleY) others = Arrays.copyOfRange(room.getRoom(), 0, 17);

        String[] cutted = new String[17];
        if (doubleX) {
            for (int i = 0; i < 17; i++) {
                cutted[i] = strs[i].substring(verif ? 0 : 17,verif ? 17 : 34);
            }
        } else {
            cutted = strs;
        }

        cutted = Doors.generateBotDoor(cutted);

        if (doubleX) {
            for (int i = 0; i < 17; i++) {
                strs[i] = verif ? cutted[i] + strs[i].substring(17, 34) : strs[i].substring(0,17) + cutted[i];
            }
        } else {
            strs = cutted;
        }

        String[] def = new String[room.getRoom().length];
        for (int i = 0; i < def.length; i++) {
            if (doubleY) {
                if (i > 16) {
                    def[i] = strs[i-17];
                } else {
                    def[i] = others[i];
                }
            } else {
                def[i] = strs[i];
            }
        }
        room.setRoom(def);
    }

    public static void updateRoomWithLeftDoors(Room room, int y) {
        boolean verif = y == room.getY();
        boolean doubleY = room.getSy() == 2;

        String[] strs = Arrays.copyOfRange(room.getRoom(), y==room.getY() ? 0 : 17, y==room.getY() ? 17 : 34);


        String[] others = new String[17];
        if (doubleY) others = Arrays.copyOfRange(room.getRoom(), y==room.getY() ? 17 : 0, y==room.getY() ? 34 : 17);


        strs = Doors.generateLeftDoor(strs);

        String[] def = new String[room.getRoom().length];
        for (int i = 0; i < def.length; i++) {
            if (doubleY) {
                def[i] = verif ? (i < 17 ? strs[i] : others[i-17]) : (i < 17 ? others[i] : strs[i-17]);
            } else {
                def[i] = strs[i];
            }
        }
        room.setRoom(def);
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
                        taken[x + y * 8] = true;
                        taken[x + (y + 1) * 8] = true;
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

        for (int i = 0; i < 5; i++) {
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

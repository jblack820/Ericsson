package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    //Solution for task 3

    final char EMPTY_FIELD = '.';
    final char ASSASSIN = 'A';
    final char GUARD_WATCHING_LEFT = '<';
    final char GUARD_WATCHING_RIGHT = '>';
    final char GUARD_WATCHING_DOWN = 'v';
    final char GUARD_WATCHING_UP = '^';
    final char GUARDED_FIELD = 'G';
    final char ASSASSIN_SAFE_FIELD_CHARACTER = 'P';
    final List<char[]> TABLE = new ArrayList<>();
    boolean isAssassinSeen = false;
    static class Field {
        private final int x;
        private final int y;
        private boolean isNeighboursChecked;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setNeighboursChecked(boolean neighboursChecked) {
            isNeighboursChecked = neighboursChecked;
        }

        public Field(int x, int y, boolean neighboursChecked) {
            this.x = x;
            this.y = y;
            this.isNeighboursChecked = neighboursChecked;
        }
    }
    Field assassinField;
    final List<Field> ASSASSIN_SAFE_PATH_FIELD_LIST = new ArrayList<>();

    public boolean solution(String[] B) {
        createCharTable(B);
        markFieldsWatchedByGuards();
        if (isAssassinSeen) {
            return false;
        }
        markSafeFieldsForAssassin();
        return isAssassinTargetFieldSafe();
    }

    private boolean isAssassinTargetFieldSafe() {
        return getCharacterOnTable(TABLE.size() - 1, TABLE.get(0).length - 1) == ASSASSIN_SAFE_FIELD_CHARACTER;
    }

    public static void printTable(List<char[]> table) {
        for (char[] arr : table) {
            System.out.println(Arrays.toString(arr));
        }
    }

    private void assassinSpotted() {
        isAssassinSeen = true;
    }

    private void markSafeFieldsForAssassin() {
        assassinField = getAssassinLocation();
        ASSASSIN_SAFE_PATH_FIELD_LIST.add(assassinField);
        calculateAllSafeFields();
    }

    private void calculateAllSafeFields() {
        while (isThereUncheckedFields()) {
            for (int i = 0; i < ASSASSIN_SAFE_PATH_FIELD_LIST.size(); i++) {
                if (!ASSASSIN_SAFE_PATH_FIELD_LIST.get(i).isNeighboursChecked){
                    checkPositionForFreeFieldsAround(ASSASSIN_SAFE_PATH_FIELD_LIST.get(i));
                }
            }
        }
    }

    private boolean isThereUncheckedFields() {
        for (int i = 0; i < ASSASSIN_SAFE_PATH_FIELD_LIST.size(); i++) {
            if (!ASSASSIN_SAFE_PATH_FIELD_LIST.get(i).isNeighboursChecked){
                return true;
            }
        }

        return false;
    }

    public char getCharacterOnTable(int posY, int posX) {
        return TABLE.get(posY)[posX];
    }

    public char getCharacterOnTable(Field field) {
        return TABLE.get(field.getY())[field.getX()];
    }

    public void markTableField(Field field, char character) {
        TABLE.get(field.getY())[field.getX()] = character;
    }

    private void checkPositionForFreeFieldsAround(Field currentField) {

        List<Field> newFreePathFields = new ArrayList<>();

        //check upper left
        if (currentField.getY() > 0 && currentField.getX() > 0) {
            Field targetField = new Field(currentField.getX() - 1, currentField.getY() - 1, false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check left
        if (currentField.getX() > 0) {
            Field targetField = new Field(currentField.getX() - 1, currentField.getY(), false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check bottom left
        if (currentField.getY() < TABLE.size() - 1 && currentField.getX() > 0) {
            Field targetField = new Field(currentField.getX() - 1, currentField.getY() + 1, false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check bottom
        if (currentField.getY() < TABLE.size() - 1) {
            Field targetField = new Field(currentField.getX(), currentField.getY() + 1, false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check bottom right
        if (currentField.getY() < TABLE.size() - 1 && currentField.getX() < TABLE.get(0).length - 1) {
            Field targetField = new Field(currentField.getX() + 1, currentField.getY() + 1, false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check right
        if (currentField.getX() < TABLE.get(0).length - 1) {
            Field targetField = new Field(currentField.getX() + 1, currentField.getY(), false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check upper right
        if (currentField.getY() > 0 && currentField.getX() < TABLE.get(0).length - 1) {
            Field targetField = new Field(currentField.getX() + 1, currentField.getY() - 1, false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        //check up
        if (currentField.getY() > 0) {
            Field targetField = new Field(currentField.getX(), currentField.getY() - 1, false);
            if (getCharacterOnTable(targetField) == EMPTY_FIELD) {
                markTableField(targetField, ASSASSIN_SAFE_FIELD_CHARACTER);
                newFreePathFields.add(targetField);
            }
        }

        currentField.setNeighboursChecked(true);
        ASSASSIN_SAFE_PATH_FIELD_LIST.addAll(newFreePathFields);
    }

    private Field getAssassinLocation() {
        Field assassinLocation = null;
        for (int i = 0; i < TABLE.size(); i++) {
            for (int j = 0; j < TABLE.get(i).length; j++) {
                if (TABLE.get(i)[j] == ASSASSIN) {
                    assassinLocation = new Field(j, i, false);
                    break;
                }
            }
        }
        return assassinLocation;
    }

    private void createCharTable(String[] B) {
        for (String s : B) {
            char[] arr = s.toCharArray();
            TABLE.add(arr);
        }
    }

    private void markFieldsWatchedByGuards() {

        for (int posY = 0; posY < TABLE.size(); posY++) {

            char[] currentRow = TABLE.get(posY);
            for (int currentX = 0; currentX < currentRow.length; currentX++) {

                if (currentRow[currentX] == GUARD_WATCHING_RIGHT) {
                    markGuardWatchingRightFields(posY, currentX);
                }

                if (!isAssassinSeen) {
                    if (currentRow[currentX] == GUARD_WATCHING_LEFT)
                        markGuardWatchingLeftFields(posY, currentX);
                }

                if (!isAssassinSeen) {
                    if (currentRow[currentX] == GUARD_WATCHING_DOWN) {
                        markGuardWatchingDownFields(posY, currentX);
                    }
                }

                if (!isAssassinSeen) {
                    if (currentRow[currentX] == GUARD_WATCHING_UP) {
                        markGuardWatchingUpFields(posY, currentX);
                    }
                }
            }
        }

    }

    private void markGuardWatchingUpFields(int posY, int posX) {

        for (int currentRowIndex = posY - 1; currentRowIndex >= 0; currentRowIndex--) {
            if (TABLE.get(currentRowIndex)[posX] == EMPTY_FIELD || TABLE.get(currentRowIndex)[posX] == GUARDED_FIELD) {
                TABLE.get(currentRowIndex)[posX] = GUARDED_FIELD;
            } else if (TABLE.get(currentRowIndex)[posX] == ASSASSIN) {
                assassinSpotted();
                break;
            } else {
                break;
            }
        }
    }

    private void markGuardWatchingDownFields(int posY, int columnIndex) {
        for (int currentRowIndex = posY + 1; currentRowIndex < TABLE.size(); currentRowIndex++) {
            if (TABLE.get(currentRowIndex)[columnIndex] == EMPTY_FIELD) {
                TABLE.get(currentRowIndex)[columnIndex] = GUARDED_FIELD;
            } else if (TABLE.get(currentRowIndex)[columnIndex] == ASSASSIN) {
                assassinSpotted();
                break;
            } else {
                break;
            }
        }
    }

    private void markGuardWatchingLeftFields(int posY, int posX) {
        char[] currentRow = TABLE.get(posY);
        for (int i = posX - 1; i >= 0; i--) {
            if (currentRow[i] == EMPTY_FIELD || currentRow[i] == GUARDED_FIELD) {
                currentRow[i] = GUARDED_FIELD;
            } else if (currentRow[i] == ASSASSIN) {
                assassinSpotted();
                break;
            } else {
                break;
            }
        }
    }

    public void markGuardWatchingRightFields(int posY, int posX) {
        char[] currentRow = TABLE.get(posY);
        for (int currentColumn = posX + 1; currentColumn < currentRow.length; currentColumn++) {
            if (currentRow[currentColumn] == EMPTY_FIELD || currentRow[currentColumn] == GUARDED_FIELD) {
                currentRow[currentColumn] = GUARDED_FIELD;
            } else if (currentRow[currentColumn] == ASSASSIN) {
                assassinSpotted();
                break;
            } else {
                break;
            }
        }
    }

}

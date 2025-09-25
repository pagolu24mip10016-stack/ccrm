package edu.ccrm.domain;

public enum LetterGrade {
    A_PLUS, A, A_MINUS, B_PLUS, B, B_MINUS, C_PLUS, C, C_MINUS, D, F;

    @Override
    public String toString() {
        switch(this) {
            case A_PLUS: return "A+";
            case A: return "A";
            case A_MINUS: return "A-";
            case B_PLUS: return "B+";
            case B: return "B";
            case B_MINUS: return "B-";
            case C_PLUS: return "C+";
            case C: return "C";
            case C_MINUS: return "C-";
            case D: return "D";
            case F: return "F";
            default: throw new IllegalArgumentException();
        }
    }
}

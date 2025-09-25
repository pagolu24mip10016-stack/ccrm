package edu.ccrm.service;

import edu.ccrm.domain.LetterGrade;

public class GradeService {

    public LetterGrade computeLetterGrade(int marks) {
        if (marks >= 90) return LetterGrade.A_PLUS;
        else if (marks >= 85) return LetterGrade.A;
        else if (marks >= 80) return LetterGrade.A_MINUS;
        else if (marks >= 75) return LetterGrade.B_PLUS;
        else if (marks >= 70) return LetterGrade.B;
        else if (marks >= 65) return LetterGrade.B_MINUS;
        else if (marks >= 60) return LetterGrade.C_PLUS;
        else if (marks >= 55) return LetterGrade.C;
        else if (marks >= 50) return LetterGrade.C_MINUS;
        else if (marks >= 40) return LetterGrade.D;
        else return LetterGrade.F;
    }

    public double computeGPA(int marks) {
        LetterGrade grade = computeLetterGrade(marks);
        switch (grade) {
            case A_PLUS: return 4.0;
            case A: return 4.0;
            case A_MINUS: return 3.7;
            case B_PLUS: return 3.3;
            case B: return 3.0;
            case B_MINUS: return 2.7;
            case C_PLUS: return 2.3;
            case C: return 2.0;
            case C_MINUS: return 1.7;
            case D: return 1.0;
            default: return 0.0; // F or others
        }
    }
}

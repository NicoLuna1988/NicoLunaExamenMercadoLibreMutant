package com.ExamenMercadoLibre.Mutant.Service;

import com.ExamenMercadoLibre.Mutant.Model.EnumDirection;
import com.ExamenMercadoLibre.Mutant.Excepcion.IncorrectNitrogenBaseException;
import com.ExamenMercadoLibre.Mutant.Excepcion.InvalidDataReceivedException;
import com.ExamenMercadoLibre.Mutant.Excepcion.ServiceException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Service
public class DnaAnalyzeServiceImpl  implements  DnaAnalyzeService{

    private char[][] matrixDna;
    private int sequenceCount = 0;
    private char lastNitrogenBase;

    /**
     * Setting Properties
     * CantSameSequenceMinToMutant is the number of times the pattern has to be repeated to determine that it belongs to a mutant
     * CantNitrogenBaseToMutant it is the amount of nitrogen bases that have to be repeated to form the pattern
     */
    private int CantSameSequenceMinToMutant = 2;
    private int CantNitrogenBaseToMutant = 4;


    /**
     * Method that analyzes the DNA sequence
     * @param dna String[]
     * @return boolean True(Mutant) / false (Human)
     */
    public boolean isMutant(String[] dna) throws IncorrectNitrogenBaseException, ServiceException, InvalidDataReceivedException {
        try {

            matrixDna = ProcessDna(dna);
            boolean isMutant = analyzeDNA();

            return isMutant;

        } catch (InvalidDataReceivedException ex) {
            throw new InvalidDataReceivedException(ex.getMessage());
        } catch (IncorrectNitrogenBaseException ex) {
            throw new IncorrectNitrogenBaseException(ex.getMessage());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    /**
     * function that runs the path in all directions of the DNA sequence matrix in search of the pattern that determines whether it is mutant or not
     * @return boolean True(Mutant) / false (Human)
     */
    private boolean analyzeDNA() {

        if (AnalyzeHorizontalDna(matrixDna)) {
            return true;
        } else if (AnalyzeVerticalDna(matrixDna)) { //vertical and horizontal sequences
            return true;
        } else if (AnalyzeBottomDiagonalsFromLeft(matrixDna)) { //diagonals below the main diagonal from the left
            return true;
        } else if (AnalyzeTopDiagonalsFromLeft(matrixDna)) { //diagonals above the main diagonal (including it) from the left
            return true;
        } else if (AnalyzeBottomSecondaryDiagonalsFromRight(matrixDna)) { //diagonals below the secondary diagonal from the right
            return true;
        } else if (AnalyzeTopSecondaryDiagonalsFromRight(matrixDna)) { //diagonals above the secondary diagonal from the right
            return true;
        } else {
            return false;
        }


    }

    /**
     * Function that validates the DNA sequence and assembles the matrix to analyze
     * @return char[][] matrixDna
     */
    private char[][] ProcessDna(String[] dna) throws ServiceException, InvalidDataReceivedException, IncorrectNitrogenBaseException {
        try {
            int dnaSequenceLength = dna.length;
            if (dnaSequenceLength == 0) {
                throw new InvalidDataReceivedException("Empty Sequence");
            }
            Pattern pattern = Pattern.compile("[acgt]+", Pattern.CASE_INSENSITIVE);
            matrixDna = new char[dnaSequenceLength][dnaSequenceLength];
            for (int position = 0; position < dnaSequenceLength; position++) {
                if (dna[position].length() < 4) {
                    throw new InvalidDataReceivedException("It does not have the minimum characters to determine if it is mutant or human");
                } else if (dna[position].length() != dnaSequenceLength) {
                    throw new InvalidDataReceivedException("The nitrogen base size of the sequence does not match the total sequences");
                } else if (!pattern.matcher(dna[position]).matches()) {
                    throw new IncorrectNitrogenBaseException("DNA contains an invalid nitrogen base");
                } else {
                    matrixDna[position] = dna[position].toUpperCase().toCharArray();
                }
            }
            return matrixDna;

        } catch (InvalidDataReceivedException ex) {
            throw new InvalidDataReceivedException(ex.getMessage());
        } catch (IncorrectNitrogenBaseException ex) {
            throw new IncorrectNitrogenBaseException(ex.getMessage());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }

    }


    Boolean AnalyzeHorizontalDna(char[][] matrixDna) {

        for (int row = 0; row < matrixDna.length; row++) {
            lastNitrogenBase = matrixDna[row][0];
            if (AnalyzeHorizontalOrVertical(EnumDirection.HORIZONTAL, lastNitrogenBase, row)) {
                return true;
            }
        }

        return false;
    }

    Boolean AnalyzeVerticalDna(char[][] matrixDna) {

        for (int col = 0; col < matrixDna.length; col++) {
            lastNitrogenBase = matrixDna[0][col];
            if (AnalyzeHorizontalOrVertical(EnumDirection.VERTICAL, lastNitrogenBase, col)) {
                return true;
            }
        }

        return false;
    }

    private boolean AnalyzeHorizontalOrVertical(EnumDirection direction, char lastNitrogenBase, int index) {
        int CountSameNitrogenBase = 1;

        char currentNitrogenBase;

        for (int subindex = 1; matrixDna.length - subindex + CountSameNitrogenBase >= CantNitrogenBaseToMutant && subindex < matrixDna.length; subindex++) {

            currentNitrogenBase = (EnumDirection.HORIZONTAL.equals(direction) ? matrixDna[index][subindex] : matrixDna[subindex][index]);
            if (lastNitrogenBase == currentNitrogenBase) {
                CountSameNitrogenBase++;
                if (CountSameNitrogenBase == CantNitrogenBaseToMutant) {
                    sequenceCount++;
                    CountSameNitrogenBase = 0;
                    if (sequenceCount == CantSameSequenceMinToMutant) {
                        return true;   // is Mutant.
                    }
                }
            } else {
                lastNitrogenBase = currentNitrogenBase;
                CountSameNitrogenBase = 1;
            }
        }
        return false;
    }


    Boolean AnalyzeBottomDiagonalsFromLeft(char[][] matrixDna) {

        for (int row = 1; row < matrixDna.length; row++) {
            if (ProcessDiagonal(EnumDirection.LEFT_TO_RIGHT, false, row, 0)) {
                return true;
            }
        }
        return false;
    }


    Boolean AnalyzeBottomSecondaryDiagonalsFromRight(char[][] dnaSequence) {


        for (int row = 1; row < dnaSequence.length; row++) {
            if (ProcessDiagonal(EnumDirection.RIGHT_TO_LEFT, false, row, dnaSequence.length - 1)) {
                return true;
            }
        }
        return false;
    }


    Boolean AnalyzeTopDiagonalsFromLeft(char[][] dnaSequence) {

        for (int col = 0; col < dnaSequence.length; col++) {
            if (ProcessDiagonal(EnumDirection.LEFT_TO_RIGHT, true, 0, col)) {
                return true;
            }
        }
        return false;
    }


    Boolean AnalyzeTopSecondaryDiagonalsFromRight(char[][] dnaSequence) {


        for (int col = 1; col < dnaSequence.length; col++) {

            if (ProcessDiagonal(EnumDirection.RIGHT_TO_LEFT, true, 0, dnaSequence.length - col)) {
                return true;
            }
        }
        return false;
    }


    private boolean ProcessDiagonal(EnumDirection direction, boolean includeMainDiagonal, int baseN, int baseM) {
        int count = 1;
        int countSameNitrogenBase = 1;
        char lastNitrogenBase = matrixDna[baseN][baseM];
        char currentNitrogenBase;


        boolean bottomCondition = baseN + count < matrixDna.length;


        boolean topCondition = (direction.equals(EnumDirection.LEFT_TO_RIGHT) && baseM + count < matrixDna.length || direction.equals(EnumDirection.RIGHT_TO_LEFT) && baseM - count >= 0);


        while ((includeMainDiagonal == true && topCondition) || (includeMainDiagonal == false && bottomCondition)) {

            currentNitrogenBase = (direction.equals(EnumDirection.LEFT_TO_RIGHT)) ? matrixDna[baseN + count][baseM + count] : matrixDna[baseN + count][baseM - count];
            if (lastNitrogenBase == currentNitrogenBase) {
                countSameNitrogenBase++;
                if (countSameNitrogenBase == CantNitrogenBaseToMutant) {
                    sequenceCount++;
                    countSameNitrogenBase = 0;
                    if (sequenceCount >= CantSameSequenceMinToMutant) {
                        return true; //is Mutant
                    }
                }
            } else {
                lastNitrogenBase = currentNitrogenBase;
                countSameNitrogenBase = 1;
            }

            count++;
            bottomCondition = baseN + count < matrixDna.length;
            topCondition = (direction.equals(EnumDirection.LEFT_TO_RIGHT) && baseM + count < matrixDna.length || direction.equals(EnumDirection.RIGHT_TO_LEFT) && baseM - count >= 0);
        }
        return false; // is Human
    }
}


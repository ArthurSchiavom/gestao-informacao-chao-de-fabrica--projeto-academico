package eapli.base.app.common.console.presentation.formatter;

/* Extraído de https://gist.github.com/Wneh/4139136 */
/* The MIT License (MIT)
 * Copyright (c) 2012 Carl Eriksson
 *
 * Permission is hereby granted, free of charge, to any person obtaininga
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction,including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.ArrayList;
import java.lang.StringBuilder;

public class ConsoleTable {

    /*
     * Modify these to suit your use
     */
    private final int TABLEPADDING = 4;
    private final char SEPERATOR_CHAR = '-';

    private ArrayList<String> headers;
    private ArrayList<ArrayList<String>> table;
    private ArrayList<Integer> maxLength;
    private int rows,cols;

    /**
     * Constructor that needs two arraylist
     * <br>1: The headersIs is one list containing strings with the columns headers
     * <br>2: The content is an matrix of Strings build up with ArrayList containing the content
     *  <br>
     * <br>Call the printTable method to print the table
     */
    public ConsoleTable(ArrayList<String> headersIn, ArrayList<ArrayList<String>> content){
        this.headers = headersIn;
        this.maxLength =  new ArrayList<Integer>();
        //Set headers length to maxLength at first
        for(int i = 0; i < headers.size(); i++){
            maxLength.add(headers.get(i).length());
        }
        this.table = content;

        calcMaxLengthAll();
    }
    /**
     * To update the matrix
     */
    public void updateField(int row, int col, String input){
        //Update the value
        table.get(row).set(col,input);
        //Then calculate the max length of the column
        calcMaxLengthCol(col);
    }

    /**
     * Prints the content in table to console
     */
    public String generateTable(){
        //Take out the
        StringBuilder sb = new StringBuilder();
        StringBuilder sbRowSep = new StringBuilder();
        String padder = "";
        int rowLength = 0;
        String rowSeperator = "";

        //Create padding string containing just containing spaces
        for(int i = 0; i < TABLEPADDING; i++){
            padder += " ";
        }

        //Create the rowSeperator
        for(int i = 0; i < maxLength.size(); i++){
            sbRowSep.append("|");
            for(int j = 0; j < maxLength.get(i)+(TABLEPADDING*2); j++){
                sbRowSep.append(SEPERATOR_CHAR);
            }
        }
        sbRowSep.append("|");
        rowSeperator = sbRowSep.toString();

        sb.append(rowSeperator);
        sb.append("\n");
        //HEADERS
        sb.append("|");
        for(int i = 0; i < headers.size(); i++){
            sb.append(padder);
            sb.append(headers.get(i));
            //Fill up with empty spaces
            for(int k = 0; k < (maxLength.get(i)-headers.get(i).length()); k++){
                sb.append(" ");
            }
            sb.append(padder);
            sb.append("|");
        }
        sb.append("\n");
        sb.append(rowSeperator);
        sb.append("\n");

        //BODY
        for(int i = 0; i < table.size(); i++){
            ArrayList<String> tempRow = table.get(i);
            //New row
            sb.append("|");
            for(int j = 0; j < tempRow.size(); j++){
                sb.append(padder);
                sb.append(tempRow.get(j));
                //Fill up with empty spaces
                for(int k = 0; k < (maxLength.get(j)-tempRow.get(j).length()); k++){
                    sb.append(" ");
                }
                sb.append(padder);
                sb.append("|");
            }
            sb.append("\n");
            sb.append(rowSeperator);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Fills maxLenth with the length of the longest word
     * in each column
     *
     * This will only be used if the user dont send any data
     * in first init
     */
    private void calcMaxLengthAll(){
        for(int i = 0; i < table.size(); i++){
            ArrayList<String> temp = table.get(i);
            for(int j = 0; j < temp.size(); j++){
                //If the table content was longer then current maxLength - update it
                if(temp.get(j).length() > maxLength.get(j)){
                    maxLength.set(j, temp.get(j).length());
                }
            }
        }
    }

    /**
     * Same as calcMaxLength but instead its only for the column given as inparam
     */
    private void calcMaxLengthCol(int col){
        for(int i = 0; i < table.size(); i++){
            if(table.get(i).get(col).length() > maxLength.get(col)){
                maxLength.set(col, table.get(i).get(col).length());
            }
        }
    }
}
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position;

public class GoMainGUI {

    // The word of the day
    static String word = "spill";
    static String guess;
    static char[] wordArr = word.toCharArray();
    static boolean winner;
    // setup the GUI
    static JFrame frame = new JFrame("CWorldle");
    static JPanel mainPanel = new JPanel();

    static JPanel keyboardPanel;

    static CardLayout cL1;
    static JPanel switch1;
    static JPanel iP1;
    static JPanel p1;
    static JPanel dp1;
    static JPanel fp1;

    static CardLayout cL2;
    static JPanel switch2;
    static JPanel iP2;
    static JPanel p2;
    static JPanel dp2;
    static JPanel fp2;

    static CardLayout cL3;
    static JPanel switch3;
    static JPanel iP3;
    static JPanel p3;
    static JPanel dp3;
    static JPanel fp3;

    static CardLayout cL4;
    static JPanel switch4;
    static JPanel iP4;
    static JPanel p4;
    static JPanel dp4;
    static JPanel fp4;

    static CardLayout cL5;
    static JPanel switch5;
    static JPanel iP5;
    static JPanel p5;
    static JPanel dp5;
    static JPanel fp5;

    static CardLayout cL6;
    static JPanel switch6;
    static JPanel iP6;
    static JPanel p6;
    static JPanel dp6;
    static JPanel fp6;

    // keyboard Panel
    static JPanel mainKB;
    static JPanel kb1;
    static JPanel kb2;
    static JPanel kb3;

    // static JPanel currentPanel;
    private static List<JTextField> fieldList;
    static ArrayList<String> guessedWords = new ArrayList<String>();
    static ArrayList<Character> notUsed = new ArrayList<Character>();
    static ArrayList<Character> rightSpot = new ArrayList<Character>();
    static ArrayList<Character> doops = new ArrayList<Character>();
    static ArrayList<Character> inWord = new ArrayList<Character>();
    static ArrayList<Character> guessedChar = new ArrayList<Character>();

    static int numChars = 0;
    static ArrayList<String> charGuess = new ArrayList<String>();
    static boolean validGuess;
    static int guesses = 0;

    static final int maxSize = 1;
    // creates border
    static Border blackline = BorderFactory.createLineBorder(Color.black, 2);

    // create font for input
    static Font font1 = new Font("SansSerif", Font.BOLD, 50);
    static Font font2 = new Font("SansSerif", Font.BOLD, 20);

    static Action actionEnter = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // System.out.println("some action");
            // add checks for valid input && valid word
            // if valid call check function to see what characters are correct
            try {
                checkWord();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        // final int maxSize=1;
        createGUI();

    }

    public static JPanel guessPanel() {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new GridLayout(1, 5));
        returnPanel.setBorder(blackline);
        fieldList = new ArrayList<JTextField>();
        numChars = 0;
        for (int i = 0; i < 5; i++) {
            final JTextField txt1 = new JTextField();
            txt1.setBorder(blackline);
            txt1.setFont(font1);
            txt1.setPreferredSize(new Dimension(150, 20));
            txt1.setHorizontalAlignment(JTextField.CENTER);
            txt1.setCaretColor(Color.WHITE);
            NavigationFilter filter = new NavigationFilter() {
                @Override
                public void setDot(FilterBypass fb, int dot, Position.Bias bias) {
                    // System.out.println("set dot = " + dot);
                    if (numChars > 4) {
                        numChars = -1;
                    }
                    // System.out.println("NumChars= " + numChars);
                    if (dot >= maxSize) {
                        fb.setDot(0, bias);
                        txt1.transferFocus();
                        charGuess.add(txt1.getText());
                        numChars++;
                        return;
                    }
                    fb.setDot(dot, bias);
                }

                @Override
                public void moveDot(FilterBypass fb, int dot, Position.Bias bias) {
                    if (dot >= maxSize) {
                        fb.setDot(0, bias);
                        txt1.transferFocus();
                        return;
                    }
                    fb.moveDot(dot, bias);
                }
            };
            txt1.setNavigationFilter(filter);
            txt1.addActionListener(actionEnter);
            ((AbstractDocument) txt1.getDocument())
                    .setDocumentFilter(new DocumentSizeFilter(maxSize));
            returnPanel.add(txt1);
            fieldList.add(txt1);
        }
        return returnPanel;
    }

    public static JPanel dummyPanel() {
        JPanel returnDP = new JPanel();
        returnDP.setLayout(new GridLayout(1, 5));
        returnDP.setBorder(blackline);
        for (int i = 0; i < 5; i++) {
            final JTextField txt1 = new JTextField();
            txt1.setBorder(blackline);
            txt1.setFont(font1);
            txt1.setPreferredSize(new Dimension(150, 20));
            txt1.setHorizontalAlignment(JTextField.CENTER);
            txt1.setCaretColor(Color.WHITE);
            txt1.setEnabled(false);
            returnDP.add(txt1);
        }
        return returnDP;
    }

    public static JPanel filledPanel() {
        JPanel filledP = new JPanel();
        filledP.setLayout(new GridLayout(1, 5));
        filledP.setBorder(blackline);
        for (int i = 0; i < 5; i++) {
            boolean only;
            final JTextField txt1 = new JTextField();
            txt1.setBorder(blackline);
            txt1.setFont(font1);
            txt1.setPreferredSize(new Dimension(150, 20));
            txt1.setHorizontalAlignment(JTextField.CENTER);
            txt1.setCaretColor(Color.WHITE);
            txt1.setEnabled(false);
            String tmpGuessChar = charGuess.get(i).toString();
            String tmpWordChar = Character.toString(wordArr[i]);
            char c = tmpGuessChar.charAt(0);
            txt1.setText(tmpGuessChar);
            if (check(word) == true && check(guess) == true) {
                if (tmpGuessChar.equals(tmpWordChar)) {
                    txt1.setBackground(Color.GREEN);
                } else if (inWord.contains(c)) {
                    txt1.setBackground(Color.YELLOW);
                }
            } else if (check(guess) == true && check(word) == false) {
                // System.out.println("yes dup chars");
                // get dup char & check if its in the word
                ArrayList<Character> doops = getUniqueCharacters(guess);
                // System.out.println("doops is " + doops);
                // System.out.println("doops size " + doops.size());
                // see if doop is in the right spot before doing anything else
                if (doops.size() >= 1) {
                    // System.out.println("in the doops size check");
                    // System.out.println("doops at 0 is: " + doops.get(0));
                    if (rightSpot.contains(doops.get(0))) {
                        // System.out.println("doop is " + doops.get(0));
                        if (doops.contains(c)) {
                            if (tmpWordChar.equals(tmpGuessChar)) {
                                txt1.setBackground(Color.GREEN);
                                doops.remove(new Character(c));
                            }
                        }
                    } else if (rightSpot.contains(c)) {
                        txt1.setBackground(Color.GREEN);
                    } else if (inWord.contains(c)) {
                        txt1.setBackground(Color.YELLOW);
                    }
                } else if (inWord.contains(c) ){
                    txt1.setBackground(Color.YELLOW);
                }
            } else {
                // System.out.println("no, all unique chars");
                if (tmpGuessChar.equals(tmpWordChar)) {
                    txt1.setBackground(Color.GREEN);
                } else if (inWord.contains(c)) {
                    txt1.setBackground(Color.YELLOW);
                }
            }
            filledP.add(txt1);
        }
        return filledP;
    }

    static void checkWord() throws IOException {
        char[] wordArr = word.toCharArray();
        // System.out.println("charGuess is " + charGuess.toString());
        StringBuilder guessBuild = new StringBuilder();
        for (String s : charGuess) {
            guessBuild.append(s);
        }
        guess = guessBuild.toString();
        if (containsWord(guess) == false) {
            // System.out.println("Invalid word, Please enter a valid guess");
            // System.out.println("char guess pre clear " + charGuess.size());
            Clear(guesses);
            charGuess.clear();
            invalidWord();
            // System.out.println("char guess post clear " + charGuess.size());
            validGuess = false;
        } else if (guess.length() != 5 || guess.isEmpty()) {
            // System.out.println("Invalid guess, only " + guess.length()
            // + " characters! Try again.");
            Clear(guesses);
            charGuess.clear();
            invalidWord();
            validGuess = false;
        } else if (word.equals(guess)) {
            // System.out.println("Correct!");
            guesses++;
            winner = true;
            enableActivePanel();
            infoBox();
            System.exit(0);
        } else {
            guessedWords.add(guess);
            char[] guessArr = guess.toCharArray();
            // System.out.println("guess is " + guess);
            // System.out.println("guess length is " + guessArr.length);
            for (int i = 0; i < guessArr.length; i++) {
                String tmp = String.valueOf(guessArr[i]);
                if (word.indexOf(guessArr[i]) == -1) {
                    // System.out.println("nah it aint in here");
                    if (notUsed.contains(guessArr[i]) == false) {
                        notUsed.add(guessArr[i]);
                    }
                } else if (word.contains(tmp)) {
                    if (guessArr[i] == wordArr[i]) {
                        // System.out.println(guessArr[i] +
                        // " is in the correct spot");
                        if (rightSpot.contains(guessArr[i]) == false) {
                            rightSpot.add(guessArr[i]);
                        }
                    } else {
                        // System.out.println(guessArr[i] +
                        // " is in the word, not this spot");
                        if (inWord.contains(guessArr[i]) == false) {
                            inWord.add(guessArr[i]);
                        }
                    }
                }
            }
            if (validGuess = true) {
                guesses++;
                enableActivePanel();
                charGuess.clear();
                // System.out.println("Right spot is " + rightSpot.toString());
                // System.out.println("in word is " + inWord.toString());
            }
        }
        charGuess.clear();
    }

    static boolean containsWord(String s) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(
                "AA.txt"));
        String st;
        while ((st = br.readLine()) != null) { // final String lineFromFile =
            // scanner.nextLine();
            if (st.contains(s)) {
                return true;
            }
        }
        return false;
    }

    static void createGUI() {
        charGuess.clear();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main Panel for components
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 2 panels for swicthing between place holder and interactive for text
        iP1 = new JPanel();
        iP1.setLayout(new GridLayout(1, 1));
        cL1 = new CardLayout();
        switch1 = new JPanel(cL1);
        dp1 = dummyPanel();
        p1 = guessPanel();
        // fp1 = filledPanel();
        switch1.add(dp1, "PH1");
        switch1.add(p1, "GP1");
        // switch1.add(fp1, "FP1");
        cL1.show(switch1, "GP1");

        iP2 = new JPanel();
        iP2.setLayout(new GridLayout(1, 1));
        cL2 = new CardLayout();
        switch2 = new JPanel(cL2);
        dp2 = dummyPanel();
        p2 = guessPanel();
        switch2.add(dp2, "PH2");
        switch2.add(p2, "GP2");
        cL2.show(switch2, "PH2");

        iP3 = new JPanel();
        iP3.setLayout(new GridLayout(1, 1));
        cL3 = new CardLayout();
        switch3 = new JPanel(cL3);
        dp3 = dummyPanel();
        p3 = guessPanel();
        switch3.add(dp3, "PH3");
        switch3.add(p3, "GP3");
        cL3.show(switch3, "PH3");

        iP4 = new JPanel();
        iP4.setLayout(new GridLayout(1, 1));
        cL4 = new CardLayout();
        switch4 = new JPanel(cL4);
        dp4 = dummyPanel();
        p4 = guessPanel();
        switch4.add(dp4, "PH4");
        switch4.add(p4, "GP4");
        cL4.show(switch4, "PH4");

        iP5 = new JPanel();
        iP5.setLayout(new GridLayout(1, 1));
        cL5 = new CardLayout();
        switch5 = new JPanel(cL5);
        dp5 = dummyPanel();
        p5 = guessPanel();
        switch5.add(dp5, "PH5");
        switch5.add(p5, "GP5");
        cL5.show(switch5, "PH5");

        iP6 = new JPanel();
        iP6.setLayout(new GridLayout(1, 1));
        cL6 = new CardLayout();
        switch6 = new JPanel(cL6);
        dp6 = dummyPanel();
        p6 = guessPanel();
        switch6.add(dp6, "PH6");
        switch6.add(p6, "GP6");
        cL6.show(switch6, "PH6");

        iP1.add(switch1);
        iP2.add(switch2);
        iP3.add(switch3);
        iP4.add(switch4);
        iP5.add(switch5);
        iP6.add(switch6);
        mainPanel.add(iP1);
        mainPanel.add(iP2);
        mainPanel.add(iP3);
        mainPanel.add(iP4);
        mainPanel.add(iP5);
        mainPanel.add(iP6);

        mainPanel.add(keyboard());

        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(600, 700));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void Clear(int theP) {
        // System.out.println("its called");
        // System.out.println("Guesses is in clear function " + guesses);
        switch (theP) {
            case 0:
                p1.removeAll();
                p1 = guessPanel();
                switch1.add(p1, "GP1");
                cL1.show(switch1, "GP1");
                break;
            case 1:
                p2.removeAll();
                p2 = guessPanel();
                switch2.add(p2, "GP2");
                cL2.show(switch2, "GP2");
                break;
            case 2:
                p3.removeAll();
                p3 = guessPanel();
                switch3.add(p3, "GP3");
                cL3.show(switch3, "GP3");
                break;
            case 3:
                p4.removeAll();
                p4 = guessPanel();
                switch4.add(p4, "GP4");
                cL4.show(switch4, "GP4");
                break;
            case 4:
                p5.removeAll();
                p5 = guessPanel();
                switch5.add(p5, "GP5");
                cL5.show(switch5, "GP5");
                break;
            case 5:
                p6.removeAll();
                p6 = guessPanel();
                switch6.add(p6, "GP6");
                cL6.show(switch6, "GP6");
                break;
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void enableActivePanel() {

        // System.out.println("we int the enableActivePanel. Guesses is "
        // + guesses);

        if (winner) {
            switch (guesses) {
                case 0:
                    fp1 = filledPanel();
                    switch1.add(fp1, "FP1");
                    cL1.show(switch1, "FP1");
                    break;
                case 1:
                    fp1 = filledPanel();
                    switch1.add(fp1, "FP1");
                    cL1.show(switch1, "FP1");
                    cL2.show(switch2, "FP2");
                    break;
                case 2:
                    fp2 = filledPanel();
                    switch2.add(fp2, "FP2");
                    cL2.show(switch2, "FP2");
                    cL3.show(switch3, "FP3");
                    break;
                case 3:
                    fp3 = filledPanel();
                    switch3.add(fp3, "FP3");
                    cL3.show(switch3, "FP3");
                    cL4.show(switch4, "FP4");
                    break;
                case 4:
                    fp4 = filledPanel();
                    switch4.add(fp4, "FP4");
                    cL4.show(switch4, "FP4");
                    cL5.show(switch5, "FP5");
                    break;
                case 5:
                    fp5 = filledPanel();
                    switch5.add(fp5, "FP5");
                    cL5.show(switch5, "FP5");
                    cL6.show(switch6, "FP6");
                    break;
            }
        }

        switch (guesses) {
            // case 0:
            // fp1 = filledPanel();
            // switch1.add(fp1, "FP1");
            // cL1.show(switch1, "FP1");
            // break;
            case 1:
                fp1 = filledPanel();
                switch1.add(fp1, "FP1");
                cL1.show(switch1, "FP1");
                cL2.show(switch2, "GP2");
                break;
            case 2:
                fp2 = filledPanel();
                switch2.add(fp2, "FP2");
                cL2.show(switch2, "FP2");
                cL3.show(switch3, "GP3");
                break;
            case 3:
                fp3 = filledPanel();
                switch3.add(fp3, "FP3");
                cL3.show(switch3, "FP3");
                cL4.show(switch4, "GP4");
                break;
            case 4:
                fp4 = filledPanel();
                switch4.add(fp4, "FP4");
                cL4.show(switch4, "FP4");
                cL5.show(switch5, "GP5");
                break;
            case 5:
                fp5 = filledPanel();
                switch5.add(fp5, "FP5");
                cL5.show(switch5, "FP5");
                cL6.show(switch6, "GP6");
                break;
        }
        mainKB.removeAll();
        mainPanel.add(keyboard());
    }

    static JPanel keyboard() {

        mainKB = new JPanel();
        mainKB.setLayout(new BoxLayout(mainKB, BoxLayout.Y_AXIS));
        kb1 = new JPanel();
        kb2 = new JPanel();
        mainKB.add(kb1);

        String row1 = "qwertyuiop";
        String row2 = "asdfghjkl";
        String row3 = "zxcvbnm";

        char[] row1Arr = row1.toCharArray();
        kb1 = new JPanel();
        kb1.setLayout(new GridLayout(1, row1Arr.length));
        kb1.setBorder(blackline);
        for (int i = 0; i < row1Arr.length; i++) {
            String s = Character.toString(row1Arr[i]);
            final Button txt1 = new Button(s);
            txt1.setPreferredSize(new Dimension(10, 10));
            txt1.setEnabled(false);
            txt1.setFont(font2);
            if (rightSpot.contains(row1Arr[i])) {
                txt1.setBackground(Color.GREEN);
            } else if (inWord.contains(row1Arr[i])) {
                txt1.setBackground(Color.YELLOW);
            } else if (notUsed.contains(row1Arr[i])) {
                txt1.setBackground(Color.GRAY);
            }
            kb1.add(txt1);
        }
        char[] row2Arr = row2.toCharArray();
        // JPanel keyboardPanel = new JPanel();
        kb2 = new JPanel();
        kb2.setLayout(new GridLayout(1, row2Arr.length));
        kb2.setBorder(blackline);
        for (int i = 0; i < row2Arr.length; i++) {
            String s = Character.toString(row2Arr[i]);
            final Button txt1 = new Button(s);
            txt1.setPreferredSize(new Dimension(10, 10));
            txt1.setEnabled(false);
            txt1.setFont(font2);
            if (rightSpot.contains(row2Arr[i])) {
                txt1.setBackground(Color.GREEN);
            } else if (inWord.contains(row2Arr[i])) {
                // System.out.println("yeah should change keyboard");
                txt1.setBackground(Color.YELLOW);
            } else if (notUsed.contains(row2Arr[i])) {
                txt1.setBackground(Color.GRAY);
            }
            kb2.add(txt1);
        }
        char[] row3Arr = row3.toCharArray();
        // JPanel keyboardPanel = new JPanel();
        kb3 = new JPanel();
        kb3.setLayout(new GridLayout(1, row3Arr.length));
        kb3.setBorder(blackline);
        for (int i = 0; i < row3Arr.length; i++) {
            String s = Character.toString(row3Arr[i]);
            final Button txt1 = new Button(s);
            txt1.setPreferredSize(new Dimension(10, 10));
            txt1.setEnabled(false);
            txt1.setFont(font2);
            if (rightSpot.contains(row3Arr[i])) {
                txt1.setBackground(Color.GREEN);
            } else if (inWord.contains(row3Arr[i])) {
                txt1.setBackground(Color.YELLOW);
            } else if (notUsed.contains(row3Arr[i])) {
                txt1.setBackground(Color.GRAY);
            }
            kb3.add(txt1);
        }

        mainKB.add(kb1);
        mainKB.add(kb2);
        mainKB.add(kb3);
        return mainKB;
    }

    public static ArrayList<Character> getUniqueCharacters(String s) {
        ArrayList<Character> retArr = new ArrayList<Character>();
        for (int i = 0; i < s.length(); i++) {
            // isThere[s.charAt(i)] = true;
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    retArr.add(s.charAt(i));
                    // System.out.println("What i think i need: " +
                    // s.charAt(i));
                }
            }
        }

        return retArr;
    }

    public static boolean check(CharSequence g) {
        for (int i = 0; i < g.length(); i++) {
            for (int j = i + 1; j < g.length(); j++) {
                if (g.charAt(i) == g.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void infoBox() {
        JOptionPane.showMessageDialog(null, "YOU WON!", "CWordle",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void invalidWord() {
        JOptionPane.showMessageDialog(null,
                "Invalid Word. Please Enter A Valid Guess", "CWordle",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

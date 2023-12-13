import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class SimpleGUI extends JFrame{
    private JButton solveButton = new JButton("GO");
    private JButton randomButton = new JButton("rand");
    private JTextField matrixTextField00 = new JTextField("0", 5);
    private JTextField matrixTextField01 = new JTextField("0", 5);
    private JTextField matrixTextField02 = new JTextField("0", 5);
    private JTextField matrixTextField10 = new JTextField("0", 5);
    private JTextField matrixTextField11 = new JTextField("0", 5);
    private JTextField matrixTextField12 = new JTextField("0", 5);
    private JTextField matrixTextField20 = new JTextField("0", 5);
    private JTextField matrixTextField21 = new JTextField("0", 5);
    private JTextField matrixTextField22 = new JTextField("0", 5);
    private JTextField matrixTextField30 = new JTextField("0", 5);
    private JTextField matrixTextField31 = new JTextField("0", 5);
    private JTextField matrixTextField32 = new JTextField("0", 5);

    private JLabel solvedX1 = new JLabel("");
    private JLabel solvedX2 = new JLabel("");
    private JLabel solvedX3 = new JLabel("");
    private final JLabel RESULT_LABEL = new JLabel(" Result:", SwingConstants.CENTER);
    private final JLabel ERROR_LABEL = new JLabel("", SwingConstants.CENTER);

    public SimpleGUI(){
        super("");
        this.setBounds(820, 440, 430, 180);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SquareRootMethod");
        matrixTextField30.setHorizontalAlignment(SwingConstants.CENTER);
        matrixTextField31.setHorizontalAlignment(SwingConstants.CENTER);
        matrixTextField32.setHorizontalAlignment(SwingConstants.CENTER);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,5,2,2));
        container.add(matrixTextField00);
        container.add(matrixTextField01);
        container.add(matrixTextField02);
        container.add(matrixTextField30);
        container.add(solvedX1);
        container.add(randomButton);
        container.add(matrixTextField10);
        container.add(matrixTextField11);
        container.add(matrixTextField12);
        container.add(matrixTextField31);
        container.add(solvedX2);
        container.add(solveButton);
        container.add(matrixTextField20);
        container.add(matrixTextField21);
        container.add(matrixTextField22);
        container.add(matrixTextField32);
        container.add(solvedX3);
        container.add(ERROR_LABEL);

        //this.initMatrix();

        solveButton.addActionListener(new SolveButtonEventListener());
        randomButton.addActionListener(new RandomButtonEventListener());
    }

    class SolveButtonEventListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            double[][] A = {
                    {Double.parseDouble(matrixTextField00.getText()), Double.parseDouble(matrixTextField01.getText()), Double.parseDouble(matrixTextField02.getText())},
                    {Double.parseDouble(matrixTextField10.getText()), Double.parseDouble(matrixTextField11.getText()), Double.parseDouble(matrixTextField12.getText())},
                    {Double.parseDouble(matrixTextField20.getText()), Double.parseDouble(matrixTextField21.getText()), Double.parseDouble(matrixTextField22.getText())},
            };

            double[] B = {
                    Double.parseDouble(matrixTextField30.getText()),
                    Double.parseDouble(matrixTextField31.getText()),
                    Double.parseDouble(matrixTextField32.getText())
            };

            double X[] = Main.solve(A, B);

            if(X == null){
                solvedX1.setText("X1 = ?");
                solvedX2.setText("X2 = ?");
                solvedX3.setText("X3 = ?");
                ERROR_LABEL.setForeground(Color.RED);
                ERROR_LABEL.setText("ERROR!");

            }
            else {
                solvedX1.setText(String.format("X1 = %.3f", X[0]));
                solvedX2.setText(String.format("X2 = %.3f", X[1]));
                solvedX3.setText(String.format("X3 = %.3f", X[2]));
                ERROR_LABEL.setForeground(Color.BLACK);
                ERROR_LABEL.setText("");

                System.out.println("Matrix X:");
                for(int i = 0; i < X.length; i++){
                    System.out.printf("X%d = %.3f \n", i,  X[i]);
                }
                System.out.println("Is correct: " + Main.isCorrect(A, B, X));
            }
        }
    }

    class RandomButtonEventListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            generateMatrix();
        }
    }
    public void initMatrix(){
        matrixTextField00.setText("3.2");
        matrixTextField01.setText("1.0");
        matrixTextField02.setText("1.0");

        matrixTextField10.setText("1.0");
        matrixTextField11.setText("3.7");
        matrixTextField12.setText("1.0");

        matrixTextField20.setText("1.0");
        matrixTextField21.setText("1.0");
        matrixTextField22.setText("4.2");

        matrixTextField30.setText("4.0");
        matrixTextField31.setText("4.5");
        matrixTextField32.setText("4.0");
    }
    public void generateMatrix(){
        Random rand = new Random();
        int max = 21;
        int min = 5;
        int N = 3;
        int[][] R = new int[N][N];
        int sum = 0;

        for(int i = 0; i < N; i++){
            for(int j = i; j < N; j++){
                if(i == j){
                    R[i][j] = rand.nextInt(max - min) + min;
                }
                else{
                    R[i][j] = rand.nextInt(max)%(R[i][i]/2);
                    R[j][i] = R[i][j];
                }
                sum+=R[i][j];
            }
            sum = 0;
        }

        matrixTextField00.setText(String.valueOf(R[0][0]));
        matrixTextField01.setText(String.valueOf(R[0][1]));
        matrixTextField02.setText(String.valueOf(R[0][2]));

        matrixTextField10.setText(String.valueOf(R[1][0]));
        matrixTextField11.setText(String.valueOf(R[1][1]));
        matrixTextField12.setText(String.valueOf(R[1][2]));

        matrixTextField20.setText(String.valueOf(R[2][0]));
        matrixTextField21.setText(String.valueOf(R[2][1]));
        matrixTextField22.setText(String.valueOf(R[2][2]));

        matrixTextField30.setText(String.valueOf(rand.nextInt(100)));
        matrixTextField31.setText(String.valueOf(rand.nextInt(100)));
        matrixTextField32.setText(String.valueOf(rand.nextInt(100)));
    }
}
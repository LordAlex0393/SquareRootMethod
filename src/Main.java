import java.util.Random;

public class Main {
    private static final double[][] A = {
            {10,   1,   -0.5, 0.7},
            {1,    15,  0.5,  4},
            {-0.5, 0.5, 20,   1},
            {0.7,  4,   1,    17}
    };

    private static final double[] B = {11.2, 20.5, 21, 22.7};

    public static void main(String[] args) {
        initGUI();

//        int N = 8;
//        double[][] A2 = generateMatrix(N);
//        double[] B2 = generateSolutionMatrix(N);
//        printFullMatrix(A2, B2);
//        double X[] = solve(A2, B2);

        double X[] = solve(A, B);
        printFullMatrix(A, B);

        System.out.println("Matrix X:");
        for(int i = 0; i < X.length; i++){
            System.out.printf("X%d\t=\t%.2f \n", i+1,  X[i]);
        }
    }
    public static void initGUI(){
        SimpleGUI app = new SimpleGUI();
        app.setVisible(true);
    }

    public static double[][] createMatrixT(double[][] A) {
        int n = A.length;
        double[][] T = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                if (i == j) {
                    for (int k = 0; k < i; k++) {
                        sum += T[k][i] * T[k][i];
                    }
                    T[i][j] = Math.sqrt(A[i][i] - sum);
                }
                else if(i < j){
                    for (int k = 0; k < i; k++) {
                        sum += T[k][i] * T[k][j];
                    }
                    T[i][j] = (A[i][j] - sum)/T[i][i];
                }
                else if(i > j){
                    T[i][j] = 0;
                }
            }
        }
        return T;
    }

    public static double[] findY(double[][] T, double[] B) {
        int n = T.length;
        double[] Y = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += T[j][i] * Y[j];
            }
            Y[i] = (B[i] - sum) / T[i][i];
        }

        return Y;
    }

    public static double[] findX(double[][] U, double[] Y) {
        int n = U.length;
        double[] X = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U[i][j] * X[j];
            }
            X[i] = (Y[i] - sum) / U[i][i];
        }

        return X;
    }

    public static double[] solve(double[][] A, double[] B){

        if(!isApplicable(A)){
            System.out.println("Метод квадратных корней не применим");
            return null;
        }

        var T = createMatrixT(A);

        double[][] test = multiplyMatrices(transpose(T), T);
        if(!compareMatrices(test, A)){
            System.out.println("Ошибка при задании матрицы");
            return null;
        }

        double[] Y = findY(T, B);
        double[] X = findX(T, Y);
        return X;
    }

    public static double[][] transpose(double[][] A) {
        int n = A.length;
        double[][] AT = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                AT[i][j] = A[j][i];
            }
        }
        return AT;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%.2f\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int m1 = matrix1.length;
        int n1 = matrix1[0].length;
        int m2 = matrix2.length;
        int n2 = matrix2[0].length;

        if (n1 != m2) {
            throw new IllegalArgumentException();
        }

        double[][] result = new double[m1][n2];

        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n2; j++) {
                for (int k = 0; k < n1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    public static boolean compareMatrices(double[][] matrix1, double[][] matrix2) {
        double eps = 0.000001;
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (Math.abs(matrix1[i][j] - matrix2[i][j]) > eps) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCorrect(double[][] A, double[] B, double[] X){
        double eps = 0.000001;
        double sum = 0;
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                sum+=A[i][j]*X[j];
            }
            if(Math.abs(sum-B[i]) > eps){
                return false;
            }
            sum = 0;
        }
        return true;
    }

    public static boolean isApplicable(double[][] A){
        for(int i = 0; i < A.length; i++){
            int sum = 0;
            for(int j = 0; j < A.length; j++){
                if(i!=j){
                    sum+=Math.abs(A[i][j]);
                }
            }
            if(Math.abs(A[i][i]) <= sum){
                return false;
            }
        }
        return true;
    }

    public static double[][] generateMatrix(int N) {
        Random rand = new Random();
        int max = 41;
        int min = 9;
        double[][] R = new double[N][N];
        int sum = 0;

        while (!isApplicable(R)) {
            for (int i = 0; i < N; i++) {
                for (int j = i; j < N; j++) {
                    if (i == j) {
                        R[i][j] = rand.nextInt(max - min) + min;
                    } else {
                        R[i][j] = (rand.nextInt(max-1)+1) % ((R[i][i] / (N - 1)) - 1);
                        R[j][i] = R[i][j];
                    }
                    sum += R[i][j];
                }
                sum = 0;
            }
        }
        return R;
    }
    public static double[] generateSolutionMatrix(int N) {
        Random rand = new Random();
        int max = 101;
        int min = 5;
        double[] R = new double[N];
        for(int i = 0; i < N; i++){
            R[i] = rand.nextInt(max-min)+min;
        }
        return R;
    }

    public static void printFullMatrix(double[][] matrix, double[] B) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%.2f\t", matrix[i][j]);
            }
            System.out.printf("\t= %.2f\n", B[i]);
        }
    }
}